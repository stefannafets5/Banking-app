package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

/**
 * The type Set alias.
 */
public final class SetAlias implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Set alias.
     *
     * @param bank  the bank
     * @param input the input
     */
    public SetAlias(final Bank bank, final CommandInput input) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        bank.setAlias(input);
    }
}
