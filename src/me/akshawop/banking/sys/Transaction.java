package me.akshawop.banking.sys;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Transaction(ResultSet rs) {
        try {
            this.transactionId = rs.getInt("transaction_id");
            this.accountNumber = rs.getInt("account");
            this.description = rs.getString("description");
            this.type = rs.getString("transaction_type");
            this.mode = rs.getString("mode");
            this.amount = rs.getDouble("amount");
            this.balance = rs.getDouble("balance");
            this.timestamp = rs.getTimestamp("transaction_timestamp");
        } catch (SQLException e) {
            System.err.println("Cannot access the Database while creating new Transaction object!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("something went wrong while creating new Transaction object!");
            System.err.println("More info:\n" + e);
        }
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
