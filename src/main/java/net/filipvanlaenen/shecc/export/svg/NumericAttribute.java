package net.filipvanlaenen.shecc.export.svg;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * A class representing a numeric attribute.
 */
public class NumericAttribute implements Attribute {
    /**
     * The decimal format.
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.######",
            DecimalFormatSymbols.getInstance(Locale.US));

    /**
     * The name of the attribute.
     */
    private final String name;
    /**
     * The value of the attribute.
     */
    private final Number number;

    /**
     * Constructs an attribute with a numeric value.
     *
     * @param name
     *            The name of the attribute.
     * @param number
     *            The number.
     */
    NumericAttribute(final String name, final Number number) {
        this.name = name;
        this.number = number;
    }

    /**
     * Converts the attribute value to a string.
     *
     * @return A string representing the value.
     */
    @Override
    public String asString() {
        return DECIMAL_FORMAT.format(number);
    }
}
