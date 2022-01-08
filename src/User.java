import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a user of the app. A user can be an admin or a provider or a customer.
 *  It contains all the common fields & methods that all these three different type of users have.
 */
public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private String country;
    private String fullName;
    private String typeOfUser;
    private boolean approved;
    private ArrayList<Message> inbox;

    /**
     * Empty Constructor
     */
    public User(){

    }

    /**
     * This constructor initialises all the fields of a user
     * @param username This is the username that the user enters for signing in (all usernames are unique)
     * @param password This is the password that the user types for signing in
     * @param email This is the email of a user (all emails are unique)
     * @param country This is the country where the user is from
     * @param fullName This is the name + surname of the user
     */
    public User(String username,String password,String email,String country,String fullName){
        this.country = country;
        this.email = email;
        this.password = password;
        this.username = username;
        this.fullName = fullName;
        inbox = new ArrayList<>();
        approved = false;
    }

    /**
     * This method sets if a user's account is approved by an admin or not.
     * @param approved This variable is true when an account is approved and false when it's not approved
     */
    public void setApproved(boolean approved){
        this.approved = approved;
    }

    /**
     * @return This method returns true or false based on if an account is approved by an admin or not
     */
    public boolean getApproved(){
        return this.approved;
    }

    /**
     * This method adds a new message on a user's inbox
     * @param message This variable contains the message
     */
    public void addMessage(Message message){
        inbox.add(message);
    }

    /**
     * @return The message list of the user
     */
    public ArrayList<Message> getMessages(){
        return inbox;
    }

    /**
     * @return This method returns the username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * @return This method returns the password
     */
    public String getPassword(){
        return password;
    }

    /**
     *
     * @return This method returns the name of a user
     */
    public String getFullName() { return this.fullName; }

    /**
     *
     * @return This method returns the email of a user
     */
    public String getEmail() { return this.email; }

    /**
     *
     * @return This method returns the country that a user is from
     */
    public String getCountry() { return this.country; }

    /**
     * This method sets the type of user (Admin,Provider,Customer)
     * @param type The type can be one of the above
     */
    public void setTypeOfUser(String type) { typeOfUser = type; }

    /**
     * @return This method returns the type of user
     */
    public String getTypeOfUser() { return this.typeOfUser; }
}
