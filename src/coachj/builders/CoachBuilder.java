package coachj.builders;

import coachj.dao.DatabaseDirectConnection;
import coachj.models.Coach;
import coachj.models.Country;
import coachj.utils.CountingUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates new coaches by creating Coach entity or MySQL statement
 *
 * @author Eduardo M. Rodrigues
 * @version 1.1
 * @date 30/07/2013
 */
public class CoachBuilder {

    /* fields */
    private short id;
    private int countryId;
    private String firstName;
    private String lastName;
    private short age;
    private short retirementAge;
    private short greed;
    private short loyalty;
    private short rotationUse;
    private short draftMethod;
    private short patience;
    private short technique;
    private short discipline;
    private short workEthic;
    private short shoot;
    private short pass;
    private short rebound;
    private short defense;
    private short physicality;
    private short tempo;
    private boolean retired;
    private short seasons;
    private short remainingYears;
    private int totalEarnings;
    private short starPoints;
    private short jobStabilityIndex;
    private short titles;
    private short marketValue;
    private int salary;
    private short failedContractAttempts;

    /**
     * Constructors
     *
     * @param countryId Country's id
     * @param firstName Coach's first name
     * @param lastName Coach's last name
     */
    public CoachBuilder(int countryId, String firstName, String lastName) {
        this.countryId = countryId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Generic constructor
     */
    public CoachBuilder() {
    }

    /**
     * Sets coach's attributes
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
        this.loyalty = (short) (generator.nextDouble() * 80);
        this.rotationUse = (short) (generator.nextDouble() * 20 + 60);

        /**
         * Drafting methods <br />
         * 0 - player with greatest market value, any position <br />
         * 1 - player with greatest rate in coach's favorite attribute, any
         * position <br />
         * 2 - player with greatest market value, worst starter position <br />
         * 3 - player with greatest rate in coach's favorite attribute, worst
         * starter position <br />
         * 4 - player with greatest market value, worst reserve position <br />
         * 5 - player with greatest rate in coach's favorite attribute, worst
         * reserve position
         */
        this.draftMethod = (short) generator.nextInt(6);
        this.patience = (short) (generator.nextDouble() * 20 + 60);
        this.technique = (short) (generator.nextDouble() * 20 + 60);
        this.discipline = (short) (generator.nextDouble() * 20 + 60);
        this.workEthic = (short) (generator.nextDouble() * 20 + 60);
        this.shoot = (short) (generator.nextDouble() * 20 + 60);
        this.pass = (short) (generator.nextDouble() * 20 + 60);
        this.rebound = (short) (generator.nextDouble() * 20 + 60);
        this.defense = (short) (generator.nextDouble() * 20 + 60);
        this.physicality = (short) (generator.nextDouble() * 20 + 60);
        this.tempo = (short) (generator.nextDouble() * 20 + 60);

        /*
         * Setting default values
         */
        this.retired = false;
        this.seasons = (short) 0;
        this.remainingYears = (short) 0;
        this.totalEarnings = 0;
        this.starPoints = (short) 0;
        this.jobStabilityIndex = (short) 50;
        this.titles = (short) 0;

        /**
         * calculating market value based on coach's attributes
         */
        this.marketValue = (short) (technique + discipline + workEthic + shoot
                + pass + rebound + defense + physicality + tempo);

        /**
         * calculating salary based on coach's market value
         */
        this.salary = marketValue * 4000;

    }

    /**
     * Fills the attributes from data retrieved from the database
     *
     * @param coachId Coach's id
     * @param connection Database connection used to retrieve data
     */
    public void fillAttributesFromDatabase(short coachId,
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
        String sqlStatement = "SELECT * FROM coach "
                + "WHERE id = " + coachId;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and setting attributes
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();

            this.id = coachId;
            this.firstName = resultSet.getString("firstName");
            this.lastName = resultSet.getString("lastName");
            this.age = resultSet.getShort("age");
            this.retirementAge = resultSet.getShort("retirementAge");
            this.greed = resultSet.getShort("greed");
            this.loyalty = resultSet.getShort("loyalty");
            this.rotationUse = resultSet.getShort("rotationUse");
            this.draftMethod = resultSet.getShort("draftMethod");
            this.patience = resultSet.getShort("patience");
            this.technique = resultSet.getShort("technique");
            this.discipline = resultSet.getShort("discipline");
            this.workEthic = resultSet.getShort("workEthic");
            this.shoot = resultSet.getShort("shoot");
            this.pass = resultSet.getShort("pass");
            this.rebound = resultSet.getShort("rebound");
            this.defense = resultSet.getShort("defense");
            this.physicality = resultSet.getShort("physicality");
            this.tempo = resultSet.getShort("tempo");
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

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
    }

    /**
     * Creates and returns a Coach entity.
     *
     * @return
     */
    public Coach generateCoachEntity() {

        /**
         * Creating objects that are required to complete the operation
         */
        Coach coach = new Coach();
        Country country = new Country((short) this.countryId);

        /**
         * Setting attributes
         */
        coach.setId(this.id);
        coach.setCountry(country);
        coach.setFirstName(this.firstName);
        coach.setLastName(this.lastName);
        coach.setAge(this.age);
        coach.setRetirementAge(this.retirementAge);
        coach.setGreed(this.greed);
        coach.setLoyalty(this.loyalty);
        coach.setRotationUse(this.rotationUse);
        coach.setDraftMethod(this.draftMethod);
        coach.setPatience(this.patience);
        coach.setTechnique(this.technique);
        coach.setDiscipline(this.discipline);
        coach.setWorkEthic(this.workEthic);
        coach.setShoot(this.shoot);
        coach.setPass(this.pass);
        coach.setRebound(this.rebound);
        coach.setDefense(this.defense);
        coach.setPhysicality(this.physicality);
        coach.setTempo(this.tempo);
        coach.setRetired(this.retired);
        coach.setSeasons(this.seasons);
        coach.setRemainingYears(this.remainingYears);
        coach.setTotalEarnings(this.totalEarnings);
        coach.setStarPoints(this.starPoints);
        coach.setJobStabilityIndex(this.jobStabilityIndex);
        coach.setTitles(this.titles);
        coach.setMarketValue(this.marketValue);
        coach.setSalary(this.salary);
        coach.setFailedContractAttempts(failedContractAttempts);

        return coach;
    }

    /**
     * Creates and returns the MySQL statement that inserts the coach into the
     * database
     *
     * @return
     */
    public String generateInsertSQL() {
        String sqlInsertStatement = "INSERT INTO coach (country, firstName, lastName, "
                + "age, retired, seasons, retirementAge, remainingYears, greed, "
                + "loyalty, salary, totalEarnings, marketValue, starPoints, rotationUse,"
                + "draftMethod, patience, technique, discipline, workEthic, shoot, "
                + "pass, rebound, defense, physicality, tempo, jobStabilityIndex,"
                + "titles) VALUES (" + this.countryId + ", '" + this.firstName + "', '"
                + this.lastName + "', " + this.age + ", " + this.retired + ", "
                + this.seasons + ", " + this.retirementAge + ", " + this.remainingYears + ", "
                + this.greed + ", " + this.loyalty + ", " + this.salary + ", "
                + this.totalEarnings + ", " + this.marketValue + ", " + this.starPoints + ", "
                + this.rotationUse + ", " + this.draftMethod + ", " + this.patience + ", "
                + this.technique + ", " + this.discipline + ", " + this.workEthic + ", "
                + this.shoot + ", " + this.pass + ", " + this.rebound + ", " + this.defense + ", "
                + this.physicality + ", " + this.tempo + ", " + this.jobStabilityIndex + ", "
                + this.titles + ")";

        return sqlInsertStatement;
    }
} // end class CoachBuilder
