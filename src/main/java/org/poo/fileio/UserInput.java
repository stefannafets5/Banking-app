package org.poo.fileio;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User input.
 */
@Data
@NoArgsConstructor
public final class UserInput {
    private String firstName;
    private String lastName;
    private String email;
    private String birthDate;
    private String occupation;
}
