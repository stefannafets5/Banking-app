package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

/**
 * The type Send money.
 */
public final class SendMoney implements Command {
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Send money.
     *
     * @param bank  the bank
     * @param input the input
     */
    public SendMoney(final Bank bank, final CommandInput input) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        bank.sendMoney(input);
    }
}
