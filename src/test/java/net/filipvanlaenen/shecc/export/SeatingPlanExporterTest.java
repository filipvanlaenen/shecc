package net.filipvanlaenen.shecc.export;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.shecc.ParliamentaryGroup;
import net.filipvanlaenen.shecc.SeatingPlan;

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
     * The magic number ten.
     */
    private static final int TEN = 10;

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group using the
     * default hemicycle layout to SVG.
     */
    @Test
    void svgExportForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED), new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.666667\" cy=\"-0\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group using the
     * default hemicycle layout to SVG, using the letter R for the red group and B for the blue group.
     */
    @Test
    void svgExportForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayout() {
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.666667\" cy=\"-0\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.666667\""
                + " y=\"0.1\">R</text>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.666667\""
                + " y=\"0.1\">B</text>\n" + "    </g>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group using the
     * default hemicycle layout to SVG, using the letter R for the red group and B for the blue group, and rotating the
     * letters to the center.
     */
    @Test
    void svgExportForTwoRedAndOneBlueSeatsWithRotatedLettersInADefaultHemicycleLayout() {
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setRotateLetters(true);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.666667\" cy=\"-0\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\""
                + " transform=\"rotate(-90 -0.666667,-0)\" x=\"-0.666667\" y=\"0.1\">R</text>\n" + "    </g>\n"
                + "    <g>\n" + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\""
                + " transform=\"rotate(0 0,-0.666667)\" x=\"0\" y=\"-0.566667\">R</text>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\""
                + " transform=\"rotate(90 0.666667,-0)\" x=\"0.666667\" y=\"0.1\">B</text>\n" + "    </g>\n"
                + "  </g>\n" + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for the red group and one for the blue
     * group using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, "Red"), new ParliamentaryGroup(1, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"2900\" viewBox=\"-1.05 -1.05 2.1 2.9\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.666667\" cy=\"-0\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"-0.7\" cy=\"0.6\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"0.7\">Red"
                + " (2)</text>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"-0.7\" cy=\"1.5\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"1.6\">Blue"
                + " (1)</text>\n" + "  </g>\n" + "  <text fill=\"black\" font-size=\"0.029\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0355\" y=\"-1.0645\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the legend uses up only one line when there is place enough.
     */
    @Test
    void svgExportsLegendOnOneLineWhenThereIsPlaceEnough() {
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, "Red"), new ParliamentaryGroup(2, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1550\" viewBox=\"-1.05 -1.05 2.1 1.55\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.833333\" cy=\"-0\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.5\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.833333\" fill=\"#0000FF\" r=\"0.15\"/>\n"
                + "    <circle cx=\"0.833333\" cy=\"-0\" fill=\"#0000FF\" r=\"0.15\"/>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"-0.85\" cy=\"0.3\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"-0.625\" y=\"0.35\">Red"
                + " (2)</text>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"0.15\" cy=\"0.3\" fill=\"#0000FF\" r=\"0.15\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"0.375\" y=\"0.35\">Blue"
                + " (2)</text>\n" + "  </g>\n" + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the legend uses the legend label width ratio when specified.
     */
    @Test
    void svgExportsLegendWithSpecifiedLegendLabelWidthRatio() {
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, "Red"), new ParliamentaryGroup(2, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setLegendLabelWidthRatio(TEN);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"2000\" viewBox=\"-1.05 -1.05 2.1 2\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.833333\" cy=\"-0\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.5\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.833333\" fill=\"#0000FF\" r=\"0.15\"/>\n"
                + "    <circle cx=\"0.833333\" cy=\"-0\" fill=\"#0000FF\" r=\"0.15\"/>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"-0.85\" cy=\"0.3\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"-0.625\" y=\"0.35\">Red"
                + " (2)</text>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"-0.85\" cy=\"0.75\" fill=\"#0000FF\" r=\"0.15\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"-0.625\" y=\"0.8\">Blue"
                + " (2)</text>\n" + "  </g>\n" + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the last row of the legend is centered.
     */
    @Test
    void svgExportsCentersLastRowOfLegend() {
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED, "Red"),
                new ParliamentaryGroup(1, BLUE, "Blue"), new ParliamentaryGroup(1, MAGENTA, "Magenta"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"2000\" viewBox=\"-1.05 -1.05 2.1 2\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.833333\" cy=\"-0\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.5\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.833333\" fill=\"#0000FF\" r=\"0.15\"/>\n"
                + "    <circle cx=\"0.833333\" cy=\"-0\" fill=\"#FF00FF\" r=\"0.15\"/>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"-0.85\" cy=\"0.3\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"-0.625\" y=\"0.35\">Red"
                + " (2)</text>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"0.15\" cy=\"0.3\" fill=\"#0000FF\" r=\"0.15\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"0.375\" y=\"0.35\">Blue"
                + " (1)</text>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"-0.35\" cy=\"0.75\" fill=\"#FF00FF\" r=\"0.15\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"-0.125\" y=\"0.8\">Magenta"
                + " (1)</text>\n" + "  </g>\n" + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for the red group and one for the blue
     * group using the default hemicycle layout to SVG, using the letter R for the red group and B for the blue group.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayout() {
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED, "Red", "R"),
                new ParliamentaryGroup(1, BLUE, "Blue", "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"2900\" viewBox=\"-1.05 -1.05 2.1 2.9\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.666667\" cy=\"-0\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.666667\""
                + " y=\"0.1\">R</text>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.666667\""
                + " y=\"0.1\">B</text>\n" + "    </g>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.7\" cy=\"0.6\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.7\""
                + " y=\"0.7\">R</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"0.7\">Red"
                + " (2)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.7\" cy=\"1.5\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.7\""
                + " y=\"1.6\">B</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"1.6\">Blue"
                + " (1)</text>\n" + "  </g>\n" + "  <text fill=\"black\" font-size=\"0.029\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0355\" y=\"-1.0645\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for the red group and one for the blue
     * group using the default hemicycle layout to SVG, using the letter R for the red group and B for the blue group,
     * and with a text font set.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayoutWithASpecificFont() {
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED, "Red", "R"),
                new ParliamentaryGroup(1, BLUE, "Blue", "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setFontFamily("Lato");
        String actual = exporter.export(plan);
        String expected = "<svg height=\"2900\" viewBox=\"-1.05 -1.05 2.1 2.9\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.666667\" cy=\"-0\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\""
                + " x=\"-0.666667\" y=\"0.1\">R</text>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\""
                + " x=\"0.666667\" y=\"0.1\">B</text>\n" + "    </g>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.7\" cy=\"0.6\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\""
                + " x=\"-0.7\" y=\"0.7\">R</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\""
                + " y=\"0.7\">Red (2)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.7\" cy=\"1.5\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\""
                + " x=\"-0.7\" y=\"1.6\">B</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\""
                + " y=\"1.6\">Blue (1)</text>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-family=\"Lato\" font-size=\"0.029\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0355\" y=\"-1.0645\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for the red group and one for the blue
     * group using the default hemicycle layout to SVG, using the letter R for the red group and B for the blue group,
     * and with a custom text color.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayoutWithATextColor() {
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED, "Red", "R"),
                new ParliamentaryGroup(1, BLUE, "Blue", "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setFontColor(MAGENTA);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"2900\" viewBox=\"-1.05 -1.05 2.1 2.9\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.666667\" cy=\"-0\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.666667\""
                + " y=\"0.1\">R</text>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n" + "    </g>\n" + "    <g>\n"
                + "      <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.666667\""
                + " y=\"0.1\">B</text>\n" + "    </g>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.7\" cy=\"0.6\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.7\""
                + " y=\"0.7\">R</text>\n" + "    </g>\n"
                + "    <text fill=\"#FF00FF\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"0.7\">Red"
                + " (2)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.7\" cy=\"1.5\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.7\""
                + " y=\"1.6\">B</text>\n" + "    </g>\n"
                + "    <text fill=\"#FF00FF\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"1.6\">Blue"
                + " (1)</text>\n" + "  </g>\n" + "  <text fill=\"#FF00FF\" font-size=\"0.029\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0355\" y=\"-1.0645\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group and a
     * custom copyright notice using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportsWithCustomCopyrightNoticeForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED), new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setCustomCopyrightNotice("John Doe");
        String actual = exporter.export(plan);
        String year = new SimpleDateFormat("yyyy", Locale.US).format(new Date());
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.666667\" cy=\"-0\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">© " + year
                + " John Doe, chart produced using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group and a
     * background color using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportsWithBackgroundColorForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED), new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.1\" width=\"2.1\" x=\"-1.05\" y=\"-1.05\"/>\n" + "  <g>\n"
                + "    <circle cx=\"-0.666667\" cy=\"-0\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group, a title
     * and a background color using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportsWithTitleAndBackgroundColorForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED), new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        exporter.setTitle("Lorem Ipsum");
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1200\" viewBox=\"-1.05 -1.15 2.1 1.2\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.2\" width=\"2.1\" x=\"-1.05\" y=\"-1.15\"/>\n"
                + "  <text fill=\"black\" font-size=\"0.05\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.05\">Lorem Ipsum</text>\n" + "  <g>\n"
                + "    <circle cx=\"-0.666667\" cy=\"-0\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.15)\" x=\"1.0395\" y=\"-1.1605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group and one for the blue group, a title,
     * a subtitle and a background color using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportsWithTitleAndSubtitleAndBackgroundColorForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED), new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        exporter.setTitle("Lorem Ipsum");
        exporter.setSubtitle("Dolor Sit Amet");
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1285\" viewBox=\"-1.05 -1.235 2.1 1.285\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.285\" width=\"2.1\" x=\"-1.05\" y=\"-1.235\"/>\n"
                + "  <text fill=\"black\" font-size=\"0.05\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.135\">Lorem Ipsum</text>\n"
                + "  <text fill=\"black\" font-size=\"0.035\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.05\">Dolor Sit Amet</text>\n" + "  <g>\n"
                + "    <circle cx=\"-0.666667\" cy=\"-0\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.235)\" x=\"1.0395\" y=\"-1.2455\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for the red/magenta group and one for the
     * blue group using the default hemicycle layout to SVG.
     */
    @Test
    void svgExportWithLegendForTwoRedMagentaAndOneBlueSeatsInADefaultHemicycleLayout() {
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(1, RED_MAGENTA_GREEN, "Red/Magenta/Green"),
                new ParliamentaryGroup(2, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"2900\" viewBox=\"-1.05 -1.05 2.1 2.9\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                + "      <path d=\"M -0.666667 -0 L -0.666667 -0.3 A 0.3 0.3 0 0 1 -0.406859 0.15 Z\""
                + " fill=\"#FF0000\"/>\n"
                + "      <path d=\"M -0.666667 -0 L -0.406859 0.15 A 0.3 0.3 0 0 1 -0.926474 0.15 Z\""
                + " fill=\"#FF00FF\"/>\n"
                + "      <path d=\"M -0.666667 -0 L -0.926474 0.15 A 0.3 0.3 0 0 1 -0.666667 -0.3 Z\""
                + " fill=\"#00FF00\"/>\n" + "    </g>\n"
                + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0.666667\" cy=\"-0\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "  </g>\n" + "  <g>\n"
                + "    <g>\n"
                + "      <path d=\"M -0.7 0.6 L -0.7 0.3 A 0.3 0.3 0 0 1 -0.440192 0.75 Z\" fill=\"#FF0000\"/>\n"
                + "      <path d=\"M -0.7 0.6 L -0.440192 0.75 A 0.3 0.3 0 0 1 -0.959808 0.75 Z\" fill=\"#FF00FF\"/>\n"
                + "      <path d=\"M -0.7 0.6 L -0.959808 0.75 A 0.3 0.3 0 0 1 -0.7 0.3 Z\" fill=\"#00FF00\"/>\n"
                + "    </g>\n" + "    <text fill=\"black\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\""
                + " y=\"0.7\">Red/Magenta/Green (1)</text>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"-0.7\" cy=\"1.5\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"1.6\">Blue"
                + " (2)</text>\n" + "  </g>\n" + "  <text fill=\"black\" font-size=\"0.029\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0355\" y=\"-1.0645\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }
}
