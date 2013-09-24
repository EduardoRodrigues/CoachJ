package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.ScheduledGame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for the regular season and playoffs schedule
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 22/08/2013
 */
public class ScheduleUtils {

    /**
     * Delete all previously created games of the given season from the database
     *
     * @param season Season to remove games from
     * @param connection Database connection used to retrieve data
     */
    public static void removeSeasonPreviousGames(short season,
            DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        /**
         * Removing all previous games for the given season
         */
        String sqlStatement = "DELETE FROM game WHERE season = " + season;
        connection.executeSQL(sqlStatement);
    }

    /**
     * Schedules an unscheduled games for the given season
     *
     * @param season Season to schedule game
     * @param failedAttempts Number of the failed attempts to schedule a game,
     * if it's greater than 30, tries to find a alternative date
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static boolean scheduleNextGame(short season, int failedAttempts,
            DatabaseDirectConnection connection) {

        boolean gameScheduled = false;
        /**
         * Generates random numbers
         */
        Random generator = new Random();

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        /**
         * Retrieving the last date with a scheduled game on it and how many
         * games are already scheduled, if there are a number between 2-10
         * games, go to the next day.
         */
        String gameDay = getLastScheduledDate(season, connection);
        short gamesScheduledDate = getGamesScheduledDate(gameDay, connection);
        short homeTeam;
        short awayTeam = 0;
        int gameId = 0;

        if (failedAttempts > 30) {
            gameDay = getRandomScheduleDate(season, connection);
            gamesScheduledDate = getGamesScheduledDate(gameDay, connection);
        }

        if (gamesScheduledDate >= generator.nextInt(6) + 7) {
            gameDay = DateUtils.calculateDate(gameDay, 1);
        }

        /**
         * Trying to find teams
         */
        homeTeam = findTeam(gameDay, (short) 0, connection);

        /*
         * If a home team was found for that day, try to find the away team
         */
        if (homeTeam != 0 && !teamHasGameThisDay(gameDay, homeTeam, connection)) {
            awayTeam = findTeam(gameDay, homeTeam, connection);
        }

        /**
         * If both teams were found, check if a game between them was already
         * schedule
         */
        if (homeTeam != 0 && awayTeam != 0 && !teamHasGameThisDay(gameDay, awayTeam, connection)) {
            gameId = findNotScheduledGameBetweenTeams(homeTeam, awayTeam, connection);
        }

        /**
         * There's a not scheduled game between teams, schedule it
         */
        if (gameId != 0) {
            scheduleGame(gameId, gameDay, connection);
            gameScheduled = true;
            System.out.println("Game Scheduled: " + gameDay + ", id: " + gameId);
        }

        return gameScheduled;
    }

    /**
     * Returns the last day with a scheduled game
     *
     * @param season Season year
     * @param connection Database connection used to retrieve data
     * @return
     */
    private static String getLastScheduledDate(short season,
            DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        String lastScheduledDate = null;
        String sqlStatement = "SELECT date FROM game WHERE season = " + season
                + " AND date IS NOT NULL ORDER BY date DESC LIMIT 1";
        ResultSet resultSet = connection.getResultSet(sqlStatement);

        try {
            if (resultSet.next()) {
                lastScheduledDate = resultSet.getString("date");
            } else {
                lastScheduledDate = season + "-02-01";
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lastScheduledDate;
    }

    /**
     * Returns the next day with a scheduled game
     *
     * @param season Season year
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static String getNextScheduledDate(short season,
            DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        String nextScheduledDate = null;
        String sqlStatement = "SELECT date FROM game WHERE season = " + season
                + " AND played = 0 AND date IS NOT NULL ORDER BY date LIMIT 1";
        ResultSet resultSet = connection.getResultSet(sqlStatement);

        try {
            if (resultSet.next()) {
                nextScheduledDate = resultSet.getString("date");
            } else {
                nextScheduledDate = getLastScheduledDate(season, connection);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return nextScheduledDate;
    }
    
    /**
     * Returns the id for the next game to be played
     *
     * @param season Season year
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static int getNextGameId(short season,
            DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        int nextGameId = 0;
        String sqlStatement = "SELECT id FROM game WHERE season = " + season
                + " AND played = FALSE ORDER BY date, time LIMIT 1";
        ResultSet resultSet = connection.getResultSet(sqlStatement);

        try {
            if (resultSet.next()) {
                nextGameId = resultSet.getInt("id");
            } 
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return nextGameId;
    }
    
    
    /**
     * Returns the number of games already schedule in the given date
     *
     * @param date Date to count scheduled games
     * @param connection Database connection used to retrieve data
     * @return
     */
    private static short getGamesScheduledDate(String date,
            DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        short gamesScheduledDate = 0;
        String sqlStatement = "SELECT COUNT(id) AS gamesScheduled FROM game "
                + " WHERE date = '" + date + "'";
        ResultSet resultSet = connection.getResultSet(sqlStatement);

        try {
            if (resultSet.next()) {
                gamesScheduledDate = resultSet.getShort("gamesScheduled");
            } else {
                gamesScheduledDate = 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return gamesScheduledDate;
    }

    /**
     * Tries to find a team that hasn't played 2 games in the 2 previous days
     * from the base date
     *
     * @param date Base date
     * @param excludedId Id not to be included in the search
     * @param connection Database connection used to retrieve data
     * @return <b>0</b> if no team is found, or the id of the found team
     */
    private static short findTeam(String date, short excludedId,
            DatabaseDirectConnection connection) {

        short teamId = 0;
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        /**
         * Obtaining the last 2 dates to find which team doesn't have played for
         * 2 consecutive nights
         */
        String previousDay = DateUtils.calculateDate(date, -1);
        String previousPreviousDay = DateUtils.calculateDate(date, 2);

        String sqlStatement = "SELECT id FROM franchise WHERE registered = 1 "
                + "AND id != " + excludedId + " AND id NOT IN (SELECT awayTeam "
                + "FROM game WHERE date BETWEEN '" + previousPreviousDay + "' AND '"
                + previousDay + "') ORDER BY RAND() LIMIT 1";
        ResultSet resultSet = connection.getResultSet(sqlStatement);

        try {
            if (resultSet.next()) {
                teamId = resultSet.getShort("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teamId;
    }

    /**
     * Tries to find a not scheduled game between the given teams
     *
     * @param homeTeam Home team id
     * @param awayTeam Away team id
     * @param connection Database connection used to retrieve data
     * @return
     */
    private static short findNotScheduledGameBetweenTeams(short homeTeam, short awayTeam,
            DatabaseDirectConnection connection) {

        short gameId = 0;
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        /**
         * Checking whether there's a not scheduled game between the teams
         */
        String sqlStatement = "SELECT id FROM game WHERE date IS NULL "
                + "AND homeTeam = " + homeTeam + " AND awayTeam = " + awayTeam;
        ResultSet resultSet = connection.getResultSet(sqlStatement);

        try {
            if (resultSet.next()) {
                gameId = resultSet.getShort("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return gameId;
    }

    /**
     * Effectively schedule the game in the database
     *
     * @param gameId Game's id
     * @param date Date to schedule the game
     * @param connection Database connection used to retrieve data
     */
    private static void scheduleGame(int gameId, String date,
            DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        String sqlStatement = "UPDATE game SET date = '" + date + "' WHERE id = "
                + gameId;
        connection.executeSQL(sqlStatement);
    }

    /**
     * Returns whether the given team already has a scheduled game in the given
     * date
     *
     * @param date Date to schedule the game
     * @param teamId Team's id
     * @param connection Database connection used to retrieve data
     * @return
     */
    private static boolean teamHasGameThisDay(String date, short teamId,
            DatabaseDirectConnection connection) {

        boolean teamHasGame = false;
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        /**
         * Checking whether there's a not scheduled game between the teams
         */
        String sqlStatement = "SELECT id FROM game WHERE date = '" + date + "' "
                + "AND (homeTeam = " + teamId + " OR awayTeam = " + teamId + ")";
        ResultSet resultSet = connection.getResultSet(sqlStatement);

        try {
            if (resultSet.next()) {
                teamHasGame = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teamHasGame;
    }

    /**
     * Returns a ramdom date from the given season's schedule
     *
     * @param season Season year
     * @param connection Database connection used to retrieve data
     * @return
     */
    private static String getRandomScheduleDate(short season,
            DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        String randomScheduleDate = null;
        String sqlStatement = "SELECT date FROM game "
                + "WHERE season = " + season + " AND date IS NOT NULL "
                + "ORDER BY RAND() LIMIT 1";
        ResultSet resultSet = connection.getResultSet(sqlStatement);

        try {
            if (resultSet.next()) {
                randomScheduleDate = resultSet.getString("date");
            } else {
                randomScheduleDate = season + "-02-01";
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return randomScheduleDate;
    }    
    
} // end class ScheduleUtils
