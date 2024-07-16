package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>GroupSize</code> class.
 */
public class GroupSizeTest {
    /**
     * Verifies that the text for a simple group size can be parsed correctly.
     */
    @Test
    void parseGroupSizeShouldParseSimpleGroupSizeCorrectly() {
        GroupSize actual = GroupSize.parseGroupSize("1");
        assertEquals(new SimpleGroupSize(1), actual);
    }

    /**
     * Verifies that the text for a differentiated group size can be parsed correctly.
     */
    @Test
    void parseGroupSizeShouldParseDifferentiatedGroupSizeCorrectly() {
        GroupSize actual = GroupSize.parseGroupSize("1:2");
        assertEquals(new DifferentiatedGroupSize(1, 2), actual);
    }
}
