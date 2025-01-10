package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

/**
 * The type Create card.
 */
public final class CreateCard implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Create card.
     *
     * @param bank  the bank
     * @param input the input
     */
    public CreateCard(final Bank bank, final CommandInput input) {
        this.bank = bank;
        this.input = input;
    }

    @Override
    public void execute() {
        bank.createCard(input, "permanent");
    }
}
