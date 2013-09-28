package coachj.ingame;

import coachj.builders.CourtSpotBuilder;
import coachj.builders.NarrationBuilder;
import coachj.builders.RefereeBuilder;
import coachj.dao.DatabaseDirectConnection;
import coachj.enums.CourtZones;
import coachj.enums.GameParameters;
import coachj.models.Referee;
import coachj.structures.CourtSpot;
import coachj.structures.GameNarration;
import coachj.structures.Play;
import coachj.utils.ArenaUtils;
import coachj.utils.CourtUtils;
import coachj.utils.GameUtils;
import coachj.utils.MathUtils;
import coachj.utils.PlayerUtils;
import coachj.utils.RefereeUtils;
import coachj.utils.SettingsUtils;
import coachj.utils.StatsUtils;
import coachj.utils.TimeUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Stores information about the game that is about to be played
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 28/08/2013
 */
public class PlayGame {

    /**
     * Fields
     */
    private int season;
    private int gameId;
    private short homeTeamId;
    private short awayTeamId;
    private String gameDate;
    private String gameTime;
    private short refereeId;
    private int attendance;
    private short arenaId;
    private String gameType;
    private short awayScore;
    private short homeScore;
    private short timeLeft;
    private int shotClock;
    private short period;
    private boolean inProgress;
    private boolean paused = false;
    private boolean fastMode;
    private boolean officialTimeoutCalled;
    private int playScore;
    private int freeThrowsToShoot = 0;
    private String language;
    private int elapsedTime;
    private int ballLastLocation;
    private int ballCurrentLocation;
    private String currentEvent;
    private String lastEvent;
    private String shotDescription;
    private boolean openLook = false;
    private int tipOffWinner;
    private int ballPossession;
    private double offensiveEffort;
    private double defensiveEffort;
    private int leadingTeam;
    private int gap;
    private int playerInId;
    private int playerOutId;
    private String lastScoringPlayScore = "------";
    private String lastScoringPlay = "------";
    private String lastScorerStats = "------";
    private Random generator = new Random();
    private InGamePlayer activeOffensivePlayer;
    private InGamePlayer activeDefensivePlayer;
    private InGamePlayer lastActiveOffensivePlayer;
    private InGamePlayer lastActiveDefensivePlayer;
    private Referee referee;
    private ArrayList<Team> teams = new ArrayList<>();
    private ObservableList<Play> plays = FXCollections
            .observableArrayList();
    private ObservableList<Play> scoringPlays = FXCollections
            .observableArrayList();
    private ArrayList<CourtSpot> courtSpots = new ArrayList<>();
    private Map<String, GameNarration> narration = new HashMap<>();
    /**
     * Keeps a reference to the application's database connection
     */
    private DatabaseDirectConnection connection;
    /**
     * League average rates, used to guide players in decisions
     */
    private double jumpShotAverage;
    private double quickShotAverage;
    private double threePointersAverage;
    private double courtVisionAverage;
    private double driveAverage;
    private double lowPostAverage;
    private double inThePaintAverage;
    private double passAverage;
    private double alleyOopPassAverage;
    private double dunkAverage;
    private double noLookPassAverage;
    private double behindTheBackPassAverage;
    private double aggressivenessAverage;
    private double freeThrowsAverage;
    private double creativityAverage;
    /**
     * Court zones
     */
    ArrayList<Integer> dunkingZone = new ArrayList<>();
    ArrayList<Integer> threePointerZone = new ArrayList<>();
    ArrayList<Integer> jumpShotZone = new ArrayList<>();
    ArrayList<Integer> paintZone = new ArrayList<>();
    ArrayList<Integer> lowPostZone = new ArrayList<>();
    ArrayList<Integer> startOffenseZone = new ArrayList<>();
    ArrayList<Integer> reboundingZone = new ArrayList<>();
    /**
     * Constants
     */
    private final int AWAY_TEAM = 1;
    private final int HOME_TEAM = 2;

    /**
     * Creates a new game
     *
     * @param gameId Game's id
     * @param connection Database connection used to retrieve data
     */
    public PlayGame(int gameId, DatabaseDirectConnection connection) {

        this.gameId = gameId;
        /**
         * Creates a reference to database connection
         */
        this.connection = connection;

        /**
         * Getting language
         */
        this.language = SettingsUtils.getSetting("language", "pt");

        /**
         * Retrieving data to set up fields
         */
        String sqlStatement = "SELECT homeTeam, awayTeam, date, time, referee, "
                + " arena, type FROM game WHERE id = " + gameId;
        ResultSet resultSet = this.connection.getResultSet(sqlStatement);
        Team awayTeam;
        Team homeTeam;
        this.season = Short.parseShort(SettingsUtils.getSetting("currentSeason",
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));


        try {
            resultSet.first();

            /**
             * Base data
             */
            this.homeTeamId = resultSet.getShort("homeTeam");
            this.awayTeamId = resultSet.getShort("awayTeam");
            this.gameDate = resultSet.getString("date");
            this.gameTime = resultSet.getString("time");
            this.arenaId = resultSet.getShort("arena");
            this.refereeId = RefereeUtils.getRandomRefereeId(gameDate, connection);
            this.gameType = resultSet.getString("type");
            this.attendance = GameUtils.calculateGameAttendance(gameType,
                    awayTeamId, homeTeamId, arenaId, connection);
            this.awayScore = 0;
            this.homeScore = 0;
            this.shotClock = GameParameters.SHOTCLOCK.getParameterValue();
            this.timeLeft = 720;
            this.period = 1;
            this.leadingTeam = 0;
            this.gap = 0;

            /**
             * Calculating average rates
             */
            jumpShotAverage = StatsUtils.getLeagueAttributeAverageRate("pullUpJumper",
                    connection);
            quickShotAverage = StatsUtils.getLeagueAttributeAverageRate("catchAndShootShot",
                    connection);
            threePointersAverage = StatsUtils.getLeagueAttributeAverageRate("threePointers",
                    connection);
            courtVisionAverage = StatsUtils.getLeagueAttributeAverageRate("courtVision",
                    connection);
            driveAverage = StatsUtils.getLeagueAttributeAverageRate("drive",
                    connection);
            lowPostAverage = StatsUtils.getLeagueAttributeAverageRate("lowPost",
                    connection);
            inThePaintAverage = StatsUtils.getLeagueAttributeAverageRate("inThePaint",
                    connection);
            passAverage = StatsUtils.getLeagueAttributeAverageRate("pass",
                    connection);
            alleyOopPassAverage = StatsUtils.getLeagueAttributeAverageRate("alleyOopPass",
                    connection);
            dunkAverage = StatsUtils.getLeagueAttributeAverageRate("dunk",
                    connection);
            noLookPassAverage = StatsUtils.getLeagueAttributeAverageRate("noLookPass",
                    connection);
            behindTheBackPassAverage = StatsUtils.getLeagueAttributeAverageRate("behindTheBackPass",
                    connection);
            aggressivenessAverage = StatsUtils.getLeagueAttributeAverageRate("aggressiveness",
                    connection);
            freeThrowsAverage = StatsUtils.getLeagueAttributeAverageRate("freeThrows",
                    connection);
            creativityAverage = StatsUtils.getLeagueAttributeAverageRate("creativity",
                    connection);
            /**
             * Creating teams
             */
            awayTeam = new Team(awayTeamId, this.connection);
            homeTeam = new Team(homeTeamId, this.connection);
            teams.add(0, null); // ghost entry
            teams.add(1, awayTeam);
            teams.add(2, homeTeam);

            /**
             * Creating referee
             */
            RefereeBuilder refereeBuilder = new RefereeBuilder();
            refereeBuilder.fillAttributesFromDatabase(refereeId, this.connection);
            referee = refereeBuilder.generateRefereeEntity();

            /**
             * Filling up court spots' information
             */
            CourtSpot currentCourtSpot;
            courtSpots.add(null); // ghost entry
            for (int i = 1; i <= 420; i++) {
                currentCourtSpot = CourtSpotBuilder.generateCourtSpot(i, language,
                        this.connection);
                courtSpots.add(currentCourtSpot);
            }

            /**
             * Retrieving narration data
             */
            GameNarration currentNarration;
            sqlStatement = "SELECT id, playType FROM narration ORDER BY id";
            ResultSet narrationDataResultSet = connection.getResultSet(sqlStatement);

            while (narrationDataResultSet.next()) {
                currentNarration = NarrationBuilder.generateGameNarration(
                        narrationDataResultSet.getInt("id"), language, this.connection);

                this.narration.put(narrationDataResultSet.getString("playType"),
                        currentNarration);
            }

            /**
             * Filling up court zones arrays
             */
            dunkingZone.add(CourtZones.RIGHT_LOW_POST.getCourtZone());
            dunkingZone.add(CourtZones.PAINT.getCourtZone());
            dunkingZone.add(CourtZones.LEFT_LOW_POST.getCourtZone());

            threePointerZone.add(CourtZones.RIGHT_CORNER.getCourtZone());
            threePointerZone.add(CourtZones.RIGHT_WING.getCourtZone());
            threePointerZone.add(CourtZones.TOP_ARC.getCourtZone());
            threePointerZone.add(CourtZones.LEFT_CORNER.getCourtZone());
            threePointerZone.add(CourtZones.LEFT_WING.getCourtZone());

            jumpShotZone.add(CourtZones.RIGHT_SIDE_LANE.getCourtZone());
            jumpShotZone.add(CourtZones.RIGHT_ELBOW.getCourtZone());
            jumpShotZone.add(CourtZones.TOP_KEY.getCourtZone());
            jumpShotZone.add(CourtZones.LEFT_SIDE_LANE.getCourtZone());
            jumpShotZone.add(CourtZones.LEFT_ELBOW.getCourtZone());

            paintZone.add(CourtZones.PAINT.getCourtZone());

            lowPostZone.add(CourtZones.RIGHT_LOW_POST.getCourtZone());
            lowPostZone.add(CourtZones.LEFT_LOW_POST.getCourtZone());

            startOffenseZone.add(CourtZones.RIGHT_WING.getCourtZone());
            startOffenseZone.add(CourtZones.TOP_ARC.getCourtZone());
            startOffenseZone.add(CourtZones.LEFT_WING.getCourtZone());

            reboundingZone.add(CourtZones.RIGHT_SIDE_LANE.getCourtZone());
            reboundingZone.add(CourtZones.RIGHT_LOW_POST.getCourtZone());
            reboundingZone.add(CourtZones.PAINT.getCourtZone());
            reboundingZone.add(CourtZones.LEFT_LOW_POST.getCourtZone());
            reboundingZone.add(CourtZones.LEFT_SIDE_LANE.getCourtZone());

        } catch (SQLException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets up several fields to start a period
     *
     * @param period
     */
    private void startPeriod(int period) {

        System.out.println("Start of period: " + period);

        /**
         * Setting up period length
         */
        int periodLength = this.period < 5 ? GameParameters.QUARTER_LENGTH
                .getParameterValue() : GameParameters.OVERTIME_LENGTH.getParameterValue();

        this.timeLeft = (short) periodLength;

        /**
         * Resting players accordingly to the intermission between quarters and
         * setting their substitution time to 0
         */
        if (period == 2) {
            restPlayers(16);
        } else if (period > 1) {
            restPlayers(5);
        }

        resetPlayersSubstitutionTime(periodLength);

        /**
         * Setting up shotclock, official timeout
         */
        this.shotClock = GameParameters.SHOTCLOCK.getParameterValue();
        this.setOfficialTimeoutCalled(false);

        /**
         * Setting up team's timeouts and fouls
         */
        teams.get(AWAY_TEAM).setTimeoutsLeft((short) 3);
        teams.get(HOME_TEAM).setTimeoutsLeft((short) 3);
        teams.get(AWAY_TEAM).setFouls((short) 0);
        teams.get(HOME_TEAM).setFouls((short) 0);
        teams.get(AWAY_TEAM).setLastTimeoutCall(periodLength);
        teams.get(HOME_TEAM).setLastTimeoutCall(periodLength);

        /**
         * Setting up which team has the ball possession at the start of the
         * quarter, the team which won the initial tip-off starts in the fourth,
         * the team which lost starts the second and third
         */
        if (period == 2 || period == 3) {
            this.ballPossession = 3 - this.tipOffWinner;
        } else if (period == 4) {
            this.ballPossession = this.tipOffWinner;
        } else {
            this.ballPossession = HOME_TEAM;
        }

        /**
         * Setting up the ball location at the start of the quarter. In the
         * first quarter and overtime, the ball is located at the center of the
         * court for the tip-off. In the other ones, the ball starts at the end
         * line of the defensive halfcourt
         */
        if (period == 1 || period > 4) {
            this.ballCurrentLocation = 203;
            this.ballLastLocation = 203;
            this.lastEvent = "";
            this.currentEvent = "tip-off";
            this.activeOffensivePlayer = selectPlayerByRosterPosition(HOME_TEAM,
                    1);
            this.lastActiveOffensivePlayer = selectPlayerByRosterPosition(HOME_TEAM,
                    2);
            this.activeDefensivePlayer = selectPlayerByRosterPosition(AWAY_TEAM,
                    1);
            this.lastActiveDefensivePlayer = selectPlayerByRosterPosition(AWAY_TEAM,
                    2);
        } else {
            this.ballCurrentLocation = 413;
            this.ballLastLocation = 0;
            this.lastEvent = "";
            this.currentEvent = "after basket inbound pass";
            this.activeOffensivePlayer = selectPlayerByRosterPosition(HOME_TEAM,
                    3);
            this.lastActiveOffensivePlayer = selectPlayerByRosterPosition(HOME_TEAM,
                    5);
            this.activeDefensivePlayer = selectPlayerByRosterPosition(AWAY_TEAM,
                    3);
            this.lastActiveDefensivePlayer = selectPlayerByRosterPosition(AWAY_TEAM,
                    5);
        }

        /**
         * Setting inPlayerId and outPlayerId fields to the value of the
         * offensive players just to satisfy foreign key requirements
         */
        playerInId = activeOffensivePlayer.getBaseAttributes().getId();
        playerOutId = lastActiveOffensivePlayer.getBaseAttributes().getId();
    }

    /**
     * Directs the event processing to the appropriate method
     */
    public void processCurrentEvent() {

        if (!this.isPaused()) {

            try {
                System.out.println("Last event: " + lastEvent); // delete
                System.out.println("Current event: " + currentEvent); // delete
                System.out.println("Time left: " + timeLeft); // delete

                if (this.currentEvent.equalsIgnoreCase("end of game")) {
                    processEndGame();
                } else if (this.currentEvent.equalsIgnoreCase("start of period")) {
                    startPeriod(this.period);
                } else if (this.currentEvent.equalsIgnoreCase("missed shot")) {
                    processMissedShot();
                } else if (this.currentEvent.equalsIgnoreCase("shooting foul")) {
                    processShootingFoul();
                } else if (this.currentEvent.equalsIgnoreCase("free-throw")) {
                    processFreeThrow();
                } else if (this.timeLeft <= 0) {
                    processEndOfPeriod();
                } else if (this.currentEvent.equalsIgnoreCase("tip-off")) {
                    processTipOff();
                } else if (this.currentEvent.equalsIgnoreCase("loose-ball")) {
                    processLooseBall();
                } else if (this.currentEvent.equalsIgnoreCase("ball to playmaker")) {
                    processBallToPlaymaker();
                } else if (this.currentEvent.equalsIgnoreCase("player decision")) {
                    processPlayerDecision();
                } else if (this.currentEvent.equalsIgnoreCase("ball to offensive court")) {
                    processBallToOffensiveCourt();
                } else if (this.currentEvent.equalsIgnoreCase("pass")) {
                    processPass(-1, null);
                } else if (this.currentEvent.equalsIgnoreCase("carry ball")) {
                    processCarryBall();
                } else if (this.currentEvent.equalsIgnoreCase("look for pass")) {
                    processLookForPass();
                } else if (this.currentEvent.equalsIgnoreCase("dribble")) {
                    processDribble();
                } else if (this.currentEvent.equalsIgnoreCase("completed pass")) {
                    processCompletedPass();
                } else if (this.currentEvent.equalsIgnoreCase("bad pass turnover")) {
                    processBadPassTurnover();
                } else if (this.currentEvent.equalsIgnoreCase("after turnover inbound pass")) {
                    processInboundPass();
                } else if (this.currentEvent.equalsIgnoreCase("drive")) {
                    processDrive();
                } else if (this.currentEvent.equalsIgnoreCase("hit shot")) {
                    processHitShot();
                } else if (this.currentEvent.equalsIgnoreCase("rebound")) {
                    processRebound();
                } else if (this.currentEvent.equalsIgnoreCase("fake")) {
                    processFake();
                } else if (this.currentEvent.equalsIgnoreCase("after basket inbound pass")) {
                    processAfterBasketInboundPass();
                } else if (this.currentEvent.equalsIgnoreCase("back down")) {
                    processBackDown();
                } else if (this.currentEvent.equalsIgnoreCase("failed back down")) {
                    processFailedBackDown();
                } else if (this.currentEvent.equalsIgnoreCase("inbound pass")) {
                    processInboundPass();
                } else if (this.currentEvent.equalsIgnoreCase("block")) {
                    processBlock();
                } else if (this.currentEvent.equalsIgnoreCase("pass deflected")) {
                    processPassDeflected();
                } else if (this.currentEvent.equalsIgnoreCase("charging foul")) {
                    processChargingFoul();
                } else if (this.currentEvent.equalsIgnoreCase("blocking foul")) {
                    processBlockingFoul();
                } else if (this.currentEvent.equalsIgnoreCase("reaching foul")) {
                    processReachingFoul();
                } else if (this.currentEvent.equalsIgnoreCase("intentional foul")) {
                    processIntentionalFoul();
                } else if (this.currentEvent.equalsIgnoreCase("shotclock violation")) {
                    processShotclockViolation();
                } else if (this.currentEvent.equalsIgnoreCase("loses control")) {
                    processLosesControl();
                } else if (this.currentEvent.equalsIgnoreCase("steal")) {
                    processSteal();
                } else if (this.currentEvent.equalsIgnoreCase("ball to outside shooter")) {
                    processBallToOutsideShooter();
                } else if (this.currentEvent.equalsIgnoreCase("ball to jump shooter")) {
                    processBallToJumpShooter();
                } else if (this.currentEvent.equalsIgnoreCase("ball to hottest shooter")) {
                    processBallToHottestShooter();
                } else if (this.currentEvent.equalsIgnoreCase("buzzer shot play")) {
                    processBuzzerPlayShot();
                } else if (this.currentEvent.equalsIgnoreCase("traveling violation")) {
                    processTravelingViolation();
                } else if (this.currentEvent.equalsIgnoreCase("low-post play")) {
                    processLowPostPlay();
                } else if (this.currentEvent.equalsIgnoreCase("quick shot")) {
                    processQuickShot();
                } else if (this.currentEvent.equalsIgnoreCase("shoot")) {
                    processShot();
                } else if (this.currentEvent.equalsIgnoreCase("end of period")) {
                    processEndOfPeriod();
                } else {
                    this.lastEvent = this.currentEvent;
                    this.currentEvent = "pass";
                    System.out.println("Unhandled event: " + this.lastEvent);
                }
            } catch (Exception ex) {
                this.inProgress = false;
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("--------------------");
                System.err.println("Message: " + ex.getMessage());
                System.err.println("--------------------");
                currentEvent = "end of game";
            }
        }
    }

    /**
     * Processes tip-off events
     */
    private void processTipOff() {

        /**
         * Recording start of the period
         */
        System.out.println("Start of period"); // delete
        this.currentEvent = "start of period";
        createPlay();

        /**
         * Setting tip-off
         */
        this.currentEvent = "tip-off";
        System.out.println("Tip-off"); // delete

        /**
         * Calculating efforts for offense and defense
         */
        this.offensiveEffort = activeOffensivePlayer.calculateJumpBallEffort(
                teams.get(ballPossession).getCoach());
        this.defensiveEffort = activeDefensivePlayer.calculateJumpBallEffort(
                teams.get(3 - ballPossession).getCoach());
        System.out.println("Tip-off efforts calculated"); // delete

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        if (this.offensiveEffort > this.defensiveEffort) {
            this.ballPossession = HOME_TEAM;
            this.tipOffWinner = HOME_TEAM;
        } else {
            this.ballPossession = AWAY_TEAM;
            this.tipOffWinner = AWAY_TEAM;
        }

        /**
         * Moving the ball
         */
        this.ballLastLocation = this.ballCurrentLocation;
        this.ballCurrentLocation = MathUtils.generateRandomInt(166, 270);

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "loose-ball";
        System.out.println("End tip-off"); // delete
    }

    /**
     * Processes buzzer shot play events
     */
    private void processBuzzerPlayShot() {

        /**
         * Active player runs the clock down to 5 seconds, drives then makes a
         * decision
         */
        this.currentEvent = "run the clock";

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Elapsing time
         */
        this.elapsedTime = this.shotClock - 6;
        doPlay(elapsedTime);

        /**
         * Driving
         */
        this.currentEvent = "drive";
    }

    /**
     * Processes loose-ball events
     */
    private void processLooseBall() {

        /**
         * Changing active and last active players to ensure motion
         */
        this.lastActiveOffensivePlayer = activeOffensivePlayer;
        this.lastActiveDefensivePlayer = activeDefensivePlayer;
        this.activeOffensivePlayer = getRandomPlayer(lastActiveOffensivePlayer
                .getRosterPosition(), ballPossession);
        this.activeDefensivePlayer = getRandomPlayer(lastActiveDefensivePlayer
                .getRosterPosition(), 3 - ballPossession);

        /**
         * Setting active offensive player and active defensive player new
         * locations
         */
        this.activeOffensivePlayer.setCurrentZoneLocation(courtSpots
                .get(ballCurrentLocation).getCourtZone());
        this.activeDefensivePlayer.setCurrentZoneLocation(courtSpots
                .get(ballCurrentLocation).getCourtZone());

        /**
         * Elapsing time
         */
        this.elapsedTime = MathUtils.generateRandomInt(1, 3);
        doPlay(elapsedTime);

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Processes ball to playmaker event
     */
    private void processBallToPlaymaker() {
        /**
         * Moving the ball to the playmaker. First we calculate the distance
         * between the player who currently has the ball and the playmaker to
         * calculate the elapsed time
         */
        InGamePlayer playmaker = teams.get(ballPossession).getBestPlaymaker();
        int activeOffensivePlayerSpot = CourtUtils.getRandomCourtZoneSpot(
                this.activeOffensivePlayer.getCurrentZoneLocation(), connection);
        int playmakerSpot = CourtUtils.getRandomCourtZoneSpot(
                playmaker.getCurrentZoneLocation(), connection);
        CourtSpot activeOffensivePlayerCourtSpot = courtSpots.get(activeOffensivePlayerSpot);
        CourtSpot playmakerCourtSpot = courtSpots.get(playmakerSpot);
        double distance = CourtUtils.distanceBetweenCourtSpots(activeOffensivePlayerCourtSpot,
                playmakerCourtSpot);

        /**
         * Updating ball location
         */
        updateBallLocation(ballCurrentLocation, playmakerSpot);

        /**
         * Updating active and last active players
         */
        lastActiveOffensivePlayer = activeOffensivePlayer;
        activeOffensivePlayer = playmaker;
        lastActiveDefensivePlayer = activeDefensivePlayer;
        activeDefensivePlayer = getRandomPlayer(lastActiveDefensivePlayer.getRosterPosition(),
                3 - ballPossession);

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(distance / 7, 1);
        doPlay(elapsedTime);

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Processes player decision
     */
    private void processPlayerDecision() {
        this.currentEvent = activeOffensivePlayer.getPlayerDecision(this);
    }

    /**
     * Processes the ball to offensive court event
     */
    private void processBallToOffensiveCourt() {
        /**
         * Select a random spot in the offensive court, calculate the distance
         * between the original spot and the new one, elapse the time and pass
         * the decision to the player
         */
        int originalSpot = ballCurrentLocation;
        int newSpot = CourtUtils.getRandomCourtZoneSpot(
                CourtZones.OFFENSIVE_HALFCOURT.getCourtZone(), connection);
        CourtSpot originalCourtSpot = courtSpots.get(originalSpot);
        CourtSpot newCourtSpot = courtSpots.get(newSpot);
        double distance = CourtUtils.distanceBetweenCourtSpots(originalCourtSpot,
                newCourtSpot);

        /**
         * Updating ball location
         */
        updateBallLocation(ballCurrentLocation, newSpot);

        /**
         * Moving active players
         */
        activeOffensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());
        activeDefensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(distance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Processes pass events
     *
     * @param passDestinationZone Zone to where the pass is headed, if it's -1,
     * a random one is picked
     * @param passDestinationPlayer Player to whom the pass is thrown, if it's
     * null, a random one is picked
     */
    private void processPass(int passDestinationZone, InGamePlayer passDestinationPlayer) {
        /**
         * Since this isn't generally a more acute pass, we retrieve a random
         * location in the offensive halfcourt, pick a player near the that
         * spot, calculate the distance, move the ball and pass the decision
         * back to the new active offensive player
         */
        int originalSpot = ballCurrentLocation;
        int destinationZone;

        /**
         * Checking if it's a random pass or a directed one
         */
        if (passDestinationZone == -1) {
            destinationZone = MathUtils.generateRandomInt(1, 13);
        } else {
            destinationZone = passDestinationZone;
        }

        int newSpot = CourtUtils.getRandomCourtZoneSpot(destinationZone, connection);
        CourtSpot originalCourtSpot = courtSpots.get(originalSpot);
        CourtSpot newCourtSpot = courtSpots.get(newSpot);
        double distance = CourtUtils.distanceBetweenCourtSpots(originalCourtSpot,
                newCourtSpot);
        System.out.println("Pass origin: " + originalSpot); // delete
        System.out.println("Pass destination: " + newSpot); // delete
        System.out.println("Pass distance: " + distance); // delete

        /**
         * If the original spot and the destination spot are the same, suppress
         * the pass and turn into a fake move *
         */
        if (originalSpot == newSpot) {
            this.lastEvent = this.currentEvent;
            this.currentEvent = "fake";
            return;
        }

        /**
         * Calculating offensive and defensive efforts
         */
        this.offensiveEffort = activeOffensivePlayer.calculatePassEffort(
                teams.get(ballPossession).getCoach());
        this.defensiveEffort = activeDefensivePlayer.calculateDefendPassEffort(
                teams.get(3 - ballPossession).getCoach());

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(distance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Checking whether there's a turnover, the pass was completed or
         * deflected to define the current event
         *
         * Bad pass turnover
         */
        if (offensiveEffort < MathUtils.generateRandomInt(1, (int) distance)
                || offensiveEffort < MathUtils.generateRandomInt(0, 10)) {
            this.lastEvent = this.currentEvent;
            this.currentEvent = "bad pass turnover";
        } else if (defensiveEffort > offensiveEffort) {
            /**
             * deflected pass
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "pass deflected";
        } else {
            /**
             * Completed pass, the active offensive players becomes the last
             * active offensive player and the receiver the active one
             */
            lastActiveOffensivePlayer = activeOffensivePlayer;

            /**
             * If the pass is a directed one, the destination player becomes the
             * active offensive player
             */
            if (passDestinationPlayer == null) {
                activeOffensivePlayer = getRandomPlayer(lastActiveOffensivePlayer.getRosterPosition(),
                        ballPossession);
            } else {
                activeOffensivePlayer = passDestinationPlayer;
            }

            lastActiveDefensivePlayer = activeDefensivePlayer;
            activeDefensivePlayer = getRandomPlayer(lastActiveDefensivePlayer.getRosterPosition(),
                    3 - ballPossession);

            /**
             * Updating ball location
             */
            updateBallLocation(ballCurrentLocation, newSpot);

            /**
             * Moving active players
             */
            activeOffensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());
            activeDefensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());

            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "completed pass";
        }

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();
    }

    /**
     * Processes completed pass events
     */
    private void processCompletedPass() {
        /**
         * The pass is completed and the decision is handed back to the player
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";

    }

    /**
     * Processes carry ball events
     */
    private void processCarryBall() {
        /**
         * Moving the ball into one of the start offense zones (2, 8, 13) and
         * passing the decision back to the player
         */
        int originalSpot = ballCurrentLocation;
        int destinationZone = startOffenseZone.get(MathUtils.generateRandomInt(0,
                startOffenseZone.size() - 1));
        int newSpot = CourtUtils.getRandomCourtZoneSpot(destinationZone, connection);
        CourtSpot originalCourtSpot = courtSpots.get(originalSpot);
        CourtSpot newCourtSpot = courtSpots.get(newSpot);
        double distance = CourtUtils.distanceBetweenCourtSpots(originalCourtSpot,
                newCourtSpot);

        /**
         * Updating ball location
         */
        updateBallLocation(ballCurrentLocation, newSpot);

        /**
         * Moving active players
         */
        activeOffensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());
        activeDefensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(distance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Processes charging foul events
     */
    private void processChargingFoul() {
        /**
         * Register a turnover and adding a personal foul for the active player
         * and the offensive team, then switch the possession to the other team
         * and call for the after turnover inbound pass event
         */
        activeOffensivePlayer.updateTurnovers();
        activeOffensivePlayer.updatePersonalFouls();
        teams.get(ballPossession).updateTurnovers();
        teams.get(ballPossession).updateFouls();
        teams.get(ballPossession).updateTotalFouls();

        /**
         * Switching possession
         */
        this.ballPossession = 3 - this.ballPossession;

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        if (activeOffensivePlayer.getPersonalFouls() == GameParameters.PERSONAL_FOULS_LIMIT
                .getParameterValue()) {
            activeOffensivePlayer.setEjected(true);
            activeOffensivePlayer.getBaseAttributes().setPlayable(false);

            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "fouled out";

            /**
             * Creating play log and adding it to the observable list
             */
            createPlay();
        }

        /**
         * Resetting shotclock
         */
        resetShotClock();

        /**
         * Checking for substitutions
         */
        checkForSubstitution();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "after turnover inbound pass";
    }

    /**
     * Processes blocking foul events
     */
    private void processBlockingFoul() {
        /**
         * Register a foul for the defensive player then check if the team has
         * already reached the limit of collective fouls to decide if the next
         * event it's an inbound pass or a free-throw
         */
        activeDefensivePlayer.updatePersonalFouls();
        teams.get(3 - ballPossession).updateFouls();
        teams.get(3 - ballPossession).updateTotalFouls();

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Checking whether the defensive player was fouled out
         */
        if (activeDefensivePlayer.getPersonalFouls() == GameParameters.PERSONAL_FOULS_LIMIT.getParameterValue()) {
            activeDefensivePlayer.setEjected(true);
            activeDefensivePlayer.getBaseAttributes().setPlayable(false);

            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "fouled out";

            /**
             * Creating play log and adding it to the observable list
             */
            createPlay();
        }

        /**
         * Resetting shotclock
         */
        resetShotClock();

        /**
         * Checking for substitutions
         */
        checkForSubstitution();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;

        if (this.teams.get(ballPossession).getFouls()
                > GameParameters.COLLECTIVE_FOULS_LIMIT.getParameterValue()) {
            this.freeThrowsToShoot = 2;
            this.currentEvent = "free-throw";
        } else {
            this.currentEvent = "after turnover inbound pass";
        }
    }

    /**
     * Processes reaching foul events
     */
    private void processReachingFoul() {
        /**
         * Register a foul for the defensive player then check if the team has
         * already reached the limit of collective fouls to decide if the next
         * event it's an inbound pass or a free-throw
         */
        activeDefensivePlayer.updatePersonalFouls();
        teams.get(3 - ballPossession).updateFouls();
        teams.get(3 - ballPossession).updateTotalFouls();

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Checking whether the defensive player was fouled out
         */
        if (activeDefensivePlayer.getPersonalFouls() == GameParameters.PERSONAL_FOULS_LIMIT.getParameterValue()) {
            activeDefensivePlayer.setEjected(true);
            activeDefensivePlayer.getBaseAttributes().setPlayable(false);

            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "fouled out";

            /**
             * Creating play log and adding it to the observable list
             */
            createPlay();
        }

        /**
         * Resetting shotclock
         */
        resetShotClock();

        /**
         * Checking for substitutions
         */
        checkForSubstitution();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;

        if (this.teams.get(ballPossession).getFouls()
                > GameParameters.COLLECTIVE_FOULS_LIMIT.getParameterValue()) {
            this.freeThrowsToShoot = 2;
            this.currentEvent = "free-throw";
        } else {
            this.currentEvent = "after turnover inbound pass";
        }
    }

    /**
     * Processes intentional foul events
     */
    private void processIntentionalFoul() {
        /**
         * Register a foul for the defensive player then check if the team has
         * already reached the limit of collective fouls to decide if the next
         * event it's an inbound pass or a free-throw
         */
        activeDefensivePlayer.updatePersonalFouls();
        teams.get(3 - ballPossession).updateFouls();
        teams.get(3 - ballPossession).updateTotalFouls();

        System.out.println("Inside intentional foul");
        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Checking whether the defensive player was fouled out
         */
        if (activeDefensivePlayer.getPersonalFouls() == GameParameters.PERSONAL_FOULS_LIMIT.getParameterValue()) {
            activeDefensivePlayer.setEjected(true);
            activeDefensivePlayer.getBaseAttributes().setPlayable(false);

            /**
             * Updating events
             */
            this.currentEvent = "fouled out";

            /**
             * Creating play log and adding it to the observable list
             */
            createPlay();
        }

        /**
         * Resetting shotclock
         */
        resetShotClock();

        /**
         * Updating events
         */
        this.lastEvent = "intentional foul";

        if (this.teams.get(3 - ballPossession).getFouls()
                > GameParameters.COLLECTIVE_FOULS_LIMIT.getParameterValue()) {
            this.freeThrowsToShoot = 2;
            this.currentEvent = "free-throw";
        } else {
            this.currentEvent = "after turnover inbound pass";
        }

        /**
         * Checking for substitutions
         */
        checkForSubstitution();
    }

    /**
     * Processes shotclock violation events
     */
    private void processShotclockViolation() {
        /**
         * Register a turnover for the active player and the offensive team,
         * then switch the possession to the other team and call for the after
         * turnover inbound pass event
         */
        activeOffensivePlayer.updateTurnovers();
        teams.get(ballPossession).updateTurnovers();

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Switching possession and active players
         */
        this.ballPossession = 3 - this.ballPossession;
        activeOffensivePlayer = getRandomPlayer(0, ballPossession);
        lastActiveOffensivePlayer = getRandomPlayer(activeOffensivePlayer.getRosterPosition(),
                ballPossession);
        activeDefensivePlayer = getRandomPlayer(0, 3 - ballPossession);
        lastActiveDefensivePlayer = getRandomPlayer(activeDefensivePlayer.getRosterPosition(),
                3 - ballPossession);

        /**
         * Resetting shotclock
         */
        resetShotClock();

        /**
         * Checking for substitutions
         */
        checkForSubstitution();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "after turnover inbound pass";
    }

    /**
     * Processes look for pass events
     */
    private void processLookForPass() {
        /**
         * We simply run the clock some few seconds and pass the decision back
         * to the player
         *
         * Elapsing time
         */
        this.elapsedTime = MathUtils.generateRandomInt(2, 5);
        doPlay(elapsedTime);

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Processes dribble events
     */
    private void processDribble() {
        /**
         * We simply run the clock some few seconds and pass the decision back
         * to the player
         *
         * Elapsing time
         */
        this.elapsedTime = MathUtils.generateRandomInt(2, 5);
        doPlay(elapsedTime);

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Processes failed back down events
     */
    private void processFailedBackDown() {
        /**
         * We simply run the clock some few seconds and pass the decision back
         * to the player
         *
         * Elapsing time
         */
        this.elapsedTime = MathUtils.generateRandomInt(2, 5);
        doPlay(elapsedTime);

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Processes block events
     */
    private void processBlock() {
        /**
         * Register a block for the active defensive player and for the
         * defensive team, then generate a loose-ball event and a new location
         * for the ball
         */
        activeDefensivePlayer.updateBlocks();
        activeDefensivePlayer.updateDefensiveMomentum(1);
        activeOffensivePlayer.updateOffensiveMomentum(-3);
        activeOffensivePlayer.updateBlockedShots();
        teams.get(3 - ballPossession).updateBlocks();
        teams.get(ballPossession).updateBlockedShots();

        /**
         * Elapsing time
         */
        this.elapsedTime = 1;
        doPlay(elapsedTime);

        /**
         * Updating events
         */
        this.currentEvent = "blocked " + this.shotDescription;

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Updating ball location and possession data
         */
        updateBallLocation(ballCurrentLocation, MathUtils.generateRandomInt(1, 430));
        int nextPossession = ballPossession;

        /**
         * If the new ball location is greater than 420, the ball was sent out
         * of bounds
         */
        if (ballCurrentLocation > 420) {
            /**
             * Updating events and setting a new ball location to its old
             * location
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "inbound pass";
            this.ballCurrentLocation = this.ballLastLocation;

        } else {
            /**
             * Generating next possession
             */
            nextPossession = MathUtils.generateRandomInt(1, 2);

            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "loose-ball";
        }

        /**
         * If the team that got the loose-ball was the one that was defending,
         * reset the clock and set the ball into the defensive halfcourt
         */
        if (nextPossession != this.ballPossession) {
            resetShotClock();
            this.ballPossession = nextPossession;
            this.ballCurrentLocation = MathUtils.generateRandomInt(211, 420);
        }

    }

    /**
     * Processes pass deflected events
     */
    private void processPassDeflected() {
        /**
         * Elapsing time
         */
        this.elapsedTime = 1;
        doPlay(elapsedTime);

        /**
         * Updating ball location
         */
        updateBallLocation(ballCurrentLocation, MathUtils.generateRandomInt(1, 430));

        System.out.println("Ball deflected to spot: " + ballCurrentLocation); // delete

        /**
         * If the new ball location is greater than 420, the ball was sent out
         * of bounds
         */
        if (ballCurrentLocation > 420) {
            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "inbound pass";

            /**
             * Generating a new ball current location
             */
            this.ballCurrentLocation = MathUtils.generateRandomInt(1, 420);
        } else {
            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "loose-ball";
        }
    }

    /**
     * Processes bad pass turnover events
     */
    private void processBadPassTurnover() {
        /**
         * Register a turnover for the active player and for the offensive team,
         * then switch the possession to the other team and call for the after
         * turnover inbound pass event
         */
        activeOffensivePlayer.updateTurnovers();
        teams.get(ballPossession).updateTurnovers();

        /**
         * Switching possession
         */
        this.ballPossession = 3 - this.ballPossession;

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Resetting shotclock
         */
        resetShotClock();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "after turnover inbound pass";
    }

    /**
     * Processes traveling violation events
     */
    private void processTravelingViolation() {
        /**
         * Register a turnover for the active player and the offensive team,
         * then switch the possession to the other team and call for the after
         * turnover inbound pass event
         */
        activeOffensivePlayer.updateTurnovers();
        teams.get(ballPossession).updateTurnovers();


        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Switching possession and active players
         */
        this.ballPossession = 3 - this.ballPossession;
        activeOffensivePlayer = getRandomPlayer(0, ballPossession);
        lastActiveOffensivePlayer = getRandomPlayer(activeOffensivePlayer.getRosterPosition(),
                ballPossession);
        activeDefensivePlayer = getRandomPlayer(0, 3 - ballPossession);
        lastActiveDefensivePlayer = getRandomPlayer(activeDefensivePlayer.getRosterPosition(),
                3 - ballPossession);

        /**
         * Resetting shotclock
         */
        resetShotClock();

        /**
         * Checking for substitutions
         */
        checkForSubstitution();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "after turnover inbound pass";
    }

    /**
     * Processes loses control events
     */
    private void processLosesControl() {
        /**
         * Register a turnover for the active player and for the offensive team,
         * then switch the possession to the other team and call for the after
         * turnover inbound pass event
         */
        activeOffensivePlayer.updateTurnovers();
        teams.get(ballPossession).updateTurnovers();

        /**
         * Switching possession
         */
        this.ballPossession = 3 - this.ballPossession;

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Resetting shotclock
         */
        resetShotClock();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "after turnover inbound pass";
    }

    /**
     * Processes steal events
     */
    private void processSteal() {
        /**
         * Register a turnover for the active player and for the offensive team,
         * add a steal for the active defender and the defensive team, then
         * switch the possession to the other team
         */
        activeOffensivePlayer.updateTurnovers();
        teams.get(ballPossession).updateTurnovers();
        activeDefensivePlayer.updateSteals();
        teams.get(3 - ballPossession).updateSteals();

        /**
         * Switching possession
         */
        this.ballPossession = 3 - this.ballPossession;

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Switching active players
         */
        this.activeOffensivePlayer = activeDefensivePlayer;
        this.lastActiveOffensivePlayer = getRandomPlayer(activeOffensivePlayer
                .getRosterPosition(), ballPossession);
        this.activeDefensivePlayer = getRandomPlayer(0, 3 - ballPossession);
        this.lastActiveDefensivePlayer = getRandomPlayer(activeDefensivePlayer
                .getRosterPosition(), 3 - ballPossession);

        /**
         * Resetting shotclock, setting new ball location and player's current
         * zone location (defensive halfcourt)
         */
        resetShotClock();
        this.ballCurrentLocation = MathUtils.generateRandomInt(211, 420);
        this.activeDefensivePlayer.setCurrentZoneLocation(CourtZones.DEFENSIVE_HALFCOURT
                .getCourtZone());
        this.activeOffensivePlayer.setCurrentZoneLocation(CourtZones.DEFENSIVE_HALFCOURT
                .getCourtZone());
        this.lastActiveDefensivePlayer.setCurrentZoneLocation(CourtZones.DEFENSIVE_HALFCOURT
                .getCourtZone());
        this.lastActiveOffensivePlayer.setCurrentZoneLocation(CourtZones.DEFENSIVE_HALFCOURT
                .getCourtZone());

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Processes ball to outside shooter events
     */
    private void processBallToOutsideShooter() {
        /**
         * Finding the best outside shooter, if he's the player who already has
         * the ball, another player decision event is generated
         *
         */
        InGamePlayer outsideShooter = teams.get(this.ballPossession).getBestOutsideShooter();

        if (activeOffensivePlayer.equals(outsideShooter)) {
            this.lastEvent = this.currentEvent;
            this.currentEvent = "player decision";
            return;
        }

        /**
         * If the outside shooter is another player, move him to a three-pointer
         * area and give him the ball
         */
        int passDestinationZone = CourtUtils.getRandomSectionedZone(threePointerZone);
        lastActiveOffensivePlayer = outsideShooter;

        /**
         * Processing pass
         */
        processPass(passDestinationZone, lastActiveOffensivePlayer);
    }

    /**
     * Processes quick shot events
     */
    private void processQuickShot() {
        /**
         * Finding the best quick shooter, if he's the player who already has
         * the ball, another player decision event is generated
         *
         */
        InGamePlayer quickShooter = teams.get(this.ballPossession).getBestQuickShooter();

        if (activeOffensivePlayer.equals(quickShooter)) {
            this.lastEvent = this.currentEvent;
            this.currentEvent = "pass";
        } else {

            /**
             * If the quick shooter is another player, move him to a field goal
             * area, give him the ball and make him shoot
             */
            int originalSpot = ballCurrentLocation;
            int destinationZone = CourtUtils.getRandomSectionedZone(jumpShotZone);
            int newSpot = CourtUtils.getRandomCourtZoneSpot(destinationZone, connection);
            CourtSpot originalCourtSpot = courtSpots.get(originalSpot);
            CourtSpot newCourtSpot = courtSpots.get(newSpot);
            double distance = CourtUtils.distanceBetweenCourtSpots(originalCourtSpot,
                    newCourtSpot);
            lastActiveOffensivePlayer = quickShooter;
            lastActiveOffensivePlayer.setCurrentZoneLocation(destinationZone);
            updateBallLocation(ballLastLocation, newSpot);

            /**
             * Elapsing time
             */
            this.elapsedTime = (int) Math.max(distance / 8, 1);
            doPlay(elapsedTime);

            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "completed pass";

            /**
             * Creating play log and adding it to the observable list
             */
            createPlay();

            /**
             * Making player shoot
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "shoot";
        }
    }

    /**
     * Processes low-post play events
     */
    private void processLowPostPlay() {
        /**
         * Finding the best low-post player, if he's the player who already has
         * the ball, another player decision event is generated
         *
         */
        System.out.println("Entering low post play"); // delete
        InGamePlayer lowPostShooterr = teams.get(this.ballPossession).getBestLowPostShooter();

        if (activeOffensivePlayer.equals(lowPostShooterr)) {
            this.lastEvent = this.currentEvent;
            this.currentEvent = "player decision";
            return;
        }

        /**
         * If the low-post shooter is another player, move him to a low-post
         * area and give him the ball
         */
        int passDestinationZone = CourtUtils.getRandomSectionedZone(lowPostZone);
        lastActiveOffensivePlayer = lowPostShooterr;

        /**
         * Processing pass
         */
        processPass(passDestinationZone, lastActiveOffensivePlayer);
    }

    /**
     * Processes ball to jump shooter events
     */
    private void processBallToJumpShooter() {
        /**
         * Finding the best jump shooter, if he's the player who already has the
         * ball, another player decision event is generated
         *
         */
        InGamePlayer jumpShooter = teams.get(this.ballPossession).getBestJumpShooter();

        if (activeOffensivePlayer.equals(jumpShooter)) {
            this.lastEvent = this.currentEvent;
            this.currentEvent = "player decision";
            return;
        }

        /**
         * If the jump shooter is another player, move him to a field goal area
         * and give him the ball
         */
        int passDestinationZone = CourtUtils.getRandomSectionedZone(jumpShotZone);
        lastActiveOffensivePlayer = jumpShooter;

        /**
         * Processing pass
         */
        processPass(passDestinationZone, lastActiveOffensivePlayer);
    }

    /**
     * Processes ball to hottest shooter events
     */
    private void processBallToHottestShooter() {
        /**
         * Finding the hottest shooter, if he's the player who already has the
         * ball, another player decision event is generated
         *
         */
        InGamePlayer hottestShooter = teams.get(this.ballPossession).getHottestShooter();

        if (activeOffensivePlayer.equals(hottestShooter)) {
            this.lastEvent = this.currentEvent;
            this.currentEvent = "player decision";
            return;
        }

        /**
         * If the jump shooter is another player, move him to a random area and
         * give him the ball
         */
        int passDestinationZone = -1;
        lastActiveOffensivePlayer = hottestShooter;

        /**
         * Processing pass
         */
        processPass(passDestinationZone, lastActiveOffensivePlayer);
    }

    /**
     * Processes after turnover inbound pass
     */
    private void processInboundPass() {

        /**
         * Checking for substitutions before inbounding the ball
         */
        checkForSubstitution();

        /**
         * Selecting the inbound pass zone.
         */
        int originalSpot = ballCurrentLocation;
        CourtSpot originalCourtSpot = courtSpots.get(originalSpot);
        int destinationZone = CourtUtils.getAdjacentZone(originalCourtSpot.getCourtZone());
        int newSpot = CourtUtils.getRandomCourtZoneSpot(destinationZone, connection);
        CourtSpot newCourtSpot = courtSpots.get(newSpot);

        /**
         * Selecting the new active players, the ball is usually inbounded to
         * the playmaker.
         */
        activeOffensivePlayer = teams.get(ballPossession).getBestPlaymaker();
        activeDefensivePlayer = getRandomPlayer(0, 3 - ballPossession);
        lastActiveOffensivePlayer = getRandomPlayer(activeOffensivePlayer.getRosterPosition(),
                ballPossession);
        lastActiveDefensivePlayer = getRandomPlayer(activeDefensivePlayer.getRosterPosition(),
                3 - ballPossession);

        /**
         * Moving the active players to the zone where the ball is inbounded
         */
        activeOffensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());
        activeDefensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());

        /**
         * Elapsing time
         */
        /*this.elapsedTime = 1;
         elapseTime(elapsedTime);*/
        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Processes drive events
     */
    private void processDrive() {
        /**
         * If the ball is in the low-post zone, back down
         */
        if (lowPostZone.contains(activeOffensivePlayer.getCurrentZoneLocation())) {
            this.currentEvent = "back down";
            return;
        }

        /**
         * Calculating offensive and defensive efforts
         */
        this.offensiveEffort = activeOffensivePlayer.calculateDriveEffort(
                teams.get(ballPossession).getCoach());
        this.defensiveEffort = activeDefensivePlayer.calculateDefendDriveEffort(
                teams.get(3 - ballPossession).getCoach());

        /**
         * If the offensive and defensive efforts are equal, check the referee's
         * charging foul rate to see if it's a offensive foul or a defensive one
         */
        if (this.offensiveEffort == this.defensiveEffort) {

            if (this.referee.getChargingFouls() > MathUtils.generateRandomInt(0, 200)) {
                /**
                 * Updating events
                 */
                this.lastEvent = this.currentEvent;
                this.currentEvent = "charging foul";
            } else {
                /**
                 * Updating events
                 */
                this.lastEvent = this.currentEvent;
                this.currentEvent = "blocking foul";
            }

            return;
        }

        /**
         * If the effort is more than 10% smaller than the defensive one, it
         * means the ball was stolen by the defender
         */
        if (this.offensiveEffort < (this.defensiveEffort * 0.9)) {
            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "steal";
            return;
        }

        /**
         * If the offensive effort is 1 to 10% smaller than the defensive one,
         * the active player loses control of the ball
         */
        if (this.offensiveEffort < (this.defensiveEffort)) {
            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "loses control";
            return;
        }

        /**
         * Checking for traveling violation
         */
        if (this.offensiveEffort < MathUtils.generateRandomInt(0, 5)) {
            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "traveling violation";
            return;
        }

        /**
         * If the offensive effort is greater than the defensive, the driving
         * attempt is successful and the active offensive player moves to an
         * adjacent zone. First, we check if there's no reaching foul by the
         * defender
         *
         * Selecting the spot where the player will drive to, since the player
         * is not supposed to drive back to the defensive halfcourt or the
         * offensive halfcourt, those 2 zones are discarded
         */
        if (this.referee.getReachingFouls() > MathUtils.generateRandomInt(0, 300)
                && activeDefensivePlayer.getBaseAttributes().getAggressiveness()
                > MathUtils.generateRandomInt(0, 300)) {
            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "reaching foul";
            return;
        }

        int originalSpot = ballCurrentLocation;
        CourtSpot originalCourtSpot = courtSpots.get(originalSpot);
        int destinationZone = 0;

        while (true) {
            destinationZone = CourtUtils.getAdjacentZone(originalCourtSpot.getCourtZone());

            if (destinationZone < 14) {
                break;
            }
        }
        int newSpot = CourtUtils.getRandomCourtZoneSpot(destinationZone, connection);
        CourtSpot newCourtSpot = courtSpots.get(newSpot);

        /**
         * Updating ball location
         */
        updateBallLocation(ballCurrentLocation, newSpot);

        /**
         * Elapsing time
         */
        this.elapsedTime = MathUtils.generateRandomInt(1, 2);
        doPlay(elapsedTime);

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Moving active player and select new defensive players
         */
        activeOffensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());
        lastActiveDefensivePlayer = activeDefensivePlayer;
        activeDefensivePlayer = getRandomPlayer(lastActiveDefensivePlayer.getRosterPosition(),
                3 - ballPossession);

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Processes back down events
     */
    private void processBackDown() {
        /**
         * Calculating offensive and defensive efforts
         */
        this.offensiveEffort = activeOffensivePlayer.calculateBackDownEffort(
                teams.get(ballPossession).getCoach());
        this.defensiveEffort = activeDefensivePlayer.calculateDefendBackDownEffort(
                teams.get(3 - ballPossession).getCoach());

        /**
         * If the offensive and defensive efforts are equal, check the referee's
         * charging foul rate to see if it's a offensive foul or a defensive one
         */
        if (this.offensiveEffort == this.defensiveEffort) {

            if (this.referee.getChargingFouls() > MathUtils.generateRandomInt(0, 100)) {
                /**
                 * Updating events
                 */
                this.lastEvent = this.currentEvent;
                this.currentEvent = "charging foul";
            } else {
                /**
                 * Updating events
                 */
                this.lastEvent = this.currentEvent;
                this.currentEvent = "failed back down";
            }

            return;
        }

        /**
         * If the effort is more than 10% smaller than the defensive one, it
         * means the ball was stolen by the defender
         */
        if (this.offensiveEffort < (this.defensiveEffort * 0.9)) {
            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "steal";
            return;
        }

        /**
         * If the offensive effort is 1 to 10% smaller than the defensive one,
         * the active player loses control of the ball
         */
        if (this.offensiveEffort < (this.defensiveEffort)) {
            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "loses control";
            return;
        }

        /**
         * Checking for traveling violation
         */
        if (this.offensiveEffort < MathUtils.generateRandomInt(0, 5)) {
            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "traveling";
            return;
        }

        /**
         * If the offensive effort is greater than the defensive, the backing
         * down attempt is successful and the active offensive player moves
         * inside the paint
         *
         * Selecting the spot where the player will back down to
         */
        int newSpot = MathUtils.generateRandomInt(7, 9);
        CourtSpot newCourtSpot = courtSpots.get(newSpot);

        /**
         * Updating ball location
         */
        updateBallLocation(ballCurrentLocation, newSpot);

        /**
         * Elapsing time
         */
        this.elapsedTime = MathUtils.generateRandomInt(1, 4);
        doPlay(elapsedTime);

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Moving active player and select new defensive players
         */
        activeOffensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());
        lastActiveDefensivePlayer = activeDefensivePlayer;
        activeDefensivePlayer = getRandomPlayer(lastActiveDefensivePlayer.getRosterPosition(),
                3 - ballPossession);

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Checks for substitutions. Each team can make up to 2 substitutions at a
     * time
     */
    private void checkForSubstitution() {
        int playerInRosterPosition;
        int playerOutRosterPosition;
        Team currentTeam;

        /**
         * Looping through each team to find players to substitute
         */
        for (int i = 1; i < 3; i++) {
            playerInRosterPosition = 0;
            playerOutRosterPosition = 0;
            currentTeam = this.teams.get(i);

            for (int j = 0; j < GameParameters.MAX_SUBSTITUTIONS.getParameterValue(); j++) {

                /**
                 * Retrieving if there's a player to leave the court, if there
                 * is, retrieve the player to enter
                 */
                playerOutRosterPosition = 0;
                playerOutRosterPosition = currentTeam.getPlayerToBeReplaced(this);

                if (playerOutRosterPosition > 0) {
                    playerInRosterPosition = currentTeam.getPlayerToReplace(
                            playerOutRosterPosition, this);

                    if (playerOutRosterPosition > 0) {
                        makeSubstitutions(i, playerInRosterPosition, playerOutRosterPosition);
                        System.out.println("In: " + playerInRosterPosition);
                        System.out.println("Out: " + playerOutRosterPosition);
                    }
                }
            }
        }
    }

    /**
     * Performs substitutions
     *
     * @param team Team making substitutions
     * @param playerInRosterPosition Player to enter the court
     * @param playerOutRosterPosition Player to leave the court
     */
    private void makeSubstitutions(int team, int playerInRosterPosition,
            int playerOutRosterPosition) {
        InGamePlayer playerIn = teams.get(team).getPlayers().get(playerInRosterPosition - 1);
        InGamePlayer playerOut = teams.get(team).getPlayers().get(playerOutRosterPosition - 1);
        String previousEvent = this.currentEvent;

        System.out.println(this.period + " - " + TimeUtils.intToTime(timeLeft) + " - Substitution: " + teams.get(team).getCompleteName()
                + " " + playerIn.getCompleteName() + " for "
                + playerOut.getCompleteName()); // delete

        System.out.println(playerIn.getCompleteName() + " - " + playerIn.toString()); // delete
        System.out.println(playerOut.getCompleteName() + " - " + playerOut.toString()); // delete

        /**
         * Checking player out, zeroing his seconds and recording his
         * substitution time
         */
        playerOut.setOnCourt(false);
        playerOut.setSecondsOnCourt((short) 0);
        playerOut.setSecondsInBench((short) 0);
        playerOut.setSubstitutionTime(this.timeLeft);
        playerOut.setCurrentZoneLocation(0);

        /**
         * Checking player in, zeroing his seconds and recording his
         * substitution time
         */
        playerIn.setOnCourt(true);
        playerIn.setSecondsOnCourt((short) 0);
        playerIn.setSecondsInBench((short) 0);
        playerIn.setSubstitutionTime(this.timeLeft);
        playerIn.setCurrentZoneLocation(15);

        /**
         * Setting fields that store the players involved
         */
        this.playerInId = playerIn.getBaseAttributes().getId();
        this.playerOutId = playerOut.getBaseAttributes().getId();

        /**
         * Updating events
         */
        this.currentEvent = "substitution";

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        System.out.println("Players on the floor: "
                + teams.get(team).getPlayersOnTheFloor()); // delete

        /**
         * Restoring the previous current event
         */
        this.currentEvent = previousEvent;
    }

    /**
     * Processes official timeout events
     */
    private void processOfficialTimeout() {
        String previousEvent = this.currentEvent;

        /**
         * Updating events and resting players
         */
        this.currentEvent = "official timeout";
        this.setOfficialTimeoutCalled(true);
        restPlayers(1);
        zeroMomenta();
        checkForSubstitution();

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Restoring the previous current event
         */
        this.currentEvent = previousEvent;
    }

    /**
     * Processes timeout events
     */
    private void processTimeout(int caller) {
        String previousEvent = this.currentEvent;

        /**
         * Updating team's stats and resting players
         */
        this.currentEvent = "timeout";
        this.teams.get(caller).updateTimeouts();
        this.teams.get(caller).setLastTimeoutCall(this.timeLeft);
        restPlayers(1);
        zeroMomenta();
        checkForSubstitution();

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();
       
        /**
         * Restoring the previous current event
         */
        this.lastEvent= this.currentEvent;
        this.currentEvent = previousEvent;
    }

    /**
     * Checks for timeouts
     */
    private void checkForTimeout() {
        /**
         * Checking for official timeout
         */
        if (this.timeLeft < GameParameters.QUARTER_LENGTH.getParameterValue() / 2
                && !this.isOfficialTimeoutCalled()) {
            processOfficialTimeout();
            return;
        }

        /**
         * Retrieving the team which has the ball's possession
         */
        Team possessionTeam = this.teams.get(ballPossession);

        /**
         * If the game is close and there's less than one minute to play in the
         * fourth quarter or overtime
         */
        if (this.period > 3 && this.timeLeft < 60 && this.gap < 10
                && this.ballPossession != this.leadingTeam
                && possessionTeam.getTimeoutsLeft() > 0) {
            processTimeout(ballPossession);
            return;
        }

        /**
         * If the game is close and there's less than one minute to play in the
         * fourth quarter or overtime
         */
        if (this.period > 3 && this.timeLeft < 60 && this.gap < 10
                && this.ballPossession == this.leadingTeam
                && possessionTeam.getTimeoutsLeft() > 0) {
            processTimeout(ballPossession);
            return;
        }

        /**
         * If the team is being outscored badly, call a timeout if none was
         * called in the last 2:00
         */
        if (possessionTeam.getTimeoutsLeft() > 0
                && this.ballPossession != this.leadingTeam && this.gap > 20
                && possessionTeam.getLastTimeoutCall() > this.timeLeft + 120) {
            processTimeout(ballPossession);
            return;
        }

        /**
         * If the opponent is a huge run, call a timeout if none was called in
         * the last 2:00
         */
        if (possessionTeam.getTimeoutsLeft() > 0
                && this.ballPossession != this.leadingTeam
                && this.teams.get(3 - ballPossession).getCurrentRun() > 10
                && possessionTeam.getLastTimeoutCall() > this.timeLeft + 120) {
            processTimeout(ballPossession);
        }
    }

    /**
     * Processes shoot event
     */
    private void processShot() {
        /**
         * Calculating shot distance
         */
        int shootingSpot = ballCurrentLocation;
        CourtSpot shootingCourtSpot = courtSpots.get(shootingSpot);
        CourtSpot basketCourtSpot = courtSpots.get(GameParameters.BASKET_COURT_SPOT.getParameterValue());
        double distance = CourtUtils.distanceBetweenCourtSpots(shootingCourtSpot,
                basketCourtSpot);
        System.out.println("Shot origin: " + shootingSpot); // delete       
        System.out.println("Shot distance: " + distance); // delete

        /*
         * Retrieving player decision and directing flow to the proper method
         */
        this.shotDescription = activeOffensivePlayer.getShootDecision(this,
                distance);

        System.out.println("After player getShootDecision: " + this.shotDescription); // delete

        switch (this.shotDescription) {
            case "long defensive halfcourt three-pointer":
                processLongDefensiveHalfcourtThreePointer(distance);
                break;
            case "long offensive halfcourt three-pointer":
                processLongOffensiveHalfcourtThreePointer(distance);
                break;
            case "three-pointer":
                processThreePointer(distance);
                break;
            case "pull-up jumper":
                processPullUpJumper(distance);
                break;
            case "fadeaway":
                processFadeawayShot(distance);
                break;
            case "bank shot":
                processBankShot(distance);
                break;
            case "layup":
                processLayup(distance);
                break;
            case "low-post layup":
                processLowPostLayup(distance);
                break;
            case "turnaround":
                processTurnaround(distance);
                break;
            case "hook":
                processHook(distance);
                break;
            case "running jumper":
                processRunningJumper(distance);
                break;
            case "floating jumper":
                processFloatingJumper(distance);
                break;
            case "dunk":
                processDunk(distance);
                break;
            case "finger-roll":
                processFingerRoll(distance);
                break;
            case "scoop shot":
                processScoopShot(distance);
                break;
        }
    }

    /**
     * Processes long defensive halfcourt three-pointer events
     */
    private void processLongDefensiveHalfcourtThreePointer(double shotDistance) {
        /**
         * Calculating only offensive effort, since shots from defensive
         * halfcourt aren't contested, then passing the flow to the evaluate
         * shot method
         */
        this.offensiveEffort = activeOffensivePlayer
                .calculateLongDefensiveHalfcourtThreePointerEffort(teams.get(ballPossession).getCoach(),
                this, shotDistance);
        this.defensiveEffort = 0;

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.DEFENSIVE_HALFCOURT_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes long offensive halfcourt three-pointer events
     */
    private void processLongOffensiveHalfcourtThreePointer(double shotDistance) {
        /**
         * Calculating only offensive effort, since shots from defensive
         * halfcourt aren't contested, then passing the flow to the evaluate
         * shot method
         */
        this.offensiveEffort = activeOffensivePlayer
                .calculateLongOffensiveHalfcourtThreePointerEffort(teams.get(ballPossession).getCoach(),
                this, shotDistance);
        this.defensiveEffort = 0;

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.OFFENSIVE_HALFCOURT_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes three-pointer events
     */
    private void processThreePointer(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateThreePointerEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestThreePointerEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.THREE_POINTER_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes pull-up jumper events
     */
    private void processPullUpJumper(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculatePullUpJumperEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestPullUpJumperEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.FIELD_GOAL_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes running jumper events
     */
    private void processRunningJumper(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateRunningJumperEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestRunningJumperEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.RUNNING_JUMPER_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes finger-roll events
     */
    private void processFingerRoll(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateFingerRollEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestFingerRollEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.FINGER_ROLL_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes scoop shot events
     */
    private void processScoopShot(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateScoopShotEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestScoopShotEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.SCOOP_SHOT_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes floating jumper events
     */
    private void processFloatingJumper(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateFloatingJumperEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestFloatingJumperEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.FLOATING_JUMPER_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes dunk events
     */
    private void processDunk(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateDunkEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestDunkEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.DUNK_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes layup events
     */
    private void processLayup(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateLayupEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestLayupEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.LAYUP_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes low-post layup events
     */
    private void processLowPostLayup(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateLowPostLayupEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestLowPostLayupEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.LAYUP_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes turnaround events
     */
    private void processTurnaround(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateTurnaroundEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestTurnaroundEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.TURNAROUND_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes hook events
     */
    private void processHook(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateHookEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestHookEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.TURNAROUND_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes fadeaway shot events
     */
    private void processFadeawayShot(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateFadeawayShotEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestFadeawayShotEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.FIELD_GOAL_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes bank shot events
     */
    private void processBankShot(double shotDistance) {
        /**
         * Calculating offensive and defensive efforts, then passing the flow to
         * the evaluate shot method
         */
        this.offensiveEffort = activeOffensivePlayer.calculateBankShotEffort(
                teams.get(ballPossession).getCoach(), this, shotDistance);

        if (this.isOpenLook()) {
            this.defensiveEffort = 0;
        } else {
            this.defensiveEffort = activeDefensivePlayer.calculateContestBankShotEffort(
                    teams.get(3 - ballPossession).getCoach(), this, shotDistance);
        }

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(shotDistance / 4, 1);
        doPlay(elapsedTime);

        /**
         * Handing flow to the evaluate shot method
         */
        evaluateShot(GameParameters.FIELD_GOAL_DIFFICULTY.getParameterValue()
                + shotDistance);
    }

    /**
     * Processes after basket inbound pass events
     */
    private void processAfterBasketInboundPass() {

        /**
         * Switching possession
         */
        this.ballPossession = 3 - this.ballPossession;

        /**
         * Checking timeout before inbounding the ball
         */
        checkForTimeout();

        /**
         * Selecting the inbound pass zone.If the game is in the last 2:00 of
         * the quarter and a timeout was called, the ball is inbounded in the
         * offensive halfcourt
         */
        int originalSpot;
        int destinationZone;

        if (this.timeLeft < 120 && this.lastEvent.equalsIgnoreCase("timeout")) {
            originalSpot = 106;
            destinationZone = MathUtils.generateRandomInt(1, 2);
        } else {
            originalSpot = 413;
            destinationZone = 15;            
        }

        int newSpot = CourtUtils.getRandomCourtZoneSpot(destinationZone, connection);
        CourtSpot newCourtSpot = courtSpots.get(newSpot);

        /**
         * Updating ball location
         */
        updateBallLocation(originalSpot, newSpot);

        /**
         * Selecting the new active players, the ball is usually inbounded to
         * the playmaker.
         */
        activeOffensivePlayer = teams.get(ballPossession).getBestPlaymaker();
        activeDefensivePlayer = getRandomPlayer(0, 3 - ballPossession);
        lastActiveOffensivePlayer = getRandomPlayer(activeOffensivePlayer.getRosterPosition(),
                ballPossession);
        lastActiveDefensivePlayer = getRandomPlayer(activeDefensivePlayer.getRosterPosition(),
                3 - ballPossession);

        /**
         * Moving the active players to the zone where the ball is inbounded
         */
        activeOffensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());
        activeDefensivePlayer.setCurrentZoneLocation(newCourtSpot.getCourtZone());

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Ends the game and realize database procedures
     */
    private void processEndGame() {
        this.inProgress = false;
        this.currentEvent = "end of game";
        createPlay();
        System.out.println("Game halted"); // delete
    }

    /**
     * Evaluates if a shot was hit or not
     *
     * @param shotDifficulty Shot's difficulty. To hit the shot, the offensive
     * player has to be equal or greater than this number, and also greater than
     * the defensive effort
     */
    private void evaluateShot(double shotDifficulty) {
        /**
         * Retrieving the basket points
         */
        int basketPoints = courtSpots.get(ballCurrentLocation).getBasketPoints();

        /**
         * Updating player's and team's shooting attempts. First we check it's a
         * field goal or a three-pointer
         */
        if (basketPoints == 3) {
            activeOffensivePlayer.updateThreePointersAttempted();
            teams.get(ballPossession).updateThreePointersAttempted();
        } else {
            activeOffensivePlayer.updateFieldGoalsAttempted();
            teams.get(ballPossession).updateFieldGoalsAttempted();
        }

        /**
         * If the defensive effort is 40% greater than the offensive effort, a
         * block happened
         */
        if (this.defensiveEffort > (this.offensiveEffort * 1.4)) {
            this.lastEvent = this.currentEvent;
            this.currentEvent = "block";
            return;
        }

        /**
         * If the defensive effort is greater or equal than the offensive effort
         * but less than 5% greater, is a missed shot
         */
        if (this.offensiveEffort <= (this.defensiveEffort * 1.05)) {

            this.lastEvent = this.currentEvent;
            this.currentEvent = "missed shot";
            return;
        }

        /**
         * If the offensive effort is 5% greater than the defensive one, check
         * if it reached at least the shotDifficulty parameter value to see it
         * was hit
         */
        if (this.offensiveEffort > shotDifficulty) {
            /**
             * Updating current event
             */
            this.currentEvent = "hit shot";
        } else {

            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "missed shot";
        }
    }

    /**
     * Processes missed shot events
     */
    private void processMissedShot() {
        /**
         * Updating current event and decreasing active offensive player's
         * offensive momentum
         */
        System.out.println("Entering missed shot: " + this.shotDescription); // delete
        if (isOpenLook()) {
            this.currentEvent = "missed open " + this.shotDescription;
            this.activeOffensivePlayer.updateOffensiveMomentum(-2);
        } else {
            this.currentEvent = "missed " + this.shotDescription;
            this.activeOffensivePlayer.updateOffensiveMomentum(-1);
            this.activeDefensivePlayer.updateDefensiveMomentum(1);
        }

        System.out.println("Missed shot: " + this.currentEvent); // delete

        /**
         * Creating play log and adding it to the observable list and resetting
         * the shotclock
         */
        createPlay();
        resetShotClock();

        /**
         * If it wasn't an open look, check the referee's shooting foul rate and
         * the defender's aggressiveness to see if it's a shooting foul or just
         * a missed shot
         */
        if (!openLook) {
            /**
             * Retrieving the points to be awarded if the shot was hit
             */
            int basketPoints = this.courtSpots.get(this.ballCurrentLocation)
                    .getBasketPoints();

            if (this.referee.getShootingFouls() > MathUtils.generateRandomInt(0,
                    50 * basketPoints)
                    && this.activeDefensivePlayer.getBaseAttributes().getAggressiveness()
                    > MathUtils.generateRandomInt(0, 50 * basketPoints)) {

                /**
                 * Updating events
                 */
                this.lastEvent = this.currentEvent;
                this.currentEvent = "shooting foul";

                /**
                 * Determining the number of free-throws to shoot and resetting
                 * the openlook field
                 */
                this.freeThrowsToShoot = basketPoints;
                this.setOpenLook(false);
                return;
            }
        }

        /**
         * Elapsing time, just to check if the time has expired
         *
         * this.elapsedTime = 0; elapseTime(elapsedTime);
         */
        /**
         * If there's no shooting foul, generate a rebound event and reset the
         * open look field
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "rebound";
        this.setOpenLook(false);
    }

    /**
     * Processes hit shot events
     */
    private void processHitShot() {
        /**
         * Retrieving basket points and setting the playscore field
         */
        int basketPoints = courtSpots.get(ballCurrentLocation).getBasketPoints();
        this.playScore = basketPoints;

        System.out.println("Last Event: " + this.lastEvent); // delete
        /**
         * Updating players' stats based on the basket's points: shooting stats,
         * scoring stats. Furthermore, we increase offensive player's offensive
         * momentum and decrease defensive player's defensive momentum
         */
        if (basketPoints == 3) {
            activeOffensivePlayer.updateThreePointersMade();
        } else {
            activeOffensivePlayer.updateFieldGoalsMade();
        }

        activeOffensivePlayer.updatePoints(basketPoints);
        activeOffensivePlayer.updateOffensiveMomentum(1);
        activeDefensivePlayer.updateDefensiveMomentum(-1);

        /**
         * Updating team's stats based on the basket's points: shooting stats,
         * scoring stats. Furthermore, we update its largest lead, and current
         * and biggest runs as well
         */
        if (basketPoints == 3) {
            teams.get(ballPossession).updateThreePointersMade();
        } else {
            teams.get(ballPossession).updateFieldGoalsMade();
        }

        teams.get(ballPossession).updateScore(basketPoints);
        teams.get(ballPossession).updateRuns(basketPoints);
        teams.get(ballPossession).updateLeads(basketPoints);
        teams.get(3 - ballPossession).updateRuns(0);
        teams.get(3 - ballPossession).updateLeads(0);

        /**
         * Updating current event
         */
        if (isOpenLook()) {
            this.currentEvent = "hit open " + this.shotDescription;
        } else {
            this.currentEvent = "hit " + this.shotDescription;
        }

        /**
         * Updating scoring strings
         */
        updateScoringStrings();

        /**
         * Creating play log, adding it to the observable list, resetting the
         * openlook to false and the shotclock
         */
        createPlay();
        setOpenLook(false);
        resetShotClock();

        /**
         * Checking if there was an assist to update assistant's stats and
         * offensive momentum
         */
        if (this.lastEvent.equalsIgnoreCase("completed pass")) {
            lastActiveOffensivePlayer.updateAssists();
            lastActiveOffensivePlayer.updateOffensiveMomentum(1);
            teams.get(ballPossession).updateAssists();

            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "assist";

            /**
             * Creating play log and adding it to the observable list
             */
            createPlay();
        }

        /**
         * If it wasn't an open look shot and the defensive effort was greater
         * than 90% of the offensive effort, check the referee's shooting foul
         * rate to see if there's a shooting foul
         */
        if (!isOpenLook() && this.defensiveEffort > (this.offensiveEffort * 0.9)) {
            if (this.referee.getShootingFouls() > MathUtils.generateRandomInt(0,
                    50 * basketPoints)) {
                /**
                 * Updating events
                 */
                this.lastEvent = this.currentEvent;
                this.currentEvent = "shooting foul";
                this.freeThrowsToShoot = 1;
                return;
            }
        }


        /**
         * If there's no shooting fall, update events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "after basket inbound pass";
    }

    /**
     * Processes shooting foul events
     */
    private void processShootingFoul() {
        /**
         * Updating active defensive player and defensive team stats
         */
        activeDefensivePlayer.updatePersonalFouls();
        teams.get(3 - ballPossession).updateFouls();
        teams.get(3 - ballPossession).updateTotalFouls();

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Checking whether the defensive player was fouled out
         */
        if (activeDefensivePlayer.getPersonalFouls() == GameParameters.PERSONAL_FOULS_LIMIT
                .getParameterValue()) {
            activeDefensivePlayer.setEjected(true);
            activeDefensivePlayer.getBaseAttributes().setPlayable(false);

            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "fouled out";

            /**
             * Creating play log and adding it to the observable list
             */
            createPlay();
        }

        /**
         * Signaling that the active player is shooting free-throws, so he can't
         * be replaced
         */
        activeOffensivePlayer.setShootingFreeThrows(true);
        checkForSubstitution();

        /**
         * Updating events and resetting the shotclock
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "free-throw";
        resetShotClock();
    }

    /**
     * Processes free-throw event
     */
    private void processFreeThrow() {
        /**
         * Calculating shooter's effort, updating his number of free-throws
         * attempted and decreasing the number of free-throws to shoot
         */
        this.offensiveEffort = activeOffensivePlayer.calculateFreeThrowEffort(
                teams.get(ballPossession).getCoach(), this);
        activeOffensivePlayer.updateFreeThrowsAttempted();
        teams.get(ballPossession).updateFreeThrowsAttempted();
        this.freeThrowsToShoot -= 1;

        /**
         * If this was the last free-throw shot by the player, set the
         * isShootingFreeThrow field to false
         */
        activeOffensivePlayer.setShootingFreeThrows(false);

        /**
         * Resting players
         */
        restPlayers(1);

        /**
         * If the game is not in fast mode, causes the thread to sleep
         */
        if (!this.fastMode) {
            try {
                Thread.sleep(MathUtils.generateRandomInt(2, 7) * 1000);

            } catch (InterruptedException ex) {
                Logger.getLogger(PlayGame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * If the offensive effort was greater than the shot difficulty, the
         * shot is hit and the stats are updated for the player and the team.
         * Furthermore the playscore field is updated
         */
        if (this.offensiveEffort > GameParameters.FREE_THROW_DIFFICULTY.getParameterValue()) {
            activeOffensivePlayer.updatePoints(1);
            activeOffensivePlayer.updateFreeThrowsMade();
            activeOffensivePlayer.updateOffensiveMomentum(1);
            teams.get(ballPossession).updateFreeThrowsMade();
            teams.get(ballPossession).updateScore(1);
            this.playScore = 1;

            /**
             * If the player hit the shot and there's more to shoot, the next
             * event is still a free-throw; otherwise is a inbound pass
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "hit free-throw";

            /**
             * Updating scoring strings
             */
            updateScoringStrings();

            /**
             * Creating play log and adding it to the observable list
             */
            createPlay();

            if (this.freeThrowsToShoot > 0) {
                this.currentEvent = "free-throw";
            } else {
                this.currentEvent = "after basket inbound pass";
            }

        } else {
            /**
             * If the player missed the shot and there's more to shoot, the next
             * event is still a free-throw; otherwise is a rebound
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "missed free-throw";

            /**
             * Creating play log and adding it to the observable list
             */
            createPlay();

            if (this.freeThrowsToShoot > 0) {
                this.currentEvent = "free-throw";
            } else {
                this.currentEvent = "rebound";
            }
        }
    }

    /**
     * Processes end of period events
     */
    private void processEndOfPeriod() {
        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "end of period";

        System.out.println("Entering end of period"); // delete
        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * If the period is the fourth and the game isn't tied, the game is
         * over, otherwise, start the next period
         */
        if (this.period < 4 || teams.get(1).getScore() == teams.get(2).getScore()) {
            this.period += 1;
            this.lastEvent = this.currentEvent;
            this.currentEvent = "start of period";
        } else {
            this.lastEvent = this.currentEvent;
            this.currentEvent = "end of game";
        }
    }

    /**
     * Processes rebound events
     */
    private void processRebound() {
        /**
         * Generating the ball next location
         */
        int originalSpot = ballCurrentLocation;
        int newSpot = CourtUtils.getRandomSectionedZone(reboundingZone);
        CourtSpot originalCourtSpot = courtSpots.get(originalSpot);
        CourtSpot newCourtSpot = courtSpots.get(newSpot);
        double distance = CourtUtils.distanceBetweenCourtSpots(originalCourtSpot,
                newCourtSpot);

        /**
         * Updating ball location
         */
        updateBallLocation(ballCurrentLocation, newSpot);

        /**
         * Elapsing time
         */
        this.elapsedTime = (int) Math.max(distance / 2, 1);
        doPlay(elapsedTime);

        /**
         * Changing the active players to those ones who are closest to the ball
         */
        activeOffensivePlayer = getRandomPlayer(0, ballPossession);
        lastActiveOffensivePlayer = getRandomPlayer(activeOffensivePlayer.getRosterPosition(),
                ballPossession);
        activeDefensivePlayer = getRandomPlayer(0, 3 - ballPossession);
        lastActiveDefensivePlayer = getRandomPlayer(activeDefensivePlayer.getRosterPosition(),
                3 - ballPossession);

        /**
         * Calculating efforts
         */
        this.offensiveEffort = activeOffensivePlayer.calculateOffensiveReboundEffort(
                teams.get(ballPossession).getCoach(), this);
        this.defensiveEffort = activeDefensivePlayer.calculateDefensiveReboundEffort(
                teams.get(3 - ballPossession).getCoach(), this);

        /**
         * If the active offensive player gets the rebound, it's an offensive
         * one and the shotclock is reset. Furthermore, player's and team's
         * stats are updated
         */
        if (offensiveEffort > defensiveEffort) {
            activeOffensivePlayer.updateOffensiveRebounds();
            activeOffensivePlayer.updateOffensiveMomentum(1);
            teams.get(ballPossession).updateOffensiveRebounds();

            /**
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "offensive rebound";

            /**
             * Creating play log and adding it to the observable list
             */
            createPlay();
        } else {
            /**
             * If the defensive player gets the rebounds, the shotclock is also
             * reset and the possession switches to his team. Furthermore,
             * player's and team's stats are updated and the active players are
             * switched
             *
             * Updating events
             */
            this.lastEvent = this.currentEvent;
            this.currentEvent = "defensive rebound";

            /**
             * Creating play log and adding it to the observable list
             */
            createPlay();

            /**
             * Updating stats and switching players
             */
            activeDefensivePlayer.updateDefensiveRebounds();
            activeDefensivePlayer.updateDefensiveMomentum(1);
            teams.get(3 - ballPossession).updateDefensiveRebounds();
            this.ballPossession = 3 - this.ballPossession;

            activeOffensivePlayer = activeDefensivePlayer;
            lastActiveOffensivePlayer = getRandomPlayer(activeOffensivePlayer.getRosterPosition(),
                    ballPossession);
            activeDefensivePlayer = getRandomPlayer(0, 3 - ballPossession);
            lastActiveDefensivePlayer = getRandomPlayer(activeDefensivePlayer.getRosterPosition(),
                    3 - ballPossession);

            /**
             * Setting up active players new locations
             */
            activeOffensivePlayer.setCurrentZoneLocation(15);
            activeDefensivePlayer.setCurrentZoneLocation(15);
            lastActiveOffensivePlayer.setCurrentZoneLocation(15);
            lastActiveDefensivePlayer.setCurrentZoneLocation(15);

            /**
             * Updating ball location
             */
            newSpot = MathUtils.generateRandomInt(301, 420);
            updateBallLocation(ballCurrentLocation, newSpot);
        }

        /**
         * Resetting the shot clock and updating events
         */
        resetShotClock();
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    private void processFake() {
        /**
         * Calculating the effort for active players
         */
        this.offensiveEffort = activeOffensivePlayer.calculateFakeEffort(
                teams.get(ballPossession).getCoach());
        this.defensiveEffort = activeDefensivePlayer.calculateDefendFakeEffort(
                teams.get(3 - ballPossession).getCoach());

        /**
         * Elapsing time
         */
        this.elapsedTime = MathUtils.generateRandomInt(1, 2);
        doPlay(elapsedTime);

        /**
         * If the offensive effort is greater than the defensive one, the fake
         * is successful and the active offensive player has an open look
         */
        if (offensiveEffort > defensiveEffort) {
            this.setOpenLook(true);
            lastActiveDefensivePlayer = activeDefensivePlayer;
            activeDefensivePlayer = getRandomPlayer(lastActiveDefensivePlayer.getRosterPosition(),
                    3 - ballPossession);
        } else {
            this.setOpenLook(false);
            this.currentEvent = "failed fake";
        }

        /**
         * Creating play log and adding it to the observable list
         */
        createPlay();

        /**
         * Updating events
         */
        this.lastEvent = this.currentEvent;
        this.currentEvent = "player decision";
    }

    /**
     * Creates a play object and adds to the observable list
     *
     * @return
     */
    private void createPlay() {

        Play currentPlay = new Play();
        currentPlay.setActiveDefensivePlayerId(activeDefensivePlayer.getBaseAttributes()
                .getId());
        currentPlay.setActiveOffensivePlayerId(activeOffensivePlayer.getBaseAttributes()
                .getId());
        currentPlay.setAwayScore(this.teams.get(1).getScore());
        currentPlay.setBallFrom(this.ballLastLocation);
        currentPlay.setBallTo(this.ballCurrentLocation);
        currentPlay.setGameDate(this.gameDate);
        currentPlay.setHomeScore(this.teams.get(2).getScore());
        currentPlay.setLastActiveDefensivePlayerId(lastActiveDefensivePlayer.getBaseAttributes()
                .getId());
        currentPlay.setLastActiveOffensivePlayerId(lastActiveOffensivePlayer.getBaseAttributes()
                .getId());
        currentPlay.setNarrationId(getGameNarration(this.currentEvent).getId());
        currentPlay.setPeriod(this.period);
        currentPlay.setPlayType(this.getCurrentEvent());
        currentPlay.setPlayDescription(parseNarration(getGameNarration(this.currentEvent)));
        currentPlay.setPlayerInId(playerInId);
        currentPlay.setPlayerOutId(playerOutId);
        currentPlay.setPlayScore(this.playScore);
        currentPlay.setSeason((short) this.season);
        currentPlay.setShotClock(this.shotClock);
        currentPlay.setTime(TimeUtils.intToTime(this.timeLeft));

        // delete
        System.out.println(this.period + " - " + TimeUtils.intToTime(this.timeLeft) + " - "
                + parseNarration(getGameNarration(this.currentEvent)));
        /*System.out.println("Open look: " + this.openLook);
         System.out.println("Offensive Effort: " + offensiveEffort);
         System.out.println("Defensive Effort: " + defensiveEffort);
         System.out.println("Ball Last Location: " + ballLastLocation);
         System.out.println("Ball Current Location: " + ballCurrentLocation);
         System.out.println("Narration Id: " + getGameNarration(this.currentEvent).getId());
         System.out.println("Active Offensive Player: " + activeOffensivePlayer.getCompleteName());
         System.out.println("Active Offensive Player Current Location: "
         + activeOffensivePlayer.getCurrentZoneLocation());
         System.out.println("Last Active Offensive Player: " + lastActiveOffensivePlayer.getCompleteName());
         System.out.println("Last Active Offensive Player Current Location: "
         + lastActiveOffensivePlayer.getCurrentZoneLocation());
         System.out.println("Active Defensive Player: " + activeDefensivePlayer.getCompleteName());
         System.out.println("Active Defensive Player Current Location: "
         + activeDefensivePlayer.getCurrentZoneLocation());
         System.out.println("Last Active Defensive Player: " + lastActiveDefensivePlayer.getCompleteName());
         System.out.println("Last Active Defensive Player Current Location: "
         + lastActiveDefensivePlayer.getCurrentZoneLocation());

         System.out.println(currentPlay.toString());*/

        try {
            this.plays.add(0, currentPlay);

            /**
             * If it was a scoring play, add it to the scoring log tableview,
             * then reset the playscore to zero
             */
            if (this.playScore > 0) {
                this.scoringPlays.add(0, currentPlay);
            }

            this.playScore = 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.exit(0);
        }

        System.out.println("Leaving create play"); // delete       
    }

    /**
     * Returns game narration entry, based on the play type
     *
     * @param playType Play type description
     * @return
     */
    private GameNarration getGameNarration(String playType) {
        return this.narration.get(playType);
    }

    /**
     * Returns the player in the given team, in the given position
     *
     * @param team Team number
     * @param rosterPosition Player roster position
     * @return
     */
    private InGamePlayer selectPlayerByRosterPosition(int team, int rosterPosition) {
        InGamePlayer selectedPlayer = teams.get(team).getPlayers().get(rosterPosition - 1);
        return selectedPlayer;
    }

    /**
     * Keeps the action going by elapsing the time, moving the players and
     * updating stats
     *
     * @param elapsedTime Time to elapse
     */
    private void doPlay(int elapsedTime) {

        /**
         * If the game is not in fast mode, causes the thread to sleep
         */
        if (!this.fastMode) {
            try {
                Thread.sleep(elapsedTime * 1000);
                System.out.println("Elapsing time: " + elapsedTime); // delete          

            } catch (InterruptedException ex) {
                Logger.getLogger(PlayGame.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("Time left: " + this.timeLeft);
        System.out.println("ShotClock: " + this.shotClock);

        /**
         * If the elapsed time for the play is greater than the time left,
         * correct it
         */
        if (elapsedTime >= timeLeft) {
            elapsedTime = timeLeft;
        }

        /**
         * Updating player's time stats, updating stamina and moving players
         */
        updatePlayersTimeStats(elapsedTime);
        movePlayers();
        updatePlayersCurrentStaminaLevel();

        /**
         * Updating scores and gap between the teams
         */
        this.awayScore = this.teams.get(1).getScore();
        this.homeScore = this.teams.get(2).getScore();
        this.gap = Math.abs(this.homeScore - this.awayScore);

        /**
         * Updating the leading team
         */
        if (this.awayScore == this.homeScore) {
            this.leadingTeam = 0;
        } else if (this.awayScore > this.homeScore) {
            this.leadingTeam = 1;
        } else {
            this.leadingTeam = 2;
        }

        /**
         * Updating time left in the quarter, shotclock and players time stats
         */
        this.timeLeft -= elapsedTime;
        this.shotClock -= elapsedTime;

        /**
         * If the timeleft reaches 0, the period is over
         */
        if (this.timeLeft <= 0) {
            this.timeLeft = 0;
            return;
        }

        /**
         * If the shotclock reaches 0, a shotclock violation is called
         */
        if (this.shotClock <= 0) {
            this.shotClock = 0;
            this.lastEvent = this.currentEvent;
            this.currentEvent = "shotclock violation";            
        }
    }

    /**
     * Parses a game narration structure, replacing marks for player names,
     * court spots, shots and so on.
     *
     * @param narration Game narration structure to be parsed
     * @return
     */
    private String parseNarration(GameNarration narration) {
        String parsedNarration = narration.getNarration();

        /**
         * Parsing active players
         */
        parsedNarration = parsedNarration.replace("[aop]",
                activeOffensivePlayer.getCompleteName());
        parsedNarration = parsedNarration.replace("[adp]",
                activeDefensivePlayer.getCompleteName());
        parsedNarration = parsedNarration.replace("[laop]",
                lastActiveOffensivePlayer.getCompleteName());
        parsedNarration = parsedNarration.replace("[ladp]",
                lastActiveDefensivePlayer.getCompleteName());
        parsedNarration = parsedNarration.replace("[spot]",
                courtSpots.get(ballCurrentLocation).getDescription());
        parsedNarration = parsedNarration.replace("[in]",
                PlayerUtils.getPlayerCompleteName(playerInId, connection));
        parsedNarration = parsedNarration.replace("[out]",
                PlayerUtils.getPlayerCompleteName(playerOutId, connection));
        parsedNarration = parsedNarration.replace("[at]",
                this.teams.get(ballPossession).getCompleteName());

        return parsedNarration;
    }

    /**
     * Update time stats for players in the bench and on the court
     */
    private void updatePlayersTimeStats(int elapsedTime) {
        InGamePlayer currentPlayer;

        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < teams.get(i).getPlayers().size(); j++) {

                currentPlayer = teams.get(i).getPlayers().get(j);

                if (currentPlayer.isOnCourt()) {
                    updatePlayerSecondsOnTheCourt(currentPlayer, elapsedTime);
                    updatePlayerPlayingTime(currentPlayer, elapsedTime);
                } else {
                    updatePlayerSecondsInTheBench(currentPlayer, elapsedTime);
                }
            }
        }
    }

    /**
     * Update player's stamina levels
     */
    private void updatePlayersCurrentStaminaLevel() {
        InGamePlayer currentPlayer;
        int staminaAdjust;
        int newStaminaLevel;

        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < teams.get(i).getPlayers().size(); j++) {

                currentPlayer = teams.get(i).getPlayers().get(j);

                /**
                 * If the player's on the court, decrease his stamina level,
                 * otherwise, increase it. The current stamina level can't be
                 * greater than player's stamina level at the start of the game
                 */
                if (currentPlayer.isOnCourt()) {
                    staminaAdjust = (currentPlayer.getSecondsOnCourt() / 60)
                            * (3 + currentPlayer.getBaseAttributes().getTirednessRate());
                    newStaminaLevel = currentPlayer.getStaminaLevel() - staminaAdjust;
                    currentPlayer.setCurrentStaminaLevel(newStaminaLevel);
                } else {
                    staminaAdjust = (currentPlayer.getSecondsInBench() / 60)
                            * (8 - currentPlayer.getBaseAttributes().getTirednessRate());
                    newStaminaLevel = currentPlayer.getCurrentStaminaLevel() + staminaAdjust;

                    /**
                     * If the player is on the bench for more than 60 seconds,
                     * update stamina level and reset the seconds on the bench
                     */
                    if (currentPlayer.getSecondsInBench() > 60) {
                        currentPlayer.setSecondsInBench((short) 0);
                        currentPlayer.setCurrentStaminaLevel(Math.min(newStaminaLevel,
                                currentPlayer.getStaminaLevel()));
                    }
                }
            }
        }
    }

    /**
     * Rest all players
     *
     * @param minutes Minutes to rest
     */
    private void restPlayers(int minutes) {
        InGamePlayer currentPlayer;
        int newStaminaLevel;
        int staminaAdjust;

        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < teams.get(i).getPlayers().size(); j++) {

                currentPlayer = teams.get(i).getPlayers().get(j);
                staminaAdjust = minutes
                        * (8 - currentPlayer.getBaseAttributes().getTirednessRate());
                newStaminaLevel = currentPlayer.getCurrentStaminaLevel() + staminaAdjust;
                currentPlayer.setCurrentStaminaLevel(Math.min(newStaminaLevel,
                        currentPlayer.getStaminaLevel()));
            }
        }
    }

    /**
     * Zeroes the offensive and defensive momenta for players on both teams
     */
    private void zeroMomenta() {
        InGamePlayer currentPlayer;

        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < teams.get(i).getPlayers().size(); j++) {

                currentPlayer = teams.get(i).getPlayers().get(j);
                currentPlayer.setOffensiveMomentum((short) 0);
                currentPlayer.setDefensiveMomentum((short) 0);
            }
        }
    }

    /**
     * Resets the substitution time for all players to 0
     */
    private void resetPlayersSubstitutionTime(int substitutionTime) {

        InGamePlayer currentPlayer;


        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < teams.get(i).getPlayers().size(); j++) {
                currentPlayer = teams.get(i).getPlayers().get(j);
                currentPlayer.setSubstitutionTime(substitutionTime);
            }
        }
    }

    /**
     * Moves all the players on the court, except the active offensive player
     * and the active defensive player
     */
    private void movePlayers() {
        InGamePlayer currentPlayer;

        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < teams.get(i).getPlayers().size(); j++) {

                currentPlayer = teams.get(i).getPlayers().get(j);

                if (currentPlayer.isOnCourt()
                        && !currentPlayer.equals(this.activeOffensivePlayer)
                        && !currentPlayer.equals(this.activeDefensivePlayer)) {
                    currentPlayer.move();
                }
            }
        }
    }

    /**
     * Returns a random player on the court, trying to select the player closest
     * to the ball
     *
     * @param excludedRosterPosition Roster position to be ignored during the
     * search
     * @return
     */
    public InGamePlayer getRandomPlayer(int excludedRosterPosition, int team) {
        InGamePlayer randomPlayer;
        InGamePlayer currentPlayer;

        /**
         * Picking up a random player on the court;
         */
        while (true) {
            randomPlayer = teams.get(team).getPlayers().get(MathUtils.generateRandomInt(0, 14));
            if (randomPlayer.isOnCourt() && randomPlayer.getRosterPosition() != excludedRosterPosition) {
                break;
            }
        }

        for (int i = 0; i < 15; i++) {

            currentPlayer = teams.get(team).getPlayers().get(i);

            /**
             * Checking if the player is on the court and is in the same court
             * zone as the ball or in a adjacent one;
             */
            if (currentPlayer.getBaseAttributes().getPlayable()
                    && currentPlayer.isOnCourt()
                    && currentPlayer.getRosterPosition() != excludedRosterPosition
                    && CourtUtils.isAdjacentZone(courtSpots.get(ballCurrentLocation).getCourtZone(),
                    currentPlayer.getCurrentZoneLocation())) {
                randomPlayer = currentPlayer;
            }
        }

        return randomPlayer;
    }

    /**
     * Updates player's seconds on the court
     *
     * @param player InGamePlayer object
     * @param elapsedSeconds Seconds to add
     */
    private void updatePlayerSecondsOnTheCourt(InGamePlayer player, int elapsedSeconds) {
        int secondsOnTheCourt = player.getSecondsOnCourt();
        secondsOnTheCourt += elapsedSeconds;
        player.setSecondsOnCourt((short) secondsOnTheCourt);
    }

    /**
     * Updates player's playing time
     *
     * @param player InGamePlayer object
     * @param elapsedSeconds Seconds to add
     */
    private void updatePlayerPlayingTime(InGamePlayer player, int elapsedSeconds) {
        int playingTime = player.getPlayingTime();
        playingTime += elapsedSeconds;
        player.setPlayingTime(playingTime);
    }

    /**
     * Updates player's seconds in the bench
     *
     * @param player InGamePlayer object
     * @param elapsedSeconds Seconds to add
     */
    private void updatePlayerSecondsInTheBench(InGamePlayer player, int elapsedSeconds) {
        int secondsInTheBench = player.getSecondsInBench();
        secondsInTheBench += elapsedSeconds;
        player.setSecondsInBench((short) secondsInTheBench);
    }

    /**
     * Updates ball location
     *
     * @param oldLocation
     * @param newLocation
     */
    private void updateBallLocation(int oldLocation, int newLocation) {
        this.ballLastLocation = oldLocation;
        this.ballCurrentLocation = newLocation;
    }

    /**
     * Resets the shotclock
     */
    private void resetShotClock() {
        /**
         * If the time left is longer than the shotclock parameter, set the
         * shotclock to the parameter's value; otherwise, set the shotclock to
         * the time left
         */
        if (this.timeLeft > GameParameters.SHOTCLOCK.getParameterValue()) {
            this.shotClock = GameParameters.SHOTCLOCK.getParameterValue();
        } else {
            this.shotClock = this.timeLeft;
        }
    }

    /**
     * Updates the scoring string showed in the UI
     */
    private void updateScoringStrings() {
        /**
         * Updating scoring strings
         */
        lastScoringPlayScore = teams.get(1).getCompleteName() + " " + teams.get(1).getScore()
                + ", " + teams.get(2).getCompleteName() + " " + teams.get(2).getScore();
        lastScoringPlay = this.period + " | (" + TimeUtils.intToTime(this.timeLeft)
                + ") " + teams.get(ballPossession).getAbbreviature() + " - "
                + parseNarration(getGameNarration(this.currentEvent));
        lastScorerStats = activeOffensivePlayer.getStatsLine();
    }

    /**
     * Save game's basic data into database
     */
    public void save() {
        /**
         * Building sql statement and executing it
         */
        String sqlStatement = "UPDATE game SET "
                + " referee = " + this.refereeId + ", "
                + " awayScore = " + this.awayScore + ", "
                + " homeScore = " + this.homeScore + ", "
                + " played = 1, "
                + " periodsPlayed = " + this.period + ", "
                + " awayTeamCoach = " + this.teams.get(AWAY_TEAM).getCoachId() + ", "
                + " homeTeamCoach = " + this.teams.get(HOME_TEAM).getCoachId() + ", "
                + " awayTeamLargestLead = " + this.teams.get(AWAY_TEAM).getLargestLead() + ", "
                + " homeTeamLargestLead = " + this.teams.get(HOME_TEAM).getLargestLead() + ", "
                + " awayTeamBiggestRun = " + this.teams.get(AWAY_TEAM).getLongestRun() + ", "
                + " homeTeamBiggestRun = " + this.teams.get(HOME_TEAM).getLongestRun() + ", "
                + " attendance = " + this.attendance + ", "
                + " grossRevenue = " + (this.attendance
                * Integer.parseInt(SettingsUtils.getSetting("ticketPrice", "100"))) + ", "
                + " maintenanceCost = " + (this.attendance
                * ArenaUtils.getArenaSeatMaintenanceCost(arenaId, connection))
                + " WHERE id = " + this.gameId;

        this.connection.executeSQL(sqlStatement);
    }

    /**
     * Saves the logs for every play of the game
     */
    public void savePlayLogs() {
        String sqlStatement;

        System.out.println("Entering save play logs"); // delete
        /**
         * Deleting previous logs from this game
         */
        sqlStatement = "DELETE FROM play_log WHERE game = " + this.gameId;
        this.connection.executeSQL(sqlStatement);

        /**
         * Looping through the play log observable list to save each entry into
         * database
         */
        for (int i = 0; i < this.plays.size(); i++) {
            Play currentPlay = this.plays.get(i);

            sqlStatement = "INSERT INTO play_log (season, game, ballFrom, ballTo,"
                    + " activeOffensivePlayer, lastActiveOffensivePlayer,"
                    + " activeDefensivePlayer, lastActiveDefensivePlayer, playType,"
                    + " narration, date, period, time, shotClock, awayScore, homeScore,"
                    + "score, playerIn, playerOut) VALUES ("
                    + currentPlay.getSeason() + ", "
                    + this.gameId + ", "
                    + currentPlay.getBallFrom() + ", "
                    + currentPlay.getBallTo() + ", "
                    + currentPlay.getActiveOffensivePlayerId() + ", "
                    + currentPlay.getLastActiveOffensivePlayerId() + ", "
                    + currentPlay.getActiveDefensivePlayerId() + ", "
                    + currentPlay.getLastActiveDefensivePlayerId() + ", "
                    + "'" + currentPlay.getPlayType() + "', "
                    + currentPlay.getNarrationId() + ", "
                    + "'" + this.gameDate + "', "
                    + currentPlay.getPeriod() + ", "
                    + "'" + currentPlay.getTime() + "', "
                    + currentPlay.getShotClock() + ", "
                    + currentPlay.getAwayScore() + ", "
                    + currentPlay.getHomeScore() + ", "
                    + currentPlay.getPlayScore() + ", "
                    + currentPlay.getPlayerInId() + ", "
                    + currentPlay.getPlayerOutId() + ")";

            this.connection.executeSQL(sqlStatement);
        }
    }

    /**
     * Saves player logs and updates data
     */
    public void savePlayerData() {
        String sqlStatement;

        /**
         * Deleting previous logs from this game
         */
        sqlStatement = "DELETE FROM player_log WHERE game = " + this.gameId;
        this.connection.executeSQL(sqlStatement);

        /**
         * Looping through the play log observable list to save each entry into
         * database
         */
        for (int i = 1; i < 3; i++) {
            Team currentTeam = this.teams.get(i);
            int opponent = this.teams.get(3 - i).getId();
            String homeRoad = i == 1 ? "R" : "H";
            String result = this.teams.get(i).getScore()
                    > this.teams.get(3 - i).getScore() ? "W" : "L";

            for (int j = 0; j < currentTeam.getPlayers().size(); j++) {
                InGamePlayer currentPlayer = currentTeam.getPlayers().get(j);

                /**
                 * Saving player logs for this game
                 */
                sqlStatement = "INSERT INTO player_log (season, game, player, "
                        + "team, opponent, event, gameType, gameDate, homeRoad, result, "
                        + "notes, rosterPosition, playingTime, points, fieldGoalsAttempted, "
                        + "fieldGoalsMade, freeThrowsAttempted, freeThrowsMade, "
                        + "threePointersAttempted, threePointersMade, assists, offensiveRebounds, "
                        + "defensiveRebounds, blocks, blockedShots, steals, turnovers, "
                        + "personalFouls, technicalFouls) VALUES ("
                        + this.season + ", "
                        + this.gameId + ", "
                        + currentPlayer.getBaseAttributes().getId() + ", "
                        + currentTeam.getId() + ", "
                        + opponent + ", "
                        + "NULL, "
                        + "'" + this.gameType + "', "
                        + "'" + this.gameDate + "', "
                        + "'" + homeRoad + "', "
                        + "'" + result + "', "
                        + "NULL, "
                        + currentPlayer.getRosterPosition() + ", "
                        + currentPlayer.getPlayingTime() + ", "
                        + currentPlayer.getPoints() + ", "
                        + currentPlayer.getFieldGoalsAttempted() + ", "
                        + currentPlayer.getFieldGoalsMade() + ", "
                        + currentPlayer.getFreeThrowsAttempted() + ", "
                        + currentPlayer.getFreeThrowsMade() + ", "
                        + currentPlayer.getThreePointersAttempted() + ", "
                        + currentPlayer.getThreePointersMade() + ", "
                        + currentPlayer.getAssists() + ", "
                        + currentPlayer.getOffensiveRebounds() + ", "
                        + currentPlayer.getDefensiveRebounds() + ", "
                        + currentPlayer.getBlocks() + ", "
                        + currentPlayer.getBlockedShots() + ", "
                        + currentPlayer.getSteals() + ", "
                        + currentPlayer.getTurnovers() + ", "
                        + currentPlayer.getPersonalFouls() + ", "
                        + currentPlayer.getTechnicalFouls() + ")";

                this.connection.executeSQL(sqlStatement);

                /**
                 * Updating player data
                 */
                int happinessLevelAdjust = result.equalsIgnoreCase("W") ? 1 : -1;
                int regularSeasonExperienceAdjust = this.gameType.equalsIgnoreCase("R")
                        ? 1 : 0;
                int playoffsExperienceAdjust = this.gameType.equalsIgnoreCase("P")
                        ? 1 : 0;

                sqlStatement = "UPDATE player SET accumulatedFatigue = accumulatedFatigue + "
                        + Math.max(currentPlayer.getPlayingTime() / 600 
                        / currentPlayer.getBaseAttributes().getTirednessRate(), 1) + ", happinessLevel = "
                        + "happinessLevel + " + happinessLevelAdjust + ", "
                        + "regularSeasonExperience = regularSeasonExperience + "
                        + regularSeasonExperienceAdjust + ", playoffsExperience = "
                        + "playoffsExperience + " + playoffsExperienceAdjust + ", "
                        + "gamesWithTeam = gamesWithTeam + 1 WHERE id = "
                        + currentPlayer.getBaseAttributes().getId();

                this.connection.executeSQL(sqlStatement);
            }
        }
    }

    /**
     * Saves team logs and updates data
     */
    public void saveTeamData() {
        String sqlStatement;

        /**
         * Deleting previous logs from this game
         */
        sqlStatement = "DELETE FROM franchise_log WHERE game = " + this.gameId;
        this.connection.executeSQL(sqlStatement);

        /**
         * Looping through the play log observable list to save each entry into
         * database
         */
        for (int i = 1; i < 3; i++) {
            Team currentTeam = this.teams.get(i);
            int opponent = this.teams.get(3 - i).getId();
            String homeRoad = i == 1 ? "R" : "H";
            String result = this.teams.get(i).getScore()
                    > this.teams.get(3 - i).getScore() ? "W" : "L";

            /**
             * Saving team logs for this game
             */
            sqlStatement = "INSERT INTO franchise_log (season, game, team, "
                    + "opponent, gameType, gameDate, homeRoad, result, "
                    + "points, fieldGoalsAttempted, "
                    + "fieldGoalsMade, freeThrowsAttempted, freeThrowsMade, "
                    + "threePointersAttempted, threePointersMade, assists, offensiveRebounds, "
                    + "defensiveRebounds, blocks, blockedShots, steals, turnovers, "
                    + "personalFouls, technicalFouls) VALUES ("
                    + this.season + ", "
                    + this.gameId + ", "
                    + currentTeam.getId() + ", "
                    + opponent + ", "
                    + "'" + this.gameType + "', "
                    + "'" + this.gameDate + "', "
                    + "'" + homeRoad + "', "
                    + "'" + result + "', "
                    + currentTeam.getScore() + ", "
                    + currentTeam.getFieldGoalsAttempted() + ", "
                    + currentTeam.getFieldGoalsMade() + ", "
                    + currentTeam.getFreeThrowsAttempted() + ", "
                    + currentTeam.getFreeThrowsMade() + ", "
                    + currentTeam.getThreePointersAttempted() + ", "
                    + currentTeam.getThreePointersMade() + ", "
                    + currentTeam.getAssists() + ", "
                    + currentTeam.getOffensiveRebounds() + ", "
                    + currentTeam.getDefensiveRebounds() + ", "
                    + currentTeam.getBlocks() + ", "
                    + currentTeam.getBlockedShots() + ", "
                    + currentTeam.getSteals() + ", "
                    + currentTeam.getTurnovers() + ", "
                    + currentTeam.getTotalFouls() + ", "
                    + currentTeam.getTechnicalFouls() + ")";

            this.connection.executeSQL(sqlStatement);
        }
    }

    /* getters and setters */
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public short getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(short homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public short getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(short awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public short getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(short refereeId) {
        this.refereeId = refereeId;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public short getArenaId() {
        return arenaId;
    }

    public void setArenaId(short arenaId) {
        this.arenaId = arenaId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public short getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(short awayScore) {
        this.awayScore = awayScore;
    }

    public short getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(short homeScore) {
        this.homeScore = homeScore;
    }

    public short getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(short timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getShotClock() {
        return shotClock;
    }

    public void setShotClock(int shotClock) {
        this.shotClock = shotClock;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public short getPeriod() {
        return period;
    }

    public void setPeriod(short period) {
        this.period = period;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public ObservableList<Play> getPlays() {
        return plays;
    }

    public void setPlays(ObservableList<Play> plays) {
        this.plays = plays;
    }

    public ObservableList<Play> getScoringPlays() {
        return scoringPlays;
    }

    public void setScoringPlays(ObservableList<Play> scoringPlays) {
        this.scoringPlays = scoringPlays;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<CourtSpot> getCourtSpots() {
        return courtSpots;
    }

    public void setCourtSpots(ArrayList<CourtSpot> courtSpots) {
        this.courtSpots = courtSpots;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getBallLocation() {
        return ballLastLocation;
    }

    public void setBallLocation(int ballLocation) {
        this.ballLastLocation = ballLocation;
    }

    public int getBallNewLocation() {
        return ballCurrentLocation;
    }

    public void setBallNewLocation(int ballNewLocation) {
        this.ballCurrentLocation = ballNewLocation;
    }

    public InGamePlayer getActiveOffensivePlayer() {
        return activeOffensivePlayer;
    }

    public void setActiveOffensivePlayer(InGamePlayer activeOffensivePlayer) {
        this.activeOffensivePlayer = activeOffensivePlayer;
    }

    public InGamePlayer getActiveDefensivePlayer() {
        return activeDefensivePlayer;
    }

    public void setActiveDefensivePlayer(InGamePlayer activeDefensivePlayer) {
        this.activeDefensivePlayer = activeDefensivePlayer;
    }

    public InGamePlayer getLastActiveOffensivePlayer() {
        return lastActiveOffensivePlayer;
    }

    public void setLastActiveOffensivePlayer(InGamePlayer lastActiveOffensivePlayer) {
        this.lastActiveOffensivePlayer = lastActiveOffensivePlayer;
    }

    public InGamePlayer getLastActiveDefensivePlayer() {
        return lastActiveDefensivePlayer;
    }

    public void setLastActiveDefensivePlayer(InGamePlayer lastActiveDefensivePlayer) {
        this.lastActiveDefensivePlayer = lastActiveDefensivePlayer;
    }

    public Map<String, GameNarration> getNarration() {
        return narration;
    }

    public void setNarration(Map<String, GameNarration> narration) {
        this.narration = narration;
    }

    public String getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(String currentEvent) {
        this.currentEvent = currentEvent;
    }

    public String getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(String lastEvent) {
        this.lastEvent = lastEvent;
    }

    public int getBallLastLocation() {
        return ballLastLocation;
    }

    public void setBallLastLocation(int ballLastLocation) {
        this.ballLastLocation = ballLastLocation;
    }

    public int getBallCurrentLocation() {
        return ballCurrentLocation;
    }

    public void setBallCurrentLocation(int ballCurrentLocation) {
        this.ballCurrentLocation = ballCurrentLocation;
    }

    public int getTipOffWinner() {
        return tipOffWinner;
    }

    public void setTipOffWinner(int tipOffWinner) {
        this.tipOffWinner = tipOffWinner;
    }

    public int getBallPossession() {
        return ballPossession;
    }

    public void setBallPossession(int ballPossession) {
        this.ballPossession = ballPossession;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getPlayScore() {
        return playScore;
    }

    public void setPlayScore(int playScore) {
        this.playScore = playScore;
    }

    public boolean isFastMode() {
        return fastMode;
    }

    public void setFastMode(boolean fastMode) {
        this.fastMode = fastMode;
    }

    public double getOffensiveEffort() {
        return offensiveEffort;
    }

    public void setOffensiveEffort(double offensiveEffort) {
        this.offensiveEffort = offensiveEffort;
    }

    public double getDefensiveEffort() {
        return defensiveEffort;
    }

    public void setDefensiveEffort(double defensiveEffort) {
        this.defensiveEffort = defensiveEffort;
    }

    public int getLeadingTeam() {
        return leadingTeam;
    }

    public void setLeadingTeam(int leadingTeam) {
        this.leadingTeam = leadingTeam;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public Random getGenerator() {
        return generator;
    }

    public void setGenerator(Random generator) {
        this.generator = generator;
    }

    public double getJumpShotAverage() {
        return jumpShotAverage;
    }

    public void setJumpShotAverage(double jumpShotAverage) {
        this.jumpShotAverage = jumpShotAverage;
    }

    public double getThreePointersAverage() {
        return threePointersAverage;
    }

    public void setThreePointersAverage(double threePointersAverage) {
        this.threePointersAverage = threePointersAverage;
    }

    public double getCourtVisionAverage() {
        return courtVisionAverage;
    }

    public void setCourtVisionAverage(double courtVisionAverage) {
        this.courtVisionAverage = courtVisionAverage;
    }

    public double getLowPostGameAverage() {
        return lowPostAverage;
    }

    public void setLowPostGameAverage(double lowPostGameAverage) {
        this.lowPostAverage = lowPostGameAverage;
    }

    public double getInThePaintScoringAverage() {
        return inThePaintAverage;
    }

    public void setInThePaintScoringAverage(double inThePaintScoringAverage) {
        this.inThePaintAverage = inThePaintScoringAverage;
    }

    public double getAlleyOopPassingAverage() {
        return alleyOopPassAverage;
    }

    public void setAlleyOopPassingAverage(double alleyOopPassingAverage) {
        this.alleyOopPassAverage = alleyOopPassingAverage;
    }

    public double getDunkingAverage() {
        return dunkAverage;
    }

    public void setDunkingAverage(double dunkingAverage) {
        this.dunkAverage = dunkingAverage;
    }

    public double getNoLookPassingAverage() {
        return noLookPassAverage;
    }

    public void setNoLookPassingAverage(double noLookPassingAverage) {
        this.noLookPassAverage = noLookPassingAverage;
    }

    public double getBehindTheBackPassingAverage() {
        return behindTheBackPassAverage;
    }

    public void setBehindTheBackPassingAverage(double behindTheBackPassingAverage) {
        this.behindTheBackPassAverage = behindTheBackPassingAverage;
    }

    public double getAggressivenessAverage() {
        return aggressivenessAverage;
    }

    public void setAggressivenessAverage(double aggressivenessAverage) {
        this.aggressivenessAverage = aggressivenessAverage;
    }

    public double getFreeThrowShootingAverage() {
        return freeThrowsAverage;
    }

    public void setFreeThrowShootingAverage(double freeThrowShootingAverage) {
        this.freeThrowsAverage = freeThrowShootingAverage;
    }

    public double getDriveAverage() {
        return driveAverage;
    }

    public void setDriveAverage(double driveAverage) {
        this.driveAverage = driveAverage;
    }

    public double getLowPostAverage() {
        return lowPostAverage;
    }

    public void setLowPostAverage(double lowPostAverage) {
        this.lowPostAverage = lowPostAverage;
    }

    public double getInThePaintAverage() {
        return inThePaintAverage;
    }

    public void setInThePaintAverage(double inThePaintAverage) {
        this.inThePaintAverage = inThePaintAverage;
    }

    public double getPassAverage() {
        return passAverage;
    }

    public void setPassAverage(double passAverage) {
        this.passAverage = passAverage;
    }

    public double getAlleyOopPassAverage() {
        return alleyOopPassAverage;
    }

    public void setAlleyOopPassAverage(double alleyOopPassAverage) {
        this.alleyOopPassAverage = alleyOopPassAverage;
    }

    public double getDunkAverage() {
        return dunkAverage;
    }

    public void setDunkAverage(double dunkAverage) {
        this.dunkAverage = dunkAverage;
    }

    public double getNoLookPassAverage() {
        return noLookPassAverage;
    }

    public void setNoLookPassAverage(double noLookPassAverage) {
        this.noLookPassAverage = noLookPassAverage;
    }

    public double getBehindTheBackPassAverage() {
        return behindTheBackPassAverage;
    }

    public void setBehindTheBackPassAverage(double behindTheBackPassAverage) {
        this.behindTheBackPassAverage = behindTheBackPassAverage;
    }

    public double getFreeThrowsAverage() {
        return freeThrowsAverage;
    }

    public void setFreeThrowsAverage(double freeThrowsAverage) {
        this.freeThrowsAverage = freeThrowsAverage;
    }

    public double getCreativityAverage() {
        return creativityAverage;
    }

    public void setCreativityAverage(double creativityAverage) {
        this.creativityAverage = creativityAverage;
    }

    public ArrayList<Integer> getDunkingZone() {
        return dunkingZone;
    }

    public void setDunkingZone(ArrayList<Integer> dunkingZone) {
        this.dunkingZone = dunkingZone;
    }

    public ArrayList<Integer> getThreePointerZone() {
        return threePointerZone;
    }

    public void setThreePointerZone(ArrayList<Integer> threePointerZone) {
        this.threePointerZone = threePointerZone;
    }

    public ArrayList<Integer> getJumpShotZone() {
        return jumpShotZone;
    }

    public void setJumpShotZone(ArrayList<Integer> jumpShotZone) {
        this.jumpShotZone = jumpShotZone;
    }

    public ArrayList<Integer> getPaintZone() {
        return paintZone;
    }

    public void setPaintZone(ArrayList<Integer> paintZone) {
        this.paintZone = paintZone;
    }

    public ArrayList<Integer> getLowPostZone() {
        return lowPostZone;
    }

    public void setLowPostZone(ArrayList<Integer> lowPostZone) {
        this.lowPostZone = lowPostZone;
    }

    public double getQuickShotAverage() {
        return quickShotAverage;
    }

    public void setQuickShotAverage(double quickShotAverage) {
        this.quickShotAverage = quickShotAverage;
    }

    public String getShotDescription() {
        return shotDescription;
    }

    public void setShotDescription(String shotDescription) {
        this.shotDescription = shotDescription;
    }

    public DatabaseDirectConnection getConnection() {
        return connection;
    }

    public void setConnection(DatabaseDirectConnection connection) {
        this.connection = connection;
    }

    public ArrayList<Integer> getStartOffenseZone() {
        return startOffenseZone;
    }

    public void setStartOffenseZone(ArrayList<Integer> startOffenseZone) {
        this.startOffenseZone = startOffenseZone;
    }

    public boolean isOpenLook() {
        return openLook;
    }

    public void setOpenLook(boolean openLook) {
        this.openLook = openLook;
    }

    public int getFreeThrowsToShoot() {
        return freeThrowsToShoot;
    }

    public void setFreeThrowsToShoot(int freeThrowsToShoot) {
        this.freeThrowsToShoot = freeThrowsToShoot;
    }

    public String getLastScoringPlayScore() {
        return lastScoringPlayScore;
    }

    public void setLastScoringPlayScore(String lastScoringPlayScore) {
        this.lastScoringPlayScore = lastScoringPlayScore;
    }

    public String getLastScoringPlay() {
        return lastScoringPlay;
    }

    public void setLastScoringPlay(String lastScoringPlay) {
        this.lastScoringPlay = lastScoringPlay;
    }

    public String getLastScorerStats() {
        return lastScorerStats;
    }

    public void setLastScorerStats(String lastScorerStats) {
        this.lastScorerStats = lastScorerStats;
    }

    public int getInPlayerId() {
        return playerInId;
    }

    public void setInPlayerId(int inPlayerId) {
        this.playerInId = inPlayerId;
    }

    public int getOutPlayerId() {
        return playerOutId;
    }

    public void setOutPlayerId(int outPlayerId) {
        this.playerOutId = outPlayerId;
    }

    public boolean isOfficialTimeoutCalled() {
        return officialTimeoutCalled;
    }

    public void setOfficialTimeoutCalled(boolean officialTimeoutCalled) {
        this.officialTimeoutCalled = officialTimeoutCalled;
    }

    public int getPlayerInId() {
        return playerInId;
    }

    public void setPlayerInId(int playerInId) {
        this.playerInId = playerInId;
    }

    public int getPlayerOutId() {
        return playerOutId;
    }

    public void setPlayerOutId(int playerOutId) {
        this.playerOutId = playerOutId;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
} // end PlayGame
