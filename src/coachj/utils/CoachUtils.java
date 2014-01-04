package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import coachj.models.Coach;
import coachj.structures.CoachCurrentContract;
import coachj.structures.CoachTransactionRecord;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for coaches
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 05/08/2013
 */
public class CoachUtils {

    /**
     * Returns contract data for the given coach
     *
     * @param coachId Coach's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static CoachCurrentContract getCoachContractSummary(short coachId,
            DatabaseDirectConnection connection) {

        ResultSet resultSet;
        String sqlStatement = "SELECT firstName, lastName, remainingYears, salary "
                + "FROM coach "
                + "WHERE id = " + coachId;
        CoachCurrentContract contract = new CoachCurrentContract(coachId);

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            contract.setFirstName(resultSet.getString("firstName"));
            contract.setLastName(resultSet.getString("lastName"));
            contract.setRemainingYears(resultSet.getShort("remainingYears"));
            contract.setSalary(resultSet.getInt("salary"));
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return contract;
    }

    /**
     * Returns if the coach agrees with the proposal or not
     *
     * @param coach Coach to whom the proposal is sent
     * @param coachProposal Proposal's values
     * @param franchiseOffer Franchise's offer
     * @return
     */
    public static boolean agreeWithTerms(Coach coach, int coachProposal,
            CoachTransactionRecord offer) {
        /**
         * Variables that return the coach's response and the percentual
         * difference between the coach's proposal and the franchise's offer
         */
        boolean agreeWithTerms;
        double differenceRate;

        /**
         * Comparing the franchise's offer with the coach's proposal
         */
        if (offer.getSalary() >= coachProposal) {
            agreeWithTerms = true;
        } else {
            differenceRate = (1 - (double) offer.getSalary() / coachProposal) * 100;
            if (differenceRate < coach.getGreed() + offer.getLength()) {
                agreeWithTerms = false;
            } else {
                agreeWithTerms = true;
            }
        }

        return agreeWithTerms;
    }

    /**
     * Hires a coach, binding him to a franchise
     *
     * @param contract Contract's parameters
     * @param connection Database connection used to retrieve data
     */
    public static void hireCoach(CoachTransactionRecord contract,
            DatabaseDirectConnection connection) {

        String sqlStatement;

        /**
         * Recording contract transaction
         */
        sqlStatement = "INSERT INTO coach_transaction (season, coach, "
                + "franchise, type, date, contractLength, salary) VALUES ("
                + contract.getSeason() + ", " + contract.getCoach() + ", "
                + contract.getFranchise() + ", '" + contract.getType() + "', '"
                + contract.getDate() + "', " + contract.getLength() + ", "
                + contract.getSalary() + ")";
        System.out.println(sqlStatement); //delete
        connection.executeSQL(sqlStatement);

        /**
         * Updating coach's record
         */
        sqlStatement = "UPDATE coach SET salary = " + contract.getSalary() + ", "
                + "remainingYears = " + contract.getLength() + ", failedContractAttempts = 0 "
                + " WHERE id = " + contract.getCoach();
        connection.executeSQL(sqlStatement);

        /**
         * Updating franchise's record
         */
        sqlStatement = "UPDATE franchise SET coach = " + contract.getCoach()
                + " WHERE id = " + contract.getFranchise();
        connection.executeSQL(sqlStatement);
    }

    /**
     * Records a failed attempt to either sign or resign a coach
     *
     * @param coach Coach's id
     * @param connection Database connection used to retrieve data
     */
    public static void recordFailedContractAttempt(int coach,
            DatabaseDirectConnection connection) {

        String sqlStatement;

        /**
         * Updating coach's record
         */
        sqlStatement = "UPDATE coach SET failedContractAttempts = failedContractAttempts + 1 "
                + " WHERE id = " + coach;
        connection.executeSQL(sqlStatement);
    }

    /**
     * Returns the current salary for the given coach
     *
     * @param coachId Coach's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static int getCoachSalary(short coachId,
            DatabaseDirectConnection connection) {

        ResultSet resultSet;
        String sqlStatement = "SELECT salary FROM coach WHERE id = " + coachId;
        int salary = 0;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            salary = resultSet.getInt("salary");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salary;
    }

    /**
     * Returns the draft method for the given coach
     *
     * @param coachId Coach's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getCoachDraftMethod(short coachId,
            DatabaseDirectConnection connection) {

        ResultSet resultSet;
        String sqlStatement = "SELECT draftMethod FROM coach WHERE id = " + coachId;
        short draftMethod = 0;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            draftMethod = resultSet.getShort("draftMethod");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return draftMethod;
    }

    /**
     * Returns the franchise for the given coach
     *
     * @param coachId Coach's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getCoachFranchiseId(short coachId,
            DatabaseDirectConnection connection) {

        ResultSet resultSet;
        String sqlStatement = "SELECT id FROM franchise WHERE coach = " + coachId;
        short franchiseId = 0;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            franchiseId = resultSet.getShort("id");
        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return franchiseId;
    }

    /**
     * Releases a coach, unbinding him from a franchise
     *
     * @param contract Contract's parameters
     * @param connection Database connection used to retrieve data
     */
    public static void releaseCoach(CoachTransactionRecord contract,
            DatabaseDirectConnection connection) {

        String sqlStatement;

        /**
         * Recording transaction
         */
        sqlStatement = "INSERT INTO coach_transaction (season, coach, "
                + "franchise, type, date, contractLength, salary) VALUES ("
                + contract.getSeason() + ", " + contract.getCoach() + ", "
                + contract.getFranchise() + ", '" + contract.getType() + "', '"
                + contract.getDate() + "', " + contract.getLength() + ", "
                + contract.getSalary() + ")";
        System.out.println(sqlStatement); //delete
        connection.executeSQL(sqlStatement);

        /**
         * Updating coach's record
         */
        sqlStatement = "UPDATE coach SET remainingYears = 0, failedContractAttempts = 0, "
                + " salary = marketValue * 4000 WHERE id = " + contract.getCoach();
        connection.executeSQL(sqlStatement);

        /**
         * Updating franchise's record
         */
        sqlStatement = "UPDATE franchise SET coach = NULL"
                + " WHERE id = " + contract.getFranchise();
        connection.executeSQL(sqlStatement);
    }

    /**
     * Fires a coach, unbinding him from a franchise
     *
     * @param contract Contract's parameters
     * @param connection Database connection used to retrieve data
     */
    public static void fireCoach(CoachTransactionRecord contract,
            DatabaseDirectConnection connection) {

        String sqlStatement;

        /**
         * Recording transaction
         */
        sqlStatement = "INSERT INTO coach_transaction (season, coach, "
                + "franchise, type, date, contractLength, salary) VALUES ("
                + contract.getSeason() + ", " + contract.getCoach() + ", "
                + contract.getFranchise() + ", '" + contract.getType() + "', '"
                + contract.getDate() + "', " + contract.getLength() + ", "
                + contract.getSalary() + ")";
        System.out.println(sqlStatement); //delete
        connection.executeSQL(sqlStatement);

        /**
         * Updating coach's record
         */
        sqlStatement = "UPDATE coach SET remainingYears = 0, failedContractAttempts = 0, "
                + " salary = marketValue * 4000, totalEarnings = totalEarnings + "
                + contract.getSalary() + " WHERE id = " + contract.getCoach();
        connection.executeSQL(sqlStatement);

        /**
         * Updating franchise's record
         */
        sqlStatement = "UPDATE franchise SET coach = NULL, assets = assets - "
                + contract.getSalary() + " WHERE id = " + contract.getFranchise();
        connection.executeSQL(sqlStatement);
    }

    /**
     * Returns the id of the best unemployed coach
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static short getBestUnemployedCoachId(DatabaseDirectConnection connection) {

        String sqlStatement = "SELECT id FROM coach "
                + "WHERE retired = false AND id NOT IN (SELECT coach "
                + "FROM franchise WHERE coach IS NOT NULL) "
                + "ORDER BY marketValue, RAND() LIMIT 1";
        ResultSet resultSet;
        short coachId = 0;

        /**
         * Opening the connection, retrieving the general manager's id and
         * returning it
         */
        try {
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            coachId = resultSet.getShort("id");
        } catch (SQLException ex) {
            Logger.getLogger(GeneralManagerUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return coachId;
    }

    /**
     * Processes coach's contract
     *
     * @param contract Contract data
     */
    public static void processCoachContract(CoachTransactionRecord contract,
            DatabaseDirectConnection connection) {

        /**
         * Checking the type of contract to process it
         */
        if (contract.getType().equalsIgnoreCase("C")
                || contract.getType().equalsIgnoreCase("R")) {
            CoachUtils.hireCoach(contract, connection);
        } else if (contract.getType().equalsIgnoreCase("W")) {
            CoachUtils.fireCoach(contract, connection);
        } else {
            CoachUtils.releaseCoach(contract, connection);
        }
    }

    /**
     * Returns the SQL statement that selects a player for drafting based on
     * coach's drafting method attribute, as follows:
     *
     * 0 - player with greatest market value, any position <br />
     * 1 - player with greatest rate in coach's favorite attribute, any position
     * <br />
     * 2 - player with greatest market value, worst starter position <br />
     * 3 - player with greatest rate in coach's favorite attribute, worst
     * starter position <br />
     * 4 - player with greatest market value, worst reserve position <br />
     * 5 - player with greatest rate in coach's favorite attribute, worst
     * reserve position
     *
     * To assure teams have balanced rosters, the algorithm for the SQL prevents
     * the selection of a player with the same position that already has 3
     * players on the roster
     *
     * @param coachId Coach's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getCoachDraftingSQL(short coachId,
            DatabaseDirectConnection connection) {

        String coachDraftingSQL = null;
        short coachDraftingMethod = CoachUtils.getCoachDraftMethod(coachId, connection);
        short franchiseId = CoachUtils.getCoachFranchiseId(coachId, connection);
        String franchiseFewestPosition = FranchiseUtils.getFranchiseFewestPosition(franchiseId,
                connection);

        if (coachDraftingMethod == 0) {
            /* 0 - player with greatest market value, any position */
            coachDraftingSQL = "SELECT id FROM player WHERE franchise IS NULL AND active =  false "
                    + "AND retired = false AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY marketValue DESC LIMIT 1";
        } else if (coachDraftingMethod == 1) {
            /* 1 - player with greatest rate in coach's favorite attribute, any position */
            coachDraftingSQL = "SELECT id FROM player WHERE franchise IS NULL AND active =  false "
                    + "AND retired = false AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY " + CoachUtils.getCoachPreferredAttribute(coachId, connection) + " DESC, "
                    + "marketValue DESC LIMIT 1";
        } else if (coachDraftingMethod == 2) {
            /* 2 - player with greatest market value, worst starter position */
            coachDraftingSQL = "SELECT id FROM player WHERE franchise IS NULL AND active =  false "
                    + "AND retired = false AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY marketValue DESC LIMIT 1";
        } else if (coachDraftingMethod == 3) {
            /* 3 - player with greatest rate in coach's favorite attribute, worst
             starter position */
            coachDraftingSQL = "SELECT id FROM player WHERE franchise IS NULL AND active =  false "
                    + "AND retired = false AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY " + CoachUtils.getCoachPreferredAttribute(coachId, connection)
                    + " DESC, marketValue DESC LIMIT 1";
        } else if (coachDraftingMethod == 4) {
            /* 4 - player with greatest market value, worst reserve position */
            coachDraftingSQL = "SELECT id FROM player WHERE franchise IS NULL AND active =  false "
                    + "AND retired = false AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY marketValue DESC LIMIT 1";
        } else if (coachDraftingMethod == 5) {
            /* 3 - player with greatest rate in coach's favorite attribute, worst
             reserve position */
            coachDraftingSQL = "SELECT id FROM player WHERE franchise IS NULL AND active =  false "
                    + "AND retired = false AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY " + CoachUtils.getCoachPreferredAttribute(coachId, connection)
                    + " DESC, marketValue DESC LIMIT 1";
        }

        return coachDraftingSQL;
    }

    /**
     * Returns the SQL statement that selects a player for signing based on
     * coach's drafting method attribute, as follows:
     *
     * 0 - player with greatest market value, any position <br />
     * 1 - player with greatest rate in coach's favorite attribute, any position
     * <br />
     * 2 - player with greatest market value, worst starter position <br />
     * 3 - player with greatest rate in coach's favorite attribute, worst
     * starter position <br />
     * 4 - player with greatest market value, worst reserve position <br />
     * 5 - player with greatest rate in coach's favorite attribute, worst
     * reserve position
     *
     * To assure teams have balanced rosters, the algorithm for the SQL prevents
     * the selection of a player with the same position that already has 3
     * players on the roster
     *
     * @param coachId Coach's id
     * @param maximumOffer Sets the highest salary that can be retrieved
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getCoachFreeAgentSelectSQL(short coachId, int maximumOffer,
            DatabaseDirectConnection connection) {

        String coachFreeAgentSelectSQL = null;
        short coachDraftingMethod = CoachUtils.getCoachDraftMethod(coachId, connection);
        short franchiseId = CoachUtils.getCoachFranchiseId(coachId, connection);
        String franchiseFewestPosition = FranchiseUtils.getFranchiseFewestPosition(franchiseId,
                connection);

        if (coachDraftingMethod == 0) {
            /* 0 - player with greatest market value, any position */
            coachFreeAgentSelectSQL = "SELECT id FROM player WHERE active =  false "
                    + "AND retired = false AND salary <= " + maximumOffer
                    + " AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY marketValue DESC LIMIT 1";
        } else if (coachDraftingMethod == 1) {
            /* 1 - player with greatest rate in coach's favorite attribute, any position */
            coachFreeAgentSelectSQL = "SELECT id FROM player WHERE active =  false "
                    + "AND retired = false AND salary <= " + maximumOffer
                    + " AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY " + CoachUtils.getCoachPreferredAttribute(coachId, connection) + " DESC, "
                    + "marketValue DESC LIMIT 1";
        } else if (coachDraftingMethod == 2) {
            /* 2 - player with greatest market value, worst starter position */
            coachFreeAgentSelectSQL = "SELECT id FROM player WHERE active =  false "
                    + "AND retired = false AND salary <= " + maximumOffer
                    + " AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY marketValue DESC LIMIT 1";
        } else if (coachDraftingMethod == 3) {
            /* 3 - player with greatest rate in coach's favorite attribute, worst
             starter position */
            coachFreeAgentSelectSQL = "SELECT id FROM player WHERE active =  false "
                    + "AND retired = false AND salary <= " + maximumOffer
                    + " AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY " + CoachUtils.getCoachPreferredAttribute(coachId, connection)
                    + " DESC, marketValue DESC LIMIT 1";
        } else if (coachDraftingMethod == 4) {
            /* 4 - player with greatest market value, worst reserve position */
            coachFreeAgentSelectSQL = "SELECT id FROM player WHERE active =  false "
                    + "AND retired = false AND salary <= " + maximumOffer
                    + " AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY marketValue DESC LIMIT 1";
        } else if (coachDraftingMethod == 5) {
            /* 3 - player with greatest rate in coach's favorite attribute, worst
             reserve position */
            coachFreeAgentSelectSQL = "SELECT id FROM player WHERE active =  false "
                    + "AND retired = false AND salary <= " + maximumOffer
                    + " AND position = '" + franchiseFewestPosition + "' "
                    + "ORDER BY " + CoachUtils.getCoachPreferredAttribute(coachId, connection)
                    + " DESC, marketValue DESC LIMIT 1";
        }

        return coachFreeAgentSelectSQL;
    }

    /**
     * Returns the preferred attribute that the given coach seeks in a player
     *
     * @param coachId Coach's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getCoachPreferredAttribute(short coachId,
            DatabaseDirectConnection connection) {

        TreeMap<Short, String> attributesMap = getCoachAttributesMap(coachId,
                connection);

        return attributesMap.lastEntry().getValue();
    }

    /**
     * Builds a TreeMap with the attributes for the given coach
     *
     * @param coachId Coach's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    private static TreeMap<Short, String> getCoachAttributesMap(short coachId,
            DatabaseDirectConnection connection) {

        String sqlStatement = "SELECT shoot, pass, rebound, defense, technique FROM coach "
                + "WHERE id = " + coachId + " LIMIT 1";
        ResultSet resultSet;
        TreeMap<Short, String> attributesMap = new TreeMap<>();

        /**
         * Opening the connection, retrieving the coach's attributes and
         * returning the preferred one
         */
        try {
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            attributesMap.put(resultSet.getShort("rebound"),
                    "playable DESC, (seasonMomentum + offensiveRebound + defensiveRebound - injuryImpact)");
            attributesMap.put(resultSet.getShort("technique"),
                    "playable DESC, (seasonMomentum + technique - injuryImpact)");
            attributesMap.put(resultSet.getShort("pass"), "(seasonMomentum + pass - injuryImpact)");
            attributesMap.put(resultSet.getShort("shoot"),
                    "playable DESC, (seasonMomentum + fieldGoals + threePointers - injuryImpact)");
            attributesMap.put(resultSet.getShort("defense"),
                    "playable DESC, (seasonMomentum + contest + oneOnOneDefense + helpDefense - injuryImpact)");
        } catch (SQLException ex) {
            Logger.getLogger(GeneralManagerUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return attributesMap;
    }

    /**
     * Returns the SQL ORDER BY clause that's used by the given coach to reorder
     * his team's roster
     *
     * @param coachId Coach's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getCoachRosterOrderingString(short coachId,
            DatabaseDirectConnection connection) {

        String coachRosterOrderingString = null;

        NavigableMap<Short, String> attributesMap = getCoachAttributesMap(coachId,
                connection).descendingMap();

        for (String item : attributesMap.values()) {
            if (coachRosterOrderingString == null) {
                coachRosterOrderingString = item + " DESC";
            } else {
                coachRosterOrderingString += ", " + item + " DESC";
            }
        }

        return coachRosterOrderingString;
    }
    
    /**
     * Returns the coach's complete name
     *
     * @param coachId Coach's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getCoachCompleteName(int coachId,
            DatabaseDirectConnection connection) {


        ResultSet resultSet;
        String sqlStatement = "SELECT firstName, lastName FROM coach "
                + "WHERE id = " + coachId;
        String coachCompleteName = null;

        try {
            /**
             * Executing query, retrieving result and returning
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();
            coachCompleteName = resultSet.getString("firstName") + " "
                    + resultSet.getString("lastName");
        } catch (SQLException ex) {
            Logger.getLogger(CoachUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return coachCompleteName;
    }
} // end CoachUtils
