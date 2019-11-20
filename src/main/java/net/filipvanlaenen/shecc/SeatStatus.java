package net.filipvanlaenen.shecc;

/**
 * An enumeration holding the possible statuses for seats.
 */
public enum SeatStatus {
    /**
     * A certain seat, i.e. below the lower bound of the confidence interval.
     */
    CERTAIN,
    /**
     * A likely seat, i.e. in the lower half of the confidence interval.
     */
    LIKELY,
    /**
     * An uncertain seat, i.e. in the upper half of the confidence interval.
     */
    UNCERTAIN
}
