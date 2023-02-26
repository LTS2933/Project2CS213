package src;

public class TriState extends NonResident { 
    private String state;
    
    private static final int VALID_CREDITS = 12;
    private static final int NY_TUITION = 4000;
    private static final int CT_TUITION = 5000;

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
        if (creditsEnrolled >= VALID_CREDITS) return super.tuitionDue(creditsEnrolled) - (state.toLowerCase().equals("ny") ? NY_TUITION : CT_TUITION);
        return super.tuitionDue(creditsEnrolled);
    }
}
