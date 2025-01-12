package org.poo.users;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Period;

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
    private String birthDate;
    private String ocupation;
    private String plan;
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    /**
     * Instantiates a new User.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     */
    public User(final String firstName, final String lastName, final String email,
                final String birthDate, final String ocupation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.ocupation = ocupation;
        if (ocupation.equals("student")){
            this.plan = "student";
        } else {
            this.plan = "standard";
        }
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getOcupation() {
        return ocupation;
    }

    public void setOcupation(String ocupation) {
        this.ocupation = ocupation;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
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

    public int verifyAge21(final String birthDate) {
        if (Period.between(LocalDate.parse(birthDate), LocalDate.now()).getYears() >= 21) {
            return 1;
        }
        return 0;
    }

    public double checkCommision (final double amount, final double ronSpent) {
        String plan = getPlan();

        if (plan.equals("standard")) {
            return amount * 0.002; // 0.2%
        } else if (plan.equals("silver") && ronSpent >= 500) {
            return amount * 0.001; // 0.1%
        }
        return 0;
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
    public void addSplitPaymentFailedTransaction(final CommandInput input, final String poor, final String type,
                                                 final String fromIban, final ArrayList<Double> amountList,
                                                 final double amount) {
        getTransactions().add(new SplitPaymentFailed(input, poor, fromIban, amountList, type, amount));
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
     * @param amountList   the amount list
     * @param splitAmount  the split amount
     * @param currency     the currency
     * @param ibanList     the iban list
     */
    public void addSplitCardPaymentTransaction(final int timestamp, final ArrayList<Double> amountList,
                                                final double splitAmount, final String currency,
                                                final ArrayList<String> ibanList, final String type) {
        getTransactions().add(new SplitCardPayment(timestamp, amountList,
                              splitAmount, currency, ibanList, type));
    }

    public void addUpgradePlanTransaction(final int timestamp, final String iban, final String plan) {
        getTransactions().add(new UpgradePlan(timestamp, iban, plan));
    }

    public void addCashWithdrawalTransaction(final int timestamp, final double amount) {
        getTransactions().add(new WithdrawCash(timestamp, amount));
    }

    public void addInterestTransaction(final int timestamp, final double amount, final String currency) {
        getTransactions().add(new AddInterest(timestamp, amount, currency));
    }
}
