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
 * @author Eduardo
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
    @NamedQuery(name = "PlayerLog.findByPlayingTime", query = "SELECT p FROM PlayerLog p WHERE p.playingTime = :playingTime"),
    @NamedQuery(name = "PlayerLog.findByPoints", query = "SELECT p FROM PlayerLog p WHERE p.points = :points"),
    @NamedQuery(name = "PlayerLog.findByFieldGoalsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.fieldGoalsAttempted = :fieldGoalsAttempted"),
    @NamedQuery(name = "PlayerLog.findByFiieldGoalsMade", query = "SELECT p FROM PlayerLog p WHERE p.fiieldGoalsMade = :fiieldGoalsMade"),
    @NamedQuery(name = "PlayerLog.findByFreeThrowsAttempted", query = "SELECT p FROM PlayerLog p WHERE p.freeThrowsAttempted = :freeThrowsAttempted"),
    @NamedQuery(name = "PlayerLog.findByFreeThrowsMade", query = "SELECT p FROM PlayerLog p WHERE p.freeThrowsMade = :freeThrowsMade"),
    @NamedQuery(name = "PlayerLog.findByThreePointersAttempted", query = "SELECT p FROM PlayerLog p WHERE p.threePointersAttempted = :threePointersAttempted"),
    @NamedQuery(name = "PlayerLog.findByThreePointersMade", query = "SELECT p FROM PlayerLog p WHERE p.threePointersMade = :threePointersMade"),
    @NamedQuery(name = "PlayerLog.findByAssists", query = "SELECT p FROM PlayerLog p WHERE p.assists = :assists"),
    @NamedQuery(name = "PlayerLog.findByOffensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.offensiveRebounds = :offensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findByDefensiveRebounds", query = "SELECT p FROM PlayerLog p WHERE p.defensiveRebounds = :defensiveRebounds"),
    @NamedQuery(name = "PlayerLog.findByBlocks", query = "SELECT p FROM PlayerLog p WHERE p.blocks = :blocks"),
    @NamedQuery(name = "PlayerLog.findByBlockedShots", query = "SELECT p FROM PlayerLog p WHERE p.blockedShots = :blockedShots"),
    @NamedQuery(name = "PlayerLog.findBySteals", query = "SELECT p FROM PlayerLog p WHERE p.steals = :steals"),
    @NamedQuery(name = "PlayerLog.findByTurnovers", query = "SELECT p FROM PlayerLog p WHERE p.turnovers = :turnovers"),
    @NamedQuery(name = "PlayerLog.findByPersonalFouls", query = "SELECT p FROM PlayerLog p WHERE p.personalFouls = :personalFouls"),
    @NamedQuery(name = "PlayerLog.findByTechnicalFouls", query = "SELECT p FROM PlayerLog p WHERE p.technicalFouls = :technicalFouls")})
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
    @Column(name = "playingTime")
    private short playingTime;
    @Basic(optional = false)
    @Column(name = "points")
    private short points;
    @Basic(optional = false)
    @Column(name = "fieldGoalsAttempted")
    private short fieldGoalsAttempted;
    @Basic(optional = false)
    @Column(name = "fiieldGoalsMade")
    private short fiieldGoalsMade;
    @Basic(optional = false)
    @Column(name = "freeThrowsAttempted")
    private short freeThrowsAttempted;
    @Basic(optional = false)
    @Column(name = "freeThrowsMade")
    private short freeThrowsMade;
    @Basic(optional = false)
    @Column(name = "threePointersAttempted")
    private short threePointersAttempted;
    @Basic(optional = false)
    @Column(name = "threePointersMade")
    private short threePointersMade;
    @Basic(optional = false)
    @Column(name = "assists")
    private short assists;
    @Basic(optional = false)
    @Column(name = "offensiveRebounds")
    private short offensiveRebounds;
    @Basic(optional = false)
    @Column(name = "defensiveRebounds")
    private short defensiveRebounds;
    @Basic(optional = false)
    @Column(name = "blocks")
    private short blocks;
    @Basic(optional = false)
    @Column(name = "blockedShots")
    private short blockedShots;
    @Basic(optional = false)
    @Column(name = "steals")
    private short steals;
    @Basic(optional = false)
    @Column(name = "turnovers")
    private short turnovers;
    @Basic(optional = false)
    @Column(name = "personalFouls")
    private short personalFouls;
    @Basic(optional = false)
    @Column(name = "technicalFouls")
    private short technicalFouls;
    @JoinColumn(name = "season", referencedColumnName = "year")
    @ManyToOne(optional = false)
    private Season season;
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
    @JoinColumn(name = "event", referencedColumnName = "id")
    @ManyToOne
    private Event event;

    public PlayerLog() {
    }

    public PlayerLog(Integer id) {
        this.id = id;
    }

    public PlayerLog(Integer id, char gameType, Date gameDate, char homeRoad, char result, short rosterPosition, short playingTime, short points, short fieldGoalsAttempted, short fiieldGoalsMade, short freeThrowsAttempted, short freeThrowsMade, short threePointersAttempted, short threePointersMade, short assists, short offensiveRebounds, short defensiveRebounds, short blocks, short blockedShots, short steals, short turnovers, short personalFouls, short technicalFouls) {
        this.id = id;
        this.gameType = gameType;
        this.gameDate = gameDate;
        this.homeRoad = homeRoad;
        this.result = result;
        this.rosterPosition = rosterPosition;
        this.playingTime = playingTime;
        this.points = points;
        this.fieldGoalsAttempted = fieldGoalsAttempted;
        this.fiieldGoalsMade = fiieldGoalsMade;
        this.freeThrowsAttempted = freeThrowsAttempted;
        this.freeThrowsMade = freeThrowsMade;
        this.threePointersAttempted = threePointersAttempted;
        this.threePointersMade = threePointersMade;
        this.assists = assists;
        this.offensiveRebounds = offensiveRebounds;
        this.defensiveRebounds = defensiveRebounds;
        this.blocks = blocks;
        this.blockedShots = blockedShots;
        this.steals = steals;
        this.turnovers = turnovers;
        this.personalFouls = personalFouls;
        this.technicalFouls = technicalFouls;
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

    public short getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(short playingTime) {
        this.playingTime = playingTime;
    }

    public short getPoints() {
        return points;
    }

    public void setPoints(short points) {
        this.points = points;
    }

    public short getFieldGoalsAttempted() {
        return fieldGoalsAttempted;
    }

    public void setFieldGoalsAttempted(short fieldGoalsAttempted) {
        this.fieldGoalsAttempted = fieldGoalsAttempted;
    }

    public short getFiieldGoalsMade() {
        return fiieldGoalsMade;
    }

    public void setFiieldGoalsMade(short fiieldGoalsMade) {
        this.fiieldGoalsMade = fiieldGoalsMade;
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

    public short getAssists() {
        return assists;
    }

    public void setAssists(short assists) {
        this.assists = assists;
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

    public short getBlocks() {
        return blocks;
    }

    public void setBlocks(short blocks) {
        this.blocks = blocks;
    }

    public short getBlockedShots() {
        return blockedShots;
    }

    public void setBlockedShots(short blockedShots) {
        this.blockedShots = blockedShots;
    }

    public short getSteals() {
        return steals;
    }

    public void setSteals(short steals) {
        this.steals = steals;
    }

    public short getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(short turnovers) {
        this.turnovers = turnovers;
    }

    public short getPersonalFouls() {
        return personalFouls;
    }

    public void setPersonalFouls(short personalFouls) {
        this.personalFouls = personalFouls;
    }

    public short getTechnicalFouls() {
        return technicalFouls;
    }

    public void setTechnicalFouls(short technicalFouls) {
        this.technicalFouls = technicalFouls;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
    
}
