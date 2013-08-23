/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coachj.models;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0 /2012
 */
@Entity
@Table(name = "player_transaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlayerTransaction.findAll", query = "SELECT p FROM PlayerTransaction p"),
    @NamedQuery(name = "PlayerTransaction.findById", query = "SELECT p FROM PlayerTransaction p WHERE p.id = :id"),
    @NamedQuery(name = "PlayerTransaction.findByType", query = "SELECT p FROM PlayerTransaction p WHERE p.type = :type"),
    @NamedQuery(name = "PlayerTransaction.findByDate", query = "SELECT p FROM PlayerTransaction p WHERE p.date = :date"),
    @NamedQuery(name = "PlayerTransaction.findByContractLength", query = "SELECT p FROM PlayerTransaction p WHERE p.contractLength = :contractLength"),
    @NamedQuery(name = "PlayerTransaction.findBySalary", query = "SELECT p FROM PlayerTransaction p WHERE p.salary = :salary")})
public class PlayerTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "type")
    private char type;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "contractLength")
    private short contractLength;
    @Basic(optional = false)
    @Column(name = "salary")
    private int salary;
    @JoinColumn(name = "season", referencedColumnName = "year")
    @ManyToOne(optional = false)
    private Season season;
    @JoinColumn(name = "player", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player player;
    @JoinColumn(name = "sourceFranchise", referencedColumnName = "id")
    @ManyToOne
    private Franchise sourceFranchise;
    @JoinColumn(name = "destinationFranchise", referencedColumnName = "id")
    @ManyToOne
    private Franchise destinationFranchise;

    public PlayerTransaction() {
    }

    public PlayerTransaction(Integer id) {
        this.id = id;
    }

    public PlayerTransaction(Integer id, char type, Date date, short contractLength, int salary) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.contractLength = contractLength;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public short getContractLength() {
        return contractLength;
    }

    public void setContractLength(short contractLength) {
        this.contractLength = contractLength;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Franchise getSourceFranchise() {
        return sourceFranchise;
    }

    public void setSourceFranchise(Franchise sourceFranchise) {
        this.sourceFranchise = sourceFranchise;
    }

    public Franchise getDestinationFranchise() {
        return destinationFranchise;
    }

    public void setDestinationFranchise(Franchise destinationFranchise) {
        this.destinationFranchise = destinationFranchise;
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
        if (!(object instanceof PlayerTransaction)) {
            return false;
        }
        PlayerTransaction other = (PlayerTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.PlayerTransaction[ id=" + id + " ]";
    }

} // end class PlayerTransaction
