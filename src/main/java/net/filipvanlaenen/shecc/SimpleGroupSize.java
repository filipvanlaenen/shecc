package net.filipvanlaenen.shecc;

import java.util.Objects;

class SimpleGroupSize extends GroupSize {

    private final int size;

    SimpleGroupSize(int size) {
        this.size = size;
    }

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
