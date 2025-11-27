package com.valkryst.MicroISRC;

import com.valkryst.MicroISRC.exception.ISRCExhaustionException;
import com.valkryst.MicroISRC.exception.InvalidPrefixException;
import com.valkryst.MicroISRC.repository.ISRCRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Year;
import java.time.format.DateTimeFormatter;

public final class ISRCGenerator {
    /** {@link DateTimeFormatter} for formatting {@link Year} instances as valid <i>Year of Reference</i> values. */
    private static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormatter.ofPattern("uu");

    /** Singleton instance. */
    private static final ISRCGenerator INSTANCE = new ISRCGenerator();

    /** Represents the minimum value for an ISRC <i>Designation Code</i>. */
    public static final int MIN_DESIGNATION_CODE = 0;

    /** Represents the maximum value for an ISRC <i>Designation Code</i>. */
    public static final int MAX_DESIGNATION_CODE = 99_999;

    /** {@link ISRCRepository} to use when storing and retrieving prefix data. */
    @Getter @Setter private ISRCRepository repository;

    /** Private constructor to prevent instantiation. */
    private ISRCGenerator() {}

    /**
     * Attempts to generate
     *
     * @param yearOfReference {@link Year} to generate an ISRC for.
     * @return Generated ISRC.
     *
     * @throws IllegalStateException If the {@link #repository} has not been set.
     * @throws InvalidPrefixException See {@link InvalidPrefixException}.
     * @throws ISRCExhaustionException See {@link ISRCExhaustionException}.
     */
    public synchronized String generate(final @NonNull Year yearOfReference) throws IllegalStateException, ISRCExhaustionException {
        if (repository == null) {
            throw new IllegalStateException("No ISRCRepository has been set.");
        }

        String prefix = null;
        int designationCode = -1;
        for (final String tmpPrefix : repository.getPrefixes(yearOfReference)) {
            if (tmpPrefix == null) {
                throw new InvalidPrefixException();
            }

            if (tmpPrefix.length() != 5) {
                throw new InvalidPrefixException("Encountered prefix '" + tmpPrefix + "' with invalid length.");
            }

            int tmpDesignationCode = repository.getAndIncrementDesignationCode(tmpPrefix, yearOfReference);
            if (tmpDesignationCode < MAX_DESIGNATION_CODE) {
                prefix = tmpPrefix;
                designationCode = tmpDesignationCode;
                break;
            }
        }

        if (prefix == null) {
            throw new ISRCExhaustionException(yearOfReference);
        }

        return prefix + yearOfReference.format(YEAR_FORMATTER) + String.format("%05d", designationCode);
    }

    /**
     * Retrieves the singleton instance.
     *
     * @return {@link ISRCGenerator#INSTANCE}.
     */
    public static ISRCGenerator getInstance() {
        return INSTANCE;
    }
}