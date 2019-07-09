package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>ChartLayout</code> class.
 */
class HemicycleLayoutTest {

    /**
     * Test verifying that the number of seats is wired correctly from the
     * constructor to the getter.
     */
    @Test
    void noOfSeatsIsWiredCorrectlyFromConstructorToGetter() {
        HemicycleLayout layout = new HemicycleLayout(1);
        assertEquals(1, layout.getNoOfSeats());
    }

    /**
     * Test verifying that the constructor throws a
     * <code>IllegalArgumentException</code> if the number of seats is zero or
     * negative.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfNoOfSeatsIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(0);
        });
    }

    /**
     * Test verifying that if no angle is set, π is used as the default value.
     */
    @Test
    void piShouldBeDefaultAngle() {
        HemicycleLayout layout = new HemicycleLayout(1);
        assertEquals(Math.PI, layout.getAngle());
    }

    /**
     * Test verifying that the angle is wired correctly from the constructor to the
     * getter.
     */
    @Test
    void angleIsWiredCorrectlyFromConstructorToGetter() {
        final double testAngle = 3.0;
        HemicycleLayout layout = new HemicycleLayout(1, testAngle);
        assertEquals(testAngle, layout.getAngle());
    }

    /**
     * Test verifying that the constructor throws a
     * <code>IllegalArgumentException</code> if the angle is zero or negative.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfAngleIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(1, 0.0);
        });
    }
}
