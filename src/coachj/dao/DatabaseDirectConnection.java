package coachj.dao;

import coachj.utils.SettingsUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Performs database operations without using the JPA
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 30/07/2013
 */
public class DatabaseDirectConnection {

    /*
     * Connection's URL
     */
    private String URL = null;
    /*
     * MySQL user
     */
    private String USER = null;
    /*
     * User's password
     */
    private String PASSWORD = null;
    /*
     * Controls connection to the database
     */
    private Connection connection;
    /*
     * Executes SQL statements
     */
    private Statement sqlStatement;

    /**
     * Constructor
     */
    public DatabaseDirectConnection() {
        this.URL = SettingsUtils.getSetting("MySqlUrl", "jdbc:mysql://localhost/coach_j");
        this.USER = SettingsUtils.getSetting("MySqlUser", "root");
        this.PASSWORD = SettingsUtils.getSetting("MySqlUserPassword", "playmobil");
    }

    /**
     * Connects to the database
     *
     * @return
     */
    public Connection open() {

        /**
         * Trying to connect to the database
         */
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return this.connection;

        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(DatabaseDirectConnection.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    /**
     * Closes database connection
     */
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            Logger.getLogger(DatabaseDirectConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Executes SQL statements that do not return resultsets
     *
     * @param sqlStatement SQL statement to be executed
     */
    public void executeSQL(String sqlStatement) {
        /**
         * Opens connection and executes statement
         */
        
        System.out.println("Execute SQL:" + sqlStatement); // delete     
        try {
            if (connection.isClosed()) {
                this.connection = open();
            }
            this.sqlStatement = connection.createStatement();
            this.sqlStatement.executeUpdate(sqlStatement);

        } catch (SQLException e) {
            Logger.getLogger(DatabaseDirectConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Executes SQL statements that return resultsets
     *
     * @param sqlStatement SQL statement to be executed
     * @return
     */
    public ResultSet getResultSet(String sqlStatement) {
        /**
         * Opens connection and executes statement
         */
        ResultSet resultSet = null;
        System.out.println("Get ResultSet:" + sqlStatement); // delete      
        try {
            if (connection.isClosed()) {
                this.connection = open();
            }

            this.sqlStatement = this.connection.createStatement();
            resultSet = this.sqlStatement.executeQuery(sqlStatement);

        } catch (SQLException e) {
            Logger.getLogger(DatabaseDirectConnection.class.getName()).log(Level.SEVERE, null, e);
        }

        return resultSet;
    }
} // end DatabaseDirectConnection class
