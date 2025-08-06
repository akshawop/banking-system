package me.akshawop.banking.sys;

public enum AccountType {
    SAVINGS("01"), CURRENT("02");

    private final String code;

    AccountType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}