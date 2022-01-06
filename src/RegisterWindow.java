import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;
import java.util.Locale;

public class RegisterWindow extends JPanel{
    private MainFrame mainFrame;

    private JLabel fullnameLabel = new JLabel("Full name: ");
    private JLabel emailLabel = new JLabel("Email: ");
    private JLabel countryLabel = new JLabel("Select your country: ");
    private JLabel usernameLabel = new JLabel("Choose a username: ");
    private JLabel passwordLabel = new JLabel("Select a password: ");

    private JTextField fullName = new JTextField("e.g. Myrto Gkogkou");
    private JTextField email = new JTextField("example@gmail.com");
    private JTextField country = new JTextField("e.g. Greece");
    private JTextField username = new JTextField("Username");
    private JPasswordField password = new JPasswordField("Password");

    private JLabel invalidInfo = new JLabel();
    private JLabel note = new JLabel("Note: Your account will be activated once it's approved by an admin.");

    private ButtonGroup typeUser = new ButtonGroup();

    private JButton createAcc = new JButton("Create account");
    private JButton cancelBtn = new JButton("Cancel");

    private JRadioButton customerAcc = new JRadioButton("Customer");
    private JRadioButton providerAcc = new JRadioButton("Provider");

    private JLabel title = new JLabel("Registration Form");
    private JLabel radioBtnLabel = new JLabel("I am a: ");

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

    private final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth()+1) - MainFrame.FRAME_IMAGE_RESOLUTION[0];
    private final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight()+1);

    private final int TITLE_HEIGHT = 42;

    private final int RADIO_BTN_LABEL_WIDTH = 80;
    private final int RADIO_BTN_LABEL_HEIGHT = 20;

    private final int RADIO_BTN_WIDTH = 120;
    private final int RADIO_BTN_HEIGHT = 20;

    private final int FIELD_LABELS_WIDTH = 100;
    private final int FIELD_LABELS_HEIGHT = 30;

    private final int FIELDS_WIDTH = 200;
    private final int FIELDS_HEIGHT = 30;

    private final int CREATE_BTN_WIDTH = 200;
    private final int CREATE_BTN_HEIGHT = 30;

    private final String INVALID_INFO_MESSAGE = "Invalid inputs. Please, use valid information.";
    private final int INVALID_INFO_MESSAGE_HEIGHT = 20;

    private final int NOTE_HEIGHT = 20;

    private final int MARGIN_TITLE_FROM_TOP = 50;
    private final int MARGIN_RADIO_BTN_FROM_TITLE = 50;
    private final int MARGIN_BETWEEN_RADIO_BTN = 20;
    private final int MARGIN_FIELDS_FROM_RADIO_BTN = 50;
    private final int MARGIN_BETWEEN_FIELDS_AND_THEIR_LABELS = 20;
    private final int MARGIN_BETWEEN_FIELDS = 20;
    private final int MARGIN_CREATE_BTN_FROM_FIELDS = 100;
    private final int MARGIN_INVALID_MESSAGE_FROM_CREATE_BTN = 20;
    private final int MARGIN_BETWEEN_BTN = 10;

    public RegisterWindow(AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations, MainFrame mainFrame){
        this.mainFrame = mainFrame;

        Rectangle bounds = env.getMaximumWindowBounds();
        int pixelCounter = MARGIN_TITLE_FROM_TOP;

        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);

        title.setBounds(0, pixelCounter, PANEL_WIDTH, TITLE_HEIGHT);
        title.setForeground(Color.decode("#3B5998"));
        title.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, TITLE_HEIGHT-4));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setOpaque(false);

        pixelCounter += TITLE_HEIGHT + MARGIN_RADIO_BTN_FROM_TITLE;

        int radioBtnRowPx = RADIO_BTN_LABEL_WIDTH+2*RADIO_BTN_WIDTH+2*MARGIN_BETWEEN_RADIO_BTN;
        radioBtnLabel.setBounds((PANEL_WIDTH-radioBtnRowPx)/2, pixelCounter, RADIO_BTN_LABEL_WIDTH, RADIO_BTN_LABEL_HEIGHT);
        radioBtnLabel.setForeground(Color.decode("#000000"));
        radioBtnLabel.setFont(new Font("Tahoma", Font.ITALIC, RADIO_BTN_LABEL_HEIGHT));
        radioBtnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        radioBtnLabel.setOpaque(false);

        customerAcc.setOpaque(false);
        customerAcc.setBounds((PANEL_WIDTH-radioBtnRowPx)/2+RADIO_BTN_LABEL_WIDTH+MARGIN_BETWEEN_RADIO_BTN,pixelCounter,RADIO_BTN_WIDTH, RADIO_BTN_HEIGHT);
        customerAcc.setSelected(true);
        customerAcc.setFocusable(false);
        customerAcc.setFont(new Font("Tahoma", Font.PLAIN, RADIO_BTN_HEIGHT));

        providerAcc.setOpaque(false);
        providerAcc.setBounds((PANEL_WIDTH-radioBtnRowPx)/2+RADIO_BTN_LABEL_WIDTH+RADIO_BTN_WIDTH+2*MARGIN_BETWEEN_RADIO_BTN,pixelCounter,RADIO_BTN_WIDTH,RADIO_BTN_HEIGHT);
        providerAcc.setSelected(true);
        providerAcc.setFocusable(false);
        providerAcc.setFont(new Font("Tahoma", Font.PLAIN, RADIO_BTN_HEIGHT));

        pixelCounter += RADIO_BTN_HEIGHT + MARGIN_FIELDS_FROM_RADIO_BTN;

        typeUser.add(customerAcc);
        typeUser.add(providerAcc);

        fullName.setBounds((PANEL_WIDTH-FIELDS_WIDTH)/2,pixelCounter,FIELDS_WIDTH,FIELDS_HEIGHT);
        fullnameLabel.setBounds(0, pixelCounter, (PANEL_WIDTH-FIELDS_WIDTH)/2-MARGIN_BETWEEN_FIELDS_AND_THEIR_LABELS, FIELD_LABELS_HEIGHT);
        fullnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fullName.setOpaque(false);
        fullnameLabel.setOpaque(false);

        pixelCounter += FIELD_LABELS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        email.setBounds((PANEL_WIDTH-FIELDS_WIDTH)/2,pixelCounter,FIELDS_WIDTH,FIELDS_HEIGHT);
        emailLabel.setBounds(0, pixelCounter, (PANEL_WIDTH-FIELDS_WIDTH)/2-MARGIN_BETWEEN_FIELDS_AND_THEIR_LABELS, FIELD_LABELS_HEIGHT);
        emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        email.setOpaque(false);
        emailLabel.setOpaque(false);

        pixelCounter += FIELD_LABELS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        country.setBounds((PANEL_WIDTH-FIELDS_WIDTH)/2,pixelCounter,FIELDS_WIDTH,FIELDS_HEIGHT);
        countryLabel.setBounds(0, pixelCounter, (PANEL_WIDTH-FIELDS_WIDTH)/2-MARGIN_BETWEEN_FIELDS_AND_THEIR_LABELS, FIELD_LABELS_HEIGHT);
        countryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        country.setOpaque(false);
        countryLabel.setOpaque(false);

        pixelCounter += FIELD_LABELS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        username.setBounds((PANEL_WIDTH-FIELDS_WIDTH)/2,pixelCounter,FIELDS_WIDTH,FIELDS_HEIGHT);
        usernameLabel.setBounds(0, pixelCounter, (PANEL_WIDTH-FIELDS_WIDTH)/2-MARGIN_BETWEEN_FIELDS_AND_THEIR_LABELS, FIELD_LABELS_HEIGHT);
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        username.setOpaque(false);
        usernameLabel.setOpaque(false);

        pixelCounter += FIELD_LABELS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        password.setBounds((PANEL_WIDTH-FIELDS_WIDTH)/2,pixelCounter,FIELDS_WIDTH,FIELDS_HEIGHT);
        passwordLabel.setBounds(0, pixelCounter, (PANEL_WIDTH-FIELDS_WIDTH)/2-MARGIN_BETWEEN_FIELDS_AND_THEIR_LABELS, FIELD_LABELS_HEIGHT);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        password.setOpaque(false);
        passwordLabel.setOpaque(false);

        pixelCounter += FIELD_LABELS_HEIGHT + MARGIN_CREATE_BTN_FROM_FIELDS;

        createAcc.setFocusable(false);
        createAcc.setBounds((PANEL_WIDTH-CREATE_BTN_WIDTH)/2, pixelCounter, CREATE_BTN_WIDTH, CREATE_BTN_HEIGHT);
        createAcc.setForeground(Color.WHITE);
        createAcc.setFont(new Font("Tahoma", Font.BOLD, 16));
        createAcc.setBackground(Color.decode("#3B5998"));

        pixelCounter += CREATE_BTN_HEIGHT;

        invalidInfo.setText(INVALID_INFO_MESSAGE);
        invalidInfo.setForeground(Color.red);
        invalidInfo.setOpaque(false);
        invalidInfo.setBounds(0, pixelCounter-CREATE_BTN_HEIGHT-INVALID_INFO_MESSAGE_HEIGHT-MARGIN_INVALID_MESSAGE_FROM_CREATE_BTN, PANEL_WIDTH, INVALID_INFO_MESSAGE_HEIGHT);
        invalidInfo.setHorizontalAlignment(SwingConstants.CENTER);
        invalidInfo.setVisible(false);

        pixelCounter += MARGIN_BETWEEN_BTN;

        cancelBtn.setFocusable(false);
        cancelBtn.setBounds((PANEL_WIDTH-CREATE_BTN_WIDTH)/2, pixelCounter, CREATE_BTN_WIDTH, CREATE_BTN_HEIGHT);
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
        cancelBtn.setForeground(Color.decode("#3B5998"));

        pixelCounter += CREATE_BTN_HEIGHT;

        note.setBounds(0, PANEL_HEIGHT-NOTE_HEIGHT-70, PANEL_WIDTH,NOTE_HEIGHT);
        note.setHorizontalAlignment(SwingConstants.CENTER);

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

        fullName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(fullName.getText().equals("e.g. Myrto Gkogkou")) fullName.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(fullName.getText().equals("")) fullName.setText("e.g. Myrto Gkogkou");
            }
        });

        country.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(country.getText().equals("e.g. Greece")) country.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(country.getText().equals("")) country.setText("e.g. Greece");
            }
        });

        email.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(email.getText().equals("example@gmail.com")) email.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(email.getText().equals("")) email.setText("example@gmail.com");
            }
        });

        createAcc.addActionListener(e -> {
            boolean isCreated = false;
            String usernameTxt = username.getText();
            String passwordTxt = String.valueOf(password.getPassword());
            String countryTxt = country.getText().toUpperCase(Locale.ROOT);
            String emailTxt = email.getText();
            String fullNameTxt = fullName.getText();
            if(usernameTxt.isEmpty() || passwordTxt.isEmpty() || fullNameTxt.isEmpty() || countryTxt.isEmpty() || emailTxt.isEmpty())
                invalidInfo.setVisible(true);
            else{
                if(customerAcc.isSelected()){
                    Customer customer = new Customer(usernameTxt, passwordTxt, emailTxt, countryTxt, fullNameTxt);
                    if(listOfAccounts.usernameIsTaken(customer)) invalidInfo.setVisible(true);
                    else if(listOfAccounts.emailIsTaken(customer)) invalidInfo.setVisible(true);
                    else{
                        listOfAccounts.createAccountForCustomers(customer);
                        isCreated = true;
                    }

                }
                else if(providerAcc.isSelected()){
                    Provider provider= new Provider(usernameTxt, passwordTxt, emailTxt, countryTxt, fullNameTxt);
                    if(listOfAccounts.usernameIsTaken(provider)){
                        invalidInfo.setText("Sorry,this username is already taken!");
                        invalidInfo.setVisible(true);
                    }
                    else if(listOfAccounts.emailIsTaken(provider)){
                        invalidInfo.setText("Sorry,this email is already taken!");
                        invalidInfo.setVisible(true);
                    }
                    else{
                        listOfAccounts.createAccountForProviders(provider);
                        isCreated = true;
                    }
                }

                if(isCreated){
                    mainFrame.remove(this);
                    mainFrame.getContentPane().repaint();
                    mainFrame.getContentPane().add(new LogInWindow(listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
                    mainFrame.getContentPane().repaint();
                }
            }
        });

        cancelBtn.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new LogInWindow(listOfAccounts,listOfAccommodations,listOfReservations, mainFrame));
            mainFrame.getContentPane().repaint();
        });

        this.add(title);
        this.add(username);
        this.add(usernameLabel);
        this.add(password);
        this.add(passwordLabel);
        this.add(fullName);
        this.add(fullnameLabel);
        this.add(email);
        this.add(emailLabel);
        this.add(country);
        this.add(countryLabel);

        this.add(radioBtnLabel);
        this.add(customerAcc);
        this.add(providerAcc);

        this.add(invalidInfo);
        this.add(createAcc);
        this.add(cancelBtn);

        this.add(note);

        this.setOpaque(false);
        this.setLayout(null);
        this.setVisible(true);
    }

}
