package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.SortedCollection;

/**
 * Unit tests on the <code>RowConnectedSeatingPlan</code> class.
 */
public class RowConnectedSeatingPlanTest {
    /**
     * The magic number three.
     */
    private static final int THREE = 3;
    private static final int FOUR = 4;
    /**
     * A parliamentary group with two seats using the color red.
     */
    private static final ParliamentaryGroup RED_GROUP_WITH_TWO_SEATS = new ParliamentaryGroup(2, 0xFF0000);
    /**
     * A parliamentary group with one seat using the color blue.
     */
    private static final ParliamentaryGroup BLUE_GROUP_WITH_ONE_SEAT = new ParliamentaryGroup(1, 0x0000FF);
    /**
     * A parliamentary group with three differentiated seats using the color green.
     */
    private static final ParliamentaryGroup GREEN_GROUP_WITH_ONE_TWO_THREE_SEATS =
            new ParliamentaryGroup(new DifferentiatedGroupSize(1, 2, 3), 0x0000FF);
    /**
     * A list with two red seats and one blue seat to run the tests on.
     */
    private static ParliamentaryGroup[] twoRedSeatsAndOneBlue =
            new ParliamentaryGroup[] {RED_GROUP_WITH_TWO_SEATS, BLUE_GROUP_WITH_ONE_SEAT};
    /**
     * A list with one-two-three green seats and one blue seat to run the tests on.
     */
    private static ParliamentaryGroup[] oneTwoThreeGreenSeatsAndOneBlue =
            new ParliamentaryGroup[] {GREEN_GROUP_WITH_ONE_TWO_THREE_SEATS, BLUE_GROUP_WITH_ONE_SEAT};
    /**
     * A list with one blue seat and one-two-three green seats to run the tests on.
     */
    private static ParliamentaryGroup[] oneBlueAndOneTwoThreeGreenSeats =
            new ParliamentaryGroup[] {BLUE_GROUP_WITH_ONE_SEAT, GREEN_GROUP_WITH_ONE_TWO_THREE_SEATS};

    private static SortedCollection<SeatPosition> THREE_SEAT_POSITIONS = new HemicycleLayout(THREE).getSeatPositions();
    private static SortedCollection<SeatPosition> FOUR_SEAT_POSITIONS = new HemicycleLayout(FOUR).getSeatPositions();

    /**
     * Test verifying that the list of parliamentary groups is wired correctly from the constructor to the getter.
     */
    @Test
    void parliamentaryGroupsIsWiredCorrectlyFromTheConstructorToTheGetter() {
        RowConnectedSeatingPlan seatingPlan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, twoRedSeatsAndOneBlue);
        assertTrue(seatingPlan.getParliamentaryGroups().containsSame(Collection.of(twoRedSeatsAndOneBlue)));
    }

    /**
     * Test verifying that the number of seats is calculated correctly.
     */
    @Test
    void numberOfSeatsIsThreeForTwoRedSeatsAndOneBlueSeat() {
        RowConnectedSeatingPlan seatingPlan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, twoRedSeatsAndOneBlue);
        assertEquals(THREE, seatingPlan.getNumberOfSeats());
    }

    /**
     * Test verifying that the red group holds the first seat.
     */
    @Test
    void firstSeatIsForTheRedGroupForTwoRedSeatsAndOneBlue() {
        RowConnectedSeatingPlan seatingPlan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, twoRedSeatsAndOneBlue);
        assertEquals(RED_GROUP_WITH_TWO_SEATS, seatingPlan.getParliamentaryGroupAtSeat(0));
    }

    /**
     * Test verifying that the blue group holds the third seat.
     */
    @Test
    void thirdSeatIsForTheBlueGroupForTwoRedSeatsAndOneBlue() {
        RowConnectedSeatingPlan seatingPlan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, twoRedSeatsAndOneBlue);
        assertEquals(BLUE_GROUP_WITH_ONE_SEAT, seatingPlan.getParliamentaryGroupAtSeat(2));
    }

    /**
     * Test verifying that the first seat of the green group is certain if green is to the left.
     */
    @Test
    void firstGreenSeatIsCertainInTheLeftHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, oneTwoThreeGreenSeatsAndOneBlue);
        assertEquals(SeatStatus.CERTAIN, seatingPlan.getSeatStatus(0));
    }

    /**
     * Test verifying that the second seat of the green group is likely if green is to the left.
     */
    @Test
    void secondGreenSeatIsLikelyInTheLeftHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, oneTwoThreeGreenSeatsAndOneBlue);
        assertEquals(SeatStatus.LIKELY, seatingPlan.getSeatStatus(1));
    }

    /**
     * Test verifying that the third seat of the green group is unlikely if green is to the left.
     */
    @Test
    void thirdGreenSeatIsUnlikelyInTheLeftHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, oneTwoThreeGreenSeatsAndOneBlue);
        assertEquals(SeatStatus.UNLIKELY, seatingPlan.getSeatStatus(2));
    }

    /**
     * Test verifying that the last seat of the green group is certain if green is to the right.
     */
    @Test
    void lastGreenSeatIsCertainInTheRightHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, oneBlueAndOneTwoThreeGreenSeats);
        assertEquals(SeatStatus.CERTAIN, seatingPlan.getSeatStatus(THREE));
    }

    /**
     * Test verifying that the second seat of the green group is likely if green is to the right.
     */
    @Test
    void secondGreenSeatIsLikelyInTheRightHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, oneBlueAndOneTwoThreeGreenSeats);
        assertEquals(SeatStatus.LIKELY, seatingPlan.getSeatStatus(2));
    }

    /**
     * Test verifying that the third seat of the green group is unlikely if green is to the right.
     */
    @Test
    void firstGreenSeatIsUnlikelyInTheRightHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, oneBlueAndOneTwoThreeGreenSeats);
        assertEquals(SeatStatus.UNLIKELY, seatingPlan.getSeatStatus(1));
    }

    /**
     * Test verifying that the seating plan knows that it doesn't have likely or unlikely seats registered.
     */
    @Test
    void hasLikelyOrUnlikelySeatsReturnsFalseForCertainSeatsOnly() {
        RowConnectedSeatingPlan seatingPlan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, twoRedSeatsAndOneBlue);
        assertFalse(seatingPlan.hasUncertainSeats());
    }

    /**
     * Test verifying that the seating plan knows that it has likely or unlikely seats registered.
     */
    @Test
    void hasLikelyOrUnlikelySeatsReturnsTrueIfThereAreLikelyOrUnlikelySeats() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, oneBlueAndOneTwoThreeGreenSeats);
        assertTrue(seatingPlan.hasUncertainSeats());
    }
}
