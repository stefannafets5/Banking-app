package org.poo.commerciants;

import org.poo.users.Account;
import org.poo.utils.Utils;

/**
 * The type Transactions strategy.
 */
public final class TransactionsStrategy implements CashbackStrategy {

    @Override
    public double calculateCashback(final double amount, final String type,
                                    final String plan, final Account account,
                                    final int nrOfTransactions) {
        if (nrOfTransactions > 2 && type.equals("Food") && account.getDiscountFood() == 0) {
            account.setDiscountFood(1);
            return amount * Utils.DISCOUNT_2;
        }
        if (nrOfTransactions > Utils.NUM_FOR_UPGRADE
                && type.equals("Clothes") && account.getDiscountClothes() == 0) {
            account.setDiscountClothes(1);
            return amount * Utils.DISCOUNT_5;
        }
        if (nrOfTransactions > Utils.NUM_TR_FOR_DISCOUNT
                && type.equals("Tech") && account.getDiscountTech() == 0) {
            account.setDiscountTech(1);
            return amount * Utils.DISCOUNT_10;
        }
        return amount;
    }
}
