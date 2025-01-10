package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.converter.ConverterJson;
import org.poo.fileio.CommandInput;

/**
 * The type Change interest rate.
 */
public final class ChangeInterestRate implements Command {
    private Bank bank;
    private CommandInput input;
    private ConverterJson out;

    /**
     * Instantiates a new Change interest rate.
     *
     * @param bank  the bank
     * @param input the input
     * @param out   the out
     */
    public ChangeInterestRate(final Bank bank, final CommandInput input, final ConverterJson out) {
        this.input = input;
        this.bank = bank;
        this.out = out;
    }

    @Override
    public void execute() {
        if (bank.changeInterestRate(input) == 0) {
            out.printError(input.getTimestamp(), "changeInterestRate",
                    "This is not a savings account");
        }
    }
}
