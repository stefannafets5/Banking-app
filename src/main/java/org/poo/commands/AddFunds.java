package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

/**
 * The type Add funds.
 */
public final class AddFunds implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Add funds.
     *
     * @param bank  the bank
     * @param input the input
     */
    public AddFunds(final Bank bank, final CommandInput input) {
        this.bank = bank;
        this.input = input;
    }

    @Override
    public void execute() {
        bank.addFounds(input);
    }
}
