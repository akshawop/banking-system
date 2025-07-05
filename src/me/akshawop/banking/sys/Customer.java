package me.akshawop.banking.sys;

import java.sql.Date;

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

    public Customer(int customerId, String name, String aadhaar, String pan, Address address, String phone,
            String email, Date registrationDate) {
        this.customerId = customerId;
        this.name = name;
        this.aadhaar = aadhaar;
        this.pan = pan;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.registrationDate = registrationDate;
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
