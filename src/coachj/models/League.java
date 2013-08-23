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
 * @author Eduardo M. Rodrigues
 * @version 1.0 /2012
 */
@Entity
@Table(name = "league")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "League.findAll", query = "SELECT l FROM League l"),
    @NamedQuery(name = "League.findById", query = "SELECT l FROM League l WHERE l.id = :id"),
    @NamedQuery(name = "League.findByName", query = "SELECT l FROM League l WHERE l.name = :name"),
    @NamedQuery(name = "League.findByCurrentSeason", query = "SELECT l FROM League l WHERE l.currentSeason = :currentSeason"),
    @NamedQuery(name = "League.findByCurrentDate", query = "SELECT l FROM League l WHERE l.currentDate = :currentDate"),
    @NamedQuery(name = "League.findBySalaryCap", query = "SELECT l FROM League l WHERE l.salaryCap = :salaryCap"),
    @NamedQuery(name = "League.findByMinimumSalary", query = "SELECT l FROM League l WHERE l.minimumSalary = :minimumSalary"),
    @NamedQuery(name = "League.findByFoulsLimit", query = "SELECT l FROM League l WHERE l.foulsLimit = :foulsLimit"),
    @NamedQuery(name = "League.findByTicketPrice", query = "SELECT l FROM League l WHERE l.ticketPrice = :ticketPrice"),
    @NamedQuery(name = "League.findByFreeThrowMinimumEffort", query = "SELECT l FROM League l WHERE l.freeThrowMinimumEffort = :freeThrowMinimumEffort"),
    @NamedQuery(name = "League.findByFieldGoalMinimumEffort", query = "SELECT l FROM League l WHERE l.fieldGoalMinimumEffort = :fieldGoalMinimumEffort"),
    @NamedQuery(name = "League.findByThreePointerMinimumEffort", query = "SELECT l FROM League l WHERE l.threePointerMinimumEffort = :threePointerMinimumEffort"),
    @NamedQuery(name = "League.findByOffenseToDefenseRate", query = "SELECT l FROM League l WHERE l.offenseToDefenseRate = :offenseToDefenseRate"),
    @NamedQuery(name = "League.findByTimeoutsPerQuarter", query = "SELECT l FROM League l WHERE l.timeoutsPerQuarter = :timeoutsPerQuarter"),
    @NamedQuery(name = "League.findBySecondsPerPossession", query = "SELECT l FROM League l WHERE l.secondsPerPossession = :secondsPerPossession"),
    @NamedQuery(name = "League.findByStatus", query = "SELECT l FROM League l WHERE l.status = :status"),
    @NamedQuery(name = "League.findByHomeAdvantageAdjustement", query = "SELECT l FROM League l WHERE l.homeAdvantageAdjustement = :homeAdvantageAdjustement")})
public class League implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "currentSeason")
    private short currentSeason;
    @Column(name = "currentDate")
    @Temporal(TemporalType.DATE)
    private Date currentDate;
    @Basic(optional = false)
    @Column(name = "salaryCap")
    private int salaryCap;
    @Basic(optional = false)
    @Column(name = "minimumSalary")
    private int minimumSalary;
    @Basic(optional = false)
    @Column(name = "foulsLimit")
    private short foulsLimit;
    @Basic(optional = false)
    @Column(name = "ticketPrice")
    private short ticketPrice;
    @Basic(optional = false)
    @Column(name = "freeThrowMinimumEffort")
    private short freeThrowMinimumEffort;
    @Basic(optional = false)
    @Column(name = "fieldGoalMinimumEffort")
    private short fieldGoalMinimumEffort;
    @Basic(optional = false)
    @Column(name = "threePointerMinimumEffort")
    private short threePointerMinimumEffort;
    @Basic(optional = false)
    @Column(name = "offenseToDefenseRate")
    private short offenseToDefenseRate;
    @Basic(optional = false)
    @Column(name = "timeoutsPerQuarter")
    private short timeoutsPerQuarter;
    @Basic(optional = false)
    @Column(name = "secondsPerPossession")
    private short secondsPerPossession;
    @Basic(optional = false)
    @Column(name = "status")
    private short status;
    @Basic(optional = false)
    @Column(name = "homeAdvantageAdjustement")
    private short homeAdvantageAdjustement;

    public League() {
    }

    public League(Short id) {
        this.id = id;
    }

    public League(Short id, String name, short currentSeason, int salaryCap, int minimumSalary, short foulsLimit, short ticketPrice, short freeThrowMinimumEffort, short fieldGoalMinimumEffort, short threePointerMinimumEffort, short offenseToDefenseRate, short timeoutsPerQuarter, short secondsPerPossession, short status, short homeAdvantageAdjustement) {
        this.id = id;
        this.name = name;
        this.currentSeason = currentSeason;
        this.salaryCap = salaryCap;
        this.minimumSalary = minimumSalary;
        this.foulsLimit = foulsLimit;
        this.ticketPrice = ticketPrice;
        this.freeThrowMinimumEffort = freeThrowMinimumEffort;
        this.fieldGoalMinimumEffort = fieldGoalMinimumEffort;
        this.threePointerMinimumEffort = threePointerMinimumEffort;
        this.offenseToDefenseRate = offenseToDefenseRate;
        this.timeoutsPerQuarter = timeoutsPerQuarter;
        this.secondsPerPossession = secondsPerPossession;
        this.status = status;
        this.homeAdvantageAdjustement = homeAdvantageAdjustement;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(short currentSeason) {
        this.currentSeason = currentSeason;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public int getSalaryCap() {
        return salaryCap;
    }

    public void setSalaryCap(int salaryCap) {
        this.salaryCap = salaryCap;
    }

    public int getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(int minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public short getFoulsLimit() {
        return foulsLimit;
    }

    public void setFoulsLimit(short foulsLimit) {
        this.foulsLimit = foulsLimit;
    }

    public short getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(short ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public short getFreeThrowMinimumEffort() {
        return freeThrowMinimumEffort;
    }

    public void setFreeThrowMinimumEffort(short freeThrowMinimumEffort) {
        this.freeThrowMinimumEffort = freeThrowMinimumEffort;
    }

    public short getFieldGoalMinimumEffort() {
        return fieldGoalMinimumEffort;
    }

    public void setFieldGoalMinimumEffort(short fieldGoalMinimumEffort) {
        this.fieldGoalMinimumEffort = fieldGoalMinimumEffort;
    }

    public short getThreePointerMinimumEffort() {
        return threePointerMinimumEffort;
    }

    public void setThreePointerMinimumEffort(short threePointerMinimumEffort) {
        this.threePointerMinimumEffort = threePointerMinimumEffort;
    }

    public short getOffenseToDefenseRate() {
        return offenseToDefenseRate;
    }

    public void setOffenseToDefenseRate(short offenseToDefenseRate) {
        this.offenseToDefenseRate = offenseToDefenseRate;
    }

    public short getTimeoutsPerQuarter() {
        return timeoutsPerQuarter;
    }

    public void setTimeoutsPerQuarter(short timeoutsPerQuarter) {
        this.timeoutsPerQuarter = timeoutsPerQuarter;
    }

    public short getSecondsPerPossession() {
        return secondsPerPossession;
    }

    public void setSecondsPerPossession(short secondsPerPossession) {
        this.secondsPerPossession = secondsPerPossession;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getHomeAdvantageAdjustement() {
        return homeAdvantageAdjustement;
    }

    public void setHomeAdvantageAdjustement(short homeAdvantageAdjustement) {
        this.homeAdvantageAdjustement = homeAdvantageAdjustement;
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
        if (!(object instanceof League)) {
            return false;
        }
        League other = (League) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.League[ id=" + id + " ]";
    }

} // end class League
