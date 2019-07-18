package net.filipvanlaenen.shecc.export.svg;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * An interface defining attributes.
 */
public interface Attribute {

    /**
     * The decimal format.
     */
     DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.######", DecimalFormatSymbols.getInstance(Locale.US));

    /**
     * Converts the attribute value to a string.
     *
     * @return A string representing the value.
     */
    String asString();

}
