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
 * @author Eduardo M. Rodrigues
 * @version 1.0 /2012
 */
@Entity
@Table(name = "game")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findById", query = "SELECT g FROM Game g WHERE g.id = :id"),
    @NamedQuery(name = "Game.findByDate", query = "SELECT g FROM Game g WHERE g.date = :date"),
    @NamedQuery(name = "Game.findByTime", query = "SELECT g FROM Game g WHERE g.time = :time"),
    @NamedQuery(name = "Game.findByType", query = "SELECT g FROM Game g WHERE g.type = :type"),
    @NamedQuery(name = "Game.findByPlayoffSeries", query = "SELECT g FROM Game g WHERE g.playoffSeries = :playoffSeries"),
    @NamedQuery(name = "Game.findByAwayScore", query = "SELECT g FROM Game g WHERE g.awayScore = :awayScore"),
    @NamedQuery(name = "Game.findByHomeScore", query = "SELECT g FROM Game g WHERE g.homeScore = :homeScore"),
    @NamedQuery(name = "Game.findByPlayed", query = "SELECT g FROM Game g WHERE g.played = :played"),
    @NamedQuery(name = "Game.findByPeriodsPlayed", query = "SELECT g FROM Game g WHERE g.periodsPlayed = :periodsPlayed"),
    @NamedQuery(name = "Game.findByAwayTeamLargestLead", query = "SELECT g FROM Game g WHERE g.awayTeamLargestLead = :awayTeamLargestLead"),
    @NamedQuery(name = "Game.findByHomeTeamLargestLead", query = "SELECT g FROM Game g WHERE g.homeTeamLargestLead = :homeTeamLargestLead"),
    @NamedQuery(name = "Game.findByAwayTeamBiggestRun", query = "SELECT g FROM Game g WHERE g.awayTeamBiggestRun = :awayTeamBiggestRun"),
    @NamedQuery(name = "Game.findByHomeTeamBiggestRun", query = "SELECT g FROM Game g WHERE g.homeTeamBiggestRun = :homeTeamBiggestRun"),
    @NamedQuery(name = "Game.findByAttendance", query = "SELECT g FROM Game g WHERE g.attendance = :attendance"),
    @NamedQuery(name = "Game.findByGrossRevenue", query = "SELECT g FROM Game g WHERE g.grossRevenue = :grossRevenue"),
    @NamedQuery(name = "Game.findByMaintenanceCost", query = "SELECT g FROM Game g WHERE g.maintenanceCost = :maintenanceCost")})
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "time")
    @Temporal(TemporalType.TIME)
    private Date time;
    @Basic(optional = false)
    @Column(name = "type")
    private char type;
    @Basic(optional = false)
    @Column(name = "playoffSeries")
    private short playoffSeries;
    @Basic(optional = false)
    @Column(name = "awayScore")
    private short awayScore;
    @Basic(optional = false)
    @Column(name = "homeScore")
    private short homeScore;
    @Basic(optional = false)
    @Column(name = "played")
    private boolean played;
    @Basic(optional = false)
    @Column(name = "periodsPlayed")
    private short periodsPlayed;
    @Basic(optional = false)
    @Column(name = "awayTeamLargestLead")
    private short awayTeamLargestLead;
    @Basic(optional = false)
    @Column(name = "homeTeamLargestLead")
    private short homeTeamLargestLead;
    @Basic(optional = false)
    @Column(name = "awayTeamBiggestRun")
    private short awayTeamBiggestRun;
    @Basic(optional = false)
    @Column(name = "homeTeamBiggestRun")
    private short homeTeamBiggestRun;
    @Basic(optional = false)
    @Column(name = "attendance")
    private int attendance;
    @Basic(optional = false)
    @Column(name = "grossRevenue")
    private int grossRevenue;
    @Basic(optional = false)
    @Column(name = "maintenanceCost")
    private int maintenanceCost;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Collection<PlayerLog> playerLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Collection<GameNews> gameNewsCollection;
    @JoinColumn(name = "season", referencedColumnName = "year")
    @ManyToOne(optional = false)
    private Season season;
    @JoinColumn(name = "arena", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Arena arena;
    @JoinColumn(name = "homeTeam", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Franchise homeTeam;
    @JoinColumn(name = "awayTeam", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Franchise awayTeam;
    @JoinColumn(name = "referee", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Referee referee;
    @JoinColumn(name = "homeTeamCoach", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Coach homeTeamCoach;
    @JoinColumn(name = "awayTeamCoach", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Coach awayTeamCoach;

    public Game() {
    }

    public Game(Integer id) {
        this.id = id;
    }

    public Game(Integer id, Date time, char type, short playoffSeries, short awayScore, short homeScore, boolean played, short periodsPlayed, short awayTeamLargestLead, short homeTeamLargestLead, short awayTeamBiggestRun, short homeTeamBiggestRun, int attendance, int grossRevenue, int maintenanceCost) {
        this.id = id;
        this.time = time;
        this.type = type;
        this.playoffSeries = playoffSeries;
        this.awayScore = awayScore;
        this.homeScore = homeScore;
        this.played = played;
        this.periodsPlayed = periodsPlayed;
        this.awayTeamLargestLead = awayTeamLargestLead;
        this.homeTeamLargestLead = homeTeamLargestLead;
        this.awayTeamBiggestRun = awayTeamBiggestRun;
        this.homeTeamBiggestRun = homeTeamBiggestRun;
        this.attendance = attendance;
        this.grossRevenue = grossRevenue;
        this.maintenanceCost = maintenanceCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public short getPlayoffSeries() {
        return playoffSeries;
    }

    public void setPlayoffSeries(short playoffSeries) {
        this.playoffSeries = playoffSeries;
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

    public boolean getPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public short getPeriodsPlayed() {
        return periodsPlayed;
    }

    public void setPeriodsPlayed(short periodsPlayed) {
        this.periodsPlayed = periodsPlayed;
    }

    public short getAwayTeamLargestLead() {
        return awayTeamLargestLead;
    }

    public void setAwayTeamLargestLead(short awayTeamLargestLead) {
        this.awayTeamLargestLead = awayTeamLargestLead;
    }

    public short getHomeTeamLargestLead() {
        return homeTeamLargestLead;
    }

    public void setHomeTeamLargestLead(short homeTeamLargestLead) {
        this.homeTeamLargestLead = homeTeamLargestLead;
    }

    public short getAwayTeamBiggestRun() {
        return awayTeamBiggestRun;
    }

    public void setAwayTeamBiggestRun(short awayTeamBiggestRun) {
        this.awayTeamBiggestRun = awayTeamBiggestRun;
    }

    public short getHomeTeamBiggestRun() {
        return homeTeamBiggestRun;
    }

    public void setHomeTeamBiggestRun(short homeTeamBiggestRun) {
        this.homeTeamBiggestRun = homeTeamBiggestRun;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getGrossRevenue() {
        return grossRevenue;
    }

    public void setGrossRevenue(int grossRevenue) {
        this.grossRevenue = grossRevenue;
    }

    public int getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(int maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    @XmlTransient
    public Collection<PlayerLog> getPlayerLogCollection() {
        return playerLogCollection;
    }

    public void setPlayerLogCollection(Collection<PlayerLog> playerLogCollection) {
        this.playerLogCollection = playerLogCollection;
    }

    @XmlTransient
    public Collection<GameNews> getGameNewsCollection() {
        return gameNewsCollection;
    }

    public void setGameNewsCollection(Collection<GameNews> gameNewsCollection) {
        this.gameNewsCollection = gameNewsCollection;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public Franchise getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Franchise homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Franchise getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Franchise awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Coach getHomeTeamCoach() {
        return homeTeamCoach;
    }

    public void setHomeTeamCoach(Coach homeTeamCoach) {
        this.homeTeamCoach = homeTeamCoach;
    }

    public Coach getAwayTeamCoach() {
        return awayTeamCoach;
    }

    public void setAwayTeamCoach(Coach awayTeamCoach) {
        this.awayTeamCoach = awayTeamCoach;
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
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Game[ id=" + id + " ]";
    }

} // end class Game
