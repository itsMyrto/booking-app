import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ProviderAccommodationList extends JPanel {
    ProviderAccommodationList(Provider provider,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations){
        ArrayList<Accommodation> accommodationList;
        accommodationList = listOfAccommodations.providerAccommodationList(provider);
        JButton returnButton = new JButton("Return");
        setSize(1500,800);
        setOpaque(false);
        setLayout(null);
        int size = accommodationList.size();
        String [] tableHeaders = {"Name","Type","Country","City","Rooms","Wifi","Parking","Pool","Restaurant","Pets Allowed"};
        String[][] data = new String[size][10];
        int count = 0;
        for(Accommodation x: accommodationList){
            data[count][0] = x.getName();
            data[count][1] = x.getType();
            data[count][2] = x.getLocation().getCountry();
            data[count][3] = x.getLocation().getTown();
            data[count][4] = String.valueOf(x.getNumberOfRooms());
            data[count][5] = String.valueOf(x.getHasWifi());
            data[count][6] = String.valueOf(x.getHasParking());
            data[count][7] = String.valueOf(x.getHasPool());
            data[count][8] = String.valueOf(x.getHasRestaurant());
            data[count][9] = String.valueOf(x.getPetsAllowed());
            count++;
        }

        JTable table = new JTable(data,tableHeaders);
        table.setEnabled(false);
        table.setOpaque(false);
        table.setBackground(Color.CYAN);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);
        scrollPane.setBounds(200,100,1000,200);
        returnButton.setBounds(500,10,80,30);
        returnButton.addActionListener(e -> {
            removeAll();
            repaint();
            add(new ProviderWindow(provider,listOfAccommodations,listOfAccounts,listOfReservations));
        });
        add(scrollPane);
        add(returnButton);
        setVisible(true);
    }

}
