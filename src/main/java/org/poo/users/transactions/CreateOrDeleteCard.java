package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Create or delete card.
 */
public final class CreateOrDeleteCard extends Transaction {
    private String cardNr;
    private String email;
    private String iban;

    /**
     * Instantiates a new Create or delete card.
     *
     * @param timestamp   the timestamp
     * @param email       the email
     * @param cardNr      the card nr
     * @param iban        the iban
     * @param description the description
     */
    public CreateOrDeleteCard(final int timestamp, final String email, final String cardNr,
                              final String iban, final String description) {
        super(timestamp, description);
        this.cardNr = cardNr;
        this.email = email;
        this.iban = iban;
        setFromIban(iban);
    }

    /**
     * Gets card nr.
     *
     * @return the card nr
     */
    public String getCardNr() {
        return cardNr;
    }

    /**
     * Sets card nr.
     *
     * @param cardNr the card nr
     */
    public void setCardNr(final String cardNr) {
        this.cardNr = cardNr;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets iban.
     *
     * @return the iban
     */
    public String getIban() {
        return iban;
    }

    /**
     * Sets iban.
     *
     * @param iban the iban
     */
    public void setIban(final String iban) {
        this.iban = iban;
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("card", getCardNr());
        txt.put("cardHolder", getEmail());
        txt.put("account", getIban());
        return txt;
    }
}
