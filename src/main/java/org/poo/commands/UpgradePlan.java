package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.converter.ConverterJson;
import org.poo.fileio.CommandInput;

/**
 * The type Upgrade plan.
 */
public final class UpgradePlan implements Command {
    private Bank bank;
    private CommandInput input;
    private ConverterJson out;

    /**
     * Instantiates a new Upgrade Plan.
     *
     * @param bank  the bank
     * @param input the input
     * @param out   the out
     */
    public UpgradePlan(final Bank bank, final CommandInput input, final ConverterJson out) {
        this.input = input;
        this.bank = bank;
        this.out = out;
    }

    @Override
    public void execute() {
        bank.upgradePlan(input, out);
    }
}
