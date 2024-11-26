package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>SeatPositionInHemicycleComparator</code> class.
 */
public class SeatPositionInHemicycleComparatorTest {

    /**
     * The delta to compare angles.
     */
    private static final double HALF_ANGLE_DELTA = 0.0000005D;
    /**
     * The magic number six.
     */
    private static final double SIX = 6D;

    /**
     * The comparator instance to run the tests on.
     */
    private static SeatPositionInHemicycleComparator comparator;

    /**
     * Initialization method creating the comparator to run the tests on.
     */
    @BeforeAll
    static void createComparator() {
        comparator = new SeatPositionInHemicycleComparator();
    }

    /**
     * Test verifying that the comparator returns 0 when comparing two seat positions with the same coordinates.
     */
    @Test
    void seatPositionsWithEqualCoordinatesReturnZero() {
        SeatPosition seatPosition1 = new SeatPosition(1, 1D, 1D);
        SeatPosition seatPosition2 = new SeatPosition(1, 1D, 1D);
        assertEquals(0, comparator.compare(seatPosition1, seatPosition2));
    }

    /**
     * Test verifying that the comparator returns 0 when comparing two seat positions with the same radiuses, but with
     * the angle of the first seat position slightly less than the angle of the second seat position.
     */
    @Test
    void seatPositionsWithEqualRadiusButFirstAngleSlightlyLessReturnZero() {
        SeatPosition seatPosition1 = new SeatPosition(1, 1D, 1D - HALF_ANGLE_DELTA);
        SeatPosition seatPosition2 = new SeatPosition(1, 1D, 1D);
        assertEquals(0, comparator.compare(seatPosition1, seatPosition2));
    }

    /**
     * Test verifying that the comparator returns 0 when comparing two seat positions with the same radiuses, but with
     * the angle of the first seat position slightly greater than the angle of the second seat position.
     */
    @Test
    void seatPositionsWithEqualRadiusButFirstAngleSlightlyGreaterReturnZero() {
        SeatPosition seatPosition1 = new SeatPosition(1, 1D, 1D + HALF_ANGLE_DELTA);
        SeatPosition seatPosition2 = new SeatPosition(1, 1D, 1D);
        assertEquals(0, comparator.compare(seatPosition1, seatPosition2));
    }

    /**
     * Test verifying that the comparator returns a negative number if the first seat position has an angle closer to
     * 3π/2.
     */
    @Test
    void comparisonResultIsNegativeIfFirstSeatPositionIsCloserToSouth() {
        SeatPosition seatPosition1 = new SeatPosition(1, 1D, 2D);
        SeatPosition seatPosition2 = new SeatPosition(1, 1D, 1D);
        assertTrue(comparator.compare(seatPosition1, seatPosition2) < 0);
    }

    /**
     * Test verifying that the comparator returns a positive number if the second seat position has an angle closer to
     * 3π/2.
     */
    @Test
    void comparisonResultIsPositiveIfSecondSeatPositionIsCloserToSouth() {
        SeatPosition seatPosition1 = new SeatPosition(1, 1D, 1D);
        SeatPosition seatPosition2 = new SeatPosition(1, 1D, 2D);
        assertTrue(comparator.compare(seatPosition1, seatPosition2) > 0);
    }

    /**
     * Test verifying that the comparator returns a negative number if the first seat position has a positive angle less
     * than 3π/2, and the second seat position has a negative angle greater than -π/2.
     */
    @Test
    void comparisonResultIsNegativeIfSecondSeatPositionIsSouthEastAndFirstNot() {
        SeatPosition seatPosition1 = new SeatPosition(1, 1D, 1D);
        SeatPosition seatPosition2 = new SeatPosition(1, 1D, SIX);
        assertTrue(comparator.compare(seatPosition1, seatPosition2) < 0);
    }

    /**
     * Test verifying that the comparator returns a negative number if the first seat position has a smaller radius.
     */
    @Test
    void comparisonResultIsNegativeIfFirstSeatPositionHasSmallerRadius() {
        SeatPosition seatPosition1 = new SeatPosition(1, 1D, 1D);
        SeatPosition seatPosition2 = new SeatPosition(1, 2D, 1D);
        assertTrue(comparator.compare(seatPosition1, seatPosition2) < 0);
    }

    /**
     * Test verifying that the comparator returns a positive number if the first seat position has a larger radius.
     */
    @Test
    void comparisonResultIsPositiveIfFirstSeatPositionHasLargerRadius() {
        SeatPosition seatPosition1 = new SeatPosition(1, 2D, 1D);
        SeatPosition seatPosition2 = new SeatPosition(1, 1D, 1D);
        assertTrue(comparator.compare(seatPosition1, seatPosition2) > 0);
    }

}
