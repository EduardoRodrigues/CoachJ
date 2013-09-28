package coachj.lists;

/**
 * Creates items that can be attached to a list object displaying a string as 
 * description and having an id in the background
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 02/08/2013
 */
public class IdDescriptionListItem {

    /*
     * Fields that store the identifier and the description of the item.
     */
    private int id;
    private String description;

    /**
     * Constructor
     * @param id Item's id
     * @param description Item's description
     */
    public IdDescriptionListItem(int id, String description) {
        this.id = id;
        this.description = description;
    }

    /* getters and setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /* overriden methods */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IdDescriptionListItem other = (IdDescriptionListItem) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return description;
    }   
} // end IdDescriptionListItem
