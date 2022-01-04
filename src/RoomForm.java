import javax.swing.*;
import java.awt.*;

public class RoomForm extends JPanel {
    public RoomForm(Accommodation accommodation,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations){
        setSize(1500,800);
        JButton addRoom = new JButton("Add Room");
        JButton finish = new JButton("Finish");
        JLabel error = new JLabel("Invalid Information");
        error.setForeground(Color.red);
        addRoom.setFocusable(false);
        finish.setFocusable(false);
        JTextField capacity = new JTextField("capacity");
        JTextField price = new JTextField("price per night");
        JTextField beds = new JTextField("number of beds");
        JComboBox<String> view;
        String[] viewType = {"Sea","Forest","The city","Landmark"};
        view = new JComboBox<>(viewType);
        capacity.setBounds(100,100,100,30);
        price.setBounds(100,150,100,30);
        beds.setBounds(100,200,100,30);
        view.setBounds(50,50,100,30);
        addRoom.setBounds(300,250,150,30);
        finish.setBounds(300,300,80,30);
        view.setSelectedIndex(0);

        addRoom.addActionListener(e -> {
            String capacityTxt = capacity.getText();
            String bedsTxt = beds.getText();
            String priceTxt = price.getText();
            int selectedViewIndex = view.getSelectedIndex();
            String selectedView = viewType[selectedViewIndex];
            double priceDouble;
            int bedsInt,capacityInt;
            try {
                bedsInt = Integer.parseInt(bedsTxt);
                priceDouble = Double.parseDouble(priceTxt);
                capacityInt = Integer.parseInt(capacityTxt);
            } catch(Exception er){
                error.setBounds(200,400,200,30);
                //er.printStackTrace();
                return;
            }
            if(!(capacityTxt.isEmpty() && bedsTxt.isEmpty() && priceTxt.isEmpty())){
                Room room = new Room(capacityInt,priceDouble,selectedView,bedsInt);
                accommodation.addRoom(room);
                capacity.setText("capacity");
                price.setText("price per night");
                beds.setText("number of beds");
            }

        });

        finish.addActionListener(e -> {
            removeAll();
            repaint();
            listOfAccommodations.addNewAccommodation(accommodation);
            add(new ProviderWindow(accommodation.getProvider(),listOfAccommodations,listOfAccounts,listOfReservations));
        });

        add(error);
        add(finish);
        add(addRoom);
        add(capacity);
        add(price);
        add(beds);
        add(view);
        setOpaque(false);
        setLayout(null);
        setVisible(true);
    }
}
