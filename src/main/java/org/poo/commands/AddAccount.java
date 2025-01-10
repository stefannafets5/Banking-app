package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

/**
 * The type Add account.
 */
public final class AddAccount implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Add account.
     *
     * @param bank  the bank
     * @param input the input
     */
    public AddAccount(final Bank bank, final CommandInput input) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        bank.addAccount(input);
    }
}
