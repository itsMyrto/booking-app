import java.io.*;
import java.util.ArrayList;

/**
 * This class keeps and saves the reservations in the app.
 */
public class ReservationsCreated {

    private ArrayList<Reservation> reservations;

    /**
     * The constructor first checks if the file with all the reservations is empty.If it's not then it loads all the reservations
     * made in previous executions of the program.
     */
    public ReservationsCreated(){
        reservations = new ArrayList<>();
        if(!isEmptyFile()) {
            try {
                FileInputStream fis = new FileInputStream("reservations.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                reservations = (ArrayList<Reservation>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method  updates the file where all the reservations are saved (for example if a new reservation is added,or a reservation is cancelled)
     */
    public void updateReservation(){
        try {
            FileOutputStream fos = new FileOutputStream("reservations.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(reservations);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method checks if the file with all the reservations is empty or not.
     * @return True if it's empty else false
     */
    public boolean isEmptyFile(){
        boolean isEmpty = false;
        try {
            FileInputStream fis = new FileInputStream("reservations.txt");
            if(fis.read() == -1){
                isEmpty = true;
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isEmpty;
    }

    /**
     * This method adds an ew reservations in the app & updates the file where all the reservations are saved
     * @param reservation The new reservation
     */
    public void addNewReservation(Reservation reservation){
        reservations.add(reservation);
        try {
            FileOutputStream fos = new FileOutputStream("reservations.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(reservations);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method searches for all the reservations of a customer (both active & cancelled)
     * @param customer The customer
     * @return A list with all the reservations of the customer
     */
    public ArrayList<Reservation> reservationsOfASpecificCustomer(Customer customer){
        ArrayList<Reservation> reservation = new ArrayList<>();
        for(Reservation x:reservations){
            if(x.getCustomer().getEmail().equals(customer.getEmail())){
                reservation.add(x);
            }
        }

        return reservation;
    }

    /**
     * This method searches for all the cancelled reservations of a customer
     * @param customer The customer
     * @return A list with customer's cancelled reservations
     */
    public ArrayList<Reservation> cancelledReservationsOfACustomer(Customer customer){
        ArrayList<Reservation> reservation = new ArrayList<>();
        for(Reservation x:reservations){
            if(x.getCustomer().getEmail().equals(customer.getEmail()) && x.getCancelled()){
                reservation.add(x);
            }
        }
        return reservation;
    }

    /**
     * This method searches for a specific reservation of a specific customer
     * @param customer The customer who made a reservation
     * @param name The name of the accommodation
     * @param date The date of the reservation
     * @param roomNumber The reserved room
     * @return The reservation if it is found else null
     */
    public Reservation getASpecificReservation(Customer customer,String name,Date date,int roomNumber){
        for(Reservation x:reservations){
            if(x.getCustomer().getEmail().equals(customer.getEmail()) && x.getAccommodation().getName().equals(name) && x.getDate().equalDates(date) && (x.getRoomNumber()+1)==roomNumber){
                return x;
            }
        }
        return null;
    }

    /**
     * This method searches for all the reservations that customers made for all the accommodations of a specific provider
     * @param provider The provider
     * @return A list with all the reservations
     */
    public ArrayList<Reservation> reservationsForASpecificProvider(Provider provider){
        ArrayList<Reservation> list = new ArrayList<>();
        for(Reservation x:reservations){
            if(x.getAccommodation().getProvider().getEmail().equals(provider.getEmail()) && !x.getCancelled()){
                list.add(x);
            }
        }
        return list;
    }

    /**
     * This method searches all the cancelled reservations of all the accommodations of a provider
     * @param provider the provider
     * @return A list with all cancelled reservations
     */
    public ArrayList<Reservation> cancelledReservationsForASpecificProvider(Provider provider){
        ArrayList<Reservation> list = new ArrayList<>();
        for(Reservation x:reservations){
            if(x.getAccommodation().getProvider().getEmail().equals(provider.getEmail()) && x.getCancelled()){
                list.add(x);
            }
        }
        return list;
    }

    /**
     * This method counts all the reservations of a specific accommodation
     * @param accommodation The accommodation we want to find out how many reservations it has
     * @return The number of total reservations
     */
    public int getTheNumberOfReservationsOfAnAccommodation(Accommodation accommodation){
        int total = 0;
        for(Reservation x:reservations){
            if(x.getAccommodation().getName().equals(accommodation.getName()) && x.getAccommodation().getProvider().getEmail().equals(accommodation.getProvider().getEmail()) && !x.getCancelled()){
                total++;
            }
        }
        return total;
    }

    /**
     * This method removes all the reservations for the same accommodation.This method is called when a provider wants to remove
     * an accommodation from the app.
     * @param accommodation The accommodation that is going to be removed
     */
    public void removeReservations(Accommodation accommodation){
        reservations.removeIf(x -> x.getAccommodation().getProvider().getEmail().equals(accommodation.getProvider().getEmail()) && x.getAccommodation().getName().equals(accommodation.getName()));
    }

    /**
     * This method searches for all the cancelled reservations
     * @return An array list that contains all the cancelled reservations
     */
    public ArrayList<Reservation> getAllCancelledReservations(){
        ArrayList<Reservation> r = new ArrayList<>();
        for(Reservation x:reservations){
            if(x.getCancelled()){
               r.add(x);
            }
        }
        return r;
    }

    /**
     * This method searches for all the active reservations (the customer have not cancelled them)
     * @return An array list that contains all the active reservations
     */
    public ArrayList<Reservation> getAllActiveReservations(){
        ArrayList<Reservation> r = new ArrayList<>();
        for(Reservation x:reservations){
            if(!x.getCancelled()){
                r.add(x);
            }
        }
        return r;
    }

    /**
     * @return All the reservations of the app
     */
    public ArrayList<Reservation> getAllReservation(){
        return reservations;
    }
}
