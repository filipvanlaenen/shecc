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
     * The default value for the angle, set to π (180°).
     */
    private static final double DEFAULT_ANGLE = Math.PI;

    /**
     * The number of seats in the hemicycle.
     */
    private final int noOfSeats;

    /**
     * The angle for the hemicycle.
     */
    private final double angle;

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
        if (noOfSeats <= 0) {
            throw new IllegalArgumentException("The number of seats in a hemicycle should be strictly positive.");
        }
        this.noOfSeats = noOfSeats;
        if (angle <= 0.0) {
            throw new IllegalArgumentException("The angle of an hemicycle should be strictly positive.");
        }
        this.angle = angle;
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
}
