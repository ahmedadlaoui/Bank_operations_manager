package Model;

import Model.enums.AccountType;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String id;
    private AccountType type;
    private double balance;
    private Client client;
    private List<Transaction> transactions;

    public Account(String id, AccountType type, Client client) {
        this.id = id;
        this.type = type;
        this.client = client;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public Client getClient() { return client; }

    public List<Transaction> getTransactions() { return transactions; }
    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            this.transactions.add(transaction);
        }
    }

    @Override
    public String toString() {
        return "Account{id='" + id + "', type=" + type + ", balance=" + balance + "}";
    }
}


