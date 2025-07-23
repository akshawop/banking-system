package me.akshawop.banking.cli;

import java.util.Scanner;

import me.akshawop.banking.sys.Account;
import me.akshawop.banking.sys.AccountDAO;
import me.akshawop.banking.sys.Branch;
import me.akshawop.banking.sys.BranchDAO;
import me.akshawop.banking.sys.Customer;
import me.akshawop.banking.sys.CustomerDAO;
import me.akshawop.banking.util.ClearScreen;
import me.akshawop.banking.util.InputChecker;

public final class AccountCLI extends AccountDAO {
    private static Scanner in;
    private static AccountCLI dao;
    private static String input;

    private AccountCLI(Account account) {
        super(account);
    }

    private static void init() {
        System.out.println();
        dao.printAccountInfo();
        System.out.println();
    }

    private static void updateNominee() {
        System.out.print("\nCustomer ID of the Nominee: ");
        int nominee;
        try {
            nominee = Integer.parseInt(in.nextLine().trim());
        } catch (Exception e) {
            nominee = 0;
        }

        if (nominee == dao.getCurrentAccount().getNominee()) {
            System.out.println("\nThis Customer is Already the Nominee of this Account!\n");
            return;
        }

        if (nominee == dao.getCurrentAccount().getCustomerId()) {
            System.out.println("\nThis Customer is the Account Holder of this Account!\n");
            return;
        }

        if (nominee > 0) {
            Customer customer = CustomerDAO.fetchCustomer(nominee);
            if (customer != null) {
                System.out.println(
                        "\nNominee: " + customer.getCustomerId() + " [" + customer.getName().toUpperCase() + "]");
                System.out.println("\n--Verify to choose this Customer as the Nominee for this Account--\n");
                System.out.println("y -> To confirm");
                System.out.println("[anything else] -> To cancel");
                System.out.print("updatenominee> ");
                String choice = in.nextLine().toLowerCase().trim();
                if (!choice.equals("y")) {
                    System.out.println("\nCANCELED!\n");
                    return;
                }

                if (dao.updateNominee(nominee) == 0)
                    System.out.println("\nNominee updated successfully!\n");
                else
                    System.out.println("\nNominee update unsuccessful!\n");

            } else {
                System.out.println("\nNominee update unsuccessful!");
                System.out.println("No such Customer exists!\n");
            }
        } else
            System.out.println("\nInvalid Customer ID!\n");
    }

    private static void transferAccount() {
        System.out.print("Transfer to Branch(Branch Code): ");
        String branchCode = in.nextLine().toLowerCase().trim();

        if (!InputChecker.checkBranchCode(branchCode, 'c')) {
            System.out.println("\nInvalid Branch Code entered!\n");
            return;
        }

        if (branchCode.equalsIgnoreCase(dao.getCurrentAccount().getIfscCode().substring(5))) {
            System.out.println("\nTransfer of Account to the same Branch has no effect!\n");
            return;
        }

        Branch branch = BranchDAO.fetchBranch(branchCode);
        if (branch != null) {
            if (dao.transferAccount(branchCode) == 0) {
                System.out.println(
                        "\nAccount transferred to " + branch.getBranchName().toUpperCase() + " branch successfully!\n");
                input = "exit";
            } else
                System.out.println("\nAccount transfer unsuccessful!\n");
        } else
            System.out.println("\nNo such Branch exists!\n");
    }

    private static void block() {
        int stat = dao.blockAccount();
        if (stat == -1)
            System.out.println("\nAccount is already Blocked!\n");
        else if (stat == 0)
            System.out.println("\nAccount BLOCKED!\n");
        else
            System.out.println("\nAcount Blocking unsuccessful!\n");
    }

    private static void unblock() {
        int stat = dao.unblockAccount();
        if (stat == -1)
            System.out.println("\nAccount is already Active!\n");
        else if (stat == 0)
            System.out.println("\nAccount UNBLOCKED!\n");
        else
            System.out.println("\nAcount Activation unsuccessful!\n");
    }

    private static void help() {
        System.out.println("        --HELP MENU--");
        System.out.println("[options -> descriptions]\n");
        System.out.println("exit -> logout");
        System.out.println("info -> Get current Account's information");
        System.out.println("updatenominee -> Change the Nominee for this Account");
        System.out.println("transferaccount -> Transfer this Account to another Branch");
        System.out.println("block -> Block the Account");
        System.out.println("unblock -> Unblock the Account");
        System.out.println("help -> To see this help menu again");
        System.out.println("clear -> To clear screen\n");
    }

    private static void selectOption(String input) {
        switch (input) {
            case "updatenominee":
                // update nominee
                updateNominee();
                break;

            case "transferaccount":
                // transfer account
                transferAccount();
                break;

            case "block":
                // block account
                block();
                break;

            case "unblock":
                // unblock account
                unblock();
                break;

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
