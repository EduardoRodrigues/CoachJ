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
 * @author Eduardo M. Rodrigues
 * @version 1.0 /2012
 */
@Entity
@Table(name = "narration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Narration.findAll", query = "SELECT n FROM Narration n"),
    @NamedQuery(name = "Narration.findById", query = "SELECT n FROM Narration n WHERE n.id = :id"),
    @NamedQuery(name = "Narration.findByNarrationEn", query = "SELECT n FROM Narration n WHERE n.narrationEn = :narrationEn"),
    @NamedQuery(name = "Narration.findByNarrationPt", query = "SELECT n FROM Narration n WHERE n.narrationPt = :narrationPt")})
public class Narration implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "narration_en")
    private String narrationEn;
    @Basic(optional = false)
    @Column(name = "narration_pt")
    private String narrationPt;
    @JoinColumn(name = "playType", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PlayType playType;

    public Narration() {
    }

    public Narration(Integer id) {
        this.id = id;
    }

    public Narration(Integer id, String narrationEn, String narrationPt) {
        this.id = id;
        this.narrationEn = narrationEn;
        this.narrationPt = narrationPt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNarrationEn() {
        return narrationEn;
    }

    public void setNarrationEn(String narrationEn) {
        this.narrationEn = narrationEn;
    }

    public String getNarrationPt() {
        return narrationPt;
    }

    public void setNarrationPt(String narrationPt) {
        this.narrationPt = narrationPt;
    }

    public PlayType getPlayType() {
        return playType;
    }

    public void setPlayType(PlayType playType) {
        this.playType = playType;
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
        if (!(object instanceof Narration)) {
            return false;
        }
        Narration other = (Narration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.Narration[ id=" + id + " ]";
    }

} // end class Narration
