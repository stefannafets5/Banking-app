package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

/**
 * The type Withdraw savings.
 */
public final class WithdrawSavings implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Withdraw Savings.
     *
     * @param bank  the bank
     * @param input the input
     */
    public WithdrawSavings(final Bank bank, final CommandInput input) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        bank.withdrawSavings(input);
    }
}
