import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents an accommodation.
 * It has some characteristics like what type is it, the location, the name, if it has parking or Wi-Fi.
 * It has an ArrayList that keeps all the accommodation's rooms.
 */
public class Accommodation implements Serializable {
    private Provider provider;
    private String name;
    private String type;
    private boolean hasWifi;
    private boolean hasParking;
    private Location location;
    private boolean hasPool;
    private boolean hasRestaurant;
    private boolean petsAllowed;
    private ArrayList<Room> rooms;

    /**
     * this is an empty constructor
     */
    public Accommodation()
    {
        rooms = new ArrayList<>();
    }

    /**
     * This is a constructor.
     * Creates an accommodation with specified name, country, type, location and whether or not it has parking or Wi-Fi
     * @param name the name of the accommodation
     * @param type the type of the accommodation (for example "hotel")
     * @param hasWifi whether or not the accommodation has Wi-Fi
     * @param hasParking whether or not the accommodation has parking
     * @param location the location of the accommodation
     */
    public Accommodation(Provider provider,String name,String type, boolean hasWifi, boolean hasParking,boolean hasPool,boolean hasRestaurant,boolean petsAllowed, Location location)
    {
        rooms = new ArrayList<>();
        this.type = type;
        this.provider = provider;
        this.hasPool = hasPool;
        this.hasRestaurant = hasRestaurant;
        this.petsAllowed = petsAllowed;
        this.hasWifi = hasWifi;
        this.hasParking = hasParking;
        this.location = location;
        this.name = name;
    }

    /**
     * This method returns if the accommodation has a pool or not.
     * @return true or false
     */
    public boolean getHasPool(){
        return hasPool;
    }

    /**
     * This method returns if the accommodation has a restaurant or not
     * @return true or false
     */
    public boolean getHasRestaurant(){
        return hasRestaurant;
    }

    /**
     * This method returns if pets are allowed in the accommodation
     * @return true or false
     */
    public boolean getPetsAllowed(){
        return petsAllowed;
    }
    /**
     * This method sets the name according to the parameter
     * @param name of the Accommodation
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @return  the name of the accommodation
     */
    public String getName(){
        return this.name;
    }

    /**
     * This method sets the type of the Accommodation (hotel/apartment etc.)
     * @param type the type of the Accommodation hotel/apartment etc.
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * This method sets the hasWifi according to the parameter
     * @param hasWifi is true/false if the Accommodation has Wi-Fi or not
     */
    public void setHasWifi(boolean hasWifi)
    {
        this.hasWifi = hasWifi;
    }

    /**
     * This method sets the hasParking according to the parameter
     * @param hasParking is true/false if the Accommodation has parking or not
     */
    public void setHasParking(boolean hasParking)
    {
        this.hasParking = hasParking;
    }

    /**
     * This method sets the location of the Accommodation
     * @param location is a class which as fields the town, the street and the street number of the location
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    /**
     * @return  the type of the Accommodation
     */
    public String getType()
    {
        return this.type;
    }

    /**
     * @return  true/false if the Accommodation has Wi-Fi or not
     */
    public boolean getHasWifi()
    {
        return this.hasWifi;
    }

    /**
     * @return  true/false if the Accommodation has parking or not
     */
    public boolean getHasParking()
    {
        return this.hasParking;
    }

    /**
     * @return  the location of the Accommodation
     */
    public Location getLocation()
    {
        return this.location;
    }

    /**
     * @return  location as a string
     */
    public String getStringLocation()
    {
        return location.getCountry()+" "+location.getTown () +" "+ location.getStreet () +" "+ location.getStreetNumber ();
    }

    /**
     * This method adds a room to the arraylist with rooms
     * @param room is one room
     */
    public void addRoom(Room room)
    {
        if(rooms == null)
        {
            rooms = new ArrayList<>();
        }
        rooms.add(room);
    }

    /**
     * @return The provider who owns this accommodation
     */
    public Provider getProvider(){
        return provider;
    }

    /**
     * This method removes a room from  the arraylist with rooms
     * @param index is the number of one room
     */
    public void removeRoom(int index)
    {
        if(rooms.size()>0)
        {
            rooms.remove(index);
        }
    }

    /**
     * @return  how many rooms has the Accommodation
     */
    public int getNumberOfRooms()
    {
        return rooms.size();
    }

    /**
     * This method returns a room based on its number
     * @param index The number of the room
     * @return A room object
     */
    public Room getSpecificRoom(int index)
    {
        return rooms.get(index);
    }

    /**
     * This method returns all the rooms of an accommodation
     * @return A list filled with rooms
     */
    public ArrayList<Room> getRooms(){
        return rooms;
    }

}
