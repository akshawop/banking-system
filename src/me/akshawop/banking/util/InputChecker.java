package me.akshawop.banking.util;

public class InputChecker {
    // in type, provide 'o' for OPTIONAL and 'c' or any other character for
    // COMPULSORY

    public static boolean checkBankName(String bankName, char type) {
        if (Character.toLowerCase(type) == 'o')
            return bankName.length() <= 100;
        else
            return (bankName.length() > 0 && bankName.length() <= 100);
    }

    public static boolean checkBankCode(String bankCode, char type) {
        if (Character.toLowerCase(type) == 'o')
            return (bankCode.length() == 0 || bankCode.length() == 4);
        else
            return bankCode.length() == 4;
    }

    public static boolean checkBranchName(String branchName, char type) {
        if (Character.toLowerCase(type) == 'o')
            return branchName.length() <= 50;
        else
            return (branchName.length() > 0 && branchName.length() <= 50);
    }

    public static boolean checkBranchCode(String branchCode, char type) {
        if (Character.toLowerCase(type) == 'o')
            return (branchCode.length() == 0 || branchCode.length() == 6);
        else
            return branchCode.length() == 6;
    }

    public static boolean checkName(String name, char type) {
        if (Character.toLowerCase(type) == 'o')
            return (name.length() == 0 || name.length() <= 25);
        else
            return (name.length() > 0 && name.length() <= 25);
    }
}
