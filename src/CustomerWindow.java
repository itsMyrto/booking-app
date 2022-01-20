import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class creates the panel that contains the main menu of the customer when a customer logs in the app.
 * It shows some buttons with all the actions that a customer can do such as search accommodations,check the reservations,check the inbox.
 */
public class CustomerWindow extends JPanel {
    /**
     * The constructor that creates the panel
     * @param customer The customer who is currently logged in
     * @param listOfAccounts A list of all the accounts of the app
     * @param listOfAccommodations A list of all the accommodations of the app
     * @param listOfReservations A list of all the reservations of the app
     * @param mainFrame The main frame of the application
     */
    public CustomerWindow(Customer customer,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations, MainFrame mainFrame){
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight() + 1);
        int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth() + 1) - MainFrame.FRAME_IMAGE_RESOLUTION[0];
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/magnifier.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int BTN_HEIGHT = 40;
        double logoDimensions = BTN_HEIGHT *0.75;
        Image rescaledImg = null;
        if (img != null) {
            rescaledImg = img.getScaledInstance((int) logoDimensions, (int) logoDimensions, Image.SCALE_SMOOTH);
        }
        ImageIcon icon = new ImageIcon(rescaledImg);

        int MARGIN_TITLE_FROM_TOP = 80;
        int pixelCounter = MARGIN_TITLE_FROM_TOP;

        JLabel welcomeUserLabel = new JLabel();
        welcomeUserLabel.setText("Hello, " + customer.getFullName());
        welcomeUserLabel.setOpaque(false);
        int WELCOME_LABEL_HEIGHT = 40;
        welcomeUserLabel.setBounds(0, MARGIN_TITLE_FROM_TOP, PANEL_WIDTH, WELCOME_LABEL_HEIGHT);
        Color CUSTOMIZED_COLOR = Color.decode("#3B5998");
        welcomeUserLabel.setForeground(CUSTOMIZED_COLOR);
        welcomeUserLabel.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, WELCOME_LABEL_HEIGHT -10));
        welcomeUserLabel.setHorizontalAlignment(SwingConstants.CENTER);

        int MARGIN_SEARCH_BTN_FROM_TITLE = 50;
        pixelCounter += WELCOME_LABEL_HEIGHT + MARGIN_SEARCH_BTN_FROM_TITLE;

        JButton searchBtn = new JButton("Find hotels");
        searchBtn.setBackground(CUSTOMIZED_COLOR);
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        searchBtn.setIcon(icon);
        searchBtn.setHorizontalTextPosition(SwingConstants.LEFT);
        searchBtn.setIconTextGap(20);
        searchBtn.setFocusable(false);
        int BTN_WIDTH = 200;
        searchBtn.setBounds((PANEL_WIDTH - BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);

        int MARGIN_BTN_FROM_FIRST_BTN = 100;
        pixelCounter += BTN_HEIGHT + MARGIN_BTN_FROM_FIRST_BTN;

        JButton showReservationsBtn = new JButton("Show Reservations ");
        showReservationsBtn.setBackground(Color.WHITE);
        showReservationsBtn.setForeground(CUSTOMIZED_COLOR);
        showReservationsBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        showReservationsBtn.setFocusable(false);
        showReservationsBtn.setBounds((PANEL_WIDTH - BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);

        int MARGIN_BETWEEN_BTN = 20;
        pixelCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        JButton profileBtn = new JButton("Show Profile");
        profileBtn.setBackground(Color.WHITE);
        profileBtn.setForeground(CUSTOMIZED_COLOR);
        profileBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        profileBtn.setFocusable(false);
        profileBtn.setBounds((PANEL_WIDTH - BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);

        pixelCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        JButton inboxBtn = new JButton("Show inbox");
        inboxBtn.setBackground(Color.WHITE);
        inboxBtn.setForeground(CUSTOMIZED_COLOR);
        inboxBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        inboxBtn.setFocusable(false);
        inboxBtn.setBounds((PANEL_WIDTH - BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);

        pixelCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        JButton logoutBtn = new JButton("Log Out");
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(CUSTOMIZED_COLOR);
        logoutBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        logoutBtn.setFocusable(false);
        logoutBtn.setBounds((PANEL_WIDTH - BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);

        searchBtn.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.removeInitialImage();
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new SearchAndBookAccommodations(customer,listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
            mainFrame.getContentPane().repaint();
        });

        inboxBtn.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.removeInitialImage();
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new ShowMessages(customer,listOfAccounts,listOfAccommodations,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
        });

        showReservationsBtn.addActionListener(e->{
            mainFrame.remove(this);
            mainFrame.removeInitialImage();
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new ShowCustomerReservations(customer,listOfAccounts,listOfAccommodations,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
        });

        profileBtn.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.removeInitialImage();
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new UserProfileWindow(customer,listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
            mainFrame.getContentPane().repaint();
        });

        logoutBtn.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new LogInWindow(listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
            mainFrame.getContentPane().repaint();
        });

        this.add(welcomeUserLabel);
        this.add(logoutBtn);
        this.add(profileBtn);
        this.add(inboxBtn);
        this.add(showReservationsBtn);
        this.add(searchBtn);

        this.setOpaque(false);
        this.setLayout(null);
        this.setVisible(true);
    }
}