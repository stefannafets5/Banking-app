package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

public class AcceptSplitPayment implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Accept Split Payment.
     *
     * @param bank  the bank
     * @param input the input
     */
    public AcceptSplitPayment(final Bank bank, final CommandInput input) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        bank.acceptSplitPayment(input);
    }
}
