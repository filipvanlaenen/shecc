package net.filipvanlaenen.shecc.export.svg;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing text.
 */
public class Text implements GraphicsElement {
    /**
     * A map with the numeric attributes.
     */
    private final Map<String, Number> numericAttributes = new HashMap<String, Number>();
    /**
     * A map with the string attributes.
     */
    private final Map<String, String> stringAttributes = new HashMap<String, String>();
    /**
     * A map with the color attributes.
     */
    private final Map<String, Integer> colorAttributes = new HashMap<String, Integer>();
    /**
     * The content.
     */
    private final String content;

    /**
     * Constructs a text element with a string as its content.
     *
     * @param content
     *            A string.
     */
    public Text(final String content) {
        this.content = content;
    }

    /**
     * Sets the fill color.
     *
     * @param fill
     *            The fill color as an integer.
     * @return The instance called.
     */
    public Text fill(final Integer fill) {
        colorAttributes.put("fill", fill);
        return this;
    }

    /**
     * Sets the font size for the text.
     *
     * @param fontSize
     *            The font size for the text.
     * @return The instance called.
     */
    public Text fontSize(final Number fontSize) {
        numericAttributes.put("font-size", fontSize);
        return this;
    }

    /**
     * Sets the value for the text anchor.
     *
     * @param textAnchor
     *            The text anchor for the text.
     * @return The instance called.
     */
    public Text textAnchor(final String textAnchor) {
        stringAttributes.put("text-anchor", textAnchor);
        return this;
    }

    /**
     * Sets the x coordinate of the text.
     *
     * @param x
     *            The x coordinate of the text.
     * @return The instance called.
     */
    public Text x(final Number x) {
        numericAttributes.put("x", x);
        return this;
    }

    /**
     * Sets the y coordinate of the text.
     *
     * @param y
     *            The y coordinate of the text.
     * @return The instance called.
     */
    public Text y(final Number y) {
        numericAttributes.put("y", y);
        return this;
    }

    /**
     * Returns a string representation of the text.
     *
     * @return A string representation of the text.
     */
    @Override
    public String asString() {
        return "<text" + Attributes.attributesAsString(numericAttributes, colorAttributes, stringAttributes) + ">"
                + content + "</text>";
    }
}
