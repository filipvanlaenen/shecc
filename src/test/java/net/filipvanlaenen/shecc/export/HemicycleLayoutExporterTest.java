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
     * Just below π. Can e.g. used to test a boundary condition depending on the number π.
     */
    private static final double JUST_BELOW_PI = 3.13D;
    /**
     * Just above π. Can e.g. used to test a boundary condition depending on the number π.
     */
    private static final double JUST_ABOVE_PI = 3.15D;

    /**
     * Test verifying the export of a default hemicycle layout with three seats to SVG.
     */
    @Test
    void svgExportForThreeSeatsInADefaultHemicycleLayout() {
        HemicycleLayout layout = new HemicycleLayout(THREE);
        HemicycleLayoutExporter exporter = new HemicycleLayoutExporter();
        String actual = exporter.export(layout);
        String expected = "<svg height=\"789.3291\" viewBox=\"-0.326822 -1.05 0.653645 0.789329\" width=\"653.644857\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"0\" cy=\"-0.583333\" fill=\"grey\" r=\"0.075\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.75\" fill=\"grey\" r=\"0.075\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.916667\" fill=\"grey\" r=\"0.075\"/>\n"
                + "  <text fill=\"black\" font-size=\"0.007893\" text-anchor=\"end\" transform=\"rotate(270 0.326822,-1.05)\" x=\"0.322876\" y=\"-1.053947\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a hemicycle layout with three seats and a angle slightly less than π to SVG.
     */
    @Test
    void svgExportForThreeSeatsWithAnAngleLessThanPi() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_BELOW_PI);
        HemicycleLayoutExporter exporter = new HemicycleLayoutExporter();
        String actual = exporter.export(layout);
        String expected =
                "<svg height=\"1431.395636\" viewBox=\"-1.051915 -1.05 2.103831 1.431396\" width=\"2103.830599\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.666655\" cy=\"-0.003864\" fill=\"grey\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"grey\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.666655\" cy=\"-0.003864\" fill=\"grey\" r=\"0.3\"/>\n"
                + "  <text fill=\"black\" font-size=\"0.021038\" text-anchor=\"end\" transform=\"rotate(270 1.051915,-1.05)\" x=\"1.041396\" y=\"-1.060519\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a hemicycle layout with three seats and a angle slightly greater than π to SVG.
     */
    @Test
    void svgExportForThreeSeatsWithAnAngleGreaterThanPi() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_ABOVE_PI);
        HemicycleLayoutExporter exporter = new HemicycleLayoutExporter();
        String actual = exporter.export(layout);
        String expected = "<svg height=\"1437.534049\" viewBox=\"-1.05 -1.05 2.1 1.437534\" width=\"2100\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.666661\" cy=\"0.002802\" fill=\"grey\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"grey\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.666661\" cy=\"0.002802\" fill=\"grey\" r=\"0.3\"/>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\" transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }
}
