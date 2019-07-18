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
     * Just below π. Can e.g. used to test a boundary condition depending on the
     * number π.
     */
    private static final double JUST_BELOW_PI = 3.13D;
    /**
     * Just above π. Can e.g. used to test a boundary condition depending on the
     * number π.
     */
    private static final double JUST_ABOVE_PI = 3.15D;

    /**
     * Test verifying the export of a default hemicycle layout with three seats to
     * SVG.
     */
    @Test
    void svgExportForThreeSeatsInADefaultHemicycleLayout() {
        HemicycleLayout layout = new HemicycleLayout(THREE);
        HemicycleLayoutExporter exporter = new HemicycleLayoutExporter();
        String actual = exporter.export(layout);
        String expected = "<svg height=\"1000\" viewBox=\"-1 -1 2 1\" width=\"2000\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#777777\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#777777\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#777777\" r=\"0.3\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.02\" text-anchor=\"end\" transform=\"rotate(270 1,-1)\" x=\"0.99\" y=\"-1.01\">Produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a hemicycle layout with three seats and a angle
     * slightly less than π to SVG.
     */
    @Test
    void svgExportForThreeSeatsWithAnAngleLessThanPi() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_BELOW_PI);
        HemicycleLayoutExporter exporter = new HemicycleLayoutExporter();
        String actual = exporter.export(layout);
        String expected = "<svg height=\"1000\" viewBox=\"-0.999983 -1 1.999966 1\" width=\"1999.966403\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.576058\" cy=\"-0.335562\" fill=\"#777777\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#777777\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.576058\" cy=\"-0.335562\" fill=\"#777777\" r=\"0.3\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.02\" text-anchor=\"end\" transform=\"rotate(270 0.999983,-1)\" x=\"0.989983\" y=\"-1.01\">Produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a hemicycle layout with three seats and a angle
     * slightly greater than π to SVG.
     */
    @Test
    void svgExportForThreeSeatsWithAnAngleGreaterThanPi() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_ABOVE_PI);
        HemicycleLayoutExporter exporter = new HemicycleLayoutExporter();
        String actual = exporter.export(layout);
        String expected = "<svg height=\"1004.203661\" viewBox=\"-1 -1 2 1.004204\" width=\"2000\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.578282\" cy=\"-0.331714\" fill=\"#777777\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#777777\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.578282\" cy=\"-0.331714\" fill=\"#777777\" r=\"0.3\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.02\" text-anchor=\"end\" transform=\"rotate(270 1,-1)\" x=\"0.99\" y=\"-1.01\">Produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }
}
