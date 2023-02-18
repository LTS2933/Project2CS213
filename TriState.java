package src;

public class TriState extends NonResident { 
    private String state;

    public TriState(String state, Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
        this.state = state;
    }

    public String getState(){
        return this.state;
    }

    public void setState(String state){
        this.state = state;
    }

    @Override
    public String toString(){
        return super.toString() + "(tri-state:" + state.toUpperCase() + ")";
    }

    @Override
    public double tuitionDue(int creditsEnrolled) {
        return super.tuitionDue(creditsEnrolled) - (state.equals("NY") ? 4000 : 5000);
    }
}
