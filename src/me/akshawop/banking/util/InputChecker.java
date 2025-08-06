package me.akshawop.banking.util;

/**
 * User input checker to check different kinds of user inputs in the entire
 * banking system.
 * 
 * In {@code type}, provide 'o' for OPTIONAL inputs and 'c' or any other character for
 * COMPULSORY
 * 
 */
public class InputChecker {
    private static boolean hasNoSpaceChar(String str) {
        return !(str.indexOf(' ') >= 0);
    }

    private static boolean checkIfOnlyNumbers(String str) {
        for (int i = 0, size = str.length(); i < size; i++) {
            char ch = str.charAt(i);
            if (!(ch >= 48 && ch <= 57))
                return false;
        }
        return true;
    }

    public static boolean checkBankName(String bankName, char type) {
        if (Character.toLowerCase(type) == 'o')
            return bankName.length() <= 100;
        else
            return (bankName.length() > 0 && bankName.length() <= 100);
    }

    public static boolean checkBankCode(String bankCode, char type) {
        if (Character.toLowerCase(type) == 'o')
            return ((bankCode.length() == 0 || bankCode.length() == 4) && hasNoSpaceChar(bankCode));
        else
            return (bankCode.length() == 4 && hasNoSpaceChar(bankCode));
    }

    public static boolean checkBranchName(String branchName, char type) {
        if (Character.toLowerCase(type) == 'o')
            return branchName.length() <= 50;
        else
            return (branchName.length() > 0 && branchName.length() <= 50);
    }

    public static boolean checkBranchCode(String branchCode, char type) {
        if (Character.toLowerCase(type) == 'o')
            return ((branchCode.length() == 0 || branchCode.length() == 6) && hasNoSpaceChar(branchCode));
        else
            return (branchCode.length() == 6 && hasNoSpaceChar(branchCode));
    }

    public static boolean checkName(String name, char type) {
        if (Character.toLowerCase(type) == 'o')
            return ((name.length() == 0 || name.length() <= 25) && hasNoSpaceChar(name));
        else
            return ((name.length() > 0 && name.length() <= 25) && hasNoSpaceChar(name));
    }

    public static boolean checkAadhaar(String aadhaar) {
        if (aadhaar.length() == 12) {
            return checkIfOnlyNumbers(aadhaar);
        }
        return false;
    }

    public static boolean checkPAN(String pan) {
        return ((pan.length() == 0 || pan.length() == 10) && hasNoSpaceChar(pan));
    }

    public static boolean checkPhone(String phone, char type) {
        if (Character.toLowerCase(type) == 'o')
            return (phone.length() == 0 || (phone.length() == 10 && checkIfOnlyNumbers(phone)));
        else
            return (phone.length() == 10 && checkIfOnlyNumbers(phone));
    }

    public static boolean checkEmail(String email) {
        if (email.length() == 0)
            return true;

        if (email.length() < 5)
            return false;

        if (email.indexOf('@') < 1)
            return false;

        // local part
        String local = email.substring(0, email.indexOf('@'));
        if (local.indexOf('.') == 0 || local.indexOf('.') == local.length() - 1 || local.indexOf("..") >= 0)
            return false;

        for (int i = 0, size = local.length(); i < size; i++) {
            char ch = local.charAt(i);
            if (!((ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57) || ch == 33 || (ch >= 35 && ch <= 39) || ch == 42
                    || ch == 43 || ch == 45 || ch == 46 || ch == 47 || ch == 61 || ch == 63 || ch == 94 || ch == 95
                    || ch == 96 || ch == 126))
                return false;
        }

        // domain part
        String domain = email.substring(email.indexOf('@') + 1);
        if (domain.indexOf('.') == 0 || domain.indexOf('.') == domain.length() - 1 || domain.indexOf("..") >= 0)
            return false;

        if (domain.indexOf('.') < 0)
            return false;

        for (int i = 0, size = domain.length(); i < size; i++) {
            char ch = domain.charAt(i);
            if (!((ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57) || ch == 46))
                return false;
        }

        return true;
    }
}
