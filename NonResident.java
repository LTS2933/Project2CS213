public class NonResident extends Student {

    private static final int  tuitionNonResident = 33005;
    private static final int fullCredits = 16;

    private static final int minCredits = 12;
    private static final int partTimeNonResRate = 966;

    private static final double univFeePartTime = 2614.4;
    public NonResident(){
        super();
    }

    @Override
    public double tuitionDue(int creditsEnrolled) {
        if (creditsEnrolled < minCredits) return partTimeNonResRate * creditsEnrolled + univFeePartTime;
        if (creditsEnrolled > fullCredits) return (partTimeNonResRate * (creditsEnrolled - fullCredits) + tuitionNonResident);
        return tuitionNonResident;
    }

    @Override
    public boolean isResident() {
        return false;
    }
    @Override
    public String toString(){
        return super.toString() + " (non-resident)";
    }

}
