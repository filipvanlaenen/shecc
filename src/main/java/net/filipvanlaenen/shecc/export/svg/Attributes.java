package net.filipvanlaenen.shecc.export.svg;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * A class helping to export attributes from SVG elements to a string.
 */
public class Attributes {

    /**
     * The decimal format for numbers.
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.######",
            DecimalFormatSymbols.getInstance(Locale.US));

    /**
     * Converts a numeric value from the map of numeric attributes to a string.
     *
     * @param attributeName
     *            The name of the attribute to be exported.
     * @param numericAttributes
     *            The map with the numeric attributes.
     * @return A string representing the requested numeric attribute's value.
     */
    private static String numericAttributeValueAsString(final String attributeName,
            final Map<String, Number> numericAttributes) {
        return DECIMAL_FORMAT.format(numericAttributes.get(attributeName));
    }

    /**
     * Converts a numeric array from the map of numeric array attributes to a
     * string.
     *
     * @param attributeName
     *            The name of the attribute to be exported.
     * @param numericArrayAttributes
     *            The map with the numeric array attributes.
     * @return A string representing the requested numeric array attribute's value.
     */
    private static String numericArrayAttributeValueAsString(final String attributeName,
            final Map<String, Number[]> numericArrayAttributes) {
        Number[] numbers = numericArrayAttributes.get(attributeName);
        List<String> values = new ArrayList<String>();
        for (Number number : numbers) {
            values.add(DECIMAL_FORMAT.format(number));
        }
        return String.join(" ", values);
    }

    /**
     * Converts a map with numeric attributes to a string.
     *
     * @param numericAttributes
     *            The map with the numeric attributes.
     * @return A string representing the numeric attributes.
     */
    static String attributesAsString(final Map<String, Number> numericAttributes) {
        return attributesAsString(numericAttributes, Collections.emptyMap());
    }

    /**
     * Converts a map with numeric attributes together with a map of numeric array
     * attributes to a string.
     *
     * @param numericAttributes
     *            The map with the numeric attributes.
     * @param numericArrayAttributes
     *            The map with the numeric array attributes.
     * @return A string representing the numeric and the numeric array attributes.
     */
    static String attributesAsString(final Map<String, Number> numericAttributes,
            final Map<String, Number[]> numericArrayAttributes) {
        List<String> attributeStrings = new ArrayList<String>();
        Set<String> attributeNameSet = new HashSet<String>();
        attributeNameSet.addAll(numericAttributes.keySet());
        attributeNameSet.addAll(numericArrayAttributes.keySet());
        List<String> attributeNameList = new ArrayList<String>(attributeNameSet);
        Collections.sort(attributeNameList);
        Iterator<String> attributeNameIterator = attributeNameList.iterator();
        while (attributeNameIterator.hasNext()) {
            String attributeName = attributeNameIterator.next();
            String attributeValueString;
            if (numericAttributes.containsKey(attributeName)) {
                attributeValueString = numericAttributeValueAsString(attributeName, numericAttributes);
            } else {
                attributeValueString = numericArrayAttributeValueAsString(attributeName, numericArrayAttributes);
            }
            attributeStrings.add(attributeName + "=\"" + attributeValueString + "\"");
        }
        if (attributeStrings.isEmpty()) {
            return "";
        } else {
            return " " + String.join(" ", attributeStrings);
        }
    }
}
