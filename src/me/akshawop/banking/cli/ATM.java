package me.akshawop.banking.cli;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import me.akshawop.banking.sys.Account;
import me.akshawop.banking.sys.AccountDAO;
import me.akshawop.banking.sys.Card;
import me.akshawop.banking.sys.CardDAO;
import me.akshawop.banking.sys.CardStatus;
import me.akshawop.banking.sys.CardType;
import me.akshawop.banking.sys.Transaction;
import me.akshawop.banking.sys.TransactionDAO;
import me.akshawop.banking.sys.TransactionMode;
import me.akshawop.banking.util.ClearScreen;
import me.akshawop.banking.util.IncorrectPinException;
import me.akshawop.banking.util.NotEnoughBalanceException;

public final class ATM extends AccountDAO {
    protected ATM(Account account) {
        super(account);
    }

    private static Scanner in = new Scanner(System.in);
    private static ATM dao;
    private static String cardNumber;
    private static Double currentBalance;
    private static Path path;

    private static final String DESCRIPTION = "Withdrawal BY CASH through ATM with card number ending with ";

    private static boolean correctPin(Card card, String pin) {
        String dbPin = CardDAO.getCardPin(card);
        if (dbPin == null)
            return false;

        else if (pin.equals(dbPin))
            return true;

        return false;
    }

    private static void init() {
        do {
            try {
                System.out.print("---Insert the card and press Enter key to continue---");
                in.nextLine().trim();
                FileInputStream fileIn = new FileInputStream(path.toString());
                ObjectInputStream objIn = new ObjectInputStream(fileIn);
                Card card = (Card) objIn.readObject();
                objIn.close();
                fileIn.close();

                if (!card.type().equals(CardType.DEBIT.toString()))
                    throw new Exception("Invalid card inserted!");

                if (!CardDAO.getCardStatus(card).equals(CardStatus.ACTIVE.toString())) {
                    throw new Exception("Card is not active!");
                }

                Account account = CardDAO.getAccount(card);

                if (account == null)
                    throw new Exception("No such account found!");

                System.out.print("Enter the pin> ");
                if (!correctPin(card, in.nextLine().trim())) {
                    throw new IncorrectPinException();
                }

                dao = new ATM(account);
                cardNumber = card.cardNumber().substring(12);
                currentBalance = account.getBalance();
                break;
            } catch (IncorrectPinException e) {
                System.out.println("\nInvalid Pin Entered!\n");
                System.out.print("Press Enter to proceed> ");
                in.nextLine();
                ClearScreen.clearConsole();
            } catch (Exception e) {
                System.out.println("\nInvalid Card!\n");
                System.err.println(e);
                System.out.print("Press Enter to proceed> ");
                in.nextLine();
                ClearScreen.clearConsole();
            }
        } while (true);
    }

    private static void withdraw() {
        try {
            System.out.print("Enter the amount> $");
            double amount = Double.parseDouble(in.nextLine().trim());

            if (amount > currentBalance)
                throw new NotEnoughBalanceException();

            Transaction tr = dao.withdraw(DESCRIPTION + cardNumber, TransactionMode.ATM, amount);
            System.out.println("\n---Transaction Details---");
            TransactionDAO.printTransactionForATM(tr);
            System.out.println("\nTransaction Completed successfully!\n");
            System.out.print("Take your card and press Enter to proceed> ");
            in.nextLine();
            ClearScreen.clearConsole();
        } catch (NotEnoughBalanceException e) {
            System.out.println("\nNot enough balance in account to proceed this transaction!\n");
            System.out.print("Take your card and press Enter to proceed> ");
            in.nextLine();
            ClearScreen.clearConsole();
        } catch (Exception e) {
            System.out.println("\nInvalid Amount entered!\n");
            System.out.print("Take your card and press Enter to proceed> ");
            in.nextLine();
            ClearScreen.clearConsole();
        }
    }

    private static void help() {
        System.out.println("        --HELP MENU--");
        System.out.println("[options -> descriptions]\n");
        System.out.println("withdraw -> Withdraw Money");
        System.out.println("balance -> Check Balance amount");
        System.out.println("changepin -> Change the current Card PIN");
        System.out.println("cancel -> To cancel the current session\n");

        // System.out.println(" ---DEVELOPER OPTIONS---\n");
        // System.out.println("exit -> Shut Down ATM Machine\n");
    }

    private static void selectOption(String input) {
        switch (input) {
            case "withdraw":
                // withdraw money
                withdraw();
                break;

            case "balance":
                // check balance
                System.out.println("\nAccount Balance: $" + currentBalance);
                System.out.print("\nTake your card and press Enter to proceed> ");
                in.nextLine();
                ClearScreen.clearConsole();
                break;

            case "changepin":
                // change the card pin
                break;

            case "cancel":
                // cancel the current session
                break;

            case "exit":
                // exit
                break;

            default:
                System.out.println("Invalid input!");
        }
    }

    public static void main(String args[]) {
        try {
            // initialize the system
            ClearScreen.clearConsole();
            do {
                System.out.print("Enter the location of the file where to find the Card(card.ser): ");
                String location = in.nextLine().trim();

                if (location.length() == 0) {
                    System.out.println("Enter a valid path!\n");
                    continue;
                }

                // check if it is a valid directory path
                try {
                    path = Paths.get(location).normalize();
                } catch (InvalidPathException e) {
                    System.out.println("Enter a valid path!\n");
                    continue;
                }

                System.out.println("\n--Insert a Dummy Card to the given location to complete the process---\n");
                System.out.print("Press Enter to continue> ");
                in.nextLine();
                if (!Files.isDirectory(path)) {
                    System.out.println("Enter a valid path!\n");
                    continue;
                }

                // locate the card.ser file in it
                path = Paths.get(location, "card.ser").normalize();
                break;
            } while (true);

            // start
            String input;
            do {
                ClearScreen.clearConsole();
                init();
                ClearScreen.clearConsole();
                help();

                do {
                    System.out.print("atm> ");
                    input = in.nextLine().toLowerCase().trim();
                } while (input.length() == 0);

                selectOption(input);
                dao = null;
                cardNumber = null;
                currentBalance = 0.0;
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
