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

    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth());
    public final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());
    private final int TABLE_WIDTH = (int) (0.9*PANEL_WIDTH);
    private final int TABLE_HEIGHT = (int) (0.7*PANEL_HEIGHT);
    private final int MARGIN_TABLE_FROM_TOP = (PANEL_HEIGHT-TABLE_HEIGHT)/2;
    private final int MARGIN_BETWEEN_TABLE_AND_EDGES = (PANEL_WIDTH-TABLE_WIDTH)/2;

    ProviderAccommodationList(Provider provider,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations,MainFrame mainFrame){

        setOpaque(false);
        setLayout(null);

        this.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        setBackground(Color.WHITE);

        int pixelCounter = 0;

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(0,pixelCounter,50,20);
        returnButton.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        returnButton.setForeground(new Color(191, 0, 255));
        returnButton.setBackground(Color.white);
        returnButton.setFocusable(false);
        returnButton.setBorder(null);

        JLabel label = new JLabel("My Accommodation List");
        label.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,28));
        label.setForeground(new Color(0x06307C));
        label.setBounds(0,pixelCounter,PANEL_WIDTH,40);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        pixelCounter+=50;

        JLabel label2 = new JLabel("Click to an accommodation to make changes");
        label2.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        label2.setForeground(Color.red);
        label2.setBounds(0,pixelCounter,PANEL_WIDTH,30);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        add(label2);

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
                    arrow.setBackground(new Color(131, 167, 245));
                }
            }
        };

        filters.addActionListener(e -> {
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
        });

        filters.setBounds(PANEL_WIDTH/10,pixelCounter,200,20);

        table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC+Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(182, 219, 252));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);
        scrollPane.getViewport().setBackground(new Color(0xFFFFFF));
        scrollPane.setBounds(MARGIN_BETWEEN_TABLE_AND_EDGES,MARGIN_TABLE_FROM_TOP,TABLE_WIDTH,TABLE_HEIGHT);

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int rowIndex = table.getSelectedRow();
                if(rowIndex != -1){
                    JFrame frame = new JFrame("Modify Accommodations");
                    frame.setSize(1500,800);
                    frame.getContentPane().setBackground(Color.WHITE);
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setLayout(null);
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                    JLabel label = new JLabel("Change information");
                    label.setForeground(new Color(0x06307C));
                    label.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,26));
                    label.setBounds(580,-10,300,50);
                    frame.add(label);

                    JLabel nameA = new JLabel("Change the name:");
                    nameA.setForeground(new Color(0x06307C));
                    nameA.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,16));
                    nameA.setBounds(100,50,300,50);
                    frame.add(nameA);

                    JLabel typeA = new JLabel("Change the type:");
                    typeA.setForeground(new Color(0x06307C));
                    typeA.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,16));
                    typeA.setBounds(100,150,300,50);
                    frame.add(typeA);

                    JLabel roomA = new JLabel("Choose a room:");
                    roomA.setForeground(new Color(0x06307C));
                    roomA.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,16));
                    roomA.setBounds(100,250,300,50);
                    frame.add(roomA);

                    JLabel capacityA = new JLabel("Change the capacity:");
                    capacityA.setForeground(new Color(0x06307C));
                    capacityA.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,16));
                    capacityA.setBounds(100,350,300,50);
                    frame.add(capacityA);

                    JLabel priceA = new JLabel("Change the price(one night):");
                    priceA.setForeground(new Color(0x06307C));
                    priceA.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,16));
                    priceA.setBounds(100,450,300,50);
                    frame.add(priceA);

                    JLabel numberOfBedsA = new JLabel("Change the number of beds:");
                    numberOfBedsA.setForeground(new Color(0x06307C));
                    numberOfBedsA.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,16));
                    numberOfBedsA.setBounds(100,550,300,50);
                    frame.add(numberOfBedsA);

                    JLabel newRoom = new JLabel("Add a new Room");
                    newRoom.setForeground(new Color(0x06307C));
                    newRoom.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,16));
                    newRoom.setBounds(620,50,300,50);
                    frame.add(newRoom);

                    JLabel chooseView = new JLabel("Select the view");
                    chooseView.setForeground(new Color(0x06307C));
                    chooseView.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,16));
                    chooseView.setBounds(600,250,300,50);
                    frame.add(chooseView);

                    JButton addRoom = new JButton("Add Room");
                    addRoom.setFocusable(false);
                    addRoom.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
                    addRoom.setBackground(new Color(0x06307C));
                    addRoom.setForeground(Color.white);
                    addRoom.setBounds(600,450,200,30);

                    JLabel error2 = new JLabel("Invalid Information");
                    error2.setForeground(Color.red);

                    JLabel error = new JLabel("Invalid information");
                    error.setForeground(Color.red);
                    error.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));

                    JTextField newRoomCapacity = new JTextField("Room Capacity");
                    newRoomCapacity.setBounds(600,100,200,25);

                    JTextField newRoomPrice = new JTextField("Price For One Night");
                    newRoomPrice.setBounds(600,150,200,25);

                    JTextField newRoomBeds = new JTextField("Number of Beds");
                    newRoomBeds.setBounds(600,200,200,25);

                    JButton deleteTheAccommodation = new JButton("Delete This Accommodation");
                    deleteTheAccommodation.setBounds(700,700,300,30);
                    deleteTheAccommodation.setForeground(Color.white);
                    deleteTheAccommodation.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
                    deleteTheAccommodation.setBackground(new Color(0x06307C));

                    JComboBox<String> view;
                    String[] viewType = {"Sea","Forest","The city","Landmark","Lake"};
                    view = new JComboBox<>(viewType){
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
                    view.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 12));
                    view.setBounds(600,300,200,25);
                    view.setSelectedIndex(0);

                    String nameOfTheAcc = table.getValueAt(rowIndex,0).toString();
                    String address = table.getValueAt(rowIndex,4).toString();
                    Accommodation selectedAccommodation = listOfAccommodations.getSpecificAccommodation(nameOfTheAcc,address);
                    if(selectedAccommodation.getNumberOfRooms()==0){
                        frame.dispose();
                        return;
                    }

                    JTextField name = new JTextField(selectedAccommodation.getName());

                    JTextField type = new JTextField(selectedAccommodation.getType());

                    JTextField capacity = new JTextField(String.valueOf(selectedAccommodation.getSpecificRoom(0).getCapacity()));

                    JTextField price = new JTextField(String.valueOf(selectedAccommodation.getSpecificRoom(0).getPricePerNight()));

                    JTextField beds = new JTextField(String.valueOf(selectedAccommodation.getSpecificRoom(0).getTotalBeds()));

                    JButton changes = new JButton("Confirm Changes");
                    changes.setBounds(350,700,300,30);
                    changes.setForeground(Color.WHITE);
                    changes.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
                    changes.setBackground(new Color(0x06307C));

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

                    rooms.addActionListener(e12 -> {
                        System.out.println("Here");
                        int optionFromComboBox = rooms.getSelectedIndex();
                        name.setText(selectedAccommodation.getName());
                        type.setText(selectedAccommodation.getType());
                        capacity.setText(String.valueOf(selectedAccommodation.getSpecificRoom(optionFromComboBox).getCapacity()));
                        beds.setText(String.valueOf(selectedAccommodation.getSpecificRoom(optionFromComboBox).getTotalBeds()));
                        price.setText(String.valueOf(selectedAccommodation.getSpecificRoom(optionFromComboBox).getPricePerNight()));

                    });


                    addRoom.addActionListener(e1 -> {
                        String capacityTxt = newRoomCapacity.getText();
                        String bedsTxt = newRoomBeds.getText();
                        String priceTxt = newRoomPrice.getText();
                        int selectedViewIndex = view.getSelectedIndex();
                        String selectedView = viewType[selectedViewIndex];
                        double priceDouble;
                        int bedsInt,capacityInt;
                        try {
                            bedsInt = Integer.parseInt(bedsTxt);
                            priceDouble = Double.parseDouble(priceTxt);
                            capacityInt = Integer.parseInt(capacityTxt);
                        } catch(Exception er){
                            error.setText("Invalid Information");
                            error.setBounds(630,400,200,30);
                            //er.printStackTrace();
                            return;
                        }
                        if(!(capacityTxt.isEmpty() && bedsTxt.isEmpty() && priceTxt.isEmpty())){
                            Room room = new Room(capacityInt,priceDouble,selectedView,bedsInt);
                            System.out.println("Room Added");
                            newRoomCapacity.setText("Room Capacity");
                            newRoomPrice.setText("Price for one Night");
                            newRoomBeds.setText("Number of Beds");
                            selectedAccommodation.addRoom(room);
                            listOfAccommodations.updateAccommodationList();
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
                            error.setBounds(150,620,300,100);
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
                            frame.dispose();
                        }

                    });

                    deleteTheAccommodation.addActionListener(e1 -> {
                        listOfAccommodations.deleteAnAccommodation(selectedAccommodation);
                        listOfReservations.removeReservations(selectedAccommodation);
                        listOfAccommodations.updateAccommodationList();
                        listOfReservations.updateReservation();
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
                        frame.dispose();
                    });

                    name.setBounds(100,100,200,30);
                    type.setBounds(100,200,200,30);
                    rooms.setBounds(100,300,200,30);
                    capacity.setBounds(100,400,200,30);
                    beds.setBounds(100,600,200,30);
                    price.setBounds(100,500,200,30);

                    frame.add(newRoom);
                    frame.add(newRoomCapacity);
                    frame.add(newRoomBeds);
                    frame.add(newRoomPrice);
                    frame.add(view);
                    frame.add(addRoom);
                    frame.add(error2);
                    frame.add(numberOfBedsA);
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
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new ProviderWindow(provider,listOfAccommodations,listOfAccounts,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
            mainFrame.addInitialImage();
        });
        add(scrollPane);
        add(returnButton);
        add(filters);
        add(label);
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

