import java.io.Serializable;
import java.util.HashSet;

/**
 * This class represents a room of accommodation.
 * It contains a HashSet that has reservation dates of a room. It has also the price of the room per night(pricePerNight)
 * and the number of people that can stay in the room (capacity)
 */
public class Room implements Serializable
{
    private HashSet<Date> reservedDates;
    private int capacity;
    private double pricePerNight;
    private String view;
    private int totalBeds;

    /**
     * This is a constructor that creates an empty HashSet.
     */
    public Room()
    {
        reservedDates = new HashSet<>();
    }

    /**
     * This is a constructor that sets the fields of the class.
     * @param capacity is for the number of people that can stay in the room. 
     * @param pricePerNight is the price of the room per night. 
     */ 
    public Room(int capacity,double pricePerNight,String view,int totalBeds)
    {
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.view = view;
        this.totalBeds = totalBeds;
        reservedDates = new HashSet<>();
    }

    /**
     * This method is a getter for the pricePerNight. 
     * @return pricePerNight the price of the room. 
     */
    public String getView(){
        return view;
    }

    /**
     * @return The total number of beds of the room. For example if the capacity of the room is 2 it could have
     * 2 single beds or one double bed.
     */
    public int getTotalBeds(){
        return totalBeds;
    }

    /**
     * @return The price of the room for one night
     */
    public double getPricePerNight()
    { 
        return pricePerNight; 
    }
    
    /**
     * This method sets the price of the room. 
     * @param pricePerNight is the price of the room. 
     */ 
    public void setPricePerNight(double pricePerNight)
    {
        this.pricePerNight = pricePerNight; 
    }
    
    /**
     * This method is a getter for the capacity. 
     * @return capacity the number of people that can stay in the room. 
     */
    public int getCapacity()
    { 
        return capacity; 
    }
    
    /**
     * This method sets the capacity of the room. 
     * @param capacity is the number of people that can stay in the room. 
     */
    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    /**
     * This method checks if the given Date coincides with any Date in the HashSet or not.
     * @param date is the date we want to check before inserting it to the HashSet.
     * @return true if the parameter date doesn't coincide with any Date object in the HashSet.
     */
    public boolean availableDate(Date date)
    {
        if(reservedDates == null)
        {
            return true;
        }
        else
        {
            int biggestDate=1;//1 means that the date we check is the biggest
            int smallestDate=1;//1 means that the date we check is the smallest
            for (Date HashSetDate : reservedDates)
            {
                
                //if there is any date in the HashSet after the date we want to check then the date 
                //is not the biggest so the variable biggestDate becomes 0
                if(date.isBefore("start", HashSetDate, "end"))
                {
                    biggestDate=0;
                }
                //if there is any date in the HashSet before the date we want to check then the date 
                //is not the smallest so the variable smallestDate becomes 0
                if(date.isAfter("end", HashSetDate, "start"))
                {
                    smallestDate=0;
                }
            }
            if(biggestDate==1 || smallestDate==1)
            {
                return true;//returns true if the date that we checked is before or after every other reserved date
            }
            else//the date is somewhere between the Dates in the HashSet
            {
                Date tempDate = new Date(0,0,0,0,0,0);
                //it searches in the HashMap the biggest check-out date that is before the check-in date of the 
                //date we want to insert. That date is being saved in "tempDate"
                for (Date HashSetDate1 : reservedDates)
                {
                    if(date.isAfter("start", HashSetDate1, "end"))
                    {
                        if(HashSetDate1.isAfter("end",tempDate,"end"))
                        {
                            tempDate = HashSetDate1;
                        }
                    }
                }
                if(tempDate.getStartDay()==0)//if in the previous search didn't find anything
                {
                    return false;
                }
                else
                {
                    Date tempDate1 = new Date(100,100,4000,100,100,4000);
                    //it searches in the HashMap the smallest check-in date that is after the check-out date of the 
                    //temp date. That date is being saved in "tempDate1"
                    for (Date HashSetDate2 : reservedDates)
                    {
                        if(tempDate.isBefore("end", HashSetDate2, "start"))
                        {
                            if(HashSetDate2.isBefore("start", tempDate1,"start"))
                            {
                                tempDate1 = HashSetDate2;
                            }
                        }
                    }
                    if(tempDate.getStartYear()==4000)//if in the previous search didn't find anything
                    {
                        return false;
                    }
                    else
                    {
                        //if the check-in date of we want to insert is after the tempDate and the if the 
                        //check-out date that we want to insert is before the tempDate1
                        return date.isAfter("start", tempDate, "end") && date.isBefore("end", tempDate1, "start");
                    }
                }
            }
        }
    }
    
    /**
     * This method adds a new reservation to the HashSet with the dates. 
     * @param date refers to a date that we want to insert in the HashSet with the dates. 
     */
    public void addReservedDates(Date date)
    {
        if(reservedDates == null)
        {
            reservedDates = new HashSet<>();
            reservedDates.add(date);
        }
        else if(availableDate(date))//checks if the date can be inserted
        {
            reservedDates.add(date);
        }

    }
    
    /**
     * This method cancels a room's reservation.
     * It removes a Date object from the HashSet reservedDates. 
     * @param date is the reservation dates that is being removed. 
     */
    public void removeReservedDates(Date date)
    {
         reservedDates.remove(date);
    }

    /**
     * This method changes the number of beds of a room
     * @param number The new number of beds
     */
    public void setTotalBeds(int number){
        this.totalBeds = number;
    }
}
