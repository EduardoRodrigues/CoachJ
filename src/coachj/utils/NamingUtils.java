package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for naming data that aims to decrease the number of repeated names
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 26/07/2013
 */
public class NamingUtils {

    /**
     * Retrieves the id of the country with lowest number of last names in the referenced table
     *
     * @param referencedTable Table to look for the country
     * @param connection Connection used to reach database
     * @return
     */
    public static String getRandomCountry(String referencedTable,
            DatabaseDirectConnection connection) {
        /*
         * Variables that retrieve the resultset store sql statements, the 
         * country retrieved from the table and the country with the biggest 
         * number of names already inserted into the referenced table
         */
        ResultSet resultSet;
        String sqlStatement;
        String country = null;
        String excludedCountry = getCountryWithMostLastNames(referencedTable, connection);

        /**
         * Retrieving countrythat does not appear in the referenced table
         */
        try {
            sqlStatement = "SELECT country "
                    + "FROM last_name WHERE "
                    + "country IN (SELECT country FROM last_name) AND "
                    + "country IN (SELECT country FROM first_name) AND "
                    + "country != " + excludedCountry
                    + " ORDER BY RAND() LIMIT 1;";
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            country = resultSet.getString("country");

            /**
             * If the referenced table is empty, randomly pick a last name
             */
            if (country == null) {
                sqlStatement = "SELECT country FROM last_name"
                        + " GROUP BY country ORDER BY COUNT(country), RAND() LIMIT 1";

                resultSet = connection.getResultSet(sqlStatement);
                resultSet.first();
                country = resultSet.getString("country");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NamingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return country;
    }

    /**
     * Retrieves a random last name with the lowest occurrence rate in the given table
     *
     * @param referencedTable Table with occurrences
     * @param connection Connection used to reach database
     * @return
     */
    public static String getRandomLastName(String referencedTable,
            DatabaseDirectConnection connection) {
        /*
         * Variables that retrieve the resultset, store sql statements and the 
         * last name retrieved from the table
         */
        ResultSet resultSet;
        String sqlStatement;
        String lastName = null;

        /**
         * Retrieving last name that does not appear in the referenced table
         */
        try {
            sqlStatement = "SELECT name FROM last_name"
                    + " WHERE name NOT IN (SELECT lastName FROM " + referencedTable + ") "
                    + " AND country IN (SELECT country FROM first_name) "
                    + " ORDER BY RAND() LIMIT 1";
            resultSet = connection.getResultSet(sqlStatement);
            //resultSet.first();
            //lastName = resultSet.getString("name");

            /**
             * If all last names already have been used, pick a random one
             */
            if (resultSet.first()) {
                lastName = resultSet.getString("name");
            } else {
                //if (lastName.equalsIgnoreCase("null")) {
                sqlStatement = "SELECT name FROM last_name"
                        + " WHERE country != " + getCountryWithMostLastNames(referencedTable,
                        connection)
                        + " ORDER BY RAND() LIMIT 1";
                resultSet = connection.getResultSet(sqlStatement);
                resultSet.first();
                lastName = resultSet.getString("name");
            }

        } catch (SQLException ex) {
            Logger.getLogger(NamingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lastName;
    }

    /**
     * Retrieves a random first name from the same country of the given last name, trying not to
     * create an already taken one
     *
     * @param country Country of the Last name given
     * @param connection Connection used to reach database
     * @return
     */
    public static String getRandomFirstName(String country,
            DatabaseDirectConnection connection) {
        /*
         * Variables that retrieve the resultset, store sql statements and 
         * the first name retrieved from the table
         */
        ResultSet resultSet;
        String sqlStatement;
        String firstName = null;

        /**
         * retrieving first name from the given country
         */
        try {
            sqlStatement = "SELECT name FROM first_name"
                    + " WHERE country = " + country + " ORDER BY RAND() LIMIT 1";

            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            firstName = resultSet.getString("name");

        } catch (SQLException ex) {
            Logger.getLogger(NamingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return firstName;
    }

    /**
     * Since MySQL currently does not support the LIMIT clause in subqueries, this method was
     * created to query a table and return the id of the country with more last names within it.
     *
     * @param referencedTable Table to look for the country
     * @param connection Connection used to reach database
     * @return
     */
    public static String getCountryWithMostLastNames(String referencedTable,
            DatabaseDirectConnection connection) {
        /*
         * Variables that retrieve the resultset, store sql statements and 
         * the country retrieved from the table
         */
        ResultSet resultSet;
        String sqlStatement;
        String country = null;

        /**
         * retrieving the country
         */
        try {
            sqlStatement = "SELECT country FROM "
                    + referencedTable + " ORDER BY COUNT(country) DESC, RAND() LIMIT 1";
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            country = resultSet.getString("country");

        } catch (SQLException ex) {
            Logger.getLogger(NamingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return country;
    }

    /**
     * Returns the country related to a given last name
     *
     * @param lastName The name to look into the table
     * @param connection Connection used to reach database
     * @return
     */
    public static String getLastNameCountry(String lastName,
            DatabaseDirectConnection connection) {
        /*
         * Variables that retrieve the resultset, store sql statements 
         * and the country retrieved from the table
         */
        ResultSet resultSet;
        String sqlStatement;
        String country = null;

        /**
         * retrieving the country
         */
        try {
            sqlStatement = "SELECT country FROM last_name "
                    + " WHERE name = '" + lastName + "' LIMIT 1";

            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            country = resultSet.getString("country");

        } catch (SQLException ex) {
            Logger.getLogger(NamingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return country;
    }
} // end class NamingUtils
