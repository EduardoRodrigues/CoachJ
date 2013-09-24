package coachj.structures;

/**
 * Stores information about the square-meter piece of the court
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 01/09/2013
 */
public class CourtSpot {

    /**
     * Fields
     */   
    private int distanceX;
    private int distanceY;
    private int basketPoints;
    private int id;
    private int courtZone;
    private String description;
    private String receivingConstruction;
    private String scoringConstruction;
    
    /**
     * Constructor
     */
    public CourtSpot() {
    }

    /* getters and setters */
    public int getDistanceX() {
        return distanceX;
    }

    public void setDistanceX(int distanceX) {
        this.distanceX = distanceX;
    }

    public int getDistanceY() {
        return distanceY;
    }

    public void setDistanceY(int distanceY) {
        this.distanceY = distanceY;
    }

    public int getBasketPoints() {
        return basketPoints;
    }

    public void setBasketPoints(int basketPoints) {
        this.basketPoints = basketPoints;
    }      

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourtZone() {
        return courtZone;
    }

    public void setCourtZone(int courtZone) {
        this.courtZone = courtZone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceivingConstruction() {
        return receivingConstruction;
    }

    public void setReceivingConstruction(String receivingConstruction) {
        this.receivingConstruction = receivingConstruction;
    }

    public String getScoringConstruction() {
        return scoringConstruction;
    }

    public void setScoringConstruction(String scoringConstruction) {
        this.scoringConstruction = scoringConstruction;
    }    
    
} // end CourtSpot
