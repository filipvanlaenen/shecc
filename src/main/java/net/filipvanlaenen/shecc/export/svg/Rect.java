package net.filipvanlaenen.shecc.export.svg;

/**
 * A class representing a rectangle.
 *
 * @see <a href="https://www.w3.org/TR/SVG/shapes.html#RectElement">Basic Shapes
 *      — SVG 2: 10.2. The ‘rect’ element</a>
 */
public class Rect implements ShapeElement {
    /**
     * The attributes.
     */
    private final Attributes attributes = new Attributes();

    /**
     * Sets the fill color.
     *
     * @param fill
     *            The fill color as an integer.
     * @return The instance called.
     */
    public Rect fill(final Integer fill) {
        attributes.addColorAttribute("fill", fill);
        return this;
    }

    /**
     * Sets the height of the rectangle.
     *
     * @param height
     *            The height of the rectangle.
     * @return The instance called.
     */
    public Rect height(final Number height) {
        attributes.addNumericAttribute("height", height);
        return this;
    }

    /**
     * Sets the width of the rectangle.
     *
     * @param width
     *            The width of the rectangle.
     * @return The instance called.
     */
    public Rect width(final Number width) {
        attributes.addNumericAttribute("width", width);
        return this;
    }

    /**
     * Sets the x coordinate of the left edge of the rectangle.
     *
     * @param x
     *            The x coordinate of left edge of the rectangle.
     * @return The instance called.
     */
    public Rect x(final Number x) {
        attributes.addNumericAttribute("x", x);
        return this;
    }

    /**
     * Sets the y coordinate of the top edge of the rectangle.
     *
     * @param y
     *            The y coordinate of top edge of the rectangle.
     * @return The instance called.
     */
    public Rect y(final Number y) {
        attributes.addNumericAttribute("y", y);
        return this;
    }

    /**
     * Returns a string representation of the rectangle.
     *
     * @return A string representation of the rectangle.
     */
    @Override
    public String asString() {
        return "<rect" + attributes.asString() + "/>";
    }
}
