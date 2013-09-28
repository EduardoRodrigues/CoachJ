package coachj;

import coachj.dao.DatabaseDirectConnection;
import coachj.enums.SeasonStatus;
import coachj.ingame.PlayGame;
import coachj.ingame.InGamePlayer;
import coachj.structures.Play;
import coachj.utils.ArenaUtils;
import coachj.utils.SceneUtils;
import coachj.utils.ScheduleUtils;
import coachj.utils.SettingsUtils;
import coachj.utils.StatsUtils;
import coachj.utils.TimeUtils;
import java.net.URL;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
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
    private TableColumn awayTeamPlayerFreeTrowsTableColumn;
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
    private TableColumn awayTeamPlayerCurrentLocationTableColumn;
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
    private TableColumn homeTeamPlayerFreeTrowsTableColumn;
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
    private TableColumn homeTeamPlayerCurrentLocationTableColumn;
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
         * Setting up scoreboard, game info panel and others information display
         * controls
         */
        scoreboardAwayTeamLabel.setText(game.getTeams().get(1).getCompleteName());
        scoreboardHomeTeamLabel.setText(game.getTeams().get(2).getCompleteName());
        awayTeamTab.setText("Boxscore: " + game.getTeams().get(1).getCompleteName());
        homeTeamTab.setText("Boxscore: " + game.getTeams().get(2).getCompleteName());
        awayTeamCompleteNameLabel.setText(game.getTeams().get(1).getCompleteName());
        homeTeamCompleteNameLabel.setText(game.getTeams().get(2).getCompleteName());
        playByPlayAwayScoreTableColumn.setText(game.getTeams().get(1).getAbbreviature());
        playByPlayHomeScoreTableColumn.setText(game.getTeams().get(2).getAbbreviature());
        scoringLogAwayScoreTableColumn.setText(game.getTeams().get(1).getAbbreviature());
        scoringLogHomeScoreTableColumn.setText(game.getTeams().get(2).getAbbreviature());
        updateScoreBoard();
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
     * Updates game's scoreboard
     */
    private void updateScoreBoard() {

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

        scoreboardAwayTeamFoulsLabel.setText(awayTeamFouls);
        scoreboardAwayTeamScoreLabel.setText(awayTeamScore);
        scoreboardAwayTeamTimeoutsLabel.setText(awayTeamTimeoutsLeft);

        scoreboardHomeTeamFoulsLabel.setText(homeTeamFouls);
        scoreboardHomeTeamScoreLabel.setText(homeTeamScore);
        scoreboardHomeTeamTimeoutsLabel.setText(homeTeamTimeoutsLeft);

        scoreboardPeriodLabel.setText(period);
        scoreboardShotClockLabel.setText(shotClock);
        scoreboardTimeLeftLabel.setText(timeLeft);

        lastScoringPlayScoreLabel.setText(lastScoringPlayScore);
        lastScoringPlayLabel.setText(lastScoringPlay);
        lastScorerStatsLineLabel.setText(lastScorerStats);
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
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    String playerName = p.getValue().getBaseAttributes().getPosition()
                            + " " + p.getValue().getBaseAttributes().getFirstName()
                            + " " + p.getValue().getBaseAttributes().getLastName();

                    if (p.getValue().isOnCourt()) {
                        playerName += " (" + resources.getString("ch_em_quadra") + ")";
                    } else {
                        playerName += " ";
                    }
                    return new SimpleStringProperty(playerName);
                } else {
                    return new SimpleStringProperty("------");
                }
            }
        });

        awayTeamPlayerMinutesTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(TimeUtils.intToTime(p.getValue()
                            .getPlayingTime()));
                } else {
                    return new SimpleStringProperty("00:00");
                }
            }
        });        
               
        awayTeamPlayerPointsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getPoints()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        awayTeamPlayerFieldGoalsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(StatsUtils.getBoxScoreShootingPercentage(
                            p.getValue().getFieldGoalsMade(), p.getValue().getFieldGoalsAttempted()));
                } else {
                    return new SimpleStringProperty("00-00 (0.000)");
                }
            }
        });

        awayTeamPlayerFreeTrowsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(StatsUtils.getBoxScoreShootingPercentage(
                            p.getValue().getFreeThrowsMade(), p.getValue().getFreeThrowsAttempted()));
                } else {
                    return new SimpleStringProperty("00-00 (0.000)");
                }
            }
        });

        awayTeamPlayerThreePointersTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(StatsUtils.getBoxScoreShootingPercentage(
                            p.getValue().getThreePointersMade(), p.getValue().getThreePointersAttempted()));
                } else {
                    return new SimpleStringProperty("00-00 (0.000)");
                }
            }
        });

        awayTeamPlayerDefensiveReboundsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getDefensiveRebounds()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        awayTeamPlayerOffensiveReboundsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getOffensiveRebounds()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        awayTeamPlayerTotalReboundsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getDefensiveRebounds() + p.getValue().getOffensiveRebounds()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        awayTeamPlayerAssistsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getAssists()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        awayTeamPlayerStealsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getSteals()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        awayTeamPlayerBlocksTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getBlocks()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        awayTeamPlayerTurnoversTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getTurnovers()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        awayTeamPlayerPersonalFoulsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getPersonalFouls()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        awayTeamPlayerStaminaTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getCurrentStaminaLevel()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        /*awayTeamPlayerCurrentLocationTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getCurrentZoneLocation()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        awayTeamPlayerOffensiveMomentumTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%d",
                            p.getValue().getOffensiveMomentum()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        awayTeamPlayerDefensiveMomentumTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%d",
                            p.getValue().getDefensiveMomentum()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });*/

        //awayTeamBoxScoreTableView.setItems(awayTeamBoxScoreList);

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
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    String playerName = p.getValue().getBaseAttributes().getPosition()
                            + " " + p.getValue().getBaseAttributes().getFirstName()
                            + " " + p.getValue().getBaseAttributes().getLastName();

                    if (p.getValue().isOnCourt()) {
                        playerName += " (" + resources.getString("ch_em_quadra") + ")";
                    } else {
                        playerName += " ";
                    }
                    return new SimpleStringProperty(playerName);
                } else {
                    return new SimpleStringProperty("------");
                }
            }
        });

        homeTeamPlayerMinutesTableColumn.setCellValueFactory(
                new Callback<CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(TimeUtils.intToTime(p.getValue()
                            .getPlayingTime()));
                } else {
                    return new SimpleStringProperty("00:00");
                }
            }
        });

        homeTeamPlayerPointsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getPoints()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        homeTeamPlayerFieldGoalsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(StatsUtils.getBoxScoreShootingPercentage(
                            p.getValue().getFieldGoalsMade(), p.getValue().getFieldGoalsAttempted()));
                } else {
                    return new SimpleStringProperty("00-00 (0.000)");
                }
            }
        });

        homeTeamPlayerFreeTrowsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(StatsUtils.getBoxScoreShootingPercentage(
                            p.getValue().getFreeThrowsMade(), p.getValue().getFreeThrowsAttempted()));
                } else {
                    return new SimpleStringProperty("00-00 (0.000)");
                }
            }
        });

        homeTeamPlayerThreePointersTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(StatsUtils.getBoxScoreShootingPercentage(
                            p.getValue().getThreePointersMade(), p.getValue().getThreePointersAttempted()));
                } else {
                    return new SimpleStringProperty("00-00 (0.000)");
                }
            }
        });

        homeTeamPlayerDefensiveReboundsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getDefensiveRebounds()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        homeTeamPlayerOffensiveReboundsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getOffensiveRebounds()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        homeTeamPlayerTotalReboundsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getDefensiveRebounds() + p.getValue().getOffensiveRebounds()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        homeTeamPlayerAssistsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getAssists()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        homeTeamPlayerStealsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getSteals()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        homeTeamPlayerBlocksTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getBlocks()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        homeTeamPlayerTurnoversTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getTurnovers()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        homeTeamPlayerPersonalFoulsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getPersonalFouls()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        homeTeamPlayerStaminaTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getCurrentStaminaLevel()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        /*homeTeamPlayerCurrentLocationTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%02d",
                            p.getValue().getCurrentZoneLocation()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        homeTeamPlayerOffensiveMomentumTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%d",
                            p.getValue().getOffensiveMomentum()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });

        homeTeamPlayerDefensiveMomentumTableColumn.setCellValueFactory(
                new Callback<CellDataFeatures<InGamePlayer, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<InGamePlayer, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%d",
                            p.getValue().getDefensiveMomentum()));
                } else {
                    return new SimpleStringProperty("00");
                }
            }
        });*/

        //homeTeamBoxScoreTableView.setItems(homeTeamBoxScoreList);
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
                     * To avoid exception caused by the updating of the
                     * observable lists, they are refreshed later
                     */
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            updateScoreBoard();
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
             * Method to be executed when the playGameTask completes
             * successfully
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
                game.savePlayLogs();
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