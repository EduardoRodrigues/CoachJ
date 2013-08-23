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
@Table(name = "general_manager_award")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeneralManagerAward.findAll", query = "SELECT g FROM GeneralManagerAward g"),
    @NamedQuery(name = "GeneralManagerAward.findById", query = "SELECT g FROM GeneralManagerAward g WHERE g.id = :id"),
    @NamedQuery(name = "GeneralManagerAward.findByDate", query = "SELECT g FROM GeneralManagerAward g WHERE g.date = :date")})
public class GeneralManagerAward implements Serializable {
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
    @JoinColumn(name = "award", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Award award;
    @JoinColumn(name = "generalManager", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GeneralManager generalManager;

    public GeneralManagerAward() {
    }

    public GeneralManagerAward(Integer id) {
        this.id = id;
    }

    public GeneralManagerAward(Integer id, Date date) {
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

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public GeneralManager getGeneralManager() {
        return generalManager;
    }

    public void setGeneralManager(GeneralManager generalManager) {
        this.generalManager = generalManager;
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
        if (!(object instanceof GeneralManagerAward)) {
            return false;
        }
        GeneralManagerAward other = (GeneralManagerAward) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.GeneralManagerAward[ id=" + id + " ]";
    }

} // end class GeneralManagerAward
