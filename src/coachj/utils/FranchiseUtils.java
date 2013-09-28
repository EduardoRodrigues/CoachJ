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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

        ResultSet resultSet;
        String sqlStatement = "SELECT f.team, c.name AS city FROM franchise f "
                + "INNER JOIN city c ON f.city = c.id "
                + "WHERE f.id = " + franchiseId;
        String franchiseCompleteName = null;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            franchiseCompleteName = resultSet.getString("city") + " " + resultSet.getString("team");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return franchiseCompleteName;
    }

    /**
     * Returns the city where the franchise is located
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getFranchiseCity(int franchiseId,
            DatabaseDirectConnection connection) {

        ResultSet resultSet;
        String sqlStatement = "SELECT c.name AS city FROM franchise f "
                + "INNER JOIN city c ON f.city = c.id "
                + "WHERE f.id = " + franchiseId;
        String franchiseCity = null;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            franchiseCity = resultSet.getString("city");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return franchiseCity;
    }

    /**
     * Returns the franchise's abbreviature
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getFranchiseAbbreviature(int franchiseId,
            DatabaseDirectConnection connection) {

        ResultSet resultSet;
        String sqlStatement = "SELECT abbreviature FROM franchise "
                + "WHERE id = " + franchiseId;
        String franchiseAbbreviature = null;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            franchiseAbbreviature = resultSet.getString("abbreviature");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return franchiseAbbreviature;
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

        ResultSet resultSet;
        String sqlStatement = "SELECT coach FROM franchise f "
                + "WHERE f.id = " + franchiseId;
        short franchiseCoachId = 0;

        try {
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

        ResultSet resultSet;
        String sqlStatement = "SELECT generalManager FROM franchise f "
                + "WHERE f.id = " + franchiseId;
        short franchiseGeneralManagerId = 0;

        try {
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
        ResultSet resultSet;
        String sqlStatement = "SELECT " + fieldName + " AS value FROM franchise f "
                + "WHERE f.id = " + franchiseId;
        String fieldValue = null;

        try {

            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            fieldValue = resultSet.getString("value");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
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

        ResultSet resultSet;
        String sqlStatement;
        int payroll = 0;

        try {
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

        ResultSet resultSet;
        String sqlStatement = "SELECT COUNT(id) AS signedPlayers FROM player "
                + "WHERE franchise = " + franchiseId + " AND retired = false";
        short signedPlayers = 0;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);

            resultSet.first();
            signedPlayers = resultSet.getShort("signedPlayers");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
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

        ResultSet resultSet;
        String sqlStatement = "SELECT COUNT(id) AS activePlayers FROM player "
                + "WHERE franchise = " + franchiseId + " AND retired = false "
                + "AND active = true";
        short activePlayers = 0;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            activePlayers = resultSet.getShort("activePlayers");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
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

        ResultSet resultSet;
        String sqlStatement = "SELECT arena FROM franchise "
                + "WHERE id = " + franchiseId;
        short arenaId = 0;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            arenaId = resultSet.getShort("arena");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arenaId;
    }

    /**
     * Returns the number of ticket holders for the given franchise
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static int getFranchiseTicketHolders(int franchiseId,
            DatabaseDirectConnection connection) {

        ResultSet resultSet;
        String sqlStatement = "SELECT ticketHolders FROM franchise "
                + "WHERE id = " + franchiseId;
        int ticketHolders = 0;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            ticketHolders = resultSet.getInt("ticketHolders");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ticketHolders;
    }

    /**
     * Returns the current season record for the given franchise
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static double getFranchiseRecord(int franchiseId,
            DatabaseDirectConnection connection) {

        ResultSet resultSet;
        String sqlStatement = "SELECT record FROM franchise "
                + "WHERE id = " + franchiseId;
        double record = 0;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            record = resultSet.getDouble("record");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return record;
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

        ResultSet resultSet;
        String sqlStatement = "SELECT coach FROM franchise "
                + "WHERE id = " + franchiseId;
        Short coachId;
        boolean hasCoach = false;

        try {
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

        ResultSet resultSet;
        String sqlStatement = "SELECT generalManager FROM franchise "
                + "WHERE id = " + franchiseId;
        Short generalManagerId;
        boolean hasGeneralManager = false;

        try {
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

        GeneralManagerUtils.processGeneralManagerContract(offer, connection);
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
            coachId = FranchiseUtils.getFranchiseCoachId(franchiseId, connection);
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

        CoachUtils.processCoachContract(offer, connection);
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

    /**
     * Returns the position with fewest players within the given franchise's
     * roster
     *
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getFranchiseFewestPosition(short franchiseId,
            DatabaseDirectConnection connection) {

        String franchiseFewestPosition = "SF";
        String currentPosition;
        int franchiseActivePlayers = FranchiseUtils.getActivePlayers(franchiseId,
                connection);
        int lowerBound = franchiseActivePlayers / 5;
        int currentPositionCount;
        ArrayList<String> positionsArrayList = new ArrayList<>();

        /**
         * Populating the array list, then shuffling it
         */
        positionsArrayList.add("PG");
        positionsArrayList.add("SG");
        positionsArrayList.add("SF");
        positionsArrayList.add("PF");
        positionsArrayList.add("C");

        Collections.shuffle(positionsArrayList);

        /**
         * Iterating through the arraylist until finding a position that has the
         * lowest number of players
         */
        for (int i = 0; i < positionsArrayList.size(); i++) {
            currentPosition = positionsArrayList.get(i);
            currentPositionCount = getFranchisePositionPlayersCount(franchiseId,
                    currentPosition, connection);

            if (currentPositionCount == lowerBound) {
                franchiseFewestPosition = currentPosition;
                break;
            }
        }

        return franchiseFewestPosition;
    }

    /**
     * Returns the number of players that primarily play at that given position
     * in the given franchise
     *
     * @param franchiseId Franchise's id
     * @param position Player's position
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getFranchisePositionPlayersCount(short franchiseId, String position,
            DatabaseDirectConnection connection) {

        ResultSet resultSet;
        String sqlStatement;
        short franchisePositionPlayersCount = 0;

        try {
            sqlStatement = "SELECT COUNT(position) AS positionCount FROM player "
                    + "WHERE active = true AND franchise = " + franchiseId
                    + " AND position = '" + position + "'";
            resultSet = connection.getResultSet(sqlStatement);
            if (resultSet.next()) {
                franchisePositionPlayersCount = resultSet.getShort("positionCount");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RosterUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return franchisePositionPlayersCount;
    }

    /**
     * Returns the top rebounder for a team in a given game
     * 
     * @param franchiseId Franchise's id
     * @param GameId Game's id
     * @param connection Database connection used to retrieve data
     * @return 
     */
    public static String getGameTopRebounder(int franchiseId, int GameId,
            DatabaseDirectConnection connection) {

        String gameTopRebounder = null;
        String playerName;
        short rebounds;
        ResultSet resultSet;
        String sqlStatement;

        try {
            sqlStatement = "SELECT player, MAX(defensiveRebounds + offensiveRebounds) "
                    + "AS rebounds FROM player_log "
                    + "WHERE team = " + franchiseId
                    + " AND game = " + GameId + " GROUP BY player "
                    + "ORDER BY rebounds DESC, playingTime LIMIT 1";
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();

            playerName = PlayerUtils.getPlayerCompleteName(resultSet.getInt("player"),
                    connection);
            rebounds = resultSet.getShort("rebounds");
            gameTopRebounder = playerName + " (" + rebounds + ")";

        } catch (SQLException ex) {
            Logger.getLogger(RosterUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gameTopRebounder;
    }
    
    /**
     * Returns the top scorer for a team in a given game
     * 
     * @param franchiseId Franchise's id
     * @param GameId Game's id
     * @param connection Database connection used to retrieve data
     * @return 
     */
    public static String getGameTopScorer(int franchiseId, int GameId,
            DatabaseDirectConnection connection) {

        String gameTopScorer = null;
        String playerName;
        short points;
        ResultSet resultSet;
        String sqlStatement;

        try {
            sqlStatement = "SELECT player, MAX(points) "
                    + "AS points FROM player_log "
                    + "WHERE team = " + franchiseId
                    + " AND game = " + GameId + " GROUP BY player "
                    + "ORDER BY points DESC, playingTime LIMIT 1";
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();

            playerName = PlayerUtils.getPlayerCompleteName(resultSet.getInt("player"),
                    connection);
            points = resultSet.getShort("points");
            gameTopScorer = playerName + " (" + points + ")";

        } catch (SQLException ex) {
            Logger.getLogger(RosterUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gameTopScorer;
    }
    
    /**
     * Returns the top assistant for a team in a given game
     * 
     * @param franchiseId Franchise's id
     * @param GameId Game's id
     * @param connection Database connection used to retrieve data
     * @return 
     */
    public static String getGameTopAssistant(int franchiseId, int GameId,
            DatabaseDirectConnection connection) {

        String gameTopAssistant = null;
        String playerName;
        short assists;
        ResultSet resultSet;
        String sqlStatement;

        try {
            sqlStatement = "SELECT player, MAX(assists) "
                    + "AS assists FROM player_log "
                    + "WHERE team = " + franchiseId
                    + " AND game = " + GameId + " GROUP BY player "
                    + "ORDER BY assists DESC, playingTime LIMIT 1";
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();

            playerName = PlayerUtils.getPlayerCompleteName(resultSet.getInt("player"),
                    connection);
            assists = resultSet.getShort("assists");
            gameTopAssistant = playerName + " (" + assists + ")";

        } catch (SQLException ex) {
            Logger.getLogger(RosterUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gameTopAssistant;
    }
} // end FranchiseUtils
