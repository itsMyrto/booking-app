import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class contains all the accommodations registered by all providers
 */
public class AccommodationsCreated {

    private ArrayList<Accommodation> allAccommodations;

    /**
     * The constructor where it checks if the file,where all the accommodations are saved, is empty. If it's not then it loads all the data from previous program executions
     */
    public AccommodationsCreated(){
        allAccommodations = new ArrayList<>();
        if(!isEmptyFile()) {
            try {
                FileInputStream fis = new FileInputStream("accommodations.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                allAccommodations = (ArrayList<Accommodation>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method searches for a specific provider
     * @param provider The provider the method is searching for
     * @return It returns the provider
     */
    public ArrayList<Accommodation> providerAccommodationList(Provider provider){
        ArrayList<Accommodation> accommodationList = new ArrayList<>();
        for(Accommodation s : allAccommodations){
            if(s.getProvider().getEmail().equals(provider.getEmail())){
                accommodationList.add(s);
            }
        }
        return accommodationList;
    }

    /**
     * This method checks if the file,where all the accommodations are saved,is empty.
     * @return True or False
     */
    public boolean isEmptyFile(){
        boolean isEmpty = false;
        try {
            FileInputStream fis = new FileInputStream("accommodations.txt");
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
     * This method adds a new accommodation, and it saves it in the file
     * @param accommodation
     */
    public void addNewAccommodation(Accommodation accommodation){
        allAccommodations.add(accommodation);
        try {
            FileOutputStream fos = new FileOutputStream("accommodations.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allAccommodations);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates the file where all the accommodations are saved
     */
    public void updateAccommodationList(){
        try {
            FileOutputStream fos = new FileOutputStream("accommodations.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allAccommodations);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method searches an accommodation based on its name and its location (in case there are two accommodations with the same name)
     * @param name The name of the accommodation
     * @param address The address of the accommodation
     * @return The accommodation
     */
    public Accommodation getSpecificAccommodation(String name,String address){
        for(Accommodation x:allAccommodations){
            if(x.getName().equals(name) && x.getLocation().getStreet().equals(address)){
                return x;
            }
        }
        return null;
    }

    /**
     * This method is called when a provider makes changes to his accommodation (such as name, or type),so the changes can be visible to the customers who made a reservation to this accommodation
     * @param listOfReservations The list of reservations
     * @param accommodation The accommodation that has been changed
     */
    public void updateReservation(ReservationsCreated listOfReservations,Accommodation accommodation){
        for(Reservation x:listOfReservations.getAllReservation()){
            if(x.getAccommodation().getProvider().getEmail().equals(accommodation.getProvider().getEmail()) && x.getAccommodation().getName().equals(accommodation.getName()) && !x.getCancelled()){
                x.getAccommodation().setType(accommodation.getType());
                x.getAccommodation().setName(accommodation.getName());
            }
        }
    }

    /**
     * This method searches all the accommodations based on the filters a customer used
     * @param parkingSelected If customer wants parking
     * @param wifiSelected If customer wants wifi
     * @param poolSelected If customer wants a pool
     * @param restaurantSelected If customer wants a restaurant
     * @param petsSelected If customer wants to bring a pet
     * @param roomCapacity The room capacity
     * @param maxPrice The maximum value of the price the customer is able to pay
     * @param countryDestination The country that the customer wants to travel to
     * @param cityDestination The city the customer wants to travel to
     * @param date The date of the reservation (both arriving & leaving)
     * @return A hashmap that contains the available accommodations and their room number
     */
    public HashMap<Accommodation,Integer> searchForAccommodations(boolean parkingSelected,boolean wifiSelected,boolean poolSelected,boolean restaurantSelected,boolean petsSelected,int roomCapacity,double maxPrice,String countryDestination,String cityDestination,Date date){
        HashMap<Accommodation,Integer> selectedAccommodations = new HashMap<>();
        System.out.println("Got in class");
        System.out.println(parkingSelected+" "+wifiSelected+" "+poolSelected+" "+cityDestination+" "+countryDestination);
        for(Accommodation x: allAccommodations) {
            if (x.getLocation().getCountry().equals(countryDestination)) {
                if (x.getLocation().getTown().equals(cityDestination)) {
                    for (int i = 0; i < x.getNumberOfRooms(); i++) {
                        if (x.getSpecificRoom(i).availableDate(date)) {
                            if (x.getSpecificRoom(i).getPricePerNight() <= maxPrice) {
                                if (x.getSpecificRoom(i).getCapacity() == roomCapacity) {
                                    if (parkingSelected && !x.getHasParking()) {
                                        continue;
                                    }
                                    if (wifiSelected && !x.getHasWifi()) {
                                        continue;
                                    }
                                    if (poolSelected && !x.getHasPool()) {
                                        continue;
                                    }
                                    if (petsSelected && !x.getPetsAllowed()) {
                                        continue;
                                    }
                                    if (restaurantSelected && !x.getHasRestaurant()) {
                                        continue;
                                    }
                                    selectedAccommodations.put(x,i);
                                }
                            }
                        }
                    }
                }
            }
        }

        return selectedAccommodations;
    }

    /**
     * This method returns all the accommodations of a provider
     * @param email The email of the provider
     * @return The provider's accommodation list
     */
    public ArrayList<Accommodation> getAccommodationsOfAProvider(String email){
        ArrayList<Accommodation> accommodations = new ArrayList<>();
        for(Accommodation x:allAccommodations){
            if(x.getProvider().getEmail().equals(email)){
                accommodations.add(x);
            }
        }
        return accommodations;
    }

    /**
     * @return All the accommodations
     */
    public ArrayList<Accommodation> getTheAccommodationList(){
        return allAccommodations;
    }

    /**
     * This method deletes an accommodation
     * @param accommodation The accommodation that a provider wants to delete
     */
    public void deleteAnAccommodation(Accommodation accommodation){
        allAccommodations.remove(accommodation);
    }

}