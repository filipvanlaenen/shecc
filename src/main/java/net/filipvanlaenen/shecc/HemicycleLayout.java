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
     * The default ratio between the inner and the outer radius for the hemicycle, set to a third.
     */
    private static final double DEFAULT_RADIUS_RATIO = 1.0D / 3.0D;

    /**
     * The magic number one half.
     */
    private static final double ONE_HALF = 0.5D;

    /**
     * The number of seats in the hemicycle.
     */
    private final int noOfSeats;

    /**
     * The angle for the hemicycle.
     */
    private final double angle;

    /**
     * The ratio between the inner and the outer radius of the hemicycle.
     */
    private final double radiusRatio;

    /**
     * The number of rows. This field is calculated and set through lazy initialization.
     */
    private int noOfRows;

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
        this(noOfSeats, DEFAULT_ANGLE);
    }

    /**
     * Constructs a hemicycle layout with a number of seats and an angle.
     *
     * @param noOfSeats The number of seats in the hemicycle.
     * @param angle     The angle for the hemicycle.
     */
    public HemicycleLayout(final int noOfSeats, final double angle) {
        this(noOfSeats, angle, DEFAULT_RADIUS_RATIO);
    }

    /**
     * Constructs a hemicycle layout with a number of seats and an angle.
     *
     * @param noOfSeats   The number of seats in the hemicycle.
     * @param angle       The angle for the hemicycle.
     * @param radiusRatio The ratio between the inner and the outer radius of the hemicycle.
     */
    HemicycleLayout(final int noOfSeats, final double angle, final double radiusRatio) {
        if (noOfSeats <= 0) {
            throw new IllegalArgumentException("The number of seats in a hemicycle should be strictly positive.");
        }
        this.noOfSeats = noOfSeats;
        if (angle <= 0.0D || angle > MAX_ANGLE) {
            throw new IllegalArgumentException(
                    "The angle of an hemicycle should be strictly positive but not greater than 2π.");
        }
        this.angle = angle;
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
    int calculateNumberOfRows() {
        int n = 0;
        while (true) {
            n += 1;
            double width = (1.0D - radiusRatio) / n;
            int s = 0;
            for (int row = 1; row <= n; row++) {
                double rowRadius = radiusRatio + ((double) row - ONE_HALF) * width;
                s += (int) (angle * rowRadius / width);
            }
            if (s >= noOfSeats) {
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
     * @param rowLengths
     * @return
     */
    private int[] calculateNumberOfSeatsPerRow() {
        int thisNoOfRows = getNoOfRows();
        double width = (1.0D - radiusRatio) / thisNoOfRows;
        double[] rowRadii = new double[thisNoOfRows];
        double[] quote = new double[thisNoOfRows];
        for (int row = 1; row <= thisNoOfRows; row++) {
            rowRadii[row - 1] = radiusRatio + ((double) row - ONE_HALF) * width;
            quote[row - 1] = rowRadii[row - 1];
        }
        int[] seats = new int[thisNoOfRows];
        for (int seat = 1; seat <= noOfSeats; seat++) {
            int row = 0;
            double highestQuote = quote[0];
            for (int i = 1; i < thisNoOfRows; i++) {
                if (quote[i] > highestQuote) {
                    row = i;
                    highestQuote = quote[i];
                }
            }
            seats[row] += 1;
            quote[row] = rowRadii[row] / (seats[row] + 1);
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
        SeatPosition[] seatPositionArray = new SeatPosition[noOfSeats];
        int thisNoOfRows = getNoOfRows();
        double rowWidth = (1.0D - radiusRatio) / thisNoOfRows;
        int[] numberOfSeatsOnRow = calculateNumberOfSeatsPerRow();
        int seatNumber = 0;
        for (int row = 1; row <= thisNoOfRows; row++) {
            double rowRadius = radiusRatio + ((double) row - ONE_HALF) * rowWidth;
            double anglePerSeat = angle / numberOfSeatsOnRow[row - 1];
            for (int seat = 0; seat < numberOfSeatsOnRow[row - 1]; seat++) {
                double seatAngle = Math.PI / 2D + anglePerSeat * (seat + (1D - numberOfSeatsOnRow[row - 1]) / 2D);
                if (seatAngle < 0D) {
                    seatAngle += Math.PI * 2D;
                }
                seatPositionArray[seatNumber++] = new SeatPosition(rowRadius, seatAngle);
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
     * Returns the total height of the hemicycle layout. If the angle of the hemicycle is π or less, the height is one.
     * Otherwise. the height is one plus the sine of half the angle minus π (1 + sin((α-π)/2)).
     *
     * @return The total height.
     */
    public double getHeight() {
        return getAngle() < Math.PI ? 1D : 1D + Math.sin((getAngle() - Math.PI) / 2D);
    }

    /**
     * Returns the number of rows for the optimal distribution of seats for the hemicycle layout.
     *
     * @return The number of rows.
     */
    public int getNoOfRows() {
        if (noOfRows == 0) {
            noOfRows = calculateNumberOfRows();
        }
        return noOfRows;
    }

    /**
     * Returns the number of seats in the hemicycle.
     *
     * @return The number of seats in the hemicycle.
     */
    int getNoOfSeats() {
        return noOfSeats;
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
        return (1D - getRadiusRatio()) / getNoOfRows();
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
     * Returns the total width of the hemicycle layout. If the angle of the hemicycle is π or more, the width is two.
     * Otherwise, the width is twice the sine of half the angle (2*sin(α/2)).
     *
     * @return The total width.
     */
    public double getWidth() {
        return getAngle() < Math.PI ? 2D * Math.sin(getAngle() / 2D) : 2D;
    }
}
