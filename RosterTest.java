package src;

import static org.junit.Assert.*;
import org.junit.Test;

public class RosterTest { 

    @Test
    public void test_addInternationalStudent_True(){
        Roster roster = new Roster();
        International internationalStudent = new International(true, new Profile("April", "March", new Date("3/31/2000")), Major.BAIT, 0);
        assertTrue(roster.add(internationalStudent));
    }

    @Test
    public void test_addInternationalStudent_False(){
        Roster roster = new Roster();
        International internationalStudent = new International(false, new Profile("Mary", "Lindsey", new Date("12/1/2001")), Major.BAIT, 89);
        roster.add(internationalStudent);
        assertFalse(roster.add(internationalStudent));
    }

    @Test
    public void test_addTristateStudent_True(){
        Roster roster = new Roster();
        TriState tristateStudent = new TriState("NY", new Profile("Duke", "Ellington", new Date("2/29/2004")), Major.MATH, 46);
        assertTrue(roster.add(tristateStudent));
    }

    @Test 
    public void test_addTristateStudent_False(){
        Roster roster = new Roster();
        TriState tristateStudent = new TriState("CT", new Profile("Roy", "Brooks", new Date("8/8/1999")), Major.MATH, 111);
        roster.add(tristateStudent);
        assertFalse(roster.add(tristateStudent));
    }

    @Test 
    public void test_removeInternationalStudent_True(){
        Roster roster = new Roster();
        International internationalStudent = new International(false, new Profile("Bill", "Scanlan", new Date("5/1/1999")), Major.CS, 120);
        roster.add(internationalStudent);
        assertTrue(roster.remove(internationalStudent));
    }

    @Test 
    public void test_removeInternationalStudent_False(){
        Roster roster = new Roster();
        International internationalStudent = new International(false, new Profile("John", "Doe", new Date("4/3/2003")), Major.CS, 29);
        assertFalse(roster.remove(internationalStudent));
    }

    @Test
    public void test_removeTristateStudent_True(){
        Roster roster = new Roster();
        TriState tristateStudent = new TriState("NY", new Profile("John", "Doe", new Date("12/30/2004")), Major.EE, 45);
        roster.add(tristateStudent);
        assertTrue(roster.remove(tristateStudent));
    }

    @Test
    public void test_removeTristateStudent_False(){
        Roster roster = new Roster();
        TriState tristateStudent = new TriState("NY", new Profile("John", "Doe", new Date("12/30/2004")), Major.EE, 45);
        assertFalse(roster.remove(tristateStudent));
    }
    
}
