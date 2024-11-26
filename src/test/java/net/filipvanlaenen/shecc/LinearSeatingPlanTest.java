package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Collection;

/**
 * Unit tests on the <code>LinearSeatingPlan</code> class.
 */
public class LinearSeatingPlanTest {
    /**
     * The magic number three.
     */
    private static final int THREE = 3;
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

    /**
     * Test verifying that the list of parliamentary groups is wired correctly from the constructor to the getter.
     */
    @Test
    void parliamentaryGroupsIsWiredCorrectlyFromTheConstructorToTheGetter() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(twoRedSeatsAndOneBlue);
        assertTrue(seatingPlan.getParliamentaryGroups().containsSame(Collection.of(twoRedSeatsAndOneBlue)));
    }

    /**
     * Test verifying that the number of seats is calculated correctly.
     */
    @Test
    void numberOfSeatsIsThreeForTwoRedSeatsAndOneBlueSeat() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(twoRedSeatsAndOneBlue);
        assertEquals(THREE, seatingPlan.getNumberOfSeats());
    }

    /**
     * Test verifying that the red group holds the first seat.
     */
    @Test
    void firstSeatIsForTheRedGroupForTwoRedSeatsAndOneBlue() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(twoRedSeatsAndOneBlue);
        assertEquals(RED_GROUP_WITH_TWO_SEATS, seatingPlan.getParliamentaryGroupAtSeat(0));
    }

    /**
     * Test verifying that the blue group holds the third seat.
     */
    @Test
    void thirdSeatIsForTheBlueGroupForTwoRedSeatsAndOneBlue() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(twoRedSeatsAndOneBlue);
        assertEquals(BLUE_GROUP_WITH_ONE_SEAT, seatingPlan.getParliamentaryGroupAtSeat(2));
    }

    /**
     * Test verifying that the first seat of the green group is certain if green is to the left.
     */
    @Test
    void firstGreenSeatIsCertainInTheLeftHalfOfTheHemicycle() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(oneTwoThreeGreenSeatsAndOneBlue);
        assertEquals(SeatStatus.CERTAIN, seatingPlan.getSeatStatus(0));
    }

    /**
     * Test verifying that the second seat of the green group is likely if green is to the left.
     */
    @Test
    void secondGreenSeatIsLikelyInTheLeftHalfOfTheHemicycle() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(oneTwoThreeGreenSeatsAndOneBlue);
        assertEquals(SeatStatus.LIKELY, seatingPlan.getSeatStatus(1));
    }

    /**
     * Test verifying that the third seat of the green group is unlikely if green is to the left.
     */
    @Test
    void thirdGreenSeatIsUnlikelyInTheLeftHalfOfTheHemicycle() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(oneTwoThreeGreenSeatsAndOneBlue);
        assertEquals(SeatStatus.UNLIKELY, seatingPlan.getSeatStatus(2));
    }

    /**
     * Test verifying that the last seat of the green group is certain if green is to the right.
     */
    @Test
    void lastGreenSeatIsCertainInTheRightHalfOfTheHemicycle() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(oneBlueAndOneTwoThreeGreenSeats);
        assertEquals(SeatStatus.CERTAIN, seatingPlan.getSeatStatus(THREE));
    }

    /**
     * Test verifying that the second seat of the green group is likely if green is to the right.
     */
    @Test
    void secondGreenSeatIsLikelyInTheRightHalfOfTheHemicycle() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(oneBlueAndOneTwoThreeGreenSeats);
        assertEquals(SeatStatus.LIKELY, seatingPlan.getSeatStatus(2));
    }

    /**
     * Test verifying that the third seat of the green group is unlikely if green is to the right.
     */
    @Test
    void firstGreenSeatIsUnlikelyInTheRightHalfOfTheHemicycle() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(oneBlueAndOneTwoThreeGreenSeats);
        assertEquals(SeatStatus.UNLIKELY, seatingPlan.getSeatStatus(1));
    }

    /**
     * Test verifying that the seating plan knows that it doesn't have likely or unlikely seats registered.
     */
    @Test
    void hasLikelyOrUnlikelySeatsReturnsFalseForCertainSeatsOnly() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(twoRedSeatsAndOneBlue);
        assertFalse(seatingPlan.hasUncertainSeats());
    }

    /**
     * Test verifying that the seating plan knows that it has likely or unlikely seats registered.
     */
    @Test
    void hasLikelyOrUnlikelySeatsReturnsTrueIfThereAreLikelyOrUnlikelySeats() {
        LinearSeatingPlan seatingPlan = new LinearSeatingPlan(oneBlueAndOneTwoThreeGreenSeats);
        assertTrue(seatingPlan.hasUncertainSeats());
    }
}
