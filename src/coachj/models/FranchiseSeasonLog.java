/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coachj.models;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0 /2012
 */
@Entity
@Table(name = "franchise_season_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FranchiseSeasonLog.findAll", query = "SELECT f FROM FranchiseSeasonLog f"),
    @NamedQuery(name = "FranchiseSeasonLog.findById", query = "SELECT f FROM FranchiseSeasonLog f WHERE f.id = :id"),
    @NamedQuery(name = "FranchiseSeasonLog.findByTicketHolders", query = "SELECT f FROM FranchiseSeasonLog f WHERE f.ticketHolders = :ticketHolders"),
    @NamedQuery(name = "FranchiseSeasonLog.findBySeed", query = "SELECT f FROM FranchiseSeasonLog f WHERE f.seed = :seed"),
    @NamedQuery(name = "FranchiseSeasonLog.findByHomeWins", query = "SELECT f FROM FranchiseSeasonLog f WHERE f.homeWins = :homeWins"),
    @NamedQuery(name = "FranchiseSeasonLog.findByHomeLosses", query = "SELECT f FROM FranchiseSeasonLog f WHERE f.homeLosses = :homeLosses"),
    @NamedQuery(name = "FranchiseSeasonLog.findByAwayWins", query = "SELECT f FROM FranchiseSeasonLog f WHERE f.awayWins = :awayWins"),
    @NamedQuery(name = "FranchiseSeasonLog.findByAwayLosses", query = "SELECT f FROM FranchiseSeasonLog f WHERE f.awayLosses = :awayLosses"),
    @NamedQuery(name = "FranchiseSeasonLog.findByRecord", query = "SELECT f FROM FranchiseSeasonLog f WHERE f.record = :record"),
    @NamedQuery(name = "FranchiseSeasonLog.findByLast10", query = "SELECT f FROM FranchiseSeasonLog f WHERE f.last10 = :last10"),
    @NamedQuery(name = "FranchiseSeasonLog.findByStreak", query = "SELECT f FROM FranchiseSeasonLog f WHERE f.streak = :streak"),
    @NamedQuery(name = "FranchiseSeasonLog.findByPlayoffStatus", query = "SELECT f FROM FranchiseSeasonLog f WHERE f.playoffStatus = :playoffStatus")})
public class FranchiseSeasonLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @Column(name = "ticketHolders")
    private int ticketHolders;
    @Basic(optional = false)
    @Column(name = "seed")
    private short seed;
    @Basic(optional = false)
    @Column(name = "homeWins")
    private short homeWins;
    @Basic(optional = false)
    @Column(name = "homeLosses")
    private short homeLosses;
    @Basic(optional = false)
    @Column(name = "awayWins")
    private short awayWins;
    @Basic(optional = false)
    @Column(name = "awayLosses")
    private short awayLosses;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "record")
    private BigDecimal record;
    @Basic(optional = false)
    @Column(name = "last10")
    private String last10;
    @Basic(optional = false)
    @Column(name = "streak")
    private String streak;
    @Basic(optional = false)
    @Column(name = "playoffStatus")
    private String playoffStatus;
    @JoinColumn(name = "season", referencedColumnName = "year")
    @ManyToOne(optional = false)
    private Season season;
    @JoinColumn(name = "mvp", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player mvp;
    @JoinColumn(name = "personalFoulsLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player personalFoulsLeader;
    @JoinColumn(name = "turnoversLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player turnoversLeader;
    @JoinColumn(name = "franchise", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Franchise franchise;
    @JoinColumn(name = "minutesLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player minutesLeader;
    @JoinColumn(name = "pointsLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player pointsLeader;
    @JoinColumn(name = "fieldGoalsLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player fieldGoalsLeader;
    @JoinColumn(name = "freeThrowsLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player freeThrowsLeader;
    @JoinColumn(name = "threePointersLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player threePointersLeader;
    @JoinColumn(name = "offensiveReboundsLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player offensiveReboundsLeader;
    @JoinColumn(name = "defensiveReboundsLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player defensiveReboundsLeader;
    @JoinColumn(name = "totalReboundsLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player totalReboundsLeader;
    @JoinColumn(name = "assistsLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player assistsLeader;
    @JoinColumn(name = "stealsLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player stealsLeader;
    @JoinColumn(name = "blocksLeader", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player blocksLeader;

    public FranchiseSeasonLog() {
    }

    public FranchiseSeasonLog(Short id) {
        this.id = id;
    }

    public FranchiseSeasonLog(Short id, int ticketHolders, short seed, short homeWins, short homeLosses, short awayWins, short awayLosses, BigDecimal record, String last10, String streak, String playoffStatus) {
        this.id = id;
        this.ticketHolders = ticketHolders;
        this.seed = seed;
        this.homeWins = homeWins;
        this.homeLosses = homeLosses;
        this.awayWins = awayWins;
        this.awayLosses = awayLosses;
        this.record = record;
        this.last10 = last10;
        this.streak = streak;
        this.playoffStatus = playoffStatus;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public int getTicketHolders() {
        return ticketHolders;
    }

    public void setTicketHolders(int ticketHolders) {
        this.ticketHolders = ticketHolders;
    }

    public short getSeed() {
        return seed;
    }

    public void setSeed(short seed) {
        this.seed = seed;
    }

    public short getHomeWins() {
        return homeWins;
    }

    public void setHomeWins(short homeWins) {
        this.homeWins = homeWins;
    }

    public short getHomeLosses() {
        return homeLosses;
    }

    public void setHomeLosses(short homeLosses) {
        this.homeLosses = homeLosses;
    }

    public short getAwayWins() {
        return awayWins;
    }

    public void setAwayWins(short awayWins) {
        this.awayWins = awayWins;
    }

    public short getAwayLosses() {
        return awayLosses;
    }

    public void setAwayLosses(short awayLosses) {
        this.awayLosses = awayLosses;
    }

    public BigDecimal getRecord() {
        return record;
    }

    public void setRecord(BigDecimal record) {
        this.record = record;
    }

    public String getLast10() {
        return last10;
    }

    public void setLast10(String last10) {
        this.last10 = last10;
    }

    public String getStreak() {
        return streak;
    }

    public void setStreak(String streak) {
        this.streak = streak;
    }

    public String getPlayoffStatus() {
        return playoffStatus;
    }

    public void setPlayoffStatus(String playoffStatus) {
        this.playoffStatus = playoffStatus;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Player getMvp() {
        return mvp;
    }

    public void setMvp(Player mvp) {
        this.mvp = mvp;
    }

    public Player getPersonalFoulsLeader() {
        return personalFoulsLeader;
    }

    public void setPersonalFoulsLeader(Player personalFoulsLeader) {
        this.personalFoulsLeader = personalFoulsLeader;
    }

    public Player getTurnoversLeader() {
        return turnoversLeader;
    }

    public void setTurnoversLeader(Player turnoversLeader) {
        this.turnoversLeader = turnoversLeader;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public Player getMinutesLeader() {
        return minutesLeader;
    }

    public void setMinutesLeader(Player minutesLeader) {
        this.minutesLeader = minutesLeader;
    }

    public Player getPointsLeader() {
        return pointsLeader;
    }

    public void setPointsLeader(Player pointsLeader) {
        this.pointsLeader = pointsLeader;
    }

    public Player getFieldGoalsLeader() {
        return fieldGoalsLeader;
    }

    public void setFieldGoalsLeader(Player fieldGoalsLeader) {
        this.fieldGoalsLeader = fieldGoalsLeader;
    }

    public Player getFreeThrowsLeader() {
        return freeThrowsLeader;
    }

    public void setFreeThrowsLeader(Player freeThrowsLeader) {
        this.freeThrowsLeader = freeThrowsLeader;
    }

    public Player getThreePointersLeader() {
        return threePointersLeader;
    }

    public void setThreePointersLeader(Player threePointersLeader) {
        this.threePointersLeader = threePointersLeader;
    }

    public Player getOffensiveReboundsLeader() {
        return offensiveReboundsLeader;
    }

    public void setOffensiveReboundsLeader(Player offensiveReboundsLeader) {
        this.offensiveReboundsLeader = offensiveReboundsLeader;
    }

    public Player getDefensiveReboundsLeader() {
        return defensiveReboundsLeader;
    }

    public void setDefensiveReboundsLeader(Player defensiveReboundsLeader) {
        this.defensiveReboundsLeader = defensiveReboundsLeader;
    }

    public Player getTotalReboundsLeader() {
        return totalReboundsLeader;
    }

    public void setTotalReboundsLeader(Player totalReboundsLeader) {
        this.totalReboundsLeader = totalReboundsLeader;
    }

    public Player getAssistsLeader() {
        return assistsLeader;
    }

    public void setAssistsLeader(Player assistsLeader) {
        this.assistsLeader = assistsLeader;
    }

    public Player getStealsLeader() {
        return stealsLeader;
    }

    public void setStealsLeader(Player stealsLeader) {
        this.stealsLeader = stealsLeader;
    }

    public Player getBlocksLeader() {
        return blocksLeader;
    }

    public void setBlocksLeader(Player blocksLeader) {
        this.blocksLeader = blocksLeader;
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
        if (!(object instanceof FranchiseSeasonLog)) {
            return false;
        }
        FranchiseSeasonLog other = (FranchiseSeasonLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.FranchiseSeasonLog[ id=" + id + " ]";
    }

} // end class FranchiseSeasonLog
