package org.poo.users;

import java.util.ArrayList;

import org.poo.commerciants.CashbackStrategy;
import org.poo.commerciants.Commerciant;
import org.poo.commerciants.SpendingThresholdStrategy;
import org.poo.commerciants.TransactionsStrategy;
import org.poo.fileio.CommandInput;
import org.poo.users.transactions.Transaction;
import org.poo.utils.Utils;

/**
 * The type Account.
 */
public class Account {
    private String currency;
    private String type;
    private String iban;
    private String alias;
    private double balance;
    private double minBalance;
    private double moneySpent = 0;
    private int nrOfTransactions = 0;
    private int discountFood = 0;
    private int discountClothes = 0;
    private int discountTech = 0;
    private ArrayList<Card> cards = new ArrayList<>();

    /**
     * Instantiates a new Account.
     *
     * @param currency the currency
     * @param type     the type
     */
    public Account(final String currency, final String type) {
        this.currency = currency;
        this.type = type;
        this.balance = 0;
        this.iban = Utils.generateIBAN();
    }

    /**
     * Gets cards.
     *
     * @return the cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Sets cards.
     *
     * @param cards the cards
     */
    public void setCards(final ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(final double balance) {
        this.balance = balance;
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
     * Gets currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets currency.
     *
     * @param currency the currency
     */
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    /**
     * Gets alias.
     *
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets alias.
     *
     * @param alias the alias
     */
    public void setAlias(final String alias) {
        this.alias = alias;
    }

    /**
     * Gets min balance.
     *
     * @return the min balance
     */
    public double getMinBalance() {
        return minBalance;
    }

    /**
     * Sets min balance.
     *
     * @param minBalance the min balance
     */
    public void setMinBalance(final double minBalance) {
        this.minBalance = minBalance;
    }

    /**
     * Gets money spent.
     *
     * @return the money spent
     */
    public double getMoneySpent() {
        return moneySpent;
    }

    /**
     * Sets money spent.
     *
     * @param moneySpent the money spent
     */
    public void setMoneySpent(final double moneySpent) {
        this.moneySpent = moneySpent;
    }

    /**
     * Gets nr of transactions.
     *
     * @return the nr of transactions
     */
    public int getNrOfTransactions() {
        return nrOfTransactions;
    }

    /**
     * Sets nr of transactions.
     *
     * @param nrOfTransactions the nr of transactions
     */
    public void setNrOfTransactions(final int nrOfTransactions) {
        this.nrOfTransactions = nrOfTransactions;
    }

    /**
     * Gets discount food.
     *
     * @return the discount food
     */
    public int getDiscountFood() {
        return discountFood;
    }

    /**
     * Sets discount food.
     *
     * @param discountFood the discount food
     */
    public void setDiscountFood(final int discountFood) {
        this.discountFood = discountFood;
    }

    /**
     * Gets discount clothes.
     *
     * @return the discount clothes
     */
    public int getDiscountClothes() {
        return discountClothes;
    }

    /**
     * Sets discount clothes.
     *
     * @param discountClothes the discount clothes
     */
    public void setDiscountClothes(final int discountClothes) {
        this.discountClothes = discountClothes;
    }

    /**
     * Gets discount tech.
     *
     * @return the discount tech
     */
    public int getDiscountTech() {
        return discountTech;
    }

    /**
     * Sets discount tech.
     *
     * @param discountTech the discount tech
     */
    public void setDiscountTech(final int discountTech) {
        this.discountTech = discountTech;
    }

    /**
     * Add commerciant transaction.
     */
    public void addCommerciantTransaction() {
        this.nrOfTransactions++;
    }

    /**
     * Add money.
     *
     * @param amount the amount
     */
    public void addMoney(final double amount) {
        this.balance += amount;
    }

    /**
     * Subtract money.
     *
     * @param amount the amount
     */
    public void subtractMoney(final double amount) {
        this.balance -= amount;
    }

    /**
     * Add money spent.
     *
     * @param amount the amount
     */
    public void addMoneySpent(final double amount) {
        this.moneySpent += amount;
    }

    /**
     * Subtract money spent.
     *
     * @param amount the amount
     */
    public void subtractMoneySpent(final double amount) {
        this.moneySpent -= amount;
    }

    /**
     * Add card.
     *
     * @param input            the input
     * @param cardType         the type
     * @param emailCardCreator the email card creator
     * @param transactions     the transactions
     * @param description      the description
     */
    public void addCard(final CommandInput input, final String cardType,
                        final String emailCardCreator,
                         final ArrayList<Transaction> transactions, final String description) {
        Card card = new Card(cardType, emailCardCreator);

        card.addCardCreationTransaction(input.getTimestamp(),
                input.getEmail(), getIban(), transactions, description);
        getCards().add(card);

    }

    /**
     * Remove card.
     *
     * @param index        the index
     * @param input        the input
     * @param email        the email
     * @param transactions the transactions
     * @param description  the description
     */
    public void removeCard(final int index, final CommandInput input, final String email,
                            final ArrayList<Transaction> transactions, final String description) {
        getCards().get(index).addCardCreationTransaction(input.getTimestamp(),
                email, getIban(), transactions, description);
        getCards().remove(index);
    }

    /**
     * Check for cashback double.
     *
     * @param name         the name
     * @param commerciants the commerciants
     * @param amount       the amount
     * @param plan         the plan
     * @param account      the account
     * @return the double
     */
    public double checkForCashback(final String name, final ArrayList<Commerciant> commerciants,
                                    final double amount, final String plan, final Account account) {
        String strategy = "";
        String strategyType = "";

        for (Commerciant commerciant : commerciants) {
            if (commerciant.getName().equals(name)) {
                strategy = commerciant.getCashbackStrategy();
                strategyType = commerciant.getType();
            }
        }
        CashbackStrategy cashbackStrategy;
        if (strategy.equals("nrOfTransactions")) {
            cashbackStrategy = new TransactionsStrategy();
        } else {
            cashbackStrategy = new SpendingThresholdStrategy();
        }
        return cashbackStrategy.calculateCashback(amount, strategyType,
                plan, account, getNrOfTransactions());
    }
}
