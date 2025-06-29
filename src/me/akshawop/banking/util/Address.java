package me.akshawop.banking.util;

public class Address {
    public String street;
    public String city;
    public String district;
    public String state;
    public String pinCode;

    public Address() {
        // form
    }

    public Address(String street, String city, String district, String state, String pinCode) {
        this.street = street;
        this.city = city;
        this.district = district;
        this.state = state;
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return street + ", " + city + ", " + district + ", " + state
                + ", " + pinCode;
    }

}
