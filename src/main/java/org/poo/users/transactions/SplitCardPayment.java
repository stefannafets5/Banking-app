package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

/**
 * The type Split card payment.
 */
public final class SplitCardPayment extends Transaction {
    private double amount;
    private double splitAmount;
    private String currency;
    private ArrayList<String> ibanList;

    /**
     * Instantiates a new Split card payment.
     *
     * @param timestamp    the timestamp
     * @param amount       the amount
     * @param splitAmount the split amount
     * @param currency     the currency
     * @param ibanList     the iban list
     */
    public SplitCardPayment(final int timestamp, final double amount, final double splitAmount,
                            final String currency, final ArrayList<String> ibanList) {
        super(timestamp, "Split payment of " + splitAmount + "0 " + currency);
        this.amount = amount;
        this.currency = currency;
        this.ibanList = ibanList;
        this.splitAmount = splitAmount;
        if (splitAmount % 1 != 0) {
            this.setDescription("Split payment of " + splitAmount + " " + currency);
        }
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
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    /**
     * Gets iban list.
     *
     * @return the iban list
     */
    public ArrayList<String> getIbanList() {
        return ibanList;
    }

    /**
     * Sets iban list.
     *
     * @param ibanList the iban list
     */
    public void setIbanList(final ArrayList<String> ibanList) {
        this.ibanList = ibanList;
    }

    /**
     * Gets split amount.
     *
     * @return the split amount
     */
    public double getSplitAmount() {
        return splitAmount;
    }

    /**
     * Sets split amount.
     *
     * @param splitAmount the split amount
     */
    public void setSplitAmount(final double splitAmount) {
        this.splitAmount = splitAmount;
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("amount", getSplitAmount() / getIbanList().size());
        txt.put("currency", getCurrency());
        txt.putPOJO("involvedAccounts", getIbanList());
        return txt;
    }
}
