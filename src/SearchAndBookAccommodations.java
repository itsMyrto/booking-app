import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class SearchAndBookAccommodations extends JPanel {

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth() * 0.8);
    private final int PANEL_HEIGHT = (int) env.getMaximumWindowBounds().getHeight();

    public SearchAndBookAccommodations(Customer customer,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){
        LocalDate dateNow = LocalDate.now();

        this.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        String [] tableHeaders = {"Name","Type","Country","City","Address","Room Number","Price (one night)","Capacity","View"};
        Date date = new Date(dateNow.getDayOfMonth(),dateNow.getMonthValue(),dateNow.getYear(),dateNow.getDayOfMonth()+1,dateNow.getMonthValue(),dateNow.getYear());
        DefaultTableModel model = new DefaultTableModel(createTable(listOfAccommodations.getTheAccommodationList()),tableHeaders){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Color white = new Color(255, 255, 255);
        JTable table = new JTable(model);
        table.setOpaque(false);
        table.setFocusable(false);
        table.setRowSelectionAllowed(true);
        table.setBackground(Color.CYAN);
        table.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(300,100,1000,500);
        scrollPane.setVisible(true);
        scrollPane.getViewport().setBackground(white);

        JTextField country = new JTextField("Greece");
        JTextField city = new JTextField("Kastoria");
        JCheckBox pool = new JCheckBox("Pool");
        JCheckBox restaurant = new JCheckBox("Restaurant");
        JButton book = new JButton("Book");
        JCheckBox wifi = new JCheckBox("Wifi");
        JCheckBox parking = new JCheckBox("Parking",false);
        JCheckBox pets = new JCheckBox("Pets Allowed",false);
        JSlider people = new JSlider(1,13,1);
        JSlider price = new JSlider(0,1000,100);
        JTextField arrivingDate = new JTextField("xx/yy/wwww");
        JTextField leavingDate = new JTextField("xx/yy/wwww");

        JButton applyFilters = new JButton("Apply");
        JLabel invalidDates = new JLabel("Invalid Dates");

        invalidDates.setForeground(Color.red);

        applyFilters.setBounds(50,650,100,30);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int rowIndex = table.getSelectedRow();
                String arrivingTxt = arrivingDate.getText();
                String leavingTxt = leavingDate.getText();
                String[] arriving = arrivingTxt.split("/");
                String[] leaving = leavingTxt.split("/");
                int arrivingDay ;
                int arrivingMonth ;
                int arrivingYear ;
                int leavingDay;
                int leavingMonth ;
                int leavingYear ;
                if(arriving.length !=3 && leaving.length != 3){
                    invalidDates.setVisible(true);
                    invalidDates.setBounds(50,700,100,50);
                    return;
                }
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
                if(!dateOption.checkingTheDates()){
                    invalidDates.setVisible(true);
                    invalidDates.setBounds(50,700,100,50);
                    return;
                }
                invalidDates.setVisible(false);
                if(rowIndex != -1){
                    String[] options = {"Yes","No"};
                    int option = JOptionPane.showOptionDialog(null, "Are you sure you want to book it?", "Confirm Reservation", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    if (option != -1) {
                        if(options[option].equals("Yes")){
                            String accommodationName = table.getValueAt(rowIndex,0).toString();
                            String location = table.getValueAt(rowIndex,4).toString();
                            String roomNumberTxt = table.getValueAt(rowIndex,5).toString();
                            int roomNumber = Integer.parseInt(roomNumberTxt);
                            roomNumber--;
                            Accommodation accommodation = listOfAccommodations.getSpecificAccommodation(accommodationName,location);
                            if(accommodation != null){
                                Reservation reservation = new Reservation(accommodation,customer,dateOption,roomNumber);
                                accommodation.getSpecificRoom(roomNumber).addReservedDates(dateOption);
                                listOfReservations.addNewReservation(reservation);
                                listOfAccommodations.updateAccommodationList();
                                model.removeRow(rowIndex);
                            }

                        }
                    }
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

        applyFilters.addActionListener(e -> {
            if(e.getSource()==applyFilters){
                String arrivingTxt = arrivingDate.getText();
                String leavingTxt = leavingDate.getText();
                String[] arriving = arrivingTxt.split("/");
                String[] leaving = leavingTxt.split("/");
                int arrivingDay ;
                int arrivingMonth ;
                int arrivingYear ;
                int leavingDay;
                int leavingMonth ;
                int leavingYear ;
                if(arriving.length !=3 && leaving.length != 3){
                    invalidDates.setVisible(true);
                    invalidDates.setBounds(50,700,100,50);
                    return;
                }
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
                if(!dateOption.checkingTheDates()){
                    invalidDates.setVisible(true);
                    invalidDates.setBounds(50,700,100,50);
                    return;
                }
                invalidDates.setVisible(false);
                int maxPrice = price.getValue();
                int capacity = people.getValue();
                String countryTxt = country.getText().toUpperCase(Locale.ROOT);
                String cityTxt = city.getText().toUpperCase(Locale.ROOT);
                boolean poolOption,restaurantOption,wifiOption,parkingOption,petsOption;
                poolOption = restaurantOption = wifiOption = parkingOption = petsOption = false;
                if(pool.isSelected())  poolOption = true;
                if(parking.isSelected())  parkingOption = true;
                if(wifi.isSelected())  wifiOption = true;
                if(restaurant.isSelected())  restaurantOption = true;
                if(pets.isSelected())  petsOption = true;
                updateTable(model,parkingOption,wifiOption,poolOption,restaurantOption,petsOption,capacity,maxPrice,countryTxt,cityTxt,listOfAccommodations,dateOption);
            }
        });

        add(invalidDates);
        add(arrivingDate);
        add(leavingDate);
        add(scrollPane);

        add(applyFilters);

        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(true);
    }

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

class FiltersPanel extends JPanel{

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth()*0.2);
    private final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());

    private JLabel title = new JLabel("FILTERS");

    private JTextField country = new JTextField("Greece");
    private JTextField city = new JTextField("Kastoria");
    private JTextField arrivingDate = new JTextField("xx/yy/wwww");
    private JTextField leavingDate = new JTextField("xx/yy/wwww");

    private JSlider people = new JSlider(1,13,1);
    private JSlider price = new JSlider(0,1000,100);

    private JCheckBox pool = new JCheckBox("Pool", false);
    private JCheckBox restaurant = new JCheckBox("Restaurant", false);
    private JCheckBox wifi = new JCheckBox("Wifi", false);
    private JCheckBox parking = new JCheckBox("Parking",false);
    private JCheckBox pets = new JCheckBox("Pets Allowed",false);

    private JButton applyFilters = new JButton("Apply");
    private JButton book = new JButton("Book");

    private JLabel invalidDates = new JLabel("Invalid Dates");

    private final int TITLE_HEIGHT = 40;

    private final int FIELDS_WIDTH = (int) (PANEL_WIDTH*0.6);
    private final int FIELDS_HEIGHT = 30;

    private final int SLIDERS_WIDTH = (int) (PANEL_WIDTH*0.6);
    private final int SLIDERS_HEIGHT = 40;

    private final int CHECKBOXES_WIDTH = (int) (PANEL_WIDTH*0.6);
    private final int CHECKBOXES_HEIGHT = 20;

    private final int BTN_WIDTH = PANEL_WIDTH/2;
    private final int BTN_HEIGHT = 30;

    private final int MARGIN_TITLE_FROM_TOP = 10;
    private final int MARGIN_FIELDS_FROM_TITLE = 20;
    private final int MARGIN_BETWEEN_FIELDS = 20;
    private final int MARGIN_SLIDERS_FROM_FIELDS = 50;
    private final int MARGIN_BETWEEN_SLIDERS = 20;
    private final int MARGIN_CHECKBOXES_FROM_SLIDERS = 30;
    private final int MARGIN_BETWEEN_CHECKBOXES = 20;

    private final Color CUSTOMIZED_COLOR = Color.decode("#3B5998");

    public FiltersPanel(MainFrame mainFrame){
        this.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        int pixelCounter = MARGIN_TITLE_FROM_TOP;

        title.setOpaque(false);
        title.setBounds(0, pixelCounter, PANEL_WIDTH, TITLE_HEIGHT);
        title.setForeground(CUSTOMIZED_COLOR);
        title.setFocusable(false);
        title.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, TITLE_HEIGHT-10));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        pixelCounter += TITLE_HEIGHT + MARGIN_FIELDS_FROM_TITLE;

        country.setBounds((PANEL_WIDTH-FIELDS_WIDTH)/2, pixelCounter, FIELDS_WIDTH, FIELDS_HEIGHT);

        pixelCounter += FIELDS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        city.setBounds((PANEL_WIDTH-FIELDS_WIDTH)/2, pixelCounter, FIELDS_WIDTH, FIELDS_HEIGHT);

        pixelCounter += FIELDS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        arrivingDate.setBounds((PANEL_WIDTH-FIELDS_WIDTH)/2, pixelCounter, FIELDS_WIDTH, FIELDS_HEIGHT);

        pixelCounter += FIELDS_HEIGHT + MARGIN_BETWEEN_FIELDS;

        leavingDate.setBounds((PANEL_WIDTH-FIELDS_WIDTH)/2, pixelCounter, FIELDS_WIDTH, FIELDS_HEIGHT);

        pixelCounter += FIELDS_HEIGHT + MARGIN_SLIDERS_FROM_FIELDS;

        people.setOpaque(false);
        people.setPaintLabels(true);
        people.setPaintTicks(true);
        people.setPaintTrack(true);
        people.setMinorTickSpacing(1);
        people.setMajorTickSpacing(4);
        people.setFont(new Font("Tahoma", Font.BOLD, 16));
        people.setForeground(CUSTOMIZED_COLOR);
        people.setBounds(0, pixelCounter, PANEL_WIDTH, SLIDERS_HEIGHT);
        people.setAlignmentX(CENTER_ALIGNMENT);

        pixelCounter += SLIDERS_HEIGHT + MARGIN_BETWEEN_SLIDERS;

        price.setOpaque(false);
        price.setFont(new Font("Tahoma", Font.BOLD, 16));
        price.setForeground(CUSTOMIZED_COLOR);
        price.setBounds(0, pixelCounter, PANEL_WIDTH, SLIDERS_HEIGHT);
        price.setPaintLabels(true);
        price.setPaintTicks(true);
        price.setPaintTrack(true);
        price.setMinorTickSpacing(50);
        price.setMajorTickSpacing(500);
        price.setAlignmentX(CENTER_ALIGNMENT);

        pixelCounter += SLIDERS_HEIGHT + MARGIN_CHECKBOXES_FROM_SLIDERS;

        pool.setBounds((PANEL_WIDTH-CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        pool.setText("Pool");
        pool.setForeground(CUSTOMIZED_COLOR);
        pool.setOpaque(false);
        pool.setFont(new Font("Tahoma", Font.BOLD, 16));
        pool.setAlignmentX(CENTER_ALIGNMENT);

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        restaurant.setBounds((PANEL_WIDTH-CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        restaurant.setText("restaurant");
        restaurant.setForeground(CUSTOMIZED_COLOR);
        restaurant.setOpaque(false);
        restaurant.setFont(new Font("Tahoma", Font.BOLD, 16));

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        wifi.setBounds((PANEL_WIDTH-CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        wifi.setText("wifi");
        wifi.setForeground(CUSTOMIZED_COLOR);
        wifi.setOpaque(false);
        wifi.setFont(new Font("Tahoma", Font.BOLD, 16));

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        parking.setBounds((PANEL_WIDTH-CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        parking.setText("parking");
        parking.setForeground(CUSTOMIZED_COLOR);
        parking.setOpaque(false);
        parking.setFont(new Font("Tahoma", Font.BOLD, 16));

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        pets.setBounds((PANEL_WIDTH-CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        pets.setText("pets");
        pets.setForeground(CUSTOMIZED_COLOR);
        pets.setOpaque(false);
        pets.setFont(new Font("Tahoma", Font.BOLD, 16));

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;



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

        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(true);
    }
}