package net.filipvanlaenen.shecc;

/**
 * A class representing a parliamentary group.
 */
public class ParliamentaryGroup {

    /**
     * The size of the parliamentary group.
     */
    private final GroupSize size;

    /**
     * The color of the parliamentary group.
     */
    private final int color;

    /**
     * The character of the parliamentary group.
     */
    private final String character;

    /**
     * The name of the parliamentary group.
     */
    private final String name;

    /**
     * Constructs a parliamentary group with a size and a color.
     *
     * @param size
     *            The size of the parliamentary group as an integer.
     * @param color
     *            The color of the parliamentary group.
     */
    public ParliamentaryGroup(final int size, final int color) {
        this(size, color, null);
    }

    /**
     * Constructs a parliamentary group with a size and a color.
     *
     * @param size
     *            The size of the parliamentary group.
     * @param color
     *            The color of the parliamentary group.
     */
    public ParliamentaryGroup(final GroupSize size, final int color) {
        this(size, color, null);
    }

    /**
     * Constructs a parliamentary group with a size, color and name.
     *
     * @param size
     *            The size of the parliamentary group as an integer.
     * @param color
     *            The color of the parliamentary group.
     * @param name
     *            The name of the parliamentary group.
     */
    public ParliamentaryGroup(final int size, final int color, final String name) {
        this(size, color, name, null);
    }

    /**
     * Constructs a parliamentary group with a size, color and name.
     *
     * @param size
     *            The size of the parliamentary group.
     * @param color
     *            The color of the parliamentary group.
     * @param name
     *            The name of the parliamentary group.
     */
    public ParliamentaryGroup(final GroupSize size, final int color, final String name) {
        this(size, color, name, null);
    }

    /**
     * Constructs a parliamentary group with a size, color, name and a character.
     *
     * @param size
     *            The size of the parliamentary group as an integer.
     * @param color
     *            The color of the parliamentary group.
     * @param name
     *            The name of the parliamentary group.
     * @param character
     *            The character of the parliamentary group.
     */
    public ParliamentaryGroup(final int size, final int color, final String name, final String character) {
        this(new SimpleGroupSize(size), color, name, character);
    }

    /**
     * Constructs a parliamentary group with a size, color, name and a character.
     *
     * @param size
     *            The size of the parliamentary group.
     * @param color
     *            The color of the parliamentary group.
     * @param name
     *            The name of the parliamentary group.
     * @param character
     *            The character of the parliamentary group.
     */
    public ParliamentaryGroup(final GroupSize size, final int color, final String name, final String character) {
        this.size = size;
        this.color = color;
        this.name = name;
        this.character = character;
    }

    /**
     * Returns the size of the parliamentary group.
     *
     * @return The size of the parliamentary group.
     */
    public GroupSize getSize() {
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

    /**
     * Returns the character of the parliamentary group.
     *
     * @return The character of the parliamentary group.
     */
    public String getCharacter() {
        return character;
    }

    /**
     * Returns the name of the parliamentary group.
     *
     * @return The name of the parliamentary group.
     */
    public String getName() {
        return name;
    }

}
