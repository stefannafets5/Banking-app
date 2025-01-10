package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.converter.ConverterJson;
import org.poo.fileio.CommandInput;

/**
 * The type Print transactions.
 */
public final class PrintTransactions implements Command {
    private Bank bank;
    private CommandInput input;
    private ConverterJson out;

    /**
     * Instantiates a new Print transactions.
     *
     * @param bank  the bank
     * @param input the input
     * @param out   the out
     */
    public PrintTransactions(final Bank bank, final CommandInput input, final ConverterJson out) {
        this.input = input;
        this.bank = bank;
        this.out = out;
    }

    @Override
    public void execute() {
        bank.printTransactions(input, out);
    }
}
