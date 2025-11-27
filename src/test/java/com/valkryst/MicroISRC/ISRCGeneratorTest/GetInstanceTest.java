package com.valkryst.MicroISRC.ISRCGeneratorTest;

import com.valkryst.MicroISRC.ISRCGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GetInstanceTest {
    @Test
    public void returnsSingletonInstance() {
        Assertions.assertSame(ISRCGenerator.getInstance(), ISRCGenerator.getInstance());
    }
}
