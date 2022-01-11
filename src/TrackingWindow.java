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
    public TrackingWindow(Admin admin,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){
        setLayout(null);
        setSize(1500,800);
        setBackground(Color.WHITE);
        JLabel label2 = new JLabel("Enter an email:");
        label2.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        label2.setForeground(new Color(0x06307C));
        label2.setBounds(20,145,400,30);
        label2.setVisible(false);
        add(label2);

        JLabel label = new JLabel("Track Customers & Providers");
        label.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,28));
        label.setForeground(new Color(0x06307C));
        label.setBounds(500,50,400,30);
        add(label);

        String [] tableHeaders2 = {"Customer Email","Name","Country","City","Room Number","Total Price","Dates"};
        String [] tableHeaders = {"Provider Email","Name","Type","Country","City","Rooms","Reservations"};
        String[][] data = makeTable(listOfAccommodations,listOfReservations);
        JTextField userEmail = new JTextField("User Email");
        JLabel error = new JLabel();
        error.setBounds(400,150,200,30);
        userEmail.setBounds(170,150,220,25);
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
                    arrow.setBackground(new Color(131, 167, 245));
                }
            }
        };

        filters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                error.setVisible(false);
                int optionFromComboBox = filters.getSelectedIndex();
                if (optionFromComboBox == 1 || optionFromComboBox==2) {
                    label2.setVisible(false);
                    userEmail.setVisible(false);
                    for(int i=0;i<7;i++){
                        table.getColumnModel().getColumn(i).setHeaderValue(tableHeaders2[i]);
                    }
                    updateTable(optionFromComboBox, model, listOfAccounts, listOfAccommodations, listOfReservations);
                }
                else if(optionFromComboBox==0){
                    label2.setVisible(false);
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
                    label2.setVisible(true);
                    userEmail.setVisible(true);
                    userEmail.addActionListener(e1 -> {
                        String email = userEmail.getText();
                        User user = listOfAccounts.getSpecificAccount(email);
                        if(user == null){
                            error.setForeground(Color.red);
                            error.setText("There is no user with that email");
                            error.setVisible(true);
                            updateTableForASpecificUser(null,optionFromComboBox,model,listOfAccounts,listOfAccommodations,listOfReservations);
                            return;
                        }
                        else if(user.getEmail().equals(admin.getEmail())){
                            error.setVisible(true);
                            error.setText("This is you");
                        }
                        else if(user instanceof Admin){
                            error.setForeground(Color.red);
                            error.setText("You cannot track another Admin");
                            error.setVisible(true);
                            return;
                        }
                        error.setVisible(false);
                        for(int i=0;i<7;i++){
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
        filters.setBounds(170,100,220,20);
        filters.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 12));
        filters.setForeground(new Color(0x06307C));
        table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC+Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(182, 219, 252));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(170,200,1200,500);
        scrollPane.getViewport().setBackground(Color.WHITE);
        //table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
            System.out.println(x.getCustomer().getFullName()+" "+x.getAccommodation().getName());
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
