package me.akshawop.banking.sys;

import java.sql.*;

import me.akshawop.banking.sql.SQLQueries;

/**
 * The Data Access Object of {@code Customer}
 * 
 * @see CustomerCLI
 * @see Customer
 */
public class CustomerDAO {
    private Customer customer;

    /**
     * Constructs a {@code CustomerDAO} with the specified {@code Customer} object.
     * 
     * @param customer The {@code Customer} object to be used in the DAO
     */
    protected CustomerDAO(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the {@code Customer} object which is being currently used by the DAO.
     * 
     * @return the current {@code Customer} object
     */
    public Customer getCurrentCustomer() {
        return customer;
    }

    /**
     * Fetch the data of a Customer from the Database.
     * 
     * @param customerId The {@code int} Customer ID of the Customer whose data to
     *                   be fetched
     * 
     * @return {@code Customer} object if exists; {@code null} if doesn't
     * 
     * @log an error message if any error occurs
     */
    public static Customer fetchCustomer(int customerId) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getCustomerFromDB(customerId));

            if (!rs.next()) {
                con.close();
                return null;
            } else {
                Customer customer = new Customer(rs);
                con.close();
                return customer;
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
     * Prints the current {@code Customer} object's data.
     * 
     * @log the data of the currently using Customer
     */
    protected void printCustomerInfo() {
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName().toUpperCase());
        System.out.println("Aadhaar No.: " + customer.getAadhaar());
        System.out.println("PAN: " + (customer.getPan().length() == 0 ? "N/A" : customer.getPan().toUpperCase()));
        System.out.println("Address: " + (customer.getAddress() + "").toUpperCase());
        System.out.println("Phone: " + customer.getPhone());
        System.out.println("Email: " + (customer.getEmail().length() == 0 ? "N/A" : customer.getEmail()));
        System.out.println("Registered on: " + customer.getRegistrationDate());
    }

    /**
     * Creates a new Account of the current Customer in the Database
     *
     * @param account The {@code Account} object of the account to be created
     * 
     * @return {@code 0} if process was successful; {@code 1} if unsuccessful
     * 
     * @log an error message if any error occurs
     */
    protected int createAccount(Account account) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.createAccountInDB(account));
            con.close();
            return 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Duplicate Account Creation not allowed!");
            System.out.println("A " + account.getType() + " Account for this Customer already exists!");
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
     * Deletes a Account from the Database.
     * 
     * @param accountNumber The {@code int} Account Number of the Branch to be
     *                      deleted
     * 
     * @return {@code 0} if successful; {@code 1} if unsuccessful
     * 
     * @log an error message if any error occurs
     */
    protected int closeAccount(int accountNumber) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.deleteAccountFromDB(accountNumber));
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
     * Transfers the remaining balance of the account which is being closed to
     * another account.
     * 
     * @param fromAccountNumber The {@code int} Account Number of the account from
     *                          where to deduct the balance from
     * @param toAccountNumber   The {@code int} Account Number of the account to
     *                          where to add the balance to
     * 
     * @return {@code 0} if process was successful; {@code 1} if not
     * 
     * @log an error message if any error occurs
     */
    protected int transferBalance(int fromAccountNumber, int toAccountNumber) {
        try {
            Account toAccount = AccountDAO.fetchAccount(toAccountNumber);
            Account fromAccount = AccountDAO.fetchAccount(fromAccountNumber);

            double balance = fromAccount.getBalance();
            if (balance == 0) {
                return 0;
            }
            String description = "Remaining account balance transfer from " + "X".repeat(10)
                    + String.format("%04d", fromAccountNumber) + " to " + "X".repeat(10)
                    + String.format("%04d", toAccountNumber);

            new AccountDAO(fromAccount).withdraw(description, TransactionMode.BRANCH, balance).getTransactionId();
            new AccountDAO(toAccount).deposit(description, TransactionMode.BRANCH, balance).getTransactionId();

            return 0;
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        return 1;
    }

    /**
     * Prints the {@code Account} information.
     * Uses the {@link AccountDAO#printAccountInfo()} method.
     * 
     * @param account The {@code Account} object
     * 
     * @log every field of the {@code Account} object
     */
    protected static void printAccountInfo(Account account) {
        new AccountDAO(account).printAccountInfo();
    }

    /**
     * Lists the Accounts' data which belong to the currently logged in Customer,
     * from
     * the Database.
     * 
     * @param from  The {@code int} Account Number as the upper limit from where the
     *              list should start
     * @param limit The number of Accounts to list
     * 
     * @log an error message if any error occurs
     */
    protected void listAccounts() {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.listAccounts(customer.getCustomerId()));

            if (!rs.next()) {
                System.out.println("\nNo Accounts found.\n");
                con.close();
            } else {
                System.out.println("    --Accounts--\n");
                do {
                    new AccountDAO(new Account(rs)).printAccountInfo();
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
