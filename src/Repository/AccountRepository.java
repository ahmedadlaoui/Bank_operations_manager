package Repository;

import Model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountRepository {
    public void Save(Account account);
    public List<Account> FindByClientID(UUID ClientID);
}
