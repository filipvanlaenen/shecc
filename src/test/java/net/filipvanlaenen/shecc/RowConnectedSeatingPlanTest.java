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
    /**
     * The magic number four.
     */
    private static final int FOUR = 4;
    /**
     * The magic number five.
     */
    private static final int FIVE = 5;
    /**
     * The magic number eight.
     */
    private static final int EIGHT = 8;
    /**
     * The RGB value for blue.
     */
    private static final int BLUE = 0x0000FF;
    /**
     * A parliamentary group with no seat using the color blue.
     */
    private static final ParliamentaryGroup BLUE_GROUP_WITH_NO_SEAT = new ParliamentaryGroup(0, BLUE);
    /**
     * A parliamentary group with one seat using the color blue.
     */
    private static final ParliamentaryGroup BLUE_GROUP_WITH_ONE_SEAT = new ParliamentaryGroup(1, BLUE);
    /**
     * A parliamentary group with two seats using the color blue.
     */
    private static final ParliamentaryGroup BLUE_GROUP_WITH_TWO_SEATS = new ParliamentaryGroup(2, BLUE);
    /**
     * A parliamentary group with three seats using the color blue.
     */
    private static final ParliamentaryGroup BLUE_GROUP_WITH_THREE_SEATS = new ParliamentaryGroup(3, BLUE);
    /**
     * A parliamentary group with one seat using the color green.
     */
    private static final ParliamentaryGroup GREEN_GROUP_WITH_ONE_SEAT = new ParliamentaryGroup(1, 0x00FF00);
    /**
     * A parliamentary group with three seats using the color green.
     */
    private static final ParliamentaryGroup GREEN_GROUP_WITH_THREE_SEATS = new ParliamentaryGroup(3, 0x00FF00);
    /**
     * A parliamentary group with three differentiated seats using the color green.
     */
    private static final ParliamentaryGroup GREEN_GROUP_WITH_ONE_TWO_THREE_SEATS =
            new ParliamentaryGroup(new DifferentiatedGroupSize(1, 2, 3), 0x0000FF);
    /**
     * A parliamentary group with two seats using the color red.
     */
    private static final ParliamentaryGroup RED_GROUP_WITH_TWO_SEATS = new ParliamentaryGroup(2, 0xFF0000);
    /**
     * The seat positions for a hemicycle layout with three seats.
     */
    private static final SortedCollection<SeatPosition> THREE_SEAT_POSITIONS =
            new HemicycleLayout(THREE).getSeatPositions();
    /**
     * The seat positions for a hemicycle layout with four seats.
     */
    private static final SortedCollection<SeatPosition> FOUR_SEAT_POSITIONS =
            new HemicycleLayout(FOUR).getSeatPositions();
    /**
     * The seat positions for a hemicycle layout with eight seats.
     */
    private static final SortedCollection<SeatPosition> EIGHT_SEAT_POSITIONS =
            new HemicycleLayout(EIGHT).getSeatPositions();
    /**
     * A list with two red seats and one blue seat to run the tests on.
     */
    private static final ParliamentaryGroup[] TWO_RED_SEATS_AND_ONE_BLUE =
            new ParliamentaryGroup[] {RED_GROUP_WITH_TWO_SEATS, BLUE_GROUP_WITH_ONE_SEAT};
    /**
     * A list with one-two-three green seats and one blue seat to run the tests on.
     */
    private static final ParliamentaryGroup[] ONE_TWO_THREE_GREEN_SEATS_AND_ONE_BLUE =
            new ParliamentaryGroup[] {GREEN_GROUP_WITH_ONE_TWO_THREE_SEATS, BLUE_GROUP_WITH_ONE_SEAT};
    /**
     * A list with one blue seat and one-two-three green seats to run the tests on.
     */
    private static final ParliamentaryGroup[] ONE_BLUE_AND_ONE_TWO_THREE_GREEN_SEATS =
            new ParliamentaryGroup[] {BLUE_GROUP_WITH_ONE_SEAT, GREEN_GROUP_WITH_ONE_TWO_THREE_SEATS};

    /**
     * Test verifying that the list of parliamentary groups is wired correctly from the constructor to the getter.
     */
    @Test
    void parliamentaryGroupsShouldBeWiredCorrectlyFromTheConstructorToTheGetter() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, TWO_RED_SEATS_AND_ONE_BLUE);
        assertTrue(seatingPlan.getParliamentaryGroups().containsSame(Collection.of(TWO_RED_SEATS_AND_ONE_BLUE)));
    }

    /**
     * Test verifying that the number of seats is calculated correctly.
     */
    @Test
    void numberOfSeatsShouldBeThreeForTwoRedSeatsAndOneBlueSeat() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, TWO_RED_SEATS_AND_ONE_BLUE);
        assertEquals(THREE, seatingPlan.getNumberOfSeats());
    }

    /**
     * Test verifying that the red group holds the first seat.
     */
    @Test
    void firstSeatShouldBeForTheRedGroupForTwoRedSeatsAndOneBlue() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, TWO_RED_SEATS_AND_ONE_BLUE);
        assertEquals(RED_GROUP_WITH_TWO_SEATS, seatingPlan.getParliamentaryGroupAtSeat(0));
    }

    /**
     * Test verifying that the red group holds the first seat if the blue group has no seats.
     */
    @Test
    void firstSeatShouldBeForTheRedGroupForNoBlueSeatsAndTwoRedSeatsAndOneGreen() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, new ParliamentaryGroup[] {BLUE_GROUP_WITH_NO_SEAT,
                        RED_GROUP_WITH_TWO_SEATS, GREEN_GROUP_WITH_ONE_SEAT});
        assertEquals(RED_GROUP_WITH_TWO_SEATS, seatingPlan.getParliamentaryGroupAtSeat(0));
    }

    /**
     * Test verifying that the blue group holds the third seat.
     */
    @Test
    void thirdSeatShouldBeForTheBlueGroupForTwoRedSeatsAndOneBlue() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, TWO_RED_SEATS_AND_ONE_BLUE);
        assertEquals(BLUE_GROUP_WITH_ONE_SEAT, seatingPlan.getParliamentaryGroupAtSeat(2));
    }

    /**
     * Test verifying that the red group holds the third seat to keep the rows connected.
     */
    @Test
    void thirdSeatShouldBeForTheRedGroupForTwoRedSeatsAndOneBlueAndOneGreen() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, new ParliamentaryGroup[] {RED_GROUP_WITH_TWO_SEATS,
                        BLUE_GROUP_WITH_ONE_SEAT, GREEN_GROUP_WITH_ONE_SEAT});
        assertEquals(RED_GROUP_WITH_TWO_SEATS, seatingPlan.getParliamentaryGroupAtSeat(2));
    }

    /**
     * Test verifying that the red group holds the sixth seat to keep the rows connected.
     */
    @Test
    void sixthSeatShouldBeForTheRedGroupForThreeBlueAndTwoRedAndThreeGreenSeats() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(EIGHT_SEAT_POSITIONS, new ParliamentaryGroup[] {BLUE_GROUP_WITH_THREE_SEATS,
                        RED_GROUP_WITH_TWO_SEATS, GREEN_GROUP_WITH_THREE_SEATS});
        assertEquals(RED_GROUP_WITH_TWO_SEATS, seatingPlan.getParliamentaryGroupAtSeat(FIVE));
    }

    /**
     * Test verifying that the blue group holds the fourth seat because it can't keep the rows connected.
     */
    @Test
    void fourthSeatShouldBeForTheBlueGroupForTwoRedSeatsAndTwoBlue() {
        RowConnectedSeatingPlan seatingPlan = new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS,
                new ParliamentaryGroup[] {RED_GROUP_WITH_TWO_SEATS, BLUE_GROUP_WITH_TWO_SEATS});
        assertEquals(BLUE_GROUP_WITH_TWO_SEATS, seatingPlan.getParliamentaryGroupAtSeat(THREE));
    }

    /**
     * Test verifying that the first seat of the red group is certain if green has a simple group size.
     */
    @Test
    void firstRedSeatShouldBeCertainInForASimpleGroupSize() {
        RowConnectedSeatingPlan seatingPlan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup[] {RED_GROUP_WITH_TWO_SEATS, GREEN_GROUP_WITH_ONE_SEAT});
        assertEquals(SeatStatus.CERTAIN, seatingPlan.getSeatStatus(0));
    }

    /**
     * Test verifying that the first seat of the green group is certain if green is to the left.
     */
    @Test
    void firstGreenSeatShouldBeCertainInTheLeftHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup[] {GREEN_GROUP_WITH_ONE_TWO_THREE_SEATS});
        assertEquals(SeatStatus.CERTAIN, seatingPlan.getSeatStatus(0));
    }

    /**
     * Test verifying that the second seat of the green group is unlikely if green is to the left.
     */
    @Test
    void secondGreenSeatShouldBeUnlikelyInTheLeftHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, ONE_TWO_THREE_GREEN_SEATS_AND_ONE_BLUE);
        assertEquals(SeatStatus.UNLIKELY, seatingPlan.getSeatStatus(1));
    }

    /**
     * Test verifying that the third seat of the green group is likely if green is to the left.
     */
    @Test
    void thirdGreenSeatShouldBeLikelyInTheLeftHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, ONE_TWO_THREE_GREEN_SEATS_AND_ONE_BLUE);
        assertEquals(SeatStatus.LIKELY, seatingPlan.getSeatStatus(2));
    }

    /**
     * Test verifying that the last seat of the green group is certain if green is to the right.
     */
    @Test
    void lastGreenSeatShouldBeCertainInTheRightHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, ONE_BLUE_AND_ONE_TWO_THREE_GREEN_SEATS);
        assertEquals(SeatStatus.CERTAIN, seatingPlan.getSeatStatus(THREE));
    }

    /**
     * Test verifying that the second seat of the green group is likely if green is to the right.
     */
    @Test
    void secondGreenSeatShouldBeLikelyInTheRightHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, ONE_BLUE_AND_ONE_TWO_THREE_GREEN_SEATS);
        assertEquals(SeatStatus.LIKELY, seatingPlan.getSeatStatus(2));
    }

    /**
     * Test verifying that the third seat of the green group is unlikely if green is to the right.
     */
    @Test
    void firstGreenSeatShouldBeUnlikelyInTheRightHalfOfTheHemicycle() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, ONE_BLUE_AND_ONE_TWO_THREE_GREEN_SEATS);
        assertEquals(SeatStatus.UNLIKELY, seatingPlan.getSeatStatus(1));
    }

    /**
     * Test verifying that the seating plan knows that it doesn't have likely or unlikely seats registered.
     */
    @Test
    void hasLikelyOrUnlikelySeatsShouldReturnFalseForCertainSeatsOnly() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, TWO_RED_SEATS_AND_ONE_BLUE);
        assertFalse(seatingPlan.hasUncertainSeats());
    }

    /**
     * Test verifying that the seating plan knows that it doesn't have likely or unlikely seats registered even if
     * there's a differentiated group size that no likely or uncertain seats.
     */
    @Test
    void hasLikelyOrUnlikelySeatsShouldReturnFalseForCertainSeatsOnlyEvenWithADifferentiatedGroupSize() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, new ParliamentaryGroup[] {RED_GROUP_WITH_TWO_SEATS,
                        new ParliamentaryGroup(new DifferentiatedGroupSize(1, 1, 1), BLUE)});
        assertFalse(seatingPlan.hasUncertainSeats());
    }

    /**
     * Test verifying that the seating plan knows that it has likely or unlikely seats registered.
     */
    @Test
    void hasLikelyOrUnlikelySeatsShouldReturnTrueIfThereAreLikelyOrUnlikelySeats() {
        RowConnectedSeatingPlan seatingPlan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, ONE_BLUE_AND_ONE_TWO_THREE_GREEN_SEATS);
        assertTrue(seatingPlan.hasUncertainSeats());
    }
}
