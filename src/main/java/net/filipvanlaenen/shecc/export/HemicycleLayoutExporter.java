package net.filipvanlaenen.shecc.export;

import java.util.Iterator;

import net.filipvanlaenen.shecc.HemicycleLayout;
import net.filipvanlaenen.shecc.SeatPosition;
import net.filipvanlaenen.shecc.export.svg.Circle;
import net.filipvanlaenen.shecc.export.svg.Svg;

public class HemicycleLayoutExporter {

    String export(HemicycleLayout layout) {
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
