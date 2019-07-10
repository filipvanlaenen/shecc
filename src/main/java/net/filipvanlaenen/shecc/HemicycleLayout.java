package net.filipvanlaenen.shecc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
class HemicycleLayout {

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
     * Constructs a hemicycle layout with a number of seats only.
     *
     * @param noOfSeats
     *            The number of seats in the hemicycle.
     */
    HemicycleLayout(final int noOfSeats) {
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
    HemicycleLayout(final int noOfSeats, final double angle) {
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
     * Returns the number of rows for the optimal distribution of seats for the
     * hemicycle layout.
     *
     * @return The number of rows.
     */
    int getNoOfRows() {
        int noOfRows = 0;
        while (true) {
            noOfRows += 1;
            double width = (1.0D - radiusRatio) / noOfRows;
            int s = 0;
            for (int row = 1; row <= noOfRows; row++) {
                double rowRadius = radiusRatio + ((double) row - ONE_HALF) * width;
                s += (int) (angle * rowRadius / width);
            }
            if (s >= noOfSeats) {
                return noOfRows;
            }
        }
    }

    /**
     * Returns an unordered set with the seat positions.
     *
     * @return A set with all the seat positions.
     */
    private Set<SeatPosition> getSeatPositionSet() {
        Set<SeatPosition> seatPositions = new HashSet<SeatPosition>();
        int noOfRows = getNoOfRows();
        double width = (1.0D - radiusRatio) / noOfRows;
        for (int row = 1; row <= noOfRows; row++) {
            double rowRadius = radiusRatio + ((double) row - ONE_HALF) * width;
            seatPositions.add(new SeatPosition(rowRadius, Math.PI / 2D));
        }
        return seatPositions;
    }

    /**
     * Returns a sorted list with the seat positions.
     *
     * @return A sorted list with the seat positions.
     */
    private List<SeatPosition> getSeatPositionList() {
        Set<SeatPosition> set = getSeatPositionSet();
        List<SeatPosition> list = new ArrayList<SeatPosition>(set);
        Collections.sort(list, new Comparator<SeatPosition>() {
            @Override
            public int compare(final SeatPosition seatPosition1, final SeatPosition seatPosition2) {
                return 0;
            }
        });
        return list;
    }

    /**
     * Returns the seat position at index i.
     *
     * @param i
     *            The index of the requested seat position
     * @return The seat position at index i.
     */
    SeatPosition getSeatPosition(final int i) {
        return getSeatPositionList().get(i);
    }
}
