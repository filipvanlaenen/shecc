package net.filipvanlaenen.shecc;

import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * A class representing a seating plan.
 */
public class SeatingPlan {
    /**
     * The parliamentary groups for this seating plan.
     */
    private final OrderedCollection<ParliamentaryGroup> parliamentaryGroups;
    /**
     * The total number of seats.
     */
    private final int numberOfSeats;
    /**
     * Whether or not the seating plan has likely or unlikely seats.
     */
    private final boolean hasUncertainSeats;
    /**
     * An array holding a pointer for each seat to the parliamentary group holding the seat.
     */
    private final ParliamentaryGroup[] seats;
    /**
     * An array holding the statuses of the seats.
     */
    private final SeatStatus[] seatStatuses;

    /**
     * Constructs a seating plan based on an ordered list of parliamentary groups.
     *
     * @param parliamentaryGroups The parliamentary groups to be seated.
     */
    public SeatingPlan(final ParliamentaryGroup... parliamentaryGroups) {
        this.parliamentaryGroups = OrderedCollection.of(parliamentaryGroups);
        numberOfSeats = calculateNumberOfSeats();
        hasUncertainSeats = calculateHasUncertainSeats();
        seats = calculateSeats();
        seatStatuses = calculateSeatStatuses();
    }

    /**
     * Calculates whether any of the parliamentary groups has a likely or unlikely seat.
     *
     * @return True if at least one of the parliamentary groups has a likely or unlikely seat.
     */
    private boolean calculateHasUncertainSeats() {
        for (ParliamentaryGroup parliamentaryGroup : parliamentaryGroups) {
            if (parliamentaryGroup.getSize() instanceof DifferentiatedGroupSize) {
                DifferentiatedGroupSize size = (DifferentiatedGroupSize) parliamentaryGroup.getSize();
                if (size.getFullSize() > size.lowerBound()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculates the total number of seats for all parliamentary groups together.
     *
     * @return The total number of seats.
     */
    private int calculateNumberOfSeats() {
        int total = 0;
        for (ParliamentaryGroup parliamentaryGroup : parliamentaryGroups) {
            total += parliamentaryGroup.getSize().getFullSize();
        }
        return total;
    }

    /**
     * Calculates the parliamentary group for each seat.
     *
     * @return An array with the parliamentary group for each seat.
     */
    private ParliamentaryGroup[] calculateSeats() {
        ParliamentaryGroup[] result = new ParliamentaryGroup[numberOfSeats];
        int numberOfSeatsSoFar = 0;
        for (ParliamentaryGroup parliamentaryGroup : parliamentaryGroups) {
            int fullSize = parliamentaryGroup.getSize().getFullSize();
            for (int seatNumber = numberOfSeatsSoFar; seatNumber < numberOfSeatsSoFar + fullSize; seatNumber++) {
                result[seatNumber] = parliamentaryGroup;
            }
            numberOfSeatsSoFar += fullSize;
        }
        return result;
    }

    /**
     * Calculates the seat status for each seat.
     *
     * @return An array with the status for each seat.
     */
    private SeatStatus[] calculateSeatStatuses() {
        SeatStatus[] result = new SeatStatus[numberOfSeats];
        int numberOfSeatsSoFar = 0;
        for (ParliamentaryGroup parliamentaryGroup : parliamentaryGroups) {
            GroupSize size = parliamentaryGroup.getSize();
            int fullSize = size.getFullSize();
            for (int seatNumber = numberOfSeatsSoFar; seatNumber < numberOfSeatsSoFar + fullSize; seatNumber++) {
                if (size instanceof SimpleGroupSize) {
                    result[seatNumber] = SeatStatus.CERTAIN;
                } else {
                    boolean certainSeatsToTheLeft = numberOfSeatsSoFar * 2 + fullSize < numberOfSeats;
                    result[seatNumber] = calculateSeatStatusWithinGroup(seatNumber, numberOfSeatsSoFar,
                            (DifferentiatedGroupSize) size, certainSeatsToTheLeft);
                }
            }
            numberOfSeatsSoFar += fullSize;
        }
        return result;
    }

    /**
     * Calculates the status of a seat within a group.
     *
     * @param seatNumber            The number of the seat for which the status has to be calculated.
     * @param startIndex            The seat number for the first seat of the parliamentary group.
     * @param size                  The size of the parliamentary group.
     * @param certainSeatsToTheLeft Whether the certain seats should be placed to the left, meaning that the ordering is
     *                              certain-likely-unlikely.
     * @return The seat's status.
     */
    private SeatStatus calculateSeatStatusWithinGroup(final int seatNumber, final int startIndex,
            final DifferentiatedGroupSize size, final boolean certainSeatsToTheLeft) {
        if (certainSeatsToTheLeft) {
            if (startIndex + size.lowerBound() > seatNumber) {
                return SeatStatus.CERTAIN;
            } else if (startIndex + size.median() > seatNumber) {
                return SeatStatus.LIKELY;
            } else {
                return SeatStatus.UNLIKELY;
            }
        } else {
            if (startIndex + size.getFullSize() - size.lowerBound() <= seatNumber) {
                return SeatStatus.CERTAIN;
            } else if (startIndex + size.getFullSize() - size.median() <= seatNumber) {
                return SeatStatus.LIKELY;
            } else {
                return SeatStatus.UNLIKELY;
            }
        }
    }

    /**
     * Returns the total number of seats for all parliamentary groups together.
     *
     * @return The total number of seats.
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Returns the parliamentary group occupying a given seat.
     *
     * @param seatNumber The number of the seat.
     * @return The parliamentary group sitting at the request seat number.
     */
    public ParliamentaryGroup getParliamentaryGroupAtSeat(final int seatNumber) {
        return seats[seatNumber];
    }

    /**
     * Returns an ordered collection with the parliamentary groups.
     *
     * @return An ordered collection with the parliamentary groups
     */
    public OrderedCollection<ParliamentaryGroup> getParliamentaryGroups() {
        return parliamentaryGroups;
    }

    /**
     * Returns the status of a seat.
     *
     * @param seatNumber The number of the seat in the hemicycle.
     * @return The seat's status.
     */
    public SeatStatus getSeatStatus(final int seatNumber) {
        return seatStatuses[seatNumber];
    }

    /**
     * Returns whether any of the parliamentary groups has a likely or unlikely seat.
     *
     * @return True if at least one of the parliamentary groups has a likely or unlikely seat.
     */
    public boolean hasUncertainSeats() {
        return hasUncertainSeats;
    }
}
