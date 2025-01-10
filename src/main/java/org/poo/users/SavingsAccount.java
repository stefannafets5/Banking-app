package org.poo.users;

/**
 * The type Savings account.
 */
public class SavingsAccount extends Account {
    /**
     * The Interest rate.
     */
    private double interestRate;

    /**
     * Instantiates a new Savings account.
     *
     * @param currency     the currency
     * @param type         the type
     * @param interestRate the interest rate
     */
    public SavingsAccount(final String currency, final String type, final double interestRate) {
        super(currency, type);
        this.interestRate = interestRate;
    }

    /**
     * Gets interest rate.
     *
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Sets interest rate.
     *
     * @param interestRate the interest rate
     */
    public void setInterestRate(final double interestRate) {
        this.interestRate = interestRate;
    }
}
