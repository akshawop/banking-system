package me.akshawop.banking.inputmodules.forms;

import java.util.Scanner;

import me.akshawop.banking.customtype.Address;
import me.akshawop.banking.inputmodules.NewAddress;
import me.akshawop.banking.sys.Branch;
import me.akshawop.banking.util.InputChecker;

public class CreateBranchForm {
    static String branchName;
    static String branchCode;
    static Address address;

    private static void verify() {
        System.out.println("\n    --PLEASE VERIFY THE DETAILS--");
        System.out.println("Branch Name: " + branchName.toUpperCase());
        System.out.println("Branch Code: " + branchCode.toUpperCase());
        System.out.println("Branch Address -->");
        address.formPrint();
        System.out.println();
    }

    public static Branch fillUp(Scanner in) {
        boolean cancelForm = false;

        do {
            System.out.println("\n--Please fill up the following details--");
            System.out.println("[* -> COMPULSORY FIELD]\n");

            // branch name input
            do {
                System.out.print("Branch Name*: ");
                branchName = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkBranchName(branchName, 'c')) {
                    System.out.println("Wrong input: Cannot be empty or greater than 50 characters");
                    continue;
                }
                break;
            } while (true);

            // branch code input
            do {
                System.out.print("Branch Code*: ");
                branchCode = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkBranchCode(branchCode, 'c')) {
                    System.out.println("Wrong input: Has to be 6 characters long");
                    continue;
                }
                break;
            } while (true);

            // branch address input
            System.out.println("Branch Address* -->");
            address = NewAddress.input(in);

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
                    return new Branch(branchCode, branchName, address);
                case "no":
                    cancelForm = true;
                default:
                    break;
            }
        } while (!cancelForm);
        return null;
    }
}
