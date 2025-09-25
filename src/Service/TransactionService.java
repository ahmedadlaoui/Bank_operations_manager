package Service;

import Model.Account;
import Model.Client;
import Model.Transaction;
import Model.User;
import Repository.imp.InMemoryTransactionRepository;
import Repository.imp.InMemoryAccountRepository;
import Model.enums.TransactionType;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TransactionService {
    private final InMemoryTransactionRepository transactionRepo = new InMemoryTransactionRepository();
    private final InMemoryAccountRepository accountRepo = InMemoryAccountRepository.getInstance();
    private final Scanner input = new Scanner(System.in);

    public void MakeTransaction(User user) {
        Client client = (Client) user;

        System.out.println("Choose transaction type: ");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        System.out.println("3. Transfer");
        System.out.print("Choose your choice : ");
        int choice = input.nextInt();
        input.nextLine();

        TransactionType type;
        switch (choice) {
            case 1:
                type = TransactionType.DEPOSIT;
                break;
            case 2:
                type = TransactionType.WITHDRAWAL;
                break;
            case 3:
                type = TransactionType.TRANSFER;
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        System.out.println();
        System.out.print("Enter the amount : ");
        double amount;
        try {
            amount = input.nextDouble();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid amount format.");
            input.nextLine();
            return;
        }

        System.out.print("Enter the reason : ");
        String reason = input.nextLine();

        System.out.print("Enter your account ID : ");
        UUID accountId;
        try {
            accountId = UUID.fromString(input.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid account ID format.");
            return;
        }

        Account clientAccount = this.FindAccountByClientID(client, accountId);
        if (clientAccount == null) {
            System.out.println("Invalid account ID.");
            return;
        }

        // Handle Deposit / Withdrawal
        if (type == TransactionType.DEPOSIT) {
            clientAccount.setBalance(clientAccount.getBalance() + amount);
            Transaction transaction = new Transaction(type, amount, reason, clientAccount);
            transactionRepo.Save(transaction);
            System.out.println("✅ Deposit successful. New balance: " + clientAccount.getBalance());

        } else if (type == TransactionType.WITHDRAWAL) {
            if (clientAccount.getBalance() < amount) {
                System.out.println("❌ Insufficient funds.");
                return;
            }
            clientAccount.setBalance(clientAccount.getBalance() - amount);
            Transaction transaction = new Transaction(type, amount, reason, clientAccount);
            transactionRepo.Save(transaction);
            System.out.println("✅ Withdrawal successful. New balance: " + clientAccount.getBalance());

        } else {
            System.out.print("Enter the recipient's account ID : ");
            UUID recipientAccountId;
            try {
                recipientAccountId = UUID.fromString(input.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid recipient account ID format.");
                return;
            }

            Account recipientAccount = this.FindAccountByAccountID(recipientAccountId);
            if (recipientAccount == null) {
                System.out.println("Invalid recipient account ID.");
                return;
            }

            if (clientAccount.getBalance() < amount) {
                System.out.println("❌ Insufficient funds for transfer.");
                return;
            }

            clientAccount.setBalance(clientAccount.getBalance() - amount);
            recipientAccount.setBalance(recipientAccount.getBalance() + amount);

            Transaction transaction = new Transaction(type, amount, reason, clientAccount, recipientAccount);
            transactionRepo.Save(transaction);

            System.out.println("✅ Transfer successful.");
            System.out.println("Your new balance: " + clientAccount.getBalance());
        }
    }

    public void DisplayTransactionHistory(User user) {
        if (!(user instanceof Client)) {
            System.out.println("⚠️ Only clients can have a transaction history.");
            return;
        }

        Client client = (Client) user;
        List<Transaction> transactions = this.transactionRepo.findByClientID(client.getId());

        System.out.println();
        System.out.println("=== Transaction History ===");

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction tx : transactions) {
            System.out.println("------------------------------");
            System.out.println("Transaction ID : " + tx.getId());
            System.out.println("Type           : " + tx.getType());
            System.out.println("Amount         : " + tx.getAmount());
            System.out.println("Reason         : " + tx.getReason());
            if (tx.getSourceAccount() != null) {
                System.out.println("From Account   : " + tx.getSourceAccount().getId());
            }
            if (tx.getDestinationAccount() != null) {
                System.out.println("To Account     : " + tx.getDestinationAccount().getId());
            }
            System.out.println("Date           : " + tx.getDate());
        }
        System.out.println("------------------------------");
    }

    private Account FindAccountByClientID(Client client, UUID accountId) {
        List<Account> clientAccounts = this.accountRepo.FindByClientID(client.getId());
        for (Account account : clientAccounts) {
            if (account.getId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }

    private Account FindAccountByAccountID(UUID accountId) {
        return this.accountRepo.findById(accountId);
    }
}
