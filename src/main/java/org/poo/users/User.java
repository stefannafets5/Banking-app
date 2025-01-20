package org.poo.users;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Period;

import org.poo.fileio.CommandInput;
import org.poo.users.transactions.*;
import org.poo.users.transactions.Error;
import org.poo.utils.Utils;

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
    private int ron300Payment = 0;
    private ArrayList<Account> accounts = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    /**
     * Instantiates a new User.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @param birthDate the birth date
     * @param ocupation the ocupation
     */
    public User(final String firstName, final String lastName, final String email,
                final String birthDate, final String ocupation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.ocupation = ocupation;
        if (ocupation.equals("student")) {
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

    /**
     * Gets birth date.
     *
     * @return the birth date
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Sets birth date.
     *
     * @param birthDate the birth date
     */
    public void setBirthDate(final String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets ocupation.
     *
     * @return the ocupation
     */
    public String getOcupation() {
        return ocupation;
    }

    /**
     * Sets ocupation.
     *
     * @param ocupation the ocupation
     */
    public void setOcupation(final String ocupation) {
        this.ocupation = ocupation;
    }

    /**
     * Gets plan.
     *
     * @return the plan
     */
    public String getPlan() {
        return plan;
    }

    /**
     * Sets plan.
     *
     * @param plan the plan
     */
    public void setPlan(final String plan) {
        this.plan = plan;
    }

    /**
     * Gets ron 300 payment.
     *
     * @return the ron 300 payment
     */
    public int getRon300Payment() {
        return ron300Payment;
    }

    /**
     * Sets ron 300 payment.
     *
     * @param ron300Payment the ron 300 payment
     */
    public void setRon300Payment(final int ron300Payment) {
        this.ron300Payment = ron300Payment;
    }

    /**
     * Add 300 ron payment.
     */
    public void add300RonPayment() {
        setRon300Payment(getRon300Payment() + 1);
    }

    /**
     * Add account.
     *
     * @param input the input
     * @param limit the limit
     */
    public void addAccount(final CommandInput input, final double limit) {
        if (input.getAccountType().equals("savings")) {
            SavingsAccount account = new SavingsAccount(input.getCurrency(),
                    input.getAccountType(), input.getInterestRate());
            getAccounts().add(account);
            addAccountCreationTransaction(input.getTimestamp());
        } else if (input.getAccountType().equals("classic")) {
            Account account = new Account(input.getCurrency(), input.getAccountType());
            getAccounts().add(account);
            addAccountCreationTransaction(input.getTimestamp());
        } else { // business account
            BusinessAccount account = new BusinessAccount(input.getCurrency(),
                    input.getAccountType(), input.getEmail());
            account.setDepositLimit(limit);
            account.setSpendingLimit(limit);
            getAccounts().add(account);
        }
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
     * Verify age 21 int.
     *
     * @param birth the birth date
     * @return the int
     */
    public int verifyAge21(final String birth) {
        if (Period.between(LocalDate.parse(birth), LocalDate.now()).getYears() >= Utils.ADULT_AGE) {
            return 1;
        }
        return 0;
    }

    /**
     * Check commision double.
     *
     * @param amount   the amount
     * @param ronSpent the ron spent
     * @return the double
     */
    public double checkCommission(final double amount, final double ronSpent) {
        if (getPlan().equals("standard")) {
            return amount * Utils.STANDARD_COMMISION; // 0.2%
        } else if (getPlan().equals("silver") && ronSpent >= Utils.LIMIT) {
            return amount * Utils.SILVER_COMMISION; // 0.1%
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
     * @param input      the input
     * @param poor       the poor
     * @param type       the type
     * @param fromIban   the from iban
     * @param amountList the amount list
     * @param amount     the amount
     * @param isRejected the is rejected
     */
    public void addSplitPaymentFailedTransaction(final CommandInput input, final String poor,
                                                 final String type, final String fromIban,
                                                 final ArrayList<Double> amountList,
                                                 final double amount, final int isRejected) {
        getTransactions().add(new SplitPaymentFailed(input, poor, fromIban,
                amountList, type, amount, isRejected));
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
     * @param timestamp   the timestamp
     * @param amountList  the amount list
     * @param splitAmount the split amount
     * @param currency    the currency
     * @param ibanList    the iban list
     * @param type        the type
     */
    public void addSplitCardPaymentTransaction(final int timestamp,
                                               final ArrayList<Double> amountList,
                                               final double splitAmount, final String currency,
                                               final ArrayList<String> ibanList,
                                               final String type) {
        getTransactions().add(new SplitCardPayment(timestamp, amountList,
                              splitAmount, currency, ibanList, type));
    }

    /**
     * Add upgrade plan transaction.
     *
     * @param timestamp     the timestamp
     * @param iban          the iban
     * @param upgradePlan   the plan
     */
    public void addUpgradePlanTransaction(final int timestamp, final String iban,
                                          final String upgradePlan) {
        getTransactions().add(new UpgradePlan(timestamp, iban, upgradePlan));
    }

    /**
     * Add cash withdrawal transaction.
     *
     * @param timestamp the timestamp
     * @param amount    the amount
     */
    public void addCashWithdrawalTransaction(final int timestamp, final double amount) {
        getTransactions().add(new WithdrawCash(timestamp, amount));
    }

    /**
     * Add interest transaction.
     *
     * @param timestamp the timestamp
     * @param amount    the amount
     * @param currency  the currency
     */
    public void addInterestTransaction(final int timestamp, final double amount,
                                       final String currency) {
        getTransactions().add(new AddInterest(timestamp, amount, currency));
    }

    /**
     * Add savings withdrawal transaction.
     *
     * @param timestamp   the timestamp
     * @param description the description
     * @param amount      the amount
     * @param classicIban the classic iban
     * @param savingsIban the savings iban
     */
    public void addSavingsWithdrawalTransaction(final int timestamp, final String description,
                                                final double amount, final String classicIban,
                                                final String savingsIban) {
        getTransactions().add(new SavingsWithdrawal(timestamp, description,
                amount, classicIban, savingsIban));
    }
}
