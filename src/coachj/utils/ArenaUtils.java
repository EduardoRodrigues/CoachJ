package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for arenas
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 28/08/2013
 */
public class ArenaUtils {

    /**
     * Returns the capacity for the given arena
     * 
     * @param arenaId Arena's id
     * @param connection Database connection used to retrieve data
     * @return 
     */
    public static int getArenaCapacity(short arenaId, DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        
        ResultSet resultSet;
        String sqlStatement = "SELECT capacity FROM arena WHERE id = " + arenaId;
        int arenaCapacity = 0;

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
            arenaCapacity = resultSet.getInt("capacity");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return arenaCapacity;
    }
    
    /**
     * Returns the name for the given arena
     * 
     * @param arenaId Arena's id
     * @param connection Database connection used to retrieve data
     * @return 
     */
    public static String getArenaName(short arenaId, DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        
        ResultSet resultSet;
        String sqlStatement = "SELECT name FROM arena WHERE id = " + arenaId;
        String arenaName = null;

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
            arenaName = resultSet.getString("name");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return arenaName;
    }
} // end ArenaUtils
