package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.converter.ConverterJson;
import org.poo.fileio.CommandInput;

/**
 * The type Pay online.
 */
public final class PayOnline implements Command {
    private Bank bank;
    private CommandInput input;
    private ConverterJson out;

    /**
     * Instantiates a new Pay online.
     *
     * @param bank  the bank
     * @param input the input
     * @param out   the out
     */
    public PayOnline(final Bank bank, final CommandInput input, final ConverterJson out) {
        this.input = input;
        this.bank = bank;
        this.out = out;
    }

    @Override
    public void execute() {
        if (bank.payOnline(input) == 2) {
            out.printError(input.getTimestamp(), "payOnline", "Card not found");
        }
    }
}
