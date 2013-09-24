/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj;

import coachj.dao.DatabaseDirectConnection;
import coachj.enums.SeasonStatus;
import coachj.ingame.InGamePlayer;
import coachj.models.Player;
import coachj.structures.ScheduledGame;
import coachj.utils.FranchiseUtils;
import coachj.utils.RosterUtils;
import coachj.utils.SceneUtils;
import coachj.utils.SettingsUtils;
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
         * Retrieving auxiliary values
         */
        String completeFranchiseName = FranchiseUtils.getFranchiseCompleteName(userFranchiseId,
                connection);
        completeFranchiseNameLabel.setText(completeFranchiseName);

        /**
         * Filling up observable list and binding it to the tableview
         */
        rosterList.setAll(RosterUtils.getFranchiseRoster(userFranchiseId, connection));
        bindRosterTableView();

        /**
         * Allowing tableViews to resize and fit properly
         */
        playersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
}