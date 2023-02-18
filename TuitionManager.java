package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class is responsible for executing the project. Contains methods for 
 * reading standard input, detecting errors, and executing user commands
 * that interact with instance variables.
 * @author Christian Osma, Liam Smith
 */
public class TuitionManager {
    private static final int REQUIRED_ADD_TOKENS = 4;
    private static final int REQUIRED_TRISTATE_ADD_TOKENS = 4;
    private static final int REQUIRED_INTERNATIONAL_ADD_TOKENS = 5;
    private static final int REQUIRED_ENROLL_TOKENS = 4;
    private static final int REQUIRED_DROP_TOKENS = 3;
    private static final int REQUIRED_AWARD_TOKENS = 3;
    private static final boolean NOT_FROM_LOAD = false;
    private static final boolean FROM_LOAD = true;
    private static final int MIN_SCHOLARSHIP = 1;
    private static final int MAX_SCHOLARSHIP = 10000;
    private static final int ERROR_CREDITS = -404;

    private Scanner sc;
    private Enrollment enrollment;
    private Roster roster;

    /**
     * Default constructor that creates a new Scanner that reads from standard input, 
     * creates a new Enrollment instance with the default constructor, and a new Roster
     * instance with the default constructor.
     */
    public TuitionManager(){
        this.sc = new Scanner(System.in);
        this.enrollment = new Enrollment();
        this.roster = new Roster();
    }

    
    /** 
     * Starts TuitionManager and continuously reads input from the user 
     * and calls other methods to handle those commands. Continues until the Q
     * commands which exits the program.
     * @throws FileNotFoundException Error if the file was not found
     */
    public void run() throws FileNotFoundException{
        System.out.println("Tuition Manager running...");
        System.out.println();
        boolean running = true;
        while(running){
            String line = sc.nextLine();
            StringTokenizer st = new StringTokenizer(line);
            if (st.countTokens() == 0) continue;

            String firstOperation = st.nextToken();
            if (firstOperation.equals("C")) handleChangeMajor(st);
            else if (firstOperation.equals("P")) this.roster.print();
            else if (firstOperation.equals("PS")) this.roster.printByStanding();
            else if (firstOperation.equals("PC")) this.roster.printBySchoolMajor();
            else if (firstOperation.equals("AR")) handleAddResident(st, NOT_FROM_LOAD);
            else if (firstOperation.equals("AN")) handleAddNonResident(st, NOT_FROM_LOAD);
            else if (firstOperation.equals("AT")) handleAddTristateStudent(st, NOT_FROM_LOAD);
            else if (firstOperation.equals("AI")) handleAddInternationalStudent(st, NOT_FROM_LOAD);
            else if (firstOperation.equals("E")) handleEnrollStudent(st);
            else if (firstOperation.equals("D")) handleDropStudent(st);
            else if (firstOperation.equals("S")) handleAwardScholarship(st);
            else if (firstOperation.equals("PE")) this.enrollment.print();
            else if (firstOperation.equals("PT")) handlePrintTuition();
            else if (firstOperation.equals("LS")) handleLoadStudents(st.nextToken());
            else if (firstOperation.equals("SE")) handleSemesterEnd();
            else if (firstOperation.equals("R")) handleRemoveStudent(st);
            else if (firstOperation.equals("Q")){
                System.out.println("Tuition Manager terminated.");
                running = false;
            } else System.out.println(firstOperation + " is an invalid command!");
        }
    }

    /**
     * Handles SE commands which displays the students in the enrollment list that
     * are eligible for graduation after the semester.
     */
    private void handleSemesterEnd(){
        System.out.println("Credit completed has been updated.");
        System.out.println("** list of students eligible for graduation **");
        Student [] array = new Student[this.roster.getSize()];
        int index = 0;
        for (int i = 0; i < this.enrollment.getSize(); i++){ //iterates through enrollment
            for(int j = 0; j < this.roster.getSize(); j++){ //iterates through roster
                Profile enroll =  this.enrollment.returnEnrollStudent()[i].getProfile(); //finds the profile through enrollment
                Profile roster = this.roster.returnRoster()[j].getProfile(); //finds the profile through roster
                if(enroll.equals(roster)) {
                    int creditsEnrolled = this.enrollment.returnEnrollStudent()[i].getCreditsEnrolled();
                    int creditsCompleted = this.roster.returnRoster()[j].getCredits();
                    if (creditsCompleted + creditsEnrolled >= 120){
                        this.roster.returnRoster()[j].setCreditCompleted(creditsCompleted + creditsEnrolled);
                        array[index] = this.roster.returnRoster()[j];
                        index++;
                    }
                }
            }
        }
        for (int i = 0; i<index-1; i++){
            Student minimum = array[i];
            int minIndex = i;
            for (int j= i+1; j<index; j++){
                if (array[j].getCredits() > minimum.getCredits()){
                    minimum = array[j];
                    minIndex = j;
                }
            }
            System.out.println(minimum.toString());
            Student temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
        System.out.println(array[index-1].toString());
    }

    /**
     * Iterates through the enrollment list and prints out the tuition due
     * by each student, which is handle by the tuitionDue() method in the Student class.
     */
    private void handlePrintTuition(){
        if (this.roster.getSize() == 0){
            System.out.println("Student roster is empty!");
            return;
        }
        System.out.println("** Tuition due **");
        for (int i = 0; i < this.enrollment.getSize(); i++){ //iterates through enrollment
            for(int j = 0; j < this.roster.getSize(); j++){ //iterates through roster
                Profile enroll =  this.enrollment.returnEnrollStudent()[i].getProfile(); //finds the profile through enrollment
                Profile roster = this.roster.returnRoster()[j].getProfile(); //finds the profile through roster
                if(enroll.equals(roster)){
                    int creditsEnrolled = this.enrollment.returnEnrollStudent()[i].getCreditsEnrolled();
                    double tuition = this.roster.returnRoster()[j].tuitionDue(creditsEnrolled);
                    DecimalFormat df = new DecimalFormat("#,###.00");

                    EnrollStudent enrollStudent = this.enrollment.returnEnrollStudent()[i];
                    Student dummyStudent = new Resident(enrollStudent.getProfile(), Major.CS, 0);
                    System.out.println(enrollStudent.getProfile().toString() + " " + this.roster.typeOfStudent(dummyStudent) + " enrolled " + enrollStudent.getCreditsEnrolled() + " credits: tuition due: $" + df.format(tuition));
                }
            }
        }
        System.out.println("* end of tuition due *");
    }

    
    /** 
     * This method reads input from a file passed into it and adds students to the roster depending
     * on the contents of that file.
     * @param file String representation of the file to read containing new students
     * @throws FileNotFoundException Error if the file was not found
     */
    private void handleLoadStudents(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/" + file));
        while (scanner.hasNextLine()){
            StringTokenizer st = new StringTokenizer(scanner.nextLine(), ",");
            String firstOperating = st.nextToken();
            switch(firstOperating){
                case "R": 
                    handleAddResident(st, FROM_LOAD);
                    break;
                case "N":
                    handleAddNonResident(st, FROM_LOAD);
                    break;
                case "I":
                    handleAddInternationalStudent(st, FROM_LOAD);
                    break;
                case "T":
                    handleAddTristateStudent(st, FROM_LOAD);
                    break;
                default: 
                    System.out.println("Invalid command.");
                    return;
            }
        }
        System.out.println("Students loaded to the roster.");
        scanner.close();
    }

    
    /** 
     * Awards a scholarship to a specific student by updating their scholarship amount. 
     * This method also detects and displays errors such as invalid scholarships, non-resident
     * students, part time students, and if there is not enough information.
     * @param st StringTokenizer representing the remainder of the user commands.
     */
    private void handleAwardScholarship(StringTokenizer st){
        if (st.countTokens() < REQUIRED_AWARD_TOKENS){
            System.out.println("Missing data in line command.");
            return;
        }
        String firstName = st.nextToken();
        String lastName = st.nextToken();
        Date dob = new Date(st.nextToken());
        Student student = new Resident(new Profile(firstName, lastName, dob), Major.CS, 0);
        if (this.roster.contains(student) == false){
            System.out.println(student.getProfile().toString() + " is not in the roster.");
            return;
        }
        if (this.roster.isResident(student) == false){
            String typeOfStudent = this.roster.typeOfStudent(student);
            System.out.println(student.getProfile().toString() + " " + typeOfStudent + " is not eligible for the scholarship.");
            return;
        }

        String stringScholarship = st.nextToken();
        int scholarship = 0;
        try {
            scholarship = Integer.parseInt(stringScholarship);
        } catch (Exception e){
            System.out.println("Amount is not an integer.");
            return;
        }
        if (scholarship < MIN_SCHOLARSHIP || scholarship > MAX_SCHOLARSHIP){
            System.out.println(scholarship + ": invalid amount.");
            return;
        }
        EnrollStudent enrollStudent = new EnrollStudent(student.getProfile(), 0);
        if (this.enrollment.isPartTime(enrollStudent) == true){
            System.out.println(enrollStudent.getProfile().toString() + " part time student is not eligible for the scholarship.");
            return;
        }
        this.roster.updateScholarship(student, scholarship);
        System.out.println(student.getProfile().toString() + ": scholarship amount updated.");
    }

    /**
     Removes student from the Roster unless they are not in the roster.
     Prints out error or success message.
     @param st : StringTokenizer that contains the rest of the user input
     */
    private void handleRemoveStudent(StringTokenizer st){
        String firstName = st.nextToken();
        String lastName = st.nextToken();
        String stringDate = st.nextToken();
        Date date = new Date(stringDate);

        Student student = new Resident(new Profile(firstName, lastName, date), Major.CS, 106);
        if (this.roster.contains(student) == false){
            System.out.println(firstName + " " + lastName + " " + stringDate + " is not in the roster.");
            return;
        }

        this.roster.remove(student);
        System.out.println(firstName + " " + lastName + " " + stringDate + " removed from the roster.");

    }

    
    /** 
     * Method that handles dropping a student from enrollment using the remainder
     * of the user commands. Detects and displays errors such as invalid commands 
     * and if the student is not in the enrollment list.
     * @param st StringTokenizer representing the remainder of the user command
     */
    private void handleDropStudent(StringTokenizer st){
        if (st.countTokens() < REQUIRED_DROP_TOKENS){
            System.out.println("Missing data in line command.");
            return;
        }

        String firstName = st.nextToken();
        String lastName = st.nextToken();
        Date dob = new Date(st.nextToken());

        EnrollStudent enrollStudent = new EnrollStudent(new Profile(firstName, lastName, dob), 0);
        if (this.enrollment.contains(enrollStudent) == false){
            System.out.println(enrollStudent.getProfile().toString() + " is not enrolled.");
            return;
        }

        this.enrollment.remove(enrollStudent);
        System.out.println(enrollStudent.getProfile().toString() + " dropped.");
    }

    
    /** 
     * This method handles enrolling new students into the enrollment list. This method
     * also detects errors such as invalid commands, invalid date of births, invalid
     * credits enrolled, and if the student is not in the roster.
     * @param st StringTokenizer representing the rest of the user command
     */
    private void handleEnrollStudent(StringTokenizer st){
        if (st.countTokens() < REQUIRED_ENROLL_TOKENS){
            System.out.println("Missing data in line command.");
            return;
        }
        String firstName = st.nextToken();
        String lastName = st.nextToken();
        String stringDOB = st.nextToken();
        Date dob = new Date(stringDOB);
        String stringCredits = st.nextToken();

        boolean validDate = validDate(dob, stringDOB);
        if (validDate == false) return;

        int credits = 0;
        try {
            credits = Integer.parseInt(stringCredits);
        } catch (Exception e){ 
            System.out.println("Credits enrolled is not an integer.");
            return;
        }

        Student student = new Resident(new Profile(firstName, lastName, dob), Major.CS, credits);
        if (this.roster.contains(student) == false){
            System.out.println("Cannot enroll: " + student.getProfile().toString() + " is not in the roster.");
            return;
        }

        if (this.roster.validCredits(student, credits) == false){
            String typeOfStudent = this.roster.typeOfStudent(student);
            System.out.println(typeOfStudent + " " + credits + ": invalid credit hours.");
            return;
        }
        EnrollStudent newStudent = new EnrollStudent(new Profile(firstName, lastName, dob), credits);
        if (this.enrollment.contains(newStudent)) this.enrollment.setCreditsEnrolled(newStudent, credits);
        else this.enrollment.add(newStudent);
        System.out.println(newStudent.getProfile().toString() + " enrolled " + credits + " credits");
    }
    
    /** 
     * This method handles added new international students. Detects errors such as 
     * invalid commands, invalid dates, invalid majors, etc. Prints out a success 
     * message unless this method was called from handleLoadStudent() method.
     * @param st StringTokenizer representing the remainder of user commands
     * @param isFromLoad true if the method calling this is from handleLoadStudent() and false if not
     */
    private void handleAddInternationalStudent(StringTokenizer st, boolean isFromLoad){
        if (st.countTokens() < REQUIRED_INTERNATIONAL_ADD_TOKENS){
            System.out.println("Missing data in line command.");
            return;
        }
        String firstName = st.nextToken();
        String lastName = st.nextToken();
        String stringDOB = st.nextToken();
        Date dob = new Date(stringDOB);
        String stringMajor = st.nextToken();
        String stringCredits = st.nextToken();

        boolean isStudyAbroad = false;
        if (st.hasMoreTokens() == true && st.nextToken().toLowerCase().equals("true")) isStudyAbroad = true;
        boolean validDate = validDate(dob, stringDOB);
        if (validDate == false) return;

        Major major = getMajor(stringMajor.toUpperCase());
        if (major == null){
            System.out.println("Major code invalid: " + stringMajor);
            return;
        }
        int credits = 0;
        try { 
            credits = Integer.parseInt(stringCredits);
        } catch (Exception e){ 
            System.out.println("Credits completed invalid: not an integer!");
        }
        if (credits < 0){
            System.out.println("Credits completed invalid: cannot be negative!");
            return;
        }
        Student student = new International(isStudyAbroad, new Profile(firstName, lastName, dob), major, credits);
        boolean addStudent = this.roster.add(student);
        if (addStudent == false){
            System.out.println(student.getProfile().toString() + " is already in the roster.");
            return;
        }
        if (isFromLoad == NOT_FROM_LOAD) System.out.println(student.getProfile().toString() + " added to the roster.");
    }

    
    /** 
     * This method handles adding new tri-state students. Detects errors such as invalid 
     * states, invalid commands, invalid majors, and invalid credits. Displays success
     * message unless the method is called from handleLoadStudents().
     * @param st StringTokenizer representing the remainder of the user command
     * @param isFromLoad boolean that is true if the method calling it is handleLoadStudent() and false otherwise
     */
    private void handleAddTristateStudent(StringTokenizer st, boolean isFromLoad){
        if (st.countTokens() < REQUIRED_TRISTATE_ADD_TOKENS){
            if (st.countTokens() == 3) System.out.println("Missing data in command line.");
            else System.out.println("Missing data in line command.");
            return;
        }
        String firstName = st.nextToken();
        String lastName = st.nextToken();
        String stringDOB = st.nextToken();
        Date dob = new Date(stringDOB);
        String stringMajor = st.nextToken();
        String stringCredits = st.nextToken();
        if (st.hasMoreTokens() == false){
            System.out.println("Missing the state code.");
            return;
        }
        String state = st.nextToken();
        boolean validDate = validDate(dob, stringDOB);
        if (validDate == false) return;

        Major major = getMajor(stringMajor.toUpperCase());
        if (major == null){
            System.out.println("Major code invalid: " + stringMajor);
            return;
        }
        int credits = validCredits(stringCredits);
        if (credits == ERROR_CREDITS) return;
        if (state.toLowerCase().equals("ny") == false && state.toLowerCase().equals("ct") == false && state.toLowerCase().equals("nj") == false){
            System.out.println(state + ": Invalid state code.");
            return;
        }
        Student student = new TriState(state, new Profile(firstName, lastName, dob), major, credits);
        boolean addStudent = this.roster.add(student);
        if (addStudent == false){
            System.out.println(student.getProfile().toString() + " is already in the roster.");
            return;
        }
        if (isFromLoad == NOT_FROM_LOAD) System.out.println(student.getProfile().toString() + " added to the roster.");
    }

    
    /** 
     * This method handles adding new non-resident students. Detects errors such as
     * invalid commands, invalid major, invalid date of birth, and if the student is already
     * in the roster. Displays success message unless message caller is handleLoadStudents()
     * @param st StringTokenizer representing the remainder of the user command
     * @param isFromLoad true if the method caller is handleLoadStudents() and false otherwise
     */
    private void handleAddNonResident(StringTokenizer st, boolean isFromLoad){
        if (st.countTokens() < REQUIRED_ADD_TOKENS){
            System.out.println("Missing data in line command.");
            return;
        }
        String firstName = st.nextToken();
        String lastName = st.nextToken();
        String stringDOB = st.nextToken();
        Date dob = new Date(stringDOB);
        String stringMajor = st.nextToken();
        String stringCredits = st.nextToken();

        boolean validDate = validDate(dob, stringDOB);
        if (validDate == false) return;

        Major major = getMajor(stringMajor.toUpperCase());
        if (major == null){
            System.out.println("Major code invalid: " + stringMajor);
            return;
        }
        int credits = 0;
        try { 
            credits = Integer.parseInt(stringCredits);
        } catch (Exception e){ 
            System.out.println("Credits completed invalid: not an integer!");
        }
        if (credits < 0){
            System.out.println("Credits completed invalid: cannot be negative!");
            return;
        }
        Student student = new NonResident(new Profile(firstName, lastName, dob), major, credits);
        boolean addStudent = this.roster.add(student);
        if (addStudent == false){
            System.out.println(student.getProfile().toString() + " is already in the roster.");
            return;
        }
        if (isFromLoad == NOT_FROM_LOAD) System.out.println(student.getProfile().toString() + " added to the roster.");
    }

    
    /** 
     * This method handles adding new resident students. Detects errors such as invalid
     * commands and invalid inputted information about the resident student. Displays
     * success message unless the method caller is handleLoadStudents()
     * @param st StringTokenizer containing the remainder of the user command
     * @param isFromLoad true if the method caller is handleLoadStudents() and false otherwise
     */
    private void handleAddResident(StringTokenizer st, boolean isFromLoad){
        if (st.countTokens() < REQUIRED_ADD_TOKENS){
            System.out.println("Missing data in line command.");
            return;
        }
        String firstName = st.nextToken();
        String lastName = st.nextToken();
        String stringDOB = st.nextToken();
        Date dob = new Date(stringDOB);
        String stringMajor = st.nextToken();
        String stringCredits = st.nextToken();
        Major major = getMajor(stringMajor.toUpperCase());
        if (major == null){
            System.out.println("Major code invlaid: " + stringMajor);
            return;
        }
        boolean validDate = validDate(dob, stringDOB);
        if (validDate == false) return;

        int credits = 0;
        try  {
            credits = Integer.parseInt(stringCredits);
        } catch (Exception e){
            System.out.println("Credits completed invalid: not an integer!");
            return;
        }
        if (credits < 0){
            System.out.println("Credits completed invalid: cannot be negative!");
            return;
        }
        Student student = new Resident(new Profile(firstName, lastName, dob), major, credits);
        boolean addStudent = this.roster.add(student);
        if (addStudent == false){
            System.out.println(student.getProfile().toString() + " is already in the roster.");
            return;
        }
        if (isFromLoad == NOT_FROM_LOAD) System.out.println(student.getProfile().toString() + " added to the roster.");
    }

    /**
     * Helper method that checks if the date is a valid of date of birth.
     * @param date Date object which represents the date to be checked
     * @param stringDate date inputted by the user, which is used to print error to standard output
     * @return true if the date is a valid date of birth and false otherwise
     */
    private boolean validDate(Date date, String stringDate){
        if (date.isValid() == false){
            System.out.println("DOB invalid: " + stringDate + " not a valid calendar date!");
            return false;
        }
        if (isBeforeCurrent(date) == false){
            System.out.println("DOB invalid: " + stringDate + " is today or in the future!");
            return false;
        }
        if (date.getAge() < 16){
            System.out.println("DOB invalid: " + stringDate + " younger than 16 years old.");
            return false;
        }
        return true;
    }

    /**
     Takes in a student's date of birth and returns true if the student
     was born before present and false otherwise
     @param dob : student's date of birth
     @return true if student was born before present day
     */
    private boolean isBeforeCurrent(Date dob){
        Date currentDate = new Date();
        int compare = dob.compareTo(currentDate);

        if (compare < 0) return true;
        return false;
    }

    /**
     Takes String input and returns its equivalent value in the
     enum Major class.
     @param major : String representation of the inputed major
     @return returns object from Major class depending on input
     */
    private Major getMajor(String major){
        switch(major){
            case "BAIT":
                return Major.BAIT;
            case "CS":
                return Major.CS;
            case "MATH":
                return Major.MATH;
            case "ITI":
                return Major.ITI;
            case "EE":
                return Major.EE;
            default:
                return null;
        }
    }

    /**
     Changes student major given the rest of the command from the user.
     Prints out error if major is invalid or if the student is not in the roster
     and success message if major was successfully changed
     @param st : StringTokenizer that contains the rest of the user command
     */
    private void handleChangeMajor(StringTokenizer st){
        String firstName = st.nextToken();
        String lastName = st.nextToken();

        String stringDate = st.nextToken();
        Date date = new Date(stringDate);

        String stringMajor = st.nextToken();
        Major major = getMajor(stringMajor);

        if (major == null){
            System.out.println("Major code invalid: " + stringMajor);
            return;
        }

        Student student = new Resident(new Profile(firstName, lastName, date), Major.CS, 100);
        if (this.roster.contains(student) == false){
            System.out.println(firstName + " " + lastName + " " + stringDate + " is not in the roster.");
            return;
        }

        System.out.println(firstName + " " + lastName + " " + stringDate + " major changed to " + stringMajor);
        this.roster.changeMajor(student, major);
    }

    private int validCredits(String number){
        int credits = 0;

        try { 
            credits = Integer.parseInt(number);
        } catch (Exception e){
            System.out.println("Credits completed invalid: not an integer!");
            return ERROR_CREDITS;
        }

        if (credits < 0){
            System.out.println("Credits completed invalid: cannot be negative!");
            return ERROR_CREDITS;
        }

        return credits;
    }

}
