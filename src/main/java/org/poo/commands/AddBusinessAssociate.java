package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

/**
 * The type Add business associate.
 */
public final class AddBusinessAssociate implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Add Business Associate.
     *
     * @param bank  the bank
     * @param input the input
     */
    public AddBusinessAssociate(final Bank bank, final CommandInput input) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        bank.addBusinessAssociate(input);
    }
}
