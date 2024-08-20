package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>DifferentiatedGroupSize</code> class.
 */
public class DifferentiatedGroupSizeTest {
    /**
     * The magic number three.
     */
    private static final int THREE = 3;

    /**
     * Test verifying that getFullSize returns the total size.
     */
    @Test
    void getFullSizeShouldReturnTheTotalSize() {
        DifferentiatedGroupSize size = new DifferentiatedGroupSize(1, 2, THREE);
        assertEquals(THREE, size.getFullSize());
    }

    /**
     * Test verifying that the constructor taking an integer array with two integers is wired correctly.
     */
    @Test
    void constructorWithArrayShouldSetSizesCorectlyWhenTwoIntegersAreProvided() {
        DifferentiatedGroupSize size = new DifferentiatedGroupSize(new int[] {1, 2});
        assertEquals(1, size.lowerBound());
        assertEquals(2, size.median());
        assertEquals(2, size.total());
    }

    /**
     * Test verifying that the constructor taking an integer array with three integers is wired correctly.
     */
    @Test
    void constructorWithArrayShouldSetSizesCorectlyWhenThreeIntegersAreProvided() {
        DifferentiatedGroupSize size = new DifferentiatedGroupSize(new int[] {1, 2, THREE});
        assertEquals(1, size.lowerBound());
        assertEquals(2, size.median());
        assertEquals(THREE, size.total());
    }
}
