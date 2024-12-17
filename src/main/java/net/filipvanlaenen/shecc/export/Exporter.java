package net.filipvanlaenen.shecc.export;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.filipvanlaenen.tsvgj.ColorKeyword;
import net.filipvanlaenen.tsvgj.Text;
import net.filipvanlaenen.tsvgj.TextAnchorValue;
import net.filipvanlaenen.tsvgj.Transform;

/**
 * Abstract superclass for exporter classes. Contains common methods shared by all exporters.
 */
abstract class Exporter {
    /**
     * The rotation angle for the copyright notice.
     */
    private static final double COPYRIGHT_NOTICE_ROTATION_ANGLE = 270D;
    /**
     * The ratio between the seat circle radius and the row width.
     */
    protected static final double RADIUS_ROW_WIDTH_RATIO = 0.45D;
    /**
     * The factor to scale up the view box to the SVG dimensions.
     */
    protected static final double VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR = 1000D;
    /**
     * The margin between the actual drawing and the edges of the SVG document.
     */
    protected static final double EDGES_MARGIN = 0.05D;
    /**
     * The magic number one hundred.
     */
    private static final double ONE_HUNDRED = 100D;
    /**
     * The magic number two hundred.
     */
    private static final double TWO_HUNDRED = 200D;

    /**
     * The font color as an integer.
     */
    private Integer fontColor;
    /**
     * The font family.
     */
    private String fontFamily;

    /**
     * Creates a copyright notice. If no custom notice is provided, only a message telling the chart was produced by
     * SHecC will be created.
     *
     * @param customNotice Custom copyright notice.
     * @param x            The x coordinate of the top right corner of the view box.
     * @param y            The y coordinate of the top right corner of the view box.
     * @param width        The width of the view box.
     * @param height       The height of the view box.
     * @return A text element containing the copyright notice.
     */
    protected Text createCopyrightNotice(final String customNotice, final double x, final double y, final double width,
            final double height) {
        double size = Math.max(width, height);
        String fullNotice = customNotice == null ? "Chart produced using SHecC"
                : "© " + getYear() + " " + customNotice + ", chart produced using SHecC";
        Text text = new Text(fullNotice).x(x - size / TWO_HUNDRED).y(y - size / TWO_HUNDRED)
                .fontSize(size / ONE_HUNDRED).textAnchor(TextAnchorValue.END)
                .transform(Transform.rotate(COPYRIGHT_NOTICE_ROTATION_ANGLE, x, y));
        if (fontColor == null) {
            text.fill(ColorKeyword.BLACK);
        } else {
            text.fill(fontColor);
        }
        setFontFamilyUnlessNull(text);
        return text;
    }

    /**
     * Returns the font color, or zero if the font color is <code>null</code>.
     *
     * @return The font color, or zero if the font color is <code>null</code>.
     */
    protected Integer getFontColorOrZero() {
        return fontColor == null ? 0 : fontColor;
    }

    /**
     * Returns the current year as a string.
     *
     * @return The current year as a string.
     */
    private String getYear() {
        return new SimpleDateFormat("yyyy", Locale.US).format(new Date());
    }

    /**
     * Specifies the font color.
     *
     * @param fontColor The font color as an integer.
     */
    public void setFontColor(final Integer fontColor) {
        this.fontColor = fontColor;
    }

    /**
     * Specifies the font family.
     *
     * @param fontFamily The name of the font family.
     */
    public void setFontFamily(final String fontFamily) {
        this.fontFamily = fontFamily;
    }

    /**
     * Sets the font family on a text element, unless the font family is <code>null</code>.
     *
     * @param text The text element for which the font family should be set.
     */
    protected void setFontFamilyUnlessNull(final Text text) {
        if (fontFamily != null) {
            text.fontFamily(fontFamily);
        }
    }
}
