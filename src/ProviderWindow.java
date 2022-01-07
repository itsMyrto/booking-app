import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProviderWindow extends JPanel {

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth() - MainFrame.FRAME_IMAGE_RESOLUTION[0]);
    private final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());

    private JButton addAccommodation = new JButton("Add Accommodation");
    private JButton showAccommodation = new JButton("Show All Accommodations");
    private JButton showProfile = new JButton("Show Profile");
    private JButton showInbox = new JButton("Show Inbox");
    private JButton logout = new JButton("Log out");

    private JLabel welcomeMessage = new JLabel();

    private final int TITLE_HEIGHT = 40;

    private final int BTN_WIDTH = 250;
    private final int BTN_HEIGHT = 40;

    private final int MARGIN_BTN_FROM_EDGES = (PANEL_WIDTH-BTN_WIDTH)/2;
    private final int MARGIN_TITLE_FROM_TOP = 80;
    private final int MARGIN_BETWEEN_BTN = 50;
    private final int MARGIN_BTN_FROM_TITLE = 70;

    private final String FONT = "Tahoma";
    private final Color CUSTOMIZED_COLOR = Color.decode("#3B5998");

    ProviderWindow(Provider provider,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations,MainFrame mainFrame){
        this.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        int pxCounter = MARGIN_TITLE_FROM_TOP;

        welcomeMessage.setText("Hello, " + provider.getFullName());
        welcomeMessage.setFont(new Font(FONT,Font.ITALIC+Font.BOLD,TITLE_HEIGHT-10));
        welcomeMessage.setForeground(CUSTOMIZED_COLOR);
        welcomeMessage.setBounds(0, pxCounter, PANEL_WIDTH, TITLE_HEIGHT);
        welcomeMessage.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeMessage.setOpaque(false);

        pxCounter += TITLE_HEIGHT + MARGIN_BTN_FROM_TITLE;

        addAccommodation.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        showAccommodation.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        showProfile.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        showInbox.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

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
