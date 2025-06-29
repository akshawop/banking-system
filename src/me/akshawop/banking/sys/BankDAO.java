package me.akshawop.banking.sys;

import me.akshawop.banking.cli.BankCLI;

public sealed class BankDAO permits BankCLI {
    protected static Bank bank;

    protected static Bank getBank() {
        // getBank
        return null;
    }

    protected static void addBank(Bank bank) {
        // addBankToDB
    }

    protected static void updateBankData(Bank bank) {
        // updateBankData
    }

    protected static void addBranch(Branch branch) {
        // addBranchToDB
    }

    protected static void removeBranch(String branchCode) {
        // removeBranch
    }

    protected static Branch getBranch(String branchCode) {
        // getBranch
        return null;
    }

    protected static void listBranches() {
        // listBranches
    }

    protected static void listCustomers(int from, int limit) {

    }

    protected static void listAccounts(int from, int limit) {

    }
}
