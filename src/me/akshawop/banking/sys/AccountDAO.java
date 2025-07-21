package me.akshawop.banking.sys;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import me.akshawop.banking.sql.SQLQueries;

public class AccountDAO {
    private Account account;
    private int accountNumber;

    protected AccountDAO(Account account) {
        this.account = account;
        this.accountNumber = Integer.parseInt(account.getAccountNumber().substring(6));
    }

    /**
     * Gets the {@code Customer} object which is being currently used by the DAO.
     * 
     * @return the current {@code Customer} object
     */
    public Account getCurrentAccount() {
        return account;
    }

    /**
     * Fetch the data of a Account from the Database.
     * 
     * @param accountNumber The {@code int} Account Number of the Account whose data
     *                      to
     *                      be fetched
     * 
     * @return {@code Account} object if exists; {@code null} if doesn't
     * 
     * @log an error message if any error occurs
     */
    public static Account fetchAccount(int accountNumber) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getAccountFromDB(accountNumber));

            if (!rs.next()) {
                con.close();
                return null;
            } else {
                Account account = new Account(rs);
                con.close();
                return account;
            }
        } catch (SQLTimeoutException e) {
            System.err.println("Error: Database timeout!");
            System.err.println("More info:\n" + e);
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        return null;
    }

    /**
     * Prints the {@code Account} information which is being currently used by the
     * DAO.
     * 
     * @log every field of the {@code Account} object
     */
    public void printAccountInfo() {
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
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.updateNomineeInDB(nomineeId, accountNumber));
            con.close();
            return 0;
        } catch (SQLTimeoutException e) {
            System.err.println("Error: Database timeout!");
            System.err.println("More info:\n" + e);
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        return 1;
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
