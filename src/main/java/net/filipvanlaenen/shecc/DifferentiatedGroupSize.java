package net.filipvanlaenen.shecc;

/**
 * A group size having a lower bound, a median, and a total number of seats.
 */
class DifferentiatedGroupSize extends GroupSize {

    /**
     * The lower bound for the number of seats of a group.
     */
    private final int lowerBound;
    /**
     * The median for the number of seats of a group.
     */
    private final int median;
    /**
     * The total number of seats for a group.
     */
    private final int total;

    /**
     * Constructs a group size with a lower bound, a median and a total number of
     * seats. The first number in the array of integers specifies the lower bound
     * for the group size, the second number the median, and if a third number is
     * provided, it specifies the total number of seats. If no third number is
     * provided, the total number of seats is set to be equal to the median.
     *
     * @param sizes
     *            The lower bound, the median and optionally the total number of
     *            seats.
     */
    DifferentiatedGroupSize(final int... sizes) {
        this.lowerBound = sizes[0];
        this.median = sizes[1];
        if (sizes.length > 2) {
            this.total = sizes[2];
        } else {
            this.total = sizes[1];
        }
    }

    @Override
    public int getFullSize() {
        return total;
    }

    /**
     * Returns the lower bound for the number of seats of a group.
     *
     * @return The lower bound for the number of seats of a group.
     */
    public int getLowerBound() {
        return lowerBound;
    }

    /**
     * Returns the median for the number of seats of a group.
     *
     * @return The median for the number of seats of a group.
     */
    public int getMedian() {
        return median;
    }

}
