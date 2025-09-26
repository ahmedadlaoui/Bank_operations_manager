package View;

import Model.User;
import Service.AccountService;
import Service.ClientService;
import Service.TransactionService;

import java.util.Scanner;

public class ManagerConsoleUI {

    private final Scanner input = new Scanner(System.in);
    private final ClientService clientService ;
    private final AccountService accountService ;
    private final TransactionService transactionService ;

    public ManagerConsoleUI(ClientService clientService, AccountService accountService, TransactionService transactionService) {
        this.clientService = clientService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public void start(User currentUser) {

        boolean running = true;

        while (running) {
            System.out.println("\n=== MANAGER SPACE ===");
            System.out.println("1. List Client banking informations");
            System.out.println("2. List all accounts");
            System.out.println("3. List all transactions");
            System.out.println("4. filter a client’s transactions by type");
            System.out.println("5. Identify suspicious transactions");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    this.clientService.GetInformationsByMail();
                    break;

                case 2:
                    this.accountService.DisplayAllAccounts();
                    break;

                case 3:
                    this.transactionService.DisplayAllTransactions();
                    break;

                case 4:
                    this.transactionService.FilterClientTransactions();
                    break;

                case 5:
                    this.transactionService.IdentifySuspiciousTransactions();
                    break;

                case 0:
                    System.out.println("Logging out...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
