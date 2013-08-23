package coachj.utils;

import coachj.dao.DatabaseDirectConnection;

/**
 * Utility class for the regular season and playoffs schedule
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 22/08/2013
 */
public class ScheduleUtils {

    public static void removeSeasonPreviousGames(short season,
            DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            connection.open();
        }

        /**
         * Removing all previous games for the given season
         */
        String sqlStatement = "DELETE FROM game WHERE season = " + season;
        connection.executeSQL(sqlStatement);
    }    
} // end class ScheduleUtils
