package net.filipvanlaenen.shecc;

import net.filipvanlaenen.kolektoj.OrderedCollection;
import net.filipvanlaenen.kolektoj.SortedCollection;

/**
 * A class representing a seating plan where all parliamentary groups should have seats on connected rows. There may be
 * cases where the last parliamentary group doesn't have seats on connected rows. Note that even though the seats may be
 * on connected rows, the difference in angles from one row to another may be so large that the group as such isn't
 * really connected in the layout.
 */
public class RowConnectedSeatingPlan {
    /**
     * Whether or not the seating plan has likely or unlikely seats.
     */
    private final boolean hasUncertainSeats;
    /**
     * The total number of seats.
     */
    private final int numberOfSeats;
    /**
     * The parliamentary groups for this seating plan.
     */
    private final OrderedCollection<ParliamentaryGroup> parliamentaryGroups;
    private final SeatPosition[] seatPositions;
    /**
     * An array holding a pointer for each seat to the parliamentary group holding the seat.
     */
    private final ParliamentaryGroup[] seats;
    /**
     * An array holding the statuses of the seats.
     */
    private final SeatStatus[] seatStatuses;

    /**
     * Constructs a row connected seating plan based on a sorted collection of seat positions and an ordered list of
     * parliamentary groups.
     *
     * @param seatPositions       A sorted collection with the seat positions.
     * @param parliamentaryGroups The parliamentary groups to be seated.
     */
    public RowConnectedSeatingPlan(SortedCollection<SeatPosition> seatPositions,
            final ParliamentaryGroup... parliamentaryGroups) {
        this.parliamentaryGroups = OrderedCollection.of(parliamentaryGroups);
        numberOfSeats = calculateNumberOfSeats();
        this.seatPositions = seatPositions.toArray(new SeatPosition[numberOfSeats]);
        hasUncertainSeats = calculateHasUncertainSeats();
        seats = new ParliamentaryGroup[numberOfSeats];
        seatStatuses = new SeatStatus[numberOfSeats];
        calculateSeatsAndStatuses();
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

    private void calculateSeatsAndStatuses() {
        for (ParliamentaryGroup parliamentaryGroup : parliamentaryGroups) {
            GroupSize size = parliamentaryGroup.getSize();
            int fullSize = size.getFullSize();
            if (fullSize > 0) {
                int firstSeat = findFirstEmptySeat();
                seats[firstSeat] = parliamentaryGroup;
                int row = seatPositions[firstSeat].row();
                int lowRow = row;
                int highRow = row;
                if (size instanceof SimpleGroupSize) {
                    seatStatuses[firstSeat] = SeatStatus.CERTAIN;
                } else {
                    boolean certainSeatsToTheLeft = firstSeat * 2 + fullSize < numberOfSeats;
                    seatStatuses[firstSeat] = calculateSeatStatusWithinGroup(firstSeat, firstSeat,
                            (DifferentiatedGroupSize) size, certainSeatsToTheLeft);
                }
                for (int i = 1; i < fullSize; i++) {
                    int seatNumber = 0;
                    while (seatNumber < numberOfSeats
                            && (seats[seatNumber] != null || seatPositions[seatNumber].row() > highRow + 1
                                    || seatPositions[seatNumber].row() < lowRow - 1)) {
                        seatNumber++;
                    }
                    if (seatNumber == numberOfSeats) {
                        seatNumber = findFirstEmptySeat();
                    }
                    seats[seatNumber] = parliamentaryGroup;
                    row = seatPositions[seatNumber].row();
                    if (row < lowRow) {
                        lowRow = row;
                    }
                    if (row > highRow) {
                        highRow = row;
                    }
                    if (size instanceof SimpleGroupSize) {
                        seatStatuses[seatNumber] = SeatStatus.CERTAIN;
                    } else {
                        boolean certainSeatsToTheLeft = firstSeat * 2 + fullSize < numberOfSeats;
                        seatStatuses[seatNumber] = calculateSeatStatusWithinGroup(seatNumber, firstSeat,
                                (DifferentiatedGroupSize) size, certainSeatsToTheLeft);
                    }
                }
            }
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

    private int findFirstEmptySeat() {
        int seatNumber = 0;
        while (seats[seatNumber] != null) {
            seatNumber++;
        }
        return seatNumber;
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
