package net.filipvanlaenen.shecc.export.svg;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a numeric array attribute.
 */
public class NumericArrayAttribute implements Attribute {

    /**
     * The name of the attribute.
     */
    private final String name;
    /**
     * The value of the attribute, an array of numbers.
     */
    private final Number[] numbers;

    /**
     * Constructs an attribute with a numeric array value.
     *
     * @param name
     *            The name of the attribute.
     * @param numbers
     *            The numbers.
     */
    NumericArrayAttribute(final String name, final Number... numbers) {
        this.name = name;
        this.numbers = numbers;
    }

    /**
     * Converts the attribute value to a string.
     *
     * @return A string representing the value.
     */
    @Override
    public String asString() {
        List<String> values = new ArrayList<String>();
        for (Number number : numbers) {
            values.add(DECIMAL_FORMAT.format(number));
        }
        return String.join(" ", values);
    }

}
