package me.akshawop.banking.customtype;

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

    @Override
    public String toString() {
        return street + ", " + city + ", " + district + ", " + state
                + ", " + pinCode;
    }

    public void formPrint() {
        System.out.println("Street: " + street);
        System.out.println("City: " + city);
        System.out.println("District: " + district);
        System.out.println("State: " + state);
        System.out.println("Area PIN Code: " + pinCode);
    }
}
