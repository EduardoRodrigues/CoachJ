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
@Table(name = "country")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c"),
    @NamedQuery(name = "Country.findById", query = "SELECT c FROM Country c WHERE c.id = :id"),
    @NamedQuery(name = "Country.findByName", query = "SELECT c FROM Country c WHERE c.name = :name"),
    @NamedQuery(name = "Country.findByAbbreviature", query = "SELECT c FROM Country c WHERE c.abbreviature = :abbreviature")})
public class Country implements Serializable {
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
    @Column(name = "abbreviature")
    private String abbreviature;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private Collection<Player> playerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private Collection<City> cityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private Collection<FirstName> firstNameCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private Collection<Coach> coachCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private Collection<GeneralManager> generalManagerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private Collection<Referee> refereeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private Collection<LastName> lastNameCollection;

    public Country() {
    }

    public Country(Short id) {
        this.id = id;
    }

    public Country(Short id, String name, String abbreviature) {
        this.id = id;
        this.name = name;
        this.abbreviature = abbreviature;
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

    public String getAbbreviature() {
        return abbreviature;
    }

    public void setAbbreviature(String abbreviature) {
        this.abbreviature = abbreviature;
    }

    @XmlTransient
    public Collection<Player> getPlayerCollection() {
        return playerCollection;
    }

    public void setPlayerCollection(Collection<Player> playerCollection) {
        this.playerCollection = playerCollection;
    }

    @XmlTransient
    public Collection<City> getCityCollection() {
        return cityCollection;
    }

    public void setCityCollection(Collection<City> cityCollection) {
        this.cityCollection = cityCollection;
    }

    @XmlTransient
    public Collection<FirstName> getFirstNameCollection() {
        return firstNameCollection;
    }

    public void setFirstNameCollection(Collection<FirstName> firstNameCollection) {
        this.firstNameCollection = firstNameCollection;
    }

    @XmlTransient
    public Collection<Coach> getCoachCollection() {
        return coachCollection;
    }

    public void setCoachCollection(Collection<Coach> coachCollection) {
        this.coachCollection = coachCollection;
    }

    @XmlTransient
    public Collection<GeneralManager> getGeneralManagerCollection() {
        return generalManagerCollection;
    }

    public void setGeneralManagerCollection(Collection<GeneralManager> generalManagerCollection) {
        this.generalManagerCollection = generalManagerCollection;
    }

    @XmlTransient
    public Collection<Referee> getRefereeCollection() {
        return refereeCollection;
    }

    public void setRefereeCollection(Collection<Referee> refereeCollection) {
        this.refereeCollection = refereeCollection;
    }

    @XmlTransient
    public Collection<LastName> getLastNameCollection() {
        return lastNameCollection;
    }

    public void setLastNameCollection(Collection<LastName> lastNameCollection) {
        this.lastNameCollection = lastNameCollection;
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
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Country[ id=" + id + " ]";
    }

} // end class Country
