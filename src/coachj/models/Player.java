/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduardo
 */
@Entity
@Table(name = "player")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findById", query = "SELECT p FROM Player p WHERE p.id = :id"),
    @NamedQuery(name = "Player.findByPosition", query = "SELECT p FROM Player p WHERE p.position = :position"),
    @NamedQuery(name = "Player.findByPosition2", query = "SELECT p FROM Player p WHERE p.position2 = :position2"),
    @NamedQuery(name = "Player.findByFirstName", query = "SELECT p FROM Player p WHERE p.firstName = :firstName"),
    @NamedQuery(name = "Player.findByLastName", query = "SELECT p FROM Player p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "Player.findBySeasons", query = "SELECT p FROM Player p WHERE p.seasons = :seasons"),
    @NamedQuery(name = "Player.findByHallOfFame", query = "SELECT p FROM Player p WHERE p.hallOfFame = :hallOfFame"),
    @NamedQuery(name = "Player.findByJersey", query = "SELECT p FROM Player p WHERE p.jersey = :jersey"),
    @NamedQuery(name = "Player.findByAge", query = "SELECT p FROM Player p WHERE p.age = :age"),
    @NamedQuery(name = "Player.findByHeight", query = "SELECT p FROM Player p WHERE p.height = :height"),
    @NamedQuery(name = "Player.findByWeight", query = "SELECT p FROM Player p WHERE p.weight = :weight"),
    @NamedQuery(name = "Player.findByInjuryImpact", query = "SELECT p FROM Player p WHERE p.injuryImpact = :injuryImpact"),
    @NamedQuery(name = "Player.findByRecoveryDate", query = "SELECT p FROM Player p WHERE p.recoveryDate = :recoveryDate"),
    @NamedQuery(name = "Player.findByInjuryTendency", query = "SELECT p FROM Player p WHERE p.injuryTendency = :injuryTendency"),
    @NamedQuery(name = "Player.findByGamesOut", query = "SELECT p FROM Player p WHERE p.gamesOut = :gamesOut"),
    @NamedQuery(name = "Player.findByActive", query = "SELECT p FROM Player p WHERE p.active = :active"),
    @NamedQuery(name = "Player.findByPlayable", query = "SELECT p FROM Player p WHERE p.playable = :playable"),
    @NamedQuery(name = "Player.findByRosterPosition", query = "SELECT p FROM Player p WHERE p.rosterPosition = :rosterPosition"),
    @NamedQuery(name = "Player.findByRemainingYears", query = "SELECT p FROM Player p WHERE p.remainingYears = :remainingYears"),
    @NamedQuery(name = "Player.findByFailedContractAttempts", query = "SELECT p FROM Player p WHERE p.failedContractAttempts = :failedContractAttempts"),
    @NamedQuery(name = "Player.findBySalary", query = "SELECT p FROM Player p WHERE p.salary = :salary"),
    @NamedQuery(name = "Player.findByTotalEarnings", query = "SELECT p FROM Player p WHERE p.totalEarnings = :totalEarnings"),
    @NamedQuery(name = "Player.findByBodyMassIndex", query = "SELECT p FROM Player p WHERE p.bodyMassIndex = :bodyMassIndex"),
    @NamedQuery(name = "Player.findByDraftYear", query = "SELECT p FROM Player p WHERE p.draftYear = :draftYear"),
    @NamedQuery(name = "Player.findByMarketValue", query = "SELECT p FROM Player p WHERE p.marketValue = :marketValue"),
    @NamedQuery(name = "Player.findByStarPoints", query = "SELECT p FROM Player p WHERE p.starPoints = :starPoints"),
    @NamedQuery(name = "Player.findByTechnique", query = "SELECT p FROM Player p WHERE p.technique = :technique"),
    @NamedQuery(name = "Player.findByAggressiveness", query = "SELECT p FROM Player p WHERE p.aggressiveness = :aggressiveness"),
    @NamedQuery(name = "Player.findBySpeed", query = "SELECT p FROM Player p WHERE p.speed = :speed"),
    @NamedQuery(name = "Player.findByStamina", query = "SELECT p FROM Player p WHERE p.stamina = :stamina"),
    @NamedQuery(name = "Player.findByAccumulatedFatigue", query = "SELECT p FROM Player p WHERE p.accumulatedFatigue = :accumulatedFatigue"),
    @NamedQuery(name = "Player.findByTirednessRate", query = "SELECT p FROM Player p WHERE p.tirednessRate = :tirednessRate"),
    @NamedQuery(name = "Player.findByPeakAge", query = "SELECT p FROM Player p WHERE p.peakAge = :peakAge"),
    @NamedQuery(name = "Player.findByStrenght", query = "SELECT p FROM Player p WHERE p.strenght = :strenght"),
    @NamedQuery(name = "Player.findByRookieWeakness", query = "SELECT p FROM Player p WHERE p.rookieWeakness = :rookieWeakness"),
    @NamedQuery(name = "Player.findBySeasonMomentum", query = "SELECT p FROM Player p WHERE p.seasonMomentum = :seasonMomentum"),
    @NamedQuery(name = "Player.findByRetired", query = "SELECT p FROM Player p WHERE p.retired = :retired"),
    @NamedQuery(name = "Player.findByHappinessLevel", query = "SELECT p FROM Player p WHERE p.happinessLevel = :happinessLevel"),
    @NamedQuery(name = "Player.findByPatience", query = "SELECT p FROM Player p WHERE p.patience = :patience"),
    @NamedQuery(name = "Player.findByDiscipline", query = "SELECT p FROM Player p WHERE p.discipline = :discipline"),
    @NamedQuery(name = "Player.findByWorkEthic", query = "SELECT p FROM Player p WHERE p.workEthic = :workEthic"),
    @NamedQuery(name = "Player.findByLoyalty", query = "SELECT p FROM Player p WHERE p.loyalty = :loyalty"),
    @NamedQuery(name = "Player.findByGreed", query = "SELECT p FROM Player p WHERE p.greed = :greed"),
    @NamedQuery(name = "Player.findByHand", query = "SELECT p FROM Player p WHERE p.hand = :hand"),
    @NamedQuery(name = "Player.findByLowPost", query = "SELECT p FROM Player p WHERE p.lowPost = :lowPost"),
    @NamedQuery(name = "Player.findByOffenseDedication", query = "SELECT p FROM Player p WHERE p.offenseDedication = :offenseDedication"),
    @NamedQuery(name = "Player.findByDefenseDedication", query = "SELECT p FROM Player p WHERE p.defenseDedication = :defenseDedication"),
    @NamedQuery(name = "Player.findByShootingRange", query = "SELECT p FROM Player p WHERE p.shootingRange = :shootingRange"),
    @NamedQuery(name = "Player.findByFieldGoals", query = "SELECT p FROM Player p WHERE p.fieldGoals = :fieldGoals"),
    @NamedQuery(name = "Player.findByThreePointers", query = "SELECT p FROM Player p WHERE p.threePointers = :threePointers"),
    @NamedQuery(name = "Player.findByFreeThrows", query = "SELECT p FROM Player p WHERE p.freeThrows = :freeThrows"),
    @NamedQuery(name = "Player.findByInThePaint", query = "SELECT p FROM Player p WHERE p.inThePaint = :inThePaint"),
    @NamedQuery(name = "Player.findByShotCorrection", query = "SELECT p FROM Player p WHERE p.shotCorrection = :shotCorrection"),
    @NamedQuery(name = "Player.findByPullUpJumper", query = "SELECT p FROM Player p WHERE p.pullUpJumper = :pullUpJumper"),
    @NamedQuery(name = "Player.findByRunningJumper", query = "SELECT p FROM Player p WHERE p.runningJumper = :runningJumper"),
    @NamedQuery(name = "Player.findByFloatingJumper", query = "SELECT p FROM Player p WHERE p.floatingJumper = :floatingJumper"),
    @NamedQuery(name = "Player.findByBankShot", query = "SELECT p FROM Player p WHERE p.bankShot = :bankShot"),
    @NamedQuery(name = "Player.findByScoopShot", query = "SELECT p FROM Player p WHERE p.scoopShot = :scoopShot"),
    @NamedQuery(name = "Player.findByFadeawayShot", query = "SELECT p FROM Player p WHERE p.fadeawayShot = :fadeawayShot"),
    @NamedQuery(name = "Player.findByTurnaroundShot", query = "SELECT p FROM Player p WHERE p.turnaroundShot = :turnaroundShot"),
    @NamedQuery(name = "Player.findByHookShot", query = "SELECT p FROM Player p WHERE p.hookShot = :hookShot"),
    @NamedQuery(name = "Player.findByLayupShot", query = "SELECT p FROM Player p WHERE p.layupShot = :layupShot"),
    @NamedQuery(name = "Player.findByReverseLayupShot", query = "SELECT p FROM Player p WHERE p.reverseLayupShot = :reverseLayupShot"),
    @NamedQuery(name = "Player.findByFingerRollShot", query = "SELECT p FROM Player p WHERE p.fingerRollShot = :fingerRollShot"),
    @NamedQuery(name = "Player.findByCatchAndShootShot", query = "SELECT p FROM Player p WHERE p.catchAndShootShot = :catchAndShootShot"),
    @NamedQuery(name = "Player.findByStepBackShot", query = "SELECT p FROM Player p WHERE p.stepBackShot = :stepBackShot"),
    @NamedQuery(name = "Player.findByDunk", query = "SELECT p FROM Player p WHERE p.dunk = :dunk"),
    @NamedQuery(name = "Player.findByCrunchTimeShooting", query = "SELECT p FROM Player p WHERE p.crunchTimeShooting = :crunchTimeShooting"),
    @NamedQuery(name = "Player.findByFakeShotFrequency", query = "SELECT p FROM Player p WHERE p.fakeShotFrequency = :fakeShotFrequency"),
    @NamedQuery(name = "Player.findByBallHandling", query = "SELECT p FROM Player p WHERE p.ballHandling = :ballHandling"),
    @NamedQuery(name = "Player.findByDribble", query = "SELECT p FROM Player p WHERE p.dribble = :dribble"),
    @NamedQuery(name = "Player.findByDrive", query = "SELECT p FROM Player p WHERE p.drive = :drive"),
    @NamedQuery(name = "Player.findByCourtVision", query = "SELECT p FROM Player p WHERE p.courtVision = :courtVision"),
    @NamedQuery(name = "Player.findByCreativity", query = "SELECT p FROM Player p WHERE p.creativity = :creativity"),
    @NamedQuery(name = "Player.findByPass", query = "SELECT p FROM Player p WHERE p.pass = :pass"),
    @NamedQuery(name = "Player.findByBehindTheBackPass", query = "SELECT p FROM Player p WHERE p.behindTheBackPass = :behindTheBackPass"),
    @NamedQuery(name = "Player.findByNoLookPass", query = "SELECT p FROM Player p WHERE p.noLookPass = :noLookPass"),
    @NamedQuery(name = "Player.findByAlleyOopPass", query = "SELECT p FROM Player p WHERE p.alleyOopPass = :alleyOopPass"),
    @NamedQuery(name = "Player.findByBouncePass", query = "SELECT p FROM Player p WHERE p.bouncePass = :bouncePass"),
    @NamedQuery(name = "Player.findBySteal", query = "SELECT p FROM Player p WHERE p.steal = :steal"),
    @NamedQuery(name = "Player.findByBlock", query = "SELECT p FROM Player p WHERE p.block = :block"),
    @NamedQuery(name = "Player.findByContest", query = "SELECT p FROM Player p WHERE p.contest = :contest"),
    @NamedQuery(name = "Player.findByBoxOut", query = "SELECT p FROM Player p WHERE p.boxOut = :boxOut"),
    @NamedQuery(name = "Player.findByOneOnOneDefense", query = "SELECT p FROM Player p WHERE p.oneOnOneDefense = :oneOnOneDefense"),
    @NamedQuery(name = "Player.findByHelpDefense", query = "SELECT p FROM Player p WHERE p.helpDefense = :helpDefense"),
    @NamedQuery(name = "Player.findByDefensiveRebound", query = "SELECT p FROM Player p WHERE p.defensiveRebound = :defensiveRebound"),
    @NamedQuery(name = "Player.findByOffensiveRebound", query = "SELECT p FROM Player p WHERE p.offensiveRebound = :offensiveRebound"),
    @NamedQuery(name = "Player.findByJump", query = "SELECT p FROM Player p WHERE p.jump = :jump"),
    @NamedQuery(name = "Player.findByMilestones", query = "SELECT p FROM Player p WHERE p.milestones = :milestones"),
    @NamedQuery(name = "Player.findByRegularSeasonExperience", query = "SELECT p FROM Player p WHERE p.regularSeasonExperience = :regularSeasonExperience"),
    @NamedQuery(name = "Player.findByPlayoffsExperience", query = "SELECT p FROM Player p WHERE p.playoffsExperience = :playoffsExperience"),
    @NamedQuery(name = "Player.findByGamesWithTeam", query = "SELECT p FROM Player p WHERE p.gamesWithTeam = :gamesWithTeam"),
    @NamedQuery(name = "Player.findByTitles", query = "SELECT p FROM Player p WHERE p.titles = :titles")})
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "position")
    private String position;
    @Basic(optional = false)
    @Column(name = "position2")
    private String position2;
    @Basic(optional = false)
    @Column(name = "firstName")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "lastName")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "seasons")
    private short seasons;
    @Basic(optional = false)
    @Column(name = "hallOfFame")
    private boolean hallOfFame;
    @Basic(optional = false)
    @Column(name = "jersey")
    private short jersey;
    @Basic(optional = false)
    @Column(name = "age")
    private short age;
    @Basic(optional = false)
    @Column(name = "height")
    private short height;
    @Basic(optional = false)
    @Column(name = "weight")
    private short weight;
    @Basic(optional = false)
    @Column(name = "injuryImpact")
    private short injuryImpact;
    @Column(name = "recoveryDate")
    @Temporal(TemporalType.DATE)
    private Date recoveryDate;
    @Basic(optional = false)
    @Column(name = "injuryTendency")
    private short injuryTendency;
    @Column(name = "gamesOut")
    private Short gamesOut;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @Column(name = "playable")
    private boolean playable;
    @Basic(optional = false)
    @Column(name = "rosterPosition")
    private short rosterPosition;
    @Basic(optional = false)
    @Column(name = "remainingYears")
    private short remainingYears;
    @Basic(optional = false)
    @Column(name = "failedContractAttempts")
    private short failedContractAttempts;
    @Basic(optional = false)
    @Column(name = "salary")
    private int salary;
    @Basic(optional = false)
    @Column(name = "totalEarnings")
    private int totalEarnings;
    @Basic(optional = false)
    @Column(name = "bodyMassIndex")
    private float bodyMassIndex;
    @Basic(optional = false)
    @Column(name = "draftYear")
    private short draftYear;
    @Basic(optional = false)
    @Column(name = "marketValue")
    private short marketValue;
    @Basic(optional = false)
    @Column(name = "starPoints")
    private short starPoints;
    @Basic(optional = false)
    @Column(name = "technique")
    private short technique;
    @Basic(optional = false)
    @Column(name = "aggressiveness")
    private short aggressiveness;
    @Basic(optional = false)
    @Column(name = "speed")
    private short speed;
    @Basic(optional = false)
    @Column(name = "stamina")
    private short stamina;
    @Basic(optional = false)
    @Column(name = "accumulatedFatigue")
    private short accumulatedFatigue;
    @Basic(optional = false)
    @Column(name = "tirednessRate")
    private short tirednessRate;
    @Basic(optional = false)
    @Column(name = "peakAge")
    private short peakAge;
    @Basic(optional = false)
    @Column(name = "strenght")
    private short strenght;
    @Basic(optional = false)
    @Column(name = "rookieWeakness")
    private short rookieWeakness;
    @Basic(optional = false)
    @Column(name = "seasonMomentum")
    private short seasonMomentum;
    @Basic(optional = false)
    @Column(name = "retired")
    private boolean retired;
    @Basic(optional = false)
    @Column(name = "happinessLevel")
    private short happinessLevel;
    @Basic(optional = false)
    @Column(name = "patience")
    private short patience;
    @Basic(optional = false)
    @Column(name = "discipline")
    private short discipline;
    @Basic(optional = false)
    @Column(name = "workEthic")
    private short workEthic;
    @Basic(optional = false)
    @Column(name = "loyalty")
    private short loyalty;
    @Basic(optional = false)
    @Column(name = "greed")
    private short greed;
    @Basic(optional = false)
    @Column(name = "hand")
    private char hand;
    @Basic(optional = false)
    @Column(name = "lowPost")
    private short lowPost;
    @Basic(optional = false)
    @Column(name = "offenseDedication")
    private short offenseDedication;
    @Basic(optional = false)
    @Column(name = "defenseDedication")
    private short defenseDedication;
    @Basic(optional = false)
    @Column(name = "shootingRange")
    private short shootingRange;
    @Basic(optional = false)
    @Column(name = "fieldGoals")
    private short fieldGoals;
    @Basic(optional = false)
    @Column(name = "threePointers")
    private short threePointers;
    @Basic(optional = false)
    @Column(name = "freeThrows")
    private short freeThrows;
    @Basic(optional = false)
    @Column(name = "inThePaint")
    private short inThePaint;
    @Basic(optional = false)
    @Column(name = "shotCorrection")
    private short shotCorrection;
    @Basic(optional = false)
    @Column(name = "pullUpJumper")
    private short pullUpJumper;
    @Basic(optional = false)
    @Column(name = "runningJumper")
    private short runningJumper;
    @Basic(optional = false)
    @Column(name = "floatingJumper")
    private short floatingJumper;
    @Basic(optional = false)
    @Column(name = "bankShot")
    private short bankShot;
    @Basic(optional = false)
    @Column(name = "scoopShot")
    private short scoopShot;
    @Basic(optional = false)
    @Column(name = "fadeawayShot")
    private short fadeawayShot;
    @Basic(optional = false)
    @Column(name = "turnaroundShot")
    private short turnaroundShot;
    @Basic(optional = false)
    @Column(name = "hookShot")
    private short hookShot;
    @Basic(optional = false)
    @Column(name = "layupShot")
    private short layupShot;
    @Basic(optional = false)
    @Column(name = "reverseLayupShot")
    private short reverseLayupShot;
    @Basic(optional = false)
    @Column(name = "fingerRollShot")
    private short fingerRollShot;
    @Basic(optional = false)
    @Column(name = "catchAndShootShot")
    private short catchAndShootShot;
    @Basic(optional = false)
    @Column(name = "stepBackShot")
    private short stepBackShot;
    @Basic(optional = false)
    @Column(name = "dunk")
    private short dunk;
    @Basic(optional = false)
    @Column(name = "crunchTimeShooting")
    private short crunchTimeShooting;
    @Basic(optional = false)
    @Column(name = "fakeShotFrequency")
    private short fakeShotFrequency;
    @Basic(optional = false)
    @Column(name = "ballHandling")
    private short ballHandling;
    @Basic(optional = false)
    @Column(name = "dribble")
    private short dribble;
    @Basic(optional = false)
    @Column(name = "drive")
    private short drive;
    @Basic(optional = false)
    @Column(name = "courtVision")
    private short courtVision;
    @Basic(optional = false)
    @Column(name = "creativity")
    private short creativity;
    @Basic(optional = false)
    @Column(name = "pass")
    private short pass;
    @Basic(optional = false)
    @Column(name = "behindTheBackPass")
    private short behindTheBackPass;
    @Basic(optional = false)
    @Column(name = "noLookPass")
    private short noLookPass;
    @Basic(optional = false)
    @Column(name = "alleyOopPass")
    private short alleyOopPass;
    @Basic(optional = false)
    @Column(name = "bouncePass")
    private short bouncePass;
    @Basic(optional = false)
    @Column(name = "steal")
    private short steal;
    @Basic(optional = false)
    @Column(name = "block")
    private short block;
    @Basic(optional = false)
    @Column(name = "contest")
    private short contest;
    @Basic(optional = false)
    @Column(name = "boxOut")
    private short boxOut;
    @Basic(optional = false)
    @Column(name = "oneOnOneDefense")
    private short oneOnOneDefense;
    @Basic(optional = false)
    @Column(name = "helpDefense")
    private short helpDefense;
    @Basic(optional = false)
    @Column(name = "defensiveRebound")
    private short defensiveRebound;
    @Basic(optional = false)
    @Column(name = "offensiveRebound")
    private short offensiveRebound;
    @Basic(optional = false)
    @Column(name = "jump")
    private short jump;
    @Basic(optional = false)
    @Column(name = "milestones")
    private short milestones;
    @Basic(optional = false)
    @Column(name = "regularSeasonExperience")
    private short regularSeasonExperience;
    @Basic(optional = false)
    @Column(name = "playoffsExperience")
    private short playoffsExperience;
    @Basic(optional = false)
    @Column(name = "gamesWithTeam")
    private short gamesWithTeam;
    @Basic(optional = false)
    @Column(name = "titles")
    private short titles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Collection<PlayerLog> playerLogCollection;
    @JoinColumn(name = "favoriteCourtZone", referencedColumnName = "id")
    @ManyToOne
    private CourtZone favoriteCourtZone;
    @JoinColumn(name = "country", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Country country;
    @JoinColumn(name = "event", referencedColumnName = "id")
    @ManyToOne
    private Event event;
    @JoinColumn(name = "franchise", referencedColumnName = "id")
    @ManyToOne
    private Franchise franchise;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Collection<Draft> draftCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assistsLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "totalReboundsLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "defensiveReboundsLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offensiveReboundsLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "threePointersLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection4;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stealsLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection5;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "blocksLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection6;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turnoversLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection7;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personalFoulsLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection8;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mvp")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection9;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "minutesLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection10;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pointsLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection11;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fieldGoalsLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection12;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "freeThrowsLeader")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection13;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Collection<PlayerAward> playerAwardCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Collection<PlayerTransaction> playerTransactionCollection;

    public Player() {
    }

    public Player(Integer id) {
        this.id = id;
    }

    public Player(Integer id, String position, String position2, String firstName, String lastName, short seasons, boolean hallOfFame, short jersey, short age, short height, short weight, short injuryImpact, short injuryTendency, boolean active, boolean playable, short rosterPosition, short remainingYears, short failedContractAttempts, int salary, int totalEarnings, float bodyMassIndex, short draftYear, short marketValue, short starPoints, short technique, short aggressiveness, short speed, short stamina, short accumulatedFatigue, short tirednessRate, short peakAge, short strenght, short rookieWeakness, short seasonMomentum, boolean retired, short happinessLevel, short patience, short discipline, short workEthic, short loyalty, short greed, char hand, short lowPost, short offenseDedication, short defenseDedication, short shootingRange, short fieldGoals, short threePointers, short freeThrows, short inThePaint, short shotCorrection, short pullUpJumper, short runningJumper, short floatingJumper, short bankShot, short scoopShot, short fadeawayShot, short turnaroundShot, short hookShot, short layupShot, short reverseLayupShot, short fingerRollShot, short catchAndShootShot, short stepBackShot, short dunk, short crunchTimeShooting, short fakeShotFrequency, short ballHandling, short dribble, short drive, short courtVision, short creativity, short pass, short behindTheBackPass, short noLookPass, short alleyOopPass, short bouncePass, short steal, short block, short contest, short boxOut, short oneOnOneDefense, short helpDefense, short defensiveRebound, short offensiveRebound, short jump, short milestones, short regularSeasonExperience, short playoffsExperience, short gamesWithTeam, short titles) {
        this.id = id;
        this.position = position;
        this.position2 = position2;
        this.firstName = firstName;
        this.lastName = lastName;
        this.seasons = seasons;
        this.hallOfFame = hallOfFame;
        this.jersey = jersey;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.injuryImpact = injuryImpact;
        this.injuryTendency = injuryTendency;
        this.active = active;
        this.playable = playable;
        this.rosterPosition = rosterPosition;
        this.remainingYears = remainingYears;
        this.failedContractAttempts = failedContractAttempts;
        this.salary = salary;
        this.totalEarnings = totalEarnings;
        this.bodyMassIndex = bodyMassIndex;
        this.draftYear = draftYear;
        this.marketValue = marketValue;
        this.starPoints = starPoints;
        this.technique = technique;
        this.aggressiveness = aggressiveness;
        this.speed = speed;
        this.stamina = stamina;
        this.accumulatedFatigue = accumulatedFatigue;
        this.tirednessRate = tirednessRate;
        this.peakAge = peakAge;
        this.strenght = strenght;
        this.rookieWeakness = rookieWeakness;
        this.seasonMomentum = seasonMomentum;
        this.retired = retired;
        this.happinessLevel = happinessLevel;
        this.patience = patience;
        this.discipline = discipline;
        this.workEthic = workEthic;
        this.loyalty = loyalty;
        this.greed = greed;
        this.hand = hand;
        this.lowPost = lowPost;
        this.offenseDedication = offenseDedication;
        this.defenseDedication = defenseDedication;
        this.shootingRange = shootingRange;
        this.fieldGoals = fieldGoals;
        this.threePointers = threePointers;
        this.freeThrows = freeThrows;
        this.inThePaint = inThePaint;
        this.shotCorrection = shotCorrection;
        this.pullUpJumper = pullUpJumper;
        this.runningJumper = runningJumper;
        this.floatingJumper = floatingJumper;
        this.bankShot = bankShot;
        this.scoopShot = scoopShot;
        this.fadeawayShot = fadeawayShot;
        this.turnaroundShot = turnaroundShot;
        this.hookShot = hookShot;
        this.layupShot = layupShot;
        this.reverseLayupShot = reverseLayupShot;
        this.fingerRollShot = fingerRollShot;
        this.catchAndShootShot = catchAndShootShot;
        this.stepBackShot = stepBackShot;
        this.dunk = dunk;
        this.crunchTimeShooting = crunchTimeShooting;
        this.fakeShotFrequency = fakeShotFrequency;
        this.ballHandling = ballHandling;
        this.dribble = dribble;
        this.drive = drive;
        this.courtVision = courtVision;
        this.creativity = creativity;
        this.pass = pass;
        this.behindTheBackPass = behindTheBackPass;
        this.noLookPass = noLookPass;
        this.alleyOopPass = alleyOopPass;
        this.bouncePass = bouncePass;
        this.steal = steal;
        this.block = block;
        this.contest = contest;
        this.boxOut = boxOut;
        this.oneOnOneDefense = oneOnOneDefense;
        this.helpDefense = helpDefense;
        this.defensiveRebound = defensiveRebound;
        this.offensiveRebound = offensiveRebound;
        this.jump = jump;
        this.milestones = milestones;
        this.regularSeasonExperience = regularSeasonExperience;
        this.playoffsExperience = playoffsExperience;
        this.gamesWithTeam = gamesWithTeam;
        this.titles = titles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition2() {
        return position2;
    }

    public void setPosition2(String position2) {
        this.position2 = position2;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public short getSeasons() {
        return seasons;
    }

    public void setSeasons(short seasons) {
        this.seasons = seasons;
    }

    public boolean getHallOfFame() {
        return hallOfFame;
    }

    public void setHallOfFame(boolean hallOfFame) {
        this.hallOfFame = hallOfFame;
    }

    public short getJersey() {
        return jersey;
    }

    public void setJersey(short jersey) {
        this.jersey = jersey;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public short getWeight() {
        return weight;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }

    public short getInjuryImpact() {
        return injuryImpact;
    }

    public void setInjuryImpact(short injuryImpact) {
        this.injuryImpact = injuryImpact;
    }

    public Date getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(Date recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public short getInjuryTendency() {
        return injuryTendency;
    }

    public void setInjuryTendency(short injuryTendency) {
        this.injuryTendency = injuryTendency;
    }

    public Short getGamesOut() {
        return gamesOut;
    }

    public void setGamesOut(Short gamesOut) {
        this.gamesOut = gamesOut;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getPlayable() {
        return playable;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

    public short getRosterPosition() {
        return rosterPosition;
    }

    public void setRosterPosition(short rosterPosition) {
        this.rosterPosition = rosterPosition;
    }

    public short getRemainingYears() {
        return remainingYears;
    }

    public void setRemainingYears(short remainingYears) {
        this.remainingYears = remainingYears;
    }

    public short getFailedContractAttempts() {
        return failedContractAttempts;
    }

    public void setFailedContractAttempts(short failedContractAttempts) {
        this.failedContractAttempts = failedContractAttempts;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(int totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public float getBodyMassIndex() {
        return bodyMassIndex;
    }

    public void setBodyMassIndex(float bodyMassIndex) {
        this.bodyMassIndex = bodyMassIndex;
    }

    public short getDraftYear() {
        return draftYear;
    }

    public void setDraftYear(short draftYear) {
        this.draftYear = draftYear;
    }

    public short getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(short marketValue) {
        this.marketValue = marketValue;
    }

    public short getStarPoints() {
        return starPoints;
    }

    public void setStarPoints(short starPoints) {
        this.starPoints = starPoints;
    }

    public short getTechnique() {
        return technique;
    }

    public void setTechnique(short technique) {
        this.technique = technique;
    }

    public short getAggressiveness() {
        return aggressiveness;
    }

    public void setAggressiveness(short aggressiveness) {
        this.aggressiveness = aggressiveness;
    }

    public short getSpeed() {
        return speed;
    }

    public void setSpeed(short speed) {
        this.speed = speed;
    }

    public short getStamina() {
        return stamina;
    }

    public void setStamina(short stamina) {
        this.stamina = stamina;
    }

    public short getAccumulatedFatigue() {
        return accumulatedFatigue;
    }

    public void setAccumulatedFatigue(short accumulatedFatigue) {
        this.accumulatedFatigue = accumulatedFatigue;
    }

    public short getTirednessRate() {
        return tirednessRate;
    }

    public void setTirednessRate(short tirednessRate) {
        this.tirednessRate = tirednessRate;
    }

    public short getPeakAge() {
        return peakAge;
    }

    public void setPeakAge(short peakAge) {
        this.peakAge = peakAge;
    }

    public short getStrenght() {
        return strenght;
    }

    public void setStrenght(short strenght) {
        this.strenght = strenght;
    }

    public short getRookieWeakness() {
        return rookieWeakness;
    }

    public void setRookieWeakness(short rookieWeakness) {
        this.rookieWeakness = rookieWeakness;
    }

    public short getSeasonMomentum() {
        return seasonMomentum;
    }

    public void setSeasonMomentum(short seasonMomentum) {
        this.seasonMomentum = seasonMomentum;
    }

    public boolean getRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public short getHappinessLevel() {
        return happinessLevel;
    }

    public void setHappinessLevel(short happinessLevel) {
        this.happinessLevel = happinessLevel;
    }

    public short getPatience() {
        return patience;
    }

    public void setPatience(short patience) {
        this.patience = patience;
    }

    public short getDiscipline() {
        return discipline;
    }

    public void setDiscipline(short discipline) {
        this.discipline = discipline;
    }

    public short getWorkEthic() {
        return workEthic;
    }

    public void setWorkEthic(short workEthic) {
        this.workEthic = workEthic;
    }

    public short getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(short loyalty) {
        this.loyalty = loyalty;
    }

    public short getGreed() {
        return greed;
    }

    public void setGreed(short greed) {
        this.greed = greed;
    }

    public char getHand() {
        return hand;
    }

    public void setHand(char hand) {
        this.hand = hand;
    }

    public short getLowPost() {
        return lowPost;
    }

    public void setLowPost(short lowPost) {
        this.lowPost = lowPost;
    }

    public short getOffenseDedication() {
        return offenseDedication;
    }

    public void setOffenseDedication(short offenseDedication) {
        this.offenseDedication = offenseDedication;
    }

    public short getDefenseDedication() {
        return defenseDedication;
    }

    public void setDefenseDedication(short defenseDedication) {
        this.defenseDedication = defenseDedication;
    }

    public short getShootingRange() {
        return shootingRange;
    }

    public void setShootingRange(short shootingRange) {
        this.shootingRange = shootingRange;
    }

    public short getFieldGoals() {
        return fieldGoals;
    }

    public void setFieldGoals(short fieldGoals) {
        this.fieldGoals = fieldGoals;
    }

    public short getThreePointers() {
        return threePointers;
    }

    public void setThreePointers(short threePointers) {
        this.threePointers = threePointers;
    }

    public short getFreeThrows() {
        return freeThrows;
    }

    public void setFreeThrows(short freeThrows) {
        this.freeThrows = freeThrows;
    }

    public short getInThePaint() {
        return inThePaint;
    }

    public void setInThePaint(short inThePaint) {
        this.inThePaint = inThePaint;
    }

    public short getShotCorrection() {
        return shotCorrection;
    }

    public void setShotCorrection(short shotCorrection) {
        this.shotCorrection = shotCorrection;
    }

    public short getPullUpJumper() {
        return pullUpJumper;
    }

    public void setPullUpJumper(short pullUpJumper) {
        this.pullUpJumper = pullUpJumper;
    }

    public short getRunningJumper() {
        return runningJumper;
    }

    public void setRunningJumper(short runningJumper) {
        this.runningJumper = runningJumper;
    }

    public short getFloatingJumper() {
        return floatingJumper;
    }

    public void setFloatingJumper(short floatingJumper) {
        this.floatingJumper = floatingJumper;
    }

    public short getBankShot() {
        return bankShot;
    }

    public void setBankShot(short bankShot) {
        this.bankShot = bankShot;
    }

    public short getScoopShot() {
        return scoopShot;
    }

    public void setScoopShot(short scoopShot) {
        this.scoopShot = scoopShot;
    }

    public short getFadeawayShot() {
        return fadeawayShot;
    }

    public void setFadeawayShot(short fadeawayShot) {
        this.fadeawayShot = fadeawayShot;
    }

    public short getTurnaroundShot() {
        return turnaroundShot;
    }

    public void setTurnaroundShot(short turnaroundShot) {
        this.turnaroundShot = turnaroundShot;
    }

    public short getHookShot() {
        return hookShot;
    }

    public void setHookShot(short hookShot) {
        this.hookShot = hookShot;
    }

    public short getLayupShot() {
        return layupShot;
    }

    public void setLayupShot(short layupShot) {
        this.layupShot = layupShot;
    }

    public short getReverseLayupShot() {
        return reverseLayupShot;
    }

    public void setReverseLayupShot(short reverseLayupShot) {
        this.reverseLayupShot = reverseLayupShot;
    }

    public short getFingerRollShot() {
        return fingerRollShot;
    }

    public void setFingerRollShot(short fingerRollShot) {
        this.fingerRollShot = fingerRollShot;
    }

    public short getCatchAndShootShot() {
        return catchAndShootShot;
    }

    public void setCatchAndShootShot(short catchAndShootShot) {
        this.catchAndShootShot = catchAndShootShot;
    }

    public short getStepBackShot() {
        return stepBackShot;
    }

    public void setStepBackShot(short stepBackShot) {
        this.stepBackShot = stepBackShot;
    }

    public short getDunk() {
        return dunk;
    }

    public void setDunk(short dunk) {
        this.dunk = dunk;
    }

    public short getCrunchTimeShooting() {
        return crunchTimeShooting;
    }

    public void setCrunchTimeShooting(short crunchTimeShooting) {
        this.crunchTimeShooting = crunchTimeShooting;
    }

    public short getFakeShotFrequency() {
        return fakeShotFrequency;
    }

    public void setFakeShotFrequency(short fakeShotFrequency) {
        this.fakeShotFrequency = fakeShotFrequency;
    }

    public short getBallHandling() {
        return ballHandling;
    }

    public void setBallHandling(short ballHandling) {
        this.ballHandling = ballHandling;
    }

    public short getDribble() {
        return dribble;
    }

    public void setDribble(short dribble) {
        this.dribble = dribble;
    }

    public short getDrive() {
        return drive;
    }

    public void setDrive(short drive) {
        this.drive = drive;
    }

    public short getCourtVision() {
        return courtVision;
    }

    public void setCourtVision(short courtVision) {
        this.courtVision = courtVision;
    }

    public short getCreativity() {
        return creativity;
    }

    public void setCreativity(short creativity) {
        this.creativity = creativity;
    }

    public short getPass() {
        return pass;
    }

    public void setPass(short pass) {
        this.pass = pass;
    }

    public short getBehindTheBackPass() {
        return behindTheBackPass;
    }

    public void setBehindTheBackPass(short behindTheBackPass) {
        this.behindTheBackPass = behindTheBackPass;
    }

    public short getNoLookPass() {
        return noLookPass;
    }

    public void setNoLookPass(short noLookPass) {
        this.noLookPass = noLookPass;
    }

    public short getAlleyOopPass() {
        return alleyOopPass;
    }

    public void setAlleyOopPass(short alleyOopPass) {
        this.alleyOopPass = alleyOopPass;
    }

    public short getBouncePass() {
        return bouncePass;
    }

    public void setBouncePass(short bouncePass) {
        this.bouncePass = bouncePass;
    }

    public short getSteal() {
        return steal;
    }

    public void setSteal(short steal) {
        this.steal = steal;
    }

    public short getBlock() {
        return block;
    }

    public void setBlock(short block) {
        this.block = block;
    }

    public short getContest() {
        return contest;
    }

    public void setContest(short contest) {
        this.contest = contest;
    }

    public short getBoxOut() {
        return boxOut;
    }

    public void setBoxOut(short boxOut) {
        this.boxOut = boxOut;
    }

    public short getOneOnOneDefense() {
        return oneOnOneDefense;
    }

    public void setOneOnOneDefense(short oneOnOneDefense) {
        this.oneOnOneDefense = oneOnOneDefense;
    }

    public short getHelpDefense() {
        return helpDefense;
    }

    public void setHelpDefense(short helpDefense) {
        this.helpDefense = helpDefense;
    }

    public short getDefensiveRebound() {
        return defensiveRebound;
    }

    public void setDefensiveRebound(short defensiveRebound) {
        this.defensiveRebound = defensiveRebound;
    }

    public short getOffensiveRebound() {
        return offensiveRebound;
    }

    public void setOffensiveRebound(short offensiveRebound) {
        this.offensiveRebound = offensiveRebound;
    }

    public short getJump() {
        return jump;
    }

    public void setJump(short jump) {
        this.jump = jump;
    }

    public short getMilestones() {
        return milestones;
    }

    public void setMilestones(short milestones) {
        this.milestones = milestones;
    }

    public short getRegularSeasonExperience() {
        return regularSeasonExperience;
    }

    public void setRegularSeasonExperience(short regularSeasonExperience) {
        this.regularSeasonExperience = regularSeasonExperience;
    }

    public short getPlayoffsExperience() {
        return playoffsExperience;
    }

    public void setPlayoffsExperience(short playoffsExperience) {
        this.playoffsExperience = playoffsExperience;
    }

    public short getGamesWithTeam() {
        return gamesWithTeam;
    }

    public void setGamesWithTeam(short gamesWithTeam) {
        this.gamesWithTeam = gamesWithTeam;
    }

    public short getTitles() {
        return titles;
    }

    public void setTitles(short titles) {
        this.titles = titles;
    }

    @XmlTransient
    public Collection<PlayerLog> getPlayerLogCollection() {
        return playerLogCollection;
    }

    public void setPlayerLogCollection(Collection<PlayerLog> playerLogCollection) {
        this.playerLogCollection = playerLogCollection;
    }

    public CourtZone getFavoriteCourtZone() {
        return favoriteCourtZone;
    }

    public void setFavoriteCourtZone(CourtZone favoriteCourtZone) {
        this.favoriteCourtZone = favoriteCourtZone;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    @XmlTransient
    public Collection<Draft> getDraftCollection() {
        return draftCollection;
    }

    public void setDraftCollection(Collection<Draft> draftCollection) {
        this.draftCollection = draftCollection;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection() {
        return franchiseSeasonLogCollection;
    }

    public void setFranchiseSeasonLogCollection(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection) {
        this.franchiseSeasonLogCollection = franchiseSeasonLogCollection;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection1() {
        return franchiseSeasonLogCollection1;
    }

    public void setFranchiseSeasonLogCollection1(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection1) {
        this.franchiseSeasonLogCollection1 = franchiseSeasonLogCollection1;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection2() {
        return franchiseSeasonLogCollection2;
    }

    public void setFranchiseSeasonLogCollection2(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection2) {
        this.franchiseSeasonLogCollection2 = franchiseSeasonLogCollection2;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection3() {
        return franchiseSeasonLogCollection3;
    }

    public void setFranchiseSeasonLogCollection3(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection3) {
        this.franchiseSeasonLogCollection3 = franchiseSeasonLogCollection3;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection4() {
        return franchiseSeasonLogCollection4;
    }

    public void setFranchiseSeasonLogCollection4(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection4) {
        this.franchiseSeasonLogCollection4 = franchiseSeasonLogCollection4;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection5() {
        return franchiseSeasonLogCollection5;
    }

    public void setFranchiseSeasonLogCollection5(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection5) {
        this.franchiseSeasonLogCollection5 = franchiseSeasonLogCollection5;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection6() {
        return franchiseSeasonLogCollection6;
    }

    public void setFranchiseSeasonLogCollection6(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection6) {
        this.franchiseSeasonLogCollection6 = franchiseSeasonLogCollection6;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection7() {
        return franchiseSeasonLogCollection7;
    }

    public void setFranchiseSeasonLogCollection7(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection7) {
        this.franchiseSeasonLogCollection7 = franchiseSeasonLogCollection7;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection8() {
        return franchiseSeasonLogCollection8;
    }

    public void setFranchiseSeasonLogCollection8(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection8) {
        this.franchiseSeasonLogCollection8 = franchiseSeasonLogCollection8;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection9() {
        return franchiseSeasonLogCollection9;
    }

    public void setFranchiseSeasonLogCollection9(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection9) {
        this.franchiseSeasonLogCollection9 = franchiseSeasonLogCollection9;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection10() {
        return franchiseSeasonLogCollection10;
    }

    public void setFranchiseSeasonLogCollection10(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection10) {
        this.franchiseSeasonLogCollection10 = franchiseSeasonLogCollection10;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection11() {
        return franchiseSeasonLogCollection11;
    }

    public void setFranchiseSeasonLogCollection11(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection11) {
        this.franchiseSeasonLogCollection11 = franchiseSeasonLogCollection11;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection12() {
        return franchiseSeasonLogCollection12;
    }

    public void setFranchiseSeasonLogCollection12(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection12) {
        this.franchiseSeasonLogCollection12 = franchiseSeasonLogCollection12;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection13() {
        return franchiseSeasonLogCollection13;
    }

    public void setFranchiseSeasonLogCollection13(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection13) {
        this.franchiseSeasonLogCollection13 = franchiseSeasonLogCollection13;
    }

    @XmlTransient
    public Collection<PlayerAward> getPlayerAwardCollection() {
        return playerAwardCollection;
    }

    public void setPlayerAwardCollection(Collection<PlayerAward> playerAwardCollection) {
        this.playerAwardCollection = playerAwardCollection;
    }

    @XmlTransient
    public Collection<PlayerTransaction> getPlayerTransactionCollection() {
        return playerTransactionCollection;
    }

    public void setPlayerTransactionCollection(Collection<PlayerTransaction> playerTransactionCollection) {
        this.playerTransactionCollection = playerTransactionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Player[ id=" + id + " ]";
    }
    
}
