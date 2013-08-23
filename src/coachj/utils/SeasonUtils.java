package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilities for managing seasons
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 09/08/2013
 */
public class SeasonUtils {

    /**
     * Returns the year of the last season played
     *
     * @param connection Database connection used to retrieve data
     * @return The year of the last season played or zero if there's no played
     * season
     */
    public static int lastSeason(DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset, store 
         * the sql statement and the current season
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT year, finished FROM season "                
                + "ORDER BY year DESC LIMIT 1";
        int lastSeason = 0;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Checking if there's a record in the database, if yes and it's
             * finished, generate a new season and setup the starting date. If
             * not, the season's is already on course and nothing has to be done
             */
            resultSet = connection.getResultSet(sqlStatement);
            if (resultSet.next()) {
                resultSet.first();
                if (resultSet.getBoolean("finished")) {
                    lastSeason = resultSet.getInt("year");
                } 
            }
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
       
        return lastSeason;
    }

    /**
     * Generates a new season   
     * 
     * @param connection Database connection used to retrieve data
     */
    public static void generateNewSeason(DatabaseDirectConnection connection) {
         /*
         * Variables that connect to the database, retrieve the resultset, store 
         * the sql statement and the current season
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }        
        String sqlStatement;
        
        int newSeason;
        int lastSeason = lastSeason(connection);
        
        if (lastSeason == 0) {
            newSeason = Calendar.getInstance().get(Calendar.YEAR);
        } else {
            newSeason = ++lastSeason;
        }
        
        /**
         * generating new season and setting the current date
         */
        connection.open();
        sqlStatement = "INSERT INTO season (year, finished) " +
                "VALUES(" + newSeason + ", false)";
        connection.executeSQL(sqlStatement);
        SettingsUtils.setSetting("currentDate", newSeason + "-01-01");
        SettingsUtils.setSetting("currentSeason", String.valueOf(newSeason));
        connection.close();
    }
} // end class SeasonUtils
