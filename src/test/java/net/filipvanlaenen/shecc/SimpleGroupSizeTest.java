package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class SimpleGroupSizeTest {
    /**
     * Test verifying that the size is wired correctly from the constructor to the
     * getter.
     */
    @Test
    void sizeIsWiredCorrectlyFromConstructorToGetter() {
        SimpleGroupSize size = new SimpleGroupSize(1);
        assertEquals(1, size.getSize());
    }
    
    /**
     * Test verifying that getFullSize returns the size.
     */
    @Test
    void getFullSizeReturnsTheSize() {
        SimpleGroupSize size = new SimpleGroupSize(1);
        assertEquals(1, size.getFullSize());
    }

    /**
     * Test verifying that a simple group size is equal to itself.
     */
    @Test
    void simpleGroupSizeIsEqualToItself() {
        SimpleGroupSize size = new SimpleGroupSize(1);
        assertEquals(size, size);
    }

    /**
     * Test verifying that a simple group size is equal to another simple group size
     * with the same size.
     */
    @Test
    void simpleGroupSizeIsEqualToOtherSimpleGroupSizeWithTheSameSize() {
        SimpleGroupSize size1 = new SimpleGroupSize(1);
        SimpleGroupSize size2 = new SimpleGroupSize(1);
        assertEquals(size1, size2);
    }

    /**
     * Test verifying that a simple group size is not equal to another simple group
     * size with a different size.
     */
    @Test
    void simpleGroupSizeIsNotEqualToAnotherSimpleGroupSizeWithADifferentSize() {
        SimpleGroupSize size1 = new SimpleGroupSize(1);
        SimpleGroupSize size2 = new SimpleGroupSize(2);
        assertNotEquals(size1, size2);
    }

    /**
     * Test verifying that a seat position is not equal to null.
     */
    @Test
    void simpleGroupSizeIsNotEqualToNull() {
        SimpleGroupSize size = new SimpleGroupSize(1);
        assertNotEquals(size, null);
    }

    /**
     * Test verifying that a simple group size is not equal to an object of another
     * class, like e.g. a String.
     */
    @Test
    void simpleGroupSizeIsNotEqualToAString() {
        SimpleGroupSize size = new SimpleGroupSize(1);
        assertNotEquals(size, "foo");
    }

    /**
     * Test verifying that two simple group sizes with the same sizes have the same
     * hash code.
     */
    @Test
    void simpleGroupSizesWithTheSameSizesHaveTheSameHashCode() {
        SimpleGroupSize size1 = new SimpleGroupSize(1);
        SimpleGroupSize size2 = new SimpleGroupSize(1);
        assertEquals(size1.hashCode(), size2.hashCode());
    }

    /**
     * Test verifying that simple group sizes with a different size have a different
     * hash code. Note that this isn't a required by the contract for the hash code
     * method, but in general this is a good practice.
     */
    @Test
    void simpleGroupSizesWithADifferentSizesHaveDifferentHashCodes() {
        SimpleGroupSize size1 = new SimpleGroupSize(1);
        SimpleGroupSize size2 = new SimpleGroupSize(2);
        assertNotEquals(size1.hashCode(), size2.hashCode());
    }

}
