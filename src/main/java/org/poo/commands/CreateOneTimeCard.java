package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

/**
 * The type Create one time card.
 */
public final class CreateOneTimeCard implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Create one time card.
     *
     * @param bank  the bank
     * @param input the input
     */
    public CreateOneTimeCard(final Bank bank, final CommandInput input) {
        this.bank = bank;
        this.input = input;
    }

    @Override
    public void execute() {
        bank.createCard(input, "oneTime");
    }
}
