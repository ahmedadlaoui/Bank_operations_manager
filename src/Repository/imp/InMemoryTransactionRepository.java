package Repository.imp;

import Model.Transaction;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class InMemoryTransactionRepository {
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public void Save(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> findByClientID(UUID ClientID) {
        ArrayList<Transaction> transactionsByClientID = new ArrayList<>();
        for (Transaction transaction : this.transactions) {
            if (transaction.getSourceAccount().getClient().getId().equals(ClientID)) {
                transactionsByClientID.add(transaction);
            }
        }
        return transactionsByClientID;
    }

}
