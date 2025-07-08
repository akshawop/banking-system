package me.akshawop.banking.sys;

import java.sql.*;

import me.akshawop.banking.cli.BankCLI;
import me.akshawop.banking.sql.SQLQueries;

/**
 * The Data Access Object of {@code Bank}
 * 
 * @see BankCLI
 * @see Bank
 */
public class BankDAO {
    private Bank bank;

    /**
     * Constructs a {@code BankDAO} if a Bank already exists in the Database.
     * <p>
     * Uses {@code fetchBank} method.
     * 
     * @see BankDAO#fetchBank
     */
    protected BankDAO() {
        this.bank = fetchBank();
    }

    /**
     * Gets the {@code Bank} object which is being currently used by the DAO.
     * 
     * @return the current {@code Bank} object
     */
    protected Bank getCurrentBank() {
        return bank;
    }

    /**
     * Prints the {@code Bank} information which is being currently used by the DAO.
     * 
     * @log Bank Name and Bank Code
     */
    protected void showBankInfo() {
        System.out.println("\nBank: " + bank.getBankName().toUpperCase());
        System.out.println("Code: " + bank.getBankCode().toUpperCase() + "\n");
    }

    /**
     * Fetch the Bank data from the Database if exists.
     * 
     * @return {@code Bank} object if exists; {@code null} if not
     * 
     * @log an error message if any error occurs
     */
    protected Bank fetchBank() {
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

    /**
     * Adds a new Bank to the Database. And assigns it to the DAO's {@code bank}.
     * 
     * @param bank The {@code Bank} object with the new Bank details
     * 
     * @return {@code 0} if successful; {@code 1} if unsuccessful
     * 
     * @log an error message if any error occurs
     */
    protected int addBank(Bank bank) {
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

    /**
     * Updates the pre-existing Bank's data in the Database. And assigns it to the
     * DAO's {@code bank}.
     * 
     * @param bank The {@code Bank} object with new data
     * 
     * @return {@code 0} if successful; {@code 1} if unsuccessful
     * 
     * @log an error message if any error occurs
     */
    protected int updateBankData(Bank bank) {
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

    /**
     * Adds a new Branch to the Database.
     * 
     * @param branch The {@code branch} object with the new Branch data
     * 
     * @return {@code 0} if successful; {@code 1} if unsuccessful
     * 
     * @log an error message if any error occurs
     */
    protected int addBranch(Branch branch) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.addBranchToDB(branch));
            con.close();
            return 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Duplicate Branch creation not allowed!");
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
     * Deletes a Branch from the Database.
     * 
     * @param branchCode The {@code String} Branch Code of the Branch to be deleted
     * 
     * @return {@code 0} if successful; {@code 1} if unsuccessful
     * 
     * @log an error message if any error occurs
     */
    protected int removeBranch(String branchCode) {
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

    /**
     * Gets the data of a Branch from the Database whose Branch Code is provided.
     * 
     * @param branchCode The {@code String} Branch Code of the Branch to be fetched
     * 
     * @return {@code Branch} if the Branch exists; {@code null} if doesn't
     * 
     * @log an error message if any error occurs
     */
    protected Branch getBranch(String branchCode) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getBranchFromDB(branchCode));

            if (!rs.next()) {
                con.close();
                return null;
            } else {
                Branch branch = new Branch(rs);
                con.close();
                return branch;
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
     * List all the Branches in the Database.
     * 
     * @log an error message if any error occurs
     */
    protected void listBranches() {
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

    /**
     * Lists the Customers data in the Database.
     * 
     * @param from  The {@code int} Customer ID as the upper limit from where the
     *              list should
     *              start
     * @param limit The number of Customers to list
     * 
     * @log an error message if any error occurs
     */
    protected void listCustomers(int from, int limit) {
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
                    new CustomerDAO(new Customer(rs)).printCustomerInfo();
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
     * Lists the Accounts data in the Database.
     * 
     * @param from  The {@code int} Account Number as the upper limit from where the
     *              list should start
     * @param limit The number of Accounts to list
     * 
     * @log an error message if any error occurs
     */
    protected void listAccounts(int from, int limit) {
        // listAccounts
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.listAccounts(from, limit));

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
}
