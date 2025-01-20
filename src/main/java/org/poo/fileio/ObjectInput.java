package org.poo.fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Object input.
 */
@Data
@NoArgsConstructor
public final class ObjectInput {
    private UserInput[] users;
    private ExchangeInput[] exchangeRates;
    private CommandInput[] commands;
    private CommerciantInput[] commerciants;
}
