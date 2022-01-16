import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * This class shows a bootstrap card which contains all the useful details of a user, such as name,email and origin.
 */
public class UserProfileWindow extends JPanel {
    /**
     * This is the constructor where the whole panel is been created
     * @param user This is the user whose bootstrap card is going to be shown
     * @param listOfAccounts This is the list that contains all the created accounts
     * @param listOfAccommodations This is the list that contains all the accommodation
     * @param listOfReservations This is the list that contains all the reservations
     * @param mainFrame This is the main frame of the program
     */
    public UserProfileWindow(User user, AccountsCreated listOfAccounts, AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame) {
        setSize(1500,800);
        setLayout(null);
        setBackground(Color.WHITE);

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(10,10,50,20);
        returnButton.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        returnButton.setForeground(new Color(191, 0, 255));
        returnButton.setBackground(Color.white);
        returnButton.setFocusable(false);
        returnButton.setBorder(null);
        add(returnButton);

        returnButton.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.addInitialImage();
            if(user instanceof Customer){
                mainFrame.getContentPane().add(new CustomerWindow((Customer) user, listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
            }
            else if(user instanceof Provider){
                mainFrame.getContentPane().add(new ProviderWindow(((Provider) user),listOfAccommodations,listOfAccounts,listOfReservations,mainFrame));
            }else{
                mainFrame.getContentPane().add(new AdminWindow((Admin)user, listOfAccounts, listOfAccommodations,listOfReservations,mainFrame));
            }
            mainFrame.getContentPane().repaint();
        });


        JLabel imgLabel = new JLabel(new ImageIcon("src/profileimage.png"));
        JLabel type = new JLabel(user.getTypeOfUser().toUpperCase(Locale.ROOT));
        type.setForeground(Color.WHITE);
        type.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,28));
        type.setBounds(800,250,300,30);

        JLabel personalInfo = new JLabel("Personal Information");
        JLabel email = new JLabel("Email: "+user.getEmail());
        JLabel fullName = new JLabel("Full Name: "+user.getFullName());
        JLabel country = new JLabel("Origin: "+user.getCountry());

        personalInfo.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,28));
        personalInfo.setBounds(600,50,400,50);
        personalInfo.setForeground(new Color(0x06307C));

        email.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,20));
        email.setBounds(780,410,400,50);
        email.setForeground(Color.white);

        fullName.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,20));
        fullName.setBounds(780,330,400,50);
        fullName.setForeground(Color.WHITE);

        country.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,20));
        country.setBounds(780,495,400,50);
        country.setForeground(Color.WHITE);


        imgLabel.setBounds(250,200,1000,400);
        add(type);
        add(email);
        add(personalInfo);
        add(fullName);
        add(country);
        add(imgLabel);
    }
}
