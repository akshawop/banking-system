package me.akshawop.banking.sys;

import java.sql.*;

import me.akshawop.banking.customtype.Address;
import me.akshawop.banking.sql.SQLQueries;

public class BranchDAO {
    private Branch branch;

    protected BranchDAO(String branchCode) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getBranchFromDB(branchCode));

            if (rs.next()) {
                int branchId = rs.getInt("branch_id");
                String branchName = rs.getString("branch_name");
                Address address = new Address(rs);
                Date openingDate = rs.getDate("opening_date");
                branch = new Branch(branchId, branchCode, branchName, address, openingDate);
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

    protected void addCustomer(Customer customer) {

    }

    protected Customer getCustomer(int customerId) {
        return null;
    }

    protected void updateCustomer(int customerId) {

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
