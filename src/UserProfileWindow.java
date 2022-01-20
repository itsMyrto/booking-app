import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * This class shows a bootstrap card which contains all the useful details of a user, such as name,email and origin.
 */
public class UserProfileWindow extends JPanel {

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth());
    public final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());
    /**
     * This is the constructor where the whole panel is been created
     * @param user This is the user whose bootstrap card is going to be shown
     * @param listOfAccounts This is the list that contains all the created accounts
     * @param listOfAccommodations This is the list that contains all the accommodation
     * @param listOfReservations This is the list that contains all the reservations
     * @param mainFrame This is the main frame of the program
     */
    public UserProfileWindow(User user, AccountsCreated listOfAccounts, AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame) {

        this.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        setBackground(Color.WHITE);

        int MARGIN_TITLE_FROM_TOP = 10;
        int pixelCounter = MARGIN_TITLE_FROM_TOP;

        setLayout(null);
        setBackground(Color.WHITE);

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(0, MARGIN_TITLE_FROM_TOP,50,20);
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

        pixelCounter+=150;
        JLabel imgLabel = new JLabel(new ImageIcon("src/profileimage.png"));
        imgLabel.setBounds(0,pixelCounter,PANEL_WIDTH,400);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel type = new JLabel(user.getTypeOfUser().toUpperCase(Locale.ROOT));
        JLabel personalInfo = new JLabel("Personal Information");
        JLabel email = new JLabel("Email: "+user.getEmail());
        JLabel fullName = new JLabel("Full Name: "+user.getFullName());
        JLabel country = new JLabel("Origin: "+user.getCountry());

        pixelCounter+=50;
        type.setForeground(Color.WHITE);
        type.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,28));
        type.setBounds(0,pixelCounter,PANEL_WIDTH,30);
        type.setHorizontalAlignment(SwingConstants.CENTER);

        personalInfo.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,28));
        personalInfo.setBounds(0,MARGIN_TITLE_FROM_TOP,PANEL_WIDTH,30);
        personalInfo.setHorizontalAlignment(SwingConstants.CENTER);
        personalInfo.setForeground(new Color(0x06307C));

        pixelCounter+=95;
        email.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,20));
        email.setBounds(PANEL_WIDTH/2,pixelCounter,PANEL_WIDTH,30);
        email.setForeground(Color.white);


        pixelCounter+=75;
        fullName.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,20));
        fullName.setBounds(PANEL_WIDTH/2,pixelCounter,PANEL_WIDTH,30);
        fullName.setForeground(Color.WHITE);

        pixelCounter+=85;
        country.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,20));
        country.setBounds(PANEL_WIDTH/2,pixelCounter,PANEL_WIDTH,30);
        country.setForeground(Color.WHITE);

        add(returnButton);
        add(type);
        add(email);
        add(personalInfo);
        add(fullName);
        add(country);
        add(imgLabel);
    }
}
