/**
 * This class, Resident, extends Student as a subclass. Contains methods for
 * checking what tuition should be due for the Student depending on credits enrolled
 * and the toString() method.
 * @author Christian Osma, Liam Smith
 */
public class Resident extends Student {
    private int scholarship;

    private static final int  tuitionResident = 15804;
    private static final int fullCredits = 16;
    private static final int minCredits = 12;
    private static final int partTimeResRate = 404;
    private static final double univFeePartTime = 2614.4;

    /**
     * Default constructor that instantiates a new Resident object by calling the
     * Student default constructor
     */
    public Resident(){
        super();
    }
    /**
     * Overloaded constructor that instantiates a new NonResident object by calling the
     * Student constructor and taking 3 parameters to do so
     * @param profile The Profile object to use as an instance variable for the object
     * @param major The Major enum to use as an instance variable for the object
     * @param creditsCompleted the number of credits completed as an integer to use as an instance variable for the object
     */
    public Resident(Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
    }
    /**
     * Overloaded constructor that instantiates a new Resident object by calling the
     * Student default constructor and taking 1 parameter to complete instantiation
     * @param scholarship The integer representation of the scholarship to use as an instance variable for the object
     */
    public Resident(int scholarship){
        super();
        this.scholarship = scholarship;
    }
    /**
     * Calculates what tuition will be due in USD for a Resident Student depending on their credits enrolled
     * @param creditsEnrolled number of credits currently enrolled as an integer
     * @return double value of tuition due for the Student in USD
     */

    @Override
    public double tuitionDue(int creditsEnrolled) {
        if (creditsEnrolled < minCredits) return (partTimeResRate * creditsEnrolled) + univFeePartTime;
        if (creditsEnrolled > fullCredits) return (partTimeResRate * (creditsEnrolled - fullCredits)) + tuitionResident - this.scholarship;
            return tuitionResident - this.scholarship;
    }
    /**
     * Returns whether the Student is a resident
     * @return true since we are in the Resident class
     */
    @Override
    public boolean isResident() {
        return true;
    }

    /**
     * Converts the Resident Student to a String format
     * @return String representation of the Resident Student
     */
    @Override
    public String toString(){
        return super.toString() + " (resident)";
    }

}
