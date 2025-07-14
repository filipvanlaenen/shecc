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
    /**
     * An array with the seat positions.
     */
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
    public RowConnectedSeatingPlan(final SortedCollection<SeatPosition> seatPositions,
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

    /**
     * Calculates all the seats and the seat statuses.
     */
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
                seatStatuses[firstSeat] = calculateSeatStatusWithinGroup(0, firstSeat, size);
                for (int seatIndex = 1; seatIndex < fullSize; seatIndex++) {
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
                    // EQMU: Changing the conditional boundary below produces an equivalent mutant.
                    if (row < lowRow) {
                        lowRow = row;
                    }
                    // EQMU: Changing the conditional boundary below produces an equivalent mutant.
                    if (row > highRow) {
                        highRow = row;
                    }
                    seatStatuses[seatNumber] = calculateSeatStatusWithinGroup(seatIndex, firstSeat, size);
                }
            }
        }
    }

    /**
     * Calculates the status of a seat within a group.
     *
     * @param seatIndex  The index (in the parliamentary group) of the seat for which the status has to be calculated.
     * @param startIndex The seat number for the first seat of the parliamentary group.
     * @param size       The size of the parliamentary group.
     *
     * @return The seat's status.
     */
    private SeatStatus calculateSeatStatusWithinGroup(final int seatIndex, final int startIndex, final GroupSize size) {
        if (size instanceof SimpleGroupSize) {
            return SeatStatus.CERTAIN;
        } else {
            return calculateSeatStatusWithinGroup(seatIndex, startIndex, (DifferentiatedGroupSize) size);
        }
    }

    /**
     * Calculates the status of a seat within a group.
     *
     * @param seatIndex  The index (in the parliamentary group) of the seat for which the status has to be calculated.
     * @param startIndex The seat number for the first seat of the parliamentary group.
     * @param size       The size of the parliamentary group.
     *
     * @return The seat's status.
     */
    private SeatStatus calculateSeatStatusWithinGroup(final int seatIndex, final int startIndex,
            final DifferentiatedGroupSize size) {
        boolean certainSeatsToTheLeft = startIndex * 2 + size.getFullSize() <= numberOfSeats;
        if (certainSeatsToTheLeft) {
            if (seatIndex < size.lowerBound()) {
                return SeatStatus.CERTAIN;
            } else if (seatIndex < size.median()) {
                return SeatStatus.LIKELY;
            } else {
                return SeatStatus.UNLIKELY;
            }
        } else {
            if (size.getFullSize() - seatIndex <= size.lowerBound()) {
                return SeatStatus.CERTAIN;
            } else if (size.getFullSize() - seatIndex <= size.median()) {
                return SeatStatus.LIKELY;
            } else {
                return SeatStatus.UNLIKELY;
            }
        }
    }

    /**
     * Finds the first seat that's empty.
     *
     * @return The seat number of the first empty seat.
     */
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
