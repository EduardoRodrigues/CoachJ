package coachj.structures;

/**
 * Stores information about player's stats
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 23/09/2013
 */
public class PlayerAverages {

    /**
     * Fields
     */
    private int jersey;
    private String completeName;
    private int minutesPerGame;
    private double pointsPerGame;
    private double fieldGoalPercentage;
    private double freeThrowPercentage;
    private double threePointerPercentage;
    private double reboundsPerGame;
    private double assistsPerGame;
    
    /**
     * Constructor
     */
    public PlayerAverages() {
    }

    /* getters and setters */
    public int getJersey() {
        return jersey;
    }

    public void setJersey(int jersey) {
        this.jersey = jersey;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public int getMinutesPerGame() {
        return minutesPerGame;
    }

    public void setMinutesPerGame(int minutesPerGame) {
        this.minutesPerGame = minutesPerGame;
    }

    public double getPointsPerGame() {
        return pointsPerGame;
    }

    public void setPointsPerGame(double pointsPerGame) {
        this.pointsPerGame = pointsPerGame;
    }

    public double getFieldGoalPercentage() {
        return fieldGoalPercentage;
    }

    public void setFieldGoalPercentage(double fieldGoalPercentage) {
        this.fieldGoalPercentage = fieldGoalPercentage;
    }

    public double getFreeThrowPercentage() {
        return freeThrowPercentage;
    }

    public void setFreeThrowPercentage(double freeThrowPercentage) {
        this.freeThrowPercentage = freeThrowPercentage;
    }

    public double getThreePointerPercentage() {
        return threePointerPercentage;
    }

    public void setThreePointerPercentage(double threePointerPercentage) {
        this.threePointerPercentage = threePointerPercentage;
    }

    public double getReboundsPerGame() {
        return reboundsPerGame;
    }

    public void setReboundsPerGame(double reboundsPerGame) {
        this.reboundsPerGame = reboundsPerGame;
    }

    public double getAssistsPerGame() {
        return assistsPerGame;
    }

    public void setAssistsPerGame(double assistsPerGame) {
        this.assistsPerGame = assistsPerGame;
    }
} // end PlayerAverages
