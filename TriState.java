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
        if (creditsEnrolled >= 12) return super.tuitionDue(creditsEnrolled) - (state.toLowerCase().equals("ny") ? 4000 : 5000);
        return super.tuitionDue(creditsEnrolled);
    }
}
