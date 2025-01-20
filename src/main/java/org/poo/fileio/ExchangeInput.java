package org.poo.fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Exchange input.
 */
@Data
@NoArgsConstructor
public final class ExchangeInput {
    private String from;
    private String to;
    private double rate;
    private int timestamp;
}
