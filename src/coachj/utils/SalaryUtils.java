package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for calculating salaries
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 22/08/2013
 */
public class SalaryUtils {

    public static double getPlayerAverageMarketValue(DatabaseDirectConnection connection) {
        double playerAverageMarketValue = 0;
        String sqlStatement = "SELECT AVG(marketValue) AS averageMarketValue "
                + "FROM player WHERE retired = false";
        ResultSet resultSet;
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            connection.open();
        }

        try {
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            playerAverageMarketValue = resultSet.getDouble("averageMarketValue");
        } catch (SQLException ex) {
            Logger.getLogger(SalaryUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return playerAverageMarketValue;
    }
} // end SalaryUtils
