import javax.swing.*;
import java.awt.*;

public class RoomForm extends JPanel {
    public RoomForm(Accommodation accommodation,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations,MainFrame mainFrame){
        setSize(1500,800);
        JLabel filterLabel = new JLabel("Which view does the room have?");
        JLabel notice = new JLabel("Please fill the below:");
        JLabel note = new JLabel("You can add as many rooms as you want");
        note.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 15));
        filterLabel.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 17));
        filterLabel.setForeground(new Color(0x06307C));
        notice.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 17));
        notice.setForeground(new Color(0x06307C));
        notice.setBounds(260,100,200,30);
        filterLabel.setBounds(230,300,300,30);
        note.setBounds(200,500,500,30);
        JButton addRoom = new JButton("Add Room");
        JButton finish = new JButton("Finish");
        JLabel error = new JLabel("Invalid Information");
        finish.setBackground(new Color(0x06307C));
        finish.setForeground(Color.white);
        error.setForeground(Color.red);
        error.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        addRoom.setFocusable(false);
        finish.setFocusable(false);
        addRoom.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        addRoom.setBackground(new Color(0x06307C));
        addRoom.setForeground(Color.white);
        JTextField capacity = new JTextField("Room Capacity");
        JTextField price = new JTextField("Price For One Night");
        JTextField beds = new JTextField("Number of Beds");
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
        capacity.setBounds(250,150,200,25);
        price.setBounds(250,200,200,25);
        beds.setBounds(250,250,200,25);
        view.setBounds(250,350,200,25);
        addRoom.setBounds(250,450,200,25);
        finish.setBounds(250,650,200,25);
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
                error.setBounds(300,550,200,30);
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
            add(new ProviderWindow(accommodation.getProvider(),listOfAccommodations,listOfAccounts,listOfReservations,mainFrame));
        });
        add(note);
        add(error);
        add(finish);
        add(addRoom);
        add(capacity);
        add(price);
        add(beds);
        add(view);
        add(notice);
        add(filterLabel);
        setOpaque(false);
        setLayout(null);
        setVisible(true);
    }
}
