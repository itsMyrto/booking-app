import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class RegisterWindow extends JPanel{
    private JTextField username = new JTextField("username");
    private JPasswordField password = new JPasswordField("");
    private JTextField email = new JTextField("email");
    private JTextField fullName = new JTextField("full name");
    private JTextField country = new JTextField("country");
    private JLabel label = new JLabel("Complete the registration form");
    private JLabel validInfo = new JLabel();
    private JButton createAcc = new JButton("Create account");
    private JLabel note = new JLabel();
    private ButtonGroup typeUser = new ButtonGroup();
    private JRadioButton customerAcc = new JRadioButton("Customer");
    private JRadioButton providerAcc = new JRadioButton("Provider");

    public RegisterWindow(AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        setSize(1500, 800);
        typeUser.add(customerAcc);
        typeUser.add(providerAcc);
        setOpaque(false);
        customerAcc.setOpaque(false);
        providerAcc.setOpaque(false);
        customerAcc.setSelected(true);
        customerAcc.setBounds(50,10,80,30);
        providerAcc.setBounds(200,10,80,30);
        username.setBounds(150,100,80,30);
        password.setBounds(150,150,80,30);
        fullName.setBounds(150,200,80,30);
        country.setBounds(150,250,80,30);
        email.setBounds(150,300,80,30);
        label.setBounds(150,10,200,100);
        validInfo.setForeground(Color.red);
        createAcc.setBounds(150,350,200,30);
        createAcc.setFocusable(false);
        createAcc.setFont(new Font("Arial", Font.BOLD, 10));
        note.setText("Note:Your account will be activated when it gets approved by an admin");
        note.setBounds(50,350,400,100);

        createAcc.addActionListener(e -> {
            boolean isCreated = false;
            String usernameTxt = username.getText();
            String passwordTxt = String.valueOf(password.getPassword());
            String countryTxt = country.getText().toUpperCase(Locale.ROOT);
            String emailTxt = email.getText();
            String fullNameTxt = fullName.getText();
            if(usernameTxt.isEmpty() || passwordTxt.isEmpty() || fullNameTxt.isEmpty() || countryTxt.isEmpty() || emailTxt.isEmpty()){
                validInfo.setText("Invalid Information");
                validInfo.setBounds(200,40,200,100);
            }
            else{
                if(customerAcc.isSelected()){
                    Customer customer = new Customer(usernameTxt, passwordTxt, emailTxt, countryTxt, fullNameTxt);
                    if(listOfAccounts.usernameIsTaken(customer)){
                        validInfo.setText("Sorry,this username is already taken!");
                        validInfo.setBounds(150,40,300,100);
                    }
                    else if(listOfAccounts.emailIsTaken(customer)){
                        validInfo.setText("Sorry,this email is already taken!");
                        validInfo.setBounds(150,40,300,100);
                    }
                    else{
                        listOfAccounts.createAccountForCustomers(customer);
                        isCreated = true;
                    }

                }
                else if(providerAcc.isSelected()){
                    Provider provider= new Provider(usernameTxt, passwordTxt, emailTxt, countryTxt, fullNameTxt);
                    if(listOfAccounts.usernameIsTaken(provider)){
                        validInfo.setText("Sorry,this username is already taken!");
                        validInfo.setBounds(150,40,300,100);
                    }
                    else if(listOfAccounts.emailIsTaken(provider)){
                        validInfo.setText("Sorry,this email is already taken!");
                        validInfo.setBounds(150,40,300,100);
                    }
                    else{
                        listOfAccounts.createAccountForProviders(provider);
                        isCreated = true;
                    }
                }

                if(isCreated){
                    removeAll();
                    repaint();
                    add(new LogInWindow(listOfAccounts,listOfAccommodations,listOfReservations));
                }
            }
        });

        add(customerAcc);
        add(providerAcc);
        add(validInfo);
        add(createAcc);
        add(username);
        add(password);
        add(fullName);
        add(email);
        add(country);
        add(label);
        add(note);
        setLayout(null);
        setVisible(true);
    }

}
