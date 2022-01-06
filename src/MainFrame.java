import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private LogInWindow login;

    private JLabel img = new JLabel();

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

    public static final int[] FRAME_IMAGE_RESOLUTION = {800, 829};

    public MainFrame(AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        Rectangle bounds = env.getMaximumWindowBounds();

        login = new LogInWindow(listOfAccounts,listOfAccommodations,listOfReservations, this);
        login.setSize(new Dimension(bounds.getBounds().width, bounds.getBounds().height));

        ImageIcon icon = new ImageIcon("src/hotel.jpg");
        img.setIcon(icon);
        img.setBounds(bounds.getBounds().width-FRAME_IMAGE_RESOLUTION[0], 0, FRAME_IMAGE_RESOLUTION[0], FRAME_IMAGE_RESOLUTION[1]);
        img.setOpaque(false);

        this.getContentPane().add(login);
        this.getContentPane().add(img);

        this.setTitle("Hermes Reservation App");
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.white);
        this.setLayout(null);
        this.setVisible(true);
    }

    public void removeInitialImage(){
        this.remove(img);
    }

    public MainFrame(){

    }
}
