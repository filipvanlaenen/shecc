package net.filipvanlaenen.shecc.export.svg;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
     * A map with all the attributes.
     */
    private final Map<String, Attribute> attributes = new HashMap<String, Attribute>();

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
     * Converts a color value from the map of color attributes to a string.
     *
     * @param attributeName
     *            The name of the attribute to be exported.
     * @param colorAttributes
     *            The map with the color attributes.
     * @return A string representing the requested color attribute's value.
     */
    private static String colorAttributeValueAsString(final String attributeName,
            final Map<String, Integer> colorAttributes) {
        return String.format("#%06X", colorAttributes.get(attributeName));
    }

    /**
     * Converts a string value from the map of string attributes to a string.
     *
     * @param attributeName
     *            The name of the attribute to be exported.
     * @param stringAttributes
     *            The map with the string attributes.
     * @return A string representing the requested string attribute's value.
     */
    private static String stringAttributeValueAsString(final String attributeName,
            final Map<String, String> stringAttributes) {
        return stringAttributes.get(attributeName);
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
     * Converts a map with numeric attributes together with a map of color
     * attributes to a string.
     *
     * @param numericAttributes
     *            The map with the numeric attributes.
     * @param colorAttributes
     *            The map with the color attributes.
     * @return A string representing the numeric and the numeric array attributes.
     */
    static String attributesAsString(final Map<String, Number> numericAttributes,
            final Map<String, Integer> colorAttributes) {
        return attributesAsString(numericAttributes, colorAttributes, Collections.emptyMap());
    }

    /**
     * Converts a map with numeric attributes together with a map of color and
     * string attributes to a string.
     *
     * @param numericAttributes
     *            The map with the numeric attributes.
     * @param colorAttributes
     *            The map with the color attributes.
     * @param stringAttributes
     *            The map with the string attributes.
     * @return A string representing the numeric and the numeric array attributes.
     */
    static String attributesAsString(final Map<String, Number> numericAttributes,
            final Map<String, Integer> colorAttributes, final Map<String, String> stringAttributes) {
        return attributesAsString(numericAttributes, colorAttributes, stringAttributes, Collections.emptyMap());
    }

    /**
     * Converts a map with numeric attributes together with a map of color
     * attributes and numeric array attributes to a string.
     *
     * @param numericAttributes
     *            The map with the numeric attributes.
     * @param colorAttributes
     *            The map with the color attributes.
     * @param stringAttributes
     *            The map with the string attributes.
     * @param numericArrayAttributes
     *            The map with the numeric array attributes.
     * @return A string representing the numeric, color and the numeric array
     *         attributes.
     */
    static String attributesAsString(final Map<String, Number> numericAttributes,
            final Map<String, Integer> colorAttributes, final Map<String, String> stringAttributes,
            final Map<String, Number[]> numericArrayAttributes) {
        List<String> attributeStrings = new ArrayList<String>();
        Set<String> attributeNameSet = new HashSet<String>();
        attributeNameSet.addAll(numericAttributes.keySet());
        attributeNameSet.addAll(colorAttributes.keySet());
        attributeNameSet.addAll(stringAttributes.keySet());
        attributeNameSet.addAll(numericArrayAttributes.keySet());
        List<String> attributeNameList = new ArrayList<String>(attributeNameSet);
        Collections.sort(attributeNameList);
        Iterator<String> attributeNameIterator = attributeNameList.iterator();
        while (attributeNameIterator.hasNext()) {
            String attributeName = attributeNameIterator.next();
            String attributeValueString;
            if (numericAttributes.containsKey(attributeName)) {
                attributeValueString = numericAttributeValueAsString(attributeName, numericAttributes);
            } else if (colorAttributes.containsKey(attributeName)) {
                attributeValueString = colorAttributeValueAsString(attributeName, colorAttributes);
            } else if (stringAttributes.containsKey(attributeName)) {
                attributeValueString = stringAttributeValueAsString(attributeName, stringAttributes);
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

    /**
     * Adds a numeric attribute to the set of attributes.
     *
     * @param name
     *            The name of the attribute.
     * @param number
     *            The numeric value of the attribute.
     */
    void addNumericAttribute(final String name, final Number number) {
        attributes.put(name, new NumericAttribute(name, number));
    }

    /**
     * Adds a color attribute to the set of attributes, the color specified as an
     * integer number.
     *
     * @param name
     *            The name of the attribute.
     * @param color
     *            The color specified as an integer number.
     */
    void addColorAttribute(String name, Integer color) {
        attributes.put(name, new ColorAttribute(name, color));
    }

    /**
     * Adds a numeric array attribute to the set of attributes.
     *
     * @param name
     *            The name of the attribute.
     * @param numbers
     *            The numeric array value of the attribute.
     */
    void addNumericArrayAttribute(String name, Number... numbers) {
        attributes.put(name, new NumericArrayAttribute(name, numbers));
    }

    /**
     * Adds an enumerated attribute to the set of attributes.
     *
     * @param name
     *            The name of the attribute.
     * @param value
     *            The enumerated value of the attribute.
     */
    void addEnumeratedAttribute(final String name, final Enum value) {
        attributes.put(name, new EnumeratedArrayAttribute(name, value));
    }

    String asString() {
        Set<String> attributeNameSet = new HashSet<String>();
        attributeNameSet.addAll(attributes.keySet());
        List<String> attributeNameList = new ArrayList<String>(attributeNameSet);
        Collections.sort(attributeNameList);
        Iterator<String> attributeNameIterator = attributeNameList.iterator();
        List<String> attributeStrings = new ArrayList<String>();
        while (attributeNameIterator.hasNext()) {
            String attributeName = attributeNameIterator.next();
            attributeStrings.add(attributeName + "=\"" + attributes.get(attributeName).asString() + "\"");
        }
        if (attributeStrings.isEmpty()) {
            return "";
        } else {
            return " " + String.join(" ", attributeStrings);
        }
    }

}
