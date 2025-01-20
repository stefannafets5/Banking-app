package org.poo.commerciants;

import java.util.ArrayList;

/**
 * The type Commerciant.
 */
public class Commerciant {
    private String name;
    private int id;
    private String iban;
    private String type;
    private String cashbackStrategy;
    private ArrayList<String> managerList;
    private ArrayList<String> employeeList;
    private double businessMoneySpent = 0;

    /**
     * Instantiates a new Commerciant.
     *
     * @param name             the name
     * @param id               the id
     * @param iban             the iban
     * @param type             the type
     * @param cashbackStrategy the cashback strategy
     */
    public Commerciant(final String name, final int id, final String iban,
                       final String type, final String cashbackStrategy) {
        this.name = name;
        this.id = id;
        this.iban = iban;
        this.type = type;
        this.cashbackStrategy = cashbackStrategy;
        this.managerList = new ArrayList<>();
        this.employeeList = new ArrayList<>();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(final int id) {
        this.id = id;
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
     * Gets cashback strategy.
     *
     * @return the cashback strategy
     */
    public String getCashbackStrategy() {
        return cashbackStrategy;
    }

    /**
     * Sets cashback strategy.
     *
     * @param cashbackStrategy the cashback strategy
     */
    public void setCashbackStrategy(final String cashbackStrategy) {
        this.cashbackStrategy = cashbackStrategy;
    }

    /**
     * Gets manager list.
     *
     * @return the manager list
     */
    public ArrayList<String> getManagerList() {
        return managerList;
    }

    /**
     * Sets manager list.
     *
     * @param managerList the manager list
     */
    public void setManagerList(final ArrayList<String> managerList) {
        this.managerList = managerList;
    }

    /**
     * Gets employee list.
     *
     * @return the employee list
     */
    public ArrayList<String> getEmployeeList() {
        return employeeList;
    }

    /**
     * Sets employee list.
     *
     * @param employeeList the employee list
     */
    public void setEmployeeList(final ArrayList<String> employeeList) {
        this.employeeList = employeeList;
    }

    /**
     * Gets business money spent.
     *
     * @return the business money spent
     */
    public double getBusinessMoneySpent() {
        return businessMoneySpent;
    }

    /**
     * Sets business money spent.
     *
     * @param businessMoneySpent the business money spent
     */
    public void setBusinessMoneySpent(final double businessMoneySpent) {
        this.businessMoneySpent = businessMoneySpent;
    }
}
