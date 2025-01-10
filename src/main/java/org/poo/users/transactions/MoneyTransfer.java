package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Money transfer.
 */
public final class MoneyTransfer extends Transaction {
    private String senderIban;
    private String receiverIban;
    private double amount;
    private String transferType;
    private String currency;

    /**
     * Instantiates a new Money transfer.
     *
     * @param timestamp    the timestamp
     * @param description  the description
     * @param senderIban   the sender iban
     * @param receiverIban the receiver iban
     * @param amount       the amount
     * @param transferType the transfer type
     * @param currency     the currency
     */
    public MoneyTransfer(final int timestamp, final String description, final String senderIban,
                         final String receiverIban, final double amount, final String transferType,
                         final String currency) {
        super(timestamp, description);
        this.senderIban = senderIban;
        this.receiverIban = receiverIban;
        this.amount = amount;
        this.transferType = transferType;
        this.currency = currency;
    }

    /**
     * Gets sender iban.
     *
     * @return the sender iban
     */
    public String getSenderIban() {
        return senderIban;
    }

    /**
     * Sets sender iban.
     *
     * @param senderIban the sender iban
     */
    public void setSenderIban(final String senderIban) {
        this.senderIban = senderIban;
    }

    /**
     * Gets receiver iban.
     *
     * @return the receiver iban
     */
    public String getReceiverIban() {
        return receiverIban;
    }

    /**
     * Sets receiver iban.
     *
     * @param receiverIban the receiver iban
     */
    public void setReceiverIban(final String receiverIban) {
        this.receiverIban = receiverIban;
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
     * Gets transfer type.
     *
     * @return the transfer type
     */
    public String getTransferType() {
        return transferType;
    }

    /**
     * Sets transfer type.
     *
     * @param transferType the transfer type
     */
    public void setTransferType(final String transferType) {
        this.transferType = transferType;
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

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("senderIBAN", getSenderIban());
        txt.put("receiverIBAN", getReceiverIban());
        txt.put("amount", getAmount() + " " + getCurrency());
        txt.put("transferType", getTransferType());
        return txt;
    }
}
