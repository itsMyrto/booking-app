import javax.swing.*;
import java.awt.*;

/**
 * This class creates the main frame of the application.
 * The login page is added.
 */
public class MainFrame extends JFrame {
    private LogInWindow login;

    private JLabel img = new JLabel();

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final int FRAME_WIDTH = env.getMaximumWindowBounds().width;
    private final int FRAME_HEIGHT = env.getMaximumWindowBounds().height;

    private final int IMG_WIDTH = (int) (FRAME_WIDTH*0.6);
    private final int IMG_HEIGHT = FRAME_HEIGHT;
    private final int MARGIN_IMG_FROM_LEFT_EDGE = FRAME_WIDTH - IMG_WIDTH;

    public static final int[] FRAME_IMAGE_RESOLUTION = {800, 829};

    public MainFrame(AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        login = new LogInWindow(listOfAccounts,listOfAccommodations,listOfReservations, this);
        login.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        ImageIcon icon = new ImageIcon("src/hotel.jpg");
        img.setIcon(icon);
        img.setBounds(MARGIN_IMG_FROM_LEFT_EDGE, 0, IMG_WIDTH, IMG_HEIGHT);
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

    public int getFRAME_WIDTH(){
        return FRAME_WIDTH;
    }

    public int getFRAME_HEIGHT(){
        return FRAME_HEIGHT;
    }

    /**
     *
     * @return the image width
     */
    public int getIMG_WIDTH(){
        return IMG_WIDTH;
    }

    /**
     *
     * @return the image height
     */
    public int getIMG_HEIGHT(){
        return IMG_HEIGHT;
    }

    /**
     * This method adds an image into the frame
     */
    public void addInitialImage() { this.add(img); }

    /**
     * This method removes an image from the frame
     */
    public void removeInitialImage(){
        this.remove(img);
    }

    /**
     * Empty Constructor
     */
    public MainFrame(){

    }
}