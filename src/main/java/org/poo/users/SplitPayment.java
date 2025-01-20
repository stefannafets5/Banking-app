package org.poo.users;

import org.poo.fileio.CommandInput;
import org.poo.converter.CurrencyConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplitPayment {
    private CommandInput input;
    private ArrayList<String> ibanList;
    private ArrayList<Double> amountList;
    private Map<String, Boolean> status;
    private boolean isCompleted;
    private boolean isRejected;
    private double totalAmount;
    private String poor = "nobody";
    private String from;
    private String type;
    private int timestamp;

    public SplitPayment(CommandInput input) {
        this.type = input.getSplitPaymentType();
        this.input = input;
        this.ibanList = new ArrayList<String>(input.getAccounts());
        if (input.getSplitPaymentType().equals("custom")) {
            this.amountList = new ArrayList<Double>(input.getAmountForUsers());
        } else {
            this.amountList = new ArrayList<Double>();
            double sum = input.getAmount() / input.getAccounts().size();
            for (int i = 0; i < input.getAccounts().size(); i++) {
                this.amountList.add(sum);
            }
        }
        this.totalAmount = input.getAmount();
        this.from = input.getCurrency();
        this.timestamp = input.getTimestamp();
        this.status = new HashMap<>();
        for (String iban : ibanList) {
            status.put(iban, null);
        }
        this.isCompleted = false;
        this.isRejected = false;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public boolean getIsRejected() {
        return isRejected;
    }

    public String getType() {
        return type;
    }

    public CommandInput getInput() {
        return input;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getTimestamp() {
        return timestamp;
    }

    private String getFrom(){
        return from;
    }

    public String getPoor() {
        return poor;
    }

    public void setPoor(String poor) {
        this.poor = poor;
    }

    public ArrayList<String> getIbanList() {
        return ibanList;
    }

    public ArrayList<Double> getAmountList() {
        return amountList;
    }

    public void acceptPayment(String email, ArrayList<User> users, CurrencyConverter converter) {
        for (int i = 0; i < getIbanList().size(); i++) {
            String currentIban = getIbanList().get(i);
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    for (Account currentAccount : user.getAccounts()) {
                        if (currentAccount.getIban().equals(currentIban) && status.containsKey(currentIban)) {
                                status.put(currentIban, true);
                                checkCompletion(users, converter);
                        }
                    }
                }
            }
        }
    }

    public void rejectPayment(String email, ArrayList<User> users) {
        for (int i = 0; i < getIbanList().size(); i++) {
            String currentIban = getIbanList().get(i);
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    for (Account currentAccount : user.getAccounts()) {
                        if (currentAccount.getIban().equals(currentIban) && status.containsKey(currentIban)) {
                            status.put(currentIban, false);
                            this.isCompleted = true;
                            this.isRejected = true;
                        }
                    }
                }
            }
        }
        if (getIsRejected()) {
            for (int i = 0; i < getIbanList().size(); i++) {
                String currentIban = getIbanList().get(i);
                for (User user : users) {
                    for (Account currentAccount : user.getAccounts()) {
                        if (currentAccount.getIban().equals(currentIban)) {
                            user.addSplitPaymentFailedTransaction(getInput(), getPoor(),
                                    getType(), currentIban, getAmountList(), getTotalAmount(), 1);
                        }
                    }
                }
            }
        }
    }

    private void checkCompletion(ArrayList<User> users, CurrencyConverter converter) {
        if (status.values().stream().allMatch(Boolean.TRUE::equals)) {
            // if everyone accepted
            this.isCompleted = true;
            checkMoney(users, converter);
            if (getPoor().equals("nobody")) {
                payMoney(users, converter);
            }
        }
    }

    private void checkMoney(ArrayList<User> users, CurrencyConverter converter) {
        // check if everyone can pay
        loop:
        for (int i = 0; i < getIbanList().size(); i++) {
            String currentIban = getIbanList().get(i);
            for (User user : users) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(currentIban)) {
                        String to = currentAccount.getCurrency();
                        double amountToBePayed =
                                converter.convert(getAmountList().get(i), getFrom(), to);
                        if (currentAccount.getBalance() < amountToBePayed) {
                            setPoor(currentAccount.getIban());
                            break loop; // get out of all the for loops
                        }
                    }
                }
            }
        }

        for (int i = 0; i < getIbanList().size(); i++) {
            String currentIban = getIbanList().get(i);
            for (User user : users) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(currentIban) && !getPoor().equals("nobody")) {
                        user.addSplitPaymentFailedTransaction(getInput(), getPoor(),
                                getType(), currentIban, getAmountList(), getTotalAmount(), 0);
                    }
                }
            }
        }
    }

    private void payMoney(ArrayList<User> users, CurrencyConverter converter) {
        // take the money out of the accounts
        for (int i = 0; i < getIbanList().size(); i++) {
            String currentIban = getIbanList().get(i);
            for (User user : users) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(currentIban)) {
                        String to = currentAccount.getCurrency();
                        double amountToBePayed =
                                converter.convert(getAmountList().get(i), getFrom(), to);
                        // TODO s-ar putea sa trebuiasca sa bag comision
                        currentAccount.subtractMoney(amountToBePayed);
                        user.addSplitCardPaymentTransaction(getTimestamp(),
                                getAmountList(), getTotalAmount(), getFrom(), getIbanList(), getType());
                    }
                }
            }
        }
    }

    public Boolean getStatusForEmail(String email, ArrayList<User> users) {
        for (int i = 0; i < getIbanList().size(); i++) {
            String currentIban = getIbanList().get(i);
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    for (Account currentAccount : user.getAccounts()) {
                        if (currentAccount.getIban().equals(currentIban)) {
                            return status.get(currentIban);
                        }
                    }
                }
            }
        }
        return false;
    }
}
