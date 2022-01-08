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

}
