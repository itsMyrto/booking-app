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
-> Username : willSmith , Password : 789
-> Username : robinSchulz , Password : 000

Providers :
-> Username : kygo , Password : raging
-> Username : RickAndMorty , Password : 123
-> Username : david , Password : 123
-> Username : stroustrup , Password : cplusplus
-> Username : jamesGosling , Password : java
 */