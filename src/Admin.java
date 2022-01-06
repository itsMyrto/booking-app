

import java.util.Iterator;
import java.util.Scanner;

/**
 * This class represents a specific kind of user, the admin.
 * Admins are the ones who manage the app, they have access to every information that refers to customers/providers/booking accommodations.
 */

public class Admin extends User{

/**
     * This constructor creates an admin and initialises all the fields
     * @param username Is the name that is used for log in
     * @param password Is the password for log in
     * @param email The email of the admin
     * @param country The origin of the admin
     * @param fullName Name & surname of the admin
     */
    public Admin(String username, String password, String email, String country,String fullName) {
        super(username, password, email, country,fullName);
        setTypeOfUser("admin");
        setApproved(true);
    }


/**
     * This method prints the menu of an admin.
     *//*

    public void printAdminsMenu(){
        System.out.println();
        System.out.println("-------------Menu-------------------");
        System.out.println("1.)Search a specific user & show information");
        System.out.println("2.)Search a specific reservation & show information");
        System.out.println("3.)Approve new users");
        System.out.println("4.)Send a message to user");
        System.out.println("5.)Show profile");
        System.out.println("6.)Show Inbox");
        System.out.println("7.)Log out");
        System.out.println("Please enter a number (1,2,3,4,5,6 or 7):  ");
        System.out.println("-----------------------------------");
        System.out.println();
    }

    */
/**
     * This method is used for searching a user based on the email that is given (emails are unique).
     * It checks if the email is used by a user and if it is then it shows all the personal info.
     * If it is not found then it shows a suitable for the circumstance message.
     * @param allAccounts This is where all the accounts of the app are saved
     *//*

    public void searchCustomer(AccountsCreated allAccounts) {
        System.out.println("Enter the email of a user: ");
        Scanner scanner = new Scanner(System.in);
        String usersEmail = scanner.nextLine();
        User user = allAccounts.getSpecificAccount(usersEmail);
        if(user == null){
            System.out.println("There is no account with that email...");
        }
        else{
            System.out.println("User's information: ");
            System.out.println("Name:"+user.getFullName());
            System.out.println("Type:"+user.getTypeOfUser());
            System.out.println("Origin:"+user.getCountry());
            if(user instanceof Provider){
                ((Provider) user).checkAllAccommodations();
            }
            else if(user instanceof Customer){
                ((Customer) user).checkReservations();
            }
        }
    }

    */
/**
     * This method is used for searching the reservations of specific accommodation based on the name of the accommodation(all names are unique).
     * If the desired accommodation is found then it shows all the dates & the customers who booked it.
     * @param allReservations This object contains all the reservations made using the app
     *//*

    public void searchReservations(Reservations allReservations){
        System.out.println("Enter the name of a specific accommodation:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        int count = 0;
        for(Reservation x:allReservations.getReservations()){
            if(x.getAccommodationName().equals(name)){
                System.out.println("Name:"+x.getAccommodationName()+",Booked by(customer details):"+allReservations.getCustomersEmail().get(count)+",Dates:"+x.getReservationDates().getDatesAsString());
                count++;
            }
        }
    }

    */
/**
     * This method shows a list of new users who want to use the app and want to verify their account, so they can use it.
     * For each new user it prints the name, the email,the origin and the type. Then the admin can decide if the new user
     * is going to be accepted or not. If it is accepted then the user will be able to log in. If not then the account is deleted.
     * @param accounts This is where all the accounts are saved.
     *//*

    public void approveNewUser(AccountsCreated accounts){
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------Approve new users-----------");
        if(accounts.getUnapprovedAccounts().isEmpty()){
            System.out.println("There are not any new accounts for approval");
            return;
        }
        System.out.println("Total requests for approving:"+accounts.getUnapprovedAccounts().size());
        String[] answer = new String[accounts.getUnapprovedAccounts().size()];
        User[] users = new User[accounts.getUnapprovedAccounts().size()];
        Iterator<User> it = accounts.getUnapprovedAccounts().iterator();
        int i=0;
        while(it.hasNext()){
            users[i] = it.next();
            System.out.println("User's name:"+users[i].getFullName()+",Email:"+users[i].getEmail()+",Origin:"+users[i].getCountry()+",Type:"+users[i].getTypeOfUser());
            System.out.println("Do you want to approve this user (yes/no)?");
            answer[i] = scanner.nextLine();
            while(!(answer[i].equals("yes") || answer[i].equals("no"))){
                System.out.println("Please type a valid answer");
                answer[i] = scanner.nextLine();
            }
            i++;
        }

        for(int j=0;j<i;j++){
            if(answer[j].equals("yes")){
                users[j].setApproved(true);
                accounts.removeUnapprovedAccount(users[j]);
                accounts.addAccount(users[j]);
            }
            else{
                accounts.removeUnapprovedAccount(users[j]);
            }
        }
    }

    */
/**
     * This method is used for sending messages in other users
     * An admin can send a message to someone using the email which is unique for all the users.
     * @param allAccounts This is where all the accounts are saved
     *//*

    public void sendMessage(AccountsCreated allAccounts){
        System.out.println("Enter the email of the user you want to send a message: ");
        Scanner scanner = new Scanner(System.in);
        String usersEmail = scanner.nextLine();
        User user = allAccounts.getSpecificAccount(usersEmail);
        if(user == null){
            System.out.println("There is no account with that email...");
        }
        else{
            System.out.println("Message: ");
            String txt = scanner.nextLine();
            User sender = allAccounts.getSpecificAccount(getEmail());
            Message message = new Message(txt,sender);
            user.addMessage(message);
        }
    }

    */
}


