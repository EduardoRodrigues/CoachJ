package coachj.builders;

import coachj.models.Country;
import coachj.models.Referee;
import java.util.Random;

/**
 * Generates new referees
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 30/07/2013
 */
public class RefereeBuilder {

    /* fields */
    private final int countryId;
    private final String firstName;
    private final String lastName;
    private short age;
    private short retirementAge;
    private boolean retired;
    private short shootingFouls;
    private short chargingFouls;
    private short reachingFouls;
    private short technicalFouls;
    private short blockingFouls;

    /**
     * Constructor
     *
     * @param countryId Country's id
     * @param firstName Referee's first name
     * @param lastName Referee's last name
     */
    public RefereeBuilder(int countryId, String firstName, String lastName) {
        this.countryId = countryId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Sets referee's attributes
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
        this.shootingFouls = (short) (generator.nextDouble() * 10 + 75);
        this.chargingFouls = (short) (generator.nextDouble() * 10 + 75);
        this.reachingFouls = (short) (generator.nextDouble() * 10 + 75);
        this.technicalFouls = (short) (generator.nextDouble() * 10 + 75);
        this.blockingFouls = (short) (generator.nextDouble() * 10 + 75);

        /*
         * Setting default values
         */
        this.retired = false;
    }
    
    /**
     * Creates and returns a General Manager entity.
     *
     * @return
     */
    public Referee generateRefereeEntity() {

        /**
         * Creating objects that are required to complete the operation
         */
        Referee referee = new Referee();
        Country country = new Country((short) this.countryId);

        /**
         * Setting attributes
         */
        referee.setCountry(country);
        referee.setFirstName(this.firstName);
        referee.setLastName(this.lastName);
        referee.setAge(this.age);
        referee.setRetirementAge(this.retirementAge);
        referee.setRetired(this.retired);
        referee.setShootingFouls(shootingFouls);
        referee.setChargingFouls(chargingFouls);
        referee.setReachingFouls(reachingFouls);
        referee.setTechnicalFouls(technicalFouls);
        referee.setBlockingFouls(blockingFouls);
     
        return referee;
    }
    
    /**
     * Creates and returns the MySQL statement that inserts the referee into the
     * database
     *
     * @return
     */
    public String generateInsertSQL() {
        String sqlInsertStatement = "INSERT INTO referee (country, firstName, lastName, "
                + "age, retirementAge, retired, shootingFouls, chargingFouls, reachingFouls, "
                + "technicalFouls, blockingFouls) VALUES (" + this.countryId + ", '" + this.firstName + "', '"
                + this.lastName + "', " + this.age + ", " + this.retirementAge + ", "
                + this.retired + ", " + this.shootingFouls + ", " + this.chargingFouls  + ", " 
                + this.reachingFouls + ", " + this.technicalFouls + ", " + this.blockingFouls + ")";

        return sqlInsertStatement;
    }
} // end class RefereeBuilder
