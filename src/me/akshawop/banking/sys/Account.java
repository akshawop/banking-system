package me.akshawop.banking.sys;

import java.sql.Date;

public class Account {
    private String accountNumber;
    private String branchCode;
    private String type;
    private int customerId;
    private String nominee = "";
    private double balance;
    private double minBalance;
    private String status;
    private Date openingDate;

    public Account(String branchCode, String type, int customerId, String nominee, double balance, double minBalance) {
        this.branchCode = branchCode;
        this.type = type;
        this.customerId = customerId;
        this.nominee = nominee;
        this.balance = balance;
        this.minBalance = minBalance;
    }

    public Account(String accountNumber, String branchCode, String type, int customerId, String nominee, double balance,
            double minBalance, String status, Date openingDate) {
        this.accountNumber = accountNumber;
        this.branchCode = branchCode;
        this.type = type;
        this.customerId = customerId;
        this.nominee = nominee;
        this.balance = balance;
        this.minBalance = minBalance;
        this.status = status;
        this.openingDate = openingDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getType() {
        return type;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getNominee() {
        return nominee;
    }

    public double getBalance() {
        return balance;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public String getStatus() {
        return status;
    }

    public Date getOpeningDate() {
        return openingDate;
    }
}
