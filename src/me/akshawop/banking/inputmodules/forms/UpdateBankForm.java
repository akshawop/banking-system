package me.akshawop.banking.inputmodules.forms;

import java.util.Scanner;

import me.akshawop.banking.sys.Bank;

public class UpdateBankForm {
    private static boolean isValidBankName(String bankName) {
        return bankName.length() <= 100;
    }

    private static boolean isValidBankCode(String bankCode) {
        return (bankCode.length() == 0 || bankCode.length() == 4);
    }

    public static Bank fillUp(Scanner in, Bank recBank) {
        boolean cancelForm = false;
        Bank bank = new Bank(recBank);

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
                    System.out.println("Wrong input: Cannot be greater than 100 characters");
                    continue;
                }
                if (bankName.length() != 0)
                    bank.setBankName(bankName);
                break;
            } while (true);

            // bank code input
            do {
                System.out.print("Bank Code: ");
                bankCode = in.nextLine().toLowerCase().trim();
                if (!isValidBankCode(bankCode)) {
                    System.out.println("Wrong input: Has to be 4 characters long");
                    continue;
                }
                if (bankCode.length() != 0)
                    bank.setBankCode(bankCode);
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
                    return bank;
                case "no":
                    cancelForm = true;
                default:
                    break;
            }
        } while (!cancelForm);
        return null;
    }
}
