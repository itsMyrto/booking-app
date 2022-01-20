import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * This class contains the panel that show up when a provider wants to add a new Accommodation.
 * It contains some fields where the provider has to type important information for the accommodation (such as the name,the type etc.)
 * and also some options about the facilities that the accommodations has (such as pool,wifi,parking etc.)
 */
public class AccommodationForm extends JPanel {

    private JComboBox<String> typeOfAccommodation ;

    /**
     * This is the constructor where the window is created
     * @param provider The connected provider who wants to add a new accommodation
     * @param listOfAccommodations The list with all the accommodations of the program
     * @param listOfAccounts The list with all the accounts of the program
     * @param listOfReservations The list with all the reservations of the program
     * @param mainFrame The frame of the program
     */
    public AccommodationForm(Provider provider,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations,MainFrame mainFrame){

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());
        int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth());
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        int xAxis = (int) (PANEL_WIDTH/3.8);
        setLayout(null);

        JButton continueProcess = new JButton("Continue");
        continueProcess.setBounds(PANEL_WIDTH/13, (int) (PANEL_HEIGHT/1.5),100,30);
        continueProcess.setForeground(Color.WHITE);
        continueProcess.setBackground(new Color(6, 48, 124));
        continueProcess.setFont(new Font("sans serif",Font.ITALIC,15));
        continueProcess.setFocusable(false);

        JLabel error = new JLabel("Please Enter Valid Information");
        error.setForeground(Color.red);
        error.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,17));


        JLabel changeTheType = new JLabel("What kind of accommodation is it?");
        changeTheType.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,15));
        changeTheType.setBounds(PANEL_WIDTH/22,PANEL_HEIGHT/12,300,30);
        changeTheType.setForeground(new Color(0x06307C));

        JLabel information = new JLabel("Information:");
        information.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,18));
        information.setBounds(PANEL_WIDTH/15,PANEL_HEIGHT/4,200,30);
        information.setForeground(new Color(0x06307C));

        JLabel facilities = new JLabel("Facilities:");
        facilities.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,18));
        facilities.setForeground(new Color(0x06307C));
        facilities.setBounds(xAxis,PANEL_HEIGHT/8,200,30);

        String[] types = {"Hotel","Apartment","Hostel","Motel"};
        typeOfAccommodation = new JComboBox<>(types){
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

        typeOfAccommodation.setSelectedIndex(0);
        typeOfAccommodation.setBounds(PANEL_WIDTH/18,PANEL_HEIGHT/6,150,25);
        typeOfAccommodation.setVisible(true);
        typeOfAccommodation.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 12));
        typeOfAccommodation.getEditor().getEditorComponent().setBackground(Color.WHITE);

        setOpaque(false);

        JRadioButton hasWifi = new JRadioButton("Yes");
        hasWifi.setOpaque(false);

        JRadioButton doesNotHaveWifi = new JRadioButton("No");
        doesNotHaveWifi.setOpaque(false);
        doesNotHaveWifi.setSelected(true);

        JRadioButton hasParking = new JRadioButton("Yes");
        hasParking.setOpaque(false);

        JRadioButton doesNotHaveParking = new JRadioButton("No");
        doesNotHaveParking.setOpaque(false);
        doesNotHaveParking.setSelected(true);

        JRadioButton hasRestaurant = new JRadioButton("Yes");
        hasRestaurant.setOpaque(false);

        JRadioButton doesNotHaveRestaurant = new JRadioButton("No");
        doesNotHaveRestaurant.setOpaque(false);
        doesNotHaveRestaurant.setSelected(true);

        JRadioButton petsAllowed = new JRadioButton("Yes");
        petsAllowed.setOpaque(false);

        JRadioButton petsNotAllowed = new JRadioButton("No");
        petsNotAllowed.setOpaque(false);
        petsNotAllowed.setSelected(true);

        JRadioButton hasPool = new JRadioButton("Yes");
        hasPool.setOpaque(false);

        JRadioButton doesNotHavePool = new JRadioButton("No");
        doesNotHavePool.setOpaque(false);
        doesNotHavePool.setSelected(true);

        ButtonGroup wifiGroup = new ButtonGroup();
        wifiGroup.add(hasWifi);
        wifiGroup.add(doesNotHaveWifi);

        ButtonGroup parkingGroup = new ButtonGroup();
        parkingGroup.add(hasParking);
        parkingGroup.add(doesNotHaveParking);

        ButtonGroup restaurantGroup = new ButtonGroup();
        restaurantGroup.add(hasRestaurant);
        restaurantGroup.add(doesNotHaveRestaurant);

        ButtonGroup poolGroup = new ButtonGroup();
        poolGroup.add(hasPool);
        poolGroup.add(doesNotHavePool);

        ButtonGroup petsGroup = new ButtonGroup();
        petsGroup.add(petsAllowed);
        petsGroup.add(petsNotAllowed);

        JTextField nameOfTheAccommodation = new JTextField("Name of the Accommodation");
        nameOfTheAccommodation.setBounds(PANEL_WIDTH/22,PANEL_HEIGHT/3,200,30);

        int pixels = PANEL_HEIGHT/3;
        pixels += 50;

        JTextField country = new JTextField("Country");
        country.setBounds(PANEL_WIDTH/22,pixels,200,30);

        pixels+=50;
        JTextField city = new JTextField("City");
        city.setBounds(PANEL_WIDTH/22,pixels,200,30);

        pixels+=50;
        JTextField street = new JTextField("Street");
        street.setBounds(PANEL_WIDTH/22,pixels,200,30);

        pixels+=50;
        JTextField streetNumber = new JTextField("Street Number");
        streetNumber.setBounds(PANEL_WIDTH/22,pixels,200,30);

        pixels = PANEL_HEIGHT/5;

        JLabel wifi = new JLabel("Does it have Wifi?");
        wifi.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 12));
        wifi.setForeground(new Color(0x06307C));
        wifi.setBounds(xAxis,pixels,200,50);
        pixels+=50;
        hasWifi.setBounds(xAxis,pixels,50,30);
        doesNotHaveWifi.setBounds(xAxis+50,pixels,50,30);

        JLabel parking = new JLabel("Does it have Parking?");
        parking.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 12));
        parking.setForeground(new Color(0x06307C));
        pixels+=50;
        parking.setBounds(xAxis,pixels,200,50);
        pixels+=50;
        hasParking.setBounds(xAxis,pixels,50,30);
        doesNotHaveParking.setBounds(xAxis+50,pixels,50,30);

        pixels+=50;
        JLabel restaurant = new JLabel("Does it have a Restaurant?");
        restaurant.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 12));
        restaurant.setForeground(new Color(0x06307C));
        restaurant.setBounds(xAxis,pixels,200,50);
        pixels+=50;
        hasRestaurant.setBounds(xAxis,pixels,50,30);
        doesNotHaveRestaurant.setBounds(xAxis+50,pixels,50,30);

        JLabel pets = new JLabel("Are pets allowed?");
        pets.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 12));
        pets.setForeground(new Color(0x06307C));
        pixels+=50;
        pets.setBounds(xAxis,pixels,200,50);
        pixels+=50;
        petsAllowed.setBounds(xAxis,pixels,50,30);
        petsNotAllowed.setBounds(xAxis+50,pixels,50,30);

        pixels+=50;
        JLabel pool = new JLabel("Does it have a pool?");
        pool.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 12));
        pool.setForeground(new Color(0x06307C));
        pool.setBounds(xAxis,pixels,200,50);
        pixels+=50;
        hasPool.setBounds(xAxis,pixels,50,30);
        doesNotHavePool.setBounds(xAxis+50,pixels,50,30);

        continueProcess.addActionListener(e -> {
            String nameTxt = nameOfTheAccommodation.getText();
            String countryTxt = country.getText().toUpperCase(Locale.ROOT);
            String cityTxt = city.getText().toUpperCase(Locale.ROOT);
            String streetTxt = street.getText().toUpperCase(Locale.ROOT);
            String streetNumberTxt = streetNumber.getText();
            if(nameTxt.isEmpty() || countryTxt.isEmpty() || cityTxt.isEmpty() || streetTxt.isEmpty() || streetNumberTxt.isEmpty()){
                error.setBounds(50,550,300,20);
                return;
            }
            int selectedTypeIndex = typeOfAccommodation.getSelectedIndex();
            String selectedTypeTxt = types[selectedTypeIndex];
            boolean poolOption = false,restaurantOption = false,parkingOption = false,wifiOption = false,petsOption = false;
            if(hasPool.isSelected()){
                poolOption=true;
            }
            if(hasRestaurant.isSelected()){
                restaurantOption = true;
            }
            if(hasParking.isSelected()){
                parkingOption = true;
            }
            if(petsNotAllowed.isSelected()){
                petsOption = true;
            }
            if(hasWifi.isSelected()){
                wifiOption = true;
            }
            Location location = new Location(countryTxt,cityTxt,streetTxt,streetNumberTxt);
            Accommodation accommodation = new Accommodation(provider,nameTxt,selectedTypeTxt,wifiOption,parkingOption,poolOption,restaurantOption,petsOption,location);
            removeAll();
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new RoomForm(accommodation, listOfAccommodations, listOfAccounts,listOfReservations, mainFrame));
            mainFrame.getContentPane().repaint();
        });
        add(error);
        add(facilities);
        add(information);
        add(continueProcess);
        add(changeTheType);
        add(typeOfAccommodation);
        add(wifi);
        add(hasWifi);
        add(hasParking);
        add(doesNotHaveParking);
        add(doesNotHaveWifi);
        add(hasPool);
        add(doesNotHavePool);
        add(petsNotAllowed);
        add(petsAllowed);
        add(hasRestaurant);
        add(doesNotHaveRestaurant);
        add(parking);
        add(restaurant);
        add(pool);
        add(pets);
        add(nameOfTheAccommodation);
        add(country);
        add(city);
        add(street);
        add(streetNumber);
        setVisible(true);
    }


}

/**
 *             if(accommodation.getName().equals("Calma")){
 *                 accommodation.setImage("src/calma.jpg");
 *             }
 *             else if(accommodation.getName().equals("Chloe")){
 *                 accommodation.setImage("src/chloe.jpg");
 *             }
 *             else if(accommodation.getName().equals("Doltso")){
 *                 accommodation.setImage("src/doltso.jpg");
 *             }
 *             else if(accommodation.getName().equals("Esperos Palace")){
 *                 accommodation.setImage("src/esperros.jpg");
 *             }
 *             else if(accommodation.getName().equals("Limneon")){
 *                 accommodation.setImage("src/limneon.jpg");
 *             }
 *             else if(accommodation.getName().equals("Archontiko")){
 *                 accommodation.setImage("src/luxuryhotel.jpg");
 *             }
 */

