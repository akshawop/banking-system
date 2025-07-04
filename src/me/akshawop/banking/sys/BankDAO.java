package me.akshawop.banking.sys;

import java.sql.*;

import me.akshawop.banking.customtype.Address;
import me.akshawop.banking.sql.SQLQueries;

public class BankDAO {
    private Bank bank;

    protected BankDAO() {
        this.bank = fetchBank();
    }

    protected void showBankInfo() {
        System.out.println("\nBank: " + bank.getBankName().toUpperCase());
        System.out.println("Code: " + bank.getBankCode().toUpperCase() + "\n");
    }

    protected Bank fetchBank() {
        // fetchBank
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
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        return 1;
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
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        return 1;
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
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        return 1;
    }

    protected int removeBranch(String branchCode) {
        // removeBranch
        try {
            if (getBranch(branchCode) == null) {
                System.out.println("The branch '" + branchCode.toUpperCase() + "' doesn't exists!");
                return 1;
            }

            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.removeBranchFromDB(branchCode));
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

    protected Branch getBranch(String branchCode) {
        // getBranch
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getBranchFromDB(branchCode));

            if (!rs.next()) {
                con.close();
                return null;
            } else {
                int branchId = rs.getInt("branch_id");
                String branchName = rs.getString("branch_name");
                Address address = new Address(rs.getString("street"), rs.getString("city"), rs.getString("district"),
                        rs.getString("state"), rs.getString("pin_code"));
                Date openingDate = rs.getDate("opening_date");
                con.close();
                return new Branch(branchId, branchCode, branchName, address, openingDate);
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

    protected void listBranches() {
        // listBranches
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.listBranches());

            if (!rs.next()) {
                System.out.println("\nNo Branch found.\n");
                con.close();
            } else {
                System.out.println("\nBranch Code    Branch Name");
                System.out.println("-----------    -----------");
                do {
                    System.out.println(rs.getString("branch_code").toUpperCase() + "         "
                            + rs.getString("branch_name").toUpperCase());
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

    protected void listCustomers(int from, int limit) {
        // listCustomers
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.listCustomers(from, limit));

            if (!rs.next()) {
                System.out.println("\nNo Customer found.\n");
                con.close();
            } else {
                System.out.println("Customer(s) found!\n");
                do {
                    System.out.println("Customer ID: " + rs.getInt("customer_id"));
                    System.out.println("First Name: " + rs.getString("first_name").toUpperCase());
                    System.out.println("Middle Name: " + rs.getString("mid_name").toUpperCase());
                    System.out.println("Last Name: " + rs.getString("last_name").toUpperCase());
                    System.out.println("Aadhaar No.: " + rs.getString("aadhaar"));
                    System.out.println("PAN:    " + rs.getString("pan").toUpperCase());
                    System.out.println("Phone:    " + rs.getString("phone"));
                    System.out.println("Email:    " + rs.getString("email") + "\n");
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

    protected void listAccounts(int from, int limit) {
        // listAccounts
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.listCustomers(from, limit));

            if (!rs.next()) {
                System.out.println("\nNo Accounts found.\n");
                con.close();
            } else {
                System.out.println("Account(s) found!\n");
                do {
                    System.out.println("Account No.: " + rs.getInt("account_number"));
                    System.out.println("Branch: " + rs.getInt("branch"));
                    System.out.println("Customer ID: " + rs.getInt("customer"));
                    System.out.println("Type: " + rs.getString("account_type"));
                    System.out.println("Account Balance: $" + rs.getDouble("balance"));
                    System.out.println("Status: " + rs.getString("account_status"));
                    System.out.println("Opening Date: " + rs.getString("opening_date") + "\n");
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
