import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class LogInWindow extends JPanel{
    private MainFrame mainFrame;

    private JLabel logoLabel = new JLabel();
    private JLabel loginLabel = new JLabel("Sign into your account");

    private JLabel username_label = new JLabel("Username: ");
    private JTextField username = new JTextField("Username");

    private JLabel password_label = new JLabel("Password: ");
    private JPasswordField password = new JPasswordField("Password");

    private JLabel errorOccurred = new JLabel("");
    private JButton login = new JButton("Sign In");

    private JLabel registerLabel = new JLabel("OR");
    private JButton register = new JButton("Register Now");

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

    private final int LOGIN_LABEL_HEIGHT = 30;

    private int PANEL_WIDTH;
    private int PANEL_HEIGHT;

    private final int LOGIN_BTN_WIDTH = 100;
    private final int LOGIN_BTN_HEIGHT = 40;

    private final int REGISTER_BTN_WIDTH = 200;
    private final int REGISTER_BTN_HEIGHT = 40;

    private final int REGISTER_LABEL_HEIGHT = 26;

    private final int TEXT_FIELDS_WIDTH = 200;
    private final int TEXT_FIELDS_HEIGHT = 30;

    private final String LOGIN_ERROR_MESSAGE = "An error has occurred. Please check your username or password.";
    private final String APPROVAL_ERROR_MESSAGE = "Sorry,your account haven't been approved yet";
    private final int ERROR_OCCURRED_HEIGHT = 20;

    private final int MARGIN_LOGO_FROM_TOP = 40;
    private final int MARGIN_TITLE_FROM_LOGO = 10;
    private final int MARGIN_FIELDS_FROM_TITLE = 30;
    private final int MARGIN_BETWEEN_FIELDS = 10;
    private final int MARGIN_LOGIN_BTN_FROM_FIELDS = 45;
    private final int MARGIN_REGISTER_LABEL_FROM_LOGIN_BTN = 60;
    private final int MARGIN_REGISTER_BTN_FROM_REGISTER_LABEL = 40;
    private final int MARGIN_BETWEEN_LOGIN_BTN_AND_ERROR_LABEL = 25;

    public LogInWindow(AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations, MainFrame mainFrame){
        this.mainFrame = mainFrame;

        PANEL_WIDTH = mainFrame.getFRAME_WIDTH() - mainFrame.getIMG_WIDTH();
        PANEL_HEIGHT = mainFrame.getFRAME_HEIGHT();

        // Firstly, create a counter variable to keep track of the vertical pixels
        int pixelCounter = MARGIN_LOGO_FROM_TOP;

        // Get the logo and scale it to fit any computer screen with the same output
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        double logoDimensions = (0.25*env.getMaximumWindowBounds().getHeight());
        Image rescaledImg = null;
        if (img != null) {
            rescaledImg = img.getScaledInstance((int) logoDimensions, (int) logoDimensions, Image.SCALE_SMOOTH);
        }
        ImageIcon icon = new ImageIcon(rescaledImg);
        logoLabel.setIcon(icon);
        logoLabel.setBounds(0, pixelCounter, PANEL_WIDTH, icon.getIconHeight());
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        pixelCounter += icon.getIconHeight() + MARGIN_TITLE_FROM_LOGO;

        loginLabel.setBounds(0,pixelCounter, PANEL_WIDTH, LOGIN_LABEL_HEIGHT);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        loginLabel.setFont(new Font("Sans Sheriff", Font.BOLD, 20));
        loginLabel.setForeground(Color.decode("#3B5998"));

        pixelCounter += LOGIN_LABEL_HEIGHT + MARGIN_FIELDS_FROM_TITLE;

        username.setBounds((PANEL_WIDTH-TEXT_FIELDS_WIDTH)/2,pixelCounter, TEXT_FIELDS_WIDTH, TEXT_FIELDS_HEIGHT);

        username_label.setFont(new Font("Sans Sheriff", Font.ITALIC, 14));
        username_label.setBounds(0, pixelCounter, (PANEL_WIDTH-TEXT_FIELDS_WIDTH)/2-5, TEXT_FIELDS_HEIGHT);
        username_label.setHorizontalAlignment(SwingConstants.RIGHT);

        pixelCounter += TEXT_FIELDS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        password.setBounds((PANEL_WIDTH-TEXT_FIELDS_WIDTH)/2, pixelCounter, TEXT_FIELDS_WIDTH, TEXT_FIELDS_HEIGHT);

        password_label.setFont(new Font("Sans Sheriff", Font.ITALIC, 14));
        password_label.setBounds(0, pixelCounter, (PANEL_WIDTH-TEXT_FIELDS_WIDTH)/2-5, TEXT_FIELDS_HEIGHT);
        password_label.setHorizontalAlignment(SwingConstants.RIGHT);

        pixelCounter += TEXT_FIELDS_HEIGHT + MARGIN_LOGIN_BTN_FROM_FIELDS;

        errorOccurred.setBounds(0, pixelCounter-MARGIN_BETWEEN_LOGIN_BTN_AND_ERROR_LABEL, PANEL_WIDTH, ERROR_OCCURRED_HEIGHT);
        errorOccurred.setHorizontalAlignment(SwingConstants.CENTER);
        errorOccurred.setForeground(Color.red);

        login.setFocusable(false);
        login.setBounds((PANEL_WIDTH-LOGIN_BTN_WIDTH)/2, pixelCounter, LOGIN_BTN_WIDTH, LOGIN_BTN_HEIGHT);
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Tahoma", Font.BOLD, 16));
        login.setBackground(Color.decode("#3B5998"));

        pixelCounter += LOGIN_LABEL_HEIGHT + MARGIN_REGISTER_LABEL_FROM_LOGIN_BTN;

        registerLabel.setBounds(0,pixelCounter,PANEL_WIDTH,REGISTER_LABEL_HEIGHT);
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setFont(new Font("Tahoma", Font.ITALIC, REGISTER_LABEL_HEIGHT));
        registerLabel.setForeground(Color.BLACK);

        pixelCounter += REGISTER_BTN_HEIGHT + MARGIN_REGISTER_BTN_FROM_REGISTER_LABEL;

        register.setFocusable(false);
        register.setOpaque(false);
        register.setBackground(Color.WHITE);
        register.setBounds((PANEL_WIDTH-REGISTER_BTN_WIDTH)/2,pixelCounter, REGISTER_BTN_WIDTH, REGISTER_BTN_HEIGHT);
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

    // Method that implements login button's functionality
    private void loginBtnClicked(AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        String usernameTxt;
        String passwordTxt;
        usernameTxt = username.getText();
        passwordTxt = String.valueOf(password.getPassword());
        User user = listOfAccounts.logInAccount(usernameTxt,passwordTxt);
        if(user == null)
            errorOccurred.setText(LOGIN_ERROR_MESSAGE);
        else{
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

