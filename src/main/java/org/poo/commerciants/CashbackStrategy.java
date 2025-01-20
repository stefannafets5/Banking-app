package org.poo.commerciants;

import org.poo.users.Account;

/**
 * The interface Cashback strategy.
 */
public interface CashbackStrategy {
    /**
     * Calculate cashback double.
     *
     * @param amount           the amount
     * @param type             the type
     * @param plan             the plan
     * @param account          the account
     * @param nrOfTransactions the nr of transactions
     * @return the double
     */
    double calculateCashback(double amount, String type, String plan,
                             Account account, int nrOfTransactions);
}
