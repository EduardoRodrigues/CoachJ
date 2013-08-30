package coachj.builders;

import coachj.dao.DatabaseDirectConnection;
import coachj.models.Country;
import coachj.models.GeneralManager;
import coachj.utils.CountingUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates new general managers
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 29/07/2013
 */
public class GeneralManagerBuilder {

    /* fields */
    private short id;
    private int countryId;
    private String firstName;
    private String lastName;
    private short age;
    private short retirementAge;
    private boolean retired;
    private short maximumBadSeasons;
    private short dealingStrategy;
    private short greed;
    private short seasons;
    private short remainingYears;
    private int salary;
    private int totalEarnings;
    private short marketValue;
    private short starPoints;
    private short titles;
    private short jobStabilityIndex;
    private short failedContractAttempts;

    /**
     * Constructor
     *
     * @param countryId Country's id
     * @param firstName General manager's first name
     * @param lastName General manager's last name
     */
    public GeneralManagerBuilder(int countryId, String firstName, String lastName) {
        this.countryId = countryId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Generic constructor
     */
    public GeneralManagerBuilder() {
    }

    /**
     * Sets general manager's attributes
     */
    public void setAttributes() {

        /**
         * Generates random numbers
         */
        Random generator = new Random();

        /**
         * Setting randomized attributes
         */
        this.age = (short) (generator.nextDouble() * 15 + 40);
        this.retirementAge = (short) (generator.nextDouble() * 10 + 65);
        this.greed = (short) (generator.nextDouble() * 30);
        this.maximumBadSeasons = (short) (generator.nextDouble() * 3 + 2);

        /**
         * Dealing strategies <br />
         * 0 - if more teams are interest in the player/coach, the manager will
         * only match the player/coach's proposal<br />
         * 1 - if more teams are interest in the player/coach, the manager will
         * offer 10% above the player/coach's proposal<br />
         * 2 - if more teams are interest in the player/coach, the manager will
         * offer 20% above the player/coach's proposal<br />
         * 3 - if more teams are interest in the player/coach, the manager will
         * offer 30% above the player/coach's proposal<br />
         *
         */
        this.dealingStrategy = (short) generator.nextInt(4);

        /*
         * Setting default values
         */
        this.retired = false;
        this.seasons = (short) 0;
        this.totalEarnings = 0;
        this.starPoints = (short) 0;
        this.jobStabilityIndex = (short) 50;
        this.titles = (short) 0;
        this.failedContractAttempts = (short) 0;

        /**
         * All general managers are created with the same market value
         */
        this.marketValue = (short) 500;

        /**
         * calculating salary based on coach's market value
         */
        this.salary = marketValue * 4000;
    }

    /**
     * Fills the attributes from data retrieved from the database
     *
     * @param generalManagerId General manager's id
     * @param connection Database connection used to retrieve data
     */
    public void fillAttributesFromDatabase(short generalManagerId,
            DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset and store 
         * the sql statement
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }

        ResultSet resultSet;
        String sqlStatement = "SELECT * FROM general_manager "
                + "WHERE id = " + generalManagerId;

        try {
            /**
             * Opening database connection
             */
            // // connection.open();

            /**
             * Executing query, retrieving result and setting attributes
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();

            this.id = generalManagerId;
            this.firstName = resultSet.getString("firstName");
            this.lastName = resultSet.getString("lastName");
            this.age = resultSet.getShort("age");
            this.retirementAge = resultSet.getShort("retirementAge");
            this.greed = resultSet.getShort("greed");
            this.retired = resultSet.getBoolean("retired");
            this.seasons = resultSet.getShort("seasons");
            this.remainingYears = resultSet.getShort("remainingYears");
            this.totalEarnings = resultSet.getInt("totalEarnings");
            this.starPoints = resultSet.getShort("starPoints");
            this.jobStabilityIndex = resultSet.getShort("jobStabilityIndex");
            this.titles = resultSet.getShort("titles");
            this.marketValue = resultSet.getShort("marketValue");
            this.salary = resultSet.getInt("salary");
            this.failedContractAttempts = resultSet.getShort("failedContractAttempts");
            this.maximumBadSeasons = resultSet.getShort("maximumBadSeasons");
            this.dealingStrategy = resultSet.getShort("dealingStrategy");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
    }

    /**
     * Creates and returns a General Manager entity.
     *
     * @return
     */
    public GeneralManager generateGeneralManagerEntity() {

        /**
         * Creating objects that are required to complete the operation
         */
        GeneralManager generalManager = new GeneralManager();
        Country country = new Country((short) this.countryId);

        /**
         * Setting attributes
         */
        generalManager.setId(this.id);
        generalManager.setCountry(country);
        generalManager.setFirstName(this.firstName);
        generalManager.setLastName(this.lastName);
        generalManager.setAge(this.age);
        generalManager.setRetirementAge(this.retirementAge);
        generalManager.setGreed(this.greed);
        generalManager.setRetired(this.retired);
        generalManager.setSeasons(this.seasons);
        generalManager.setTotalEarnings(this.totalEarnings);
        generalManager.setStarPoints(this.starPoints);
        generalManager.setJobStabilityIndex(this.jobStabilityIndex);
        generalManager.setTitles(this.titles);
        generalManager.setMarketValue(this.marketValue);
        generalManager.setSalary(this.salary);
        generalManager.setDealingStrategy(this.dealingStrategy);
        generalManager.setFailedContractAttempts(this.failedContractAttempts);
        generalManager.setRemainingYears(this.remainingYears);

        return generalManager;
    }

    /**
     * Creates and returns the MySQL statement that inserts the general manager
     * into the database
     *
     * @return
     */
    public String generateInsertSQL() {
        String sqlInsertStatement = "INSERT INTO general_manager (country, firstName, lastName, "
                + "age, retirementAge, retired, maximumBadSeasons, dealingStrategy, greed, "
                + "seasons, salary, totalEarnings, marketValue, starPoints, jobStabilityIndex,"
                + "titles, failedContractAttempts, remainingYears) VALUES (" + this.countryId
                + ", '" + this.firstName + "', '" + this.lastName + "', " + this.age
                + ", " + this.retirementAge + ", " + this.retired + ", " + this.maximumBadSeasons
                + ", " + this.dealingStrategy + ", " + this.greed + ", " + this.seasons
                + ", " + this.salary + ", " + this.totalEarnings + ", " + this.marketValue
                + ", " + this.starPoints + ", " + this.jobStabilityIndex + ", " + this.titles
                + ", " + this.failedContractAttempts + ", " + this.remainingYears + ")";

        return sqlInsertStatement;
    }
} // end class GeneralManagerBuilder