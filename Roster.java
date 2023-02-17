 /**
     Contains array data structure that contains list of students in the
     Roster. Grows automatically in increments of 4 if the array is full
     and contains methods that interact with the list of students.
     @author Christian Osma, Liam Smith
     */
    public class Roster {
        private static final int NOT_FOUND = -1;

        private Student [] roster;
        private int size;

        /**
         Initializes student roster to an array of size 4 and current
         size of 0.
         */
        public Roster(){
            this.roster = new Student[4];
            this.size = 0;
        }

        /**
         This method loops through the list of students and looks for the
         specific student in the params. Returns index or -1 if the
         student is not found.
         @param student : student to be found in the roster
         @return index of the student in the array of -1 if not found
         */
        public int find(Student student){
            for (int i = 0; i<this.size; i++){
                if (student.equals(roster[i])) return i;
            }
            return NOT_FOUND;
        }

        /**
         Creates a new array that contains 4 more elements than
         the current list and copies the students into the new array.
         */
        private void grow(){
            Student [] newRoster = new Student[roster.length+4];
            for (int i = 0; i<this.size; i++){
                newRoster[i] = roster[i];
            }
            this.roster = newRoster;
        }

        /**
         Takes the student from the params and adds that student
         to the roster.
         @param student : student to be added to the roster
         @return true if the student has been added
         */
        public boolean add (Student student){
            if (this.size == roster.length) grow();
            this.roster[this.size] = student;
            this.size++;
            return true;
        }

        /**
         Removes the specific student from the roster and returns true
         if it has succeeded. Returns false if the student was not found.
         @param student : student to be removed from the roster
         @return true if the student has been removed
         */
        public boolean remove (Student student){
            int indexOfStudent = find(student);
            if (indexOfStudent == -1) return false;

            roster[indexOfStudent] = null;
            for (int i = indexOfStudent; i<this.size-1; i++){
                roster[i] = roster[i+1];
            }
            this.size--;
            return true;
        }

        /**
         Method returns true if the student was found in the roster
         and false otherwise. Calls the find() method to find the student.
         @param student : student to be found in the roster
         @return true if the student is found in the roster
         */
        public boolean contains (Student student){
            int index = find(student);
            if (index == -1) return false;
            return true;
        }

        /**
         Prints the roster in standard output sorted by the student's
         Profile (last name, first name, and date of birth).
         */
        public void print(){
            if (this.size == 0){
                System.out.println("Student roster is empty!");
                return;
            }
            for (int i = 0; i<this.size-1; i++){
                Student minProfile = this.roster[i];
                int minIndex = i;
                for (int j = i+1; j<this.size ; j++){
                    if (this.roster[j].compareTo(minProfile) < 0){
                        minProfile = this.roster[j];
                        minIndex = j;
                    }
                }
                Student temp = this.roster[minIndex];
                this.roster[minIndex] = this.roster[i];
                this.roster[i] = temp;
            }
            System.out.println("* Student roster sorted by last name, first name, DOB **");
            for (int i = 0; i<this.size; i++){
                System.out.println(this.roster[i].toString());
            }
            System.out.println("* end of roster **");

        }

        /**
         Prints roster to standard output sorted by school, major.
         */
        public void printBySchoolMajor(){
            if (this.size == 0){
                System.out.println("Student roster is empty!");
                return;
            }
            int minIndex = 0;
            for (Major major: Major.values()){
                for (int i = minIndex; i<this.size; i++){
                    Student currStudent = this.roster[i];
                    Major studentMajor = currStudent.getMajor();
                    if (studentMajor == major){
                        Student temp = this.roster[minIndex];
                        this.roster[minIndex] = currStudent;
                        this.roster[i] = temp;
                        minIndex++;
                    }
                }
            }
            System.out.println("* Student roster sorted by school, major **");
            for (int i = 0; i<this.size; i++){
                System.out.println(roster[i].toString());
            }
            System.out.println("* end of roster **");
        }

        /**
         Prints roster to standard output sorted by standing
         */
        public void printByStanding(){
            if (this.size == 0){
                System.out.println("Student roster is empty!");
                return;
            }
            int index = 0;
            for (int i = index+1; i<this.size; i++){
                if (this.roster[i].getCredits() < 30){
                    Student temp = this.roster[i];
                    this.roster[i] = this.roster[index];
                    this.roster[index] = temp;
                    index++;
                }
            }
            for (int i = index; i<this.size; i++){
                if (this.roster[i].getCredits() >= 60 && this.roster[i].getCredits() < 90){
                    Student temp = this.roster[i];
                    this.roster[i] = this.roster[index];
                    this.roster[index] = temp;
                    index++;
                }
            }
            for (int i = index; i<this.size; i++){
                if (this.roster[i].getCredits() >= 90){
                    Student temp = this.roster[i];
                    this.roster[i] = this.roster[index];
                    this.roster[index] = temp;
                    index++;
                }
            }
            System.out.println("* Student roster sorted by standing **");
            for (int i = 0; i<this.size; i++) System.out.println(this.roster[i].toString());
            System.out.println("* end of roster **");
        }

        /**
         Returns a list of students that attend the corresponding
         school passed into the method, sorted by Profile.
         @param string : String representation of the school
         @return Student array of students that attend that specific school
         */
        public Student [] getBySchool(String string){
            if (this.size == 0) return null;

            int schoolSize = 0;
            String school = string.toUpperCase();
            for (int i = 0; i<this.size; i++){
                if (this.roster[i].getMajor().getSchoolName().equals(school)) schoolSize++;
            }
            Student [] array = new Student[schoolSize];
            int index = 0;
            for (int i = 0; i<this.size; i++){
                if (this.roster[i].getMajor().getSchoolName().equals(school)){
                    array[index] = this.roster[i];
                    index++;
                }
            }
            for (int i = 0; i<schoolSize-1; i++){
                Student minStudent = array[i];
                int minIndex = i;
                for (int j = i+1; j<schoolSize; j++){
                    if (array[j].compareTo(minStudent) < 0){
                        minStudent = array[j];
                        minIndex = j;
                    }
                }
                Student temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
            }
            return array;
        }

        /**
         Changes the inputed Student's major to the Major that was
         also given to the method.
         @param student : the student that wants their major changed
         @param major : the major the student wants to change into
         */
        public void changeMajor(Student student, Major major){
            int indexOfStudent = find(student);
            this.roster[indexOfStudent].setMajor(major);
        }

        public int getSize(){
            return this.size;
        }

        public Student[] returnRoster(){
            return this.roster;
        }

    }

