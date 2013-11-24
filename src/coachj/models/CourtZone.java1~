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
@Table(name = "court_zone")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourtZone.findAll", query = "SELECT c FROM CourtZone c"),
    @NamedQuery(name = "CourtZone.findById", query = "SELECT c FROM CourtZone c WHERE c.id = :id"),
    @NamedQuery(name = "CourtZone.findByDescriptionPt", query = "SELECT c FROM CourtZone c WHERE c.descriptionPt = :descriptionPt"),
    @NamedQuery(name = "CourtZone.findByDescriptionEn", query = "SELECT c FROM CourtZone c WHERE c.descriptionEn = :descriptionEn")})
public class CourtZone implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "description_pt")
    private String descriptionPt;
    @Basic(optional = false)
    @Column(name = "description_en")
    private String descriptionEn;
    @OneToMany(mappedBy = "favoriteCourtZone")
    private Collection<Player> playerCollection;

    public CourtZone() {
    }

    public CourtZone(Integer id) {
        this.id = id;
    }

    public CourtZone(Integer id, String descriptionPt, String descriptionEn) {
        this.id = id;
        this.descriptionPt = descriptionPt;
        this.descriptionEn = descriptionEn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        if (!(object instanceof CourtZone)) {
            return false;
        }
        CourtZone other = (CourtZone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.CourtZone[ id=" + id + " ]";
    }
    
}
