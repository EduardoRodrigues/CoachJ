package coachj.utils;

import coachj.CoachJ;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * Utilities for managing scenes
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 22/07/2013
 */
public class SceneUtils {

    /**
     * Loads the content of a FXML file into a Node object so that can be
     * injected into a scene
     *
     * @param fxmlFile FXML file to be injected
     * @return Node object
     */
    public static Node getContentFromFXML(String fxmlFile) {
        /**
         * Retrieving internationalization settings
         */
        Locale local = new Locale(SettingsUtils.getSetting("language", "pt"));
        FXMLLoader loader = new FXMLLoader();

        /**
         * Injecting the content of the FXML file into the Node object, with
         * proper internationalized strings retrieved from resource file
         */
        try {
            InputStream inputStream = CoachJ.class.getResourceAsStream(fxmlFile);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(CoachJ.class.getResource(fxmlFile));
            loader.setResources(ResourceBundle.getBundle("coachj.locales.coach_j", local));
            Node conteudo;
            conteudo = (Node) loader.load(inputStream);
            return conteudo;
        } catch (IOException ex) {
            Logger.getLogger(CoachJ.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Injects the content of the FXML file into an AnchorPane object - with
     * proper internationalized strings retrieved from resource file -, creates
     * a scene with the AnchorPane, loads the scene into a Stage objects and
     * then return the Controller file for the scene
     *
     * @param stage Stage which the scene will be injected in
     * @param fxmlPath Path to the FXML file to be injected
     * @return
     * @throws Exception
     */
    public static Initializable replaceSceneContent(Stage stage, String fxmlPath) throws Exception {
        Locale local = new Locale(SettingsUtils.getSetting("language", "pt"));
        FXMLLoader loader = new FXMLLoader();
        InputStream in = CoachJ.class.getResourceAsStream(fxmlPath);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(CoachJ.class.getResource(fxmlPath));
        loader.setResources(ResourceBundle.getBundle("coachj.locales.coach_j", local));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, 640, 480);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    /**
     * Injects the content of a fxml file into the application main thread.
     *
     * @param application Reference to the application main thread
     * @param object Class of the controller for the fxml file
     * @param fxmlFile Path to the fxml file     
     */
    public static void loadScene(CoachJ application, Object object, String fxmlFile) {
        /**
         * Creating a object to retrieve the controller file, then setting up
         * properties before showing it up
         */
        try {
            Object scene = SceneUtils
                    .replaceSceneContent(application.getStage(), fxmlFile);
            Field app = scene.getClass().getDeclaredField("application");
            app.setAccessible(true);
            app.set(scene, application);                        
            application.fullSizeScene();
            application.showMainStage();
        } catch (Exception ex) {
            Logger.getLogger(CoachJ.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Asks for confirmation before leaving the application
     *
     * @param message Message to be displayed
     * @param title Title of the message box
     */
    public static void exit(String message, String title) {
        if (JOptionPane.showConfirmDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
            System.exit(0);
        }
    }

    /**
     * Shows a warning message box
     *
     * @param message Message to be displayed
     * @param title Title of the message box
     */
    public static void warning(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Shows an error message box
     *
     * @param message Message to be displayed
     * @param title Title of the message box
     */
    public static void error(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title,
                JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Display a confirmation message box and returns the user's selection
     *
     * @param message Message to be displayed
     * @param title Title of the message box
     */
    public static int confirm(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
}
