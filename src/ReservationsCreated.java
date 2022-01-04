import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class has the reservations, the names of the costumers and their emails of the whole app. 
 */
public class ReservationsCreated {

    private ArrayList<Reservation> reservations;

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

    public ArrayList<Reservation> reservationsOfASpecificCustomer(Customer customer){
        ArrayList<Reservation> reservation = new ArrayList<>();
        for(Reservation x:reservations){
            if(x.getCustomer().getEmail().equals(customer.getEmail())){
                reservation.add(x);
            }
        }

        return reservation;
    }

}
