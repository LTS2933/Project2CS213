import org.junit.Test;
import static org.junit.Assert.*;

public class InternationalTest {

    @Test
    public void test_tuitionDue_International_isStudyAbroad_false() {
        Date dob = new Date(1,1,2001);
        Profile profile =  new Profile("Ava", "Lin", dob);
        Major major = Major.CS;
        International i1 = new International(false, profile, major, 50);
        double tuition = i1.tuitionDue(12);
        assertTrue(tuition == 35655.0);
    }

    @Test
    public void test_tuitionDue_International_isStudyAbroad_true(){
        Date dob = new Date(30,6,2002);
        Profile profile =  new Profile("Mia", "Diaz", dob);
        Major major = Major.CS;
        International i1 = new International(true, profile, major, 50);
        double tuition = i1.tuitionDue(9);
        assertTrue(tuition == 5918.0);
    }
}