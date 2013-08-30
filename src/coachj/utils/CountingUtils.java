package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides several counting stats
 *
 * @author Eduardo M. Rodrigues
 * @version 1.1
 * @date 25/07/2013
 */
public class CountingUtils {

    /**
     * Returns the number of franchises created
     *
     * @return
     */
    public static long createdFranchisesCount() {
        return countedFieldValue("id", "franchise", null, null, null, null);
    }

    /**
     * Returns the number of franchises registered in the league
     *
     * @return
     */
    public static long registeredFranchisesCount() {
        return countedFieldValue("id", "franchise", "registered = 1", null, null, null);
    }

    /**
     * Returns the number of players created
     *
     * @return
     */
    public static long createdPlayersCount() {
        return countedFieldValue("id", "player", null, null, null, null);
    }

    /**
     * Returns the number of players signed
     *
     * @return
     */
    public static long signedPlayersCount() {
        return countedFieldValue("id", "player", "franchise > 0", null, null, null);
    }

    /**
     * Returns the number of free agents
     *
     * @return
     */
    public static long freeAgentsCount() {
        return countedFieldValue("id", "player", "franchise IS NULL AND retired = false",
                null, null, null);
    }

    /**
     * Returns the number of draftees
     *
     * @return
     */
    public static long drafteesCount() {
        return countedFieldValue("id", "player", "franchise IS NULL AND seasons = 0 AND retired = false",
                null, null, null);
    }

    /**
     * Returns the number of retired players
     *
     * @return
     */
    public static long retiredPlayersCount() {
        return countedFieldValue("id", "player", "retired = true", null, null, null);
    }

    /**
     * Returns the number of general managers created
     *
     * @return
     */
    public static long createdGeneralManagersCount() {
        return countedFieldValue("id", "general_manager", null, null, null, null);
    }

    /**
     * Returns the number of general managers signed
     *
     * @return
     */
    public static long signedGeneralManagersCount() {
        return countedFieldValue("id", "franchise", "generalManager > 0", null,
                null, null);
    }

    /**
     * Returns the number of retired general managers
     *
     * @return
     */
    public static long retiredGeneralManagersCount() {
        return countedFieldValue("id", "general_manager", "retired = true", null,
                null, null);
    }

    /**
     * Returns the number of coaches created
     *
     * @return
     */
    public static long createdCoachesCount() {
        return countedFieldValue("id", "coach", null, null, null, null);

    }

    /**
     * Returns the number of coaches signed
     *
     * @return
     */
    public static long signedCoachesCount() {
        return countedFieldValue("id", "franchise", "coach > 0", null, null, null);
    }

    /**
     * Returns the number of retired coaches
     *
     * @return
     */
    public static long retiredCoachesCount() {
        return countedFieldValue("id", "coach", "retired = true", null, null, null);
    }

    /**
     * Returns the number of countries created
     *
     * @return
     */
    public static long createdCountriesCount() {
        return countedFieldValue("id", "country", null, null, null, null);
    }

    /**
     * Returns the number of cities created
     *
     * @return
     */
    public static long createdCitiesCount() {
        return countedFieldValue("id", "city", null, null, null, null);
    }

    /**
     * Returns the number of first names created
     *
     * @return
     */
    public static long createdFirstNamesCount() {
        return countedFieldValue("id", "first_name", null, null, null, null);
    }

    /**
     * Returns the number of last names created
     *
     * @return
     */
    public static long createdLastNamesCount() {
        return countedFieldValue("id", "last_name", null, null, null, null);
    }

    /**
     * Returns the number of arenas created
     *
     * @return
     */
    public static long createdArenasCount() {
        return countedFieldValue("id", "arena", null, null, null, null);
    }

    /**
     * Returns the number of arenas registered in the league
     *
     * @return
     */
    public static long registeredArenasCount() {
        return countedFieldValue("id", "franchise", "arena > 0", null, null, null);
    }

    /**
     * Returns the number of available players
     *
     * @return
     */
    public static long availablePlayersCount() {
        return countedFieldValue("id", "player", "retired = false", null, null, null);
    }

    /**
     * Returns the number of available general managers
     *
     * @return
     */
    public static long availableGeneralManagersCount() {
        return countedFieldValue("id", "general_manager", "retired = false", null, null, null);
    }

    /**
     * Returns the number of available coaches
     *
     * @return
     */
    public static long availableCoachesCount() {
        return countedFieldValue("id", "coach", "retired = false", null, null, null);
    }

    /**
     * Returns the number of available referees
     *
     * @return
     */
    public static long availableRefereesCount() {
        return countedFieldValue("id", "referee", "retired = false", null, null, null);
    }

    /**
     * Returns the number of franchises without general manager
     *
     * @return
     */
    public static long franchisesWithoutGeneralManagerCount() {
        return countedFieldValue("id", "franchise", "generalManager IS NULL", null, null, null);
    }

    /**
     * Returns the number of franchises without coach
     *
     * @return
     */
    public static long franchisesWithoutCoachCount() {
        return countedFieldValue("id", "franchise", "coach IS NULL", null, null, null);
    }

    /**
     * Returns the number of registered franchises that are controlled by the
     * computer
     *
     * @return
     */
    public static long computerControlledFranchisesCount() {
        short userFranchiseId = Short.parseShort(SettingsUtils.getSetting("userFranchise", "0"));
        return countedFieldValue("id", "franchise", "id != " + userFranchiseId, null, null, null);
    }

    /**
     * Returns the value of a counted field from a sql statement
     *
     * @param field Field to be counted
     * @param table Table to host the statement
     * @param whereCondition SQL WHERE filter to be applied to the records
     * @param havingCondition SQL HAVING filter to be applied to the records
     * @param groupByClause SQL GROUP BY clause
     * @param orderByClause SQL ORDER BY clause
     * @return
     */
    private static long countedFieldValue(String field, String table,
            String whereCondition, String havingCondition, String groupByClause,
            String orderByClause) {
        /**
         * Variables to store the number of records, the database connection and
         * resultset
         */
        long recordCount = 0;
        DatabaseDirectConnection connection = new DatabaseDirectConnection();
        String sqlStatement;
        ResultSet resultSet;

        /**
         * Building sql statement
         */
        sqlStatement = "SELECT COUNT(" + field + ") AS value FROM " + table;

        if (whereCondition != null) {
            sqlStatement += " WHERE " + whereCondition;
        }
        if (havingCondition != null) {
            sqlStatement += " HAVING " + havingCondition;
        }
        if (groupByClause != null) {
            sqlStatement += " GROUP BY " + groupByClause;
        }
        if (orderByClause != null) {
            sqlStatement += " ORDER BY " + orderByClause;
        }

        try {
            /**
             * Opening database connection
             */
            // connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            recordCount = resultSet.getLong("value");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return recordCount;
    }
    
    /**
     * Returns the number of seasons played
     *
     * @return
     */
    public static long playedSeasonsCount() {
        return countedFieldValue("year", "season", "finished = true", null, null, null);
    }
} // end class CountingUtils
