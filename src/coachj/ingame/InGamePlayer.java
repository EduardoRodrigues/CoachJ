package coachj.ingame;

import coachj.builders.PlayerBuilder;
import coachj.dao.DatabaseDirectConnection;
import coachj.models.Player;

/**
 * Controls players when they're in a game
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0 29/08/2013
 */
public class InGamePlayer {

    /**
     * Fields
     */
    private short personalFouls = 0;
    private short fieldGoalsAttempted = 0;
    private short fieldGoalsMade = 0;
    private short freeThrowsAttempted = 0;
    private short freeThrowsMade = 0;
    private short threePointersAttempted = 0;
    private short threePointersMade = 0;
    private short offensiveRebounds = 0;
    private short defensiveRebounds = 0;
    private short steals = 0;
    private short blocks = 0;
    private short assists = 0;
    private short turnovers = 0;
    private short secondsOnCourt = 0;
    private short secondsInBench = 0;
    private boolean ejected = false;
    private boolean isOnCourt = false;
    private boolean hasBall = false;
    private boolean isShootingFreeThrows = false;
    private short defensiveMomentum = 0;
    private short offensiveMomentum = 0;
    private Player baseAttributes;
    private short jersey;
    private String completeNamePosition;
    /**
     * Constructor
     */
    public InGamePlayer(short id) {

        /**
         * Database connection
         */
        DatabaseDirectConnection connection = new DatabaseDirectConnection();
        // // connection.open();

        PlayerBuilder playerBuilder = new PlayerBuilder();
        playerBuilder.fillAttributesFromDatabase(id, connection);
        this.baseAttributes = playerBuilder.generatePlayerEntity();
        this.jersey = this.baseAttributes.getJersey();
        this.completeNamePosition = this.baseAttributes.getPosition() + " " 
                + this.baseAttributes.getFirstName() + " " + this.baseAttributes.getLastName();
    }

    /* standard getters and setters */
    public short getPersonalFouls() {
        return personalFouls;
    }

    public void setPersonalFouls(short personalFouls) {
        this.personalFouls = personalFouls;
    }

    public short getFieldGoalsAttempted() {
        return fieldGoalsAttempted;
    }

    public void setFieldGoalsAttempted(short fieldGoalsAttempted) {
        this.fieldGoalsAttempted = fieldGoalsAttempted;
    }

    public short getFieldGoalsMade() {
        return fieldGoalsMade;
    }

    public void setFieldGoalsMade(short fieldGoalsMade) {
        this.fieldGoalsMade = fieldGoalsMade;
    }

    public short getFreeThrowsAttempted() {
        return freeThrowsAttempted;
    }

    public void setFreeThrowsAttempted(short freeThrowsAttempted) {
        this.freeThrowsAttempted = freeThrowsAttempted;
    }

    public short getFreeThrowsMade() {
        return freeThrowsMade;
    }

    public void setFreeThrowsMade(short freeThrowsMade) {
        this.freeThrowsMade = freeThrowsMade;
    }

    public short getThreePointersAttempted() {
        return threePointersAttempted;
    }

    public void setThreePointersAttempted(short threePointersAttempted) {
        this.threePointersAttempted = threePointersAttempted;
    }

    public short getThreePointersMade() {
        return threePointersMade;
    }

    public void setThreePointersMade(short threePointersMade) {
        this.threePointersMade = threePointersMade;
    }

    public short getOffensiveRebounds() {
        return offensiveRebounds;
    }

    public void setOffensiveRebounds(short offensiveRebounds) {
        this.offensiveRebounds = offensiveRebounds;
    }

    public short getDefensiveRebounds() {
        return defensiveRebounds;
    }

    public void setDefensiveRebounds(short defensiveRebounds) {
        this.defensiveRebounds = defensiveRebounds;
    }

    public short getSteals() {
        return steals;
    }

    public void setSteals(short steals) {
        this.steals = steals;
    }

    public short getBlocks() {
        return blocks;
    }

    public void setBlocks(short blocks) {
        this.blocks = blocks;
    }

    public short getAssists() {
        return assists;
    }

    public void setAssists(short assists) {
        this.assists = assists;
    }

    public short getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(short turnovers) {
        this.turnovers = turnovers;
    }

    public short getSecondsOnCourt() {
        return secondsOnCourt;
    }

    public void setSecondsOnCourt(short secondsOnCourt) {
        this.secondsOnCourt = secondsOnCourt;
    }

    public short getSecondsInBench() {
        return secondsInBench;
    }

    public void setSecondsInBench(short secondsInBench) {
        this.secondsInBench = secondsInBench;
    }

    public boolean isEjected() {
        return ejected;
    }

    public void setEjected(boolean ejected) {
        this.ejected = ejected;
    }

    public boolean isIsOnCourt() {
        return isOnCourt;
    }

    public void setIsOnCourt(boolean isOnCourt) {
        this.isOnCourt = isOnCourt;
    }

    public boolean isHasBall() {
        return hasBall;
    }

    public void setHasBall(boolean hasBall) {
        this.hasBall = hasBall;
    }

    public boolean isIsShootingFreeThrows() {
        return isShootingFreeThrows;
    }

    public void setIsShootingFreeThrows(boolean isShootingFreeThrows) {
        this.isShootingFreeThrows = isShootingFreeThrows;
    }

    public short getDefensiveMomentum() {
        return defensiveMomentum;
    }

    public void setDefensiveMomentum(short defensiveMomentum) {
        this.defensiveMomentum = defensiveMomentum;
    }

    public short getOffensiveMomentum() {
        return offensiveMomentum;
    }

    public void setOffensiveMomentum(short offensiveMomentum) {
        this.offensiveMomentum = offensiveMomentum;
    }

    public Player getBaseAttributes() {
        return baseAttributes;
    }

    public void setBaseAttributes(Player baseAttributes) {
        this.baseAttributes = baseAttributes;
    }    

    public short getJersey() {
        return jersey;
    }

    public void setJersey(short jersey) {
        this.jersey = jersey;
    }

    public String getCompleteNamePosition() {
        return completeNamePosition;
    }

    public void setCompleteNamePosition(String completeNamePosition) {
        this.completeNamePosition = completeNamePosition;
    }   
    
} // end class InGamePlayer
