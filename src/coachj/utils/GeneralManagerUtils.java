package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import coachj.models.GeneralManager;
import coachj.structures.GeneralManagerPerformance;
import coachj.structures.GeneralManagerTransactionRecord;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for general managers
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 05/08/2013
 */
public class GeneralManagerUtils {

    /**
     * Returns if the general managers agrees with the proposal or not
     *
     * @param generalManager General manager to whom the proposal is sent
     * @param coachProposal Proposal's values
     * @param franchiseOffer Franchise's offer
     * @return
     */
    public static boolean agreeWithTerms(GeneralManager generalManager, int coachProposal,
            GeneralManagerTransactionRecord offer) {
        /**
         * Variables that return the general manager's response and the
         * percentual difference between the general manager's proposal and the
         * franchise's offer
         */
        boolean agreeWithTerms;
        double differenceRate;

        /**
         * Comparing the franchise's offer with the general manager's proposal
         */
        if (offer.getSalary() >= coachProposal) {
            agreeWithTerms = true;
        } else {
            differenceRate = (1 - (double) offer.getSalary() / coachProposal) * 100;
            if (differenceRate < generalManager.getGreed() + offer.getLength()) {
                agreeWithTerms = false;
            } else {
                agreeWithTerms = true;
            }
        }

        return agreeWithTerms;
    }

    /**
     * Hires a general manager, binding him to a franchise
     *
     * @param contract Contract's parameters
     * @param connection Database connection used to retrieve data
     */
    public static void hireGeneralManager(GeneralManagerTransactionRecord contract,
            DatabaseDirectConnection connection) {
        /**
         * Variables to store the database connection and the sql statement
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        String sqlStatement;

        /**
         * Opening database connection
         */
        // // connection.open();

        /**
         * Recording contract transaction
         */
        sqlStatement = "INSERT INTO general_manager_transaction (season, generalManager, "
                + "franchise, type, date, contractLength, salary) VALUES ("
                + contract.getSeason() + ", " + contract.getGeneralManager() + ", "
                + contract.getFranchise() + ", '" + contract.getType() + "', '"
                + contract.getDate() + "', " + contract.getLength() + ", "
                + contract.getSalary() + ")";
        System.out.println("General Manager ID: " + contract.getGeneralManager()); // delete
        System.out.println(sqlStatement); //delete
        connection.executeSQL(sqlStatement);

        /**
         * Updating coach's record
         */
        sqlStatement = "UPDATE general_manager SET salary = " + contract.getSalary() + ", "
                + "remainingYears = " + contract.getLength() + ", failedContractAttempts = 0 "
                + " WHERE id = " + contract.getGeneralManager();
        connection.executeSQL(sqlStatement);

        /**
         * Updating franchise's record
         */
        sqlStatement = "UPDATE franchise SET generalManager = " + contract.getGeneralManager()
                + " WHERE id = " + contract.getFranchise();
        connection.executeSQL(sqlStatement);

        /**
         * Closing connection
         */
        connection.close();
    }

    /**
     * Records a failed attempt to either sign or resign a general manager
     *
     * @param generalManagerId General manager's id
     * @param connection Database connection used to retrieve data
     */
    public static void recordFailedContractAttempt(int generalManagerId,
            DatabaseDirectConnection connection) {
        /**
         * Variables to store the database connection and the sql statement
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        String sqlStatement;

        /**
         * Opening database connection
         */
        // // connection.open();

        /**
         * Updating coach's record
         */
        sqlStatement = "UPDATE general_manager SET failedContractAttempts = failedContractAttempts + 1 "
                + " WHERE id = " + generalManagerId;
        connection.executeSQL(sqlStatement);

        /**
         * Closing connection
         */
        connection.close();
    }

    /**
     * Releases a general manager, unbinding him from a franchise
     *
     * @param contract Contract's parameters
     * @param connection Database connection used to retrieve data
     */
    public static void releaseGeneralManager(GeneralManagerTransactionRecord contract,
            DatabaseDirectConnection connection) {
        /**
         * Variables to store the database connection and the sql statement
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        String sqlStatement;

        /**
         * Opening database connection
         */
        // // connection.open();

        /**
         * Recording transaction
         */
        sqlStatement = "INSERT INTO general_manager_transaction (season, generalManager, "
                + "franchise, type, date, contractLength, salary) VALUES ("
                + contract.getSeason() + ", " + contract.getGeneralManager() + ", "
                + contract.getFranchise() + ", '" + contract.getType() + "', '"
                + contract.getDate() + "', " + contract.getLength() + ", "
                + contract.getSalary() + ")";
        System.out.println(sqlStatement); //delete
        connection.executeSQL(sqlStatement);

        /**
         * Updating coach's record
         */
        sqlStatement = "UPDATE general_manager SET remainingYears = 0, failedContractAttempts = 0, "
                + " salary = marketValue * 4000 WHERE id = " + contract.getGeneralManager();
        connection.executeSQL(sqlStatement);

        /**
         * Updating franchise's record
         */
        sqlStatement = "UPDATE franchise SET generalManager = NULL"
                + " WHERE id = " + contract.getFranchise();
        connection.executeSQL(sqlStatement);

        /**
         * Closing connection
         */
        connection.close();
    }

    /**
     * Fires a general manager, unbinding him from a franchise
     *
     * @param contract Contract's parameters
     * @param connection Database connection used to retrieve data
     */
    public static void fireGeneralManager(GeneralManagerTransactionRecord contract,
            DatabaseDirectConnection connection) {
        /**
         * Variables to store the database connection and the sql statement
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        String sqlStatement;

        /**
         * Opening database connection
         */
        // // connection.open();

        /**
         * Recording transaction
         */
        sqlStatement = "INSERT INTO general_manager_transaction (season, generalManager, "
                + "franchise, type, date, contractLength, salary) VALUES ("
                + contract.getSeason() + ", " + contract.getGeneralManager() + ", "
                + contract.getFranchise() + ", '" + contract.getType() + "', '"
                + contract.getDate() + "', " + contract.getLength() + ", "
                + contract.getSalary() + ")";
        System.out.println(sqlStatement); //delete
        connection.executeSQL(sqlStatement);

        /**
         * Updating coach's record
         */
        sqlStatement = "UPDATE general_manager SET remainingYears = 0, failedContractAttempts = 0, "
                + " salary = marketValue * 4000, totalEarnings = totalEarnings + "
                + contract.getSalary() + " WHERE id = " + contract.getGeneralManager();
        connection.executeSQL(sqlStatement);

        /**
         * Updating franchise's record
         */
        sqlStatement = "UPDATE franchise SET generalManager = NULL, assets = assets - "
                + contract.getSalary() + " WHERE id = " + contract.getFranchise();
        connection.executeSQL(sqlStatement);

        /**
         * Closing connection
         */
        connection.close();
    }

    /**
     * Returns the id of the best unemployed general manager
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getBestUnemployedGeneralManagerId(DatabaseDirectConnection connection) {
        /**
         * Variables to store the database connection, the sql statement, the
         * resultset and the general manager's id
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        String sqlStatement = "SELECT id FROM general_manager "
                + "WHERE retired = false AND id NOT IN (SELECT generalManager "
                + "FROM franchise WHERE generalManager IS NOT NULL) "
                + "ORDER BY marketValue, RAND() LIMIT 1";
        ResultSet resultSet;
        short generalManagerId = 0;

        /**
         * Opening the connection, retrieving the general manager's id and
         * returning it
         */
        try {
            // // connection.open();
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            generalManagerId = resultSet.getShort("id");
        } catch (SQLException ex) {
            Logger.getLogger(GeneralManagerUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return generalManagerId;
    }

    /**
     * Returns a GeneralManagerPerformance data structure with information about
     * the general manager's tenure with a given franchise
     *
     * @param generalManagerId General Manager's id
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static GeneralManagerPerformance getGeneralManagerSeasonsWithFranchise(short generalManagerId,
            short franchiseId, DatabaseDirectConnection connection) {
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        String sqlStatement = "SELECT COUNT(id) AS years, SUM(1 - record - playoffStatus) "
                + "AS performanceRate FROM franchise_season_log "
                + "WHERE franchise = " + franchiseId + " AND generalManager = "
                + generalManagerId;
        ResultSet resultSet;
        GeneralManagerPerformance generalManagerPerformance = new GeneralManagerPerformance(generalManagerId, franchiseId);

        /**
         * Opening the connection, retrieving the general manager's numbers with
         * franchise and returning them
         */
        try {
            // // connection.open();
            resultSet = connection.getResultSet(sqlStatement);
            generalManagerPerformance.setYearsWithFranchise(resultSet.getShort("years"));
            generalManagerPerformance.setPerformanceRate(resultSet.getDouble("performanceRate"));
        } catch (SQLException ex) {
            Logger.getLogger(GeneralManagerUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return generalManagerPerformance;
    }
    
    /**
     * Processes general manager's contract
     * 
     * @param contract Contract data
     */
    public static void processGeneralManagerContract(GeneralManagerTransactionRecord contract) {
        
        /**
         * Checking the type of contract to process it
         */
        if (contract.getType().equalsIgnoreCase("C") || 
                contract.getType().equalsIgnoreCase("R")) {
            GeneralManagerUtils.hireGeneralManager(contract, null);
        } else if (contract.getType().equalsIgnoreCase("W")) {
            GeneralManagerUtils.fireGeneralManager(contract, null);
        } else {
            GeneralManagerUtils.releaseGeneralManager(contract, null);
        }
    }
    
    
    public static short getGeneralManagerDealingStrategy(short generalManagerId,
            DatabaseDirectConnection connection) {
        short generalManagerDealingStrategy = 0;
        
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        
        String sqlStatement = "SELECT dealingStrategy FROM general_manager "
                + "WHERE id = " + generalManagerId;
        ResultSet resultSet;        

        /**
         * Opening the connection, retrieving the general manager's id and
         * returning it
         */
        try {
            // // connection.open();
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            generalManagerDealingStrategy = resultSet.getShort("dealingStrategy");
        } catch (SQLException ex) {
            Logger.getLogger(GeneralManagerUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return generalManagerDealingStrategy;
    }       
} // end class GeneralManagerUtils
