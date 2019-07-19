package net.filipvanlaenen.shecc.export.svg;

/**
 * A class representing a circle.
 *
 * @see <a href="https://www.w3.org/TR/SVG/shapes.html#CircleElement">Basic
 *      Shapes — SVG 2: 10.3. The ‘circle’ element</a>
 *
 */
public class Circle implements ShapeElement {
    /**
     * The attributes.
     */
    private final Attributes attributes = new Attributes();

    /**
     * Sets the x coordinate of the center.
     *
     * @param cx
     *            The x coordinate of the center.
     * @return The instance called.
     */
    public Circle cx(final Number cx) {
        attributes.addNumericAttribute("cx", cx);
        return this;
    }

    /**
     * Sets the y coordinate of the center.
     *
     * @param cy
     *            The y coordinate of the center.
     * @return The instance called.
     */
    public Circle cy(final Number cy) {
        attributes.addNumericAttribute("cy", cy);
        return this;
    }

    /**
     * Sets the fill color.
     *
     * @param fill
     *            The fill color as an integer.
     * @return The instance called.
     */
    public Circle fill(final Integer fill) {
        attributes.addColorAttribute("fill", fill);
        return this;
    }

    /**
     * Sets the radius.
     *
     * @param r
     *            The radius.
     * @return The instance called.
     */
    public Circle r(final Number r) {
        attributes.addNumericAttribute("r", r);
        return this;
    }

    /**
     * Returns a string representation of the circle.
     *
     * @return A string representation of the circle.
     */
    @Override
    public String asString() {
        return "<circle" + attributes.asString() + "/>";
    }
}
