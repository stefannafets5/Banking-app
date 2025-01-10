package org.poo.bank;

/**
 * The type Exchange rate.
 */
public final class ExchangeRate {
    private final String from;
    private final String to;
    private final double rate;

    /**
     * Instantiates a new Exchange rate.
     *
     * @param from the from
     * @param to   the to
     * @param rate the rate
     */
    public ExchangeRate(final String from, final String to, final double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * Gets rate.
     *
     * @return the rate
     */
    public double getRate() {
        return rate;
    }
}
