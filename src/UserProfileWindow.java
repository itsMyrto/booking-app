import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class UserProfileWindow extends JPanel {
    public UserProfileWindow(User user, AccountsCreated listOfAccounts, AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame) {
        setSize(1500,800);
        setLayout(null);
        setBackground(Color.WHITE);
        JLabel imgLabel = new JLabel(new ImageIcon("src/profileimage.png"));
        JLabel type = new JLabel(user.getTypeOfUser().toUpperCase(Locale.ROOT));
        type.setForeground(Color.WHITE);
        type.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,28));
        type.setBounds(800,250,300,30);

        JLabel personalInfo = new JLabel("Personal Information");
        JLabel email = new JLabel("Email: "+user.getEmail());
        JLabel fullName = new JLabel("Full Name: "+user.getFullName());
        JLabel country = new JLabel("Origin: "+user.getCountry());

        personalInfo.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,28));
        personalInfo.setBounds(600,50,400,50);
        personalInfo.setForeground(new Color(0x06307C));

        email.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,20));
        email.setBounds(780,410,400,50);
        email.setForeground(Color.white);

        fullName.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,20));
        fullName.setBounds(780,330,400,50);
        fullName.setForeground(Color.WHITE);

        country.setFont(new Font("Tahoma",Font.ITALIC+Font.BOLD,20));
        country.setBounds(780,495,400,50);
        country.setForeground(Color.WHITE);


        imgLabel.setBounds(250,200,1000,400);
        add(type);
        add(email);
        add(personalInfo);
        add(fullName);
        add(country);
        add(imgLabel);
    }
}
