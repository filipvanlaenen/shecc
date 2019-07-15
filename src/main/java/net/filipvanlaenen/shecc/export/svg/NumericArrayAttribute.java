package net.filipvanlaenen.shecc.export.svg;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NumericArrayAttribute implements Attribute {
    /**
     * The decimal format.
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.######",
            DecimalFormatSymbols.getInstance(Locale.US));
    
    private final String name;
    private final Number[] numbers;

    NumericArrayAttribute(final String name, final Number... numbers) {
        this.name = name;
        this.numbers = numbers;
    }

    @Override
    public String asString() {
        List<String> values = new ArrayList<String>();
        for (Number number : numbers) {
            values.add(DECIMAL_FORMAT.format(number));
        }
        return String.join(" ", values);
    }

}
