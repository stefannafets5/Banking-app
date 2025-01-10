package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Card payment.
 */
public final class CardPayment extends Transaction {
    private double amount;
    private String commerciant;
    private String iban;

    /**
     * Instantiates a new Card payment.
     *
     * @param timestamp   the timestamp
     * @param amount      the amount
     * @param commerciant the commerciant
     * @param iban        the iban
     */
    public CardPayment(final int timestamp, final double amount,
                       final String commerciant, final String iban) {
        super(timestamp, "Card payment");
        this.amount = amount;
        this.commerciant = commerciant;
        this.iban = iban;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(final double amount) {
        this.amount = amount;
    }

    /**
     * Gets commerciant.
     *
     * @return the commerciant
     */
    public String getCommerciant() {
        return commerciant;
    }

    /**
     * Sets commerciant.
     *
     * @param commerciant the commerciant
     */
    public void setCommerciant(final String commerciant) {
        this.commerciant = commerciant;
    }

    /**
     * Gets iban.
     *
     * @return the iban
     */
    public String getIban() {
        return iban;
    }

    /**
     * Sets iban.
     *
     * @param iban the iban
     */
    public void setIban(final String iban) {
        this.iban = iban;
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("amount", getAmount());
        txt.put("commerciant", getCommerciant());
        return txt;
    }
}
