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
     * An array holding the statuses of the seats. The elements in the array are calculated and set through lazy
     * initialization.
     */
    private SeatStatus[] seatStatuses;

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
     * Calculates whether the seats within a parliamentary group with differentiated size should be sorted
     * certain-likely-unlikely or unlikely-likely-certain.
     *
     * @param startIndex The seat number for the first seat of the parliamentary group.
     * @param size       The size of the parliamentary group.
     * @return True if the seats should be sorted as certain-likely-unlikely, and false otherwise.
     */
    private boolean calculateIfCertainSeatsArePositionedToTheLeft(final int startIndex,
            final DifferentiatedGroupSize size) {
        return startIndex * 2 + size.getFullSize() < numberOfSeats;
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
     * Calculates the status of a seat.
     *
     * @param seatNumber The number of the seat in the hemicycle.
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
     * Calculates the start index of a parliamentary group in the hemicycle.
     *
     * @param targetParliamentaryGroup The parliamentary group for which the start index has to be calculated.
     * @return The seat number of the first seat for the parliamentary group in the hemicycle.
     */
    private int calculateStartIndexOfParliamentaryGroup(final ParliamentaryGroup targetParliamentaryGroup) {
        int total = 0;
        for (ParliamentaryGroup parliamentaryGroup : parliamentaryGroups) {
            if (parliamentaryGroup.equals(targetParliamentaryGroup)) {
                return total;
            } else {
                total += parliamentaryGroup.getSize().getFullSize();
            }
        }
        return total;
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
        if (seatStatuses == null) {
            seatStatuses = new SeatStatus[getNumberOfSeats()];
        }
        if (seatStatuses[seatNumber] == null) {
            seatStatuses[seatNumber] = calculateSeatStatus(seatNumber);
        }
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
