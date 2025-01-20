package org.poo.users.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SavingsWithdrawal extends Transaction {
    private double amount;
    private String classicIban;
    private String SavingsIban;

    public SavingsWithdrawal(int timestamp, String description, double amount, String classicIban, String savingsIban) {
        super(timestamp, description);
        this.amount = amount;
        this.classicIban = classicIban;
        SavingsIban = savingsIban;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getClassicIban() {
        return classicIban;
    }

    public void setClassicIban(String classicIban) {
        this.classicIban = classicIban;
    }

    public String getSavingsIban() {
        return SavingsIban;
    }

    public void setSavingsIban(String savingsIban) {
        SavingsIban = savingsIban;
    }

    @Override
    public ObjectNode toJson(final ObjectMapper mapper) {
        ObjectNode txt = mapper.createObjectNode();
        txt.put("timestamp", getTimestamp());
        txt.put("description", getDescription());
        txt.put("classicAccountIBAN", getClassicIban());
        txt.put("savingsAccountIBAN", getSavingsIban());
        txt.put("amount", getAmount());
        return txt;
    }
}
