

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

}


