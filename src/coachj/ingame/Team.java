package coachj.ingame;

import coachj.builders.CoachBuilder;
import coachj.dao.DatabaseDirectConnection;
import coachj.models.Coach;
import coachj.utils.FranchiseUtils;
import coachj.utils.PlayerUtils;
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
    private String abbreviature;
    private short coachId;
    private Coach coach;
    private short score = 0;
    private short timeoutsLeft = 3;
    private int lastTimeoutCall;
    private short fouls = 0;
    private short technicalFouls = 0;
    private short totalFouls = 0;
    private short totalPersonalFouls = 0;
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
    private short blockedShots = 0;
    private short assists = 0;
    private short turnovers = 0;
    private short currentRun = 0;
    private short longestRun = 0;
    private short currentLead = 0;
    private short largestLead = 0;
    private ArrayList<InGamePlayer> players = new ArrayList<>();
    DatabaseDirectConnection connection;

    /**
     * Constructor
     */
    public Team(short id, DatabaseDirectConnection connection) {
        this.id = id;
        this.connection = connection;

        /**
         * Retrieving data from database
         */
        this.completeName = FranchiseUtils.getFranchiseCompleteName(this.id, connection);
        this.abbreviature = FranchiseUtils.getFranchiseAbbreviature(this.id, connection);
        coachId = FranchiseUtils.getFranchiseCoachId(this.id, connection);
        CoachBuilder coachBuilder = new CoachBuilder();
        coachBuilder.fillAttributesFromDatabase(coachId, connection);
        this.coach = coachBuilder.generateCoachEntity();

        /**
         * Filling roster
         */
        fillRoster();
    }

    /**
     * Fill the team roster with its active players
     */
    private void fillRoster() {
        /**
         * Reordering roster
         */
        RosterUtils.reorderRoster(this.id, connection);

        /**
         * Retrieving player data
         */
        String sqlStatement = "SELECT id FROM player WHERE franchise = " + this.id
                + " AND active = true ORDER BY rosterPosition LIMIT 15";
        ResultSet resultSet = connection.getResultSet(sqlStatement);
        short playerId;
        short rosterPosition = 1;
        InGamePlayer player;

        try {
            while (resultSet.next()) {
                playerId = resultSet.getShort("id");
                player = new InGamePlayer(playerId, connection);
                
                /**
                 * Populating player's decision and movement arrays *
                 */
                PlayerUtils.populatePlayerArrays(player);

                /**
                 * If the player's roster position and less than or equal to 5,
                 * he's starting the game and his location is set, otherwise,
                 * his location is set to the bench (0)
                 */
                if (rosterPosition <= 5) {
                    player.setOnCourt(true);
                    player.setCurrentZoneLocation(14);
                } else {
                    player.setCurrentZoneLocation(0);
                }

                players.add(player);
                rosterPosition++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Team.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the tallest player on the court
     *
     * @return
     */
    public InGamePlayer getTallestPlayer() {

        InGamePlayer tallestPlayer = null;
        int tallestPlayerHeight = 0;

        /**
         * Iterating through the array list to find the tallest player
         */
        for (int i = 0; i < players.size(); i++) {
            InGamePlayer currentPlayer = players.get(i);

            if (currentPlayer.isOnCourt()
                    && currentPlayer.getBaseAttributes().getHeight() > tallestPlayerHeight) {
                tallestPlayer = currentPlayer;
            }
        }

        return tallestPlayer;
    }

    /**
     * Returns the best ball handler on the court
     *
     * @return
     */
    public InGamePlayer getBestBallHandler() {

        InGamePlayer bestBallHandler = null;
        int bestPlayerRate = 0;

        /**
         * Iterating through the array list to find the best ball handler
         */
        for (int i = 0; i < players.size(); i++) {
            InGamePlayer currentPlayer = players.get(i);

            if (currentPlayer.getBaseAttributes().getPlayable() && currentPlayer.isOnCourt()
                    && currentPlayer.getBaseAttributes().getBallHandling() > bestPlayerRate) {
                bestBallHandler = currentPlayer;
            }
        }

        return bestBallHandler;
    }

    /**
     * Returns the best jump shooter on the court
     *
     * @return
     */
    public InGamePlayer getBestJumpShooter() {

        InGamePlayer bestJumpShooter = null;
        int bestPlayerRate = 0;

        /**
         * Iterating through the array list to find the best ball handler
         */
        for (int i = 0; i < players.size(); i++) {
            InGamePlayer currentPlayer = players.get(i);

            if (currentPlayer.getBaseAttributes().getPlayable() && currentPlayer.isOnCourt()
                    && currentPlayer.getBaseAttributes().getPullUpJumper() > bestPlayerRate) {
                bestJumpShooter = currentPlayer;
            }
        }

        return bestJumpShooter;
    }

    /**
     * Returns the best playmaker on the court
     *
     * @return
     */
    public InGamePlayer getBestPlaymaker() {

        InGamePlayer bestPlaymaker = null;
        int bestPlayerRate = 0;

        /**
         * Iterating through the array list to find the best playmaker
         */
        for (int i = 0; i < players.size(); i++) {
            InGamePlayer currentPlayer = players.get(i);

            if (currentPlayer.getBaseAttributes().getPlayable() && currentPlayer.isOnCourt()
                    && (currentPlayer.getBaseAttributes().getPass()
                    + currentPlayer.getBaseAttributes().getCreativity() / 10) > bestPlayerRate) {
                bestPlaymaker = currentPlayer;
            }
        }

        return bestPlaymaker;
    }

    /**
     * Returns the best outside shooter on the court
     *
     * @return
     */
    public InGamePlayer getBestOutsideShooter() {

        InGamePlayer bestOutsideShooter = null;
        int bestPlayerRate = 0;

        /**
         * Iterating through the array list to find the best ball handler
         */
        for (int i = 0; i < players.size(); i++) {
            InGamePlayer currentPlayer = players.get(i);

            if (currentPlayer.getBaseAttributes().getPlayable() && currentPlayer.isOnCourt()
                    && currentPlayer.getBaseAttributes().getThreePointers() > bestPlayerRate) {
                bestOutsideShooter = currentPlayer;
            }
        }

        return bestOutsideShooter;
    }

    /**
     * Returns the best low post player on the court
     *
     * @return
     */
    public InGamePlayer getBestLowPostShooter() {

        InGamePlayer bestLowPostShooter = null;
        int bestPlayerRate = 0;

        /**
         * Iterating through the array list to find the best ball handler
         */
        for (int i = 0; i < players.size(); i++) {
            InGamePlayer currentPlayer = players.get(i);

            if (currentPlayer.getBaseAttributes().getPlayable() && currentPlayer.isOnCourt()
                    && currentPlayer.getBaseAttributes().getLowPost() > bestPlayerRate) {
                bestLowPostShooter = currentPlayer;
            }
        }

        return bestLowPostShooter;
    }

    /**
     * Returns the best dunker on the court
     *
     * @return
     */
    public InGamePlayer getBestDunker() {

        InGamePlayer bestDunker = null;
        int bestPlayerRate = 0;

        /**
         * Iterating through the array list to find the best ball handler
         */
        for (int i = 0; i < players.size(); i++) {
            InGamePlayer currentPlayer = players.get(i);

            if (currentPlayer.getBaseAttributes().getPlayable() && currentPlayer.isOnCourt()
                    && currentPlayer.getBaseAttributes().getDunk() > bestPlayerRate) {
                bestDunker = currentPlayer;
            }
        }

        return bestDunker;
    }

    /**
     * Returns the best quick shooter on the court
     *
     * @return
     */
    public InGamePlayer getBestQuickShooter() {

        InGamePlayer bestQuickShooter = null;
        int bestPlayerRate = 0;

        /**
         * Iterating through the array list to find the best ball handler
         */
        for (int i = 0; i < players.size(); i++) {
            InGamePlayer currentPlayer = players.get(i);

            if (currentPlayer.getBaseAttributes().getPlayable() && currentPlayer.isOnCourt()
                    && currentPlayer.getBaseAttributes().getCatchAndShootShot() > bestPlayerRate) {
                bestQuickShooter = currentPlayer;
            }
        }

        return bestQuickShooter;
    }

    /**
     * Returns the hottest shooter on the court
     *
     * @return
     */
    public InGamePlayer getHottestShooter() {

        InGamePlayer hottestShooter = null;
        int bestPlayerRate = 0;

        /**
         * Iterating through the array list to find the best ball handler
         */
        for (int i = 0; i < players.size(); i++) {
            InGamePlayer currentPlayer = players.get(i);

            if (currentPlayer.isOnCourt()
                    && currentPlayer.getOffensiveMomentum() > bestPlayerRate) {
                hottestShooter = currentPlayer;
            }
        }

        return hottestShooter;
    }

    /**
     * Returns the roster position for the player to be replaced
     *
     * @param gameData PlayGame object with data to be processed
     * @return The roster position of the player who'll be replaced or 0 if no
     * suitable player was found
     */
    public int getPlayerToBeReplaced(PlayGame gameData) {
        int playerToBeReplaced = 0;
        InGamePlayer currentPlayer;

        /**
         * Checking player to be replaced based on several criteria. We iterate
         * through the players array list to find it
         */
        for (int i = 0; i < this.players.size(); i++) {

            /**
             * Retrieving player
             */
            currentPlayer = this.players.get(i);

            /**
             * A player can be replace only if he's on the court and he's not
             * shooting free-throws
             */
            if (currentPlayer.isOnCourt() && !currentPlayer.isShootingFreeThrows()) {

                /**
                 * Player is not playable
                 */
                if (!currentPlayer.getBaseAttributes().getPlayable()) {
                    System.out.println("not playable"); // delete
                    return currentPlayer.getRosterPosition();
                }

                /**
                 * Player was ejected from the game
                 */
                if (currentPlayer.isEjected()) {
                    System.out.println("ejected"); // delete
                    return currentPlayer.getRosterPosition();
                }

                /**
                 * Player is in foul trouble, something that's ignored up from
                 * the last 3:00 of the 4th quarter and overtime
                 */
                if (gameData.getPeriod() > 3 && gameData.getTimeLeft() > 180                        
                        && currentPlayer.getSubstitutionTime() > gameData.getTimeLeft() + 60
                        && currentPlayer.getPersonalFouls() > 4) {
                    System.out.println("foul trouble in 4th"); // delete
                    return currentPlayer.getRosterPosition();
                }

                /**
                 * Player is in foul trouble prior to the 4th quarter
                 */
                if (gameData.getPeriod() < 4 && currentPlayer.getPersonalFouls()
                        > gameData.getPeriod()) {
                    System.out.println("foul trouble"); // delete
                    return currentPlayer.getRosterPosition();
                }

                /**
                 * Player has committed too many turnovers accordingly to the
                 * coach's patience attribute
                 */
                if (currentPlayer.getTurnovers() > gameData.getPeriod()
                        * this.coach.getPatience() / 25) {
                    System.out.println("too many turnovers"); // delete
                    return currentPlayer.getRosterPosition();
                }

                /**
                 * Player is tired and we aren't in the last 3:00 of the 4th
                 * quarter or overtime, when the tiredness of the starters is
                 * ignored
                 */
                if ((gameData.getPeriod() < 4 || (gameData.getPeriod() > 3
                        && gameData.getTimeLeft() > 180))
                        && currentPlayer.getSubstitutionTime() > gameData.getTimeLeft() + 60 + 60
                        && currentPlayer.getCurrentStaminaLevel()
                        < 40 + this.coach.getRotationUse() / 4) {
                    System.out.println("tired"); // delete
                    return currentPlayer.getRosterPosition();
                }

                /**
                 * Player is tired in the last 3:00 of the last quarter or
                 * overtime, when the tiredness of the starters is ignored
                 */
                if ((gameData.getPeriod() > 3 && gameData.getTimeLeft() < 180)
                        && currentPlayer.getRosterPosition() > 5
                        && currentPlayer.getSubstitutionTime() > gameData.getTimeLeft() + 60
                        && currentPlayer.getCurrentStaminaLevel()
                        < 40 + this.coach.getRotationUse() / 4) {
                    System.out.println("tired in 4th"); // delete
                    return currentPlayer.getRosterPosition();
                }

                /**
                 * Player has a very low rate when combining his offensive and
                 * defensive momenta and wasn't replace in the last minutes.
                 */
                if (currentPlayer.getSubstitutionTime() > gameData.getTimeLeft() + 60
                        && currentPlayer.getOffensiveMomentum() + currentPlayer
                        .getDefensiveMomentum() < 0 - this.coach.getPatience() / 10) {
                    System.out.println("bad momentum"); // delete
                    return currentPlayer.getRosterPosition();
                }

                /**
                 * It's a blowout and the starters are taken off the game
                 */
                if (gameData.getGap() > 10 + this.coach.getPatience() / 20 * 3
                        && currentPlayer.getSubstitutionTime() > gameData.getTimeLeft() + 60
                        && currentPlayer.getOffensiveMomentum() + currentPlayer
                        .getDefensiveMomentum() < 0 - this.coach.getPatience() / 10) {
                    System.out.println("blow out"); // delete
                    return currentPlayer.getRosterPosition();
                }
            }
        }
        return playerToBeReplaced;
    }

    /**
     * Returns the roster position for the player who'll replace another o ne
     *
     * @param playerToBeReplacedRosterPosition Roster position of the player to
     * be replaced
     * @param gameData PlayGame object with data to be processed
     * @return The roster position of the player who'll replace or 0 if no
     * suitable player was found
     */
    public int getPlayerToReplace(int playerToBeReplacedRosterPosition, PlayGame gameData) {
        int playerToReplace = 0;
        InGamePlayer currentPlayer;
        InGamePlayer playerToBeReplaced = this.players.get(playerToBeReplacedRosterPosition - 1);

        /**
         * Checking player to be replaced based on several criteria. We iterate
         * through the players array list to find it
         */
        for (int i = 0; i < this.players.size(); i++) {

            /**
             * Retrieving player
             */
            currentPlayer = this.players.get(i);

            /**
             * A player only can enter the game if he's in the, is playable and
             * wasn't ejected from it
             */
            if (!currentPlayer.isOnCourt() && currentPlayer.getBaseAttributes().getPlayable()
                    && !currentPlayer.isEjected()) {

                /**
                 * Game is not in the last 3:00 of the 4th quarter or overtime.
                 * Player hasn't committed too many turnovers, is not tired and
                 * wasn't replaced recently and plays at the same position as
                 * the player who'll be replaced
                 */
                if ((gameData.getPeriod() < 4 || (gameData.getPeriod() > 3
                        && gameData.getTimeLeft() > 180))
                        && currentPlayer.getPersonalFouls() <= gameData.getPeriod()
                        && currentPlayer.getTurnovers()
                        < gameData.getPeriod() * this.coach.getPatience() / 25
                        && currentPlayer.getSubstitutionTime() > gameData.getTimeLeft() + 60
                        && currentPlayer.getCurrentStaminaLevel()
                        > 40 + this.coach.getRotationUse() / 4
                        && currentPlayer.getBaseAttributes().getPosition()
                        .equals(playerToBeReplaced.getBaseAttributes().getPosition())) {
                    System.out.println("normal sub prior 4th"); // delete
                    return currentPlayer.getRosterPosition();
                }

                /**
                 * Game is in the last 3:00 of the fourth quarter or overtime,
                 * when the starters have priority to enter
                 */
                if (gameData.getPeriod() > 3 && gameData.getTimeLeft() < 180
                        && currentPlayer.getRosterPosition() < 5) {
                    System.out.println("normal sub in 4th"); // delete
                    return currentPlayer.getRosterPosition();
                }

                /**
                 * It's a blowout, so the starters are taken off the game
                 */
                if (gameData.getGap() > 10 + this.coach.getPatience() / 20 * 3
                        && !currentPlayer.isEjected()
                        && currentPlayer.getSubstitutionTime() > gameData.getTimeLeft() + 60
                        && currentPlayer.getCurrentStaminaLevel()
                        > 40 + this.coach.getRotationUse() / 4
                         && currentPlayer.getBaseAttributes().getPosition()
                        .equals(playerToBeReplaced.getBaseAttributes().getPosition())) {
                    System.out.println("sub in blowout"); // delete
                    return currentPlayer.getRosterPosition();
                }
            }
        }

        /**
         * No suitable player to replace was found, but the player to be
         * replaced is either injured, ejected or in foul trouble
         */
        for (int i = 0; i < this.players.size(); i++) {

            /**
             * Retrieving player
             */
            currentPlayer = this.players.get(i);
            if (playerToBeReplacedRosterPosition > 0) {
                if (!currentPlayer.isOnCourt() && currentPlayer.getBaseAttributes().getPlayable()
                        && !currentPlayer.isEjected()) {
                    return currentPlayer.getRosterPosition();
                }
            }
        }

        return playerToReplace;
    }

    /**
     * Returns a string with all the player on the floor
     *
     * @return
     */
    public String getPlayersOnTheFloor() {
        String playersOnTheFloor = null;

        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).isOnCourt()) {
                playersOnTheFloor += " " + this.players.get(i).getCompleteName();
            }
        }

        return playersOnTheFloor;
    }

    /* stat updating methods */
    public void updateScore(int increase) {
        this.score = (short) (this.score + increase);
    }

    public void updateTurnovers() {
        this.turnovers = (short) (this.turnovers + 1);
    }

    public void updateFouls() {
        this.fouls = (short) (this.fouls + 1);
    }
    
     public void updateTotalFouls() {
        this.totalFouls = (short) (this.totalFouls + 1);
    }

    public void updateFieldGoalsMade() {
        this.fieldGoalsMade = (short) (this.fieldGoalsMade + 1);
    }

    public void updateFieldGoalsAttempted() {
        this.fieldGoalsAttempted = (short) (this.fieldGoalsAttempted + 1);
    }

    public void updateFreeThrowsMade() {
        this.freeThrowsMade = (short) (this.freeThrowsMade + 1);
    }

    public void updateFreeThrowsAttempted() {
        this.freeThrowsAttempted = (short) (this.freeThrowsAttempted + 1);
    }

    public void updateThreePointersMade() {
        this.threePointersMade = (short) (this.threePointersMade + 1);
    }

    public void updateThreePointersAttempted() {
        this.threePointersAttempted = (short) (this.threePointersAttempted + 1);
    }

    public void updateOffensiveRebounds() {
        this.offensiveRebounds = (short) (this.offensiveRebounds + 1);
    }

    public void updateDefensiveRebounds() {
        this.defensiveRebounds = (short) (this.defensiveRebounds + 1);
    }

    public void updateSteals() {
        this.steals = (short) (this.steals + 1);
    }

    public void updateBlocks() {
        this.blocks = (short) (this.blocks + 1);
    }

    public void updateBlockedShots() {
        this.blockedShots = (short) (this.blockedShots + 1);
    }

    public void updateAssists() {
        this.assists = (short) (this.assists + 1);
    }

    public void updateTimeouts() {
        this.timeoutsLeft = (short) (this.timeoutsLeft - 1);
    }

    public void updateRuns(int basketPoints) {
        if (basketPoints > 0) {
            this.currentRun += basketPoints;

            if (this.currentRun > this.longestRun) {
                this.longestRun = this.currentRun;
            }
        } else {
            this.currentRun = 0;
        }
    }

    public void updateLeads(int basketPoints) {
        if (basketPoints > 0) {
            this.currentLead += basketPoints;

            if (this.currentLead > this.largestLead) {
                this.largestLead = this.currentLead;
            }
        } else {
            this.currentLead = 0;
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

    public String getAbbreviature() {
        return abbreviature;
    }

    public void setAbbreviature(String abbreviature) {
        this.abbreviature = abbreviature;
    }

    public int getLastTimeoutCall() {
        return lastTimeoutCall;
    }

    public void setLastTimeoutCall(int lastTimeoutCall) {
        this.lastTimeoutCall = lastTimeoutCall;
    }

    public DatabaseDirectConnection getConnection() {
        return connection;
    }

    public void setConnection(DatabaseDirectConnection connection) {
        this.connection = connection;
    }

    public short getTechnicalFouls() {
        return technicalFouls;
    }

    public void setTechnicalFouls(short technicalFouls) {
        this.technicalFouls = technicalFouls;
    }

    public short getBlockedShots() {
        return blockedShots;
    }

    public void setBlockedShots(short blockedShots) {
        this.blockedShots = blockedShots;
    }    

    public short getTotalFouls() {
        return totalFouls;
    }

    public void setTotalFouls(short totalFouls) {
        this.totalFouls = totalFouls;
    }

    public short getTotalPersonalFouls() {
        return totalPersonalFouls;
    }

    public void setTotalPersonalFouls(short totalPersonalFouls) {
        this.totalPersonalFouls = totalPersonalFouls;
    }
} // end class Team
