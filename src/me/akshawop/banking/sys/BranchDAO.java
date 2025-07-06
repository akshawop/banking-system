package me.akshawop.banking.sys;

import java.sql.*;

import me.akshawop.banking.sql.SQLQueries;

public class BranchDAO {
    private Branch branch;

    protected BranchDAO(String branchCode) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getBranchFromDB(branchCode));

            if (rs.next())
                branch = new Branch(rs);
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
    }

    protected Branch getCurrentBranch() {
        return branch;
    }

    public static boolean doesAnyBranchExists() {
        // checks for any pre-existing branch
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

    protected int addCustomer(Customer customer) {
        // add a new customer
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

    protected Customer getCustomer(int customerId) {
        return CustomerDAO.fetchCustomer(customerId);
    }

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

    protected Account accessAccount(int accountNumber) {
        return null;
    }

    protected void listAccounts(int from, int limit) {

    }

    protected void showBranchInfo() {
        // printBranchInfo
    }
}
