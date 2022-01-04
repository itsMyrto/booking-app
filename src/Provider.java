import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.Iterator;
/**
 * This class represents a provider which is a specific type of user.
 * It inherits all the properties of a User, but it has some added features.
 * A provider owns Accommodations.
 */
public class Provider extends User implements Serializable {

    /**
     * The constructor initialises all the user's characteristics
     * @param username The username that is used for Log in.
     * @param password The password that is used for Log In.
     * @param email The email of the provider.
     * @param country The country where the provider is from.
     * @param fullName Name & Surname of the provider.
     */
    public Provider(String username, String password, String email, String country,String fullName) {
        super(username, password, email, country, fullName);
        setTypeOfUser("provider");
    }

    /**
     * This constructor except from initialising user's characteristics it also creates & initialises new accommodations
     * @param username The username that is used for Log in.
     * @param password The password that is used for Log In.
     * @param email The email of the provider.
     * @param country The country where the provider is from.
     * @param fullName Name & Surname of the provider.
     * @param accommodation An object that describes an accommodation
     * @param accommodations A list with all the accommodations
     *//*
    public Provider(String username, String password, String email, String country,String fullName,ArrayList<Accommodation> accommodation,AllAccommodations accommodations) {
        super(username, password, email, country, fullName);
        listOfAccommodations = new ArrayList<>();
        for(Accommodation x:accommodation){
            listOfAccommodations.add(x);
            accommodations.addNewAccommodation(x);
        }
        setTypeOfUser("provider");

    }
    */
    /**
     * This method is used when a provider wants to change data on owned accommodations like name, location,
     * prices etc.First it prints a menu with all the allowed changes and then the provider can choose which characteristic needs a change.
     * @param totalReservations This object contains all the reservations of the app.
     * @param acc This object contains all the accommodations
     *//*
    public void changeDataOnAccommodation(Reservations totalReservations,AllAccommodations acc){
        System.out.println("Enter the name of the Accommodation in which you want to make changes: ");
        boolean found = false;
        String name = scanner.nextLine();
        for(Accommodation x:listOfAccommodations){
            if(x.getName().equals(name)){
                found = true;
                System.out.println("---------------------------");
                System.out.println("What do you want to change?");
                System.out.println("1.)Name of Accommodation");
                System.out.println("2.)Change the type of the accommodation (example: hotel-->apartments)");
                System.out.println("3.)Location");
                System.out.println("4.)If it has WiFi");
                System.out.println("5.)If it has parking lot");
                System.out.println("6.)Add a room");
                System.out.println("7.)The capacity of a specific room");
                System.out.println("8.)Change the price of a specific room");
                System.out.println("---------------------------");
                int option = scanner.nextInt();
                scanner.nextLine();
                while(option != 1 && option != 2 && option != 3 && option !=4 && option != 5 && option !=6 && option !=7 && option != 8){
                    System.out.println("Please type a valid number");
                    option = scanner.nextInt();
                    scanner.nextLine();
                }
                if(option == 1){
                    System.out.println("Enter the new name: ");
                    String newName = scanner.nextLine();
                    while(acc.accommodationAlreadyExists(newName)){
                        System.out.println("Sorry, this accommodation name already exists,please enter a unique name");
                        newName = scanner.nextLine();
                    }
                    for(Reservation y:totalReservations.getReservations()){
                        if(y.getAccommodationName().equals(name)){
                            y.setAccommodationName(newName);
                        }
                    }
                    x.setName(newName);
                }
                else if(option == 2){
                    System.out.println("Enter the new type: ");
                    String newType = scanner.nextLine();
                    x.setType(newType);
                }
                else if(option == 3){
                    System.out.println("Enter the new location.");
                    System.out.println("Enter the new country: ");
                    String newCountry = scanner.nextLine().toLowerCase();
                    System.out.println("New City: ");
                    String newCity = scanner.nextLine().toLowerCase();
                    System.out.println("New Street: ");
                    String newStreet = scanner.nextLine().toLowerCase();
                    System.out.println("New Street Number: ");
                    String temp = scanner.nextLine();
                    int newStreetNumber = Integer.parseInt(temp);
                    x.getLocation().setCountry(newCountry);
                    x.getLocation().setTown(newCity);
                    x.getLocation().setStreet(newStreet);
                    x.getLocation().setNumber(newStreetNumber);
                    scanner.nextLine();
                }
                else if(option == 4){
                    System.out.println("Enter if it has WiFi (yes or no)");
                    String answer = scanner.nextLine();
                    while(!(answer.equals("yes") || answer.equals("no"))){
                        System.out.println("Please type a valid answer");
                        answer = scanner.nextLine();
                    }
                    x.setHasWifi(answer.equals("yes"));
                }
                else if(option == 5){
                    System.out.println("Enter if it has a parking lot (yes or no)");
                    String answer = scanner.nextLine();
                    while(!(answer.equals("yes") || answer.equals("no"))){
                        System.out.println("Please type a valid answer");
                        answer = scanner.nextLine();
                    }
                    x.setHasParking(answer.equals("yes"));
                }
                else if(option == 6){
                   System.out.println("Enter new room's capacity: ");
                   String temp = scanner.nextLine();
                   int capacity = Integer.parseInt(temp);
                   System.out.println("Enter the price per night: ");
                   String temp2 = scanner.nextLine();
                   double price = Double.parseDouble(temp2);
                   Room room = new  Room(capacity,price);
                   x.addRoom(room);
                }
                else if(option == 7){
                    if(x.getNumberOfRooms()>0){
                        System.out.println("Which one of the "+x.getNumberOfRooms()+" rooms you want to change its capacity?");
                        String temp = scanner.nextLine();
                        int roomToChange = Integer.parseInt(temp);
                        while(roomToChange>x.getNumberOfRooms() || roomToChange<0){
                            System.out.println("Enter a valid number.");
                            temp = scanner.nextLine();
                            roomToChange = Integer.parseInt(temp);
                        }
                        String temp2 = scanner.nextLine();
                        int newCapacity = Integer.parseInt(temp2);
                        x.getSpecificRoom(roomToChange-1).setCapacity(newCapacity);
                    }
                    else{
                        System.out.println("Sorry, your accommodation doesn't have any rooms.");
                    }
                }
                else {
                    if(x.getNumberOfRooms()>0){
                        System.out.println("Which one of the "+x.getNumberOfRooms()+" rooms you want to change its price?");
                        String temp = scanner.nextLine();
                        int roomToChange = Integer.parseInt(temp);
                        while(roomToChange>x.getNumberOfRooms() || roomToChange<0){
                            System.out.println("Enter a valid number.");
                            temp = scanner.nextLine();
                            roomToChange = Integer.parseInt(temp);
                        }
                        String temp2 = scanner.nextLine();
                        double newPrice = Double.parseDouble(temp2);
                        x.getSpecificRoom(roomToChange-1).setPricePerNight(newPrice);
                    }
                    else{
                        System.out.println("Sorry, your accommodation doesn't have any rooms.");
                    }
                }
            }
        }
        if(!found){
            System.out.println("Sorry we couldn't find an accommodation with that name");
        }
    }

    *//**
     * This method is used by a provider for deleting specific accommodation.
     * The deletion of accommodation is based on its name (which is unique).
     * First it cancels all the reservations that might have been made,and then it removes the accommodation.
     * @param accommodation It contains a list with all the accommodations of the app
     * @param totalReservations It contains a list with all the reservations of the ap
     *//*
    public void deleteAccommodation(AllAccommodations accommodation,Reservations totalReservations) {
        boolean successfulRemove = false;
        if (listOfAccommodations.size() > 0) {
            System.out.println("Enter the name of the Accommodation you want to delete from the list");
            String name = scanner.nextLine();
            Iterator<Accommodation> it = listOfAccommodations.iterator();
            while (it.hasNext()) {
                String compareName = it.next().getName();
                if (compareName.equals(name)) {
                    successfulRemove = true;
                    it.remove();
                    accommodation.removeAnAccommodation(compareName);
                    System.out.println("Accommodation removed successfully");
                }
            }
            if(successfulRemove){
                totalReservations.removeReservation(name);
            }
        }
    }
    *//**
     * This method shows a provider's personal information
     *//*
    public void showProfile(){
        System.out.println();
        System.out.println("----------Profile----------");
        System.out.println("Name: "+this.getFullName());
        System.out.println("Email: "+this.getEmail());
        System.out.println("Country: "+this.getCountry());
        System.out.println("---------------------------");
        System.out.println();
    }

    *//**
     * This method shows all the accommodations of a provider that have been booked by a customer, with all the
     * necessary information, such as customer's name & email, dates of arriving & living etc.
     * @param totalReservations This is a list where all the reservations from all the customers that use the app are saved
     *//*
    public void showBookedAccommodations(Reservations totalReservations){
        int total = 0;
        for(Accommodation y:listOfAccommodations ){
            int count = 0;
            for(Reservation x:totalReservations.getReservations()){
                if(x.getAccommodationName().equals(y.getName())){
                    System.out.println((++total)+".)Accommodation:"+x.getAccommodationName());
                    System.out.println("----->Location:"+y.getStringLocation());
                    System.out.println("----->Dates:"+x.getReservationDates().getDatesAsString());
                    System.out.println("----->Reserved Room:"+x.getRoomNumber()+",Room Capacity:"+x.getCapacity());
                    System.out.println("----->Total Price:"+x.getPrice());
                    System.out.println("----->Customer's Info(name & email): "+totalReservations.getNameOfCustomers().get(count)+" "+totalReservations.getCustomersEmail().get(count));
                }
                count++;
                total = count;
            }
        }
        if(total == 0 ){
            System.out.println("None of your accommodations have been booked yet.");
        }
    }

    *//**
     * This method shows a list of all accommodations of a provider.
     * For each accommodation are displayed all the information that describe it (name,location,price,rooms,capacity,Wi-Fi,parking)
     *//*
    public void checkAllAccommodations(){
        System.out.println("------List Of Accommodations------");
        int count = 1;
        for(Accommodation x: listOfAccommodations){
            System.out.println(count+".)"+"Name:"+x.getName()+",Type:"+x.getType()+",Location:"+x.getStringLocation());
            System.out.println("-> Characteristics <-");
            System.out.println("Has WiFi: "+x.getHasWifi());
            System.out.println("Has Parking: "+x.getHasParking());
            System.out.println("Total rooms: "+x.getNumberOfRooms());
            for(int i=0;i<x.getNumberOfRooms();i++){
                System.out.println("No"+(i+1)+",Room capacity:"+x.getSpecificRoom(i).getCapacity()+",Price:"+x.getSpecificRoom(i).getPricePerNight());
            }
            count++;

        }
    }*/
}
