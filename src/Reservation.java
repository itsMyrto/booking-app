import java.io.Serializable;

/**
 * This class represents a reservation. 
 * It has the basic characteristics of a reservation, such as the name of the accommodation (accommodationName), 
 * the number of the room (roomNumber) and the dates of the reservation (reservationDates). 
 */
public class Reservation implements Serializable {
    private Accommodation accommodation;
    private Customer customer;
    private Date date;
    private int roomNumber;
    
    /**
     * This is an empty constructor
     */
    public Reservation() {
    }

     public Reservation(Accommodation accommodation,Customer customer,Date date,int roomNumber) {
        this.accommodation = accommodation;
        this.customer = customer;
        this.date = date;
        this.roomNumber = roomNumber;
     }

     public Customer getCustomer(){
        return customer;
     }

     public Accommodation getAccommodation(){
        return accommodation;
     }

     public Date getDate(){
        return date;
     }

     public int getRoomNumber(){
        return roomNumber;
     }

}
