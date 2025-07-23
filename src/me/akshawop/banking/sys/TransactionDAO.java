package me.akshawop.banking.sys;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import me.akshawop.banking.sql.SQLQueries;

public class TransactionDAO {
    public static void printTransaction(Transaction transaction) {
        // TODO: printTransaction
        throw new UnsupportedOperationException("Unimplemented method 'printTransaction'");
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
