package coachj.structures;

/**
 * Stores information about scheduled games
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 23/09/2013
 */
public class ScheduleGame {

    /**
     * Fields
     */
    private String date;
    private String opponent;
    private String result;
    private String topScorer;
    private String topRebounder;
    private String topAssistant;
    
    /**
     * Constructor 
     */
    public ScheduleGame() {
    }

    /* getters and setters */
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTopScorer() {
        return topScorer;
    }

    public void setTopScorer(String topScorer) {
        this.topScorer = topScorer;
    }

    public String getTopRebounder() {
        return topRebounder;
    }

    public void setTopRebounder(String topRebounder) {
        this.topRebounder = topRebounder;
    }

    public String getTopAssistant() {
        return topAssistant;
    }

    public void setTopAssistant(String topAssistant) {
        this.topAssistant = topAssistant;
    }
} // end ScheduleGame
