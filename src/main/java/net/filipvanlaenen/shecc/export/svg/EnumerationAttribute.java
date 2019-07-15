package net.filipvanlaenen.shecc.export.svg;

/**
 * A class representing an attribute selected from an enumeration.
 *
 * @param <E>
 *            The type of attribute value enumeration held by this attribute.
 */
public class EnumerationAttribute<E extends AttributeValueEnumeration> implements Attribute {

    /**
     * The name of the attribute.
     */
    private final String name;
    /**
     * The value of the attribute.
     */
    private final E value;

    /**
     * Constructs an attribute with an attribute value enumeration value.
     *
     * @param name
     *            The name of the attribute.
     * @param value
     *            The value of the attribute.
     */
    EnumerationAttribute(final String name, final E value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Converts the attribute value to a string.
     *
     * @return A string representing the value.
     */
    @Override
    public String asString() {
        return value.toString();
    }

}
