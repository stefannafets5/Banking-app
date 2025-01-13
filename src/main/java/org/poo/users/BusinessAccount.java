package org.poo.users;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BusinessAccount extends Account {
    private String ownerEmail;
    private ArrayList<String> managerEmails;
    private ArrayList<String> employeeEmails;
//    private HashMap<String, Double> managerDeposits;
//    private HashMap<String, Double> managerSpending;
//    private HashMap<String, Double> employeeDeposits;
//    private HashMap<String, Double> employeeSpending;
    private LinkedHashMap<String, Double> managerDeposits;
    private LinkedHashMap<String, Double> managerSpending;
    private LinkedHashMap<String, Double> employeeDeposits;
    private LinkedHashMap<String, Double> employeeSpending;
    private double spendingLimit = 500;
    private double depositLimit = 500;
    private double convertedDepositLimit;
    private double totalDeposited = 0;
    private double totalSpent = 0;

    public BusinessAccount(final String currency, final String type, final String ownerEmail) {
        super(currency, type);
        this.ownerEmail = ownerEmail;
        this.employeeEmails = new ArrayList<>();
        this.managerEmails = new ArrayList<>();
        this.managerDeposits = new LinkedHashMap<>();
        this.managerSpending = new LinkedHashMap<>();
        this.employeeDeposits = new LinkedHashMap<>();
        this.employeeSpending = new LinkedHashMap<>();
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public ArrayList<String> getManagerEmails() {
        return managerEmails;
    }

    public void setManagerEmails(ArrayList<String> managerEmails) {
        this.managerEmails = managerEmails;
    }

    public ArrayList<String> getEmployeeEmails() {
        return employeeEmails;
    }

    public void setEmployeeEmails(ArrayList<String> employeeEmails) {
        this.employeeEmails = employeeEmails;
    }

    public double getSpendingLimit() {
        return spendingLimit;
    }

    public void setSpendingLimit(double spendingLimit) {
        this.spendingLimit = spendingLimit;
    }

    public double getDepositLimit() {
        return depositLimit;
    }

    public void setDepositLimit(double depositLimit) {
        this.depositLimit = depositLimit;
    }

    public double getConvertedDepositLimit() {
        return convertedDepositLimit;
    }

    public void setConvertedDepositLimit(double convertedDepositLimit) {
        this.convertedDepositLimit = convertedDepositLimit;
    }

    public double getTotalDeposited() {
        return totalDeposited;
    }

    public void setTotalDeposited(double totalDeposited) {
        this.totalDeposited = totalDeposited;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public LinkedHashMap<String, Double> getManagerDeposits() {
        return managerDeposits;
    }

    public void setManagerDeposits(LinkedHashMap<String, Double> managerDeposits) {
        this.managerDeposits = managerDeposits;
    }

    public LinkedHashMap<String, Double> getManagerSpending() {
        return managerSpending;
    }

    public void setManagerSpending(LinkedHashMap<String, Double> managerSpending) {
        this.managerSpending = managerSpending;
    }

    public LinkedHashMap<String, Double> getEmployeeDeposits() {
        return employeeDeposits;
    }

    public void setEmployeeDeposits(LinkedHashMap<String, Double> employeeDeposits) {
        this.employeeDeposits = employeeDeposits;
    }

    public LinkedHashMap<String, Double> getEmployeeSpending() {
        return employeeSpending;
    }

    public void setEmployeeSpending(LinkedHashMap<String, Double> employeeSpending) {
        this.employeeSpending = employeeSpending;
    }

    public void recordManagerDeposit(String name, double amount) {
        managerDeposits.put(name, managerDeposits.getOrDefault(name, 0.0) + amount);
    }

    public void recordManagerSpending(String name, double amount) {
        managerSpending.put(name, managerSpending.getOrDefault(name, 0.0) + amount);
    }

    public void recordEmployeeDeposit(String name, double amount) {
        employeeDeposits.put(name, employeeDeposits.getOrDefault(name, 0.0) + amount);
    }

    public void recordEmployeeSpending(String name, double amount) {
        employeeSpending.put(name, employeeSpending.getOrDefault(name, 0.0) + amount);
    }

    public void addTotalSpent(double amount) {
        this.totalSpent += amount;
    }

    public void addTotalDeposited(double amount) {
        this.totalDeposited += amount;
    }

    public boolean isOwner(final String Email) {
        return ownerEmail.equals(Email);
    }

    public boolean isManager(final String Email) {
        return managerEmails.contains(Email);
    }

    public boolean isEmployee(final String Email) {
        return employeeEmails.contains(Email);
    }

    public boolean isAssociate(final String Email) {
        return (isOwner(Email) || isManager(Email) || isEmployee(Email));
    }
}
