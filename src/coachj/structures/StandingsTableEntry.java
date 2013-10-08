package coachj.structures;

/**
 * Stores information about teams displayed in the standings table
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 29/09/2013
 */
public class StandingsTableEntry {

    /**
     * Fields
     */
    private String seed;
    private String team;
    private String games;
    private String record;
    private String percentage;
    private String gamesBehind;
    private String home;
    private String road;
    private String streak;
    private String last10;
    
    /**
     * Constructor
     */
    public StandingsTableEntry() {
    }

    /* getters and setters */
    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getGamesBehind() {
        return gamesBehind;
    }

    public void setGamesBehind(String gamesBehind) {
        this.gamesBehind = gamesBehind;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getStreak() {
        return streak;
    }

    public void setStreak(String streak) {
        this.streak = streak;
    }

    public String getLast10() {
        return last10;
    }

    public void setLast10(String last10) {
        this.last10 = last10;
    }    

    public String getGames() {
        return games;
    }

    public void setGames(String games) {
        this.games = games;
    }
} // end StandingsTableEntry
