package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class WithdrawCash extends Transaction {
    private double amount;

    public WithdrawCash(final int timestamp, final double amount) {
        super(timestamp, "Cash withdrawal of " + amount);
        this.amount = amount;
    }

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
