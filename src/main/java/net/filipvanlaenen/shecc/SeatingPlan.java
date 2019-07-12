package net.filipvanlaenen.shecc;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A class representing a seating plan.
 */
public class SeatingPlan {
    /**
     * The parliamentary groups for this seating plan.
     */
    private final List<ParliamentaryGroup> parliamentaryGroups;
    /**
     * The total number of seats. This field is calculated and set through lazy
     * initialization.
     */
    private int noOfSeats;

    /**
     * An array holding a pointer for each seat to the parliamentary group holding
     * the seat. The elements in the array are calculated and set through lazy
     * initialization.
     */
    private ParliamentaryGroup[] seats;

    /**
     * Constructs a seating plan based on an ordered list of parliamentary groups.
     *
     * @param parliamentaryGroups
     *            The parliamentary groups to be seated.
     */
    public SeatingPlan(final List<ParliamentaryGroup> parliamentaryGroups) {
        this.parliamentaryGroups = Collections.unmodifiableList(parliamentaryGroups);
    }

    /**
     * Calculates the total number of seats for all parliamentary groups together.
     *
     * @return The total number of seats.
     */
    private int calculateNoOfSeats() {
        Iterator<ParliamentaryGroup> iterator = parliamentaryGroups.iterator();
        int total = 0;
        while (iterator.hasNext()) {
            total += iterator.next().getSize();
        }
        return total;
    }

    /**
     * Returns the total number of seats for all parliamentary groups together.
     *
     * @return The total number of seats.
     */
    public int getNoOfSeats() {
        if (noOfSeats == 0) {
            noOfSeats = calculateNoOfSeats();
        }
        return noOfSeats;
    }

    /**
     * Calculates the parliamentary group occupying a given seat.
     *
     * @param seatNumber
     *            The number of the seat.
     * @return The parliamentary group sitting at the request seat number.
     */
    private ParliamentaryGroup calculateParliamentaryGroupAtSeat(final int seatNumber) {
        Iterator<ParliamentaryGroup> iterator = parliamentaryGroups.iterator();
        int total = 0;
        while (iterator.hasNext()) {
            ParliamentaryGroup group = iterator.next();
            total += group.getSize();
            if (total > seatNumber) {
                return group;
            }
        }
        return null;
    }

    /**
     * Returns the parliamentary group occupying a given seat.
     *
     * @param seatNumber
     *            The number of the seat.
     * @return The parliamentary group sitting at the request seat number.
     */
    public ParliamentaryGroup getParliamentaryGroupAtSeat(final int seatNumber) {
        if (seats == null) {
            seats = new ParliamentaryGroup[getNoOfSeats()];
        }
        if (seats[seatNumber] == null) {
            seats[seatNumber] = calculateParliamentaryGroupAtSeat(seatNumber);
        }
        return seats[seatNumber];
    }

}
