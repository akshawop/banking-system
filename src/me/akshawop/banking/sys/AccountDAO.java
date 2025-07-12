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

    protected int updateNominee(int nomineeId) {
        // TODO: updateNominee
        throw new UnsupportedOperationException("Unimplemented method 'updateNominee'");
    }

    protected int transferAccount(String branchCode) {
        // TODO: transferAccount
        throw new UnsupportedOperationException("Unimplemented method 'transferAccount'");
    }

    protected int blockAccount() {
        // TODO: blockAccount
        throw new UnsupportedOperationException("Unimplemented method 'blockAccount'");
    }

    protected int unblockAccount() {
        // TODO: unblockAccount
        throw new UnsupportedOperationException("Unimplemented method 'unblockAccount'");
    }

    protected Transaction deposit(String description, String mode, double amount) {
        // TODO: deposit
        throw new UnsupportedOperationException("Unimplemented method 'deposit'");
    }

    protected Transaction withdraw(String description, String mode, double amount) {
        // TODO: withdraw
        throw new UnsupportedOperationException("Unimplemented method 'withdraw'");
    }

    void getTransactionHistory(Date fromDate, Date toDate) {
        // TODO: getTransactionHistory
        throw new UnsupportedOperationException("Unimplemented method 'getTransactionHistory'");
    }
}
