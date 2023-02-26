package src;
import java.util.Calendar;

/**
 * This class implements the Comparable Interface and runs various methods on Date objects.
 * Some of these methods include getAge(), which returns an integer value of the current object's age in years.
 *
 * @author Christian Osma, Liam Smith
 **/
public class Date implements Comparable<Date>{
    public static final int QUADRENNIAL = 4; // constant
    public static final int CENTENNIAL = 100; // constant
    public static final int QUARTERCENTENNIAL = 400; // constant
    public static final int LONGER_DAYS_IN_MONTH = 31; // constant
    public static final int SMALLER_DAYS_IN_MONTH = 30; // constant
    public static final int NUM_MONTHS = 12; // constant
    public static final int DAYS_IN_FEBRUARY_LEAPYEAR = 29; // constant
    public static final int SMALLEST_MONTH_INDEX = 1; // constant
    public static final int SMALLEST_DAY_INDEX = 1; // constant
    public static final int LOWEST_VALID_YEAR = 1900; // constant
    public static final int COMPARE_TO_LESS_THAN = -1;
    public static final int COMPARE_TO_GREATER_THAN = 1;
    public static final int COMPARE_TO_EQUAL = 0;

    private int year;
    private int month;
    private int day;

    /**
     Constructor that initiates a new Date object with today's date (year
     month and day) using the Calendar class
     */
    public Date(){
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        this.year = year;
        this.month = month+1;
        this.day = day;
    }

    /**
     Overloaded constructor, instantiates new Date object based on date passed.
     @param string - String formatted like "2/24/2003", instantiates a Date object from this String.
     */
    public Date(String string){
        String [] array = string.split("/");
        this.month = Integer.parseInt(array[0]);
        this.day = Integer.parseInt(array[1]);
        this.year = Integer.parseInt(array[2]);
    }

    /**
     Getter method, returns the year of the current object as an int type.
     @return year of current Date object
     */
    public int getYear(){
        return this.year;
    }

    /**
     Getter method, returns the month of the current object as an int, with January indexed as 0.
     @return month of current Date object as an int type
     */
    public int getMonth(){
        return this.month;
    }

    /**
     Getter method, returns the day of the current object as an int.
     @return day of current Date object as an int type.
     */
    public int getDay(){
        return this.day;
    }

    /**
     Checks if two dates are equivalent.
     @param obj to be compared with current Date object.
     @return true if the 2 dates are exactly the same (day, month, year), false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        Date compare = null;
        if (obj instanceof Date){
            compare = (Date) obj;
        }
        return compare.getDay() == this.day && compare.getMonth() == this.month && compare.getYear() == this.year;
    }

    /**
     Compares the current Date object with another Date object passed as an argument
     @param obj : Date object to be compared with current Date object
     @return 1 if the current Date object occurs later in time than the Date object parameter,
     -1 if the parameters Date object occurs later in time than this Date object,
     and 0 if the 2 Date objects are the same date.
     */
    @Override
    public int compareTo(Date obj){
        if (this.year == obj.year && this.day == obj.day && this.month == obj.month) return COMPARE_TO_EQUAL;
        if (this.year < obj.year) return COMPARE_TO_LESS_THAN;
        if (this.year <= obj.year && this.month < obj.month) return COMPARE_TO_LESS_THAN;
        if (this.year <= obj.year && this.month <= obj.month && this.day < obj.day ) return COMPARE_TO_LESS_THAN;

        return COMPARE_TO_GREATER_THAN;
    }

    /**
     Converts the current Date object to a String.
     @return String, formatted as "1/20/2018" for January 20, 2018, for example.
     */
    @Override
    public String toString(){
        return this.month + "/" + this.day + "/" + this.year;
    }

    /**
     Compares the current day (through Calendar import package) with whatever Date object is calling the method and return's the age of the Student.
     @return int corresponding to the Date object's age in years.
     */
    public int getAge(){
        Date date = new Date();

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();


        int age = year - this.getYear();
        if ((this.getMonth() > month || this.getMonth() == month && this.getDay() > day))
        {
            age--;
        }
        return age;
    }

    /**
     Checks whether the current Date object is a valid calendar date
     @return boolean to confirm/deny whether the Date is a valid calendar date.
     */
    public boolean isValid() {
        if ((this.getDay() > LONGER_DAYS_IN_MONTH) || this.getMonth() > NUM_MONTHS || this.getMonth() < SMALLEST_MONTH_INDEX || this.getDay() < SMALLEST_DAY_INDEX || this.getYear() < LOWEST_VALID_YEAR) {
            return false;
        }
        if ((this.getMonth() == (Calendar.FEBRUARY+1)) || (this.getMonth() == (Calendar.APRIL +1)) || (this.getMonth() == (Calendar.JUNE +1)) ||
                (this.getMonth() == (Calendar.SEPTEMBER +1)) || (this.getMonth() == (Calendar.NOVEMBER +1))) {
            if (this.getDay() > SMALLER_DAYS_IN_MONTH) {
                return false;
            }
        }
        if (this.getMonth() == (Calendar.FEBRUARY+1)) {
            if (this.getDay() > DAYS_IN_FEBRUARY_LEAPYEAR) {
                return false;
            }
            if (this.getDay() == DAYS_IN_FEBRUARY_LEAPYEAR) {
                if (this.getYear() % QUADRENNIAL == 0) {
                    if (this.getYear() % CENTENNIAL == 0) {
                        if (this.getYear() % QUARTERCENTENNIAL == 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Testbed main for testing the isValid() method of Date class
     * @param args array of Strings
     */
    public static void main (String [] args){
        // five invalid dates
        Date d1 = new Date("2/29/2003");
        Date d2 = new Date("4/31/2003");
        Date d3 = new Date("13/31/2003");
        Date d4 = new Date("3/32/2003");
        Date d5 = new Date("-1/31/2003");
        // five invalid dates
        Date d6 = new Date("4/6/2003");
        Date d7 = new Date("1/20/2003");

        System.out.println(d1.isValid());
        System.out.println(d2.isValid());
        System.out.println(d3.isValid());
        System.out.println(d4.isValid());
        System.out.println(d5.isValid());
        System.out.println(d6.isValid());
        System.out.println(d7.isValid());
    }
}
