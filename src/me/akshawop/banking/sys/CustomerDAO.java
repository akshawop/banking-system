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
    public CustomerDAO(Customer customer) {
        this.customer = customer;
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
    static Customer fetchCustomer(int customerId) {
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
    void printCustomerInfo() {
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName().toUpperCase());
        System.out.println("Aadhaar No.: " + customer.getAadhaar());
        System.out.println("PAN: " + customer.getPan().toUpperCase());
        System.out.println("Phone: " + customer.getPhone());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Registered on: " + customer.getRegistrationDate());
    }

    void createAccount(Account account) {

    }

    void updateAccount(int accountNumber) {

    }

    void closeAccount(int accountNumber) {

    }

    void listAccounts() {

    }
}
