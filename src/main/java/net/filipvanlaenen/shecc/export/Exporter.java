package net.filipvanlaenen.shecc.export;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.filipvanlaenen.tsvgj.Text;
import net.filipvanlaenen.tsvgj.TextAnchorValues;
import net.filipvanlaenen.tsvgj.Transform;

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
     * The ratio between the seat circle radius and the row width.
     */
    protected static final double RADIUS_ROW_WIDTH_RATIO = 0.45D;
    /**
     * The factor to scale up the view box to the SVG dimensions.
     */
    protected static final double VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR = 1000D;


    /**
     * The font color as an integer.
     */
    protected Integer fontColor;
    /**
     * The font family.
     */
    protected String fontFamily;

    /**
     * Specifies the font color.
     *
     * @param fontColor
     *            The font color as an integer.
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
     * Creates a copyright notice. If no custom notice is provided, only a message
     * telling the chart was produced by SHecC will be created.
     *
     * @param customNotice
     *            Custom copyright notice.
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
    protected Text createCopyrightNotice(final String customNotice, final double x, final double y, final double width,
            final double height) {
        double size = Math.max(width, height);
        String fullNotice = customNotice == null ? "Chart produced using SHecC"
                : "© " + getYear() + " " + customNotice + ", chart produced using SHecC";
        Text text = new Text(fullNotice).x(x - size / 200D).y(y - size / 200D).fontSize(size / 100D)
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

    /**
     * Returns the current year as a string.
     *
     * @return The current year as a string.
     */
    private String getYear() {
        return new SimpleDateFormat("yyyy", Locale.US).format(new Date());
    }
}
