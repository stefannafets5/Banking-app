package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

/**
 * The type Set minimum balance.
 */
public final class SetMinimumBalance implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Set minimum balance.
     *
     * @param bank  the bank
     * @param input the input
     */
    public SetMinimumBalance(final Bank bank, final CommandInput input) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        bank.setMinimumBalance(input);
    }
}
