package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

public class ChangeDepositLimit implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Change Deposit Limit.
     *
     * @param bank  the bank
     * @param input the input
     */
    public ChangeDepositLimit(final Bank bank, final CommandInput input) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        bank.changeLimit(input);
    }
}
