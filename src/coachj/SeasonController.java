/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.ScheduledGame;
import coachj.utils.DateUtils;
import coachj.utils.FranchiseUtils;
import coachj.utils.ListUtils;
import coachj.utils.SceneUtils;
import coachj.utils.ScheduleUtils;
import coachj.utils.SettingsUtils;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Eduardo
 */
public class SeasonController implements Initializable {

    /**
     * FXML components
     */
    @FXML
    private AnchorPane mainContent; /* main content holder */

    @FXML
    private Label completeFranchiseNameLabel;
    @FXML
    private Label seasonLabel;
    @FXML
    private Label currentDateLabel;
    @FXML
    private TableView<ScheduledGame> todayGamesTableView;
    @FXML
    private TableColumn todayGameAwayTeamTableColumn;
    @FXML
    private TableColumn todayGameAwayScoreTableColumn;
    @FXML
    private TableColumn todayGameAtTableColumn;
    @FXML
    private TableColumn todayGameHomeTeamTableColumn;
    @FXML
    private TableColumn todayGameHomeScoreTableColumn;
    @FXML
    private TableColumn todayGameTimeTableColumn;
    @FXML
    private TableView<ScheduledGame> previousGamesTableView;
    @FXML
    private TableColumn previousGameAwayTeamTableColumn;
    @FXML
    private TableColumn previousGameAwayScoreTableColumn;
    @FXML
    private TableColumn previousGameAtTableColumn;
    @FXML
    private TableColumn previousGameHomeTeamTableColumn;
    @FXML
    private TableColumn previousGameHomeScoreTableColumn;
    @FXML
    private TableColumn previousGameTimeTableColumn;
    @FXML
    private Button gotoNextGameButton;
    @FXML
    private Button gameCenterButton;
    /**
     * Observable lists to store information about games
     */
    private ObservableList<ScheduledGame> previousGamesList = FXCollections
            .observableArrayList();
    private ObservableList<ScheduledGame> todayGamesList = FXCollections
            .observableArrayList();
    /**
     * Keeps a reference to the application's thread
     */
    private CoachJ application;
    
    private Object caller;
    private String callerFxml;
    /**
     * Reference to resources file
     */
    private ResourceBundle resources;
    /**
     * Auxiliary fields
     */
    private short userFranchiseId = Short.parseShort(SettingsUtils.getSetting("userFranchise",
            "1"));
    private short season = Short.parseShort(SettingsUtils.getSetting("currentSeason",
            String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));
    String currentDate;
    String yesterday;
    ResultSet resultSet;
    ScheduledGame selectedGame;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Creates a reference to the resources file
         */
        this.resources = rb;
        
        /**
         * Database connection
         */
        DatabaseDirectConnection connection = new DatabaseDirectConnection();
        // // connection.open();

        /**
         * Retrieving auxiliary values
         */
        String completeFranchiseName = FranchiseUtils.getFranchiseCompleteName(userFranchiseId,
                connection);
        completeFranchiseNameLabel.setText(completeFranchiseName);
        seasonLabel.setText(resources.getString("ch_temporada") + " " + season);
        currentDate = ScheduleUtils.getNextScheduledDate(season, connection);
        yesterday = DateUtils.calculateDate(currentDate, -1);
        SettingsUtils.setSetting("currentDate", currentDate);
        currentDateLabel.setText(currentDate);

         /**
         * Allowing tableViews to resize and fit properly
         */
        todayGamesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        /**
         * Binding the columns of both tableviews to their data models
         */
        todayGameTimeTableColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduledGame, String>("time"));
        todayGameAwayTeamTableColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduledGame, String>("awayTeam"));
        todayGameAwayScoreTableColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduledGame, Short>("awayScore"));
        todayGameHomeTeamTableColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduledGame, String>("homeTeam"));
        todayGameHomeScoreTableColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduledGame, Short>("homeScore"));
        
        previousGameTimeTableColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduledGame, String>("time"));
        previousGameAwayTeamTableColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduledGame, String>("awayTeam"));
        previousGameAwayScoreTableColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduledGame, Short>("awayScore"));
        previousGameHomeTeamTableColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduledGame, String>("homeTeam"));
        previousGameHomeScoreTableColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduledGame, Short>("homeScore"));
        
        /**
         * Filling table views with games
         */
        fillTodayGamesTableView(connection);
        fillPreviousGamesTableView(connection);

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
     * Fills the table view with today's games
     *
     * @param connection Database connection used to retrieve data
     */
    private void fillTodayGamesTableView(DatabaseDirectConnection connection) {

        todayGamesList = ListUtils.fillScheduledGameListFromSQL("SELECT id, played, date, "
                + "time, awayTeam, awayScore, homeTeam, homeScore FROM game WHERE "
                + "date = '" + currentDate + "' ORDER BY time, RAND()", connection);
        todayGamesTableView.setItems(todayGamesList);
    }
    
    /**
     * Fills the table view with previous games
     *
     * @param connection Database connection used to retrieve data
     */
    private void fillPreviousGamesTableView(DatabaseDirectConnection connection) {

        previousGamesList = ListUtils.fillScheduledGameListFromSQL("SELECT id, played, date, "
                + "time, awayTeam, awayScore, homeTeam, homeScore FROM game WHERE "
                + "date = '" + yesterday + "' ORDER BY time, RAND()", connection);
        previousGamesTableView.setItems(previousGamesList);
    }
    
    /**
     * Handles the click on the table view with today's games and updates the
     * action buttons states properly
     */
    @FXML
    private void todayGamesTableViewClick() {
        if (todayGamesTableView.getSelectionModel().getSelectedIndex() != -1) {
            selectedGame = todayGamesTableView.getSelectionModel().getSelectedItem();
            updateActionButtons(selectedGame);
        }
    }
    
    /**
     * Handles the click on the table view with previous games and updates the
     * action buttons states properly
     */
    @FXML
    private void previousGamesTableViewClick() {
        if (previousGamesTableView.getSelectionModel().getSelectedIndex() != -1) {
            selectedGame = previousGamesTableView.getSelectionModel().getSelectedItem();
            updateActionButtons(selectedGame);
        }
    }
    
    /**
     * Updates the action buttons accordingly to the selected game
     * 
     * @param game Selected game
     */
    private void updateActionButtons(ScheduledGame game) {
        gotoNextGameButton.setDisable(game.isPlayed());
        gameCenterButton.setDisable(! game.isPlayed());
    }
    
    /**
     * Loads the play game scene
     */
    @FXML
    private void gotoNextGame() {
        SceneUtils.loadScene(application, GameController.class.getClass(), "Game.fxml");        
    }
}
