package org.poo.fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Commerciant input.
 */
@Data
@NoArgsConstructor
public final class CommerciantInput {
    private String commerciant;
    private int id;
    private String account;
    private String type;
    private String cashbackStrategy;
}
