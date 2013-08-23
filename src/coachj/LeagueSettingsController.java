/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 *
 * @author Eduardo
 */
public class LeagueSettingsController implements Initializable {

    private CoachJ application;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void setApp(CoachJ application){
        this.application = application;
    }
    
}
