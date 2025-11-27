package com.valkryst.MicroISRC.exception;

import com.valkryst.MicroISRC.repository.ISRCRepository;
import lombok.NonNull;

/** Signals that an {@code null} or malformed ISRC prefix (see {@link ISRCRepository}) has been encountered. */
public class InvalidPrefixException extends RuntimeException {
    /** Constructs a new {@link InvalidPrefixException}, signaling that a {@code null} prefix has been encountered. */
    public InvalidPrefixException() {
        super("Encountered a null prefix.");
    }

    /**
     * Constructs a new {@link InvalidPrefixException}, signaling that a malformed prefix has been encountered.
     *
     * @param message See {@link RuntimeException#RuntimeException(String)}.
     */
    public InvalidPrefixException(final @NonNull String message) {
        super(message);
    }
}
