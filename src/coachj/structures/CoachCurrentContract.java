package coachj.structures;

/**
 * Stores basic information about coaches' contracts
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 05/08/2013
 */
public class CoachCurrentContract {

    /**
     * Fields to store coach's id, first and last name, remaining years in the
     * contract and salary
     */
    short coachId;
    String firstName;
    String lastName;
    short remainingYears;
    int salary;    

    /**
     * Constructor
     * 
     * @param coachId Coach's id
     */
    public CoachCurrentContract(short coachId) {
        this.coachId = coachId;
    }
    
    /* getters and setters */
    public short getCoachId() {
        return coachId;
    }

    public void setCoachId(short coachId) {
        this.coachId = coachId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public short getRemainingYears() {
        return remainingYears;
    }

    public void setRemainingYears(short remainingYears) {
        this.remainingYears = remainingYears;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
} // end CoachCurrentContract
