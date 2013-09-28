package coachj.builders;

import coachj.dao.DatabaseDirectConnection;
import coachj.structures.PlayerGameStats;
import coachj.utils.FranchiseUtils;
import coachj.utils.GameUtils;
import coachj.utils.PlayerUtils;
import coachj.utils.TimeUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates new playergamestats objects
 *
 * @author Eduardo M. Rodrigues
 * @version 1.1
 * @date 27/09/2013
 */
public class PlayerGameStatsBuilder {

    public static PlayerGameStats buildPlayerGameStats(int playerId, int gameId,
            DatabaseDirectConnection connection) {
        
        PlayerGameStats playerGameStats = new PlayerGameStats();        
        ResultSet resultSet;
        String sqlStatement;
        
        sqlStatement = "SELECT playingTime, points, fieldGoalsMade, fieldGoalsAttempted, "
                + "FORMAT(fieldGoalsMade / fieldGoalsAttempted, 3) AS fieldGoalPercentage,"
                + "FORMAT(freeThrowsMade / freeThrowsAttempted, 3) AS freeThrowPercentage,"
                + "FORMAT(threePointersMade / threePointersAttempted, 3) AS threePointerPercentage,"
                + "freeThrowsMade, freeThrowsAttempted, threePointersMade, threePointersAttempted, "
                + "offensiveRebounds, defensiveRebounds, (offensiveRebounds + defensiveRebounds) "
                + "AS totalRebounds, assists, blocks, steals, personalFouls, turnovers, "
                + "gameDate, homeRoad, opponent, result, team FROM player_log WHERE game = " 
                + gameId + " AND player = " + playerId;
        
        resultSet = connection.getResultSet(sqlStatement);
        
        try {
            resultSet.first();
            String assists = resultSet.getString("assists");
            String blocks = resultSet.getString("blocks");
            String gameDate = resultSet.getString("gameDate");
            String minutes = TimeUtils.intToTime(resultSet.getInt("playingTime"));
            String points = resultSet.getString("points");
            String fieldGoals = resultSet.getString("fieldGoalsMade") + "/" 
                    + resultSet.getString("fieldGoalsAttempted") + " (" + 
                    resultSet.getString("fieldGoalPercentage") + ")";
            String freeThrows = resultSet.getString("freeThrowsMade") + "/" 
                    + resultSet.getString("freeThrowsAttempted") + " (" + 
                    resultSet.getString("freeThrowPercentage") + ")";
            String threePointers = resultSet.getString("threePointersMade") + "/" 
                    + resultSet.getString("threePointersAttempted") + " (" + 
                    resultSet.getString("threePointerPercentage") + ")";
            String offensiveRebounds = resultSet.getString("offensiveRebounds");
            String defensiveRebounds = resultSet.getString("defensiveRebounds");
            String totalRebounds = resultSet.getString("totalRebounds");
            String steals = resultSet.getString("steals");
            String personalFouls = resultSet.getString("personalFouls");
            String turnovers = resultSet.getString("turnovers");
            String homeRoad = resultSet.getString("homeRoad").equalsIgnoreCase("H")
                    ? "vs" : "@";
            int team = resultSet.getInt("team");
            String opponent = homeRoad + " " + FranchiseUtils.getFranchiseCity(
                    resultSet.getInt("opponent"), connection);
            String result = resultSet.getString("result") + " " + 
                    GameUtils.getGameScore(gameId, team, connection); 
            
            playerGameStats.setAssists(assists);
            playerGameStats.setBlocks(blocks);
            playerGameStats.setDate(gameDate);
            playerGameStats.setFieldGoals(fieldGoals);
            playerGameStats.setFreeThrows(freeThrows);
            playerGameStats.setOffensiveRebounds(offensiveRebounds);
            playerGameStats.setDefensiveRebounds(defensiveRebounds);
            playerGameStats.setPersonalFouls(personalFouls);
            playerGameStats.setSteals(steals);
            playerGameStats.setThreePointers(threePointers);
            playerGameStats.setTotalRebounds(totalRebounds);
            playerGameStats.setTurnovers(turnovers);
            playerGameStats.setPoints(points);
            playerGameStats.setMinutes(minutes);
            playerGameStats.setOpponent(opponent);
            playerGameStats.setResult(result);
            
        } catch (SQLException ex) {
            Logger.getLogger(PlayerAveragesBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return playerGameStats;
    }
    
} // end PlayerGameStatsBuilder
