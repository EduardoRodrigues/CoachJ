package coachj.utils;

import coachj.lists.AppTables;
import coachj.dao.DatabaseDirectConnection;
import coachj.dao.JPAUtil;
import com.mysql.jdbc.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Utilities for MySQL databases
 *
 * @author Eduardo M. Rodrigues
 * @version 1.1
 * @date 30/07/2013
 */
public class MySqlUtils {

    /**
     * Executes a SQL statement and returns the number of records retrieved
     *
     * @param sql The SQL statement to be executed
     * @return Number of records retrieved
     */
    public static long recordCount(String sql) {
        /**
         * Creating the EntityManager, running the query and retrieving the
         * number of records found
         */
        long count = 0;
        EntityManager em = JPAUtil.getInstance().createEntityManager();
        Query query = em.createNativeQuery(sql);
        query.setMaxResults(1);

        System.out.print(sql + ": "); // delete

        try {
            count = (long) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error trying to retrieve number of records: "
                    + e.getMessage());
        } finally {
            em.close();
        }
        System.out.println(count); // delete
        return count;
    }

    /**
     * Executes a SQL statement and returns the value of a field as a String
     *
     * @param sql The SQL statement to be executed
     * @return The String value of the field
     */
    public static String getFieldValueAsString(String sql) {
        /**
         * Creating the EntityManager, running the query and retrieving the
         * value of the field
         */
        String fieldValue = null;
        EntityManager em = JPAUtil.getInstance().createEntityManager();
        Query query = em.createNativeQuery(sql);
        query.setMaxResults(1);
        System.out.println(sql); // delete
        try {
            fieldValue = query.getResultList().get(0).toString();
        } catch (Exception e) {
            System.out.println("Error trying to retrieve number of records: "
                    + e.getMessage());
        } finally {
            em.close();
        }

        return fieldValue;
    }

    /**
     * Verifies the MySQL connection
     *
     * @return <b>true</b> if connection is ok, <b>false</b> otherwise
     */
    public static boolean checkDatabaseConnection() {

        /**
         * Retrieving connection parameters
         */
        final String URL = SettingsUtils.getSetting("MySqlUrl", "jdbc:mysql://localhost/coach_j");
        final String USER = SettingsUtils.getSetting("MySqlUser", "root");
        final String PASSWORD = SettingsUtils.getSetting("MySqlUserPassword", "playmobil");

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection with database established"); // delete            
            return true;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(MySqlUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
    }

    /**
     * Verifies the database's tables
     *
     * @return <b>true</b> if tables are ok, <b>false</b> otherwise
     */
    public static boolean checkDatabaseTables() {

        /**
         * Retrieving the list of application's tables
         */        
        AppTables appTables = new AppTables();
        List<String> tableNames = appTables.getTableNames();
        int tableCount = 0; // auxiliary variable

        /**
         * Retrieving connection parameters
         */
        final String URL = SettingsUtils.getSetting("MySqlUrl", "jdbc:mysql://localhost/coach_j");
        final String USER = SettingsUtils.getSetting("MySqlUser", "root");
        final String PASSWORD = SettingsUtils.getSetting("MySqlUserPassword", "playmobil");

        /**
         * Connecting to the database, retrieving information about the tables
         * and comparing with the information from the AppTables class
         */
        try {      
            Connection connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet resultSet;
            for (int i = 0; i < tableNames.size(); i++) {
                resultSet = metadata.getTables(null, null, tableNames.get(i), null);

                if (resultSet.next()) {
                    tableCount++;
                }
            }
           
            return tableNames.size() == tableCount;

        } catch (SQLException ex) {
            Logger.getLogger(MySqlUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Verifies data integrity within tables
     *
     * @return <b>true</b> if data are ok, <b>false</b> otherwise
     */
    public static boolean checkDatabaseIntegrity() {

        /**
         * Variables that store the list of application's tables, the database
         * connection, the resultset, the sql statement and the table's status
         */
        AppTables appTables = new AppTables();
        List<String> tableNames = appTables.getTableNames();
        DatabaseDirectConnection connection = new DatabaseDirectConnection();
        ResultSet resultSet;
        String sqlStatement;
        String tableStatus;

        /**
         * Opening database connection and checking tables
         */
        connection.open();

        for (int i = 0; i < tableNames.size(); i++) {

            try {
                sqlStatement = "CHECK TABLE " + tableNames.get(i) + " EXTENDED";
                resultSet = connection.getResultSet(sqlStatement);
                resultSet.first();
                tableStatus = resultSet.getString("Msg_text");                

                if (!tableStatus.equalsIgnoreCase("OK")) {
                    return false;
                }

            } catch (SQLException ex) {
                Logger.getLogger(MySqlUtils.class
                        .getName()).log(Level.SEVERE, null, ex);

                return false;
            } 
        }

        return true;
    }
}
