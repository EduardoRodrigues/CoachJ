package coachj;

import coachj.dao.DatabaseDirectConnection;
import coachj.enums.SeasonStatus;
import coachj.ingame.GamePlay;
import coachj.ingame.InGamePlayer;
import coachj.utils.ArenaUtils;
import coachj.utils.SceneUtils;
import coachj.utils.ScheduleUtils;
import coachj.utils.SettingsUtils;
import coachj.utils.TimeUtils;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Eduardo
 */
public class GameController implements Initializable {

    /**
     * FXML components
     */
    @FXML
    private AnchorPane mainContent; /* main content holder */

    @FXML
    private Button goBackButton;
    @FXML
    private Label scoreboardAwayTeamLabel;
    @FXML
    private Label scoreboardAwayTeamScoreLabel;
    @FXML
    private Label scoreboardAwayTeamTimeoutsLabel;
    @FXML
    private Label scoreboardAwayTeamFoulsLabel;
    @FXML
    private Label scoreboardPeriodLabel;
    @FXML
    private Label scoreboardTimeLeftLabel;
    @FXML
    private Label scoreboardShotClockLabel;
    @FXML
    private Label scoreboardHomeTeamLabel;
    @FXML
    private Label scoreboardHomeTeamScoreLabel;
    @FXML
    private Label scoreboardHomeTeamTimeoutsLabel;
    @FXML
    private Label scoreboardHomeTeamFoulsLabel;
    @FXML
    private Label gameDateLabel;
    @FXML
    private Label gameTimeLabel;
    @FXML
    private Label arenaLabel;
    @FXML
    private Label attendanceLabel;
    @FXML
    private Label refereeLabel;
    @FXML
    private Tab awayTeamTab;
    @FXML
    private TableView<InGamePlayer> awayTeamBoxScoreTableView;
    @FXML
    private TableColumn awayTeamPlayerJerseyTableColumn;
    @FXML
    private TableColumn awayTeamPlayerCompleteNameTableColumn;
    @FXML
    private Tab homeTeamTab;
    @FXML
    private TableView<InGamePlayer> homeTeamBoxScoreTableView;
    @FXML
    private TableColumn homeTeamPlayerJerseyTableColumn;
    @FXML
    private TableColumn homeTeamPlayerCompleteNameTableColumn;
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
     * Auxiliary fields
     */
    private short season = Short.parseShort(SettingsUtils.getSetting("currentSeason",
            String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));
    private int gameId;
    private GamePlay game;
    
    /**
     * Observable list to keep the box score updated
     */
    private ObservableList<InGamePlayer> awayTeamBoxScoreList = FXCollections
            .observableArrayList();
    private ObservableList<InGamePlayer> homeTeamBoxScoreList = FXCollections
            .observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Creating and opening database connection
         */
        connection = new DatabaseDirectConnection();
        connection.open();
        
        /**
         * Creates a reference to the resources file
         */
        this.resources = rb;

        /**
         * Retrieving the id of the next game to be played
         */
        gameId = ScheduleUtils.getNextGameId(season, connection);
        game = new GamePlay(gameId, connection);

        /**
         * Setting up scoreboard
         */
        scoreboardAwayTeamLabel.setText(game.getTeams().get(0).getCompleteName());
        scoreboardHomeTeamLabel.setText(game.getTeams().get(1).getCompleteName());
        awayTeamTab.setText(game.getTeams().get(0).getCompleteName());
        homeTeamTab.setText(game.getTeams().get(1).getCompleteName());
        updateScoreBoard();
        updateGameInfoPanel();

        /**
         * Getting teams
         */
        awayTeamBoxScoreList.setAll(game.getTeams().get(0).getPlayers());
        homeTeamBoxScoreList.setAll(game.getTeams().get(1).getPlayers());

        /**
         * Binding rosters to the table views
         */
        awayTeamPlayerJerseyTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, Short>("jersey"));
        awayTeamPlayerCompleteNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("completeNamePosition"));
        awayTeamBoxScoreTableView.setItems(awayTeamBoxScoreList);

        homeTeamPlayerJerseyTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, Short>("jersey"));
        homeTeamPlayerCompleteNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("completeNamePosition"));
        homeTeamBoxScoreTableView.setItems(homeTeamBoxScoreList);
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
     * Return to the regular season or playoff scene
     */
    @FXML
    private void goBack() {
        /**
         * Retrieving the season status to define scene content
         */
        int seasonStatus = Integer.parseInt(SettingsUtils.getSetting("seasonStatus", "0"));
        /*game
         game.getTeams().get(0).getPlayers().get(0).setJersey((short) 9);
         /*awayTeamPlayerJerseyTableColumn.setVisible(false);
         awayTeamPlayerJerseyTableColumn.setVisible(true);*/


        if (seasonStatus == SeasonStatus.SEASON.getStatus()) {
            SceneUtils.loadScene(this.application, SeasonController.class.getClass(),
                    "Season.fxml");
        } else if (seasonStatus == SeasonStatus.PLAYOFFS.getStatus()) {
            SceneUtils.loadScene(this.application, SeasonController.class.getClass(),
                    "Playoffs.fxml");
        }

        /**
         * Closing connection
         */
        connection.close();
    }

    /**
     * Updates game's scoreboard
     */
    private void updateScoreBoard() {

        String awayTeamFouls = String.valueOf(game.getTeams().get(0).getFouls());
        String awayTeamScore = String.valueOf(game.getTeams().get(0).getScore());
        String awayTeamTimeoutsLeft = String.valueOf(game.getTeams().get(0).getTimeoutsLeft());
        String homeTeamFouls = String.valueOf(game.getTeams().get(1).getFouls());
        String homeTeamScore = String.valueOf(game.getTeams().get(1).getScore());
        String homeTeamTimeoutsLeft = String.valueOf(game.getTeams().get(1).getTimeoutsLeft());
        String timeLeft = TimeUtils.intToTime(game.getTimeLeft());
        String shotClock = String.valueOf(game.getShotClock());
        String period = String.valueOf(game.getPeriod());

        scoreboardAwayTeamFoulsLabel.setText(awayTeamFouls);
        scoreboardAwayTeamScoreLabel.setText(awayTeamScore);
        scoreboardAwayTeamTimeoutsLabel.setText(awayTeamTimeoutsLeft);

        scoreboardHomeTeamFoulsLabel.setText(homeTeamFouls);
        scoreboardHomeTeamScoreLabel.setText(homeTeamScore);
        scoreboardHomeTeamTimeoutsLabel.setText(homeTeamTimeoutsLeft);

        scoreboardPeriodLabel.setText(period);
        scoreboardShotClockLabel.setText(shotClock);
        scoreboardTimeLeftLabel.setText(timeLeft);
    }

    /**
     * Update game's information panel
     */
    private void updateGameInfoPanel() {
        String gameDate = game.getGameDate();
        String gameTime = game.getGameTime();
        String arenaName = ArenaUtils.getArenaName(game.getArenaId(), connection);
        String attendance = String.format("%d", game.getAttendance());
        String refereeName = game.getReferee().getFirstName() + " " + game.getReferee()
                .getLastName();

        gameDateLabel.setText(gameDate);
        gameTimeLabel.setText(gameTime);
        arenaLabel.setText(arenaName);
        attendanceLabel.setText(attendance);
        refereeLabel.setText(refereeName);
    }
}
