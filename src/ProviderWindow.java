import javax.swing.*;
import java.awt.*;

/**
 * This class creates the panel that shows the main menu of a provider that contains a few buttons for each action like
 * add an accommodation show/manage accommodation & show profile
 */
public class ProviderWindow extends JPanel {

    /**
     * The constructor that creates the panel
     * @param provider The provider who is logged in
     * @param listOfAccommodations A list with all the accommodations
     * @param listOfAccounts A list with all the accounts
     * @param listOfReservations A list with all the reservations
     * @param mainFrame The frame of the app
     */
    ProviderWindow(Provider provider,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations,MainFrame mainFrame){
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());
        int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth() - MainFrame.FRAME_IMAGE_RESOLUTION[0]);
        this.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        int pxCounter = 80;

        JLabel welcomeMessage = new JLabel();
        welcomeMessage.setText("Hello, " + provider.getFullName());
        String FONT = "Tahoma";
        int TITLE_HEIGHT = 40;
        welcomeMessage.setFont(new Font(FONT,Font.ITALIC+Font.BOLD, TITLE_HEIGHT -10));
        Color CUSTOMIZED_COLOR = Color.decode("#3B5998");
        welcomeMessage.setForeground(CUSTOMIZED_COLOR);
        welcomeMessage.setBounds(0, pxCounter, PANEL_WIDTH, TITLE_HEIGHT);
        welcomeMessage.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeMessage.setOpaque(false);

        int MARGIN_BTN_FROM_TITLE = 70;
        pxCounter += TITLE_HEIGHT + MARGIN_BTN_FROM_TITLE;

        int BTN_WIDTH = 250;
        int MARGIN_BTN_FROM_EDGES = (PANEL_WIDTH - BTN_WIDTH) / 2;
        int BTN_HEIGHT = 40;
        JButton addAccommodation = new JButton("Add Accommodation");
        addAccommodation.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        int MARGIN_BETWEEN_BTN = 50;
        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        JButton showAccommodation = new JButton("Show All Accommodations");
        showAccommodation.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        JButton showProfile = new JButton("Show Profile");
        showProfile.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        JButton showInbox = new JButton("Show Inbox");
        showInbox.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        JButton logout = new JButton("Log out");
        logout.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        addAccommodation.setBackground(Color.WHITE);
        addAccommodation.setForeground(CUSTOMIZED_COLOR);
        addAccommodation.setFont(new Font(FONT,Font.BOLD,16));
        addAccommodation.setFocusable(false);

        showAccommodation.setBackground(Color.WHITE);
        showAccommodation.setForeground(CUSTOMIZED_COLOR);
        showAccommodation.setFont(new Font(FONT,Font.BOLD,16));
        showAccommodation.setFocusable(false);

        showProfile.setBackground(Color.WHITE);
        showProfile.setForeground(CUSTOMIZED_COLOR);
        showProfile.setFont(new Font(FONT,Font.BOLD,16));
        showProfile.setFocusable(false);

        showInbox.setBackground(Color.WHITE);
        showInbox.setForeground(CUSTOMIZED_COLOR);
        showInbox.setFont(new Font(FONT,Font.BOLD,16));
        showInbox.setFocusable(false);

        logout.setBackground(Color.WHITE);
        logout.setForeground(CUSTOMIZED_COLOR);
        logout.setFont(new Font(FONT,Font.BOLD,16));
        logout.setFocusable(false);

        showInbox.addActionListener(e->{
            mainFrame.remove(this);
            mainFrame.removeInitialImage();
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new ShowMessages(provider, listOfAccounts, listOfAccommodations,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
        });

        addAccommodation.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new AccommodationForm(provider,listOfAccommodations,listOfAccounts,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
        });

        showProfile.addActionListener(e-> {
            mainFrame.remove(this);
            mainFrame.removeInitialImage();
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new UserProfileWindow(provider,listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
            mainFrame.getContentPane().repaint();
        });

        showAccommodation.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.removeInitialImage();
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new ProviderAccommodationList(provider,listOfAccommodations,listOfAccounts,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();

        });

        logout.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new LogInWindow(listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
            mainFrame.getContentPane().repaint();
        });

        this.add(addAccommodation);
        this.add(showAccommodation);
        this.add(showProfile);
        this.add(showInbox);
        this.add(logout);
        this.add(welcomeMessage);

        this.setOpaque(false);
        this.setLayout(null);
        this.setVisible(true);
    }
}
