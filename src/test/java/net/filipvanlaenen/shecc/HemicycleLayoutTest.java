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

    /**
     * Test verifying that if the ratio between the inner and the outer radius is
     * not set, 2/3 is used as the default value.
     */
    @Test
    void twoThirdsShouldBeDefaultRadiusRatio() {
        HemicycleLayout layout = new HemicycleLayout(1);
        final double expectedRadiusRatio = 2.0D / 3.0D;
        assertEquals(expectedRadiusRatio, layout.getRadiusRatio());
    }

    /**
     * Test verifying that the ratio between the inner and the outer radius is wired
     * correctly from the constructor to the getter.
     */
    @Test
    void radiusRatioIsWiredCorrectlyFromConstructorToGetter() {
        final double testRatio = 0.5;
        final double testAngle = 3.0;
        HemicycleLayout layout = new HemicycleLayout(1, testAngle, testRatio);
        assertEquals(testRatio, layout.getRadiusRatio());
    }

    /**
     * Test verifying that the constructor throws a
     * <code>IllegalArgumentException</code> if the ratio between the inner and the
     * outer radius is zero or negative.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfRadiusRatioIsZero() {
        final double testAngle = 3.0;
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(1, testAngle, 0.0);
        });
    }

    /**
     * Test verifying that the constructor throws a
     * <code>IllegalArgumentException</code> if the ratio between the inner and the
     * outer radius is one or greater.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfRadiusRatioIsOne() {
        final double testAngle = 3.0;
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(1, testAngle, 1.0);
        });
    }
}
