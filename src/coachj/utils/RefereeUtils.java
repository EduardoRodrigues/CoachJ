package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for referees
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 28/08/2013
 */
public class RefereeUtils {

    /**
     * Returns the id of a random referee who hasn't already officiated a game
     * in the given date
     * 
     * @param date Game's date
     * @param connection Database connection used to retrieve data
     * @return 
     */
    public static short getRandomRefereeId(String date,
            DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }

        short refereeId = 0;
        ResultSet resultSet;
        String sqlStatement = "SELECT id FROM referee WHERE retired = false AND "
                + " id NOT IN (SELECT referee FROM game WHERE referee IS NOT NULL "
                + " AND date = '" + date + "') ORDER BY RAND() LIMIT 1";

        try {
            /**
             * Opening database connection
             */
            // // connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            refereeId = resultSet.getShort("id");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return refereeId;
    }
} // end RefereeUtils
