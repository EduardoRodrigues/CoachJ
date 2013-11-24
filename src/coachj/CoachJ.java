package coachj;

import coachj.dao.DatabaseDirectConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import coachj.utils.SettingsUtils;
import coachj.utils.MySqlUtils;
import coachj.utils.SceneUtils;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * Startup class of CoachJ's application
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 07/07/2013
 */
public class CoachJ extends Application {

    /*
     * Field used to keep reference to the main stage
     */
    private Stage stage;
    /**
     * Database connection
     */
    private DatabaseDirectConnection connection;

    /**
     * Main method (deprecated)
     *
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(CoachJ.class, (java.lang.String[]) null);
    }

    /**
     * Application's starting method
     *
     * @param primaryStage Main stage
     */
    @Override
    public void start(Stage primaryStage) {

        /*
         * Binding main stage to the private field and 
         * retrieving league's name
         */
        this.stage = primaryStage;
        String leagueName = SettingsUtils.getSetting("name", "All Americas Basketball League");

        /**
         * Creating and opening database connection
         */
        connection = new DatabaseDirectConnection();
        connection.open();

        /**
         * Checking database connection, tables and data integrity
         */
        boolean databaseConnection = MySqlUtils.checkDatabaseConnection();
        boolean databaseTables = MySqlUtils.checkDatabaseTables();
        boolean databaseIntegrity = MySqlUtils.checkDatabaseIntegrity();

        /**
         * Storing result of operations into app setting to be used by the post
         * loader scene
         */
        SettingsUtils.setSetting("databaseConnection", String.valueOf(databaseConnection));
        SettingsUtils.setSetting("databaseTables", String.valueOf(databaseTables));
        SettingsUtils.setSetting("databaseIntegrity", String.valueOf(databaseIntegrity));

        /*
         * Setting window's properties and calling postloader
         * scene
         */
        try {
            stage.setTitle("Coach J | " + leagueName);
            stage.setWidth(640);
            stage.setHeight(480);
            gotoPostLoader();
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(CoachJ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * Utility methods that allow controllers to modify the main stage
     */
    /**
     * Gets the application's main stage
     *
     * @return
     * @see Stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Changes the dimensions of the main stage
     *
     * @param width New width for the stage
     * @param height New height for the stage
     */
    public void setMainStageSize(double width, double height) {
        this.stage.setWidth(width);
        this.stage.setHeight(height);
    }

    /**
     * Changes the coordinates from which the main stage is drawn upon
     *
     * @param X Horizontal position
     * @param Y Vertical position
     */
    public void setMainStageLocation(double X, double Y) {
        this.stage.setX(X);
        this.stage.setY(Y);
    }

    /**
     * Allows or not the scene's window to be resized
     *
     * @param resizable Whether the window can be resized or not
     */
    public void setResizableStage(boolean resizable) {
        this.stage.setResizable(resizable);
    }

    /**
     * Displays the main stage
     */
    public void showMainStage() {
        this.stage.show();
    }

    /**
     * Loads the postloader scene
     */
    private void gotoPostLoader() {
        try {
            /**
             * Closing connection
             */
            connection.close();
            
            PostLoaderController postLoader = (PostLoaderController) 
                    SceneUtils.replaceSceneContent(this.stage, "PostLoader.fxml");
            postLoader.setApp(this);
            
            
        } catch (Exception ex) {
            Logger.getLogger(CoachJ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Resizes the main scene to full size screen
     */
    public void fullSizeScene() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        setMainStageSize(bounds.getWidth(), bounds.getHeight());
        setMainStageLocation(0, 0);
        setResizableStage(true);
    }    
}
