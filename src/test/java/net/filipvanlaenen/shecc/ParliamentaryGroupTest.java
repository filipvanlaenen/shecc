package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>ParliamentaryGroup</code> class.
 */
public class ParliamentaryGroupTest {
    /**
     * Magic number for the color red.
     */
    private static final int RED = 0xFF0000;
    /**
     * Magic number for the color magenta.
     */
    private static final int MAGENTA = 0xFF00FF;
    /**
     * Array representing the red/magenta color combination.
     */
    private static final int[] RED_MAGENTA = new int[] {RED, MAGENTA};
    /**
     * Test verifying that the size is wired correctly from the constructor to the
     * getter if it is provided as an integer.
     */
    @Test
    void integerSizeIsWiredCorrectlyFromTheConstructorToTheGetter() {
        ParliamentaryGroup parliamentaryGroup = new ParliamentaryGroup(1, RED);
        assertEquals(new SimpleGroupSize(1), parliamentaryGroup.getSize());
    }

    /**
     * Test verifying that the size is wired correctly from the constructor to the
     * getter if it is provided as a simple size.
     */
    @Test
    void simpleSizeIsWiredCorrectlyFromTheConstructorToTheGetter() {
        SimpleGroupSize simpleSize = new SimpleGroupSize(1);
        ParliamentaryGroup parliamentaryGroup = new ParliamentaryGroup(simpleSize, RED);
        assertEquals(simpleSize, parliamentaryGroup.getSize());
    }

    /**
     * Test verifying that the size is wired correctly from the constructor to the
     * getter if it is provided as a differentiated size.
     */
    @Test
    void differentiatedSizeIsWiredCorrectlyFromTheConstructorToTheGetter() {
        DifferentiatedGroupSize differentiatedSize = new DifferentiatedGroupSize(1, 2, 3);
        ParliamentaryGroup parliamentaryGroup = new ParliamentaryGroup(differentiatedSize, RED);
        assertEquals(differentiatedSize, parliamentaryGroup.getSize());
    }

    /**
     * Test verifying that the color is wired correctly from the constructor to the
     * getter if only one color is specified.
     */
    @Test
    void singleColorIsWiredCorrectlyFromTheConstructorToTheGetter() {
        ParliamentaryGroup parliamentaryGroup = new ParliamentaryGroup(1, RED);
        assertArrayEquals(new int[] {RED}, parliamentaryGroup.getColors());
    }

    /**
     * Test verifying that a set of colors is wired correctly from the constructor to the
     * getter.
     */
    @Test
    void colorsAreWiredCorrectlyFromTheConstructorToTheGetter() {
        ParliamentaryGroup parliamentaryGroup = new ParliamentaryGroup(1, RED_MAGENTA);
        assertArrayEquals(RED_MAGENTA, parliamentaryGroup.getColors());
    }

    /**
     * Test verifying that by default, the character of a parliamentary group is
     * null.
     */
    @Test
    void byDefaultTheCharacterIsNull() {
        ParliamentaryGroup parliamentaryGroup = new ParliamentaryGroup(1, RED);
        assertNull(parliamentaryGroup.getCharacter());
    }

    /**
     * Test verifying that the character is wired correctly from the constructor to
     * the getter.
     */
    @Test
    void characterIsWiredCorrectlyFromTheConstructorToTheGetter() {
        ParliamentaryGroup parliamentaryGroup = new ParliamentaryGroup(1, RED, null, "R");
        assertEquals("R", parliamentaryGroup.getCharacter());
    }

    /**
     * Test verifying that by default, the name of a parliamentary group is null.
     */
    @Test
    void byDefaultTheNameIsNull() {
        ParliamentaryGroup parliamentaryGroup = new ParliamentaryGroup(1, RED);
        assertNull(parliamentaryGroup.getName());
    }

    /**
     * Test verifying that the name is wired correctly from the constructor to the
     * getter.
     */
    @Test
    void nameIsWiredCorrectlyFromTheConstructorToTheGetter() {
        ParliamentaryGroup parliamentaryGroup = new ParliamentaryGroup(1, RED, "Red");
        assertEquals("Red", parliamentaryGroup.getName());
    }
}
