/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj.models;

import java.io.Serializable;
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
 * @author Eduardo
 */
@Entity
@Table(name = "playoff_series")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlayoffSeries.findAll", query = "SELECT p FROM PlayoffSeries p"),
    @NamedQuery(name = "PlayoffSeries.findById", query = "SELECT p FROM PlayoffSeries p WHERE p.id = :id"),
    @NamedQuery(name = "PlayoffSeries.findByUpperSeedTeamWins", query = "SELECT p FROM PlayoffSeries p WHERE p.upperSeedTeamWins = :upperSeedTeamWins"),
    @NamedQuery(name = "PlayoffSeries.findByLowerSeedTeamWins", query = "SELECT p FROM PlayoffSeries p WHERE p.lowerSeedTeamWins = :lowerSeedTeamWins"),
    @NamedQuery(name = "PlayoffSeries.findByRound", query = "SELECT p FROM PlayoffSeries p WHERE p.round = :round"),
    @NamedQuery(name = "PlayoffSeries.findByNumber", query = "SELECT p FROM PlayoffSeries p WHERE p.number = :number")})
public class PlayoffSeries implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "upperSeedTeamWins")
    private short upperSeedTeamWins;
    @Basic(optional = false)
    @Column(name = "lowerSeedTeamWins")
    private short lowerSeedTeamWins;
    @Basic(optional = false)
    @Column(name = "round")
    private short round;
    @Basic(optional = false)
    @Column(name = "number")
    private short number;
    @JoinColumn(name = "season", referencedColumnName = "year")
    @ManyToOne(optional = false)
    private Season season;
    @JoinColumn(name = "lowerSeedTeam", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Franchise lowerSeedTeam;
    @JoinColumn(name = "upperSeedTeam", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Franchise upperSeedTeam;

    public PlayoffSeries() {
    }

    public PlayoffSeries(Integer id) {
        this.id = id;
    }

    public PlayoffSeries(Integer id, short upperSeedTeamWins, short lowerSeedTeamWins, short round, short number) {
        this.id = id;
        this.upperSeedTeamWins = upperSeedTeamWins;
        this.lowerSeedTeamWins = lowerSeedTeamWins;
        this.round = round;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getUpperSeedTeamWins() {
        return upperSeedTeamWins;
    }

    public void setUpperSeedTeamWins(short upperSeedTeamWins) {
        this.upperSeedTeamWins = upperSeedTeamWins;
    }

    public short getLowerSeedTeamWins() {
        return lowerSeedTeamWins;
    }

    public void setLowerSeedTeamWins(short lowerSeedTeamWins) {
        this.lowerSeedTeamWins = lowerSeedTeamWins;
    }

    public short getRound() {
        return round;
    }

    public void setRound(short round) {
        this.round = round;
    }

    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Franchise getLowerSeedTeam() {
        return lowerSeedTeam;
    }

    public void setLowerSeedTeam(Franchise lowerSeedTeam) {
        this.lowerSeedTeam = lowerSeedTeam;
    }

    public Franchise getUpperSeedTeam() {
        return upperSeedTeam;
    }

    public void setUpperSeedTeam(Franchise upperSeedTeam) {
        this.upperSeedTeam = upperSeedTeam;
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
        if (!(object instanceof PlayoffSeries)) {
            return false;
        }
        PlayoffSeries other = (PlayoffSeries) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.PlayoffSeries[ id=" + id + " ]";
    }
    
}
