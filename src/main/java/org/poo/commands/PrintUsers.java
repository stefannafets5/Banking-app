package org.poo.commands;

import org.poo.converter.ConverterJson;
import org.poo.bank.Bank;

/**
 * The type Print users.
 */
public final class PrintUsers implements Command {
    private Bank bank;
    private ConverterJson out;
    private int timestamp;

    /**
     * Instantiates a new Print users.
     *
     * @param bank      the bank
     * @param out       the out
     * @param timestamp the timestamp
     */
    public PrintUsers(final Bank bank, final ConverterJson out, final int timestamp) {
        this.bank = bank;
        this.out = out;
        this.timestamp = timestamp;
    }

    @Override
    public void execute() {
        out.printUsers(bank, timestamp);
    }
}
