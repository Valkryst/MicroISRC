package com.valkryst.MicroISRC.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.time.ZoneId;

public class ISRCExhaustionExceptionTest {
    @Test
    public void hasCorrectMessageWhenConstructedWithYear() {
        final Year year = Year.now(ZoneId.systemDefault());
        Assertions.assertEquals(
            "All prefixes and their designation codes have been exhausted for " + year + ".",
            new ISRCExhaustionException(year).getMessage()
        );
    }
}
