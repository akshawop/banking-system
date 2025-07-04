package me.akshawop.banking.sys;

public class BranchDAO {
    private Branch branch;

    protected BranchDAO(Branch branch) {
        this.branch = branch;
    }

    protected void addCustomer(Customer customer) {

    }

    protected Customer getCustomer(int customerId) {
        return null;
    }

    protected void updateCustomer(int customerId) {

    }

    protected Account accessAccount(int accountNumber) {
        return null;
    }

    protected void listAccounts(int from, int limit) {

    }

    protected void showBranchInfo() {
        // printBranchInfo
    }
}
