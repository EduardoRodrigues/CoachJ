package coachj.builders;

import coachj.dao.DatabaseDirectConnection;
import coachj.models.Country;
import coachj.models.CourtZone;
import coachj.models.Player;
import coachj.utils.SettingsUtils;
import coachj.ingame.CourtZones;
import coachj.utils.CountingUtils;
import coachj.utils.PositionUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates new players by creating Player entity or MySQL statement
 *
 * @author Eduardo M. Rodrigues
 * @version 1.1
 * @date 30/07/2013
 */
public class PlayerBuilder {

    /* fields */
    private int id;
    private int countryId;
    private String firstName;
    private String lastName;
    private String position;
    private short seasons;
    private boolean hallOfFame;
    private short jersey;
    private short age;
    private short height;
    private short weight;
    private short injuryTendency;
    private boolean isPlayable;
    private short rosterPosition;
    private short remainingYears;
    private int salary;
    private int totalEarnings;
    private float bodyMassIndex;
    private short draftYear;
    private short marketValue;
    private short starPoints;
    private short technique;
    private short aggressiveness;
    private short speed;
    private short stamina;
    private short accumulatedFatigue;
    private short tirednessRate;
    private short peakAge;
    private short strenght;
    private short rookieWeakness;
    private short seasonMomentum;
    private boolean retired;
    private short happinessLevel;
    private short patience;
    private short discipline;
    private short workEthic;
    private short loyalty;
    private short greed;
    private char hand;
    private short lowPost;
    private short offenseDedication;
    private short defenseDedication;
    private short shootingRange;
    private short fieldGoals;
    private short threePointers;
    private short freeThrows;
    private short inThePaint;
    private short shotCorrection;
    private short pullUpJumper;
    private short runningJumper;
    private short floatingJumper;
    private short bankShot;
    private short scoopShot;
    private short fadeawayShot;
    private short turnaroundShot;
    private short hookShot;
    private short layupShot;
    private short reverseLayupShot;
    private short fingerRollShot;
    private short catchAndShootShot;
    private short stepBackShot;
    private short dunk;
    private short crunchTimeShooting;
    private short fakeShotFrequency;
    private short ballHandling;
    private short dribble;
    private short drive;
    private short courtVision;
    private short creativity;
    private short pass;
    private short behindTheBackPass;
    private short noLookPass;
    private short alleyOopPass;
    private short bouncePass;
    private short steal;
    private short block;
    private short contest;
    private short boxOut;
    private short oneOnOneDefense;
    private short helpDefense;
    private short defensiveRebound;
    private short offensiveRebound;
    private short jump;
    private short milestones;
    private short regularSeasonExperience;
    private short playoffsExperience;
    private short gamesWithTeam;
    private short gamesOut;
    private short titles;
    private String position2;
    private short favoriteCourtZone;

    /**
     * Constructor
     *
     * @param countryId Country's id
     * @param firstName Player's first name
     * @param lastName Player's last name
     * @param position Player's primary position
     */
    public PlayerBuilder(int countryId, String firstName, String lastName,
            String position) {
        this.countryId = countryId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
    }

    public PlayerBuilder() {
    }

    /**
     * Sets coach's attributes
     *
     * @param connection Database connection used to retrieve data
     */
    public void setAttributes(DatabaseDirectConnection connection) {

        /**
         * Variables that store database connection and the baseline values for
         * each position profile
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
        }

        short minimumHeight = 180;
        short maximumHeight = 190;
        short minimumFieldGoals = 60;
        short minimumThreePointers = 70;
        short minimumPass = 70;
        short minimumRebound = 70;
        short minimumBallHandling = 70;
        short minimumLowPost = 70;
        short minimumShootingRange = 70;

        /**
         * retrieving position's profile values
         */
        // // connection.open();

        ResultSet positionProfile = connection.getResultSet("SELECT * FROM position_profile"
                + " WHERE position ='" + this.position + "'");
        try {
            positionProfile.first();
            minimumHeight = positionProfile.getShort("minimumHeight");
            maximumHeight = positionProfile.getShort("maximumHeight");
            minimumFieldGoals = positionProfile.getShort("minimumFieldGoals");
            minimumThreePointers = positionProfile.getShort("minimumThreePointers");
            minimumPass = positionProfile.getShort("minimumPass");
            minimumRebound = positionProfile.getShort("minimumRebound");
            minimumBallHandling = positionProfile.getShort("minimumBallHandling");
            minimumLowPost = positionProfile.getShort("minimumLowPost");
            minimumShootingRange = positionProfile.getShort("minimumShootingRange");

        } catch (SQLException ex) {
            Logger.getLogger(PlayerBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        connection.close();

        /**
         * Generates random numbers
         */
        Random generator = new Random();

        /**
         * setting attributes based on primary position
         */
        this.height = (short) (generator.nextDouble() * (maximumHeight - minimumHeight)
                + minimumHeight);
        this.fieldGoals = (short) (generator.nextDouble() * 5 + minimumFieldGoals);
        this.threePointers = (short) (generator.nextDouble() * 5 + minimumThreePointers);
        this.pass = (short) (generator.nextDouble() * 5 + minimumPass);
        this.offensiveRebound = (short) (generator.nextDouble() * 5 + minimumRebound);
        this.defensiveRebound = (short) (generator.nextDouble() * 5 + minimumRebound);
        this.ballHandling = (short) (generator.nextDouble() * 5 + minimumBallHandling);
        this.lowPost = (short) (generator.nextDouble() * 5 + minimumLowPost);
        this.shootingRange = (short) (generator.nextDouble() * 5 + minimumShootingRange);

        /**
         * defining secondary position
         */
        this.position2 = PositionUtils.setSecondPosition(this.position, this.height);

        /**
         * Setting randomized attributes, not based on primary position
         */
        this.age = (short) (generator.nextDouble() * 4 + 20);
        this.bodyMassIndex = (float) (generator.nextDouble() * 12 + 20);
        this.weight = (short) (((double) this.height / 100) * ((double) this.height / 100)
                * this.bodyMassIndex);
        this.injuryTendency = (short) (generator.nextInt(6) + 1);
        this.technique = (short) (generator.nextDouble() * 30 + 50);
        this.aggressiveness = (short) (generator.nextDouble() * 30 + 50);
        this.tirednessRate = (short) (generator.nextInt(5) + 1);
        this.peakAge = (short) (generator.nextDouble() * 4 + 27);
        this.strenght = (short) (generator.nextDouble() * 30 + 50);
        this.rookieWeakness = (short) (generator.nextInt(5) + 1);
        this.patience = (short) (generator.nextDouble() * 30 + 50);
        this.discipline = (short) (generator.nextDouble() * 30 + 50);
        this.workEthic = (short) (generator.nextDouble() * 30 + 50);
        this.loyalty = (short) (generator.nextDouble() * 30 + 50);
        this.greed = (short) (generator.nextDouble() * 30 + 0);
        this.hand = PositionUtils.setRandomShootingHand();
        this.offenseDedication = (short) (generator.nextDouble() * 30 + 50);
        this.defenseDedication = (short) (generator.nextDouble() * 30 + 50);
        this.crunchTimeShooting = (short) generator.nextInt(3);
        this.creativity = (short) (generator.nextDouble() * 30 + 50);
        this.courtVision = (short) (generator.nextDouble() * 30 + 50);

        /**
         * Since taller and heavier players tend to be slower, the speed is
         * calculated based on the player's height and weight
         */
        this.speed = (short) (100 + generator.nextInt(6) - (((double) this.height / 5)
                + ((double) this.weight / 10)) / 2);

        /**
         * Since heavier players tend to get tired faster, the stamina is
         * calculated based on the player's weight
         */
        this.stamina = (short) (100 - (((double) this.height / 100)
                + ((double) this.weight / 100)) * 2);

        /**
         * Taller players theoretically tend to be more effective in the paint,
         * so the inThePaint field is calculated based on the player's height
         */
        this.inThePaint = (short) (generator.nextDouble() * (double) (this.height / 10)
                + this.lowPost);

        /**
         * Player's with better technique tend to have more skills to adjust
         * shots, so this field is based on that
         */
        this.shotCorrection = (short) (generator.nextDouble() * 30
                + this.technique * 0.65);

        /**
         * Attributes based on the player's field goal shooting
         */
        this.pullUpJumper = (short) (generator.nextDouble() * 30
                + this.fieldGoals * 0.65);
        this.runningJumper = (short) (generator.nextDouble() * 30
                + this.fieldGoals * 0.65);
        this.floatingJumper = (short) (generator.nextDouble() * 30
                + this.fieldGoals * 0.65);
        this.bankShot = (short) (generator.nextDouble() * 30
                + this.fieldGoals * 0.65);
        this.fadeawayShot = (short) (generator.nextDouble() * 30
                + this.fieldGoals * 0.65);
        this.turnaroundShot = (short) (generator.nextDouble() * 30
                + this.fieldGoals * 0.65);
        this.catchAndShootShot = (short) (generator.nextDouble() * 30
                + this.fieldGoals * 0.65);
        this.stepBackShot = (short) (generator.nextDouble() * 30
                + this.fieldGoals * 0.65);
        this.freeThrows = (short) (generator.nextDouble() * 30
                + this.fieldGoals * 0.65);
        /**
         * Attributes based on the player's ability to score in the paint
         */
        this.layupShot = (short) (generator.nextDouble() * 30
                + this.inThePaint * 0.85);
        this.scoopShot = (short) (generator.nextDouble() * 8
                + this.layupShot * 0.90);
        this.hookShot = (short) (generator.nextDouble() * 8
                + this.layupShot * 0.75);
        this.reverseLayupShot = (short) (generator.nextDouble() * 8
                + this.layupShot * 0.80);
        this.fingerRollShot = (short) (generator.nextDouble() * 8
                + this.layupShot * 0.85);
        this.dunk = (short) (short) (generator.nextDouble() * 8
                + this.layupShot * 0.95);

        /**
         * Attributes based on the player's creativity
         */
        this.fakeShotFrequency = (short) (generator.nextDouble() * 30
                + this.creativity * 0.65);

        /**
         * Attributes based on the player's ballhandling skills
         */
        this.dribble = (short) (generator.nextDouble() * 30
                + this.ballHandling * 0.90);
        this.drive = (short) (generator.nextDouble() * 30
                + this.ballHandling * 0.90);

        /**
         * Attributes based on the player's passing skills
         */
        this.behindTheBackPass = (short) (generator.nextDouble() * 30
                + this.pass * 0.65);
        this.noLookPass = (short) (generator.nextDouble() * 30
                + this.pass * 0.65);
        this.alleyOopPass = (short) (generator.nextDouble() * 30
                + this.pass * 0.65);
        this.bouncePass = (short) (generator.nextDouble() * 30
                + this.pass * 0.65);

        /**
         * Attributes based on the player's defensive skills
         */
        this.steal = (short) (generator.nextDouble() * 30
                + this.defenseDedication * 0.65);
        this.block = (short) (generator.nextDouble() * 30
                + this.defenseDedication * 0.65);
        this.contest = (short) (generator.nextDouble() * 30
                + this.defenseDedication * 0.65);
        this.boxOut = (short) (generator.nextDouble() * 30
                + this.defenseDedication * 0.65);
        this.oneOnOneDefense = (short) (generator.nextDouble() * 30
                + this.defenseDedication * 0.65);
        this.helpDefense = (short) (generator.nextDouble() * 30
                + this.defenseDedication * 0.65);

        /**
         * Stronger and lighter players tend to jump higher, so this field is
         * calculated based on both attributes
         */
        this.jump = (short) ((double) (this.height / 7) + (100 - this.weight * 0.85)
                + (generator.nextDouble() * 5));
        /**
         * Attribute based on player's shooting skills and position:
         */
        this.favoriteCourtZone = setFavoriteCourtZone();

        /**
         * Default values
         */
        this.seasons = 0;
        this.hallOfFame = false;
        this.jersey = 0;
        this.isPlayable = true;
        this.rosterPosition = 0;
        this.remainingYears = 0;
        this.totalEarnings = 0;
        this.draftYear = 0;
        this.starPoints = 0;
        this.accumulatedFatigue = 0;
        this.seasonMomentum = 0;
        this.retired = false;
        this.happinessLevel = 0;
        this.milestones = 0;
        this.regularSeasonExperience = 0;
        this.playoffsExperience = 0;
        this.gamesWithTeam = 0;
        this.gamesOut = 0;
        this.titles = 0;

        /**
         * calculating market value based on player's attributes
         */
        this.marketValue = (short) (technique
                + aggressiveness
                + speed
                + stamina
                - tirednessRate
                + peakAge
                + strenght
                - rookieWeakness
                + seasonMomentum
                + patience
                + discipline
                + workEthic
                + loyalty
                + greed
                + lowPost
                + offenseDedication
                + defenseDedication
                + shootingRange
                + fieldGoals
                + threePointers
                + freeThrows
                + inThePaint
                + shotCorrection
                + pullUpJumper
                + runningJumper
                + floatingJumper
                + bankShot
                + scoopShot
                + fadeawayShot
                + turnaroundShot
                + hookShot
                + layupShot
                + reverseLayupShot
                + fingerRollShot
                + catchAndShootShot
                + stepBackShot
                + dunk
                + crunchTimeShooting
                + fakeShotFrequency
                + ballHandling
                + dribble
                + drive
                + courtVision
                + creativity);

        /**
         * all players are created with the same salary, which is the league's
         * minimum
         */
        this.salary = Integer.parseInt(SettingsUtils.getSetting("minimumSalary", "1500000"));
    }

    /**
     * Creates and returns a Player entity.
     *
     * @return
     */
    public Player generatePlayerEntity() {

        /**
         * Creating objects that are required to complete the operation
         */
        Player player = new Player();
        Country country = new Country((short) this.countryId);
        CourtZone favoriteZone = new CourtZone((int) this.favoriteCourtZone);

        /**
         * Setting attributes
         */
        player.setId(this.id);
        player.setAccumulatedFatigue(accumulatedFatigue);
        player.setAge(age);
        player.setAggressiveness(aggressiveness);
        player.setAlleyOopPass(alleyOopPass);
        player.setBallHandling(ballHandling);
        player.setBankShot(bankShot);
        player.setBehindTheBackPass(behindTheBackPass);
        player.setBlock(block);
        player.setBodyMassIndex(bodyMassIndex);
        player.setBouncePass(bouncePass);
        player.setBoxOut(boxOut);
        player.setCatchAndShootShot(catchAndShootShot);
        player.setContest(contest);
        player.setCountry(country);
        player.setCourtVision(courtVision);
        player.setCreativity(creativity);
        player.setCrunchTimeShooting(crunchTimeShooting);
        player.setDefenseDedication(defenseDedication);
        player.setDefensiveRebound(defensiveRebound);
        player.setDiscipline(discipline);
        player.setDraftYear(draftYear);
        player.setDribble(dribble);
        player.setDrive(drive);
        player.setDunk(dunk);
        player.setFadeawayShot(fadeawayShot);
        player.setFavoriteCourtZone(favoriteZone);
        player.setFieldGoals(fieldGoals);
        player.setFingerRollShot(fingerRollShot);
        player.setFirstName(firstName);
        player.setFloatingJumper(floatingJumper);
        player.setFreeThrows(freeThrows);
        player.setGamesOut(gamesOut);
        player.setGamesWithTeam(gamesWithTeam);
        player.setGreed(greed);
        player.setHallOfFame(hallOfFame);
        player.setHand(hand);
        player.setHappinessLevel(happinessLevel);
        player.setHeight(height);
        player.setHelpDefense(helpDefense);
        player.setHookShot(hookShot);
        player.setInThePaint(inThePaint);
        player.setInjuryTendency(injuryTendency);
        player.setIsPlayable(isPlayable);
        player.setJersey(jersey);
        player.setJump(jump);
        player.setLastName(lastName);
        player.setLayupShot(layupShot);
        player.setLowPost(lowPost);
        player.setLoyalty(loyalty);
        player.setMarketValue(marketValue);
        player.setMilestones(milestones);
        player.setNoLookPass(noLookPass);
        player.setOffenseDedication(offenseDedication);
        player.setOffensiveRebound(offensiveRebound);
        player.setOneOnOneDefense(oneOnOneDefense);
        player.setPass(pass);
        player.setPatience(patience);
        player.setPeakAge(peakAge);
        player.setPlayoffsExperience(playoffsExperience);
        player.setPosition(position);
        player.setPosition2(position2);
        player.setPullUpJumper(pullUpJumper);
        player.setRegularSeasonExperience(regularSeasonExperience);
        player.setRemainingYears(remainingYears);
        player.setRetired(retired);
        player.setReverseLayupShot(reverseLayupShot);
        player.setRookieWeakness(rookieWeakness);
        player.setRosterPosition(rosterPosition);
        player.setRunningJumper(runningJumper);
        player.setSalary(salary);
        player.setScoopShot(scoopShot);
        player.setSeasonMomentum(seasonMomentum);
        player.setSeasons(seasons);
        player.setShootingRange(shootingRange);
        player.setShotCorrection(shotCorrection);
        player.setSpeed(speed);
        player.setStamina(stamina);
        player.setStarPoints(starPoints);
        player.setSteal(steal);
        player.setStepBackShot(stepBackShot);
        player.setStrenght(strenght);
        player.setTechnique(technique);
        player.setThreePointers(threePointers);
        player.setTirednessRate(tirednessRate);
        player.setTitles(titles);
        player.setTotalEarnings(totalEarnings);
        player.setTurnaroundShot(turnaroundShot);
        player.setWeight(weight);
        player.setWorkEthic(workEthic);
        return player;
    }

    /**
     * Returns player's favorite court zone to shoot, based on his attributes.
     * The court zones are: 0 - none<br />
     * 1 - right corner<br />
     * 2 - right wing<br />
     * 3 - right low post<br />
     * 4 - right side of the lane<br />
     * 5 - right elbow<br />
     * 6 - paint<br />
     * 7 - top of the key<br />
     * 8 - top of the arc<br />
     * 9 - left low post<br />
     * 10 - left side of the lane<br />
     * 11 - left elbow<br />
     * 12 - left corner<br />
     * 13 - left wing<br />
     * 14 - offensive halfcourt<br />
     * 15 - defensive halfcourt<br />
     *
     * @return The number of the court zone
     */
    public short setFavoriteCourtZone() {
        short favoriteZone = 0;
        Random generator = new Random();

        /**
         * For guards and small forwards, check what he's best at: shooting
         * three-pointers, midrange shots or scoring in the paint. If none, set
         * as a midrange shooter
         */
        if (this.position.equalsIgnoreCase("PG") || this.position.equalsIgnoreCase("SG")
                || this.position.equalsIgnoreCase("SF")) {
            if (this.threePointers > this.fieldGoals && this.threePointers > this.inThePaint) {
                favoriteZone = CourtZones.THREE_POINTERS_ZONES[generator
                        .nextInt(CourtZones.THREE_POINTERS_ZONES.length)];
            } else if (this.inThePaint > this.fieldGoals) {
                favoriteZone = CourtZones.PAINT_ZONES[generator
                        .nextInt(CourtZones.PAINT_ZONES.length)];
            } else {
                favoriteZone = CourtZones.MID_RANGE_ZONES[generator
                        .nextInt(CourtZones.MID_RANGE_ZONES.length)];
            }
        }

        /**
         * For centers and power forwards, check what he's best at: shooting
         * from the low post, scoring in the paint or midrange shots. If none,
         * set as an in the paint shooter
         */
        if (this.position.equalsIgnoreCase("PF") || this.position.equalsIgnoreCase("C")) {
            if (this.lowPost > this.inThePaint && this.lowPost > this.fieldGoals) {
                favoriteZone = CourtZones.LOW_POST_ZONES[generator
                        .nextInt(CourtZones.LOW_POST_ZONES.length)];
            } else if (this.inThePaint > this.fieldGoals) {
                favoriteZone = CourtZones.PAINT_ZONES[generator
                        .nextInt(CourtZones.PAINT_ZONES.length)];
            } else {
                favoriteZone = CourtZones.MID_RANGE_ZONES[generator
                        .nextInt(CourtZones.MID_RANGE_ZONES.length)];
            }
        }

        return favoriteZone;
    }

    /**
     * Creates and returns the MySQL statement that inserts the player into the
     * database
     *
     * @return
     */
    public String generateInsertSQL() {
        String sqlInsertStatement = "INSERT INTO player (country, "
                + "position, position2, favoriteCourtZone, firstName, lastName, "
                + "seasons, hallOfFame, jersey, age, height, weight, "
                + "injuryTendency, gamesOut, isPlayable, rosterPosition, "
                + "remainingYears, salary, totalEarnings, bodyMassIndex, draftYear, "
                + "marketValue, starPoints, technique, aggressiveness, speed, stamina, "
                + "accumulatedFatigue, tirednessRate, peakAge, strenght, rookieWeakness, "
                + "seasonMomentum, retired, happinessLevel, patience, discipline, workEthic, "
                + "loyalty, greed, hand, lowPost, offenseDedication, defenseDedication, "
                + "shootingRange, fieldGoals, threePointers, freeThrows, inThePaint, "
                + "shotCorrection, pullUpJumper, runningJumper, floatingJumper, bankShot, "
                + "scoopShot, fadeawayShot, turnaroundShot, hookShot, layupShot, reverseLayupShot, "
                + "fingerRollShot, catchAndShootShot, stepBackShot, dunk, crunchTimeShooting, "
                + "fakeShotFrequency, ballHandling, dribble, drive, courtVision, creativity, "
                + "pass, behindTheBackPass, noLookPass, alleyOopPass, bouncePass, steal, block, "
                + "contest, boxOut, oneOnOneDefense, helpDefense, defensiveRebound, offensiveRebound, "
                + "jump, milestones, regularSeasonExperience, playoffsExperience, gamesWithTeam, "
                + "titles) VALUES (" + this.countryId + ", '" + this.position + "', '"
                + this.position2 + "', " + this.favoriteCourtZone + ", '" + this.firstName + "', '"
                + this.lastName + "', " + this.seasons + ", " + this.hallOfFame + ", "
                + this.jersey + ", " + this.age + ", " + this.height + ", " + this.weight + ", "
                + this.injuryTendency + ", " + this.gamesOut + ", " + this.isPlayable + ", "
                + this.rosterPosition + ", " + this.remainingYears + ", " + this.salary + ", "
                + this.totalEarnings + ", " + this.bodyMassIndex + ", " + this.draftYear + ", "
                + this.marketValue + ", " + this.starPoints + ", " + this.technique + ", "
                + this.aggressiveness + ", " + this.speed + ", " + this.stamina + ", "
                + this.accumulatedFatigue + ", " + this.tirednessRate + ", " + this.peakAge + ", "
                + this.strenght + ", " + this.rookieWeakness + ", " + this.seasonMomentum + ", "
                + this.retired + ", " + this.happinessLevel + ", " + this.patience + ", "
                + this.discipline + ", " + this.workEthic + ", " + this.loyalty + ", "
                + this.greed + ", '" + this.hand + "', " + this.lowPost + ", "
                + this.offenseDedication + ", " + this.defenseDedication + ", "
                + this.shootingRange + ", " + this.fieldGoals + ", " + this.threePointers + ", "
                + this.freeThrows + ", " + this.inThePaint + ", " + this.shotCorrection + ", "
                + this.pullUpJumper + ", " + this.runningJumper + ", " + this.floatingJumper + ", "
                + this.bankShot + ", " + this.scoopShot + ", " + this.fadeawayShot + ", "
                + this.turnaroundShot + ", " + this.hookShot + ", " + this.layupShot + ", "
                + this.reverseLayupShot + ", " + this.fingerRollShot + ", " + this.catchAndShootShot + ", "
                + this.stepBackShot + ", " + this.dunk + ", " + this.crunchTimeShooting + ", "
                + this.fakeShotFrequency + ", " + this.ballHandling + ", " + this.dribble + ", "
                + this.drive + ", " + this.courtVision + ", " + this.creativity + ", "
                + this.pass + ", " + this.behindTheBackPass + ", " + this.noLookPass + ", "
                + this.alleyOopPass + ", " + this.bouncePass + ", " + this.steal + ", "
                + this.block + ", " + this.contest + ", " + this.boxOut + ", " + this.oneOnOneDefense + ", "
                + this.helpDefense + ", " + this.defensiveRebound + ", " + this.offensiveRebound + ", "
                + this.jump + ", " + this.milestones + ", " + this.regularSeasonExperience + ", "
                + this.playoffsExperience + ", " + this.gamesWithTeam + ", " + this.titles
                + ")";

        System.out.println(sqlInsertStatement); // delete
        return sqlInsertStatement;
    }

    /**
     * Fills the attributes from data retrieved from the database
     *
     * @param playerId Player's id
     * @param connection Database connection used to retrieve data
     */
    public void fillAttributesFromDatabase(short playerId,
            DatabaseDirectConnection connection) {
        /*
         * Variables that connect to the database, retrieve the resultset and store 
         * the sql statement
         */
        /**
         * Checking if there's an active database connection, otherwise, create
         * it
         */
        if (connection == null) {
            connection = new DatabaseDirectConnection();
            // // connection.open();
        }

        ResultSet resultSet;
        String sqlStatement = "SELECT * FROM player "
                + "WHERE id = " + playerId;

        try {
            /**
             * Executing query, retrieving result and setting attributes
             */
            resultSet = connection.getResultSet(sqlStatement);
            resultSet.first();

            this.id = playerId;
            this.accumulatedFatigue = resultSet.getShort("accumulatedFatigue");
            this.age = resultSet.getShort("age");
            this.aggressiveness = resultSet.getShort("aggressiveness");
            this.alleyOopPass = resultSet.getShort("alleyOopPass");
            this.ballHandling = resultSet.getShort("ballHandling");
            this.bankShot = resultSet.getShort("bankShot");
            this.behindTheBackPass = resultSet.getShort("behindTheBackPass");
            this.block = resultSet.getShort("block");
            this.bodyMassIndex = resultSet.getShort("bodyMassIndex");
            this.bouncePass = resultSet.getShort("bouncePass");
            this.boxOut = resultSet.getShort("boxOut");
            this.catchAndShootShot = resultSet.getShort("catchAndShootShot");
            this.contest = resultSet.getShort("contest");
            this.courtVision = resultSet.getShort("courtVision");
            this.creativity = resultSet.getShort("creativity");
            this.crunchTimeShooting = resultSet.getShort("crunchTimeShooting");
            this.defenseDedication = resultSet.getShort("defenseDedication");
            this.defensiveRebound = resultSet.getShort("defensiveRebound");
            this.discipline = resultSet.getShort("discipline");
            this.draftYear = resultSet.getShort("draftYear");
            this.dribble = resultSet.getShort("dribble");
            this.drive = resultSet.getShort("drive");
            this.dunk = resultSet.getShort("dunk");
            this.fadeawayShot = resultSet.getShort("fadeawayShot");
            this.fieldGoals = resultSet.getShort("fieldGoals");
            this.fingerRollShot = resultSet.getShort("fingerRollShot");
            this.firstName = resultSet.getString("firstName");
            this.floatingJumper = resultSet.getShort("floatingJumper");
            this.freeThrows = resultSet.getShort("freeThrows");
            this.gamesOut = resultSet.getShort("gamesOut");
            this.gamesWithTeam = resultSet.getShort("gamesWithTeam");
            this.greed = resultSet.getShort("greed");
            this.hallOfFame = resultSet.getBoolean("hallOfFame");
            this.hand = resultSet.getString("hand").charAt(0);
            this.happinessLevel = resultSet.getShort("happinessLevel");
            this.height = resultSet.getShort("height");
            this.helpDefense = resultSet.getShort("helpDefense");
            this.hookShot = resultSet.getShort("hookShot");
            this.inThePaint = resultSet.getShort("inThePaint");
            this.injuryTendency = resultSet.getShort("injuryTendency");
            this.isPlayable = resultSet.getBoolean("isPlayable");
            this.jersey = resultSet.getShort("jersey");
            this.jump = resultSet.getShort("jump");
            this.lastName = resultSet.getString("lastName");
            this.layupShot = resultSet.getShort("layupShot");
            this.lowPost = resultSet.getShort("lowPost");
            this.loyalty = resultSet.getShort("loyalty");
            this.marketValue = resultSet.getShort("marketValue");
            this.milestones = resultSet.getShort("milestones");
            this.noLookPass = resultSet.getShort("noLookPass");
            this.offenseDedication = resultSet.getShort("offenseDedication");
            this.offensiveRebound = resultSet.getShort("offensiveRebound");
            this.oneOnOneDefense = resultSet.getShort("oneOnOneDefense");
            this.pass = resultSet.getShort("pass");
            this.patience = resultSet.getShort("patience");
            this.peakAge = resultSet.getShort("peakAge");
            this.playoffsExperience = resultSet.getShort("playoffsExperience");
            this.position = resultSet.getString("position");
            this.position2 = resultSet.getString("position2");
            this.pullUpJumper = resultSet.getShort("pullUpJumper");
            this.regularSeasonExperience = resultSet.getShort("regularSeasonExperience");
            this.remainingYears = resultSet.getShort("remainingYears");
            this.retired = resultSet.getBoolean("retired");
            this.reverseLayupShot = resultSet.getShort("reverseLayupShot");
            this.rookieWeakness = resultSet.getShort("rookieWeakness");
            this.rosterPosition = resultSet.getShort("rosterPosition");
            this.runningJumper = resultSet.getShort("runningJumper");
            this.salary = resultSet.getInt("salary");
            this.scoopShot = resultSet.getShort("scoopShot");
            this.seasonMomentum = resultSet.getShort("seasonMomentum");
            this.seasons = resultSet.getShort("seasons");
            this.shootingRange = resultSet.getShort("shootingRange");
            this.shotCorrection = resultSet.getShort("shotCorrection");
            this.speed = resultSet.getShort("speed");
            this.stamina = resultSet.getShort("stamina");
            this.starPoints = resultSet.getShort("starPoints");
            this.steal = resultSet.getShort("steal");
            this.stepBackShot = resultSet.getShort("stepBackShot");
            this.strenght = resultSet.getShort("strenght");
            this.technique = resultSet.getShort("technique");
            this.threePointers = resultSet.getShort("threePointers");
            this.tirednessRate = resultSet.getShort("tirednessRate");
            this.titles = resultSet.getShort("titles");
            this.totalEarnings = resultSet.getInt("totalEarnings");
            this.turnaroundShot = resultSet.getShort("turnaroundShot");
            this.weight = resultSet.getShort("weight");
            this.workEthic = resultSet.getShort("workEthic");

        } catch (SQLException ex) {
            Logger.getLogger(CountingUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
    }
} // end class PlayerBuilder
