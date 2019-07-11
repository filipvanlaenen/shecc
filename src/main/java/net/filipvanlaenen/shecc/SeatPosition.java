package net.filipvanlaenen.shecc;

import java.util.Objects;

/**
 * Class defining the position of a seat in a hemicycle. A seat position is
 * defined by its polar coordinates, i.e. a radius and an angle, and should be
 * treated as a value object.
 */
public class SeatPosition {

    /**
     * The radius, the first polar coordinate for the seat position.
     */
    private final double radius;
    /**
     * The angle, the second polar coordinate for the seat position.
     */
    private final double angle;

    /**
     * Constructs a seat position with its polar coordinates, the radius and the
     * angle.
     *
     * @param radius
     *            The radius for the seat position.
     * @param angle
     *            The angle for the seat position.
     */
    SeatPosition(final double radius, final double angle) {
        this.radius = radius;
        this.angle = angle;
    }

    /**
     * Returns the radius, the first polar coordinate for the seat position.
     *
     * @return The radius for the seat position.
     */
    double getRadius() {
        return radius;
    }

    /**
     * Returns the angle, the second polar coordinate for the seat position.
     *
     * @return The angle for the seat position.
     */
    double getAngle() {
        return angle;
    }

    /**
     * Tests for equality with another object. Since a seat position is a value
     * object, two seat positions are equal if their polar coordinates are equal.
     */
    @Override
    public boolean equals(final Object object) {
        if (object instanceof SeatPosition) {
            SeatPosition other = (SeatPosition) object;
            return this.getRadius() == other.getRadius() && this.getAngle() == other.getAngle();
        }
        return false;
    }

    /**
     * Returns a hash code based on the seat position's attributes, i.e. radius and
     * angle.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getRadius(), getAngle());
    }

    /**
     * Returns a string representation of a seat position, including the polar
     * coordinates.
     */
    @Override
    public String toString() {
        return "Seat position (" + getRadius() + ", " + getAngle() + " rad)";
    }

}
