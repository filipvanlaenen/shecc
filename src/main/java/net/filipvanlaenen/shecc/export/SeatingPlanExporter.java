package net.filipvanlaenen.shecc.export;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.filipvanlaenen.shecc.HemicycleLayout;
import net.filipvanlaenen.shecc.ParliamentaryGroup;
import net.filipvanlaenen.shecc.SeatPosition;
import net.filipvanlaenen.shecc.SeatingPlan;
import net.filipvanlaenen.shecc.export.svg.Circle;
import net.filipvanlaenen.shecc.export.svg.Svg;
import net.filipvanlaenen.shecc.export.svg.Text;
import net.filipvanlaenen.shecc.export.svg.TextAnchorValues;

/**
 * A class exporting seating plans.
 */
public class SeatingPlanExporter {
    /**
     * Magic number for the color white.
     */
    private static final int WHITE = 0xFFFFFF;
    /**
     * Magic number for the color black.
     */
    private static final int BLACK = 0x000000;
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
    private static final double FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY = 0.333333D;
    /**
     * The decimal format.
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.######",
            DecimalFormatSymbols.getInstance(Locale.US));
    /**
     * Specifies whether a legend should be displayed.
     */
    private boolean displayLegend;
    /**
     * Specifies whether the letters should be rotated towards the center.
     */
    private boolean rotateLetters;

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
            canvasHeight +=  seatRadius * 3D;
        }
        double svgHeight = canvasHeight * VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR;
        Svg svg = new Svg().width(svgWidth).height(svgHeight).viewBox(-halfWidth, -1, width, canvasHeight);
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
                    double angle = 180D * (Math.PI / 2D - seatPosition.getAngle()) / Math.PI;
                    text.transform("rotate(" + DECIMAL_FORMAT.format(angle) + " " + DECIMAL_FORMAT.format(x) + ","
                            + DECIMAL_FORMAT.format(-y) + ")");
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
            double y = -1 + hemicycleHeight + seatRadius * 2D;
            while (parliamentaryGroups.hasNext()) {
                ParliamentaryGroup parliamentaryGroup = parliamentaryGroups.next();
                int color = parliamentaryGroup.getColor();
                double x = -halfWidth + seatRadius + width * legendPositionNumber / noOfParliamentaryGroups;
                svg.addElement(new Circle().cx(x).cy(y).r(seatRadius).fill(color));
                String character = parliamentaryGroup.getCharacter();
                double textY = y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY;
                if (character != null) {
                    Text text = new Text(character).x(x).y(textY).fontSize(seatRadius).fill(WHITE)
                            .textAnchor(TextAnchorValues.MIDDLE);
                    svg.addElement(text);
                }
                Text text = new Text(parliamentaryGroup.getName()).x(x + 1.5D * seatRadius).y(textY).fontSize(seatRadius)
                        .fill(BLACK).textAnchor(TextAnchorValues.START);
                svg.addElement(text);
                legendPositionNumber += 1;
            }
        }
        return svg.asString();
    }

    /**
     * Specifies whether a legend should be displayed.
     *
     * @param displayLegend
     *            True if a legend should be displayed.
     */
    void setDisplayLegend(final boolean displayLegend) {
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
}
