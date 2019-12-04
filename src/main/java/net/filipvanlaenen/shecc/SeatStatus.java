package net.filipvanlaenen.shecc;

/**
 * An enumeration holding the possible statuses for seats.
 */
public enum SeatStatus {
    /**
     * A certain seat, i.e. below the lower bound of the confidence interval.
     */
    CERTAIN(1.0D),
    /**
     * A likely seat, i.e. in the lower half of the confidence interval.
     */
    LIKELY(0.5D),
    /**
     * An uncertain seat, i.e. in the upper half of the confidence interval.
     */
    UNCERTAIN(0.2D);

    /**
     * The opacity for the seat status.
     */
    private final double opacity;

    /**
     * Constructs a seat status with an opacity.
     *
     * @param opacity
     *            The opacity for the seat status.
     */
    SeatStatus(final double opacity) {
        this.opacity = opacity;
    }

    /**
     * Returns the opacity for the seat status.
     *
     * @return The opacity for the seat status.
     */
    public double getOpacity() {
        return opacity;
    }
}
