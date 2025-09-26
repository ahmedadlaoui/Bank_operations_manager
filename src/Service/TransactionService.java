package Service;

import Model.Account;
import Model.Client;
import Model.Transaction;
import Model.User;
import Repository.imp.InMemoryTransactionRepository;
import Repository.imp.InMemoryAccountRepository;
import Model.enums.TransactionType;

import java.util.*;

public class TransactionService {
    private final InMemoryTransactionRepository transactionRepo = InMemoryTransactionRepository.getInstance();
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
            case 1 -> type = TransactionType.DEPOSIT;
            case 2 -> type = TransactionType.WITHDRAWAL;
            case 3 -> type = TransactionType.TRANSFER;
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }

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

        Account clientAccount = findAccountByClientID(client, accountId);
        if (clientAccount == null) {
            System.out.println("Invalid account ID.");
            return;
        }

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

            Account recipientAccount = findAccountByAccountID(recipientAccountId);
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

    public void DisplayAllTransactions() {
        List<Transaction> allTransactions = transactionRepo.findAll();
        if (allTransactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        loopAndPrintTransactions(allTransactions);
    }

    private void loopAndPrintTransactions(List<Transaction> transactions) {
        for (Transaction tx : transactions) {
            System.out.println("------------------------------");
            System.out.println("Transaction ID   : " + tx.getId());
            System.out.println("Type             : " + tx.getType());
            System.out.println("Amount           : " + tx.getAmount());
            System.out.println("Reason           : " + tx.getReason());
            if (tx.getSourceAccount() != null)
                System.out.println("From Account     : " + tx.getSourceAccount().getId());
            if (tx.getDestinationAccount() != null)
                System.out.println("To Account       : " + tx.getDestinationAccount().getId());
            System.out.println("Date             : " + tx.getDate());
        }
        System.out.println("------------------------------");
    }

    public void FilterClientTransactions() {
        System.out.println("\n=== Filter Client Transactions ===");
        System.out.print("Enter transaction type (Deposit, Withdrawal, Transfer): ");
        String typeInput = input.nextLine().trim().toUpperCase();

        TransactionType selectedType;
        try {
            selectedType = TransactionType.valueOf(typeInput);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid transaction type.");
            return;
        }

        System.out.print("Enter the Client ID: ");
        UUID clientId;
        try {
            clientId = UUID.fromString(input.nextLine().trim());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid Client ID format.");
            return;
        }

        List<Transaction> clientTransactions = transactionRepo.findByClientID(clientId);
        if (clientTransactions.isEmpty()) {
            System.out.println("\n⚠️ No transactions found for this client.");
            return;
        }

        List<Transaction> filteredTransactions = clientTransactions.stream()
                .filter(t -> t.getType() == selectedType)
                .toList();

        if (filteredTransactions.isEmpty()) {
            System.out.println("\n⚠️ No transactions of type " + selectedType + " found for this client.");
            return;
        }

        System.out.println("\n=== Filtered Transactions ===");
        displayTransactions(filteredTransactions);
    }

    public void IdentifySuspiciousTransactions() {
        System.out.print("Enter suspicious amount: ");
        double amount;
        try {
            amount = Double.parseDouble(input.nextLine().trim());
            if (amount <= 0) {
                System.out.println("Amount must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        List<Transaction> suspiciousTransactions = transactionRepo.GetSuspiciousTransactions(amount);
        if (suspiciousTransactions.isEmpty()) {
            System.out.println("No suspicious transactions found.");
            return;
        }
        System.out.println("\n=== Suspicious transactions ===");
        displayTransactions(suspiciousTransactions);
    }

    public void DisplayTransactionHistory(User user) {
        if (!(user instanceof Client)) {
            System.out.println("⚠️ Only clients can have a transaction history.");
            return;
        }

        Client client = (Client) user;
        List<Transaction> transactions = transactionRepo.findByClientID(client.getId());

        System.out.println("\n=== Transaction History ===");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        loopAndPrintTransactions(transactions);
    }

    public void FilterOrSort(User user) {
        if (!(user instanceof Client)) {
            System.out.println("⚠️ Only clients can use this feature.");
            return;
        }

        Client client = (Client) user;
        List<Transaction> transactions = transactionRepo.findByClientID(client.getId());
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        while (true) {
            System.out.println("\n=== Filter / Sort Transactions ===");
            System.out.println("1. Sort by Amount");
            System.out.println("2. Sort by Date");
            System.out.println("3. Filter by Type (Deposit / Withdrawal / Transfer)");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (choice == 0) break;

            switch (choice) {
                case 1 -> {
                    transactions.sort(Comparator.comparingDouble(Transaction::getAmount));
                    System.out.println("\n--- Transactions Sorted by Amount ---");
                    displayTransactions(transactions);
                }
                case 2 -> {
                    transactions.sort(Comparator.comparing(Transaction::getDate));
                    System.out.println("\n--- Transactions Sorted by Date ---");
                    displayTransactions(transactions);
                }
                case 3 -> {
                    System.out.print("Enter type (DEPOSIT / WITHDRAWAL / TRANSFER): ");
                    String typeInput = input.nextLine().toUpperCase();
                    try {
                        TransactionType filterType = TransactionType.valueOf(typeInput);
                        List<Transaction> filtered = transactions.stream()
                                .filter(t -> t.getType() == filterType)
                                .toList();
                        System.out.println("\n--- Transactions Filtered by " + filterType + " ---");
                        displayTransactions(filtered);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid type entered.");
                    }
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private Account findAccountByClientID(Client client, UUID accountId) {
        return accountRepo.FindByClientID(client.getId()).stream()
                .filter(a -> a.getId().equals(accountId))
                .findFirst().orElse(null);
    }

    private Account findAccountByAccountID(UUID accountId) {
        return accountRepo.findById(accountId);
    }

    private void displayTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        for (Transaction t : transactions) {
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
