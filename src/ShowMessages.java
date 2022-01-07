import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShowMessages extends JPanel {
    public ShowMessages(User user, AccountsCreated listOfAccounts, AccommodationsCreated listOfAccommodations,ReservationsCreated listOfReservations,MainFrame mainFrame){
        setBackground(Color.WHITE);
        setLayout(new FlowLayout());
        setSize(1500,800);
        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(0x6FC4CB));
        panel2.setSize(400,1500);
        panel2.setBounds(500,100,500,800);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(500,100,500,800);
        panel2.add(scrollPane);

        JLabel inboxEmpty = new JLabel("Your inbox is empty");
        inboxEmpty.setBounds(200,200,200,200);
        inboxEmpty.setVisible(false);
        panel2.add(inboxEmpty);

        if(user.getMessages().size()==0){
            inboxEmpty.setVisible(true);
        }
        else{
            ArrayList<Message> messages = user.getMessages();
            JLabel[] labels = new JLabel[messages.size()];
            int count = 0;
            for(Message x:messages){
                labels[count].setText(x.getMessage());
                panel2.add(labels[count]);
                count++;
            }
        }

        add(panel2);
    }
}
