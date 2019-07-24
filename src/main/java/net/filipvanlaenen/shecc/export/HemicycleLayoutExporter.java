package net.filipvanlaenen.shecc.export;

import java.util.Iterator;

import net.filipvanlaenen.shecc.HemicycleLayout;
import net.filipvanlaenen.shecc.SeatPosition;
import net.filipvanlaenen.tsvgj.Circle;
import net.filipvanlaenen.tsvgj.Svg;

/**
 * A class exporting hemicycle layouts.
 */
public class HemicycleLayoutExporter extends Exporter {
    /**
     * The magic number for a neutral grey color.
     */
    private static final int NEUTRAL_GREY = 0x777777;

    /**
     * Exports a hemicycle layout to SVG.
     *
     * @param layout
     *            The hemicycle layout to be exported.
     * @return A string representing the hemicycle layout in SVG.
     */
    String export(final HemicycleLayout layout) {
        double width = layout.getWidth() + 2 * EDGES_MARGIN;
        double halfWidth = width / 2D;
        double svgWidth = width * VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR;
        double height = layout.getHeight() + 2 * EDGES_MARGIN;
        double svgHeight = height * VIEW_BOX_TO_SVG_DIMENSIONS_FACTOR;
        Svg svg = new Svg().width(svgWidth).height(svgHeight).viewBox(-halfWidth, -1 - EDGES_MARGIN, width, height);
        double seatRadius = layout.getRowWidth() * RADIUS_ROW_WIDTH_RATIO;
        Iterator<SeatPosition> seatPositions = layout.getSeatPositions().iterator();
        while (seatPositions.hasNext()) {
            SeatPosition seatPosition = seatPositions.next();
            svg.addElement(
                    new Circle().cx(seatPosition.getX()).cy(-seatPosition.getY()).r(seatRadius).fill(NEUTRAL_GREY));
        }
        svg.addElement(createCopyrightNotice(null, halfWidth, -1D - EDGES_MARGIN, width, height));
        return svg.asString();
    }

}
