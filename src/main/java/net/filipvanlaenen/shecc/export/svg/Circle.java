package net.filipvanlaenen.shecc.export.svg;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public String asString() {
        return "<circle" + Attributes.attributesAsString(numericAttributes) + "/>";
    }
}
