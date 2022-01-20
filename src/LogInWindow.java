
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * This class contains the panel that show the log in window
 */
public class LogInWindow extends JPanel{
    private MainFrame mainFrame;

    private JTextField username = new JTextField("Username");

    private JPasswordField password = new JPasswordField("Password");

    private final JLabel errorOccurred = new JLabel("");

    public LogInWindow(AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations, MainFrame mainFrame){
        this.mainFrame = mainFrame;

        int PANEL_WIDTH = mainFrame.getFRAME_WIDTH() - mainFrame.getIMG_WIDTH();
        int PANEL_HEIGHT = mainFrame.getFRAME_HEIGHT();

        // Firstly, create a counter variable to keep track of the vertical pixels
        int pixelCounter = 40;

        // Get the logo and scale it to fit any computer screen with the same output
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        double logoDimensions = (0.25* env.getMaximumWindowBounds().getHeight());
        Image rescaledImg = null;
        if (img != null) {
            rescaledImg = img.getScaledInstance((int) logoDimensions, (int) logoDimensions, Image.SCALE_SMOOTH);
        }
        ImageIcon icon = new ImageIcon(rescaledImg);
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(icon);
        logoLabel.setBounds(0, pixelCounter, PANEL_WIDTH, icon.getIconHeight());
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        int MARGIN_TITLE_FROM_LOGO = 10;
        pixelCounter += icon.getIconHeight() + MARGIN_TITLE_FROM_LOGO;

        int LOGIN_LABEL_HEIGHT = 30;
        JLabel loginLabel = new JLabel("Sign into your account");
        loginLabel.setBounds(0,pixelCounter, PANEL_WIDTH, LOGIN_LABEL_HEIGHT);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        loginLabel.setFont(new Font("Sans Sheriff", Font.BOLD, 20));
        loginLabel.setForeground(Color.decode("#3B5998"));

        int MARGIN_FIELDS_FROM_TITLE = 30;
        pixelCounter += LOGIN_LABEL_HEIGHT + MARGIN_FIELDS_FROM_TITLE;

        int TEXT_FIELDS_HEIGHT = 30;
        int TEXT_FIELDS_WIDTH = 200;
        username.setBounds((PANEL_WIDTH - TEXT_FIELDS_WIDTH)/2,pixelCounter, TEXT_FIELDS_WIDTH, TEXT_FIELDS_HEIGHT);

        JLabel username_label = new JLabel("Username: ");
        username_label.setFont(new Font("Sans Sheriff", Font.ITALIC, 14));
        username_label.setBounds(0, pixelCounter, (PANEL_WIDTH - TEXT_FIELDS_WIDTH)/2-5, TEXT_FIELDS_HEIGHT);
        username_label.setHorizontalAlignment(SwingConstants.RIGHT);

        int MARGIN_BETWEEN_FIELDS = 10;
        pixelCounter += TEXT_FIELDS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        password.setBounds((PANEL_WIDTH - TEXT_FIELDS_WIDTH)/2, pixelCounter, TEXT_FIELDS_WIDTH, TEXT_FIELDS_HEIGHT);

        JLabel password_label = new JLabel("Password: ");
        password_label.setFont(new Font("Sans Sheriff", Font.ITALIC, 14));
        password_label.setBounds(0, pixelCounter, (PANEL_WIDTH - TEXT_FIELDS_WIDTH)/2-5, TEXT_FIELDS_HEIGHT);
        password_label.setHorizontalAlignment(SwingConstants.RIGHT);

        int MARGIN_LOGIN_BTN_FROM_FIELDS = 45;
        pixelCounter += TEXT_FIELDS_HEIGHT + MARGIN_LOGIN_BTN_FROM_FIELDS;

        int MARGIN_BETWEEN_LOGIN_BTN_AND_ERROR_LABEL = 25;
        int ERROR_OCCURRED_HEIGHT = 20;
        errorOccurred.setBounds(0, pixelCounter- MARGIN_BETWEEN_LOGIN_BTN_AND_ERROR_LABEL, PANEL_WIDTH, ERROR_OCCURRED_HEIGHT);
        errorOccurred.setHorizontalAlignment(SwingConstants.CENTER);
        errorOccurred.setForeground(Color.red);

        JButton login = new JButton("Sign In");
        login.setFocusable(false);
        int LOGIN_BTN_HEIGHT = 40;
        int LOGIN_BTN_WIDTH = 100;
        login.setBounds((PANEL_WIDTH - LOGIN_BTN_WIDTH)/2, pixelCounter, LOGIN_BTN_WIDTH, LOGIN_BTN_HEIGHT);
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Tahoma", Font.BOLD, 16));
        login.setBackground(Color.decode("#3B5998"));

        int MARGIN_REGISTER_LABEL_FROM_LOGIN_BTN = 60;
        pixelCounter += LOGIN_LABEL_HEIGHT + MARGIN_REGISTER_LABEL_FROM_LOGIN_BTN;

        int REGISTER_LABEL_HEIGHT = 26;
        JLabel registerLabel = new JLabel("OR");
        registerLabel.setBounds(0,pixelCounter, PANEL_WIDTH, REGISTER_LABEL_HEIGHT);
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setFont(new Font("Tahoma", Font.ITALIC, REGISTER_LABEL_HEIGHT));
        registerLabel.setForeground(Color.BLACK);

        int MARGIN_REGISTER_BTN_FROM_REGISTER_LABEL = 40;
        int REGISTER_BTN_HEIGHT = 40;
        pixelCounter += REGISTER_BTN_HEIGHT + MARGIN_REGISTER_BTN_FROM_REGISTER_LABEL;

        JButton register = new JButton("Register Now");
        register.setFocusable(false);
        register.setOpaque(false);
        register.setBackground(Color.WHITE);
        int REGISTER_BTN_WIDTH = 200;
        register.setBounds((PANEL_WIDTH - REGISTER_BTN_WIDTH)/2,pixelCounter, REGISTER_BTN_WIDTH, REGISTER_BTN_HEIGHT);
        register.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, 16));
        register.setForeground(Color.decode("#3B5998"));

        pixelCounter += REGISTER_BTN_HEIGHT;

        username.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(username.getText().equals("Username")) username.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(username.getText().equals("")) username.setText("Username");
            }
        });

        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Arrays.equals(password.getPassword(), new char[]{'P', 'a', 's', 's', 'w', 'o', 'r', 'd'})) {
                    password.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(Arrays.equals(password.getPassword(), new char[]{})) password.setText("Password");
            }
        });

        username.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    loginBtnClicked(listOfAccounts, listOfAccommodations, listOfReservations);
            }
        });

        password.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    loginBtnClicked(listOfAccounts, listOfAccommodations, listOfReservations);
            }
        });

        login.addActionListener(e->{
            loginBtnClicked(listOfAccounts, listOfAccommodations, listOfReservations);
        });

        register.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new RegisterWindow(listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
            mainFrame.getContentPane().repaint();
        });

        // Add components to JPanel
        this.add(logoLabel);
        this.add(errorOccurred);
        this.add(username_label);
        this.add(password_label);
        this.add(username);
        this.add(password);
        this.add(loginLabel);
        this.add(registerLabel);
        this.add(login);
        this.add(register);

        // Set JPanel parameters
        this.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setOpaque(false);
        this.setLayout(null);
        this.setVisible(true);
    }

    /**
     * This method implements the functionality of all the buttons of this panel
     * @param listOfAccounts All the accounts
     * @param listOfAccommodations All the accommodations
     * @param listOfReservations All the reservations
     */
    private void loginBtnClicked(AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        String usernameTxt;
        String passwordTxt;
        usernameTxt = username.getText();
        passwordTxt = String.valueOf(password.getPassword());
        User user = listOfAccounts.logInAccount(usernameTxt,passwordTxt);
        String LOGIN_ERROR_MESSAGE = "An error has occurred. Please check your username or password.";
        if(user == null)
            errorOccurred.setText(LOGIN_ERROR_MESSAGE);
        else{
            String APPROVAL_ERROR_MESSAGE = "Sorry,your account haven't been approved yet";
            if(listOfAccounts.accountIsUnapproved(user))
                errorOccurred.setText(APPROVAL_ERROR_MESSAGE);
            else{
                if(user instanceof Customer){
                    mainFrame.remove(this);
                    mainFrame.getContentPane().repaint();
                    mainFrame.getContentPane().add(new CustomerWindow((Customer) user, listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
                    mainFrame.getContentPane().repaint();
                }
                else if(user instanceof Provider){
                    mainFrame.remove(this);
                    mainFrame.getContentPane().repaint();
                    mainFrame.getContentPane().add(new ProviderWindow(((Provider) user),listOfAccommodations,listOfAccounts,listOfReservations,mainFrame));
                    mainFrame.getContentPane().repaint();
                }
                else {
                    mainFrame.remove(this);
                    mainFrame.getContentPane().repaint();
                    mainFrame.getContentPane().add(new AdminWindow(((Admin) user), listOfAccounts, listOfAccommodations,listOfReservations,mainFrame));
                    mainFrame.getContentPane().repaint();
                }
            }
        }
    }
}

