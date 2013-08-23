/**
 * Application's settings key
 * 
 * name - league's name
 * seasonStatus - Season's status
 * language - application's language
 * requiredFranchises - minimum franchises required to start the season
 * minimumPlayersPerTeam - minimum players required in a team's roster
 * draftRounds - number of drafting rounds
 * databaseConnection - holds the status of the database's connection check
 * databaseTables - holds the status of the database's tables check
 * databaseIntegrity - holds the status of the database's data integrity check
 * MySqlUrl - MySQL connection's URL
 * MySqlUser - MySQL's user account
 * MySqlUserPassword - MySQL user's password
 * minimumSalary - lowest salary paid to players/coaches
 * currentDate - Season's current date
 * currentSeason - Current season
 */

package coachj.utils;

import java.util.prefs.Preferences;

/**
 * Manages application's settings
 * 
 * @author Eduardo M. Rodrigues
 * @version 1.0 
 * @date 09/10/2012
 */
public class SettingsUtils {

    /**
     * Preferences Objects responsible to manage application settings.
     */
    private static Preferences settings = Preferences.userRoot()
            .node("br/com/coachj/settings");
    
    /**
     * Returns the value of a setting.
     * 
     * @param setting Setting node to be retrieved
     * @param defaultValue Default value in case the setting can't be retrieved
     * @return The value of the setting, as a String
     */
    public static String getSetting(String setting, String defaultValue) {
        String settingValue;
        
        settingValue = settings.get(setting, defaultValue);
        
        return settingValue;
    }
    
    /**
     * Sets the value of a setting.
     * 
     * @param setting Setting to be changed
     * @param value New value of the setting
     */
    public static void setSetting(String setting, String value) {
        settings.put(setting, value);
    }
    
} // end class SettingsUtils
