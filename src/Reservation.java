import java.io.Serializable;

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

    public Reservation(Accommodation accommodation,Customer customer,Date date,int roomNumber) {
        this.accommodation = accommodation;
        this.customer = customer;
        this.date = date;
        this.roomNumber = roomNumber;
        this.cancelled = false;
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

    public boolean getCancelled(){
        return cancelled;
    }

    public void setCancelled(boolean c){
        this.cancelled = c;
    }

}
