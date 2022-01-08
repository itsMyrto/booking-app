import java.io.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		AccountsCreated listOfAccounts = new AccountsCreated();
		AccommodationsCreated listOfAccommodations = new AccommodationsCreated();
		ReservationsCreated listOfReservations = new ReservationsCreated();
		MainFrame frame = new MainFrame(listOfAccounts,listOfAccommodations,listOfReservations);

	}
}

/*
Admins:
-> Username : gregtsoum , Password : 123
-> Username : gmeditsk , Password : 456
-> Username : myrto, Password : football

Customers:
-> Username : bastilleDan , Password : pompeii
-> Username : antogrizi , Password : grizou
-> Username : keramidas , Password : thinksilicon
-> Username : robinSchulz , Password : 000

Providers :
-> Username : kygo , Password : music
-> Username : RickAndMorty , Password : 123
-> Username : lioMessi , Password : lio10

 */