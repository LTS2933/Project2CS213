import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.StringTokenizer;

    public class TuitionManager {
        private static final int REQUIRED_ADD_TOKENS = 4;
        private static final int REQUIRED_TRISTATE_ADD_TOKENS = 4;
        private static final int REQUIRED_INTERNATIONAL_ADD_TOKENS = 5;
        private static final int REQUIRED_ENROLL_TOKENS = 4;
        private static final int REQUIRED_DROP_TOKENS = 3;
        private static final int MIN_SCHOLARSHIP = 1;
        private static final int MAX_SCHOLARSHIP = 10000;

        private Scanner sc;
        private Enrollment enrollment;
        private Roster roster;

        public TuitionManager() {
            this.sc = new Scanner(System.in);
            this.enrollment = new Enrollment();
            this.roster = new Roster();
        }

        public void run() {
            System.out.println("Tuition Manager running...");
            System.out.println();
            boolean running = true;
            while (running) {
                String line = sc.nextLine();
                StringTokenizer st = new StringTokenizer(line);
                if (st.countTokens() == 0) continue;

                String firstOperation = st.nextToken();
                switch (firstOperation) {
                    case "P":
                        roster.print();
                        break;
                    case "PS":
                        roster.printByStanding();
                        break;
                    case "PC":
                        roster.printBySchoolMajor();
                        break;
                    case "AR":
                        // add resident student
                        handleAddResident(st);
                        break;
                    case "AN":
                        // add non resident student
                        handleAddNonResident(st);
                        break;
                    case "AT":
                        // add tristate student
                        handleAddTristateStudent(st);
                        break;
                    case "AI":
                        // add international student
                        handleAddInternationalStudent(st);
                        break;
                    case "E":
                        // enroll a student
                        handleEnrollStudent(st);
                        break;
                    case "D":
                        // drop a student
                        handleDropStudent(st);
                        break;
                    case "S":
                        // award a scholarship
                       handleAwardScholarship(st);
                        break;
                    case "PE":
                        // display current enrollment list
                        this.enrollment.print();
                        break;
                    case "PT":
                        // display tuition due based on credits
                        if (this.enrollment.getSize() == 0){
                            System.out.println("Enrollment is empty!");
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
                                    DecimalFormat df = new DecimalFormat("#0.00");
                                    System.out.println(this.enrollment.returnEnrollStudent()[i].toString() + ": tuition due: " + df.format(tuition));
                                }
                            }
                        }
                        break;
                    case "LS":
                        // read from external file "studentList.txt"
                        break;
                    case "SE":
                        // semester end
                        for (int i = 0; i < this.enrollment.getSize(); i++){ //iterates through enrollment
                            for(int j = 0; j < this.roster.getSize(); j++){ //iterates through roster
                                Profile enroll =  this.enrollment.returnEnrollStudent()[i].getProfile(); //finds the profile through enrollment
                                Profile roster = this.roster.returnRoster()[j].getProfile(); //finds the profile through roster
                                if(enroll.equals(roster)) {
                                    int creditsEnrolled = this.enrollment.returnEnrollStudent()[i].getCreditsEnrolled();
                                    int creditsCompleted = this.roster.returnRoster()[j].getCredits();
                                    if (creditsCompleted + creditsEnrolled >= 120){
                                        System.out.println(enroll.toString());
                                    }
                                }
                        break;
                    case "Q":
                        System.out.println("Tuition Manager terminated.");
                        running = false;
                        break;
                    default:
                        System.out.println(firstOperation + " is an invalid command!");
                        break;
                }
            }
        }

        private void handleDropStudent(StringTokenizer st) {
            if (st.countTokens() < REQUIRED_DROP_TOKENS) {
                System.out.println("Missing data in line command.");
                return;
            }

            String firstName = st.nextToken();
            String lastName = st.nextToken();
            Date dob = new Date(st.nextToken());

            EnrollStudent enrollStudent = new EnrollStudent(new Profile(firstName, lastName, dob), 0);
            if (this.enrollment.contains(enrollStudent) == false) {
                System.out.println(enrollStudent.getProfile().toString() + " is not enrolled.");
                return;
            }

            this.enrollment.remove(enrollStudent);
            System.out.println(enrollStudent.getProfile().toString() + " dropped.");
        }

        private void handleEnrollStudent(StringTokenizer st) {
            if (st.countTokens() < REQUIRED_ENROLL_TOKENS) {
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
            } catch (Exception e) {
                System.out.println("Credits enrolled is not an integer.");
                return;
            }

            // change to any of student subclasses
            Student student = new Resident(new Profile(firstName, lastName, dob), Major.CS, credits);
            if (this.roster.contains(student) == false) {
                System.out.println("Cannot enroll: " + student.getProfile().toString() + " is not in the roster.");
                return;
            }

            EnrollStudent newStudent = new EnrollStudent(new Profile(firstName, lastName, dob), credits);
            this.enrollment.add(newStudent);
            System.out.println(newStudent.getProfile().toString() + " enrolled " + newStudent.getCreditCompleted() + " credits");
        }

        private void handleAddInternationalStudent(StringTokenizer st) {
            if (st.countTokens() < REQUIRED_INTERNATIONAL_ADD_TOKENS) {
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
            String token = st.nextToken();
            if (token != null && token.equals("true")) isStudyAbroad = true;

            boolean validDate = validDate(dob, stringDOB);
            if (validDate == false) return;

            Major major = getMajor(stringMajor.toUpperCase());
            if (major == null) {
                System.out.println("Major code invalid: " + stringMajor);
                return;
            }

            int credits = 0;
            try {
                credits = Integer.parseInt(stringCredits);
            } catch (Exception e) {
                System.out.println("Credits completed invalid: not an integer!");
            }
            if (credits < 0) {
                System.out.println("Credits completed invalid: cannot be negative!");
                return;
            }

            Student student = new International(isStudyAbroad, new Profile(firstName, lastName, dob), major, credits);
            if (this.roster.contains(student) == true) {
                System.out.println(student.getProfile().toString() + " is already in the roster.");
                return;
            }
            this.roster.add(student);
            System.out.println(student.getProfile().toString() + " added to the roster.");

        }

        private void handleAddTristateStudent(StringTokenizer st) {
            if (st.countTokens() < REQUIRED_TRISTATE_ADD_TOKENS) {
                System.out.println("Missing data in line command.");
                return;
            }
            String firstName = st.nextToken();
            String lastName = st.nextToken();
            String stringDOB = st.nextToken();
            Date dob = new Date(stringDOB);
            String stringMajor = st.nextToken();
            String stringCredits = st.nextToken();
            // no inputted state
            if (st.hasMoreTokens() == false) {
                System.out.println("Missing the state code.");
                return;
            }

            String state = st.nextToken();
            boolean validDate = validDate(dob, stringDOB);
            if (validDate == false) return;

            Major major = getMajor(stringMajor.toUpperCase());
            if (major == null) {
                System.out.println("Major code invalid: " + stringMajor);
                return;
            }

            int credits = 0;
            try {
                credits = Integer.parseInt(stringCredits);
            } catch (Exception e) {
                System.out.println("Credits completed invalid: not an integer!");
            }
            if (credits < 0) {
                System.out.println("Credits completed invalid: cannot be negative!");
                return;
            }
            if (state.toLowerCase().equals("ny") == false && state.toLowerCase().equals("ct") == false && state.toLowerCase().equals("nj") == false) {
                System.out.println(state + ": Invalid state code");
                return;
            }

            Student student = new TriState(state, new Profile(firstName, lastName, dob), major, credits);
            if (this.roster.contains(student) == true) {
                System.out.println(student.getProfile().toString() + " is already in the roster.");
                return;
            }
            this.roster.add(student);
            System.out.println(student.getProfile().toString() + " added to the roster.");
        }

        private void handleAddNonResident(StringTokenizer st) {
            if (st.countTokens() < REQUIRED_ADD_TOKENS) {
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
            if (major == null) {
                System.out.println("Major code invalid: " + stringMajor);
                return;
            }

            int credits = 0;
            try {
                credits = Integer.parseInt(stringCredits);
            } catch (Exception e) {
                System.out.println("Credits completed invalid: not an integer!");
            }
            if (credits < 0) {
                System.out.println("Credits completed invalid: cannot be negative!");
                return;
            }

            Student student = new NonResident(new Profile(firstName, lastName, dob), major, credits);
            if (this.roster.contains(student) == true) {
                System.out.println(student.getProfile().toString() + " is already in the roster.");
                return;
            }
            this.roster.add(student);
            System.out.println(student.getProfile().toString() + " added to the roster.");
        }

        private void handleAddResident(StringTokenizer st) {
            if (st.countTokens() < REQUIRED_ADD_TOKENS) {
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
            if (major == null) {
                System.out.println("Major code invlaid: " + stringMajor);
                return;
            }
            boolean validDate = validDate(dob, stringDOB);
            if (validDate == false) return;

            int credits = 0;
            try {
                credits = Integer.parseInt(stringCredits);
            } catch (Exception e) {
                System.out.println("Credits completed invalid: not an integer!");
                return;
            }
            if (credits < 0) {
                System.out.println("Credits completed invalid: cannot be negative!");
                return;
            }
            // change to resident student
            Student student = new Resident(new Profile(firstName, lastName, dob), major, credits);
            if (this.roster.contains(student)) {
                System.out.println(student.getProfile().toString() + " is already in the roster.");
                return;
            }
            this.roster.add(student);
            System.out.println(student.getProfile().toString() + " added to the roster.");
        }

        /**
         * Helper method that checks if the date is a valid of date of birth.
         *
         * @param date       Date object which represents the date to be checked
         * @param stringDate date inputted by the user, which is used to print error to standard output
         * @return true if the date is a valid date of birth and false otherwise
         */
        private boolean validDate(Date date, String stringDate) {
            if (date.isValid() == false) {
                System.out.println("DOB invalid: " + stringDate + " not a valid calendar date!");
                return false;
            }
            if (isBeforeCurrent(date) == false) {
                System.out.println("DOB invalid: " + stringDate + " is today or in the future!");
                return false;
            }
            if (date.getAge() < 16) {
                System.out.println("DOB invalid: " + stringDate + " younger than 16 years old.");
                return false;
            }
            return true;
        }

        /**
         * Takes in a student's date of birth and returns true if the student
         * was born before present and false otherwise
         *
         * @param dob : student's date of birth
         * @return true if student was born before present day
         */
        private boolean isBeforeCurrent(Date dob) {
            Date currentDate = new Date();
            int compare = dob.compareTo(currentDate);

            if (compare < 0) return true;
            return false;
        }

        /**
         * Takes String input and returns its equivalent value in the
         * enum Major class.
         *
         * @param major : String representation of the inputed major
         * @return returns object from Major class depending on input
         */
        private Major getMajor(String major) {
            switch (major) {
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
    }
