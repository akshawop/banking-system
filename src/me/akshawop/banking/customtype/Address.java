package me.akshawop.banking.customtype;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Address {
    public String street;
    public String city;
    public String district;
    public String state;
    public String pinCode;

    public Address() {
        // filled by form
    }

    public Address(String street, String city, String district, String state, String pinCode) {
        this.street = street;
        this.city = city;
        this.district = district;
        this.state = state;
        this.pinCode = pinCode;
    }

    public Address(Address recvAddress) {
        this.street = recvAddress.street;
        this.city = recvAddress.city;
        this.district = recvAddress.district;
        this.state = recvAddress.state;
        this.pinCode = recvAddress.pinCode;
    }

    public Address(ResultSet rs) {
        try {
            this.street = rs.getString("street");
            this.city = rs.getString("city");
            this.district = rs.getString("district");
            this.state = rs.getString("state");
            this.pinCode = rs.getString("pin_code");
        } catch (SQLException e) {
            System.err.println("Cannot access the Database while creating new Address object!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("something went wrong while creating new Address object!");
            System.err.println("More info:\n" + e);
        }
    }

    @Override
    public String toString() {
        return street + ", " + city + ", " + district + ", " + state
                + ", " + pinCode;
    }

    public void formPrint() {
        System.out.println("Street: " + street.toUpperCase());
        System.out.println("City: " + city.toUpperCase());
        System.out.println("District: " + district.toUpperCase());
        System.out.println("State: " + state.toUpperCase());
        System.out.println("Area PIN Code: " + pinCode.toUpperCase());
    }
}
