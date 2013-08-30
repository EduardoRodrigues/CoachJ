package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.DraftSummary;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for the draft scene
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 16/08/2013
 */
public class DraftUtils {

    /**
     * Returns the current draft round for the given season
     *
     * @param season Season to retrieve the draft round from
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getDraftRound(short season,
            DatabaseDirectConnection connection) {
        short draftRound = 1;
        short draftPick;
        short draftRounds = DraftUtils.getTotalDraftRounds();
        short requiredFranchises = Short.parseShort(SettingsUtils
                .getSetting("requiredFranchises", "32"));

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT round, pick "
                + "FROM draft "
                + "WHERE season = " + season + " ORDER BY round DESC, pick DESC LIMIT 1";

        try {
            /**
             * Opening database connection
             */
            // // connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);

            /**
             * Checking there are draft records for the given season
             */
            if (!resultSet.next()) {
                draftRound = 1;
            } else {
                /**
                 * Retrieving values
                 */
                draftRound = resultSet.getShort("round");
                draftPick = resultSet.getShort("pick");

                /**
                 * Checking if the number of pick has reached the number of
                 * franchises
                 */
                if (draftPick == requiredFranchises) {
                    draftRound++;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return draftRound;
    }

    /**
     * Returns the number of the next draft pick
     *
     * @param season Season when the drafting operation occured
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getDraftPick(short season,
            DatabaseDirectConnection connection) {
        short draftPick = 1;
        short requiredFranchises = Short.parseShort(SettingsUtils
                .getSetting("requiredFranchises", "32"));

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT pick "
                + "FROM draft "
                + "WHERE season = " + season + " ORDER BY round DESC, pick DESC LIMIT 1";

        try {
            /**
             * Opening database connection
             */
            // // connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);

            /**
             * Checking there are draft records for the given season
             */
            if (!resultSet.next()) {
                draftPick = 1;
            } else {
                /**
                 * Retrieving values
                 */
                draftPick = (short) (resultSet.getShort("pick") + 1);

                /**
                 * Checking if the number of pick has reached the number of
                 * franchises
                 */
                if (draftPick > requiredFranchises) {
                    draftPick = 1;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return draftPick;
    }

    /**
     * Returns the number of total draft rounds that will be performed, in the
     * very first season this number is 10, afterwards, it's 2
     *
     * @return
     */
    public static short getTotalDraftRounds() {
        return CountingUtils.playedSeasonsCount() > 0
                ? Short.parseShort(SettingsUtils.getSetting("draftRounds", "2")) : 10;
    }

    /**
     * Returns the id of the next franchise to pick a player
     *
     * @param season Season when the drafting operation occured
     * @param draftRound Drafting round
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getNextFranchise(short season, short draftRound,
            DatabaseDirectConnection connection) {
        short nextFranchise = 1;

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT id FROM franchise "
                + "WHERE registered = TRUE AND id NOT IN "
                + "(SELECT franchise FROM draft WHERE season = 2013 AND round = "
                + draftRound + ") ORDER BY record, RAND() LIMIT 1";

        try {
            /**
             * Opening database connection
             */
            // // connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);

            /**
             * Checking there are draft records for the given season
             */
            if (!resultSet.next()) {
                nextFranchise = 0;
            } else {
                /**
                 * Retrieving franchise
                 */
                nextFranchise = resultSet.getShort("id");
            }

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return nextFranchise;
    }

    /**
     * Records a drafting operation into the database
     * 
     * @param draftOperation Data structure with information about the drafting
     * operation
     * @param connection Database connection used to retrieve data
     */
    public static void recordDraft(DraftSummary draftOperation,
            DatabaseDirectConnection connection) {
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        
        String sqlStatement;
        short franchiseId = draftOperation.getFranchiseId();
        int playerId = draftOperation.getPlayerId();
        int minimumSalary = Integer.parseInt(SettingsUtils.getSetting("minimumSalary",
                "1500000"));
        short draftYear = Short.parseShort(SettingsUtils.getSetting("currentSeason",
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));

        /**
         * Opening database connection
         */
        // // connection.open();

        /**
         * Registering player into the franchise
         */
        sqlStatement = "UPDATE player SET franchise = " + franchiseId + ", "
                + "jersey = " + FranchiseUtils.getAvailableJerseyNumber(franchiseId, connection) + ", "
                + "salary = " + minimumSalary + ", draftYear = " + draftYear + ", "
                + "remainingYears = 2, isActive = true WHERE id = " + playerId;
        connection.executeSQL(sqlStatement);

        /**
         * Registering draft operation
         */
        sqlStatement = "INSERT INTO draft (season, player, franchise, round, pick) "
                + "VALUES (" + draftYear + ", " + playerId + ", " + franchiseId + ", "
                + draftOperation.getDraftRound() + ", " + draftOperation.getDraftPick()
                + ")";
        connection.executeSQL(sqlStatement);       
    }
} // end DraftUtils
