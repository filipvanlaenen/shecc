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
     * An array holding the statuses of the seats. The elements in the array are
     * calculated and set through lazy initialization.
     */
    private SeatStatus[] seatStatuses;

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
     * Returns an unmodifiable copy of the list of the parliamentary groups.
     *
     * @return An unmodifiable copy of the list of the parliamentary groups
     */
    public List<ParliamentaryGroup> getParliamentaryGroups() {
        return parliamentaryGroups;
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
            total += iterator.next().getSize().getFullSize();
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
            total += group.getSize().getFullSize();
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

    /**
     * Returns the status of a seat.
     *
     * @param seatNumber
     *            The number of the seat in the hemicycle.
     * @return The seat's status.
     */
    public SeatStatus getSeatStatus(final int seatNumber) {
        if (seatStatuses == null) {
            seatStatuses = new SeatStatus[getNoOfSeats()];
        }
        if (seatStatuses[seatNumber] == null) {
            seatStatuses[seatNumber] = calculateSeatStatus(seatNumber);
        }
        return seatStatuses[seatNumber];
    }

    /**
     * Calculates the status of a seat.
     *
     * @param seatNumber
     *            The number of the seat in the hemicycle.
     * @return The seat's status.
     */
    private SeatStatus calculateSeatStatus(final int seatNumber) {
        ParliamentaryGroup group = getParliamentaryGroupAtSeat(seatNumber);
        if (group.getSize() instanceof SimpleGroupSize) {
            return SeatStatus.CERTAIN;
        } else {
            int startIndex = calculateStartIndexOfParliamentaryGroup(group);
            DifferentiatedGroupSize size = (DifferentiatedGroupSize) group.getSize();
            boolean certainSeatsToTheLeft = calculateIfCertainSeatsArePositionedToTheLeft(startIndex, size);
            return calculateSeatStatusWithinGroup(seatNumber, startIndex, size, certainSeatsToTheLeft);
        }
    }

    /**
     * Calculates the status of a seat within a group.
     *
     * @param seatNumber
     *            The number of the seat for which the status has to be calculated.
     * @param startIndex
     *            The seat number for the first seat of the parliamentary group.
     * @param size
     *            The size of the parliamentary group.
     * @param certainSeatsToTheLeft
     *            Whether the certain seats should be placed to the left, meaning
     *            that the ordering is certain-likely-unlikely.
     * @return The seat's status.
     */
    private SeatStatus calculateSeatStatusWithinGroup(final int seatNumber, final int startIndex,
            final DifferentiatedGroupSize size, final boolean certainSeatsToTheLeft) {
        if (certainSeatsToTheLeft) {
            if (startIndex + size.getLowerBound() > seatNumber) {
                return SeatStatus.CERTAIN;
            } else if (startIndex + size.getMedian() > seatNumber) {
                return SeatStatus.LIKELY;
            } else {
                return SeatStatus.UNLIKELY;
            }
        } else {
            if (startIndex + size.getFullSize() - size.getLowerBound() <= seatNumber) {
                return SeatStatus.CERTAIN;
            } else if (startIndex + size.getFullSize() - size.getMedian() <= seatNumber) {
                return SeatStatus.LIKELY;
            } else {
                return SeatStatus.UNLIKELY;
            }
        }
    }

    /**
     * Calculates whether the seats within a parliamentary group with differentiated
     * size should be sorted certain-likely-unlikely or unlikely-likely-certain.
     *
     * @param startIndex
     *            The seat number for the first seat of the parliamentary group.
     * @param size
     *            The size of the parliamentary group.
     * @return True if the seats should be sorted as certain-likely-unlikely, and
     *         false otherwise.
     */
    private boolean calculateIfCertainSeatsArePositionedToTheLeft(final int startIndex,
            final DifferentiatedGroupSize size) {
        return startIndex + size.getFullSize() < noOfSeats;
    }

    /**
     * Calculates the start index of a parliamentary group in the hemicycle.
     *
     * @param group
     *            The parliamentary group for which the start index has to be
     *            calculated.
     * @return The seat number of the first seat for the parliamentary group in the
     *         hemicycle.
     */
    private int calculateStartIndexOfParliamentaryGroup(final ParliamentaryGroup group) {
        Iterator<ParliamentaryGroup> iterator = parliamentaryGroups.iterator();
        int total = 0;
        boolean groupReached = false;
        while (!groupReached && iterator.hasNext()) {
            ParliamentaryGroup currentGroup = iterator.next();
            if (currentGroup.equals(group)) {
                groupReached = true;
            } else {
                total += currentGroup.getSize().getFullSize();
            }
        }
        return total;
    }

    public boolean hasLikelyOrUnlikelySeats() {
        Iterator<ParliamentaryGroup> iterator = parliamentaryGroups.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getSize() instanceof DifferentiatedGroupSize) {
                return true;
            }
        }
        return false;
    }

}
