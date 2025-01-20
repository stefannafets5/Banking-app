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
    private ArrayList<Double> amountList;
    private String currency;
    private String poor;
    private String type;
    private double amount;
    private int isRejected;
    private ArrayList<String> ibanList;


    /**
     * Instantiates a new Split payment failed.
     *
     * @param input      the input
     * @param poor       the poor
     * @param fromIban   the from iban
     * @param amountList the amount list
     * @param splitType  the split type
     * @param amount     the amount
     * @param isRejected the is rejected
     */
    public SplitPaymentFailed(final CommandInput input, final String poor, final String fromIban,
                              final ArrayList<Double> amountList, final String splitType,
                              final double amount, final int isRejected) {
        super(input.getTimestamp(), "Split payment of "
                + input.getAmount() + "0 " + input.getCurrency());
        if (splitType.equals("custom") && isRejected == 0) {
            this.setDescription("Split payment of "
                    + input.getAmount() + " " + input.getCurrency());
        }
        this.amountList = amountList;
        this.currency = input.getCurrency();
        this.poor = poor;
        this.type = splitType;
        this.amount = amount / input.getAccounts().size();
        this.isRejected = isRejected;
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

    /**
     * Gets is rejected.
     *
     * @return the is rejected
     */
    public int getIsRejected() {
        return isRejected;
    }

    /**
     * Sets is rejected.
     *
     * @param isRejected the is rejected
     */
    public void setIsRejected(final int isRejected) {
        this.isRejected = isRejected;
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();

        if (getType().equals("custom")) {
            ArrayNode amountForUsers = mapper.createArrayNode();

            for (Double currentAmount : getAmountList()) {
                amountForUsers.add(currentAmount);
            }

            txt.set("amountForUsers", amountForUsers);
        } else {
            txt.put("amount", getAmount());
        }

        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("splitPaymentType", getType());
        txt.put("currency", getCurrency());
        if (getIsRejected() == 0) {
            txt.put("error", "Account " + poor
                    + " has insufficient funds for a split payment.");
        } else {
            txt.put("error", "One user rejected the payment.");
        }
        ArrayNode involvedAccounts = mapper.createArrayNode();
        for (String iban : ibanList) {
            involvedAccounts.add(iban);
        }
        txt.set("involvedAccounts", involvedAccounts);
        return txt;
    }
}
