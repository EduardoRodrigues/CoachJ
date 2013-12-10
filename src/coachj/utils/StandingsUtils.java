package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.StandingsTableEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for the standings table
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 29/09/2013
 */
public class StandingsUtils {

    /**
     * Returns an array list with the standings table data for the given season
     *
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static ArrayList<StandingsTableEntry> getStandingsTableData(
            DatabaseDirectConnection connection) {

        ArrayList<StandingsTableEntry> standingsTableData = new ArrayList<>();
        StandingsTableEntry currentEntry;
        ResultSet resultSet;
        int games;
        int seed = 1;
        String team;
        String record;
        String home;
        String road;
        double percentage;
        int wins;
        int losses;
        int homeWins;
        int homeLosses;
        int roadWins;
        int roadLosses;
        String streak;
        String last10;
        double gamesBehind;
        String sqlStatement = "SELECT * FROM franchise ORDER BY (homeWins + awayWins) DESC, "
                + " record DESC, (homeLosses + awayLosses), tiebreaker DESC";

        resultSet = connection.getResultSet(sqlStatement);

        /**
         * Looping through the resultset to fill up the table
         */
        try {
            while (resultSet.next()) {

                team = FranchiseUtils.getFranchiseCompleteName(resultSet.getInt("id"),
                        connection);
                wins = resultSet.getShort("homeWins") + resultSet.getShort("awayWins");
                losses = resultSet.getShort("homeLosses") + resultSet.getShort("awayLosses");
                homeWins = resultSet.getShort("homeWins");
                homeLosses = resultSet.getShort("homeLosses");
                roadWins = resultSet.getShort("awayWins");
                roadLosses = resultSet.getShort("awayLosses");
                games = wins + losses;
                record = "(" + wins + "-" + losses + ")";
                home = "(" + homeWins + "-" + homeLosses + ")";
                road = "(" + roadWins + "-" + roadLosses + ")";
                percentage = (double) wins / games;
                gamesBehind = 0;
                streak = resultSet.getString("streak");
                last10 = resultSet.getString("last10");

                currentEntry = new StandingsTableEntry();
                currentEntry.setSeed(String.valueOf(seed));
                currentEntry.setTeam(team);
                currentEntry.setGames(String.valueOf(games));
                currentEntry.setRecord(record);
                currentEntry.setPercentage(String.format("%01.3f", percentage));
                currentEntry.setHome(String.valueOf(home));
                currentEntry.setRoad(String.valueOf(road));
                currentEntry.setStreak(streak);
                currentEntry.setLast10(last10);
                currentEntry.setGamesBehind(String.format("%01.1f", gamesBehind));

                standingsTableData.add(currentEntry);
                seed++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StandingsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return standingsTableData;
    }

    /**
     * Returns the current streak for the given franchise in the given season
     *
     * @param franchiseId Franchise's id
     * @param season Season year
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getFranchiseStreak(int franchiseId, int season,
            DatabaseDirectConnection connection) {

        String franchiseStreak;
        int streak = 0;
        String streakType = "";
        String gameResult;
        int homeTeam;
        int homeScore;
        int awayTeam;
        int awayScore;
        ResultSet resultSet;

        String sqlStatement = "SELECT homeTeam, homeScore, awayTeam, awayScore "
                + "FROM game WHERE season = " + season + " AND (homeTeam = "
                + franchiseId + " OR awayTeam = " + franchiseId + ") AND played = 1 "
                + "ORDER BY date DESC";

        resultSet = connection.getResultSet(sqlStatement);

        try {
            while (resultSet.next()) {
                /**
                 * Retrieving values from the resultset
                 */
                homeTeam = resultSet.getInt("homeTeam");
                awayTeam = resultSet.getInt("awayTeam");
                homeScore = resultSet.getInt("homeScore");
                awayScore = resultSet.getInt("awayScore");

                /**
                 * Checking whether the teams has won or lost the game, and
                 * compare it to the current streak
                 */
                if (homeTeam == franchiseId) {
                    if (homeScore > awayScore) {
                        gameResult = "W";
                    } else {
                        gameResult = "L";
                    }
                } else {
                    if (homeScore < awayScore) {
                        gameResult = "W";
                    } else {
                        gameResult = "L";
                    }
                }

                if (streakType.equals("")) {
                    streakType = gameResult;
                    streak++;
                } else {
                    if (streakType.equalsIgnoreCase(gameResult)) {
                        streak++;
                    } else {
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(StandingsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        franchiseStreak = streakType + " " + streak;
        return franchiseStreak;
    }

    /**
     * Returns the summary of the last 10 games for the given franchise in the
     * given season
     *
     * @param franchiseId Franchise's id
     * @param season Season year
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getFranchiseLast10(int franchiseId, int season,
            DatabaseDirectConnection connection) {

        String franchiseLast10;        
        int homeTeam;
        int homeScore;
        int awayTeam;
        int awayScore;
        int wins = 0;
        int losses = 0;
        ResultSet resultSet;

        String sqlStatement = "SELECT homeTeam, homeScore, awayTeam, awayScore "
                + "FROM game WHERE season = " + season + " AND (homeTeam = "
                + franchiseId + " OR awayTeam = " + franchiseId + ") AND played = 1 "
                + "ORDER BY date DESC LIMIT 10";

        resultSet = connection.getResultSet(sqlStatement);
        
        try {
            while (resultSet.next()) {
                /**
                 * Retrieving values from the resultset
                 */
                homeTeam = resultSet.getInt("homeTeam");
                awayTeam = resultSet.getInt("awayTeam");
                homeScore = resultSet.getInt("homeScore");
                awayScore = resultSet.getInt("awayScore");

                /**
                 * Checking whether the teams has won or lost the game, and
                 * compare it to the current streak
                 */
                if (homeTeam == franchiseId) {
                    if (homeScore > awayScore) {
                        wins++;
                    } else {
                        losses++;
                    }
                } else {
                    if (homeScore < awayScore) {
                        wins++;
                    } else {
                        losses++;
                    }
                }               
            }
        } catch (SQLException ex) {
            Logger.getLogger(StandingsUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        franchiseLast10 = String.format("%1d-%1d", wins, losses);
        return franchiseLast10;

    }
} // end StandingsUtils
