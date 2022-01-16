import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class ShowMessages extends JPanel {

    public ShowMessages(User user, AccountsCreated listOfAccounts, AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());
        int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth());
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        mainFrame.setVisible(false);

        int pxCounter = 0;
        int counter = 0;

        JLabel header = new JLabel("INBOX");
        header.setBounds(670,10,200,50);
        header.setFont(new Font("sans serif",Font.ITALIC+Font.BOLD,28));
        header.setForeground(new Color(6, 48, 124));
        add(header);

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(10,10,50,20);
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
            if(user instanceof Customer){
                mainFrame.getContentPane().add(new CustomerWindow((Customer)user, listOfAccounts, listOfAccommodations,listOfReservations,mainFrame));
            }
            else{
                mainFrame.getContentPane().add(new ProviderWindow(((Provider) user),listOfAccommodations,listOfAccounts,listOfReservations,mainFrame));
            }
            mainFrame.getContentPane().repaint();
        });

        ArrayList<Message> messages = user.getMessages();

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


        JScrollPane scrollPane = new JScrollPane(panel);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        int MARGIN_PANE_FROM_TOP = (PANEL_HEIGHT - PANE_HEIGHT) / 2;
        int MARGIN_PANE_FROM_LEFT_EDGE = (PANEL_WIDTH - PANE_WIDTH) / 2;
        scrollPane.setBounds(MARGIN_PANE_FROM_LEFT_EDGE, MARGIN_PANE_FROM_TOP, PANE_WIDTH, PANE_HEIGHT);
        scrollPane.setForeground(CUSTOMIZED_COLOR);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.add(scrollPane);

        this.setOpaque(false);
        this.setLayout(null);

        panel.setVisible(true);
        this.setVisible(true);

        mainFrame.setVisible(true);

    }
}
