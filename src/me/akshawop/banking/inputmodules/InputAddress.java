package me.akshawop.banking.inputmodules;

import java.util.Scanner;

import me.akshawop.banking.customtype.Address;

public class InputAddress {
    private static Address address = new Address();

    private static boolean isValidAddress(String str) {
        return (str.length() > 0 && str.length() <= 50);
    }

    private static boolean isValidPinCode(String pinCode) {
        return pinCode.length() == 6;
    }

    public static Address input(Scanner in) {
        // street
        do {
            System.out.print("Street*: ");
            address.street = in.nextLine().toLowerCase().trim();
            if (!isValidAddress(address.street)) {
                System.out.println("Wrong input: Cannot be empty or greater than 50 characters");
                continue;
            }
            break;
        } while (true);

        // city
        do {
            System.out.print("City*: ");
            address.city = in.nextLine().toLowerCase().trim();
            if (!isValidAddress(address.city)) {
                System.out.println("Wrong input: Cannot be empty or greater than 50 characters");
                continue;
            }
            break;
        } while (true);

        // district
        do {
            System.out.print("District*: ");
            address.district = in.nextLine().toLowerCase().trim();
            if (!isValidAddress(address.district)) {
                System.out.println("Wrong input: Cannot be empty or greater than 50 characters");
                continue;
            }
            break;
        } while (true);

        // state
        do {
            System.out.print("State*: ");
            address.state = in.nextLine().toLowerCase().trim();
            if (!isValidAddress(address.state)) {
                System.out.println("Wrong input: Cannot be empty or greater than 50 characters");
                continue;
            }
            break;
        } while (true);

        // pinCode
        do {
            System.out.print("Area PIN Code*: ");
            address.pinCode = in.nextLine().toLowerCase().trim();
            if (!isValidPinCode(address.pinCode)) {
                System.out.println("Wrong input: Cannot be empty or greater than 50 characters");
                continue;
            }
            break;
        } while (true);
        return address;
    }
}
