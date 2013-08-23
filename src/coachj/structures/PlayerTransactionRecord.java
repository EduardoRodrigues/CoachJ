package coachj.structures;

/**
 * Stores information about coach transactions
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 21/08/2013
 */
public class PlayerTransactionRecord {

    /**
     * Fields
     */
    private short season;
    private int player;
    private short franchise;
    private String type;
    private String date;
    private short length;
    private int salary;
    
    /**
     * Constructor
     */
    public PlayerTransactionRecord() {
    }

    /* getters and setters */
    public short getSeason() {
        return season;
    }

    public void setSeason(short season) {
        this.season = season;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public short getFranchise() {
        return franchise;
    }

    public void setFranchise(short franchise) {
        this.franchise = franchise;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    
    
} // end PlayerTransactionRecord
