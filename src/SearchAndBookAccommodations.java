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

    public SearchAndBookAccommodations(Customer customer,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations){
        LocalDate dateNow = LocalDate.now();
        setSize(1500,800);
        setLayout(null);
        String [] tableHeaders = {"Name","Type","Country","City","Address","Room Number","Price (one night)","Capacity","View"};
        Date date = new Date(dateNow.getDayOfMonth(),dateNow.getMonthValue(),dateNow.getYear(),dateNow.getDayOfMonth()+1,dateNow.getMonthValue(),dateNow.getYear());
        DefaultTableModel model = new DefaultTableModel(createTable(listOfAccommodations.getTheAccommodationList()),tableHeaders){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Color white=new Color(255, 255, 255);
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
        JButton applyFilters = new JButton("Apply");
        JButton book = new JButton("Book");
        JCheckBox wifi = new JCheckBox("Wifi");
        JCheckBox parking = new JCheckBox("Parking",false);
        JCheckBox pets = new JCheckBox("Pets Allowed",false);
        JSlider people = new JSlider(1,13,1);
        JSlider price = new JSlider(0,1000,100);
        JTextField arrivingDate = new JTextField("xx/yy/wwww");
        JTextField leavingDate = new JTextField("xx/yy/wwww");
        JLabel invalidDates = new JLabel("Invalid Dates");
        invalidDates.setForeground(Color.red);
        applyFilters.setBounds(50,650,100,30);
        price.setBounds(20,480,150,80);
        price.setBackground(Color.BLACK);
        people.setBounds(20,550,150,80);
        price.setPaintLabels(true);
        price.setPaintTicks(true);
        price.setPaintTrack(true);
        people.setPaintLabels(true);
        people.setPaintTicks(true);
        people.setPaintTrack(true);
        price.setMinorTickSpacing(50);
        price.setMajorTickSpacing(500);
        people.setMinorTickSpacing(1);
        people.setMajorTickSpacing(4);
        pool.setBounds(50,390,100,30);
        restaurant.setBounds(50,350,100,30);
        wifi.setBounds(50,230,100,30);
        parking.setBounds(50,270,100,30);
        pets.setBounds(50,310,100,30);
        country.setBounds(50,190,100,30);
        city.setBounds(50,150,100,30);
        arrivingDate.setBounds(50,70,100,30);
        leavingDate.setBounds(50,110,100,30);
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
        pool.setOpaque(false);
        people.setOpaque(false);
        price.setOpaque(false);
        restaurant.setOpaque(false);
        wifi.setOpaque(false);
        parking.setOpaque(false);
        pets.setOpaque(false);


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
                int maxPrice = price.getValue();
                int capacity = people.getValue();
                String countryTxt = country.getText().toUpperCase(Locale.ROOT);
                String cityTxt = city.getText().toUpperCase(Locale.ROOT);
                boolean poolOption,restaurantOption,wifiOption,parkingOption,petsOption;
                poolOption = restaurantOption = wifiOption = parkingOption = petsOption = false;
                if(pool.isSelected()){
                    poolOption = true;
                }
                if(parking.isSelected()){
                    parkingOption = true;
                }
                if(wifi.isSelected()){
                    wifiOption = true;
                }
                if(restaurant.isSelected()){
                    restaurantOption = true;
                }
                if(pets.isSelected()){
                    petsOption = true;
                }
                updateTable(model,parkingOption,wifiOption,poolOption,restaurantOption,petsOption,capacity,maxPrice,countryTxt,cityTxt,listOfAccommodations,dateOption);
            }
        });
        add(invalidDates);
        add(arrivingDate);
        add(leavingDate);
        add(applyFilters);
        add(scrollPane);
        add(city);
        add(country);
        add(price);
        add(people);
        add(pool);
        add(restaurant);
        add(wifi);
        add(parking);
        add(pets);
        setOpaque(false);
        setVisible(true);
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
