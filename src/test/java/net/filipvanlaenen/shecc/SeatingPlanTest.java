package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
     * A list with two red seats and one blue seat to run the tests on.
     */
    private static List<ParliamentaryGroup> twoRedSeatsAndOneBlue;

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
}
