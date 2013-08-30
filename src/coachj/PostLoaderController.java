package coachj;

import coachj.dao.DatabaseDirectConnection;
import coachj.enums.SeasonStatus;
import coachj.utils.SettingsUtils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import coachj.utils.SceneUtils;
import coachj.utils.SeasonUtils;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller for the postloader scene
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 07/07/2013
 */
public class PostLoaderController implements Initializable {

    /**
     * FXML components
     */
    @FXML
    private AnchorPane mainContent; /* main content holder */

    @FXML
    private ImageView databaseConnectionImageView;
    @FXML
    private ImageView databaseTablesImageView;
    @FXML
    private ImageView databaseDataImageView;
    @FXML
    private Button loadDefaultDataButton;
    @FXML
    private Button loadBackupDataButton;
    @FXML
    private Button proceedButton;    
    
    /**
     * Keeps a reference to the application's thread
     */
    private CoachJ application;
    
    /**
     * Keeps a reference to the application's database connection
     */
    private DatabaseDirectConnection connection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Checking database
         */
        getDatabaseVerificationResults();
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
     * Checks database's connections, tables and data integrity
     */
    private void getDatabaseVerificationResults() {

        boolean databaseOk = true; // control variable

        /**
         * Images to be loaded into the UI
         */
        Image okImage = new Image(getClass().getResourceAsStream("img/ok16.png"));
        Image errorImage = new Image(getClass().getResourceAsStream("img/error16.png"));

        /**
         * Retrieving values from the application settings that store the result
         * of database checks and displaying them in the UI as images
         */
        boolean databaseConnection = Boolean.parseBoolean(SettingsUtils.getSetting("databaseConnection",
                "false"));
        boolean databaseTables = Boolean.parseBoolean(SettingsUtils.getSetting("databaseTables",
                "false"));
        boolean databaseIntegrity = Boolean.parseBoolean(SettingsUtils.getSetting("databaseIntegrity",
                "false"));

        if (databaseConnection) {
            databaseConnectionImageView.setImage(okImage);
        } else {
            databaseConnectionImageView.setImage(errorImage);
            databaseOk = false;
        }

        if (databaseTables) {
            databaseTablesImageView.setImage(okImage);
        } else {
            databaseTablesImageView.setImage(errorImage);
            loadDefaultDataButton.setVisible(true);
            loadBackupDataButton.setVisible(true);
            databaseOk = false;
        }

        if (databaseIntegrity) {
            databaseDataImageView.setImage(okImage);
        } else {
            databaseDataImageView.setImage(errorImage);
            loadDefaultDataButton.setVisible(true);
            loadBackupDataButton.setVisible(true);
            databaseOk = false;
        }

        /**
         * If everything is ok, check whether there's an opened season record.
         * if no, create a new one
         */
        if (SettingsUtils.getSetting("currentSeason", "0").equalsIgnoreCase("0")
                || SettingsUtils.getSetting("currentSeason", "0")
                .equalsIgnoreCase(String.valueOf(SeasonUtils.lastSeason(null)))) {
            SeasonUtils.generateNewSeason(null);
        }

        /**
         * loading main scene
         */
        if (databaseOk) {
            proceedButton.setVisible(true);
        }
    }

    @FXML
    private void proceed() {
        /**
         * Retrieving the season status to define scene content
         */
        int seasonStatus = Integer.parseInt(SettingsUtils.getSetting("seasonStatus", "0"));

        if (seasonStatus == SeasonStatus.OFF_SEASON.getStatus()) {
            SceneUtils.loadScene(this.application, OffSeasonController.class.getClass(),
                    "OffSeason.fxml");
        } else if (seasonStatus == SeasonStatus.PRE_SEASON.getStatus()) {
            SceneUtils.loadScene(this.application, PreSeasonController.class.getClass(),
                    "PreSeason.fxml");
        } else if (seasonStatus == SeasonStatus.DRAFT.getStatus()) {
            SceneUtils.loadScene(this.application, DraftController.class.getClass(),
                "Draft.fxml");
        } else if (seasonStatus == SeasonStatus.SEASON.getStatus()) {
            SceneUtils.loadScene(this.application, SeasonController.class.getClass(),
                "Season.fxml");
        }
    }
}
