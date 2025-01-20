package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Savings withdrawal.
 */
public final class SavingsWithdrawal extends Transaction {
    private double amount;
    private String classicIban;
    private String savingsIban;

    /**
     * Instantiates a new Savings withdrawal.
     *
     * @param timestamp   the timestamp
     * @param description the description
     * @param amount      the amount
     * @param classicIban the classic iban
     * @param savingsIban the savings iban
     */
    public SavingsWithdrawal(final int timestamp, final String description,
                             final double amount, final String classicIban,
                             final String savingsIban) {
        super(timestamp, description);
        this.amount = amount;
        this.classicIban = classicIban;
        this.savingsIban = savingsIban;
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
     * Gets classic iban.
     *
     * @return the classic iban
     */
    public String getClassicIban() {
        return classicIban;
    }

    /**
     * Sets classic iban.
     *
     * @param classicIban the classic iban
     */
    public void setClassicIban(final String classicIban) {
        this.classicIban = classicIban;
    }

    /**
     * Gets savings iban.
     *
     * @return the savings iban
     */
    public String getSavingsIban() {
        return savingsIban;
    }

    /**
     * Sets savings iban.
     *
     * @param savingsIban the savings iban
     */
    public void setSavingsIban(final String savingsIban) {
        this.savingsIban = savingsIban;
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("classicAccountIBAN", getClassicIban());
        txt.put("savingsAccountIBAN", getSavingsIban());
        txt.put("amount", getAmount());
        return txt;
    }
}
