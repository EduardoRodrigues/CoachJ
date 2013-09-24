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
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long createdFranchisesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "franchise", null, null, null, null,
                connection);
    }

    /**
     * Returns the number of franchises registered in the league
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long registeredFranchisesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "franchise", "registered = 1", null, null,
                null, connection);
    }

    /**
     * Returns the number of players created
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long createdPlayersCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "player", null, null, null, null, connection);
    }

    /**
     * Returns the number of players signed
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long signedPlayersCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "player", "franchise > 0", null, null,
                null, connection);
    }

    /**
     * Returns the number of free agents
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long freeAgentsCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "player", "franchise IS NULL AND retired = false",
                null, null, null, connection);
    }

    /**
     * Returns the number of draftees
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long drafteesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "player", "franchise IS NULL AND seasons = 0 AND retired = false",
                null, null, null, connection);
    }

    /**
     * Returns the number of retired players
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long retiredPlayersCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "player", "retired = true", null, null,
                null, connection);
    }

    /**
     * Returns the number of general managers created
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long createdGeneralManagersCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "general_manager", null, null, null, null, connection);
    }

    /**
     * Returns the number of general managers signed
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long signedGeneralManagersCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "franchise", "generalManager > 0", null,
                null, null, connection);
    }

    /**
     * Returns the number of retired general managers
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long retiredGeneralManagersCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "general_manager", "retired = true", null,
                null, null, connection);
    }

    /**
     * Returns the number of coaches created
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long createdCoachesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "coach", null, null, null, null, connection);

    }

    /**
     * Returns the number of coaches signed
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long signedCoachesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "franchise", "coach > 0", null, null, null,
                connection);
    }

    /**
     * Returns the number of retired coaches
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long retiredCoachesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "coach", "retired = true", null, null,
                null, connection);
    }

    /**
     * Returns the number of countries created
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long createdCountriesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "country", null, null, null, null, connection);
    }

    /**
     * Returns the number of cities created
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long createdCitiesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "city", null, null, null, null, connection);
    }

    /**
     * Returns the number of first names created
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long createdFirstNamesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "first_name", null, null, null, null, connection);
    }

    /**
     * Returns the number of last names created
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long createdLastNamesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "last_name", null, null, null, null, connection);
    }

    /**
     * Returns the number of arenas created
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long createdArenasCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "arena", null, null, null, null, connection);
    }

    /**
     * Returns the number of arenas registered in the league
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long registeredArenasCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "franchise", "arena > 0", null, null,
                null, connection);
    }

    /**
     * Returns the number of available players
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long availablePlayersCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "player", "retired = false", null, null,
                null, connection);
    }

    /**
     * Returns the number of available general managers
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long availableGeneralManagersCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "general_manager", "retired = false", null,
                null, null, connection);
    }

    /**
     * Returns the number of available coaches
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long availableCoachesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "coach", "retired = false", null, null,
                null, connection);
    }

    /**
     * Returns the number of available referees
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long availableRefereesCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "referee", "retired = false", null, null,
                null, connection);
    }

    /**
     * Returns the number of franchises without general manager
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long franchisesWithoutGeneralManagerCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "franchise", "generalManager IS NULL", null,
                null, null, connection);
    }

    /**
     * Returns the number of franchises without coach
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long franchisesWithoutCoachCount(DatabaseDirectConnection connection) {
        return countedFieldValue("id", "franchise", "coach IS NULL", null, null,
                null, connection);
    }

    /**
     * Returns the number of registered franchises that are controlled by the
     * computer
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long computerControlledFranchisesCount(DatabaseDirectConnection connection) {
        short userFranchiseId = Short.parseShort(SettingsUtils.getSetting("userFranchise",
                "0"));
        return countedFieldValue("id", "franchise", "id != " + userFranchiseId,
                null, null, null, connection);
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
     * @param connection Database connection used to retrieve data
     * @return
     */
    private static long countedFieldValue(String field, String table,
            String whereCondition, String havingCondition, String groupByClause,
            String orderByClause, DatabaseDirectConnection connection) {
        /**
         * Variables to store the number of records, the database connection and
         * resultset
         */
        long recordCount = 0;
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
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            recordCount = resultSet.getLong("value");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return recordCount;
    }

    /**
     * Returns the number of seasons played
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static long playedSeasonsCount(DatabaseDirectConnection connection) {
        return countedFieldValue("year", "season", "finished = true", null, null,
                null, connection);
    }
} // end class CountingUtils
