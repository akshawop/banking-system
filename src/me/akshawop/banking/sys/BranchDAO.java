package me.akshawop.banking.sys;

public class BranchDAO {
    private Branch branch;

    public BranchDAO() {
    }

    public BranchDAO(Branch branch) {
        this.branch = branch;
    }

    public void addBranchToDB(Branch branch) {
        // add branch to DB
    }

    void printBranchInfo() {
        // printBranchInfo
    }

    void addCustomer(Customer customer) {

    }

    Customer getCustomer(int customerId) {
        return null;
    }

    void updateCustomer(int customeId) {

    }

    protected Account accessAccount(int accountNumber) {
        return null;
    }

    void listCustomer(int from, int limit) {

    }

    void listAccounts(int from, int limit) {

    }
}
