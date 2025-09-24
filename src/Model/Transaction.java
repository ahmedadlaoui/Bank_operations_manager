package Model;
import Model.enums.TransactionType;

import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private TransactionType type;
    private double amount;
    private LocalDateTime date;
    private String reason;
    private Account sourceAccount;
    private Account destinationAccount; // optional for transfers

    public Transaction(String id, TransactionType type, double amount, LocalDateTime date,
                       String reason, Account sourceAccount, Account destinationAccount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.reason = reason;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
    }

    public String getId() { return id; }
    public TransactionType getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
    public String getReason() { return reason; }
    public Account getSourceAccount() { return sourceAccount; }
    public Account getDestinationAccount() { return destinationAccount; }

    @Override
    public String toString() {
        String dest = destinationAccount != null ? ", to=" + destinationAccount.getId() : "";
        return "Transaction{id='" + id + "', type=" + type + ", amount=" + amount + ", date=" + date + dest + "}";
    }
}


