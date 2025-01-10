package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Payment failed.
 */
public final class PaymentFailed extends Transaction {

    /**
     * Instantiates a new Payment failed.
     *
     * @param timestamp the timestamp
     */
    public PaymentFailed(final int timestamp) {
        super(timestamp, "Insufficient funds");
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        return txt;
    }
}
