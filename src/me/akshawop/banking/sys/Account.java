package me.akshawop.banking.sys;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    private String accountNumber;
    private String ifscCode;
    private AccountType type;
    private int customerId;
    private int nominee;
    private double balance;
    private double minBalance;
    private AccountStatus status;
    private Date openingDate;

    public Account(int customerId, String ifscCode, AccountType type, int nominee, double balance, double minBalance) {
        this.customerId = customerId;
        this.ifscCode = ifscCode;
        this.type = type;
        this.nominee = nominee;
        this.balance = balance;
        this.minBalance = minBalance;
    }

    public Account(ResultSet rs) {
        try {
            String branchId = rs.getInt("branch") + "";
            String type = AccountType.valueOf(rs.getString("account_type")).getCode();
            String accNo = rs.getInt("account_number") + "";
            this.accountNumber = branchId + "0".repeat(4 - branchId.length()) + type + "0".repeat(8 - accNo.length())
                    + accNo;

            this.ifscCode = rs.getString("bank.bank_code") + '0' + rs.getString("branch.branch_code");
            this.type = AccountType.valueOf(rs.getString("account_type"));
            this.customerId = rs.getInt("customer");
            this.nominee = rs.getInt("nominee");
            this.balance = rs.getDouble("balance");
            this.minBalance = rs.getDouble("min_balance");
            this.status = AccountStatus.valueOf(rs.getString("account_status"));
            this.openingDate = rs.getDate("opening_date");
        } catch (SQLException e) {
            System.err.println("Cannot access the Database while creating new Account object!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("something went wrong while creating new Account object!");
            System.err.println("More info:\n" + e);
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public AccountType getType() {
        return type;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getNominee() {
        return nominee;
    }

    public double getBalance() {
        return balance;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setNominee(int nominee) {
        this.nominee = nominee;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
