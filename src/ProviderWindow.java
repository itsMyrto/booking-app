import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProviderWindow extends JPanel {

    JButton addAccommodation = new JButton("Add Accommodation");
    JButton showAccommodation = new JButton("Show All Accommodations");
    JButton showProfile = new JButton("Show Profile");
    JButton showInbox = new JButton("Show Inbox");
    JButton logout = new JButton("Log out");


    ProviderWindow(Provider provider,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations,MainFrame mainFrame){
        this.setSize(new Dimension(1500, 800));

        JLabel welcomeMessage = new JLabel("Hello, "+provider.getFullName());
        welcomeMessage.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,28));
        welcomeMessage.setForeground(new Color(0x06307C));
        welcomeMessage.setBounds(270,100,300,50);
        setOpaque(false);
        addAccommodation.setBounds(200,200,250,50);
        showAccommodation.setBounds(200,280,250,50);
        showProfile.setBounds(200,360,250,50);
        showInbox.setBounds(200,440,250,50);
        logout.setBounds(200,520,250,50);

        addAccommodation.setBackground(Color.WHITE);
        addAccommodation.setForeground(new Color(0x06307C));
        addAccommodation.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,15));

        showAccommodation.setBackground(Color.WHITE);
        showAccommodation.setForeground(new Color(0x06307C));
        showAccommodation.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,15));

        showProfile.setBackground(Color.WHITE);
        showProfile.setForeground(new Color(0x06307C));
        showProfile.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,15));

        showInbox.setBackground(Color.WHITE);
        showInbox.setForeground(new Color(0x06307C));
        showInbox.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,15));

        logout.setBackground(Color.WHITE);
        logout.setForeground(new Color(0x06307C));
        logout.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,15));

        addAccommodation.setFocusable(false);
        showAccommodation.setFocusable(false);
        showProfile.setFocusable(false);
        showInbox.setFocusable(false);
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

        add(addAccommodation);
        add(showAccommodation);
        add(showProfile);
        add(showInbox);
        add(logout);
        add(welcomeMessage);
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