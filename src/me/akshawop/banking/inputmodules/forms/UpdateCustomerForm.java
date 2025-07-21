package me.akshawop.banking.inputmodules.forms;

import java.util.Scanner;

import me.akshawop.banking.customtype.Address;
import me.akshawop.banking.inputmodules.UpdateAddress;
import me.akshawop.banking.sys.Customer;
import me.akshawop.banking.util.InputChecker;

public class UpdateCustomerForm {
    private static Customer customer;
    private static String firstName;
    private static String midName;
    private static String lastName;
    private static String pan;
    private static Address address;
    private static String phone;
    private static String email;

    private static void verify() {
        System.out.println("\n    --PLEASE VERIFY THE DETAILS--");
        System.out.println("First Name: " + customer.getFirstName().toUpperCase());
        System.out.println("Middle Name: " + customer.getMidName().toUpperCase());
        System.out.println("Last Name: " + customer.getLastName().toUpperCase());
        if (customer.getPan().length() != 0)
            System.out.println("PAN: " + customer.getPan().toUpperCase());
        System.out.println("Address -->");
        customer.getAddress().formPrint();
        System.out.println("Phone: " + customer.getPhone());
        System.out.println("Email: " + customer.getEmail());
        System.out.println();
    }

    public static Customer fillUp(Scanner in, Customer recvCustomer) {
        customer = new Customer(recvCustomer);
        boolean cancelForm = false;

        do {
            System.out.println("\n--Please fill up the following details--");
            System.out.println("[* -> COMPULSORY FIELD]\n");

            // first name input
            do {
                System.out.print("First Name: ");
                firstName = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkName(firstName, 'o')) {
                    System.out.println("Wrong input: Cannot be greater than 25 characters");
                    continue;
                }
                firstName = firstName.length() == 0 ? customer.getFirstName() : firstName;
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
                midName = midName.length() == 0 ? customer.getMidName() : midName;
                break;
            } while (true);

            // last name input
            do {
                System.out.print("Last Name: ");
                lastName = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkName(firstName, 'o')) {
                    System.out.println("Wrong input: Cannot be greater than 25 characters");
                    continue;
                }
                lastName = lastName.length() == 0 ? customer.getLastName() : lastName;
                break;
            } while (true);

            // concatenate name
            String name = firstName + " " + midName + (midName.length() == 0 ? "" : " ") + lastName;
            customer.setName(name);

            // pan input
            if (customer.getPan().length() == 0) {
                do {
                    System.out.print("PAN: ");
                    pan = in.nextLine().toLowerCase().trim();
                    if (!InputChecker.checkPAN(pan)) {
                        System.out.println("Wrong input: Invalid PAN");
                        continue;
                    }
                    pan = pan.length() == 0 ? customer.getPan() : pan;
                    break;
                } while (true);
            } else
                pan = customer.getPan();
            customer.setPan(pan);

            // customer address input
            System.out.println("Address -->");
            address = UpdateAddress.input(in, customer.getAddress());
            customer.setAddress(address);

            // phone input
            do {
                System.out.print("Phone: ");
                phone = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkPhone(phone, 'o')) {
                    System.out.println("Wrong input: Invalid Phone number");
                    continue;
                }
                phone = phone.length() == 0 ? customer.getPhone() : phone;
                break;
            } while (true);
            customer.setPhone(phone);

            // email input
            do {
                System.out.print("Email: ");
                email = in.nextLine().toLowerCase().trim();
                if (!InputChecker.checkEmail(email)) {
                    System.out.println("Wrong input: Invalid Email address");
                    continue;
                }
                email = email.length() == 0 ? customer.getEmail() : email;
                break;
            } while (true);
            customer.setEmail(email);

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
                    return customer;
                case "no":
                    cancelForm = true;
                default:
                    break;
            }
        } while (!cancelForm);
        return null;
    }
}
