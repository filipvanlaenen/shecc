package net.filipvanlaenen.shecc.export;

import java.util.Iterator;

import net.filipvanlaenen.shecc.HemicycleLayout;
import net.filipvanlaenen.shecc.SeatPosition;
import net.filipvanlaenen.shecc.export.svg.Circle;
import net.filipvanlaenen.shecc.export.svg.Svg;

/**
 * A class exporting hemicycle layouts.
 */
public class HemicycleLayoutExporter {
    /**
     * The magic number for a neutral grey color.
     */
    private static final int NEUTRAL_GREY = 0x777777;
    /**
     * The ratio between the seat circle radius and the row width.
     */
    private static final double RADIUS_ROW_WIDTH_RATIO = 0.45D;
    /**
     * The factor to scale up the view box to the SVG dimensions.
     */
    private static final double VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR = 100D;

    /**
     * Exports a hemicycle layout to SVG.
     *
     * @param layout
     *            The hemicycle layout to be exported.
     * @return A string representing the hemicycle layout in SVG.
     */
    String export(final HemicycleLayout layout) {
        double width = layout.getWidth();
        double halfWidth = width / 2D;
        double svgWidth = width * VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR;
        double height = layout.getHeight();
        double svgHeight = height * VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR;
        Svg svg = new Svg().width(svgWidth).height(svgHeight).viewBox(-halfWidth, -1, width, height);
        double seatRadius = layout.getRowWidth() * RADIUS_ROW_WIDTH_RATIO;
        Iterator<SeatPosition> seatPositions = layout.getSeatPositions().iterator();
        while (seatPositions.hasNext()) {
            SeatPosition seatPosition = seatPositions.next();
            svg.addElement(
                    new Circle().cx(seatPosition.getX()).cy(-seatPosition.getY()).r(seatRadius).fill(NEUTRAL_GREY));
        }
        return svg.asString();
    }

}
