import java.io.*;
import java.util.ArrayList;

/**
 * This class contains all the created accounts
 */
public class AccountsCreated {

    private ArrayList<User> allAccounts;

    AccountsCreated(){
        allAccounts = new ArrayList<>();
        if(!isEmptyFile()) {
            try {
                FileInputStream fis = new FileInputStream("accounts.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                allAccounts = (ArrayList<User>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            Admin admin = new Admin("gregtsoum","123","gregTsoumakas@email.com","Greece","Gregory Tsoumakas");
            Admin admin2 = new Admin("gmeditsk","456","georgeMeditsk@email.com","Greece","Georgios Meditskos");
            Admin admin3 = new Admin("itsMyrto","football","itsMyrto@email.com","Greece","Myrto Gkogkou");
            allAccounts.add(admin2);
            allAccounts.add(admin3);
            allAccounts.add(admin);
            updateAccounts();
        }
    }

    public boolean usernameIsTaken(User user){
        boolean exists = false;
        for(User x : allAccounts){
            if (x.getUsername().equals(user.getUsername())) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public boolean emailIsTaken(User user){
        boolean exists = false;
        for(User x : allAccounts){
            if (x.getEmail().equals(user.getEmail())) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public ArrayList<Message> getTheMessageListOfAUser(User user){
        ArrayList<Message> messages = new ArrayList<>();
        for(User x:allAccounts){
            if(x.getEmail().equals(user.getEmail())){
                messages = user.getMessages();
                break;
            }
        }
        return messages;
    }

    public void createAccountForCustomers(Customer customer) {
        addNewAccount(customer);
    }

    public ArrayList<User> getUnapprovedAccount(){
        ArrayList<User> unapproved = new ArrayList();
        for(User x:allAccounts){
            if(!x.getApproved()){
                unapproved.add(x);
            }
        }
        return unapproved;
    }

    public void createAccountForProviders(Provider provider){
        addNewAccount(provider);
    }

    public void addNewAccount(User user){
        allAccounts.add(user);
        try {
            FileOutputStream fos = new FileOutputStream("accounts.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allAccounts);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAccounts(){
        try {
            FileOutputStream fos = new FileOutputStream("accounts.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allAccounts);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean accountIsUnapproved(User user){
        boolean isUnapproved = !user.getApproved();
        return isUnapproved;
    }

    public User logInAccount(String username,String password){
        for(User users:allAccounts){
            if(users.getUsername().equals(username) && users.getPassword().equals(password)){
                return users;
            }
        }
        return null;
    }

    public void printMethod(){
        for(User c:allAccounts){
            System.out.println(c.getUsername());
        }
    }

    public void sendMessage(Message message,String email){
        for(User x:allAccounts){
            if(x.getEmail().equals(email)){
                x.addMessage(message);
            }
        }
        updateAccounts();
    }

    public boolean isEmptyFile(){
        boolean isEmpty = false;
        try {
            FileInputStream fis = new FileInputStream("accounts.txt");
            if(fis.read() == -1){
                isEmpty = true;
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isEmpty;
    }

    public User getSpecificAccount(String email){
        for(User x:allAccounts){
            if(x.getEmail().equals(email)){
                return x;
            }
        }
        return null;
    }

    public String[] getAllUserEmails() {
        ArrayList<String> emails = new ArrayList<>();
        for (User x : allAccounts) {
            emails.add(x.getEmail());
        }
        String[] emailsArray = new String[emails.size()];
        int i=0;
        for(String x:emails){
            emailsArray[i]=x;
            i++;
        }
        return emailsArray;
    }
}


/*
public AccountsCreated(AllAccommodations totalAccommodations){
        allAccounts = new ArrayList<>();
        unapprovedAccounts = new ArrayList<>();
        User user1 = new Customer("antogrizi","grizou7","itsanto@domain.com","france","Antoine Griezmann");
        User user2 = new Customer("lioMessi","lio10","liomessi@domain.com","argentina","Lionel Andres Messi");
        User user3 = new Admin("gregtsoumakas","java","gregorytsoumakas@domain.com","greece","Grigorios Tsoumakas");
        User user4 = new Admin("george","javaLabs","georgeMeditskos@domain.com","greece","Georgios Meditskos");

        ArrayList<Accommodation> listOfAccommodations = new ArrayList<>();

        Location location = new Location("greece","kastoria","megalou alexandrou",12);
        Room room = new Room(4,105);
        Room room1 = new Room(3,85);
        Room room2 = new Room(2,65);
        Room room3 = new Room(1,45);
        Accommodation acc = new Accommodation("Kastor","hotel",true,true,location);
        acc.addRoom(room);
        acc.addRoom(room1);
        acc.addRoom(room2);
        acc.addRoom(room3);
        listOfAccommodations.add(acc);

        location = new Location("greece","kastoria","leoforos gounaradon",35);
        room = new Room(4,90);
        room1 = new Room(3,54);
        room2 = new Room(2,42);
        room3 = new Room(1,30);
        acc = new Accommodation("Chloe","hotel",true,true,location);
        acc.addRoom(room);
        acc.addRoom(room1);
        acc.addRoom(room2);
        acc.addRoom(room3);
        listOfAccommodations.add(acc);
        User user5 = new Provider("myrtoGk","football","itsmyrto@domain.com","greece","Myrto Gkogkou",listOfAccommodations,totalAccommodations);

        listOfAccommodations = new ArrayList<>();

        location = new Location("greece","kastoria","makedonomachon",2);
        room = new Room(4,500);
        room1 = new Room(3,400);
        room2 = new Room(2,200);
        acc = new Accommodation("Europa","hotel",true,true,location);
        acc.addRoom(room);
        acc.addRoom(room2);
        acc.addRoom(room1);
        listOfAccommodations.add(acc);

        location = new Location("greece","thessaloniki","konstantinou karamanli",37);
        room = new Room(5,300);
        room1 = new Room(4,250.55);
        room2 = new Room(3,135.67);
        room3 = new Room(2,100.76);
        acc = new Accommodation("Macedonia palace","hotel",true,true,location);
        acc.addRoom(room3);
        acc.addRoom(room);
        acc.addRoom(room1);
        acc.addRoom(room2);
        listOfAccommodations.add(acc);

        location = new Location("greece","thessaloniki","papanastasiou",45);
        room = new Room(4,45);
        room1 = new Room(4,47);
        room2 = new Room(4,48);
        room3 = new Room(4,49);
        acc = new Accommodation("El greco","apartments",true,true,location);
        acc.addRoom(room);
        acc.addRoom(room1);
        acc.addRoom(room2);
        acc.addRoom(room3);
        listOfAccommodations.add(acc);
        User user6 = new Provider("robert","bayern","lewandowski@domain.com","poland","Robert Lewandowski",listOfAccommodations,totalAccommodations);

        listOfAccommodations = new ArrayList<>();
        location = new Location("greece","volos","topali",3);
        room = new Room(3,55);
        room1 = new Room(3,55);
        room2 = new Room(3,55);
        room3 = new Room(3,55);
        acc = new Accommodation("Alexandros","hotel",true,false,location);
        acc.addRoom(room);
        acc.addRoom(room2);
        acc.addRoom(room1);
        acc.addRoom(room3);
        listOfAccommodations.add(acc);

        location = new Location("greece","volos","georgiou kartali",76);
        room = new Room(4,100);
        acc = new Accommodation("Volos View","apartment",true,true,location);
        acc.addRoom(room);
        listOfAccommodations.add(acc);

        location = new Location("greece","pilio","neochori",10);
        room = new Room(4,80);
        room1 = new Room(4,80);
        room2 = new Room(4,80);
        room3 = new Room(4,80);
        Room room5 = new Room(4,80);
        acc = new Accommodation("Archontiko Prepala","hotel",true,true,location);
        acc.addRoom(room);
        acc.addRoom(room1);
        acc.addRoom(room2);
        acc.addRoom(room3);
        acc.addRoom(room5);
        listOfAccommodations.add(acc);
        User user7 = new Provider("Artemisaki","artemis2002","artemisaki@domain.com","greece","Artemisia Margaritoulia",listOfAccommodations,totalAccommodations);

        allAccounts.add(user1);
        allAccounts.add(user2);
        allAccounts.add(user3);
        allAccounts.add(user4);
        allAccounts.add(user5);
        allAccounts.add(user6);
        allAccounts.add(user7);

    }

    public void addAccount(User newAccount){
        allAccounts.add(newAccount);
    }

    public void addUnapprovedAccount(User newAccount){
        unapprovedAccounts.add(newAccount);
    }

    public ArrayList<User> getUnapprovedAccounts(){
        return unapprovedAccounts;
    }

    public void removeUnapprovedAccount(User user){
        unapprovedAccounts.remove(user);
    }

    public boolean accountAlreadyExists(String username){
        boolean exists = false;
        for(User x : allAccounts){
            if (x.getUsername().equals(username)) {
                exists = true;
                break;
            }
        }
        for(User x:unapprovedAccounts){
            if (x.getUsername().equals(username)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public boolean accountAlreadyExists1(String email){
        boolean exists = false;
        for(User x : allAccounts){
            if (x.getEmail().equals(email)) {
                exists = true;
                break;
            }
        }
        for(User x:unapprovedAccounts){
            if (x.getEmail().equals(email)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public User getSpecificAccount(String username,String password){
        for(User x : allAccounts){
            if(x.getUsername().equals(username) && x.getPassword().equals(password) && x.getApproved()){
                return x;
            }
        }
        return null;
    }

    public User getSpecificAccount(String email){
        for(User x : allAccounts){
            if(x.getEmail().equals(email)){
                return x;
            }
        }
        return null;
    }
*/

/*
public void addNewAccount(User user){
        if(!allAccounts.isEmpty()){
            try {
                FileInputStream fis = new FileInputStream("accounts.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                allAccounts = (ArrayList<User>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            allAccounts.add(user);
            printAccounts();
            System.out.println();

            try {
                FileOutputStream fos = new FileOutputStream("accounts.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(allAccounts);
                oos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            allAccounts.add(user);
            try {
                FileOutputStream fos = new FileOutputStream("accounts.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(allAccounts);
                oos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        printAccounts();
        System.out.println();
    }

    public void printAccounts(){
        for(User s:allAccounts){
            System.out.println(s.getUsername());
        }
    }

    public ArrayList<User> openExistingFile(){
        try {
            FileInputStream fis = new FileInputStream("accounts.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            allAccounts = (ArrayList<User>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

 */