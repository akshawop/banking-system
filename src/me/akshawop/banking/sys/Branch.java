package me.akshawop.banking.sys;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Branch(ResultSet rs) {
        try {
            this.branchId = rs.getInt("branch_id");
            this.branchCode = rs.getString("branch_code");
            this.branchName = rs.getString("branch_name");
            this.address = new Address(rs);
            this.openingDate = rs.getDate("opening_date");
        } catch (SQLException e) {
            System.err.println("Cannot access the Database while creating new Branch object!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("something went wrong while creating new Branch object!");
            System.err.println("More info:\n" + e);
        }
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
