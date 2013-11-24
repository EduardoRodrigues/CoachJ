/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj.models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
 * @author Eduardo
 */
@Entity
@Table(name = "event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findById", query = "SELECT e FROM Event e WHERE e.id = :id"),
    @NamedQuery(name = "Event.findByDescriptionPt", query = "SELECT e FROM Event e WHERE e.descriptionPt = :descriptionPt"),
    @NamedQuery(name = "Event.findByDescriptionEn", query = "SELECT e FROM Event e WHERE e.descriptionEn = :descriptionEn"),
    @NamedQuery(name = "Event.findByType", query = "SELECT e FROM Event e WHERE e.type = :type"),
    @NamedQuery(name = "Event.findByCanPlay", query = "SELECT e FROM Event e WHERE e.canPlay = :canPlay"),
    @NamedQuery(name = "Event.findByMinimumImpact", query = "SELECT e FROM Event e WHERE e.minimumImpact = :minimumImpact"),
    @NamedQuery(name = "Event.findByMaximumImpact", query = "SELECT e FROM Event e WHERE e.maximumImpact = :maximumImpact"),
    @NamedQuery(name = "Event.findByMinimumDays", query = "SELECT e FROM Event e WHERE e.minimumDays = :minimumDays"),
    @NamedQuery(name = "Event.findByMaximumDays", query = "SELECT e FROM Event e WHERE e.maximumDays = :maximumDays"),
    @NamedQuery(name = "Event.findByGamesOut", query = "SELECT e FROM Event e WHERE e.gamesOut = :gamesOut")})
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @Column(name = "description_pt")
    private String descriptionPt;
    @Basic(optional = false)
    @Column(name = "description_en")
    private String descriptionEn;
    @Basic(optional = false)
    @Column(name = "type")
    private char type;
    @Basic(optional = false)
    @Column(name = "canPlay")
    private short canPlay;
    @Basic(optional = false)
    @Column(name = "minimumImpact")
    private short minimumImpact;
    @Basic(optional = false)
    @Column(name = "maximumImpact")
    private short maximumImpact;
    @Basic(optional = false)
    @Column(name = "minimumDays")
    private short minimumDays;
    @Basic(optional = false)
    @Column(name = "maximumDays")
    private short maximumDays;
    @Basic(optional = false)
    @Column(name = "gamesOut")
    private short gamesOut;
    @OneToMany(mappedBy = "event")
    private Collection<PlayerLog> playerLogCollection;
    @OneToMany(mappedBy = "event")
    private Collection<Player> playerCollection;

    public Event() {
    }

    public Event(Short id) {
        this.id = id;
    }

    public Event(Short id, String descriptionPt, String descriptionEn, char type, short canPlay, short minimumImpact, short maximumImpact, short minimumDays, short maximumDays, short gamesOut) {
        this.id = id;
        this.descriptionPt = descriptionPt;
        this.descriptionEn = descriptionEn;
        this.type = type;
        this.canPlay = canPlay;
        this.minimumImpact = minimumImpact;
        this.maximumImpact = maximumImpact;
        this.minimumDays = minimumDays;
        this.maximumDays = maximumDays;
        this.gamesOut = gamesOut;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getDescriptionPt() {
        return descriptionPt;
    }

    public void setDescriptionPt(String descriptionPt) {
        this.descriptionPt = descriptionPt;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public short getCanPlay() {
        return canPlay;
    }

    public void setCanPlay(short canPlay) {
        this.canPlay = canPlay;
    }

    public short getMinimumImpact() {
        return minimumImpact;
    }

    public void setMinimumImpact(short minimumImpact) {
        this.minimumImpact = minimumImpact;
    }

    public short getMaximumImpact() {
        return maximumImpact;
    }

    public void setMaximumImpact(short maximumImpact) {
        this.maximumImpact = maximumImpact;
    }

    public short getMinimumDays() {
        return minimumDays;
    }

    public void setMinimumDays(short minimumDays) {
        this.minimumDays = minimumDays;
    }

    public short getMaximumDays() {
        return maximumDays;
    }

    public void setMaximumDays(short maximumDays) {
        this.maximumDays = maximumDays;
    }

    public short getGamesOut() {
        return gamesOut;
    }

    public void setGamesOut(short gamesOut) {
        this.gamesOut = gamesOut;
    }

    @XmlTransient
    public Collection<PlayerLog> getPlayerLogCollection() {
        return playerLogCollection;
    }

    public void setPlayerLogCollection(Collection<PlayerLog> playerLogCollection) {
        this.playerLogCollection = playerLogCollection;
    }

    @XmlTransient
    public Collection<Player> getPlayerCollection() {
        return playerCollection;
    }

    public void setPlayerCollection(Collection<Player> playerCollection) {
        this.playerCollection = playerCollection;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Event[ id=" + id + " ]";
    }
    
}
