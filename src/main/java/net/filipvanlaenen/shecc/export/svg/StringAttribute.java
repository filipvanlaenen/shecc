package net.filipvanlaenen.shecc.export.svg;

/**
 * A class representing a string attribute.
 */
public class StringAttribute implements Attribute {

    /**
     * The name of the attribute.
     */
    private final String name;
    /**
     * The value of the attribute.
     */
    private final String string;

    /**
     * Constructs an attribute with a string value.
     *
     * @param name
     *            The name of the attribute.
     * @param string
     *            The string.
     */
    StringAttribute(final String name, final String string) {
        this.name = name;
        this.string = string;
    }

    /**
     * Converts the attribute value to a string.
     *
     * @return A string representing the value.
     */
    @Override
    public String asString() {
        return string;
    }

}
