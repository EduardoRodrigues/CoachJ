package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import coachj.enums.CourtZones;
import coachj.ingame.InGamePlayer;
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


        ResultSet resultSet;
        String sqlStatement = "SELECT position, firstName, lastName FROM player "
                + "WHERE id = " + playerId;
        String playerCompleteName = null;

        try {
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
        }

        return playerCompleteName;
    }
    
    /**
     * Returns the player's jersey
     *
     * @param playerId Player's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static int getPlayerJersey(int playerId,
            DatabaseDirectConnection connection) {


        ResultSet resultSet;
        String sqlStatement = "SELECT jersey FROM player "
                + "WHERE id = " + playerId;
        int playerJersey = 0;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            playerJersey = resultSet.getInt("jersey");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return playerJersey;
    }

    /**
     * Returns the player's position
     *
     * @param playerId Player's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getPlayerPosition(int playerId,
            DatabaseDirectConnection connection) {

        ResultSet resultSet;
        String sqlStatement = "SELECT position FROM player "
                + "WHERE id = " + playerId;
        String position = null;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            position = resultSet.getString("position");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return position;
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

        ResultSet resultSet;
        String sqlStatement = "SELECT franchise FROM player "
                + "WHERE id = " + playerId;
        short playerFranchiseId = 0;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            playerFranchiseId = resultSet.getShort("franchise");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
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

        ResultSet resultSet;
        String sqlStatement = "SELECT salary FROM player "
                + "WHERE id = " + playerId;
        int playerSalary = 0;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            playerSalary = resultSet.getInt("salary");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
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
         * Variables that return the player's response and the percentage
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
            if (differenceRate < generator.nextInt(player.getGreed() + 1) + offer.getLength()) {
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

        String sqlStatement;

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
                + ", active = true, gamesWithTeam = 0 WHERE id = " + contract.getPlayer();
        connection.executeSQL(sqlStatement);
    }

    /**
     * Records a failed attempt to either sign or resign a player
     *
     * @param player Player's id
     * @param connection Database connection used to retrieve data
     */
    public static void recordFailedContractAttempt(int player,
            DatabaseDirectConnection connection) {

        String sqlStatement;

        /**
         * Updating player's record
         */
        sqlStatement = "UPDATE player SET failedContractAttempts = failedContractAttempts + 1 "
                + " WHERE id = " + player;
        connection.executeSQL(sqlStatement);
    }

    /**
     * Releases a player, unbinding him from a franchise
     *
     * @param contract Contract's parameters
     * @param connection Database connection used to retrieve data
     */
    public static void releasePlayer(PlayerTransactionRecord contract,
            DatabaseDirectConnection connection) {

        String sqlStatement;

        /**
         * Recording transaction
         */
        sqlStatement = "INSERT INTO player_transaction (season, player, "
                + "franchise, type, date, contractLength, salary) VALUES ("
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
                + " salary = marketValue * 4000, franchise = NULL, active = false WHERE id = "
                + contract.getPlayer();
        connection.executeSQL(sqlStatement);
    }

    /**
     * Fires a player, unbinding him from a franchise
     *
     * @param contract Contract's parameters
     * @param connection Database connection used to retrieve data
     */
    public static void firePlayer(PlayerTransactionRecord contract,
            DatabaseDirectConnection connection) {

        String sqlStatement;

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
                + contract.getSalary() + ", franchise = NULL, active = false WHERE id = "
                + contract.getPlayer();
        connection.executeSQL(sqlStatement);
    }

    /**
     * Populates player's decision and movement arrays
     *
     * @param player
     */
    public static void populatePlayerArrays(InGamePlayer player) {

        /**
         * Populating decision array with decisions based on player's attributes
         *
         * Since passing is one of the most commom actions, populate the array
         * with 10 of that, then add more based on player's passing attribute
         */
        populatePlayerDecisionArray(player, "pass", 20);
        populatePlayerDecisionArray(player, "pass", player.getBaseAttributes().getPass() / 8);

        /* 
         * Populating with shot decision, based on player's fieldgoal and 
         * aggressiveness attributes
         */
        populatePlayerDecisionArray(player, "shoot", player.getBaseAttributes().getFieldGoals() / 20);
        populatePlayerDecisionArray(player, "shoot", player.getBaseAttributes().getAggressiveness() / 40);

        /* 
         * Populating with fake decision, based on player's fakeshotfrequency and 
         * technique attributes
         */
        populatePlayerDecisionArray(player, "fake", player.getBaseAttributes().getFakeShotFrequency() / 40);
        populatePlayerDecisionArray(player, "fake", player.getBaseAttributes().getTechnique() / 40);

        /* 
         * Populating with look decision, based on player's courtvision and 
         * patience attributes
         */
        populatePlayerDecisionArray(player, "look for pass", player.getBaseAttributes().getCourtVision() / 40);
        populatePlayerDecisionArray(player, "look for pass", player.getBaseAttributes().getPatience() / 40);

        /* 
         * Populating with dribble decision, based on player's dribble and 
         * patience attributes
         */
        populatePlayerDecisionArray(player, "dribble", player.getBaseAttributes().getDribble() / 20);
        populatePlayerDecisionArray(player, "dribble", player.getBaseAttributes().getPatience() / 40);

        /* 
         * Populating with drive decision, based on player's drive and aggressiveness
         * attributes
         */
        populatePlayerDecisionArray(player, "drive", player.getBaseAttributes().getDrive() / 10);
        populatePlayerDecisionArray(player, "drive", player.getBaseAttributes().getAggressiveness() / 50);

        /**
         * Populating movement array with decisions based on player's positions
         *
         * The court zones 14 (offensive halfcourt) and 15 (defensive halfcourt)
         * are inserted in the movement array based on player's positions not
         * attributes, since point guards and shooting guards tend to spend more
         * time in those zones of the court
         */
        switch (player.getBaseAttributes().getPosition()) {
            case "PG":
            case "SG":
                populatePlayerMovementArray(player,
                        CourtZones.DEFENSIVE_HALFCOURT.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.OFFENSIVE_HALFCOURT.getCourtZone(), 4);
                break;
            case "SF":
                populatePlayerMovementArray(player,
                        CourtZones.DEFENSIVE_HALFCOURT.getCourtZone(), 2);
                populatePlayerMovementArray(player,
                        CourtZones.OFFENSIVE_HALFCOURT.getCourtZone(), 2);
                break;
            default:
                populatePlayerMovementArray(player,
                        CourtZones.DEFENSIVE_HALFCOURT.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.OFFENSIVE_HALFCOURT.getCourtZone(), 1);
                break;
        }

        switch (player.getBaseAttributes().getPosition2()) {
            case "PG":
            case "SG":
                populatePlayerMovementArray(player,
                        CourtZones.DEFENSIVE_HALFCOURT.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.OFFENSIVE_HALFCOURT.getCourtZone(), 4);
                break;
            case "SF":
                populatePlayerMovementArray(player,
                        CourtZones.DEFENSIVE_HALFCOURT.getCourtZone(), 2);
                populatePlayerMovementArray(player,
                        CourtZones.OFFENSIVE_HALFCOURT.getCourtZone(), 2);
                break;
            default:
                populatePlayerMovementArray(player,
                        CourtZones.DEFENSIVE_HALFCOURT.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.OFFENSIVE_HALFCOURT.getCourtZone(), 1);
                break;
        }

        /**
         * Populating backcourt zones (2, 8 and 13) and corners (1 and 12)
         * accordingly to the player's positions
         */
        switch (player.getBaseAttributes().getPosition()) {
            case "PG":
            case "SG":
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_WING.getCourtZone(), 10);
                populatePlayerMovementArray(player,
                        CourtZones.TOP_ARC.getCourtZone(), 10);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_WING.getCourtZone(), 10);
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_CORNER.getCourtZone(), 10);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_CORNER.getCourtZone(), 10);
                break;
            case "SF":
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_WING.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.TOP_ARC.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_WING.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_CORNER.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_CORNER.getCourtZone(), 4);
                break;
            default:
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_WING.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.TOP_ARC.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_WING.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_CORNER.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_CORNER.getCourtZone(), 1);
                break;
        }

        switch (player.getBaseAttributes().getPosition2()) {
            case "PG":
            case "SG":
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_WING.getCourtZone(), 10);
                populatePlayerMovementArray(player,
                        CourtZones.TOP_ARC.getCourtZone(), 10);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_WING.getCourtZone(), 10);
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_CORNER.getCourtZone(), 10);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_CORNER.getCourtZone(), 10);
                break;
            case "SF":
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_WING.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.TOP_ARC.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_WING.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_CORNER.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_CORNER.getCourtZone(), 4);
                break;
            default:
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_WING.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.TOP_ARC.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_WING.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_CORNER.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_CORNER.getCourtZone(), 1);
                break;
        }

        /**
         * Populating paint (6) and low-post zones (3, 9) accordingly to the
         * player's positions
         */
        switch (player.getBaseAttributes().getPosition()) {
            case "PG":
            case "SG":
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_LOW_POST.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.PAINT.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_LOW_POST.getCourtZone(), 1);
                break;
            case "SF":
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_LOW_POST.getCourtZone(), 2);
                populatePlayerMovementArray(player,
                        CourtZones.PAINT.getCourtZone(), 2);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_LOW_POST.getCourtZone(), 2);
                break;
            default:
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_LOW_POST.getCourtZone(), 10);
                populatePlayerMovementArray(player,
                        CourtZones.PAINT.getCourtZone(), 10);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_LOW_POST.getCourtZone(), 10);
                break;
        }

        switch (player.getBaseAttributes().getPosition2()) {
            case "PG":
            case "SG":
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_LOW_POST.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.PAINT.getCourtZone(), 1);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_LOW_POST.getCourtZone(), 1);
                break;
            case "SF":
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_LOW_POST.getCourtZone(), 2);
                populatePlayerMovementArray(player,
                        CourtZones.PAINT.getCourtZone(), 2);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_LOW_POST.getCourtZone(), 2);
                break;
            default:
                populatePlayerMovementArray(player,
                        CourtZones.RIGHT_LOW_POST.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.PAINT.getCourtZone(), 4);
                populatePlayerMovementArray(player,
                        CourtZones.LEFT_LOW_POST.getCourtZone(), 4);
                break;
        }

        /*
         * Inserting extra elements for player's favorite court zone
         */
        populatePlayerMovementArray(player, player.getBaseAttributes()
                .getFavoriteCourtZone().getId(), 5);

        /**
         * Populating movement array with decisions based on player's attributes
         *
         * Three-Pointer zones: right corner (1), right wing (2), top of the arc
         * (8), left wing (13), and left corner (12)
         */
        populatePlayerMovementArray(player,
                CourtZones.RIGHT_CORNER.getCourtZone(),
                player.getBaseAttributes().getThreePointers() / 10);
        populatePlayerMovementArray(player,
                CourtZones.RIGHT_WING.getCourtZone(),
                player.getBaseAttributes().getThreePointers() / 10);
        populatePlayerMovementArray(player,
                CourtZones.TOP_ARC.getCourtZone(),
                player.getBaseAttributes().getThreePointers() / 10);
        populatePlayerMovementArray(player,
                CourtZones.LEFT_WING.getCourtZone(),
                player.getBaseAttributes().getThreePointers() / 10);
        populatePlayerMovementArray(player,
                CourtZones.LEFT_CORNER.getCourtZone(),
                player.getBaseAttributes().getThreePointers() / 10);

        /**
         * Jump shot zones: right side of the lane (4), right elbow (5), top of
         * the arc (7), left side of the lane (10), left elbow (11)
         */
        populatePlayerMovementArray(player,
                CourtZones.RIGHT_SIDE_LANE.getCourtZone(),
                player.getBaseAttributes().getPullUpJumper() / 10);
        populatePlayerMovementArray(player,
                CourtZones.RIGHT_ELBOW.getCourtZone(),
                player.getBaseAttributes().getPullUpJumper() / 10);
        populatePlayerMovementArray(player,
                CourtZones.TOP_KEY.getCourtZone(),
                player.getBaseAttributes().getPullUpJumper() / 10);
        populatePlayerMovementArray(player,
                CourtZones.LEFT_SIDE_LANE.getCourtZone(),
                player.getBaseAttributes().getPullUpJumper() / 10);
        populatePlayerMovementArray(player,
                CourtZones.LEFT_ELBOW.getCourtZone(),
                player.getBaseAttributes().getPullUpJumper() / 10);

        /**
         * Populating the lane zone (6)
         */
        populatePlayerMovementArray(player,
                CourtZones.PAINT.getCourtZone(),
                player.getBaseAttributes().getInThePaint() / 10);

        /**
         * Populating low-post zones (3, 9)
         */
        populatePlayerMovementArray(player,
                CourtZones.RIGHT_LOW_POST.getCourtZone(),
                player.getBaseAttributes().getLowPost() / 10);
        populatePlayerMovementArray(player,
                CourtZones.LEFT_LOW_POST.getCourtZone(),
                player.getBaseAttributes().getLowPost() / 10);
    }

    /**
     * Populates the decision array
     *
     * @param player InGamePlayer object
     * @param decision Decision to be inserted
     * @param count Number of times the decision will be inserted
     */
    private static void populatePlayerDecisionArray(InGamePlayer player, String decision,
            int count) {
        for (int i = 0; i < count; i++) {
            player.getDecisionArray().add(decision);
        }
    }

    /**
     * Populates the movement array
     *
     * @param courtZone Court zone to be inserted
     * @param count Number of times the decision will be inserted
     */
    private static void populatePlayerMovementArray(InGamePlayer player, int courtZone, int count) {
        for (int i = 0; i < count; i++) {
            player.getMovementArray().add(courtZone);
        }
    }
} // end PlayerUtils
