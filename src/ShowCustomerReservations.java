import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class ShowCustomerReservations extends JPanel {
    public ShowCustomerReservations(Customer customer,AccountsCreated listOfAccounts,AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){
        setSize(1500,800);
        setLayout(null);
        JLabel label = new JLabel("MY RESERVATIONS");
        label.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 30));
        JLabel chooseFilter = new JLabel("Choose a filter");
        chooseFilter.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 15));
        chooseFilter.setForeground(new Color(6, 48, 124));
        chooseFilter.setBounds(195,60,200,20);
        label.setForeground(new Color(6, 48, 124));
        label.setBounds(615,10,500,150);
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

        filter.setBounds(170,100,170,30);
        filter.setFocusable(false);
        filter.setFont(new Font("Sans Sheriff", Font.ITALIC+Font.BOLD, 12));
        filter.setSelectedIndex(0);
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Here");
                int optionFromComboBox = filter.getSelectedIndex();
                updateTable(optionFromComboBox,model,customer,listOfReservations);
            }
        });

        JTable table = new JTable(model);
        table.setOpaque(false);
        setOpaque(false);
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
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    String accommodationName = table.getValueAt(rowIndex,0).toString();
                    String roomNumberTxt = table.getValueAt(rowIndex,5).toString();
                    int roomNumber = Integer.parseInt(roomNumberTxt);
                    String dateTxt = table.getValueAt(rowIndex,10).toString();
                    String[] date = dateTxt.split("/");
                    String[] yearTxt = date[2].split("-");
                    JButton cancel = new JButton("Cancel this Reservation");
                    JLabel label = new JLabel("Reservation Details");
                    JLabel error = new JLabel();
                    JButton back = new JButton("Go Back");
                    cancel.setBounds(50,300,200,30);
                    int day = Integer.parseInt(date[0]);
                    int month = Integer.parseInt(date[1]);
                    int year = Integer.parseInt(yearTxt[0]);
                    int day2 = Integer.parseInt(date[3]);
                    int month2 = Integer.parseInt(date[4]);
                    int year2 = Integer.parseInt(yearTxt[1]);
                    System.out.println(day+" "+month+" "+year);
                    System.out.println(roomNumber);
                    Date dateOfTheReservation = new Date(day,month,year,day2,month2,year2);
                    LocalDate dateNow = LocalDate.now();
                    cancel.setFocusable(false);
                    cancel.addActionListener(e1 -> {
                        Reservation reservation = listOfReservations.getASpecificReservation(customer,accommodationName,dateOfTheReservation,roomNumber);
                        error.setForeground(Color.red);
                        if(reservation.getCancelled()){
                            error.setText("You cannot cancel a cancelled reservation");
                            error.setBounds(100,100,300,50);
                        }
                        else{
                            if(year < dateNow.getYear() || year == dateNow.getYear() && month < dateNow.getMonthValue() || (year == dateNow.getYear() && month == dateNow.getMonthValue() && day<dateNow.getDayOfMonth())){
                                error.setBounds(100,100,300,100);
                                error.setText("You cannot cancel a reservation that is overdue");
                                error.setBounds(100,100,300,50);
                            }
                            else{
                                reservation.setCancelled(true);
                                listOfReservations.updateReservation();
                            }
                        }
                        updateTable(filter.getSelectedIndex(),model,customer,listOfReservations);

                    });

                    back.addActionListener(e1 -> {
                        frame.dispose();
                    });

                    label.setBounds(300,300,600,200);
                    back.setBounds(200,300,100,30);
                    frame.add(back);
                    frame.add(cancel);
                    frame.add(label);
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
        scrollPane.setBounds(170,200,1200,500);
        scrollPane.getViewport().setBackground(Color.WHITE);
        filter.getEditor().getEditorComponent().setBackground(Color.WHITE);
        scrollPane.setVisible(true);
        add(scrollPane);
        add(filter);
        add(chooseFilter);
        add(label);
    }

    public String[][] createTable(Customer customer,ReservationsCreated listOfReservations){
        ArrayList<Reservation> customerReservations = listOfReservations.reservationsOfASpecificCustomer(customer);
        System.out.println(customerReservations.size());
        String[][] data = new String[customerReservations.size()][11];
        int count = 0;
        for(Reservation x: customerReservations){
            int r = x.getRoomNumber();
            //System.out.println(r+" "+x.getAccommodation().getName());
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
