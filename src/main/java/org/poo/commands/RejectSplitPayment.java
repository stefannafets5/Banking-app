package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.converter.ConverterJson;
import org.poo.fileio.CommandInput;

/**
 * The type Reject split payment.
 */
public final class RejectSplitPayment implements Command {
    private Bank bank;
    private CommandInput input;
    private ConverterJson out;

    /**
     * Instantiates a new Reject Split Payment.
     *
     * @param bank  the bank
     * @param input the input
     * @param out   the out
     */
    public RejectSplitPayment(final Bank bank, final CommandInput input, final ConverterJson out) {
        this.input = input;
        this.bank = bank;
        this.out = out;
    }

    @Override
    public void execute() {
        bank.rejectSplitPayment(input, out);
    }
}
