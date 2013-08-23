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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "arena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Arena.findAll", query = "SELECT a FROM Arena a"),
    @NamedQuery(name = "Arena.findById", query = "SELECT a FROM Arena a WHERE a.id = :id"),
    @NamedQuery(name = "Arena.findByName", query = "SELECT a FROM Arena a WHERE a.name = :name"),
    @NamedQuery(name = "Arena.findByCapacity", query = "SELECT a FROM Arena a WHERE a.capacity = :capacity"),
    @NamedQuery(name = "Arena.findBySeatMaintenanceCost", query = "SELECT a FROM Arena a WHERE a.seatMaintenanceCost = :seatMaintenanceCost")})
public class Arena implements Serializable {
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
    @Column(name = "capacity")
    private short capacity;
    @Basic(optional = false)
    @Column(name = "seatMaintenanceCost")
    private short seatMaintenanceCost;
    @JoinColumn(name = "city", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private City city;
    @OneToMany(mappedBy = "arena")
    private Collection<Franchise> franchiseCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arena")
    private Collection<Game> gameCollection;

    public Arena() {
    }

    public Arena(Short id) {
        this.id = id;
    }

    public Arena(Short id, String name, short capacity, short seatMaintenanceCost) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.seatMaintenanceCost = seatMaintenanceCost;
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

    public short getCapacity() {
        return capacity;
    }

    public void setCapacity(short capacity) {
        this.capacity = capacity;
    }

    public short getSeatMaintenanceCost() {
        return seatMaintenanceCost;
    }

    public void setSeatMaintenanceCost(short seatMaintenanceCost) {
        this.seatMaintenanceCost = seatMaintenanceCost;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @XmlTransient
    public Collection<Franchise> getFranchiseCollection() {
        return franchiseCollection;
    }

    public void setFranchiseCollection(Collection<Franchise> franchiseCollection) {
        this.franchiseCollection = franchiseCollection;
    }

    @XmlTransient
    public Collection<Game> getGameCollection() {
        return gameCollection;
    }

    public void setGameCollection(Collection<Game> gameCollection) {
        this.gameCollection = gameCollection;
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
        if (!(object instanceof Arena)) {
            return false;
        }
        Arena other = (Arena) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Arena[ id=" + id + " ]";
    }

} // end class Arena
