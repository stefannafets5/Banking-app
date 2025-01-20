package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Upgrade plan.
 */
public final class UpgradePlan extends Transaction {
    private String iban;
    private String plan;

    /**
     * Instantiates a new Upgrade plan.
     *
     * @param timestamp the timestamp
     * @param iban      the iban
     * @param plan      the plan
     */
    public UpgradePlan(final int timestamp, final String iban, final String plan) {
        super(timestamp, "Upgrade plan");
        this.iban = iban;
        this.plan = plan;
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
     * Gets plan.
     *
     * @return the plan
     */
    public String getPlan() {
        return plan;
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("accountIBAN", getIban());
        txt.put("newPlanType", getPlan());
        return txt;
    }
}
