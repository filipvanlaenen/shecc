package net.filipvanlaenen.shecc.export;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.shecc.ParliamentaryGroup;
import net.filipvanlaenen.shecc.SeatingPlan;

/**
 * Unit tests on the <code>SeatingPlanExporter</code> class.
 */
public class SeatingPlanExporterTest {
    /**
     * Magic number for the color red.
     */
    private static final int RED = 0xFF0000;
    /**
     * Magic number for the color blue.
     */
    private static final int BLUE = 0x0000FF;

    /**
     * Test verifying the export of a seating plan with two seats for the red group
     * and one for the blue group using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        groups.add(new ParliamentaryGroup(2, RED));
        groups.add(new ParliamentaryGroup(1, BLUE));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        String actual = exporter.export(plan);
        String expected = "<svg height=\"100\" viewBox=\"-1 -1 2 1\" width=\"200\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "</svg>";
        assertEquals(expected, actual);
    }
}
