package me.akshawop.banking.inputmodules.forms;

import java.util.Scanner;

import me.akshawop.banking.sys.Bank;

public class CreateBankForm {
    private static boolean isValidBankName(String bankName) {
        return (bankName.length() > 0 && bankName.length() <= 100);
    }

    private static boolean isValidBankCode(String bankCode) {
        return bankCode.length() == 4;
    }

    public static Bank fillUp(Scanner in) {
        boolean cancelForm = false;

        do {
            String bankName;
            String bankCode;

            System.out.println("\n--Please fill up the following details--");
            System.out.println("[* -> COMPULSORY FIELD]\n");

            // bank name input
            do {
                System.out.print("Bank Name*: ");
                bankName = in.nextLine().toLowerCase().trim();
                if (!isValidBankName(bankName)) {
                    System.out.println("Wrong input: Cannot be empty or greater than 100 characters");
                    continue;
                }
                break;
            } while (true);

            // bank code input
            do {
                System.out.print("Bank Code*: ");
                bankCode = in.nextLine().toLowerCase().trim();
                if (!isValidBankCode(bankCode)) {
                    System.out.println("Wrong input: Has to be 4 characters long");
                    continue;
                }
                break;
            } while (true);

            // submit confirmation
            System.out.println("\nSubmit the form?");
            System.out.println("yes -> To confirm");
            System.out.println("no -> To cancel");
            System.out.println("[anything else] -> To refill the form");
            System.out.print("form> ");
            String input = in.nextLine().toLowerCase().trim();

            switch (input) {
                case "yes":
                    return new Bank(bankCode, bankName);
                case "no":
                    cancelForm = true;
                default:
                    break;
            }
        } while (!cancelForm);
        return null;
    }
}
