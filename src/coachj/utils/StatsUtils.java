package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for stats
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 28/08/2013
 */
public class StatsUtils {

    /**
     * Returns a string with shots made, shots attempted and shooting percentage,
     * to be used for displaying purposes
     * 
     * @param shotsMade Number of shots made
     * @param shotsAttempted Number of shots attempted
     * @return 
     */
    public static String getBoxScoreShootingPercentage(int shotsMade, int shotsAttempted) {
        String boxScoreShootingPercentage;
        double shootingPercentage = 0;

        if (shotsAttempted > 0) {
            shootingPercentage = (double) shotsMade / shotsAttempted;
        }
        
        boxScoreShootingPercentage = String.format("%02d-%02d (%.3f)", shotsMade, shotsAttempted,
                shootingPercentage);

        return boxScoreShootingPercentage;
    }
    
    /**
     * Returns the league's average rate for the given attribute
     * 
     * @param attribute Attribute to calculate average
     * @param connection Database connection used to retrieve data
     * @return 
     */
    public static double getLeagueAttributeAverageRate(String attribute, 
            DatabaseDirectConnection connection) {
        
        double leagueAttributeAverage = 65;
        ResultSet resultSet;
        String sqlStatement = "SELECT AVG(" + attribute + ") AS leagueAverage "
                + "FROM player WHERE active = true AND franchise IS NOT NULL";

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            leagueAttributeAverage = resultSet.getDouble("leagueAverage");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return leagueAttributeAverage;
    }
    
} // end StatsUtils
