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
 * @author Eduardo
 */
@Entity
@Table(name = "referee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Referee.findAll", query = "SELECT r FROM Referee r"),
    @NamedQuery(name = "Referee.findById", query = "SELECT r FROM Referee r WHERE r.id = :id"),
    @NamedQuery(name = "Referee.findByFirstName", query = "SELECT r FROM Referee r WHERE r.firstName = :firstName"),
    @NamedQuery(name = "Referee.findByLastName", query = "SELECT r FROM Referee r WHERE r.lastName = :lastName"),
    @NamedQuery(name = "Referee.findByAge", query = "SELECT r FROM Referee r WHERE r.age = :age"),
    @NamedQuery(name = "Referee.findByRetirementAge", query = "SELECT r FROM Referee r WHERE r.retirementAge = :retirementAge"),
    @NamedQuery(name = "Referee.findByRetired", query = "SELECT r FROM Referee r WHERE r.retired = :retired"),
    @NamedQuery(name = "Referee.findByShootingFouls", query = "SELECT r FROM Referee r WHERE r.shootingFouls = :shootingFouls"),
    @NamedQuery(name = "Referee.findByChargingFouls", query = "SELECT r FROM Referee r WHERE r.chargingFouls = :chargingFouls"),
    @NamedQuery(name = "Referee.findByReachingFouls", query = "SELECT r FROM Referee r WHERE r.reachingFouls = :reachingFouls"),
    @NamedQuery(name = "Referee.findByTechnicalFouls", query = "SELECT r FROM Referee r WHERE r.technicalFouls = :technicalFouls"),
    @NamedQuery(name = "Referee.findByBlockingFouls", query = "SELECT r FROM Referee r WHERE r.blockingFouls = :blockingFouls")})
public class Referee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @Column(name = "firstName")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "lastName")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "age")
    private short age;
    @Basic(optional = false)
    @Column(name = "retirementAge")
    private short retirementAge;
    @Basic(optional = false)
    @Column(name = "retired")
    private boolean retired;
    @Basic(optional = false)
    @Column(name = "shootingFouls")
    private short shootingFouls;
    @Basic(optional = false)
    @Column(name = "chargingFouls")
    private short chargingFouls;
    @Basic(optional = false)
    @Column(name = "reachingFouls")
    private short reachingFouls;
    @Basic(optional = false)
    @Column(name = "technicalFouls")
    private short technicalFouls;
    @Basic(optional = false)
    @Column(name = "blockingFouls")
    private short blockingFouls;
    @OneToMany(mappedBy = "referee")
    private Collection<Game> gameCollection;
    @JoinColumn(name = "country", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Country country;

    public Referee() {
    }

    public Referee(Short id) {
        this.id = id;
    }

    public Referee(Short id, String firstName, String lastName, short age, short retirementAge, boolean retired, short shootingFouls, short chargingFouls, short reachingFouls, short technicalFouls, short blockingFouls) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.retirementAge = retirementAge;
        this.retired = retired;
        this.shootingFouls = shootingFouls;
        this.chargingFouls = chargingFouls;
        this.reachingFouls = reachingFouls;
        this.technicalFouls = technicalFouls;
        this.blockingFouls = blockingFouls;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public short getRetirementAge() {
        return retirementAge;
    }

    public void setRetirementAge(short retirementAge) {
        this.retirementAge = retirementAge;
    }

    public boolean getRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public short getShootingFouls() {
        return shootingFouls;
    }

    public void setShootingFouls(short shootingFouls) {
        this.shootingFouls = shootingFouls;
    }

    public short getChargingFouls() {
        return chargingFouls;
    }

    public void setChargingFouls(short chargingFouls) {
        this.chargingFouls = chargingFouls;
    }

    public short getReachingFouls() {
        return reachingFouls;
    }

    public void setReachingFouls(short reachingFouls) {
        this.reachingFouls = reachingFouls;
    }

    public short getTechnicalFouls() {
        return technicalFouls;
    }

    public void setTechnicalFouls(short technicalFouls) {
        this.technicalFouls = technicalFouls;
    }

    public short getBlockingFouls() {
        return blockingFouls;
    }

    public void setBlockingFouls(short blockingFouls) {
        this.blockingFouls = blockingFouls;
    }

    @XmlTransient
    public Collection<Game> getGameCollection() {
        return gameCollection;
    }

    public void setGameCollection(Collection<Game> gameCollection) {
        this.gameCollection = gameCollection;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        if (!(object instanceof Referee)) {
            return false;
        }
        Referee other = (Referee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Referee[ id=" + id + " ]";
    }
    
}
