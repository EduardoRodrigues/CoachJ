package coachj.utils;

import coachj.builders.CoachBuilder;
import coachj.builders.GeneralManagerBuilder;
import coachj.dao.DatabaseDirectConnection;
import coachj.models.Coach;
import coachj.models.GeneralManager;
import coachj.structures.CoachCurrentContract;
import coachj.structures.CoachPerformance;
import coachj.structures.CoachTransactionRecord;
import coachj.structures.GeneralManagerPerformance;
import coachj.structures.GeneralManagerTransactionRecord;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for franchises
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 04/08/2013
 */
public class FranchiseUtils {

    /**
     * Returns the franchise's complete name
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getFranchiseCompleteName(int franchiseId,
            DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset, store 
         * the sql statement and franchise's complete name
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT f.team, c.name AS city FROM franchise f "
                + "INNER JOIN city c ON f.city = c.id "
                + "WHERE f.id = " + franchiseId;
        String franchiseCompleteName = null;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            franchiseCompleteName = resultSet.getString("city") + " " + resultSet.getString("team");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return franchiseCompleteName;
    }

    /**
     * Return the id of the franchise's coach or 0 if the franchise currently
     * does not have a coach
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getFranchiseCoachId(int franchiseId,
            DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset, store 
         * the sql statement and franchise's complete name
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT coach FROM franchise f "
                + "WHERE f.id = " + franchiseId;
        short franchiseCoachId = 0;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.beforeFirst();

            if (resultSet.first()) {
                franchiseCoachId = resultSet.getShort("coach");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return franchiseCoachId;
    }

    /**
     * Return the id of the franchise's general manager or 0 if the franchise
     * currently does not have one
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getFranchiseGeneralManagerId(int franchiseId,
            DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset, store 
         * the sql statement and franchise's complete name
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT generalManager FROM franchise f "
                + "WHERE f.id = " + franchiseId;
        short franchiseGeneralManagerId = 0;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.beforeFirst();

            if (resultSet.first()) {
                franchiseGeneralManagerId = resultSet.getShort("generalManager");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return franchiseGeneralManagerId;
    }

    /**
     * Returns the value of a field as a String
     *
     * @param franchiseId Franchise's id
     * @param fieldName Field to be returned
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getFieldValueAsString(int franchiseId, String fieldName,
            DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset, store 
         * the sql statement and get the field value
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT " + fieldName + " AS value FROM franchise f "
                + "WHERE f.id = " + franchiseId;
        String fieldValue = null;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            fieldValue = resultSet.getString("value");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }

        return fieldValue;
    }

    /**
     * Returns the payroll amount for the given franchise
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static int getPayroll(int franchiseId,
            DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset, store 
         * the sql statement and franchise's complete name
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement;
        int payroll = 0;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Retrieving players payroll
             */
            sqlStatement = "SELECT SUM(salary) AS payroll FROM player "
                    + "WHERE franchise = " + franchiseId + " AND retired = false";
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            payroll = resultSet.getInt("payroll");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return payroll;
    }

    /**
     * Returns the number of players signed for the given franchise
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getSignedPlayers(int franchiseId,
            DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset, store 
         * the sql statement and franchise's complete name
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT COUNT(id) AS signedPlayers FROM player "
                + "WHERE franchise = " + franchiseId + " AND retired = false";
        short signedPlayers = 0;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);

            resultSet.first();
            signedPlayers = resultSet.getShort("signedPlayers");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return signedPlayers;
    }

    /**
     * Returns the number of active players for the given franchise
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getActivePlayers(int franchiseId,
            DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset, store 
         * the sql statement and franchise's complete name
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT COUNT(id) AS activePlayers FROM player "
                + "WHERE franchise = " + franchiseId + " AND retired = false "
                + "AND isActive = true";
        short activePlayers = 0;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            activePlayers = resultSet.getShort("activePlayers");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return activePlayers;
    }

    /**
     * Returns the id for the arena of the given franchise
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getFranchiseArenaId(int franchiseId,
            DatabaseDirectConnection connection) {       
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT arena FROM franchise "
                + "WHERE id = " + franchiseId;
        short arenaId = 0;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            arenaId = resultSet.getShort("arena");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return arenaId;
    }    
    
    /**
     * Indicates if the given franchise has a coach
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static boolean franchiseHasCoach(int franchiseId,
            DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset, store 
         * the sql statement and franchise's complete name
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT coach FROM franchise "
                + "WHERE id = " + franchiseId;
        Short coachId;
        boolean hasCoach = false;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            coachId = resultSet.getShort("coach");
            System.out.println(coachId); // delete
            /**
             * if the franchise does have a coach, check if there still are
             * years on his contract
             */
            if (coachId > 0) {
                CoachCurrentContract contract = CoachUtils.getCoachContractSummary(coachId,
                        connection);
                System.out.println("Remaining years: " + contract.getRemainingYears()); // delete
                hasCoach = contract.getRemainingYears() > 0;
            } else {
                hasCoach = false;
            }


        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return hasCoach;
    }

    /**
     * Indicates if the given franchise has a general manager
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static boolean franchiseHasGeneralManager(int franchiseId,
            DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset, store 
         * the sql statement and franchise's complete name
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }
        ResultSet resultSet;
        String sqlStatement = "SELECT generalManager FROM franchise "
                + "WHERE id = " + franchiseId;
        Short generalManagerId;
        boolean hasGeneralManager = false;

        try {
            /**
             * Opening database connection
             */
            connection.open();

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            generalManagerId = resultSet.getShort("generalManager");
            hasGeneralManager = generalManagerId > 0 ? true : false;
            System.out.println("General Manager Id:" + generalManagerId); // delete
            System.out.println("Has General Manager:" + hasGeneralManager); // delete
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return hasGeneralManager;
    }

    /**
     * This method analyses the logs from previous seasons to decide whether the
     * franchise will keep its current general manager. The logic for that
     * follows:
     *
     * If the season is the first one to be played by the franchise, it'll try
     * to hire the general manager with the highest market value;
     *
     * If the season is the second one, the franchise will keep the general
     * manager;
     *
     * If the season is the third one and beyond, the logs from the last seasons
     * with the general manager will be analysed, if the rate is below -3, the
     * general manager will be fired and the franchise will try to hire the
     * general manager with the highest market value;
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     */
    public static void analyseGeneralManagerPerformance(short franchiseId,
            DatabaseDirectConnection connection) {
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            connection.open();
        }
        /**
         * Auxiliary variables
         */
        short generalManagerId = 0;
        GeneralManagerBuilder generalManagerBuilder = new GeneralManagerBuilder();
        GeneralManager generalManager;
        GeneralManagerTransactionRecord offer = new GeneralManagerTransactionRecord();

        /**
         * Checking whether the franchise has a general manager, if not, hire
         * the best available
         */
        if (!franchiseHasGeneralManager(franchiseId, connection)) {
            generalManagerId = GeneralManagerUtils
                    .getBestUnemployedGeneralManagerId(connection);
            generalManagerBuilder.fillAttributesFromDatabase(generalManagerId,
                    connection);
            generalManager = generalManagerBuilder.generateGeneralManagerEntity();
            offer.setType("C");
            offer.setLength((short) 4);
            System.out.println("General Manager: " + generalManagerId); // delete
        } else {
            /**
             * Retrieving franchise's previous season logs with the general
             * manager
             */
            GeneralManagerPerformance generalManagerPerformance = new GeneralManagerPerformance(generalManagerId, franchiseId);
            generalManagerId = FranchiseUtils.getFranchiseGeneralManagerId(franchiseId,
                    connection);
            generalManagerBuilder.fillAttributesFromDatabase(generalManagerId,
                    connection);
            generalManager = generalManagerBuilder.generateGeneralManagerEntity();

            /**
             * Checking the number of seasons with the franchise to decide what
             * to do:
             *
             * - In the first and second seasons, do nothing; up from the third
             * one and beyond, analyse records
             */
            if (generalManagerPerformance.getYearsWithFranchise() <= 2
                    && generalManager.getRemainingYears() > 0) {
                return;
            } else {

                if (generalManagerPerformance.getPerformanceRate() < -3) {
                    /**
                     * Bad performance rate, waive or release general manager
                     */
                    offer.setLength((short) 0);
                    if (generalManager.getRemainingYears() == 0) {
                        offer.setType("L");
                    } else {
                        offer.setType("W");
                    }
                } else {
                    /**
                     * Good performance rate, keep or resign general manager
                     */
                    offer.setLength((short) 4);
                    if (generalManager.getRemainingYears() == 0) {
                        offer.setType("R");
                    } else {
                        offer.setType("C");
                    }
                }
            }
        }

        /**
         * Setting up contract and processing it
         */
        offer.setGeneralManager(generalManager.getId());
        offer.setDate(SettingsUtils.getSetting("currentDate",
                Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
        offer.setFranchise(franchiseId);
        offer.setSalary(generalManager.getSalary());
        offer.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));

        GeneralManagerUtils.processGeneralManagerContract(offer);
    }

    /**
     * This method analyses the logs from previous seasons to decide whether the
     * franchise will keep its current coach. The logic for that follows:
     *
     * If the season is the first one to be played by the franchise, it'll try
     * to hire the coach with the highest market value;
     *
     * If the season is the second one, the franchise will keep the coach;
     *
     * If the season is the third one and beyond, the logs from the last seasons
     * with the coach will be analysed, if the rate is below the general manager
     * maximum bad season attributes, the coach will be fired and the franchise
     * will try to hire the coach with the highest market value;
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     */
    public static void analyseCoachPerformance(short franchiseId,
            DatabaseDirectConnection connection) {
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            connection.open();
        }
        /**
         * Auxiliary variables
         */
        short coachId = 0;
        CoachBuilder coachBuilder = new CoachBuilder();
        Coach coach;
        CoachTransactionRecord offer = new CoachTransactionRecord();

        /**
         * Checking whether the franchise has a general manager, if not, hire
         * the best available
         */
        if (!franchiseHasCoach(franchiseId, connection)) {
            coachId = CoachUtils.getBestUnemployedCoachId(connection);
            coachBuilder.fillAttributesFromDatabase(coachId, connection);
            coach = coachBuilder.generateCoachEntity();
            offer.setType("C");
            offer.setLength((short) 4);
            System.out.println("Coach: " + coachId); // delete
        } else {
            /**
             * Retrieving franchise's previous season logs with the coach
             */
            CoachPerformance coachPerformance = new CoachPerformance(coachId, franchiseId);
            coachId = FranchiseUtils.getFranchiseCoachId(franchiseId, null);
            coachBuilder.fillAttributesFromDatabase(coachId, connection);
            coach = coachBuilder.generateCoachEntity();

            /**
             * Checking the number of seasons with the franchise to decide what
             * to do:
             *
             * - In the first and second seasons, do nothing; up from the third
             * one and beyond, analyse records
             */
            if (coachPerformance.getYearsWithFranchise() <= 2
                    && coach.getRemainingYears() > 0) {
                return;
            } else {

                if (coachPerformance.getPerformanceRate() < -3) {
                    /**
                     * Bad performance rate, waive or release coach
                     */
                    offer.setLength((short) 0);
                    if (coach.getRemainingYears() == 0) {
                        offer.setType("L");
                    } else {
                        offer.setType("W");
                    }
                } else {
                    /**
                     * Good performance rate, keep or resign coach
                     */
                    offer.setLength((short) 4);
                    if (coach.getRemainingYears() == 0) {
                        offer.setType("R");
                    } else {
                        offer.setType("C");
                    }
                }
            }
        }

        /**
         * Setting up contract and processing it
         */
        offer.setCoach(coach.getId());
        offer.setDate(SettingsUtils.getSetting("currentDate",
                Calendar.getInstance().get(Calendar.YEAR) + "-01-01"));
        offer.setFranchise(franchiseId);
        offer.setSalary(coach.getSalary());
        offer.setSeason(Short.parseShort(SettingsUtils.getSetting("currentSeason",
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))));

        CoachUtils.processCoachContract(offer);
    }

    /**
     * Returns a random number available for using on a jersey in the given
     * franchise
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getAvailableJerseyNumber(int franchiseId,
            DatabaseDirectConnection connection) {
        
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            connection.open();
        }
        /**
         * Auxiliary variables
         */
        Random generator = new Random();
        short availableJerseyNumber = (short) (generator.nextInt(51));
        ResultSet resultSet;
        String sqlStatement;        

        /**
         * Loop to find an available jersey number
         */
        while (true) {
            sqlStatement = "SELECT jersey FROM player WHERE retired = false AND "
                    + "franchise = " + franchiseId + " AND jersey = " + availableJerseyNumber;
            resultSet = connection.getResultSet(sqlStatement);

            try {
                if (resultSet.next()) {
                    availableJerseyNumber++;
                    if (availableJerseyNumber > 99) {
                        availableJerseyNumber = 0;
                    }
                } else {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(FranchiseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return availableJerseyNumber;
    }
} // end FranchiseUtils
