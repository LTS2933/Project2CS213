package src;

public class International extends NonResident{
    private static final int healthInsuranceFee = 2650;
    private static final int PART_TIME_CREDITS = 12;
    private static final int TUITION = 5918;

    private boolean isStudyAbroad;

    public International(Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
        this.isStudyAbroad = false;
    }

    public International(boolean isStudyAbroad, Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
        this.isStudyAbroad = isStudyAbroad;
    }

    public boolean getIsStudyAbroad(){
        return this.isStudyAbroad;
    }

    public void setIsStudyAbroad(boolean isStudyAbroad){
        this.isStudyAbroad = isStudyAbroad;
    }

    @Override
    public String toString(){
        String str = super.toString() + "(international";
        if (this.isStudyAbroad == true){
            str += ":study abroad)";
        } else {
            str += ")";
         }
        return str;
    }

    @Override
    public boolean isValid(int creditEnrolled){
        if (this.isStudyAbroad == true && creditEnrolled > PART_TIME_CREDITS) return false;
        if (this.isStudyAbroad == false && creditEnrolled < PART_TIME_CREDITS) return false;

        return true;
    }

    @Override
    public double tuitionDue(int creditsEnrolled){
        double tuition = 0;
        if (this.getIsStudyAbroad()) {
            tuition = TUITION;
        }
        else {
            tuition = super.tuitionDue(creditsEnrolled) + healthInsuranceFee;
        }
        return tuition;
    }
}
