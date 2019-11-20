package net.filipvanlaenen.shecc.export;

import java.util.Iterator;
import java.util.List;

import net.filipvanlaenen.shecc.HemicycleLayout;
import net.filipvanlaenen.shecc.ParliamentaryGroup;
import net.filipvanlaenen.shecc.SeatPosition;
import net.filipvanlaenen.shecc.SeatStatus;
import net.filipvanlaenen.shecc.SeatingPlan;
import net.filipvanlaenen.tsvgj.Circle;
import net.filipvanlaenen.tsvgj.FontWeightValues;
import net.filipvanlaenen.tsvgj.G;
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
     * The factor used to calculate the gap between the seat symbol and the legend
     * text based on the seat radius.
     */
    private static final double SEAT_RADIUS_TO_LEGEND_GAP_FACTOR = 1.5D;
    /**
     * The default ratio used to calculate the width of the slot in the legend for
     * the names, based on the seat radius.
     */
    private static final int DEFAULT_SEAT_RADIUS_TO_LEGEND_LABEL_WIDTH_RATIO = 6;
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
    private static final double TITLE_MARGIN = 0.05D;

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
     * Overrides the default legend label width ratio, defined in terms of number of
     * seat radiuses.
     */
    private Integer legendLabelWidthRatio;
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
        double layoutWidth = layout.getWidth();
        double layoutHalfWidth = layoutWidth / 2D;
        double width = layoutWidth + 2 * EDGES_MARGIN;
        double halfWidth = width / 2D;
        double svgWidth = width * VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR;
        double hemicycleHeight = layout.getHeight();
        double canvasHeight = hemicycleHeight + 2 * EDGES_MARGIN;
        double seatRadius = layout.getRowWidth() * RADIUS_ROW_WIDTH_RATIO;
        List<ParliamentaryGroup> parliamentaryGroupsList = plan.getParliamentaryGroups();
        int noOfParliamentaryGroups = parliamentaryGroupsList.size();
        int noOfLegendRows = 1
                + (int) (getLegendLabelWidthRatio() * seatRadius * noOfParliamentaryGroups / layoutWidth);
        if (displayLegend) {
            canvasHeight += seatRadius * SEAT_RADIUS_TO_LEGEND_HEIGHT_FACTOR * noOfLegendRows;
        }
        double canvasTopEdge = -1D - EDGES_MARGIN;
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
            Text text = new Text(title).x(0D).y(y).fontSize(TITLE_HEIGHT).fontWeight(FontWeightValues.BOLD)
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
        if (subtitle != null) {
            Text text = new Text(subtitle).x(0D).y(-1D - TITLE_MARGIN).fontSize(SUBTITLE_HEIGHT)
                    .fontWeight(FontWeightValues.BOLD).textAnchor(TextAnchorValues.MIDDLE);
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
            Circle circle = new Circle().cx(x).cy(-y).r(seatRadius).fill(color);
            SeatStatus seatStatus = plan.getSeatStatus(seatNumber);
            if (seatStatus == SeatStatus.LIKELY) {
                circle.opacity(0.5D);
            } else if (seatStatus == SeatStatus.UNCERTAIN) {
                circle.opacity(0.2D);
            }
            svg.addElement(circle);
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
            Iterator<ParliamentaryGroup> parliamentaryGroups = parliamentaryGroupsList.iterator();
            int legendPositionNumber = 0;
            int noOfSlotsPerLegendRow = noOfParliamentaryGroups / noOfLegendRows;
            if (noOfParliamentaryGroups % noOfLegendRows > 0) {
                noOfSlotsPerLegendRow += 1;
            }
            double legendSlotWidth = layoutWidth / noOfSlotsPerLegendRow;
            while (parliamentaryGroups.hasNext()) {
                G parliamentaryGroupGrouping = new G();
                ParliamentaryGroup parliamentaryGroup = parliamentaryGroups.next();
                int color = parliamentaryGroup.getColor();
                int legendColumn = legendPositionNumber % noOfSlotsPerLegendRow;
                int legendRow = legendPositionNumber / noOfSlotsPerLegendRow;
                double x = -layoutHalfWidth + seatRadius + legendSlotWidth * legendColumn;
                if (legendRow == noOfLegendRows - 1) {
                    x += (noOfSlotsPerLegendRow * noOfLegendRows - noOfParliamentaryGroups) * legendSlotWidth / 2D;
                }
                double y = -1D + hemicycleHeight + seatRadius * 2D
                        + legendRow * SEAT_RADIUS_TO_LEGEND_HEIGHT_FACTOR * seatRadius;
                Circle circle = new Circle().cx(x).cy(y).r(seatRadius).fill(color);
                String character = parliamentaryGroup.getCharacter();
                double textY = y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY;
                if (character == null) {
                    parliamentaryGroupGrouping.addElement(circle);
                } else {
                    Text text = new Text(character).x(x).y(textY).fill(WHITE).fontSize(seatRadius)
                            .textAnchor(TextAnchorValues.MIDDLE);
                    if (fontFamily != null) {
                        text.fontFamily(fontFamily);
                    }
                    G seatGrouping = new G();
                    seatGrouping.addElement(circle);
                    seatGrouping.addElement(text);
                    parliamentaryGroupGrouping.addElement(seatGrouping);
                }
                Text text = new Text(
                        parliamentaryGroup.getName() + " (" + parliamentaryGroup.getSize().getFullSize() + ")")
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
                parliamentaryGroupGrouping.addElement(text);
                svg.addElement(parliamentaryGroupGrouping);
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

    /**
     * Overrides the default legend label width ratio. The legend label width ratio
     * is defined in terms of number of seat radiuses.
     *
     * @param legendLabelWidthRatio
     *            The width of the legend labels in terms of number of seat
     *            radiuses.
     */
    public void setLegendLabelWidthRatio(final int legendLabelWidthRatio) {
        this.legendLabelWidthRatio = legendLabelWidthRatio;
    }

    /**
     * Returns the legend label width ratio to be used, i.e. the provided one, or if
     * no ratio has been provided, the default one.
     *
     * @return The legend label width ratio to be used.
     */
    private int getLegendLabelWidthRatio() {
        if (legendLabelWidthRatio == null) {
            return DEFAULT_SEAT_RADIUS_TO_LEGEND_LABEL_WIDTH_RATIO;
        } else {
            return legendLabelWidthRatio;
        }
    }
}
