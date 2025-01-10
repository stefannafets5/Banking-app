package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.converter.ConverterJson;
import org.poo.fileio.CommandInput;

/**
 * The type Add interest.
 */
public final class AddInterest implements Command {
    private Bank bank;
    private CommandInput input;
    private ConverterJson out;

    /**
     * Instantiates a new Add interest.
     *
     * @param bank  the bank
     * @param input the input
     * @param out   the out
     */
    public AddInterest(final Bank bank, final CommandInput input, final ConverterJson out) {
        this.input = input;
        this.bank = bank;
        this.out = out;
    }

    @Override
    public void execute() {
        if (bank.addInterest(input) == 0) {
            out.printError(input.getTimestamp(), "addInterest", "This is not a savings account");
        }
    }
}
