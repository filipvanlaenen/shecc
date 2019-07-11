package net.filipvanlaenen.shecc.export.svg;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Attributes {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.######");

    private static String numericAttributeValueAsString(String attributeName, Map<String, Number> numericAttributes) {
        return DECIMAL_FORMAT.format(numericAttributes.get(attributeName));
    }

    private static String numericArrayAttributeValueAsString(String attributeName,
            Map<String, Number[]> numericArrayAttributes) {
        Number[] numbers = numericArrayAttributes.get(attributeName);
        List<String> values = new ArrayList<String>();
        for (Number number : numbers) {
            values.add(DECIMAL_FORMAT.format(number));
        }
        return String.join(" ", values);
    }

    static String attributesAsString(Map<String, Number> numericAttributes) {
        return attributesAsString(numericAttributes, Collections.emptyMap());
    }

    static String attributesAsString(Map<String, Number> numericAttributes,
            Map<String, Number[]> numericArrayAttributes) {
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
