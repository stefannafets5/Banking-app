package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AddInterest extends Transaction {
    private double amount;
    private String currency;

    public AddInterest(final int timestamp, final double amount, final String currency) {
        super(timestamp, "Interest rate income");
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("amount", getAmount());
        txt.put("currency", getCurrency());
        return txt;
    }
}
