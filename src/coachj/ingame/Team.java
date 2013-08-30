package coachj.ingame;

import coachj.builders.CoachBuilder;
import coachj.dao.DatabaseDirectConnection;
import coachj.models.Coach;
import coachj.utils.FranchiseUtils;
import coachj.utils.RosterUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stores information about a team which is playing a game
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 28/08/2013
 */
public class Team {

    private short id;
    private String completeName;
    private short coachId;
    private Coach coach;
    private short score = 0;
    private short timeoutsLeft = 3;
    private short fouls = 0;
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
    private short currentRun = 0;
    private short longestRun = 0;
    private short currentLead = 0;
    private short largestLead = 0;
    private ArrayList<InGamePlayer> players = new ArrayList<>();
    DatabaseDirectConnection connection = new DatabaseDirectConnection();

    /**
     * Constructor
     */
    public Team(short id) {
        this.id = id;

        /**
         * Database connection
         */
        // // connection.open();

        /**
         * Retrieving data from database
         */
        this.completeName = FranchiseUtils.getFranchiseCompleteName(this.id, connection);
        coachId = FranchiseUtils.getFranchiseCoachId(this.id, connection);
        CoachBuilder coachBuilder = new CoachBuilder();
        coachBuilder.fillAttributesFromDatabase(coachId, connection);
        this.coach = coachBuilder.generateCoachEntity();

        /**
         * Filling roster
         */
        fillRoster();
        
        /**
         * Closing database connection
         */
        connection.close();
    }

    private void fillRoster() {
        /**
         * Reordering roster
         */
        RosterUtils.reorderRoster(this.id, connection);
        
        /**
         * Retrieving player data
         */
        String sqlStatement = "SELECT id FROM player WHERE franchise = " + this.id + 
                " AND isActive = true ORDER BY rosterPosition LIMIT 15";
        ResultSet resultSet = connection.getResultSet(sqlStatement);
        short playerId;
        InGamePlayer player;
        
        try {
            while (resultSet.next()) {
                playerId = resultSet.getShort("id");
                player = new InGamePlayer(playerId);
                players.add(player);            
            }
        } catch (SQLException ex) {
            Logger.getLogger(Team.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    /* getters and setters */
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public short getCoachId() {
        return coachId;
    }

    public void setCoachId(short coachId) {
        this.coachId = coachId;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
    }

    public short getTimeoutsLeft() {
        return timeoutsLeft;
    }

    public void setTimeoutsLeft(short timeoutsLeft) {
        this.timeoutsLeft = timeoutsLeft;
    }

    public short getFouls() {
        return fouls;
    }

    public void setFouls(short fouls) {
        this.fouls = fouls;
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

    public short getCurrentRun() {
        return currentRun;
    }

    public void setCurrentRun(short currentRun) {
        this.currentRun = currentRun;
    }

    public short getLongestRun() {
        return longestRun;
    }

    public void setLongestRun(short longestRun) {
        this.longestRun = longestRun;
    }

    public short getCurrentLead() {
        return currentLead;
    }

    public void setCurrentLead(short currentLead) {
        this.currentLead = currentLead;
    }

    public short getLargestLead() {
        return largestLead;
    }

    public void setLargestLead(short largestLead) {
        this.largestLead = largestLead;
    }

    public ArrayList<InGamePlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<InGamePlayer> players) {
        this.players = players;
    }    
    
} // end class Team
