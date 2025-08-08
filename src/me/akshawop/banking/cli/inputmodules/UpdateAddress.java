package me.akshawop.banking.cli.inputmodules;

import java.util.Scanner;

import me.akshawop.banking.customtype.Address;

public class UpdateAddress {
    private static Address address;
    private static String street;
    private static String city;
    private static String district;
    private static String state;
    private static String pinCode;

    private static boolean isValidAddress(String str) {
        return (str.length() == 0 || str.length() <= 50);
    }

    private static boolean isValidPinCode(String pinCode) {
        return (pinCode.length() == 0 || pinCode.length() == 6);
    }

    public static Address input(Scanner in, Address recvAddress) {
        address = new Address(recvAddress);
        // street
        do {
            System.out.print("Street: ");
            street = in.nextLine().toLowerCase().trim();
            if (!isValidAddress(street)) {
                System.out.println("Wrong input: Cannot be greater than 50 characters");
                continue;
            }
            break;
        } while (true);
        address.street = street.length() == 0 ? address.street : street;

        // city
        do {
            System.out.print("City: ");
            city = in.nextLine().toLowerCase().trim();
            if (!isValidAddress(city)) {
                System.out.println("Wrong input: Cannot be greater than 50 characters");
                continue;
            }
            break;
        } while (true);
        address.city = city.length() == 0 ? address.city : city;

        // district
        do {
            System.out.print("District: ");
            district = in.nextLine().toLowerCase().trim();
            if (!isValidAddress(district)) {
                System.out.println("Wrong input: Cannot be greater than 50 characters");
                continue;
            }
            break;
        } while (true);
        address.district = district.length() == 0 ? address.district : district;

        // state
        do {
            System.out.print("State: ");
            state = in.nextLine().toLowerCase().trim();
            if (!isValidAddress(state)) {
                System.out.println("Wrong input: Cannot be greater than 50 characters");
                continue;
            }
            break;
        } while (true);
        address.state = state.length() == 0 ? address.state : state;

        // pinCode
        do {
            System.out.print("Area PIN Code: ");
            pinCode = in.nextLine().toLowerCase().trim();
            if (!isValidPinCode(pinCode)) {
                System.out.println("Wrong input: Has to be 6 characters long");
                continue;
            }
            break;
        } while (true);
        address.pinCode = pinCode.length() == 0 ? address.pinCode : pinCode;

        return address;
    }
}
