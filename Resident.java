public class Resident extends Student {
    private int scholarship;
    /*

     */

    private static final int  tuitionResident = 15804;
    private static final int fullCredits = 16;
    private static final int minCredits = 12;
    private static final int partTimeResRate = 404;
    private static final double univFeePartTime = 2614.4;
    public Resident(){
        super();
    }
    public Resident(int scholarship){
        super();
        this.scholarship = scholarship;
    }

    @Override
    public double tuitionDue(int creditsEnrolled) {
        if (creditsEnrolled < minCredits) return (partTimeResRate * creditsEnrolled) + univFeePartTime;
        if (creditsEnrolled > fullCredits) return (partTimeResRate * (creditsEnrolled - fullCredits)) + tuitionResident - scholarship;
            return tuitionResident - scholarship;
    }
    public double tuitionDue(int creditsEnrolled, int scholarship) {
        if (creditsEnrolled > minCredits) return (partTimeResRate * (creditsEnrolled - minCredits)) + tuitionResident;
        return tuitionResident;
    }

    @Override
    public boolean isResident() {
        return true;
    }

    @Override
    public String toString(){
        return super.toString() + " (resident)";
    }
    /*

     */

}
