package com.valkryst.MicroISRC.exception;

import com.valkryst.MicroISRC.repository.ISRCRepository;
import lombok.NonNull;

import java.time.Year;

/**
 * Signals that all <i>Designation Code</i> values for all prefixes (see {@link ISRCRepository}) have been used for a
 * given {@link Year}, and that it is impossible to generate another ISRC value.
 */
public class ISRCExhaustionException extends RuntimeException {
    /**
     * Constructs a new {@link ISRCExhaustionException}.
     *
     * @param yearOfReference {@link Year} for which an ISRC was being generated for.
     */
    public ISRCExhaustionException(final @NonNull Year yearOfReference) {
        super("All prefixes and their designation codes have been exhausted for " + yearOfReference + ".");
    }
}
