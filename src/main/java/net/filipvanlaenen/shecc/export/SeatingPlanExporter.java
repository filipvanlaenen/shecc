package net.filipvanlaenen.shecc.export;

import java.util.Iterator;
import java.util.List;

import net.filipvanlaenen.shecc.HemicycleLayout;
import net.filipvanlaenen.shecc.ParliamentaryGroup;
import net.filipvanlaenen.shecc.SeatPosition;
import net.filipvanlaenen.shecc.SeatingPlan;
import net.filipvanlaenen.tsvgj.Circle;
import net.filipvanlaenen.tsvgj.Rect;
import net.filipvanlaenen.tsvgj.Svg;
import net.filipvanlaenen.tsvgj.Text;
import net.filipvanlaenen.tsvgj.TextAnchorValues;
import net.filipvanlaenen.tsvgj.Transform;

/**
 * A class exporting seating plans.
 */
public class SeatingPlanExporter extends Exporter {
    /**
     * Magic number for the color white.
     */
    private static final int WHITE = 0xFFFFFF;
    /**
     * The magic number 180 for a straight angle.
     */
    private static final double STRAIGHT_ANGLE = 180D;
    /**
     * The ratio between the seat circle radius and the row width.
     */
    private static final double RADIUS_ROW_WIDTH_RATIO = 0.45D;
    /**
     * The factor to scale up the view box to the SVG dimensions.
     */
    private static final double VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR = 1000D;
    /**
     * The factor used to move text down such that it appears vertically centered in
     * the middle, relative to the font size.
     */
    private static final double FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY = 1D / 3D;
    /**
     * The factor used to calculate the height of the legend based on the seat
     * radius.
     */
    private static final double SEAT_RADIUS_TO_LEGEND_HEIGHT_FACTOR = 3D;
    /**
     * The factor used to calculate the gap between of the seat symbol and the
     * legend text based on the seat radius.
     */
    private static final double SEAT_RADIUS_TO_LEGEND_GAP_FACTOR = 1.5D;
    /**
     * The height of the title.
     */
    private static final double TITLE_HEIGHT = 0.05D;
    /**
     * The height of the subtitle.
     */
    private static final double SUBTITLE_HEIGHT = TITLE_HEIGHT * 0.7D;
    /**
     * The margin between the layout and the title.
     */
    private static final double TITLE_MARGIN = 0.02D;

    /**
     * The background color as an integer.
     */
    private Integer backgroundColor;
    /**
     * A custom copyright notice text.
     */
    private String customCopyrightNotice;
    /**
     * Specifies whether a legend should be displayed.
     */
    private boolean displayLegend;
    /**
     * Specifies whether the letters should be rotated towards the center.
     */
    private boolean rotateLetters;
    /**
     * A title.
     */
    private String title;
    /**
     * A subtitle.
     */
    private String subtitle;

    /**
     * Exports a seating plan to SVG.
     *
     * @param plan
     *            The seating plan to be exported.
     * @return A string representing the seating plan in SVG.
     */
    public String export(final SeatingPlan plan) {
        HemicycleLayout layout = new HemicycleLayout(plan.getNoOfSeats());
        double width = layout.getWidth();
        double halfWidth = width / 2D;
        double svgWidth = width * VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR;
        double hemicycleHeight = layout.getHeight();
        double canvasHeight = hemicycleHeight;
        double seatRadius = layout.getRowWidth() * RADIUS_ROW_WIDTH_RATIO;
        if (displayLegend) {
            canvasHeight += seatRadius * SEAT_RADIUS_TO_LEGEND_HEIGHT_FACTOR;
        }
        double canvasTopEdge = -1D;
        if (title != null) {
            double titleSpace = TITLE_HEIGHT + TITLE_MARGIN;
            canvasTopEdge -= titleSpace;
            canvasHeight += titleSpace;
            if (subtitle != null) {
                double subtitleSpace = SUBTITLE_HEIGHT + TITLE_MARGIN;
                canvasTopEdge -= subtitleSpace;
                canvasHeight += subtitleSpace;
            }
        }
        double svgHeight = canvasHeight * VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR;
        Svg svg = new Svg().width(svgWidth).height(svgHeight).viewBox(-halfWidth, canvasTopEdge, width, canvasHeight);
        if (backgroundColor != null) {
            svg.addElement(
                    new Rect().x(-halfWidth).y(canvasTopEdge).width(width).height(canvasHeight).fill(backgroundColor));
        }
        if (title != null) {
            double y = -1D - TITLE_MARGIN - (subtitle != null ? SUBTITLE_HEIGHT + TITLE_HEIGHT : 0D);
            Text text = new Text(title).x(0D).y(y).fontSize(TITLE_HEIGHT).textAnchor(TextAnchorValues.MIDDLE);
            if (fontColor == null) {
                text.fill(BLACK);
            } else {
                text.fill(fontColor);
            }
            if (fontFamily != null) {
                text.fontFamily(fontFamily);
            }
            svg.addElement(text);
        }
        if (subtitle != null) {
            Text text = new Text(subtitle).x(0D).y(-1D - TITLE_MARGIN).fontSize(SUBTITLE_HEIGHT)
                    .textAnchor(TextAnchorValues.MIDDLE);
            if (fontColor == null) {
                text.fill(BLACK);
            } else {
                text.fill(fontColor);
            }
            if (fontFamily != null) {
                text.fontFamily(fontFamily);
            }
            svg.addElement(text);
        }
        Iterator<SeatPosition> seatPositions = layout.getSeatPositions().iterator();
        int seatNumber = 0;
        while (seatPositions.hasNext()) {
            SeatPosition seatPosition = seatPositions.next();
            ParliamentaryGroup parliamentaryGroup = plan.getParliamentaryGroupAtSeat(seatNumber);
            int color = parliamentaryGroup.getColor();
            double x = seatPosition.getX();
            double y = seatPosition.getY();
            svg.addElement(new Circle().cx(x).cy(-y).r(seatRadius).fill(color));
            String character = parliamentaryGroup.getCharacter();
            if (character != null) {
                Text text = new Text(character).x(x).y(-y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY)
                        .fontSize(seatRadius).fill(WHITE).textAnchor(TextAnchorValues.MIDDLE);
                if (rotateLetters) {
                    double angle = STRAIGHT_ANGLE * (Math.PI / 2D - seatPosition.getAngle()) / Math.PI;
                    text.transform(Transform.rotate(angle, x, -y));
                }
                if (fontFamily != null) {
                    text.fontFamily(fontFamily);
                }
                svg.addElement(text);
            }
            seatNumber += 1;
        }
        if (displayLegend) {
            List<ParliamentaryGroup> parliamentaryGroupsList = plan.getParliamentaryGroups();
            int noOfParliamentaryGroups = parliamentaryGroupsList.size();
            Iterator<ParliamentaryGroup> parliamentaryGroups = parliamentaryGroupsList.iterator();
            int legendPositionNumber = 0;
            double y = -1D + hemicycleHeight + seatRadius * 2D;
            while (parliamentaryGroups.hasNext()) {
                ParliamentaryGroup parliamentaryGroup = parliamentaryGroups.next();
                int color = parliamentaryGroup.getColor();
                double x = -halfWidth + seatRadius + width * legendPositionNumber / noOfParliamentaryGroups;
                svg.addElement(new Circle().cx(x).cy(y).r(seatRadius).fill(color));
                String character = parliamentaryGroup.getCharacter();
                double textY = y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY;
                if (character != null) {
                    Text text = new Text(character).x(x).y(textY).fill(WHITE).fontSize(seatRadius)
                            .textAnchor(TextAnchorValues.MIDDLE);
                    if (fontFamily != null) {
                        text.fontFamily(fontFamily);
                    }
                    svg.addElement(text);
                }
                Text text = new Text(parliamentaryGroup.getName() + " (" + parliamentaryGroup.getSize() + ")")
                        .x(x + SEAT_RADIUS_TO_LEGEND_GAP_FACTOR * seatRadius).y(textY).fontSize(seatRadius)
                        .textAnchor(TextAnchorValues.START);
                if (fontColor == null) {
                    text.fill(BLACK);
                } else {
                    text.fill(fontColor);
                }
                if (fontFamily != null) {
                    text.fontFamily(fontFamily);
                }
                svg.addElement(text);
                legendPositionNumber += 1;
            }
        }
        svg.addElement(createCopyrightNotice(customCopyrightNotice, halfWidth, canvasTopEdge, width, canvasHeight));
        return svg.asString();
    }

    /**
     * Specifies whether a legend should be displayed.
     *
     * @param displayLegend
     *            True if a legend should be displayed.
     */
    public void setDisplayLegend(final boolean displayLegend) {
        this.displayLegend = displayLegend;
    }

    /**
     * Specifies whether the letters should be rotated towards the center.
     *
     * @param rotateLetters
     *            True if the letters should be rotated towards the center.
     */
    void setRotateLetters(final boolean rotateLetters) {
        this.rotateLetters = rotateLetters;
    }

    /**
     * Specifies a custom copyright notice text.
     *
     * @param customCopyrightNotice
     *            The custom copyright notice text
     */
    public void setCustomCopyrightNotice(final String customCopyrightNotice) {
        this.customCopyrightNotice = customCopyrightNotice;
    }

    /**
     * Specifies the background color.
     *
     * @param backgroundColor
     *            The background color as an integer.
     */
    public void setBackgroundColor(final Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Specifies the title.
     *
     * @param title
     *            The title.
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Specifies the subtitle.
     *
     * @param subtitle
     *            The subtitle.
     */
    public void setSubtitle(final String subtitle) {
        this.subtitle = subtitle;
    }
}
