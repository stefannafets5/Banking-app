package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Withdraw cash.
 */
public final class WithdrawCash extends Transaction {
    private double amount;

    /**
     * Instantiates a new Withdraw cash.
     *
     * @param timestamp the timestamp
     * @param amount    the amount
     */
    public WithdrawCash(final int timestamp, final double amount) {
        super(timestamp, "Cash withdrawal of " + amount);
        this.amount = amount;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }


    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("amount", getAmount());
        return txt;
    }
}
