package me.akshawop.banking.cli;

import java.util.Scanner;

import me.akshawop.banking.inputmodules.forms.*;
import me.akshawop.banking.sys.Bank;
import me.akshawop.banking.sys.BankDAO;
import me.akshawop.banking.sys.Branch;
import me.akshawop.banking.util.InputChecker;

public final class BankCLI extends BankDAO {
    private static Scanner in = new Scanner(System.in);
    private static Bank bank;
    private static BankCLI dao = new BankCLI();

    private BankCLI() {
        super();
    }

    private static boolean doesBankAlreadyExists() {
        return dao.getCurrentBank() != null;
    }

    private static void init() {
        if (doesBankAlreadyExists()) {
            bank = dao.fetchBank();
        } else {
            System.out.println("\nNo Bank exists, create a new one!");
            Bank newBank = CreateBankForm.fillUp(in); // form fill up
            if (newBank == null) {
                System.out.println("Program stopped successfully");
                System.exit(0);
            }
            dao.addBank(newBank);
            bank = dao.getCurrentBank();
        }
        System.out.println("\n    " + bank.getBankName().toUpperCase() + "\n");
    }

    private static void updateBank() {
        Bank newBank = UpdateBankForm.fillUp(in, bank);
        if (newBank != null) {
            if (dao.updateBankData(newBank) == 0) {
                bank = dao.getCurrentBank();
                System.out.println("\nBank update Successful!\n");
            } else
                System.err.println("\nUpdate unsuccessful!\n");
        } else
            System.out.println("\nUpdate cancelled!\n");
    }

    private static void createBranch() {
        Branch newBranch = CreateBranchForm.fillUp(in);
        if (newBranch != null) {
            if (dao.addBranch(newBranch) == 0)
                System.out.println("\nBranch created Successfully!\n");
            else
                System.err.println("\nBranch creation unsuccessful!\n");
        } else
            System.out.println("\nBranch creation cancelled!\n");
    }

    // TODO: add the verify before closing and accounts transfer to another branch
    // feature while branch closing process
    private static void closeBranch() {
        System.out.print("\nBranch Code: ");
        String branchCode = in.nextLine().toLowerCase().trim();
        if (InputChecker.checkBranchCode(branchCode, 'c')) {
            if (dao.removeBranch(branchCode) == 0)
                System.out.println("\nBranch closed successfully!\n");
            else
                System.out.println("\nBranch closing unsuccessful!\n");
        } else
            System.out.println("\nInvalid Branch Code!\n");
    }

    private static void branchLogin() {
        BranchCLI.run(in);
    }

    private static void listCustomers() {
        System.out.print("\nList from(Customer ID): ");
        int from = in.nextInt();
        in.nextLine();
        if (from < 1) {
            System.out.println("Invalid Customer ID!\n");
            return;
        }
        System.out.print("Number of Customers to list: ");
        int limit = in.nextInt();
        in.nextLine();
        if (limit < 1) {
            System.out.println("Invalid limit, should be greater than 0!\n");
            return;
        }
        dao.listCustomers(from, limit);
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
        System.out.println("info -> Get Bank Info");
        System.out.println("updatebank -> Update the bank info");
        System.out.println("createbranch -> Create a new Branch");
        System.out.println("closebranch -> Close an existing Branch");
        System.out.println("branchlogin -> Login to an existing Branch");
        System.out.println("listbranches -> List all Branches");
        System.out.println("listcustomers -> List existing Customers");
        System.out.println("listaccounts -> List existing Accounts");
        System.out.println("help -> To see this help menu again\n");
    }

    public static void selectOption(String input) {
        switch (input) {
            case "updatebank":
                // update bank
                updateBank();
                break;

            case "createbranch":
                // add branch
                createBranch();
                break;

            case "closebranch":
                // remove branch
                closeBranch();
                break;

            case "branchlogin":
                // get branch
                branchLogin();
                break;

            case "listbranches":
                // list branches
                dao.listBranches();
                break;

            case "listcustomers":
                // list customers
                listCustomers();
                break;

            case "listaccounts":
                // list accounts
                listAccounts();
                break;

            case "info":
                // print bank info
                dao.showBankInfo();
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
            if (args.length != 0) {
                selectOption(args[0].toLowerCase().trim());
                return;
            }

            // start
            init();
            help();

            String input;
            do {
                System.out.print("bank> ");
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
}
