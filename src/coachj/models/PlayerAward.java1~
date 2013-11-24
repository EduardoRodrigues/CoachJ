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
 * @author Eduardo
 */
@Entity
@Table(name = "player_award")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlayerAward.findAll", query = "SELECT p FROM PlayerAward p"),
    @NamedQuery(name = "PlayerAward.findById", query = "SELECT p FROM PlayerAward p WHERE p.id = :id"),
    @NamedQuery(name = "PlayerAward.findByDate", query = "SELECT p FROM PlayerAward p WHERE p.date = :date")})
public class PlayerAward implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "season", referencedColumnName = "year")
    @ManyToOne(optional = false)
    private Season season;
    @JoinColumn(name = "player", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Player player;
    @JoinColumn(name = "award", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Award award;

    public PlayerAward() {
    }

    public PlayerAward(Integer id) {
        this.id = id;
    }

    public PlayerAward(Integer id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
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
        if (!(object instanceof PlayerAward)) {
            return false;
        }
        PlayerAward other = (PlayerAward) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.PlayerAward[ id=" + id + " ]";
    }
    
}
