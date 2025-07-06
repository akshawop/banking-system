package me.akshawop.banking.sys;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.akshawop.banking.customtype.Address;

public class Customer {
    private int customerId;
    private String name;
    private String aadhaar;
    private String pan = "";
    private Address address;
    private String phone;
    private String email = "";
    private Date registrationDate;

    public Customer(String name, String aadhaar, String pan, Address address, String phone, String email) {
        this.name = name;
        this.aadhaar = aadhaar;
        this.pan = pan;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Customer(ResultSet rs) {
        try {
            String midName = rs.getString("mid_name");
            this.name = rs.getString("first_name") + " " + midName + (midName.length() == 0 ? "" : " ")
                    + rs.getString("last_name");
            this.aadhaar = rs.getString("aadhaar");
            this.pan = (rs.getString("pan") == null ? "" : rs.getString("pan"));
            this.address = new Address(rs);
            this.phone = rs.getString("phone");
            this.email = (rs.getString("email") == null ? "" : rs.getString("email"));
            this.registrationDate = rs.getDate("registration_date");
        } catch (SQLException e) {
            System.err.println("Cannot access the Database while creating new Address object!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("something went wrong while creating new Address object!");
            System.err.println("More info:\n" + e);
        }
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public String getPan() {
        return pan;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
