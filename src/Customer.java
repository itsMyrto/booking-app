import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a customer, the type of user who uses the app for
 * booking accommodations. It inherits all the properties of the general class user,
 * but it has also features for searching,booking etc.
 */
public class Customer extends User implements Serializable {

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
    }


}
