import java.io.Serializable;

/**
 * This class represents a reservation
 */
public class Reservation implements Serializable {
    private Accommodation accommodation;
    private Customer customer;
    private Date date;
    private int roomNumber;
    private boolean cancelled;

    /**
     * This is an empty constructor
     */
    public Reservation() {
    }

    /**
     * This constructor initialises the fields
     * @param accommodation The accommodation that is reserved
     * @param customer The customer who did the reservation
     * @param date The dates of the reservation
     * @param roomNumber The room number of the accommodation where the customer is going to stay
     */
    public Reservation(Accommodation accommodation,Customer customer,Date date,int roomNumber) {
        this.accommodation = accommodation;
        this.customer = customer;
        this.date = date;
        this.roomNumber = roomNumber;
        this.cancelled = false;
    }

    /**
     * @return The customer who made this reservation
     */
    public Customer getCustomer(){
        return customer;
    }

    /**
     * @return The accommodation that is reserved
     */
    public Accommodation getAccommodation(){
        return accommodation;
    }

    /**
     * @return The dates of the reservation
     */
    public Date getDate(){
        return date;
    }

    /**
     * @return The number of the room of the accommodation that the customer is going to stay
     */
    public int getRoomNumber(){
        return roomNumber;
    }

    /**
     * @return If the reservation is cancelled or not
     */
    public boolean getCancelled(){
        return cancelled;
    }

    /**
     * This method changes the reservation status (if its cancelled or not)
     * @param c true if the reservation is valid,else false
     */
    public void setCancelled(boolean c){
        this.cancelled = c;
    }

}
