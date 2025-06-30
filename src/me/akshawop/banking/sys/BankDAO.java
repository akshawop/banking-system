package me.akshawop.banking.sys;

import java.sql.*;

import me.akshawop.banking.cli.BankCLI;
import me.akshawop.banking.sql.SQLQueries;

public sealed class BankDAO permits BankCLI {
    protected static Bank bank;

    protected static Bank getBank() {
        // getBank
        Connection con = DB.connect();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getBank());
            con.close();
            if (rs.isClosed()) {
                return null;
            } else {
                rs.next();
                String bankCode = rs.getString("bank_code");
                String bankName = rs.getString("bank_name");
                return new Bank(bankCode, bankName);
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

    protected static void addBank(Bank bank) {
        // addBankToDB
    }

    protected static void updateBankData(Bank bank) {
        // updateBankData
    }

    protected static void addBranch(Branch branch) {
        // addBranchToDB
    }

    protected static void removeBranch(String branchCode) {
        // removeBranch
    }

    protected static Branch getBranch(String branchCode) {
        // getBranch
        return null;
    }

    protected static void listBranches() {
        // listBranches
    }

    protected static void listCustomers(int from, int limit) {

    }

    protected static void listAccounts(int from, int limit) {

    }
}
