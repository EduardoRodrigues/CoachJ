package fx_dismissed;

import coachj.CoachJ;
import coachj.enums.SeasonStatus;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import coachj.utils.SettingsUtils;

/**
 * Controller for the application's main scene
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 07/07/2013
 */
public class MainSceneController implements Initializable {

    /**
     * FXML components
     */
    @FXML
    private Pane mainContent; /* main content holder */

    @FXML
    private Menu leagueMenu;
    @FXML
    private Menu directoriesMenu;
    @FXML
    private Menu preSeasonMenu;
    @FXML
    private Menu seasonMenu;
    @FXML
    private Menu playoffsMenu;
    @FXML
    private Menu applicationMenu;
    /**
     * Keeps a reference to the application's thread
     */
    private CoachJ application;
    /**
     * Reference to resources file
     */
    private ResourceBundle resources;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Creates a reference to the resources file and calls the method that
         * define the main content of the scene
         */
        this.resources = rb;

        defineMainContent();
    }

    /**
     * Define the content to the displayed in the main content holder
     */
    public void defineMainContent() {


        String fxmlPath; // path to fxml file
        /**
         * Retrieving the season status to define content
         */
        int seasonStatus = Integer.parseInt(SettingsUtils.getSetting("seasonStatus", "0"));

        /**
         * Clearing the main content pane in case an previous scene is already
         * loaded into it
         */
        if (!mainContent.getChildren().isEmpty()) {
            mainContent.getChildren().clear();
        }

        /**
         * Setting up main menu bar accordingly to the season status
         */
        setupMainMenuBar(seasonStatus);

        if (seasonStatus == SeasonStatus.OFF_SEASON.getStatus()) {
            fxmlPath = "OffSeasonStatus.fxml";
        } else if (seasonStatus == SeasonStatus.PRE_SEASON.getStatus()) {
            fxmlPath = "PreSeason.fxml";
        } else {
            fxmlPath = "Season.fxml";

        }

        this.setMainContent(fxmlPath);      
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
     * Asks for confirmation before leaving the application
     */
    public void exit() {
        if (JOptionPane.showConfirmDialog(null,
                resources.getString("ch_deseja_sair"), resources.getString("ch_sair"), JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == 0) {
            System.exit(0);
        }
    }

    /**
     * Injects a FXML into the main content holder
     *
     * @param fxmlPath FXML file to be injected
     */
    public void setMainContent(String fxmlPath) {
        /**
         * Retrieving internationalization settings
         */
        Locale idioma = new Locale(SettingsUtils.getSetting("language", "pt"));
        FXMLLoader loader = new FXMLLoader(); // FXML file loader

        /**
         * Injecting the content of the FXML file into the main content holder
         */
        try {
            InputStream in = CoachJ.class.getResourceAsStream(fxmlPath);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(CoachJ.class.getResource(fxmlPath));
            loader.setResources(ResourceBundle.getBundle("coachj.locales.coach_j", idioma));
            Node conteudo;
            conteudo = (Node) loader.load(in);
            mainContent.getChildren().add(conteudo);
        } catch (IOException ex) {
            Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Configures the main menu bar, accordingly to the season status
     *
     * @param seasonStatus Season's current status
     */
    public void setupMainMenuBar(int seasonStatus) {
        leagueMenu.setDisable(!(seasonStatus == SeasonStatus.OFF_SEASON.getStatus()));
        directoriesMenu.setDisable(!(seasonStatus == SeasonStatus.OFF_SEASON.getStatus()));
        preSeasonMenu.setDisable(!(seasonStatus == SeasonStatus.PRE_SEASON.getStatus()));
        seasonMenu.setDisable(!(seasonStatus == SeasonStatus.SEASON.getStatus()));
        playoffsMenu.setDisable(!(seasonStatus == SeasonStatus.PLAYOFFS.getStatus()));
    }
}
