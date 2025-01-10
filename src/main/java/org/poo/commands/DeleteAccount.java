package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;
import org.poo.converter.ConverterJson;

/**
 * The type Delete account.
 */
public final class DeleteAccount implements Command {
    private CommandInput input;
    private Bank bank;
    private ConverterJson out;

    /**
     * Instantiates a new Delete account.
     *
     * @param bank  the bank
     * @param input the input
     * @param out   the out
     */
    public DeleteAccount(final Bank bank, final CommandInput input, final ConverterJson out) {
        this.input = input;
        this.bank = bank;
        this.out = out;
    }

    @Override
    public void execute() {
        if (bank.deleteAccount(input) == 1) {
            out.deleteAccount(input.getTimestamp());
        } else {
            out.deleteAccountFail(input.getTimestamp());
        }
    }
}
