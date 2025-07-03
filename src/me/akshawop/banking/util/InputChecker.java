package me.akshawop.banking.util;

public class InputChecker {

    public static boolean checkBankName(String bankName, char type) {
        if (type == 'o' || type == 'O')
            return bankName.length() <= 100;
        else
            return (bankName.length() > 0 && bankName.length() <= 100);
    }

    public static boolean checkBankCode(String bankCode, char type) {
        if (type == 'o' || type == 'O')
            return (bankCode.length() == 0 || bankCode.length() == 4);
        else
            return bankCode.length() == 4;
    }

    public static boolean checkBranchName(String branchName, char type) {
        if (type == 'o' || type == 'O')
            return branchName.length() <= 50;
        else
            return (branchName.length() > 0 && branchName.length() <= 50);
    }

    public static boolean checkBranchCode(String branchCode, char type) {
        if (type == 'o' || type == 'O')
            return branchCode.length() == 0 || branchCode.length() == 6;
        else
            return branchCode.length() == 6;
    }
}
