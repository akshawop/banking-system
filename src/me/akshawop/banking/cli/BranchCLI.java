package me.akshawop.banking.cli;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import me.akshawop.banking.cli.inputmodules.forms.NewCustomerForm;
import me.akshawop.banking.cli.inputmodules.forms.UpdateCustomerForm;
import me.akshawop.banking.sys.Account;
import me.akshawop.banking.sys.AccountDAO;
import me.akshawop.banking.sys.Branch;
import me.akshawop.banking.sys.BranchDAO;
import me.akshawop.banking.sys.Card;
import me.akshawop.banking.sys.Customer;
import me.akshawop.banking.sys.Transaction;
import me.akshawop.banking.sys.TransactionDAO;
import me.akshawop.banking.util.ClearScreen;
import me.akshawop.banking.util.InputChecker;

public final class BranchCLI extends BranchDAO {
    private static Scanner in;
    private static Branch branch;
    private static BranchCLI dao;

    private static String depositDescription;
    private static String withdrawDescription;

    private BranchCLI(String branchCode) {
        super(branchCode);
    }

    private static int init() {
        if (!doesAnyBranchExists()) {
            System.out.println("No Branch found!");
            System.out.println("First create one from the Bank.");
            return 1;
        }

        BankCLI.selectOption("listbranches");
        System.out.println("--Select a Branch from above list--\n");
        String branchCode;
        do {
            System.out.print("Branch Code: ");
            branchCode = in.nextLine().toLowerCase().trim();

            if (InputChecker.checkBranchCode(branchCode, 'c')) {
                dao = new BranchCLI(branchCode);
                if (dao.getCurrentBranch() == null) {
                    System.out.println("Entered Branch Code doesn't exist. Try Again!\n");
                    continue;
                }
            } else {
                System.out.println("Invalid Branch Code entered. Try Again!\n");
                continue;
            }
            branch = dao.getCurrentBranch();
            depositDescription = "BY CASH in " + branch.getBranchName().toUpperCase() + " branch";
            withdrawDescription = "BY CASH from " + branch.getBranchName().toUpperCase() + " branch";
            break;
        } while (true);
        System.out.println("\nLogin into " + branch.getBranchName().toUpperCase() + " Branch successful!\n");
        return 0;
    }

    private static void addCustomer() {
        Customer newCustomer = NewCustomerForm.fillUp(in);
        if (newCustomer != null) {
            if (dao.addCustomer(newCustomer) == 0) {
                System.out.println("\nNew Customer added Successfully!\n");
                System.out.println(
                        "CUSTOMER ID: " + BranchCLI.getCustomerId(newCustomer.getAadhaar()) + " has been assigned!\n");
            } else
                System.out.println("\nNew Customer entry unsuccessful!\n");
        } else
            System.out.println("\nNew Customer entry cancelled!\n");
    }

    private static void customerLogin() {
        try {
            System.out.print("Customer ID: ");
            int customerId = Integer.parseInt(in.nextLine().trim());

            if (customerId > 0) {
                Customer customer = dao.getCustomer(customerId);
                if (customer != null) {
                    CustomerCLI.run(in, branch, customer);
                } else
                    System.out.println("\nCustomer with ID '" + customerId + "' DOESN'T exist!\n");
            } else
                System.out.println("\nInvalid Customer ID entered!\n");
        } catch (Exception e) {
            System.out.println("\nInvalid Input\n");
        }
    }

    private static void updateCustomer() {
        try {
            System.out.print("Enter the Customer ID: ");
            int customerId = Integer.parseInt(in.nextLine().trim());

            if (customerId > 0) {
                Customer customer = dao.getCustomer(customerId);
                if (customer != null) {
                    Customer newCustomer = UpdateCustomerForm.fillUp(in, customer);
                    if (newCustomer != null) {
                        if (dao.updateCustomer(newCustomer) == 0)
                            System.out.println("\nCustomer data updated Successfully!\n");
                        else
                            System.out.println("\nUpdate unsuccessful!\n");
                    } else
                        System.out.println("\nUpdate canceled!\n");
                } else
                    System.out.println("\nCustomer with ID '" + customerId + "' DOESN'T exist!\n");
            } else
                System.out.println("\nInvalid Customer ID entered!\n");
        } catch (Exception e) {
            System.out.println("\nInvalid Input\n");
        }
    }

    private static void accountLogin() {
        System.out.print("\nAccount Number(Last Digits of the Account Number after the zeros): ");
        int accountNumber;
        try {
            accountNumber = Integer.parseInt(in.nextLine().trim());
        } catch (Exception e) {
            accountNumber = 0;
        }
        if (accountNumber > 0) {
            Account account = AccountDAO.fetchAccount(accountNumber);
            if (account != null && (account.getIfscCode().substring(5).equalsIgnoreCase(branch.getBranchCode()))) {
                AccountCLI.run(in, account);
            } else {
                System.out.println("\nAccount login unsuccessful!");
                System.out.println("This Account doesn't belongs to this Branch!\n");
            }
        } else
            System.out.println("\nInvalid Account Number!\n");
    }

    private static void deposit() {
        try {
            System.out.print("\nAccount Number(Last Digits of the Account Number after the zeros): ");
            Account account = AccountDAO.fetchAccount(Integer.parseInt(in.nextLine().trim()));

            System.out.println();
            new AccountCLI(account).printAccountInfo();
            System.out.println();

            if (account == null) {
                System.out.println("\nNo such account found!\n");
                return;
            }

            System.out.println("***Enter a negative, zero or NaN value to cancel***");
            System.out.print("Enter the amount> $");
            double amount = Double.parseDouble(in.nextLine().trim());

            if (amount <= 0)
                throw new Exception();

            Transaction tr = dao.deposit(account, depositDescription, amount);
            System.out.println("\n---Transaction Details---");
            TransactionDAO.printTransactionForATM(tr);
            System.out.println("\nTransaction Completed successfully!\n");
        } catch (Exception e) {
            System.out.println("\nDeposit Canceled!\n");
        }
    }

    private static void withdraw() {
        try {
            System.out.print("\nAccount Number(Last Digits of the Account Number after the zeros): ");
            Account account = AccountDAO.fetchAccount(Integer.parseInt(in.nextLine().trim()));

            System.out.println();
            new AccountCLI(account).printAccountInfo();
            System.out.println();

            if (account == null) {
                System.out.println("\nNo such account found!\n");
                return;
            }

            System.out.println("***Enter a negative, zero or NaN value to cancel***");
            System.out.print("Enter the amount> $");
            double amount = Double.parseDouble(in.nextLine().trim());

            if (amount <= 0)
                throw new Exception();

            Transaction tr = dao.withdraw(account, withdrawDescription, amount);
            System.out.println("\n---Transaction Details---");
            TransactionDAO.printTransactionForATM(tr);
            System.out.println("\nTransaction Completed successfully!\n");
        } catch (Exception e) {
            System.out.println("\nWithdrawal Canceled!\n");
        }
    }

    private static void issueDebitCard() {
        System.out.print("\nAccount Number(Last Digits of the Account Number after the zeros): ");
        int accountNumber;
        try {
            accountNumber = Integer.parseInt(in.nextLine().trim());
        } catch (Exception e) {
            accountNumber = 0;
        }
        if (accountNumber > 0) {
            Account account = AccountDAO.fetchAccount(accountNumber);
            if (account != null && (account.getIfscCode().substring(5).equalsIgnoreCase(branch.getBranchCode()))) {
                Card card = new AccountCLI(account).generateNewDebitCard(3);

                // serialize the card
                try {
                    if (card != null)
                        System.out.println("Card Issued Successfully!");
                    else {
                        System.out.println("\nCan't issue the Card!");
                        throw new Exception();
                    }

                    Path path;
                    do {
                        System.out.print("Enter the location to where to save the card: ");
                        String location = in.nextLine().trim();

                        // check if the directory exists
                        try {
                            path = Paths.get(location).normalize();
                        } catch (InvalidPathException e) {
                            System.out.println("Enter a valid path!\n");
                            continue;
                        }
                        if (!Files.isDirectory(path)) {
                            System.out.println("Enter a valid path!\n");
                            continue;
                        }
                        break;
                    } while (true);

                    // serialize and store it in a file with the filename as <accountNumber>.ser at
                    // the given path
                    System.out.println(path);
                    ObjectOutputStream objOut = new ObjectOutputStream(
                            new FileOutputStream(path + "/" + accountNumber + ".ser"));
                    objOut.writeObject(card);
                    System.out.println("\nCard saved Successfully!\n");
                    objOut.close();
                } catch (IOException e) {
                    System.out.println("\nAn error occurred, card saving failed!\n");
                    System.err.println(e);
                } catch (Exception e) {
                    System.out.println("\nAn error occurred!\n");
                    System.err.println("Error: something went wrong!");
                    System.err.println("More info:\n" + e);
                }
            } else {
                System.out.println("\nDebit Card issue unsuccessful!");
                System.out.println("This Account doesn't belongs to this Branch!\n");
            }
        } else {
            System.out.println("\nDebit Card issue unsuccessful!");
            System.out.println("\nInvalid Account Number!\n");
        }
    }

    private static void listAccounts() {
        try {
            System.out.print("\nList from(Last Digits of the Account Number after the zeros): ");
            int from = Integer.parseInt(in.nextLine().trim());
            if (from < 1) {
                System.out.println("Invalid Account Number!\n");
                return;
            }
            System.out.print("Number of Accounts to list: ");
            int limit = Integer.parseInt(in.nextLine().trim());
            if (limit < 1) {
                System.out.println("Invalid limit, should be greater than 0!\n");
                return;
            }
            dao.listAccounts(from, limit);
        } catch (Exception e) {
            System.err.println("\nInvalid Input\n");
        }
    }

    private static void help() {
        System.out.println("        --HELP MENU--");
        System.out.println("[options -> descriptions]\n");
        System.out.println("exit -> logout");
        System.out.println("info -> Get current Branch Info\n");

        System.out.println("--Customer Options--");
        System.out.println("addcustomer -> Add a new Customer");
        System.out.println("customerlogin -> Login to a Customer's ID");
        System.out.println("updatecustomer -> Update existing Customer's data\n");

        System.out.println("--Account Options--");
        System.out.println("accountlogin -> Login to an Account");
        System.out.println("deposit -> Deposit money to an Account");
        System.out.println("withdraw -> Withdraw money from an Account");
        System.out.println("issuedebitcard -> Issue a NEW Debit Card to an Account");
        System.out.println("listaccounts -> List Accounts in the current Branch\n");

        System.out.println("--Other Options--");
        System.out.println("help -> To see this help menu again");
        System.out.println("clear -> To clear screen\n");
    }

    private static void selectOption(String input) {
        switch (input) {
            case "addcustomer":
                // add customer
                addCustomer();
                break;

            case "customerlogin":
                // get customer
                customerLogin();
                break;

            case "updatecustomer":
                // update customer
                updateCustomer();
                break;

            case "accountlogin":
                // get account
                accountLogin();
                break;

            case "deposit":
                // deposit amount to an account
                deposit();
                break;

            case "withdraw":
                // withdraw amount to an account
                withdraw();
                break;

            case "issuedebitcard":
                // generate a new debit card to an account
                issueDebitCard();
                break;

            case "listaccounts":
                // list accounts
                listAccounts();
                break;

            case "info":
                // print branch info
                System.out.println();
                dao.printBranchInfo();
                System.out.println();
                break;

            case "help":
                // show help menu
                help();
                break;

            case "clear":
                // clear the screen
                ClearScreen.clearConsole();
                break;

            case "exit":
                // exit
                break;

            case "":
                // retake input
                break;

            default:
                System.out.println("Invalid input!");
        }
    }

    public static void main(String[] args) {
        try {
            in = new Scanner(System.in);
            if (args.length != 0) {
                selectOption(args[0].toLowerCase().trim());
                return;
            }

            // start
            ClearScreen.clearConsole();
            if (init() == 1)
                System.exit(0);

            help();

            String input;
            do {
                System.out.print("branch> ");
                input = in.nextLine().toLowerCase().trim();

                selectOption(input);
            } while (!input.equals("exit"));
            System.out.println("Program stopped successfully");
        } catch (Exception e) {
            System.err.println("An Error Occurred!\n" + e);
            System.err.println("\nProgram stopped Abnormally!");
        } finally {
            in.close();
        }
    }

    public static void run(Scanner bankIn) {
        try {
            in = bankIn;
            // start
            if (init() == 1)
                return;
            help();

            String input;
            do {
                System.out.print("branch> ");
                input = in.nextLine().toLowerCase().trim();

                selectOption(input);
            } while (!input.equals("exit"));
            System.out.println("Logged out of Branch!\n");
        } catch (Exception e) {
            System.err.println("An Error Occurred!\n" + e);
            System.err.println("\nProgram stopped Abnormally!");
        }
    }
}
