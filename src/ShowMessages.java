import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShowMessages extends JPanel {

    private final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final int PANEL_WIDTH = (int) (env.getMaximumWindowBounds().getWidth());
    private final int PANEL_HEIGHT = (int) (env.getMaximumWindowBounds().getHeight());

    private JScrollPane scrollPane = new JScrollPane();

    private ArrayList<JLabel> messagesLabel = new ArrayList<>();

    private ArrayList<Message> messages = new ArrayList<>();

    private final int PANE_WIDTH = (int) (0.8*PANEL_WIDTH);
    private final int PANE_HEIGHT = (int) (0.7*PANEL_HEIGHT);

    private final int MSG_LABEL_HEIGHT = 40;
    private final String MARGIN_MSG_FROM_PANE_EDGE = "    ";
//    private final int MSG_LABEL_WIDTH = PANE_WIDTH-MARGIN_MSG_FROM_PANE_EDGE;

    private final int MARGIN_PANE_FROM_LEFT_EDGE = (PANEL_WIDTH-PANE_WIDTH)/2;
    private final int MARGIN_PANE_FROM_TOP = (PANEL_HEIGHT-PANE_HEIGHT)/2;

    private final String FONT = "Tahoma";
    private final Color CUSTOMIZED_COLOR = Color.decode("#3B5998");

    public ShowMessages(User user, AccountsCreated listOfAccounts, AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){
        this.setSize(PANEL_WIDTH,PANEL_HEIGHT);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(MARGIN_PANE_FROM_LEFT_EDGE,MARGIN_PANE_FROM_TOP,PANE_WIDTH,PANE_HEIGHT);
        scrollPane.setForeground(CUSTOMIZED_COLOR);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        int pxCounter = 0;
        int counter = 0;

        for(int i=0; i<50; i++){
            Message msg1 = new Message("Hello, " + i, new User("Gregory", "1", "g", "g", "Tsoum"));
            messages.add(msg1);
        }

        for(Message x : messages){
            String msg = MARGIN_MSG_FROM_PANE_EDGE + x.getMessage();
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

            scrollPane.add(temp);
        }

        if(user.getMessages().size()==0){

        }
        else{
            ArrayList<Message> messages = user.getMessages();
            JLabel[] labels = new JLabel[messages.size()];
            int count = 0;
            for(Message x:messages){
                labels[count].setText(x.getMessage());
                count++;
            }
        }

        this.add(scrollPane);

        this.setOpaque(false);
        this.setLayout(null);
        this.setVisible(true);
    }
}
