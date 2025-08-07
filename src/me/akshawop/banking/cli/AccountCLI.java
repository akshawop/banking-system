package me.akshawop.banking.cli;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Scanner;

import me.akshawop.banking.sys.Account;
import me.akshawop.banking.sys.AccountDAO;
import me.akshawop.banking.sys.Branch;
import me.akshawop.banking.sys.BranchDAO;
import me.akshawop.banking.sys.Card;
import me.akshawop.banking.sys.CardDAO;
import me.akshawop.banking.sys.Customer;
import me.akshawop.banking.sys.CustomerDAO;
import me.akshawop.banking.util.ClearScreen;
import me.akshawop.banking.util.InputChecker;

public final class AccountCLI extends AccountDAO {
    private static Scanner in;
    private static AccountCLI dao;
    private static String input;

    AccountCLI(Account account) {
        super(account);
    }

    private static void init() {
        System.out.println();
        dao.printAccountInfo();
        System.out.println();
    }

    private static void history() {
        try {
            System.out.print("\nFrom Date: ");
            Date fromDate = Date.valueOf(in.nextLine().trim());

            System.out.print("To Date: ");
            Date toDate = Date.valueOf(in.nextLine().trim());

            if (toDate.compareTo(fromDate) < 0) {
                System.out.println("\n[From Date] should be before the [To Date]!\n");
                return;
            }

            dao.getTransactionHistory(fromDate, toDate);
        } catch (Exception e) {
            System.err.println("\nInvalid Input\n");
        }
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

    private static void blockCard() {
        System.out.print("Card Number: ");
        Card card = dao.getCard(in.nextLine().trim());

        if (card == null) {
            System.out.println("\nNo Such Card Issued to your Account!\n");
            return;
        }

        int stat = dao.blockCard(card);
        if (stat == -1)
            System.out.println("\nCard is already Blocked!\n");
        else if (stat == 0)
            System.out.println("\nCard BLOCKED!\n");
        else
            System.out.println("\nCard Blocking unsuccessful!\n");

        if (stat == -2)
            System.out.println("Card has Expired!\n");
    }

    private static void unblockCard() {
        System.out.print("Card Number: ");
        Card card = dao.getCard(in.nextLine().trim());

        if (card == null) {
            System.out.println("\nNo Such Card Issued to your Account!\n");
            return;
        }

        int stat = dao.unblockCard(card);
        if (stat == -1)
            System.out.println("\nCard is already Active!\n");
        else if (stat == 0)
            System.out.println("\nCard UNBLOCKED!\n");
        else
            System.out.println("\nCard Activation unsuccessful!\n");

        if (stat == -2)
            System.out.println("Card has Expired!\n");
    }

    private static void listAllIssuedCards() {
        CardDAO.printCardInfoOfAccount(dao.getCurrentAccountNumber());
    }

    private static void generateCard() {
        System.out.print("Card Number: ");
        Card card = dao.getCard(in.nextLine().trim());

        if (card == null) {
            System.out.println("\nNo Such Card Issued to your Account!\n");
            return;
        }

        try {
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
            ObjectOutputStream objOut = new ObjectOutputStream(
                    new FileOutputStream(path + "/" + dao.getCurrentAccountNumber() + ".ser"));
            objOut.writeObject(card);
            System.out.println("\nCard saved Successfully!\n");
            objOut.close();
        } catch (IOException e) {
            System.out.println("\nAn error occurred, card saving failed!\n");
            System.err.println(e);
        } catch (Exception e) {
            System.out.println("\nAn error occurred, card saving failed!\n");
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
    }

    private static void help() {
        System.out.println("        --HELP MENU--");
        System.out.println("[options -> descriptions]\n");
        System.out.println("exit -> logout");
        System.out.println("info -> Get current Account's information\n");

        System.out.println("--Account Options--");
        System.out.println("history -> To see the Transaction History of this Account");
        System.out.println("updatenominee -> Change the Nominee for this Account");
        System.out.println("transferaccount -> Transfer this Account to another Branch");
        System.out.println("block -> Block the Account");
        System.out.println("unblock -> Unblock the Account\n");

        System.out.println("--Card Options--");
        System.out.println("blockcard -> Block a Card");
        System.out.println("unblockcard -> Unblock a Card");
        System.out.println("listallcards -> List All the Issued Cards which belong this Account");
        System.out.println("generatecard -> Generate an existing Card's object and store it\n");

        System.out.println("--Other Options--");
        System.out.println("help -> To see this help menu again");
        System.out.println("clear -> To clear screen\n");
    }

    private static void selectOption(String input) {
        switch (input) {
            case "history":
                // get the transaction history
                history();
                break;

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

            case "blockcard":
                // block a card
                blockCard();
                break;

            case "unblockcard":
                // unblock a card
                unblockCard();
                break;

            case "listallcards":
                // list all the issued cards
                listAllIssuedCards();
                break;

            case "generatecard":
                // generate an existing card's object and store it
                generateCard();
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
