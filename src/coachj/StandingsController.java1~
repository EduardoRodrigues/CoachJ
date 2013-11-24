/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj;

import coachj.dao.DatabaseDirectConnection;
import coachj.enums.SeasonStatus;
import coachj.structures.StandingsTableEntry;
import coachj.utils.SceneUtils;
import coachj.utils.SettingsUtils;
import coachj.utils.StandingsUtils;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Eduardo
 */
public class StandingsController implements Initializable {

    /**
     * FXML components
     */
    @FXML
    private AnchorPane mainContent; /* main content holder */

    @FXML
    private TableView<StandingsTableEntry> standingsTableView;
    @FXML
    private TableColumn standingsSeedTableColumn;
    @FXML
    private TableColumn standingsFranchiseTableColumn;
    @FXML
    private TableColumn standingsGamesTableColumn;
    @FXML
    private TableColumn standingsRecordTableColumn;
    @FXML
    private TableColumn standingsPercentageTableColumn;
    @FXML
    private TableColumn standingsHomeTableColumn;
    @FXML
    private TableColumn standingsRoadTableColumn;
    @FXML
    private TableColumn standingsGamesBehindTableColumn;
    @FXML
    private TableColumn standingsStreakTableColumn;
    @FXML
    private TableColumn standingsLast10TableColumn;
    /**
     * Observable lists to store information about standings entries
     */
    private ObservableList<StandingsTableEntry> standingEntriesList = FXCollections
            .observableArrayList();
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
    private short userFranchiseId = Short.parseShort(SettingsUtils.getSetting("userFranchise",
            "1"));
    private short season = Short.parseShort(SettingsUtils.getSetting("currentSeason",
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
         * Filling observable list and binding it to the table view
         */
        standingEntriesList.setAll(StandingsUtils.getStandingsTableData(connection));
        bindStandingsTableView();
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
    }

    /**
     * Binds standings entries to the table view
     */
    private void bindStandingsTableView() {

        standingsSeedTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<StandingsTableEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StandingsTableEntry, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getSeed()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        standingsFranchiseTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<StandingsTableEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StandingsTableEntry, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getTeam()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        standingsRecordTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<StandingsTableEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StandingsTableEntry, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getRecord()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });
        
        standingsGamesTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<StandingsTableEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StandingsTableEntry, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getGames()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        standingsPercentageTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<StandingsTableEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StandingsTableEntry, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getPercentage()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        standingsGamesBehindTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<StandingsTableEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StandingsTableEntry, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getGamesBehind()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        standingsHomeTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<StandingsTableEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StandingsTableEntry, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getHome()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        standingsRoadTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<StandingsTableEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StandingsTableEntry, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getRoad()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        standingsStreakTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<StandingsTableEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StandingsTableEntry, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getStreak()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });

        standingsLast10TableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<StandingsTableEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StandingsTableEntry, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getLast10()));
                } else {
                    return new SimpleStringProperty("---");
                }
            }
        });
        
        standingsTableView.setItems(standingEntriesList);
    }
}