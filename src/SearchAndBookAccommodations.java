import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * This is the class that contains the panel for the search & book accommodation.
 * It contains one panel for the filters and one panel for the JTable.
 */
public class SearchAndBookAccommodations extends JPanel {

    private FiltersPanel filtersPanel ;
    /**
     * The constructor that creates the panel
     * @param customer The customer who searches for accommodations
     * @param listOfAccounts A list with all the accounts
     * @param listOfAccommodations A list with all the accommodations
     * @param listOfReservations A list with all the reservations
     * @param mainFrame The main frame of the program
     */
    public SearchAndBookAccommodations(Customer customer,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){

        filtersPanel = new FiltersPanel(mainFrame);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int PANEL_HEIGHT = (int) env.getMaximumWindowBounds().getHeight();
        int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth() * 0.8);
        this.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBounds(filtersPanel.PANEL_WIDTH, 0, PANEL_WIDTH, PANEL_HEIGHT);

        String [] tableHeaders = {"Name","Type","Country","City","Address","Room Number","Price (one night)","Capacity","View"};
        DefaultTableModel model = new DefaultTableModel(createTable(listOfAccommodations.getTheAccommodationList()),tableHeaders){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(10,10,50,20);
        returnButton.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        returnButton.setForeground(new Color(191, 0, 255));
        returnButton.setBackground(Color.white);
        returnButton.setFocusable(false);
        returnButton.setBorder(null);
        add(returnButton);
        returnButton.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.remove(filtersPanel);
            mainFrame.getContentPane().repaint();
            mainFrame.addInitialImage();
            mainFrame.getContentPane().add(new CustomerWindow(customer, listOfAccounts, listOfAccommodations,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
        });

        JTable table = new JTable(model);
        table.setOpaque(false);
        table.setFocusable(false);
        table.setRowSelectionAllowed(true);
        Color TABLE_CELLS_COLOR = Color.decode("#3B5998");
        table.setBackground(TABLE_CELLS_COLOR);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        int TABLE_CELLS_HEIGHT = 30;
        table.setRowHeight(TABLE_CELLS_HEIGHT);

        // Align cells in the middle
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0; i<table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(table);
        int TABLE_HEIGHT = (int) (0.7 * PANEL_HEIGHT);
        int MARGIN_TABLE_FROM_TOP = (PANEL_HEIGHT - TABLE_HEIGHT) / 2;
        int TABLE_WIDTH = (int) (0.9 * PANEL_WIDTH);
        int MARGIN_BETWEEN_TABLE_AND_EDGES = (PANEL_WIDTH - TABLE_WIDTH) / 2;
        scrollPane.setBounds(MARGIN_BETWEEN_TABLE_AND_EDGES, MARGIN_TABLE_FROM_TOP, TABLE_WIDTH, TABLE_HEIGHT);
        scrollPane.setVisible(true);
        scrollPane.getViewport().setBackground(Color.WHITE);
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
                String arrivingTxt = filtersPanel.arrivingDate.getText();
                String leavingTxt = filtersPanel.leavingDate.getText();
                String[] arriving = arrivingTxt.split("/");
                String[] leaving = leavingTxt.split("/");
                int arrivingDay ;
                int arrivingMonth ;
                int arrivingYear ;
                int leavingDay;
                int leavingMonth ;
                int leavingYear ;
                if(arriving.length !=3 && leaving.length != 3) filtersPanel.invalidDates.setVisible(true);
                try {
                    arrivingDay = Integer.parseInt(arriving[0]);
                    arrivingMonth = Integer.parseInt(arriving[1]);
                    arrivingYear = Integer.parseInt(arriving[2]);
                    leavingDay = Integer.parseInt(leaving[0]);
                    leavingMonth = Integer.parseInt(leaving[1]);
                    leavingYear = Integer.parseInt(leaving[2]);
                } catch(Exception er){
                    return;
                }
                Date dateOption = new Date(arrivingDay,arrivingMonth,arrivingYear,leavingDay,leavingMonth,leavingYear);
                if(!dateOption.checkingTheDates()) filtersPanel.invalidDates.setVisible(true);

                if(rowIndex != -1){
                    JFrame frame = new JFrame("Make a reservation");
                    frame.setSize(1000,800);
                    frame.setResizable(false);
                    frame.getContentPane().setBackground(Color.white);
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                    JButton yes = new JButton("Reserve");
                    yes.setBackground(new Color(0x06307C));
                    yes.setForeground(Color.WHITE);
                    yes.setFocusable(false);
                    yes.setBounds(250,650,200,40);

                    JButton no = new JButton("Return");
                    no.setBackground(new Color(0x06307C));
                    no.setForeground(Color.WHITE);
                    no.setFocusable(false);
                    no.setBounds(500,650,200,40);

                    String accommodationName = table.getValueAt(rowIndex,0).toString();
                    String location = table.getValueAt(rowIndex,4).toString();
                    String roomNumberTxt = table.getValueAt(rowIndex,5).toString();

                    Accommodation accommodation = listOfAccommodations.getSpecificAccommodation(accommodationName,location);

                    JLabel label = new JLabel(accommodation.getName()+" "+accommodation.getType()+", Location: "+accommodation.getLocation().getCountry()+","+accommodation.getLocation().getTown()+" "+accommodation.getLocation().getStreet()+" "+accommodation.getLocation().getStreetNumber());
                    label.setBounds(220,100,900,30);
                    label.setForeground(new Color(0x06307C));
                    label.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,16));

                    JLabel accImage = accommodation.getImage();
                    accImage.setBounds(180,150,600,450);

                    yes.addActionListener(e1 -> {
                        int roomNumber = Integer.parseInt(roomNumberTxt);
                        roomNumber--;
                        System.out.println(accommodation.getNumberOfRooms()+" "+roomNumber);
                        Reservation reservation = new Reservation(accommodation,customer,dateOption,roomNumber);
                        accommodation.getSpecificRoom(roomNumber).addReservedDates(dateOption);
                        listOfReservations.addNewReservation(reservation);
                        listOfAccommodations.updateAccommodationList();
                        model.removeRow(rowIndex);
                        frame.dispose();
                    });

                    no.addActionListener(e1 -> {
                        frame.dispose();
                    });

                    frame.add(accImage);
                    frame.add(label);
                    frame.add(yes);
                    frame.add(no);
                    frame.setLayout(null);
                    frame.setLocationRelativeTo(null);
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

        filtersPanel.applyFilters.addActionListener(e -> {
            if(e.getSource()==filtersPanel.applyFilters){
                String arrivingTxt = filtersPanel.arrivingDate.getText();
                String leavingTxt = filtersPanel.leavingDate.getText();
                String[] arriving = arrivingTxt.split("/");
                String[] leaving = leavingTxt.split("/");
                int arrivingDay ;
                int arrivingMonth ;
                int arrivingYear ;
                int leavingDay;
                int leavingMonth ;
                int leavingYear ;
                if(arriving.length !=3 && leaving.length != 3) filtersPanel.invalidDates.setVisible(true);
                try {
                    arrivingDay = Integer.parseInt(arriving[0]);
                    arrivingMonth = Integer.parseInt(arriving[1]);
                    arrivingYear = Integer.parseInt(arriving[2]);
                    leavingDay = Integer.parseInt(leaving[0]);
                    leavingMonth = Integer.parseInt(leaving[1]);
                    leavingYear = Integer.parseInt(leaving[2]);
                } catch(Exception er) {
                    return;
                }
                Date dateOption = new Date(arrivingDay,arrivingMonth,arrivingYear,leavingDay,leavingMonth,leavingYear);
                if(!dateOption.checkingTheDates()) {
                    filtersPanel.invalidDates.setVisible(true);
                    model.setRowCount(0);
                    return;
                }
                filtersPanel.invalidDates.setVisible(false);
                int maxPrice = filtersPanel.price.getValue();
                int capacity = filtersPanel.people.getValue();
                String countryTxt = filtersPanel.country.getText().toUpperCase(Locale.ROOT);
                String cityTxt = filtersPanel.city.getText().toUpperCase(Locale.ROOT);
                boolean poolOption = false, restaurantOption = false, wifiOption = false, parkingOption = false, petsOption = false;
                if(filtersPanel.pool.isSelected())  poolOption = true;
                if(filtersPanel.parking.isSelected())  parkingOption = true;
                if(filtersPanel.wifi.isSelected())  wifiOption = true;
                if(filtersPanel.restaurant.isSelected())  restaurantOption = true;
                if(filtersPanel.pets.isSelected())  petsOption = true;
                updateTable(model,parkingOption,wifiOption,poolOption,restaurantOption,petsOption,capacity,maxPrice,countryTxt,cityTxt,listOfAccommodations,dateOption);
            }
        });

        this.add(scrollPane);

        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(true);
    }

    /**
     * This method is used to collect new data 7 update the JTable when new filters are applied to change the available accommodations
     * @param model The model of the JTable
     * @param parkingSelected if parking is selected as a filter
     * @param wifiSelected if wifi is selected as a filter
     * @param poolSelected if pool is selected as a filter
     * @param restaurantSelected if restaurant is selected as a filter
     * @param petsSelected if pets allowed is selected as a filter
     * @param roomCapacity the newo room capacity
     * @param maxPrice the new max price
     * @param countryDestination the new country destination
     * @param cityDestination the new city destination
     * @param listOfAccommodations A list of all accommodations
     * @param date The new dates of arriving leaving
     */
    public void updateTable(DefaultTableModel model,boolean parkingSelected,boolean wifiSelected,boolean poolSelected,boolean restaurantSelected,boolean petsSelected,int roomCapacity,double maxPrice,String countryDestination,String cityDestination,AccommodationsCreated listOfAccommodations,Date date){
        HashMap<Accommodation,Integer> appliedFilters = listOfAccommodations.searchForAccommodations(parkingSelected,wifiSelected,poolSelected,restaurantSelected,petsSelected,roomCapacity,maxPrice,countryDestination,cityDestination,date);

        if(appliedFilters.size()==0){
            model.setRowCount(0);
            return;
        }
        String[][] newTable = createTable(appliedFilters);

        model.setRowCount(0);

        for (String[] strings : newTable) {
            model.addRow(new Object[] {strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8]});
        }
    }

    /**
     * This method collects the data that are shown at the beginning of the searching when the customer doesn't have applied filters yet.
     * Something like a recommendation list.
     * @param acc A list with all the accommodations
     * @return A string array with useful accommodation info
     */
    public String[][] createTable(ArrayList<Accommodation> acc){

        int size =0;
        for(Accommodation x:acc){
            size += x.getNumberOfRooms();
        }
        String[][] data = new String[size][9];
        int count = 0;
        for(Accommodation x: acc){
            for(int i=0;i<x.getNumberOfRooms();i++){
                System.out.println(x.getName());
                data[count][0] = x.getName();
                data[count][1] = x.getType();
                data[count][2] = x.getLocation().getCountry();
                data[count][3] = x.getLocation().getTown();
                data[count][4] = x.getLocation().getStreet().toUpperCase(Locale.ROOT);
                data[count][5] = String.valueOf(i+1);
                data[count][6] = String.valueOf(x.getSpecificRoom(i).getPricePerNight());
                data[count][7] = String.valueOf(x.getSpecificRoom(i).getCapacity());
                data[count][8] = x.getSpecificRoom(i).getView();
                count++;
            }

        }

        return data;
    }

    /**
     * This method is called only by the updateTable method.It gets a hashmap with all the available accommodations and makes a string array
     * that saves useful info like the name of the accommodation,the facilities etc.
     * @param acc A hashmap with all the available accounts based on filters
     * @return A string array with accommodation details
     */
    public String[][] createTable(HashMap<Accommodation,Integer> acc){
        int size = acc.size();

        String[][] data = new String[size][9];
        int count = 0;
        for(Accommodation x: acc.keySet()){
            data[count][0] = x.getName();
            data[count][1] = x.getType();
            data[count][2] = x.getLocation().getCountry();
            data[count][3] = x.getLocation().getTown();
            data[count][4] = x.getLocation().getStreet().toUpperCase(Locale.ROOT);
            data[count][5] = String.valueOf(acc.get(x)+1);
            data[count][6] = String.valueOf(x.getSpecificRoom(acc.get(x)).getPricePerNight());
            data[count][7] = String.valueOf(x.getSpecificRoom(acc.get(x)).getCapacity());
            data[count][8] = x.getSpecificRoom(acc.get(x)).getView();
            count++;
        }
        return data;
    }

}

/**
 * This is the panel where the filters are added
 */
class FiltersPanel extends JPanel{

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth()*0.2);
    public final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());

    private final String[] fieldTexts = { "Country", "City", "xx/yy/wwww"};

    public JTextField country = new JTextField(fieldTexts[0]);
    public JTextField city = new JTextField(fieldTexts[1]);
    public JTextField arrivingDate = new JTextField(fieldTexts[2]);
    public JTextField leavingDate = new JTextField(fieldTexts[2]);

    public JSlider people = new JSlider(1,13,1);
    public JSlider price = new JSlider(0,1000,100);

    public JCheckBox pool = new JCheckBox("Pool", false);
    public JCheckBox restaurant = new JCheckBox("Restaurant", false);
    public JCheckBox wifi = new JCheckBox("Wifi", false);
    public JCheckBox parking = new JCheckBox("Parking",false);
    public JCheckBox pets = new JCheckBox("Pets Allowed",false);

    public JButton applyFilters = new JButton("Apply");

    public JLabel invalidDates = new JLabel("Invalid Dates");

    public FiltersPanel(MainFrame mainFrame){
        this.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        int pixelCounter = 10;

        JLabel title = new JLabel("FILTERS");
        title.setOpaque(false);
        int TITLE_HEIGHT = 40;
        title.setBounds(0, pixelCounter, PANEL_WIDTH, TITLE_HEIGHT);
        Color CUSTOMIZED_COLOR = Color.decode("#3B5998");
        title.setForeground(CUSTOMIZED_COLOR);
        title.setFocusable(false);
        title.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, TITLE_HEIGHT -10));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        int MARGIN_FIELDS_FROM_TITLE = 20;
        pixelCounter += TITLE_HEIGHT + MARGIN_FIELDS_FROM_TITLE;

        int FIELDS_HEIGHT = 20;
        int FIELDS_WIDTH = (int) (PANEL_WIDTH * 0.6);
        country.setBounds((PANEL_WIDTH- FIELDS_WIDTH)/2, pixelCounter, FIELDS_WIDTH, FIELDS_HEIGHT);

        int MARGIN_BETWEEN_FIELDS = 20;
        pixelCounter += FIELDS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        city.setBounds((PANEL_WIDTH- FIELDS_WIDTH)/2, pixelCounter, FIELDS_WIDTH, FIELDS_HEIGHT);

        pixelCounter += FIELDS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        arrivingDate.setBounds((PANEL_WIDTH- FIELDS_WIDTH)/2, pixelCounter, FIELDS_WIDTH, FIELDS_HEIGHT);

        pixelCounter += FIELDS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        leavingDate.setBounds((PANEL_WIDTH- FIELDS_WIDTH)/2, pixelCounter, FIELDS_WIDTH, FIELDS_HEIGHT);

        int MARGIN_SLIDERS_FROM_FIELDS = 40;
        pixelCounter += FIELDS_HEIGHT + MARGIN_SLIDERS_FROM_FIELDS;

        people.setOpaque(false);
        people.setPaintLabels(true);
        people.setPaintTicks(true);
        people.setPaintTrack(true);
        people.setMinorTickSpacing(1);
        people.setMajorTickSpacing(4);
        people.setFont(new Font("Tahoma", Font.BOLD, 16));
        people.setForeground(CUSTOMIZED_COLOR);
        int SLIDERS_HEIGHT = 40;
        int SLIDERS_WIDTH = (int) (PANEL_WIDTH * 0.6);
        people.setBounds((PANEL_WIDTH- SLIDERS_WIDTH)/2, pixelCounter, SLIDERS_WIDTH, SLIDERS_HEIGHT);
        people.setFocusable(false);
        people.setAlignmentX(CENTER_ALIGNMENT);

        int MARGIN_BETWEEN_SLIDERS = 20;
        pixelCounter += SLIDERS_HEIGHT + MARGIN_BETWEEN_SLIDERS;

        price.setOpaque(false);
        price.setFont(new Font("Tahoma", Font.BOLD, 16));
        price.setForeground(CUSTOMIZED_COLOR);
        price.setBounds((PANEL_WIDTH- SLIDERS_WIDTH)/2, pixelCounter, SLIDERS_WIDTH, SLIDERS_HEIGHT);
        price.setPaintLabels(true);
        price.setPaintTicks(true);
        price.setFocusable(false);
        price.setPaintTrack(true);
        price.setMinorTickSpacing(50);
        price.setMajorTickSpacing(500);
        price.setAlignmentX(CENTER_ALIGNMENT);

        int MARGIN_CHECKBOXES_FROM_SLIDERS = 20;
        pixelCounter += SLIDERS_HEIGHT + MARGIN_CHECKBOXES_FROM_SLIDERS;

        int CHECKBOXES_WIDTH = (int) (PANEL_WIDTH * 0.6);
        pool.setBounds((PANEL_WIDTH- CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        pool.setText("Pool");
        pool.setForeground(CUSTOMIZED_COLOR);
        pool.setOpaque(false);
        pool.setFocusable(false);
        pool.setFont(new Font("Tahoma", Font.BOLD, 16));
        pool.setAlignmentX(CENTER_ALIGNMENT);

        int MARGIN_BETWEEN_CHECKBOXES = 20;
        int CHECKBOXES_HEIGHT = 20;
        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        restaurant.setBounds((PANEL_WIDTH- CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        restaurant.setText("Restaurant");
        restaurant.setForeground(CUSTOMIZED_COLOR);
        restaurant.setOpaque(false);
        restaurant.setFocusable(false);
        restaurant.setFont(new Font("Tahoma", Font.BOLD, 16));

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        wifi.setBounds((PANEL_WIDTH- CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        wifi.setText("Wifi");
        wifi.setForeground(CUSTOMIZED_COLOR);
        wifi.setOpaque(false);
        wifi.setFocusable(false);
        wifi.setFont(new Font("Tahoma", Font.BOLD, 16));

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        parking.setBounds((PANEL_WIDTH- CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        parking.setText("Parking");
        parking.setForeground(CUSTOMIZED_COLOR);
        parking.setOpaque(false);
        parking.setFocusable(false);
        parking.setFont(new Font("Tahoma", Font.BOLD, 16));

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        pets.setBounds((PANEL_WIDTH- CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        pets.setText("Pets");
        pets.setForeground(CUSTOMIZED_COLOR);
        pets.setFocusable(false);
        pets.setOpaque(false);
        pets.setFont(new Font("Tahoma", Font.BOLD, 16));

        int MARGIN_BTN_FROM_CHECKBOXES = 60;
        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BTN_FROM_CHECKBOXES;

        int BTN_HEIGHT = 30;
        int BTN_WIDTH = PANEL_WIDTH / 2;
        applyFilters.setBounds((PANEL_WIDTH- BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);
        applyFilters.setFocusable(false);
        applyFilters.setForeground(Color.WHITE);
        applyFilters.setFont(new Font("Tahoma", Font.BOLD, 16));
        applyFilters.setBackground(CUSTOMIZED_COLOR);

        pixelCounter -= MARGIN_BTN_FROM_CHECKBOXES /2;

        invalidDates.setForeground(Color.RED);
        invalidDates.setFont(new Font("Tahoma", Font.BOLD, 16));
        invalidDates.setBounds(0, pixelCounter, PANEL_WIDTH, CHECKBOXES_HEIGHT);
        invalidDates.setOpaque(false);
        invalidDates.setHorizontalAlignment(SwingConstants.CENTER);
        invalidDates.setVisible(false);

        country.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(country.getText().equals(fieldTexts[0])) country.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(country.getText().equals("")) country.setText(fieldTexts[0]);
            }
        });

        city.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(city.getText().equals(fieldTexts[1])) city.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(city.getText().equals("")) city.setText(fieldTexts[1]);
            }
        });

        country.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(country.getText().equals("Country")) country.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(country.getText().equals("")) country.setText("Country");
            }
        });

        arrivingDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(arrivingDate.getText().equals(fieldTexts[2])) arrivingDate.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(arrivingDate.getText().equals("")) arrivingDate.setText(fieldTexts[2]);
            }
        });

        leavingDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(leavingDate.getText().equals(fieldTexts[2])) leavingDate.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(leavingDate.getText().equals("")) leavingDate.setText(fieldTexts[2]);
            }
        });

        this.add(title);
        this.add(country);
        this.add(city);
        this.add(arrivingDate);
        this.add(leavingDate);
        this.add(people);
        this.add(price);
        this.add(pets);
        this.add(parking);
        this.add(wifi);
        this.add(restaurant);
        this.add(pool);
        this.add(applyFilters);
        this.add(invalidDates);

        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(true);
        mainFrame.getContentPane().add(this);
    }
}
