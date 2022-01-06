import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class contains all the accommodations registered by all providers
 */
public class AccommodationsCreated {

    private ArrayList<Accommodation> allAccommodations;

    /**
     * Constructor
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

    public ArrayList<Accommodation> providerAccommodationList(Provider provider){
        ArrayList<Accommodation> accommodationList = new ArrayList<>();
        for(Accommodation s : allAccommodations){
            if(s.getProvider().getEmail().equals(provider.getEmail())){
                accommodationList.add(s);
            }
        }
        return accommodationList;
    }

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

    public Accommodation getSpecificAccommodation(String name,String address){
        for(Accommodation x:allAccommodations){
            if(x.getName().equals(name) && x.getLocation().getStreet().equals(address)){
                return x;
            }
        }
        return null;
    }

    public void updateReservation(ReservationsCreated listOfReservations,Accommodation accommodation){
        for(Reservation x:listOfReservations.getAllReservation()){
            if(x.getAccommodation().getProvider().getEmail().equals(accommodation.getProvider().getEmail()) && x.getAccommodation().getName().equals(accommodation.getName()) && !x.getCancelled()){
                x.getAccommodation().setType(accommodation.getType());
                x.getAccommodation().setName(accommodation.getName());
            }
        }
    }

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

    public ArrayList<Accommodation> getAccommodationsOfAProvider(String email){
        ArrayList<Accommodation> accommodations = new ArrayList<>();
        for(Accommodation x:allAccommodations){
            if(x.getProvider().getEmail().equals(email)){
                accommodations.add(x);
            }
        }
        return accommodations;
    }

    public ArrayList<Accommodation> getTheAccommodationList(){
        return allAccommodations;
    }

    public void deleteAnAccommodation(Accommodation accommodation){
        allAccommodations.remove(accommodation);
    }

    /**
     * This method adds a new accommodation
     * @param newAccommodation The list that contains all the accommodations
     *//*
    public void addNewAccommodation(Accommodation newAccommodation){
        totalAccommodationsList.add(newAccommodation);
    }

    *//**
     * This method prints all the accommodations
     *//*
    public void printAllAccommodations(){
        if(totalAccommodationsList.size() > 0 ){
            for(Accommodation x:totalAccommodationsList){
                System.out.println(x.getName()+" "+x.getType()+" "+x.getStringLocation());
            }
        }
    }

    *//**
     * This accommodation searches if an accommodation with a specific name already exists.
     * All the accommodation names are unique.
     * @param name The name of an accommodation
     * @return True if it is used already, else false
     *//*
    public boolean accommodationAlreadyExists(String name){
        boolean nameExists = false;
        for(Accommodation x:totalAccommodationsList){
            if (x.getName().equals(name)) {
                nameExists = true;
                break;
            }
        }
        return nameExists;
    }

    *//**
     * This method adds a new date. It is used when a customer makes a reservation
     * @param name The name of the accommodation where the reservation is made
     * @param room The number of the room (since every accommodation can have multiple rooms)
     * @param date The date of the reservation
     *//*
    public void updatingDates(String name,int room,Date date){
        for(Accommodation x:totalAccommodationsList){
            if(x.getName().equals(name)){
                x.getSpecificRoom(room-1).removeReservedDates(date);
            }
        }
    }

    *//**
     * This method removes an accommodation. It is used when a provider wants to remove an accommodation from the app.
     * @param name The name of the accommodation
     *//*
    public void removeAnAccommodation(String name) {
        Iterator<Accommodation> it = totalAccommodationsList.iterator();
        while (it.hasNext()) {
            String compareName = it.next().getName();
            if (compareName.equals(name)) {
                it.remove();
            }
        }
    }

    *//**
     * This method applies the users preferences and shows only the accommodations that require the users options
     * @param name The name of the customer
     * @param email The email of the customer
     * @param totalReservations All the reservations
     * @param personalReservations Customer's personal reservations
     * @param date Dates of check-in/check-out
     * @param priceLowLimit The minimum price a customer wants to pay
     * @param priceHighLimit The maximum price a customer wants to pay
     * @param people How many people are going to be in the room
     * @param country In which country the customer wants to search for an accommodation
     * @param city In which city the customer wants to search for an accommodation
     *//*
    public void bookAnAccommodation(String name,String email,Reservations totalReservations,ArrayList<Reservation> personalReservations,Date date,double priceLowLimit,double priceHighLimit,int people,String country,String city){
        String answer;
        Scanner scanner = new Scanner(System.in);
        int total = 0;
        HashMap<Integer,Accommodation> accommodations = new HashMap<>();
        HashMap<Integer,Integer> room = new HashMap<>();
        System.out.println("List of available accommodations with the filters you chose: ");
        for(Accommodation x: totalAccommodationsList){
            if(x.getLocation().getCountry().equals(country)){
                if(x.getLocation().getTown().equals(city)){
                    for(int i=0;i<x.getNumberOfRooms();i++){
                        if(x.getSpecificRoom(i).availableDate(date)){
                            if(x.getSpecificRoom(i).getPricePerNight()*date.nightsBetweenDates()>=priceLowLimit && x.getSpecificRoom(i).getPricePerNight()*date.nightsBetweenDates()<=priceHighLimit){
                                if(x.getSpecificRoom(i).getCapacity()==people){
                                    System.out.println(++total+".)"+x.getName()+",Type:"+x.getType()+",Location:"+x.getStringLocation()+",Price:"+date.nightsBetweenDates()*x.getSpecificRoom(i).getPricePerNight()+",Room Number:"+(i+1)+",WiFi:"+x.getHasWifi()+",Parking:"+x.getHasParking());
                                    accommodations.put(total,x);
                                    room.put(total,i+1);
                                }
                            }
                        }
                    }
                }
            }
        }
        if(total == 0){
            System.out.println("We are so sorry, there are not any available accommodations with these filters.");
            return;
        }
        System.out.println("Do you want to book any of these? (yes/no)");
        answer = scanner.nextLine();
        while(!(answer.equals("yes")) && !(answer.equals("no"))){
            System.out.println("Please enter a valid answer.");
            answer = scanner.nextLine();
        }
        if(answer.equals("yes")){
            System.out.println("Chose which one: ");
            answer = scanner.nextLine();
            int option = Integer.parseInt(answer);
            while(option<1 || option>total){
                System.out.println("Please enter a valid: ");
                answer = scanner.nextLine();
                option = Integer.parseInt(answer);
            }
            double totalPrice = accommodations.get(option).getSpecificRoom(room.get(option)-1).getPricePerNight()*date.nightsBetweenDates();
            Reservation reservation = new Reservation(accommodations.get(option).getName(), room.get(option), date,people,totalPrice);
            personalReservations.add(reservation);
            accommodations.get(option).getSpecificRoom(room.get(option)-1).addReservedDates(date);
            totalReservations.addReservation(reservation,name,email);
            System.out.println("Reservation added successfully.");
        }
    }*/
}