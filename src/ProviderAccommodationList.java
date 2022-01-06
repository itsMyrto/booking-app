import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class ProviderAccommodationList extends JPanel {
    ProviderAccommodationList(Provider provider,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations){
        JButton returnButton = new JButton("Return");
        setSize(1500,800);
        setOpaque(false);
        setLayout(null);
        String [] tableHeaders = {"Name","Type","Country","City","Address","Rooms","Wifi","Parking","Pool","Restaurant","Pets Allowed"};
        String [] tablesHeaders2 = {"Name","Type","Country","City","Address","Room Number","Total Price","Customer Email","Customer Name","Customer Origin","Dates"};
        String[][] data = makeTable(listOfAccommodations,provider);
        DefaultTableModel model = new DefaultTableModel(data,tableHeaders){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setOpaque(false);
        JComboBox<String> filters;
        String[] filterOption = {"Show All Accommodations","Show Reserved Accommodations","Show Cancelled Reservations"};
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
                int optionFromComboBox = filters.getSelectedIndex();
                if (optionFromComboBox != 0) {
                    for(int i=0;i<11;i++){
                        table.getColumnModel().getColumn(i).setHeaderValue(tablesHeaders2[i]);
                    }
                    updateTable(optionFromComboBox, model, provider, listOfReservations, listOfAccommodations);
                }
                else{
                    for(int i=0;i<11;i++){
                        table.getColumnModel().getColumn(i).setHeaderValue(tableHeaders[i]);
                        String[][] accommodationData = makeAccommodationList(listOfAccommodations,provider,model);
                        for (String[] strings : accommodationData) {
                            model.addRow(new Object[] {strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8],strings[9],strings[10]});
                        }
                    }
                }
            }
        });
        filters.setBounds(100,10,250,30);
        table.setBackground(Color.CYAN);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);
        scrollPane.setBounds(50,100,1400,200);
        returnButton.setBounds(500,10,80,30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int rowIndex = table.getSelectedRow();
                if(rowIndex != -1){
                    JFrame frame = new JFrame("Modify Accommodations");
                    JLabel error = new JLabel("Invalid information");
                    error.setForeground(Color.red);
                    frame.setSize(1500,800);
                    frame.setLocationRelativeTo(null);
                    frame.setLayout(null);
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    String nameOfTheAcc = table.getValueAt(rowIndex,0).toString();
                    String address = table.getValueAt(rowIndex,4).toString();
                    Accommodation selectedAccommodation = listOfAccommodations.getSpecificAccommodation(nameOfTheAcc,address);
                    JTextField name = new JTextField(selectedAccommodation.getName());
                    JTextField type = new JTextField(selectedAccommodation.getType());
                    JTextField capacity = new JTextField(String.valueOf(selectedAccommodation.getSpecificRoom(0).getCapacity()));
                    JTextField price = new JTextField(String.valueOf(selectedAccommodation.getSpecificRoom(0).getPricePerNight()));
                    JTextField beds = new JTextField(String.valueOf(selectedAccommodation.getSpecificRoom(0).getTotalBeds()));
                    JButton changes = new JButton("Confirm Changes");
                    JComboBox<String> rooms;
                    String[] totalRooms = new String[selectedAccommodation.getNumberOfRooms()];
                    for(int i=0;i<selectedAccommodation.getNumberOfRooms();i++){
                        totalRooms[i] = String.valueOf(i+1);
                    }
                    rooms = new JComboBox<>(totalRooms){
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
                    rooms.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Here");
                            int optionFromComboBox = rooms.getSelectedIndex();
                            name.setText(selectedAccommodation.getName());
                            type.setText(selectedAccommodation.getType());
                            capacity.setText(String.valueOf(selectedAccommodation.getSpecificRoom(optionFromComboBox).getCapacity()));
                            beds.setText(String.valueOf(selectedAccommodation.getSpecificRoom(optionFromComboBox).getTotalBeds()));
                            price.setText(String.valueOf(selectedAccommodation.getSpecificRoom(optionFromComboBox).getPricePerNight()));

                        }
                    });

                    changes.addActionListener(e1 -> {
                        String capacityTxt = capacity.getText();
                        String typeTxt = type.getText();
                        String nameTxt = name.getText();
                        String bedsTxt = beds.getText();
                        String priceTxt = price.getText();
                        double priceDouble;
                        int bedsInt,capacityInt;
                        try {
                            bedsInt = Integer.parseInt(bedsTxt);
                            priceDouble = Double.parseDouble(priceTxt);
                            capacityInt = Integer.parseInt(capacityTxt);

                        } catch(Exception er){
                            error.setBounds(200,500,300,100);
                            return;
                        }
                        if(!capacityTxt.isEmpty() && !name.getText().isEmpty()){
                            selectedAccommodation.setType(typeTxt);
                            selectedAccommodation.setName(nameTxt);
                            selectedAccommodation.getSpecificRoom(rooms.getSelectedIndex()).setPricePerNight(priceDouble);
                            selectedAccommodation.getSpecificRoom(rooms.getSelectedIndex()).setTotalBeds(bedsInt);
                            selectedAccommodation.getSpecificRoom(rooms.getSelectedIndex()).setCapacity(capacityInt);
                            listOfAccommodations.updateAccommodationList();
                            listOfAccommodations.updateReservation(listOfReservations,selectedAccommodation);
                            listOfAccommodations.updateAccommodationList();
                            listOfReservations.updateReservation();

                        }

                    });

                    JButton deleteTheAccommodation = new JButton("Delete This Accommodation");
                    deleteTheAccommodation.addActionListener(e1 -> {
                        listOfAccommodations.deleteAnAccommodation(selectedAccommodation);
                        listOfReservations.removeReservations(selectedAccommodation);
                        frame.dispose();
                    });

                    name.setBounds(100,100,200,30);
                    deleteTheAccommodation.setBounds(100,200,200,30);
                    type.setBounds(100,50,200,30);
                    rooms.setBounds(100,300,200,30);
                    capacity.setBounds(100,350,200,30);
                    beds.setBounds(100,390,200,30);
                    price.setBounds(100,430,200,30);
                    changes.setBounds(100,480,200,30);
                    frame.add(capacity);
                    frame.add(beds);
                    frame.add(price);
                    frame.add(rooms);
                    frame.add(error);
                    frame.add(name);
                    frame.add(changes);
                    frame.add(deleteTheAccommodation);
                    frame.add(type);
                    frame.setVisible(true);

                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        returnButton.addActionListener(e -> {
            removeAll();
            repaint();
            add(new ProviderWindow(provider,listOfAccommodations,listOfAccounts,listOfReservations));
        });
        add(scrollPane);
        add(returnButton);
        add(filters);
        setVisible(true);
    }

    public void updateTable(int option,DefaultTableModel model,Provider provider,ReservationsCreated reservations,AccommodationsCreated listOfAccommodations){
        model.setRowCount(0);
        ArrayList<Reservation> providerReservedAccommodations;

        if(option==1){
            System.out.println("In the 1");
            providerReservedAccommodations = reservations.reservationsForASpecificProvider(provider);
        }
        else{
            System.out.println("In the 2");
            providerReservedAccommodations = reservations.cancelledReservationsForASpecificProvider(provider);
        }

        int size = providerReservedAccommodations.size();
        String[][] data = new String[size][11];
        int count = 0;
        for(Reservation x: providerReservedAccommodations){
            data[count][0] = x.getAccommodation().getName();
            data[count][1] = x.getAccommodation().getType();
            data[count][2] = x.getAccommodation().getLocation().getCountry();
            data[count][3] = x.getAccommodation().getLocation().getTown();
            data[count][4] = x.getAccommodation().getLocation().getStreet().toUpperCase(Locale.ROOT);
            data[count][5] = String.valueOf(x.getRoomNumber()+1);
            data[count][6] = String.valueOf(x.getDate().nightsBetweenDates()*x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getPricePerNight());
            data[count][7] = x.getCustomer().getEmail();
            data[count][8] = x.getCustomer().getFullName();
            data[count][9] = x.getCustomer().getCountry();
            data[count][10] = x.getDate().getDatesAsString();
            count++;
        }

        for (String[] strings : data) {
            model.addRow(new Object[] {strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8],strings[9],strings[10]});
        }
    }

    public String[][] makeTable(AccommodationsCreated listOfAccommodations,Provider provider){
        ArrayList<Accommodation> accommodationList;
        accommodationList = listOfAccommodations.providerAccommodationList(provider);
        int size = accommodationList.size();
        String[][] data = new String[size][11];
        int count = 0;
        for(Accommodation x: accommodationList){
            data[count][0] = x.getName();
            data[count][1] = x.getType();
            data[count][2] = x.getLocation().getCountry();
            data[count][3] = x.getLocation().getTown();
            data[count][4] = x.getLocation().getStreet();
            data[count][5] = String.valueOf(x.getNumberOfRooms());
            data[count][6] = String.valueOf(x.getHasWifi());
            data[count][7] = String.valueOf(x.getHasParking());
            data[count][8] = String.valueOf(x.getHasPool());
            data[count][9] = String.valueOf(x.getHasRestaurant());
            data[count][10] = String.valueOf(x.getPetsAllowed());
            count++;
        }
        return data;
    }


    public String[][] makeAccommodationList(AccommodationsCreated listOfAccommodations,Provider provider,DefaultTableModel model){
        model.setRowCount(0);
        ArrayList<Accommodation> accommodationList;
        accommodationList = listOfAccommodations.providerAccommodationList(provider);
        int size = accommodationList.size();
        String[][] data = new String[size][11];
        int count = 0;
        for(Accommodation x: accommodationList){
            data[count][0] = x.getName();
            data[count][1] = x.getType();
            data[count][2] = x.getLocation().getCountry();
            data[count][3] = x.getLocation().getTown();
            data[count][4] = x.getLocation().getStreet();
            data[count][5] = String.valueOf(x.getNumberOfRooms());
            data[count][6] = String.valueOf(x.getHasWifi());
            data[count][7] = String.valueOf(x.getHasParking());
            data[count][8] = String.valueOf(x.getHasPool());
            data[count][9] = String.valueOf(x.getHasRestaurant());
            data[count][10] = String.valueOf(x.getPetsAllowed());
            count++;
        }
        return data;
    }
}
