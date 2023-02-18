/**
 * This class, NonResident extends Student as a subclass. Contains methods for
 * checking what tuition should be due for the Student depending on credits enrolled
 * and the toString() method.
 * @author Christian Osma, Liam Smith
 */
public class NonResident extends Student {

    private static final int  tuitionNonResident = 33005;
    private static final int fullCredits = 16;

    private static final int minCredits = 12;
    private static final int partTimeNonResRate = 966;

    private static final double univFeePartTime = 2614.4;

    /**
     * Default constructor that instantiates a new NonResident object by calling the
     * Student default constructor
     */
    public NonResident(){
        super();
    }

    /**
     * Overloaded constructor that instantiates a new NonResident object by calling the
     * Student constructor and taking 3 parameters to do so
     * @param profile The Profile object to use as an instance variable for the object
     * @param major The Major enum to use as an instance variable for the object
     * @param creditsCompleted the number of credits completed as an integer to use as an instance variable for the object
     */
    public NonResident(Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
    }

    /**
     * Calculates what tuition will be due in USD for a NonResident Student depending on their credits enrolled
     * @param creditsEnrolled number of credits currently enrolled as an integer
     * @return double value of tuition due for the Student in USD
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        if (creditsEnrolled < minCredits) return partTimeNonResRate * creditsEnrolled + univFeePartTime;
        if (creditsEnrolled > fullCredits) return (partTimeNonResRate * (creditsEnrolled - fullCredits) + tuitionNonResident);
        return tuitionNonResident;
    }

    /**
     * Returns whether the Student is a resident
     * @return false since we are in the NonResident class
     */
    @Override
    public boolean isResident() {
        return false;
    }

    /**
     * Converts the NonResident Student to a String format
     * @return String representation of the NonResident Student
     */
    @Override
    public String toString(){
        return super.toString() + " (non-resident)";
    }

}
