import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        LogInWindow login = new LogInWindow(listOfAccounts,listOfAccommodations,listOfReservations);
        setTitle("Hermes-Hotel Reservation App");
        getContentPane().add(login);
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        this.getContentPane().setBackground(Color.decode("#c2e9fb"));
        setLayout(null);
        setVisible(true);
    }
}
