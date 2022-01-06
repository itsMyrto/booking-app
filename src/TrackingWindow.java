import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Locale;

public class TrackingWindow extends JPanel {
    public TrackingWindow(Admin admin,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        setLayout(null);
        setSize(1500,800);
        String [] tableHeaders2 = {"Customer Email","Name","Country","City","Room Number","Total Price","Dates"};
        String [] tableHeaders = {"Provider Email","Name","Type","Country","City","Rooms","Reservations"};
        String[][] data = makeTable(listOfAccommodations,listOfReservations);
        JTextField userEmail = new JTextField("User Email");
        JLabel error = new JLabel();
        userEmail.setBounds(400,50,200,30);
        DefaultTableModel model = new DefaultTableModel(data,tableHeaders){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        JComboBox<String> filters;
        String[] filterOption = {"Show All Accommodations","Show All Reservations","Show All Cancelled Reservations","Search a specific user"};
        filters = new JComboBox<>(filterOption){
            @Override
            public void setBounds(int x, int y, int width, int height) {
                super.setBounds(x, y, width, height);
                Component[] comps = getComponents();
                if (comps != null && comps.length >= 1) {
                    Component arrow = comps[0];
                    arrow.setSize(20, height);
                    arrow.setLocation(width - arrow.getWidth(), 0);
                }
            }
        };

        filters.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                error.setVisible(false);
                int optionFromComboBox = filters.getSelectedIndex();
                if (optionFromComboBox == 1 || optionFromComboBox==2) {
                    userEmail.setVisible(false);
                    for(int i=0;i<7;i++){
                        table.getColumnModel().getColumn(i).setHeaderValue(tableHeaders2[i]);
                    }
                    updateTable(optionFromComboBox, model, listOfAccounts, listOfAccommodations, listOfReservations);
                }
                else if(optionFromComboBox==0){
                    userEmail.setVisible(false);
                    for(int i=0;i<7;i++){
                        table.getColumnModel().getColumn(i).setHeaderValue(tableHeaders[i]);
                        String[][] accommodationData = makeAccommodationList(listOfAccommodations,listOfReservations,model);
                        System.out.println(accommodationData.length+" checking");
                        for (String[] strings : accommodationData) {
                            model.addRow(new Object[] {strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6]});
                        }
                    }
                }
                else if(optionFromComboBox==3){
                    userEmail.setVisible(true);
                    userEmail.addActionListener(e1 -> {
                        String email = userEmail.getText();
                        User user = listOfAccounts.getSpecificAccount(email);
                        if(user == null){
                            error.setForeground(Color.red);
                            error.setText("There is no user with that email");
                            error.setBounds(500,5,200,30);
                            error.setVisible(true);
                            updateTableForASpecificUser(null,optionFromComboBox,model,listOfAccounts,listOfAccommodations,listOfReservations);
                            return;
                        }
                        else if(user instanceof Admin){
                            error.setForeground(Color.red);
                            error.setText("You cannot track another Admin");
                            error.setBounds(500,5,200,30);
                            error.setVisible(true);
                            return;
                        }
                        error.setVisible(false);
                        for(int i=0;i<7;i++){
                            System.out.println("Here is the problem");
                            table.getColumnModel().getColumn(i).setHeaderValue(tableHeaders[i]);
                            String[][] accommodationData = makeAccommodationList(listOfAccommodations,listOfReservations,model);
                            for (String[] strings : accommodationData) {
                                model.addRow(new Object[] {strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6]});
                            }
                        }
                        updateTableForASpecificUser(user,optionFromComboBox,model,listOfAccounts,listOfAccommodations,listOfReservations);
                    });
                }
            }
        });
        filters.setBounds(100,10,250,30);
        table.setBackground(Color.CYAN);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);
        scrollPane.setBounds(50,100,1400,200);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userEmail.setVisible(false);
        table.setOpaque(false);
        add(userEmail);
        add(scrollPane);
        add(error);
        add(filters);
        setVisible(true);

    }

    public String[][] makeTable(AccommodationsCreated listOfAccommodations,ReservationsCreated reservations){
        ArrayList<Accommodation> accommodationList;
        accommodationList = listOfAccommodations.getTheAccommodationList();
        int size = accommodationList.size();
        String[][] data = new String[size][7];
        int count = 0;
        for(Accommodation x: accommodationList){
            data[count][0] = x.getProvider().getEmail();
            data[count][1] = x.getName();
            data[count][2] = x.getType();
            data[count][3] = x.getLocation().getCountry();
            data[count][4] = x.getLocation().getTown();
            data[count][5] = String.valueOf(x.getNumberOfRooms());
            data[count][6] = String.valueOf(reservations.getTheNumberOfReservationsOfAnAccommodation(x));
            count++;
        }
        return data;
    }

    public void updateTable(int optionFromComboBox,DefaultTableModel model,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        model.setRowCount(0);
        ArrayList<Reservation> reservations;
        if(optionFromComboBox==1){
            System.out.println("In the 1");
            reservations = listOfReservations.getAllActiveReservations();
        }
        else if(optionFromComboBox==2){
            System.out.println("In the 2");
            reservations = listOfReservations.getAllCancelledReservations();
        }
        else{
            return;
        }

        int size = reservations.size();
        String[][] data = new String[size][7];
        int count = 0;
        for(Reservation x: reservations){
            data[count][0] = x.getCustomer().getEmail();
            data[count][1] = x.getAccommodation().getName();
            data[count][2] = x.getAccommodation().getLocation().getCountry();
            data[count][3] = x.getAccommodation().getLocation().getTown();
            data[count][4] = String.valueOf(x.getRoomNumber()+1);
            data[count][5] = String.valueOf(x.getDate().nightsBetweenDates()*x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getPricePerNight());
            data[count][6] = x.getDate().getDatesAsString();
            count++;
        }

        for (String[] strings : data) {
            model.addRow(new Object[] {strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6]});
        }
    }

    public void updateTableForASpecificUser(User user,int optionFromComboBox,DefaultTableModel model,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        model.setRowCount(0);
        if(user instanceof Customer){
            ArrayList<Reservation> reservations = listOfReservations.reservationsOfASpecificCustomer((Customer) user);
            int size = reservations.size();
            String[][] data = new String[size][7];
            int count = 0;
            for(Reservation x: reservations){
                data[count][0] = x.getCustomer().getEmail();
                data[count][1] = x.getAccommodation().getName();
                data[count][2] = x.getAccommodation().getLocation().getCountry();
                data[count][3] = x.getAccommodation().getLocation().getTown();
                data[count][4] = String.valueOf(x.getRoomNumber()+1);
                data[count][5] = String.valueOf(x.getDate().nightsBetweenDates()*x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getPricePerNight());
                data[count][6] = x.getDate().getDatesAsString();
                count++;
            }
            for (String[] strings : data) {
                model.addRow(new Object[] {strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6]});
            }
        } else if (user instanceof Provider) {
            ArrayList<Accommodation> accommodations = listOfAccommodations.getAccommodationsOfAProvider(user.getEmail());
            int size = accommodations.size();
            String[][] data = new String[size][7];
            int count = 0;
            for(Accommodation x: accommodations){
                data[count][0] = x.getProvider().getEmail();
                data[count][1] = x.getName();
                data[count][2] = x.getType();
                data[count][3] = x.getLocation().getCountry();
                data[count][4] = x.getLocation().getTown();
                data[count][5] = String.valueOf(x.getNumberOfRooms());
                data[count][6] = String.valueOf(listOfReservations.getTheNumberOfReservationsOfAnAccommodation(x));
                count++;
            }
            for (String[] strings : data) {
                model.addRow(new Object[] {strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6]});
            }
        }
    }

    public String[][] makeAccommodationList(AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,DefaultTableModel model){
        model.setRowCount(0);
        ArrayList<Accommodation> accommodationList;
        accommodationList = listOfAccommodations.getTheAccommodationList();
        int size = accommodationList.size();
        System.out.println(size);
        String[][] data = new String[size][7];
        int count = 0;
        for(Accommodation x: accommodationList){
            data[count][0] = x.getProvider().getEmail();
            data[count][1] = x.getName();
            data[count][2] = x.getType();
            data[count][3] = x.getLocation().getCountry();
            data[count][4] = x.getLocation().getTown();
            data[count][5] = String.valueOf(x.getNumberOfRooms());
            data[count][6] = String.valueOf(listOfReservations.getTheNumberOfReservationsOfAnAccommodation(x));
            count++;
        }
        return data;
    }
}
