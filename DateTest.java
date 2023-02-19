package src;

import static org.junit.Assert.*;
import org.junit.Test;

public class DateTest { 

    @Test
    public void test_invalidFebruaryDate(){
        Date date = new Date("2/29/2003");
        assertFalse(date.isValid());
    }

    @Test
    public void test_invalidAprilDate(){
        Date date = new Date("4/31/2003");
        assertFalse(date.isValid());
    }

    @Test
    public void test_invalidMonth_Above(){
        Date date = new Date("13/31/2003");
        assertFalse(date.isValid());
    }

    @Test
    public void test_invalidMarchDate(){
        Date date = new Date("3/32/2003");
        assertFalse(date.isValid());
    }

    @Test
    public void test_invalidMonth_Below(){
        Date date = new Date("-1/31/2003");
        assertFalse(date.isValid());
    }

    @Test
    public void test_validAprilDate(){
        Date date = new Date("4/6/2003");
        assertTrue(date.isValid());
    }

    @Test
    public void test_validJanuaryDate(){
        Date date = new Date("1/20/2003");
        assertTrue(date.isValid());
    }

}
