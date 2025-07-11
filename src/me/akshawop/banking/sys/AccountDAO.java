package me.akshawop.banking.sys;

import java.sql.Date;

public class AccountDAO {
    private Account account;

    protected AccountDAO(Account account) {
        this.account = account;
    }

    protected void printAccountInfo() {
        System.out.println("Account No.: " + account.getAccountNumber());
        System.out.println("IFSC Code: " + account.getIfscCode().toUpperCase());
        System.out.println("Customer ID: " + account.getCustomerId());
        System.out.println("Type: " + account.getType());
        System.out.println("Account Balance: $" + account.getBalance());
        System.out.println("Minimum Balance: $" + account.getMinBalance());
        System.out.println("Nominee Customer ID: " + (account.getNominee() == 0 ? "N/A" : account.getNominee()));
        System.out.println("Status: " + account.getStatus());
        System.out.println("Opening Date: " + account.getOpeningDate());
    }

    protected Transaction deposit(String description, String mode, double amount) {
        return null;
    }

    protected Transaction withdraw(String description, String mode, double amount) {
        return null;
    }

    void getTransactionHistory(Date fromDate, Date toDate) {

    }
}
