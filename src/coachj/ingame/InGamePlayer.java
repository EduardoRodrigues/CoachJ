package coachj.ingame;

import coachj.builders.PlayerBuilder;
import coachj.dao.DatabaseDirectConnection;
import coachj.enums.CourtZones;
import coachj.enums.GameParameters;
import coachj.models.Coach;
import coachj.models.Player;
import coachj.utils.CourtUtils;
import coachj.utils.MathUtils;
import coachj.utils.TimeUtils;
import java.util.ArrayList;
import java.util.Collections;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    private short points = 0;
    private int playingTime = 0;
    private short personalFouls = 0;
    private short technicalFouls = 0;
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
    private short secondsOnCourt = 0;
    private short secondsInBench = 0;
    private boolean ejected = false;
    private boolean onCourt = false;
    private boolean hasBall = false;
    private boolean shootingFreeThrows = false;
    private short defensiveMomentum = 0;
    private short offensiveMomentum = 0;
    private int rosterPosition = 0;
    private int currentZoneLocation = 0;
    private int staminaLevel;
    private int currentStaminaLevel;
    private int staminaAdjustment;
    private int substitutionTime = 720;
    private double performanceIndex = 0;
    private double offensiveIndex = 0;
    private double defensiveIndex = 0;
    private double efficiencyIndex = 0;
    private Player baseAttributes;
    private ArrayList<String> decisionArray = new ArrayList<>();
    private ArrayList<Integer> movementArray = new ArrayList<>();
    private ArrayList<String> movementLog = new ArrayList<>();

    /**
     * Constructor
     */
    public InGamePlayer(short id, DatabaseDirectConnection connection) {

        PlayerBuilder playerBuilder = new PlayerBuilder();
        playerBuilder.fillAttributesFromDatabase(id, connection);
        this.baseAttributes = playerBuilder.buildPlayerEntity();
        this.rosterPosition = this.baseAttributes.getRosterPosition();
        this.staminaLevel = this.baseAttributes.getStamina()
                - this.baseAttributes.getAccumulatedFatigue();
        this.currentStaminaLevel = this.staminaLevel;
    }

    /* in game methods */
    /**
     * Returns player's next action
     *
     * @param game Game data
     * @return
     */
    public String getPlayerDecision(PlayGame game) {
        String playerDecision;

        try {
            System.out.println("Entering player decision"); // delete
            /**
             * Objects to access team, players and coach information
             */
            Team team = game.getTeams().get(game.getBallPossession());
            Coach coach = game.getTeams().get(game.getBallPossession()).getCoach();

            /**
             * Best passing options on the court
             */
            InGamePlayer bestPlaymaker = team.getBestPlaymaker();
            InGamePlayer bestJumpShooter = team.getBestJumpShooter();
            InGamePlayer bestOutsideShooter = team.getBestOutsideShooter();
            InGamePlayer bestLowPostShooter = team.getBestLowPostShooter();
            InGamePlayer bestDunker = team.getBestDunker();
            InGamePlayer bestQuickShooter = team.getBestQuickShooter();
            InGamePlayer hottestShooter = team.getHottestShooter();
            System.out.println("Got best passing options"); // delete
            System.out.println("Best Playmaker: " + bestPlaymaker.getCompleteName()); // delete

            /**
             * Checking if the player's team is currently trailing
             */
            boolean teamIsTrailing = game.getBallPossession() != game.getLeadingTeam() && game.getGap() != 0;

            System.out.println("Checked trailing team"); // delete

            /**
             * Checking for intentional fouls at the end of the game.
             */
            if (game.getPeriod() > 3 && game.getTimeLeft() < 60
                    && game.getBallPossession() == game.getLeadingTeam()
                    && game.getGap() < (game.getTimeLeft() / 20 * 3)) {
                playerDecision = "intentional foul";
                return playerDecision;
            }

            /**
             * If the shotclock is about to expire, shoot
             */
            if (game.getShotClock() <= 3) {
                playerDecision = "shoot";
                return playerDecision;
            }

            /**
             * If the ball is on the defensive halfcourt, pushes it to the offensive one
             */
            if (this.getCurrentZoneLocation() == CourtZones.DEFENSIVE_HALFCOURT.getCourtZone()) {
                playerDecision = "ball to offensive court";
                return playerDecision;
            }

            /**
             * If there's time and the ball is way behind the three-point line, move it ahead
             */
            if (this.getCurrentZoneLocation() == CourtZones.OFFENSIVE_HALFCOURT.getCourtZone()) {
                playerDecision = "carry ball";
                return playerDecision;
            }

            /**
             * If the shotclock is dead and the team is trailing by 3 in fourth quarter or overtime,
             * take a quick three-pointer
             */
            if (teamIsTrailing && game.getPeriod() > 3
                    && game.getTimeLeft() < GameParameters.SHOTCLOCK.getParameterValue()
                    && game.getGap() == 3) {
                playerDecision = "quick three-pointer";
                return playerDecision;
            }

            /**
             * If the shotclock's dead and team is trailing by 2 or 1 in fourth quarter or overtime,
             * take a shot very close to the end of time
             */
            if (teamIsTrailing && game.getPeriod() > 3
                    && game.getTimeLeft() < GameParameters.SHOTCLOCK.getParameterValue()
                    && game.getShotClock() < GameParameters.SHOTCLOCK.getParameterValue()
                    && game.getShotClock() > 8
                    && game.getGap() < 3) {
                playerDecision = "buzzer shot play";
                return playerDecision;
            }

            /**
             * If shotclock's dead and the game's tied in fourth quarter or overtime, take a shot
             * very close to the end of time
             */
            if (game.getGap() == 0 && game.getPeriod() > 3
                    && game.getTimeLeft() < GameParameters.SHOTCLOCK.getParameterValue()
                    && game.getShotClock() < GameParameters.SHOTCLOCK.getParameterValue()
                    && game.getShotClock() > 8) {
                playerDecision = "buzzer shot play";
                return playerDecision;
            }

            /**
             * If shotclock's dead in the first three quarters, take a shot very close to the end of
             * time to prevent the opponent from taking another shot
             */
            if (game.getPeriod() < 4
                    && game.getTimeLeft() < GameParameters.SHOTCLOCK.getParameterValue()
                    && game.getShotClock() < GameParameters.SHOTCLOCK.getParameterValue()
                    && game.getShotClock() > 8) {
                playerDecision = "buzzer shot play";
                return playerDecision;
            }

            /**
             * If the team is trailing late in the last 2 minutes of the fourth quarter or overtime
             * by a margin between 9 and 12 points, take a quick three-pointer
             */
            if (teamIsTrailing && game.getPeriod() > 3 && game.getTimeLeft() < 120
                    && (game.getGap() >= 9 && game.getGap() <= 12)) {
                playerDecision = "quick three-pointer";
                return playerDecision;
            }

            /**
             * If the team is trailing late in the last 2 minutes of the fourth quarter or overtime
             * by a margin between 7 and 8 points, take a quick shot
             */
            if (teamIsTrailing && game.getPeriod() > 3 && game.getTimeLeft() < 120
                    && (game.getGap() >= 7 && game.getGap() <= 8)) {
                playerDecision = "quick shot";
                return playerDecision;
            }

            /**
             * If the last event was a loose-ball, get the ball to playmaker to initiate the offense
             */
            if ((game.getLastEvent().equalsIgnoreCase("loose-ball")
                    || game.getLastEvent().equalsIgnoreCase("ball to offensive court")
                    || game.getLastEvent().equalsIgnoreCase("after turnover inbound pass")
                    || game.getLastEvent().equalsIgnoreCase("after basket inbound pass")
                    || game.getLastEvent().equalsIgnoreCase("carry ball")
                    || game.getLastEvent().equalsIgnoreCase("inbound pass")
                    || game.getLastEvent().equalsIgnoreCase("defensive rebound"))
                    && !this.equals(bestPlaymaker)
                    && !this.getBaseAttributes().getPosition()
                    .equalsIgnoreCase("PG")) {
                playerDecision = "ball to playmaker";
                return playerDecision;
            }

            System.out.println("Before court vision options"); // delete
            /**
             * If the player has a good court vision and a good pass, he'll try to find out a good
             * option to pass. A comparison between player's court vision and a randomly generated
             * integer between 0 and 100 is necessary to avoid repetitive decisions by the same
             * player
             */
            if (this.baseAttributes.getCourtVision() > MathUtils.generateRandomInt(0, 100)
                    && this.baseAttributes.getCourtVision() > game.getCourtVisionAverage()
                    && this.baseAttributes.getPass() > game.getPassAverage()
                    && (this.baseAttributes.getPosition().equalsIgnoreCase("PG")
                    || this.baseAttributes.getPosition2().equalsIgnoreCase("SG"))) {

                /**
                 * If the player is a highly creative one, he'll try to throw out unexpected passes.
                 * A comparison between player's creativity and a randomly generated integer between
                 * 0 and 100 is necessary to avoid repetitive decisions by the same player
                 */
                System.out.println("Before creative options"); // delete
                if (this.baseAttributes.getCreativity() > MathUtils.generateRandomInt(0, 100)
                        && this.baseAttributes.getCreativity() > (game.getCreativityAverage() * 1.25)) {

                    /**
                     * Behind the back pass
                     */
                    if (this.baseAttributes.getCreativity() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getCourtVision() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getBehindTheBackPass() > MathUtils.generateRandomInt(0, 100)) {
                        playerDecision = "behind-the-back pass";
                        return playerDecision;
                    }

                    /**
                     * No-look pass
                     */
                    if (this.baseAttributes.getCreativity() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getCourtVision() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getNoLookPass() > MathUtils.generateRandomInt(0, 100)) {
                        playerDecision = "no-look pass";
                        return playerDecision;
                    }

                    /**
                     * No-look pass for an dunker in the dunking area
                     */
                    if (this.baseAttributes.getCreativity() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getCourtVision() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getNoLookPass() > MathUtils.generateRandomInt(0, 100)
                            && !this.equals(bestDunker)
                            && game.getDunkingZone().contains(bestDunker.getCurrentZoneLocation())) {
                        playerDecision = "no-look pass to dunker";
                        return playerDecision;
                    }

                    /**
                     * No-look pass for an shooter in the jump shot area
                     */
                    if (this.baseAttributes.getCreativity() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getCourtVision() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getNoLookPass() > MathUtils.generateRandomInt(0, 100)
                            && !this.equals(bestJumpShooter)
                            && game.getJumpShotZone().contains(bestJumpShooter.getCurrentZoneLocation())) {
                        playerDecision = "no-look pass to jump shooter";
                        return playerDecision;
                    }

                    /**
                     * No-look pass for an shooter in the perimeter
                     */
                    if (this.baseAttributes.getCreativity() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getCourtVision() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getNoLookPass() > MathUtils.generateRandomInt(0, 100)
                            && !this.equals(bestOutsideShooter)
                            && game.getThreePointerZone().contains(bestOutsideShooter.getCurrentZoneLocation())) {
                        playerDecision = "no-look pass to outside shooter";
                        return playerDecision;
                    }

                    /**
                     * Bounce pass to a cutting dunker in the lane
                     */
                    if (this.baseAttributes.getCreativity() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getCourtVision() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getBouncePass() > MathUtils.generateRandomInt(0, 100)
                            && !this.equals(bestDunker)
                            && game.getDunkingZone().contains(bestDunker.getCurrentZoneLocation())) {
                        playerDecision = "bounce pass to dunker";
                        return playerDecision;
                    }

                    /**
                     * Alley-oop pass to a cutting dunker in the lane
                     */
                    if (this.baseAttributes.getCreativity() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getCourtVision() > MathUtils.generateRandomInt(0, 100)
                            && this.baseAttributes.getAlleyOopPass() > MathUtils.generateRandomInt(0, 100)
                            && !this.equals(bestDunker)
                            && game.getDunkingZone().contains(bestDunker.getCurrentZoneLocation())) {
                        playerDecision = "alley-oop pass to dunker";
                        return playerDecision;
                    }
                }

                /**
                 * Giving the ball to the hottest scorer. Since this decision is more based in
                 * practice and discipline by the player, the discipline is tested instead of
                 * creativity
                 */
                if (this.baseAttributes.getDiscipline() > MathUtils.generateRandomInt(0, 100)
                        && !this.equals(hottestShooter)
                        && hottestShooter.getOffensiveMomentum() > 15) {
                    playerDecision = "ball to hottest shooter";
                    return playerDecision;
                }

                /**
                 * Comparing passing options to decide which one is the best. Since this decision is
                 * more based in practice and discipline by the player, the discipline is tested
                 * instead of creativity
                 */
                System.out.println("Before comparing options"); // delete
                if (bestLowPostShooter.getOffensiveMomentum() > 0
                        && bestLowPostShooter.baseAttributes.getLowPost()
                        > game.getLowPostAverage()
                        && bestLowPostShooter.baseAttributes.getLowPost()
                        > bestJumpShooter.baseAttributes.getJump()
                        && bestLowPostShooter.baseAttributes.getLowPost()
                        > bestQuickShooter.baseAttributes.getCatchAndShootShot()
                        && bestLowPostShooter.baseAttributes.getLowPost()
                        > bestOutsideShooter.baseAttributes.getThreePointers()) {
                    playerDecision = "low-post play";
                    return playerDecision;
                }

                if (bestJumpShooter.getOffensiveMomentum() > 0
                        && bestJumpShooter.baseAttributes.getPullUpJumper()
                        > game.getJumpShotAverage()
                        && bestJumpShooter.baseAttributes.getPullUpJumper()
                        > bestQuickShooter.baseAttributes.getCatchAndShootShot()
                        && bestJumpShooter.baseAttributes.getPullUpJumper()
                        > bestOutsideShooter.baseAttributes.getThreePointers()) {
                    playerDecision = "ball to jump shooter";
                    return playerDecision;
                }

                if (bestQuickShooter.getOffensiveMomentum() > 0
                        && bestQuickShooter.baseAttributes.getCatchAndShootShot()
                        > game.getQuickShotAverage()
                        && bestQuickShooter.baseAttributes.getCatchAndShootShot()
                        > bestOutsideShooter.baseAttributes.getThreePointers()) {
                    playerDecision = "quick shot";
                    return playerDecision;
                }

                if (bestOutsideShooter.getOffensiveMomentum() > 0
                        && bestOutsideShooter.baseAttributes.getThreePointers()
                        > game.getThreePointersAverage()) {
                    playerDecision = "ball to outside shooter";
                    return playerDecision;
                }
            }

            System.out.println("Before three pointer zone options"); // delete
            /**
             * If the player isn't a good passer or a creative playmaker, he'll assess his next
             * moves based on where on the court he is, who is guarding him and his abilities
             *
             * The first batch of decisions are taken if the player has the ball in the perimeter
             * area
             */
            if (game.getThreePointerZone().contains(this.currentZoneLocation)) {

                /**
                 * Player is an above-the-average outside shooter and has an open look
                 */
                if (this.baseAttributes.getThreePointers() > game.getThreePointersAverage() * 1.2
                        && game.isOpenLook()) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player is an above-the-average outside shooter, and is on a mismatch against a
                 * smaller player, and is shooting above .300, and the shotclock is within coach's
                 * tempo dynamics
                 */
                if (this.baseAttributes.getThreePointers() > game.getThreePointersAverage() * 1.2
                        && this.baseAttributes.getHeight()
                        > game.getActiveDefensivePlayer().baseAttributes.getHeight()
                        && this.getThreePointersShootingPercentage() > 0.3
                        && game.getShotClock() < 5 + coach.getTempo() / 10) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player is an above-the-average outside shooter, and is shooting above .300, and
                 * the shotclock is within coach's tempo dynamics
                 */
                if (this.baseAttributes.getThreePointers() > game.getThreePointersAverage() * 1.2
                        && this.getThreePointersShootingPercentage() > 0.3
                        && game.getShotClock() < 5 + coach.getTempo() / 10) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player isn't a good outside shooter, but is a good passer, and the shotclock is
                 * above coach's tempo dynamics
                 */
                if (this.baseAttributes.getPass() > game.getPassAverage()
                        && game.getShotClock() > 5 + coach.getTempo() / 10) {
                    playerDecision = "pass";
                    return playerDecision;
                }

                /**
                 * Player isn't a good outside shooter and is a big man, and the shotclock is above
                 * coach's tempo dynamics
                 */
                if ((this.baseAttributes.getPosition().equals("C")
                        || this.baseAttributes.getPosition2().equals("C"))
                        && game.getShotClock() > 5 + coach.getTempo() / 10) {
                    playerDecision = "pass";
                    return playerDecision;
                }

                /**
                 * Player is neither a good outside shooter or passer, but can drive the ball, and
                 * the shotclock is above coach's tempo dynamics
                 */
                if (this.baseAttributes.getDrive() > game.getDriveAverage() * 1.2
                        && game.getShotClock() > 5 + coach.getTempo() / 10
                        && !game.getLastEvent().equalsIgnoreCase("drive")) {
                    playerDecision = "drive";
                    return playerDecision;
                }
            }

            /**
             * Decisions taken if the player has the ball in the jump shot area
             */
            if (game.getJumpShotZone().contains(this.currentZoneLocation)) {

                /**
                 * Player is a good jump shooter and has an open look
                 */
                if (this.baseAttributes.getPullUpJumper() > game.getJumpShotAverage() * 1.2
                        && game.isOpenLook()) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player is a good jump shooter, and is on a mismatch against a smaller player, and
                 * is shooting above .300, and the shotclock is within coach's tempo dynamics
                 */
                if (this.baseAttributes.getPullUpJumper() > game.getJumpShotAverage() * 1.2
                        && this.baseAttributes.getHeight()
                        > game.getActiveDefensivePlayer().baseAttributes.getHeight()
                        && this.getFieldGoalsShootingPercentage() > 0.3
                        && game.getShotClock() < 5 + coach.getTempo() / 10) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player is a good jump shooter, and is shooting above .300, and the shotclock is
                 * within coach's tempo dynamics
                 */
                if (this.baseAttributes.getPullUpJumper() > game.getJumpShotAverage() * 1.2
                        && this.getFieldGoalsShootingPercentage() > 0.3
                        && game.getShotClock() < 5 + coach.getTempo() / 10) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player isn't a good jump shooter, but is a good passer, and the shotclock is
                 * above coach's tempo dynamics
                 */
                if (this.baseAttributes.getPass() > game.getPassAverage()
                        && game.getShotClock() > 5 + coach.getTempo() / 10) {
                    playerDecision = "pass";
                    return playerDecision;
                }

                /**
                 * Player isn't a good jump shooter and is a big man, and the shotclock is above
                 * coach's tempo dynamics
                 */
                if ((this.baseAttributes.getPosition().equals("C")
                        || this.baseAttributes.getPosition2().equals("C"))
                        && game.getShotClock() > 5 + coach.getTempo() / 10) {
                    playerDecision = "pass";
                    return playerDecision;
                }

                /**
                 * Player is neither a good jump shooter or passer, but can drive the ball, and the
                 * shotclock is above coach's tempo dynamics
                 */
                if (this.baseAttributes.getDrive() > game.getDriveAverage() * 1.2
                        && game.getShotClock() > 5 + coach.getTempo() / 10
                        && !game.getLastEvent().equalsIgnoreCase("drive")) {
                    playerDecision = "drive";
                    return playerDecision;
                }
            }

            /**
             * Decisions taken if the player has the ball in the low-post area
             */
            if (game.getLowPostZone().contains(this.currentZoneLocation)) {

                /**
                 * Player is a good low-post shooter and has an open look
                 */
                if (this.baseAttributes.getLowPost() > game.getLowPostAverage() * 1.2
                        && game.isOpenLook()) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player is a good low-post shooter, and is on a mismatch against a smaller player,
                 * and is shooting above .300, and the shotclock is within coach's tempo dynamics
                 */
                if (this.baseAttributes.getLowPost() > game.getLowPostAverage() * 1.2
                        && this.baseAttributes.getHeight()
                        > game.getActiveDefensivePlayer().baseAttributes.getHeight()
                        && this.getFieldGoalsShootingPercentage() > 0.3
                        && game.getShotClock() < 5 + coach.getTempo() / 10) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player is a good low-post shooter, and is shooting above .300, and the shotclock
                 * is within coach's tempo dynamics
                 */
                if (this.baseAttributes.getLowPost() > game.getLowPostAverage() * 1.2
                        && this.getFieldGoalsShootingPercentage() > 0.3
                        && game.getShotClock() < 5 + coach.getTempo() / 10) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player isn't a good low-post shooter, but is on a mismatch against a smaller
                 * player and hasn't failed to back down before, and the shotclock is within coach's
                 * tempo dynamics
                 */
                if (this.baseAttributes.getLowPost() > game.getLowPostAverage() * 1.2
                        && this.baseAttributes.getHeight()
                        > game.getActiveDefensivePlayer().baseAttributes.getHeight()
                        && game.getShotClock() < 5 + coach.getTempo() / 10
                        && !game.getLastEvent().equalsIgnoreCase("failed back down")) {
                    playerDecision = "back down";
                    return playerDecision;
                }

                /**
                 * Player isn't a good low post shooter, but is a good passer, and the shotclock is
                 * above coach's tempo dynamics
                 */
                if (this.baseAttributes.getPass() > game.getPassAverage()
                        && game.getShotClock() > 5 + coach.getTempo() / 10) {
                    playerDecision = "pass";
                    return playerDecision;
                }
            }

            /**
             * Decisions taken if the player has the ball in the paint
             */
            if (game.getPaintZone().contains(this.currentZoneLocation)) {

                /**
                 * Player is a good near-the-basket scorer and has an open look
                 */
                if (this.baseAttributes.getInThePaint() > game.getInThePaintAverage() * 1.2
                        && game.isOpenLook()) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player is a good near-the-basket scorer and is very agressive
                 */
                if (this.baseAttributes.getInThePaint() > game.getInThePaintAverage() * 1.2
                        && this.baseAttributes.getAggressiveness()
                        > game.getAggressivenessAverage()) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player is a good near-the-basket scorer, but it's not very agressive, and the
                 * shotclock is within coach's tempo dynamics
                 */
                if (this.baseAttributes.getInThePaint() > game.getInThePaintAverage() * 1.2
                        && game.getShotClock() < 5 + coach.getTempo() / 10) {
                    playerDecision = "shoot";
                    return playerDecision;
                }

                /**
                 * Player is a good near-the-basket scorer, but it's not very agressive, and the
                 * shotclock is above coach's tempo dynamics
                 */
                if (this.baseAttributes.getInThePaint() > game.getInThePaintAverage() * 1.2
                        && game.getShotClock() > 5 + coach.getTempo() / 10) {
                    playerDecision = "pass";
                    return playerDecision;
                }

                /**
                 * Player isn't a good near-the-basket finisher, but is a good passer, and the
                 * shotclock is above coach's tempo dynamics
                 */
                if (this.baseAttributes.getInThePaint() > game.getInThePaintAverage() * 1.2
                        && game.getShotClock() > 5 + coach.getTempo() / 10) {
                    playerDecision = "pass";
                    return playerDecision;
                }
            }

        } catch (Exception e) {
            System.err.println("Player Decision Error: " + e.getStackTrace());
        }

        /**
         * If no decision was taken until now, take a decision from the decision array list
         */
        return takeDecision();
    }

    /**
     * Returns the kind of shot the player will take
     *
     * @param game Game data
     * @return
     */
    public String getShootDecision(PlayGame game, double distance) {
        String shootDecision = "pull-up jumper";

        System.out.println("Entering player getShootDecision"); // delete
        /**
         * Shooting decisions for player in the three-pointer area
         *
         * Defensive-halfcourt
         */
        if (this.currentZoneLocation == CourtZones.DEFENSIVE_HALFCOURT.getCourtZone()) {
            shootDecision = "long defensive halfcourt three-pointer";
            return shootDecision;
        }

        /**
         * Offensive halfcourt, but way too far from the basket
         */
        if (this.currentZoneLocation == CourtZones.OFFENSIVE_HALFCOURT.getCourtZone()) {
            shootDecision = "long offensive halfcourt three-pointer";
            return shootDecision;
        }

        /**
         * Perimeter shot
         */
        if (game.getThreePointerZone().contains(this.currentZoneLocation)) {
            shootDecision = "three-pointer";
            return shootDecision;
        }

        /**
         * Shooting decisions for player in the field goal area
         *
         * Right and left elbows and top of the arc, the pull-up jumper is the only option
         */
        if (this.currentZoneLocation == 5 || this.currentZoneLocation == 7
                || this.currentZoneLocation == 11) {
            shootDecision = "pull-up jumper";
            return shootDecision;
        }

        /**
         * Midrange field goal zone in the right and left sides of the lane
         */
        if ((this.currentZoneLocation == 4 || this.currentZoneLocation == 10)
                && distance > 3) {
            /**
             * If the player has an open look or is taller than his defender, take a pull-up jumper.
             * Otherwise, take a fadeaway shot
             */
            if (game.isOpenLook() || this.baseAttributes.getHeight()
                    > game.getActiveDefensivePlayer().getBaseAttributes().getHeight()) {
                shootDecision = "pull-up jumper";
                return shootDecision;
            } else {
                shootDecision = "fadeaway";
                return shootDecision;
            }
        }

        /**
         * Closer distance in the right and left sides of the lane
         */
        if ((this.currentZoneLocation == 4 || this.currentZoneLocation == 10)
                && distance < 3) {
            /**
             * If the player has an open look or is taller than his defender, take a pull-up jumper.
             * Otherwise, take a bank shot
             */
            if (game.isOpenLook() || this.baseAttributes.getHeight()
                    > game.getActiveDefensivePlayer().getBaseAttributes().getHeight()) {
                shootDecision = "bank shot";
                return shootDecision;
            } else {
                shootDecision = "fadeaway";
                return shootDecision;
            }
        }

        /**
         * Shooting decisions for player in the low-post area
         */
        if (this.currentZoneLocation == 3 || this.currentZoneLocation == 9) {
            /**
             * If the player has an open look or is taller than his defender, take a layup.
             * Otherwise, take a turnaround shot or a hook
             */
            if (game.isOpenLook() || this.baseAttributes.getHeight()
                    > game.getActiveDefensivePlayer().getBaseAttributes().getHeight()) {
                shootDecision = "low-post layup";
                return shootDecision;
            } else {
                if (MathUtils.generateRandomInt(1, 2) == 1) {
                    shootDecision = "turnaround";
                } else {
                    shootDecision = "hook";
                }

                return shootDecision;
            }
        }

        /**
         * Shooting decisions for player in the lane
         */
        if (this.currentZoneLocation == 6) {
            /*
             * If the player is in the higher half of the lane and has an open look, 
             * go for a layup or a dunk, depending on his dunking skills
             */
            if (game.getBallCurrentLocation() > 45) {
                if (game.isOpenLook()) {
                    if (this.getBaseAttributes().getDunk() > game.getDunkingAverage()) {
                        shootDecision = "dunk";
                    } else {
                        shootDecision = "layup";
                    }
                } else {
                    /**
                     * If it's not an open look, take a floating jumper, a dunk or a running jumper
                     * depending on the height of the active defender and the player's
                     * aggressiveness
                     */
                    if (this.baseAttributes.getDunk() > game.getDunkingAverage()
                            && this.getBaseAttributes().getAggressiveness() > game.getAggressivenessAverage()
                            && this.getBaseAttributes().getAggressiveness() > MathUtils.generateRandomInt(0, 100)) {
                        shootDecision = "dunk";
                    } else if (this.baseAttributes.getHeight() > game.getActiveDefensivePlayer()
                            .getBaseAttributes().getHeight()) {
                        shootDecision = "running jumper";
                    } else {
                        shootDecision = "floating jumper";
                    }
                }
            } else {
                /*
                 * If the player is in the lower half of the lane and has an open look, or the player
                 is a good dunker, go for a dunk; otherwise, take a finger-roll or scoop shot
                 */
                if (game.isOpenLook()) {
                    shootDecision = "dunk";
                } else {
                    /**
                     * If it's not an open look, take a finger-roll or a scoop shot depending on the
                     * height of the active defender
                     */
                    if (this.baseAttributes.getDunk() > game.getDunkingAverage()
                            || this.getBaseAttributes().getAggressiveness() > game.getAggressivenessAverage()) {
                        shootDecision = "dunk";
                    } else if (this.baseAttributes.getHeight() > game.getActiveDefensivePlayer()
                            .getBaseAttributes().getHeight()) {
                        shootDecision = "finger-roll";
                    } else {
                        shootDecision = "scoop shot";
                    }
                }
            }
        }

        return shootDecision;
    }

    /**
     * Returns a random decision from the decision array
     */
    private String takeDecision() {
        /**
         * Shuffling the decision array and picking a random decision
         */
        System.out.println("Taking decision"); // delete
        Collections.shuffle(decisionArray);
        return this.decisionArray.get(MathUtils.generateRandomInt(0,
                this.decisionArray.size() - 1));
    }

    /**
     * Calculates the effort for a player in a jump ball
     *
     * @param coach Player's coach
     * @return
     */
    public double calculateJumpBallEffort(Coach coach) {
        double jumpBallEffort = (this.baseAttributes.getHeight() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getWorkEthic() / 10)
                + (this.baseAttributes.getDiscipline() / 10)
                + (coach.getPhysicality() / 10)
                + (MathUtils.generateRandomInt(-5, 5))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return jumpBallEffort;
    }

    /**
     * Calculates the effort for a player trying to make a normal pass
     *
     * @param coach Player's coach
     * @return
     */
    public double calculatePassEffort(Coach coach) {
        double passEffort = (this.baseAttributes.getPass())
                + (this.baseAttributes.getCourtVision() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getWorkEthic() / 10)
                + (this.baseAttributes.getDiscipline() / 10)
                + (MathUtils.generateRandomInt(-5, 10))
                + (coach.getPass() / 10)
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return passEffort;
    }

    /**
     * Calculates the effort for a player trying to fake a shot
     *
     * @param coach Player's coach
     * @return
     */
    public double calculateFakeEffort(Coach coach) {
        double fakeEffort = (this.baseAttributes.getTechnique())
                + (this.baseAttributes.getCourtVision() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getWorkEthic() / 10)
                + (this.baseAttributes.getBallHandling() / 10)
                + (coach.getTechnique() / 10)
                + (MathUtils.generateRandomInt(-5, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return fakeEffort;
    }

    /**
     * Calculates the effort for a player trying to defend a pump-fake move
     *
     * @param coach Player's coach
     * @return
     */
    public double calculateDefendFakeEffort(Coach coach) {
        double defendFakeEffort = (this.baseAttributes.getOneOnOneDefense())
                + (this.baseAttributes.getCourtVision() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getWorkEthic() / 10)
                + (this.baseAttributes.getTechnique() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-5, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return defendFakeEffort;
    }

    /**
     * Calculates the effort for a player trying to drive
     *
     * @param coach Player's coach
     * @return
     */
    public double calculateDriveEffort(Coach coach) {
        double driveEffort = (this.baseAttributes.getDrive())
                + (this.baseAttributes.getSpeed() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getAggressiveness() / 10)
                + (this.baseAttributes.getDribble() / 10)
                + (coach.getTempo() / 10)
                + (MathUtils.generateRandomInt(-5, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return driveEffort;
    }

    /**
     * Calculates the effort for a player trying to back down
     *
     * @param coach Player's coach
     * @return
     */
    public double calculateBackDownEffort(Coach coach) {
        double backDownEffort = (this.baseAttributes.getLowPost())
                + (this.baseAttributes.getAggressiveness() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getStrenght() / 10)
                + (this.baseAttributes.getDribble() / 10)
                + (coach.getTempo() / 10)
                + (MathUtils.generateRandomInt(-5, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return backDownEffort;
    }

    /**
     * Calculates the effort for a player trying to defend a back down
     *
     * @param coach Player's coach
     * @return
     */
    public double calculateDefendBackDownEffort(Coach coach) {
        double defendBackDownEffort = (this.baseAttributes.getLowPost())
                + (this.baseAttributes.getOneOnOneDefense() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getStrenght() / 10)
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (coach.getTempo() / 10)
                + (MathUtils.generateRandomInt(-5, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return defendBackDownEffort;
    }

    /**
     * Calculates the effort for a player trying to defend a normal pass
     *
     * @param coach Player's coach
     * @return
     */
    public double calculateDefendPassEffort(Coach coach) {
        double defendPassEffort = (this.baseAttributes.getOneOnOneDefense())
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getWorkEthic() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-5, 5))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return defendPassEffort;
    }

    /**
     * Calculates the effort for a player trying to defend a drive
     *
     * @param coach Player's coach
     * @return
     */
    public double calculateDefendDriveEffort(Coach coach) {
        double defendPassEffort = (this.baseAttributes.getOneOnOneDefense())
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getSpeed() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-5, 5))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return defendPassEffort;
    }

    /**
     * Calculates the effort for a player shooting a three-pointer from the defensive halfcourt
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateLongDefensiveHalfcourtThreePointerEffort(Coach coach,
            PlayGame game, double distance) {
        double longDefensiveHalfcourtThreePointerEffort = (this.baseAttributes.getThreePointers())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getOffenseDedication() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.baseAttributes.getWorkEthic() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-35, 20))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            longDefensiveHalfcourtThreePointerEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            longDefensiveHalfcourtThreePointerEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return longDefensiveHalfcourtThreePointerEffort;
    }

    /**
     * Calculates the effort for a player shooting a three-pointer from the offensive halfcourt
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateLongOffensiveHalfcourtThreePointerEffort(Coach coach,
            PlayGame game, double distance) {
        double longOffensiveHalfcourtThreePointerEffort = (this.baseAttributes.getThreePointers())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getOffenseDedication() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getWorkEthic() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-35, 20))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            longOffensiveHalfcourtThreePointerEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            longOffensiveHalfcourtThreePointerEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return longOffensiveHalfcourtThreePointerEffort;
    }

    /**
     * Calculates the effort for a player shooting a three-pointer from the perimeter
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateThreePointerEffort(Coach coach,
            PlayGame game, double distance) {
        double threePointerEffort = (this.baseAttributes.getThreePointers())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getOffenseDedication() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getWorkEthic() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            threePointerEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            threePointerEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return threePointerEffort;
    }

    /**
     * Calculates the effort for a player shooting a pullup jumper
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculatePullUpJumperEffort(Coach coach,
            PlayGame game, double distance) {
        double pullUpJumperEffort = (this.baseAttributes.getPullUpJumper())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getOffenseDedication() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            pullUpJumperEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            pullUpJumperEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return pullUpJumperEffort;
    }

    /**
     * Calculates the effort for a player shooting a running jumper in the lane
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateRunningJumperEffort(Coach coach,
            PlayGame game, double distance) {
        double runningJumperEffort = (this.baseAttributes.getRunningJumper())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getTechnique() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            runningJumperEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            runningJumperEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return runningJumperEffort;
    }

    /**
     * Calculates the effort for a player shooting a finger-roll shot in the lane
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateFingerRollEffort(Coach coach,
            PlayGame game, double distance) {
        double fingerRollEffort = (this.baseAttributes.getFingerRollShot())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getTechnique() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            fingerRollEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            fingerRollEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return fingerRollEffort;
    }

    /**
     * Calculates the effort for a player shooting a floating jumper in the lane
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateFloatingJumperEffort(Coach coach,
            PlayGame game, double distance) {
        double floatingJumperEffort = (this.baseAttributes.getFloatingJumper())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getTechnique() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            floatingJumperEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            floatingJumperEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return floatingJumperEffort;
    }

    /**
     * Calculates the effort for a player shooting a scoop shot in the lane
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateScoopShotEffort(Coach coach,
            PlayGame game, double distance) {
        double scoopShotEffort = (this.baseAttributes.getScoopShot())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getTechnique() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getWorkEthic() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            scoopShotEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            scoopShotEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return scoopShotEffort;
    }

    /**
     * Calculates the effort for a player performing a layup shot
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateLayupEffort(Coach coach,
            PlayGame game, double distance) {
        double layupEffort = (this.baseAttributes.getLayupShot())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getBallHandling() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getTechnique() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            layupEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            layupEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return layupEffort;
    }

    /**
     * Calculates the effort for a player performing a dunk shot
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateDunkEffort(Coach coach,
            PlayGame game, double distance) {
        double dunkEffort = (this.baseAttributes.getDunk())
                + (this.baseAttributes.getJump() / 10)
                + (this.baseAttributes.getStrenght() / 10)
                + (this.baseAttributes.getHeight() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getAggressiveness() / 10)
                + (coach.getPhysicality() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            dunkEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            dunkEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return dunkEffort;
    }

    /**
     * Calculates the effort for a player performing a layup shot from the low post
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateLowPostLayupEffort(Coach coach,
            PlayGame game, double distance) {
        double lowPostLayupEffort = (this.baseAttributes.getLowPost())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getLayupShot() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getTechnique() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            lowPostLayupEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            lowPostLayupEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return lowPostLayupEffort;
    }

    /**
     * Calculates the effort for a player performing a turnaround shot from the low post
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateTurnaroundEffort(Coach coach,
            PlayGame game, double distance) {
        double turnaroundEffort = (this.baseAttributes.getTurnaroundShot())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getLowPost() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getTechnique() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            turnaroundEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            turnaroundEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return turnaroundEffort;
    }

    /**
     * Calculates the effort for a player performing a hook shot from the low post
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateHookEffort(Coach coach,
            PlayGame game, double distance) {
        double hookEffort = (this.baseAttributes.getHookShot())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getLowPost() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getTechnique() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            hookEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            hookEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return hookEffort;
    }

    /**
     * Calculates the effort for a player shooting a fadeaway shot
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateFadeawayShotEffort(Coach coach,
            PlayGame game, double distance) {
        double fadeawayShotEffort = (this.baseAttributes.getFadeawayShot())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getOffenseDedication() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getTechnique() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            fadeawayShotEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            fadeawayShotEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return fadeawayShotEffort;
    }

    /**
     * Calculates the effort for a player shooting a bank shot
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateBankShotEffort(Coach coach,
            PlayGame game, double distance) {
        double bankShotEffort = (this.baseAttributes.getBankShot())
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.baseAttributes.getDiscipline() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getTechnique() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * If the player is shooting from a distance greater than its shooting range, a penalty is
         * applied
         */
        if (distance > this.baseAttributes.getShootingRange()) {
            bankShotEffort -= distance - this.baseAttributes.getShootingRange();
        }

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            bankShotEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return bankShotEffort;
    }

    /**
     * Calculates the effort for a player contesting a pullup jumper from the field goal zone
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestPullUpJumperEffort(Coach coach,
            PlayGame game, double distance) {
        double contestPullUpJumperEffort = (this.baseAttributes.getContest())
                + (this.baseAttributes.getOneOnOneDefense() / 10)
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestPullUpJumperEffort;
    }

    /**
     * Calculates the effort for a player contesting a running jumper in the lane
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestRunningJumperEffort(Coach coach,
            PlayGame game, double distance) {
        double contestRunningJumperEffort = (this.baseAttributes.getContest())
                + (this.baseAttributes.getOneOnOneDefense() / 10)
                + (this.baseAttributes.getBlock() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestRunningJumperEffort;
    }

    /**
     * Calculates the effort for a player contesting a finger roll in the lane
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestFingerRollEffort(Coach coach,
            PlayGame game, double distance) {
        double contestRunningJumperEffort = (this.baseAttributes.getContest())
                + (this.baseAttributes.getOneOnOneDefense() / 10)
                + (this.baseAttributes.getBlock() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestRunningJumperEffort;
    }

    /**
     * Calculates the effort for a player contesting a floating jumper from the field goal zone
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestFloatingJumperEffort(Coach coach,
            PlayGame game, double distance) {
        double contestFloatingJumperEffort = (this.baseAttributes.getBlock())
                + (this.baseAttributes.getOneOnOneDefense() / 10)
                + (this.baseAttributes.getContest() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestFloatingJumperEffort;
    }

    /**
     * Calculates the effort for a player contesting a scoop shot from the field goal zone
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestScoopShotEffort(Coach coach,
            PlayGame game, double distance) {
        double contestScoopShotEffort = (this.baseAttributes.getBlock())
                + (this.baseAttributes.getOneOnOneDefense() / 10)
                + (this.baseAttributes.getContest() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestScoopShotEffort;
    }

    /**
     * Calculates the effort for a player contesting a layup attempt
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestLayupEffort(Coach coach,
            PlayGame game, double distance) {
        double contestLayupEffort = (this.baseAttributes.getBlock())
                + (this.baseAttributes.getContest() / 10)
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestLayupEffort;
    }

    /**
     * Calculates the effort for a player contesting a dunk attempt
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestDunkEffort(Coach coach,
            PlayGame game, double distance) {
        double contestLayupEffort = (this.baseAttributes.getBlock())
                + (this.baseAttributes.getHeight() / 10)
                + (this.baseAttributes.getStrenght() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getPhysicality() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestLayupEffort;
    }

    /**
     * Calculates the effort for a player contesting a layup attempt from the low post
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestLowPostLayupEffort(Coach coach,
            PlayGame game, double distance) {
        double contestLayupEffort = (this.baseAttributes.getLowPost())
                + (this.baseAttributes.getContest() / 10)
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getBlock() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestLayupEffort;
    }

    /**
     * Calculates the effort for a player contesting a turnaround shot attempt from the low post
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestTurnaroundEffort(Coach coach,
            PlayGame game, double distance) {
        double contestTurnaroundEffort = (this.baseAttributes.getLowPost())
                + (this.baseAttributes.getContest() / 10)
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getBlock() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestTurnaroundEffort;
    }

    /**
     * Calculates the effort for a player contesting a hook shot attempt from the low post
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestHookEffort(Coach coach,
            PlayGame game, double distance) {
        double contestHookEffort = (this.baseAttributes.getLowPost())
                + (this.baseAttributes.getContest() / 10)
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getBlock() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestHookEffort;
    }

    /**
     * Calculates the effort for a player contesting a fadeaway shot
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestFadeawayShotEffort(Coach coach,
            PlayGame game, double distance) {
        double contestFadeawayShotEffort = (this.baseAttributes.getContest())
                + (this.baseAttributes.getOneOnOneDefense() / 10)
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestFadeawayShotEffort;
    }

    /**
     * Calculates the effort for a player contesting a bank shot
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestBankShotEffort(Coach coach,
            PlayGame game, double distance) {
        double contestBankShotEffort = (this.baseAttributes.getContest())
                + (this.baseAttributes.getOneOnOneDefense() / 10)
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestBankShotEffort;
    }

    /**
     * Calculates the effort for a player shooting a free-throw
     *
     * @param coach Player's coach
     * @param game Game data
     *
     * @return
     */
    public double calculateFreeThrowEffort(Coach coach, PlayGame game) {
        double freeThrowEffort = (this.baseAttributes.getFreeThrows())
                + (this.baseAttributes.getTechnique() / 10)
                + (this.baseAttributes.getShotCorrection() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.baseAttributes.getDiscipline() / 10)
                + (this.baseAttributes.getWorkEthic() / 10)
                + (coach.getShoot() / 10)
                + (MathUtils.generateRandomInt(-20, 20))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        /**
         * Adding crunch time shooting if the shot is taken in the last 2 minutes of the fourth
         * quarter or overtime
         */
        if (game.getPeriod() > 3 && game.getTimeLeft() < 120) {
            freeThrowEffort += this.baseAttributes.getCrunchTimeShooting();
        }

        return freeThrowEffort;
    }

    /**
     * Calculates the effort for a player battling for an offensive rebound
     *
     * @param coach Player's coach
     * @param game Game data
     *
     * @return
     */
    public double calculateOffensiveReboundEffort(Coach coach, PlayGame game) {
        double offensiveReboundEffort = (this.baseAttributes.getOffensiveRebound())
                + (this.baseAttributes.getOffenseDedication() / 10)
                + (this.baseAttributes.getHeight() / 10)
                + (this.getOffensiveMomentum() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (this.baseAttributes.getStrenght() / 10)
                + (coach.getRebound() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return offensiveReboundEffort;
    }

    /**
     * Calculates the effort for a player battling for a defensive rebound
     *
     * @param coach Player's coach
     * @param game Game data
     *
     * @return
     */
    public double calculateDefensiveReboundEffort(Coach coach, PlayGame game) {
        double defensiveReboundEffort = (this.baseAttributes.getDefensiveRebound())
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (this.baseAttributes.getHeight() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.baseAttributes.getJump() / 10)
                + (this.baseAttributes.getBoxOut() / 10)
                + (coach.getRebound() / 10)
                + (MathUtils.generateRandomInt(-10, 25))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return defensiveReboundEffort;
    }

    /**
     * Calculates the effort for a player contesting a three-pointer from the perimeter
     *
     * @param coach Player's coach
     * @param game Game data
     * @param distance Shot's distance
     * @return
     */
    public double calculateContestThreePointerEffort(Coach coach,
            PlayGame game, double distance) {
        double contestThreePointerEffort = (this.baseAttributes.getContest())
                + (this.baseAttributes.getOneOnOneDefense() / 10)
                + (this.baseAttributes.getDefenseDedication() / 10)
                + (this.getDefensiveMomentum() / 10)
                + (this.getCurrentStaminaLevel() / 10)
                + (this.baseAttributes.getWorkEthic() / 10)
                + (coach.getDefense() / 10)
                + (MathUtils.generateRandomInt(-10, 10))
                + (calculateDefaultEffortBonus()
                - (calculateDefaultEffortPenalties()));

        return contestThreePointerEffort;
    }

    /**
     * Moves the player on the court
     *
     * @param game Game data
     */
    public void move(PlayGame game) {

        this.movementLog.add(game.getPeriod() + " - " + TimeUtils.intToTime(game.getTimeLeft()) + " :"
                + CourtUtils.getCourtZoneDescription(this.currentZoneLocation,
                        "pt", game.getConnection())); // delete*/

        /**
         * If the player is in the defensive halfcourt (15) he can only move to the offensive
         * halfcourt (14)
         */
        if (this.currentZoneLocation == CourtZones.DEFENSIVE_HALFCOURT.getCourtZone()) {
            this.currentZoneLocation = CourtZones.OFFENSIVE_HALFCOURT.getCourtZone();
            return;
        }

        /**
         * If the player is in the offensive halfcourt (14), he'll move to the next zone accordingly
         * to his position
         */
        if (this.currentZoneLocation == 14) {
            switch (this.baseAttributes.getPosition()) {
                case "PG":
                    this.currentZoneLocation = CourtZones.TOP_ARC.getCourtZone();
                    break;
                case "SG":
                    this.currentZoneLocation = CourtZones.RIGHT_WING.getCourtZone();
                    break;
                case "SF":
                    this.currentZoneLocation = CourtZones.LEFT_WING.getCourtZone();
                    break;
                case "PF":
                    this.currentZoneLocation = CourtZones.LEFT_LOW_POST.getCourtZone();
                    break;
                default:
                    this.currentZoneLocation = CourtZones.RIGHT_LOW_POST.getCourtZone();
                    break;
            }

            return;
        }

        /**
         * If the player is another zone, retrieves a random zone from the player's movement array
         * and check if it's adjacent to the player's current zone location, if true, the player
         * moves to the retrieved zones, otherwise, the player stays in the same zone. The loop
         * tries for (currentStaminaLevel / 10) times move the player to another cell just to
         * provide more flow to the game.
         */
        int moveTo = this.currentZoneLocation;
        int randomLocation;
        int adjacentZone;
        ArrayList<Integer> possibleLocations = new ArrayList<>();
        possibleLocations.add(moveTo);

        for (int i = 0; i < this.currentStaminaLevel / 10; i++) {
            randomLocation = this.getMovementArray().get(MathUtils.generateRandomInt(0,
                    this.getMovementArray().size() - 1));
            if (CourtUtils.isAdjacentZone(randomLocation, this.currentZoneLocation)
                    && randomLocation != this.currentZoneLocation && randomLocation < 14) {
                possibleLocations.add(randomLocation);
            }

            adjacentZone = CourtUtils.getAdjacentZone(this.currentZoneLocation);
            possibleLocations.add(adjacentZone);
        }

        moveTo = possibleLocations.get(MathUtils.generateRandomInt(0, possibleLocations.size() - 1));

        System.out.println("Current Location: " + this.currentZoneLocation); // delete
        System.out.println("Possible locations: " + possibleLocations.toString()); // delete 
        System.out.println("Chosen location: " + moveTo);

        /**
         * Checking if the player hasn't moved to make him to an adjacent zone
         */
        if (moveTo == this.currentZoneLocation) {
            moveTo = CourtUtils.getAdjacentZone(moveTo);
        }

        /**
         * Preventing a center/power forward with a low threePointer attribute to move to outside
         * the perimeter; instead, moving him to the paint
         */
        if (this.getBaseAttributes().getPosition().equalsIgnoreCase("C")) {
            System.out.println("Center/Power Forward moving to outside");
            System.out.println("Player " + this.getCompleteName());
            System.out.println(game.getPeriod() + " - " + TimeUtils.intToTime(game.getTimeLeft()) + " :"
                    + CourtUtils.getCourtZoneDescription(this.currentZoneLocation,
                            "pt", game.getConnection()));
            System.out.println("Is center? " + (this.getBaseAttributes().getPosition().equalsIgnoreCase("C")
                    || this.getBaseAttributes().getPosition2().equalsIgnoreCase("C")));
            System.out.println("Outside? " + (game.getCourtSpots().get(moveTo).getBasketPoints() == 3));
            System.out.println("Good outside shooter? " + (this.getBaseAttributes().getThreePointers() < game.getThreePointersAverage() * 1.2));
        }

        if (this.getBaseAttributes().getPosition().equalsIgnoreCase("C")
                || this.getBaseAttributes().getPosition2().equalsIgnoreCase("C")
                && (game.getCourtSpots().get(moveTo).getBasketPoints() == 3
                && this.getBaseAttributes().getThreePointers() < game.getThreePointersAverage() * 1.2)) {
            if (this.getBaseAttributes().getPosition().equalsIgnoreCase("C")) {
                moveTo = CourtZones.RIGHT_LOW_POST.getCourtZone();
            } else {
                moveTo = CourtZones.LEFT_LOW_POST.getCourtZone();
            }
        }

        /**
         * Preventing players from going back to the offensive and defensive halfcourts once the
         * offensive possession has started, instead, moving them to the offensive backcourt
         */
        if (game.getCourtSpots().get(moveTo).getCourtZone() == CourtZones.OFFENSIVE_HALFCOURT.getCourtZone()
                || game.getCourtSpots().get(moveTo).getCourtZone() == CourtZones.DEFENSIVE_HALFCOURT.getCourtZone()) {
            moveTo = game.getStartOffenseZone().get(MathUtils.generateRandomInt(0,
                    game.getStartOffenseZone().size() - 1)).intValue();
        }

        this.currentZoneLocation = moveTo;
    }

    /**
     * Calculates default penalties to every effort
     *
     * @return
     */
    private int calculateDefaultEffortPenalties() {
        return this.baseAttributes.getRookieWeakness()
                + (this.baseAttributes.getAccumulatedFatigue() / 10)
                + (this.baseAttributes.getInjuryImpact());
    }

    /**
     * Calculates default bonus to every effort
     *
     * @return
     */
    private int calculateDefaultEffortBonus() {
        int defaultEffortBonus = 0;

        /**
         * If the player is shooting from his favorite zone, add a bonus
         */
        if (this.currentZoneLocation == this.baseAttributes.getFavoriteCourtZone().getId()) {
            defaultEffortBonus++;
        }

        return defaultEffortBonus;
    }

    /* Stat updating methods */
    public void updatePoints(int increase) {
        this.points = (short) (this.points + increase);
    }

    public void updateOffensiveMomentum(int adjust) {
        this.offensiveMomentum = (short) (this.offensiveMomentum + adjust);
    }

    public void updateDefensiveMomentum(int adjust) {
        this.defensiveMomentum = (short) (this.defensiveMomentum + adjust);
    }

    public void updateTurnovers() {
        this.turnovers = (short) (this.turnovers + 1);
    }

    public void updatePersonalFouls() {
        this.personalFouls = (short) (this.personalFouls + 1);
    }

    public void updateTechnicalFouls() {
        this.technicalFouls = (short) (this.technicalFouls + 1);
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

    public void updatePerformanceIndex() {
        double index = this.points + (this.assists * 2) + (this.offensiveRebounds * 1.5) + this.defensiveRebounds
                + (this.blocks * 1.5) - (this.blockedShots * 1.5) + this.steals - this.turnovers - this.personalFouls
                - (this.technicalFouls * 2);

        /**
         * Checking whether the calculated index is not a number, if true, turns it into 0;
         */
        if (Double.isNaN(index)) {
            index = 0;
        }

        this.setPerformanceIndex(index);
    }

    public void updateOffensiveIndex() {
        double index = this.points + (this.assists * 2) + (this.offensiveRebounds * 1.5) - (this.blockedShots * 1.5)
                - this.turnovers;

        /**
         * Checking whether the calculated index is not a number, if true, turns it into 0;
         */
        if (Double.isNaN(index)) {
            index = 0;
        }

        this.setOffensiveIndex(index);
    }

    public void updateDefensiveIndex() {
        double index = this.defensiveRebounds + (this.blocks * 1.5) + this.steals - this.personalFouls
                - (this.technicalFouls * 2);

        /**
         * Checking whether the calculated index is not a number, if true, turns it into 0;
         */
        if (Double.isNaN(index)) {
            index = 0;
        }

        this.setDefensiveIndex(index);
    }

    public void updateEfficiencyIndex() {
        double index = (((double) this.fieldGoalsMade / this.fieldGoalsAttempted * 2)
                + ((double) this.freeThrowsMade / this.freeThrowsAttempted)
                + ((double) this.threePointersMade / this.threePointersAttempted * 3)
                + (this.assists * 2) + (this.offensiveRebounds * 1.5) + this.defensiveRebounds
                + (this.blocks + 1.5) - (this.blockedShots * 1.5) + this.steals - this.turnovers - this.personalFouls
                - (this.technicalFouls * 2)) / (this.playingTime / 60);

        /**
         * Checking whether the calculated index is not a number, if true, turns it into 0;
         */
        if (Double.isNaN(index)) {
            index = 0;
        }
        this.setEfficiencyIndex(index);
    }

    /* comparing methods */
    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InGamePlayer other = (InGamePlayer) obj;
        if (this.baseAttributes.getId() != other.baseAttributes.getId()) {
            return false;
        }
        return true;
    }

    /* non-standard getters and setters */
    private double getFieldGoalsShootingPercentage() {
        return (double) fieldGoalsMade / fieldGoalsAttempted;
    }

    private double getThreePointersShootingPercentage() {
        return (double) threePointersMade / threePointersAttempted;
    }

    public int getTotalRebounds() {
        return this.offensiveRebounds + this.defensiveRebounds;
    }

    public String getStatsLine() {
        return this.getCompleteName() + ": " + this.points + " Pts, " + TimeUtils.intToTime(playingTime)
                + ", " + this.fieldGoalsMade + "-" + this.fieldGoalsAttempted + " FG, "
                + this.threePointersMade + "-" + this.threePointersAttempted
                + " TP, " + this.freeThrowsMade + "-" + this.freeThrowsAttempted
                + " FT, " + this.getAssists() + " Ass, " + this.getDefensiveRebounds()
                + " DR, " + this.getOffensiveRebounds() + " OR, " + this.getBlocks() + " Blk, "
                + this.getSteals() + " Stl, " + this.getPersonalFouls() + " PF, "
                + this.getTurnovers() + " TO";
    }

    @Override
    public String toString() {
        return "InGamePlayer{" + "personalFouls=" + personalFouls + ", secondsOnCourt=" + secondsOnCourt + ", secondsInBench=" + secondsInBench + ", ejected=" + ejected + ", onCourt=" + onCourt + ", shootingFreeThrows=" + shootingFreeThrows + ", defensiveMomentum=" + defensiveMomentum + ", offensiveMomentum=" + offensiveMomentum + ", rosterPosition=" + rosterPosition + ", currentZoneLocation=" + currentZoneLocation + ", staminaLevel=" + staminaLevel + ", currentStaminaLevel=" + currentStaminaLevel + ", substitutionTime=" + substitutionTime + '}';
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

    public boolean isHasBall() {
        return hasBall;
    }

    public void setHasBall(boolean hasBall) {
        this.hasBall = hasBall;
    }

    public boolean isShootingFreeThrows() {
        return shootingFreeThrows;
    }

    public void setShootingFreeThrows(boolean isShootingFreeThrows) {
        this.shootingFreeThrows = isShootingFreeThrows;
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

    public short getPoints() {
        return points;
    }

    public void setPoints(short points) {
        this.points = points;
    }

    public String getCompleteName() {
        return this.baseAttributes.getPosition() + " " + this.baseAttributes.getFirstName()
                + " " + this.baseAttributes.getLastName();
    }

    public int getRosterPosition() {
        return rosterPosition;
    }

    public void setRosterPosition(int rosterPosition) {
        this.rosterPosition = rosterPosition;
    }

    public int getCurrentZoneLocation() {
        return currentZoneLocation;
    }

    public void setCurrentZoneLocation(int currentZoneLocation) {
        this.currentZoneLocation = currentZoneLocation;
    }

    public ArrayList<String> getDecisionArray() {
        return decisionArray;
    }

    public void setDecisionArray(ArrayList<String> decisionArray) {
        this.decisionArray = decisionArray;
    }

    public ArrayList<Integer> getMovementArray() {
        return movementArray;
    }

    public void setMovementArray(ArrayList<Integer> movementArray) {
        this.movementArray = movementArray;
    }

    public int getStaminaLevel() {
        return staminaLevel;
    }

    public void setStaminaLevel(int staminaLevel) {
        this.staminaLevel = staminaLevel;
    }

    public int getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(int playingTime) {
        this.playingTime = playingTime;
    }

    public int getCurrentStaminaLevel() {
        return currentStaminaLevel;
    }

    public void setCurrentStaminaLevel(int currentStamina) {
        this.currentStaminaLevel = currentStamina;
    }

    public int getSubstitutionTime() {
        return substitutionTime;
    }

    public void setSubstitutionTime(int substitutionTime) {
        this.substitutionTime = substitutionTime;
    }

    public boolean isOnCourt() {
        return onCourt;
    }

    public void setOnCourt(boolean onCourt) {
        this.onCourt = onCourt;
    }

    public int getStaminaAdjustment() {
        return staminaAdjustment;
    }

    public void setStaminaAdjustment(int staminaAdjustment) {
        this.staminaAdjustment = staminaAdjustment;
    }

    public short getBlockedShots() {
        return blockedShots;
    }

    public void setBlockedShots(short blockedShots) {
        this.blockedShots = blockedShots;
    }

    public short getTechnicalFouls() {
        return technicalFouls;
    }

    public void setTechnicalFouls(short technicalFouls) {
        this.technicalFouls = technicalFouls;
    }

    public double getPerformanceIndex() {
        return performanceIndex;
    }

    public void setPerformanceIndex(double performanceIndex) {
        this.performanceIndex = performanceIndex;
    }

    public double getOffensiveIndex() {
        return offensiveIndex;
    }

    public void setOffensiveIndex(double offensiveIndex) {
        this.offensiveIndex = offensiveIndex;
    }

    public double getDefensiveIndex() {
        return defensiveIndex;
    }

    public void setDefensiveIndex(double defensiveIndex) {
        this.defensiveIndex = defensiveIndex;
    }

    public double getEfficiencyIndex() {
        return efficiencyIndex;
    }

    public void setEfficiencyIndex(double efficiencyIndex) {
        this.efficiencyIndex = efficiencyIndex;
    }

    /**
     * JavaFX properties necessary to allow observable lists to work properly in the game scene
     */
    private final StringProperty stringPlayingTime = new SimpleStringProperty();

    public String getStringPlayingTime() {
        return stringPlayingTime.get();
    }

    public void setStringPlayingTime(String value) {
        stringPlayingTime.set(value);
    }

    public StringProperty stringPlayingTimeProperty() {
        return stringPlayingTime;
    }
    private final StringProperty stringPoints = new SimpleStringProperty();

    public String getStringPoints() {
        return stringPoints.get();
    }

    public void setStringPoints(String value) {
        stringPoints.set(value);
    }

    public StringProperty stringPointsProperty() {
        return stringPoints;
    }
    private final StringProperty stringFieldGoals = new SimpleStringProperty();

    public String getStringFieldGoals() {
        return stringFieldGoals.get();
    }

    public void setStringFieldGoals(String value) {
        stringFieldGoals.set(value);
    }

    public StringProperty stringFieldGoalsProperty() {
        return stringFieldGoals;
    }
    private final StringProperty stringFreeThrows = new SimpleStringProperty();

    public String getStringFreeThrows() {
        return stringFreeThrows.get();
    }

    public void setStringFreeThrows(String value) {
        stringFreeThrows.set(value);
    }

    public StringProperty stringFreeThrowsProperty() {
        return stringFreeThrows;
    }

    private final StringProperty stringThreePointers = new SimpleStringProperty();

    public String getStringThreePointers() {
        return stringThreePointers.get();
    }

    public void setStringThreePointers(String value) {
        stringThreePointers.set(value);
    }

    public StringProperty stringThreePointersProperty() {
        return stringThreePointers;
    }

    private final StringProperty stringDefensiveRebounds = new SimpleStringProperty();

    public String getStringDefensiveRebounds() {
        return stringDefensiveRebounds.get();
    }

    public void setStringDefensiveRebounds(String value) {
        stringDefensiveRebounds.set(value);
    }

    public StringProperty stringDefensiveReboundsProperty() {
        return stringDefensiveRebounds;
    }

    private final StringProperty stringOffensiveRebounds = new SimpleStringProperty();

    public String getStringOffensiveRebounds() {
        return stringOffensiveRebounds.get();
    }

    public void setStringOffensiveRebounds(String value) {
        stringOffensiveRebounds.set(value);
    }

    public StringProperty stringOffensiveReboundsProperty() {
        return stringOffensiveRebounds;
    }

    private final StringProperty stringTotalRebounds = new SimpleStringProperty();

    public String getStringTotalRebounds() {
        return stringTotalRebounds.get();
    }

    public void setStringTotalRebounds(String value) {
        stringTotalRebounds.set(value);
    }

    public StringProperty stringTotalReboundsProperty() {
        return stringTotalRebounds;
    }

    private final StringProperty stringAssists = new SimpleStringProperty();

    public String getStringAssists() {
        return stringAssists.get();
    }

    public void setStringAssists(String value) {
        stringAssists.set(value);
    }

    public StringProperty stringAssistsProperty() {
        return stringAssists;
    }

    private final StringProperty stringSteals = new SimpleStringProperty();

    public String getStringSteals() {
        return stringSteals.get();
    }

    public void setStringSteals(String value) {
        stringSteals.set(value);
    }

    public StringProperty stringStealsProperty() {
        return stringSteals;
    }

    private final StringProperty stringBlocks = new SimpleStringProperty();

    public String getStringBlocks() {
        return stringBlocks.get();
    }

    public void setStringBlocks(String value) {
        stringBlocks.set(value);
    }

    public StringProperty stringBlocksProperty() {
        return stringBlocks;
    }

    private final StringProperty stringTurnovers = new SimpleStringProperty();

    public String getStringTurnovers() {
        return stringTurnovers.get();
    }

    public void setStringTurnovers(String value) {
        stringTurnovers.set(value);
    }

    public StringProperty stringTurnoversProperty() {
        return stringTurnovers;
    }

    private final StringProperty stringPersonalFouls = new SimpleStringProperty();

    public String getStringPersonalFouls() {
        return stringPersonalFouls.get();
    }

    public void setStringPersonalFouls(String value) {
        stringPersonalFouls.set(value);
    }

    public StringProperty stringPersonalFoulsProperty() {
        return stringPersonalFouls;
    }

    private final StringProperty stringStamina = new SimpleStringProperty();

    public String getStringStamina() {
        return stringStamina.get();
    }

    public void setStringStamina(String value) {
        stringStamina.set(value);
    }

    public StringProperty stringStaminaProperty() {
        return stringStamina;
    }

    private final StringProperty stringPlayer = new SimpleStringProperty();

    public String getStringPlayer() {
        return stringPlayer.get();
    }

    public void setStringPlayer(String value) {
        stringPlayer.set(value);
    }

    public StringProperty stringPlayerProperty() {
        return stringPlayer;
    }

    private final StringProperty stringJersey = new SimpleStringProperty();

    public String getStringJersey() {
        return stringJersey.get();
    }

    public void setStringJersey(String value) {
        stringJersey.set(value);
    }

    public StringProperty stringJerseyProperty() {
        return stringJersey;
    }

    private final StringProperty stringPerformanceIndex = new SimpleStringProperty();

    public String getStringPerformanceIndex() {
        return stringPerformanceIndex.get();
    }

    public void setStringPerformanceIndex(String value) {
        stringPerformanceIndex.set(value);
    }

    public StringProperty stringPerformanceIndexProperty() {
        return stringPerformanceIndex;
    }

    private final StringProperty stringOffensiveIndex = new SimpleStringProperty();

    public String getStringOffensiveIndex() {
        return stringOffensiveIndex.get();
    }

    public void setStringOffensiveIndex(String value) {
        stringOffensiveIndex.set(value);
    }

    public StringProperty stringOffensiveIndexProperty() {
        return stringOffensiveIndex;
    }

    private final StringProperty stringDefensiveIndex = new SimpleStringProperty();

    public String getStringDefensiveIndex() {
        return stringDefensiveIndex.get();
    }

    public void setStringDefensiveIndex(String value) {
        stringDefensiveIndex.set(value);
    }

    public StringProperty stringDefensiveIndexProperty() {
        return stringDefensiveIndex;
    }

    private final StringProperty stringEfficiencyIndex = new SimpleStringProperty();

    public String getStringEfficiencyIndex() {
        return stringEfficiencyIndex.get();
    }

    public void setStringEfficiencyIndex(String value) {
        stringEfficiencyIndex.set(value);
    }

    public StringProperty stringEfficiencyIndexProperty() {
        return stringEfficiencyIndex;
    }

    private final StringProperty stringOffensiveMomentum = new SimpleStringProperty();

    public String getStringOffensiveMomentum() {
        return stringOffensiveMomentum.get();
    }

    public void setStringOffensiveMomentum(String value) {
        stringOffensiveMomentum.set(value);
    }

    public StringProperty stringOffensiveMomentumProperty() {
        return stringOffensiveMomentum;
    }

    private final StringProperty stringDefensiveMomentum = new SimpleStringProperty();

    public String getStringDefensiveMomentum() {
        return stringDefensiveMomentum.get();
    }

    public void setStringDefensiveMomentum(String value) {
        stringDefensiveMomentum.set(value);
    }

    public StringProperty stringDefensiveMomentumProperty() {
        return stringDefensiveMomentum;
    }

    public void printMovementLog() {
        for (int i = 0; i < this.movementLog.size(); i++) {
            System.out.println(this.movementLog.get(i).toString());
        }
    }

} // end class InGamePlayer
