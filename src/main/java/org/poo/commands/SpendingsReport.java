package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.converter.ConverterJson;
import org.poo.fileio.CommandInput;

/**
 * The type Spendings report.
 */
public final class SpendingsReport implements Command {
    private Bank bank;
    private CommandInput input;
    private ConverterJson out;

    /**
     * Instantiates a new Spendings report.
     *
     * @param bank  the bank
     * @param input the input
     * @param out   the out
     */
    public SpendingsReport(final Bank bank, final CommandInput input, final ConverterJson out) {
        this.input = input;
        this.bank = bank;
        this.out = out;
    }

    @Override
    public void execute() {
        if (bank.createReport(input, out, "spendings") == 0) {
            out.printError(input.getTimestamp(), "spendingsReport", "Account not found");
        }
    }
}
