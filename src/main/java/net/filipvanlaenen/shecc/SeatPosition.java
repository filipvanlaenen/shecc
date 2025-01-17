package net.filipvanlaenen.shecc;

/**
 * Class defining the position of a seat in a hemicycle. A seat position is defined by its polar coordinates, i.e. a
 * radius and an angle, and should be treated as a value object.
 *
 * @param row    The row of the seat position.
 * @param radius The radius for the seat position.
 * @param angle  The angle for the seat position.
 */
public record SeatPosition(int row, double radius, double angle) {
    /**
     * Returns the x coordinate for the seat position.
     *
     * @return The x coordinate for the seat position.
     */
    public double getX() {
        return radius * Math.cos(angle);
    }

    /**
     * Returns the y coordinate for the seat position.
     *
     * @return The y coordinate for the seat position.
     */
    public double getY() {
        return radius * Math.sin(angle);
    }

    /**
     * Returns a string representation of a seat position, including the polar coordinates.
     */
    @Override
    public String toString() {
        return "Seat position (" + radius() + ", " + angle() + " rad)";
    }
}
