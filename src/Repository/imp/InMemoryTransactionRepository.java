package Repository.imp;

import Model.Transaction;
import Model.enums.TransactionType;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class InMemoryTransactionRepository {

    private static InMemoryTransactionRepository instance; // Singleton instance
    private final ArrayList<Transaction> transactions;

    // Private constructor
    private InMemoryTransactionRepository() {
        this.transactions = new ArrayList<>();
    }

    // Public method to get the singleton instance
    public static InMemoryTransactionRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryTransactionRepository();
        }
        return instance;
    }

    public void Save(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> findByClientID(UUID clientID) {
        ArrayList<Transaction> transactionsByClientID = new ArrayList<>();
        for (Transaction transaction : this.transactions) {
            if (transaction.getSourceAccount().getClient().getId().equals(clientID)) {
                transactionsByClientID.add(transaction);
            }
        }
        return transactionsByClientID;
    }
    public List<Transaction> findAll() {
        return this.transactions;
    }
    public List<Transaction> FilterByType(TransactionType transactionType) {
        ArrayList<Transaction> transactionsByTransactionType = new ArrayList<>();
        for (Transaction transaction : this.transactions) {
            if (transaction.getType().equals(transactionType)) {
                transactionsByTransactionType.add(transaction);
            }
        }
        return transactionsByTransactionType;
    }
}
