package net.filipvanlaenen.shecc.export;

import net.filipvanlaenen.shecc.export.svg.Text;
import net.filipvanlaenen.shecc.export.svg.TextAnchorValues;
import net.filipvanlaenen.shecc.export.svg.Transform;

/**
 * Abstract superclass for exporter classes. Contains common methods shared by
 * all exporters.
 */
abstract class Exporter {
    /**
     * Magic number for the color black.
     */
    protected static final int BLACK = 0x000000;

    /**
     * Specifies the font color.
     */
    protected Integer fontColor;
    /**
     * Specifies the font family.
     */
    protected String fontFamily;

    /**
     * Specifies the font color.
     *
     * @param fontColor
     *            The name of the font color.
     */
    public void setFontColor(final Integer fontColor) {
        this.fontColor = fontColor;
    }

    /**
     * Specifies the font family.
     *
     * @param fontFamily
     *            The name of the font family.
     */
    public void setFontFamily(final String fontFamily) {
        this.fontFamily = fontFamily;
    }

    /**
     * Creates a copyright notice.
     *
     * @param x
     *            The x coordinate of the top right corner of the view box.
     * @param y
     *            The y coordinate of the top right corner of the view box.
     * @param width
     *            The width of the view box.
     * @param height
     *            The height of the view box.
     * @return A text element containing the copyright notice.
     */
    protected Text createCopyrightNotice(final double x, final double y, final double width, final double height) {
        double size = width > height ? width : height;
        Text text = new Text("Produced using SHecC").x(x - size / 200D).y(y - size / 200D).fontSize(size / 100D)
                .textAnchor(TextAnchorValues.END).transform(Transform.rotate(270D, x, y));
        if (fontColor == null) {
            text.fill(BLACK);
        } else {
            text.fill(fontColor);
        }
        if (fontFamily != null) {
            text.fontFamily(fontFamily);
        }
        return text;
    }
}
