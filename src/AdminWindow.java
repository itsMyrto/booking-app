import javax.swing.*;
import java.awt.*;

public class AdminWindow extends JPanel {
    public AdminWindow(Admin admin,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){
        setOpaque(false);
        setLayout(null);
        setVisible(true);
        setBackground(Color.white);
        setSize(1500,800);
        JButton tracking = new JButton("Track other Users");
        JButton approveUser = new JButton("Approve Users");
        JButton showProfile = new JButton("Show Profile");
        JButton showInbox = new JButton("Send messages/Show Inbox");
        JButton logout = new JButton("Log out");

        tracking.setBounds(100,50,200,30);
        showProfile.setBounds(100,100,200,30);
        showInbox.setBounds(100,170,200,30);
        logout.setBounds(100,250,200,30);

        JLabel welcomeMessage = new JLabel("Hello, "+admin.getFullName());
        welcomeMessage.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,26));
        welcomeMessage.setForeground(new Color(0x06307C));
        welcomeMessage.setBounds(200,100,400,50);
        setOpaque(false);
        add(welcomeMessage);

        tracking.setBounds(200,260,250,50);
        showProfile.setBounds(200,340,250,50);
        showInbox.setBounds(200,420,250,50);
        logout.setBounds(200,500,250,50);
        approveUser.setBounds(200,180,250,50);

        approveUser.setBackground(Color.WHITE);
        approveUser.setForeground(new Color(0x06307C));
        approveUser.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,15));

        tracking.setBackground(Color.WHITE);
        tracking.setForeground(new Color(0x06307C));
        tracking.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,15));

        showProfile.setBackground(Color.WHITE);
        showProfile.setForeground(new Color(0x06307C));
        showProfile.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,15));

        showInbox.setBackground(Color.WHITE);
        showInbox.setForeground(new Color(0x06307C));
        showInbox.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,15));

        logout.setBackground(Color.WHITE);
        logout.setForeground(new Color(0x06307C));
        logout.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,15));

        approveUser.setFocusable(false);
        tracking.setFocusable(false);
        showProfile.setFocusable(false);
        showInbox.setFocusable(false);
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

        logout.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new LogInWindow(listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
            mainFrame.getContentPane().repaint();
        });

        add(approveUser);
        add(tracking);
        add(showProfile);
        add(showInbox);
        add(logout);
    }
}
