package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Error.
 */
public final class Error extends Transaction {

    /**
     * Instantiates a new Error.
     *
     * @param timestamp   the timestamp
     * @param description the description
     */
    public Error(final int timestamp, final String description) {
        super(timestamp, description);
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        return txt;
    }
}
