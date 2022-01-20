import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class contains the panel where the admins read messages from other admins & send messages to all users.
 * On the left side there is a text field where the admin can write the message. There is also a combo box with  all the emails of the users,where
 * the admin can choose one to send the message.On the right side there is a small JTable that contains all the messages that other admins have sent.
 */
public class ShowMessagesAdmin extends JPanel {
    /**
     * This is the constructor that creates the inbox panel
     * @param admin The admin who is logged in
     * @param listOfAccounts A list with all the accounts of the app
     * @param listOfAccommodations A list with all the accommodations of the app
     * @param listOfReservations A list with all the reservations of the app
     * @param mainFrame The frame of the app
     */
    public ShowMessagesAdmin(Admin admin, AccountsCreated listOfAccounts, AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());
        int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth());

        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        mainFrame.setVisible(false);

        int pxCounter = 0;
        int counter = 0;

        ArrayList<Message> messages = admin.getMessages();

        JLabel header = new JLabel("INBOX");
        header.setBounds(0,pxCounter,PANEL_WIDTH,30);
        header.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,28));
        header.setForeground(new Color(6, 48, 124));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        add(header);

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(0,pxCounter,50,20);
        returnButton.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,14));
        returnButton.setForeground(new Color(191, 0, 255));
        returnButton.setBackground(Color.white);
        returnButton.setFocusable(false);
        returnButton.setBorder(null);
        add(returnButton);
        returnButton.addActionListener(e -> {
            mainFrame.remove(this);
            mainFrame.getContentPane().repaint();
            mainFrame.addInitialImage();
            mainFrame.getContentPane().add(new AdminWindow(admin, listOfAccounts, listOfAccommodations,listOfReservations,mainFrame));
            mainFrame.getContentPane().repaint();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));
        Color CUSTOMIZED_COLOR = Color.decode("#3B5998");
        int PANE_WIDTH = (int) (0.8 * PANEL_WIDTH);
        for(Message x : messages){
            String MARGIN_MSG_FROM_PANE_EDGE = "    ";
            String msg = MARGIN_MSG_FROM_PANE_EDGE + x.getMessage() + "   From: "+x.getSender().getEmail();
            JLabel temp = new JLabel(msg);
            if(counter % 2 == 0) {
                temp.setOpaque(true);
                temp.setBackground(Color.decode("#CDCDCD"));
            }
            else temp.setBackground(Color.WHITE);
            temp.setForeground(CUSTOMIZED_COLOR);
            String FONT = "Tahoma";
            temp.setFont(new Font(FONT, Font.BOLD, 18));
            int MSG_LABEL_HEIGHT = 40;
            temp.setBounds(0, pxCounter, PANE_WIDTH, MSG_LABEL_HEIGHT);
            temp.setHorizontalAlignment(SwingConstants.LEFT);
            temp.setVerticalAlignment(SwingConstants.CENTER);

            pxCounter += MSG_LABEL_HEIGHT;
            counter++;

            panel.add(temp);
        }

        int PANE_HEIGHT = (int) (0.7 * PANEL_HEIGHT);
        panel.setSize(new Dimension(PANE_WIDTH, PANE_HEIGHT));

        JTextField textField = new JTextField("Write your message here");
        textField.setBounds(PANEL_WIDTH/10,(PANEL_HEIGHT/3),300,200);

        JButton send = new JButton("Send");
        send.setBackground(Color.GREEN);
        send.setForeground(Color.WHITE);
        send.setBounds(PANEL_WIDTH/6,(int)(PANEL_HEIGHT/1.6),100,40);
        send.setFocusable(false);

        String[] allEmails = listOfAccounts.getAllUserEmails();
        JComboBox<String > users = new JComboBox<>(allEmails) {
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

        send.addActionListener(e -> {
            int option = users.getSelectedIndex();
            String message = textField.getText();
            Message msg = new Message(message,admin);
            listOfAccounts.sendMessage(msg,allEmails[option]);
            textField.setText("Write your message here");
        });

        users.setBounds(PANEL_WIDTH/10,PANEL_HEIGHT/4,200,30);


        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        int MARGIN_PANE_FROM_LEFT_EDGE = 2*(PANEL_WIDTH - PANE_WIDTH) ;
        int MARGIN_PANE_FROM_TOP = (PANEL_HEIGHT - PANE_HEIGHT) ;
        scrollPane.setBounds(MARGIN_PANE_FROM_LEFT_EDGE, MARGIN_PANE_FROM_TOP, (int)(PANE_WIDTH/1.5), PANE_HEIGHT/2);
        scrollPane.setForeground(CUSTOMIZED_COLOR);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());


        this.setOpaque(false);
        this.setLayout(null);
        this.add(scrollPane);
        this.add(textField);
        this.add(send);
        this.add(users);
        panel.setVisible(true);
        this.setVisible(true);
        mainFrame.setVisible(true);

    }
}
