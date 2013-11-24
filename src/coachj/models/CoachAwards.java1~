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
@Table(name = "coach_awards")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CoachAwards.findAll", query = "SELECT c FROM CoachAwards c"),
    @NamedQuery(name = "CoachAwards.findById", query = "SELECT c FROM CoachAwards c WHERE c.id = :id"),
    @NamedQuery(name = "CoachAwards.findByDate", query = "SELECT c FROM CoachAwards c WHERE c.date = :date")})
public class CoachAwards implements Serializable {
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
    @JoinColumn(name = "award", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Award award;
    @JoinColumn(name = "season", referencedColumnName = "year")
    @ManyToOne(optional = false)
    private Season season;
    @JoinColumn(name = "coach", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Coach coach;

    public CoachAwards() {
    }

    public CoachAwards(Integer id) {
        this.id = id;
    }

    public CoachAwards(Integer id, Date date) {
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

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
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
        if (!(object instanceof CoachAwards)) {
            return false;
        }
        CoachAwards other = (CoachAwards) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.CoachAwards[ id=" + id + " ]";
    }
    
}
