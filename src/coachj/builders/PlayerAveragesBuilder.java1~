package coachj.builders;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.PlayerAverages;
import coachj.utils.PlayerUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates new playeraverages objects
 *
 * @author Eduardo M. Rodrigues
 * @version 1.1
 * @date 23/09/2013
 */
public class PlayerAveragesBuilder {

    /**
     * Builds a PlayerAverages object
     * 
     * @param playerId Player's id
     * @param season Season to retrieve stats from, if it's null, the stats for
     * all the player's career are retrieved
     * @param connection Database connection used to retrieve data
     * @return
     */
    public static PlayerAverages buildPlayerAverages(int playerId, Integer season,
            DatabaseDirectConnection connection) {
        PlayerAverages playerAverages = new PlayerAverages();
        ResultSet resultSet;
        String sqlStatement;

        sqlStatement = "SELECT AVG(playingTime) AS minutesPerGame, "
                + "AVG(points) AS pointsPerGame, "
                + "AVG(fieldGoalsMade / fieldGoalsAttempted) AS fieldGoalPercentage, "
                + "AVG(freeThrowsMade / freeThrowsAttempted) AS freeThrowPercentage, "
                + "AVG(threePointersMade / threePointersAttempted) AS threePointerPercentage, "
                + "AVG(offensiveRebounds + defensiveRebounds) AS reboundsPerGame, "
                + "AVG(assists) assistsPerGame FROM player_log WHERE player = "
                + playerId;
        
        /**
         * Checking whether the season was informed
         */
        if (season != null) {
            sqlStatement += " AND season = " + season;
        }
        
        resultSet = connection.getResultSet(sqlStatement);
        
        try {
            resultSet.first();
            playerAverages.setJersey(PlayerUtils.getPlayerJersey(playerId, connection));
            playerAverages.setCompleteName(PlayerUtils.getPlayerCompleteName(playerId, connection));
            playerAverages.setMinutesPerGame(resultSet.getInt("minutesPerGame"));
            playerAverages.setPointsPerGame(resultSet.getDouble("pointsPerGame"));
            playerAverages.setFieldGoalPercentage(resultSet.getDouble("fieldGoalPercentage"));
            playerAverages.setFreeThrowPercentage(resultSet.getDouble("freeThrowPercentage"));
            playerAverages.setThreePointerPercentage(resultSet.getDouble("threePointerPercentage"));
            playerAverages.setReboundsPerGame(resultSet.getDouble("reboundsPerGame"));
            playerAverages.setAssistsPerGame(resultSet.getDouble("assistsPerGame"));
            
        } catch (SQLException ex) {
            Logger.getLogger(PlayerAveragesBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return playerAverages;
    }
} // end PlayerAveragesBuilder
