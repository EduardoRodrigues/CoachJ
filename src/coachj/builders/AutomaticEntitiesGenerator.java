package coachj.builders;

import coachj.dao.DatabaseDirectConnection;
import coachj.utils.NamingUtils;
import coachj.utils.PositionUtils;

/**
 * Utility class that automatic generates entities
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 25/07/2013
 */
public class AutomaticEntitiesGenerator {

    /**
     * Generates n Coach entities.
     *
     * @param quantity Number of coaches to be generated
     */
    public static void generateCoaches(int quantity,
            DatabaseDirectConnection connection) {
        /**
         * Variables to store country, first name, last name and database
         * connection
         */
        String country;
        String firstName;
        String lastName;
        
        for (int i = 0; i < quantity; i++) {
            lastName = NamingUtils.getRandomLastName("coach", connection);
            country = NamingUtils.getLastNameCountry(lastName, connection);
            firstName = NamingUtils.getRandomFirstName(country, connection);
            System.out.println("Generating coach: " + i);

            CoachBuilder coachBuilder = new CoachBuilder(Integer.parseInt(country),
                    firstName, lastName);
            coachBuilder.setAttributes();
            String coachInsertSQL = coachBuilder.generateInsertSQL();
            connection.executeSQL(coachInsertSQL);
        }
    }

    /**
     * Generates n GeneralManager entities.
     *
     * @param quantity Number of general managers to be generated
     */
    public static void generateGeneralManagers(int quantity,
            DatabaseDirectConnection connection) {
        /**
         * Variables to store country, first name, last name and database
         * connection
         */
        String country;
        String firstName;
        String lastName;

        for (int i = 0; i < quantity; i++) {
            lastName = NamingUtils.getRandomLastName("general_manager", connection);
            country = NamingUtils.getLastNameCountry(lastName, connection);
            firstName = NamingUtils.getRandomFirstName(country, connection);
            System.out.println("Generating manager: " + i); // delete

            GeneralManagerBuilder generalManagerBuilder = new GeneralManagerBuilder(
                    Integer.parseInt(country), firstName, lastName);
            generalManagerBuilder.setAttributes();
            String generalManagerInsertSQL = generalManagerBuilder.generateInsertSQL();
            connection.executeSQL(generalManagerInsertSQL);
        }
    }

    /**
     * Generates n Referee entities.
     *
     * @param quantity Number of referees to be generated
     */
    public static void generateReferees(int quantity,
            DatabaseDirectConnection connection) {
        /**
         * Variables to store country, first name, last name and database
         * connection
         */
        String country;
        String firstName;
        String lastName;        

        for (int i = 0; i < quantity; i++) {
            lastName = NamingUtils.getRandomLastName("referee", connection);
            country = NamingUtils.getLastNameCountry(lastName, connection);
            firstName = NamingUtils.getRandomFirstName(country, connection);
            System.out.println("Generating referee: " + i); // delete

            RefereeBuilder refereeBuilder = new RefereeBuilder(Integer.parseInt(country),
                    firstName, lastName);
            refereeBuilder.setAttributes();
            String refereeInsertSQL = refereeBuilder.generateInsertSQL();
            connection.executeSQL(refereeInsertSQL);
        }
    }

    /**
     * Generates n Player entities.
     *
     * @param quantity Number of players to be generated
     */
    public static void generatePlayers(int quantity,
            DatabaseDirectConnection connection) {
        /**
         * Variables to store country, first name, last name and database
         * connection
         */
        String country;
        String firstName;
        String lastName;
        String position;
        
        for (int i = 0; i < quantity; i++) {
            lastName = NamingUtils.getRandomLastName("player", connection);
            country = NamingUtils.getLastNameCountry(lastName, connection);
            firstName = NamingUtils.getRandomFirstName(country, connection);
            position = PositionUtils.getRandomPosition();
            System.out.println("Generating player: " + i); // delete

            PlayerBuilder playerBuilder = new PlayerBuilder(Integer.parseInt(country),
                    firstName, lastName, position);
            playerBuilder.setAttributes(connection);
            String playerInsertSQL = playerBuilder.generateInsertSQL();
            connection.executeSQL(playerInsertSQL);
        }
    }
} // end class AutomaticEntitiesGenerator
