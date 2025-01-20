package org.poo.users;

import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * The type Business account.
 */
public class BusinessAccount extends Account {
    private String ownerEmail;
    private ArrayList<String> managerEmails;
    private ArrayList<String> employeeEmails;
    private LinkedHashMap<String, Double> managerDeposits;
    private LinkedHashMap<String, Double> managerSpending;
    private LinkedHashMap<String, Double> employeeDeposits;
    private LinkedHashMap<String, Double> employeeSpending;
    private double spendingLimit = Utils.LIMIT;
    private double depositLimit = Utils.LIMIT;
    private double totalDeposited = 0;
    private double totalSpent = 0;
    private ArrayList<String> outOrderForReportManager;
    private ArrayList<String> outOrderForReportEmployee;

    /**
     * Instantiates a new Business account.
     *
     * @param currency   the currency
     * @param type       the type
     * @param ownerEmail the owner email
     */
    public BusinessAccount(final String currency, final String type, final String ownerEmail) {
        super(currency, type);
        this.ownerEmail = ownerEmail;
        this.employeeEmails = new ArrayList<>();
        this.managerEmails = new ArrayList<>();
        this.outOrderForReportManager = new ArrayList<>();
        this.outOrderForReportEmployee = new ArrayList<>();
        this.managerDeposits = new LinkedHashMap<>();
        this.managerSpending = new LinkedHashMap<>();
        this.employeeDeposits = new LinkedHashMap<>();
        this.employeeSpending = new LinkedHashMap<>();
    }

    /**
     * Gets owner email.
     *
     * @return the owner email
     */
    public String getOwnerEmail() {
        return ownerEmail;
    }

    /**
     * Sets owner email.
     *
     * @param ownerEmail the owner email
     */
    public void setOwnerEmail(final String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    /**
     * Gets manager emails.
     *
     * @return the manager emails
     */
    public ArrayList<String> getManagerEmails() {
        return managerEmails;
    }

    /**
     * Sets manager emails.
     *
     * @param managerEmails the manager emails
     */
    public void setManagerEmails(final ArrayList<String> managerEmails) {
        this.managerEmails = managerEmails;
    }

    /**
     * Gets employee emails.
     *
     * @return the employee emails
     */
    public ArrayList<String> getEmployeeEmails() {
        return employeeEmails;
    }

    /**
     * Sets employee emails.
     *
     * @param employeeEmails the employee emails
     */
    public void setEmployeeEmails(final ArrayList<String> employeeEmails) {
        this.employeeEmails = employeeEmails;
    }

    /**
     * Gets spending limit.
     *
     * @return the spending limit
     */
    public double getSpendingLimit() {
        return spendingLimit;
    }

    /**
     * Sets spending limit.
     *
     * @param spendingLimit the spending limit
     */
    public void setSpendingLimit(final double spendingLimit) {
        this.spendingLimit = spendingLimit;
    }

    /**
     * Gets deposit limit.
     *
     * @return the deposit limit
     */
    public double getDepositLimit() {
        return depositLimit;
    }

    /**
     * Sets deposit limit.
     *
     * @param depositLimit the deposit limit
     */
    public void setDepositLimit(final double depositLimit) {
        this.depositLimit = depositLimit;
    }

    /**
     * Gets total deposited.
     *
     * @return the total deposited
     */
    public double getTotalDeposited() {
        return totalDeposited;
    }

    /**
     * Sets total deposited.
     *
     * @param totalDeposited the total deposited
     */
    public void setTotalDeposited(final double totalDeposited) {
        this.totalDeposited = totalDeposited;
    }

    /**
     * Gets total spent.
     *
     * @return the total spent
     */
    public double getTotalSpent() {
        return totalSpent;
    }

    /**
     * Sets total spent.
     *
     * @param totalSpent the total spent
     */
    public void setTotalSpent(final double totalSpent) {
        this.totalSpent = totalSpent;
    }

    /**
     * Gets manager deposits.
     *
     * @return the manager deposits
     */
    public LinkedHashMap<String, Double> getManagerDeposits() {
        return managerDeposits;
    }

    /**
     * Sets manager deposits.
     *
     * @param managerDeposits the manager deposits
     */
    public void setManagerDeposits(final LinkedHashMap<String, Double> managerDeposits) {
        this.managerDeposits = managerDeposits;
    }

    /**
     * Gets manager spending.
     *
     * @return the manager spending
     */
    public LinkedHashMap<String, Double> getManagerSpending() {
        return managerSpending;
    }

    /**
     * Sets manager spending.
     *
     * @param managerSpending the manager spending
     */
    public void setManagerSpending(final LinkedHashMap<String, Double> managerSpending) {
        this.managerSpending = managerSpending;
    }

    /**
     * Gets employee deposits.
     *
     * @return the employee deposits
     */
    public LinkedHashMap<String, Double> getEmployeeDeposits() {
        return employeeDeposits;
    }

    /**
     * Sets employee deposits.
     *
     * @param employeeDeposits the employee deposits
     */
    public void setEmployeeDeposits(final LinkedHashMap<String, Double> employeeDeposits) {
        this.employeeDeposits = employeeDeposits;
    }

    /**
     * Gets employee spending.
     *
     * @return the employee spending
     */
    public LinkedHashMap<String, Double> getEmployeeSpending() {
        return employeeSpending;
    }

    /**
     * Sets employee spending.
     *
     * @param employeeSpending the employee spending
     */
    public void setEmployeeSpending(final LinkedHashMap<String, Double> employeeSpending) {
        this.employeeSpending = employeeSpending;
    }

    /**
     * Gets out order for report manager.
     *
     * @return the out order for report manager
     */
    public ArrayList<String> getOutOrderForReportManager() {
        return outOrderForReportManager;
    }

    /**
     * Sets out order for report manager.
     *
     * @param outOrderForReportManager the out order for report manager
     */
    public void setOutOrderForReportManager(final ArrayList<String> outOrderForReportManager) {
        this.outOrderForReportManager = outOrderForReportManager;
    }

    /**
     * Gets out order for report employee.
     *
     * @return the out order for report employee
     */
    public ArrayList<String> getOutOrderForReportEmployee() {
        return outOrderForReportEmployee;
    }

    /**
     * Sets out order for report employee.
     *
     * @param outOrderForReportEmployee the out order for report employee
     */
    public void setOutOrderForReportEmployee(final ArrayList<String> outOrderForReportEmployee) {
        this.outOrderForReportEmployee = outOrderForReportEmployee;
    }

    /**
     * Record manager deposit.
     *
     * @param name   the name
     * @param amount the amount
     */
    public void recordManagerDeposit(final String name, final double amount) {
        managerDeposits.put(name, managerDeposits.getOrDefault(name, 0.0) + amount);
    }

    /**
     * Record manager spending.
     *
     * @param name   the name
     * @param amount the amount
     */
    public void recordManagerSpending(final String name, final double amount) {
        managerSpending.put(name, managerSpending.getOrDefault(name, 0.0) + amount);
    }

    /**
     * Record employee deposit.
     *
     * @param name   the name
     * @param amount the amount
     */
    public void recordEmployeeDeposit(final String name, final double amount) {
        employeeDeposits.put(name, employeeDeposits.getOrDefault(name, 0.0) + amount);
    }

    /**
     * Record employee spending.
     *
     * @param name   the name
     * @param amount the amount
     */
    public void recordEmployeeSpending(final String name, final double amount) {
        System.out.println(amount);
        employeeSpending.put(name, employeeSpending.getOrDefault(name, 0.0) + amount);
        System.out.println(employeeSpending.get(name));
    }

    /**
     * Add total spent.
     *
     * @param amount the amount
     */
    public void addTotalSpent(final double amount) {
        this.totalSpent += amount;
    }

    /**
     * Add total deposited.
     *
     * @param amount the amount
     */
    public void addTotalDeposited(final double amount) {
        this.totalDeposited += amount;
    }

    /**
     * Is owner boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isOwner(final String email) {
        return ownerEmail.equals(email);
    }

    /**
     * Is manager boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isManager(final String email) {
        return managerEmails.contains(email);
    }

    /**
     * Is employee boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isEmployee(final String email) {
        return employeeEmails.contains(email);
    }

    /**
     * Is associate boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isAssociate(final String email) {
        return (isOwner(email) || isManager(email) || isEmployee(email));
    }
}
