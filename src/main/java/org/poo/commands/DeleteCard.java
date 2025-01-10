package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

/**
 * The type Delete card.
 */
public final class DeleteCard implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Delete card.
     *
     * @param bank  the bank
     * @param input the input
     */
    public DeleteCard(final Bank bank, final CommandInput input) {
        this.bank = bank;
        this.input = input;
    }

    @Override
    public void execute() {
        bank.deleteCard(input);
    }
}
