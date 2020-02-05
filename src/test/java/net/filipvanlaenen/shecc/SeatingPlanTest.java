package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>SeatingPlan</code> class.
 */
public class SeatingPlanTest {
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
    private static final ParliamentaryGroup GREEN_GROUP_WITH_ONE_TWO_THREE_SEATS = new ParliamentaryGroup(
            new DifferentiatedGroupSize(1, 2, 3), 0x0000FF);
    /**
     * A list with two red seats and one blue seat to run the tests on.
     */
    private static List<ParliamentaryGroup> twoRedSeatsAndOneBlue;
    /**
     * A list with one-two-three green seats and one blue seat to run the tests on.
     */
    private static List<ParliamentaryGroup> oneTwoThreeGreenSeatsAndOneBlue;
    /**
     * A list with one blue seat and one-two-three green seats to run the tests on.
     */
    private static List<ParliamentaryGroup> oneBlueAndOneTwoThreeGreenSeats;

    /**
     * Initialization method creating a list of groups with two red seats and one
     * blue.
     */
    @BeforeAll
    static void createListWithTwoRedSeatsAndOneBlueSeat() {
        twoRedSeatsAndOneBlue = new ArrayList<ParliamentaryGroup>();
        twoRedSeatsAndOneBlue.add(RED_GROUP_WITH_TWO_SEATS);
        twoRedSeatsAndOneBlue.add(BLUE_GROUP_WITH_ONE_SEAT);
    }

    /**
     * Initialization method creating a list of groups with one-two-three green
     * seats and one blue.
     */
    @BeforeAll
    static void createListWithOneTwoThreeGreenSeatsAndOneBlueSeat() {
        oneTwoThreeGreenSeatsAndOneBlue = new ArrayList<ParliamentaryGroup>();
        oneTwoThreeGreenSeatsAndOneBlue.add(GREEN_GROUP_WITH_ONE_TWO_THREE_SEATS);
        oneTwoThreeGreenSeatsAndOneBlue.add(BLUE_GROUP_WITH_ONE_SEAT);
    }

    /**
     * Initialization method creating a list of groups with one blue and
     * one-two-three green seats.
     */
    @BeforeAll
    static void createListWithOneBlueSeatAndOneTwoThreeGreenSeats() {
        oneBlueAndOneTwoThreeGreenSeats = new ArrayList<ParliamentaryGroup>();
        oneBlueAndOneTwoThreeGreenSeats.add(BLUE_GROUP_WITH_ONE_SEAT);
        oneBlueAndOneTwoThreeGreenSeats.add(GREEN_GROUP_WITH_ONE_TWO_THREE_SEATS);
    }

    /**
     * Test verifying that the list of parliamentary groups is wired correctly from
     * the constructor to the getter.
     */
    @Test
    void parliamentaryGroupsIsWiredCorrectlyFromTheConstructorToTheGetter() {
        SeatingPlan seatingPlan = new SeatingPlan(twoRedSeatsAndOneBlue);
        assertEquals(twoRedSeatsAndOneBlue, seatingPlan.getParliamentaryGroups());
    }

    /**
     * Test verifying that the number of seats is calculated correctly.
     */
    @Test
    void numberOfSeatsIsThreeForTwoRedSeatsAndOneBlueSeat() {
        SeatingPlan seatingPlan = new SeatingPlan(twoRedSeatsAndOneBlue);
        assertEquals(THREE, seatingPlan.getNoOfSeats());
    }

    /**
     * Test verifying that the red group holds the first seat.
     */
    @Test
    void firstSeatIsForTheRedGroupForTwoRedSeatsAndOneBlue() {
        SeatingPlan seatingPlan = new SeatingPlan(twoRedSeatsAndOneBlue);
        assertEquals(RED_GROUP_WITH_TWO_SEATS, seatingPlan.getParliamentaryGroupAtSeat(0));
    }

    /**
     * Test verifying that the blue group holds the third seat.
     */
    @Test
    void thirdSeatIsForTheBlueGroupForTwoRedSeatsAndOneBlue() {
        SeatingPlan seatingPlan = new SeatingPlan(twoRedSeatsAndOneBlue);
        assertEquals(BLUE_GROUP_WITH_ONE_SEAT, seatingPlan.getParliamentaryGroupAtSeat(2));
    }

    /**
     * Test verifying that the first seat of the green group is certain if green is
     * to the left.
     */
    @Test
    void firstGreenSeatIsCertainInTheLeftHalfOfTheHemicycle() {
        SeatingPlan seatingPlan = new SeatingPlan(oneTwoThreeGreenSeatsAndOneBlue);
        assertEquals(SeatStatus.CERTAIN, seatingPlan.getSeatStatus(0));
    }

    /**
     * Test verifying that the second seat of the green group is likely if green is
     * to the left.
     */
    @Test
    void secondGreenSeatIsLikelyInTheLeftHalfOfTheHemicycle() {
        SeatingPlan seatingPlan = new SeatingPlan(oneTwoThreeGreenSeatsAndOneBlue);
        assertEquals(SeatStatus.LIKELY, seatingPlan.getSeatStatus(1));
    }

    /**
     * Test verifying that the third seat of the green group is unlikely if green is
     * to the left.
     */
    @Test
    void thirdGreenSeatIsUnlikelyInTheLeftHalfOfTheHemicycle() {
        SeatingPlan seatingPlan = new SeatingPlan(oneTwoThreeGreenSeatsAndOneBlue);
        assertEquals(SeatStatus.UNLIKELY, seatingPlan.getSeatStatus(2));
    }

    /**
     * Test verifying that the last seat of the green group is certain if green is
     * to the right.
     */
    @Test
    void lastGreenSeatIsCertainInTheRightHalfOfTheHemicycle() {
        SeatingPlan seatingPlan = new SeatingPlan(oneBlueAndOneTwoThreeGreenSeats);
        assertEquals(SeatStatus.CERTAIN, seatingPlan.getSeatStatus(THREE));
    }

    /**
     * Test verifying that the second seat of the green group is likely if green is
     * to the right.
     */
    @Test
    void secondGreenSeatIsLikelyInTheRightHalfOfTheHemicycle() {
        SeatingPlan seatingPlan = new SeatingPlan(oneBlueAndOneTwoThreeGreenSeats);
        assertEquals(SeatStatus.LIKELY, seatingPlan.getSeatStatus(2));
    }

    /**
     * Test verifying that the third seat of the green group is unlikely if green is
     * to the right.
     */
    @Test
    void firstGreenSeatIsUnlikelyInTheRightHalfOfTheHemicycle() {
        SeatingPlan seatingPlan = new SeatingPlan(oneBlueAndOneTwoThreeGreenSeats);
        assertEquals(SeatStatus.UNLIKELY, seatingPlan.getSeatStatus(1));
    }

    /**
     * Test verifying that the seating plan knows that it doesn't have likely or unlikely
     * seats registered.
     */
    @Test
    void hasLikelyOrUnlikelySeatsReturnsFalseForCertainSeatsOnly() {
        SeatingPlan seatingPlan = new SeatingPlan(twoRedSeatsAndOneBlue);
        assertFalse(seatingPlan.hasUncertainSeats());
    }
    
    /**
     * Test verifying that the seating plan knows that it has likely or unlikely
     * seats registered.
     */
    @Test
    void hasLikelyOrUnlikelySeatsReturnsTrueIfThereAreLikelyOrUnlikelySeats() {
        SeatingPlan seatingPlan = new SeatingPlan(oneBlueAndOneTwoThreeGreenSeats);
        assertTrue(seatingPlan.hasUncertainSeats());
    }
}
