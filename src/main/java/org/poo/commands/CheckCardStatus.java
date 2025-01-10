package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.converter.ConverterJson;
import org.poo.fileio.CommandInput;

/**
 * The type Check card status.
 */
public final class CheckCardStatus implements Command {
    private Bank bank;
    private CommandInput input;
    private ConverterJson out;

    /**
     * Instantiates a new Check card status.
     *
     * @param bank  the bank
     * @param input the input
     * @param out   the out
     */
    public CheckCardStatus(final Bank bank, final CommandInput input, final ConverterJson out) {
        this.input = input;
        this.bank = bank;
        this.out = out;
    }

    @Override
    public void execute() {
        if (bank.checkCardStatus(input) == 0) {
            out.printError(input.getTimestamp(), "checkCardStatus", "Card not found");
        }
    }
}
