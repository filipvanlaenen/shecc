package net.filipvanlaenen.shecc.export.svg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing the root element of an SVG document.
 */
public class Svg {

    /**
     * The attributes.
     */
    private final Attributes attributes = new Attributes();
    /**
     * A list with the elements.
     */
    private final List<GraphicsElement> elements = new ArrayList<GraphicsElement>();

    /**
     * Sets the height.
     *
     * @param height
     *            The height.
     * @return The instance called.
     */
    public Svg height(final Number height) {
        attributes.addNumericAttribute("height", height);
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
        attributes.addNumericAttribute("width", width);
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
        attributes.addNumericArrayAttribute("viewBox", new Number[] {minX, minY, width, height});
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
     * Adds a text element.
     *
     * @param text
     *            A text element.
     */
    public void addElement(final Text text) {
        this.elements.add(text);
    }

    /**
     * Returns a string representation of the elements.
     *
     * @return A string representation of the elements.
     */
    private String elementsAsString() {
        List<String> elementStrings = new ArrayList<String>();
        Iterator<GraphicsElement> elementIterator = elements.iterator();
        while (elementIterator.hasNext()) {
            GraphicsElement element = elementIterator.next();
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
        return "<svg" + attributes.asString() + " xmlns=\"http://www.w3.org/2000/svg\""
                + (elements.isEmpty() ? "/>" : ">\n" + elementsAsString() + "</svg>");

    }
}
