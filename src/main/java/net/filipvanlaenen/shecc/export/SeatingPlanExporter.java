package net.filipvanlaenen.shecc.export;

import java.util.Iterator;

import net.filipvanlaenen.kolektoj.ModifiableMap;
import net.filipvanlaenen.kolektoj.OrderedCollection;
import net.filipvanlaenen.shecc.HemicycleLayout;
import net.filipvanlaenen.shecc.ParliamentaryGroup;
import net.filipvanlaenen.shecc.RowConnectedSeatingPlan;
import net.filipvanlaenen.shecc.SeatPosition;
import net.filipvanlaenen.shecc.SeatStatus;
import net.filipvanlaenen.tsvgj.Circle;
import net.filipvanlaenen.tsvgj.ColorKeyword;
import net.filipvanlaenen.tsvgj.FontWeightValue;
import net.filipvanlaenen.tsvgj.G;
import net.filipvanlaenen.tsvgj.NoneValue;
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
     * The magic number three.
     */
    private static final int THREE = 3;
    /**
     * The magic number 180 for a straight angle.
     */
    private static final double STRAIGHT_ANGLE = 180D;
    /**
     * The factor used to move text down such that it appears vertically centered in the middle, relative to the font
     * size.
     */
    private static final double FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY = 1D / 3D;
    /**
     * The factor used to calculate the height of the legend based on the seat radius.
     */
    private static final double SEAT_RADIUS_TO_LEGEND_HEIGHT_FACTOR = 3D;
    /**
     * The factor used to calculate the gap between the seat symbol and the legend text based on the seat radius.
     */
    private static final double SEAT_RADIUS_TO_LEGEND_GAP_FACTOR = 1.5D;
    /**
     * The default ratio used to calculate the width of the slot in the legend for the names, based on the seat radius.
     */
    private static final int DEFAULT_SEAT_RADIUS_TO_LEGEND_LABEL_WIDTH_RATIO = 7;
    /**
     * Factor to calculate the stroke width based on a circle's radius.
     */
    private static final double RADIUS_TO_STROKE_FACTOR = 0.2D;
    /**
     * The opacity for a transparent seat.
     */
    private static final double SEMITRANSPARENT_SEAT_OPACITY = 0.3D;
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
     * The angle (in radians) of the hemicycle.
     */
    private Double angle;
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
     * Overrides the default legend label width ratio, defined in terms of number of seat radiuses.
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
     * @param plan The seating plan to be exported.
     * @return A string representing the seating plan in SVG.
     */
    public String export(final RowConnectedSeatingPlan plan) {
        HemicycleLayout layout = new HemicycleLayout(plan.getNumberOfSeats(), angle);
        double layoutWidth = layout.getWidth();
        double layoutHalfWidth = layoutWidth / 2D;
        double width = layoutWidth + 2 * EDGES_MARGIN;
        double halfWidth = width / 2D;
        double svgWidth = width * VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR;
        double hemicycleHeight = layout.getHeight();
        double canvasHeight = hemicycleHeight + 2 * EDGES_MARGIN;
        double seatRadius = layout.getRowWidth() * RADIUS_ROW_WIDTH_RATIO;
        OrderedCollection<ParliamentaryGroup> parliamentaryGroupsList = plan.getParliamentaryGroups();
        int noOfParliamentaryGroups = parliamentaryGroupsList.size();
        int noOfParliamentaryGroupLegendRows =
                1 + (int) (getLegendLabelWidthRatio() * seatRadius * noOfParliamentaryGroups / layoutWidth);
        int noOfLegendRows = noOfParliamentaryGroupLegendRows + (plan.hasUncertainSeats() ? 1 : 0);
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
            svg.addElement(createLegendGrouping(plan, layoutWidth, layoutHalfWidth, hemicycleHeight, seatRadius,
                    parliamentaryGroupsList, noOfParliamentaryGroupLegendRows, noOfLegendRows));
        }
        svg.addElement(createCopyrightNotice(customCopyrightNotice, halfWidth, canvasTopEdge, width, canvasHeight));
        return svg.asString();
    }

    /**
     * Creates a grouping with the legend.
     *
     * @param plan                             The seating plan for the hemicycle.
     * @param layoutWidth                      The width of the layout.
     * @param layoutHalfWidth                  Half of the width of the layout.
     * @param hemicycleHeight                  The height of the hemicycle.
     * @param seatRadius                       The seat radius.
     * @param parliamentaryGroupsList          The parliamentary groups.
     * @param noOfParliamentaryGroupLegendRows The number of legend rows for the parliamary groups.
     * @param noOfLegendRows                   The number of legend rows.
     * @return A grouping with the legend.
     */
    private G createLegendGrouping(final RowConnectedSeatingPlan plan, final double layoutWidth,
            final double layoutHalfWidth, final double hemicycleHeight, final double seatRadius,
            final OrderedCollection<ParliamentaryGroup> parliamentaryGroupsList,
            final int noOfParliamentaryGroupLegendRows, final int noOfLegendRows) {
        G parliamentaryGroupsLegendGrouping = new G();
        Iterator<ParliamentaryGroup> parliamentaryGroups = parliamentaryGroupsList.iterator();
        int legendSlotIndex = 0;
        int noOfParliamentaryGroups = parliamentaryGroupsList.size();
        int noOfSlotsPerLegendRow = noOfParliamentaryGroups / noOfParliamentaryGroupLegendRows;
        if (noOfParliamentaryGroups % noOfParliamentaryGroupLegendRows > 0) {
            noOfSlotsPerLegendRow += 1;
        }
        double legendSlotWidth = layoutWidth / noOfSlotsPerLegendRow;
        while (parliamentaryGroups.hasNext()) {
            G parliamentaryGroupGrouping = createLegendSlotGrouping(parliamentaryGroups.next(), layoutHalfWidth,
                    hemicycleHeight, seatRadius, noOfParliamentaryGroups, noOfParliamentaryGroupLegendRows,
                    legendSlotIndex, noOfSlotsPerLegendRow, legendSlotWidth);
            parliamentaryGroupsLegendGrouping.addElement(parliamentaryGroupGrouping);
            legendSlotIndex += 1;
        }
        if (plan.hasUncertainSeats()) {
            G seatStatusLegendGrouping = new G();
            double seatStatuslegendSlotWidth = layoutWidth / THREE;
            seatStatusLegendGrouping.addElement(
                    createCertainSeatsLegendSlotGrouping(layoutHalfWidth, hemicycleHeight, seatRadius, noOfLegendRows));
            seatStatusLegendGrouping.addElement(createLikelySeatsLegendSlotGrouping(layoutHalfWidth, hemicycleHeight,
                    seatRadius, noOfLegendRows, seatStatuslegendSlotWidth));
            seatStatusLegendGrouping.addElement(createUnlikelySeatsLegendSlotGrouping(layoutHalfWidth, hemicycleHeight,
                    seatRadius, noOfLegendRows, seatStatuslegendSlotWidth));
            G legendGrouping = new G();
            legendGrouping.addElement(parliamentaryGroupsLegendGrouping);
            legendGrouping.addElement(seatStatusLegendGrouping);
            return legendGrouping;
        } else {
            return parliamentaryGroupsLegendGrouping;
        }
    }

    /**
     * Creates a grouping with the legend slot for a parliamentary group.
     *
     * @param parliamentaryGroup      The parliamentary group for which a legend slot should be created.
     * @param layoutHalfWidth         Half of the width of the layout.
     * @param hemicycleHeight         The height of the hemicycle.
     * @param seatRadius              The seat radius.
     * @param noOfParliamentaryGroups The total number of parliamentary groups.
     * @param noOfLegendRows          The number of legend rows.
     * @param legendSlotIndex         The index of the legend slot to be created.
     * @param noOfSlotsPerLegendRow   The number of slots per legend row.
     * @param legendSlotWidth         The width for the legend slots.
     * @return A grouping with the legend slot for a parliamentary group.
     */
    private G createLegendSlotGrouping(final ParliamentaryGroup parliamentaryGroup, final double layoutHalfWidth,
            final double hemicycleHeight, final double seatRadius, final int noOfParliamentaryGroups,
            final int noOfLegendRows, final int legendSlotIndex, final int noOfSlotsPerLegendRow,
            final double legendSlotWidth) {
        G parliamentaryGroupGrouping = new G();
        int legendColumn = legendSlotIndex % noOfSlotsPerLegendRow;
        int legendRow = legendSlotIndex / noOfSlotsPerLegendRow;
        double x = -layoutHalfWidth + seatRadius + legendSlotWidth * legendColumn;
        if (legendRow == noOfLegendRows - 1) {
            x += (noOfSlotsPerLegendRow * noOfLegendRows - noOfParliamentaryGroups) * legendSlotWidth / 2D;
        }
        double y =
                -1D + hemicycleHeight + seatRadius * 2D + legendRow * SEAT_RADIUS_TO_LEGEND_HEIGHT_FACTOR * seatRadius;
        String character = parliamentaryGroup.getCharacter();
        double textY = y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY;
        if (character == null) {
            addColoredCircleOrSectors(parliamentaryGroupGrouping, x, y, seatRadius, parliamentaryGroup.getColors());
        } else {
            Text text = new Text(character).x(x).y(textY).fill(ColorKeyword.WHITE).fontSize(seatRadius)
                    .textAnchor(TextAnchorValue.MIDDLE);
            setFontFamilyUnlessNull(text);
            G seatGrouping = new G();
            addColoredCircleOrSectors(seatGrouping, x, y, seatRadius, parliamentaryGroup.getColors());
            seatGrouping.addElement(text);
            parliamentaryGroupGrouping.addElement(seatGrouping);
        }
        Text text = new Text(parliamentaryGroup.getName() + " (" + parliamentaryGroup.getSize().getFullSize() + ")")
                .x(x + SEAT_RADIUS_TO_LEGEND_GAP_FACTOR * seatRadius).y(textY).fontSize(seatRadius)
                .textAnchor(TextAnchorValue.START);
        text.fill(getFontColorOrZero());
        setFontFamilyUnlessNull(text);
        parliamentaryGroupGrouping.addElement(text);
        return parliamentaryGroupGrouping;
    }

    /**
     * Creates a grouping for the legend slot for the certain seats.
     *
     * @param layoutHalfWidth Half of the width of the layout.
     * @param hemicycleHeight The height of the hemicycle.
     * @param seatRadius      The seat radius.
     * @param noOfLegendRows  The number of legend rows.
     * @return A grouping containing the legend slot for the certain seats.
     */
    private G createCertainSeatsLegendSlotGrouping(final double layoutHalfWidth, final double hemicycleHeight,
            final double seatRadius, final int noOfLegendRows) {
        G certainSeatsLegendSlotGrouping = new G();
        double x = -layoutHalfWidth + seatRadius;
        double y = -1D + hemicycleHeight + seatRadius * 2D
                + (noOfLegendRows - 1) * SEAT_RADIUS_TO_LEGEND_HEIGHT_FACTOR * seatRadius;
        double textY = y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY;
        Text text = new Text("X").x(x).y(textY).fill(ColorKeyword.WHITE).fontSize(seatRadius)
                .textAnchor(TextAnchorValue.MIDDLE);
        setFontFamilyUnlessNull(text);
        G seatGrouping = new G();
        seatGrouping.addElement(createColoredCircle(x, y, seatRadius, getFontColorOrZero()));
        seatGrouping.addElement(text);
        certainSeatsLegendSlotGrouping.addElement(seatGrouping);
        Text legendText = new Text("Certain (P ≥ 97.5%)").x(x + SEAT_RADIUS_TO_LEGEND_GAP_FACTOR * seatRadius).y(textY)
                .fontSize(seatRadius).textAnchor(TextAnchorValue.START);
        legendText.fill(getFontColorOrZero());
        setFontFamilyUnlessNull(legendText);
        certainSeatsLegendSlotGrouping.addElement(legendText);
        return certainSeatsLegendSlotGrouping;
    }

    /**
     * Creates a grouping for the legend slot for the likely seats.
     *
     * @param layoutHalfWidth Half of the width of the layout.
     * @param hemicycleHeight The height of the hemicycle.
     * @param seatRadius      The seat radius.
     * @param noOfLegendRows  The number of legend rows.
     * @param legendSlotWidth The width for the legend slots.
     * @return A grouping containing the legend slot for the likely seats.
     */
    private G createLikelySeatsLegendSlotGrouping(final double layoutHalfWidth, final double hemicycleHeight,
            final double seatRadius, final int noOfLegendRows, final double legendSlotWidth) {
        G certainSeatsLegendSlotGrouping = new G();
        double x = -layoutHalfWidth + seatRadius + legendSlotWidth;
        double y = -1D + hemicycleHeight + seatRadius * 2D
                + (noOfLegendRows - 1) * SEAT_RADIUS_TO_LEGEND_HEIGHT_FACTOR * seatRadius;
        double textY = y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY;
        Text text = new Text("X").x(x).y(textY).fontSize(seatRadius).textAnchor(TextAnchorValue.MIDDLE);
        text.fill(getFontColorOrZero());
        setFontFamilyUnlessNull(text);
        G seatGrouping = new G();
        seatGrouping.addElement(createSemitransparentCircle(x, y, seatRadius, getFontColorOrZero()));
        seatGrouping.addElement(text);
        certainSeatsLegendSlotGrouping.addElement(seatGrouping);
        Text legendText = new Text("Likely (P ≥ 50%)").x(x + SEAT_RADIUS_TO_LEGEND_GAP_FACTOR * seatRadius).y(textY)
                .fontSize(seatRadius).textAnchor(TextAnchorValue.START);
        legendText.fill(getFontColorOrZero());
        setFontFamilyUnlessNull(legendText);
        certainSeatsLegendSlotGrouping.addElement(legendText);
        return certainSeatsLegendSlotGrouping;
    }

    /**
     * Creates a grouping for the legend slot for the unlikely seats.
     *
     * @param layoutHalfWidth Half of the width of the layout.
     * @param hemicycleHeight The height of the hemicycle.
     * @param seatRadius      The seat radius.
     * @param noOfLegendRows  The number of legend rows.
     * @param legendSlotWidth The width for the legend slots.
     * @return A grouping containing the legend slot for the unlikely seats.
     */
    private G createUnlikelySeatsLegendSlotGrouping(final double layoutHalfWidth, final double hemicycleHeight,
            final double seatRadius, final int noOfLegendRows, final double legendSlotWidth) {
        G certainSeatsLegendSlotGrouping = new G();
        double x = -layoutHalfWidth + seatRadius + legendSlotWidth * 2;
        double y = -1D + hemicycleHeight + seatRadius * 2D
                + (noOfLegendRows - 1) * SEAT_RADIUS_TO_LEGEND_HEIGHT_FACTOR * seatRadius;
        double textY = y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY;
        Text text = new Text("X").x(x).y(textY).fontSize(seatRadius).textAnchor(TextAnchorValue.MIDDLE);
        text.fill(getFontColorOrZero());
        setFontFamilyUnlessNull(text);
        G seatGrouping = new G();
        seatGrouping.addElement(createOutlinedCircle(x, y, seatRadius, getFontColorOrZero()));
        seatGrouping.addElement(text);
        certainSeatsLegendSlotGrouping.addElement(seatGrouping);
        Text legendText = new Text("Unlikely (P < 50%)").x(x + SEAT_RADIUS_TO_LEGEND_GAP_FACTOR * seatRadius).y(textY)
                .fontSize(seatRadius).textAnchor(TextAnchorValue.START);
        legendText.fill(getFontColorOrZero());
        setFontFamilyUnlessNull(legendText);
        certainSeatsLegendSlotGrouping.addElement(legendText);
        return certainSeatsLegendSlotGrouping;
    }

    /**
     * Creates the rectangle for the background for the diagram. The rectangle is horizontally centered around the Y
     * axis.
     *
     * @param width         The width of the canvas.
     * @param canvasHeight  The height of the canvas.
     * @param canvasTopEdge The y coordinate for the top edge of the canvas.
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
        Text text = new Text(title).x(0D).y(y).fontSize(TITLE_HEIGHT).fontWeight(FontWeightValue.BOLD)
                .textAnchor(TextAnchorValue.MIDDLE);
        text.fill(getFontColorOrZero());
        setFontFamilyUnlessNull(text);
        return text;
    }

    /**
     * Creates a text element for the subtitle.
     *
     * @return A text for the subtitle.
     */
    private Text createSubtitleText() {
        Text text = new Text(subtitle).x(0D).y(-1D - TITLE_MARGIN).fontSize(SUBTITLE_HEIGHT)
                .fontWeight(FontWeightValue.BOLD).textAnchor(TextAnchorValue.MIDDLE);
        text.fill(getFontColorOrZero());
        setFontFamilyUnlessNull(text);
        return text;
    }

    /**
     * Creates a grouping for a hemicycle and its content.
     *
     * @param layout     The layout for the hemicycle.
     * @param plan       The seating plan for the hemicycle.
     * @param seatRadius The seat radius.
     * @return A grouping for the hemicycle and its content.
     */
    private G createHemicycleGrouping(final HemicycleLayout layout, final RowConnectedSeatingPlan plan,
            final double seatRadius) {
        Iterator<SeatPosition> seatPositions = layout.getSeatPositions().iterator();
        G hemicycleGrouping = new G();
        ModifiableMap<ParliamentaryGroup, G> parliamentaryGroupGroupings = ModifiableMap.empty();
        int seatNumber = 0;
        while (seatPositions.hasNext()) {
            SeatPosition seatPosition = seatPositions.next();
            ParliamentaryGroup parliamentaryGroup = plan.getParliamentaryGroupAtSeat(seatNumber);
            if (!parliamentaryGroupGroupings.containsKey(parliamentaryGroup)) {
                G parliamentaryGroupGrouping = new G();
                parliamentaryGroupGroupings.add(parliamentaryGroup, parliamentaryGroupGrouping);
                hemicycleGrouping.addElement(parliamentaryGroupGrouping);
            }
            G parliamentaryGroupGrouping = parliamentaryGroupGroupings.get(parliamentaryGroup);
            double x = seatPosition.getX();
            double y = seatPosition.getY();
            SeatStatus seatStatus = plan.getSeatStatus(seatNumber);
            String character = parliamentaryGroup.getCharacter();
            if (character == null) {
                addDecoratedCircleOrSectors(parliamentaryGroupGrouping, x, -y, seatRadius,
                        parliamentaryGroup.getColors(), seatStatus);
            } else {
                G seatGroup = new G();
                addDecoratedCircleOrSectors(seatGroup, x, -y, seatRadius, parliamentaryGroup.getColors(), seatStatus);
                Text text = new Text(character).x(x).y(-y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY)
                        .fontSize(seatRadius).fill(ColorKeyword.WHITE).textAnchor(TextAnchorValue.MIDDLE);
                if (seatStatus == SeatStatus.CERTAIN) {
                    text.fill(ColorKeyword.WHITE);
                } else {
                    text.fill(parliamentaryGroup.getColors()[0]);
                }
                if (rotateLetters) {
                    double letterAngle = STRAIGHT_ANGLE * (Math.PI / 2D - seatPosition.angle()) / Math.PI;
                    text.transform(Transform.rotate(letterAngle, x, -y));
                }
                setFontFamilyUnlessNull(text);
                seatGroup.addElement(text);
                parliamentaryGroupGrouping.addElement(seatGroup);
            }
            seatNumber += 1;
        }
        return hemicycleGrouping;
    }

    /**
     * Creates a circle with a color.
     *
     * @param x      The x coordinate of the center.
     * @param y      The y coordinate of the center.
     * @param radius The radius.
     * @param color  The color.
     * @return A colored circle.
     */
    private Circle createColoredCircle(final double x, final double y, final double radius, final int color) {
        return new Circle().cx(x).cy(y).r(radius).fill(color);
    }

    /**
     * Creates a semi-transparent circle outlined with a color.
     *
     * @param x      The x coordinate of the center.
     * @param y      The y coordinate of the center.
     * @param radius The radius.
     * @param color  The color.
     * @return A colored circle.
     */
    private Circle createSemitransparentCircle(final double x, final double y, final double radius, final int color) {
        double strokeWidth = radius * RADIUS_TO_STROKE_FACTOR;
        return new Circle().cx(x).cy(y).r(radius - strokeWidth / 2D).fill(color)
                .fillOpacity(SEMITRANSPARENT_SEAT_OPACITY).stroke(color).strokeWidth(strokeWidth);
    }

    /**
     * Creates a circle outlined with a color.
     *
     * @param x      The x coordinate of the center.
     * @param y      The y coordinate of the center.
     * @param radius The radius.
     * @param color  The color.
     * @return A colored circle.
     */
    private Circle createOutlinedCircle(final double x, final double y, final double radius, final int color) {
        double strokeWidth = radius * RADIUS_TO_STROKE_FACTOR;
        return new Circle().cx(x).cy(y).r(radius - strokeWidth / 2D).fill(NoneValue.NONE).stroke(color)
                .strokeWidth(strokeWidth);
    }

    /**
     * Creates a grouping with colored sectors.
     *
     * @param x      The x coordinate of the center.
     * @param y      The y coordinate of the center.
     * @param radius The radius.
     * @param colors An array with the colors.
     * @return A grouping with colored sectors.
     */
    private G createColoredSectors(final double x, final double y, final double radius, final int[] colors) {
        G g = new G();
        for (int i = 0; i < colors.length; i++) {
            double angle1 = 2 * Math.PI * i / colors.length;
            double angle2 = 2 * Math.PI * (i + 1) / colors.length;
            double x1 = x + radius * Math.sin(angle1);
            double y1 = y - radius * Math.cos(angle1);
            double x2 = x + radius * Math.sin(angle2);
            double y2 = y - radius * Math.cos(angle2);
            Path path =
                    new Path().moveTo(x, y).lineTo(x1, y1).arcTo(radius, radius, 0, Path.LargeArcFlagValues.SMALL_ARC,
                            Path.SweepFlagValues.POSITIVE_ANGLE, x2, y2).closePath().fill(colors[i]);
            g.addElement(path);
        }
        return g;
    }

    /**
     * Creates a grouping with semi-transparent sectors.
     *
     * @param x      The x coordinate of the center.
     * @param y      The y coordinate of the center.
     * @param radius The radius.
     * @param colors An array with the colors.
     * @return A grouping with semi-transparent sectors.
     */
    private G createSemitransparentSectors(final double x, final double y, final double radius, final int[] colors) {
        G g = new G();
        for (int i = 0; i < colors.length; i++) {
            double angle1 = 2 * Math.PI * i / colors.length;
            double angle2 = 2 * Math.PI * (i + 1) / colors.length;
            double x1 = x + radius * Math.sin(angle1);
            double y1 = y - radius * Math.cos(angle1);
            double x2 = x + radius * Math.sin(angle2);
            double y2 = y - radius * Math.cos(angle2);
            Path fillPath = new Path()
                    .moveTo(x, y).lineTo(x1, y1).arcTo(radius, radius, 0, Path.LargeArcFlagValues.SMALL_ARC,
                            Path.SweepFlagValues.POSITIVE_ANGLE, x2, y2)
                    .closePath().fill(colors[i]).opacity(SEMITRANSPARENT_SEAT_OPACITY);
            g.addElement(fillPath);
            double strokeWidth = radius * RADIUS_TO_STROKE_FACTOR;
            double smallerRadius = radius - strokeWidth / 2D;
            x1 = x + smallerRadius * Math.sin(angle1);
            y1 = y - smallerRadius * Math.cos(angle1);
            x2 = x + smallerRadius * Math.sin(angle2);
            y2 = y - smallerRadius * Math.cos(angle2);
            Path strokePath = new Path().moveTo(x1, y1)
                    .arcTo(smallerRadius, smallerRadius, 0, Path.LargeArcFlagValues.SMALL_ARC,
                            Path.SweepFlagValues.POSITIVE_ANGLE, x2, y2)
                    .fill(NoneValue.NONE).stroke(colors[i]).strokeWidth(strokeWidth);
            g.addElement(strokePath);
        }
        return g;
    }

    /**
     * Creates a grouping with outlined sectors.
     *
     * @param x      The x coordinate of the center.
     * @param y      The y coordinate of the center.
     * @param radius The radius.
     * @param colors An array with the colors.
     * @return A grouping with outlined sectors.
     */
    private G createOutlinedSectors(final double x, final double y, final double radius, final int[] colors) {
        G g = new G();
        for (int i = 0; i < colors.length; i++) {
            double angle1 = 2 * Math.PI * i / colors.length;
            double angle2 = 2 * Math.PI * (i + 1) / colors.length;
            double strokeWidth = radius * RADIUS_TO_STROKE_FACTOR;
            double smallerRadius = radius - strokeWidth / 2D;
            double x1 = x + smallerRadius * Math.sin(angle1);
            double y1 = y - smallerRadius * Math.cos(angle1);
            double x2 = x + smallerRadius * Math.sin(angle2);
            double y2 = y - smallerRadius * Math.cos(angle2);
            Path strokePath = new Path().moveTo(x1, y1)
                    .arcTo(smallerRadius, smallerRadius, 0, Path.LargeArcFlagValues.SMALL_ARC,
                            Path.SweepFlagValues.POSITIVE_ANGLE, x2, y2)
                    .fill(NoneValue.NONE).stroke(colors[i]).strokeWidth(strokeWidth);
            g.addElement(strokePath);
        }
        return g;
    }

    /**
     * Adds a colored circle or a grouping with colored sectors, depending on the number of colors.
     *
     * @param g      The grouping to which the circle or the grouping with the sectors should be added.
     * @param x      The x coordinate of the center.
     * @param y      The y coordinate of the center.
     * @param radius The radius.
     * @param colors An array with the colors.
     */
    private void addColoredCircleOrSectors(final G g, final double x, final double y, final double radius,
            final int[] colors) {
        if (colors.length == 1) {
            g.addElement(createColoredCircle(x, y, radius, colors[0]));
        } else {
            g.addElement(createColoredSectors(x, y, radius, colors));
        }
    }

    /**
     * Adds a semi-transparent circle or a grouping with semi-transparent sectors, depending on the number of colors.
     *
     * @param g      The grouping to which the circle or the grouping with the sectors should be added.
     * @param x      The x coordinate of the center.
     * @param y      The y coordinate of the center.
     * @param radius The radius.
     * @param colors An array with the colors.
     */
    private void addSemitransparentCircleOrSectors(final G g, final double x, final double y, final double radius,
            final int[] colors) {
        if (colors.length == 1) {
            g.addElement(createSemitransparentCircle(x, y, radius, colors[0]));
        } else {
            g.addElement(createSemitransparentSectors(x, y, radius, colors));
        }
    }

    /**
     * Adds an outlined circle or a grouping with outlined sectors, depending on the number of colors.
     *
     * @param g      The grouping to which the circle or the grouping with the sectors should be added.
     * @param x      The x coordinate of the center.
     * @param y      The y coordinate of the center.
     * @param radius The radius.
     * @param colors An array with the colors.
     */
    private void addOutlinedCircleOrSectors(final G g, final double x, final double y, final double radius,
            final int[] colors) {
        if (colors.length == 1) {
            g.addElement(createOutlinedCircle(x, y, radius, colors[0]));
        } else {
            g.addElement(createOutlinedSectors(x, y, radius, colors));
        }
    }

    /**
     * Adds a decorated circle or a grouping with decorated sectors, depending on the number of colors and the status of
     * the seat.
     *
     * @param g          The grouping to which the circle or the grouping with the sectors should be added.
     * @param x          The x coordinate of the center.
     * @param y          The y coordinate of the center.
     * @param radius     The radius.
     * @param colors     An array with the colors.
     * @param seatStatus The status of the seat.
     */
    private void addDecoratedCircleOrSectors(final G g, final double x, final double y, final double radius,
            final int[] colors, final SeatStatus seatStatus) {
        switch (seatStatus) {
        case CERTAIN:
        default:
            addColoredCircleOrSectors(g, x, y, radius, colors);
            break;
        case LIKELY:
            addSemitransparentCircleOrSectors(g, x, y, radius, colors);
            break;
        case UNLIKELY:
            addOutlinedCircleOrSectors(g, x, y, radius, colors);
            break;
        }
    }

    /**
     * Returns the angle (in radians).
     *
     * @return The angle (in radians).
     */
    public Double getAngle() {
        return angle;
    }

    /**
     * Returns the legend label width ratio to be used, i.e. the provided one, or if no ratio has been provided, the
     * default one.
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

    /**
     * Specifies the angle (in degrees) of the hemicycle.
     *
     * @param angle The angle (in degrees) of the hemicycle.
     */
    public void setAngle(final double angle) {
        this.angle = Math.PI * angle / STRAIGHT_ANGLE;
    }

    /**
     * Specifies whether a legend should be displayed.
     *
     * @param displayLegend True if a legend should be displayed.
     */
    public void setDisplayLegend(final boolean displayLegend) {
        this.displayLegend = displayLegend;
    }

    /**
     * Specifies whether the letters should be rotated towards the center.
     *
     * @param rotateLetters True if the letters should be rotated towards the center.
     */
    void setRotateLetters(final boolean rotateLetters) {
        this.rotateLetters = rotateLetters;
    }

    /**
     * Specifies a custom copyright notice text.
     *
     * @param customCopyrightNotice The custom copyright notice text
     */
    public void setCustomCopyrightNotice(final String customCopyrightNotice) {
        this.customCopyrightNotice = customCopyrightNotice;
    }

    /**
     * Specifies the background color.
     *
     * @param backgroundColor The background color as an integer.
     */
    public void setBackgroundColor(final Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Specifies the title.
     *
     * @param title The title.
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Specifies the subtitle.
     *
     * @param subtitle The subtitle.
     */
    public void setSubtitle(final String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * Overrides the default legend label width ratio. The legend label width ratio is defined in terms of number of
     * seat radiuses.
     *
     * @param legendLabelWidthRatio The width of the legend labels in terms of number of seat radiuses.
     */
    public void setLegendLabelWidthRatio(final int legendLabelWidthRatio) {
        this.legendLabelWidthRatio = legendLabelWidthRatio;
    }
}
