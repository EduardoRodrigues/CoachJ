package coachj.utils;

import coachj.dao.DatabaseDirectConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for games
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 28/08/2013
 */
public class GameUtils {

    /**
     * Calculate the attendance for the given game
     * 
     * @param gameType Type of the game (O) off-season, (R) regular season, (P) playoffs
     * @param awayTeam Away team's id
     * @param homeTeam Home team's id
     * @param arena Arena's id
     * @param connection Database connection used to retrieve data
     * @return 
     */
    public static int calculateGameAttendance(String gameType, short awayTeam,
            short homeTeam, short arena, DatabaseDirectConnection connection) {
        /**
         * Retrieving necessary data to calculate attendance
         */
        int attendance;
        int arenaCapacity = ArenaUtils.getArenaCapacity(arena, connection);
        int ticketHolders = FranchiseUtils.getFranchiseTicketHolders(homeTeam, connection);
        double awayTeamRecord = FranchiseUtils.getFranchiseRecord(awayTeam, connection);
        double homeTeamRecord = FranchiseUtils.getFranchiseRecord(homeTeam, connection);

        /**
         * If it's a playoff game, the arena is sellout. Otherwise, the
         * attendance is based on the record of both teams playing plus the
         * number of ticket holders of the home team
         */
        if (gameType.equalsIgnoreCase("P") || (homeTeamRecord + awayTeamRecord == 0)) {
            attendance = arenaCapacity;
        } else {
            attendance = (int) (ticketHolders + (arenaCapacity * homeTeamRecord)
                    + (arenaCapacity * awayTeamRecord));
            attendance = attendance > arenaCapacity ? arenaCapacity : attendance;
        }

        return attendance;
    }
    
    /**
     * Returns the given game's score
     * @param gameId Game's id
     * @param perspectiveFromTeam Team whose score will be showed first
     * @param connection Database connection used to retrieve data
     * @return 
     */
    public static String getGameScore(int gameId, int perspectiveFromTeam, 
            DatabaseDirectConnection connection) {
        String gameScore = null;
        ResultSet resultSet;
        String sqlStatement = "SELECT awayTeam, awayScore,homeScore "
                + " FROM game WHERE id = " + gameId;
        
        resultSet = connection.getResultSet(sqlStatement);
        
        try {
            resultSet.first();
            if (resultSet.getInt("awayTeam") == perspectiveFromTeam) {
                gameScore = resultSet.getString("awayScore") + "-" 
                        + resultSet.getString("homeScore");
            } else {
                gameScore = resultSet.getString("homeScore") + "-" 
                        + resultSet.getString("awayScore");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(GameUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return gameScore;
    }
   
} // end GameUtils
