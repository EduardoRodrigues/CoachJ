package coachj.enums;

/**
 * Enum that holds the values for the stats of the game
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 08/09/2013
 */
public enum GameStats {

    POINTS(0),
    PERSONAL_FOULS(1),
    FIELD_GOALS_ATTEMPTED(2),
    FIELD_GOALS_MADE(3),
    FREE_THROWS_ATTEMPTED(4),
    FREE_THROWS_MADE(5),
    THREE_POINTERS_ATTEMPTED(6),
    THREE_POINTERS_MADE(7),
    OFFENSIVE_REBOUNDS(8),
    DEFENSIVE_REBOUNDS(9),
    STEALS(10),
    BLOCKS(11),
    ASSISTS(12),
    TURNOVERS(13);
    private final int stat;

    private GameStats(int stat) {
        this.stat = stat;
    }

    public int getStat() {
        return stat;
    }
}
