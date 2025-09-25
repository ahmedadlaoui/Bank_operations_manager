package Repository;

import Model.Transaction;

public interface TransactionRepository {
    public void Save(Transaction transaction);
}
