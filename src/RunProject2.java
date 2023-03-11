package Project2CS213.src;

import java.io.FileNotFoundException;

/**
 * Driver class to run Project 2
 * @author Christian Osma, Liam Smith
 */
public class RunProject2 {

    /**
     * Creates a new TuitionManager and calls the run method to start the project
     * @param args arrray of Strings
     * @throws FileNotFoundException
     */
    public static void main (String [] args) throws FileNotFoundException{
        new TuitionManager().run();
    }
}