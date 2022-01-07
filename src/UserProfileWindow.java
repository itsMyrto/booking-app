import javax.swing.*;

public class UserProfileWindow extends JPanel {
    public UserProfileWindow(User user, AccountsCreated listOfAccounts, AccommodationsCreated listOfAccommodations){
        setSize(1500,800);
        setLayout(null);
        JLabel imgLabel = new JLabel(new ImageIcon("src/profile.png"));
        add(imgLabel);
        //Email: user.getEmail();
        //Full Name: user.getFullName();
        //Country : user.getCountry();
    }
}
