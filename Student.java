/**
 * This class implements the Comparable Interface and runs various methods on Student objects.
 * Students can be sorted by standing through this Class using the compareTo() method.
 *
 * @author Christian Osma, Liam Smith
 **/
public abstract class Student implements Comparable<Student>{
    private Profile profile;
    private Major major;
    private int creditsCompleted;

    /**
    Default constructor. Takes no arguments and instantiates a new Student object. Calls the Default
    Constructor of the Profile Class, initiates major as null, and creditsCompleted as 0.
     */
    public Student(){
        this.profile = new Profile();
        this.major = null;
        this.creditsCompleted = 0;
    }
    /**
    Overloaded constructor. Takes 3 arguments and uses them to populate the current Student object.
    @param profile - Profile object that populates the Profile attribute of the object
    @param major - enum instance that populates the Student's Major attribute
    @param creditsCompleted - number of credits the Student has completed in school as an int type
     */
    public Student(Profile profile, Major major, int creditsCompleted){
        this.profile = profile;
        this.major = major;
        this.creditsCompleted = creditsCompleted;
    }
    /**
    Copy constructor. Copies the information of the argument and stores its attributes within
    the corresponding instance variables of the current Student object.
    @param st - Student object which contains the information we wish to copy to the current Student object
     */
    public Student(Student st){
        this.profile = st.profile;
        this.major = st.major;
        this.creditsCompleted = st.creditsCompleted;
    }

    /**
    Getter method, returns the Profile of the current Student as a String type.
    @return Profile of the current Student as a String (toString method is called)
     */
    public Profile getProfile(){
        return this.profile;
    }
    public Student find(){
        return this;
    }
    /**

     */
    public boolean isValid(int creditsEnrolled){
        return (creditsEnrolled > 2 || creditsEnrolled < 24);
    }

    /**

     */
    public abstract double tuitionDue(int creditsEnrolled);
    public abstract boolean isResident();

    /**
    Getter method, returns the Major of the current Student as an enum type.
    @return Major of the current Student as an enum
     */
    public Major getMajor(){
        return this.major;
    }

    /**
    Setter method, sets Student's Major to that of user's choice
    @param major - Major enum which will be used to set Student's Major attribute
     */
    public void setMajor(Major major){
        this.major = major;
    }

    /**
    Getter method, returns the number of credits completed by the Student as an int.
    @return number of credits completed by the current Student object
     */
    public int getCredits(){
        return this.creditsCompleted;
    }

    /**
    Converts the current Student object to a String format
    @return String that includes various attributes of the current Student in a specific format
     */
    @Override
    public String toString(){
        String str = profile.toString();
        str =  str + " (" + major.getMajorCode() + " " + major + " " + major.getSchoolName() + ") ";
        str = str + "credits completed : " + creditsCompleted;
        String year = "Senior";
        if (this.creditsCompleted < 30) year = "Freshman";
        else if (this.creditsCompleted < 60) year = "Sophomore";
        else if (this.creditsCompleted < 90) year = "Junior";
        str += " (" + year + ")";
        return str;
    }
    /**
    Says whether the current Student is equal to the one passed as an argument
    @param obj - should be an instance of Student or method will automatically return false.
    Otherwise, obj is a Student instance to be compared against the current Student object
    @return true if the two Student objects are the same Student, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        Student st = null;
        if (obj instanceof Student) {
            st = (Student) obj;
        }
        return (this.profile.equals(st.getProfile()));
    }
    /**
    Helps to sort Student objects based on names and dates of birth.
    @param st - instance of Student object which will be used to compare against current Student object.
    @return -1 if the current Student should take priority over Student st, 0 if they are equal, and 1 if st
    should take priority in the sorting order.
     */
    @Override
    public int compareTo(Student st) {
        return this.profile.compareTo(st.profile);
    }
}