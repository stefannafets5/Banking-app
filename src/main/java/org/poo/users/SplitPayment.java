package org.poo.users;

import org.poo.fileio.CommandInput;
import org.poo.converter.CurrencyConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Split payment.
 */
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

    /**
     * Instantiates a new Split payment.
     *
     * @param input the input
     */
    public SplitPayment(final CommandInput input) {
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

    /**
     * Gets is completed.
     *
     * @return the is completed
     */
    public boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * Gets is rejected.
     *
     * @return the is rejected
     */
    public boolean getIsRejected() {
        return isRejected;
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
     * Gets input.
     *
     * @return the input
     */
    public CommandInput getInput() {
        return input;
    }

    /**
     * Gets total amount.
     *
     * @return the total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    private String getFrom() {
        return from;
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
     * Gets amount list.
     *
     * @return the amount list
     */
    public ArrayList<Double> getAmountList() {
        return amountList;
    }

    /**
     * Accept payment.
     *
     * @param email     the email
     * @param users     the users
     * @param converter the converter
     */
    public void acceptPayment(final String email, final ArrayList<User> users,
                              final CurrencyConverter converter) {
        for (int i = 0; i < getIbanList().size(); i++) {
            String currentIban = getIbanList().get(i);
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    for (Account currentAccount : user.getAccounts()) {
                        if (currentAccount.getIban().equals(currentIban)
                                && status.containsKey(currentIban)) {
                                status.put(currentIban, true);
                                checkCompletion(users, converter);
                        }
                    }
                }
            }
        }
    }

    /**
     * Reject payment.
     *
     * @param email the email
     * @param users the users
     */
    public void rejectPayment(final String email, final ArrayList<User> users) {
        for (int i = 0; i < getIbanList().size(); i++) {
            String currentIban = getIbanList().get(i);
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    for (Account currentAccount : user.getAccounts()) {
                        if (currentAccount.getIban().equals(currentIban)
                                && status.containsKey(currentIban)) {
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
                                    getType(), currentIban,
                                    getAmountList(), getTotalAmount(), 1);
                        }
                    }
                }
            }
        }
    }

    private void checkCompletion(final ArrayList<User> users, final CurrencyConverter converter) {
        if (status.values().stream().allMatch(Boolean.TRUE::equals)) {
            // everyone accepted
            this.isCompleted = true;
            checkMoney(users, converter);
            if (getPoor().equals("nobody")) {
                payMoney(users, converter);
            }
        }
    }

    private void checkMoney(final ArrayList<User> users, final CurrencyConverter converter) {
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
                    if (currentAccount.getIban().equals(currentIban)
                            && !getPoor().equals("nobody")) {
                        user.addSplitPaymentFailedTransaction(getInput(), getPoor(),
                                getType(), currentIban, getAmountList(), getTotalAmount(), 0);
                    }
                }
            }
        }
    }

    private void payMoney(final ArrayList<User> users, final CurrencyConverter converter) {
        // take the money out of the accounts
        for (int i = 0; i < getIbanList().size(); i++) {
            String currentIban = getIbanList().get(i);
            for (User user : users) {
                for (Account currentAccount : user.getAccounts()) {
                    if (currentAccount.getIban().equals(currentIban)) {
                        String to = currentAccount.getCurrency();
                        double amountToBePayed =
                                converter.convert(getAmountList().get(i), getFrom(), to);
                        currentAccount.subtractMoney(amountToBePayed);
                        user.addSplitCardPaymentTransaction(getTimestamp(), getAmountList(),
                                getTotalAmount(), getFrom(), getIbanList(), getType());
                    }
                }
            }
        }
    }

    /**
     * Gets status for email.
     *
     * @param email the email
     * @param users the users
     * @return the status for email
     */
    public Boolean getStatusForEmail(final String email, final ArrayList<User> users) {
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
