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
import javax.persistence.Lob;
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
@Table(name = "game_news")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GameNews.findAll", query = "SELECT g FROM GameNews g"),
    @NamedQuery(name = "GameNews.findById", query = "SELECT g FROM GameNews g WHERE g.id = :id"),
    @NamedQuery(name = "GameNews.findByDate", query = "SELECT g FROM GameNews g WHERE g.date = :date"),
    @NamedQuery(name = "GameNews.findByTitle", query = "SELECT g FROM GameNews g WHERE g.title = :title"),
    @NamedQuery(name = "GameNews.findByShortText", query = "SELECT g FROM GameNews g WHERE g.shortText = :shortText")})
public class GameNews implements Serializable {
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
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "shortText")
    private String shortText;
    @Lob
    @Column(name = "article")
    private String article;
    @JoinColumn(name = "season", referencedColumnName = "year")
    @ManyToOne(optional = false)
    private Season season;
    @JoinColumn(name = "game", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Game game;
    @JoinColumn(name = "relatedFranchise2", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Franchise relatedFranchise2;
    @JoinColumn(name = "relatedFranchise1", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Franchise relatedFranchise1;

    public GameNews() {
    }

    public GameNews(Integer id) {
        this.id = id;
    }

    public GameNews(Integer id, Date date, String title, String shortText) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.shortText = shortText;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Franchise getRelatedFranchise2() {
        return relatedFranchise2;
    }

    public void setRelatedFranchise2(Franchise relatedFranchise2) {
        this.relatedFranchise2 = relatedFranchise2;
    }

    public Franchise getRelatedFranchise1() {
        return relatedFranchise1;
    }

    public void setRelatedFranchise1(Franchise relatedFranchise1) {
        this.relatedFranchise1 = relatedFranchise1;
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
        if (!(object instanceof GameNews)) {
            return false;
        }
        GameNews other = (GameNews) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coachj.models.GameNews[ id=" + id + " ]";
    }
    
}
