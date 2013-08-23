/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coachj.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0 /2012
 */
@Entity
@Table(name = "player_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlayerLog.findAll", query = "SELECT p FROM PlayerLog p"),
    @NamedQuery(name = "PlayerLog.findById", query = "SELECT p FROM PlayerLog p WHERE p.id = :id"),
    @NamedQuery(name = "PlayerLog.findByGameType", query = "SELECT p FROM PlayerLog p WHERE p.gameType = :gameType"),
    @NamedQuery(name = "PlayerLog.findByGameDate", query = "SELECT p FROM PlayerLog p WHERE p.gameDate = :gameDate"),
    @NamedQuery(name = "PlayerLog.findByHomeRoad", query = "SELECT p FROM PlayerLog p WHERE p.homeRoad = :homeRoad"),
    @NamedQuery(name = "PlayerLog.findByResult", query = "SELECT p FROM PlayerLog p WHERE p.result = :result"),
    @NamedQuery(name = "PlayerLog.findByNotes", query = "SELECT p FROM PlayerLog p WHERE p.notes = :notes"),
    @NamedQuery(name = "PlayerLog.findByRosterPosition", query = "SELECT p FROM PlayerLog p WHERE p.rosterPosition = :rosterPosition"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterPlayingTime", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterPlayingTime = :firstQuarterPlayingTime"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterPoints", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterPoints = :firstQuarterPoints"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterFieldGoalsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterFieldGoalsAttempted = :firstQuarterFieldGoalsAttempted"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterFieldGoalsMade", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterFieldGoalsMade = :firstQuarterFieldGoalsMade"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterFreeThrowsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterFreeThrowsAttempted = :firstQuarterFreeThrowsAttempted"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterFreeThrowsMade", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterFreeThrowsMade = :firstQuarterFreeThrowsMade"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterThreePointersAttempted", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterThreePointersAttempted = :firstQuarterThreePointersAttempted"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterThreePointersMade", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterThreePointersMade = :firstQuarterThreePointersMade"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterAssists", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterAssists = :firstQuarterAssists"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterOffensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterOffensiveRebounds = :firstQuarterOffensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterDefensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterDefensiveRebounds = :firstQuarterDefensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterBlocks", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterBlocks = :firstQuarterBlocks"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterBlockedShots", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterBlockedShots = :firstQuarterBlockedShots"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterSteals", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterSteals = :firstQuarterSteals"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterTurnovers", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterTurnovers = :firstQuarterTurnovers"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterPersonalFouls", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterPersonalFouls = :firstQuarterPersonalFouls"),
    @NamedQuery(name = "PlayerLog.findByFirstQuarterTechnicalFouls", query = "SELECT p FROM PlayerLog p WHERE p.firstQuarterTechnicalFouls = :firstQuarterTechnicalFouls"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterPlayingTime", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterPlayingTime = :secondQuarterPlayingTime"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterPoints", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterPoints = :secondQuarterPoints"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterFieldGoalsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterFieldGoalsAttempted = :secondQuarterFieldGoalsAttempted"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterFieldGoalsMade", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterFieldGoalsMade = :secondQuarterFieldGoalsMade"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterFreeThrowsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterFreeThrowsAttempted = :secondQuarterFreeThrowsAttempted"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterFreeThrowsMade", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterFreeThrowsMade = :secondQuarterFreeThrowsMade"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterThreePointersAttempted", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterThreePointersAttempted = :secondQuarterThreePointersAttempted"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterThreePointersMade", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterThreePointersMade = :secondQuarterThreePointersMade"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterAssists", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterAssists = :secondQuarterAssists"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterOffensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterOffensiveRebounds = :secondQuarterOffensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterDefensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterDefensiveRebounds = :secondQuarterDefensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterBlocks", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterBlocks = :secondQuarterBlocks"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterBlockedShots", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterBlockedShots = :secondQuarterBlockedShots"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterSteals", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterSteals = :secondQuarterSteals"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterTurnovers", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterTurnovers = :secondQuarterTurnovers"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterPersonalFouls", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterPersonalFouls = :secondQuarterPersonalFouls"),
    @NamedQuery(name = "PlayerLog.findBySecondQuarterTechnicalFouls", query = "SELECT p FROM PlayerLog p WHERE p.secondQuarterTechnicalFouls = :secondQuarterTechnicalFouls"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterPlayingTime", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterPlayingTime = :thirdQuarterPlayingTime"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterPoints", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterPoints = :thirdQuarterPoints"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterFieldGoalsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterFieldGoalsAttempted = :thirdQuarterFieldGoalsAttempted"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterFieldGoalsMade", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterFieldGoalsMade = :thirdQuarterFieldGoalsMade"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterFreeThrowsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterFreeThrowsAttempted = :thirdQuarterFreeThrowsAttempted"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterFreeThrowsMade", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterFreeThrowsMade = :thirdQuarterFreeThrowsMade"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterThreePointersAttempted", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterThreePointersAttempted = :thirdQuarterThreePointersAttempted"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterThreePointersMade", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterThreePointersMade = :thirdQuarterThreePointersMade"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterAssists", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterAssists = :thirdQuarterAssists"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterOffensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterOffensiveRebounds = :thirdQuarterOffensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterDefensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterDefensiveRebounds = :thirdQuarterDefensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterBlocks", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterBlocks = :thirdQuarterBlocks"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterBlockedShots", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterBlockedShots = :thirdQuarterBlockedShots"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterSteals", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterSteals = :thirdQuarterSteals"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterTurnovers", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterTurnovers = :thirdQuarterTurnovers"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterPersonalFouls", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterPersonalFouls = :thirdQuarterPersonalFouls"),
    @NamedQuery(name = "PlayerLog.findByThirdQuarterTechnicalFouls", query = "SELECT p FROM PlayerLog p WHERE p.thirdQuarterTechnicalFouls = :thirdQuarterTechnicalFouls"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterPlayingTime", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterPlayingTime = :fourthQuarterPlayingTime"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterPoints", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterPoints = :fourthQuarterPoints"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterFieldGoalsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterFieldGoalsAttempted = :fourthQuarterFieldGoalsAttempted"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterFieldGoalsMade", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterFieldGoalsMade = :fourthQuarterFieldGoalsMade"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterFreeThrowsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterFreeThrowsAttempted = :fourthQuarterFreeThrowsAttempted"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterFreeThrowsMade", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterFreeThrowsMade = :fourthQuarterFreeThrowsMade"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterThreePointersAttempted", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterThreePointersAttempted = :fourthQuarterThreePointersAttempted"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterThreePointersMade", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterThreePointersMade = :fourthQuarterThreePointersMade"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterAssists", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterAssists = :fourthQuarterAssists"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterOffensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterOffensiveRebounds = :fourthQuarterOffensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterDefensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterDefensiveRebounds = :fourthQuarterDefensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterBlocks", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterBlocks = :fourthQuarterBlocks"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterBlockedShots", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterBlockedShots = :fourthQuarterBlockedShots"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterSteals", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterSteals = :fourthQuarterSteals"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterTurnovers", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterTurnovers = :fourthQuarterTurnovers"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterPersonalFouls", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterPersonalFouls = :fourthQuarterPersonalFouls"),
    @NamedQuery(name = "PlayerLog.findByFourthQuarterTechnicalFouls", query = "SELECT p FROM PlayerLog p WHERE p.fourthQuarterTechnicalFouls = :fourthQuarterTechnicalFouls"),
    @NamedQuery(name = "PlayerLog.findByOvertimePlayingTime", query = "SELECT p FROM PlayerLog p WHERE p.overtimePlayingTime = :overtimePlayingTime"),
    @NamedQuery(name = "PlayerLog.findByOvertimePoints", query = "SELECT p FROM PlayerLog p WHERE p.overtimePoints = :overtimePoints"),
    @NamedQuery(name = "PlayerLog.findByOvertimeFieldGoalsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.overtimeFieldGoalsAttempted = :overtimeFieldGoalsAttempted"),
    @NamedQuery(name = "PlayerLog.findByOvertimeFieldGoalsMade", query = "SELECT p FROM PlayerLog p WHERE p.overtimeFieldGoalsMade = :overtimeFieldGoalsMade"),
    @NamedQuery(name = "PlayerLog.findByOvertimeFreeThrowsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.overtimeFreeThrowsAttempted = :overtimeFreeThrowsAttempted"),
    @NamedQuery(name = "PlayerLog.findByOvertimeFreeThrowsMade", query = "SELECT p FROM PlayerLog p WHERE p.overtimeFreeThrowsMade = :overtimeFreeThrowsMade"),
    @NamedQuery(name = "PlayerLog.findByOvertimeThreePointersAttempted", query = "SELECT p FROM PlayerLog p WHERE p.overtimeThreePointersAttempted = :overtimeThreePointersAttempted"),
    @NamedQuery(name = "PlayerLog.findByOvertimeThreePointersMade", query = "SELECT p FROM PlayerLog p WHERE p.overtimeThreePointersMade = :overtimeThreePointersMade"),
    @NamedQuery(name = "PlayerLog.findByOvertimeAssists", query = "SELECT p FROM PlayerLog p WHERE p.overtimeAssists = :overtimeAssists"),
    @NamedQuery(name = "PlayerLog.findByOvertimeOffensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.overtimeOffensiveRebounds = :overtimeOffensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findByOvertimeDefensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.overtimeDefensiveRebounds = :overtimeDefensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findByOvertimeBlocks", query = "SELECT p FROM PlayerLog p WHERE p.overtimeBlocks = :overtimeBlocks"),
    @NamedQuery(name = "PlayerLog.findByOvertimeBlockedShots", query = "SELECT p FROM PlayerLog p WHERE p.overtimeBlockedShots = :overtimeBlockedShots"),
    @NamedQuery(name = "PlayerLog.findByOvertimeSteals", query = "SELECT p FROM PlayerLog p WHERE p.overtimeSteals = :overtimeSteals"),
    @NamedQuery(name = "PlayerLog.findByOvertimeTurnovers", query = "SELECT p FROM PlayerLog p WHERE p.overtimeTurnovers = :overtimeTurnovers"),
    @NamedQuery(name = "PlayerLog.findByOvertimePersonalFouls", query = "SELECT p FROM PlayerLog p WHERE p.overtimePersonalFouls = :overtimePersonalFouls"),
    @NamedQuery(name = "PlayerLog.findByOvertimeTechnicalFouls", query = "SELECT p FROM PlayerLog p WHERE p.overtimeTechnicalFouls = :overtimeTechnicalFouls")})
public class PlayerLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "gameType")
    private char gameType;
    @Basic(optional = false)
    @Column(name = "gameDate")
    @Temporal(TemporalType.DATE)
    private Date gameDate;
    @Basic(optional = false)
    @Column(name = "homeRoad")
    private char homeRoad;
    @Basic(optional = false)
    @Column(name = "result")
    private char result;
    @Column(name = "notes")
    private String notes;
    @Basic(optional = false)
    @Column(name = "rosterPosition")
    private short rosterPosition;
    @Basic(optional = false)
    @Column(name = "firstQuarterPlayingTime")
    private short firstQuarterPlayingTime;
    @Basic(optional = false)
    @Column(name = "firstQuarterPoints")
    private short firstQuarterPoints;
    @Basic(optional = false)
    @Column(name = "firstQuarterFieldGoalsAttempted")
    private short firstQuarterFieldGoalsAttempted;
    @Basic(optional = false)
    @Column(name = "firstQuarterFieldGoalsMade")
    private short firstQuarterFieldGoalsMade;
    @Basic(optional = false)
    @Column(name = "firstQuarterFreeThrowsAttempted")
    private short firstQuarterFreeThrowsAttempted;
    @Basic(optional = false)
    @Column(name = "firstQuarterFreeThrowsMade")
    private short firstQuarterFreeThrowsMade;
    @Basic(optional = false)
    @Column(name = "firstQuarterThreePointersAttempted")
    private short firstQuarterThreePointersAttempted;
    @Basic(optional = false)
    @Column(name = "firstQuarterThreePointersMade")
    private short firstQuarterThreePointersMade;
    @Basic(optional = false)
    @Column(name = "firstQuarterAssists")
    private short firstQuarterAssists;
    @Basic(optional = false)
    @Column(name = "firstQuarterOffensiveRebounds")
    private short firstQuarterOffensiveRebounds;
    @Basic(optional = false)
    @Column(name = "firstQuarterDefensiveRebounds")
    private short firstQuarterDefensiveRebounds;
    @Basic(optional = false)
    @Column(name = "firstQuarterBlocks")
    private short firstQuarterBlocks;
    @Basic(optional = false)
    @Column(name = "firstQuarterBlockedShots")
    private short firstQuarterBlockedShots;
    @Basic(optional = false)
    @Column(name = "firstQuarterSteals")
    private short firstQuarterSteals;
    @Basic(optional = false)
    @Column(name = "firstQuarterTurnovers")
    private short firstQuarterTurnovers;
    @Basic(optional = false)
    @Column(name = "firstQuarterPersonalFouls")
    private short firstQuarterPersonalFouls;
    @Basic(optional = false)
    @Column(name = "firstQuarterTechnicalFouls")
    private short firstQuarterTechnicalFouls;
    @Basic(optional = false)
    @Column(name = "secondQuarterPlayingTime")
    private short secondQuarterPlayingTime;
    @Basic(optional = false)
    @Column(name = "secondQuarterPoints")
    private short secondQuarterPoints;
    @Basic(optional = false)
    @Column(name = "secondQuarterFieldGoalsAttempted")
    private short secondQuarterFieldGoalsAttempted;
    @Basic(optional = false)
    @Column(name = "secondQuarterFieldGoalsMade")
    private short secondQuarterFieldGoalsMade;
    @Basic(optional = false)
    @Column(name = "secondQuarterFreeThrowsAttempted")
    private short secondQuarterFreeThrowsAttempted;
    @Basic(optional = false)
    @Column(name = "secondQuarterFreeThrowsMade")
    private short secondQuarterFreeThrowsMade;
    @Basic(optional = false)
    @Column(name = "secondQuarterThreePointersAttempted")
    private short secondQuarterThreePointersAttempted;
    @Basic(optional = false)
    @Column(name = "secondQuarterThreePointersMade")
    private short secondQuarterThreePointersMade;
    @Basic(optional = false)
    @Column(name = "secondQuarterAssists")
    private short secondQuarterAssists;
    @Basic(optional = false)
    @Column(name = "secondQuarterOffensiveRebounds")
    private short secondQuarterOffensiveRebounds;
    @Basic(optional = false)
    @Column(name = "secondQuarterDefensiveRebounds")
    private short secondQuarterDefensiveRebounds;
    @Basic(optional = false)
    @Column(name = "secondQuarterBlocks")
    private short secondQuarterBlocks;
    @Basic(optional = false)
    @Column(name = "secondQuarterBlockedShots")
    private short secondQuarterBlockedShots;
    @Basic(optional = false)
    @Column(name = "secondQuarterSteals")
    private short secondQuarterSteals;
    @Basic(optional = false)
    @Column(name = "secondQuarterTurnovers")
    private short secondQuarterTurnovers;
    @Basic(optional = false)
    @Column(name = "secondQuarterPersonalFouls")
    private short secondQuarterPersonalFouls;
    @Basic(optional = false)
    @Column(name = "secondQuarterTechnicalFouls")
    private short secondQuarterTechnicalFouls;
    @Basic(optional = false)
    @Column(name = "thirdQuarterPlayingTime")
    private short thirdQuarterPlayingTime;
    @Basic(optional = false)
    @Column(name = "thirdQuarterPoints")
    private short thirdQuarterPoints;
    @Basic(optional = false)
    @Column(name = "thirdQuarterFieldGoalsAttempted")
    private short thirdQuarterFieldGoalsAttempted;
    @Basic(optional = false)
    @Column(name = "thirdQuarterFieldGoalsMade")
    private short thirdQuarterFieldGoalsMade;
    @Basic(optional = false)
    @Column(name = "thirdQuarterFreeThrowsAttempted")
    private short thirdQuarterFreeThrowsAttempted;
    @Basic(optional = false)
    @Column(name = "thirdQuarterFreeThrowsMade")
    private short thirdQuarterFreeThrowsMade;
    @Basic(optional = false)
    @Column(name = "thirdQuarterThreePointersAttempted")
    private short thirdQuarterThreePointersAttempted;
    @Basic(optional = false)
    @Column(name = "thirdQuarterThreePointersMade")
    private short thirdQuarterThreePointersMade;
    @Basic(optional = false)
    @Column(name = "thirdQuarterAssists")
    private short thirdQuarterAssists;
    @Basic(optional = false)
    @Column(name = "thirdQuarterOffensiveRebounds")
    private short thirdQuarterOffensiveRebounds;
    @Basic(optional = false)
    @Column(name = "thirdQuarterDefensiveRebounds")
    private short thirdQuarterDefensiveRebounds;
    @Basic(optional = false)
    @Column(name = "thirdQuarterBlocks")
    private short thirdQuarterBlocks;
    @Basic(optional = false)
    @Column(name = "thirdQuarterBlockedShots")
    private short thirdQuarterBlockedShots;
    @Basic(optional = false)
    @Column(name = "thirdQuarterSteals")
    private short thirdQuarterSteals;
    @Basic(optional = false)
    @Column(name = "thirdQuarterTurnovers")
    private short thirdQuarterTurnovers;
    @Basic(optional = false)
    @Column(name = "thirdQuarterPersonalFouls")
    private short thirdQuarterPersonalFouls;
    @Basic(optional = false)
    @Column(name = "thirdQuarterTechnicalFouls")
    private short thirdQuarterTechnicalFouls;
    @Basic(optional = false)
    @Column(name = "fourthQuarterPlayingTime")
    private short fourthQuarterPlayingTime;
    @Basic(optional = false)
    @Column(name = "fourthQuarterPoints")
    private short fourthQuarterPoints;
    @Basic(optional = false)
    @Column(name = "fourthQuarterFieldGoalsAttempted")
    private short fourthQuarterFieldGoalsAttempted;
    @Basic(optional = false)
    @Column(name = "fourthQuarterFieldGoalsMade")
    private short fourthQuarterFieldGoalsMade;
    @Basic(optional = false)
    @Column(name = "fourthQuarterFreeThrowsAttempted")
    private short fourthQuarterFreeThrowsAttempted;
    @Basic(optional = false)
    @Column(name = "fourthQuarterFreeThrowsMade")
    private short fourthQuarterFreeThrowsMade;
    @Basic(optional = false)
    @Column(name = "fourthQuarterThreePointersAttempted")
    private short fourthQuarterThreePointersAttempted;
    @Basic(optional = false)
    @Column(name = "fourthQuarterThreePointersMade")
    private short fourthQuarterThreePointersMade;
    @Basic(optional = false)
    @Column(name = "fourthQuarterAssists")
    private short fourthQuarterAssists;
    @Basic(optional = false)
    @Column(name = "fourthQuarterOffensiveRebounds")
    private short fourthQuarterOffensiveRebounds;
    @Basic(optional = false)
    @Column(name = "fourthQuarterDefensiveRebounds")
    private short fourthQuarterDefensiveRebounds;
    @Basic(optional = false)
    @Column(name = "fourthQuarterBlocks")
    private short fourthQuarterBlocks;
    @Basic(optional = false)
    @Column(name = "fourthQuarterBlockedShots")
    private short fourthQuarterBlockedShots;
    @Basic(optional = false)
    @Column(name = "fourthQuarterSteals")
    private short fourthQuarterSteals;
    @Basic(optional = false)
    @Column(name = "fourthQuarterTurnovers")
    private short fourthQuarterTurnovers;
    @Basic(optional = false)
    @Column(name = "fourthQuarterPersonalFouls")
    private short fourthQuarterPersonalFouls;
    @Basic(optional = false)
    @Column(name = "fourthQuarterTechnicalFouls")
    private short fourthQuarterTechnicalFouls;
    @Basic(optional = false)
    @Column(name = "overtimePlayingTime")
    private short overtimePlayingTime;
    @Basic(optional = false)
    @Column(name = "overtimePoints")
    private short overtimePoints;
    @Basic(optional = false)
    @Column(name = "overtimeFieldGoalsAttempted")
    private short overtimeFieldGoalsAttempted;
    @Basic(optional = false)
    @Column(name = "overtimeFieldGoalsMade")
    private short overtimeFieldGoalsMade;
    @Basic(optional = false)
    @Column(name = "overtimeFreeThrowsAttempted")
    private short overtimeFreeThrowsAttempted;
    @Basic(optional = false)
    @Column(name = "overtimeFreeThrowsMade")
    private short overtimeFreeThrowsMade;
    @Basic(optional = false)
    @Column(name = "overtimeThreePointersAttempted")
    private short overtimeThreePointersAttempted;
    @Basic(optional = false)
    @Column(name = "overtimeThreePointersMade")
    private short overtimeThreePointersMade;
    @Basic(optional = false)
    @Column(name = "overtimeAssists")
    private short overtimeAssists;
    @Basic(optional = false)
    @Column(name = "overtimeOffensiveRebounds")
    private short overtimeOffensiveRebounds;
    @Basic(optional = false)
    @Column(name = "overtimeDefensiveRebounds")
    private short overtimeDefensiveRebounds;
    @Basic(optional = false)
    @Column(name = "overtimeBlocks")
    private short overtimeBlocks;
    @Basic(optional = false)
    @Column(name = "overtimeBlockedShots")
    private short overtimeBlockedShots;
    @Basic(optional = false)
    @Column(name = "overtimeSteals")
    private short overtimeSteals;
    @Basic(optional = false)
    @Column(name = "overtimeTurnovers")
    private short overtimeTurnovers;
    @Basic(optional = false)
    @Column(name = "overtimePersonalFouls")
    private short overtimePersonalFouls;
    @Basic(optional = false)
    @Column(name = "overtimeTechnicalFouls")
    private short overtimeTechnicalFouls;
    @JoinColumn(name = "season", referencedColumnName = "year")
    @ManyToOne(optional = false)
    private Season season;
    @JoinColumn(name = "event", referencedColumnName = "id")
    @ManyToOne
    private Event event;
    @JoinColumn(name = "team", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Franchise team;
    @JoinColumn(name = "player", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player player;
    @JoinColumn(name = "opponent", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Franchise opponent;
    @JoinColumn(name = "game", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Game game;

    public PlayerLog() {
    }

    public PlayerLog(Integer id) {
        this.id = id;
    }

    public PlayerLog(Integer id, char gameType, Date gameDate, char homeRoad, char result, short rosterPosition, short firstQuarterPlayingTime, short firstQuarterPoints, short firstQuarterFieldGoalsAttempted, short firstQuarterFieldGoalsMade, short firstQuarterFreeThrowsAttempted, short firstQuarterFreeThrowsMade, short firstQuarterThreePointersAttempted, short firstQuarterThreePointersMade, short firstQuarterAssists, short firstQuarterOffensiveRebounds, short firstQuarterDefensiveRebounds, short firstQuarterBlocks, short firstQuarterBlockedShots, short firstQuarterSteals, short firstQuarterTurnovers, short firstQuarterPersonalFouls, short firstQuarterTechnicalFouls, short secondQuarterPlayingTime, short secondQuarterPoints, short secondQuarterFieldGoalsAttempted, short secondQuarterFieldGoalsMade, short secondQuarterFreeThrowsAttempted, short secondQuarterFreeThrowsMade, short secondQuarterThreePointersAttempted, short secondQuarterThreePointersMade, short secondQuarterAssists, short secondQuarterOffensiveRebounds, short secondQuarterDefensiveRebounds, short secondQuarterBlocks, short secondQuarterBlockedShots, short secondQuarterSteals, short secondQuarterTurnovers, short secondQuarterPersonalFouls, short secondQuarterTechnicalFouls, short thirdQuarterPlayingTime, short thirdQuarterPoints, short thirdQuarterFieldGoalsAttempted, short thirdQuarterFieldGoalsMade, short thirdQuarterFreeThrowsAttempted, short thirdQuarterFreeThrowsMade, short thirdQuarterThreePointersAttempted, short thirdQuarterThreePointersMade, short thirdQuarterAssists, short thirdQuarterOffensiveRebounds, short thirdQuarterDefensiveRebounds, short thirdQuarterBlocks, short thirdQuarterBlockedShots, short thirdQuarterSteals, short thirdQuarterTurnovers, short thirdQuarterPersonalFouls, short thirdQuarterTechnicalFouls, short fourthQuarterPlayingTime, short fourthQuarterPoints, short fourthQuarterFieldGoalsAttempted, short fourthQuarterFieldGoalsMade, short fourthQuarterFreeThrowsAttempted, short fourthQuarterFreeThrowsMade, short fourthQuarterThreePointersAttempted, short fourthQuarterThreePointersMade, short fourthQuarterAssists, short fourthQuarterOffensiveRebounds, short fourthQuarterDefensiveRebounds, short fourthQuarterBlocks, short fourthQuarterBlockedShots, short fourthQuarterSteals, short fourthQuarterTurnovers, short fourthQuarterPersonalFouls, short fourthQuarterTechnicalFouls, short overtimePlayingTime, short overtimePoints, short overtimeFieldGoalsAttempted, short overtimeFieldGoalsMade, short overtimeFreeThrowsAttempted, short overtimeFreeThrowsMade, short overtimeThreePointersAttempted, short overtimeThreePointersMade, short overtimeAssists, short overtimeOffensiveRebounds, short overtimeDefensiveRebounds, short overtimeBlocks, short overtimeBlockedShots, short overtimeSteals, short overtimeTurnovers, short overtimePersonalFouls, short overtimeTechnicalFouls) {
        this.id = id;
        this.gameType = gameType;
        this.gameDate = gameDate;
        this.homeRoad = homeRoad;
        this.result = result;
        this.rosterPosition = rosterPosition;
        this.firstQuarterPlayingTime = firstQuarterPlayingTime;
        this.firstQuarterPoints = firstQuarterPoints;
        this.firstQuarterFieldGoalsAttempted = firstQuarterFieldGoalsAttempted;
        this.firstQuarterFieldGoalsMade = firstQuarterFieldGoalsMade;
        this.firstQuarterFreeThrowsAttempted = firstQuarterFreeThrowsAttempted;
        this.firstQuarterFreeThrowsMade = firstQuarterFreeThrowsMade;
        this.firstQuarterThreePointersAttempted = firstQuarterThreePointersAttempted;
        this.firstQuarterThreePointersMade = firstQuarterThreePointersMade;
        this.firstQuarterAssists = firstQuarterAssists;
        this.firstQuarterOffensiveRebounds = firstQuarterOffensiveRebounds;
        this.firstQuarterDefensiveRebounds = firstQuarterDefensiveRebounds;
        this.firstQuarterBlocks = firstQuarterBlocks;
        this.firstQuarterBlockedShots = firstQuarterBlockedShots;
        this.firstQuarterSteals = firstQuarterSteals;
        this.firstQuarterTurnovers = firstQuarterTurnovers;
        this.firstQuarterPersonalFouls = firstQuarterPersonalFouls;
        this.firstQuarterTechnicalFouls = firstQuarterTechnicalFouls;
        this.secondQuarterPlayingTime = secondQuarterPlayingTime;
        this.secondQuarterPoints = secondQuarterPoints;
        this.secondQuarterFieldGoalsAttempted = secondQuarterFieldGoalsAttempted;
        this.secondQuarterFieldGoalsMade = secondQuarterFieldGoalsMade;
        this.secondQuarterFreeThrowsAttempted = secondQuarterFreeThrowsAttempted;
        this.secondQuarterFreeThrowsMade = secondQuarterFreeThrowsMade;
        this.secondQuarterThreePointersAttempted = secondQuarterThreePointersAttempted;
        this.secondQuarterThreePointersMade = secondQuarterThreePointersMade;
        this.secondQuarterAssists = secondQuarterAssists;
        this.secondQuarterOffensiveRebounds = secondQuarterOffensiveRebounds;
        this.secondQuarterDefensiveRebounds = secondQuarterDefensiveRebounds;
        this.secondQuarterBlocks = secondQuarterBlocks;
        this.secondQuarterBlockedShots = secondQuarterBlockedShots;
        this.secondQuarterSteals = secondQuarterSteals;
        this.secondQuarterTurnovers = secondQuarterTurnovers;
        this.secondQuarterPersonalFouls = secondQuarterPersonalFouls;
        this.secondQuarterTechnicalFouls = secondQuarterTechnicalFouls;
        this.thirdQuarterPlayingTime = thirdQuarterPlayingTime;
        this.thirdQuarterPoints = thirdQuarterPoints;
        this.thirdQuarterFieldGoalsAttempted = thirdQuarterFieldGoalsAttempted;
        this.thirdQuarterFieldGoalsMade = thirdQuarterFieldGoalsMade;
        this.thirdQuarterFreeThrowsAttempted = thirdQuarterFreeThrowsAttempted;
        this.thirdQuarterFreeThrowsMade = thirdQuarterFreeThrowsMade;
        this.thirdQuarterThreePointersAttempted = thirdQuarterThreePointersAttempted;
        this.thirdQuarterThreePointersMade = thirdQuarterThreePointersMade;
        this.thirdQuarterAssists = thirdQuarterAssists;
        this.thirdQuarterOffensiveRebounds = thirdQuarterOffensiveRebounds;
        this.thirdQuarterDefensiveRebounds = thirdQuarterDefensiveRebounds;
        this.thirdQuarterBlocks = thirdQuarterBlocks;
        this.thirdQuarterBlockedShots = thirdQuarterBlockedShots;
        this.thirdQuarterSteals = thirdQuarterSteals;
        this.thirdQuarterTurnovers = thirdQuarterTurnovers;
        this.thirdQuarterPersonalFouls = thirdQuarterPersonalFouls;
        this.thirdQuarterTechnicalFouls = thirdQuarterTechnicalFouls;
        this.fourthQuarterPlayingTime = fourthQuarterPlayingTime;
        this.fourthQuarterPoints = fourthQuarterPoints;
        this.fourthQuarterFieldGoalsAttempted = fourthQuarterFieldGoalsAttempted;
        this.fourthQuarterFieldGoalsMade = fourthQuarterFieldGoalsMade;
        this.fourthQuarterFreeThrowsAttempted = fourthQuarterFreeThrowsAttempted;
        this.fourthQuarterFreeThrowsMade = fourthQuarterFreeThrowsMade;
        this.fourthQuarterThreePointersAttempted = fourthQuarterThreePointersAttempted;
        this.fourthQuarterThreePointersMade = fourthQuarterThreePointersMade;
        this.fourthQuarterAssists = fourthQuarterAssists;
        this.fourthQuarterOffensiveRebounds = fourthQuarterOffensiveRebounds;
        this.fourthQuarterDefensiveRebounds = fourthQuarterDefensiveRebounds;
        this.fourthQuarterBlocks = fourthQuarterBlocks;
        this.fourthQuarterBlockedShots = fourthQuarterBlockedShots;
        this.fourthQuarterSteals = fourthQuarterSteals;
        this.fourthQuarterTurnovers = fourthQuarterTurnovers;
        this.fourthQuarterPersonalFouls = fourthQuarterPersonalFouls;
        this.fourthQuarterTechnicalFouls = fourthQuarterTechnicalFouls;
        this.overtimePlayingTime = overtimePlayingTime;
        this.overtimePoints = overtimePoints;
        this.overtimeFieldGoalsAttempted = overtimeFieldGoalsAttempted;
        this.overtimeFieldGoalsMade = overtimeFieldGoalsMade;
        this.overtimeFreeThrowsAttempted = overtimeFreeThrowsAttempted;
        this.overtimeFreeThrowsMade = overtimeFreeThrowsMade;
        this.overtimeThreePointersAttempted = overtimeThreePointersAttempted;
        this.overtimeThreePointersMade = overtimeThreePointersMade;
        this.overtimeAssists = overtimeAssists;
        this.overtimeOffensiveRebounds = overtimeOffensiveRebounds;
        this.overtimeDefensiveRebounds = overtimeDefensiveRebounds;
        this.overtimeBlocks = overtimeBlocks;
        this.overtimeBlockedShots = overtimeBlockedShots;
        this.overtimeSteals = overtimeSteals;
        this.overtimeTurnovers = overtimeTurnovers;
        this.overtimePersonalFouls = overtimePersonalFouls;
        this.overtimeTechnicalFouls = overtimeTechnicalFouls;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public char getGameType() {
        return gameType;
    }

    public void setGameType(char gameType) {
        this.gameType = gameType;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public char getHomeRoad() {
        return homeRoad;
    }

    public void setHomeRoad(char homeRoad) {
        this.homeRoad = homeRoad;
    }

    public char getResult() {
        return result;
    }

    public void setResult(char result) {
        this.result = result;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public short getRosterPosition() {
        return rosterPosition;
    }

    public void setRosterPosition(short rosterPosition) {
        this.rosterPosition = rosterPosition;
    }

    public short getFirstQuarterPlayingTime() {
        return firstQuarterPlayingTime;
    }

    public void setFirstQuarterPlayingTime(short firstQuarterPlayingTime) {
        this.firstQuarterPlayingTime = firstQuarterPlayingTime;
    }

    public short getFirstQuarterPoints() {
        return firstQuarterPoints;
    }

    public void setFirstQuarterPoints(short firstQuarterPoints) {
        this.firstQuarterPoints = firstQuarterPoints;
    }

    public short getFirstQuarterFieldGoalsAttempted() {
        return firstQuarterFieldGoalsAttempted;
    }

    public void setFirstQuarterFieldGoalsAttempted(short firstQuarterFieldGoalsAttempted) {
        this.firstQuarterFieldGoalsAttempted = firstQuarterFieldGoalsAttempted;
    }

    public short getFirstQuarterFieldGoalsMade() {
        return firstQuarterFieldGoalsMade;
    }

    public void setFirstQuarterFieldGoalsMade(short firstQuarterFieldGoalsMade) {
        this.firstQuarterFieldGoalsMade = firstQuarterFieldGoalsMade;
    }

    public short getFirstQuarterFreeThrowsAttempted() {
        return firstQuarterFreeThrowsAttempted;
    }

    public void setFirstQuarterFreeThrowsAttempted(short firstQuarterFreeThrowsAttempted) {
        this.firstQuarterFreeThrowsAttempted = firstQuarterFreeThrowsAttempted;
    }

    public short getFirstQuarterFreeThrowsMade() {
        return firstQuarterFreeThrowsMade;
    }

    public void setFirstQuarterFreeThrowsMade(short firstQuarterFreeThrowsMade) {
        this.firstQuarterFreeThrowsMade = firstQuarterFreeThrowsMade;
    }

    public short getFirstQuarterThreePointersAttempted() {
        return firstQuarterThreePointersAttempted;
    }

    public void setFirstQuarterThreePointersAttempted(short firstQuarterThreePointersAttempted) {
        this.firstQuarterThreePointersAttempted = firstQuarterThreePointersAttempted;
    }

    public short getFirstQuarterThreePointersMade() {
        return firstQuarterThreePointersMade;
    }

    public void setFirstQuarterThreePointersMade(short firstQuarterThreePointersMade) {
        this.firstQuarterThreePointersMade = firstQuarterThreePointersMade;
    }

    public short getFirstQuarterAssists() {
        return firstQuarterAssists;
    }

    public void setFirstQuarterAssists(short firstQuarterAssists) {
        this.firstQuarterAssists = firstQuarterAssists;
    }

    public short getFirstQuarterOffensiveRebounds() {
        return firstQuarterOffensiveRebounds;
    }

    public void setFirstQuarterOffensiveRebounds(short firstQuarterOffensiveRebounds) {
        this.firstQuarterOffensiveRebounds = firstQuarterOffensiveRebounds;
    }

    public short getFirstQuarterDefensiveRebounds() {
        return firstQuarterDefensiveRebounds;
    }

    public void setFirstQuarterDefensiveRebounds(short firstQuarterDefensiveRebounds) {
        this.firstQuarterDefensiveRebounds = firstQuarterDefensiveRebounds;
    }

    public short getFirstQuarterBlocks() {
        return firstQuarterBlocks;
    }

    public void setFirstQuarterBlocks(short firstQuarterBlocks) {
        this.firstQuarterBlocks = firstQuarterBlocks;
    }

    public short getFirstQuarterBlockedShots() {
        return firstQuarterBlockedShots;
    }

    public void setFirstQuarterBlockedShots(short firstQuarterBlockedShots) {
        this.firstQuarterBlockedShots = firstQuarterBlockedShots;
    }

    public short getFirstQuarterSteals() {
        return firstQuarterSteals;
    }

    public void setFirstQuarterSteals(short firstQuarterSteals) {
        this.firstQuarterSteals = firstQuarterSteals;
    }

    public short getFirstQuarterTurnovers() {
        return firstQuarterTurnovers;
    }

    public void setFirstQuarterTurnovers(short firstQuarterTurnovers) {
        this.firstQuarterTurnovers = firstQuarterTurnovers;
    }

    public short getFirstQuarterPersonalFouls() {
        return firstQuarterPersonalFouls;
    }

    public void setFirstQuarterPersonalFouls(short firstQuarterPersonalFouls) {
        this.firstQuarterPersonalFouls = firstQuarterPersonalFouls;
    }

    public short getFirstQuarterTechnicalFouls() {
        return firstQuarterTechnicalFouls;
    }

    public void setFirstQuarterTechnicalFouls(short firstQuarterTechnicalFouls) {
        this.firstQuarterTechnicalFouls = firstQuarterTechnicalFouls;
    }

    public short getSecondQuarterPlayingTime() {
        return secondQuarterPlayingTime;
    }

    public void setSecondQuarterPlayingTime(short secondQuarterPlayingTime) {
        this.secondQuarterPlayingTime = secondQuarterPlayingTime;
    }

    public short getSecondQuarterPoints() {
        return secondQuarterPoints;
    }

    public void setSecondQuarterPoints(short secondQuarterPoints) {
        this.secondQuarterPoints = secondQuarterPoints;
    }

    public short getSecondQuarterFieldGoalsAttempted() {
        return secondQuarterFieldGoalsAttempted;
    }

    public void setSecondQuarterFieldGoalsAttempted(short secondQuarterFieldGoalsAttempted) {
        this.secondQuarterFieldGoalsAttempted = secondQuarterFieldGoalsAttempted;
    }

    public short getSecondQuarterFieldGoalsMade() {
        return secondQuarterFieldGoalsMade;
    }

    public void setSecondQuarterFieldGoalsMade(short secondQuarterFieldGoalsMade) {
        this.secondQuarterFieldGoalsMade = secondQuarterFieldGoalsMade;
    }

    public short getSecondQuarterFreeThrowsAttempted() {
        return secondQuarterFreeThrowsAttempted;
    }

    public void setSecondQuarterFreeThrowsAttempted(short secondQuarterFreeThrowsAttempted) {
        this.secondQuarterFreeThrowsAttempted = secondQuarterFreeThrowsAttempted;
    }

    public short getSecondQuarterFreeThrowsMade() {
        return secondQuarterFreeThrowsMade;
    }

    public void setSecondQuarterFreeThrowsMade(short secondQuarterFreeThrowsMade) {
        this.secondQuarterFreeThrowsMade = secondQuarterFreeThrowsMade;
    }

    public short getSecondQuarterThreePointersAttempted() {
        return secondQuarterThreePointersAttempted;
    }

    public void setSecondQuarterThreePointersAttempted(short secondQuarterThreePointersAttempted) {
        this.secondQuarterThreePointersAttempted = secondQuarterThreePointersAttempted;
    }

    public short getSecondQuarterThreePointersMade() {
        return secondQuarterThreePointersMade;
    }

    public void setSecondQuarterThreePointersMade(short secondQuarterThreePointersMade) {
        this.secondQuarterThreePointersMade = secondQuarterThreePointersMade;
    }

    public short getSecondQuarterAssists() {
        return secondQuarterAssists;
    }

    public void setSecondQuarterAssists(short secondQuarterAssists) {
        this.secondQuarterAssists = secondQuarterAssists;
    }

    public short getSecondQuarterOffensiveRebounds() {
        return secondQuarterOffensiveRebounds;
    }

    public void setSecondQuarterOffensiveRebounds(short secondQuarterOffensiveRebounds) {
        this.secondQuarterOffensiveRebounds = secondQuarterOffensiveRebounds;
    }

    public short getSecondQuarterDefensiveRebounds() {
        return secondQuarterDefensiveRebounds;
    }

    public void setSecondQuarterDefensiveRebounds(short secondQuarterDefensiveRebounds) {
        this.secondQuarterDefensiveRebounds = secondQuarterDefensiveRebounds;
    }

    public short getSecondQuarterBlocks() {
        return secondQuarterBlocks;
    }

    public void setSecondQuarterBlocks(short secondQuarterBlocks) {
        this.secondQuarterBlocks = secondQuarterBlocks;
    }

    public short getSecondQuarterBlockedShots() {
        return secondQuarterBlockedShots;
    }

    public void setSecondQuarterBlockedShots(short secondQuarterBlockedShots) {
        this.secondQuarterBlockedShots = secondQuarterBlockedShots;
    }

    public short getSecondQuarterSteals() {
        return secondQuarterSteals;
    }

    public void setSecondQuarterSteals(short secondQuarterSteals) {
        this.secondQuarterSteals = secondQuarterSteals;
    }

    public short getSecondQuarterTurnovers() {
        return secondQuarterTurnovers;
    }

    public void setSecondQuarterTurnovers(short secondQuarterTurnovers) {
        this.secondQuarterTurnovers = secondQuarterTurnovers;
    }

    public short getSecondQuarterPersonalFouls() {
        return secondQuarterPersonalFouls;
    }

    public void setSecondQuarterPersonalFouls(short secondQuarterPersonalFouls) {
        this.secondQuarterPersonalFouls = secondQuarterPersonalFouls;
    }

    public short getSecondQuarterTechnicalFouls() {
        return secondQuarterTechnicalFouls;
    }

    public void setSecondQuarterTechnicalFouls(short secondQuarterTechnicalFouls) {
        this.secondQuarterTechnicalFouls = secondQuarterTechnicalFouls;
    }

    public short getThirdQuarterPlayingTime() {
        return thirdQuarterPlayingTime;
    }

    public void setThirdQuarterPlayingTime(short thirdQuarterPlayingTime) {
        this.thirdQuarterPlayingTime = thirdQuarterPlayingTime;
    }

    public short getThirdQuarterPoints() {
        return thirdQuarterPoints;
    }

    public void setThirdQuarterPoints(short thirdQuarterPoints) {
        this.thirdQuarterPoints = thirdQuarterPoints;
    }

    public short getThirdQuarterFieldGoalsAttempted() {
        return thirdQuarterFieldGoalsAttempted;
    }

    public void setThirdQuarterFieldGoalsAttempted(short thirdQuarterFieldGoalsAttempted) {
        this.thirdQuarterFieldGoalsAttempted = thirdQuarterFieldGoalsAttempted;
    }

    public short getThirdQuarterFieldGoalsMade() {
        return thirdQuarterFieldGoalsMade;
    }

    public void setThirdQuarterFieldGoalsMade(short thirdQuarterFieldGoalsMade) {
        this.thirdQuarterFieldGoalsMade = thirdQuarterFieldGoalsMade;
    }

    public short getThirdQuarterFreeThrowsAttempted() {
        return thirdQuarterFreeThrowsAttempted;
    }

    public void setThirdQuarterFreeThrowsAttempted(short thirdQuarterFreeThrowsAttempted) {
        this.thirdQuarterFreeThrowsAttempted = thirdQuarterFreeThrowsAttempted;
    }

    public short getThirdQuarterFreeThrowsMade() {
        return thirdQuarterFreeThrowsMade;
    }

    public void setThirdQuarterFreeThrowsMade(short thirdQuarterFreeThrowsMade) {
        this.thirdQuarterFreeThrowsMade = thirdQuarterFreeThrowsMade;
    }

    public short getThirdQuarterThreePointersAttempted() {
        return thirdQuarterThreePointersAttempted;
    }

    public void setThirdQuarterThreePointersAttempted(short thirdQuarterThreePointersAttempted) {
        this.thirdQuarterThreePointersAttempted = thirdQuarterThreePointersAttempted;
    }

    public short getThirdQuarterThreePointersMade() {
        return thirdQuarterThreePointersMade;
    }

    public void setThirdQuarterThreePointersMade(short thirdQuarterThreePointersMade) {
        this.thirdQuarterThreePointersMade = thirdQuarterThreePointersMade;
    }

    public short getThirdQuarterAssists() {
        return thirdQuarterAssists;
    }

    public void setThirdQuarterAssists(short thirdQuarterAssists) {
        this.thirdQuarterAssists = thirdQuarterAssists;
    }

    public short getThirdQuarterOffensiveRebounds() {
        return thirdQuarterOffensiveRebounds;
    }

    public void setThirdQuarterOffensiveRebounds(short thirdQuarterOffensiveRebounds) {
        this.thirdQuarterOffensiveRebounds = thirdQuarterOffensiveRebounds;
    }

    public short getThirdQuarterDefensiveRebounds() {
        return thirdQuarterDefensiveRebounds;
    }

    public void setThirdQuarterDefensiveRebounds(short thirdQuarterDefensiveRebounds) {
        this.thirdQuarterDefensiveRebounds = thirdQuarterDefensiveRebounds;
    }

    public short getThirdQuarterBlocks() {
        return thirdQuarterBlocks;
    }

    public void setThirdQuarterBlocks(short thirdQuarterBlocks) {
        this.thirdQuarterBlocks = thirdQuarterBlocks;
    }

    public short getThirdQuarterBlockedShots() {
        return thirdQuarterBlockedShots;
    }

    public void setThirdQuarterBlockedShots(short thirdQuarterBlockedShots) {
        this.thirdQuarterBlockedShots = thirdQuarterBlockedShots;
    }

    public short getThirdQuarterSteals() {
        return thirdQuarterSteals;
    }

    public void setThirdQuarterSteals(short thirdQuarterSteals) {
        this.thirdQuarterSteals = thirdQuarterSteals;
    }

    public short getThirdQuarterTurnovers() {
        return thirdQuarterTurnovers;
    }

    public void setThirdQuarterTurnovers(short thirdQuarterTurnovers) {
        this.thirdQuarterTurnovers = thirdQuarterTurnovers;
    }

    public short getThirdQuarterPersonalFouls() {
        return thirdQuarterPersonalFouls;
    }

    public void setThirdQuarterPersonalFouls(short thirdQuarterPersonalFouls) {
        this.thirdQuarterPersonalFouls = thirdQuarterPersonalFouls;
    }

    public short getThirdQuarterTechnicalFouls() {
        return thirdQuarterTechnicalFouls;
    }

    public void setThirdQuarterTechnicalFouls(short thirdQuarterTechnicalFouls) {
        this.thirdQuarterTechnicalFouls = thirdQuarterTechnicalFouls;
    }

    public short getFourthQuarterPlayingTime() {
        return fourthQuarterPlayingTime;
    }

    public void setFourthQuarterPlayingTime(short fourthQuarterPlayingTime) {
        this.fourthQuarterPlayingTime = fourthQuarterPlayingTime;
    }

    public short getFourthQuarterPoints() {
        return fourthQuarterPoints;
    }

    public void setFourthQuarterPoints(short fourthQuarterPoints) {
        this.fourthQuarterPoints = fourthQuarterPoints;
    }

    public short getFourthQuarterFieldGoalsAttempted() {
        return fourthQuarterFieldGoalsAttempted;
    }

    public void setFourthQuarterFieldGoalsAttempted(short fourthQuarterFieldGoalsAttempted) {
        this.fourthQuarterFieldGoalsAttempted = fourthQuarterFieldGoalsAttempted;
    }

    public short getFourthQuarterFieldGoalsMade() {
        return fourthQuarterFieldGoalsMade;
    }

    public void setFourthQuarterFieldGoalsMade(short fourthQuarterFieldGoalsMade) {
        this.fourthQuarterFieldGoalsMade = fourthQuarterFieldGoalsMade;
    }

    public short getFourthQuarterFreeThrowsAttempted() {
        return fourthQuarterFreeThrowsAttempted;
    }

    public void setFourthQuarterFreeThrowsAttempted(short fourthQuarterFreeThrowsAttempted) {
        this.fourthQuarterFreeThrowsAttempted = fourthQuarterFreeThrowsAttempted;
    }

    public short getFourthQuarterFreeThrowsMade() {
        return fourthQuarterFreeThrowsMade;
    }

    public void setFourthQuarterFreeThrowsMade(short fourthQuarterFreeThrowsMade) {
        this.fourthQuarterFreeThrowsMade = fourthQuarterFreeThrowsMade;
    }

    public short getFourthQuarterThreePointersAttempted() {
        return fourthQuarterThreePointersAttempted;
    }

    public void setFourthQuarterThreePointersAttempted(short fourthQuarterThreePointersAttempted) {
        this.fourthQuarterThreePointersAttempted = fourthQuarterThreePointersAttempted;
    }

    public short getFourthQuarterThreePointersMade() {
        return fourthQuarterThreePointersMade;
    }

    public void setFourthQuarterThreePointersMade(short fourthQuarterThreePointersMade) {
        this.fourthQuarterThreePointersMade = fourthQuarterThreePointersMade;
    }

    public short getFourthQuarterAssists() {
        return fourthQuarterAssists;
    }

    public void setFourthQuarterAssists(short fourthQuarterAssists) {
        this.fourthQuarterAssists = fourthQuarterAssists;
    }

    public short getFourthQuarterOffensiveRebounds() {
        return fourthQuarterOffensiveRebounds;
    }

    public void setFourthQuarterOffensiveRebounds(short fourthQuarterOffensiveRebounds) {
        this.fourthQuarterOffensiveRebounds = fourthQuarterOffensiveRebounds;
    }

    public short getFourthQuarterDefensiveRebounds() {
        return fourthQuarterDefensiveRebounds;
    }

    public void setFourthQuarterDefensiveRebounds(short fourthQuarterDefensiveRebounds) {
        this.fourthQuarterDefensiveRebounds = fourthQuarterDefensiveRebounds;
    }

    public short getFourthQuarterBlocks() {
        return fourthQuarterBlocks;
    }

    public void setFourthQuarterBlocks(short fourthQuarterBlocks) {
        this.fourthQuarterBlocks = fourthQuarterBlocks;
    }

    public short getFourthQuarterBlockedShots() {
        return fourthQuarterBlockedShots;
    }

    public void setFourthQuarterBlockedShots(short fourthQuarterBlockedShots) {
        this.fourthQuarterBlockedShots = fourthQuarterBlockedShots;
    }

    public short getFourthQuarterSteals() {
        return fourthQuarterSteals;
    }

    public void setFourthQuarterSteals(short fourthQuarterSteals) {
        this.fourthQuarterSteals = fourthQuarterSteals;
    }

    public short getFourthQuarterTurnovers() {
        return fourthQuarterTurnovers;
    }

    public void setFourthQuarterTurnovers(short fourthQuarterTurnovers) {
        this.fourthQuarterTurnovers = fourthQuarterTurnovers;
    }

    public short getFourthQuarterPersonalFouls() {
        return fourthQuarterPersonalFouls;
    }

    public void setFourthQuarterPersonalFouls(short fourthQuarterPersonalFouls) {
        this.fourthQuarterPersonalFouls = fourthQuarterPersonalFouls;
    }

    public short getFourthQuarterTechnicalFouls() {
        return fourthQuarterTechnicalFouls;
    }

    public void setFourthQuarterTechnicalFouls(short fourthQuarterTechnicalFouls) {
        this.fourthQuarterTechnicalFouls = fourthQuarterTechnicalFouls;
    }

    public short getOvertimePlayingTime() {
        return overtimePlayingTime;
    }

    public void setOvertimePlayingTime(short overtimePlayingTime) {
        this.overtimePlayingTime = overtimePlayingTime;
    }

    public short getOvertimePoints() {
        return overtimePoints;
    }

    public void setOvertimePoints(short overtimePoints) {
        this.overtimePoints = overtimePoints;
    }

    public short getOvertimeFieldGoalsAttempted() {
        return overtimeFieldGoalsAttempted;
    }

    public void setOvertimeFieldGoalsAttempted(short overtimeFieldGoalsAttempted) {
        this.overtimeFieldGoalsAttempted = overtimeFieldGoalsAttempted;
    }

    public short getOvertimeFieldGoalsMade() {
        return overtimeFieldGoalsMade;
    }

    public void setOvertimeFieldGoalsMade(short overtimeFieldGoalsMade) {
        this.overtimeFieldGoalsMade = overtimeFieldGoalsMade;
    }

    public short getOvertimeFreeThrowsAttempted() {
        return overtimeFreeThrowsAttempted;
    }

    public void setOvertimeFreeThrowsAttempted(short overtimeFreeThrowsAttempted) {
        this.overtimeFreeThrowsAttempted = overtimeFreeThrowsAttempted;
    }

    public short getOvertimeFreeThrowsMade() {
        return overtimeFreeThrowsMade;
    }

    public void setOvertimeFreeThrowsMade(short overtimeFreeThrowsMade) {
        this.overtimeFreeThrowsMade = overtimeFreeThrowsMade;
    }

    public short getOvertimeThreePointersAttempted() {
        return overtimeThreePointersAttempted;
    }

    public void setOvertimeThreePointersAttempted(short overtimeThreePointersAttempted) {
        this.overtimeThreePointersAttempted = overtimeThreePointersAttempted;
    }

    public short getOvertimeThreePointersMade() {
        return overtimeThreePointersMade;
    }

    public void setOvertimeThreePointersMade(short overtimeThreePointersMade) {
        this.overtimeThreePointersMade = overtimeThreePointersMade;
    }

    public short getOvertimeAssists() {
        return overtimeAssists;
    }

    public void setOvertimeAssists(short overtimeAssists) {
        this.overtimeAssists = overtimeAssists;
    }

    public short getOvertimeOffensiveRebounds() {
        return overtimeOffensiveRebounds;
    }

    public void setOvertimeOffensiveRebounds(short overtimeOffensiveRebounds) {
        this.overtimeOffensiveRebounds = overtimeOffensiveRebounds;
    }

    public short getOvertimeDefensiveRebounds() {
        return overtimeDefensiveRebounds;
    }

    public void setOvertimeDefensiveRebounds(short overtimeDefensiveRebounds) {
        this.overtimeDefensiveRebounds = overtimeDefensiveRebounds;
    }

    public short getOvertimeBlocks() {
        return overtimeBlocks;
    }

    public void setOvertimeBlocks(short overtimeBlocks) {
        this.overtimeBlocks = overtimeBlocks;
    }

    public short getOvertimeBlockedShots() {
        return overtimeBlockedShots;
    }

    public void setOvertimeBlockedShots(short overtimeBlockedShots) {
        this.overtimeBlockedShots = overtimeBlockedShots;
    }

    public short getOvertimeSteals() {
        return overtimeSteals;
    }

    public void setOvertimeSteals(short overtimeSteals) {
        this.overtimeSteals = overtimeSteals;
    }

    public short getOvertimeTurnovers() {
        return overtimeTurnovers;
    }

    public void setOvertimeTurnovers(short overtimeTurnovers) {
        this.overtimeTurnovers = overtimeTurnovers;
    }

    public short getOvertimePersonalFouls() {
        return overtimePersonalFouls;
    }

    public void setOvertimePersonalFouls(short overtimePersonalFouls) {
        this.overtimePersonalFouls = overtimePersonalFouls;
    }

    public short getOvertimeTechnicalFouls() {
        return overtimeTechnicalFouls;
    }

    public void setOvertimeTechnicalFouls(short overtimeTechnicalFouls) {
        this.overtimeTechnicalFouls = overtimeTechnicalFouls;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Franchise getTeam() {
        return team;
    }

    public void setTeam(Franchise team) {
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Franchise getOpponent() {
        return opponent;
    }

    public void setOpponent(Franchise opponent) {
        this.opponent = opponent;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
        if (!(object instanceof PlayerLog)) {
            return false;
        }
        PlayerLog other = (PlayerLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.PlayerLog[ id=" + id + " ]";
    }

} // end class PlayerLog
