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
     * Gets the {@code Account} object which is being currently used by the DAO.
     * 
     * @return the current {@code Account} object
     */
    public Account getCurrentAccount() {
        return account;
    }

    /**
     * Gets the {@code int} Account Number which is being currently used by the DAO.
     * 
     * @return the current {@code int} Account Number
     */
    public int getCurrentAccountNumber() {
        return accountNumber;
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

    /**
     * Updates the Nominee of an Account in the Database.
     * 
     * @param nomineeId The nominee's {@code int} Customer ID
     * 
     * @return {@code 0} if update successful; {@code 1} if not
     * 
     * @log an error message if any error occurs
     */
    protected int updateNominee(int nomineeId) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.updateNomineeInDB(nomineeId, accountNumber));
            con.close();
            account.setNominee(nomineeId);
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

    /**
     * Transfers the Account to another Branch.
     * 
     * @param branchCode The {@code String} Branch Code of the Branch
     * 
     * @return {@code 0} if update successful; {@code 1} if not
     * 
     * @log an error message if any error occurs
     */
    protected int transferAccount(String branchCode) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(
                    SQLQueries.transferAccount(BranchDAO.fetchBranch(branchCode).getBranchId(), accountNumber));
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

    /**
     * Blocks the currently used Account.
     * 
     * @return {@code -1} if already blocked; {@code 0} if the process is
     *         successful; {@code 1} if not
     * 
     * @log an error message if any error occurs
     */
    protected int blockAccount() {
        if (account.getStatus().equals(AccountStatus.BLOCKED.toString())) {
            System.out.println("\nAccount is already Blocked!\n");
            return -1;
        }

        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.updateAccountStatus(AccountStatus.BLOCKED, accountNumber));
            con.close();
            account.setStatus(AccountStatus.BLOCKED);
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

    /**
     * Unblocks the currently used Account.
     * 
     * @return {@code -1} if already unblocked; {@code 0} if the process is
     *         successful; {@code 1} if not
     *
     * @log an error message if any error occurs
     */
    protected int unblockAccount() {
        if (account.getStatus().equals(AccountStatus.ACTIVE.toString())) {
            return -1;
        }

        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.updateAccountStatus(AccountStatus.ACTIVE, accountNumber));
            con.close();
            account.setStatus(AccountStatus.ACTIVE);
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

    /**
     * Generates a new Card in the Database and returns it. Uses
     * {@link CardDAO#createNewCard}
     * 
     * @param expireAfterYears The {@code int} years after which the card should
     *                         expire
     * 
     * @return {@code Card}(record) object if created successfully; {@code null} if
     *         not
     * 
     * @log an error message if any error occurs
     * 
     * @see CardDAO
     * @see Card
     */
    public Card generateNewDebitCard(int expireAfterYears) {
        return CardDAO.createNewCard(CardType.DEBIT, accountNumber, expireAfterYears);
    }

    /**
     * Fetch the data of a Card which belongs to this Account from the Database.
     * Uses
     * {@link CardDAO#fetchCardDetails}
     * 
     * @param cardNumber The {@code String} Card Number of the Card whose data
     *                   to
     *                   be fetched
     * 
     * @return {@code Card}(record) object if exists; {@code null} if doesn't, or if
     *         does not belongs to the current Account
     * 
     * @log an error message if any error occurs
     */
    protected Card getCard(String cardNumber) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getCardFromDB(cardNumber));

            if (!rs.next() && (rs.getInt("account") != accountNumber)) {
                con.close();
                return null;
            } else {
                con.close();
                return CardDAO.fetchCardDetails(cardNumber);
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
