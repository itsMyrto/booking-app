import java.io.Serializable;

/**
 * This class represents a message that can be sent from admins to all the users(other admins,providers,customers)
 */
public class Message implements Serializable {
    private String text;
    private User sender;

    /**
     * Constructor that initialises the text & the user
     * @param text The message that is going to be sent
     * @param sender The person who sends the message
     */
    public Message(String text,User sender){
        this.text = text;
        this.sender = sender;
    }

    /**
     * This method is a getter for the message
     * @return The text
     */
    public String getMessage(){
        return this.text;
    }

    /**
     * This method is a getter for the sender
     * @return the user who sent the message
     */
    public User getSender(){
        return this.sender;
    }
}
