package net.filipvanlaenen.shecc;

import net.filipvanlaenen.kolektoj.SortedCollection;

/**
 *
 * Class defining the layout of a hemicycle. The layout is defined by the angle of the hemicycle, the ratio between the
 * inner and the outer radius, and the number of mandates. By default, the angle is half a circle (π rad or 180°), and
 * the ratio is two thirds.
 *
 */
public class HemicycleLayout {
    /**
     * The default value for the angle of the hemicycle, set to π (180°).
     */
    private static final double DEFAULT_ANGLE = Math.PI;
    /**
     * The maximal value for the angle of the hemicycle, set to 2π (360°).
     */
    private static final double MAX_ANGLE = Math.PI * 2D;
    /**
     * The minimal number of seats included in the calculation of the scaling angle.
     */
    private static final int MINIMAL_NUMBER_OF_SEATS_FOR_SCALING_ANGLE = 5;
    /**
     * The maximal number of seats included in the calculation of the scaling angle.
     */
    private static final int MAXIMAL_NUMBER_OF_SEATS_FOR_SCALING_ANGLE = 50;
    /**
     * The default ratio between the inner and the outer radius for the hemicycle, set to a third.
     */
    private static final double DEFAULT_RADIUS_RATIO = 1.0D / 3.0D;
    /**
     * The magic number one half.
     */
    private static final double ONE_HALF = 0.5D;

    /**
     * The angle for the hemicycle.
     */
    private final double angle;
    /**
     * The number of rows. This field is calculated and set through lazy initialization.
     */
    private int numberOfRows;
    /**
     * The number of seats in the hemicycle.
     */
    private final int numberOfSeats;
    /**
     * The ratio between the inner and the outer radius of the hemicycle.
     */
    private final double radiusRatio;
    /**
     * The sorted collection with seat positions. This field is calculated and set through lazy initialization.
     */
    private SortedCollection<SeatPosition> seatPositions;

    /**
     * Constructs a hemicycle layout with a number of seats only.
     *
     * @param noOfSeats The number of seats in the hemicycle.
     */
    public HemicycleLayout(final int noOfSeats) {
        this(noOfSeats, null);
    }

    /**
     * Constructs a hemicycle layout with a number of seats and an angle.
     *
     * @param noOfSeats The number of seats in the hemicycle.
     * @param angle     The angle for the hemicycle.
     */
    public HemicycleLayout(final int noOfSeats, final Double angle) {
        this(noOfSeats, angle, DEFAULT_RADIUS_RATIO);
    }

    /**
     * Constructs a hemicycle layout with a number of seats and an angle.
     *
     * @param noOfSeats   The number of seats in the hemicycle.
     * @param angle       The angle for the hemicycle.
     * @param radiusRatio The ratio between the inner and the outer radius of the hemicycle.
     */
    HemicycleLayout(final int noOfSeats, final Double angle, final double radiusRatio) {
        if (noOfSeats <= 0) {
            throw new IllegalArgumentException("The number of seats in a hemicycle should be strictly positive.");
        }
        this.numberOfSeats = noOfSeats;
        if (angle == null) {
            double scalingAngle = DEFAULT_ANGLE * Math.max(MINIMAL_NUMBER_OF_SEATS_FOR_SCALING_ANGLE, noOfSeats)
                    / MAXIMAL_NUMBER_OF_SEATS_FOR_SCALING_ANGLE;
            this.angle = Math.min(DEFAULT_ANGLE, scalingAngle);
        } else {
            if (angle <= 0.0D || angle > MAX_ANGLE) {
                throw new IllegalArgumentException(
                        "The angle of an hemicycle should be strictly positive but not greater than 2π.");
            }
            this.angle = angle;
        }
        if (radiusRatio <= 0.0D || radiusRatio >= 1.0D) {
            throw new IllegalArgumentException(
                    "The ratio between the inner and the outer radius of an hemicycle should be strictly between 0 "
                            + "and 1.");
        }
        this.radiusRatio = radiusRatio;
    }

    /**
     * Calculates the number of rows for the optimal distribution of seats for the hemicycle layout.
     *
     * @return The number of rows.
     */
    private int calculateNumberOfRows() {
        int n = 0;
        while (true) {
            n += 1;
            double rowWidth = (1.0D - radiusRatio) / n;
            int maximumNumberOfSeats = 0;
            for (int row = 1; row <= n; row++) {
                double rowRadius = radiusRatio + ((double) row - ONE_HALF) * rowWidth;
                maximumNumberOfSeats += Math.ceil(angle * rowRadius / rowWidth);
            }
            if (maximumNumberOfSeats >= numberOfSeats) {
                return n;
            }
        }
    }

    /**
     * Calculates the number of seats for each row.
     *
     * The seats are distributed across the rows such that the sections that a seat occupies on a row are as low as
     * possible.
     *
     * @return An array with the number of seats for each row.
     */
    private int[] calculateNumberOfSeatsPerRow() {
        int thisNoOfRows = getNumberOfRows();
        double width = (1.0D - radiusRatio) / thisNoOfRows;
        double[] rowRadii = new double[thisNoOfRows];
        double[] nextSeatArc = new double[thisNoOfRows];
        for (int row = 1; row <= thisNoOfRows; row++) {
            rowRadii[row - 1] = radiusRatio + ((double) row - ONE_HALF) * width;
            nextSeatArc[row - 1] = rowRadii[row - 1] * 2D;
        }
        int[] seats = new int[thisNoOfRows];
        for (int seat = 1; seat <= numberOfSeats; seat++) {
            int bestRow = 1;
            double highestQuote = nextSeatArc[0];
            for (int row = 2; row <= thisNoOfRows; row++) {
                // EQMU: Changing the conditional boundary below produces an equivalent mutant.
                if (nextSeatArc[row - 1] > highestQuote) {
                    bestRow = row;
                    highestQuote = nextSeatArc[row - 1];
                }
            }
            seats[bestRow - 1] += 1;
            nextSeatArc[bestRow - 1] = rowRadii[bestRow - 1] / seats[bestRow - 1];
        }
        return seats;
    }

    /**
     * Calculates a sorted collection with the seat positions.
     *
     *
     * @return A sorted collection with the seat positions.
     */
    private SortedCollection<SeatPosition> calculateSeatPositions() {
        SeatPosition[] seatPositionArray = new SeatPosition[numberOfSeats];
        int thisNoOfRows = getNumberOfRows();
        double rowWidth = getRowWidth();
        int[] numberOfSeatsOnRow = calculateNumberOfSeatsPerRow();
        int seatNumber = 0;
        double firstSeatAngle = (Math.PI - angle) / 2D;
        for (int row = 1; row <= thisNoOfRows; row++) {
            double rowRadius = radiusRatio + ((double) row - ONE_HALF) * rowWidth;
            int numberOfSeatsOnThisRow = numberOfSeatsOnRow[row - 1];
            if (numberOfSeatsOnThisRow == 1) {
                seatPositionArray[seatNumber++] = new SeatPosition(rowRadius, Math.PI / 2D);
            } else {
                double anglePerSeat = angle / (numberOfSeatsOnThisRow - 1);
                for (int seat = 0; seat < numberOfSeatsOnThisRow; seat++) {
                    double seatAngle = firstSeatAngle + anglePerSeat * seat;
                    if (seatAngle < 0D) {
                        // EQMU: Replacing double addition with subtraction below produces an equivalent mutant.
                        seatAngle += Math.PI * 2D;
                    }
                    seatPositionArray[seatNumber++] = new SeatPosition(rowRadius, seatAngle);
                }
            }
        }
        return SortedCollection.of(new SeatPositionInHemicycleComparator(), seatPositionArray);
    }

    /**
     * Returns the angle of the hemicycle.
     *
     * @return The angle of the hemicycle.
     */
    double getAngle() {
        return angle;
    }

    /**
     * Returns the total height of the hemicycle layout. For the calculation, the hemicycle is extended with a block
     * half the width of a row, and both the height at the inner and the outer edge are calculated.
     *
     * @return The total height.
     */
    public double getHeight() {
        double extensionHeight = getRowWidth() * Math.sin(angle / 2D) / 2D;
        double outerHeight = 1D - Math.cos(angle / 2D) + extensionHeight;
        double innerHeight = 1D - radiusRatio * Math.cos(angle / 2D) + extensionHeight;
        return Math.max(outerHeight, innerHeight);
    }

    /**
     * Returns the number of rows for the optimal distribution of seats for the hemicycle layout.
     *
     * @return The number of rows.
     */
    public int getNumberOfRows() {
        if (numberOfRows == 0) {
            numberOfRows = calculateNumberOfRows();
        }
        return numberOfRows;
    }

    /**
     * Returns the ratio between the inner and the outer radius of the hemicycle.
     *
     * @return The ratio between the inner and the outer radius of the hemicycle.
     */
    double getRadiusRatio() {
        return radiusRatio;
    }

    /**
     * Returns the row width.
     *
     * @return The row width.
     */
    public double getRowWidth() {
        return (1D - radiusRatio) / getNumberOfRows();
    }

    /**
     * Returns the seat position at index i.
     *
     * @param i The index of the requested seat position
     * @return The seat position at index i.
     */
    public SeatPosition getSeatPosition(final int i) {
        return getSeatPositions().getAt(i);
    }

    /**
     * Returns a sorted collection with the seat positions.
     *
     * @return A sorted collection with the seat positions.
     */
    public SortedCollection<SeatPosition> getSeatPositions() {
        if (seatPositions == null) {
            seatPositions = calculateSeatPositions();
        }
        return seatPositions;
    }

    /**
     * Returns the total width of the hemicycle layout. For the calculation, the hemicycle is extended with a block half
     * the width of a row.
     *
     * @return The total width.
     */
    public double getWidth() {
        // EQMU: Changing the conditional boundary below produces an equivalent mutant.
        return angle < Math.PI ? 2D * Math.sin(angle / 2D) + getRowWidth() * Math.cos(angle / 2D) : 2D;
    }
}
