package coachj.structures;

/**
 * Stores information about stats displayed on comboboxes
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 26/09/2013
 */
public class StatItem {

    /**
     * Fields
     */
    private String description;
    private String sqlEquation;
    
    /**
     * Constructor
     */
    public StatItem() {
    }    
    
    /* getters and setters */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    

    public String getSqlEquation() {
        return sqlEquation;
    }

    public void setSqlEquation(String sqlEquation) {
        this.sqlEquation = sqlEquation;
    }
    
    @Override
    public String toString() {
        return description;
    } 
} // end StatItem
