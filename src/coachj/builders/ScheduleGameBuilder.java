/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj.builders;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.ScheduleGame;
import coachj.utils.FranchiseUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Builds ScheduleGame objects
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date /2013
 */
public class ScheduleGameBuilder {

    /**
     * Build a ScheduleGame object
     * 
     * @param gameId Game's id
     * @param franchiseId Franchise's id
     * @param connection Database connection used to retrieve data
     * @return 
     */
    public static ScheduleGame buildScheduleGame(int gameId, short franchiseId,
            DatabaseDirectConnection connection) {
        ScheduleGame game = new ScheduleGame();
        ResultSet resultSet;
        String sqlStatement;
        String gameDate;
        String homeRoad;
        String opponent;
        String result = "---";
        String topScorer = null;
        String topRebounder = null;
        String topAssistant = null;

        /**
         * Retrieving information about the game
         */
        sqlStatement = "SELECT date, awayTeam, awayScore, homeTeam, homeScore, "
                + "periodsPlayed, played FROM game WHERE id = " + gameId;

        resultSet = connection.getResultSet(sqlStatement);

        try {

            resultSet.first();
            gameDate = resultSet.getString("date");

            /**
             * Checking which one is the home team to define several fields
             */
            if (resultSet.getShort("homeTeam") == franchiseId) {
                homeRoad = "vs ";
                opponent = FranchiseUtils.getFranchiseCity(resultSet.getShort("awayTeam"),
                        connection);

                if (resultSet.getBoolean("played")) {
                    if (resultSet.getShort("homeScore") > resultSet.getShort("awayScore")) {
                        result = "W " + resultSet.getShort("homeScore") + "-"
                                + resultSet.getShort("awayScore");
                    } else {
                        result = "L " + resultSet.getShort("awayScore") + "-"
                                + resultSet.getShort("homeScore");
                    }
                }
            } else {
                homeRoad = "@ ";
                opponent = FranchiseUtils.getFranchiseCity(resultSet.getShort("homeTeam"),
                        connection);

                if (resultSet.getBoolean("played")) {
                    if (resultSet.getShort("homeScore") < resultSet.getShort("awayScore")) {
                        result = "W " + resultSet.getShort("homeScore") + "-"
                                + resultSet.getShort("awayScore");
                    } else {
                        result = "L " + resultSet.getShort("awayScore") + "-"
                                + resultSet.getShort("homeScore");
                    }
                }
            }

            if (resultSet.getShort("periodsPlayed") > 4) {
                result += " OT";
            }

            /**
             * Retrieving top performers
             */
            if (resultSet.getBoolean("played")) {
                topRebounder = FranchiseUtils.getGameTopRebounder(franchiseId, gameId,
                        connection);
                topScorer = FranchiseUtils.getGameTopScorer(franchiseId, gameId,
                        connection);
                topAssistant = FranchiseUtils.getGameTopAssistant(franchiseId, gameId,
                        connection);
            }
            
            game.setDate(gameDate);
            game.setOpponent(homeRoad + " " + opponent);
            game.setResult(result);
            game.setTopRebounder(topRebounder);
            game.setTopAssistant(topAssistant);
            game.setTopScorer(topScorer);

        } catch (SQLException ex) {
            Logger.getLogger(ScheduleGameBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return game;
    }
} // end ScheduleGameBuilder
