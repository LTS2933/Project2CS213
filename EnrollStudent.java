public class EnrollStudent {
    private Profile profile;
    private int creditsEnrolled;

    public EnrollStudent(){
        this.profile = new Profile();
        this.creditsEnrolled = 0;
    }

    public EnrollStudent(Profile profile, int creditsEnrolled){
        this.profile = profile;
        this.creditsEnrolled = creditsEnrolled;
    }

    public Profile getProfile(){
        return this.profile;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }

    public int getCreditsEnrolled(){
        return this.creditsEnrolled;
    }

    public void setCreditsEnrolled(int creditsEnrolled){
        this.creditsEnrolled = creditsEnrolled;
    }

    @Override
    public boolean equals(Object obj){
        EnrollStudent compare = null;
        if (compare instanceof EnrollStudent){
            compare = (EnrollStudent) obj;
        }
        return compare.profile.equals(this.profile) && compare.creditsEnrolled == this.creditsEnrolled;
    }

    @Override
    public String toString(){
        return this.profile.toString() + ": credits enrolled: " + this.creditsEnrolled;
    }

    public static void main (String [] args){
        Profile pr = new Profile("Sunny", "Patel", new Date("9/2/2005"));
        EnrollStudent student = new EnrollStudent(pr, 20);
        System.out.println(student.toString());
    }

}
