import javax.swing.*;
import java.awt.*;

public class AdminWindow extends JPanel {

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth() - MainFrame.FRAME_IMAGE_RESOLUTION[0]);
    private final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());

    private JLabel welcomeMessage = new JLabel();

    JButton tracking = new JButton("Track other Users");
    JButton approveUser = new JButton("Approve Users");
    JButton showProfile = new JButton("Show Profile");
    JButton showInbox = new JButton("Send messages/Show Inbox");
    JButton logout = new JButton("Log out");

    private final int TITLE_HEIGHT = 40;

    private final int BTN_WIDTH = 270;
    private final int BTN_HEIGHT = 40;

    private final int MARGIN_BTN_FROM_EDGES = (PANEL_WIDTH-BTN_WIDTH)/2;
    private final int MARGIN_TITLE_FROM_TOP = 80;
    private final int MARGIN_BETWEEN_BTN = 50;
    private final int MARGIN_BTN_FROM_TITLE = 70;

    private final String FONT = "Tahoma";
    private final Color CUSTOMIZED_COLOR = Color.decode("#3B5998");

    public AdminWindow(Admin admin,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){
        this.setSize(1500,800);

        int pxCounter = MARGIN_TITLE_FROM_TOP;

        welcomeMessage.setText("Hello, " + admin.getFullName());
        welcomeMessage.setFont(new Font(FONT,Font.ITALIC+Font.BOLD,TITLE_HEIGHT-10));
        welcomeMessage.setForeground(CUSTOMIZED_COLOR);
        welcomeMessage.setBounds(0, pxCounter, PANEL_WIDTH, TITLE_HEIGHT);
        welcomeMessage.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeMessage.setOpaque(false);

        pxCounter += TITLE_HEIGHT + MARGIN_BTN_FROM_TITLE;

        approveUser.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        tracking.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        showProfile.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        showInbox.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        pxCounter += BTN_HEIGHT + MARGIN_BETWEEN_BTN;

        logout.setBounds(MARGIN_BTN_FROM_EDGES, pxCounter, BTN_WIDTH, BTN_HEIGHT);

        approveUser.setBackground(Color.WHITE);
        approveUser.setForeground(CUSTOMIZED_COLOR);
        approveUser.setFont(new Font(FONT,Font.BOLD,16));
        approveUser.setFocusable(false);

        tracking.setBackground(Color.WHITE);
        tracking.setForeground(CUSTOMIZED_COLOR);
        tracking.setFont(new Font(FONT,Font.BOLD,16));
        tracking.setFocusable(false);

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

        tracking.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.removeInitialImage();
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new TrackingWindow(admin,listOfAccounts,listOfAccommodations,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
        });

        showProfile.addActionListener(e-> {
            mainFrame.remove(this);
            mainFrame.removeInitialImage();
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new UserProfileWindow(admin,listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
            mainFrame.getContentPane().repaint();
        });

        approveUser.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.removeInitialImage();
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new ApproveAccounts(admin,listOfAccounts,listOfAccommodations,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();

        });

        showInbox.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.removeInitialImage();
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new ShowMessagesAdmin(admin,listOfAccounts,listOfAccommodations,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
        });

        logout.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new LogInWindow(listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
            mainFrame.getContentPane().repaint();
        });

        this.add(welcomeMessage);
        this.add(approveUser);
        this.add(tracking);
        this.add(showProfile);
        this.add(showInbox);
        this.add(logout);

        this.setOpaque(false);
        this.setLayout(null);
        this.setVisible(true);
    }
}
