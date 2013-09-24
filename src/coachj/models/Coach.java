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
 * @author Eduardo
 */
@Entity
@Table(name = "coach")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coach.findAll", query = "SELECT c FROM Coach c"),
    @NamedQuery(name = "Coach.findById", query = "SELECT c FROM Coach c WHERE c.id = :id"),
    @NamedQuery(name = "Coach.findByFirstName", query = "SELECT c FROM Coach c WHERE c.firstName = :firstName"),
    @NamedQuery(name = "Coach.findByLastName", query = "SELECT c FROM Coach c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "Coach.findByAge", query = "SELECT c FROM Coach c WHERE c.age = :age"),
    @NamedQuery(name = "Coach.findByRetired", query = "SELECT c FROM Coach c WHERE c.retired = :retired"),
    @NamedQuery(name = "Coach.findBySeasons", query = "SELECT c FROM Coach c WHERE c.seasons = :seasons"),
    @NamedQuery(name = "Coach.findByRetirementAge", query = "SELECT c FROM Coach c WHERE c.retirementAge = :retirementAge"),
    @NamedQuery(name = "Coach.findByRemainingYears", query = "SELECT c FROM Coach c WHERE c.remainingYears = :remainingYears"),
    @NamedQuery(name = "Coach.findByFailedContractAttempts", query = "SELECT c FROM Coach c WHERE c.failedContractAttempts = :failedContractAttempts"),
    @NamedQuery(name = "Coach.findBySalary", query = "SELECT c FROM Coach c WHERE c.salary = :salary"),
    @NamedQuery(name = "Coach.findByTotalEarnings", query = "SELECT c FROM Coach c WHERE c.totalEarnings = :totalEarnings"),
    @NamedQuery(name = "Coach.findByStarPoints", query = "SELECT c FROM Coach c WHERE c.starPoints = :starPoints"),
    @NamedQuery(name = "Coach.findByMarketValue", query = "SELECT c FROM Coach c WHERE c.marketValue = :marketValue"),
    @NamedQuery(name = "Coach.findByGreed", query = "SELECT c FROM Coach c WHERE c.greed = :greed"),
    @NamedQuery(name = "Coach.findByLoyalty", query = "SELECT c FROM Coach c WHERE c.loyalty = :loyalty"),
    @NamedQuery(name = "Coach.findByRotationUse", query = "SELECT c FROM Coach c WHERE c.rotationUse = :rotationUse"),
    @NamedQuery(name = "Coach.findByDraftMethod", query = "SELECT c FROM Coach c WHERE c.draftMethod = :draftMethod"),
    @NamedQuery(name = "Coach.findByPatience", query = "SELECT c FROM Coach c WHERE c.patience = :patience"),
    @NamedQuery(name = "Coach.findByTechnique", query = "SELECT c FROM Coach c WHERE c.technique = :technique"),
    @NamedQuery(name = "Coach.findByDiscipline", query = "SELECT c FROM Coach c WHERE c.discipline = :discipline"),
    @NamedQuery(name = "Coach.findByWorkEthic", query = "SELECT c FROM Coach c WHERE c.workEthic = :workEthic"),
    @NamedQuery(name = "Coach.findByShoot", query = "SELECT c FROM Coach c WHERE c.shoot = :shoot"),
    @NamedQuery(name = "Coach.findByPass", query = "SELECT c FROM Coach c WHERE c.pass = :pass"),
    @NamedQuery(name = "Coach.findByRebound", query = "SELECT c FROM Coach c WHERE c.rebound = :rebound"),
    @NamedQuery(name = "Coach.findByDefense", query = "SELECT c FROM Coach c WHERE c.defense = :defense"),
    @NamedQuery(name = "Coach.findByPhysicality", query = "SELECT c FROM Coach c WHERE c.physicality = :physicality"),
    @NamedQuery(name = "Coach.findByTempo", query = "SELECT c FROM Coach c WHERE c.tempo = :tempo"),
    @NamedQuery(name = "Coach.findByJobStabilityIndex", query = "SELECT c FROM Coach c WHERE c.jobStabilityIndex = :jobStabilityIndex"),
    @NamedQuery(name = "Coach.findByTitles", query = "SELECT c FROM Coach c WHERE c.titles = :titles")})
public class Coach implements Serializable {
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
    @Column(name = "retired")
    private boolean retired;
    @Basic(optional = false)
    @Column(name = "seasons")
    private short seasons;
    @Basic(optional = false)
    @Column(name = "retirementAge")
    private short retirementAge;
    @Basic(optional = false)
    @Column(name = "remainingYears")
    private short remainingYears;
    @Basic(optional = false)
    @Column(name = "failedContractAttempts")
    private short failedContractAttempts;
    @Basic(optional = false)
    @Column(name = "salary")
    private int salary;
    @Basic(optional = false)
    @Column(name = "totalEarnings")
    private int totalEarnings;
    @Basic(optional = false)
    @Column(name = "starPoints")
    private short starPoints;
    @Basic(optional = false)
    @Column(name = "marketValue")
    private short marketValue;
    @Basic(optional = false)
    @Column(name = "greed")
    private short greed;
    @Basic(optional = false)
    @Column(name = "loyalty")
    private short loyalty;
    @Basic(optional = false)
    @Column(name = "rotationUse")
    private short rotationUse;
    @Basic(optional = false)
    @Column(name = "draftMethod")
    private short draftMethod;
    @Basic(optional = false)
    @Column(name = "patience")
    private short patience;
    @Basic(optional = false)
    @Column(name = "technique")
    private short technique;
    @Basic(optional = false)
    @Column(name = "discipline")
    private short discipline;
    @Basic(optional = false)
    @Column(name = "workEthic")
    private short workEthic;
    @Basic(optional = false)
    @Column(name = "shoot")
    private short shoot;
    @Basic(optional = false)
    @Column(name = "pass")
    private short pass;
    @Basic(optional = false)
    @Column(name = "rebound")
    private short rebound;
    @Basic(optional = false)
    @Column(name = "defense")
    private short defense;
    @Basic(optional = false)
    @Column(name = "physicality")
    private short physicality;
    @Basic(optional = false)
    @Column(name = "tempo")
    private short tempo;
    @Basic(optional = false)
    @Column(name = "jobStabilityIndex")
    private short jobStabilityIndex;
    @Basic(optional = false)
    @Column(name = "titles")
    private short titles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coach")
    private Collection<CoachTransaction> coachTransactionCollection;
    @OneToOne(mappedBy = "coach")
    private Franchise franchise;
    @JoinColumn(name = "country", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Country country;
    @OneToMany(mappedBy = "homeTeamCoach")
    private Collection<Game> gameCollection;
    @OneToMany(mappedBy = "awayTeamCoach")
    private Collection<Game> gameCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coach")
    private Collection<CoachAwards> coachAwardsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coach")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection;

    public Coach() {
    }

    public Coach(Short id) {
        this.id = id;
    }

    public Coach(Short id, String firstName, String lastName, short age, boolean retired, short seasons, short retirementAge, short remainingYears, short failedContractAttempts, int salary, int totalEarnings, short starPoints, short marketValue, short greed, short loyalty, short rotationUse, short draftMethod, short patience, short technique, short discipline, short workEthic, short shoot, short pass, short rebound, short defense, short physicality, short tempo, short jobStabilityIndex, short titles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.retired = retired;
        this.seasons = seasons;
        this.retirementAge = retirementAge;
        this.remainingYears = remainingYears;
        this.failedContractAttempts = failedContractAttempts;
        this.salary = salary;
        this.totalEarnings = totalEarnings;
        this.starPoints = starPoints;
        this.marketValue = marketValue;
        this.greed = greed;
        this.loyalty = loyalty;
        this.rotationUse = rotationUse;
        this.draftMethod = draftMethod;
        this.patience = patience;
        this.technique = technique;
        this.discipline = discipline;
        this.workEthic = workEthic;
        this.shoot = shoot;
        this.pass = pass;
        this.rebound = rebound;
        this.defense = defense;
        this.physicality = physicality;
        this.tempo = tempo;
        this.jobStabilityIndex = jobStabilityIndex;
        this.titles = titles;
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

    public boolean getRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public short getSeasons() {
        return seasons;
    }

    public void setSeasons(short seasons) {
        this.seasons = seasons;
    }

    public short getRetirementAge() {
        return retirementAge;
    }

    public void setRetirementAge(short retirementAge) {
        this.retirementAge = retirementAge;
    }

    public short getRemainingYears() {
        return remainingYears;
    }

    public void setRemainingYears(short remainingYears) {
        this.remainingYears = remainingYears;
    }

    public short getFailedContractAttempts() {
        return failedContractAttempts;
    }

    public void setFailedContractAttempts(short failedContractAttempts) {
        this.failedContractAttempts = failedContractAttempts;
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

    public short getStarPoints() {
        return starPoints;
    }

    public void setStarPoints(short starPoints) {
        this.starPoints = starPoints;
    }

    public short getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(short marketValue) {
        this.marketValue = marketValue;
    }

    public short getGreed() {
        return greed;
    }

    public void setGreed(short greed) {
        this.greed = greed;
    }

    public short getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(short loyalty) {
        this.loyalty = loyalty;
    }

    public short getRotationUse() {
        return rotationUse;
    }

    public void setRotationUse(short rotationUse) {
        this.rotationUse = rotationUse;
    }

    public short getDraftMethod() {
        return draftMethod;
    }

    public void setDraftMethod(short draftMethod) {
        this.draftMethod = draftMethod;
    }

    public short getPatience() {
        return patience;
    }

    public void setPatience(short patience) {
        this.patience = patience;
    }

    public short getTechnique() {
        return technique;
    }

    public void setTechnique(short technique) {
        this.technique = technique;
    }

    public short getDiscipline() {
        return discipline;
    }

    public void setDiscipline(short discipline) {
        this.discipline = discipline;
    }

    public short getWorkEthic() {
        return workEthic;
    }

    public void setWorkEthic(short workEthic) {
        this.workEthic = workEthic;
    }

    public short getShoot() {
        return shoot;
    }

    public void setShoot(short shoot) {
        this.shoot = shoot;
    }

    public short getPass() {
        return pass;
    }

    public void setPass(short pass) {
        this.pass = pass;
    }

    public short getRebound() {
        return rebound;
    }

    public void setRebound(short rebound) {
        this.rebound = rebound;
    }

    public short getDefense() {
        return defense;
    }

    public void setDefense(short defense) {
        this.defense = defense;
    }

    public short getPhysicality() {
        return physicality;
    }

    public void setPhysicality(short physicality) {
        this.physicality = physicality;
    }

    public short getTempo() {
        return tempo;
    }

    public void setTempo(short tempo) {
        this.tempo = tempo;
    }

    public short getJobStabilityIndex() {
        return jobStabilityIndex;
    }

    public void setJobStabilityIndex(short jobStabilityIndex) {
        this.jobStabilityIndex = jobStabilityIndex;
    }

    public short getTitles() {
        return titles;
    }

    public void setTitles(short titles) {
        this.titles = titles;
    }

    @XmlTransient
    public Collection<CoachTransaction> getCoachTransactionCollection() {
        return coachTransactionCollection;
    }

    public void setCoachTransactionCollection(Collection<CoachTransaction> coachTransactionCollection) {
        this.coachTransactionCollection = coachTransactionCollection;
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
    public Collection<Game> getGameCollection() {
        return gameCollection;
    }

    public void setGameCollection(Collection<Game> gameCollection) {
        this.gameCollection = gameCollection;
    }

    @XmlTransient
    public Collection<Game> getGameCollection1() {
        return gameCollection1;
    }

    public void setGameCollection1(Collection<Game> gameCollection1) {
        this.gameCollection1 = gameCollection1;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coach)) {
            return false;
        }
        Coach other = (Coach) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Coach[ id=" + id + " ]";
    }
    
}
