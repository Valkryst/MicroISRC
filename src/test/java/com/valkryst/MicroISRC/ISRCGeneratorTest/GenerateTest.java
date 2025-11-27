package com.valkryst.MicroISRC.ISRCGeneratorTest;

import com.valkryst.MicroISRC.ISRCGenerator;
import com.valkryst.MicroISRC.exception.ISRCExhaustionException;
import com.valkryst.MicroISRC.exception.InvalidPrefixException;
import com.valkryst.MicroISRC.repository.InMemoryISRCRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.time.ZoneId;

public class GenerateTest {
    private final ISRCGenerator generator = ISRCGenerator.getInstance();
    private InMemoryISRCRepository repository;
    private Year yearOfReference = Year.now(ZoneId.systemDefault());

    @BeforeEach
    public void beforeEach() {
        generator.setRepository(null);
        repository = new InMemoryISRCRepository();
        yearOfReference = Year.now(ZoneId.systemDefault());
    }

    @Test
    public void throwsExceptionWhenYearOfReferenceIsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> generator.generate(null));
    }

    @Test
    public void throwsExceptionWhenRepositoryIsNull() {
        Assertions.assertThrows(IllegalStateException.class, () -> generator.generate(yearOfReference));
    }

    @Test
    public void throwsExceptionWhenRepositoryHasNoPrefixes() {
        generator.setRepository(repository);
        Assertions.assertThrows(ISRCExhaustionException.class, () -> generator.generate(yearOfReference));
    }

    @Test
    public void throwsExceptionWhenRepositoryHasOnePrefixButThePrefixHasNoDesignationCodes() {
        repository.addPrefix("ABCDE", yearOfReference);
        repository.setDesignationCode("ABCDE", yearOfReference, ISRCGenerator.MAX_DESIGNATION_CODE);

        generator.setRepository(repository);
        Assertions.assertThrows(ISRCExhaustionException.class, () -> generator.generate(yearOfReference));
    }

    @Test
    public void throwsExceptionWhenRepositoryHasMultiplePrefixesAndNoneHaveDesignationCodes() {
        repository.addPrefix("ABCDE", yearOfReference);
        repository.addPrefix("FGHIJ", yearOfReference);
        repository.setDesignationCode("ABCDE", yearOfReference, ISRCGenerator.MAX_DESIGNATION_CODE);
        repository.setDesignationCode("FGHIJ", yearOfReference, ISRCGenerator.MAX_DESIGNATION_CODE);

        generator.setRepository(repository);
        Assertions.assertThrows(ISRCExhaustionException.class, () -> generator.generate(yearOfReference));
    }

    @Test
    public void throwsExceptionWhenInvalidPrefixIsEncountered() {
        repository.addPrefix("A", yearOfReference);
        repository.setDesignationCode("A", yearOfReference, ISRCGenerator.MAX_DESIGNATION_CODE);

        generator.setRepository(repository);
        Assertions.assertThrows(InvalidPrefixException.class, () -> generator.generate(yearOfReference));
    }

    @Test
    public void returnsISRCWhenRepositoryHasMultiplePrefixesAndOnlyTheFirstHasDesignationCodes() {
        yearOfReference = Year.of(2021);

        repository.addPrefix("ABCDE", yearOfReference);
        repository.addPrefix("FGHIJ", yearOfReference);
        repository.setDesignationCode("ABCDE", yearOfReference, ISRCGenerator.MIN_DESIGNATION_CODE);
        repository.setDesignationCode("FGHIJ", yearOfReference, ISRCGenerator.MAX_DESIGNATION_CODE);

        generator.setRepository(repository);
        Assertions.assertEquals("ABCDE2100000", generator.generate(yearOfReference));
    }

    @Test
    public void returnsISRCWhenRepositoryHasMultiplePrefixesAndOnlyTheLastHasDesignationCodes() {
        yearOfReference = Year.of(2021);

        repository.addPrefix("ABCDE", yearOfReference);
        repository.addPrefix("FGHIJ", yearOfReference);
        repository.setDesignationCode("ABCDE", yearOfReference, ISRCGenerator.MAX_DESIGNATION_CODE);
        repository.setDesignationCode("FGHIJ", yearOfReference, ISRCGenerator.MIN_DESIGNATION_CODE);

        generator.setRepository(repository);
        Assertions.assertEquals("FGHIJ2100000", generator.generate(yearOfReference));
    }

    @Test
    public void returnsISRCWhenRepositoryHasMultiplePrefixesAndOnlyTheLastTwoHaveDesignationCodes() {
        yearOfReference = Year.of(2021);

        repository.addPrefix("ABCDE", yearOfReference);
        repository.addPrefix("FGHIJ", yearOfReference);
        repository.addPrefix("KLMNO", yearOfReference);
        repository.setDesignationCode("ABCDE", yearOfReference, ISRCGenerator.MAX_DESIGNATION_CODE);
        repository.setDesignationCode("FGHIJ", yearOfReference, ISRCGenerator.MIN_DESIGNATION_CODE);
        repository.setDesignationCode("KLMNO", yearOfReference, ISRCGenerator.MIN_DESIGNATION_CODE);

        generator.setRepository(repository);
        Assertions.assertEquals("FGHIJ2100000", generator.generate(yearOfReference));
    }
}
