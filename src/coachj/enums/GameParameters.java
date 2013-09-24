package coachj.enums;

/**
 * Enum that holds the values for several parameters used during the game
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 06/09/2013
 */
public enum GameParameters {

    BASKET_COURT_SPOT(8),
    SHOTCLOCK(24),
    PERSONAL_FOULS_LIMIT(6),
    TIMEOUTS_PER_QUARTER(3),
    QUARTER_LENGTH(720),
    OVERTIME_LENGTH(300),
    DEFENSIVE_HALFCOURT_DIFFICULTY (95),
    OFFENSIVE_HALFCOURT_DIFFICULTY (90),
    THREE_POINTER_DIFFICULTY (90),
    FIELD_GOAL_DIFFICULTY (85),
    LAYUP_DIFFICULTY (75),
    TURNAROUND_DIFFICULTY (85),
    FLOATING_JUMPER_DIFFICULTY (90),
    RUNNING_JUMPER_DIFFICULTY (85),
    SCOOP_SHOT_DIFFICULTY (90),
    FINGER_ROLL_DIFFICULTY (85),
    DUNK_DIFFICULTY (80),
    FREE_THROW_DIFFICULTY (80),
    MAX_SUBSTITUTIONS (2),
    COLLECTIVE_FOULS_LIMIT (5);
    private final int parameterValue;

    private GameParameters(int parameterValue) {
        this.parameterValue = parameterValue;
    }

    public int getParameterValue() {
        return parameterValue;
    }
}
