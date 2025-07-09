package me.akshawop.banking.cli;

import java.util.Scanner;

import me.akshawop.banking.inputmodules.forms.NewCustomerForm;
import me.akshawop.banking.inputmodules.forms.UpdateCustomerForm;
import me.akshawop.banking.sys.Branch;
import me.akshawop.banking.sys.BranchDAO;
import me.akshawop.banking.sys.Customer;
import me.akshawop.banking.util.InputChecker;

public final class BranchCLI extends BranchDAO {
    private static Scanner in;
    private static Branch branch;
    private static BranchCLI dao;

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
            break;
        } while (true);
        System.out.println("\nLogin into '" + branch.getBranchName().toUpperCase() + "' Branch successful!\n");
        return 0;
    }

    private static void addCustomer() {
        Customer newCustomer = NewCustomerForm.fillUp(in);
        if (newCustomer != null) {
            if (dao.addCustomer(newCustomer) == 0)
                System.out.println("\nNew Customer added Successfully!\n");
            else
                System.err.println("\nNew Customer entry unsuccessful!\n");
        } else
            System.out.println("\nNew Customer entry cancelled!\n");
    }

    private static void customerLogin() {
        System.out.print("Customer ID: ");
        int customerId = in.nextInt();
        in.nextLine();

        if (customerId > 0) {
            Customer customer = dao.getCustomer(customerId);
            if (customer != null) {
                // TODO: CustomerCLI
                throw new UnsupportedOperationException("Unimplemented method 'customerLogin'");
                // System.out.println("\nLogout successful.\n");
            } else
                System.out.println("\nCustomer with ID '" + customerId + "' DOESN'T exist!\n");
        } else
            System.out.println("\nInvalid Customer ID entered!\n");
    }

    private static void updateCustomer() {
        System.out.print("Enter the Customer ID: ");
        int customerId = in.nextInt();
        in.nextLine();

        if (customerId > 0) {
            Customer customer = dao.getCustomer(customerId);
            if (customer != null) {
                Customer newCustomer = UpdateCustomerForm.fillUp(in, customer);
                if (newCustomer != null) {
                    if (dao.updateCustomer(newCustomer) == 0)
                        System.out.println("\nCustomer data updated Successfully!\n");
                    else
                        System.err.println("\nUpdate unsuccessful!\n");
                } else
                    System.err.println("\nUpdate canceled!\n");
            } else
                System.out.println("\nCustomer with ID '" + customerId + "' DOESN'T exist!\n");
        } else
            System.out.println("\nInvalid Customer ID entered!\n");
    }

    private static void accountLogin() {
        // TODO: AccountCLI
        throw new UnsupportedOperationException("Unimplemented method 'accountLogin'");
        // System.out.println("\nLogout successful.\n");
    }

    private static void listAccounts() {
        System.out.print("\nList from(Last Digits of the Account Number after the zeros): ");
        int from = in.nextInt();
        in.nextLine();
        if (from < 1) {
            System.out.println("Invalid Account Number!\n");
            return;
        }
        System.out.print("Number of Accounts to list: ");
        int limit = in.nextInt();
        in.nextLine();
        if (limit < 1) {
            System.out.println("Invalid limit, should be greater than 0!\n");
            return;
        }
        dao.listAccounts(from, limit);
    }

    private static void help() {
        System.out.println("        --HELP MENU--");
        System.out.println("[options -> descriptions]\n");
        System.out.println("exit -> logout");
        System.out.println("info -> Get current Branch Info");
        System.out.println("addcustomer -> Add a new Customer");
        System.out.println("customerlogin -> Login to a Customer's ID");
        System.out.println("updatecustomer -> Update existing Customer's data");
        System.out.println("accountlogin -> Login to an Account");
        System.out.println("listaccounts -> List Accounts in the current Branch");
        System.out.println("help -> To see this help menu again\n");
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
