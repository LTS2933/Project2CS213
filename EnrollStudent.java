public class EnrollStudent {
    private Profile profile;
    private int creditCompleted;

    public EnrollStudent(){
        this.profile = new Profile();
        this.creditCompleted = 0;
    }

    public EnrollStudent(Profile profile, int creditCompleted){
        this.profile = profile;
        this.creditCompleted = creditCompleted;
    }

    public Profile getProfile(){
        return this.profile;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }

    public int getCreditCompleted(){
        return this.creditCompleted;
    }

    public void setCreditCompleted(int creditCompleted){
        this.creditCompleted = creditCompleted;
    }

    @Override
    public boolean equals(Object obj){
        EnrollStudent compare = null;
        if (compare instanceof EnrollStudent){
            compare = (EnrollStudent) obj;
        }
        return compare.profile.equals(this.profile) && compare.creditCompleted == this.creditCompleted;
    }

    @Override
    public String toString(){
        return this.profile.toString() + ": credits enrolled: " + this.creditCompleted;
    }

    public static void main (String [] args){
        Profile pr = new Profile("Sunny", "Patel", new Date("9/2/2005"));
        EnrollStudent student = new EnrollStudent(pr, 20);
        System.out.println(student.toString());
    }
    
}
