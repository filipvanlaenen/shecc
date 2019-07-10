package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>SeatPosition</code> class.
 */
public class SeatPositionTest {

    /**
     * Test verifying that the radius is wired correctly from the constructor to the
     * getter.
     */
    @Test
    void radiusIsWiredCorrectlyFromConstructorToGetter() {
        SeatPosition seat = new SeatPosition(1D, Math.PI);
        assertEquals(1D, seat.getRadius());
    }

    /**
     * Test verifying that the angle is wired correctly from the constructor to the
     * getter.
     */
    @Test
    void angleIsWiredCorrectlyFromConstructorToGetter() {
        SeatPosition seat = new SeatPosition(1D, Math.PI);
        assertEquals(Math.PI, seat.getAngle());
    }

    /**
     * Test verifying that a seat position is equal to itself.
     */
    @Test
    void seatPositionIsEqualToItself() {
        SeatPosition seat = new SeatPosition(1D, Math.PI);
        assertEquals(seat, seat);
    }

    /**
     * Test verifying that a seat position is equal to another seat position with
     * the same coordinates.
     */
    @Test
    void seatPositionIsEqualToOtherSeatPositionWithTheSameCoordinates() {
        SeatPosition seat1 = new SeatPosition(1D, Math.PI);
        SeatPosition seat2 = new SeatPosition(1D, Math.PI);
        assertEquals(seat1, seat2);
    }

    /**
     * Test verifying that a seat position is not equal to another seat position
     * with a different radius.
     */
    @Test
    void seatPositionIsNotEqualToAnotherSeatPositionWithADifferentRadius() {
        SeatPosition seat1 = new SeatPosition(1D, Math.PI);
        SeatPosition seat2 = new SeatPosition(2D, Math.PI);
        assertNotEquals(seat1, seat2);
    }

    /**
     * Test verifying that a seat position is not equal to another seat position
     * with a different angle.
     */
    @Test
    void seatPositionIsNotEqualToAnotherSeatPositionWithADifferentAngle() {
        SeatPosition seat1 = new SeatPosition(1D, Math.PI);
        SeatPosition seat2 = new SeatPosition(1D, 1D);
        assertNotEquals(seat1, seat2);
    }

    /**
     * Test verifying that a seat position is not equal to null.
     */
    @Test
    void seatPositionIsNotEqualToNull() {
        SeatPosition seat = new SeatPosition(1D, Math.PI);
        assertNotEquals(seat, null);
    }

    /**
     * Test verifying that a seat position is not equal to an object of another
     * class, like e.g. a String.
     */
    @Test
    void seatPositionIsNotEqualToAString() {
        SeatPosition seat = new SeatPosition(1D, Math.PI);
        assertNotEquals(seat, "foo");
    }

    /**
     * Test verifying that two seat positions with the same coordinates have the
     * same hash code.
     */
    @Test
    void seatPositionsWithTheSameCoordinatesHaveTheSameHashCode() {
        SeatPosition seat1 = new SeatPosition(1D, Math.PI);
        SeatPosition seat2 = new SeatPosition(1D, Math.PI);
        assertEquals(seat1.hashCode(), seat2.hashCode());
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
        SeatPosition seat1 = new SeatPosition(1D, Math.PI);
        SeatPosition seat2 = new SeatPosition(1D, 1D);
        assertNotEquals(seat1.hashCode(), seat2.hashCode());
    }

    /**
     * Test verifying that the String representation of a seat position shows the radius and the angle.
     */
    @Test
    void toStringShowsRadiusAndAngle() {
        SeatPosition seat = new SeatPosition(1D, Math.PI);
        assertEquals("Seat position (1.0, 3.141592653589793 rad)", seat.toString());
    }
}
