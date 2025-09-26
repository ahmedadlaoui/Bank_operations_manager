package Repository;

import Model.Client;
import Model.Transaction;
import Model.User;
import Model.enums.TransactionType;

import java.util.List;

public interface TransactionRepository {
    public void Save(Transaction transaction);
    public List<Transaction> findByClientID(Client client);
    public List<Transaction> findAll();
    public List<Transaction> FilterByType(TransactionType TransactionType);
    public List<Transaction> GetSuspiciousTransactions(Double amount);
}
