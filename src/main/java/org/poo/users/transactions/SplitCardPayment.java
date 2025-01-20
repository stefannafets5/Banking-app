package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

/**
 * The type Split card payment.
 */
public final class SplitCardPayment extends Transaction {
    private ArrayList<Double> amountList;
    private double splitAmount;
    private String currency;
    private String type;
    private ArrayList<String> ibanList;

    /**
     * Instantiates a new Split card payment.
     *
     * @param timestamp   the timestamp
     * @param amountList  the amount list
     * @param splitAmount the split amount
     * @param currency    the currency
     * @param ibanList    the iban list
     * @param type        the type
     */
    public SplitCardPayment(final int timestamp, final ArrayList<Double> amountList,
                            final double splitAmount, final String currency,
                            final ArrayList<String> ibanList, final String type) {
        super(timestamp, "Split payment of " + splitAmount + "0 " + currency);
        this.amountList = amountList;
        this.currency = currency;
        this.ibanList = ibanList;
        this.splitAmount = splitAmount;
        this.type = type;
        if (splitAmount % 1 != 0) {
            this.setDescription("Split payment of " + splitAmount + " " + currency);
        }
    }

    /**
     * Gets amount list.
     *
     * @return the amount list
     */
    public ArrayList<Double> getAmountList() {
        return amountList;
    }

    /**
     * Sets amount list.
     *
     * @param amountList the amount list
     */
    public void setAmountList(final ArrayList<Double> amountList) {
        this.amountList = amountList;
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

    /**
     * Gets split amount.
     *
     * @return the split amount
     */
    public double getSplitAmount() {
        return splitAmount;
    }

    /**
     * Sets split amount.
     *
     * @param splitAmount the split amount
     */
    public void setSplitAmount(final double splitAmount) {
        this.splitAmount = splitAmount;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        ArrayNode amountForUsers = mapper.createArrayNode();

        for (Double amount : getAmountList()) {
            amountForUsers.add(amount);
        }
        if (type.equals("custom")) {
            txt.set("amountForUsers", amountForUsers);
        } else {
            txt.put("amount", getAmountList().getFirst());
        }
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("currency", getCurrency());
        txt.put("splitPaymentType", getType());
        txt.putPOJO("involvedAccounts", getIbanList());
        return txt;
    }
}
