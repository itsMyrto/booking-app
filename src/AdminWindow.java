import javax.swing.*;

public class AdminWindow extends JPanel {
    public AdminWindow(Admin admin,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        setOpaque(false);
        setLayout(null);
        setVisible(true);
        setSize(1500,800);
        JButton tracking = new JButton("Tracking");
        JButton showProfile = new JButton("Show Profile");
        JButton showInbox = new JButton("Send messages/Show Inbox");
        JButton logout = new JButton("Log out");

        tracking.setBounds(100,50,200,30);
        showProfile.setBounds(100,100,200,30);
        showInbox.setBounds(100,170,200,30);
        logout.setBounds(100,250,200,30);


        tracking.addActionListener(e -> {
            removeAll();
            repaint();
            add(new TrackingWindow(admin,listOfAccounts,listOfAccommodations,listOfReservations));
        });

        add(tracking);
        add(showProfile);
        add(showInbox);
        add(logout);
    }
}
