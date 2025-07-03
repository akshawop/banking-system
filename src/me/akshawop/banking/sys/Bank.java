package me.akshawop.banking.sys;

public class Bank {
    private String bankCode;
    private String bankName;

    public Bank(String bankCode, String bankName) {
        this.bankCode = bankCode;
        this.bankName = bankName;
    }

    public Bank(Bank recvBank) {
        this.bankCode = recvBank.getBankCode();
        this.bankName = recvBank.getBankName();
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}