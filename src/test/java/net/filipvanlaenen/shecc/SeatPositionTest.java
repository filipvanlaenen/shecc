package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
     * Test verifying that the radius is wired correctly from the constructor to the
     * getter.
     */
    @Test
    void radiusIsWiredCorrectlyFromConstructorToGetter() {
        SeatPosition seatPosition = new SeatPosition(1D, Math.PI);
        assertEquals(1D, seatPosition.getRadius());
    }

    /**
     * Test verifying that the angle is wired correctly from the constructor to the
     * getter.
     */
    @Test
    void angleIsWiredCorrectlyFromConstructorToGetter() {
        SeatPosition seatPosition = new SeatPosition(1D, Math.PI);
        assertEquals(Math.PI, seatPosition.getAngle());
    }

    /**
     * Test verifying that a seat position is equal to itself.
     */
    @Test
    void seatPositionIsEqualToItself() {
        SeatPosition seatPosition = new SeatPosition(1D, Math.PI);
        assertEquals(seatPosition, seatPosition);
    }

    /**
     * Test verifying that a seat position is equal to another seat position with
     * the same coordinates.
     */
    @Test
    void seatPositionIsEqualToOtherSeatPositionWithTheSameCoordinates() {
        SeatPosition seatPosition1 = new SeatPosition(1D, Math.PI);
        SeatPosition seatPosition2 = new SeatPosition(1D, Math.PI);
        assertEquals(seatPosition1, seatPosition2);
    }

    /**
     * Test verifying that a seat position is not equal to another seat position
     * with a different radius.
     */
    @Test
    void seatPositionIsNotEqualToAnotherSeatPositionWithADifferentRadius() {
        SeatPosition seatPosition1 = new SeatPosition(1D, Math.PI);
        SeatPosition seatPosition2 = new SeatPosition(2D, Math.PI);
        assertNotEquals(seatPosition1, seatPosition2);
    }

    /**
     * Test verifying that a seat position is not equal to another seat position
     * with a different angle.
     */
    @Test
    void seatPositionIsNotEqualToAnotherSeatPositionWithADifferentAngle() {
        SeatPosition seatPosition1 = new SeatPosition(1D, Math.PI);
        SeatPosition seatPosition2 = new SeatPosition(1D, 1D);
        assertNotEquals(seatPosition1, seatPosition2);
    }

    /**
     * Test verifying that a seat position is not equal to null.
     */
    @Test
    void seatPositionIsNotEqualToNull() {
        SeatPosition seatPosition = new SeatPosition(1D, Math.PI);
        assertNotEquals(seatPosition, null);
    }

    /**
     * Test verifying that a seat position is not equal to an object of another
     * class, like e.g. a String.
     */
    @Test
    void seatPositionIsNotEqualToAString() {
        SeatPosition seatPosition = new SeatPosition(1D, Math.PI);
        assertNotEquals(seatPosition, "foo");
    }

    /**
     * Test verifying that two seat positions with the same coordinates have the
     * same hash code.
     */
    @Test
    void seatPositionsWithTheSameCoordinatesHaveTheSameHashCode() {
        SeatPosition seatPosition1 = new SeatPosition(1D, Math.PI);
        SeatPosition seatPosition2 = new SeatPosition(1D, Math.PI);
        assertEquals(seatPosition1.hashCode(), seatPosition2.hashCode());
    }

    /**
     * Test verifying that seat positions with a different radius have a different
     * hash code. Note that this isn't a required by the contract for the hash code
     * method, but in general this is a good practice.
     */
    @Test
    void seatPositionsWithADifferentRadiusHaveDifferentHashCodes() {
        SeatPosition seat1 = new SeatPosition(1D, Math.PI);
        SeatPosition seat2 = new SeatPosition(2D, Math.PI);
        assertNotEquals(seat1.hashCode(), seat2.hashCode());
    }

    /**
     * Test verifying that seat positions with a different angle have a different
     * hash code. Note that this isn't a required by the contract for the hash code
     * method, but in general this is a good practice.
     */
    @Test
    void seatPositionsWithADifferentAngleHaveDifferentHashCodes() {
        SeatPosition seatPosition1 = new SeatPosition(1D, Math.PI);
        SeatPosition seatPosition2 = new SeatPosition(1D, 1D);
        assertNotEquals(seatPosition1.hashCode(), seatPosition2.hashCode());
    }

    /**
     * Test verifying that the String representation of a seat position shows the
     * radius and the angle.
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
