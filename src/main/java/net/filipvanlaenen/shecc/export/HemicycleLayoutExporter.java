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
        Svg svg = new Svg().width(200).height(100).viewBox(-1, -1, 2, 1);
        double seatRadius = layout.getRowWidth() * 0.45D;
        Iterator<SeatPosition> seatPositions = layout.getSeatPositions().iterator();
        while (seatPositions.hasNext()) {
            SeatPosition seatPosition = seatPositions.next();
            svg.addElement(new Circle().cx(seatPosition.getX()).cy(-seatPosition.getY()).r(seatRadius));
        }
        return svg.asString();
    }

}
