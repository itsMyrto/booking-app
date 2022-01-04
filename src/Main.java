import java.io.*;

/**
 * This class contains the main method of the project
 */
public class Main {
	/**
	 * This is the main method. It repetitively shows the main menu of the app, and it stops when the user chooses the exit option
	 * @param args arguments
	 */
	public static void main(String[] args) throws FileNotFoundException {
		AccountsCreated listOfAccounts = new AccountsCreated();
		AccommodationsCreated listOfAccommodations = new AccommodationsCreated();
		ReservationsCreated listOfReservations = new ReservationsCreated();
		MainFrame frame = new MainFrame(listOfAccounts,listOfAccommodations,listOfReservations);

	}

}

		/*
		Scanner scanner = new Scanner(System.in);
		int option;
		Menu menu = new Menu();

		do{
			menu.printMenu();
			option = scanner.nextInt();
			while(option != 1 && option != 2 && option != 3 && option !=4){
				System.out.println("Please enter a valid number!");
				option = scanner.nextInt();
			}
			scanner.nextLine();
			menu.usersOption(option);
		}while(option != 4);
		*/