package Model;

import Model.enums.TransactionType;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private TransactionType type;
    private double amount;
    private LocalDateTime date;
    private String reason;
    private Account sourceAccount;
    private Account destinationAccount;

    // Constructor
    public Transaction(TransactionType type, double amount, String reason,
                       Account sourceAccount) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.reason = reason;
        this.sourceAccount = sourceAccount;
    }

    public Transaction(TransactionType type, double amount, String reason,
                       Account sourceAccount, Account destinationAccount) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.reason = reason;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
    }

    // Getters
    public UUID getId() { return id; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
    public String getReason() { return reason; }
    public Account getSourceAccount() { return sourceAccount; }
    public Account getDestinationAccount() { return destinationAccount; }

    @Override
    public String toString() {
        String dest = destinationAccount != null ? ", to=" + destinationAccount.getId() : "";
        return "Transaction{id='" + id + "', type=" + type + ", amount=" + amount +
                ", date=" + date + dest + "}";
    }
}
