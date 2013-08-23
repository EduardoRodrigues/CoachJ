package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import coachj.models.Player;
import coachj.structures.PlayerTransactionRecord;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for players
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 17/08/2013
 */
public class PlayerUtils {

    /**
     * Returns the player's complete name
     *
     * @param playerId Player's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getPlayerCompleteName(int playerId,
            DatabaseDirectConnection connection) {
        
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT position, firstName, lastName FROM player "
                + "WHERE id = " + playerId;
        String playerCompleteName = null;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            playerCompleteName = resultSet.getString("position") + " "
                    + resultSet.getString("firstName") + " "
                    + resultSet.getString("lastName");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return playerCompleteName;
    }
    
    /**
     * Returns the player's franchise id
     *
     * @param playerId Player's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getPlayerFranchiseId(int playerId,
            DatabaseDirectConnection connection) {
        
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT franchise FROM player "
                + "WHERE id = " + playerId;
        short playerFranchiseId = 0;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            playerFranchiseId = resultSet.getShort("franchise");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return playerFranchiseId;
    }
    
    /**
     * Returns the player's salary
     *
     * @param playerId Player's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static int getPlayerSalary(int playerId,
            DatabaseDirectConnection connection) {
        
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        
        ResultSet resultSet;
        String sqlStatement = "SELECT salary FROM player "
                + "WHERE id = " + playerId;
        int playerSalary = 0;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            playerSalary = resultSet.getInt("salary");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return playerSalary;
    }
    
    /**
     * Returns if the player agrees with the proposal or not
     *
     * @param player Player to whom the proposal is sent
     * @param playerProjectedSalary Player's projected salary
     * @param franchiseOffer Franchise's offer
     * @return
     */
    public static boolean agreeWithTerms(Player player, double playerProjectedSalary,
            PlayerTransactionRecord offer) {
        /**
         * Variables that return the player's response and the percentual
         * difference between the player's proposal and the franchise's offer
         */
        boolean agreeWithTerms;
        double differenceRate;
         /**
         * Generates random numbers
         */
        Random generator = new Random();

        /**
         * Comparing the franchise's offer with the player's proposal
         */
        if (offer.getSalary() >= playerProjectedSalary) {
            agreeWithTerms = true;
        } else {
            differenceRate = (1 - (double) offer.getSalary() / playerProjectedSalary) * 100;
            if (differenceRate < generator.nextInt(player.getGreed()) + offer.getLength()) {
                agreeWithTerms = false;
            } else {
                agreeWithTerms = true;
            }
        }

        return agreeWithTerms;
    }
    
    /**
     * Hires a player, binding him to a franchise
     *
     * @param contract Contract's parameters
     * @param connection Database connection used to retrieve data
     */
    public static void hirePlayer(PlayerTransactionRecord contract,
            DatabaseDirectConnection connection) {        
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
        connection.open();

        /**
         * Recording contract transaction
         */
        sqlStatement = "INSERT INTO player_transaction (season, player, "
                + "destinationFranchise, type, date, contractLength, salary) VALUES ("
                + contract.getSeason() + ", " + contract.getPlayer() + ", "
                + contract.getFranchise() + ", '" + contract.getType() + "', '"
                + contract.getDate() + "', " + contract.getLength() + ", "
                + contract.getSalary() + ")";
        System.out.println(sqlStatement); //delete
        connection.executeSQL(sqlStatement);

        /**
         * Updating player's record
         */
        short jersey = FranchiseUtils.getAvailableJerseyNumber(contract.getFranchise(), 
                connection);
        sqlStatement = "UPDATE player SET salary = " + contract.getSalary() + ", "
                + "remainingYears = " + contract.getLength() + ", failedContractAttempts = 0, "
                + "jersey = " + jersey + ", franchise = " + contract.getFranchise() 
                +  ", isActive = true WHERE id = " + contract.getPlayer();
        connection.executeSQL(sqlStatement);        

        /**
         * Closing connection
         */
        connection.close();
    }
    
    /**
     * Records a failed attempt to either sign or resign a player
     *
     * @param player Player's id
     * @param connection Database connection used to retrieve data
     */
    public static void recordFailedContractAttempt(int player,
            DatabaseDirectConnection connection) {       
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
        connection.open();

        /**
         * Updating player's record
         */
        sqlStatement = "UPDATE player SET failedContractAttempts = failedContractAttempts + 1 "
                + " WHERE id = " + player;
        connection.executeSQL(sqlStatement);

        /**
         * Closing connection
         */
        connection.close();
    }

    /**
     * Releases a player, unbinding him from a franchise
     *
     * @param contract Contract's parameters
     * @param connection Database connection used to retrieve data
     */
    public static void releasePlayer(PlayerTransactionRecord contract,
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
        connection.open();

        /**
         * Recording transaction
         */
        sqlStatement = "INSERT INTO player_transaction (season, player, "
                + "franchise, type, date, contractLength, salary) VALUES ("
                + contract.getSeason() + ", " + contract.getPlayer()+ ", "
                + contract.getFranchise() + ", '" + contract.getType() + "', '"
                + contract.getDate() + "', " + contract.getLength() + ", "
                + contract.getSalary() + ")";
        System.out.println(sqlStatement); //delete
        connection.executeSQL(sqlStatement);

        /**
         * Updating player's record
         */
        sqlStatement = "UPDATE player SET remainingYears = 0, failedContractAttempts = 0, "
                + " salary = marketValue * 4000, franchise = NULL, isActive = false WHERE id = " 
                + contract.getPlayer();
        connection.executeSQL(sqlStatement);        

        /**
         * Closing connection
         */
        connection.close();
    }

    /**
     * Fires a player, unbinding him from a franchise
     *
     * @param contract Contract's parameters
     * @param connection Database connection used to retrieve data
     */
    public static void firePlayer(PlayerTransactionRecord contract,
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
        connection.open();

        /**
         * Recording transaction
         */
        sqlStatement = "INSERT INTO player_transaction (season, player, "
                + "sourceFranchise, type, date, contractLength, salary) VALUES ("
                + contract.getSeason() + ", " + contract.getPlayer() + ", "
                + contract.getFranchise() + ", '" + contract.getType() + "', '"
                + contract.getDate() + "', " + contract.getLength() + ", "
                + contract.getSalary() + ")";
        System.out.println(sqlStatement); //delete
        connection.executeSQL(sqlStatement);

        /**
         * Updating player's record
         */
        sqlStatement = "UPDATE player SET remainingYears = 0, failedContractAttempts = 0, "
                + " salary = marketValue * 4000, totalEarnings = totalEarnings + "
                + contract.getSalary() + ", franchise = NULL, isActive = false WHERE id = " 
                + contract.getPlayer();
        connection.executeSQL(sqlStatement);        

        /**
         * Closing connection
         */
        connection.close();
    }
} // end PlayerUtils
