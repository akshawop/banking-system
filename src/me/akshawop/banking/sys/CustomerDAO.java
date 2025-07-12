package me.akshawop.banking.sys;

import java.sql.*;

import me.akshawop.banking.sql.SQLQueries;

/**
 * The Data Access Object of {@code Customer}
 * 
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

    protected void updateAccount(int accountNumber) {
        // TODO: updateAccount
        throw new UnsupportedOperationException("Unimplemented method 'updateAccount'");
    }

    protected void closeAccount(int accountNumber) {
        // TODO: closeAccount
        throw new UnsupportedOperationException("Unimplemented method 'closeAccount'");
    }

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
