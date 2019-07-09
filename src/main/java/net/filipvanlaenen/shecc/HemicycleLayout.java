package net.filipvanlaenen.shecc;

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
        if (angle <= 0.0D) {
            throw new IllegalArgumentException("The angle of an hemicycle should be strictly positive.");
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
}
