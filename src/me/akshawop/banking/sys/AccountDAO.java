package me.akshawop.banking.sys;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import me.akshawop.banking.sql.SQLQueries;

/**
 * The Data Access Object of {@code Account}
 * 
 * @see AccountCLI
 * @see Account
 */
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
        if (account.getStatus().equals(AccountStatus.BLOCKED)) {
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
        if (account.getStatus().equals(AccountStatus.ACTIVE)) {
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

    /**
     * Adds the given amount to an Account in the database and returns a
     * Transaction object.
     * Uses {@link TransactionDAO#createTransaction} and
     * {@link TransactionDAO#getTransaction}.
     * 
     * @param description The {@code String} description of the transaction
     * @param mode        The {@code TransactionMode} of the transaction
     * @param amount      The {@code double} amount to be deposited
     * 
     * @return {@code Transaction} object if process successful; {@code null} if not
     * 
     * @see TransactionDAO
     * 
     * @log an error message if any error occurs
     */
    protected Transaction deposit(String description, TransactionMode mode, double amount) {
        if (amount <= 0)
            return null;

        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();

            // updating the balance in DB
            double balance = account.getBalance() + amount;
            st.executeUpdate(SQLQueries.updateBalanceInDB(balance, accountNumber));
            account.setBalance(balance);
            con.close();

            // creating and returning Transaction
            Transaction tr = new Transaction(accountNumber, description, TransactionType.CREDIT, mode, amount, balance);
            if (TransactionDAO.createTransaction(tr) != 0)
                return null;

            return TransactionDAO.getTransaction(tr);
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
     * Deducts the given amount from an Account in the database and returns a
     * Transaction object.
     * Uses {@link TransactionDAO#createTransaction} and
     * {@link TransactionDAO#getTransaction}.
     * 
     * @param description The {@code String} description of the transaction
     * @param mode        The {@code TransactionMode} of the transaction
     * @param amount      The {@code double} amount to be withdrawn
     * 
     * @return {@code Transaction} object if process successful; {@code null} if not
     * 
     * @see TransactionDAO
     * 
     * @log an error message if any error occurs
     */
    protected Transaction withdraw(String description, TransactionMode mode, double amount) {
        if (amount <= 0)
            return null;

        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();

            // updating the balance in DB
            double balance = account.getBalance() - amount;
            st.executeUpdate(SQLQueries.updateBalanceInDB(balance, accountNumber));
            account.setBalance(balance);
            con.close();

            // creating and returning Transaction
            Transaction tr = new Transaction(accountNumber, description, TransactionType.DEBIT, mode, amount, balance);
            if (TransactionDAO.createTransaction(tr) != 0)
                return null;

            return TransactionDAO.getTransaction(tr);
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
     * Prints the Transaction history on the console.
     * Uses {@link TransactionDAO#printTransaction}.
     * 
     * @param fromDate The {@code Date} object of the date to fetch Transaction from
     * @param toDate The {@code Date} object of the date to fetch Transaction up to
     * 
     * @see TransactionDAO
     * 
     * @log an error message if any error occurs
     */
    protected void getTransactionHistory(Date fromDate, Date toDate) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getTransactionHistoryFromDB(accountNumber, fromDate, toDate));

            if (!rs.next()) {
                System.out.println("\nNo Transactions found.\n");
                con.close();
            } else {
                System.out.println("Transaction(s) found!\n");
                do {
                    TransactionDAO.printTransaction(new Transaction(rs));
                    System.out.println();
                } while (rs.next());
                System.out.println();
                con.close();
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
    }
}
