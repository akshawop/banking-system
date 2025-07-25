package me.akshawop.banking.sql;

import java.sql.Date;

import me.akshawop.banking.customtype.Address;
import me.akshawop.banking.sys.Account;
import me.akshawop.banking.sys.AccountStatus;
import me.akshawop.banking.sys.CardType;
import me.akshawop.banking.sys.Bank;
import me.akshawop.banking.sys.Branch;
import me.akshawop.banking.sys.BranchDAO;
import me.akshawop.banking.sys.Customer;
import me.akshawop.banking.sys.Transaction;

/**
 * Used for generating queries used in the program.
 */
public class SQLQueries {
    private static String str(String value) {
        if (value.length() == 0)
            return "NULL";
        return "'" + value + "'";
    }

    private static String address(Address address) {
        return str(address.street) + ", " + str(address.city) + ", " + str(address.district) + ", " + str(address.state)
                + ", " + str(address.pinCode);
    }

    private static String getFirstName(String name) {
        return name.substring(0, name.indexOf(" "));
    }

    private static String getMidName(String name) {
        if (name.indexOf(" ") != name.lastIndexOf(" "))
            return name.substring(name.indexOf(" ") + 1, name.lastIndexOf(" "));
        return "";
    }

    private static String getLastName(String name) {
        return name.substring(name.lastIndexOf(" ") + 1);
    }

    private static String name(String name) {
        return str(getFirstName(name)) + ", " + str(getMidName(name)) + ", " + str(getLastName(name));
    }

    // MAIN CODE
    // BankDAO
    public static String fetchBankFromDB() {
        return "SELECT * FROM bank LIMIT 1";
    }

    public static String addBankToDB(Bank bank) {
        return "INSERT INTO bank (bank_code, bank_name) VALUES (" + str(bank.getBankCode()) + ", "
                + str(bank.getBankName())
                + ")";
    }

    public static String updateBankInDB(Bank bank) {
        return "UPDATE bank SET bank_code = " + str(bank.getBankCode()) + ", bank_name = " + str(bank.getBankName())
                + " WHERE bank_id = 1";
    }

    public static String addBranchToDB(Branch branch) {
        String data = str(branch.getBranchCode()) + ", " + str(branch.getBranchName()) + ", "
                + address(branch.getAddress());
        return "INSERT INTO branch (branch_code, branch_name, street, city, district, state, pin_code) VALUES (" + data
                + ")";
    }

    public static String removeBranchFromDB(String branchCode) {
        return "DELETE FROM branch WHERE branch_code = " + str(branchCode);
    }

    public static String getBranchFromDB(String branchCode) {
        return "SELECT * FROM branch WHERE branch_code = " + str(branchCode);
    }

    public static String listBranches() {
        return "SELECT * FROM branch";
    }

    public static String listCustomers(int from, int limit) {
        return "SELECT * FROM customer WHERE customer_id >= " + from + " LIMIT " + limit;
    }

    public static String listAccounts(int from, int limit) {
        return "SELECT account.*, branch.branch_code, bank.bank_code FROM account JOIN branch ON account.branch = branch.branch_id CROSS JOIN bank WHERE account_number >= "
                + from + " LIMIT " + limit;
    }

    // BranchDAO
    public static String firstBranchInDB() {
        return "SELECT * FROM branch LIMIT 1";
    }

    public static String addCustomerToDB(Customer customer) {
        String data = name(customer.getName()) + ", " + str(customer.getAadhaar()) + ", " + str(customer.getPan())
                + ", "
                + address(customer.getAddress()) + ", " + str(customer.getPhone()) + ", " + str(customer.getEmail());

        return "INSERT INTO customer (first_name, mid_name, last_name, aadhaar, pan, street, city, district, state, pin_code, phone, email) VALUES ("
                + data + ")";
    }

    public static String getCustomerFromDB(int customerId) {
        return "SELECT * FROM customer WHERE customer_id = " + customerId;
    }

    public static String updateCustomerInDB(Customer customer) {
        String name = customer.getName();
        Address address = customer.getAddress();

        return "UPDATE customer SET "
                + "first_name = " + str(getFirstName(name)) + ", "
                + "mid_name = " + str(getMidName(name)) + ", "
                + "last_name = " + str(getLastName(name)) + ", "
                + "pan = " + str(customer.getPan()) + ", "
                + "street = " + str(address.street) + ", "
                + "city = " + str(address.city) + ", "
                + "district = " + str(address.district) + ", "
                + "state = " + str(address.state) + ", "
                + "pin_code = " + str(address.pinCode) + ", "
                + "phone = " + str(customer.getPhone()) + ", "
                + "email = " + str(customer.getEmail())
                + " WHERE customer_id = " + customer.getCustomerId();
    }

    public static String accessAccountInDB(int accountNumber) {
        return "SELECT account.*, branch.branch_code, bank.bank_code FROM account JOIN branch ON account.branch = branch.branch_id CROSS JOIN bank WHERE account_number = "
                + accountNumber;
    }

    public static String listAccounts(int branchId, int from, int limit) {
        return "SELECT account.*, branch.branch_code, bank.bank_code FROM account JOIN branch ON account.branch = branch.branch_id CROSS JOIN bank WHERE branch = "
                + branchId + " AND account_number >= " + from + " LIMIT " + limit;
    }

    // CustomerDAO
    public static String createAccountInDB(Account account) {
        String data = BranchDAO.fetchBranch(account.getIfscCode().substring(5)).getBranchId() + ", "
                + str(account.getType()) + ", "
                + account.getCustomerId()
                + ", " + (account.getNominee() == 0 ? "NULL" : account.getNominee()) + ", " + account.getBalance()
                + ", " + account.getMinBalance();

        return "INSERT INTO account (branch, account_type, customer, nominee, balance, min_balance) VALUES (" + data
                + ")";
    }

    public static String updateAccountInDB(Account account) {
        return "UPDATE account SET "
                + "branch_code = " + str(account.getIfscCode().substring(5)) + ", "
                + "account_status = " + str(account.getStatus())
                + " WHERE account_number = " + account.getAccountNumber();
    }

    public static String deleteAccountFromDB(int accountNumber) {
        return "DELETE FROM account WHERE account_number = " + accountNumber;
    }

    public static String listAccounts(int customerId) {
        return "SELECT account.*, branch.branch_code, bank.bank_code FROM account JOIN branch ON account.branch = branch.branch_id CROSS JOIN bank WHERE customer = "
                + customerId;
    }

    // AccountDAO
    public static String getAccountFromDB(int accountNumber) {
        return "SELECT account.*, branch.branch_code, bank.bank_code FROM account JOIN branch ON account.branch = branch.branch_id CROSS JOIN bank WHERE account_number = "
                + accountNumber;
    }

    public static String updateNomineeInDB(int nominee, int accountNumber) {
        return "UPDATE account SET nominee = " + nominee + " WHERE account_number = " + accountNumber;
    }

    public static String transferAccount(int branchId, int accountNumber) {
        return "UPDATE account SET branch = " + branchId + " WHERE account_number = " + accountNumber;
    }

    public static String updateAccountStatus(AccountStatus status, int accountNumber) {
        return "UPDATE account SET account_status = " + str(status.toString()) + " WHERE account_number = "
                + accountNumber;
    }

    public static String updateBalanceInDB(double balance, int accountNumber) {
        return "UPDATE account SET balance = " + balance + " WHERE account_number = " + accountNumber;
    }

    public static String getTransactionHistoryFromDB(int accountNumber, int offset, int limit) {
        return "SELECT * FROM account_transaction WHERE account = " + accountNumber + " LIMIT " + offset + ", " + limit;
    }

    // TransactionDAO
    public static String createTransactionInDB(Transaction transaction) {
        String data = transaction.getAccountNumber() + ", " + str(transaction.getDescription()) + ", "
                + str(transaction.getType()) + ", "
                + str(transaction.getMode()) + ", " + transaction.getAmount() + ", "
                + transaction.getBalance();

        return "INSERT INTO account_transaction (account, description, transaction_type, mode, amount, balance) VALUES ("
                + data
                + ")";
    }

    public static String fetchTransactionFromDB(Transaction transaction) {
        return "SELECT * FROM account_transaction WHERE "
                + "account = " + transaction.getAccountNumber() + " AND "
                + "description = " + str(transaction.getDescription()) + " AND "
                + "transaction_type = " + str(transaction.getType()) + " AND "
                + "mode = " + str(transaction.getMode()) + " AND "
                + "amount = " + transaction.getAmount() + " AND "
                + "balance = " + transaction.getBalance()
                + " ORDER BY transaction_id DESC LIMIT 1";
    }

    // Card
    public static String createCardInDB(String cardNumber, String cvv, CardType type, int accountNumber,
            Date expiryDate, String cardPin) {
        String data = str(cardNumber) + ", " + str(cvv) + ", " + str(type.toString()) + ", " + accountNumber + ", "
                + str(expiryDate.toString()) + ", " + str(cardPin);
        return "INSERT INTO card (card_number, cvv, card_type, account, expiry_date, card_pin) VALUES (" + data + ")";
    }

    public static String getCardFromDB(String cardNumber) {
        return "SELECT * FROM card WHERE card_number = " + str(cardNumber);
    }

    public static String getAllCardsFromDB(int accountNumber) {
        return "SELECT * FROM card WHERE account = " + accountNumber;
    }
}
