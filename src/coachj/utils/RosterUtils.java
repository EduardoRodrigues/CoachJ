package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.PlayerTransactionRecord;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for rosters
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 18/08/2013
 */
public class RosterUtils {

    /**
     * Reorder the roster for the given franchise, based on coach's preferences
     *
     * @param franchiseId
     * @param connection Database connection used to retrieve data
     */
    public static void reorderRoster(short franchiseId,
            DatabaseDirectConnection connection) {

        /**
         * Retrieving franchise's coach's id and his ordering string
         */
        short coachId = FranchiseUtils.getFranchiseCoachId(franchiseId, connection);
        String orderingString = CoachUtils.getCoachRosterOrderingString(coachId,
                connection);
        short activePlayers = FranchiseUtils.getActivePlayers(franchiseId, connection);

        /**
         * Resetting roster positions
         */
        resetRosterPositions(franchiseId, connection);

        /**
         * Setting new positions for starters
         */
        setRosterPosition(franchiseId, (short) 1, "C", orderingString, connection);
        setRosterPosition(franchiseId, (short) 2, "PF", orderingString, connection);
        setRosterPosition(franchiseId, (short) 3, "SF", orderingString, connection);
        setRosterPosition(franchiseId, (short) 4, "SG", orderingString, connection);
        setRosterPosition(franchiseId, (short) 5, "PG", orderingString, connection);

        /**
         * Setting new positions for reserves
         */
        for (int i = 6; i <= activePlayers; i++) {
            setRosterPosition(franchiseId, (short) i, "%", orderingString, connection);
        }
    }

    /**
     * Resets the roster positions for the players of the given franchise, so
     * that they can be reordered properly
     *
     * @param franchiseId
     * @param connection Database connection used to retrieve data
     */
    private static void resetRosterPositions(short franchiseId,
            DatabaseDirectConnection connection) {

        /**
         * Resetting roster positions
         */
        String sqlStatement = "UPDATE player SET rosterPosition = 0 "
                + "WHERE franchise = " + franchiseId;
        connection.executeSQL(sqlStatement);

    }

    /**
     * Sets the roster position for a player.
     *
     * @param franchiseId Franchise's id
     * @param rosterPosition Roster position to be assigned
     * @param playerPosition Primary position
     * @param orderingString Coach ordering criteria
     * @param connection Database connection used to retrieve data
     */
    private static void setRosterPosition(short franchiseId, short rosterPosition,
            String playerPosition, String orderingString,
            DatabaseDirectConnection connection) {

       /**
         * Retrieving player for that position, first trying a native one, then
         * trying a player that has that second position, then, finally, getting
         * the best one remaining that is able to play
         */
        ResultSet resultSet;
        String sqlStatement;
        short playerId;

        try {
            /* player native to that position */
            sqlStatement = "SELECT id FROM player WHERE isActive = true AND "
                    + "franchise = " + franchiseId + " AND rosterPosition = 0 AND "
                    + "position LIKE '" + playerPosition + "' AND isPlayable = 1 "
                    + "ORDER BY isPlayable DESC, " + orderingString + " LIMIT 0, 1";
            resultSet = connection.getResultSet(sqlStatement);

            if (resultSet.next()) {
                playerId = resultSet.getShort("id");
            } else {
                /* player that plays second position */
                sqlStatement = "SELECT id FROM player WHERE isActive = true AND "
                        + "franchise = " + franchiseId + " AND rosterPosition = 0 AND "
                        + "position2 LIKE '" + playerPosition + "' AND isPlayable = 1 "
                        + "ORDER BY isPlayable DESC, " + orderingString + " LIMIT 0, 1";
                resultSet = connection.getResultSet(sqlStatement);

                if (resultSet.next()) {
                    playerId = resultSet.getShort("id");
                } else {
                    /* whichever player able to play */
                    sqlStatement = "SELECT id FROM player WHERE isActive = true AND "
                            + "franchise = " + franchiseId + " AND rosterPosition = 0 AND "
                            + "isPlayable = 1 ORDER BY isPlayable DESC, " + orderingString
                            + " LIMIT 0, 1";
                    resultSet = connection.getResultSet(sqlStatement);
                    resultSet.first();
                    playerId = resultSet.getShort("id");
                }
            }

            /**
             * Updating player's roster position
             */
            sqlStatement = "UPDATE player SET rosterPosition = " + rosterPosition
                    + " WHERE id = " + playerId;
            connection.executeSQL(sqlStatement);

        } catch (SQLException ex) {
            Logger.getLogger(RosterUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Signs players for the computer-controlled franchises.
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     */
    public static void signPlayers(short franchiseId,
            DatabaseDirectConnection connection) {

        /**
         * Retrieving franchise financial data to calculate the maximum offer it
         * can make
         */
        int franchiseAssets = Integer.parseInt(FranchiseUtils.getFieldValueAsString(franchiseId,
                "assets", connection));
        int franchisePayroll = FranchiseUtils.getPayroll(franchiseId, connection);
        int franchiseAvailableAssets = franchiseAssets - franchisePayroll;
        short franchiseActivePlayers = FranchiseUtils.getActivePlayers(franchiseId,
                connection);
        short maximumActivePlayers = Short.parseShort(SettingsUtils
                .getSetting("minimumPlayersPerTeam", "15"));
        int minimumSalary = Integer.parseInt(SettingsUtils
                .getSetting("minimumSalary", "1500000"));
        int requiredPlayers = maximumActivePlayers - franchiseActivePlayers;
        int maximumOffer = franchiseAvailableAssets / requiredPlayers > minimumSalary
                ? franchiseAvailableAssets / requiredPlayers : minimumSalary;

        /**
         * if the franchise already has the required number of players, return
         */
        if (franchiseActivePlayers == maximumActivePlayers) {
            return;
        }

        ResultSet resultSet;
        short coachId = FranchiseUtils.getFranchiseCoachId(franchiseId, connection);
        String freeAgentSigningSQL;
        int playerId;

        System.out.println("Signing players..."); // delete

        while (franchiseActivePlayers < maximumActivePlayers) {

            try {

                /**
                 * Retrieving the selected player and signing him
                 */         
                freeAgentSigningSQL = CoachUtils.getCoachFreeAgentSelectSQL(coachId,
                maximumOffer, connection);
                resultSet = connection.getResultSet(freeAgentSigningSQL);
                resultSet.first();
                playerId = resultSet.getInt("id");

                /**
                 * Creating contract object
                 */
                PlayerTransactionRecord offer = new PlayerTransactionRecord();
                offer.setPlayer(playerId);
                offer.setDate(SettingsUtils.getSetting("currentDate",
                        Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
                offer.setFranchise(franchiseId);
                offer.setLength((short) 2);
                offer.setSalary((int) PlayerUtils.getPlayerSalary(playerId, connection));
                offer.setType("C");
                offer.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                        String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));

                PlayerUtils.hirePlayer(offer, connection);
                franchiseActivePlayers++;
            } catch (SQLException ex) {
                Logger.getLogger(RosterUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
} // end RosterUtils
