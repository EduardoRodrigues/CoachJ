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
@Table(name = "play_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlayType.findAll", query = "SELECT p FROM PlayType p"),
    @NamedQuery(name = "PlayType.findById", query = "SELECT p FROM PlayType p WHERE p.id = :id"),
    @NamedQuery(name = "PlayType.findByType", query = "SELECT p FROM PlayType p WHERE p.type = :type")})
public class PlayType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playType")
    private Collection<Narration> narrationCollection;

    public PlayType() {
    }

    public PlayType(Integer id) {
        this.id = id;
    }

    public PlayType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<Narration> getNarrationCollection() {
        return narrationCollection;
    }

    public void setNarrationCollection(Collection<Narration> narrationCollection) {
        this.narrationCollection = narrationCollection;
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
        if (!(object instanceof PlayType)) {
            return false;
        }
        PlayType other = (PlayType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.PlayType[ id=" + id + " ]";
    }

} // end class PlayType
