import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class contains the panel that is shown when an admin wants to approve new users.
 * For each user there are two option : approve or disapprove.
 * After an account gets approved/disapproved, a new account shows up until the list with unapproved accounts gets empty.
 */
public class ApproveAccounts extends JPanel {

    /**
     * @param admin This is the admin that is logged in the app
     * @param listOfAccounts The list of total accounts
     * @param listOfAccommodations The list of total Accommodations
     * @param listOfReservations The list of total Reservations
     * @param mainFrame The frame of the app
     */
    public ApproveAccounts(Admin admin,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){
        AtomicInteger i= new AtomicInteger();
        setSize(1000,800);
        setBackground(Color.WHITE);
        JLabel label = new JLabel("Approve & Disapprove Accounts");
        label.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,28));
        label.setForeground(new Color(0x06307C));
        label.setBounds(500,50,500,50);
        add(label);

        JLabel acc = new JLabel("Account");
        JLabel email = new JLabel("Email");
        JLabel fullName = new JLabel("Full Name");
        JLabel username = new JLabel("username");
        JLabel error = new JLabel("No more new accounts to approve/disapprove");
        error.setBounds(490,300,800,100);
        error.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,18));
        error.setForeground(Color.red);
        error.setVisible(false);
        add(error);

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(10,10,50,20);
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

        JLabel empty = new JLabel("The List is empty");
        empty.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,24));
        empty.setForeground(new Color(0x06307C));
        empty.setBounds(610,300,400,30);
        empty.setVisible(false);
        add(empty);

        acc.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,20));
        acc.setForeground(new Color(0x06307C));
        acc.setBounds(600,200,400,30);
        add(acc);

        email.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,20));
        email.setForeground(new Color(0x06307C));
        email.setBounds(600,250,400,30);
        add(email);

        fullName.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,20));
        fullName.setForeground(new Color(0x06307C));
        fullName.setBounds(600,300,400,30);
        add(fullName);

        username.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,20));
        username.setForeground(new Color(0x06307C));
        username.setBounds(600,350,400,30);
        add(username);


        JButton approve = new JButton("Approve");
        JButton disapprove = new JButton("Disapprove");
        approve.setBounds(450,500,200,30);
        approve.setFont(new Font("sans serif",Font.ITALIC+Font.ITALIC,16));
        approve.setBackground(Color.GREEN);
        approve.setForeground(Color.WHITE);
        approve.setFocusable(false);
        disapprove.setFocusable(false);
        disapprove.setBackground(Color.RED);
        disapprove.setForeground(Color.white);
        disapprove.setBounds(720,500,200,30);

        approve.setVisible(false);
        disapprove.setVisible(false);
        add(approve);
        add(disapprove);

        ArrayList<User> unapprovedAccounts = listOfAccounts.getUnapprovedAccount();
        System.out.println(unapprovedAccounts.size());

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
