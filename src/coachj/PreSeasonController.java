package coachj;

import coachj.dao.DatabaseDirectConnection;
import coachj.enums.SeasonStatus;
import coachj.models.Player;
import coachj.structures.PlayerTransactionRecord;
import coachj.utils.FranchiseUtils;
import coachj.utils.GeneralManagerUtils;
import coachj.utils.ListUtils;
import coachj.utils.PlayerUtils;
import coachj.utils.RosterUtils;
import coachj.utils.SalaryUtils;
import coachj.utils.SceneUtils;
import coachj.utils.ScheduleUtils;
import coachj.utils.SettingsUtils;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for the preseason scene
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 04/08/2013
 */
public class PreSeasonController implements Initializable {

    /**
     * FXML components
     */
    @FXML
    private AnchorPane mainContent; /* main content holder */

    @FXML
    private Label completeFranchiseNameLabel;
    @FXML
    private TableView<Player> rosterTableView;
    @FXML
    private TableColumn rosterIdTableColumn;
    @FXML
    private TableColumn rosterPositionTableColumn;
    @FXML
    private TableColumn rosterFirstNameTableColumn;
    @FXML
    private TableColumn rosterLastNameTableColumn;
    @FXML
    private TableColumn rosterAgeTableColumn;
    @FXML
    private TableColumn rosterHeightTableColumn;
    @FXML
    private TableColumn rosterWeightTableColumn;
    @FXML
    private TableColumn rosterMarketValueTableColumn;
    @FXML
    private TableColumn rosterOffenseDedicationTableColumn;
    @FXML
    private TableColumn rosterDefenseDedicationTableColumn;
    @FXML
    private TableView<Player> freeAgentsTableView;
    @FXML
    private TableColumn idTableColumn;
    @FXML
    private TableColumn positionTableColumn;
    @FXML
    private TableColumn firstNameTableColumn;
    @FXML
    private TableColumn lastNameTableColumn;
    @FXML
    private TableColumn ageTableColumn;
    @FXML
    private TableColumn heightTableColumn;
    @FXML
    private TableColumn weightTableColumn;
    @FXML
    private TableColumn marketValueTableColumn;
    @FXML
    private TableColumn offenseDedicationTableColumn;
    @FXML
    private TableColumn defenseDedicationTableColumn;
    @FXML
    private Label playerPositionLabel;
    @FXML
    private Label playerNameLabel;
    @FXML
    private Label playerDefenseDedicationLabel;
    @FXML
    private Label playerOffenseDedicationLabel;
    @FXML
    private Label playerFieldGoalsLabel;
    @FXML
    private Label playerFreeThrowsLabel;
    @FXML
    private Label playerThreePointersLabel;
    @FXML
    private Label playerBallHandlingLabel;
    @FXML
    private Label playerCourtVisionLabel;
    @FXML
    private Label playerPassLabel;
    @FXML
    private Label playerStealLabel;
    @FXML
    private Label playerBlockLabel;
    @FXML
    private Label playerDefensiveReboundLabel;
    @FXML
    private Label playerOffensiveReboundLabel;
    @FXML
    private Button waivePlayerButton;
    @FXML
    private Button releasePlayerButton;
    @FXML
    private Button negotiatePlayerContractButton;
    @FXML
    private Button proceedButton;
    @FXML
    private Button makeOfferButton;
    @FXML
    private Label playerAgeLabel;
    @FXML
    private Label playerSeasonsLabel;
    @FXML
    private Label playerTitlesLabel;
    @FXML
    private Label playerMarketValueLabel;
    @FXML
    private Label playerSalaryLabel;
    @FXML
    private Label playerTotalEarningsLabel;
    @FXML
    private Label playerProjectedSalaryLabel;
    @FXML
    private Label playerRemainingYearsLabel;
    @FXML
    private ProgressBar failedContractAttemptsProgressBar;
    @FXML
    private Label playerProposalLabel;
    @FXML
    private Label franchiseProposalLabel;
    @FXML
    private Label franchiseAssetsLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private ProgressBar taskCompletionProgressBar;
    @FXML
    private ComboBox contractLengthComboBox;
    @FXML
    private Tab negotiationTableTab;
    @FXML
    private TabPane preSeasonTabPane;
    /**
     * Keeps a reference to the application's thread
     */
    private CoachJ application;
    /**
     * Keeps a reference to the application's database connection
     */
    private DatabaseDirectConnection connection;
    /**
     * Reference to resources file
     */
    private ResourceBundle resources;
    /**
     * task runner
     */
    Task task;
    /**
     * Observable lists to store information about roster and free agents
     */
    private ObservableList<Player> rosterList = FXCollections
            .observableArrayList();
    private ObservableList<Player> freeAgentsList = FXCollections
            .observableArrayList();
    /**
     * Auxiliary fields
     */
    private short userFranchiseId = Short.parseShort(SettingsUtils.getSetting("userFranchise",
            "1"));
    private long franchiseAssets;
    private short franchiseGeneralManagerId;
    private int franchiseGeneralManagerDealingStrategy;
    Player selectedPlayer;
    private short minimumPlayersPerTeam = Short.parseShort(SettingsUtils
            .getSetting("minimumPlayersPerTeam", "15"));
    /**
     * Salary projection fields
     */
    double minimumSalary = Double.parseDouble(SettingsUtils.getSetting("minimumSalary",
            "1500000"));
    double playerAverageMarketValue;
    double playerMarketPointValue;
    double playerSalaryAdjusterFactor;
    double playerProjectedSalary;
    double playerProposal;
    double franchiseOffer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Creates a reference to the resources file
         */
        this.resources = rb;
       
        /*
         * Retrieving franchise id, getting name and displaying it in the appropriate label 
         */
        String completeFranchiseName = FranchiseUtils.getFranchiseCompleteName(userFranchiseId,
                connection);
        completeFranchiseNameLabel.setText(completeFranchiseName);
        short userCoachId = FranchiseUtils.getFranchiseCoachId(userFranchiseId,
                connection);
        franchiseAssets = Long.parseLong(FranchiseUtils.getFieldValueAsString(userFranchiseId,
                "assets", connection));
        franchiseGeneralManagerId = FranchiseUtils.getFranchiseGeneralManagerId(userFranchiseId,
                connection);
        franchiseGeneralManagerDealingStrategy = GeneralManagerUtils
                .getGeneralManagerDealingStrategy(franchiseGeneralManagerId, connection) * 10;

        /**
         * Calculating values used to project salaries
         */
        playerAverageMarketValue = SalaryUtils.getPlayerAverageMarketValue(connection);
        playerMarketPointValue = minimumSalary / playerAverageMarketValue;

        /**
         * Allowing tableViews to resize and fit properly
         */
        rosterTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        freeAgentsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        /**
         * Filling contract lenght combo
         */
        contractLengthComboBox.getItems().add("1");
        contractLengthComboBox.getItems().add("2");
        contractLengthComboBox.getItems().add("3");
        contractLengthComboBox.getItems().add("4");
        contractLengthComboBox.getItems().add("5");
        contractLengthComboBox.getSelectionModel().selectFirst();

        /**
         * Binding the columns of both tableviews to their data models
         */
        rosterIdTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("id"));
        rosterPositionTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, String>("position"));
        rosterFirstNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, String>("firstName"));
        rosterLastNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, String>("lastName"));
        rosterAgeTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("age"));
        rosterHeightTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("height"));
        rosterWeightTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("weight"));
        rosterOffenseDedicationTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("offenseDedication"));
        rosterDefenseDedicationTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("defenseDedication"));
        rosterMarketValueTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("marketValue"));

        idTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("id"));
        positionTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, String>("position"));
        firstNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, String>("firstName"));
        lastNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, String>("lastName"));
        ageTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("age"));
        heightTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("height"));
        weightTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("weight"));
        offenseDedicationTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("offenseDedication"));
        defenseDedicationTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("defenseDedication"));
        marketValueTableColumn.setCellValueFactory(
                new PropertyValueFactory<Player, Short>("marketValue"));

        /**
         * Reordering roster
         */
        RosterUtils.reorderRoster(userFranchiseId, connection);

        /**
         * Filling tableviews
         */
        fillRosterTableView(connection);
        fillFreeAgentsTableView(connection);
    }

    /**
     * Creates a reference to the application's thread
     *
     * @param application Reference to the CoachJ thread
     */
    public void setApp(CoachJ application) {
        this.application = application;
    }

     /**
     * Creates a reference to the application's database connection
     * 
     * @param connection Connection used to retrieve data
     */
    public void setDatabaseConnection(DatabaseDirectConnection connection) {
        this.connection = connection;
    }
    
    /**
     * Updates the information panel with data about the player selected in the
     * roster table view
     */
    @FXML
    private void rosterTableViewClick() {
        if (rosterTableView.getSelectionModel().getSelectedIndex() != -1) {
            selectedPlayer = rosterTableView.getSelectionModel().getSelectedItem();
            updatePlayerPanel(selectedPlayer);
        }
    }

    /**
     * Updates the information panel with data about the player selected in the
     * free agents table view
     */
    @FXML
    private void freeAgentsTableViewClick() {
        if (freeAgentsTableView.getSelectionModel().getSelectedIndex() != -1) {
            selectedPlayer = freeAgentsTableView.getSelectionModel().getSelectedItem();
            updatePlayerPanel(selectedPlayer);
        }
    }

    /**
     * Updates the information panel with data about the selected player
     *
     * @param player Player object
     */
    private void updatePlayerPanel(Player player) {
        /**
         * Generates random numbers
         */
        Random generator = new Random();

        /**
         * Attributes
         */
        playerPositionLabel.setText(player.getPosition());
        playerNameLabel.setText(player.getFirstName() + " " + player.getLastName());
        playerDefenseDedicationLabel.setText(String.valueOf(player.getDefenseDedication()));
        playerOffenseDedicationLabel.setText(String.valueOf(player.getOffenseDedication()));
        playerFieldGoalsLabel.setText(String.valueOf(player.getFieldGoals()));
        playerFreeThrowsLabel.setText(String.valueOf(player.getFreeThrows()));
        playerThreePointersLabel.setText(String.valueOf(player.getThreePointers()));
        playerBallHandlingLabel.setText(String.valueOf(player.getBallHandling()));
        playerCourtVisionLabel.setText(String.valueOf(player.getCourtVision()));
        playerPassLabel.setText(String.valueOf(player.getPass()));
        playerStealLabel.setText(String.valueOf(player.getSteal()));
        playerBlockLabel.setText(String.valueOf(player.getBlock()));
        playerDefensiveReboundLabel.setText(String.valueOf(player.getDefensiveRebound()));
        playerOffensiveReboundLabel.setText(String.valueOf(player.getOffensiveRebound()));

        /**
         * Career data
         */
        playerAgeLabel.setText(String.valueOf(player.getAge()));
        playerSeasonsLabel.setText(String.valueOf(player.getSeasons()));
        playerTitlesLabel.setText(String.valueOf(player.getTitles()));
        playerMarketValueLabel.setText(String.valueOf(player.getMarketValue()));
        playerSalaryLabel.setText(DecimalFormat.getCurrencyInstance()
                .format(player.getSalary()));
        playerTotalEarningsLabel.setText(DecimalFormat.getCurrencyInstance()
                .format(player.getTotalEarnings()));

        /**
         * Calculating projected salary, player and franchise's proposals
         */
        playerSalaryAdjusterFactor = player.getMarketValue()
                / playerAverageMarketValue;
        playerProjectedSalary = playerSalaryAdjusterFactor < 1 ? minimumSalary
                : (playerMarketPointValue * playerSalaryAdjusterFactor)
                * player.getMarketValue();
        playerProposal = playerProjectedSalary * ((1 + (double) player.getGreed() / 100));
        franchiseOffer = playerProjectedSalary * ((1 + (double) generator.nextInt(franchiseGeneralManagerDealingStrategy) / 100));

        /**
         * Updating proposal values
         */
        playerProjectedSalaryLabel.setText(DecimalFormat.getCurrencyInstance()
                .format(playerProjectedSalary));
        failedContractAttemptsProgressBar.setProgress((double) player
                .getFailedContractAttempts() / 3);
        playerProposalLabel.setText(DecimalFormat.getCurrencyInstance()
                .format(playerProposal));
        franchiseAssetsLabel.setText(DecimalFormat.getCurrencyInstance()
                .format(franchiseAssets));
        franchiseProposalLabel.setText(DecimalFormat.getCurrencyInstance()
                .format(franchiseOffer));

        /**
         * Updating action buttons state
         */
        updateActionButtons(player);
    }

    /**
     * Update the state of the action buttons accordingly to the selected player
     *
     * @param player Player object
     */
    private void updateActionButtons(Player player) {
        /**
         * Franchise's players
         */
        if (PlayerUtils.getPlayerFranchiseId(player.getId(), null) == userFranchiseId) {
            waivePlayerButton.setDisable(!(player.getRemainingYears() > 0));
            releasePlayerButton.setDisable(!(player.getRemainingYears() == 0));
            negotiatePlayerContractButton.setDisable(!(player.getRemainingYears() == 0));
        } else {
            waivePlayerButton.setDisable(true);
            releasePlayerButton.setDisable(true);
            negotiatePlayerContractButton.setDisable(false);
        }
    }

    /**
     * Fills the table view with draftees
     *
     * @param connection Database connection used to retrieve data
     */
    private void fillRosterTableView(DatabaseDirectConnection connection) {

        rosterList = ListUtils.fillPlayerListFromSQL("SELECT id FROM player "
                + "WHERE franchise = " + userFranchiseId + " AND retired = false "
                + "ORDER BY rosterPosition", connection);
        rosterTableView.setItems(rosterList);
    }

    /**
     * Fills the table view with free agents
     *
     * @param connection Database connection used to retrieve data
     */
    private void fillFreeAgentsTableView(DatabaseDirectConnection connection) {

        freeAgentsList = ListUtils.fillPlayerListFromSQL("SELECT id FROM player "
                + "WHERE isActive =  false AND retired = false "
                + "ORDER BY marketValue DESC", connection);
        freeAgentsTableView.setItems(freeAgentsList);
    }

    /**
     * Makes an offer to the selected player and checks whether he accepts it or
     * not
     */
    @FXML
    private void makeOffer() {       
        /**
         * Checking whether the franchise had already made the maximum of three
         * offer to the player
         */
        if (failedContractAttemptsProgressBar.getProgress() == 1) {
            SceneUtils.warning(resources.getString("ch_maximo_ofertas_atleta"),
                    resources.getString("ch_atencao"));
            return;
        }

        /**
         * Checking whether the user had selected the length of the contract
         */
        if (contractLengthComboBox.getSelectionModel().getSelectedIndex() == -1) {
            SceneUtils.warning(resources.getString("ch_tempo_contrato"),
                    resources.getString("ch_atencao"));
            contractLengthComboBox.requestFocus();
            return;
        }

        /**
         * Validating offer value
         */
        if (selectedPlayer == null) {
            SceneUtils.warning(resources.getString("ch_selecione_atleta"),
                    resources.getString("ch_atencao"));
            return;
        }

        /**
         * Checking whether is a contract renewal or a new sign, if it's a new
         * sign, check if the franchise still has room for a new player
         */
        String transactionType;
        if (PlayerUtils.getPlayerFranchiseId(selectedPlayer.getId(), connection)
                == userFranchiseId) {
            transactionType = "R";
        } else {
            transactionType = "C";
            if (FranchiseUtils.getActivePlayers(userFranchiseId, connection)
                    == minimumPlayersPerTeam) {
                SceneUtils.warning(resources.getString("ch_maximo_atletas_atingido"),
                        resources.getString("ch_atencao"));
                return;
            }
        }

        short contractLength = Short.parseShort((String) contractLengthComboBox
                .getSelectionModel().getSelectedItem());

        if (franchiseOffer < minimumSalary) {
            SceneUtils.warning(resources.getString("ch_valor_proposta_inferior_minimo"),
                    resources.getString("ch_atencao"));
            return;
        }

        if (franchiseOffer > franchiseAssets) {
            SceneUtils.warning(resources.getString("ch_proposta_maior_recursos"),
                    resources.getString("ch_atencao"));
            return;
        }

        /**
         * Creating contract object and checking player's response to offer
         */
        PlayerTransactionRecord offer = new PlayerTransactionRecord();
        offer.setPlayer(selectedPlayer.getId());
        offer.setDate(SettingsUtils.getSetting("currentDate",
                Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
        offer.setFranchise(userFranchiseId);
        offer.setLength(contractLength);
        offer.setSalary((int) franchiseOffer);
        offer.setType(transactionType);
        offer.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));

        if (PlayerUtils.agreeWithTerms(selectedPlayer, playerProjectedSalary, offer)) {
            PlayerUtils.hirePlayer(offer, connection);
            /**
             * If it's a new contract, move the player from the free agents pool
             * to the current roster
             */
            if (transactionType.equalsIgnoreCase("C")) {
                moveSignedPlayer(selectedPlayer.getId());
            }

            /**
             * Updating roster
             */
            updateRosterList(connection);

            /**
             * Informing the user about the offer
             */
            SceneUtils.warning(resources.getString("ch_aceitou_oferta"),
                    resources.getString("ch_atencao"));
        } else {
            /**
             * Recording the failed attempt
             */
            PlayerUtils.recordFailedContractAttempt(selectedPlayer.getId(), connection);
            /**
             * Informing the user about the offer
             */
            SceneUtils.warning(resources.getString("ch_rejeitou_oferta"),
                    resources.getString("ch_atencao"));
        }

        /**
         * Resetting controls
         */
        makeOfferButton.setDisable(true);
        rosterTableView.getSelectionModel().clearSelection();
        freeAgentsTableView.getSelectionModel().clearSelection();
        preSeasonTabPane.getSelectionModel().selectFirst();
        negotiatePlayerContractButton.setDisable(true);
        waivePlayerButton.setDisable(true);
        releasePlayerButton.setDisable(true);

    }

    /**
     * Removes the signed player from the free agents and inserts him into the
     * franchise's roster
     *
     * @param playerId Id of the player to be remove from
     */
    private void moveSignedPlayer(int playerId) {
        /**
         * Iterating throughout the observable list to find the appropiate id
         * and remove it, if the franchise currently choosing is the one
         * controlled by the user, add the player to its roster table view as
         * well
         */
        for (int i = 0; i < freeAgentsList.size(); i++) {
            Player currentPlayer = freeAgentsList.get(i);
            if (currentPlayer.getId() == playerId) {
                freeAgentsList.remove(i);
                rosterList.add(currentPlayer);
                return;
            }
        }
    }

    /**
     * Goes to the negotiation table
     */
    @FXML
    private void gotoNegotiationTable() {
        preSeasonTabPane.getSelectionModel().selectLast();
        makeOfferButton.setDisable(false);
    }

    /**
     * Updates the franchise's roster list
     *
     * @param connection Database connection used to retrieve data
     */
    private void updateRosterList(DatabaseDirectConnection connection) {
        /**
         * Emptying the roster list
         */
        rosterList.clear();

        /**
         * Reordering roster
         */
        RosterUtils.reorderRoster(userFranchiseId, connection);

        /**
         * Filling tableviews
         */
        fillRosterTableView(connection);
    }

    /**
     * Releases the player, asking confirmation before
     */
    @FXML
    private void confirmReleasePlayer() {        

        if (SceneUtils.confirm(resources.getString("ch_deseja_liberar_atleta"),
                resources.getString("ch_confirmar")) == 0) {
            /**
             * Creating contract object and checking player's response to offer
             */
            PlayerTransactionRecord offer = new PlayerTransactionRecord();
            offer.setPlayer(selectedPlayer.getId());
            offer.setDate(SettingsUtils.getSetting("currentDate",
                    Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
            offer.setFranchise(userFranchiseId);
            offer.setLength((short) 0);
            offer.setSalary((int) franchiseOffer);
            offer.setType("L");
            offer.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                    String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));

            /**
             * Releasing player, updating the roster list
             */
            PlayerUtils.releasePlayer(offer, connection);
            moveReleasedPlayer(selectedPlayer.getId());
            updateRosterList(connection);
        }
    }

    /**
     * Releases the player, asking confirmation before
     */
    @FXML
    private void confirmFirePlayer() {       

        if (SceneUtils.confirm(resources.getString("ch_deseja_demitir_atleta"),
                resources.getString("ch_confirmar")) == 0) {
            /**
             * Creating contract object and checking player's response to offer
             */
            PlayerTransactionRecord offer = new PlayerTransactionRecord();
            offer.setPlayer(selectedPlayer.getId());
            offer.setDate(SettingsUtils.getSetting("currentDate",
                    Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
            offer.setFranchise(userFranchiseId);
            offer.setLength((short) 0);
            offer.setSalary((int) franchiseOffer);
            offer.setType("W");
            offer.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                    String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));

            /**
             * Firing player, updating the roster list
             */
            PlayerUtils.firePlayer(offer, connection);
            moveReleasedPlayer(selectedPlayer.getId());
            updateRosterList(connection);
        }
    }

    /**
     * Removes the released player from the franchise's roster and inserts him
     * into the free agents pool
     *
     * @param playerId Id of the player to be remove from
     */
    private void moveReleasedPlayer(int playerId) {
        /**
         * Iterating throughout the observable list to find the appropiate id
         * and remove it, if the franchise currently choosing is the one
         * controlled by the user, add the player to its roster table view as
         * well
         */
        for (int i = 0; i < rosterList.size(); i++) {
            Player currentPlayer = rosterList.get(i);
            if (currentPlayer.getId() == playerId) {
                rosterList.remove(i);
                freeAgentsList.add(currentPlayer);
                return;
            }
        }
    }

    /**
     * Creates the regular season schedule and goes to the regular season scene.
     * If there are teams with incomplete rosters, they're completed.
     */
    @FXML
    private void generateRegularSeasonSchedule() {       
        /**
         * Checking whether the franchise has the required number of active
         * player in its roster
         */
        if (SceneUtils.confirm(resources.getString("ch_prosseguir_temporada"),
                resources.getString("ch_confirmar")) == 0) {
            if (FranchiseUtils.getActivePlayers(userFranchiseId, connection)
                    != minimumPlayersPerTeam) {
                SceneUtils.warning(resources.getString("ch_maximo_atletas_nao_atingido"),
                        resources.getString("ch_atencao"));
                return;
            }
        } else {
            return;
        }

        /**
         * Generating games
         */
        invokeScheduleGeneratingTask();
    }

    /**
     * Invokes the task that generates the regular season schedule
     */
    @FXML
    private void invokeScheduleGeneratingTask() {
        /**
         * Creates a new task, binds its progress to the progress indicator and
         * starts it
         */
        task = null;
        task = generateRegularScheduleTask();
        taskCompletionProgressBar.progressProperty().unbind();
        taskCompletionProgressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    /**
     * Task that signs players for the computer-controlled franchises.
     *
     * @return
     */
    private Task<Integer> generateRegularScheduleTask() {
        /**
         * Displaying controls that show the progress of the task
         */
        mainContent.setCursor(Cursor.WAIT);
        messageLabel.setVisible(true);
        messageLabel.setText(resources.getString("ch_gerando_tabela_jogos"));
        taskCompletionProgressBar.setVisible(true);
        mainContent.setCursor(Cursor.WAIT);

        /**
         * Creating task that generates the schedule
         */
        Task<Integer> generatingScheduleTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {

                /**
                 * Auxiliary variables
                 */
                short season = Short.parseShort(SettingsUtils.getSetting("currentSeason",
                        String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));
                DatabaseDirectConnection connection = new DatabaseDirectConnection();
                // // connection.open();
                int generatedGames = 0;

                /**
                 * Removing all previous games for the given season
                 */
                ScheduleUtils.removeSeasonPreviousGames(season, connection);

                /**
                 * Retrieving the id's of all registered franchises and
                 * inserting them into an arraylist
                 */
                String sqlStatement = "SELECT id FROM franchise WHERE registered = true "
                        + "ORDER BY id";
                ResultSet resultSet = connection.getResultSet(sqlStatement);
                ArrayList franchises = new ArrayList();


                try {
                    resultSet.beforeFirst();

                    while (resultSet.next()) {
                        franchises.add(resultSet.getString("id"));
                    }

                    /**
                     * Auxiliary variables
                     */
                    int gamesCount = franchises.size() * (franchises.size() - 1) * 2;

                    /**
                     * Iterating through the list to generate games
                     */
                    String homeTeam;
                    String awayTeam;
                    String arena;
                    String gameType = "R";
                    String gameTime = "23:00:00";

                    /**
                     * Generating games
                     */
                    for (int i = 0; i < franchises.size(); i++) {
                        homeTeam = (String) franchises.get(i);
                        arena = String.valueOf(FranchiseUtils.getFranchiseArenaId(Integer.parseInt(homeTeam),
                                connection));

                        for (int j = 0; j < franchises.size(); j++) {
                            awayTeam = (String) franchises.get(j);

                            if (!homeTeam.equalsIgnoreCase(awayTeam)) {
                                sqlStatement = "INSERT INTO game (season, homeTeam, awayTeam, "
                                        + "arena, type, time) VALUES (" + season + ", "
                                        + homeTeam + ", " + awayTeam + ", " + arena + ", '"
                                        + gameType + "', '" + gameTime + "')";

                                connection.executeSQL(sqlStatement);

                                generatedGames++;
                                updateProgress(generatedGames, gamesCount);
                            }
                        }
                    }

                    /**
                     * Calling stored procedures that update the time for the
                     * games based on the city where they happen and that reset
                     * the stats for the franchises
                     */
                    connection.executeSQL("CALL updateGameTime");
                    connection.executeSQL("CALL resetFranchiseStats");

                    /**
                     * Scheduling games
                     */
                    int failedAttempts = 0; //auxiliary variable

                    while (generatedGames < gamesCount) {
                        if (ScheduleUtils.scheduleNextGame(season, failedAttempts,
                                connection)) {
                            generatedGames++;
                            failedAttempts = 0;
                            System.out.println("Generated games: " + generatedGames);
                        } else {
                            failedAttempts++;
                        }
                        updateProgress(generatedGames, gamesCount);
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ScheduleUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
                return generatedGames;
            }

            /**
             * Method to be executed when the task completes successfully
             */
            @Override
            protected void succeeded() {
                System.out.println("Completed!");
                mainContent.setCursor(Cursor.DEFAULT);
                SceneUtils.warning(resources.getString("ch_tabela_gerada"),
                        resources.getString("ch_atencao"));
                messageLabel.setVisible(false);
                taskCompletionProgressBar.setVisible(false);
                gotoRegularSeason();
            }

            @Override
            protected void cancelled() {
                System.out.println("Cancelled!");
                messageLabel.setVisible(false);
                taskCompletionProgressBar.setVisible(false);
                SceneUtils.error(resources.getString("ch_gerando_tabela_jogos_erro"),
                        resources.getString("ch_erro"));
                cancelled();
                mainContent.setCursor(Cursor.DEFAULT);
            }

            @Override
            protected void failed() {
                System.out.println("Failed!");
                messageLabel.setVisible(false);
                taskCompletionProgressBar.setVisible(false);
                SceneUtils.error(resources.getString("ch_gerando_tabela_jogos_erro"),
                        resources.getString("ch_erro"));
                failed();
                mainContent.setCursor(Cursor.DEFAULT);
            }
        };

        return generatingScheduleTask;
    }

    /**
     * Loads regular season scene
     */
    private void gotoRegularSeason() {
        SettingsUtils.setSetting("seasonStatus",
                String.valueOf(SeasonStatus.SEASON.getStatus()));
        SceneUtils.loadScene(this.application, SeasonController.class.getClass(),
                "Season.fxml");
    }
}
