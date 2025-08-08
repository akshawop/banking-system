package me.akshawop.banking.cli.inputmodules.forms;

import java.util.Scanner;

import me.akshawop.banking.sys.Account;
import me.akshawop.banking.sys.AccountType;
import me.akshawop.banking.sys.CustomerDAO;

public class NewAccountForm {
    private static AccountType type;
    private static double initialBalance;
    private static int nominee;
    private static String nomineeName = "";
    private static double minBalance;

    private static void verify() {
        System.out.println("\n    --PLEASE VERIFY THE DETAILS--");
        System.out.println("Account Type: " + type);
        System.out.println("Initial Deposit Amount: $" + initialBalance);
        System.out.println(
                "Nominee: " + (nomineeName.length() == 0 ? "N/A" : nominee + " [" + nomineeName.toUpperCase() + "]"));
        System.out.println("Minimum Balance Amount: $" + minBalance);
        System.out.println();
    }

    public static Account fillUp(Scanner in, int customerId, String ifscCode) {
        boolean cancelForm = false;

        do {
            System.out.println("\n--Please fill up the following details--");
            System.out.println("[* -> COMPULSORY FIELD]\n");

            // select account type
            System.out.println("Choose from the following -->");
            System.out.println("savings -> SAVINGS Account");
            System.out.println("current -> CURRENT Account");
            do {
                System.out.print("Enter here*: ");
                String choice = in.nextLine().toLowerCase().trim();
                switch (choice) {
                    case "savings":
                        type = AccountType.SAVINGS;
                        break;

                    case "current":
                        type = AccountType.CURRENT;
                        break;

                    case "":
                        System.out.println("COMPULSORY FIELD! Can't be Empty!");
                        continue;

                    default:
                        System.out.println("Invalid Input! Try Again...");
                        continue;
                }
                break;
            } while (true);

            System.out.println("\n[Opening a '" + type + "' Account]\n");

            // input initial balance
            do {
                System.out.print("Enter the Initial Deposit Amount*: $");
                initialBalance = Double.parseDouble(in.nextLine().trim());
                if (initialBalance < 0) {
                    System.out.println("Wrong input: Can't be less than zero");
                    continue;
                }
                break;
            } while (true);

            // input nominee
            System.out.println("\nEnter the Customer ID of the Nominee for this Account -->");
            do {
                System.out.println("[Enter zero(0) to skip]");
                System.out.print("Enter here: ");
                nominee = Integer.parseInt(in.nextLine().trim());
                if (nominee != 0) {
                    if (nominee < 0) {
                        System.out.println("Wrong input: Invalid Customer ID\n");
                        continue;
                    }

                    if (CustomerDAO.fetchCustomer(nominee) == null) {
                        System.out.println("No such Customer exist! Try Again...\n");
                        continue;
                    } else {
                        nomineeName = CustomerDAO.fetchCustomer(nominee).getName();
                        System.out.println("\nDo you like to make " + nomineeName.toUpperCase()
                                + ", the Nominee for this Account?");
                        System.out.println("y -> yes");
                        System.out.println("[anything else] -> To enter again");
                        System.out.print("form> ");
                        String choice = in.nextLine().toLowerCase().trim();
                        if (!choice.equals("y"))
                            continue;
                    }
                } else
                    nomineeName = "";
                break;
            } while (true);

            // input minimum balance
            System.out.println("\nEnter the minimum balance Amount for this Account -->");
            do {
                System.out.println("[Enter zero(0) for no minimum balance amount]");
                System.out.print("Enter here: $");
                minBalance = Double.parseDouble(in.nextLine().trim());
                if (minBalance < 0) {
                    System.out.println("Wrong input: Can't be less than zero");
                    continue;
                }
                break;
            } while (true);

            // verify before submission
            verify();

            // submit confirmation
            System.out.println("\nSubmit the form?");
            System.out.println("yes -> To confirm");
            System.out.println("no -> To cancel");
            System.out.println("[anything else] -> To refill the form\n");
            System.out.print("form> ");
            String input = in.nextLine().toLowerCase().trim();

            switch (input) {
                case "yes":
                    return new Account(customerId, ifscCode, type, nominee, initialBalance, minBalance);
                case "no":
                    cancelForm = true;
                default:
                    break;
            }
        } while (!cancelForm);
        return null;
    }
}
