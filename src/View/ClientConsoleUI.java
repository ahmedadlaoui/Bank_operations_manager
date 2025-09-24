package View;

import Model.User;
import java.util.Scanner;
import Service.ClientService;

public class ClientConsoleUI {
    private final Scanner input = new Scanner(System.in);
    private final ClientService clientService = new ClientService();

    public void start(User currentUser) {

        while (currentUser != null) {
            System.out.println("\n=== CLIENT SPACE ===");
            System.out.println("1. View personal information");
            System.out.println("2. View accounts");
            System.out.println("3. View transaction history");
            System.out.println("4. Filter/sort transactions");
            System.out.println("5. Calculate total balance/deposits/withdrawals");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine());

                switch (choice) {
                    case 1:
                        this.clientService.DisplayPersonalInformation(currentUser);
                        break;
                    case 2:
                        System.out.println("You chose: View accounts");
                        break;
                    case 3:
                        System.out.println("You chose: View transaction history");
                        break;
                    case 4:
                        System.out.println("You chose: Filter/sort transactions");
                        break;
                    case 5:
                        System.out.println("You chose: Calculate total balance/deposits/withdrawals");
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        currentUser = null;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number from 0 to 5.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
