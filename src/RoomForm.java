import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class contains the panel that shows a form where a provider can add as many rooms as he/she wants for the new accommodation.
 * For each room is necessary to fill some fields like price,capacity,number of beds (4 people - 2 double beds or 4 single beds) & the view of the room
 */
public class RoomForm extends JPanel {
    public RoomForm(Accommodation accommodation,AccommodationsCreated listOfAccommodations,AccountsCreated listOfAccounts,ReservationsCreated listOfReservations,MainFrame mainFrame){

        setSize(1500,800);

        AtomicBoolean roomsAdded = new AtomicBoolean(false);

        JLabel filterLabel = new JLabel("Which view does the room have?");
        filterLabel.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 17));
        filterLabel.setForeground(new Color(0x06307C));
        filterLabel.setBounds(230,300,300,30);

        JLabel notice = new JLabel("Please fill the below:");
        notice.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 17));
        notice.setForeground(new Color(0x06307C));
        notice.setBounds(260,100,200,30);

        JLabel note = new JLabel("You can add as many rooms as you want");
        note.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 15));
        note.setBounds(200,500,500,30);


        JButton addRoom = new JButton("Add Room");
        addRoom.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        addRoom.setBackground(new Color(0x06307C));
        addRoom.setForeground(Color.white);
        addRoom.setFocusable(false);
        addRoom.setBounds(250,450,200,25);

        JButton finish = new JButton("Finish");
        finish.setBackground(new Color(0x06307C));
        finish.setForeground(Color.white);
        finish.setFocusable(false);
        finish.setBounds(250,650,200,25);

        JLabel error = new JLabel("Invalid Information");
        error.setForeground(Color.red);
        error.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));


        JTextField capacity = new JTextField("Room Capacity");
        capacity.setBounds(250,150,200,25);

        JTextField price = new JTextField("Price For One Night");
        price.setBounds(250,200,200,25);

        JTextField beds = new JTextField("Number of Beds");
        beds.setBounds(250,250,200,25);

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
        view.setBounds(250,350,200,25);
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
                error.setText("Invalid Information");
                error.setBounds(300,550,200,30);
                return;
            }
            if(!(capacityTxt.isEmpty() && bedsTxt.isEmpty() && priceTxt.isEmpty())){
                Room room = new Room(capacityInt,priceDouble,selectedView,bedsInt);
                roomsAdded.set(true);
                System.out.println("Room Added");
                accommodation.addRoom(room);
                capacity.setText("Room Capacity");
                price.setText("Price for one Night");
                beds.setText("Number of Beds");
            }

        });

        finish.addActionListener(e -> {
            if(!roomsAdded.get()){
                error.setText("Please add a room");
                error.setBounds(300,550,200,30);
                return;
            }
            listOfAccommodations.addNewAccommodation(accommodation);
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.getContentPane().add(new ProviderWindow(accommodation.getProvider(),listOfAccommodations,listOfAccounts,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();


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
