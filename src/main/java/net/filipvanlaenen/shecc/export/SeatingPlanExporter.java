package net.filipvanlaenen.shecc.export;

import java.util.Iterator;
import java.util.List;

import net.filipvanlaenen.shecc.HemicycleLayout;
import net.filipvanlaenen.shecc.ParliamentaryGroup;
import net.filipvanlaenen.shecc.SeatPosition;
import net.filipvanlaenen.shecc.SeatStatus;
import net.filipvanlaenen.shecc.SeatingPlan;
import net.filipvanlaenen.tsvgj.Circle;
import net.filipvanlaenen.tsvgj.ColorKeyword;
import net.filipvanlaenen.tsvgj.FontWeightValues;
import net.filipvanlaenen.tsvgj.G;
import net.filipvanlaenen.tsvgj.Path;
import net.filipvanlaenen.tsvgj.Rect;
import net.filipvanlaenen.tsvgj.Svg;
import net.filipvanlaenen.tsvgj.Text;
import net.filipvanlaenen.tsvgj.TextAnchorValue;
import net.filipvanlaenen.tsvgj.Transform;

/**
 * A class exporting seating plans.
 */
public class SeatingPlanExporter extends Exporter {
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
            svg.addElement(createBackgroundRectangle(width, canvasHeight, canvasTopEdge));
        }
        if (title != null) {
            svg.addElement(createTitleText());
        }
        if (subtitle != null) {
            svg.addElement(createSubtitleText());
        }
        svg.addElement(createHemicycleGrouping(layout, plan, seatRadius));
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
                int legendColumn = legendPositionNumber % noOfSlotsPerLegendRow;
                int legendRow = legendPositionNumber / noOfSlotsPerLegendRow;
                double x = -layoutHalfWidth + seatRadius + legendSlotWidth * legendColumn;
                if (legendRow == noOfLegendRows - 1) {
                    x += (noOfSlotsPerLegendRow * noOfLegendRows - noOfParliamentaryGroups) * legendSlotWidth / 2D;
                }
                double y = -1D + hemicycleHeight + seatRadius * 2D
                        + legendRow * SEAT_RADIUS_TO_LEGEND_HEIGHT_FACTOR * seatRadius;
                String character = parliamentaryGroup.getCharacter();
                double textY = y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY;
                if (character == null) {
                    addColoredCircleOrSectors(parliamentaryGroupGrouping, x, y, seatRadius,
                            parliamentaryGroup.getColors(), 1D);
                } else {
                    Text text = new Text(character).x(x).y(textY).fill(ColorKeyword.WHITE).fontSize(seatRadius)
                            .textAnchor(TextAnchorValue.MIDDLE);
                    if (fontFamily != null) {
                        text.fontFamily(fontFamily);
                    }
                    G seatGrouping = new G();
                    addColoredCircleOrSectors(seatGrouping, x, y, seatRadius, parliamentaryGroup.getColors(), 1D);
                    seatGrouping.addElement(text);
                    parliamentaryGroupGrouping.addElement(seatGrouping);
                }
                Text text = new Text(
                        parliamentaryGroup.getName() + " (" + parliamentaryGroup.getSize().getFullSize() + ")")
                                .x(x + SEAT_RADIUS_TO_LEGEND_GAP_FACTOR * seatRadius).y(textY).fontSize(seatRadius)
                                .textAnchor(TextAnchorValue.START);
                if (fontColor == null) {
                    text.fill(ColorKeyword.BLACK);
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
     * Creates the rectangle for the background for the diagram. The rectangle is
     * horizontally centered around the Y axis.
     *
     * @param width
     *            The width of the canvas.
     * @param canvasHeight
     *            The height of the canvas
     * @param canvasTopEdge
     *            The y coordinate for the top edge of the canvas.
     * @return A rectangle that serves as the background for the diagram.
     */
    private Rect createBackgroundRectangle(final double width, final double canvasHeight, final double canvasTopEdge) {
        return new Rect().x(-width / 2D).y(canvasTopEdge).width(width).height(canvasHeight).fill(backgroundColor);
    }

    /**
     * Creates a text element for the title.
     *
     * @return A text for the title.
     */
    private Text createTitleText() {
        double y = -1D - TITLE_MARGIN - (subtitle != null ? SUBTITLE_HEIGHT + TITLE_HEIGHT : 0D);
        Text text = new Text(title).x(0D).y(y).fontSize(TITLE_HEIGHT).fontWeight(FontWeightValues.BOLD)
                .textAnchor(TextAnchorValue.MIDDLE);
        if (fontColor == null) {
            text.fill(ColorKeyword.BLACK);
        } else {
            text.fill(fontColor);
        }
        if (fontFamily != null) {
            text.fontFamily(fontFamily);
        }
        return text;
    }

    /**
     * Creates a text element for the subtitle.
     *
     * @return A text for the subtitle.
     */
    private Text createSubtitleText() {
        Text text = new Text(subtitle).x(0D).y(-1D - TITLE_MARGIN).fontSize(SUBTITLE_HEIGHT)
                .fontWeight(FontWeightValues.BOLD).textAnchor(TextAnchorValue.MIDDLE);
        if (fontColor == null) {
            text.fill(ColorKeyword.BLACK);
        } else {
            text.fill(fontColor);
        }
        if (fontFamily != null) {
            text.fontFamily(fontFamily);
        }
        return text;
    }

    /**
     * Creates a grouping for a hemicycle and its content.
     *
     * @param layout
     *            The layout for the hemicycle.
     * @param plan
     *            The seating plan for the hemicycle.
     * @param seatRadius
     *            The seat radius.
     * @return A grouping for the hemicycle and its content.
     */
    private G createHemicycleGrouping(final HemicycleLayout layout, final SeatingPlan plan, final double seatRadius) {
        Iterator<SeatPosition> seatPositions = layout.getSeatPositions().iterator();
        G hemicycleGrouping = new G();
        int seatNumber = 0;
        while (seatPositions.hasNext()) {
            SeatPosition seatPosition = seatPositions.next();
            ParliamentaryGroup parliamentaryGroup = plan.getParliamentaryGroupAtSeat(seatNumber);
            double x = seatPosition.getX();
            double y = seatPosition.getY();
            SeatStatus seatStatus = plan.getSeatStatus(seatNumber);
            addColoredCircleOrSectors(hemicycleGrouping, x, -y, seatRadius, parliamentaryGroup.getColors(),
                    seatStatus.getOpacity());
            String character = parliamentaryGroup.getCharacter();
            if (character != null) {
                Text text = new Text(character).x(x).y(-y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY)
                        .fontSize(seatRadius).fill(ColorKeyword.WHITE).textAnchor(TextAnchorValue.MIDDLE);
                if (rotateLetters) {
                    double angle = STRAIGHT_ANGLE * (Math.PI / 2D - seatPosition.getAngle()) / Math.PI;
                    text.transform(Transform.rotate(angle, x, -y));
                }
                if (fontFamily != null) {
                    text.fontFamily(fontFamily);
                }
                hemicycleGrouping.addElement(text);
            }
            seatNumber += 1;
        }
        return hemicycleGrouping;
    }

    /**
     * Creates a circle with a color.
     *
     * @param x
     *            The x coordinate of the center.
     * @param y
     *            The y coordinate of the center.
     * @param radius
     *            The radius.
     * @param color
     *            The color.
     * @param opacity
     *            The opacity.
     * @return A colored circle.
     */
    private Circle createColoredCircle(final double x, final double y, final double radius, final int color,
            final double opacity) {
        Circle circle = new Circle().cx(x).cy(y).r(radius).fill(color);
        if (opacity != 1D) {
            circle.opacity(opacity);
        }
        return circle;
    }

    /**
     * Creates a grouping with colored sectors.
     *
     * @param x
     *            The x coordinate of the center.
     * @param y
     *            The y coordinate of the center.
     * @param radius
     *            The radius.
     * @param colors
     *            An array with the colors.
     * @param opacity
     *            The opacity.
     * @return A grouping with colored sectors.
     */
    private G createColoredSectors(final double x, final double y, final double radius, final int[] colors,
            final double opacity) {
        G g = new G();
        for (int i = 0; i < colors.length; i++) {
            double angle1 = 2 * Math.PI * i / colors.length;
            double angle2 = 2 * Math.PI * (i + 1) / colors.length;
            double x1 = x + radius * Math.sin(angle1);
            double y1 = y - radius * Math.cos(angle1);
            double x2 = x + radius * Math.sin(angle2);
            double y2 = y - radius * Math.cos(angle2);
            Path path = new Path().moveTo(x, y).lineTo(x1, y1).arcTo(radius, radius, 0,
                    Path.LargeArcFlagValues.SMALL_ARC, Path.SweepFlagValues.POSITIVE_ANGLE, x2, y2).closePath()
                    .fill(colors[i]);
            if (opacity != 1D) {
                path.opacity(opacity);
            }
            g.addElement(path);
        }
        return g;
    }

    /**
     * Adds a colored circle or a grouping with colored sectors, depending on the
     * number of colors.
     *
     * @param g
     *            The grouping to which the circle or the grouping with the sectors
     *            should be added.
     * @param x
     *            The x coordinate of the center.
     * @param y
     *            The y coordinate of the center.
     * @param radius
     *            The radius.
     * @param colors
     *            An array with the colors.
     * @param opacity
     *            The opacity.
     */
    private void addColoredCircleOrSectors(final G g, final double x, final double y, final double radius,
            final int[] colors, final double opacity) {
        if (colors.length == 1) {
            g.addElement(createColoredCircle(x, y, radius, colors[0], opacity));
        } else {
            g.addElement(createColoredSectors(x, y, radius, colors, opacity));
        }
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
