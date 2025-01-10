package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

/**
 * The type Split payment.
 */
public final class SplitPayment implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Split payment.
     *
     * @param bank  the bank
     * @param input the input
     */
    public SplitPayment(final Bank bank, final CommandInput input) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        bank.splitPayment(input);
    }
}
