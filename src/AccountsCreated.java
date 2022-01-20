import java.io.*;
import java.util.ArrayList;

/**
 * This class contains all the created accounts
 */
public class AccountsCreated {

    private ArrayList<User> allAccounts;

    /**
     * This is the constructor. First it checks if the file with the accounts is empty.If it's not then it loads
     * all the accounts that have been created in previous executions of the app.
     * If the file is empty then 3 admins are added.
     */
    AccountsCreated(){
        allAccounts = new ArrayList<>();
        if(!isEmptyFile()) {
            try {
                FileInputStream fis = new FileInputStream("accounts.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                allAccounts = (ArrayList<User>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            Admin admin = new Admin("gregtsoum","123","gregTsoumakas@email.com","Greece","Gregory Tsoumakas");
            Admin admin2 = new Admin("gmeditsk","456","georgeMeditsk@email.com","Greece","Georgios Meditskos");
            Admin admin3 = new Admin("itsMyrto","football","itsMyrto@email.com","Greece","Myrto Gkogkou");
            allAccounts.add(admin2);
            allAccounts.add(admin3);
            allAccounts.add(admin);
            updateAccounts();
        }
    }

    /**
     * This method checks if a username is taken or not
     * @param user The user who wants to create an account with a specific username
     * @return True if the username already exists else false
     */
    public boolean usernameIsTaken(User user){
        boolean exists = false;
        for(User x : allAccounts){
            if (x.getUsername().equals(user.getUsername())) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    /**
     * This method checks if a username is taken or not
     * @param user The user who wants to create an account with a specific email
     * @return True if the email already exists else false
     */
    public boolean emailIsTaken(User user){
        boolean exists = false;
        for(User x : allAccounts){
            if (x.getEmail().equals(user.getEmail())) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    /**
     * This method saves in an array list all the messages of a specific user
     * @param user The user that who's inbox is going to be saved
     * @return The array list with all th messages
     */
    public ArrayList<Message> getTheMessageListOfAUser(User user){
        ArrayList<Message> messages = new ArrayList<>();
        for(User x:allAccounts){
            if(x.getEmail().equals(user.getEmail())){
                messages = user.getMessages();
                break;
            }
        }
        return messages;
    }

    /**
     * This method adds a new customer in the app
     * @param customer The new customer
     */
    public void createAccountForCustomers(Customer customer) {
        addNewAccount(customer);
    }

    /**
     * This method saves in an arraylist all the unapproved accounts of the app
     * @return The arraylist with the unapproved accounts
     */
    public ArrayList<User> getUnapprovedAccount(){
        ArrayList<User> unapproved = new ArrayList();
        for(User x:allAccounts){
            if(!x.getApproved()){
                unapproved.add(x);
            }
        }
        return unapproved;
    }

    /**
     * This method adds a new provider in the app
     * @param provider The new provider
     */
    public void createAccountForProviders(Provider provider){
        addNewAccount(provider);
    }

    /**
     * This method adds a new account in the app.It also updates the file
     * @param user The new user
     */
    public void addNewAccount(User user){
        allAccounts.add(user);
        try {
            FileOutputStream fos = new FileOutputStream("accounts.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allAccounts);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates the file and adds all the new accounts or the changes that possibly have been made in existing accounts
     */
    public void updateAccounts(){
        try {
            FileOutputStream fos = new FileOutputStream("accounts.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allAccounts);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method checks if a customer is been approved or not by an admin
     * @param user The account we want to check
     * @return True if it is unapproved else false
     */
    public boolean accountIsUnapproved(User user){
        boolean isUnapproved = !user.getApproved();
        return isUnapproved;
    }

    /**
     * This method logs in users to their account
     * @param username The username of the account
     * @param password  The password of the account
     * @return The user if it is found else null if the account does not exist
     */
    public User logInAccount(String username,String password){
        for(User users:allAccounts){
            if(users.getUsername().equals(username) && users.getPassword().equals(password)){
                return users;
            }
        }
        return null;
    }

    /**
     * This method prints all the usernames
     */
    public void printMethod(){
        for(User c:allAccounts){
            System.out.println(c.getUsername());
        }
    }

    /**
     * This method is used to send a message from an admin to another user
     * @param message The message that an admin wants to send
     * @param email The email of the user who is going to receive the email
     */
    public void sendMessage(Message message,String email){
        for(User x:allAccounts){
            if(x.getEmail().equals(email)){
                x.addMessage(message);
            }
        }
        updateAccounts();
    }

    /**
     * This method checks if the file with all the accounts is empty or not
     * @return True if it is empty else false
     */
    public boolean isEmptyFile(){
        boolean isEmpty = false;
        try {
            FileInputStream fis = new FileInputStream("accounts.txt");
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
     * This method searches for a specific account
     * @param email The search is based on the email since the emails are all unique
     * @return The user if it is found else null if the account does not exist
     */
    public User getSpecificAccount(String email){
        for(User x:allAccounts){
            if(x.getEmail().equals(email)){
                return x;
            }
        }
        return null;
    }

    /**
     * This method saves in a string array all the email of all users (providers,customers & admins)
     * @return The string array with the emails
     */
    public String[] getAllUserEmails() {
        ArrayList<String> emails = new ArrayList<>();
        for (User x : allAccounts) {
            if(x.getApproved()){
                emails.add(x.getEmail());
            }
        }
        String[] emailsArray = new String[emails.size()];
        int i=0;
        for(String x:emails){
            emailsArray[i]=x;
            i++;
        }
        return emailsArray;
    }

    /**
     * This method saves in a string array all the customers & providers emails
     * @return A string array with all the email
     */
    public String[] getAllCustomersEmail(){
        ArrayList<String> emails = new ArrayList<>();
        for (User x : allAccounts) {
            if((x instanceof Customer || x instanceof Provider) && x.getApproved()){
                emails.add(x.getEmail());
            }
        }
        String[] emailsArray = new String[emails.size()];
        int i=0;
        for(String x:emails){
            emailsArray[i]=x;
            i++;
        }
        return emailsArray;
    }
}
