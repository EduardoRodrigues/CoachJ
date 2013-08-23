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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0 /2012
 */
@Entity
@Table(name = "general_manager")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeneralManager.findAll", query = "SELECT g FROM GeneralManager g"),
    @NamedQuery(name = "GeneralManager.findById", query = "SELECT g FROM GeneralManager g WHERE g.id = :id"),
    @NamedQuery(name = "GeneralManager.findByFirstName", query = "SELECT g FROM GeneralManager g WHERE g.firstName = :firstName"),
    @NamedQuery(name = "GeneralManager.findByLastName", query = "SELECT g FROM GeneralManager g WHERE g.lastName = :lastName"),
    @NamedQuery(name = "GeneralManager.findByAge", query = "SELECT g FROM GeneralManager g WHERE g.age = :age"),
    @NamedQuery(name = "GeneralManager.findByRetirementAge", query = "SELECT g FROM GeneralManager g WHERE g.retirementAge = :retirementAge"),
    @NamedQuery(name = "GeneralManager.findByFailedContractAttempts", query = "SELECT g FROM GeneralManager g WHERE g.failedContractAttempts = :failedContractAttempts"),
    @NamedQuery(name = "GeneralManager.findByRemainingYears", query = "SELECT g FROM GeneralManager g WHERE g.remainingYears = :remainingYears"),
    @NamedQuery(name = "GeneralManager.findByRetired", query = "SELECT g FROM GeneralManager g WHERE g.retired = :retired"),
    @NamedQuery(name = "GeneralManager.findByMaximumBadSeasons", query = "SELECT g FROM GeneralManager g WHERE g.maximumBadSeasons = :maximumBadSeasons"),
    @NamedQuery(name = "GeneralManager.findByDealingStrategy", query = "SELECT g FROM GeneralManager g WHERE g.dealingStrategy = :dealingStrategy"),
    @NamedQuery(name = "GeneralManager.findByGreed", query = "SELECT g FROM GeneralManager g WHERE g.greed = :greed"),
    @NamedQuery(name = "GeneralManager.findBySeasons", query = "SELECT g FROM GeneralManager g WHERE g.seasons = :seasons"),
    @NamedQuery(name = "GeneralManager.findBySalary", query = "SELECT g FROM GeneralManager g WHERE g.salary = :salary"),
    @NamedQuery(name = "GeneralManager.findByTotalEarnings", query = "SELECT g FROM GeneralManager g WHERE g.totalEarnings = :totalEarnings"),
    @NamedQuery(name = "GeneralManager.findByMarketValue", query = "SELECT g FROM GeneralManager g WHERE g.marketValue = :marketValue"),
    @NamedQuery(name = "GeneralManager.findByStarPoints", query = "SELECT g FROM GeneralManager g WHERE g.starPoints = :starPoints"),
    @NamedQuery(name = "GeneralManager.findByTitles", query = "SELECT g FROM GeneralManager g WHERE g.titles = :titles"),
    @NamedQuery(name = "GeneralManager.findByJobStabilityIndex", query = "SELECT g FROM GeneralManager g WHERE g.jobStabilityIndex = :jobStabilityIndex")})
public class GeneralManager implements Serializable {
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
    @Column(name = "failedContractAttempts")
    private short failedContractAttempts;
    @Basic(optional = false)
    @Column(name = "remainingYears")
    private short remainingYears;
    @Basic(optional = false)
    @Column(name = "retired")
    private boolean retired;
    @Basic(optional = false)
    @Column(name = "maximumBadSeasons")
    private short maximumBadSeasons;
    @Basic(optional = false)
    @Column(name = "dealingStrategy")
    private short dealingStrategy;
    @Basic(optional = false)
    @Column(name = "greed")
    private short greed;
    @Basic(optional = false)
    @Column(name = "seasons")
    private short seasons;
    @Basic(optional = false)
    @Column(name = "salary")
    private int salary;
    @Basic(optional = false)
    @Column(name = "totalEarnings")
    private int totalEarnings;
    @Basic(optional = false)
    @Column(name = "marketValue")
    private short marketValue;
    @Basic(optional = false)
    @Column(name = "starPoints")
    private short starPoints;
    @Basic(optional = false)
    @Column(name = "titles")
    private short titles;
    @Basic(optional = false)
    @Column(name = "jobStabilityIndex")
    private short jobStabilityIndex;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalManager")
    private Collection<GeneralManagerTransaction> generalManagerTransactionCollection;
    @OneToOne(mappedBy = "generalManager")
    private Franchise franchise;
    @JoinColumn(name = "country", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Country country;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generalManager")
    private Collection<GeneralManagerAward> generalManagerAwardCollection;

    public GeneralManager() {
    }

    public GeneralManager(Short id) {
        this.id = id;
    }

    public GeneralManager(Short id, String firstName, String lastName, short age, short retirementAge, short failedContractAttempts, short remainingYears, boolean retired, short maximumBadSeasons, short dealingStrategy, short greed, short seasons, int salary, int totalEarnings, short marketValue, short starPoints, short titles, short jobStabilityIndex) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.retirementAge = retirementAge;
        this.failedContractAttempts = failedContractAttempts;
        this.remainingYears = remainingYears;
        this.retired = retired;
        this.maximumBadSeasons = maximumBadSeasons;
        this.dealingStrategy = dealingStrategy;
        this.greed = greed;
        this.seasons = seasons;
        this.salary = salary;
        this.totalEarnings = totalEarnings;
        this.marketValue = marketValue;
        this.starPoints = starPoints;
        this.titles = titles;
        this.jobStabilityIndex = jobStabilityIndex;
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

    public short getFailedContractAttempts() {
        return failedContractAttempts;
    }

    public void setFailedContractAttempts(short failedContractAttempts) {
        this.failedContractAttempts = failedContractAttempts;
    }

    public short getRemainingYears() {
        return remainingYears;
    }

    public void setRemainingYears(short remainingYears) {
        this.remainingYears = remainingYears;
    }

    public boolean getRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public short getMaximumBadSeasons() {
        return maximumBadSeasons;
    }

    public void setMaximumBadSeasons(short maximumBadSeasons) {
        this.maximumBadSeasons = maximumBadSeasons;
    }

    public short getDealingStrategy() {
        return dealingStrategy;
    }

    public void setDealingStrategy(short dealingStrategy) {
        this.dealingStrategy = dealingStrategy;
    }

    public short getGreed() {
        return greed;
    }

    public void setGreed(short greed) {
        this.greed = greed;
    }

    public short getSeasons() {
        return seasons;
    }

    public void setSeasons(short seasons) {
        this.seasons = seasons;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(int totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public short getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(short marketValue) {
        this.marketValue = marketValue;
    }

    public short getStarPoints() {
        return starPoints;
    }

    public void setStarPoints(short starPoints) {
        this.starPoints = starPoints;
    }

    public short getTitles() {
        return titles;
    }

    public void setTitles(short titles) {
        this.titles = titles;
    }

    public short getJobStabilityIndex() {
        return jobStabilityIndex;
    }

    public void setJobStabilityIndex(short jobStabilityIndex) {
        this.jobStabilityIndex = jobStabilityIndex;
    }

    @XmlTransient
    public Collection<GeneralManagerTransaction> getGeneralManagerTransactionCollection() {
        return generalManagerTransactionCollection;
    }

    public void setGeneralManagerTransactionCollection(Collection<GeneralManagerTransaction> generalManagerTransactionCollection) {
        this.generalManagerTransactionCollection = generalManagerTransactionCollection;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @XmlTransient
    public Collection<GeneralManagerAward> getGeneralManagerAwardCollection() {
        return generalManagerAwardCollection;
    }

    public void setGeneralManagerAwardCollection(Collection<GeneralManagerAward> generalManagerAwardCollection) {
        this.generalManagerAwardCollection = generalManagerAwardCollection;
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
        if (!(object instanceof GeneralManager)) {
            return false;
        }
        GeneralManager other = (GeneralManager) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.GeneralManager[ id=" + id + " ]";
    }

} // end class GeneralManager
