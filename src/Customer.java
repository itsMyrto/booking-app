import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a customer, the type of user who uses the app for
 * booking accommodations. It inherits all the properties of the general class user,
 * but it has also features for searching,booking etc.
 */
public class Customer extends User implements Serializable {

    ArrayList<Reservation> personalReservations;
    ArrayList<Reservation> deletedReservations;
    /**
     * This constructor initialises all the characteristics of a customer
     *  @param username The username that is used for Log in
     *  @param password The password that is used for Log In
     *  @param email The email of the provider
     *  @param country The country where the customer is from
     *  @param fullName Name & Surname of the customer
     */
    public Customer(String username, String password, String email, String country,String fullName) {
        super(username, password, email, country,fullName);
        setTypeOfUser("customer");
        personalReservations = new ArrayList<>();
        deletedReservations = new ArrayList<>();
    }

/*
    */
/**
     * This method prints the actions
     * that are accessible from a customers account.
     *//*

    public void printCustomersMenu(){
        System.out.println();
        System.out.println("-------------Menu-------------------");
        System.out.println("1.)Search & book accommodations");
        System.out.println("2.)Show Profile");
        System.out.println("3.)Show my reservations");
        System.out.println("4.)Delete a reservation");
        System.out.println("5.)Show Inbox");
        System.out.println("6.)Show my deleted reservations");
        System.out.println("7.)Log Out");
        System.out.println("Please enter a number (1,2,3,4,5,6 or 7):  ");
        System.out.println("-----------------------------------");
        System.out.println();
    }

    */
/**
     * This method asks from the user all the necessary preferences, so it can show the appropriate hotels based on user's option.
     * It also checks if the answers given by the customer are valid or not.
     * @param addedAccommodations All the accommodations
     * @param totalReservations All the reservations
     *//*


    public void searchAccommodation(AllAccommodations addedAccommodations,Reservations totalReservations){
        String temp1;
        int dayOfArriving,monthOfArriving,yearOfArriving,dayOfLeaving,monthOfLeaving,yearOfLeaving,numOfPeople;
        double lowLimit,highLimit;
        Date date;
        String countryDestination,cityDestination;
        System.out.println("----------Searching----------");
        do{
            System.out.println("Enter the day of arriving : ");
            temp1 = scanner.nextLine();
            dayOfArriving = Integer.parseInt(temp1);
            System.out.println("Enter the month of arriving: ");
            temp1 = scanner.nextLine();
            monthOfArriving = Integer.parseInt(temp1);
            System.out.println("Enter the year of arriving: ");
            temp1 = scanner.nextLine();
            yearOfArriving = Integer.parseInt(temp1);
            System.out.println("Enter the day of leaving : ");
            temp1 = scanner.nextLine();
            dayOfLeaving = Integer.parseInt(temp1);
            System.out.println("Enter the month of leaving: ");
            temp1 = scanner.nextLine();
            monthOfLeaving = Integer.parseInt(temp1);
            System.out.println("Enter the year of leaving: ");
            temp1 = scanner.nextLine();
            yearOfLeaving = Integer.parseInt(temp1);
            date = new Date(dayOfArriving,monthOfArriving,yearOfArriving,dayOfLeaving,monthOfLeaving,yearOfLeaving);
            if(!date.checkingTheDates()){
                System.out.println("Wrong dates... Please enter them again");
            }
        }while(!date.checkingTheDates());
        System.out.println("Enter the number of people: ");
        temp1 = scanner.nextLine();
        numOfPeople = Integer.parseInt(temp1);
        while(numOfPeople < 1){
            System.out.println("Number of people cannot be negative or zero.Enter a valid number: ");
            temp1 = scanner.nextLine();
            numOfPeople = Integer.parseInt(temp1);
        }
        System.out.println("Enter the lowest price limit: ");
        temp1 = scanner.nextLine();
        lowLimit = Double.parseDouble(temp1);
        if(lowLimit < 0){
            lowLimit = 0;
        }
        System.out.println("Enter the highest limit: ");
        temp1 = scanner.nextLine();
        highLimit = Double.parseDouble(temp1);
        while(highLimit < 0){
            System.out.println("Price can't be negative. Enter a valid number.");
            temp1 = scanner.nextLine();
            highLimit = Double.parseDouble(temp1);
        }
        System.out.println("Enter the country destination: ");
        countryDestination = scanner.nextLine();
        System.out.println("Enter the city destination: ");
        cityDestination = scanner.nextLine();
        addedAccommodations.bookAnAccommodation(this.getFullName(),this.getEmail(),totalReservations,personalReservations,date,lowLimit,highLimit,numOfPeople,countryDestination.toLowerCase(),cityDestination.toLowerCase());
    }

    */
/**
     * This method shows a customer's personal information
     *//*

    public void showProfile(){
        System.out.println("----------Profile----------");
        System.out.println("Name:"+this.getFullName());
        System.out.println("Email:"+this.getEmail());
        System.out.println("Country:"+this.getCountry());
        System.out.println("---------------------------");
    }

    */
/**
     * This method is used when a customer wants to delete a reservation.
     * The name of the accommodation is needed so the reservation can be found.
     * @param totalReservations All the reservations
     * @param addedAccommodations All the accommodations
     *//*

    public void deleteReservation(Reservations totalReservations,AllAccommodations addedAccommodations) {

        System.out.println("Which one of the following reservations you want to delete?");
        checkReservations();
        if (personalReservations.size() > 0) {
            System.out.println("Enter a number: ");
            String numberOfAccommodation = scanner.nextLine();
            int numberOfAccommodationInt = Integer.parseInt(numberOfAccommodation);
            while(numberOfAccommodationInt<1 || numberOfAccommodationInt>personalReservations.size()){
                System.out.println("Please enter a valid number");
                numberOfAccommodation = scanner.nextLine();
                numberOfAccommodationInt = Integer.parseInt(numberOfAccommodation);
            }
            String nameOfAccommodation = personalReservations.get(numberOfAccommodationInt-1).getAccommodationName();
            int roomNumbers = personalReservations.get(numberOfAccommodationInt-1).getRoomNumber();
            int roomCapacity = personalReservations.get(numberOfAccommodationInt-1).getCapacity();
            double roomPrice = personalReservations.get(numberOfAccommodationInt-1).getPrice();
            Date date = personalReservations.get(numberOfAccommodationInt-1).getReservationDates();
            Reservation cancelled = new Reservation(nameOfAccommodation,roomNumbers,date,roomCapacity,roomPrice);
            deletedReservations.add(cancelled);
            totalReservations.removeReservation(this.getEmail(),cancelled);
            addedAccommodations.updatingDates(nameOfAccommodation,roomNumbers,date);
            personalReservations.remove(numberOfAccommodationInt-1);
        }
    }

    */
/**
     * This method shows all the reservations that are cancelled by a customer
     *//*

    public void showDeletedReservations(){
        int total = 0;
        System.out.println("----------Deleted Reservations----------");
        if(deletedReservations.isEmpty()){
            System.out.println("You do not have cancel any reservations");
        }
        else{
            for(Reservation x:deletedReservations){
                System.out.println(++total+".)Name:"+x.getAccommodationName()+",Room number:"+x.getRoomNumber()+",Room capacity:"+ x.getCapacity()+",Total Price:"+x.getPrice()+",Dates(Arriving-Leaving):"+x.getReservationDates().getDatesAsString());
            }
        }
        System.out.println("--------------------------------------");
    }

    */
/**
     * This method shows all the current reservations
     *//*

    public void checkReservations(){
        int total = 0;
        System.out.println("----------Total Reservations----------");
        for(Reservation x:personalReservations){
            System.out.println(++total+".)Name:"+x.getAccommodationName()+",Room number:"+x.getRoomNumber()+",Room capacity:"+ x.getCapacity()+",Total Price:"+x.getPrice()+",Dates(Arriving-Leaving):"+x.getReservationDates().getDatesAsString());
        }
        if(total == 0){
            System.out.println("Your reservation list is empty");
        }
        System.out.println("--------------------------------------");
    }
*/

}
