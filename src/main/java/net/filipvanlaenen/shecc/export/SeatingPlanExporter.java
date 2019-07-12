package net.filipvanlaenen.shecc.export;

import java.util.Iterator;

import net.filipvanlaenen.shecc.HemicycleLayout;
import net.filipvanlaenen.shecc.ParliamentaryGroup;
import net.filipvanlaenen.shecc.SeatPosition;
import net.filipvanlaenen.shecc.SeatingPlan;
import net.filipvanlaenen.shecc.export.svg.Circle;
import net.filipvanlaenen.shecc.export.svg.Svg;
import net.filipvanlaenen.shecc.export.svg.Text;

/**
 * A class exporting seating plans.
 */
public class SeatingPlanExporter {
    /**
     * Magic number for the color white.
     */
    private static final int WHITE = 0xFFFFFF;
    /**
     * The ratio between the seat circle radius and the row width.
     */
    private static final double RADIUS_ROW_WIDTH_RATIO = 0.45D;
    /**
     * The factor to scale up the view box to the SVG dimensions.
     */
    private static final double VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR = 100D;

    /**
     * The factor used to move text down such that it appears vertically centered in
     * the middle, relative to the font size.
     */
    private static final double FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY = 0.333333D;

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
        double height = layout.getHeight();
        double svgHeight = height * VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR;
        Svg svg = new Svg().width(svgWidth).height(svgHeight).viewBox(-halfWidth, -1, width, height);
        double seatRadius = layout.getRowWidth() * RADIUS_ROW_WIDTH_RATIO;
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
                svg.addElement(new Text(character).x(x).y(-y + seatRadius * FONT_SIZE_FACTOR_TO_CENTER_VERTICALLY)
                        .fontSize(seatRadius).fill(WHITE).textAnchor("middle"));
            }
            seatNumber += 1;
        }
        return svg.asString();
    }

}
