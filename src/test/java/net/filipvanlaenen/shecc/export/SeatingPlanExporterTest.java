package net.filipvanlaenen.shecc.export;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.SortedCollection;
import net.filipvanlaenen.shecc.DifferentiatedGroupSize;
import net.filipvanlaenen.shecc.HemicycleLayout;
import net.filipvanlaenen.shecc.ParliamentaryGroup;
import net.filipvanlaenen.shecc.RowConnectedSeatingPlan;
import net.filipvanlaenen.shecc.SeatPosition;

/**
 * Unit tests on the <code>SeatingPlanExporter</code> class.
 */
public class SeatingPlanExporterTest {
    /**
     * Magic number for the color white.
     */
    private static final int WHITE = 0xFFFFFF;
    /**
     * Magic number for the color red.
     */
    private static final int RED = 0xFF0000;
    /**
     * Magic number for the color green.
     */
    private static final int GREEN = 0x00FF00;
    /**
     * Magic number for the color blue.
     */
    private static final int BLUE = 0x0000FF;
    /**
     * Magic number for the color magenta.
     */
    private static final int MAGENTA = 0xFF00FF;
    /**
     * Array representing the red/magenta color combination.
     */
    private static final int[] RED_MAGENTA_GREEN = new int[] {RED, MAGENTA, GREEN};
    /**
     * The magic number three.
     */
    private static final int THREE = 3;
    /**
     * A differentiated group size of 1, 2 and 3.
     */
    private static final DifferentiatedGroupSize DIFFERENTIATED123 = new DifferentiatedGroupSize(1, 2, THREE);
    /**
     * The magic number four.
     */
    private static final int FOUR = 4;
    /**
     * The magic number ten.
     */
    private static final int TEN = 10;
    /**
     * The magic number ninety.
     */
    private static final double NINETY = 90D;
    /**
     * The delta for double comparisons.
     */
    private static final double DOUBLE_DELTA = 0.000001D;
    /**
     * The seat positions for a hemicycle layout with three seats.
     */
    private static final SortedCollection<SeatPosition> THREE_SEAT_POSITIONS =
            new HemicycleLayout(THREE).getSeatPositions();
    /**
     * The seat positions for a hemicycle layout with four seats.
     */
    private static final SortedCollection<SeatPosition> FOUR_SEAT_POSITIONS =
            new HemicycleLayout(FOUR).getSeatPositions();

    /**
     * Verifies that the angle is converted correctly from degrees to radians.
     */
    @Test
    public void setAngleShouldConvertFromDegreesToRadians() {
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setAngle(NINETY);
        assertEquals(Math.PI / 2D, exporter.getAngle(), DOUBLE_DELTA);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group using the
     * default hemicycle layout to SVG.
     */
    @Test
    void svgExportForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, new ParliamentaryGroup(2, RED),
                new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        String actual = exporter.export(plan);
        String expected = "<svg height=\"788.15216\" viewBox=\"-0.316178 -1.05 0.632355 0.788152\" width=\"632.355228\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n" + "    </g>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.007882\" text-anchor=\"end\""
                + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.312237\" y=\"-1.053941\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group using the
     * default hemicycle layout to SVG, using the letter R for the red group and B for the blue group.
     */
    @Test
    void svgExportForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        String actual = exporter.export(plan);
        String expected = "<svg height=\"788.15216\" viewBox=\"-0.316178 -1.05 0.632355 0.788152\" width=\"632.355228\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                + "        <circle cx=\"0\" cy=\"-0.444444\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.411111\">R</text>\n" + "      </g>\n" + "      <g>\n"
                + "        <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.633333\">R</text>\n" + "      </g>\n" + "    </g>\n" + "    <g>\n" + "      <g>\n"
                + "        <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.855556\">B</text>\n" + "      </g>\n" + "    </g>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.007882\" text-anchor=\"end\""
                + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.312237\" y=\"-1.053941\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group using the
     * default hemicycle layout to SVG, using the letter R for the red group and B for the blue group, and rotating the
     * letters to the center.
     */
    @Test
    void svgExportForTwoRedAndOneBlueSeatsWithRotatedLettersInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setRotateLetters(true);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"788.15216\" viewBox=\"-0.316178 -1.05 0.632355 0.788152\" width=\"632.355228\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                + "        <circle cx=\"0\" cy=\"-0.444444\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\""
                + " transform=\"rotate(0 0,-0.444444)\" x=\"0\" y=\"-0.411111\">R</text>\n" + "      </g>\n"
                + "      <g>\n" + "        <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\""
                + " transform=\"rotate(0 0,-0.666667)\" x=\"0\" y=\"-0.633333\">R</text>\n" + "      </g>\n"
                + "    </g>\n" + "    <g>\n" + "      <g>\n"
                + "        <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\""
                + " transform=\"rotate(0 0,-0.888889)\" x=\"0\" y=\"-0.855556\">B</text>\n" + "      </g>\n"
                + "    </g>\n" + "  </g>\n" + "  <text fill=\"black\" font-size=\"0.007882\" text-anchor=\"end\""
                + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.312237\" y=\"-1.053941\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for the red group and one for the blue
     * group using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, "Red"), new ParliamentaryGroup(1, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"1688.15216\" viewBox=\"-0.316178 -1.05 0.632355 1.688152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "    <g>\n" + "      <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "    </g>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.166178\" cy=\"-0.111848\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"-0.078515\">Red (2)</text>\n" + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.166178\" cy=\"0.188152\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"0.221485\">Blue (1)</text>\n" + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.016882\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.307737\" y=\"-1.058441\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the legend uses two lines when needed.
     */
    @Test
    void svgShouldExportLegendOnTwoLinesWhenNeeded() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, "Red"), new ParliamentaryGroup(2, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"1688.15216\" viewBox=\"-0.316178 -1.05 0.632355 1.688152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.139053\" cy=\"-0.877945\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "    <g>\n" + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "      <circle cx=\"0.139053\" cy=\"-0.877945\" fill=\"#0000FF\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "  </g>\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.166178\" cy=\"-0.111848\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"-0.078515\">Red (2)</text>\n" + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.166178\" cy=\"0.188152\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"0.221485\">Blue (2)</text>\n" + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.016882\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.307737\" y=\"-1.058441\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the legend uses only one line when possible.
     */
    @Test
    void svgShouldExportLegendOnOneLineIfPossible() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, "Red"), new ParliamentaryGroup(2, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setLegendLabelWidthRatio(2);
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"1088.15216\" viewBox=\"-0.316178 -1.05 0.632355 1.088152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.139053\" cy=\"-0.877945\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "    <g>\n" + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "      <circle cx=\"0.139053\" cy=\"-0.877945\" fill=\"#0000FF\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "  </g>\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.166178\" cy=\"-0.111848\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"-0.078515\">Red (2)</text>\n" + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0.1\" cy=\"-0.111848\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"0.25\""
                        + " y=\"-0.078515\">Blue (2)</text>\n" + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.010882\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.310737\" y=\"-1.055441\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the legend uses the legend label width ratio when specified.
     */
    @Test
    void svgExportsLegendWithSpecifiedLegendLabelWidthRatio() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, "Red"), new ParliamentaryGroup(2, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setLegendLabelWidthRatio(TEN);
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"1988.15216\" viewBox=\"-0.316178 -1.05 0.632355 1.988152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.139053\" cy=\"-0.877945\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "    <g>\n" + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "      <circle cx=\"0.139053\" cy=\"-0.877945\" fill=\"#0000FF\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "  </g>\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.166178\" cy=\"-0.111848\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"-0.078515\">Red (2)</text>\n" + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.166178\" cy=\"0.188152\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"0.221485\">Blue (2)</text>\n" + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.019882\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.306237\" y=\"-1.059941\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the last row of the legend is centered.
     */
    @Test
    void svgExportsCentersLastRowOfLegend() {
        RowConnectedSeatingPlan plan =
                new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS, new ParliamentaryGroup(2, RED, "Red"),
                        new ParliamentaryGroup(1, BLUE, "Blue"), new ParliamentaryGroup(1, MAGENTA, "Magenta"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"1988.15216\" viewBox=\"-0.316178 -1.05 0.632355 1.988152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.139053\" cy=\"-0.877945\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "    <g>\n" + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0.139053\" cy=\"-0.877945\" fill=\"#FF00FF\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "  </g>\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.166178\" cy=\"-0.111848\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"-0.078515\">Red (2)</text>\n" + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.166178\" cy=\"0.188152\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"0.221485\">Blue (1)</text>\n" + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.166178\" cy=\"0.488152\" fill=\"#FF00FF\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"0.521485\">Magenta (1)</text>\n" + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.019882\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.306237\" y=\"-1.059941\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for the red group and one for the blue
     * group using the default hemicycle layout to SVG, using the letter R for the red group and B for the blue group.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, "Red", "R"), new ParliamentaryGroup(1, BLUE, "Blue", "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"1688.15216\" viewBox=\"-0.316178 -1.05 0.632355 1.688152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                        + "        <circle cx=\"0\" cy=\"-0.444444\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"0\""
                        + " y=\"-0.411111\">R</text>\n" + "      </g>\n" + "      <g>\n"
                        + "        <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"0\""
                        + " y=\"-0.633333\">R</text>\n" + "      </g>\n" + "    </g>\n" + "    <g>\n" + "      <g>\n"
                        + "        <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"0\""
                        + " y=\"-0.855556\">B</text>\n" + "      </g>\n" + "    </g>\n" + "  </g>\n" + "  <g>\n"
                        + "    <g>\n" + "      <g>\n"
                        + "        <circle cx=\"-0.166178\" cy=\"-0.111848\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"-0.166178\""
                        + " y=\"-0.078515\">R</text>\n" + "      </g>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"-0.078515\">Red (2)</text>\n" + "    </g>\n" + "    <g>\n" + "      <g>\n"
                        + "        <circle cx=\"-0.166178\" cy=\"0.188152\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"-0.166178\""
                        + " y=\"0.221485\">B</text>\n" + "      </g>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"0.221485\">Blue (1)</text>\n" + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.016882\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.307737\" y=\"-1.058441\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for the red group and one for the blue
     * group using the default hemicycle layout to SVG, using the letter R for the red group and B for the blue group,
     * and with a text font set.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayoutWithASpecificFont() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, "Red", "R"), new ParliamentaryGroup(1, BLUE, "Blue", "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setFontFamily("Lato");
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"1688.15216\" viewBox=\"-0.316178 -1.05 0.632355 1.688152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                        + "        <circle cx=\"0\" cy=\"-0.444444\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-family=\"Lato\" font-size=\"0.1\" text-anchor=\"middle\""
                        + " x=\"0\" y=\"-0.411111\">R</text>\n" + "      </g>\n" + "      <g>\n"
                        + "        <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-family=\"Lato\" font-size=\"0.1\" text-anchor=\"middle\""
                        + " x=\"0\" y=\"-0.633333\">R</text>\n" + "      </g>\n" + "    </g>\n" + "    <g>\n"
                        + "      <g>\n" + "        <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-family=\"Lato\" font-size=\"0.1\" text-anchor=\"middle\""
                        + " x=\"0\" y=\"-0.855556\">B</text>\n" + "      </g>\n" + "    </g>\n" + "  </g>\n" + "  <g>\n"
                        + "    <g>\n" + "      <g>\n"
                        + "        <circle cx=\"-0.166178\" cy=\"-0.111848\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-family=\"Lato\" font-size=\"0.1\" text-anchor=\"middle\""
                        + " x=\"-0.166178\" y=\"-0.078515\">R</text>\n" + "      </g>\n"
                        + "      <text fill=\"#000000\" font-family=\"Lato\" font-size=\"0.1\" text-anchor=\"start\""
                        + " x=\"-0.016178\" y=\"-0.078515\">Red (2)</text>\n" + "    </g>\n" + "    <g>\n"
                        + "      <g>\n"
                        + "        <circle cx=\"-0.166178\" cy=\"0.188152\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-family=\"Lato\" font-size=\"0.1\" text-anchor=\"middle\""
                        + " x=\"-0.166178\" y=\"0.221485\">B</text>\n" + "      </g>\n"
                        + "      <text fill=\"#000000\" font-family=\"Lato\" font-size=\"0.1\" text-anchor=\"start\""
                        + " x=\"-0.016178\" y=\"0.221485\">Blue (1)</text>\n" + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-family=\"Lato\" font-size=\"0.016882\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.307737\" y=\"-1.058441\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for the red group and one for the blue
     * group using the default hemicycle layout to SVG, using the letter R for the red group and B for the blue group,
     * and with a custom text color.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayoutWithATextColor() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, "Red", "R"), new ParliamentaryGroup(1, BLUE, "Blue", "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setFontColor(MAGENTA);
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"1688.15216\" viewBox=\"-0.316178 -1.05 0.632355 1.688152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                        + "        <circle cx=\"0\" cy=\"-0.444444\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"0\""
                        + " y=\"-0.411111\">R</text>\n" + "      </g>\n" + "      <g>\n"
                        + "        <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"0\""
                        + " y=\"-0.633333\">R</text>\n" + "      </g>\n" + "    </g>\n" + "    <g>\n" + "      <g>\n"
                        + "        <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"0\""
                        + " y=\"-0.855556\">B</text>\n" + "      </g>\n" + "    </g>\n" + "  </g>\n" + "  <g>\n"
                        + "    <g>\n" + "      <g>\n"
                        + "        <circle cx=\"-0.166178\" cy=\"-0.111848\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"-0.166178\""
                        + " y=\"-0.078515\">R</text>\n" + "      </g>\n"
                        + "      <text fill=\"#FF00FF\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"-0.078515\">Red (2)</text>\n" + "    </g>\n" + "    <g>\n" + "      <g>\n"
                        + "        <circle cx=\"-0.166178\" cy=\"0.188152\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "        <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"-0.166178\""
                        + " y=\"0.221485\">B</text>\n" + "      </g>\n"
                        + "      <text fill=\"#FF00FF\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"0.221485\">Blue (1)</text>\n" + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"#FF00FF\" font-size=\"0.016882\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.307737\" y=\"-1.058441\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group and a
     * custom copyright notice using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportsWithCustomCopyrightNoticeForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, new ParliamentaryGroup(2, RED),
                new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setCustomCopyrightNotice("John Doe");
        String actual = exporter.export(plan);
        String year = new SimpleDateFormat("yyyy", Locale.US).format(new Date());
        String expected = "<svg height=\"788.15216\" viewBox=\"-0.316178 -1.05 0.632355 0.788152\" width=\"632.355228\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n" + "    </g>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.007882\" text-anchor=\"end\""
                + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.312237\" y=\"-1.053941\">© " + year
                + " John Doe, chart produced using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group and a
     * background color using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportsWithBackgroundColorForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, new ParliamentaryGroup(2, RED),
                new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"788.15216\" viewBox=\"-0.316178 -1.05 0.632355 0.788152\" width=\"632.355228\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"0.788152\" width=\"0.632355\" x=\"-0.316178\" y=\"-1.05\"/>\n"
                + "  <g>\n" + "    <g>\n" + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n" + "    </g>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.007882\" text-anchor=\"end\""
                + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.312237\" y=\"-1.053941\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group, a title
     * and a background color using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportsWithTitleAndBackgroundColorForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, new ParliamentaryGroup(2, RED),
                new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        exporter.setTitle("Lorem Ipsum");
        String actual = exporter.export(plan);
        String expected = "<svg height=\"888.15216\" viewBox=\"-0.316178 -1.15 0.632355 0.888152\" width=\"632.355228\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"0.888152\" width=\"0.632355\" x=\"-0.316178\" y=\"-1.15\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.05\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.05\">Lorem Ipsum</text>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n" + "    </g>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.008882\" text-anchor=\"end\""
                + " transform=\"rotate(270 0.316178,-1.15)\" x=\"0.311737\" y=\"-1.154441\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group, a title,
     * a subtitle and a background color using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportsWithTitleAndSubtitleAndBackgroundColorForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, new ParliamentaryGroup(2, RED),
                new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        exporter.setFontFamily("Arial");
        exporter.setTitle("Lorem Ipsum");
        exporter.setSubtitle("Dolor Sit Amet");
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"973.15216\" viewBox=\"-0.316178 -1.235 0.632355 0.973152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <rect fill=\"#FFFFFF\" height=\"0.973152\" width=\"0.632355\" x=\"-0.316178\""
                        + " y=\"-1.235\"/>\n"
                        + "  <text fill=\"#000000\" font-family=\"Arial\" font-size=\"0.05\" font-weight=\"bold\""
                        + " text-anchor=\"middle\" x=\"0\" y=\"-1.135\">Lorem Ipsum</text>\n"
                        + "  <text fill=\"#000000\" font-family=\"Arial\" font-size=\"0.035\" font-weight=\"bold\""
                        + " text-anchor=\"middle\" x=\"0\" y=\"-1.05\">Dolor Sit Amet</text>\n" + "  <g>\n"
                        + "    <g>\n" + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "    <g>\n" + "      <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-family=\"Arial\" font-size=\"0.009732\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.235)\" x=\"0.311312\" y=\"-1.239866\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for the red/magenta/green group and one
     * for the blue group using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportWithLegendForTwoRedMagentaGreenAndOneBlueSeatsInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(1, RED_MAGENTA_GREEN, "Red/Magenta/Green"),
                new ParliamentaryGroup(2, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"1688.15216\" viewBox=\"-0.316178 -1.05 0.632355 1.688152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                        + "        <path d=\"M 0 -0.444444 L 0 -0.544444 A 0.1 0.1 0 0 1 0.086603 -0.394444 Z\""
                        + " fill=\"#FF0000\"/>\n"
                        + "        <path d=\"M 0 -0.444444 L 0.086603 -0.394444 A 0.1 0.1 0 0 1 -0.086603 -0.394444 Z\""
                        + " fill=\"#FF00FF\"/>\n"
                        + "        <path d=\"M 0 -0.444444 L -0.086603 -0.394444 A 0.1 0.1 0 0 1 0 -0.544444 Z\""
                        + " fill=\"#00FF00\"/>\n" + "      </g>\n" + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "      <circle cx=\"0\" cy=\"-0.888889\" fill=\"#0000FF\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "  </g>\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                        + "        <path d=\"M -0.166178 -0.111848 L -0.166178 -0.211848 A 0.1 0.1 0 0 1 -0.079575"
                        + " -0.061848 Z\" fill=\"#FF0000\"/>\n"
                        + "        <path d=\"M -0.166178 -0.111848 L -0.079575 -0.061848 A 0.1 0.1 0 0 1 -0.25278"
                        + " -0.061848 Z\" fill=\"#FF00FF\"/>\n"
                        + "        <path d=\"M -0.166178 -0.111848 L -0.25278 -0.061848 A 0.1 0.1 0 0 1 -0.166178"
                        + " -0.211848 Z\" fill=\"#00FF00\"/>\n" + "      </g>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"-0.078515\">Red/Magenta/Green (1)</text>\n" + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.166178\" cy=\"0.188152\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "      <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"0.221485\">Blue (2)</text>\n" + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.016882\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.307737\" y=\"-1.058441\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with three differentiated seats for the red/magenta/green
     * group and one for the blue group using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportWithLegendForDifferentiatedThreeRedMagentaGreenAndOneBlueSeatsInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS,
                new ParliamentaryGroup(DIFFERENTIATED123, RED_MAGENTA_GREEN, "Red/Magenta/Green"),
                new ParliamentaryGroup(1, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setFontFamily("Arial");
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"1988.15216\" viewBox=\"-0.316178 -1.05 0.632355 1.988152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                        + "        <path d=\"M -0.139053 -0.877945 L -0.139053 -0.977945 A 0.1 0.1 0 0 1 -0.05245"
                        + " -0.827945 Z\" fill=\"#FF0000\"/>\n"
                        + "        <path d=\"M -0.139053 -0.877945 L -0.05245 -0.827945 A 0.1 0.1 0 0 1 -0.225655"
                        + " -0.827945 Z\" fill=\"#FF00FF\"/>\n"
                        + "        <path d=\"M -0.139053 -0.877945 L -0.225655 -0.827945 A 0.1 0.1 0 0 1 -0.139053"
                        + " -0.977945 Z\" fill=\"#00FF00\"/>\n" + "      </g>\n" + "      <g>\n"
                        + "        <path d=\"M 0 -0.534444 A 0.09 0.09 0 0 1 0.077942 -0.399444\" fill=\"none\""
                        + " stroke=\"#FF0000\" stroke-width=\"0.02\"/>\n"
                        + "        <path d=\"M 0.077942 -0.399444 A 0.09 0.09 0 0 1 -0.077942 -0.399444\" fill=\"none\""
                        + " stroke=\"#FF00FF\" stroke-width=\"0.02\"/>\n"
                        + "        <path d=\"M -0.077942 -0.399444 A 0.09 0.09 0 0 1 0 -0.534444\" fill=\"none\""
                        + " stroke=\"#00FF00\" stroke-width=\"0.02\"/>\n" + "      </g>\n" + "      <g>\n"
                        + "        <path d=\"M 0 -0.666667 L 0 -0.766667 A 0.1 0.1 0 0 1 0.086603 -0.616667 Z\""
                        + " fill=\"#FF0000\" opacity=\"0.3\"/>\n"
                        + "        <path d=\"M 0 -0.756667 A 0.09 0.09 0 0 1 0.077942 -0.621667\" fill=\"none\""
                        + " stroke=\"#FF0000\" stroke-width=\"0.02\"/>\n"
                        + "        <path d=\"M 0 -0.666667 L 0.086603 -0.616667 A 0.1 0.1 0 0 1 -0.086603 -0.616667 Z\""
                        + " fill=\"#FF00FF\" opacity=\"0.3\"/>\n"
                        + "        <path d=\"M 0.077942 -0.621667 A 0.09 0.09 0 0 1 -0.077942 -0.621667\" fill=\"none\""
                        + " stroke=\"#FF00FF\" stroke-width=\"0.02\"/>\n"
                        + "        <path d=\"M 0 -0.666667 L -0.086603 -0.616667 A 0.1 0.1 0 0 1 0 -0.766667 Z\""
                        + " fill=\"#00FF00\" opacity=\"0.3\"/>\n"
                        + "        <path d=\"M -0.077942 -0.621667 A 0.09 0.09 0 0 1 0 -0.756667\" fill=\"none\""
                        + " stroke=\"#00FF00\" stroke-width=\"0.02\"/>\n" + "      </g>\n" + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0.139053\" cy=\"-0.877945\" fill=\"#0000FF\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "  </g>\n" + "  <g>\n" + "    <g>\n" + "      <g>\n" + "        <g>\n"
                        + "          <path d=\"M -0.166178 -0.111848 L -0.166178 -0.211848 A 0.1 0.1 0 0 1 -0.079575"
                        + " -0.061848 Z\" fill=\"#FF0000\"/>\n"
                        + "          <path d=\"M -0.166178 -0.111848 L -0.079575 -0.061848 A 0.1 0.1 0 0 1 -0.25278"
                        + " -0.061848 Z\" fill=\"#FF00FF\"/>\n"
                        + "          <path d=\"M -0.166178 -0.111848 L -0.25278 -0.061848 A 0.1 0.1 0 0 1 -0.166178"
                        + " -0.211848 Z\" fill=\"#00FF00\"/>\n" + "        </g>\n"
                        + "        <text fill=\"#000000\" font-family=\"Arial\" font-size=\"0.1\" text-anchor=\"start\""
                        + " x=\"-0.016178\" y=\"-0.078515\">Red/Magenta/Green (3)</text>\n" + "      </g>\n"
                        + "      <g>\n"
                        + "        <circle cx=\"-0.166178\" cy=\"0.188152\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "        <text fill=\"#000000\" font-family=\"Arial\" font-size=\"0.1\" text-anchor=\"start\""
                        + " x=\"-0.016178\" y=\"0.221485\">Blue (1)</text>\n" + "      </g>\n" + "    </g>\n"
                        + "    <g>\n" + "      <g>\n" + "        <g>\n"
                        + "          <circle cx=\"-0.166178\" cy=\"0.788152\" fill=\"#000000\" r=\"0.1\"/>\n"
                        + "          <text fill=\"white\" font-family=\"Arial\" font-size=\"0.1\""
                        + " text-anchor=\"middle\" x=\"-0.166178\" y=\"0.821485\">X</text>\n" + "        </g>\n"
                        + "        <text fill=\"#000000\" font-family=\"Arial\" font-size=\"0.1\" text-anchor=\"start\""
                        + " x=\"-0.016178\" y=\"0.821485\">Certain (P ≥ 97.5%)</text>\n" + "      </g>\n"
                        + "      <g>\n" + "        <g>\n"
                        + "          <circle cx=\"0.011274\" cy=\"0.788152\" fill=\"#000000\" fill-opacity=\"0.3\""
                        + " r=\"0.09\" stroke=\"#000000\" stroke-width=\"0.02\"/>\n"
                        + "          <text fill=\"#000000\" font-family=\"Arial\" font-size=\"0.1\""
                        + " text-anchor=\"middle\" x=\"0.011274\" y=\"0.821485\">X</text>\n" + "        </g>\n"
                        + "        <text fill=\"#000000\" font-family=\"Arial\" font-size=\"0.1\" text-anchor=\"start\""
                        + " x=\"0.161274\" y=\"0.821485\">Likely (P ≥ 50%)</text>\n" + "      </g>\n" + "      <g>\n"
                        + "        <g>\n" + "          <circle cx=\"0.188726\" cy=\"0.788152\" fill=\"none\" r=\"0.09\""
                        + " stroke=\"#000000\" stroke-width=\"0.02\"/>\n"
                        + "          <text fill=\"#000000\" font-family=\"Arial\" font-size=\"0.1\""
                        + " text-anchor=\"middle\" x=\"0.188726\" y=\"0.821485\">X</text>\n" + "        </g>\n"
                        + "        <text fill=\"#000000\" font-family=\"Arial\" font-size=\"0.1\" text-anchor=\"start\""
                        + " x=\"0.338726\" y=\"0.821485\">Unlikely (P &lt; 50%)</text>\n" + "      </g>\n"
                        + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-family=\"Arial\" font-size=\"0.019882\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.306237\" y=\"-1.059941\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group using the
     * default hemicycle layout to SVG.
     */
    @Test
    void svgExportForThreeRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS,
                new ParliamentaryGroup(DIFFERENTIATED123, RED), new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        String actual = exporter.export(plan);
        String expected = "<svg height=\"788.15216\" viewBox=\"-0.316178 -1.05 0.632355 0.788152\" width=\"632.355228\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.139053\" cy=\"-0.877945\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"none\" r=\"0.09\" stroke=\"#FF0000\""
                + " stroke-width=\"0.02\"/>\n"
                + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" fill-opacity=\"0.3\" r=\"0.09\""
                + " stroke=\"#FF0000\" stroke-width=\"0.02\"/>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0.139053\" cy=\"-0.877945\" fill=\"#0000FF\" r=\"0.1\"/>\n" + "    </g>\n"
                + "  </g>\n" + "  <text fill=\"black\" font-size=\"0.007882\" text-anchor=\"end\""
                + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.312237\" y=\"-1.053941\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group using the
     * default hemicycle layout to SVG.
     */
    @Test
    void svgExportWithLegendForThreeRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(FOUR_SEAT_POSITIONS,
                new ParliamentaryGroup(DIFFERENTIATED123, RED, "Red"), new ParliamentaryGroup(1, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected =
                "<svg height=\"1988.15216\" viewBox=\"-0.316178 -1.05 0.632355 1.988152\" width=\"632.355228\""
                        + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.139053\" cy=\"-0.877945\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "      <circle cx=\"0\" cy=\"-0.444444\" fill=\"none\" r=\"0.09\" stroke=\"#FF0000\""
                        + " stroke-width=\"0.02\"/>\n"
                        + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" fill-opacity=\"0.3\" r=\"0.09\""
                        + " stroke=\"#FF0000\" stroke-width=\"0.02\"/>\n" + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0.139053\" cy=\"-0.877945\" fill=\"#0000FF\" r=\"0.1\"/>\n" + "    </g>\n"
                        + "  </g>\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                        + "        <circle cx=\"-0.166178\" cy=\"-0.111848\" fill=\"#FF0000\" r=\"0.1\"/>\n"
                        + "        <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"-0.078515\">Red (3)</text>\n" + "      </g>\n" + "      <g>\n"
                        + "        <circle cx=\"-0.166178\" cy=\"0.188152\" fill=\"#0000FF\" r=\"0.1\"/>\n"
                        + "        <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"0.221485\">Blue (1)</text>\n" + "      </g>\n" + "    </g>\n" + "    <g>\n"
                        + "      <g>\n" + "        <g>\n"
                        + "          <circle cx=\"-0.166178\" cy=\"0.788152\" fill=\"#000000\" r=\"0.1\"/>\n"
                        + "          <text fill=\"white\" font-size=\"0.1\" text-anchor=\"middle\" x=\"-0.166178\""
                        + " y=\"0.821485\">X</text>\n" + "        </g>\n"
                        + "        <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"-0.016178\""
                        + " y=\"0.821485\">Certain (P ≥ 97.5%)</text>\n" + "      </g>\n" + "      <g>\n"
                        + "        <g>\n"
                        + "          <circle cx=\"0.011274\" cy=\"0.788152\" fill=\"#000000\" fill-opacity=\"0.3\""
                        + " r=\"0.09\" stroke=\"#000000\" stroke-width=\"0.02\"/>\n"
                        + "          <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"middle\" x=\"0.011274\""
                        + " y=\"0.821485\">X</text>\n" + "        </g>\n"
                        + "        <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"0.161274\""
                        + " y=\"0.821485\">Likely (P ≥ 50%)</text>\n" + "      </g>\n" + "      <g>\n" + "        <g>\n"
                        + "          <circle cx=\"0.188726\" cy=\"0.788152\" fill=\"none\" r=\"0.09\""
                        + " stroke=\"#000000\" stroke-width=\"0.02\"/>\n"
                        + "          <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"middle\" x=\"0.188726\""
                        + " y=\"0.821485\">X</text>\n" + "        </g>\n"
                        + "        <text fill=\"#000000\" font-size=\"0.1\" text-anchor=\"start\" x=\"0.338726\""
                        + " y=\"0.821485\">Unlikely (P &lt; 50%)</text>\n" + "      </g>\n" + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.019882\" text-anchor=\"end\""
                        + " transform=\"rotate(270 0.316178,-1.05)\" x=\"0.306237\" y=\"-1.059941\">Chart produced"
                        + " using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }
}
