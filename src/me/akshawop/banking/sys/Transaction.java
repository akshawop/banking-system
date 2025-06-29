package me.akshawop.banking.sys;

import java.util.Date;

public class Transaction {
    private int transactionId;
    private int accountNumber;
    private String description;
    private String type;
    private String mode;
    private double amount;
    private double balance;
    private Date timestamp;

    public Transaction(int accountNumber, String description, String type, String mode, double amount, double balance) {
        this.accountNumber = accountNumber;
        this.description = description;
        this.type = type;
        this.mode = mode;
        this.amount = amount;
        this.balance = balance;
    }

    public Transaction(int transactionId, int accountNumber, String description, String type, String mode,
            double amount, double balance, Date timestamp) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.description = description;
        this.type = type;
        this.mode = mode;
        this.amount = amount;
        this.balance = balance;
        this.timestamp = timestamp;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getMode() {
        return mode;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
