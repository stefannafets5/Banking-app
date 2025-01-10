package org.poo.users;

import org.poo.users.transactions.CreateOrDeleteCard;
import org.poo.users.transactions.Transaction;
import org.poo.utils.Utils;

import java.util.ArrayList;

/**
 * The type Card.
 */
public class Card {
    private String status;
    private String cardNumber;
    private String type;

    /**
     *
     * @param type the type
     */

    public Card(final String type) {
        this.status = "active";
        this.cardNumber = Utils.generateCardNumber();
        this.type = type;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Gets card number.
     *
     * @return the card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets card number.
     *
     * @param cardNumber the card number
     */
    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Add card creation transaction.
     *
     * @param time    the timestamp
     * @param email        the email
     * @param iban         the iban
     * @param transactions the transactions
     * @param description  the description
     */
    public void addCardCreationTransaction(final int time, final String email, final String iban,
                                           final ArrayList<Transaction> transactions,
                                           final String description) {
        transactions.add(new CreateOrDeleteCard(time, email, getCardNumber(), iban, description));
    }
}
