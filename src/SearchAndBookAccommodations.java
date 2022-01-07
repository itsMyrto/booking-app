import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Locale;

public class SearchAndBookAccommodations extends JPanel {

    private FiltersPanel filtersPanel ;

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth() * 0.8);
    private final int PANEL_HEIGHT = (int) env.getMaximumWindowBounds().getHeight();

    private final int TABLE_WIDTH = (int) (0.9*PANEL_WIDTH);
    private final int TABLE_HEIGHT = (int) (0.7*PANEL_HEIGHT);

    private final int MARGIN_BETWEEN_TABLE_AND_EDGES = (PANEL_WIDTH-TABLE_WIDTH)/2;
    private final int MARGIN_TABLE_FROM_TOP = (PANEL_HEIGHT-TABLE_HEIGHT)/2;

    private final int TABLE_CELLS_HEIGHT = 30;

    private final Color TABLE_CELLS_COLOR = Color.decode("#3B5998");

    public SearchAndBookAccommodations(Customer customer,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){
        LocalDate dateNow = LocalDate.now();

        filtersPanel = new FiltersPanel(mainFrame);

        this.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBounds(filtersPanel.PANEL_WIDTH, 0, PANEL_WIDTH, PANEL_HEIGHT);

        String [] tableHeaders = {"Name","Type","Country","City","Address","Room Number","Price (one night)","Capacity","View"};
        Date date = new Date(dateNow.getDayOfMonth(),dateNow.getMonthValue(),dateNow.getYear(),dateNow.getDayOfMonth()+1,dateNow.getMonthValue(),dateNow.getYear());
        DefaultTableModel model = new DefaultTableModel(createTable(listOfAccommodations.getTheAccommodationList()),tableHeaders){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setOpaque(false);
        table.setFocusable(false);
        table.setRowSelectionAllowed(true);
        table.setBackground(TABLE_CELLS_COLOR);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.setRowHeight(TABLE_CELLS_HEIGHT);

        // Align cells in the middle
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0; i<table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JLabel label2 = new JLabel("Please fill the form if you want to book an accommodation");
        label2.setFont(new Font("sans serif",Font.BOLD+Font.ITALIC,14));
        label2.setForeground(Color.red);
        label2.setBounds(380,50,400,20);
        add(label2);
        JScrollPane scrollPane = new JScrollPane(table);
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
                    frame.setSize(500,500);
                    frame.setResizable(false);
                    frame.getContentPane().setBackground(Color.white);
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    String[] options = {"Yes","No"};
                    JLabel label = new JLabel("Are you sure you want to book this?");
                    label.setBounds(90,50,400,30);
                    label.setForeground(new Color(0x06307C));
                    label.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,18));
                    JButton yes = new JButton("Yes");
                    yes.setBackground(new Color(0x06307C));
                    yes.setForeground(Color.WHITE);
                    JButton no = new JButton("No");
                    no.setBackground(new Color(0x06307C));
                    no.setForeground(Color.WHITE);
                    yes.setFocusable(false);
                    no.setFocusable(false);
                    yes.setBounds(100,300,100,30);
                    no.setBounds(300,300,100,30);
                    frame.add(label);
                    frame.add(yes);
                    frame.add(no);
                    yes.addActionListener(e1 -> {
                        String accommodationName = table.getValueAt(rowIndex,0).toString();
                        String location = table.getValueAt(rowIndex,4).toString();
                        String roomNumberTxt = table.getValueAt(rowIndex,5).toString();
                        int roomNumber = Integer.parseInt(roomNumberTxt);
                        roomNumber--;
                        Accommodation accommodation = listOfAccommodations.getSpecificAccommodation(accommodationName,location);
                        if(accommodation != null){
                            System.out.println(accommodation.getNumberOfRooms()+" "+roomNumber);
                            Reservation reservation = new Reservation(accommodation,customer,dateOption,roomNumber);
                            accommodation.getSpecificRoom(roomNumber).addReservedDates(dateOption);
                            listOfReservations.addNewReservation(reservation);
                            listOfAccommodations.updateAccommodationList();
                            model.removeRow(rowIndex);
                            frame.dispose();
                        }
                    });

                    no.addActionListener(e1 -> {
                        frame.dispose();
                    });

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
                if(!dateOption.checkingTheDates()) filtersPanel.invalidDates.setVisible(true);

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
    public final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth()*0.2);
    public final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());

    private JLabel title = new JLabel("FILTERS");

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

    private final int TITLE_HEIGHT = 40;

    private final int FIELDS_WIDTH = (int) (PANEL_WIDTH*0.6);
    private final int FIELDS_HEIGHT = 20;

    private final int SLIDERS_WIDTH = (int) (PANEL_WIDTH*0.6);
    private final int SLIDERS_HEIGHT = 40;

    private final int CHECKBOXES_WIDTH = (int) (PANEL_WIDTH*0.6);
    private final int CHECKBOXES_HEIGHT = 20;

    private final int BTN_WIDTH = PANEL_WIDTH/2;
    private final int BTN_HEIGHT = 30;

    private final int MARGIN_TITLE_FROM_TOP = 10;
    private final int MARGIN_FIELDS_FROM_TITLE = 20;
    private final int MARGIN_BETWEEN_FIELDS = 20;
    private final int MARGIN_SLIDERS_FROM_FIELDS = 40;
    private final int MARGIN_BETWEEN_SLIDERS = 20;
    private final int MARGIN_CHECKBOXES_FROM_SLIDERS = 20;
    private final int MARGIN_BETWEEN_CHECKBOXES = 20;
    private final int MARGIN_BTN_FROM_CHECKBOXES = 60;

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
        people.setBounds((PANEL_WIDTH-SLIDERS_WIDTH)/2, pixelCounter, SLIDERS_WIDTH, SLIDERS_HEIGHT);
        people.setFocusable(false);
        people.setAlignmentX(CENTER_ALIGNMENT);

        pixelCounter += SLIDERS_HEIGHT + MARGIN_BETWEEN_SLIDERS;

        price.setOpaque(false);
        price.setFont(new Font("Tahoma", Font.BOLD, 16));
        price.setForeground(CUSTOMIZED_COLOR);
        price.setBounds((PANEL_WIDTH-SLIDERS_WIDTH)/2, pixelCounter, SLIDERS_WIDTH, SLIDERS_HEIGHT);
        price.setPaintLabels(true);
        price.setPaintTicks(true);
        price.setFocusable(false);
        price.setPaintTrack(true);
        price.setMinorTickSpacing(50);
        price.setMajorTickSpacing(500);
        price.setAlignmentX(CENTER_ALIGNMENT);

        pixelCounter += SLIDERS_HEIGHT + MARGIN_CHECKBOXES_FROM_SLIDERS;

        pool.setBounds((PANEL_WIDTH-CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        pool.setText("Pool");
        pool.setForeground(CUSTOMIZED_COLOR);
        pool.setOpaque(false);
        pool.setFocusable(false);
        pool.setFont(new Font("Tahoma", Font.BOLD, 16));
        pool.setAlignmentX(CENTER_ALIGNMENT);

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        restaurant.setBounds((PANEL_WIDTH-CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        restaurant.setText("Restaurant");
        restaurant.setForeground(CUSTOMIZED_COLOR);
        restaurant.setOpaque(false);
        restaurant.setFocusable(false);
        restaurant.setFont(new Font("Tahoma", Font.BOLD, 16));

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        wifi.setBounds((PANEL_WIDTH-CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        wifi.setText("Wifi");
        wifi.setForeground(CUSTOMIZED_COLOR);
        wifi.setOpaque(false);
        wifi.setFocusable(false);
        wifi.setFont(new Font("Tahoma", Font.BOLD, 16));

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        parking.setBounds((PANEL_WIDTH-CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        parking.setText("Parking");
        parking.setForeground(CUSTOMIZED_COLOR);
        parking.setOpaque(false);
        parking.setFocusable(false);
        parking.setFont(new Font("Tahoma", Font.BOLD, 16));

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BETWEEN_CHECKBOXES;

        pets.setBounds((PANEL_WIDTH-CHECKBOXES_WIDTH)/2, pixelCounter, CHECKBOXES_WIDTH, SLIDERS_HEIGHT);
        pets.setText("Pets");
        pets.setForeground(CUSTOMIZED_COLOR);
        pets.setFocusable(false);
        pets.setOpaque(false);
        pets.setFont(new Font("Tahoma", Font.BOLD, 16));

        pixelCounter += CHECKBOXES_HEIGHT + MARGIN_BTN_FROM_CHECKBOXES;

        applyFilters.setBounds((PANEL_WIDTH-BTN_WIDTH)/2, pixelCounter, BTN_WIDTH, BTN_HEIGHT);
        applyFilters.setFocusable(false);
        applyFilters.setForeground(Color.WHITE);
        applyFilters.setFont(new Font("Tahoma", Font.BOLD, 16));
        applyFilters.setBackground(CUSTOMIZED_COLOR);

        pixelCounter -= MARGIN_BTN_FROM_CHECKBOXES/2;

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
