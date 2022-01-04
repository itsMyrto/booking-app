import javax.swing.*;
import java.awt.*;


public class LogInWindow extends JPanel{
    private JButton login = new JButton("Log In");
    private JTextField username = new JTextField("");
    private JLabel label = new JLabel("Log in to your account");
    private JLabel label2 = new JLabel("New to our app?");
    private JButton register = new JButton("Register");
    private JPasswordField password = new JPasswordField("");
    private JLabel errorWhileLogIn = new JLabel();

    public LogInWindow(AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        setSize(new Dimension(1500, 800));
        setOpaque(false);
        login.setFocusable(false);
        register.setFocusable(false);
        login.setBounds(200,250,80,30);
        password.setBounds(200,200,80,30);
        username.setBounds(200,150,80,30);
        label.setBounds(180,50,200,100);
        label2.setBounds(150,280,300,50);
        register.setBounds(250,290,80,30);
        errorWhileLogIn.setForeground(Color.red);
        login.addActionListener(e->{
            String usernameTxt;
            String passwordTxt;
            usernameTxt = username.getText();
            passwordTxt = String.valueOf(password.getPassword());
            User user = listOfAccounts.logInAccount(usernameTxt,passwordTxt);
            if(user == null){
                errorWhileLogIn.setText("Wrong username or password.Please try again!");
                errorWhileLogIn.setBounds(100,80,300,100);
            }
            else{
                if(listOfAccounts.accountIsUnapproved(user)){
                    errorWhileLogIn.setText("Sorry,your account haven't been approved yet.");
                    errorWhileLogIn.setBounds(100,80,300,100);
                }
                else{
                    if(user instanceof Customer){
                        removeAll();
                        repaint();
                        add(new CustomerWindow((Customer) user, listOfAccounts,listOfAccommodations,listOfReservations));
                    }
                    else if(user instanceof Provider){
                        removeAll();
                        repaint();
                        add(new ProviderWindow(((Provider) user),listOfAccommodations,listOfAccounts,listOfReservations));
                    }
                    else {

                    }
                }
            }
        });

        register.addActionListener(e -> {
            removeAll();
            repaint();
            add(new RegisterWindow(listOfAccounts,listOfAccommodations,listOfReservations));
        });

        add(errorWhileLogIn);
        add(username);
        add(password);
        add(label);
        add(label2);
        add(login);
        add(register);
        setLayout(null);
        setVisible(true);
    }

}
