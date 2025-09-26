package Repository.imp;

import Model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryAccountRepository {
    private static InMemoryAccountRepository instance; // the single instance

    private final ArrayList<Account> accounts = new ArrayList<>();

    private InMemoryAccountRepository() {}

    public static synchronized InMemoryAccountRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryAccountRepository();
        }
        return instance;
    }

    public void Save(Account account) {
        this.accounts.add(account);
    }

    public List<Account> FindByClientID(UUID clientID) {
        List<Account> accountsToReturn = new ArrayList<>();
        for (Account account : this.accounts) {
            if (account.getClient().getId().equals(clientID)) {
                accountsToReturn.add(account);
            }
        }
        return accountsToReturn;
    }

    public Account findById(UUID accountID) {
        for (Account account : this.accounts) {
            if (account.getId().equals(accountID)) {
                return account;
            }
        }
        return null;
    }
    public List<Account> findAll() {
        return this.accounts;
    }
}
