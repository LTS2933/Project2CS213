public class TriState extends NonResident {
    private String state;

    public TriState(){
        super();
        state = null;
    }

    public TriState(Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
    }
    public TriState(String state){
        super();
        this.state = state;
    }

    @Override
    public double tuitionDue(int creditsEnrolled) {
        return super.tuitionDue(creditsEnrolled) - (state == "NY" ? 4000 : 5000);
    }

    @Override
    public String toString(){
        return super.toString() + " (tri-state:" + this.state + ")";
    }
}
