package org.poo.commands;

import org.poo.bank.Bank;
import org.poo.fileio.CommandInput;

public class UpgradePlan implements Command{
    private Bank bank;
    private CommandInput input;

    /**
     * Instantiates a new Upgrade Plan.
     *
     * @param bank  the bank
     * @param input the input
     */
    public UpgradePlan(final Bank bank, final CommandInput input) {
        this.input = input;
        this.bank = bank;
    }

    @Override
    public void execute() {
        bank.upgradePlan(input);
    }
}
