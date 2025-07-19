package me.akshawop.banking.cli;

import java.util.Scanner;

import me.akshawop.banking.sys.Account;
import me.akshawop.banking.sys.AccountDAO;
import me.akshawop.banking.util.ClearScreen;

public final class AccountCLI extends AccountDAO {
    private static Scanner in;
    private static AccountCLI dao;

    private AccountCLI(Account account) {
        super(account);
    }

    private static void init() {
        System.out.println();
        dao.printAccountInfo();
        System.out.println();
    }

    private static void help() {
        System.out.println("        --HELP MENU--");
        System.out.println("[options -> descriptions]\n");
        System.out.println("exit -> logout");
        System.out.println("info -> Get current Customer's information");
        // System.out.println("openaccount -> Open an Account");
        // System.out.println("updateaccount -> Update existing Account data");
        // System.out.println("closeAccount -> Close an Account");
        // System.out.println("listaccounts -> List all the Accounts of this Customer");
        System.out.println("help -> To see this help menu again");
        System.out.println("clear -> To clear screen\n");
    }

    private static void selectOption(String input) {
        switch (input) {
            // case "openaccount":
            // // open account
            // openAccount();
            // break;

            // case "updateaccount":
            // // update account
            // updateAccount();
            // break;

            // case "closeaccount":
            // // close account
            // closeAccount();
            // break;

            // case "listaccounts":
            // // list accounts
            // dao.listAccounts();
            // break;

            case "info":
                // print customer info
                init();
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

    public static void run(Scanner inputStream, Account account) {
        // start
        try {
            in = inputStream;
            dao = new AccountCLI(account);
            init();
            help();

            String input;
            do {
                System.out.print("account> ");
                input = in.nextLine().toLowerCase().trim();

                selectOption(input);
            } while (!input.equals("exit"));
            System.out.println("Logged out of Account!\n");
        } catch (Exception e) {
            System.err.println("An Error Occurred!\n" + e);
            System.err.println("\nProgram stopped Abnormally!");
        }
    }
}
