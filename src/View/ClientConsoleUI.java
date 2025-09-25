package View;

import Model.User;

import java.util.Scanner;

import Service.AccountService;
import Service.ClientService;
import Service.TransactionService;

public class ClientConsoleUI {
    private final Scanner input = new Scanner(System.in);
    private final ClientService clientService = new ClientService();
    private final AccountService accountService = new AccountService();
    private final TransactionService transactionService = new TransactionService();


    public void start(User currentUser) {

        while (currentUser != null) {
            System.out.println("\n=== CLIENT SPACE ===");
            System.out.println("1. View personal information");
            System.out.println("2. Add account");
            System.out.println("3. View accounts");
            System.out.println("4. Make transaction");
            System.out.println("5. View transaction history");
            System.out.println("6. Filter/sort transactions");
            System.out.println("7. Calculate total balance/deposits/withdrawals");
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
                        this.accountService.AddAccount(currentUser);
                        break;
                    case 3:
                        this.accountService.DisplayAccountsByClientID(currentUser);
                        break;
                    case 4:
                        this.transactionService.MakeTransaction(currentUser);
                        break;
                    case 5:
                        this.transactionService.DisplayTransactionHistory(currentUser);
                        break;
                    case 6:
                        break;
                    case 7:
                        this.accountService.CalculateTotals(currentUser);
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
