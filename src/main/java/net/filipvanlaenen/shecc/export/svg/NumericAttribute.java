package net.filipvanlaenen.shecc.export.svg;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumericAttribute implements Attribute {
    /**
     * The decimal format.
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.######",
            DecimalFormatSymbols.getInstance(Locale.US));

    private final String name;
    private final Number number;

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
