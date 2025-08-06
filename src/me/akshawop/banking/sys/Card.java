package me.akshawop.banking.sys;

import java.io.Serializable;

public record Card(String cardNumber, String cvv, CardType type) implements Serializable {
}
