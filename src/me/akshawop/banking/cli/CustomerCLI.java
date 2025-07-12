package me.akshawop.banking.cli;

import java.util.Scanner;

import me.akshawop.banking.inputmodules.forms.NewAccountForm;
import me.akshawop.banking.sys.Account;
import me.akshawop.banking.sys.Branch;
import me.akshawop.banking.sys.Customer;
import me.akshawop.banking.sys.CustomerDAO;
import me.akshawop.banking.util.ClearScreen;

final class CustomerCLI extends CustomerDAO {
    private static Scanner in;
    private static Branch branch;
    private static CustomerCLI dao;

    private CustomerCLI(Customer customer) {
        super(customer);
    }

    private static void init() {
        System.out.println("\nLogged in as " + dao.getCurrentCustomer().getName().toUpperCase() + " with Customer ID: "
                + dao.getCurrentCustomer().getCustomerId() + "\n");
    }

    private static void openAccount() {
        String ifscCode = branch.getBankCode() + '0' + branch.getBranchCode();
        Account newAccount = NewAccountForm.fillUp(in, dao.getCurrentCustomer().getCustomerId(), ifscCode);
        if (newAccount != null) {
            if (dao.createAccount(newAccount) == 0)
                System.out.println("\nNew " + newAccount.getType() + " Account opened Successfully!\n");
            else
                System.err.println("\nNew Account creation unsuccessful!\n");
        } else
            System.out.println("\nNew Account creation cancelled!\n");
    }

    private static void updateAccount() {
        // TODO: updateAccount
        throw new UnsupportedOperationException("Unimplemented method 'updateAccount'");
    }

    private static void closeAccount() {
        // TODO: closeAccount
        throw new UnsupportedOperationException("Unimplemented method 'closeAccount'");
    }

    private static void help() {
        System.out.println("        --HELP MENU--");
        System.out.println("[options -> descriptions]\n");
        System.out.println("exit -> logout");
        System.out.println("info -> Get current Customer's information");
        System.out.println("openaccount -> Open an Account");
        System.out.println("updateaccount -> Update existing Account data");
        System.out.println("closeAccount -> Close an Account");
        System.out.println("listaccounts -> List all the Accounts of this Customer");
        System.out.println("help -> To see this help menu again");
        System.out.println("clear -> To clear screen\n");
    }

    private static void selectOption(String input) {
        switch (input) {
            case "openaccount":
                // open account
                openAccount();
                break;

            case "updateaccount":
                // update account
                updateAccount();
                break;

            case "closeaccount":
                // close account
                closeAccount();
                break;

            case "listaccounts":
                // list accounts
                dao.listAccounts();
                break;

            case "info":
                // print customer info
                System.out.println();
                dao.printCustomerInfo();
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

    public static void run(Scanner branchIn, Branch accessBranch, Customer customer) {
        // start
        try {
            in = branchIn;
            branch = accessBranch;
            dao = new CustomerCLI(customer);
            init();
            help();

            String input;
            do {
                System.out.print("customer> ");
                input = in.nextLine().toLowerCase().trim();

                selectOption(input);
            } while (!input.equals("exit"));
            System.out.println("Logged out of Customer!\n");
        } catch (Exception e) {
            System.err.println("An Error Occurred!\n" + e);
            System.err.println("\nProgram stopped Abnormally!");
        }
    }
}
