package net.filipvanlaenen.shecc.export.svg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class representing the root element of an SVG document.
 */
public class Svg {

    /**
     * A map with the numeric attributes.
     */
    private final Map<String, Number> numericAttributes = new HashMap<String, Number>();
    /**
     * A map with the numeric array attributes.
     */
    private final Map<String, Number[]> numericArrayAttributes = new HashMap<String, Number[]>();
    /**
     * A list with the elements.
     */
    private final List<ShapeElement> elements = new ArrayList<ShapeElement>();

    /**
     * Sets the height.
     *
     * @param height
     *            The height.
     * @return The instance called.
     */
    public Svg height(final Number height) {
        numericAttributes.put("height", height);
        return this;
    }

    /**
     * Sets the width.
     *
     * @param width
     *            The width.
     * @return The instance called.
     */
    public Svg width(final Number width) {
        numericAttributes.put("width", width);
        return this;
    }

    /**
     * Sets the view box attribute, a list of four numbers (minX, minY, width and
     * height).
     *
     * @param minX
     *            The x coordinate of the top left corner.
     * @param minY
     *            The y coordinate of the top left corner.
     * @param width
     *            The width of the view box.
     * @param height
     *            The height of the view box.
     * @return The instance called.
     */
    public Svg viewBox(final Number minX, final Number minY, final Number width, final Number height) {
        numericArrayAttributes.put("viewBox", new Number[] {minX, minY, width, height});
        return this;
    }

    /**
     * Adds a shape element.
     *
     * @param shape
     *            A shape element.
     */
    public void addElement(final ShapeElement shape) {
        this.elements.add(shape);
    }

    /**
     * Returns a string representation of the elements.
     *
     * @return A string representation of the elements.
     */
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

    /**
     * Returns a string representation of the SVG document.
     *
     * @return A string representation of the SVG document.
     */
    public String asString() {
        return "<svg" + Attributes.attributesAsString(numericAttributes, numericArrayAttributes)
                + " xmlns=\"http://www.w3.org/2000/svg\""
                + (elements.isEmpty() ? "/>" : ">\n" + elementsAsString() + "</svg>");

    }
}
