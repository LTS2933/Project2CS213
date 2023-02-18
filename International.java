/**
 * This class, International, extends NonResident as a subclass. Contains methods for
 * checking what tuition should be due for the Student depending on credits enrolled
 * and the toString() method.
 * @author Christian Osma, Liam Smith
 */
public class International extends NonResident {
    private boolean isStudyAbroad;

    private static final int healthInsuranceFee = 2650;
    private static final int studyAbroadCost = 5918;

    /**
     * Default constructor that instantiates a new International object by calling the
     * NonResident default constructor
     */
    public International(){
        super();
    }
    /**
     * Overloaded constructor that instantiates a new International object by calling the
     * NonResident constructor and taking 3 parameters to do so
     * @param profile The Profile object to use as an instance variable for the object
     * @param major The Major enum to use as an instance variable for the object
     * @param creditsCompleted the number of credits completed as an integer to use as an instance variable for the object
     */
    public International(Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
    }
    /**
     * Overloaded constructor that instantiates a new International object by calling the
     * NonResident default constructor and taking 1 parameter to complete instantiation
     * @param isStudyAbroad The boolean value of whether the object is a study abroad student to use as an instance variable for the object
     */
    public International(boolean isStudyAbroad){
        super();
        this.isStudyAbroad = isStudyAbroad;
    }

    /**
     * Overloaded constructor that instantiates a new International object by calling the
     * NonResident default constructor and taking 1 parameter to complete instantiation
     * @param isStudyAbroad The boolean value of whether the object is a study abroad student to use as an instance variable for the object
     * @param profile The Profile object to use as an instance variable for the object
     * @param major The Major enum to use as an instance variable for the object
     * @param creditsCompleted the number of credits completed as an integer to use as an instance variable for the object
     */
    public International(boolean isStudyAbroad, Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
        this.isStudyAbroad = isStudyAbroad;
    }

    /**
     * Returns a boolean value that says whether the International Student is study abroad.
     * @return true if the Student is study abroad, false if not
     */
    public boolean getIsStudyAbroad(){
        return this.isStudyAbroad;
    }
    /**
     * Calculates what tuition will be due in USD for an International Student depending on their credits enrolled
     * @param creditsEnrolled number of credits currently enrolled as an integer
     * @return double value of tuition due for the Student in USD
     */
    @Override
    public double tuitionDue(int creditsEnrolled){
        double tuition = 0;
        if (this.getIsStudyAbroad()) {
            tuition = 5918;
        }
        else {
            tuition = super.tuitionDue(creditsEnrolled) + healthInsuranceFee;
        }
        return tuition;
    }
    /**
     * Converts the International Student to a String format
     * @return String representation of the International Student
     */
    @Override
    public String toString(){
        return super.toString() + " (international)";
    }

}
