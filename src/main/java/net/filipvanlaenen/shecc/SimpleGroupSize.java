package net.filipvanlaenen.shecc;

import java.util.Objects;

/**
 * A group size having a total number of seats only.
 */
class SimpleGroupSize extends GroupSize {

    /**
     * The size of the group.
     */
    private final int size;

    /**
     * Constructs a simple group size with the provided size.
     *
     * @param size
     *            The size of the group.
     */
    SimpleGroupSize(final int size) {
        this.size = size;
    }

    /**
     * Returns the size of the group.
     *
     * @return The size of the group.
     */
    int getSize() {
        return size;
    }

    @Override
    public int getFullSize() {
        return size;
    }

    /**
     * Tests for equality with another object. Since a simple group size is a value
     * object, two simple group sizes are equal if their sizes are equal.
     */
    @Override
    public boolean equals(final Object object) {
        if (object instanceof SimpleGroupSize) {
            SimpleGroupSize other = (SimpleGroupSize) object;
            return this.getSize() == other.getSize();
        }
        return false;
    }

    /**
     * Returns a hash code based on the simple group size's attributes, i.e. the
     * size.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getSize());
    }

}
