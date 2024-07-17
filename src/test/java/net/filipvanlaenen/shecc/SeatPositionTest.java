package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>SeatPosition</code> class.
 */
public class SeatPositionTest {
    /**
     * The delta for double comparisons.
     */
    private static final double DOUBLE_DELTA = 0.000001D;
    /**
     * The magic number a half.
     */
    private static final double A_HALF = 0.5D;

    /**
     * Test verifying that the String representation of a seat position shows the radius and the angle.
     */
    @Test
    void toStringShowsRadiusAndAngle() {
        SeatPosition seatPosition = new SeatPosition(1D, Math.PI);
        assertEquals("Seat position (1.0, 3.141592653589793 rad)", seatPosition.toString());
    }

    /**
     * Test verifying that the x coordinate is calculated correctly.
     */
    @Test
    void xCoordinateIsCalculatedCorrectly() {
        SeatPosition seatPosition = new SeatPosition(A_HALF, 1D);
        double expected = Math.cos(1D) / 2D;
        assertEquals(expected, seatPosition.getX(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that the y coordinate is calculated correctly.
     */
    @Test
    void yCoordinateIsCalculatedCorrectly() {
        SeatPosition seatPosition = new SeatPosition(A_HALF, 1D);
        double expected = Math.sin(1D) / 2D;
        assertEquals(expected, seatPosition.getY(), DOUBLE_DELTA);
    }
}
