import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShowMessagesAdmin extends JPanel {

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth());
    private final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());

    private final int PANE_WIDTH = (int) (0.8*PANEL_WIDTH);
    private final int PANE_HEIGHT = (int) (0.7*PANEL_HEIGHT);

    private final int MSG_LABEL_HEIGHT = 40;
    private final String MARGIN_MSG_FROM_PANE_EDGE = "    ";
//    private final int MSG_LABEL_WIDTH = PANE_WIDTH-MARGIN_MSG_FROM_PANE_EDGE;

    private final int MARGIN_PANE_FROM_LEFT_EDGE = (PANEL_WIDTH-PANE_WIDTH)/2;
    private final int MARGIN_PANE_FROM_TOP = (PANEL_HEIGHT-PANE_HEIGHT)/2;

    private final String FONT = "Tahoma";
    private final Color CUSTOMIZED_COLOR = Color.decode("#3B5998");
    public ShowMessagesAdmin(Admin admin, AccountsCreated listOfAccounts, AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){

        this.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        mainFrame.setVisible(false);

        int pxCounter = 0;
        int counter = 0;

        ArrayList<Message> messages = admin.getMessages();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));
        for(Message x : messages){
            String msg = MARGIN_MSG_FROM_PANE_EDGE + x.getMessage() + "   From: "+x.getSender().getEmail();
            JLabel temp = new JLabel(msg);
            if(counter % 2 == 0) {
                temp.setOpaque(true);
                temp.setBackground(Color.decode("#CDCDCD"));
            }
            else temp.setBackground(Color.WHITE);
            temp.setForeground(CUSTOMIZED_COLOR);
            temp.setFont(new Font(FONT, Font.BOLD, 18));
            temp.setBounds(0, pxCounter, PANE_WIDTH, MSG_LABEL_HEIGHT);
            temp.setHorizontalAlignment(SwingConstants.LEFT);
            temp.setVerticalAlignment(SwingConstants.CENTER);

            pxCounter += MSG_LABEL_HEIGHT;
            counter++;

            panel.add(temp);
        }

        panel.setSize(new Dimension(PANE_WIDTH,PANE_HEIGHT));

        JTextField textField = new JTextField("Write your message here");
        textField.setBounds(100,200,600,400);

        JButton send = new JButton("Send");
        send.setBackground(Color.GREEN);
        send.setForeground(Color.WHITE);
        send.setBounds(270,650,200,50);

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

        users.setBounds(100,100,250,30);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(MARGIN_PANE_FROM_LEFT_EDGE,MARGIN_PANE_FROM_TOP,PANE_WIDTH,PANE_HEIGHT);
        scrollPane.setForeground(CUSTOMIZED_COLOR);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBounds(800,200,600,400);


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
