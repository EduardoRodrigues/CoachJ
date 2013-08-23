package coachj.enums;

/**
 * Enum that holds the different status a season can be in
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0 
 * @date 22/07/2013
 */
public enum SeasonStatus {

    OFF_SEASON(0),
    DRAFT (1),
    PRE_SEASON(2),    
    SEASON(3),
    PLAYOFFS(4);
    private final int status;

    private SeasonStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
