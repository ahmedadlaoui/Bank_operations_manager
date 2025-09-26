package Service;

import Model.Account;
import Model.Client;
import Model.Transaction;
import Model.User;
import Repository.imp.InMemoryAccountRepository;
import Repository.imp.InMemoryClientRepository;
import Repository.imp.InMemoryTransactionRepository;

import java.util.List;
import java.util.Scanner;

public class ClientService {
    private final Scanner input = new Scanner(System.in);
    private final InMemoryClientRepository clientRepo = InMemoryClientRepository.getInstance();
    private final InMemoryTransactionRepository transactionRepo = InMemoryTransactionRepository.getInstance();
    private final InMemoryAccountRepository accountRepo = InMemoryAccountRepository.getInstance();

    public void DisplayPersonalInformation(User user) {

        System.out.println();
        System.out.println("=== Personal Information ===");
        if (user instanceof Client) {
            Client client = (Client) user;
            System.out.println("Client ID: " + client.getId());
        }
        System.out.println("Name  : " + user.getFirstName() + " " + user.getLastName());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Role  : " + user.getRole());
    }

    public void GetInformationsByMail() {
        System.out.print("Enter the client's e-mail Address: ");
        String email = input.nextLine();

        User searchedForUser = this.clientRepo.FindByEmail(email);

        // Check if user doesn't exist or is not a client
        if (searchedForUser == null || !searchedForUser.getRole().equalsIgnoreCase("Client")) {
            System.out.println("Client not found.");
            return;
        }

        Client client = (Client) searchedForUser;

        // Display personal info
        System.out.println("\n=== Client Information ===");
        System.out.println("Client ID: " + client.getId());
        System.out.println("Name     : " + client.getFirstName() + " " + client.getLastName());
        System.out.println("Email    : " + client.getEmail());
        System.out.println("Role     : " + client.getRole());

        // Display accounts
        List<Account> clientAccounts = this.accountRepo.FindByClientID(client.getId());
        if (clientAccounts.isEmpty()) {
            System.out.println("\nNo accounts found for this client.");
        } else {
            System.out.println("\n=== Accounts ===");
            for (Account account : clientAccounts) {
                System.out.println();
                System.out.println("Account ID   : " + account.getId());
                System.out.println("Account Type : " + account.getType());
                System.out.println("Balance      : " + account.getBalance());
            }
        }

        // Display transactions
        List<Transaction> clientTransactions = this.transactionRepo.findByClientID(client.getId());
        if (clientTransactions.isEmpty()) {
            System.out.println("\nNo transactions found for this client.");
        } else {
            System.out.println("\n=== Transactions ===");
            for (Transaction t : clientTransactions) {
                System.out.println();
                System.out.println("Transaction ID     : " + t.getId());
                System.out.println("Type               : " + t.getType());
                System.out.println("Amount             : " + t.getAmount());
                System.out.println("Reason             : " + t.getReason());
                System.out.println("Date               : " + t.getDate());
                System.out.println("Source Account     : " + t.getSourceAccount().getId());
                if (t.getDestinationAccount() != null)
                    System.out.println("Destination Account: " + t.getDestinationAccount().getId());
                System.out.println("-------------------------------");
            }

        }
    }

}
