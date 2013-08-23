package coachj.lists;

import java.util.Arrays;
import java.util.List;

/**
 * Holds data about the tables used by the application
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 23/07/2013
 */
public final class AppTables {

    /**
     * List with all the application's tabled, used for database
     * checking operations
     */
    private final List<String> tableNames;

    /**
     * Constructor
     */
    public AppTables() {
        tableNames = Arrays.asList(
                "arena",
                "award",
                "city",
                "coach",
                "coach_awards",
                "coach_transaction",
                "country",
                "court_zone",
                "draft",
                "event",
                "first_name",
                "franchise",
                "franchise_season_log",
                "game",
                "game_news",
                "general_manager",
                "general_manager_award",
                "general_manager_transaction",
                "last_name",
                "league",
                "narration",
                "play_log",
                "play_type",
                "player",
                "player_award",
                "player_log",
                "player_transaction",
                "playoff_series",
                "position_profile",
                "referee",
                "season",
                "title");
    }

    /**
     * Returns a list with all the names of the application's tables
     * 
     * @return Object List<String>
     * @see List
     */
    public List<String> getTableNames() {
        return tableNames;
    }
} // end class AppTables
