package net.filipvanlaenen.shecc;

/**
 * A group size having a lower bound, a median, and a total number of seats.
 *
 * @param lowerBound The lower bound for the number of seats of a group.
 * @param median     The median for the number of seats of a group.
 * @param total      The total number of seats for a group.
 */
public record DifferentiatedGroupSize(int lowerBound, int median, int total) implements GroupSize {
    /**
     * Constructs a group size with a lower bound, a median and a total number of seats. The first number in the array
     * of integers specifies the lower bound for the group size, the second number the median, and if a third number is
     * provided, it specifies the total number of seats. If no third number is provided, the total number of seats is
     * set to be equal to the median.
     *
     * @param sizes The lower bound, the median and optionally the total number of seats.
     */
    DifferentiatedGroupSize(final int... sizes) {
        this(sizes[0], sizes[1], sizes.length > 2 ? sizes[2] : sizes[1]);
    }

    @Override
    public int getFullSize() {
        return total;
    }
}
