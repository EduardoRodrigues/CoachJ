/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import java.sql.ResultSet;

/**
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0 /2012
 */
public class NarrationUtils {

    /**
     * Returns  narration data
     *     
     * @param narrationId Narration's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static ResultSet getNarrationData(int narrationId,
            DatabaseDirectConnection connection) {
        
        ResultSet resultSet;
        String sqlStatement = "SELECT * FROM narration WHERE id = " + narrationId
                + " LIMIT 1";

        /**
         * Executing query and retrieving resultset
         */
        resultSet = connection.getResultSet(sqlStatement);

        return resultSet;
    }
} // end class NarrationUtils
