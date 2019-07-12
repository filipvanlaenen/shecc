package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>ParliamentaryGroup</code> class.
 */
public class ParliamentaryGroupTest {
    /**
     * Test verifying that the size is wired correctly from the constructor to the
     * getter.
     */
    @Test
    void sizeIsWiredCorrectlyFromTheConstructorToTheGetter() {
        ParliamentaryGroup parliamentaryGroup = new ParliamentaryGroup(1, 0xFF0000);
        assertEquals(1, parliamentaryGroup.getSize());
    }

    /**
     * Test verifying that the color is wired correctly from the constructor to the
     * getter.
     */
    @Test
    void colorIsWiredCorrectlyFromTheConstructorToTheGetter() {
        ParliamentaryGroup parliamentaryGroup = new ParliamentaryGroup(1, 0xFF0000);
        assertEquals(0xFF0000, parliamentaryGroup.getColor());
    }
}
