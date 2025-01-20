package org.poo.commerciants;

import org.poo.users.Account;
import org.poo.utils.Utils;

/**
 * The type Spending threshold strategy.
 */
public final class SpendingThresholdStrategy implements CashbackStrategy {

    @Override
    public double calculateCashback(final double amount, final String type,
                                    final String plan, final Account account,
                                    final int nrOfTransactions) {
        if (account.getMoneySpent() >= Utils.LIMIT) {
            account.subtractMoneySpent(Utils.LIMIT);
            if (plan.equals("standard") || plan.equals("student")) {
                return amount * Utils.STANDARD_500;
            } else if (plan.equals("silver")) {
                return amount * Utils.SILVER_500;
            } else { // gold
                return amount * Utils.GOLD_500;
            }
        } else if (account.getMoneySpent() >= Utils.RON_FOR_UPGRADE) {
            account.subtractMoneySpent(Utils.RON_FOR_UPGRADE);
            if (plan.equals("standard") || plan.equals("student")) {
                return amount * Utils.STANDARD_300;
            } else if (plan.equals("silver")) {
                return amount * Utils.SILVER_300;
            } else { // gold
                return amount * Utils.GOLD_300;
            }
        } else if (account.getMoneySpent() >= Utils.FEE) {
            account.subtractMoneySpent(Utils.FEE);
            if (plan.equals("standard") || plan.equals("student")) {
                return amount * Utils.STANDARD_100;
            } else if (plan.equals("silver")) {
                return amount * Utils.SILVER_100;
            } else { // gold
                return amount * Utils.GOLD_100;
            }
        }
        return amount;
    }
}
