import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class contains the panel that is shown when an admin wants to approve new users.
 * For each user there are two option : approve or disapprove.
 * After an account gets approved/disapproved, a new account shows up until the list with unapproved accounts gets empty.
 */
public class ApproveAccounts extends JPanel {
    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth());
    public final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());
    /**
     * @param admin This is the admin that is logged in the app
     * @param listOfAccounts The list of total accounts
     * @param listOfAccommodations The list of total Accommodations
     * @param listOfReservations The list of total Reservations
     * @param mainFrame The frame of the app
     */
    public ApproveAccounts(Admin admin,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){
        AtomicInteger i= new AtomicInteger();

        this.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        setBackground(Color.WHITE);

        int MARGIN_TITLE_FROM_TOP = 10;
        int pixelCounter = MARGIN_TITLE_FROM_TOP;

        JLabel label = new JLabel("Approve & Disapprove Accounts");
        label.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,28));
        label.setForeground(new Color(0x06307C));
        label.setBounds(0, MARGIN_TITLE_FROM_TOP, PANEL_WIDTH, 40);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);

        JLabel acc = new JLabel("Account");
        JLabel email = new JLabel("Email");
        JLabel fullName = new JLabel("Full Name");
        JLabel username = new JLabel("username");

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
            mainFrame.getContentPane().add(new AdminWindow(admin, listOfAccounts, listOfAccommodations,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
        });

        pixelCounter+=150;

        JLabel empty = new JLabel("The List is empty");
        empty.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,24));
        empty.setForeground(new Color(0x06307C));
        empty.setBounds(0,pixelCounter,PANEL_WIDTH,30);
        empty.setHorizontalAlignment(SwingConstants.CENTER);
        empty.setVisible(false);
        add(empty);

        JLabel error = new JLabel("No more new accounts to approve/disapprove");
        error.setBounds(0, pixelCounter, PANEL_WIDTH, 40);
        error.setHorizontalAlignment(SwingConstants.CENTER);
        error.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,18));
        error.setForeground(Color.red);
        error.setVisible(false);
        add(error);

        acc.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,20));
        acc.setForeground(new Color(0x06307C));
        acc.setBounds(0, pixelCounter, PANEL_WIDTH, 40);
        acc.setHorizontalAlignment(SwingConstants.CENTER);
        add(acc);

        pixelCounter += 50;
        email.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,20));
        email.setForeground(new Color(0x06307C));
        email.setBounds(0, pixelCounter, PANEL_WIDTH, 40);
        email.setHorizontalAlignment(SwingConstants.CENTER);
        add(email);

        pixelCounter += 50;
        fullName.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,20));
        fullName.setForeground(new Color(0x06307C));
        fullName.setBounds(0, pixelCounter, PANEL_WIDTH, 40);
        fullName.setHorizontalAlignment(SwingConstants.CENTER);
        add(fullName);

        pixelCounter += 50;
        username.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,20));
        username.setForeground(new Color(0x06307C));
        username.setBounds(0, pixelCounter, PANEL_WIDTH, 40);
        username.setHorizontalAlignment(SwingConstants.CENTER);
        add(username);

        pixelCounter += 200;
        JButton approve = new JButton("Approve");
        approve.setBounds(PANEL_WIDTH/3,pixelCounter,200,30);
        approve.setFont(new Font("sans serif",Font.ITALIC+Font.ITALIC,16));
        approve.setBackground(Color.GREEN);
        approve.setForeground(Color.WHITE);
        approve.setFocusable(false);
        approve.setVisible(false);
        add(approve);

        JButton disapprove = new JButton("Disapprove");
        disapprove.setFocusable(false);
        disapprove.setBackground(Color.RED);
        disapprove.setForeground(Color.white);
        disapprove.setBounds(PANEL_WIDTH/3 + 300,pixelCounter,200,30);
        disapprove.setVisible(false);
        add(disapprove);

        ArrayList<User> unapprovedAccounts = listOfAccounts.getUnapprovedAccount();

        if(unapprovedAccounts.size()==0){
            empty.setVisible(true);
            username.setVisible(false);
            fullName.setVisible(false);
            email.setVisible(false);
            acc.setVisible(false);
        }
        else{
            acc.setText("Type: "+unapprovedAccounts.get(0).getTypeOfUser());
            email.setText("Email: "+unapprovedAccounts.get(0).getEmail());
            fullName.setText("Full Name: "+unapprovedAccounts.get(0).getFullName());
            username.setText("Username: "+unapprovedAccounts.get(0).getUsername());
            empty.setVisible(false);
            approve.setVisible(true);
            disapprove.setVisible(true);

            approve.addActionListener(e -> {
                int result = i.intValue();
                if(result>unapprovedAccounts.size()-1){
                    error.setVisible(true);
                    username.setVisible(false);
                    fullName.setVisible(false);
                    email.setVisible(false);
                    acc.setVisible(false);
                }
               else{
                    System.out.println("Got here");
                    unapprovedAccounts.get(result).setApproved(true);
                    i.getAndIncrement();
                    listOfAccounts.updateAccounts();
                    if(result+1<unapprovedAccounts.size()){
                        acc.setText("Type: "+unapprovedAccounts.get(result+1).getTypeOfUser());
                        email.setText("Email: "+unapprovedAccounts.get(result+1).getEmail());
                        fullName.setText("Full Name: "+unapprovedAccounts.get(result+1).getFullName());
                        username.setText("Username: "+unapprovedAccounts.get(result+1).getUsername());
                    }
                    else{
                        error.setVisible(true);
                        username.setVisible(false);
                        fullName.setVisible(false);
                        email.setVisible(false);
                        acc.setVisible(false);
                    }
                }
            });

            disapprove.addActionListener(e -> {
                int result = i.intValue();
                if(result>unapprovedAccounts.size()-1){
                    error.setVisible(true);
                    username.setVisible(false);
                    fullName.setVisible(false);
                    email.setVisible(false);
                    acc.setVisible(false);
                }
                else{
                    System.out.println("Got here");
                    i.getAndIncrement();
                    listOfAccounts.updateAccounts();
                    if(result+1<unapprovedAccounts.size()){
                        acc.setText("Type: "+unapprovedAccounts.get(result+1).getTypeOfUser());
                        email.setText("Email: "+unapprovedAccounts.get(result+1).getEmail());
                        fullName.setText("Full Name: "+unapprovedAccounts.get(result+1).getFullName());
                        username.setText("Username: "+unapprovedAccounts.get(result+1).getUsername());
                    }
                    else{
                        error.setVisible(true);
                        username.setVisible(false);
                        fullName.setVisible(false);
                        email.setVisible(false);
                        acc.setVisible(false);
                    }
                }
                i.getAndIncrement();

            });

        }

        setLayout(null);
    }
}
