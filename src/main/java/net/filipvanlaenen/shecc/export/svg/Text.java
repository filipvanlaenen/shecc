package net.filipvanlaenen.shecc.export.svg;

/**
 * A class representing text.
 */
public class Text implements GraphicsElement {
    /**
     * The attributes.
     */
    private final Attributes attributes = new Attributes();

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
        attributes.addColorAttribute("fill", fill);
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
        attributes.addNumericAttribute("font-size", fontSize);
        return this;
    }

    /**
     * Sets the value for the text anchor.
     *
     * @param textAnchor
     *            The text anchor for the text.
     * @return The instance called.
     */
    public Text textAnchor(final TextAnchorValues textAnchor) {
        attributes.addEnumerationAttribute("text-anchor", textAnchor);
        return this;
    }

    /**
     * Adds a transformation to the text.
     *
     * @param transformation
     *            The specification of the transformation, as a string.
     * @return The instance called.
     */
    public Text transform(final String transformation) {
        attributes.addStringAttribute("transform", transformation);
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
        attributes.addNumericAttribute("x", x);
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
        attributes.addNumericAttribute("y", y);
        return this;
    }

    /**
     * Returns a string representation of the text.
     *
     * @return A string representation of the text.
     */
    @Override
    public String asString() {
        return "<text" + attributes.asString() + ">" + content + "</text>";
    }

}
