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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "award")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Award.findAll", query = "SELECT a FROM Award a"),
    @NamedQuery(name = "Award.findById", query = "SELECT a FROM Award a WHERE a.id = :id"),
    @NamedQuery(name = "Award.findByDescription", query = "SELECT a FROM Award a WHERE a.description = :description"),
    @NamedQuery(name = "Award.findByStarPointsAwarded", query = "SELECT a FROM Award a WHERE a.starPointsAwarded = :starPointsAwarded")})
public class Award implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "starPointsAwarded")
    private short starPointsAwarded;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "award")
    private Collection<GeneralManagerAward> generalManagerAwardCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "award")
    private Collection<CoachAwards> coachAwardsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "award")
    private Collection<PlayerAward> playerAwardCollection;

    public Award() {
    }

    public Award(Short id) {
        this.id = id;
    }

    public Award(Short id, String description, short starPointsAwarded) {
        this.id = id;
        this.description = description;
        this.starPointsAwarded = starPointsAwarded;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getStarPointsAwarded() {
        return starPointsAwarded;
    }

    public void setStarPointsAwarded(short starPointsAwarded) {
        this.starPointsAwarded = starPointsAwarded;
    }

    @XmlTransient
    public Collection<GeneralManagerAward> getGeneralManagerAwardCollection() {
        return generalManagerAwardCollection;
    }

    public void setGeneralManagerAwardCollection(Collection<GeneralManagerAward> generalManagerAwardCollection) {
        this.generalManagerAwardCollection = generalManagerAwardCollection;
    }

    @XmlTransient
    public Collection<CoachAwards> getCoachAwardsCollection() {
        return coachAwardsCollection;
    }

    public void setCoachAwardsCollection(Collection<CoachAwards> coachAwardsCollection) {
        this.coachAwardsCollection = coachAwardsCollection;
    }

    @XmlTransient
    public Collection<PlayerAward> getPlayerAwardCollection() {
        return playerAwardCollection;
    }

    public void setPlayerAwardCollection(Collection<PlayerAward> playerAwardCollection) {
        this.playerAwardCollection = playerAwardCollection;
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
        if (!(object instanceof Award)) {
            return false;
        }
        Award other = (Award) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Award[ id=" + id + " ]";
    }

} // end class Award
