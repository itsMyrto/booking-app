import javax.swing.*;

public class CustomerWindow extends JPanel {
    public CustomerWindow(Customer customer,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        setSize(1500,800);
        JButton showProfile = new JButton("Show Profile");
        JButton searchAccommodations = new JButton("Search");
        JButton showReservations = new JButton("Show Reservations ");
        JButton showInbox = new JButton("Show inbox");
        JButton logout = new JButton("Log Out");

        searchAccommodations.setBounds(100,50,300,50);
        showReservations.setBounds(100,150,300,50);
        showProfile.setBounds(100,300,300,50);
        showInbox.setBounds(100,350,300,50);
        logout.setBounds(100,400,300,50);

        searchAccommodations.setFocusable(false);
       showReservations.setFocusable(false);
        showProfile.setFocusable(false);
        showInbox.setFocusable(false);
        logout.setFocusable(false);

        searchAccommodations.addActionListener(e -> {
            removeAll();
            repaint();
            add(new SearchAndBookAccommodations(customer,listOfAccounts,listOfAccommodations,listOfReservations));
        });

        showReservations.addActionListener(e->{
            removeAll();
            repaint();
            add(new ShowCustomerReservations(customer,listOfAccounts,listOfAccommodations,listOfReservations));
        });

        add(logout);
        add(showProfile);
        add(showInbox);
        add(showReservations);
        add(searchAccommodations);
        setOpaque(false);
        setLayout(null);
        setVisible(true);
    }
}

