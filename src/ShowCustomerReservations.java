import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class ShowCustomerReservations extends JPanel {
    public ShowCustomerReservations(Customer customer,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        setSize(1500,800);
        setLayout(null);
        String [] tableHeaders = {"Name","Type","Country","City","Address","Room Number","Price (one night)","Capacity","View","Total Price","Dates"};
        DefaultTableModel model = new DefaultTableModel(createTable(customer,listOfReservations),tableHeaders);
        JTable table = new JTable(model);
        table.setOpaque(false);
        table.setFocusable(false);
        setOpaque(false);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50,50,1200,500);
        scrollPane.setVisible(true);
        add(scrollPane);
    }

    public String[][] createTable(Customer customer,ReservationsCreated listOfReservations){
        ArrayList<Reservation> customerReservations = listOfReservations.reservationsOfASpecificCustomer(customer);
        System.out.println(customerReservations.size());
        String[][] data = new String[customerReservations.size()][11];
        int count = 0;
        for(Reservation x: customerReservations){
            data[count][0] = x.getAccommodation().getName();
            data[count][1] = x.getAccommodation().getType();
            data[count][2] = x.getAccommodation().getLocation().getCountry();
            data[count][3] = x.getAccommodation().getLocation().getTown();
            data[count][4] = x.getAccommodation().getLocation().getStreet().toUpperCase(Locale.ROOT);
            data[count][5] = String.valueOf(x.getRoomNumber());
            data[count][6] = String.valueOf(x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getPricePerNight());
            data[count][7] = String.valueOf(x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getCapacity());
            data[count][8] = x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getView();
            data[count][9] = String.valueOf(x.getDate().nightsBetweenDates()*x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getPricePerNight());
            data[count][10] = x.getDate().getDatesAsString();
            count++;
        }

        return data;
    }


}
