package coachj;

import coachj.dao.DatabaseDirectConnection;
import coachj.enums.SeasonStatus;
import coachj.lists.IdDescriptionListItem;
import coachj.models.Player;
import coachj.structures.PlayerAverages;
import coachj.structures.PlayerGameStats;
import coachj.structures.ScheduleGame;
import coachj.structures.StatItem;
import coachj.utils.FranchiseUtils;
import coachj.utils.ListUtils;
import coachj.utils.PlayerUtils;
import coachj.utils.RosterUtils;
import coachj.utils.SceneUtils;
import coachj.utils.ScheduleUtils;
import coachj.utils.SettingsUtils;
import coachj.utils.StatsUtils;
import coachj.utils.TimeUtils;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Eduardo
 */
public class TeamCentralController implements Initializable {

    /**
     * FXML components
     */
    @FXML
    private AnchorPane mainContent; /* main content holder */

    @FXML
    private Label completeFranchiseNameLabel;
    @FXML
    private TableView<Player> playersTableView;
    @FXML
    private TableColumn playerJerseyTableColumn;
    @FXML
    private TableColumn playerCompleteNameTableColumn;
    @FXML
    private TableColumn playerAgeTableColumn;
    @FXML
    private TableColumn playerHeightTableColumn;
    @FXML
    private TableColumn playerWeightTableColumn;
    @FXML
    private TableColumn playerSalaryTableColumn;
    @FXML
    private TableColumn playerMarketValueTableColumn;
    @FXML
    private TableView<PlayerAverages> seasonStatsTableView;
    @FXML
    private TableColumn seasonStatsJerseyTableColumn;
    @FXML
    private TableColumn seasonStatsCompleteNameTableColumn;
    @FXML
    private TableColumn seasonStatsMinutesPerGameTableColumn;
    @FXML
    private TableColumn seasonStatsPointsPerGameTableColumn;
    @FXML
    private TableColumn seasonStatsFieldGoalPercentageTableColumn;
    @FXML
    private TableColumn seasonStatsFreeThrowPercentageTableColumn;
    @FXML
    private TableColumn seasonStatsThreePointerPercentageTableColumn;
    @FXML
    private TableColumn seasonStatsReboundsPerGameTableColumn;
    @FXML
    private TableColumn seasonStatsAssistsPerGameTableColumn;
    @FXML
    private TableView<ScheduleGame> scheduleTableView;
    @FXML
    private TableColumn scheduleDateTableColumn;
    @FXML
    private TableColumn scheduleOpponentTableColumn;
    @FXML
    private TableColumn scheduleResultTableColumn;
    @FXML
    private TableColumn scheduleTopScorerTableColumn;
    @FXML
    private TableColumn scheduleTopRebounderTableColumn;
    @FXML
    private TableColumn scheduleTopAssistantTableColumn;
    @FXML
    private ComboBox playerGameByGameStatsComboBox;
    @FXML
    private TableView<PlayerGameStats> playerGameByGameStatsTableView;
    @FXML
    private TableColumn gameDateTableColumn;
    @FXML
    private TableColumn gameOpponentTableColumn;
    @FXML
    private TableColumn gameResultTableColumn;
    @FXML
    private TableColumn gameMinutesTableColumn;
    @FXML
    private TableColumn gamePointsTableColumn;
    @FXML
    private TableColumn gameFieldGoalsTableColumn;
    @FXML
    private TableColumn gameFreeThrowsTableColumn;
    @FXML
    private TableColumn gameThreePointersTableColumn;
    @FXML
    private TableColumn gameOffensiveReboundsTableColumn;
    @FXML
    private TableColumn gameDefensiveReboundsTableColumn;
    @FXML
    private TableColumn gameTotalReboundsTableColumn;
    @FXML
    private TableColumn gameAssistsTableColumn;
    @FXML
    private TableColumn gameStealsTableColumn;
    @FXML
    private TableColumn gameBlocksTableColumn;
    @FXML
    private TableColumn gameTurnoversTableColumn;
    @FXML
    private TableColumn gamePersonalFoulsTableColumn;
    @FXML
    private ComboBox playerSeasonPerformancePlayersComboBox;
    @FXML
    private ComboBox playerSeasonPerformanceStatsComboBox;
    @FXML
    private CategoryAxis gameAxis = new CategoryAxis();
    @FXML
    private NumberAxis statAxis = new NumberAxis();
    @FXML
    private LineChart<String, Number> playerSeasonPerformanceStatsLineChart =
            new LineChart<>(gameAxis, statAxis);
    @FXML
    private CategoryAxis comparisonCategoryAxis = new CategoryAxis();
    @FXML
    private NumberAxis comparisonStatAxis = new NumberAxis();
    @FXML
    private BarChart<String, Number> playerComparisonBarChart =
            new BarChart<>(comparisonCategoryAxis, comparisonStatAxis);
    @FXML
    private ComboBox playerComparisonComboBox;
    @FXML
    private ComboBox statComparisonComboBox;
    @FXML
    private ComboBox playerShootingChartComboBox;
    @FXML
    private ComboBox gameShootingChartComboBox;
    @FXML
    private Label shootingChartZone1Label;
    @FXML
    private Label shootingChartZone2Label;
    @FXML
    private Label shootingChartZone3Label;
    @FXML
    private Label shootingChartZone4Label;
    @FXML
    private Label shootingChartZone5Label;
    @FXML
    private Label shootingChartZone6Label;
    @FXML
    private Label shootingChartZone7Label;
    @FXML
    private Label shootingChartZone8Label;
    @FXML
    private Label shootingChartZone9Label;
    @FXML
    private Label shootingChartZone10Label;
    @FXML
    private Label shootingChartZone11Label;
    @FXML
    private Label shootingChartZone12Label;
    @FXML
    private Label shootingChartZone13Label;
    @FXML
    private Label shootingChartZone14Label;
    @FXML
    private Label shootingChartZone15Label;
    @FXML
    private ComboBox franchiseSelectComboBox;
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
     * Observable lists
     */
    private ObservableList<Player> rosterList = FXCollections
            .observableArrayList();
    private ObservableList<PlayerAverages> seasonStatsList = FXCollections
            .observableArrayList();
    private ObservableList<ScheduleGame> scheduleList = FXCollections
            .observableArrayList();
    private ObservableList<StatItem> statsList = FXCollections
            .observableArrayList();
    private ObservableList<IdDescriptionListItem> playersObservableList = FXCollections
            .observableArrayList();
    private ObservableList<IdDescriptionListItem> franchisesObservableList = FXCollections
            .observableArrayList();
    private ObservableList<ScheduleGame> gamesObservableList = FXCollections
            .observableArrayList();
    private ObservableList<PlayerGameStats> playerGameByGameStatsList = FXCollections
            .observableArrayList();
    /**
     * Auxiliary fields
     */
    private short franchiseId = Short.parseShort(SettingsUtils.getSetting("userFranchise",
            "1"));
    private final short season = Short.parseShort(SettingsUtils.getSetting("currentSeason",
            String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));

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
         * Allowing tableViews to resize and fit properly
         */
        playersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        /**
         * Filling up observables lists that are commom to all franchises
         */
         statsList.setAll(StatsUtils.getStatsDescriptorsList(resources));
         franchisesObservableList = ListUtils.fillIdDescriptionListFromSQL("SELECT f.id, CONCAT(c.name, "
                + "CONCAT(' ', f.team)) AS description "
                + "FROM franchise f "
                + "INNER JOIN city c ON f.city = c.id "
                + "ORDER BY c.name", connection);
         franchiseSelectComboBox.setItems(franchisesObservableList);
        /**
         * Updating controls
         */
        updateControls();
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
     * Binds roster to the table view
     */
    private void bindRosterTableView() {

        playerJerseyTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getJersey()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        playerCompleteNameTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getPosition()
                            + " " + p.getValue().getFirstName() + " "
                            + p.getValue().getLastName());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        playerAgeTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getAge()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        playerHeightTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%1.2f m",
                            (double) p.getValue().getHeight() / 100));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        playerWeightTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%d kg",
                            p.getValue().getWeight()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        playerSalaryTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(DecimalFormat.getCurrencyInstance()
                            .format(p.getValue().getSalary()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        playerMarketValueTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getMarketValue()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        playersTableView.setItems(rosterList);
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
    }

    /**
     * Binds player stats to the table view
     */
    private void bindRosterStatsTableView() {

        seasonStatsJerseyTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerAverages, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerAverages, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getJersey()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        seasonStatsCompleteNameTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerAverages, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerAverages, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getCompleteName());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        seasonStatsMinutesPerGameTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerAverages, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerAverages, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(TimeUtils.intToTime(p.getValue().getMinutesPerGame()));
                } else {
                    return new SimpleStringProperty("00:00");
                }
            }
        });

        seasonStatsPointsPerGameTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerAverages, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerAverages, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%2.2f",
                            p.getValue().getPointsPerGame()));
                } else {
                    return new SimpleStringProperty("0.00");
                }
            }
        });

        seasonStatsFieldGoalPercentageTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerAverages, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerAverages, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%.3f",
                            p.getValue().getFieldGoalPercentage()));
                } else {
                    return new SimpleStringProperty(".000");
                }
            }
        });

        seasonStatsFreeThrowPercentageTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerAverages, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerAverages, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%.3f",
                            p.getValue().getFreeThrowPercentage()));
                } else {
                    return new SimpleStringProperty(".000");
                }
            }
        });

        seasonStatsThreePointerPercentageTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerAverages, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerAverages, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%.3f",
                            p.getValue().getThreePointerPercentage()));
                } else {
                    return new SimpleStringProperty(".000");
                }
            }
        });

        seasonStatsReboundsPerGameTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerAverages, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerAverages, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%2.2f",
                            p.getValue().getReboundsPerGame()));
                } else {
                    return new SimpleStringProperty("0.00");
                }
            }
        });

        seasonStatsAssistsPerGameTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerAverages, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerAverages, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.format("%2.2f",
                            p.getValue().getAssistsPerGame()));
                } else {
                    return new SimpleStringProperty("0.00");
                }
            }
        });

        seasonStatsTableView.setItems(seasonStatsList);
    }

    /**
     * Binds schedule list to the table view
     */
    private void bindScheduleTableView() {

        scheduleDateTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ScheduleGame, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScheduleGame, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getDate());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        scheduleOpponentTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ScheduleGame, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScheduleGame, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getOpponent());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        scheduleResultTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ScheduleGame, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScheduleGame, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getResult());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        scheduleTopScorerTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ScheduleGame, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScheduleGame, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getTopScorer());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        scheduleTopRebounderTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ScheduleGame, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScheduleGame, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getTopRebounder());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        scheduleTopAssistantTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ScheduleGame, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ScheduleGame, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getTopAssistant());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        scheduleTableView.setItems(scheduleList);
    }

    /**
     * Generate the season performance linechart
     */
    @FXML
    private void generateSeasonPerformanceChart() {
        /**
         * Since the chart supports only 8 different series, we count them up to
         * prevent the user from adding more than that
         */
        if (playerSeasonPerformanceStatsLineChart.getData().size() == 8) {
            SceneUtils.warning(resources.getString("ch_maximo_series"),
                    resources.getString("ch_atencao"));
            return;
        }

        /**
         * Creating necessary objects and retrieving selected items
         */
        IdDescriptionListItem selectedPlayer = (IdDescriptionListItem) playerSeasonPerformancePlayersComboBox.getSelectionModel().getSelectedItem();
        StatItem selectedStat = (StatItem) playerSeasonPerformanceStatsComboBox.getSelectionModel()
                .getSelectedItem();
        int playerId = selectedPlayer.getId();
        String playerCompleteName = selectedPlayer.getDescription();
        String statDescription = selectedStat.getDescription();
        String statSqlEquation = selectedStat.getSqlEquation();

        /**
         * Setting chart series
         */
        XYChart.Series series = StatsUtils.getPlayerGameByGameStatSeries(playerId,
                season, statSqlEquation, connection);
        series.setName(playerCompleteName + " - " + statDescription);
        //series.getData().setAll(statsList);

        /**
         * Adding series to the chart
         */
        playerSeasonPerformanceStatsLineChart.getData().add(series);
    }

    /**
     * Resets the lineChart
     */
    @FXML
    private void resetSeasonPerformanceChart() {
        playerSeasonPerformanceStatsLineChart.getData().clear();
    }

    /**
     * Binds the player game by game stats to the table view
     */
    private void bindPlayerGameByGameStatsTableView() {

        gameDateTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getDate());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameMinutesTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getMinutes());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gamePointsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getPoints());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameFieldGoalsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getFieldGoals());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameFreeThrowsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getFreeThrows());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameThreePointersTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getThreePointers());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameOffensiveReboundsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getOffensiveRebounds());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameDefensiveReboundsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getDefensiveRebounds());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameTotalReboundsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getTotalRebounds());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameAssistsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getAssists());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameStealsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getSteals());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameBlocksTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getBlocks());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gamePersonalFoulsTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getPersonalFouls());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameTurnoversTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getTurnovers());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameResultTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getResult());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        gameOpponentTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<PlayerGameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PlayerGameStats, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(p.getValue().getOpponent());
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        playerGameByGameStatsTableView.setItems(playerGameByGameStatsList);
    }

    /**
     * Fills the game by game stats table view with data from the selected
     * player
     */
    @FXML
    private void fillPlayerGameByGameStatsTableView() {
        /*
         * Retrieving the selected player
         */
        IdDescriptionListItem selectedPlayer = (IdDescriptionListItem) playerGameByGameStatsComboBox.getSelectionModel().getSelectedItem();
        int playerId = selectedPlayer.getId();

        /**
         * Cleaning the observablelist and adding new items
         */
        playerGameByGameStatsList.clear();
        playerGameByGameStatsList.setAll(StatsUtils.getPlayerGameByGameStats(playerId,
                season, connection));
        playerGameByGameStatsTableView.setItems(playerGameByGameStatsList);
    }

    /**
     * Generates the stats comparison chart
     */
    @FXML
    private void generateComparisonChart() {
        /**
         * Creating necessary objects and retrieving selected items
         */
        IdDescriptionListItem selectedPlayer = (IdDescriptionListItem) playerComparisonComboBox.getSelectionModel().getSelectedItem();
        StatItem selectedStat = (StatItem) statComparisonComboBox.getSelectionModel()
                .getSelectedItem();
        int playerId = selectedPlayer.getId();
        String playerCompleteName = selectedPlayer.getDescription();
        String playerPosition = PlayerUtils.getPlayerPosition(playerId, connection);
        String statDescription = selectedStat.getDescription();
        String statSqlEquation = selectedStat.getSqlEquation();

        /**
         * Setting chart series and calculating values to be added to it
         */
        playerComparisonBarChart.getData().clear();
        comparisonCategoryAxis.setLabel(statDescription);

        double playerAverage = StatsUtils.getPlayerAverage(playerId,
                season, statSqlEquation, connection);
        double teamLeaderAverage = StatsUtils.getTeamLeaderAverage(franchiseId,
                season, statSqlEquation, connection);
        double leagueLeaderAverage = StatsUtils.getLeagueLeaderAverage(season,
                statSqlEquation, connection);
        double leagueAverage = StatsUtils.getLeagueAverage(season,
                statSqlEquation, connection);
        double positionAverage = StatsUtils.getPositionAverage(playerPosition,
                season, statSqlEquation, connection);

        XYChart.Series playerAverageSeries = new XYChart.Series();
        XYChart.Series teamLeaderAverageSeries = new XYChart.Series();
        XYChart.Series leagueLeaderAverageSeries = new XYChart.Series();
        XYChart.Series leagueAverageSeries = new XYChart.Series();
        XYChart.Series positionAverageSeries = new XYChart.Series();

        playerAverageSeries.setName(playerCompleteName);
        playerAverageSeries.getData().add(new XYChart.Data("", playerAverage));

        teamLeaderAverageSeries.setName(resources.getString("ch_lider_equipe"));
        teamLeaderAverageSeries.getData().add(new XYChart.Data("", teamLeaderAverage));

        leagueLeaderAverageSeries.setName(resources.getString("ch_lider_liga"));
        leagueLeaderAverageSeries.getData().add(new XYChart.Data("",
                leagueLeaderAverage));

        leagueAverageSeries.setName(resources.getString("ch_media_liga"));
        leagueAverageSeries.getData().add(new XYChart.Data("",
                leagueAverage));

        positionAverageSeries.setName(resources.getString("ch_media_posicao"));
        positionAverageSeries.getData().add(new XYChart.Data("",
                positionAverage));
        /**
         * Adding series to the chart
         */
        playerComparisonBarChart.getData().add(playerAverageSeries);
        playerComparisonBarChart.getData().add(teamLeaderAverageSeries);
        playerComparisonBarChart.getData().add(leagueLeaderAverageSeries);
        playerComparisonBarChart.getData().add(leagueAverageSeries);
        playerComparisonBarChart.getData().add(positionAverageSeries);
    }

    /**
     * Generates the shooting map for a player
     */
    @FXML
    private void generateShootingMap() {
        /**
         * Creating necessary objects and retrieving selected items
         */
        IdDescriptionListItem selectedPlayer = (IdDescriptionListItem) playerShootingChartComboBox
                .getSelectionModel().getSelectedItem();
        int playerId = selectedPlayer.getId();
        String gameId;
        String[] shootingData = new String[15];

        /**
         * Checking if there's a selected game in the combobox, if none, it
         * means the stats for the whole season will be displayed
         */
        if (gameShootingChartComboBox.getSelectionModel().getSelectedIndex() == -1) {
            gameId = "%";
        } else {
            ScheduleGame selectedGame = (ScheduleGame) gameShootingChartComboBox.getSelectionModel()
                    .getSelectedItem();
            gameId = String.valueOf(selectedGame.getId());
        }

        for (int i = 1; i < 16; i++) {
            shootingData[i - 1] = StatsUtils.getPlayerGameZoneShootingData(
                    playerId, season, gameId, i, connection);
        }

        shootingChartZone1Label.setText(shootingData[0]);
        shootingChartZone2Label.setText(shootingData[1]);
        shootingChartZone3Label.setText(shootingData[2]);
        shootingChartZone4Label.setText(shootingData[3]);
        shootingChartZone5Label.setText(shootingData[4]);
        shootingChartZone6Label.setText(shootingData[5]);
        shootingChartZone7Label.setText(shootingData[6]);
        shootingChartZone8Label.setText(shootingData[7]);
        shootingChartZone9Label.setText(shootingData[8]);
        shootingChartZone10Label.setText(shootingData[9]);
        shootingChartZone11Label.setText(shootingData[10]);
        shootingChartZone12Label.setText(shootingData[11]);
        shootingChartZone13Label.setText(shootingData[12]);
        shootingChartZone14Label.setText(shootingData[13]);
        shootingChartZone15Label.setText(shootingData[14]);
    }

    /**
     * Updates controls accordingly to the selected franchise
     */
    private void updateControls() {
        /**
         * Retrieving auxiliary values
         */
        String completeFranchiseName = FranchiseUtils.getFranchiseCompleteName(franchiseId,
                connection);
        completeFranchiseNameLabel.setText(completeFranchiseName);

        /**
         * Filling up observable list and binding them to the tableviews and
         * comboboxes
         */
        rosterList.setAll(RosterUtils.getFranchiseRoster(franchiseId, connection));
        seasonStatsList.setAll(RosterUtils.getFranchiseRosterStats(franchiseId,
                season, connection));
        scheduleList.setAll(ScheduleUtils.getFranchiseSchedule(franchiseId,
                season, connection));       
        playersObservableList.setAll(ListUtils.fillIdDescriptionListFromSQL(
                "SELECT id, CONCAT(position, CONCAT(' ' ,CONCAT(firstName, "
                + "CONCAT(' ', lastName)))) AS description "
                + "FROM player "
                + "WHERE active = true AND franchise = " + franchiseId
                + " ORDER BY rosterPosition", connection));
        gamesObservableList.setAll(ScheduleUtils.getFranchisePlayedGame(franchiseId,
                season, connection));

        bindRosterTableView();
        bindRosterStatsTableView();
        bindScheduleTableView();
        bindPlayerGameByGameStatsTableView();
        playerSeasonPerformanceStatsComboBox.setItems(statsList);
        statComparisonComboBox.setItems(statsList);
        playerSeasonPerformancePlayersComboBox.setItems(playersObservableList);
        playerGameByGameStatsComboBox.setItems(playersObservableList);
        playerComparisonComboBox.setItems(playersObservableList);
        playerShootingChartComboBox.setItems(playersObservableList);
        gameShootingChartComboBox.setItems(gamesObservableList);

        /**
         * Setting default choices in the comboboxes
         */
        playerSeasonPerformancePlayersComboBox.getSelectionModel().selectFirst();
        playerSeasonPerformanceStatsComboBox.getSelectionModel().selectFirst();
        playerGameByGameStatsComboBox.getSelectionModel().selectFirst();
        playerComparisonComboBox.getSelectionModel().selectFirst();
        statComparisonComboBox.getSelectionModel().selectFirst();
        playerShootingChartComboBox.getSelectionModel().selectFirst();

    }
    
    /**
     * Changes the franchise displayed in the central
     */
    @FXML
    private void changeFranchise() {
        /**
         * Checking if the user has picked a franchise
         */
        if (franchiseSelectComboBox.getSelectionModel().getSelectedIndex() == -1) {
            SceneUtils.warning(resources.getString("ch_selecione_franquia"), 
                    resources.getString("ch_atencao"));
            return;
        }
        
        /**
         * Creating necessary objects and retrieving selected items
         */
        IdDescriptionListItem selectedFranchise = (IdDescriptionListItem) franchiseSelectComboBox
                .getSelectionModel().getSelectedItem();        
        franchiseId = (short) selectedFranchise.getId();
        updateControls();
    }
}