package Repository.imp;

import Model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryAccountRepository {
    private final ArrayList<Account> accounts = new ArrayList<Account>();

    public void Save(Account account){
        this.accounts.add(account);
    }
    public List<Account> FindByClientID(UUID clientID){
        List<Account> accountsToReturn = new ArrayList<>();
        for(Account account : this.accounts){
            if (account.getClient().getId().equals(clientID)) {
                accountsToReturn.add(account);
            }
        }
        return accountsToReturn;
    }
}
