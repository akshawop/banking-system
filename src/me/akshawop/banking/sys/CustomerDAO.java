package me.akshawop.banking.sys;

import java.sql.*;

import me.akshawop.banking.sql.SQLQueries;

public class CustomerDAO {
    private Customer customer;

    public CustomerDAO(Customer customer) {
        this.customer = customer;
    }

    public Customer fetchCustomer(int customerId) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.fetchBankFromDB());

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
