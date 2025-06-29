package me.akshawop.banking.sys;

public class Bank {
    private String bankCode;
    private String bankName;

    public Bank(String bankCode, String bankName) {
        this.bankCode = bankCode;
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getBankName() {
        return bankName;
    }
}