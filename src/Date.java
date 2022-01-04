import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
* This class represents 2 dates, the check-in and the check-out date of a reservation. 
* The values startDay, startMonth and startYear are the check-in date of a reservation. 
* The values endDay, endMonth and endYear are the check-out date of a reservation.
*/

public class Date implements Serializable {
    private int startDay;//the day of the month of the check-in
    private int startMonth;//the month of the check-in
    private int startYear;//the year of the check-in
    private int endDay;//the day of the month of the check-out
    private int endMonth;//the month of the check-out
    private int endYear;//the year of the check-out
    
    /**
    * This in a constructor who sets the fields of the class. 
    * @param startDay is the day of the month of the check-in date.
    * @param startMonth is the month of the check-in date. 
    * @param startYear is the year of the check-in date.
    * @param endDay is the day of the month of the check-out date.
    * @param endMonth is the month of the check-out date. 
    * @param endYear is the year of the check-out date
    */
    public Date(int startDay,int startMonth, int startYear, int endDay, int endMonth, int endYear)
    {
        this.startDay = startDay;
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.endYear = endYear;
    }
    
    /**
    * This is an empty constructor
    */
    public Date()
    {
    }
    
    /**
    * This is a method that returns the day of the check-in date. 
    * @return the day of the month of the check-in date. 
    */
    public int getStartDay(){
        return startDay;
    }
    
    /**
    * This is a method that returns the year of the check-in date. 
    * @return the year of the check-in date. 
    */
    public int getStartYear(){
        return startYear;
    }
    /**
     * this method returns true when the object that uses this method is after the parameter date1(based on calendar)
     * otherwise it returns false
     * @param se means start/end and refers to the check-in date (startDay,startMonth,startYear) or the
     * check-out(endDay,endMonth,endYear) of the object that uses this method
     * @param se1 means start/end and refers to the check-in date (startDay,startMonth,startYear) or the check-out
     * of the parameter date1
     * @param date1 is the date we want to compare with the object that uses this method
     */ 
    public boolean isAfter(String se, Date date1, String se1) 
    {
        if(se.equals("start"))
        {
            if(se1.equals("start"))
            {
                return (this.startYear > date1.startYear) || ((this.startYear == date1.startYear) && (this.startMonth > date1.startMonth)) || ((this.startYear == date1.startYear) && (this.startMonth == date1.startMonth) && (this.startDay > date1.startDay));
                
            }
            else if(se1.equals("end"))
            {
                return (this.startYear > date1.endYear) || ((this.startYear == date1.endYear) && (this.startMonth > date1.endMonth)) || ((this.startYear == date1.endYear) && (this.startMonth == date1.endMonth) && (this.startDay > date1.endDay));
            }
            else
            {
                return false;
            }
        }
        else if(se.equals("end"))
        {
            if(se1.equals("start"))
            {
                return (this.endYear > date1.startYear) || ((this.endYear == date1.startYear) && (this.endMonth > date1.startMonth)) || ((this.endYear == date1.startYear) && (this.endMonth == date1.startMonth) && (this.endDay > date1.startDay));
            }
            else if(se1.equals("end"))
            {
                return (this.endYear > date1.endYear) || ((this.endYear == date1.endYear) && (this.endMonth > date1.endMonth)) || ((this.endYear == date1.endYear) && (this.endMonth == date1.endMonth) && (this.endDay > date1.endDay));
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    /**
     * this method returns true when the object that uses this method is before the parameter date1(based on calendar)
     * otherwise it returns false
     * @param se means start/end and refers to the check-in date (startDay,startMonth,startYear) or the
     * check-out(endDay,endMonth,endYear) of the object that uses this method
     * @param se1 means start/end and refers to the check-in date (startDay,startMonth,startYear) or the check-out
     * of the parameter date1
     * param@ date1 is the date we want to compare with the object that uses this method
     */ 
    public boolean isBefore(String se, Date date1, String se1) 
    {
        if(se.equals("start"))
        {
            if(se1.equals("start"))
            {
                return (this.startYear < date1.startYear) || ((this.startYear == date1.startYear) && (this.startMonth < date1.startMonth)) || ((this.startYear == date1.startYear) && (this.startMonth == date1.startMonth) && (this.startDay < date1.startDay));
            }
            else if(se1.equals("end"))
            {
                return (this.startYear < date1.endYear) || ((this.startYear == date1.endYear) && (this.startMonth < date1.endMonth)) || ((this.startYear == date1.endYear) && (this.startMonth == date1.endMonth) && (this.startDay < date1.endDay));
            }
            else
            {
                return false;
            }
        }
        else if(se.equals("end"))
        {
            if(se1.equals("start"))
            {
                return (this.endYear < date1.startYear) || ((this.endYear == date1.startYear) && (this.endMonth < date1.startMonth)) || ((this.endYear == date1.startYear) && (this.endMonth == date1.startMonth) && (this.endDay < date1.startDay));
            }
            else if(se1.equals("end"))
            {
                return (this.endYear < date1.endYear) || ((this.endYear == date1.endYear) && (this.endMonth < date1.endMonth)) || ((this.endYear == date1.endYear) && (this.endMonth == date1.endMonth) && (this.endDay < date1.endDay));
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    /**
     * this method returns true when the object that uses this method is the same date as parameter date1(based on calendar)
     * otherwise it returns false
     * @param se means start/end and refers to the check-in date (startDay,startMonth,startYear) or the
     * check-out(endDay,endMonth,endYear) of the object that uses this method
     * @param se1 means start/end and refers to the check-in date (startDay,startMonth,startYear) or the check-out
     * of the parameter date1
     * param@ date1 is the date we want to compare with the object that uses this method
     */ 
    public boolean isEqual(String se, Date date1, String se1)
    {
        if(se.equals("start"))
        {
            if(se1.equals("start"))
            {
                return (this.startYear == date1.startYear) && (this.startMonth == date1.startMonth) && (this.startDay == date1.startDay);
            }
            else if(se1.equals("end"))
            {
                return (this.startYear == date1.endYear) && (this.startMonth == date1.endMonth) && (this.startDay == date1.endDay);
            }
            else
            {
                return false;
            }
        }
        else if(se.equals("end"))
        {
            if(se1.equals("start"))
            {
                return (this.endYear == date1.startYear) && (this.endMonth == date1.startMonth) && (this.endDay == date1.startDay);
            }
            else if(se1.equals("end"))
            {
                return (this.endYear == date1.endYear) && (this.endMonth == date1.endMonth) && (this.endDay == date1.endDay);
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    /**
    * This is method that returns the number of nights between the check-in and check-out dates. 
    * @return how many night are between the check-in date(startDay,startMonth,startYear) and the check-out date (endDay,endMonth,endYear)
    */
    public int nightsBetweenDates()
    {
        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
	    LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);
	    int nights;
	    nights = (int)ChronoUnit.DAYS.between(startDate, endDate);
	    return nights;
    }

    /**
    * This method creates and returns a string of the reservation dates. 
    * It's useful when we want to print the reservation dates. 
    * @return the reservation dates as a string in the form day/month/year-day/month/year. 
    */
    public String getDatesAsString()
    {
        String dates;
        dates = startDay + "/"+ startMonth +"/"+ startYear +"-"+ endDay + "/"+ endMonth +"/"+ endYear;
        return dates;
    }


    /**
     * This method checks if a year is a leap year which means that February has 29 days.
     * This method is useful for when the customer enters the check-in & check out dates
     * @param year The year we want to see if it is leap or not
     * @return True for leap year else false
     */

    public boolean isLeapYear(int year){
        boolean isLeap = false;
        if(year%4 == 0){
            if(year%100 == 0){
                if(year%400 == 0){
                    isLeap = true;
                }
            }
            else{
                isLeap = true;
            }
        }
        return isLeap;
    }

    /**
     * This method checks if the dates that are given from the customer for check-in & check-out are correct.
     * @return True if the dates are correct and false when the dates are wrong
     */
    public boolean checkingTheDates(){
        LocalDate dateNow = LocalDate.now();
        boolean correctDates = (startMonth >= 1 && startMonth <= 12) && (endMonth >= 1 && endMonth <= 12);
        if(startYear < dateNow.getYear() || (startYear == dateNow.getYear() && startMonth < dateNow.getMonthValue()) || (startYear == dateNow.getYear() && startMonth == dateNow.getMonthValue() && startDay < dateNow.getDayOfMonth()) ){
            correctDates = false;
        }
        if((startDay<1 || startDay>31) || (endDay<1 || endDay>31)){
            correctDates = false;
        }
        if(startYear>endYear){
            correctDates = false;
        }
        if((startYear == endYear) && (startMonth>endMonth)){
            correctDates = false;
        }
        if((startYear == endYear) && (startMonth==endMonth) && (startDay>endDay)){
            correctDates = false;
        }
        if(startMonth == 4 || startMonth == 6 || startMonth == 9 || startMonth == 11){
            if(endDay>30){
                correctDates = false;
            }
        }
        if(endMonth == 4 || endMonth == 6 || endMonth == 9 || endMonth == 11){
            if(endDay>30){
                correctDates = false;
            }
        }
        if(startMonth == 2){
            if(isLeapYear(startYear)){
                if(startDay>29){
                    correctDates = false;
                }
            }
            else{
                if(startDay>28){
                    correctDates = false;
                }
            }
        }
        if(endMonth == 2){
            if(isLeapYear(endYear)){
                if(endDay>29){
                    correctDates = false;
                }
            }
            else{
                if(endDay>28){
                    correctDates = false;
                }
            }
        }
        return correctDates;

    }

}
