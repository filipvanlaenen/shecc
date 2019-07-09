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
     * The number of seats in the hemicycle.
     */
    private final int noOfSeats;

    /**
     * Constructs a hemicycle layout.
     *
     * @param noOfSeats
     *            The number of seats in the hemicycle.
     */
    HemicycleLayout(final int noOfSeats) {
        if (noOfSeats <= 0) {
            throw new IllegalArgumentException("The number of seats should be strictly positive.");
        }
        this.noOfSeats = noOfSeats;
    }

    /**
     * Returns the angle of the hemicycle.
     *
     * @return The angle of the hemicycle.
     */
    double getAngle() {
        return Math.PI;
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
