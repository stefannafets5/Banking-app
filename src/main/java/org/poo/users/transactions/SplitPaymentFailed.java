package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;

import java.util.ArrayList;

/**
 * The type Split payment failed.
 */
public final class SplitPaymentFailed extends Transaction {
    private double amount;
    private String currency;
    private String poor;
    private ArrayList<String> ibanList;


    /**
     * Instantiates a new Split payment failed.
     *
     * @param input    the input
     * @param poor     the poor
     * @param fromIban the from iban
     */
    public SplitPaymentFailed(final CommandInput input, final String poor, final String fromIban) {
        super(input.getTimestamp(), "Split payment of "
                + input.getAmount() + "0 " + input.getCurrency());
        this.amount = input.getAmount() / input.getAccounts().size();
        this.currency = input.getCurrency();
        this.poor = poor;
        this.ibanList = (ArrayList<String>) input.getAccounts();
        setFromIban(fromIban);
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

    /**
     * Gets poor.
     *
     * @return the poor
     */
    public String getPoor() {
        return poor;
    }

    /**
     * Sets poor.
     *
     * @param poor the poor
     */
    public void setPoor(final String poor) {
        this.poor = poor;
    }

    /**
     * Gets iban list.
     *
     * @return the iban list
     */
    public ArrayList<String> getIbanList() {
        return ibanList;
    }

    /**
     * Sets iban list.
     *
     * @param ibanList the iban list
     */
    public void setIbanList(final ArrayList<String> ibanList) {
        this.ibanList = ibanList;
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("amount", getAmount());
        txt.put("currency", getCurrency());
        txt.put("error", "Account " + poor
                + " has insufficient funds for a split payment.");
        ArrayNode involvedAccounts = mapper.createArrayNode();
        for (String iban : ibanList) {
            involvedAccounts.add(iban);
        }
        txt.set("involvedAccounts", involvedAccounts);
        return txt;
    }
}
