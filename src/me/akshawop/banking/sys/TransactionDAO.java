package me.akshawop.banking.sys;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import me.akshawop.banking.sql.SQLQueries;

public class TransactionDAO {
    public static void printTransaction(Transaction tr) {
        System.out.println("Transaction ID: " + tr.getTransactionId());
        System.out.println("Account: " + tr.getAccountNumber());
        System.out.println("Description: " + tr.getDescription());
        System.out.println("Type: " + tr.getType());
        System.out.println("Mode: " + tr.getMode());

        if (tr.getType().equals(TransactionType.DEBIT.toString()))
            System.out.println("Debit Amount: $" + tr.getAmount());
        else
            System.out.println("Credit Amount: $" + tr.getAmount());

        System.out.println("Balance: $" + tr.getBalance());
        System.out.println("Timestamp: " + tr.getTimestamp());
    }

    public static void printTransactionForATM(Transaction tr) {
        System.out.println("Transaction ID: " + tr.getTransactionId());
        System.out.println("Description: " + tr.getDescription());
        System.out.println("Type: " + tr.getType());
        System.out.println("Mode: " + tr.getMode());

        if (tr.getType().equals(TransactionType.DEBIT.toString()))
            System.out.println("Debit Amount: $" + tr.getAmount());
        else
            System.out.println("Credit Amount: $" + tr.getAmount());

        System.out.println("Balance: $" + tr.getBalance());
        System.out.println("Timestamp: " + tr.getTimestamp());
    }

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

    public static Transaction getTransaction(Transaction transaction) {
        // TODO: getTransaction
        throw new UnsupportedOperationException("Unimplemented method 'getTransaction'");
    }
}
