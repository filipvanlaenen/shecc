package net.filipvanlaenen.shecc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Class defining the layout of a hemicycle. The layout is defined by the angle
 * of the hemicycle, the ratio between the inner and the outer radius, and the
 * number of mandates. By default, the angle is half a circle (π rad or 180°),
 * and the ratio is two thirds.
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
     * The default ratio between the inner and the outer radius for the hemicycle,
     * set to a third.
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
     * The number of rows. This field is calculated and set through lazy
     * initialization.
     */
    private int noOfRows;

    /**
     * The list with seat positions, sorted. This field is calculated and set
     * through lazy initialization.
     */
    private List<SeatPosition> seatPositionList;

    /**
     * Constructs a hemicycle layout with a number of seats only.
     *
     * @param noOfSeats
     *            The number of seats in the hemicycle.
     */
    public HemicycleLayout(final int noOfSeats) {
        this(noOfSeats, DEFAULT_ANGLE);
    }

    /**
     * Constructs a hemicycle layout with a number of seats and an angle.
     *
     * @param noOfSeats
     *            The number of seats in the hemicycle.
     * @param angle
     *            The angle for the hemicycle.
     */
    public HemicycleLayout(final int noOfSeats, final double angle) {
        this(noOfSeats, angle, DEFAULT_RADIUS_RATIO);
    }

    /**
     * Constructs a hemicycle layout with a number of seats and an angle.
     *
     * @param noOfSeats
     *            The number of seats in the hemicycle.
     * @param angle
     *            The angle for the hemicycle.
     * @param radiusRatio
     *            The ratio between the inner and the outer radius of the hemicycle.
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
     * Returns the angle of the hemicycle.
     *
     * @return The angle of the hemicycle.
     */
    double getAngle() {
        return angle;
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
     * Calculates the number of rows for the optimal distribution of seats for the
     * hemicycle layout.
     *
     * @return The number of rows.
     */
    int calculateNoOfRows() {
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
     * Returns the number of rows for the optimal distribution of seats for the
     * hemicycle layout.
     *
     * @return The number of rows.
     */
    public int getNoOfRows() {
        if (noOfRows == 0) {
            noOfRows = calculateNoOfRows();
        }
        return noOfRows;
    }

    /**
     * Calculates an unordered set with the seat positions.
     *
     * @return A set with all the seat positions.
     */
    private Set<SeatPosition> calculateSeatPositionSet() {
        Set<SeatPosition> seatPositions = new HashSet<SeatPosition>();
        int thisNoOfRows = getNoOfRows();
        double width = (1.0D - radiusRatio) / thisNoOfRows;
        double[] length = new double[thisNoOfRows];
        double[] quote = new double[thisNoOfRows];
        for (int row = 1; row <= thisNoOfRows; row++) {
            double rowRadius = radiusRatio + ((double) row - ONE_HALF) * width;
            length[row - 1] = rowRadius * angle;
            quote[row - 1] = length[row - 1];
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
            quote[row] = length[row] / (seats[row] + 1);
        }
        for (int row = 1; row <= thisNoOfRows; row++) {
            double rowRadius = radiusRatio + ((double) row - ONE_HALF) * width;
            double rescale = length[row - 1] / (width * seats[row - 1]);
            for (int seat = 0; seat < seats[row - 1]; seat++) {
                double seatAngle = Math.PI / 2D + rescale * (seat + (1D - seats[row - 1]) / 2D) * width / rowRadius;
                if (seatAngle < 0D) {
                    seatAngle += Math.PI * 2D;
                }
                seatPositions.add(new SeatPosition(rowRadius, seatAngle));
            }
        }
        return seatPositions;
    }

    /**
     * Calculates a sorted list with the seat positions.
     *
     * @return A sorted list with the seat positions.
     */
    private List<SeatPosition> calculateSeatPositionList() {
        Set<SeatPosition> set = calculateSeatPositionSet();
        List<SeatPosition> list = new ArrayList<SeatPosition>(set);
        Collections.sort(list, new SeatPositionInHemicycleComparator());
        return list;
    }

    /**
     * Returns a sorted list with the seat positions.
     *
     * @return A sorted list with the seat positions.
     */
    private List<SeatPosition> getSeatPositionList() {
        if (seatPositionList == null) {
            seatPositionList = calculateSeatPositionList();
        }
        return seatPositionList;
    }

    /**
     * Returns the seat position at index i.
     *
     * @param i
     *            The index of the requested seat position
     * @return The seat position at index i.
     */
    public SeatPosition getSeatPosition(final int i) {
        return getSeatPositionList().get(i);
    }

    /**
     * Returns an unmodifiable list with all the seat positions.
     *
     * @return An unmodifiable list with all the seat positions.
     */
    public List<SeatPosition> getSeatPositions() {
        return Collections.unmodifiableList(getSeatPositionList());
    }

    /**
     * Returns the row width.
     *
     * @return The row width.
     */
    public double getRowWidth() {
        return (1D - getRadiusRatio()) / getNoOfRows();
    }
}
