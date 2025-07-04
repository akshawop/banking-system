package me.akshawop.banking.inputmodules.forms;

import java.util.Scanner;

import me.akshawop.banking.customtype.Address;
import me.akshawop.banking.inputmodules.NewAddress;
import me.akshawop.banking.sys.Customer;
import me.akshawop.banking.util.InputChecker;

public class NewCustomerForm {
    static String firstName;
    static String midName;
    static String lastName;
    static String aadhaar;
    static String pan;
    static Address address;
    static String phone;
    static String email;

    private static void verify() {
        System.out.println("\n    --PLEASE VERIFY THE DETAILS--");
        System.out.println("First Name: " + firstName);
        System.out.println("Middle Name: " + midName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Aadhaar: " + aadhaar);
        System.out.println("PAN: " + pan);
        System.out.println("Address -->");
        address.formPrint();
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println();
    }

    public static Customer fillUp(Scanner in) {
        boolean cancelForm = false;

        do {
            System.out.println("\n--Please fill up the following details--");
            System.out.println("[* -> COMPULSORY FIELD]\n");

            // first name input
            do {
                System.out.print("First Name*: ");
                firstName = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkName(firstName, 'c')) {
                    System.out.println("Wrong input: Cannot be empty or greater than 25 characters");
                    continue;
                }
                break;
            } while (true);

            // middle name input
            do {
                System.out.print("Middle Name: ");
                midName = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkName(firstName, 'o')) {
                    System.out.println("Wrong input: Cannot be greater than 25 characters");
                    continue;
                }
                break;
            } while (true);

            // last name input
            do {
                System.out.print("Last Name*: ");
                lastName = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkName(firstName, 'c')) {
                    System.out.println("Wrong input: Cannot be empty or greater than 25 characters");
                    continue;
                }
                break;
            } while (true);

            // concatenate name
            String name = firstName + " " + midName + (midName.length() == 0 ? "" : " ") + lastName;

            // aadhaar input
            do {
                System.out.print("Aadhaar*: ");
                aadhaar = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkAadhaar(aadhaar)) {
                    System.out.println("Wrong input: Invalid Aadhaar Number");
                    continue;
                }
                break;
            } while (true);

            // pan input
            do {
                System.out.print("PAN: ");
                pan = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkPAN(pan)) {
                    System.out.println("Wrong input: Invalid PAN");
                    continue;
                }
                break;
            } while (true);

            // customer address input
            System.out.println("Address* -->");
            address = NewAddress.input(in);

            // phone input
            do {
                System.out.print("Phone*: ");
                phone = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkPhone(phone, 'c')) {
                    System.out.println("Wrong input: Invalid Phone number");
                    continue;
                }
                break;
            } while (true);

            // email input
            do {
                System.out.print("Email: ");
                email = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkEmail(email)) {
                    System.out.println("Wrong input: Invalid Email address");
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
                    return new Customer(name, aadhaar, pan, address, phone, email);
                case "no":
                    cancelForm = true;
                default:
                    break;
            }
        } while (!cancelForm);
        return null;
    }
}
