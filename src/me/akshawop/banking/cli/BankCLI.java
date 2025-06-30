package me.akshawop.banking.cli;

import java.util.Scanner;

import me.akshawop.banking.inputmodules.forms.*;
import me.akshawop.banking.sys.Bank;
import me.akshawop.banking.sys.BankDAO;

public final class BankCLI extends BankDAO {
    private static boolean doesBankAlreadyExists() {
        return BankDAO.getBank() != null;
    }

    private static void help() {
        System.out.println("        --HELP MENU--");
        System.out.println("[options -> descriptions]\n");
        System.out.println("exit -> logout");
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
        Scanner in = new Scanner(System.in);

        try {
            Bank bank;

            // checking for existing bank
            if (doesBankAlreadyExists()) {
                bank = BankDAO.getBank();
            } else {
                System.out.println("\nNo Bank exists, create a new one!");
                Bank newBank = CreateBankForm.fillUp(in); // form fill up
                if (newBank == null) {
                    System.out.println("Program stopped successfully");
                    System.exit(0);
                }
                BankDAO.addBank(newBank);
                bank = BankDAO.getBank();
            }

            // start
            BankDAO.bank = bank;
            System.out.println("\n    " + bank.getBankName().toUpperCase() + "\n");
            help();

            String input;
            do {
                System.out.print("bank> ");
                input = in.next();
                in.nextLine();

                switch (input) {
                    case "updatebank":
                        // update
                        BankDAO.updateBankData(bank);
                        break;
                    case "createbranch":
                        // add branch
                        BankDAO.addBranch(null);
                        break;
                    case "closebranch":
                        // remove branch
                        BankDAO.removeBranch(null);
                        break;
                    case "branchlogin":
                        // get branch
                        BankDAO.getBranch(null);
                        break;
                    case "listbranches":
                        // list branches
                        BankDAO.listBranches();
                        break;
                    case "listcustomers":
                        // list customers
                        BankDAO.listCustomers(0, 0);
                        break;
                    case "listaccounts":
                        // list accounts
                        BankDAO.listAccounts(0, 0);
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
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("\nProgram stopped Abnormally!");
        } finally {
            in.close();
        }
    }
}
