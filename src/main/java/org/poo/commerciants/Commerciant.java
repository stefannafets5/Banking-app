package org.poo.commerciants;

import java.util.ArrayList;

public class Commerciant {
    private String name;
    private int id;
    private String iban;
    private String type;
    private String cashbackStrategy;
    private ArrayList<String> managerList;
    private ArrayList<String> employeeList;
    private double businessMoneySpent = 0;

    public Commerciant(String name, int id, String iban, String type, String cashbackStrategy) {
        this.name = name;
        this.id = id;
        this.iban = iban;
        this.type = type;
        this.cashbackStrategy = cashbackStrategy;
        this.managerList = new ArrayList<>();
        this.employeeList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCashbackStrategy() {
        return cashbackStrategy;
    }

    public void setCashbackStrategy(String cashbackStrategy) {
        this.cashbackStrategy = cashbackStrategy;
    }

    public ArrayList<String> getManagerList() {
        return managerList;
    }

    public void setManagerList(ArrayList<String> managerList) {
        this.managerList = managerList;
    }

    public ArrayList<String> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(ArrayList<String> employeeList) {
        this.employeeList = employeeList;
    }

    public double getBusinessMoneySpent() {
        return businessMoneySpent;
    }

    public void setBusinessMoneySpent(double businessMoneySpent) {
        this.businessMoneySpent = businessMoneySpent;
    }
}
