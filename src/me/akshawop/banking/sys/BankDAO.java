package me.akshawop.banking.sys;

import java.sql.*;

import me.akshawop.banking.cli.BankCLI;
import me.akshawop.banking.sql.SQLQueries;

public sealed class BankDAO permits BankCLI {
    private Bank bank;

    protected BankDAO() {
        Bank dbBank = fetchBank();
        if (dbBank != null)
            this.bank = dbBank;
    }

    protected BankDAO(Bank bank) {
        this.bank = bank;
    }

    protected void setBank(Bank bank) {
        this.bank = bank;
    }

    protected void showBankInfo() {
        System.out.println("\nBank: " + bank.getBankName().toUpperCase());
        System.out.println("Bank Code: " + bank.getBankCode() + "\n");
    }

    protected Bank fetchBank() {
        // getBank
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.fetchBankFromDB());

            if (!rs.next()) {
                con.close();
                return null;
            } else {
                String bankCode = rs.getString("bank_code");
                String bankName = rs.getString("bank_name");
                con.close();
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

    protected int addBank(Bank bank) {
        // addBankToDB
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.addBankToDB(bank));
            con.close();
            this.bank = fetchBank();
            return 0;
        } catch (SQLTimeoutException e) {
            System.err.println("Error: Database timeout!");
            System.err.println("More info:\n" + e);
            return 1;
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
            System.err.println("More info:\n" + e);
            return 1;
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
            return 1;
        }
    }

    protected int updateBankData(Bank bank) {
        // updateBankData
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.updateBankInDB(bank));
            con.close();
            this.bank = fetchBank();
            return 0;
        } catch (SQLTimeoutException e) {
            System.err.println("Error: Database timeout!");
            System.err.println("More info:\n" + e);
            return 1;
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
            System.err.println("More info:\n" + e);
            return 1;
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
            return 1;
        }
    }

    protected int addBranch(Branch branch) {
        // addBranchToDB
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.addBranchToDB(branch));
            con.close();
            return 0;
        } catch (SQLTimeoutException e) {
            System.err.println("Error: Database timeout!");
            System.err.println("More info:\n" + e);
            return 1;
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
            System.err.println("More info:\n" + e);
            return 1;
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
            return 1;
        }
    }

    protected void removeBranch(String branchCode) {
        // removeBranch
    }

    protected Branch getBranch(String branchCode) {
        // getBranch
        return null;
    }

    protected void listBranches() {
        // listBranches
    }

    protected void listCustomers(int from, int limit) {

    }

    protected void listAccounts(int from, int limit) {

    }

}
