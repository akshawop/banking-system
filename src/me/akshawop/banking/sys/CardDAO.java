package me.akshawop.banking.sys;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.time.LocalDate;

import me.akshawop.banking.sql.SQLQueries;

/**
 * The Data Access Object of {@code Card}
 * 
 * @see Card
 */
public class CardDAO {
    /**
     * Fetch the data of a Card from the Database.
     * 
     * @param cardNumber The {@code String} Card Number of the Card whose data
     *                   to
     *                   be fetched
     * 
     * @return {@code Card}(record) object if exists; {@code null} if doesn't
     * 
     * @log an error message if any error occurs
     */
    public static Card fetchCardDetails(String cardNumber) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getCardFromDB(cardNumber));

            if (!rs.next()) {
                con.close();
                return null;
            } else {
                String cvv = rs.getString("cvv");
                CardType type = CardType.valueOf(rs.getString("card_type"));
                con.close();
                return new Card(cardNumber, cvv, type);
            }
        } catch (SQLTimeoutException e) {
            System.err.println("Error: Database timeout!");
            System.err.println("More info:\n" + e);
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        return null;
    }

    /**
     * Generates a new Card in the Database and returns it. Uses
     * {@link CardDAO#fetchCardDetails}
     * 
     * @param type             The {@code CardType} type object of the type of card
     *                         to create
     * @param accountNumber    The {@code int} Account Number of the Account
     * @param expireAfterYears The {@code int} years after which the card should
     *                         expire
     * 
     * @return {@code Card}(record) object if created successfully; {@code null} if
     *         not
     * 
     * @log an error message if any error occurs
     * 
     * @see Card
     * @see CardType
     */
    public static Card createNewCard(CardType type, int accountNumber, int expireAfterYears) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();

            // create new card number until its unique in the DB
            SecureRandom sr = new SecureRandom();
            String cardNumber;
            do {
                cardNumber = sr.nextLong(1000000000000000l, 10000000000000000l) + "";
            } while (st.executeQuery(SQLQueries.getCardFromDB(cardNumber)).next());

            String cvv = sr.nextInt(100, 1000) + "";
            Date expiryDate = Date.valueOf((LocalDate.now()).plusYears(expireAfterYears));
            String cardPin = sr.nextInt(1000, 10000) + "";

            st.executeUpdate(SQLQueries.createCardInDB(cardNumber, cvv, type, accountNumber, expiryDate, cardPin));
            con.close();

            System.out.println("\n    !!!!ALERT!!!!");
            System.out.println("\nDefault Card Pin: " + cardPin + "\n");
            System.out.println("\n---NOTE IT DOWN RIGHT NOW!---\n");

            return fetchCardDetails(cardNumber);
        } catch (SQLTimeoutException e) {
            System.err.println("Error: Database timeout!");
            System.err.println("More info:\n" + e);
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        return null;
    }

    /**
     * Fetch the data of a Account of the provided Card from the Database.
     * 
     * @param card The {@code Card} object of the Card whose Account data is
     *             to
     *             be fetched
     * 
     * @return {@code Account} object if exists; {@code null} if doesn't
     * 
     * @log an error message if any error occurs
     */
    public static Account getAccount(Card card) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getAccountFromDB(card.cardNumber(), card.cvv()));

            if (!rs.next()) {
                con.close();
                return null;
            } else {
                Account account = new Account(rs);
                con.close();
                return account;
            }
        } catch (SQLTimeoutException e) {
            System.err.println("Error: Database timeout!");
            System.err.println("More info:\n" + e);
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        return null;
    }

    /**
     * Fetches the Status of the Card provided from the database.
     * 
     * @param card The {@code Card} object of the card
     * 
     * @return {@code CardStatus} object; {@code null} if can't fetch
     * 
     * @log an error message if any error occurs
     */
    public static CardStatus getCardStatus(Card card) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getCardStatus(card.cardNumber(), card.cvv()));

            if (!rs.next()) {
                con.close();
                return null;
            } else {
                CardStatus status = CardStatus.valueOf(rs.getString(1));
                con.close();
                return status;
            }
        } catch (SQLTimeoutException e) {
            System.err.println("Error: Database timeout!");
            System.err.println("More info:\n" + e);
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        return null;
    }

    /**
     * Fetches the Card pin of the Card object provided from Database.
     * 
     * @param card The {@code Card} object of the card
     * 
     * @return {@code String} card pin; {@code null} if can't fetch
     * 
     * @log an error message if any error occurs
     */
    public static String getCardPin(Card card) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getCardPinFromDB(card.cardNumber(), card.cvv()));

            if (!rs.next()) {
                con.close();
                return null;
            } else {
                String pin = rs.getString(1);
                con.close();
                return pin;
            }
        } catch (SQLTimeoutException e) {
            System.err.println("Error: Database timeout!");
            System.err.println("More info:\n" + e);
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
        return null;
    }

    /**
     * Lists all the Card's data which belong to the given Account Number from the
     * Database.
     * 
     * @param accountNumber The {@code int} Account Number
     * 
     * @log an error message if any error occurs
     */
    public static void printCardInfoOfAccount(int accountNumber) {
        try {
            Connection con = DB.connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQLQueries.getAllCardsFromDB(accountNumber));

            if (!rs.next()) {
                System.out.println("\nNo Card is issued to this Account.\n");
                con.close();
            } else {
                System.out.println("Card(s) found!\n");
                do {
                    System.out.println("Card Number: " + rs.getString("card_number"));
                    System.out.println("CVV Number: " + rs.getString("cvv"));
                    System.out.println("Expiry Date: " + rs.getDate("expiry_date"));
                    System.out.println("Status: " + rs.getString("card_status"));
                    System.out.println();
                } while (rs.next());
                System.out.println();
                con.close();
            }
        } catch (SQLTimeoutException e) {
            System.err.println("Error: Database timeout!");
            System.err.println("More info:\n" + e);
        } catch (SQLException e) {
            System.err.println("Error: Database Access Error!");
            System.err.println("More info:\n" + e);
        } catch (Exception e) {
            System.err.println("Error: something went wrong!");
            System.err.println("More info:\n" + e);
        }
    }
}
