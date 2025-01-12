package org.poo.users;

import java.util.ArrayList;

import org.poo.commerciants.Commerciant;
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

    public int getNrOfTransactions() {
        return nrOfTransactions;
    }

    public void setNrOfTransactions(int nrOfTransactions) {
        this.nrOfTransactions = nrOfTransactions;
    }

    public int getDiscountFood() {
        return discountFood;
    }

    public void setDiscountFood(int discountFood) {
        this.discountFood = discountFood;
    }

    public int getDiscountClothes() {
        return discountClothes;
    }

    public void setDiscountClothes(int discountClothes) {
        this.discountClothes = discountClothes;
    }

    public int getDiscountTech() {
        return discountTech;
    }

    public void setDiscountTech(int discountTech) {
        this.discountTech = discountTech;
    }

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
     * Add card.
     *
     * @param input        the input
     * @param cardType     the type
     * @param transactions the transactions
     * @param description  the description
     */
    public void addCard(final CommandInput input, final String cardType,
                         final ArrayList<Transaction> transactions, final String description) {
        Card card = new Card(cardType);

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

    public double checkForCashback (final String name, final ArrayList<Commerciant> commerciants,
                                    final double amount, final String plan, final double ronSpent) {
        String strategy = "";
        String type = "";

        for (Commerciant commerciant : commerciants) {
            if (commerciant.getName().equals(name)) {
                strategy = commerciant.getCashbackStrategy();
                type = commerciant.getType();
            }
        }
        if (strategy.equals("nrOfTransactions")) {
            if (getNrOfTransactions() > 2 && getDiscountFood() == 0 && type.equals("Food")){
                setDiscountFood(1);
                return amount * 0.98;
            }
            if (getNrOfTransactions() > 5 && getDiscountClothes() == 0 && type.equals("Clothes")) {
                setDiscountClothes(1);
                return amount * 0.95;
            }
            if (getNrOfTransactions() > 10 && getDiscountTech() == 0 && type.equals("Tech")) {
                setDiscountTech(1);
                return amount * 0.9;
            }
        } else { // spendingThreshold
            if (ronSpent >= 500) {
                //TODO S-ar putea sa trebuieasca sa resetez money spent
                if (plan.equals("standard") || plan.equals("student")) {
                    return amount * 0.9975;
                } else if (plan.equals("silver")) {
                    return amount * 0.995;
                } else { // gold
                    return amount * 0.993;
                }
            } else if (ronSpent >= 300) {
                if (plan.equals("standard") || plan.equals("student")) {
                    return amount * 0.998;
                } else if (plan.equals("silver")) {
                    return amount * 0.996;
                } else { // gold
                    return amount * 0.9945;
                }
            } else if (ronSpent >= 100) {
                if (plan.equals("standard") || plan.equals("student")) {
                    return amount * 0.999;
                } else if (plan.equals("silver")) {
                    return amount * 0.997;
                } else { // gold
                    return amount * 0.995;
                }
            }
        }
        return amount;
    }
}
