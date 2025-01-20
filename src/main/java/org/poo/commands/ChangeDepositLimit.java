package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.converter.ConverterJson;
import org.poo.fileio.CommandInput;

/**
 * The type Change deposit limit.
 */
public final class ChangeDepositLimit implements Command {
    private Bank bank;
    private CommandInput input;
    private ConverterJson out;

    /**
     * Instantiates a new Change Deposit Limit.
     *
     * @param bank  the bank
     * @param input the input
     * @param out   the out
     */
    public ChangeDepositLimit(final Bank bank, final CommandInput input, final ConverterJson out) {
        this.input = input;
        this.bank = bank;
        this.out = out;
    }

    @Override
    public void execute() {
        bank.changeLimit(input, out);
    }
}
