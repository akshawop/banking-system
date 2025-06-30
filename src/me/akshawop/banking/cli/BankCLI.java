package me.akshawop.banking.cli;

import java.util.Scanner;

import me.akshawop.banking.inputmodules.forms.*;
import me.akshawop.banking.sys.Bank;
import me.akshawop.banking.sys.BankDAO;

public final class BankCLI extends BankDAO {
    static Scanner in = new Scanner(System.in);
    static Bank bank;
    static BankCLI dao = new BankCLI();

    BankCLI() {
        super();
    }

    BankCLI(Bank bank) {
        super(bank);
    }

    private static boolean doesBankAlreadyExists() {
        return dao.fetchBank() != null;
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
            bank = dao.fetchBank();
        }
    }

    private static void updateBank() {
        Bank newBank = UpdateBankForm.fillUp(in, bank);
        if (newBank != null) {
            if (dao.updateBankData(newBank) == 0) {
                bank = dao.fetchBank();
                System.out.println("\nBank update Successful!");
                return;
            } else
                System.err.println("\nUpdate unsuccessful!");
        } else
            System.out.println("\nUpdate cancelled!");
    }

    private static void createBranch() {
        
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

    public static void main(String[] args) {

        try {
            // start
            init();
            System.out.println("\n    " + bank.getBankName().toUpperCase() + "\n");
            help();

            String input;
            do {
                System.out.print("bank> ");
                input = in.nextLine().toLowerCase();
                // in.nextLine();

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
                        dao.removeBranch(null);
                        break;
                    case "branchlogin":
                        // get branch
                        dao.getBranch(null);
                        break;
                    case "listbranches":
                        // list branches
                        dao.listBranches();
                        break;
                    case "listcustomers":
                        // list customers
                        dao.listCustomers(0, 0);
                        break;
                    case "listaccounts":
                        // list accounts
                        dao.listAccounts(0, 0);
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
                    default:
                        System.out.println("invalid input!");
                }
            } while (!input.equals("exit"));
            System.out.println("Program stopped successfully");
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("\nProgram stopped Abnormally!");
        } finally {
            in.close();
        }
    }
}
