package coachj;

import coachj.dao.DatabaseDirectConnection;
import coachj.enums.SeasonStatus;
import coachj.models.Player;
import coachj.structures.DraftSummary;
import coachj.utils.CoachUtils;
import coachj.utils.CountingUtils;
import coachj.utils.DraftUtils;
import coachj.utils.FranchiseUtils;
import coachj.utils.ListUtils;
import coachj.utils.PlayerUtils;
import coachj.utils.RosterUtils;
import coachj.utils.SceneUtils;
import coachj.utils.SettingsUtils;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for the draft scene
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 15/08/2013
 */
public class DraftController implements Initializable {

    /**
     * FXML components
     */
    @FXML
    private AnchorPane mainContent; /* main content holder */

    @FXML
    private TableView<Player> drafteesTableView;
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
    private TableView<DraftSummary> draftSummaryTableView;
    @FXML
    private TableColumn draftRoundTableColumn;
    @FXML
    private TableColumn draftPickTableColumn;
    @FXML
    private TableColumn draftFranchiseTableColumn;
    @FXML
    private TableColumn draftPlayerTableColumn;
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
    private Button draftPlayerButton;
    @FXML
    private Label draftRoundPickLabel;
    @FXML
    private Label nextPickLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private ProgressBar taskCompletionProgressBar;
    /**
     * Keeps a reference to the application's thread
     */
    private CoachJ application;
    /**
     * Reference to resources file
     */
    private ResourceBundle resources;
    /**
     * task runner
     */
    Task task;
    /**
     * Provides a list of available draftees
     */
    private ObservableList<Player> drafteesList = FXCollections.observableArrayList();
    /**
     * Provides a list for the user's franchise current roster
     */
    private ObservableList<Player> rosterList = FXCollections.observableArrayList();
    /**
     * Provides a list for the draft summary
     */
    private ObservableList<DraftSummary> draftSummaryList = FXCollections.observableArrayList();
    /**
     * Auxiliary fields
     */
    private short round = 0;
    private short pick = 0;
    private short totalRounds = DraftUtils.getTotalDraftRounds();
    private short totalPicks = Short.parseShort(SettingsUtils.getSetting("requiredFranchises", "32"));
    private short season = Short.parseShort(SettingsUtils.getSetting("currentSeason", "0"));
    private short nextFranchise;
    private short userFranchiseId = Short.parseShort(SettingsUtils.getSetting("userFranchise",
            "1"));
    private short minimumPlayersPerTeam = Short.parseShort(SettingsUtils
            .getSetting("minimumPlayersPerTeam", "15"));

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Creates a reference to the resources file and calls the method that
         * retrieves off-season stats
         */
        this.resources = rb;

        /**
         * Database connection
         */
        DatabaseDirectConnection connection = new DatabaseDirectConnection();

        /**
         * Allowing tableViews to resize and fit properly
         */
        drafteesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        draftSummaryTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        rosterTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        /**
         * Binding the columns of both tableviews to their data models
         */
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

        draftRoundTableColumn.setCellValueFactory(
                new PropertyValueFactory<DraftSummary, Short>("draftRound"));
        draftPickTableColumn.setCellValueFactory(
                new PropertyValueFactory<DraftSummary, Short>("draftPick"));
        draftFranchiseTableColumn.setCellValueFactory(
                new PropertyValueFactory<DraftSummary, String>("franchiseName"));
        draftPlayerTableColumn.setCellValueFactory(
                new PropertyValueFactory<DraftSummary, String>("playerName"));

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


        /**
         * Filling tableviews with draftees, previous draft picks and current
         * roster
         */
        fillDrafteesTableView(connection);
        fillDraftSummaryTableView(connection);
        fillRosterTableView(connection);

        /**
         * Updating draft summary
         */
        updateDraftSummary(connection);

        /**
         * Closing connection
         */
        connection.close();
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
     * Asks for confirmation before closing the application
     */
    @FXML
    private void exit() {
        SceneUtils.exit(resources.getString("ch_deseja_sair"),
                resources.getString("ch_sair"));
    }

    /**
     * Fills the table view with draftees
     *
     * @param connection Database connection used to retrieve data
     */
    private void fillDrafteesTableView(DatabaseDirectConnection connection) {

        drafteesList = ListUtils.fillPlayerListFromSQL("SELECT id FROM player "
                + "WHERE franchise IS NULL AND seasons = 0 AND retired = false "
                + "ORDER BY marketValue DESC", connection);
        drafteesTableView.setItems(drafteesList);
    }

    /**
     * Fills the table view with draft summary
     *
     * @param connection Database connection used to retrieve data
     */
    private void fillDraftSummaryTableView(DatabaseDirectConnection connection) {
        draftSummaryList = ListUtils.fillDraftSummaryListFromSQL("SELECT * FROM draft "
                + "WHERE season = " + season + " ORDER BY round, pick", connection);
        draftSummaryTableView.setItems(draftSummaryList);
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
     * Updates the panel with selected player's rates
     */
    @FXML
    private void updatePlayerPanel() {
        if (drafteesTableView.getSelectionModel().getSelectedIndex() != -1) {
            Player selectedPlayer = drafteesTableView.getSelectionModel().getSelectedItem();
            playerPositionLabel.setText(selectedPlayer.getPosition());
            playerNameLabel.setText(selectedPlayer.getFirstName() + " " + selectedPlayer.getLastName());
            playerDefenseDedicationLabel.setText(String.valueOf(selectedPlayer.getDefenseDedication()));
            playerOffenseDedicationLabel.setText(String.valueOf(selectedPlayer.getOffenseDedication()));
            playerFieldGoalsLabel.setText(String.valueOf(selectedPlayer.getFieldGoals()));
            playerFreeThrowsLabel.setText(String.valueOf(selectedPlayer.getFreeThrows()));
            playerThreePointersLabel.setText(String.valueOf(selectedPlayer.getThreePointers()));
            playerBallHandlingLabel.setText(String.valueOf(selectedPlayer.getBallHandling()));
            playerCourtVisionLabel.setText(String.valueOf(selectedPlayer.getCourtVision()));
            playerPassLabel.setText(String.valueOf(selectedPlayer.getPass()));
            playerStealLabel.setText(String.valueOf(selectedPlayer.getSteal()));
            playerBlockLabel.setText(String.valueOf(selectedPlayer.getBlock()));
            playerDefensiveReboundLabel.setText(String.valueOf(selectedPlayer.getDefensiveRebound()));
            playerOffensiveReboundLabel.setText(String.valueOf(selectedPlayer.getOffensiveRebound()));
            draftPlayerButton.setDisable(!(selectedPlayer.getFranchise() == null));
        }
    }

    /**
     * Selects players for the computer-controlled franchises and awaits user to
     * make his choice
     */
    @FXML
    private void proceedDraft() {

        /**
         * Checking if the maximum of rounds and picks was reached
         */
        if (round > totalRounds) {
            SceneUtils.warning(resources.getString("ch_recrutamento_terminado"),
                    resources.getString("ch_atencao"));

            if (SceneUtils.confirm(resources.getString("ch_prosseguir_pre_temporada"),
                    resources.getString("ch_confirmar")) == 0) {
                invokeSigningPlayersTask();
            }
            return;
        }

        /**
         * Checking whether the next franchise to pick up a player it's the
         * user's
         */
        if (nextFranchise == userFranchiseId) {
            SceneUtils.warning(resources.getString("ch_sua_escolha"),
                    resources.getString("ch_atencao"));
            draftPlayerButton.setDisable(false);
        } else if (nextFranchise > 0) {
            /*
             * Retrieving the franchise's coach, his method of drafting and
             * the player chosen
             */
            System.out.println("Franchise: " + nextFranchise + " Round: " + round
                    + " Pick: " + pick); // delete

            /**
             * Database connection
             */
            DatabaseDirectConnection connection = new DatabaseDirectConnection();

            /**
             * Variables to store auxiliary data
             */
            ResultSet resultSet;
            short coachId = FranchiseUtils.getFranchiseCoachId(nextFranchise, connection);
            String draftSQL = CoachUtils.getCoachDraftingSQL(coachId, connection);
            int playerId;
            String franchiseName = FranchiseUtils.getFranchiseCompleteName(nextFranchise,
                    connection);

            /*SceneUtils.warning(resources.getString("ch_rodada") + ": "
                    + round + "\n" + resources.getString("ch_escolha") + ": "
                    + pick + "\n" + resources.getString("ch_franquia")
                    + ": " + franchiseName,
                    resources.getString("ch_atencao"));*/            

            try {

                /**
                 * Retrieving the picked player and executing draft operation
                 */
                // // connection.open();
                resultSet = connection.getResultSet(draftSQL);
                resultSet.first();
                playerId = resultSet.getInt("id");
                makeDraft(playerId, connection);
            } catch (SQLException ex) {
                Logger.getLogger(DraftController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            SceneUtils.warning(resources.getString("ch_recrutamento_terminado"),
                    resources.getString("ch_atencao"));
        }
    }

    /**
     * Updates the draft summary panel
     *
     * @param connection Database connection used to retrieve data
     */
    private void updateDraftSummary(DatabaseDirectConnection connection) {
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        /**
         * Retrieving next round and pick numbers, plus the next franchise to
         * choose a player
         */
        round = DraftUtils.getDraftRound(season, connection);
        pick = DraftUtils.getDraftPick(season, connection);
        nextFranchise = DraftUtils.getNextFranchise(season, round, connection);

        /**
         * Updating controls
         */
        draftRoundPickLabel.setText(resources.getString("ch_rodada") + ": "
                + round + " - " + resources.getString("ch_escolha") + ": "
                + pick);
        nextPickLabel.setText(resources.getString("ch_proxima_escolha") + ": "
                + FranchiseUtils.getFranchiseCompleteName(nextFranchise, connection));
    }

    /**
     * Effectively drafts a player
     *
     * @param playerId Id of the player drafted
     * @param connection Database connection used to retrieve data
     */
    private void makeDraft(int playerId, DatabaseDirectConnection connection) {
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }

        /**
         * Retrieving franchise's and player's name, only to displaying purposes
         */
        String franchiseName = FranchiseUtils.getFranchiseCompleteName(nextFranchise, connection);
        String playerName = PlayerUtils.getPlayerCompleteName(playerId,
                connection);

        /**
         * Generating draft summary
         */
        DraftSummary draftOperation = new DraftSummary();
        draftOperation.setDraftPick(pick);
        draftOperation.setDraftRound(round);
        draftOperation.setFranchiseId(nextFranchise);
        draftOperation.setFranchiseName(franchiseName);
        draftOperation.setPlayerId(playerId);
        draftOperation.setPlayerName(playerName);

        /**
         * Recording draft operation, adding operation to table view
         */
        DraftUtils.recordDraft(draftOperation, connection);
        draftSummaryList.add(draftOperation);

        /*SceneUtils.warning(resources.getString("ch_rodada") + ": "
                + round + "\n" + resources.getString("ch_escolha") + ": "
                + pick + "\n" + resources.getString("ch_franquia")
                + ": " + franchiseName + "\n" + resources.getString("ch_atleta_recrutado")
                + ": " + playerName,
                resources.getString("ch_atencao"));*/

        /**
         * Taking selected player off from the draftees pool
         */
        removeDraftedPlayer(playerId);

        /**
         * Updating drafting summary
         */
        updateDraftSummary(connection);
        proceedDraft();
    }

    /**
     * Removes the drafted player from the draftees pool
     *
     * @param playerId Id of the player to be remove from
     */
    private void removeDraftedPlayer(int playerId) {
        /**
         * Iterating throughout the observable list to find the appropiate id
         * and remove it, if the franchise currently choosing is the one
         * controlled by the user, add the player to its roster table view as
         * well
         */
        for (int i = 0; i < drafteesList.size(); i++) {
            Player currentPlayer = drafteesList.get(i);
            if (currentPlayer.getId() == playerId) {
                if (nextFranchise == userFranchiseId) {
                    System.out.println("Next Franchise: " + nextFranchise); // delete
                    rosterList.add(currentPlayer);
                }
                drafteesList.remove(i);
                return;
            }
        }
    }

    /**
     * Drafts the player selected by the user
     */
    @FXML
    private void userDraftPick() {
        /**
         * Checking whether the user has selected a player
         */
        if (drafteesTableView.getSelectionModel().getSelectedIndex() == -1) {
            SceneUtils.warning(resources.getString("ch_escolha_atleta_recrutado"),
                    resources.getString("ch_atencao"));
            return;
        }

        /**
         * Drafting player and disabling draft button
         */
        Player selectedPlayer = drafteesTableView.getSelectionModel().getSelectedItem();
        int playerId = selectedPlayer.getId();
        makeDraft(playerId, null);
        draftPlayerButton.setDisable(true);
    }

    /**
     * Invokes the task the signs players for the computer-controlled
     * franchises.
     */
    private void invokeSigningPlayersTask() {
        /**
         * Creates a new task, binds its progress to the progress indicator and
         * starts it
         */
        task = null;
        task = signPlayers();
        taskCompletionProgressBar.progressProperty().unbind();
        taskCompletionProgressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    /**
     * Task that signs players for the computer-controlled franchises.
     *
     * @return
     */
    private Task<Integer> signPlayers() {
        /**
         * Displaying controls that show the progress of the task
         */
        messageLabel.setVisible(true);
        messageLabel.setText(resources.getString("ch_demais_franquias_contratando_atletas"));
        taskCompletionProgressBar.setVisible(true);
        mainContent.setCursor(Cursor.WAIT);

        /**
         * Creating task and iterating through the loop that completes the
         * rosters for the other franchises
         */
        Task<Integer> signingPlayersTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                short userFranchiseId = Short.parseShort(SettingsUtils
                        .getSetting("userFranchise", "0"));
                DatabaseDirectConnection connection = new DatabaseDirectConnection();
                String sqlStatement = "SELECT id FROM franchise "
                        + "WHERE registered = 1 AND id != " + userFranchiseId
                        + " ORDER BY record, RAND()";
                ResultSet resultSet;
                int iterations = 0;
                long computerRegisteredFranchisesCount = CountingUtils
                        .computerControlledFranchisesCount();
                short currentFranchiseId;

                /**
                 * Opening connection, populating resultset and positioning
                 * before its beginning
                 */
                // // connection.open();
                resultSet = connection.getResultSet(sqlStatement);
                resultSet.beforeFirst();

                /**
                 * Loop to sign players
                 */
                while (resultSet.next()) {

                    /**
                     * Calling method that signs players
                     */
                    currentFranchiseId = resultSet.getShort("id");
                    System.out.println("Current franchise signing players: "
                            + currentFranchiseId); // delete

                    /**
                     * If the current franchise already has the required number
                     * of players, skip it
                     */
                    if (FranchiseUtils.getActivePlayers(currentFranchiseId, connection)
                            < minimumPlayersPerTeam) {
                        RosterUtils.signPlayers(currentFranchiseId, connection);
                    }

                    /**
                     * Updating task's progress, which is bound to the progress
                     * indicator
                     */
                    iterations++;
                    updateProgress(iterations, computerRegisteredFranchisesCount);
                }

                return iterations;
            }

            /**
             * Method to be executed when the task completes successfully
             */
            @Override
            protected void succeeded() {
                messageLabel.setVisible(false);
                taskCompletionProgressBar.setVisible(false);
                SceneUtils.warning(resources.getString("ch_demais_franquias_contratando_atletas_completo"),
                        resources.getString("ch_atencao"));
                gotoPreSeason();
                System.out.println("Completed!");
                mainContent.setCursor(Cursor.DEFAULT);
            }

            @Override
            protected void cancelled() {
                System.out.println("Cancelled!");
                messageLabel.setVisible(false);
                taskCompletionProgressBar.setVisible(false);
                SceneUtils.error(resources.getString("ch_demais_franquias_contratando_atletas_interrompido"),
                        resources.getString("ch_erro"));
                cancelled();
                mainContent.setCursor(Cursor.DEFAULT);
            }

            @Override
            protected void failed() {
                System.out.println("Failed!");
                messageLabel.setVisible(false);
                taskCompletionProgressBar.setVisible(false);
                SceneUtils.error(resources.getString("ch_demais_franquias_contratando_atletas_erro"),
                        resources.getString("ch_erro"));
                failed();
                mainContent.setCursor(Cursor.DEFAULT);
            }
        };

        return signingPlayersTask;
    }

    /**
     * Loads the preseason scene
     */
    private void gotoPreSeason() {
        SettingsUtils.setSetting("seasonStatus",
                String.valueOf(SeasonStatus.PRE_SEASON.getStatus()));
        SceneUtils.loadScene(this.application, PreSeasonController.class.getClass(),
                "PreSeason.fxml");
    }
}
