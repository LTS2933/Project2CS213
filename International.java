public class International extends NonResident {
    private boolean isStudyAbroad;

    private static final int healthInsuranceFee = 2650;
    private static final int studyAbroadCost = 5918;

    public International(){
        super();
    }
    public International(Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
    }
    public International(boolean isStudyAbroad){
        super();
        this.isStudyAbroad = isStudyAbroad;
    }
    public boolean getIsStudyAbroad(){
        return this.isStudyAbroad;
    }
    @Override
    public double tuitionDue(int creditsEnrolled){
        double tuition = 0;
        if (this.getIsStudyAbroad()) {
            tuition = 5918;
        }
        else {
            tuition = super.tuitionDue(creditsEnrolled) + healthInsuranceFee;
        }
        return tuition;
    }
    @Override
    public String toString(){
        return super.toString() + " (international)";
    }

}
