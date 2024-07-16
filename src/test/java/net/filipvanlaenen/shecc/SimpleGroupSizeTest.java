package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>SimpleGroupSize</code> class.
 */
public class SimpleGroupSizeTest {
    /**
     * Test verifying that getFullSize returns the size.
     */
    @Test
    void getFullSizeReturnsTheSize() {
        SimpleGroupSize size = new SimpleGroupSize(1);
        assertEquals(1, size.getFullSize());
    }
}
