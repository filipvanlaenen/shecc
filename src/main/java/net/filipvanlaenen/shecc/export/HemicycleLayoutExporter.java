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
     * Exports a hemicycle layout to SVG.
     *
     * @param layout
     *            The hemicycle layout to be exported.
     * @return A string representing the hemicycle layout in SVG.
     */
    String export(final HemicycleLayout layout) {
        double width = layout.getWidth();
        double halfWidth = width / 2D;
        double svgWidth = width * 100D;
        double height = layout.getHeight();
        double svgHeight = height * 100D;
        Svg svg = new Svg().width(svgWidth).height(svgHeight).viewBox(-halfWidth, -1, width, height);
        double seatRadius = layout.getRowWidth() * 0.45D;
        Iterator<SeatPosition> seatPositions = layout.getSeatPositions().iterator();
        while (seatPositions.hasNext()) {
            SeatPosition seatPosition = seatPositions.next();
            svg.addElement(new Circle().cx(seatPosition.getX()).cy(-seatPosition.getY()).r(seatRadius));
        }
        return svg.asString();
    }

}
