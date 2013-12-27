package coachj;

import coachj.dao.DatabaseDirectConnection;
import coachj.enums.SeasonStatus;
import coachj.ingame.PlayGame;
import coachj.ingame.InGamePlayer;
import coachj.structures.Play;
import coachj.utils.ArenaUtils;
import coachj.utils.CoachUtils;
import coachj.utils.FranchiseUtils;
import coachj.utils.SceneUtils;
import coachj.utils.ScheduleUtils;
import coachj.utils.SettingsUtils;
import coachj.utils.TimeUtils;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

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
    private TabPane gameTabPane;
    @FXML
    private Button goBackButton;
    @FXML
    private Button playGameButton;
    @FXML
    private Button pauseGameButton;
    @FXML
    private Button saveGameButton;
    @FXML
    private CheckBox fastModeCheckBox;
    @FXML
    private Label scoreboardAwayTeamLabel;
    @FXML
    private Label scoreboardAwayTeamRecordLabel;
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
    private Label scoreboardHomeTeamRecordLabel;
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
    private Label awayTeamCompleteNameLabel;
    @FXML
    private TableView<InGamePlayer> awayTeamBoxScoreTableView;
    @FXML
    private TableColumn awayTeamPlayerJerseyTableColumn;
    @FXML
    private TableColumn awayTeamPlayerCompleteNameTableColumn;
    @FXML
    private TableColumn awayTeamPlayerMinutesTableColumn;
    @FXML
    private TableColumn awayTeamPlayerPointsTableColumn;
    @FXML
    private TableColumn awayTeamPlayerFieldGoalsTableColumn;
    @FXML
    private TableColumn awayTeamPlayerFreeThrowsTableColumn;
    @FXML
    private TableColumn awayTeamPlayerThreePointersTableColumn;
    @FXML
    private TableColumn awayTeamPlayerDefensiveReboundsTableColumn;
    @FXML
    private TableColumn awayTeamPlayerOffensiveReboundsTableColumn;
    @FXML
    private TableColumn awayTeamPlayerTotalReboundsTableColumn;
    @FXML
    private TableColumn awayTeamPlayerAssistsTableColumn;
    @FXML
    private TableColumn awayTeamPlayerStealsTableColumn;
    @FXML
    private TableColumn awayTeamPlayerBlocksTableColumn;
    @FXML
    private TableColumn awayTeamPlayerTurnoversTableColumn;
    @FXML
    private TableColumn awayTeamPlayerPersonalFoulsTableColumn;
    @FXML
    private TableColumn awayTeamPlayerStaminaTableColumn;
    @FXML
    private TableColumn awayTeamPlayerPerformanceIndexTableColumn;
    @FXML
    private TableColumn awayTeamPlayerOffensiveIndexTableColumn;
    @FXML
    private TableColumn awayTeamPlayerDefensiveIndexTableColumn;
    @FXML
    private TableColumn awayTeamPlayerEfficiencyIndexTableColumn;
    @FXML
    private TableColumn awayTeamPlayerOffensiveMomentumTableColumn;
    @FXML
    private TableColumn awayTeamPlayerDefensiveMomentumTableColumn;
    @FXML
    private Tab homeTeamTab;
    @FXML
    private Label homeTeamCompleteNameLabel;
    @FXML
    private TableView<InGamePlayer> homeTeamBoxScoreTableView;
    @FXML
    private TableColumn homeTeamPlayerJerseyTableColumn;
    @FXML
    private TableColumn homeTeamPlayerCompleteNameTableColumn;
    @FXML
    private TableColumn homeTeamPlayerMinutesTableColumn;
    @FXML
    private TableColumn homeTeamPlayerPointsTableColumn;
    @FXML
    private TableColumn homeTeamPlayerFieldGoalsTableColumn;
    @FXML
    private TableColumn homeTeamPlayerFreeThrowsTableColumn;
    @FXML
    private TableColumn homeTeamPlayerThreePointersTableColumn;
    @FXML
    private TableColumn homeTeamPlayerDefensiveReboundsTableColumn;
    @FXML
    private TableColumn homeTeamPlayerOffensiveReboundsTableColumn;
    @FXML
    private TableColumn homeTeamPlayerTotalReboundsTableColumn;
    @FXML
    private TableColumn homeTeamPlayerAssistsTableColumn;
    @FXML
    private TableColumn homeTeamPlayerStealsTableColumn;
    @FXML
    private TableColumn homeTeamPlayerBlocksTableColumn;
    @FXML
    private TableColumn homeTeamPlayerTurnoversTableColumn;
    @FXML
    private TableColumn homeTeamPlayerPersonalFoulsTableColumn;
    @FXML
    private TableColumn homeTeamPlayerStaminaTableColumn;
    @FXML
    private TableColumn homeTeamPlayerPerformanceIndexTableColumn;
    @FXML
    private TableColumn homeTeamPlayerOffensiveIndexTableColumn;
    @FXML
    private TableColumn homeTeamPlayerDefensiveIndexTableColumn;
    @FXML
    private TableColumn homeTeamPlayerEfficiencyIndexTableColumn;
    @FXML
    private TableColumn homeTeamPlayerOffensiveMomentumTableColumn;
    @FXML
    private TableColumn homeTeamPlayerDefensiveMomentumTableColumn;
    @FXML
    private Tab playByPlayTab;
    @FXML
    private TableView<Play> playByPlayTableView;
    @FXML
    private TableColumn playByPlayPeriodTableColumn;
    @FXML
    private TableColumn playByPlayTimeTableColumn;
    @FXML
    private TableColumn playByPlayAwayScoreTableColumn;
    @FXML
    private TableColumn playByPlayHomeScoreTableColumn;
    @FXML
    private TableColumn playByPlayTableColumn;
    @FXML
    private Tab scoringLogTab;
    @FXML
    private TableView<Play> scoringLogTableView;
    @FXML
    private TableColumn scoringLogPeriodTableColumn;
    @FXML
    private TableColumn scoringLogTimeTableColumn;
    @FXML
    private TableColumn scoringLogAwayScoreTableColumn;
    @FXML
    private TableColumn scoringLogHomeScoreTableColumn;
    @FXML
    private TableColumn scoringLogTableColumn;
    @FXML
    private Label lastScoringPlayLabel;
    @FXML
    private Label lastScorerStatsLineLabel;
    @FXML
    private Label lastScoringPlayScoreLabel;
    @FXML
    private Label savingInformationLabel;
    @FXML
    private Tab scoringTab;
    @FXML
    private Label scoringTabAwayTeamLabel;
    @FXML
    private Label scoringTabAwayTeamFieldGoalsLabel;
    @FXML
    private Label scoringTabAwayTeamThreePointersLabel;
    @FXML
    private Label scoringTabAwayTeamFreeThrowsLabel;
    @FXML
    private Label scoringTabAwayTeamPointsInThePaintLabel;
    @FXML
    private Label scoringTabAwayTeamScoringDroughtLabel;
    @FXML
    private Label scoringTabAwayTeamBiggestLeadLabel;
    @FXML
    private Label scoringTabAwayTeamLongestRunLabel;
    @FXML
    private Label scoringTabAwayTeamCurrentRunLabel;
    @FXML
    private Label scoringTabAwayTeamSecondChancePointsLabel;
    @FXML
    private Label scoringTabAwayTeamBenchPointsLabel;
    @FXML
    private Label scoringTabAwayTeamFastbreakPointsLabel;
    @FXML
    private Label scoringTabHomeTeamLabel;
    @FXML
    private Label scoringTabHomeTeamFieldGoalsLabel;
    @FXML
    private Label scoringTabHomeTeamThreePointersLabel;
    @FXML
    private Label scoringTabHomeTeamFreeThrowsLabel;
    @FXML
    private Label scoringTabHomeTeamPointsInThePaintLabel;
    @FXML
    private Label scoringTabHomeTeamScoringDroughtLabel;
    @FXML
    private Label scoringTabHomeTeamBiggestLeadLabel;
    @FXML
    private Label scoringTabHomeTeamLongestRunLabel;
    @FXML
    private Label scoringTabHomeTeamCurrentRunLabel;
    @FXML
    private Label scoringTabHomeTeamSecondChancePointsLabel;
    @FXML
    private Label scoringTabHomeTeamBenchPointsLabel;
    @FXML
    private Label scoringTabHomeTeamFastbreakPointsLabel;
    @FXML
    private Tab moreStatsTab;
    @FXML
    private Label moreStatsTabAwayTeamLabel;
    @FXML
    private Label moreStatsTabAwayTeamOffensiveReboundsLabel;
    @FXML
    private Label moreStatsTabAwayTeamDefensiveReboundsLabel;
    @FXML
    private Label moreStatsTabAwayTeamTotalReboundsLabel;
    @FXML
    private Label moreStatsTabAwayTeamAssistsLabel;
    @FXML
    private Label moreStatsTabAwayTeamBlocksLabel;
    @FXML
    private Label moreStatsTabAwayTeamStealsLabel;
    @FXML
    private Label moreStatsTabAwayTeamTurnoversLabel;
    @FXML
    private Label moreStatsTabAwayTeamFoulsLabel;
    @FXML
    private Label moreStatsTabHomeTeamLabel;
    @FXML
    private Label moreStatsTabHomeTeamOffensiveReboundsLabel;
    @FXML
    private Label moreStatsTabHomeTeamDefensiveReboundsLabel;
    @FXML
    private Label moreStatsTabHomeTeamTotalReboundsLabel;
    @FXML
    private Label moreStatsTabHomeTeamAssistsLabel;
    @FXML
    private Label moreStatsTabHomeTeamBlocksLabel;
    @FXML
    private Label moreStatsTabHomeTeamStealsLabel;
    @FXML
    private Label moreStatsTabHomeTeamTurnoversLabel;
    @FXML
    private Label moreStatsTabHomeTeamFoulsLabel;
    @FXML
    private Tab topPerformersStatsTab;
    @FXML
    private Label topPerformersTabAwayTeamLabel;
@FXML
    private Label topPerformersTabAwayTeamTopPerformerLabel;
@FXML
    private Label topPerformersTabAwayTeamTopPerformerPerformanceIndexLabel;
@FXML
    private Label topPerformersTabAwayTeamTopPerformerStatsLabel;
 @FXML
    private Label topPerformersTabHomeTeamLabel;
@FXML
    private Label topPerformersTabHomeTeamTopPerformerLabel;
@FXML
    private Label topPerformersTabHomeTeamTopPerformerPerformanceIndexLabel;
@FXML
    private Label topPerformersTabHomeTeamTopPerformerStatsLabel;

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
    private PlayGame game;
    /**
     * playGameTask runners
     */
    private Task playGameTask;
    private Task saveGameTask;
    /**
     * Observable lists to keep the game information updated
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
        game = new PlayGame(gameId, connection);

        /**
         * Setting up scoreboard, game info panel and others information display controls
         */
        scoreboardAwayTeamLabel.setText(game.getTeams().get(1).getCompleteName());
        scoringTabAwayTeamLabel.setText(game.getTeams().get(1).getCompleteName());
        moreStatsTabAwayTeamLabel.setText(game.getTeams().get(1).getCompleteName());
        topPerformersTabAwayTeamLabel.setText(game.getTeams().get(1).getCompleteName());
        scoreboardAwayTeamRecordLabel.setText(FranchiseUtils.getFranchiseScheduleWinsLosses(
                game.getTeams().get(1).getId(), connection));
        scoreboardHomeTeamLabel.setText(game.getTeams().get(2).getCompleteName());
        scoringTabHomeTeamLabel.setText(game.getTeams().get(2).getCompleteName());
        moreStatsTabHomeTeamLabel.setText(game.getTeams().get(2).getCompleteName());
        topPerformersTabHomeTeamLabel.setText(game.getTeams().get(2).getCompleteName());
        scoreboardHomeTeamRecordLabel.setText(FranchiseUtils.getFranchiseScheduleWinsLosses(
                game.getTeams().get(2).getId(), connection));
        awayTeamTab.setText("Boxscore: " + game.getTeams().get(1).getCompleteName());
        homeTeamTab.setText("Boxscore: " + game.getTeams().get(2).getCompleteName());
        awayTeamCompleteNameLabel.setText(game.getTeams().get(1).getCompleteName() + " ("
                + resources.getString("ch_tecnico") + ": " + CoachUtils.getCoachCompleteName(game.getTeams().get(1)
                        .getCoachId(), connection) + ")");
        homeTeamCompleteNameLabel.setText(game.getTeams().get(2).getCompleteName() + " ("
                + resources.getString("ch_tecnico") + ": " + CoachUtils.getCoachCompleteName(game.getTeams().get(2)
                        .getCoachId(), connection) + ")");
        playByPlayAwayScoreTableColumn.setText(game.getTeams().get(1).getAbbreviature());
        playByPlayHomeScoreTableColumn.setText(game.getTeams().get(2).getAbbreviature());
        scoringLogAwayScoreTableColumn.setText(game.getTeams().get(1).getAbbreviature());
        scoringLogHomeScoreTableColumn.setText(game.getTeams().get(2).getAbbreviature());
        updateGameStats();
        updateGameInfoPanel();

        /**
         * Allowing tableViews to resize and fit properly
         */
        homeTeamBoxScoreTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        awayTeamBoxScoreTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        playByPlayTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        scoringLogTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
     * Updates game's stats
     */
    private void updateGameStats() {

        /**
         * Retrieving stats from the PlayGame object
         */
        String awayTeamFouls = String.valueOf(game.getTeams().get(1).getFouls());
        String awayTeamScore = String.valueOf(game.getTeams().get(1).getScore());
        String awayTeamTimeoutsLeft = String.valueOf(game.getTeams().get(1).getTimeoutsLeft());
        String homeTeamFouls = String.valueOf(game.getTeams().get(2).getFouls());
        String homeTeamScore = String.valueOf(game.getTeams().get(2).getScore());
        String homeTeamTimeoutsLeft = String.valueOf(game.getTeams().get(2).getTimeoutsLeft());
        String timeLeft = TimeUtils.intToTime(game.getTimeLeft());
        String shotClock = String.valueOf(game.getShotClock());
        String period = String.valueOf(game.getPeriod());
        String lastScoringPlayScore = game.getLastScoringPlayScore();
        String lastScoringPlay = game.getLastScoringPlay();
        String lastScorerStats = game.getLastScorerStats();

        /**
         * Scoring tab stats
         */
        String awayTeamFieldGoals = String.format("%01d/%01d (%01.3f%%)", game.getTeams().get(1).getFieldGoalsMade(),
                game.getTeams().get(1).getFieldGoalsAttempted(), (double) game.getTeams().get(1).getFieldGoalsMade()
                / game.getTeams().get(1).getFieldGoalsAttempted());
        String awayTeamThreePointers = String.format("%01d/%01d (%01.3f%%)", game.getTeams().get(1).getThreePointersMade(),
                game.getTeams().get(1).getThreePointersAttempted(), (double) game.getTeams().get(1).getThreePointersMade()
                / game.getTeams().get(1).getThreePointersAttempted());
        String awayTeamFreeThrows = String.format("%01d/%01d (%01.3f%%)", game.getTeams().get(1).getFreeThrowsMade(),
                game.getTeams().get(1).getFreeThrowsAttempted(), (double) game.getTeams().get(1).getFreeThrowsMade()
                / game.getTeams().get(1).getFreeThrowsAttempted());
        String awayTeamPointsInThePaint = String.format("%01d", game.getTeams().get(1).getPointsInThePaint());
        String awayTeamScoringDrought = TimeUtils.intToTime(game.getTeams().get(1).getScoringDrought());
        String awayTeamBiggestLead = String.format("%01d", game.getTeams().get(1).getBiggestLead());
        String awayTeamLongestRun = String.format("%01d", game.getTeams().get(1).getLongestRun());
        String awayTeamCurrentRun = String.format("%01d", game.getTeams().get(1).getCurrentRun());
        String awayTeamSecondChancePoints = String.format("%01d", game.getTeams().get(1).getSecondChancePoints());
        String awayTeamBenchPoints = String.format("%01d", game.getTeams().get(1).getBenchPoints());
        String awayTeamFastbreakPoints = String.format("%01d", game.getTeams().get(1).getFastbreakPoints());

        String homeTeamFieldGoals = String.format("%01d/%01d (%01.3f%%)", game.getTeams().get(2).getFieldGoalsMade(),
                game.getTeams().get(2).getFieldGoalsAttempted(), (double) game.getTeams().get(2).getFieldGoalsMade()
                / game.getTeams().get(2).getFieldGoalsAttempted());
        String homeTeamThreePointers = String.format("%01d/%01d (%01.3f%%)", game.getTeams().get(2).getThreePointersMade(),
                game.getTeams().get(2).getThreePointersAttempted(), (double) game.getTeams().get(2).getThreePointersMade()
                / game.getTeams().get(2).getThreePointersAttempted());
        String homeTeamFreeThrows = String.format("%01d/%01d (%01.3f%%)", game.getTeams().get(2).getFreeThrowsMade(),
                game.getTeams().get(2).getFreeThrowsAttempted(), (double) game.getTeams().get(2).getFreeThrowsMade()
                / game.getTeams().get(2).getFreeThrowsAttempted());
        String homeTeamPointsInThePaint = String.format("%01d", game.getTeams().get(2).getPointsInThePaint());
        String homeTeamScoringDrought = TimeUtils.intToTime(game.getTeams().get(2).getScoringDrought());
        String homeTeamBiggestLead = String.format("%01d", game.getTeams().get(2).getBiggestLead());
        String homeTeamLongestRun = String.format("%01d", game.getTeams().get(2).getLongestRun());
        String homeTeamCurrentRun = String.format("%01d", game.getTeams().get(2).getCurrentRun());
        String homeTeamSecondChancePoints = String.format("%01d", game.getTeams().get(2).getSecondChancePoints());
        String homeTeamBenchPoints = String.format("%01d", game.getTeams().get(2).getBenchPoints());
        String homeTeamFastbreakPoints = String.format("%01d", game.getTeams().get(2).getFastbreakPoints());

        /**
         * Retrieving top performers
         */
        InGamePlayer awayTeamBestPerformer = game.getTeams().get(1).getBestPerformer();
        InGamePlayer homeTeamBestPerformer = game.getTeams().get(2).getBestPerformer();
        
        /**
         * More stats tab
         */
        String awayTeamDefensiveRebounds = String.format("%01d", game.getTeams().get(1).getDefensiveRebounds());
        String awayTeamOffensiveRebounds = String.format("%01d", game.getTeams().get(1).getOffensiveRebounds());
        String awayTeamTotalRebounds = String.format("%01d", game.getTeams().get(1).getDefensiveRebounds()
                + game.getTeams().get(1).getOffensiveRebounds());
        String awayTeamAssists = String.format("%01d", game.getTeams().get(1).getAssists());
        String awayTeamBlocks = String.format("%01d", game.getTeams().get(1).getBlocks());
        String awayTeamSteals = String.format("%01d", game.getTeams().get(1).getSteals());
        String awayTeamTurnovers = String.format("%01d", game.getTeams().get(1).getTurnovers());
        String awayTeamTotalFouls = String.format("%01d", game.getTeams().get(1).getTotalFouls());

        String homeTeamDefensiveRebounds = String.format("%01d", game.getTeams().get(2).getDefensiveRebounds());
        String homeTeamOffensiveRebounds = String.format("%01d", game.getTeams().get(2).getOffensiveRebounds());
        String homeTeamTotalRebounds = String.format("%01d", game.getTeams().get(2).getDefensiveRebounds()
                + game.getTeams().get(2).getOffensiveRebounds());
        String homeTeamAssists = String.format("%01d", game.getTeams().get(2).getAssists());
        String homeTeamBlocks = String.format("%01d", game.getTeams().get(2).getBlocks());
        String homeTeamSteals = String.format("%01d", game.getTeams().get(2).getSteals());
        String homeTeamTurnovers = String.format("%01d", game.getTeams().get(2).getTurnovers());
        String homeTeamTotalFouls = String.format("%01d", game.getTeams().get(2).getTotalFouls());

        /**
         * Updating main scoreboard
         */
        scoreboardAwayTeamFoulsLabel.setText(awayTeamFouls);
        scoreboardAwayTeamScoreLabel.setText(awayTeamScore);
        scoreboardAwayTeamTimeoutsLabel.setText(awayTeamTimeoutsLeft);

        scoreboardHomeTeamFoulsLabel.setText(homeTeamFouls);
        scoreboardHomeTeamScoreLabel.setText(homeTeamScore);
        scoreboardHomeTeamTimeoutsLabel.setText(homeTeamTimeoutsLeft);

        scoreboardPeriodLabel.setText(period);
        scoreboardShotClockLabel.setText(shotClock);
        scoreboardTimeLeftLabel.setText(timeLeft);

        /**
         * Updating last scoring play
         */
        lastScoringPlayScoreLabel.setText(lastScoringPlayScore);
        lastScoringPlayLabel.setText(lastScoringPlay);
        lastScorerStatsLineLabel.setText(lastScorerStats);

        /**
         * Updating scoring stats
         */
        scoringTabAwayTeamFieldGoalsLabel.setText(awayTeamFieldGoals);
        scoringTabAwayTeamThreePointersLabel.setText(awayTeamThreePointers);
        scoringTabAwayTeamFreeThrowsLabel.setText(awayTeamFreeThrows);
        scoringTabAwayTeamPointsInThePaintLabel.setText(awayTeamPointsInThePaint);
        scoringTabAwayTeamScoringDroughtLabel.setText(awayTeamScoringDrought);
        scoringTabAwayTeamBiggestLeadLabel.setText(awayTeamBiggestLead);
        scoringTabAwayTeamLongestRunLabel.setText(awayTeamLongestRun);
        scoringTabAwayTeamCurrentRunLabel.setText(awayTeamCurrentRun);
        scoringTabAwayTeamSecondChancePointsLabel.setText(awayTeamSecondChancePoints);
        scoringTabAwayTeamBenchPointsLabel.setText(awayTeamBenchPoints);
        scoringTabAwayTeamFastbreakPointsLabel.setText(awayTeamFastbreakPoints);

        scoringTabHomeTeamFieldGoalsLabel.setText(homeTeamFieldGoals);
        scoringTabHomeTeamThreePointersLabel.setText(homeTeamThreePointers);
        scoringTabHomeTeamFreeThrowsLabel.setText(homeTeamFreeThrows);
        scoringTabHomeTeamPointsInThePaintLabel.setText(homeTeamPointsInThePaint);
        scoringTabHomeTeamScoringDroughtLabel.setText(homeTeamScoringDrought);
        scoringTabHomeTeamBiggestLeadLabel.setText(homeTeamBiggestLead);
        scoringTabHomeTeamLongestRunLabel.setText(homeTeamLongestRun);
        scoringTabHomeTeamCurrentRunLabel.setText(homeTeamCurrentRun);
        scoringTabHomeTeamSecondChancePointsLabel.setText(homeTeamSecondChancePoints);
        scoringTabHomeTeamBenchPointsLabel.setText(homeTeamBenchPoints);
        scoringTabHomeTeamFastbreakPointsLabel.setText(homeTeamFastbreakPoints);

        /**
         * Updating more stats
         */
        moreStatsTabAwayTeamDefensiveReboundsLabel.setText(awayTeamDefensiveRebounds);
        moreStatsTabAwayTeamOffensiveReboundsLabel.setText(awayTeamOffensiveRebounds);
        moreStatsTabAwayTeamTotalReboundsLabel.setText(awayTeamTotalRebounds);
        moreStatsTabAwayTeamAssistsLabel.setText(awayTeamAssists);
        moreStatsTabAwayTeamBlocksLabel.setText(awayTeamBlocks);
        moreStatsTabAwayTeamStealsLabel.setText(awayTeamSteals);
        moreStatsTabAwayTeamTurnoversLabel.setText(awayTeamTurnovers);
        moreStatsTabAwayTeamFoulsLabel.setText(awayTeamTotalFouls);
        
        moreStatsTabHomeTeamDefensiveReboundsLabel.setText(homeTeamDefensiveRebounds);
        moreStatsTabHomeTeamOffensiveReboundsLabel.setText(homeTeamOffensiveRebounds);
        moreStatsTabHomeTeamTotalReboundsLabel.setText(homeTeamTotalRebounds);
        moreStatsTabHomeTeamAssistsLabel.setText(homeTeamAssists);
        moreStatsTabHomeTeamBlocksLabel.setText(homeTeamBlocks);
        moreStatsTabHomeTeamStealsLabel.setText(homeTeamSteals);
        moreStatsTabHomeTeamTurnoversLabel.setText(homeTeamTurnovers);
        moreStatsTabHomeTeamFoulsLabel.setText(homeTeamTotalFouls);
        
        /**
         * Updating best performers stats
         */
        topPerformersTabAwayTeamTopPerformerLabel.setText(awayTeamBestPerformer.getCompleteName());
        topPerformersTabAwayTeamTopPerformerPerformanceIndexLabel.setText(String.format("%01.2f", 
                awayTeamBestPerformer.getPerformanceIndex()));
        topPerformersTabAwayTeamTopPerformerStatsLabel.setText(String.format("%01d PTS, %01d-%01d FG, %01d-%01d FT, "
                + "%01d-%01d TP, %01d Reb, %01d Ass", awayTeamBestPerformer.getPoints(), 
                awayTeamBestPerformer.getFieldGoalsMade(), 
                awayTeamBestPerformer.getFieldGoalsAttempted(),
                awayTeamBestPerformer.getFreeThrowsMade(),
                awayTeamBestPerformer.getFreeThrowsAttempted(),
                awayTeamBestPerformer.getThreePointersMade(),
                awayTeamBestPerformer.getThreePointersAttempted(),
                (awayTeamBestPerformer.getDefensiveRebounds()+ awayTeamBestPerformer.getOffensiveRebounds()),
                awayTeamBestPerformer.getAssists()));
        
        topPerformersTabHomeTeamTopPerformerLabel.setText(homeTeamBestPerformer.getCompleteName());
        topPerformersTabHomeTeamTopPerformerPerformanceIndexLabel.setText(String.format("%01.2f", 
                homeTeamBestPerformer.getPerformanceIndex()));
        topPerformersTabHomeTeamTopPerformerStatsLabel.setText(String.format("%01d PTS, %01d-%01d FG, %01d-%01d FT, "
                + "%01d-%01d TP, %01d Reb, %01d Ass", homeTeamBestPerformer.getPoints(), 
                homeTeamBestPerformer.getFieldGoalsMade(), 
                homeTeamBestPerformer.getFieldGoalsAttempted(),
                homeTeamBestPerformer.getFreeThrowsMade(),
                homeTeamBestPerformer.getFreeThrowsAttempted(),
                homeTeamBestPerformer.getThreePointersMade(),
                homeTeamBestPerformer.getThreePointersAttempted(),
                (homeTeamBestPerformer.getDefensiveRebounds()+ homeTeamBestPerformer.getOffensiveRebounds()),
                homeTeamBestPerformer.getAssists()));        
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

    /**
     * Binds rosters to the boxscore table views
     */
    private void bindBoxScoreTableViews() {

        /**
         * Away team boxscore
         */
        awayTeamPlayerJerseyTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(p.getValue().getBaseAttributes().getJersey()
                                    + "");
                        } else {
                            return new SimpleStringProperty("000");
                        }
                    }
                });

        awayTeamPlayerCompleteNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringPlayer"));

        awayTeamPlayerMinutesTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringPlayingTime"));

        awayTeamPlayerPointsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringPoints"));

        awayTeamPlayerFieldGoalsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringFieldGoals"));

        awayTeamPlayerFreeThrowsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringFreeThrows"));

        awayTeamPlayerThreePointersTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringThreePointers"));

        awayTeamPlayerDefensiveReboundsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringDefensiveRebounds"));

        awayTeamPlayerOffensiveReboundsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringOffensiveRebounds"));

        awayTeamPlayerTotalReboundsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringTotalRebounds"));

        awayTeamPlayerAssistsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringAssists"));

        awayTeamPlayerStealsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringSteals"));

        awayTeamPlayerBlocksTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringBlocks"));

        awayTeamPlayerTurnoversTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringTurnovers"));

        awayTeamPlayerPersonalFoulsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringPersonalFouls"));

        awayTeamPlayerStaminaTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringStamina"));

        awayTeamPlayerPerformanceIndexTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringPerformanceIndex"));

        awayTeamPlayerOffensiveIndexTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringOffensiveIndex"));

        awayTeamPlayerDefensiveIndexTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringDefensiveIndex"));

        awayTeamPlayerEfficiencyIndexTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringEfficiencyIndex"));

        awayTeamPlayerOffensiveMomentumTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringOffensiveMomentum"));

        awayTeamPlayerDefensiveMomentumTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringDefensiveMomentum"));

        /**
         * Home team box score
         */
        homeTeamPlayerJerseyTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(p.getValue().getBaseAttributes().getJersey()
                                    + "");
                        } else {
                            return new SimpleStringProperty("000");
                        }
                    }
                });

        homeTeamPlayerCompleteNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringPlayer"));

        homeTeamPlayerMinutesTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringPlayingTime"));

        homeTeamPlayerPointsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringPoints"));

        homeTeamPlayerFieldGoalsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringFieldGoals"));

        homeTeamPlayerFreeThrowsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringFreeThrows"));

        homeTeamPlayerThreePointersTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringThreePointers"));

        homeTeamPlayerDefensiveReboundsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringDefensiveRebounds"));

        homeTeamPlayerOffensiveReboundsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringOffensiveRebounds"));

        homeTeamPlayerTotalReboundsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringTotalRebounds"));

        homeTeamPlayerAssistsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringAssists"));

        homeTeamPlayerStealsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringSteals"));

        homeTeamPlayerBlocksTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringBlocks"));

        homeTeamPlayerTurnoversTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringTurnovers"));

        homeTeamPlayerPersonalFoulsTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringPersonalFouls"));

        homeTeamPlayerStaminaTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringStamina"));

        homeTeamPlayerPerformanceIndexTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringPerformanceIndex"));

        homeTeamPlayerOffensiveIndexTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringOffensiveIndex"));

        homeTeamPlayerDefensiveIndexTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringDefensiveIndex"));

        homeTeamPlayerEfficiencyIndexTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringEfficiencyIndex"));

        homeTeamPlayerOffensiveMomentumTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringOffensiveMomentum"));

        homeTeamPlayerDefensiveMomentumTableColumn.setCellValueFactory(
                new PropertyValueFactory<InGamePlayer, String>("stringDefensiveMomentum"));
    }

    /**
     * Binds plays to the play-by-play table view
     */
    private void bindPlayByPlayTableView() {

        playByPlayPeriodTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Play, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Play, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(String.format("%d", p.getValue().getPeriod()));
                        } else {
                            return new SimpleStringProperty("00");
                        }
                    }
                });

        playByPlayTimeTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Play, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Play, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(p.getValue().getTime());
                        } else {
                            return new SimpleStringProperty("00:00");
                        }
                    }
                });

        playByPlayAwayScoreTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Play, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Play, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(String.format("%d", p.getValue().getAwayScore()));
                        } else {
                            return new SimpleStringProperty("00");
                        }
                    }
                });

        playByPlayHomeScoreTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Play, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Play, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(String.format("%d", p.getValue().getHomeScore()));
                        } else {
                            return new SimpleStringProperty("00");
                        }
                    }
                });

        playByPlayTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Play, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Play, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(p.getValue().getPlayDescription());
                        } else {
                            return new SimpleStringProperty("------");
                        }
                    }
                });

        //playByPlayTableView.setItems(game.getPlays());
    }

    /**
     * Binds scoring plays to the scoring log table view
     */
    private void bindScoringLogTableView() {

        scoringLogPeriodTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Play, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Play, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(String.format("%d", p.getValue().getPeriod()));
                        } else {
                            return new SimpleStringProperty("00");
                        }
                    }
                });

        scoringLogTimeTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Play, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Play, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(p.getValue().getTime());
                        } else {
                            return new SimpleStringProperty("00:00");
                        }
                    }
                });

        scoringLogAwayScoreTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Play, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Play, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(String.format("%d", p.getValue().getAwayScore()));
                        } else {
                            return new SimpleStringProperty("00");
                        }
                    }
                });

        scoringLogHomeScoreTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Play, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Play, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(String.format("%d", p.getValue().getHomeScore()));
                        } else {
                            return new SimpleStringProperty("00");
                        }
                    }
                });

        scoringLogTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Play, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Play, String> p) {
                        if (p.getValue() != null) {
                            return new SimpleStringProperty(p.getValue().getPlayDescription());
                        } else {
                            return new SimpleStringProperty("------");
                        }
                    }
                });

        //scoringLogTableView.setItems(this.game.getScoringPlays());
    }

    @FXML
    private void startGame() {

        /**
         * Getting teams
         */
        //awayTeamBoxScoreList.setAll(game.getTeams().get(1).getPlayers());
        //homeTeamBoxScoreList.setAll(game.getTeams().get(2).getPlayers());
        /**
         * Setting up table views
         */
        bindBoxScoreTableViews();
        bindPlayByPlayTableView();
        bindScoringLogTableView();

        /**
         * Updating controls states and invoking playGameTask
         */
        playGameButton.setDisable(true);
        pauseGameButton.setDisable(false);
        goBackButton.setDisable(true);
        invokePlayGameTask();
    }

    @FXML
    private void saveGame() {
        savingInformationLabel.setVisible(true);
        savingInformationLabel.setText(resources.getString("ch_salvando_jogo"));
        saveGameButton.setDisable(true);
        pauseGameButton.setDisable(true);
        mainContent.setCursor(Cursor.WAIT);
        invokeSaveGameTask();
    }

    /**
     * Creates a new playGameTask that simulates the game
     */
    private void invokePlayGameTask() {
        gameTabPane.getSelectionModel().select(playByPlayTab);
        game.setInProgress(true);
        game.setCurrentEvent("start of period");
        game.setFastMode(fastModeCheckBox.isSelected());
        playGameTask = null;
        playGameTask = playGameTask();
        new Thread(playGameTask).start();
    }

    /**
     * Creates a new playGameTask that saves the game
     */
    private void invokeSaveGameTask() {
        saveGameTask = null;
        saveGameTask = saveGameTask();
        new Thread(saveGameTask).start();
    }

    /**
     * Task that effectively simulates the game
     *
     * @return
     */
    private Task<Void> playGameTask() {

        Task<Void> playingGameTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                System.out.println("Game in progress: " + game.isInProgress());  // delete

                while (game.isInProgress()) {

                    game.processCurrentEvent();
                    System.out.println("Task: " + game.getCurrentEvent()); // delete

                    /**
                     * To avoid exception caused by the updating of the observable lists, they are refreshed later
                     */
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            updateGameStats();
                            playByPlayTableView.getItems().setAll(game.getPlays());
                            scoringLogTableView.getItems().setAll(game.getScoringPlays());
                            awayTeamBoxScoreTableView.getItems().setAll(game.getTeams().get(1).getPlayers());
                            homeTeamBoxScoreTableView.getItems().setAll(game.getTeams().get(2).getPlayers());
                        }
                    });
                }

                return null;
            }

            /**
             * Method to be executed when the playGameTask completes successfully
             */
            @Override
            protected void succeeded() {
                saveGameButton.setVisible(true);
            }
        };

        return playingGameTask;
    }

    /**
     * Task that saves game date
     *
     * @return
     */
    private Task<Void> saveGameTask() {

        Task<Void> savingGameTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("Starting save game task"); // delete
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                System.out.println("Início: " + dateFormat.format(cal.getTime()));
                game.savePlayLogs();
                Calendar cal2 = Calendar.getInstance();
                System.out.println("Fim: " + dateFormat.format(cal2.getTime()));
                game.savePlayerData();
                game.saveTeamData();
                game.save();
                return null;
            }

            @Override
            protected void succeeded() {
                savingInformationLabel.setText("Partida salva com sucesso");
                pauseGameButton.setDisable(true);
                saveGameButton.setVisible(false);
                goBackButton.setDisable(false);
                mainContent.setCursor(Cursor.DEFAULT);
            }
        };

        return savingGameTask;
    }

    @FXML
    private void pauseGame() {
        game.setPaused(!game.isPaused());
    }

    @FXML
    private void toggleFastMode() {
        this.game.setFastMode(fastModeCheckBox.isSelected());
    }
}
