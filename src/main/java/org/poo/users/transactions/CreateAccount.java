package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Create account.
 */
public final class CreateAccount extends Transaction {

    /**
     * Instantiates a new Create account.
     *
     * @param timestamp the timestamp
     */
    public CreateAccount(final int timestamp) {
        super(timestamp, "New account created");
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        return txt;
    }
}
