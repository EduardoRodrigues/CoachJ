/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coachj.models;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo
 */
@Entity
@Table(name = "draft")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Draft.findAll", query = "SELECT d FROM Draft d"),
    @NamedQuery(name = "Draft.findById", query = "SELECT d FROM Draft d WHERE d.id = :id"),
    @NamedQuery(name = "Draft.findByRound", query = "SELECT d FROM Draft d WHERE d.round = :round"),
    @NamedQuery(name = "Draft.findByPick", query = "SELECT d FROM Draft d WHERE d.pick = :pick")})
public class Draft implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "round")
    private short round;
    @Basic(optional = false)
    @Column(name = "pick")
    private short pick;
    @JoinColumn(name = "season", referencedColumnName = "year")
    @ManyToOne(optional = false)
    private Season season;
    @JoinColumn(name = "player", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player player;
    @JoinColumn(name = "franchise", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Franchise franchise;

    public Draft() {
    }

    public Draft(Integer id) {
        this.id = id;
    }

    public Draft(Integer id, short round, short pick) {
        this.id = id;
        this.round = round;
        this.pick = pick;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getRound() {
        return round;
    }

    public void setRound(short round) {
        this.round = round;
    }

    public short getPick() {
        return pick;
    }

    public void setPick(short pick) {
        this.pick = pick;
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

    public Franchise getFranchise() {
        return franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
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
        if (!(object instanceof Draft)) {
            return false;
        }
        Draft other = (Draft) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Draft[ id=" + id + " ]";
    }
    
}
