package Model;

import Model.enums.AccountType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account {
    private UUID id;                     // Changed from String to UUID
    private AccountType type;
    private double balance;
    private Client client;
    private List<Transaction> transactions;

    // Constructor with auto-generated UUID
    public Account(AccountType type, Client client) {
        this.id = UUID.randomUUID();     // automatically generate unique ID
        this.type = type;
        this.client = client;
        this.balance = 0.0;             // default starting balance
        this.transactions = new ArrayList<>(); // empty list initially
    }

    // Full constructor if you want to provide a UUID manually
    public Account(UUID id, AccountType type, Client client) {
        this.id = id;
        this.type = type;
        this.client = client;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    // Getters & Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

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
