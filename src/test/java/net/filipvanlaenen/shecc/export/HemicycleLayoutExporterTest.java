package net.filipvanlaenen.shecc.export;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.shecc.HemicycleLayout;

/**
 * Unit tests on the <code>HemicycleLayoutExporterTest</code> class.
 */
public class HemicycleLayoutExporterTest {

    /**
     * The magic number three.
     */
    private static final int THREE = 3;

    /**
     * Test verifying the export of a default hemicycle layout with three seats to
     * SVG.
     */
    @Test
    void svgExportForThreeSeatsInADefaultHemicycleLayout() {
        HemicycleLayout layout = new HemicycleLayout(THREE);
        HemicycleLayoutExporter exporter = new HemicycleLayoutExporter();
        String actual = exporter.export(layout);
        String expected = "<svg height=\"100\" viewBox=\"-1 -1 2 1\" width=\"200\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" r=\"0.3\"/>\n" + "</svg>";
        assertEquals(expected, actual);
    }
}
