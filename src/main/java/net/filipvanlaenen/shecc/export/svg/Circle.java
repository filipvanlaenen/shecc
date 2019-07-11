package net.filipvanlaenen.shecc.export.svg;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Circle implements ShapeElement {
    private final Map<String, Number> numericAttributes = new HashMap<String, Number>();

    public Circle cx(Number cx) {
        numericAttributes.put("cx", cx);
        return this;
    }

    public Circle cy(Number cy) {
        numericAttributes.put("cy", cy);
        return this;
    }

    public Circle r(Number r) {
        numericAttributes.put("r", r);
        return this;
    }

    private String attributesAsString() {
        List<String> attributeStrings = new ArrayList<String>();
        Set<String> attributeNameSet = numericAttributes.keySet();
        List<String> attributeNameList = new ArrayList<String>(attributeNameSet);
        Collections.sort(attributeNameList);
        Iterator<String> attributeNameIterator = attributeNameList.iterator();
        while (attributeNameIterator.hasNext()) {
            String attributeName = attributeNameIterator.next();
            String attributeValueString = new DecimalFormat("#.######").format(numericAttributes.get(attributeName));
            attributeStrings.add(attributeName + "=\"" + attributeValueString + "\"");
        }
        if (attributeStrings.isEmpty()) {
            return "";
        } else {
            return " " + String.join(" ", attributeStrings);
        }
    }

    @Override
    public String asString() {
        return "<circle" + attributesAsString() + "/>";
    }

}
