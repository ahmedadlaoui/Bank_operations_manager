package Repository.imp;

import Model.Transaction;

import java.util.ArrayList;

public class InMemoryTransactionRepository {
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public void Save(Transaction transaction) {
        this.transactions.add(transaction);
    }
}
