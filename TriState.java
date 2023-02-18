/**
 * This class extends NonResident. Contains methods for
 * checking what tuition should be due for the Student depending on credits enrolled
 * and the toString() method.
 * @author Christian Osma, Liam Smith
 */
public class TriState extends NonResident {
    private String state;

    /**
     * Default constructor that instantiates a new TriState object by calling the
     * NonResident constructor
     */
    public TriState(){
        super();
        state = null;
    }

    /**
     * Overloaded constructor that instantiates a new TriState object by calling the
     * NonResident constructor and taking 3 parameters to do so
     * @param state The String representation of the state to use as an instance variable for the object
     * @param profile The Profile object to use as an instance variable for the object
     * @param major The Major enum to use as an instance variable for the object
     * @param creditsCompleted the number of credits completed as an integer to use as an instance variable for the object
     */
    public TriState(String state, Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
        this.state = state;
    }

    /**
     * Overloaded constructor that instantiates a new TriState object by calling the
     * NonResident default constructor and taking 1 parameter to complete instantiation
     * @param state The String representation of the state to use as an instance variable for the object
     */
    public TriState(String state){
        super();
        this.state = state;
    }

    /**
     * Calculates what tuition will be due in USD for a Tri State Student depending on their credits enrolled
     * @param creditsEnrolled number of credits currently enrolled as an integer
     * @return double value of tuition due for the Student in USD
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        if (creditsEnrolled >= 12) return super.tuitionDue(creditsEnrolled) - (state.toLowerCase().equals("ny") ? 4000 : 5000);
        return super.tuitionDue(creditsEnrolled);
    }

    /**
     * Converts the Tri State Student to a String format
     * @return String representation of the Tri State Student
     */
    @Override
    public String toString(){
        return super.toString() + " (tri-state:" + this.state + ")";
    }
}
