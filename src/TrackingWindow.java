import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class is the GUI where admins track other users,like details about the reservations of the customers or the accommodations of providers.
 * There is a combo box with a few options:Reservation tracking,accommodation tracking etc. and also there is an option of tracking a specific user,
 * which is based on their email because it's always unique.
 */
public class TrackingWindow extends JPanel {

    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth());
    public final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());
    private final int TABLE_WIDTH = (int) (0.9*PANEL_WIDTH);
    private final int TABLE_HEIGHT = (int) (0.7*PANEL_HEIGHT);
    private final int MARGIN_TABLE_FROM_TOP = (PANEL_HEIGHT-TABLE_HEIGHT)/2;
    private final int MARGIN_BETWEEN_TABLE_AND_EDGES = (PANEL_WIDTH-TABLE_WIDTH)/2;

    public TrackingWindow(Admin admin,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){
        setLayout(null);
        setSize(PANEL_WIDTH,PANEL_HEIGHT);
        setBackground(Color.WHITE);

        int pixelCounter = 0;

        JLabel label = new JLabel("Track Customers & Providers");
        label.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,28));
        label.setForeground(new Color(0x06307C));
        label.setBounds(0,pixelCounter,PANEL_WIDTH,30);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(0, pixelCounter,50,20);
        returnButton.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        returnButton.setForeground(new Color(191, 0, 255));
        returnButton.setBackground(Color.white);
        returnButton.setFocusable(false);
        returnButton.setBorder(null);
        add(returnButton);

        pixelCounter+=50;
        JLabel customerDetails= new JLabel();
        customerDetails.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        customerDetails.setForeground(new Color(0x06307C));
        customerDetails.setBounds(0,pixelCounter,PANEL_WIDTH,30);
        customerDetails.setHorizontalAlignment(SwingConstants.CENTER);
        customerDetails.setVisible(false);

        String[] allEmails = listOfAccounts.getAllCustomersEmail();
        JComboBox<String > users = new JComboBox<>(allEmails) {
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

        users.setSelectedIndex(0);
        users.setVisible(false);

        returnButton.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.addInitialImage();
            mainFrame.getContentPane().add(new AdminWindow(admin, listOfAccounts, listOfAccommodations,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
        });


        String [] tableHeaders2 = {"Reservation Status","Name","Country","City","Room Number","Total Price","Dates"};
        String [] tableHeaders3 = {"User","Name","Country","City","Room Number","Total Price","Dates"};
        String [] tableHeaders = {"Provider Email","Name","Type","Country","City","Rooms","Reservations"};
        String[][] data = makeTable(listOfAccommodations,listOfReservations);

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

        filters.addActionListener(e -> {
            int optionFromComboBox = filters.getSelectedIndex();
            if (optionFromComboBox == 1 || optionFromComboBox==2) {
                users.setVisible(false);
                customerDetails.setVisible(false);
                for(int i=0;i<7;i++){
                    table.getColumnModel().getColumn(i).setHeaderValue(tableHeaders3[i]);
                }
                updateTable(optionFromComboBox, model, listOfReservations);
            }
            else if(optionFromComboBox==0){
                users.setVisible(false);
                customerDetails.setVisible(false);
                for(int i=0;i<7;i++){
                    table.getColumnModel().getColumn(i).setHeaderValue(tableHeaders[i]);
                    String[][] accommodationData = makeAccommodationList(listOfAccommodations,listOfReservations,model);
                    for (String[] strings : accommodationData) {
                        model.addRow(new Object[] {strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6]});
                    }
                }
            }
            else if(optionFromComboBox==3){
                users.setVisible(true);
                users.addActionListener(e1 -> {
                    int optionFromUserComboBox = users.getSelectedIndex();
                    String email = allEmails[optionFromUserComboBox];
                    User user = listOfAccounts.getSpecificAccount(email);
                    customerDetails.setText("Name: "+user.getFullName()+",Email: "+user.getEmail()+",Origin: "+user.getCountry());
                    customerDetails.setVisible(true);
                    if(user instanceof Customer){
                        for(int i=0;i<7;i++){
                            table.getColumnModel().getColumn(i).setHeaderValue(tableHeaders2[i]);
                        }
                    }
                    else{
                        for(int i=0;i<7;i++){
                            table.getColumnModel().getColumn(i).setHeaderValue(tableHeaders[i]);
                        }
                    }

                    updateTableForASpecificUser(user,model,listOfAccommodations,listOfReservations);
                });
            }
        });

        filters.setBounds(PANEL_WIDTH/20,pixelCounter,200,25);
        filters.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 12));
        filters.setForeground(new Color(0x06307C));

        pixelCounter += 30;
        users.setBounds(PANEL_WIDTH/20,pixelCounter,200,25);

        table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC+Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(182, 219, 252));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(MARGIN_BETWEEN_TABLE_AND_EDGES,MARGIN_TABLE_FROM_TOP,TABLE_WIDTH,TABLE_HEIGHT);
        scrollPane.getViewport().setBackground(Color.WHITE);


        add(users);
        add(customerDetails);
        table.setOpaque(false);
        add(scrollPane);
        add(filters);
        setVisible(true);

    }

    /**
     * This method makes a string array that contains all the accommodations of all the users.
     * It is used to initialise the contents of the JTable.
     * @param listOfAccommodations All the accommodations of the app
     * @param reservations All the reservations of the app
     * @return A String array that contains the new data that are going to be shown in the JTable
     */
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

    /**
     * This method is used to update the contents of the JTable when an admin choose the first or the second option from the combo box.
     * It deletes all the previous contents and adds the new ones
     * @param optionFromComboBox The option of the combo box that can be 1 or 2 for this method
     * @param model The model of the JTable
     * @param listOfReservations List of all the reservations made
     */
    public void updateTable(int optionFromComboBox,DefaultTableModel model,ReservationsCreated listOfReservations){
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

    /**
     * This method is used when an admin wants to track a specific user, and it updates the JTable with new contents.
     * All the accommodations for a provider and all the reservations for a customer
     * @param user The user an admin wants to track
     * @param model The model of the JTable
     * @param listOfAccommodations A list with all the accommodations
     * @param listOfReservations A list with all the reservations
     */
    public void updateTableForASpecificUser(User user,DefaultTableModel model,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        model.setRowCount(0);
        if(user instanceof Customer){
            ArrayList<Reservation> reservations = listOfReservations.reservationsOfASpecificCustomer((Customer) user);
            int size = reservations.size();
            String[][] data = new String[size][7];
            int count = 0;
            for(Reservation x: reservations){
                if(x.getCancelled()){
                    data[count][0] = "Active";
                }
                else{
                    data[count][0] = "Cancelled";
                }
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

    /**
     * This method is used when an admin wants to track all the accommodations. It updates the JTable with new contents.
     * @param listOfAccommodations A list with all the accommodations
     * @param listOfReservations A list with all the reservations
     * @param model The model of the JTable
     * @return A string array with all the accommodation names.
     */
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
