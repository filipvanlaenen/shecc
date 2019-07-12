package net.filipvanlaenen.shecc;

/**
 * A class representing a parliamentary group.
 */
public class ParliamentaryGroup {

    /**
     * The size of the parliamentary group.
     */
    private final int size;

    /**
     * The color of the parliamentary group.
     */
    private final int color;

    /**
     * Constructs a parliamentary group with a size and a color.
     *
     * @param size
     *            The size of the parliamentary group.
     * @param color
     *            The color of the parliamentary group.
     */
    public ParliamentaryGroup(int size, int color) {
        this.size = size;
        this.color = color;
    }

    /**
     * Returns the size of the parliamentary group.
     * 
     * @return The size of the parliamentary group.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the color of the parliamentary group.
     * 
     * @return The color of the parliamentary group.
     */
    public int getColor() {
        return color;
    }

}
