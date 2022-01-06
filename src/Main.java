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
Admins:
-> Username : gregtsoum , Password:123
-> Username : gmeditsk , Password: 456
-> Username : myrto, Password: football
 */