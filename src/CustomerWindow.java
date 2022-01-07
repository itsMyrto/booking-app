import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CustomerWindow extends JPanel {
    private MainFrame mainFrame;

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth()+1) - MainFrame.FRAME_IMAGE_RESOLUTION[0];
    private final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight()+1);

    private JLabel welcomeUserLabel = new JLabel();

    private JButton profileBtn = new JButton("Show Profile");
    private JButton searchBtn = new JButton("Find hotels");
    private JButton showReservationsBtn = new JButton("Show Reservations ");
    private JButton inboxBtn = new JButton("Show inbox");
    private JButton logoutBtn = new JButton("Log Out");

    private final int BTN_WIDTH = 200;
    private final int BTN_HEIGHT = 40;

    private final int WELCOME_LABEL_HEIGHT = 40;

    private final int MARGIN_TITLE_FROM_TOP = 80;
    private final int MARGIN_SEARCH_BTN_FROM_TITLE = 50;
    private final int MARGIN_BTN_FROM_FIRST_BTN = 100;
    private final int MARGIN_BETWEEN_BTN = 20;

    private final String FONT = "Tahoma";
    private final Color CUSTOMIZED_COLOR = Color.decode("#3B5998");

    public CustomerWindow(Customer customer,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations, MainFrame mainFrame){
        this.mainFrame = mainFrame;
        this.setSize(PANEL_WIDTH,PANEL_HEIGHT);

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/magnifier.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        double logoDimensions = BTN_HEIGHT*0.75;
        Image rescaledImg = null;
        if (img != null) {
            rescaledImg = img.getScaledInstance((int) logoDimensions, (int) logoDimensions, Image.SCALE_SMOOTH);
        }
        ImageIcon icon = new ImageIcon(rescaledImg);

        int pixelCounter = MARGIN_TITLE_FROM_TOP;

        welcomeUserLabel.setText("Hello, " + customer.getFullName());
        welcomeUserLabel.setOpaque(false);
        welcomeUserLabel.setBounds(0, MARGIN_TITLE_FROM_TOP, PANEL_WIDTH, WELCOME_LABEL_HEIGHT);
        welcomeUserLabel.setForeground(CUSTOMIZED_COLOR);
        welcomeUserLabel.setFont(new Font(FONT, Font.BOLD+Font.ITALIC, WELCOME_LABEL_HEIGHT-10));
        welcomeUserLabel.setHorizontalAlignment(SwingConstants.CENTER);

        pixelCounter += WELCOME_LABEL_HEIGHT + MARGIN_SEARCH_BTN_FROM_TITLE;

        searchBtn.setBackground(CUSTOMIZED_COLOR);
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFont(new Font(FONT, Font.BOLD, 16));
        searchBtn.setIcon(icon);
        searchBtn.setHorizontalTextPosition(SwingConstants.LEFT);
        searchBtn.setIconTextGap(20);
        searchBtn.setFocusable(false);
        searchBtn.setBounds((PANEL_WIDTH-BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);

        pixelCounter += BTN_HEIGHT + MARGIN_BTN_FROM_FIRST_BTN;

        showReservationsBtn.setBackground(Color.WHITE);
        showReservationsBtn.setForeground(CUSTOMIZED_COLOR);
        showReservationsBtn.setFont(new Font(FONT, Font.BOLD, 16));
        showReservationsBtn.setFocusable(false);
        showReservationsBtn.setBounds((PANEL_WIDTH-BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);

        pixelCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        profileBtn.setBackground(Color.WHITE);
        profileBtn.setForeground(CUSTOMIZED_COLOR);
        profileBtn.setFont(new Font(FONT, Font.BOLD, 16));
        profileBtn.setFocusable(false);
        profileBtn.setBounds((PANEL_WIDTH-BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);

        pixelCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        inboxBtn.setBackground(Color.WHITE);
        inboxBtn.setForeground(CUSTOMIZED_COLOR);
        inboxBtn.setFont(new Font(FONT, Font.BOLD, 16));
        inboxBtn.setFocusable(false);
        inboxBtn.setBounds((PANEL_WIDTH-BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);

        pixelCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setForeground(CUSTOMIZED_COLOR);
        logoutBtn.setFont(new Font(FONT, Font.BOLD, 16));
        logoutBtn.setFocusable(false);
        logoutBtn.setBounds((PANEL_WIDTH-BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);

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

