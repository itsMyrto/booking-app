import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

public class UserProfileWindow extends JPanel {
    public UserProfileWindow(User user, AccountsCreated listOfAccounts, AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame) {
        setSize(1500,800);
        setLayout(null);
        JLabel imgLabel = new JLabel(new ImageIcon("src/profile.png"));
        add(imgLabel);
        setVisible(true);
    }
}
