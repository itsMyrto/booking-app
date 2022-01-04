import javax.swing.*;
import java.awt.*;

public class ProviderWindow extends JPanel {

    JButton addAccommodation = new JButton("Add Accommodation");
    JButton changeAccommodation = new JButton("Change Accommodation");
    JButton showAccommodation = new JButton("Show All Accommodation");
    JButton showReservedAccommodation = new JButton("Show Reserved Accommodation");
    JButton deleteAccommodation = new JButton("Delete an Accommodation");
    JButton showProfile = new JButton("Show Profile");
    JButton showInbox = new JButton("Show Inbox");
    JButton logout = new JButton("Log out");

    ProviderWindow(Provider provider,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations){
        this.setSize(new Dimension(1500, 800));
        setOpaque(false);
        addAccommodation.setBounds(100,50,300,50);
        deleteAccommodation.setBounds(100,100,300,50);
        changeAccommodation.setBounds(100,150,300,50);
        showAccommodation.setBounds(100,200,300,50);
        showReservedAccommodation.setBounds(100,250,300,50);
        showProfile.setBounds(100,300,300,50);
        showInbox.setBounds(100,350,300,50);
        logout.setBounds(100,400,300,50);

        addAccommodation.setFocusable(false);
        deleteAccommodation.setFocusable(false);
        changeAccommodation.setFocusable(false);
        showAccommodation.setFocusable(false);
        showReservedAccommodation.setFocusable(false);
        showProfile.setFocusable(false);
        showInbox.setFocusable(false);
        logout.setFocusable(false);

        addAccommodation.addActionListener(e -> {
            removeAll();
            repaint();
            add(new AccommodationForm(provider,listOfAccommodations,listOfAccounts,listOfReservations));
        });

        showProfile.addActionListener(e-> {

        });

        showAccommodation.addActionListener(e -> {
            removeAll();
            repaint();
            add(new ProviderAccommodationList(provider,listOfAccommodations,listOfAccounts,listOfReservations));
        });

        logout.addActionListener(e -> {
            removeAll();
            repaint();
            add(new LogInWindow(listOfAccounts,listOfAccommodations,listOfReservations));
        });

        add(addAccommodation);
        add(changeAccommodation);
        add(showAccommodation);
        add(showReservedAccommodation);
        add(deleteAccommodation);
        add(showProfile);
        add(showInbox);
        add(logout);
        setLayout(null);
        setVisible(true);
    }
}

/*
changeData.add(changeAccommodation);
        changeData.add(deleteAccommodation);
        showData.add(showAllAccommodations);
        showData.add(showReservedAccommodations);
        profileMenu.add(addAccommodation);
        profileMenu.add(changeData);
        profileMenu.add(showData);
        profileMenu.add(showProfile);
        profileMenu.add(showInbox);
        profileMenu.add(logout);
 */