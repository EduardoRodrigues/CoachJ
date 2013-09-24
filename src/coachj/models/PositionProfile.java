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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo
 */
@Entity
@Table(name = "position_profile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PositionProfile.findAll", query = "SELECT p FROM PositionProfile p"),
    @NamedQuery(name = "PositionProfile.findById", query = "SELECT p FROM PositionProfile p WHERE p.id = :id"),
    @NamedQuery(name = "PositionProfile.findByPosition", query = "SELECT p FROM PositionProfile p WHERE p.position = :position"),
    @NamedQuery(name = "PositionProfile.findByMinimumHeight", query = "SELECT p FROM PositionProfile p WHERE p.minimumHeight = :minimumHeight"),
    @NamedQuery(name = "PositionProfile.findByMaximumHeight", query = "SELECT p FROM PositionProfile p WHERE p.maximumHeight = :maximumHeight"),
    @NamedQuery(name = "PositionProfile.findByMinimumFieldGoals", query = "SELECT p FROM PositionProfile p WHERE p.minimumFieldGoals = :minimumFieldGoals"),
    @NamedQuery(name = "PositionProfile.findByMinimumThreePointers", query = "SELECT p FROM PositionProfile p WHERE p.minimumThreePointers = :minimumThreePointers"),
    @NamedQuery(name = "PositionProfile.findByMinimumPass", query = "SELECT p FROM PositionProfile p WHERE p.minimumPass = :minimumPass"),
    @NamedQuery(name = "PositionProfile.findByMinimumRebound", query = "SELECT p FROM PositionProfile p WHERE p.minimumRebound = :minimumRebound"),
    @NamedQuery(name = "PositionProfile.findByMinimumBallHandling", query = "SELECT p FROM PositionProfile p WHERE p.minimumBallHandling = :minimumBallHandling"),
    @NamedQuery(name = "PositionProfile.findByMinimumLowPost", query = "SELECT p FROM PositionProfile p WHERE p.minimumLowPost = :minimumLowPost"),
    @NamedQuery(name = "PositionProfile.findByMinimumShootingRange", query = "SELECT p FROM PositionProfile p WHERE p.minimumShootingRange = :minimumShootingRange")})
public class PositionProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @Column(name = "position")
    private String position;
    @Basic(optional = false)
    @Column(name = "minimumHeight")
    private short minimumHeight;
    @Basic(optional = false)
    @Column(name = "maximumHeight")
    private short maximumHeight;
    @Basic(optional = false)
    @Column(name = "minimumFieldGoals")
    private short minimumFieldGoals;
    @Basic(optional = false)
    @Column(name = "minimumThreePointers")
    private short minimumThreePointers;
    @Basic(optional = false)
    @Column(name = "minimumPass")
    private short minimumPass;
    @Basic(optional = false)
    @Column(name = "minimumRebound")
    private short minimumRebound;
    @Basic(optional = false)
    @Column(name = "minimumBallHandling")
    private short minimumBallHandling;
    @Basic(optional = false)
    @Column(name = "minimumLowPost")
    private short minimumLowPost;
    @Basic(optional = false)
    @Column(name = "minimumShootingRange")
    private short minimumShootingRange;

    public PositionProfile() {
    }

    public PositionProfile(Short id) {
        this.id = id;
    }

    public PositionProfile(Short id, String position, short minimumHeight, short maximumHeight, short minimumFieldGoals, short minimumThreePointers, short minimumPass, short minimumRebound, short minimumBallHandling, short minimumLowPost, short minimumShootingRange) {
        this.id = id;
        this.position = position;
        this.minimumHeight = minimumHeight;
        this.maximumHeight = maximumHeight;
        this.minimumFieldGoals = minimumFieldGoals;
        this.minimumThreePointers = minimumThreePointers;
        this.minimumPass = minimumPass;
        this.minimumRebound = minimumRebound;
        this.minimumBallHandling = minimumBallHandling;
        this.minimumLowPost = minimumLowPost;
        this.minimumShootingRange = minimumShootingRange;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public short getMinimumHeight() {
        return minimumHeight;
    }

    public void setMinimumHeight(short minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    public short getMaximumHeight() {
        return maximumHeight;
    }

    public void setMaximumHeight(short maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    public short getMinimumFieldGoals() {
        return minimumFieldGoals;
    }

    public void setMinimumFieldGoals(short minimumFieldGoals) {
        this.minimumFieldGoals = minimumFieldGoals;
    }

    public short getMinimumThreePointers() {
        return minimumThreePointers;
    }

    public void setMinimumThreePointers(short minimumThreePointers) {
        this.minimumThreePointers = minimumThreePointers;
    }

    public short getMinimumPass() {
        return minimumPass;
    }

    public void setMinimumPass(short minimumPass) {
        this.minimumPass = minimumPass;
    }

    public short getMinimumRebound() {
        return minimumRebound;
    }

    public void setMinimumRebound(short minimumRebound) {
        this.minimumRebound = minimumRebound;
    }

    public short getMinimumBallHandling() {
        return minimumBallHandling;
    }

    public void setMinimumBallHandling(short minimumBallHandling) {
        this.minimumBallHandling = minimumBallHandling;
    }

    public short getMinimumLowPost() {
        return minimumLowPost;
    }

    public void setMinimumLowPost(short minimumLowPost) {
        this.minimumLowPost = minimumLowPost;
    }

    public short getMinimumShootingRange() {
        return minimumShootingRange;
    }

    public void setMinimumShootingRange(short minimumShootingRange) {
        this.minimumShootingRange = minimumShootingRange;
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
        if (!(object instanceof PositionProfile)) {
            return false;
        }
        PositionProfile other = (PositionProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.PositionProfile[ id=" + id + " ]";
    }
    
}
