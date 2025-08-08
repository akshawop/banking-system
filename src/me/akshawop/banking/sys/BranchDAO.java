package me.akshawop.banking.sys;

import java.sql.*;

import me.akshawop.banking.cli.BranchCLI;
import me.akshawop.banking.sql.SQLQueries;

/**
 * The Data Access Object of {@code Branch}
 * 
 * @see BranchCLI
 * @see Branch
 */
public class BranchDAO {
    private Branch branch;

    /**
     * Constructs a {@code BranchDAO} with the specified
     * Branch Code.
     * <p>
     * Uses {@code fetchBranch} method.
     *
     * @param branchCode The {@code String} Branch Code
     * 
     * @log an error message if any error occurs
     * 
     * @see BranchDAO#fetchBranch
     */
    protected BranchDAO(String branchCode) {
        this.branch = fetchBranch(branchCode);
    }

    /**
     * Gets the {@code Branch} object which is being currently used by the DAO.
     * 
     * @return the current {@code Branch} object
     */
    protected Branch getCurrentBranch() {
        return branch;
    }

    /**
     * Fetch the data of a Branch from the Database.
     * 
     * @param branchCode The {@code int} Branch Code of the Branch whose data to
     *                   be fetched
     * 
     * @return {@code Branch} object if exists; {@code null} if doesn't
     * 
     * @log an error message if any error occurs
     */
    public static Branch fetchBranch(String branchCode) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getBranchFromDB(branchCode));

            if (rs.next())
                return new Branch(rs);
            con.close();
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
     * Checks if any Branch already exists in the Database or not.
     * 
     * @return {@code true} if any Branch exists; {@code false} if none exist or any
     *         error occurs
     * @log an error message in any error occurs
     */
    public static boolean doesAnyBranchExists() {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.firstBranchInDB());

            if (rs.next()) {
                con.close();
                return true;
            }
            con.close();
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
        return false;
    }

    /**
     * Fetches the Customer ID of the Customer with the provided Aadhaar number from
     * the database.
     * 
     * @param aadhaar The {@code String} Aadhaar number of the Customer
     * 
     * @return {@code int} Customer ID if found; {@code 0} if not
     * 
     * @log an error message in any error occurs
     */
    protected static int getCustomerId(String aadhaar) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getCustomerIdFromDB(aadhaar));
            rs.next();
            int id = rs.getInt(1);
            con.close();
            return id;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Duplicate Customer not allowed!");
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
        return 0;
    }

    /**
     * Adds a new Customer to the Database
     *
     * @param customer The {@code Customer} object of the customer to be added
     * 
     * @return {@code 0} if process was successful; {@code 1} if unsuccessful
     * 
     * @log an error message if any error occurs
     */
    protected int addCustomer(Customer customer) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.addCustomerToDB(customer));
            con.close();
            return 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Duplicate Customer not allowed!");
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
     * Adds the given amount to an Account in the database and returns a
     * Transaction object.
     * Uses {@link AccountDAO#deposit}.
     * 
     * @param accountNumber The {@code int} account number of the Account to deposit
     *                      the amount to
     * @param description   The {@code String} description of the transaction
     * @param amount        The {@code double} amount to be deposited
     * 
     * @return {@code Transaction} object if process successful; {@code null} if not
     * 
     * @see TransactionDAO
     * 
     * @log an error message if any error occurs
     */
    protected Transaction deposit(Account account, String description, double amount) {
        return new AccountDAO(account).deposit(description, TransactionMode.BRANCH, amount);
    }

    /**
     * Deducts the given amount to an Account in the database and returns a
     * Transaction object.
     * Uses {@link AccountDAO#withdraw}.
     * 
     * @param accountNumber The {@code int} account number of the Account to
     *                      withdraw
     *                      the amount from
     * @param description   The {@code String} description of the transaction
     * @param amount        The {@code double} amount to be withdrawn
     * 
     * @return {@code Transaction} object if process successful; {@code null} if not
     * 
     * @see TransactionDAO
     * 
     * @log an error message if any error occurs
     */
    protected Transaction withdraw(Account account, String description, double amount) {
        return new AccountDAO(account).withdraw(description, TransactionMode.BRANCH, amount);
    }

    /**
     * Gets the {@code Customer} with the specified Customer ID from the Database.
     * <p>
     * Uses the {@link CustomerDAO#fetchCustomer}
     * 
     * @param customerId The Customer ID of the customer to fetch the details of
     * 
     * @return {@code Customer} object if the customer exists; {@code null} if not
     * 
     * @log an error message if any error occurs
     */
    protected Customer getCustomer(int customerId) {
        return CustomerDAO.fetchCustomer(customerId);
    }

    /**
     * Updates a Customer's details in the Database.
     * 
     * @param customer The newly created {@code Customer} object of the Customer
     *                 whose data is to be updated
     * 
     * @return {@code 0} if update successful; {@code 1} if not
     * 
     * @log an error message if any error occurs
     */
    protected int updateCustomer(Customer customer) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.updateCustomerInDB(customer));
            con.close();
            return 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Customer with similar data already exists!");
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
     * Lists the Accounts' data which belong to the currently logged in Branch, from
     * the Database.
     * 
     * @param from  The {@code int} Account Number as the upper limit from where the
     *              list should start
     * @param limit The number of Accounts to list
     * 
     * @log an error message if any error occurs
     */
    protected void listAccounts(int from, int limit) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.listAccounts(branch.getBranchId(), from, limit));

            if (!rs.next()) {
                System.out.println("\nNo Accounts found.\n");
                con.close();
            } else {
                System.out.println("Account(s) found!\n");
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

    /**
     * Prints the {@code Branch} information which is being currently used by the
     * DAO.
     * 
     * @log every field of the {@code Branch} object
     */
    protected void printBranchInfo() {
        System.out.println("Branch ID: " + branch.getBranchId());
        System.out.println("Branch Name: " + branch.getBranchName().toUpperCase());
        System.out.println("Branch Code: " + branch.getBranchCode().toUpperCase());
        System.out.println("Branch Address: " + (branch.getAddress().toString()).toUpperCase());
        System.out.println("Opening Date: " + branch.getOpeningDate());
    }
}
