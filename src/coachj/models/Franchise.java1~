/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj.models;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "franchise")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Franchise.findAll", query = "SELECT f FROM Franchise f"),
    @NamedQuery(name = "Franchise.findById", query = "SELECT f FROM Franchise f WHERE f.id = :id"),
    @NamedQuery(name = "Franchise.findByTeam", query = "SELECT f FROM Franchise f WHERE f.team = :team"),
    @NamedQuery(name = "Franchise.findByAbbreviature", query = "SELECT f FROM Franchise f WHERE f.abbreviature = :abbreviature"),
    @NamedQuery(name = "Franchise.findByRegistered", query = "SELECT f FROM Franchise f WHERE f.registered = :registered"),
    @NamedQuery(name = "Franchise.findByAssets", query = "SELECT f FROM Franchise f WHERE f.assets = :assets"),
    @NamedQuery(name = "Franchise.findByTicketHolders", query = "SELECT f FROM Franchise f WHERE f.ticketHolders = :ticketHolders"),
    @NamedQuery(name = "Franchise.findBySeed", query = "SELECT f FROM Franchise f WHERE f.seed = :seed"),
    @NamedQuery(name = "Franchise.findByHomeWins", query = "SELECT f FROM Franchise f WHERE f.homeWins = :homeWins"),
    @NamedQuery(name = "Franchise.findByHomeLosses", query = "SELECT f FROM Franchise f WHERE f.homeLosses = :homeLosses"),
    @NamedQuery(name = "Franchise.findByAwayWins", query = "SELECT f FROM Franchise f WHERE f.awayWins = :awayWins"),
    @NamedQuery(name = "Franchise.findByAwayLosses", query = "SELECT f FROM Franchise f WHERE f.awayLosses = :awayLosses"),
    @NamedQuery(name = "Franchise.findByRecord", query = "SELECT f FROM Franchise f WHERE f.record = :record"),
    @NamedQuery(name = "Franchise.findByLast10", query = "SELECT f FROM Franchise f WHERE f.last10 = :last10"),
    @NamedQuery(name = "Franchise.findByStreak", query = "SELECT f FROM Franchise f WHERE f.streak = :streak"),
    @NamedQuery(name = "Franchise.findByTiebreaker", query = "SELECT f FROM Franchise f WHERE f.tiebreaker = :tiebreaker"),
    @NamedQuery(name = "Franchise.findByTitles", query = "SELECT f FROM Franchise f WHERE f.titles = :titles")})
public class Franchise implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @Column(name = "team")
    private String team;
    @Basic(optional = false)
    @Column(name = "abbreviature")
    private String abbreviature;
    @Basic(optional = false)
    @Column(name = "registered")
    private boolean registered;
    @Basic(optional = false)
    @Column(name = "assets")
    private long assets;
    @Basic(optional = false)
    @Column(name = "ticketHolders")
    private int ticketHolders;
    @Basic(optional = false)
    @Column(name = "seed")
    private short seed;
    @Basic(optional = false)
    @Column(name = "homeWins")
    private short homeWins;
    @Basic(optional = false)
    @Column(name = "homeLosses")
    private short homeLosses;
    @Basic(optional = false)
    @Column(name = "awayWins")
    private short awayWins;
    @Basic(optional = false)
    @Column(name = "awayLosses")
    private short awayLosses;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "record")
    private BigDecimal record;
    @Basic(optional = false)
    @Column(name = "last10")
    private String last10;
    @Basic(optional = false)
    @Column(name = "streak")
    private String streak;
    @Basic(optional = false)
    @Column(name = "tiebreaker")
    private short tiebreaker;
    @Basic(optional = false)
    @Column(name = "titles")
    private short titles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    private Collection<PlayerLog> playerLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "opponent")
    private Collection<PlayerLog> playerLogCollection1;
    @OneToMany(mappedBy = "franchise")
    private Collection<Player> playerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relatedFranchise2")
    private Collection<GameNews> gameNewsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relatedFranchise1")
    private Collection<GameNews> gameNewsCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "franchise")
    private Collection<Title> titleCollection;
    @OneToMany(mappedBy = "franchise")
    private Collection<GeneralManagerTransaction> generalManagerTransactionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "franchise")
    private Collection<CoachTransaction> coachTransactionCollection;
    @JoinColumn(name = "generalManager", referencedColumnName = "id")
    @OneToOne
    private GeneralManager generalManager;
    @JoinColumn(name = "coach", referencedColumnName = "id")
    @OneToOne
    private Coach coach;
    @JoinColumn(name = "city", referencedColumnName = "id")
    @ManyToOne
    private City city;
    @JoinColumn(name = "arena", referencedColumnName = "id")
    @ManyToOne
    private Arena arena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "homeTeam")
    private Collection<Game> gameCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "awayTeam")
    private Collection<Game> gameCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lowerSeedTeam")
    private Collection<PlayoffSeries> playoffSeriesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "upperSeedTeam")
    private Collection<PlayoffSeries> playoffSeriesCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "franchise")
    private Collection<Draft> draftCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "franchise")
    private Collection<FranchiseSeasonLog> franchiseSeasonLogCollection;
    @OneToMany(mappedBy = "sourceFranchise")
    private Collection<PlayerTransaction> playerTransactionCollection;
    @OneToMany(mappedBy = "destinationFranchise")
    private Collection<PlayerTransaction> playerTransactionCollection1;

    public Franchise() {
    }

    public Franchise(Short id) {
        this.id = id;
    }

    public Franchise(Short id, String team, String abbreviature, boolean registered, long assets, int ticketHolders, short seed, short homeWins, short homeLosses, short awayWins, short awayLosses, BigDecimal record, String last10, String streak, short tiebreaker, short titles) {
        this.id = id;
        this.team = team;
        this.abbreviature = abbreviature;
        this.registered = registered;
        this.assets = assets;
        this.ticketHolders = ticketHolders;
        this.seed = seed;
        this.homeWins = homeWins;
        this.homeLosses = homeLosses;
        this.awayWins = awayWins;
        this.awayLosses = awayLosses;
        this.record = record;
        this.last10 = last10;
        this.streak = streak;
        this.tiebreaker = tiebreaker;
        this.titles = titles;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getAbbreviature() {
        return abbreviature;
    }

    public void setAbbreviature(String abbreviature) {
        this.abbreviature = abbreviature;
    }

    public boolean getRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public long getAssets() {
        return assets;
    }

    public void setAssets(long assets) {
        this.assets = assets;
    }

    public int getTicketHolders() {
        return ticketHolders;
    }

    public void setTicketHolders(int ticketHolders) {
        this.ticketHolders = ticketHolders;
    }

    public short getSeed() {
        return seed;
    }

    public void setSeed(short seed) {
        this.seed = seed;
    }

    public short getHomeWins() {
        return homeWins;
    }

    public void setHomeWins(short homeWins) {
        this.homeWins = homeWins;
    }

    public short getHomeLosses() {
        return homeLosses;
    }

    public void setHomeLosses(short homeLosses) {
        this.homeLosses = homeLosses;
    }

    public short getAwayWins() {
        return awayWins;
    }

    public void setAwayWins(short awayWins) {
        this.awayWins = awayWins;
    }

    public short getAwayLosses() {
        return awayLosses;
    }

    public void setAwayLosses(short awayLosses) {
        this.awayLosses = awayLosses;
    }

    public BigDecimal getRecord() {
        return record;
    }

    public void setRecord(BigDecimal record) {
        this.record = record;
    }

    public String getLast10() {
        return last10;
    }

    public void setLast10(String last10) {
        this.last10 = last10;
    }

    public String getStreak() {
        return streak;
    }

    public void setStreak(String streak) {
        this.streak = streak;
    }

    public short getTiebreaker() {
        return tiebreaker;
    }

    public void setTiebreaker(short tiebreaker) {
        this.tiebreaker = tiebreaker;
    }

    public short getTitles() {
        return titles;
    }

    public void setTitles(short titles) {
        this.titles = titles;
    }

    @XmlTransient
    public Collection<PlayerLog> getPlayerLogCollection() {
        return playerLogCollection;
    }

    public void setPlayerLogCollection(Collection<PlayerLog> playerLogCollection) {
        this.playerLogCollection = playerLogCollection;
    }

    @XmlTransient
    public Collection<PlayerLog> getPlayerLogCollection1() {
        return playerLogCollection1;
    }

    public void setPlayerLogCollection1(Collection<PlayerLog> playerLogCollection1) {
        this.playerLogCollection1 = playerLogCollection1;
    }

    @XmlTransient
    public Collection<Player> getPlayerCollection() {
        return playerCollection;
    }

    public void setPlayerCollection(Collection<Player> playerCollection) {
        this.playerCollection = playerCollection;
    }

    @XmlTransient
    public Collection<GameNews> getGameNewsCollection() {
        return gameNewsCollection;
    }

    public void setGameNewsCollection(Collection<GameNews> gameNewsCollection) {
        this.gameNewsCollection = gameNewsCollection;
    }

    @XmlTransient
    public Collection<GameNews> getGameNewsCollection1() {
        return gameNewsCollection1;
    }

    public void setGameNewsCollection1(Collection<GameNews> gameNewsCollection1) {
        this.gameNewsCollection1 = gameNewsCollection1;
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

    public GeneralManager getGeneralManager() {
        return generalManager;
    }

    public void setGeneralManager(GeneralManager generalManager) {
        this.generalManager = generalManager;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
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
    public Collection<PlayoffSeries> getPlayoffSeriesCollection() {
        return playoffSeriesCollection;
    }

    public void setPlayoffSeriesCollection(Collection<PlayoffSeries> playoffSeriesCollection) {
        this.playoffSeriesCollection = playoffSeriesCollection;
    }

    @XmlTransient
    public Collection<PlayoffSeries> getPlayoffSeriesCollection1() {
        return playoffSeriesCollection1;
    }

    public void setPlayoffSeriesCollection1(Collection<PlayoffSeries> playoffSeriesCollection1) {
        this.playoffSeriesCollection1 = playoffSeriesCollection1;
    }

    @XmlTransient
    public Collection<Draft> getDraftCollection() {
        return draftCollection;
    }

    public void setDraftCollection(Collection<Draft> draftCollection) {
        this.draftCollection = draftCollection;
    }

    @XmlTransient
    public Collection<FranchiseSeasonLog> getFranchiseSeasonLogCollection() {
        return franchiseSeasonLogCollection;
    }

    public void setFranchiseSeasonLogCollection(Collection<FranchiseSeasonLog> franchiseSeasonLogCollection) {
        this.franchiseSeasonLogCollection = franchiseSeasonLogCollection;
    }

    @XmlTransient
    public Collection<PlayerTransaction> getPlayerTransactionCollection() {
        return playerTransactionCollection;
    }

    public void setPlayerTransactionCollection(Collection<PlayerTransaction> playerTransactionCollection) {
        this.playerTransactionCollection = playerTransactionCollection;
    }

    @XmlTransient
    public Collection<PlayerTransaction> getPlayerTransactionCollection1() {
        return playerTransactionCollection1;
    }

    public void setPlayerTransactionCollection1(Collection<PlayerTransaction> playerTransactionCollection1) {
        this.playerTransactionCollection1 = playerTransactionCollection1;
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
        if (!(object instanceof Franchise)) {
            return false;
        }
        Franchise other = (Franchise) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Franchise[ id=" + id + " ]";
    }
    
}
