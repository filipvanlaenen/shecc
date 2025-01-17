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
     * The colors of the parliamentary group.
     */
    private final int[] colors;

    /**
     * The character of the parliamentary group.
     */
    private final String character;

    /**
     * The name of the parliamentary group.
     */
    private final String name;

    /**
     * Constructs a parliamentary group with a size and one color.
     *
     * @param size  The size of the parliamentary group as an integer.
     * @param color The color of the parliamentary group.
     */
    public ParliamentaryGroup(final int size, final int color) {
        this(size, color, null);
    }

    /**
     * Constructs a parliamentary group with a size and a set of colors.
     *
     * @param size   The size of the parliamentary group as an integer.
     * @param colors The colors of the parliamentary group.
     */
    public ParliamentaryGroup(final int size, final int[] colors) {
        this(size, colors, null);
    }

    /**
     * Constructs a parliamentary group with a size and one color.
     *
     * @param size  The size of the parliamentary group.
     * @param color The color of the parliamentary group.
     */
    public ParliamentaryGroup(final GroupSize size, final int color) {
        this(size, color, null);
    }

    /**
     * Constructs a parliamentary group with a size, one color and name.
     *
     * @param size  The size of the parliamentary group as an integer.
     * @param color The color of the parliamentary group.
     * @param name  The name of the parliamentary group.
     */
    public ParliamentaryGroup(final int size, final int color, final String name) {
        this(size, color, name, null);
    }

    /**
     * Constructs a parliamentary group with a size, a set of colors and name.
     *
     * @param size   The size of the parliamentary group as an integer.
     * @param colors The colors of the parliamentary group.
     * @param name   The name of the parliamentary group.
     */
    public ParliamentaryGroup(final int size, final int[] colors, final String name) {
        this(size, colors, name, null);
    }

    /**
     * Constructs a parliamentary group with a size, one color and name.
     *
     * @param size  The size of the parliamentary group.
     * @param color The color of the parliamentary group.
     * @param name  The name of the parliamentary group.
     */
    public ParliamentaryGroup(final GroupSize size, final int color, final String name) {
        this(size, color, name, null);
    }

    /**
     * Constructs a parliamentary group with a size, a set of colors and name.
     *
     * @param size   The size of the parliamentary group.
     * @param colors The colors of the parliamentary group.
     * @param name   The name of the parliamentary group.
     */
    public ParliamentaryGroup(final GroupSize size, final int[] colors, final String name) {
        this(size, colors, name, null);
    }

    /**
     * Constructs a parliamentary group with a size, one color, name and a character.
     *
     * @param size      The size of the parliamentary group as an integer.
     * @param color     The color of the parliamentary group.
     * @param name      The name of the parliamentary group.
     * @param character The character of the parliamentary group.
     */
    public ParliamentaryGroup(final int size, final int color, final String name, final String character) {
        this(new SimpleGroupSize(size), color, name, character);
    }

    /**
     * Constructs a parliamentary group with a size, a set of colors, name and a character.
     *
     * @param size      The size of the parliamentary group as an integer.
     * @param colors    The colors of the parliamentary group.
     * @param name      The name of the parliamentary group.
     * @param character The character of the parliamentary group.
     */
    public ParliamentaryGroup(final int size, final int[] colors, final String name, final String character) {
        this(new SimpleGroupSize(size), colors, name, character);
    }

    /**
     * Constructs a parliamentary group with a size, one color, name and a character.
     *
     * @param size      The size of the parliamentary group.
     * @param color     The color of the parliamentary group.
     * @param name      The name of the parliamentary group.
     * @param character The character of the parliamentary group.
     */
    public ParliamentaryGroup(final GroupSize size, final int color, final String name, final String character) {
        this(size, new int[] {color}, name, character);
    }

    /**
     * Constructs a parliamentary group with a size, a set of colors, name and a character.
     *
     * @param size      The size of the parliamentary group.
     * @param colors    The colors of the parliamentary group.
     * @param name      The name of the parliamentary group.
     * @param character The character of the parliamentary group.
     */
    public ParliamentaryGroup(final GroupSize size, final int[] colors, final String name, final String character) {
        this.size = size;
        this.colors = colors;
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
     * Returns the colors of the parliamentary group.
     *
     * @return The colors of the parliamentary group.
     */
    public int[] getColors() {
        return colors;
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
