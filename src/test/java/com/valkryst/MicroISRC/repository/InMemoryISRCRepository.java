package com.valkryst.MicroISRC.repository;

import com.valkryst.MicroISRC.exception.ISRCExhaustionException;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class exists only to serve as a reference implementation of {@link ISRCRepository} and as a concrete
 * implementation to be used with this library's tests.
 */
@NoArgsConstructor
public class InMemoryISRCRepository implements ISRCRepository {
    /**
     * A thread-safe, nested map representing which ISRC prefixes (see {@link ISRCRepository}) are valid for which
     * years and what the most-recent <i>Designation Code</i> is for each of those prefixes.
     */
    private final Map<Year, Map<String, Integer>> data = new ConcurrentHashMap<>();

    /**
     * Adds a new prefix, for the specified {@link Year}, to {@link #data} and sets the <i>Designation Code</i> to
     * {@code 0}.
     *
     * @param prefix Prefix to add.
     * @param yearOfReference {@link Year} to add the prefix for.
     */
    public void addPrefix(final String prefix, final @NonNull Year yearOfReference) {
        data.putIfAbsent(yearOfReference, new HashMap<>());
        data.get(yearOfReference).putIfAbsent(prefix, 0);
    }

    @Override
    public String[] getPrefixes(final @NonNull Year yearOfReference) {
        if (!data.containsKey(yearOfReference)) {
            throw new ISRCExhaustionException(yearOfReference);
        }

        return data.get(yearOfReference).keySet().toArray(new String[0]);
    }

    @Override
    public int getAndIncrementDesignationCode(
        final @NonNull String prefix,
        final @NonNull Year yearOfReference
    ) {
        final int designationCode = data.get(yearOfReference).get(prefix);
        data.get(yearOfReference).put(prefix, designationCode + 1);
        return designationCode;
    }

    /**
     * <p>Sets the <i>Designation Code</i> for a given prefix in a given year.</p>
     *
     * <p>
     *     This method should not be included in an actual implementation of {@link ISRCRepository}. It exists here to
     *     make writing tests a bit easier.
     * </p>
     *
     * @param prefix Prefix to set the designation code for.
     * @param yearOfReference {@link Year} to set the designation code for.
     * @param designationCode The new designation code.
     */
    public void setDesignationCode(
        final @NonNull String prefix,
        final @NonNull Year yearOfReference,
        final int designationCode
    ) {
        data.get(yearOfReference).put(prefix, designationCode);
    }
}
