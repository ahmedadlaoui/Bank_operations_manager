package Repository;

import Model.Client;
import Model.Transaction;
import Model.User;

import java.util.List;

public interface TransactionRepository {
    public void Save(Transaction transaction);
    public List<Transaction> findByClientID(Client client);
    public List<Transaction> findAll();
}
