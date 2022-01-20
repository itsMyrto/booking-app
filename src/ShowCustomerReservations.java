import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

/**
 * This class contains the panel that show a list with all the reservations of a customer
 */
public class ShowCustomerReservations extends JPanel {

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth());
    public final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());

    /**
     * This constructor creates the panel
     * @param customer The customer who is logged in
     * @param listOfAccounts A list of all the accounts
     * @param listOfAccommodations A list of all the accommodation
     * @param listOfReservations A list with all the reservations
     * @param mainFrame The frame of the app
     */
    public ShowCustomerReservations(Customer customer,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){

        this.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        setBackground(Color.WHITE);

        int pixelCounter = 0;

        setLayout(null);

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(0,pixelCounter,50,20);
        returnButton.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        returnButton.setForeground(new Color(191, 0, 255));
        returnButton.setBackground(Color.white);
        returnButton.setFocusable(false);
        returnButton.setBorder(null);
        add(returnButton);
        returnButton.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.addInitialImage();
            mainFrame.getContentPane().add(new CustomerWindow(customer, listOfAccounts, listOfAccommodations,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
        });

        JLabel label = new JLabel("MY RESERVATIONS");
        label.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 28));
        label.setForeground(new Color(6, 48, 124));
        label.setBounds(0,pixelCounter,PANEL_WIDTH,100);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        pixelCounter+=80;
        JLabel note = new JLabel("Click on a reservation to check the status or to cancel it");
        note.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        note.setForeground(Color.red);
        note.setBounds(0,pixelCounter,PANEL_WIDTH,30);
        note.setHorizontalAlignment(SwingConstants.CENTER);
        add(note);

        String [] tableHeaders = {"Name","Type","Country","City","Address","Room Number","Price (one night)","Capacity","View","Total Price","Dates"};
        DefaultTableModel model = new DefaultTableModel(createTable(customer,listOfReservations),tableHeaders){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JComboBox<String> filter;
        String [] availableFilter = {"All Reservations","Cancelled Reservations"};
        filter = new JComboBox<>(availableFilter) {
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

        filter.setBounds(PANEL_WIDTH/10,pixelCounter,200,25);
        filter.setFocusable(false);
        filter.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 12));
        filter.setSelectedIndex(0);
        filter.addActionListener(e -> {
            int optionFromComboBox = filter.getSelectedIndex();
            updateTable(optionFromComboBox,model,customer,listOfReservations);
        });

        JTable table = new JTable(model);
        table.setOpaque(false);
        table.setFocusable(false);
        table.setRowSelectionAllowed(true);
        table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC+Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(182, 219, 252));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int rowIndex = table.getSelectedRow();
                if(rowIndex != -1){
                    JFrame frame = new JFrame("Delete Reservation");
                    frame.setResizable(false);
                    frame.getContentPane().setBackground(Color.white);
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    String accommodationName = table.getValueAt(rowIndex,0).toString();
                    String roomNumberTxt = table.getValueAt(rowIndex,5).toString();
                    int roomNumber = Integer.parseInt(roomNumberTxt);
                    String dateTxt = table.getValueAt(rowIndex,10).toString();
                    String[] date = dateTxt.split("/");
                    String[] yearTxt = date[2].split("-");
                    JButton cancel = new JButton("Delete");
                    JLabel label = new JLabel("Overview");
                    JLabel error = new JLabel();
                    JButton back = new JButton("Return");
                    JLabel dates = new JLabel("->Reserved Dates:");
                    JLabel datesString = new JLabel(dateTxt);
                    JLabel accName = new JLabel("->Accommodation Name:");
                    JLabel accNameTxt = new JLabel(accommodationName);
                    JLabel roomReserved = new JLabel("->Reserved Room: "+roomNumberTxt);
                    roomReserved.setForeground(new Color(0x06307C));
                    roomReserved.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
                    roomReserved.setBounds(50,230,300,50);
                    accName.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
                    accName.setForeground(new Color(0x06307C));
                    accName.setBounds(50,150,200,50);
                    accNameTxt.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
                    accNameTxt.setBounds(235,150,200,50);
                    accNameTxt.setForeground(new Color(0x06307C));
                    dates.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
                    dates.setForeground(new Color(0x06307C));
                    dates.setBounds(50,190,200,50);
                    datesString.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
                    datesString.setBounds(200,190,200,50);
                    datesString.setForeground(new Color(0x06307C));
                    back.setFocusable(false);
                    cancel.setBounds(20,350,200,30);
                    int day = Integer.parseInt(date[0]);
                    int month = Integer.parseInt(date[1]);
                    int year = Integer.parseInt(yearTxt[0]);
                    int day2 = Integer.parseInt(date[3]);
                    int month2 = Integer.parseInt(date[4]);
                    int year2 = Integer.parseInt(yearTxt[1]);
                    System.out.println(day+" "+month+" "+year);
                    System.out.println(roomNumber);
                    Date dateOfTheReservation = new Date(day,month,year,day2,month2,year2);
                    Reservation reservation = listOfReservations.getASpecificReservation(customer,accommodationName,dateOfTheReservation,roomNumber);
                    JLabel state = new JLabel();
                    if(reservation.getCancelled()){
                        state.setForeground(Color.red);
                        state.setText("->Reservation Status: "+"Reservation Cancelled");
                    }
                    else{
                        state.setText("->Reservation Status: "+"Reservation Active");
                        state.setForeground(Color.GREEN);
                    }
                    state.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
                    state.setBounds(50,275,400,30);
                    LocalDate dateNow = LocalDate.now();
                    cancel.setFocusable(false);
                    cancel.addActionListener(e1 -> {
                        error.setForeground(Color.red);
                        if(reservation.getCancelled()){
                            error.setText("You cannot cancel a cancelled reservation");
                            error.setBounds(100,400,300,50);
                        }
                        else{
                            if(year < dateNow.getYear() || year == dateNow.getYear() && month < dateNow.getMonthValue() || (year == dateNow.getYear() && month == dateNow.getMonthValue() && day<dateNow.getDayOfMonth())){
                                error.setBounds(100,400,300,50);
                                error.setText("You cannot cancel a reservation that is overdue");
                            }
                            else{
                                reservation.setCancelled(true);
                                listOfReservations.updateReservation();
                                frame.dispose();
                            }
                        }
                        updateTable(filter.getSelectedIndex(),model,customer,listOfReservations);

                    });

                    back.addActionListener(e1 -> frame.dispose());

                    label.setBounds(200,0,600,200);
                    label.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,18));
                    label.setForeground(new Color(0x06307C));
                    back.setBounds(250,350,200,30);
                    back.setForeground(Color.WHITE);
                    back.setBackground(new Color(0x06307C));
                    cancel.setBackground(new Color(0x06307C));
                    cancel.setForeground(Color.WHITE);
                    frame.add(back);
                    frame.add(dates);
                    frame.add(roomReserved);
                    frame.add(datesString);
                    frame.add(accName);
                    frame.add(accNameTxt);
                    frame.add(cancel);
                    frame.add(label);
                    frame.add(state);
                    frame.add(error);
                    frame.setSize(500,500);
                    frame.setLayout(null);
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
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

        JScrollPane scrollPane = new JScrollPane(table);
        int TABLE_WIDTH = (int) (0.9 * PANEL_WIDTH);
        int MARGIN_BETWEEN_TABLE_AND_EDGES = (PANEL_WIDTH - TABLE_WIDTH) / 2;
        int TABLE_HEIGHT = (int) (0.7 * PANEL_HEIGHT);
        int MARGIN_TABLE_FROM_TOP = (PANEL_HEIGHT - TABLE_HEIGHT) / 2;
        scrollPane.setBounds(MARGIN_BETWEEN_TABLE_AND_EDGES, MARGIN_TABLE_FROM_TOP, TABLE_WIDTH, TABLE_HEIGHT);
        scrollPane.getViewport().setBackground(Color.WHITE);
        filter.getEditor().getEditorComponent().setBackground(Color.WHITE);
        scrollPane.setVisible(true);


        add(scrollPane);
        add(filter);
        add(label);
        setOpaque(false);
    }

    /**
     * This method is used when the JTable that shows all the accommodations is creates.It saves in a string array useful information
     * for all the reservations of a customer like the name of the accommodations,the dates,the total price, etc.
     * @param customer The customer who is logged in and want to see the reservations
     * @param listOfReservations A list with all the reservations
     * @return A string array with all the data that are going to be shown in the JTable
     */
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
            data[count][5] = String.valueOf(x.getRoomNumber()+1);
            data[count][6] = String.valueOf(x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getPricePerNight());
            data[count][7] = String.valueOf(x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getCapacity());
            data[count][8] = x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getView();
            data[count][9] = String.valueOf(x.getDate().nightsBetweenDates()*x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getPricePerNight());
            data[count][10] = x.getDate().getDatesAsString();
            count++;
        }

        return data;
    }

    /**
     * This method is used when the customer changes the option form the combo box from all reservations to cancelled reservations and vice versa.
     * It collects the new data that are going to be shown in the JTable
     * @param option The option of the JCombo box
     * @param model The model of the table
     * @param customer The customer who is logged in
     * @param reservations A list with all the reservations
     */
    public void updateTable(int option,DefaultTableModel model,Customer customer,ReservationsCreated reservations){
        model.setRowCount(0);
        ArrayList<Reservation> customerReservation;
        if(option==0){
            System.out.println("In the 0");
            customerReservation = reservations.reservationsOfASpecificCustomer(customer);
        }
        else{
            System.out.println("In the 1");
            customerReservation = reservations.cancelledReservationsOfACustomer(customer);
            System.out.println(customerReservation.size());
        }

        int size = customerReservation.size();
        String[][] data = new String[size][11];
        int count = 0;
        for(Reservation x: customerReservation){
            data[count][0] = x.getAccommodation().getName();
            data[count][1] = x.getAccommodation().getType();
            data[count][2] = x.getAccommodation().getLocation().getCountry();
            data[count][3] = x.getAccommodation().getLocation().getTown();
            data[count][4] = x.getAccommodation().getLocation().getStreet().toUpperCase(Locale.ROOT);
            data[count][5] = String.valueOf(x.getRoomNumber()+1);
            data[count][6] = String.valueOf(x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getPricePerNight());
            data[count][7] = String.valueOf(x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getCapacity());
            data[count][8] = x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getView();
            data[count][9] = String.valueOf(x.getDate().nightsBetweenDates()*x.getAccommodation().getSpecificRoom(x.getRoomNumber()).getPricePerNight());
            data[count][10] = x.getDate().getDatesAsString();
            count++;
        }

        for (String[] strings : data) {
            model.addRow(new Object[] {strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8],strings[9],strings[10]});
        }
    }

}
