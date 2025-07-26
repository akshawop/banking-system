package me.akshawop.banking.cli;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import me.akshawop.banking.sys.Card;
import me.akshawop.banking.sys.CardType;
import me.akshawop.banking.util.ClearScreen;

public final class ATM {
    private static Scanner in = new Scanner(System.in);
    private static AccountCLI dao;
    private static Path path;

    private static void init() {
        do {
            try {
                System.out.print("---Insert the card and press any key to continue---");
                in.nextLine().trim();
                FileInputStream fileIn = new FileInputStream(path.toString());
                ObjectInputStream objIn = new ObjectInputStream(fileIn);
                Card card = (Card) objIn.readObject();
                objIn.close();
                fileIn.close();

                if (!card.type().equals(CardType.DEBIT.toString())) {
                    System.out.println("\nNot a Debit card!\n");
                    throw new Exception("File not found!");
                }

                // TODO: Do something after getting the correct card
                System.out.println("Card Number: " + card.cardNumber());
                System.out.println("CVV: " + card.cvv());
                System.out.println("Card Type: " + card.type());
                break;
            } catch (Exception e) {
                System.out.println("\nInvalid Card!\n");
                System.err.println(e);
                ClearScreen.clearConsole();
            }
        } while (true);
    }

    private static void help() {
        System.out.println("        --HELP MENU--");
        System.out.println("[options -> descriptions]\n");
        System.out.println("withdraw -> Withdraw Money");
        System.out.println("balance -> Check Balance amount\n");

        System.out.println("        ---DEVELOPER OPTIONS---\n");
        System.out.println("exit -> Shut Down ATM Machine");
        System.out.println("clear -> To clear screen\n");
    }

    private static void selectOption(String input) {
        switch (input) {
            case "withdraw":
                // withdraw money
                break;

            case "balance":
                // check balance
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

    public static void main(String args[]) {
        try {
            ClearScreen.clearConsole();
            do {
                System.out.print("Enter the location of the file where to find the card(*.ser): ");
                String location = in.nextLine().trim();

                // check if the directory exists
                try {
                    path = Paths.get(location).normalize();
                } catch (InvalidPathException e) {
                    System.out.println("Enter a valid path!\n");
                    continue;
                }
                break;
            } while (true);

            // start
            String input;
            do {
                ClearScreen.clearConsole();
                init();
                System.out.println();
                help();

                do {
                    System.out.print("atm> ");
                    input = in.nextLine().toLowerCase().trim();
                } while (input.length() == 0);

                selectOption(input);
                dao = null;
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
