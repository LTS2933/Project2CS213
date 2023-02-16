public class Enrollment { 
    private EnrollStudent[] students;
    private int size;

    public Enrollment(){
        this.students = new EnrollStudent[4];
        this.size = 0;
    }

    public void add (EnrollStudent enrollStudent){
        if (this.size == students.length) grow();
        this.students[this.size] = enrollStudent;
        this.size++;
    }

    private void grow(){
        EnrollStudent [] newStudents = new EnrollStudent[this.students.length+4];
        for (int i = 0; i<this.students.length; i++){
            newStudents[i] = this.students[i];
        }
        this.students = newStudents;
    }

    public void remove(EnrollStudent enrollStudent){
        int index = -1;
        for (int i = 0; i<this.size; i++){
            if (this.students[i].equals(enrollStudent) == true){
                this.students[i] = null;
                index = i;
                break;
            }
        }
        if (index == -1) return;

        for (int i = index; i<this.size-1; i++){
            this.students[i] = this.students[i+1];
        }
        this.size--;
    }

    public boolean contains(EnrollStudent enrollStudent){
        for (int i = 0; i<this.size; i++){
            if (this.students[i].equals(enrollStudent) == true){
                return true;
            }
        }
        return false;
    }

    public void print(){
        if (this.size == 0){
            System.out.println("Enrollment is empty!");
            return;
        }
        System.out.println("** Enrollment **");
        for (int i = 0; i<this.size; i++){
            System.out.println(this.students[i].toString());
        }
        System.out.println("* end of enrollment *");
    }
}
