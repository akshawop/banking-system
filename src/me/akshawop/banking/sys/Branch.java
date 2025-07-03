package me.akshawop.banking.sys;

import java.sql.Date;

import me.akshawop.banking.customtype.Address;

public class Branch {
    private int branchId;
    private String branchCode;
    private String branchName;
    private Address address;
    private Date openingDate;

    public Branch(String branchCode, String branchName, Address address) {
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.address = address;
    }

    public Branch(int branchId, String branchCode, String branchName, Address address, Date openingDate) {
        this.branchId = branchId;
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.address = address;
        this.openingDate = openingDate;
    }

    public int getBranchId() {
        return branchId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public Address getAddress() {
        return address;
    }

    public Date getOpeningDate() {
        return openingDate;
    }
}
