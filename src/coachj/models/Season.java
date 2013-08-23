/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coachj.models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0 /2012
 */
@Entity
@Table(name = "season")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Season.findAll", query = "SELECT s FROM Season s"),
    @NamedQuery(name = "Season.findByYear", query = "SELECT s FROM Season s WHERE s.year = :year"),
    @NamedQuery(name = "Season.findByFinished", query = "SELECT s FROM Season s WHERE s.finished = :finished")})
public class Season implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "year")
    private Short year;
    @Basic(optional = false)
    @Column(name = "finished")
    private boolean finished;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<PlayerLog> playerLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<GameNews> gameNewsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<Title> titleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<GeneralManagerTransaction> generalManagerTransactionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<CoachTransaction> coachTransactionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<Game> gameCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<GeneralManagerAward> generalManagerAwardCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<PlayoffSeries> playoffSeriesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<Draft> draftCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<CoachAwards> coachAwardsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<PlayerAward> playerAwardCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "season")
    private Collection<PlayerTransaction> playerTransactionCollection;

    public Season() {
    }

    public Season(Short year) {
        this.year = year;
    }

    public Season(Short year, boolean finished) {
        this.year = year;
        this.finished = finished;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
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

    @XmlTransient
    public Collection<Title> getTitleCollection() {
        return titleCollection;
    }

    public void setTitleCollection(Collection<Title> titleCollection) {
        this.titleCollection = titleCollection;
    }

    @XmlTransient
    public Collection<GeneralManagerTransaction> getGeneralManagerTransactionCollection() {
        return generalManagerTransactionCollection;
    }

    public void setGeneralManagerTransactionCollection(Collection<GeneralManagerTransaction> generalManagerTransactionCollection) {
        this.generalManagerTransactionCollection = generalManagerTransactionCollection;
    }

    @XmlTransient
    public Collection<CoachTransaction> getCoachTransactionCollection() {
        return coachTransactionCollection;
    }

    public void setCoachTransactionCollection(Collection<CoachTransaction> coachTransactionCollection) {
        this.coachTransactionCollection = coachTransactionCollection;
    }

    @XmlTransient
    public Collection<Game> getGameCollection() {
        return gameCollection;
    }

    public void setGameCollection(Collection<Game> gameCollection) {
        this.gameCollection = gameCollection;
    }

    @XmlTransient
    public Collection<GeneralManagerAward> getGeneralManagerAwardCollection() {
        return generalManagerAwardCollection;
    }

    public void setGeneralManagerAwardCollection(Collection<GeneralManagerAward> generalManagerAwardCollection) {
        this.generalManagerAwardCollection = generalManagerAwardCollection;
    }

    @XmlTransient
    public Collection<PlayoffSeries> getPlayoffSeriesCollection() {
        return playoffSeriesCollection;
    }

    public void setPlayoffSeriesCollection(Collection<PlayoffSeries> playoffSeriesCollection) {
        this.playoffSeriesCollection = playoffSeriesCollection;
    }

    @XmlTransient
    public Collection<Draft> getDraftCollection() {
        return draftCollection;
    }

    public void setDraftCollection(Collection<Draft> draftCollection) {
        this.draftCollection = draftCollection;
    }

    @XmlTransient
    public Collection<CoachAwards> getCoachAwardsCollection() {
        return coachAwardsCollection;
    }

    public void setCoachAwardsCollection(Collection<CoachAwards> coachAwardsCollection) {
        this.coachAwardsCollection = coachAwardsCollection;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection() {
        return franchiseSeasonLogCollection;
    }

    public void setFranchiseSeasonLogCollection(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection) {
        this.franchiseSeasonLogCollection = franchiseSeasonLogCollection;
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
        hash += (year != null ? year.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Season)) {
            return false;
        }
        Season other = (Season) object;
        if ((this.year == null && other.year != null) || (this.year != null && !this.year.equals(other.year))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Season[ year=" + year + " ]";
    }

} // end class Season
