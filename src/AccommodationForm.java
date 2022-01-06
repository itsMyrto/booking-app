import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

public class AccommodationForm extends JPanel {

    private JComboBox<String> typeOfAccommodation ;

    public AccommodationForm(Provider provider,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations){
        setSize(1500, 800);
        setLayout(null);
        JButton continueProcess = new JButton("Continue");
        continueProcess.setBounds(100,150,150,30);
        continueProcess.setFocusable(false);
        JLabel changeTheType = new JLabel("Click to change the type:");
        changeTheType.setBounds(100,70,300,30);
        String[] types = {"Hotel","Apartment","Hostel","Motel"};
        typeOfAccommodation = new JComboBox<>(types);
        typeOfAccommodation.setSelectedIndex(0);
        typeOfAccommodation.setBounds(100,100,150,30);
        typeOfAccommodation.setVisible(true);
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

        JTextField nameOfTheAccommodation = new JTextField("name");
        nameOfTheAccommodation.setBounds(400,10,50,20);
        JTextField country = new JTextField("country");
        country.setBounds(400,60,50,20);
        JTextField city = new JTextField("city");
        city.setBounds(400,110,50,20);
        JTextField street = new JTextField("street");
        street.setBounds(400,160,50,20);
        JTextField streetNumber = new JTextField("number");
        streetNumber.setBounds(400,210,50,20);

        JLabel wifi = new JLabel("Does it have Wifi?");
        wifi.setBounds(400,240,200,50);
        hasWifi.setBounds(400,280,50,30);
        doesNotHaveWifi.setBounds(450,280,50,30);

        JLabel parking = new JLabel("Does it have Parking?");
        parking.setBounds(400,320,200,50);
        hasParking.setBounds(400,380,50,30);
        doesNotHaveParking.setBounds(450,380,50,30);

        JLabel restaurant = new JLabel("Does it have a Restaurant?");
        restaurant.setBounds(400,400,200,50);
        hasRestaurant.setBounds(400,480,50,30);
        doesNotHaveRestaurant.setBounds(450,480,50,30);

        JLabel pets = new JLabel("Are pets allowed?");
        pets.setBounds(400,500,200,50);
        petsAllowed.setBounds(400,580,50,30);
        petsNotAllowed.setBounds(450,580,50,30);

        JLabel pool = new JLabel("Does it have a pool?");
        pool.setBounds(400,600,200,50);
        hasPool.setBounds(400,680,50,30);
        doesNotHavePool.setBounds(450,680,50,30);

        continueProcess.addActionListener(e -> {
            String nameTxt = nameOfTheAccommodation.getText();
            String countryTxt = country.getText().toUpperCase(Locale.ROOT);
            String cityTxt = city.getText().toUpperCase(Locale.ROOT);
            String streetTxt = street.getText().toUpperCase(Locale.ROOT);
            String streetNumberTxt = streetNumber.getText();
            if(nameTxt.isEmpty() || countryTxt.isEmpty() || cityTxt.isEmpty() || streetTxt.isEmpty() || streetNumberTxt.isEmpty()){
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
            repaint();
            add(new RoomForm(accommodation,listOfAccommodations,listOfAccounts,listOfReservations));

        });

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
