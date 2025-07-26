package me.akshawop.banking.sys;

import java.io.Serializable;

public record Card(String cardNumber, String cvv, String type) implements Serializable {
}
