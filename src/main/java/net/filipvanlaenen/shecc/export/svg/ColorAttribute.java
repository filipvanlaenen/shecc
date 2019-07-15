package net.filipvanlaenen.shecc.export.svg;

/**
 * A class representing a color attribute.
 */
public class ColorAttribute implements Attribute {
    /**
     * The name of the attribute.
     */
    private final String name;
    /**
     * The color as an integer.
     */
    private final Integer color;

    /**
     * Constructs an attribute with a color value.
     *
     * @param name
     *            The name of the attribute.
     * @param color
     *            The color as an integer.
     */
    ColorAttribute(final String name, final Integer color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Converts the attribute value to a string.
     *
     * @return A string representing the value.
     */
    @Override
    public String asString() {
        return String.format("#%06X", color);
    }
}
