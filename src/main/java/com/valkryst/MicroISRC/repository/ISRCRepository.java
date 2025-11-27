package com.valkryst.MicroISRC.repository;

import com.valkryst.MicroISRC.exception.ISRCExhaustionException;
import lombok.NonNull;

import java.time.Year;

/**
 * <p>
 *     Represents a backing store containing the information required to generate new ISRCs.
 * </p>
 * <p>
 *     As outlined <a href="https://en.wikipedia.org/wiki/International_Standard_Recording_Code#Format">here</a>, an
 *     ISRC consists of a <i>Country Code</i>, <i>Registrant Code</i>, <i>Year of Reference</i>, and a
 *     <i>Designation Code</i>. For this class, we consider a combination of the <i>Country Code</i> (e.g. {@code US})
 *     and <i>Registrant Code</i> (e.g. {@code SKG}) as an ISRC prefix (e.g. {@code USSKG}).
 * </p>
 * <p>
 *     Implementations of this class <em>must</em> ensure that all implemented methods are atomic and thread-safe.
 * </p>
 */
public interface ISRCRepository {
    /**
     * Retrieves all valid ISRC prefixes for the given <i>Year of Reference</i>.
     *
     * @param yearOfReference {@link Year} to retrieve prefixes for.
     * @return Valid prefixes.
     * @throws ISRCExhaustionException If there are no prefixes for the specified {@link Year}.
     */
    String[] getPrefixes(final @NonNull Year yearOfReference) throws ISRCExhaustionException;

    /**
     * <p>
     *     Retrieves the next available designation code for a given prefix, in a given year, and then increments
     *     and stores the next value in the backing store.
     * </p>
     *
     * <p>The designation code <em>must</em> only contain values between {@code 0} and {@code 99,999}, both inclusive.</p>
     *
     * @param prefix Prefix to retrieve the designation code for.
     * @param yearOfReference {@link Year} to retrieve the designation code for.
     * @return The designation code.
     *
     * @throws ISRCExhaustionException If there are no more designation codes for the given year and prefix.
     */
    int getAndIncrementDesignationCode(
        final @NonNull String prefix,
        final @NonNull Year yearOfReference
    ) throws ISRCExhaustionException;
}
