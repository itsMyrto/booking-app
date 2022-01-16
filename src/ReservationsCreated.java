import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class keeps and saves the reservations in the app.
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

    public ArrayList<Reservation> cancelledReservationsOfACustomer(Customer customer){
        ArrayList<Reservation> reservation = new ArrayList<>();
        for(Reservation x:reservations){
            if(x.getCustomer().getEmail().equals(customer.getEmail()) && x.getCancelled()){
                reservation.add(x);
            }
        }
        return reservation;
    }

    public Reservation getASpecificReservation(Customer customer,String name,Date date,int roomNumber){
        for(Reservation x:reservations){
            if(x.getCustomer().getEmail().equals(customer.getEmail()) && x.getAccommodation().getName().equals(name) && x.getDate().equalDates(date) && (x.getRoomNumber()+1)==roomNumber){
                return x;
            }
        }
        return null;
    }

    public ArrayList<Reservation> reservationsForASpecificProvider(Provider provider){
        ArrayList<Reservation> list = new ArrayList<>();
        for(Reservation x:reservations){
            if(x.getAccommodation().getProvider().getEmail().equals(provider.getEmail()) && !x.getCancelled()){
                list.add(x);
            }
        }
        return list;
    }

    public ArrayList<Reservation> cancelledReservationsForASpecificProvider(Provider provider){
        ArrayList<Reservation> list = new ArrayList<>();
        for(Reservation x:reservations){
            if(x.getAccommodation().getProvider().getEmail().equals(provider.getEmail()) && x.getCancelled()){
                list.add(x);
            }
        }
        return list;
    }

    public int getTheNumberOfReservationsOfAnAccommodation(Accommodation accommodation){
        int total = 0;
        for(Reservation x:reservations){
            if(x.getAccommodation().getName().equals(accommodation.getName()) && x.getAccommodation().getProvider().getEmail().equals(accommodation.getProvider().getEmail()) && !x.getCancelled()){
                total++;
            }
        }
        return total;
    }

    public void removeReservations(Accommodation accommodation){
        reservations.removeIf(x -> x.getAccommodation().getProvider().getEmail().equals(accommodation.getProvider().getEmail()) && x.getAccommodation().getName().equals(accommodation.getName()));
    }

    public ArrayList<Reservation> getAllCancelledReservations(){
        ArrayList<Reservation> r = new ArrayList<>();
        for(Reservation x:reservations){
            if(x.getCancelled()){
               r.add(x);
            }
        }
        return r;
    }

    public ArrayList<Reservation> getAllActiveReservations(){
        ArrayList<Reservation> r = new ArrayList<>();
        for(Reservation x:reservations){
            if(!x.getCancelled()){
                r.add(x);
            }
        }
        return r;
    }


    public ArrayList<Reservation> getAllReservation(){
        return reservations;
    }
}
