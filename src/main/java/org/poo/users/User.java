package org.poo.users;

import java.util.ArrayList;

import org.poo.fileio.CommandInput;
import org.poo.users.transactions.*;
import org.poo.users.transactions.Error;

/**
 * The type User.
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    /**
     * Instantiates a new User.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     */
    public User(final String firstName, final String lastName, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets accounts.
     *
     * @return the accounts
     */
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * Sets accounts.
     *
     * @param account the account
     */
    public void setAccounts(final ArrayList<Account> account) {
        this.accounts = account;
    }

    /**
     * Gets transactions.
     *
     * @return the transactions
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets transactions.
     *
     * @param transactions the transactions
     */
    public void setTransactions(final ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Add account.
     *
     * @param input the input
     */
    public void addAccount(final CommandInput input) {
        if (input.getAccountType().equals("savings")) {
            SavingsAccount account = new SavingsAccount(input.getCurrency(),
                    input.getAccountType(), input.getInterestRate());
            getAccounts().add(account);
        } else {
            Account account = new Account(input.getCurrency(), input.getAccountType());
            getAccounts().add(account);
        }
        addAccountCreationTransaction(input.getTimestamp());
    }

    /**
     * Delete account.
     *
     * @param index the index
     */
    public void deleteAccount(final int index) {
        getAccounts().remove(index);
    }

    /**
     * Add error transaction.
     *
     * @param timestamp   the timestamp
     * @param description the description
     */
    public void addErrorTransaction(final int timestamp, final String description) {
        getTransactions().add(new Error(timestamp, description));
    }

    /**
     * Add account creation transaction.
     *
     * @param timestamp the timestamp
     */
    public void addAccountCreationTransaction(final int timestamp) {
        getTransactions().add(new CreateAccount(timestamp));
    }

    /**
     * Add card payment transaction.
     *
     * @param timestamp   the timestamp
     * @param amount      the amount
     * @param commerciant the commerciant
     * @param iban        the iban
     */
    public void addCardPaymentTransaction(final int timestamp, final double amount,
                                           final String commerciant, final String iban) {
        getTransactions().add(new CardPayment(timestamp, amount, commerciant, iban));
    }

    /**
     * Add payment failed transaction.
     *
     * @param timestamp the timestamp
     */
    public void addPaymentFailedTransaction(final int timestamp) {
        getTransactions().add(new PaymentFailed(timestamp));
    }

    /**
     * Add split payment failed transaction.
     *
     * @param input    the input
     * @param poor     the poor
     * @param fromIban the from iban
     */
    public void addSplitPaymentFailedTransaction(final CommandInput input, final String poor,
                                                 final String fromIban) {
        getTransactions().add(new SplitPaymentFailed(input, poor, fromIban));
    }

    /**
     * Add money transfer transaction.
     *
     * @param input    the input
     * @param type     the type
     * @param currency the currency
     * @param amount   the amount
     */
    public void addMoneyTransferTransaction(final CommandInput input, final String type,
                                             final String currency, final double amount) {
        getTransactions().add(new MoneyTransfer(input.getTimestamp(), input.getDescription(),
                              input.getAccount(), input.getReceiver(), amount, type, currency));
    }

    /**
     * Add changed interest transaction.
     *
     * @param timestamp   the timestamp
     * @param description the description
     */
    public void addChangedInterestTransaction(final int timestamp, final String description) {
        getTransactions().add(new Error(timestamp, description));
    }

    /**
     * Add split card payment transaction.
     *
     * @param timestamp    the timestamp
     * @param amount       the amount
     * @param splitAmount the split amount
     * @param currency     the currency
     * @param ibanList     the iban list
     */
    public void addSplitCardPaymentTransaction(final int timestamp, final double amount,
                                                final double splitAmount, final String currency,
                                                final ArrayList<String> ibanList) {
        getTransactions().add(new SplitCardPayment(timestamp, amount,
                              splitAmount, currency, ibanList));
    }
}
