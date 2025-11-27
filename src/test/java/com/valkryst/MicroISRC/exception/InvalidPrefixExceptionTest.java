package com.valkryst.MicroISRC.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvalidPrefixExceptionTest {
    @Test
    public void hasCorrectMessageWhenConstructedWithoutMessage() {
        Assertions.assertEquals(
            "Encountered a null prefix.",
            new InvalidPrefixException().getMessage()
        );
    }

    @Test
    public void hasCorrectMessageWhenConstructedWithMessage() {
        final var message = "This is a test message.";
        Assertions.assertEquals(message, new InvalidPrefixException(message).getMessage());
    }
}
