package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * The type Transaction.
 */
public abstract class Transaction {
    private int timestamp;
    private String description;
    private String fromIban = " ";

    /**
     * Instantiates a new Transaction.
     *
     * @param timestamp   the timestamp
     * @param description the description
     */
    public Transaction(final int timestamp, final String description) {
        this.timestamp = timestamp;
        this.description = description;
    }

    /**
     * Gets from iban.
     *
     * @return the from iban
     */
    public String getFromIban() {
        return fromIban;
    }

    /**
     * Sets from iban.
     *
     * @param fromIban the from iban
     */
    public void setFromIban(final String fromIban) {
        this.fromIban = fromIban;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * To json object node.
     *
     * @param mapper the mapper
     * @return the object node
     */
    public abstract ObjectNode toJson(ObjectMapper mapper);
}
