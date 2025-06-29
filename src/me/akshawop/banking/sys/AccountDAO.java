package me.akshawop.banking.sys;

import java.sql.Date;

public class AccountDAO {
    private Account account;

    protected AccountDAO(Account account) {
        this.account = account;
    }

    protected void printAccountInfo() {

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
