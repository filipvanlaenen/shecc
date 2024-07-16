package net.filipvanlaenen.shecc;

import java.util.Arrays;

/**
 * Superclass defining the common interface for the concept of a group size, with some basic functionality implemented.
 */
public interface GroupSize {
    /**
     * Parses a string into a group size, returning an instance of one of the subclasses.
     *
     * @param string The string describing a group size.
     * @return An instance of one of the subclasses representing the group size as defined by the provided string.
     */
    static GroupSize parseGroupSize(final String string) {
        if (string.contains(":")) {
            int[] sizes = Arrays.stream(string.split(":")).mapToInt(Integer::parseInt).toArray();
            return new DifferentiatedGroupSize(sizes);
        } else {
            int size = Integer.parseInt(string);
            return new SimpleGroupSize(size);
        }
    }

    /**
     * Returns the full size of the group.
     *
     * @return The full size of the group.
     */
    int getFullSize();
}
