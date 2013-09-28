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
@Table(name = "franchise_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FranchiseLog.findAll", query = "SELECT f FROM FranchiseLog f"),
    @NamedQuery(name = "FranchiseLog.findById", query = "SELECT f FROM FranchiseLog f WHERE f.id = :id"),
    @NamedQuery(name = "FranchiseLog.findByGameType", query = "SELECT f FROM FranchiseLog f WHERE f.gameType = :gameType"),
    @NamedQuery(name = "FranchiseLog.findByGameDate", query = "SELECT f FROM FranchiseLog f WHERE f.gameDate = :gameDate"),
    @NamedQuery(name = "FranchiseLog.findByHomeRoad", query = "SELECT f FROM FranchiseLog f WHERE f.homeRoad = :homeRoad"),
    @NamedQuery(name = "FranchiseLog.findByResult", query = "SELECT f FROM FranchiseLog f WHERE f.result = :result"),
    @NamedQuery(name = "FranchiseLog.findByPoints", query = "SELECT f FROM FranchiseLog f WHERE f.points = :points"),
    @NamedQuery(name = "FranchiseLog.findByFieldGoalsAttempted", query = "SELECT f FROM FranchiseLog f WHERE f.fieldGoalsAttempted = :fieldGoalsAttempted"),
    @NamedQuery(name = "FranchiseLog.findByFieldGoalsMade", query = "SELECT f FROM FranchiseLog f WHERE f.fieldGoalsMade = :fieldGoalsMade"),
    @NamedQuery(name = "FranchiseLog.findByFreeThrowsAttempted", query = "SELECT f FROM FranchiseLog f WHERE f.freeThrowsAttempted = :freeThrowsAttempted"),
    @NamedQuery(name = "FranchiseLog.findByFreeThrowsMade", query = "SELECT f FROM FranchiseLog f WHERE f.freeThrowsMade = :freeThrowsMade"),
    @NamedQuery(name = "FranchiseLog.findByThreePointersAttempted", query = "SELECT f FROM FranchiseLog f WHERE f.threePointersAttempted = :threePointersAttempted"),
    @NamedQuery(name = "FranchiseLog.findByThreePointersMade", query = "SELECT f FROM FranchiseLog f WHERE f.threePointersMade = :threePointersMade"),
    @NamedQuery(name = "FranchiseLog.findByAssists", query = "SELECT f FROM FranchiseLog f WHERE f.assists = :assists"),
    @NamedQuery(name = "FranchiseLog.findByOffensiveRebounds", query = "SELECT f FROM FranchiseLog f WHERE f.offensiveRebounds = :offensiveRebounds"),
    @NamedQuery(name = "FranchiseLog.findByDefensiveRebounds", query = "SELECT f FROM FranchiseLog f WHERE f.defensiveRebounds = :defensiveRebounds"),
    @NamedQuery(name = "FranchiseLog.findByBlocks", query = "SELECT f FROM FranchiseLog f WHERE f.blocks = :blocks"),
    @NamedQuery(name = "FranchiseLog.findByBlockedShots", query = "SELECT f FROM FranchiseLog f WHERE f.blockedShots = :blockedShots"),
    @NamedQuery(name = "FranchiseLog.findBySteals", query = "SELECT f FROM FranchiseLog f WHERE f.steals = :steals"),
    @NamedQuery(name = "FranchiseLog.findByTurnovers", query = "SELECT f FROM FranchiseLog f WHERE f.turnovers = :turnovers"),
    @NamedQuery(name = "FranchiseLog.findByPersonalFouls", query = "SELECT f FROM FranchiseLog f WHERE f.personalFouls = :personalFouls"),
    @NamedQuery(name = "FranchiseLog.findByTechnicalFouls", query = "SELECT f FROM FranchiseLog f WHERE f.technicalFouls = :technicalFouls")})
public class FranchiseLog implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "points")
    private short points;
    @Basic(optional = false)
    @Column(name = "fieldGoalsAttempted")
    private short fieldGoalsAttempted;
    @Basic(optional = false)
    @Column(name = "fieldGoalsMade")
    private short fieldGoalsMade;
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

    public FranchiseLog() {
    }

    public FranchiseLog(Integer id) {
        this.id = id;
    }

    public FranchiseLog(Integer id, char gameType, Date gameDate, char homeRoad, char result, short points, short fieldGoalsAttempted, short fieldGoalsMade, short freeThrowsAttempted, short freeThrowsMade, short threePointersAttempted, short threePointersMade, short assists, short offensiveRebounds, short defensiveRebounds, short blocks, short blockedShots, short steals, short turnovers, short personalFouls, short technicalFouls) {
        this.id = id;
        this.gameType = gameType;
        this.gameDate = gameDate;
        this.homeRoad = homeRoad;
        this.result = result;
        this.points = points;
        this.fieldGoalsAttempted = fieldGoalsAttempted;
        this.fieldGoalsMade = fieldGoalsMade;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FranchiseLog)) {
            return false;
        }
        FranchiseLog other = (FranchiseLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.FranchiseLog[ id=" + id + " ]";
    }
    
}
