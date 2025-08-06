package me.akshawop.banking.sys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import me.akshawop.banking.sql.SQLQueries;

public class TransactionDAO {
    /**
     * Prints the {@code Transaction} information.
     * 
     * @param transaction The {@code Transaction} object
     * 
     * @log every field of the {@code Transaction} object
     */
    public static void printTransaction(Transaction transaction) {
        System.out.println("Transaction ID: " + transaction.getTransactionId());
        System.out.println("Account: " + transaction.getAccountNumber());
        System.out.println("Description: " + transaction.getDescription());
        System.out.println("Type: " + transaction.getType());
        System.out.println("Mode: " + transaction.getMode());

        if (transaction.getType().equals(TransactionType.DEBIT))
            System.out.println("Debit Amount: $" + transaction.getAmount());
        else
            System.out.println("Credit Amount: $" + transaction.getAmount());

        System.out.println("Balance: $" + transaction.getBalance());
        System.out.println("Timestamp: " + transaction.getTimestamp());
    }

    /**
     * Prints the {@code Transaction} information for the ATM CLI.
     * 
     * @param transaction The {@code Transaction} object
     * 
     * @log fields of the {@code Transaction} object for ATM CLI
     */
    public static void printTransactionForATM(Transaction transaction) {
        System.out.println("Transaction ID: " + transaction.getTransactionId());
        System.out.println("Description: " + transaction.getDescription());
        System.out.println("Type: " + transaction.getType());
        System.out.println("Mode: " + transaction.getMode());

        if (transaction.getType().equals(TransactionType.DEBIT))
            System.out.println("Debit Amount: $" + transaction.getAmount());
        else
            System.out.println("Credit Amount: $" + transaction.getAmount());

        System.out.println("Balance: $" + transaction.getBalance());
        System.out.println("Timestamp: " + transaction.getTimestamp());
    }

    /**
     * Creates a new Transaction entry in the database.
     * 
     * @param transaction The premature {@code Transaction} object
     * 
     * @return {@code 0} if process successful; {@code 1} if not
     * 
     * @log an error message if any error occurs
     */
    public static int createTransaction(Transaction transaction) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            st.executeUpdate(SQLQueries.createTransactionInDB(transaction));
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
     * Fetches the provided Transaction entry in the database.
     * 
     * @param transaction The premature {@code Transaction} object
     * 
     * @return {@code Transaction} if process successful; {@code null} if not
     * 
     * @log an error message if any error occurs
     */
    public static Transaction getTransaction(Transaction transaction) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(SQLQueries.fetchTransactionFromDB(transaction));
            rs.next();
            transaction = new Transaction(rs);
            con.close();

            return transaction;
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
}
