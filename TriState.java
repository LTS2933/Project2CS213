public class TriState extends NonResident {
    private String state;

    public TriState(){
        super();
        state = null;
    }
    public TriState(String state){
        super();
        this.state = state;
    }

    @Override
    public double tuitionDue(int creditsEnrolled) {
        // Christian: good idea to use ? and : but == means that they have the same pointer instead of the same character sequence
        // Christian: should be .equals("NY") instead
        return super.tuitionDue(creditsEnrolled) - (state == "NY" ? 4000 : 5000);
    }

    @Override
    public String toString(){
        // Christian: should be this.state.toUpperCase()
        return super.toString() + " (tri-state:" + this.state + ")";
    }
}
