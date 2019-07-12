package net.filipvanlaenen.shecc.export.svg;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a circle.
 */
public class Circle implements ShapeElement {
    /**
     * A map with the numeric attributes.
     */
    private final Map<String, Number> numericAttributes = new HashMap<String, Number>();
    /**
     * A map with the color attributes.
     */
    private final Map<String, Integer> colorAttributes = new HashMap<String, Integer>();

    /**
     * Sets the x coordinate of the center.
     *
     * @param cx
     *            The x coordinate of the center.
     * @return The instance called.
     */
    public Circle cx(final Number cx) {
        numericAttributes.put("cx", cx);
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
        numericAttributes.put("cy", cy);
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
        colorAttributes.put("fill", fill);
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
        numericAttributes.put("r", r);
        return this;
    }

    /**
     * Returns a string representation of the circle.
     *
     * @return A string representation of the circle.
     */
    @Override
    public String asString() {
        return "<circle" + Attributes.attributesAsString(numericAttributes, colorAttributes) + "/>";
    }
}
