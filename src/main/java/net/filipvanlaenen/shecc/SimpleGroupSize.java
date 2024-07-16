package net.filipvanlaenen.shecc;

/**
 * A group size having a total number of seats only.
 *
 * @param size The size of the group.
 */
record SimpleGroupSize(int size) implements GroupSize {
    @Override
    public int getFullSize() {
        return size;
    }
}
