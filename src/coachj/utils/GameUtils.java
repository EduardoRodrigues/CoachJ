package coachj.utils;

import coachj.dao.DatabaseDirectConnection;

/**
 * Utility class for games
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 28/08/2013
 */
public class GameUtils {

    public static int calculateGameAttendance(String gameType, short awayTeam,
            short homeTeam, short arena, DatabaseDirectConnection connection) {

        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }

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
} // end GameUtils
