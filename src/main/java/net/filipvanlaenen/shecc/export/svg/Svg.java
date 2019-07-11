package net.filipvanlaenen.shecc.export.svg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Svg {

    private Number height;
    private Number width;
    private Number[] viewBox;
    private final List<ShapeElement> elements = new ArrayList<ShapeElement>();

    public Svg height(Number height) {
        this.height = height;
        return this;
    }

    public Svg width(Number width) {
        this.width = width;
        return this;
    }

    public Svg viewBox(Number minX, Number minY, Number width, Number height) {
        this.viewBox = new Number[] { minX, minY, width, height };
        return this;
    }

    public void addElement(ShapeElement shape) {
        this.elements.add(shape);
    }

    private void conditionallyAddNumericAttributeAsString(List<String> attributes, Number number, String name) {
        if (number != null) {
            attributes.add(name + "=\"" + number.toString() + "\"");
        }
    }

    private void conditionallyAddNumericArrayAttributeAsString(List<String> attributes, Number[] numberArray,
            String name) {
        if (numberArray != null) {
            List<String> values = new ArrayList<String>();
            for (Number number : numberArray) {
                values.add(number.toString());
            }
            attributes.add(name + "=\"" + String.join(" ", values) + "\"");
        }
    }

    private String attributesAsString() {
        List<String> attributeStrings = new ArrayList<String>();
        conditionallyAddNumericAttributeAsString(attributeStrings, height, "height");
        conditionallyAddNumericArrayAttributeAsString(attributeStrings, viewBox, "viewBox");
        conditionallyAddNumericAttributeAsString(attributeStrings, width, "width");
        if (attributeStrings.isEmpty()) {
            return "";
        } else {
            return " " + String.join(" ", attributeStrings);
        }
    }

    private String elementsAsString() {
        List<String> elementStrings = new ArrayList<String>();
        Iterator<ShapeElement> elementIterator = elements.iterator();
        while (elementIterator.hasNext()) {
            ShapeElement element = elementIterator.next();
            elementStrings.add(element.asString());
        }
        if (elementStrings.isEmpty()) {
            return "";
        } else {
            return "  " + String.join("\n  ", elementStrings) + "\n";
        }
    }

    public String asString() {
        return "<svg" + attributesAsString() + " xmlns=\"http://www.w3.org/2000/svg\""
                + (elements.isEmpty() ? "/>" : (">\n" + elementsAsString() + "</svg>"));

    }

}
