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
        String expected =
                "<svg height=\"805.586627\" viewBox=\"-0.408555 -1.05 0.817109 0.805587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.75\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.008171\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.404469\" y=\"-1.054086\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"805.586627\" viewBox=\"-0.408555 -1.05 0.817109 0.805587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.558333\">R</text>\n"
                        + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.75\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.725\">R</text>\n"
                        + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.891667\">B</text>\n"
                        + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.008171\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.404469\" y=\"-1.054086\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"805.586627\" viewBox=\"-0.408555 -1.05 0.817109 0.805587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" transform=\"rotate(0 0,-0.583333)\" x=\"0\" y=\"-0.558333\">R</text>\n"
                        + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.75\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" transform=\"rotate(0 0,-0.75)\" x=\"0\" y=\"-0.725\">R</text>\n"
                        + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" transform=\"rotate(0 0,-0.916667)\" x=\"0\" y=\"-0.891667\">B</text>\n"
                        + "    </g>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.008171\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.404469\" y=\"-1.054086\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"1255.586627\" viewBox=\"-0.408555 -1.05 0.817109 1.255587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.75\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n" + "  </g>\n"
                        + "  <g>\n" + "    <circle cx=\"-0.283555\" cy=\"-0.144413\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"-0.119413\">Red (2)</text>\n"
                        + "  </g>\n" + "  <g>\n"
                        + "    <circle cx=\"-0.283555\" cy=\"0.080587\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"0.105587\">Blue (1)</text>\n"
                        + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.012556\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.402277\" y=\"-1.056278\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"1255.586627\" viewBox=\"-0.408555 -1.05 0.817109 1.255587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <circle cx=\"-0.178833\" cy=\"-0.899053\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.75\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0.178833\" cy=\"-0.899053\" fill=\"#0000FF\" r=\"0.075\"/>\n" + "  </g>\n"
                        + "  <g>\n" + "    <circle cx=\"-0.283555\" cy=\"-0.144413\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"-0.119413\">Red (2)</text>\n"
                        + "  </g>\n" + "  <g>\n"
                        + "    <circle cx=\"-0.283555\" cy=\"0.080587\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"0.105587\">Blue (2)</text>\n"
                        + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.012556\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.402277\" y=\"-1.056278\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"1480.586627\" viewBox=\"-0.408555 -1.05 0.817109 1.480587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <circle cx=\"-0.178833\" cy=\"-0.899053\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.75\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0.178833\" cy=\"-0.899053\" fill=\"#0000FF\" r=\"0.075\"/>\n" + "  </g>\n"
                        + "  <g>\n" + "    <circle cx=\"-0.283555\" cy=\"-0.144413\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"-0.119413\">Red (2)</text>\n"
                        + "  </g>\n" + "  <g>\n"
                        + "    <circle cx=\"-0.283555\" cy=\"0.080587\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"0.105587\">Blue (2)</text>\n"
                        + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.014806\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.401152\" y=\"-1.057403\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"1255.586627\" viewBox=\"-0.408555 -1.05 0.817109 1.255587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <circle cx=\"-0.178833\" cy=\"-0.899053\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.75\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0.178833\" cy=\"-0.899053\" fill=\"#FF00FF\" r=\"0.075\"/>\n" + "  </g>\n"
                        + "  <g>\n" + "    <circle cx=\"-0.283555\" cy=\"-0.144413\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"-0.119413\">Red (2)</text>\n"
                        + "  </g>\n" + "  <g>\n"
                        + "    <circle cx=\"0.075\" cy=\"-0.144413\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"0.1875\" y=\"-0.119413\">Blue (1)</text>\n"
                        + "  </g>\n" + "  <g>\n"
                        + "    <circle cx=\"-0.104277\" cy=\"0.080587\" fill=\"#FF00FF\" r=\"0.075\"/>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"0.008223\" y=\"0.105587\">Magenta (1)</text>\n"
                        + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.012556\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.402277\" y=\"-1.056278\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"1255.586627\" viewBox=\"-0.408555 -1.05 0.817109 1.255587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.558333\">R</text>\n"
                        + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.75\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.725\">R</text>\n"
                        + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.891667\">B</text>\n"
                        + "    </g>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.283555\" cy=\"-0.144413\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"-0.283555\" y=\"-0.119413\">R</text>\n"
                        + "    </g>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"-0.119413\">Red (2)</text>\n"
                        + "  </g>\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.283555\" cy=\"0.080587\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"-0.283555\" y=\"0.105587\">B</text>\n"
                        + "    </g>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"0.105587\">Blue (1)</text>\n"
                        + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.012556\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.402277\" y=\"-1.056278\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"1255.586627\" viewBox=\"-0.408555 -1.05 0.817109 1.255587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.558333\">R</text>\n"
                        + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.75\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.725\">R</text>\n"
                        + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.891667\">B</text>\n"
                        + "    </g>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.283555\" cy=\"-0.144413\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.075\" text-anchor=\"middle\" x=\"-0.283555\" y=\"-0.119413\">R</text>\n"
                        + "    </g>\n"
                        + "    <text fill=\"black\" font-family=\"Lato\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"-0.119413\">Red (2)</text>\n"
                        + "  </g>\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.283555\" cy=\"0.080587\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.075\" text-anchor=\"middle\" x=\"-0.283555\" y=\"0.105587\">B</text>\n"
                        + "    </g>\n"
                        + "    <text fill=\"black\" font-family=\"Lato\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"0.105587\">Blue (1)</text>\n"
                        + "  </g>\n"
                        + "  <text fill=\"black\" font-family=\"Lato\" font-size=\"0.012556\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.402277\" y=\"-1.056278\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"1255.586627\" viewBox=\"-0.408555 -1.05 0.817109 1.255587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.558333\">R</text>\n"
                        + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.75\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.725\">R</text>\n"
                        + "    </g>\n" + "    <g>\n"
                        + "      <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"0\" y=\"-0.891667\">B</text>\n"
                        + "    </g>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.283555\" cy=\"-0.144413\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"-0.283555\" y=\"-0.119413\">R</text>\n"
                        + "    </g>\n"
                        + "    <text fill=\"#FF00FF\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"-0.119413\">Red (2)</text>\n"
                        + "  </g>\n" + "  <g>\n" + "    <g>\n"
                        + "      <circle cx=\"-0.283555\" cy=\"0.080587\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "      <text fill=\"white\" font-size=\"0.075\" text-anchor=\"middle\" x=\"-0.283555\" y=\"0.105587\">B</text>\n"
                        + "    </g>\n"
                        + "    <text fill=\"#FF00FF\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"0.105587\">Blue (1)</text>\n"
                        + "  </g>\n"
                        + "  <text fill=\"#FF00FF\" font-size=\"0.012556\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.402277\" y=\"-1.056278\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"805.586627\" viewBox=\"-0.408555 -1.05 0.817109 0.805587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.75\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.008171\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.404469\" y=\"-1.054086\">© "
                        + year + " John Doe, chart produced using SHecC</text>\n" + "</svg>";
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
        String expected =
                "<svg height=\"805.586627\" viewBox=\"-0.408555 -1.05 0.817109 0.805587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <rect fill=\"#FFFFFF\" height=\"0.805587\" width=\"0.817109\" x=\"-0.408555\" y=\"-1.05\"/>\n"
                        + "  <g>\n" + "    <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.75\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.008171\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.404469\" y=\"-1.054086\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"905.586627\" viewBox=\"-0.408555 -1.15 0.817109 0.905587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <rect fill=\"#FFFFFF\" height=\"0.905587\" width=\"0.817109\" x=\"-0.408555\" y=\"-1.15\"/>\n"
                        + "  <text fill=\"black\" font-size=\"0.05\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\" y=\"-1.05\">Lorem Ipsum</text>\n"
                        + "  <g>\n" + "    <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.75\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.009056\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.15)\" x=\"0.404027\" y=\"-1.154528\">Chart produced using SHecC</text>\n"
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
        String expected =
                "<svg height=\"990.586627\" viewBox=\"-0.408555 -1.235 0.817109 0.990587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <rect fill=\"#FFFFFF\" height=\"0.990587\" width=\"0.817109\" x=\"-0.408555\" y=\"-1.235\"/>\n"
                        + "  <text fill=\"black\" font-size=\"0.05\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\" y=\"-1.135\">Lorem Ipsum</text>\n"
                        + "  <text fill=\"black\" font-size=\"0.035\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\" y=\"-1.05\">Dolor Sit Amet</text>\n"
                        + "  <g>\n" + "    <circle cx=\"0\" cy=\"-0.583333\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.75\" fill=\"#FF0000\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n" + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.009906\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.235)\" x=\"0.403602\" y=\"-1.239953\">Chart produced using SHecC</text>\n"
                        + "</svg>";
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
        String expected =
                "<svg height=\"1255.586627\" viewBox=\"-0.408555 -1.05 0.817109 1.255587\" width=\"817.109071\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                        + "  <g>\n" + "    <g>\n"
                        + "      <path d=\"M 0 -0.583333 L 0 -0.658333 A 0.075 0.075 0 0 1 0.064952 -0.545833 Z\" fill=\"#FF0000\"/>\n"
                        + "      <path d=\"M 0 -0.583333 L 0.064952 -0.545833 A 0.075 0.075 0 0 1 -0.064952 -0.545833 Z\" fill=\"#FF00FF\"/>\n"
                        + "      <path d=\"M 0 -0.583333 L -0.064952 -0.545833 A 0.075 0.075 0 0 1 0 -0.658333 Z\" fill=\"#00FF00\"/>\n"
                        + "    </g>\n" + "    <circle cx=\"0\" cy=\"-0.75\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "    <circle cx=\"0\" cy=\"-0.916667\" fill=\"#0000FF\" r=\"0.075\"/>\n" + "  </g>\n"
                        + "  <g>\n" + "    <g>\n"
                        + "      <path d=\"M -0.283555 -0.144413 L -0.283555 -0.219413 A 0.075 0.075 0 0 1 -0.218603 -0.106913 Z\" fill=\"#FF0000\"/>\n"
                        + "      <path d=\"M -0.283555 -0.144413 L -0.218603 -0.106913 A 0.075 0.075 0 0 1 -0.348506 -0.106913 Z\" fill=\"#FF00FF\"/>\n"
                        + "      <path d=\"M -0.283555 -0.144413 L -0.348506 -0.106913 A 0.075 0.075 0 0 1 -0.283555 -0.219413 Z\" fill=\"#00FF00\"/>\n"
                        + "    </g>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"-0.119413\">Red/Magenta/Green (1)</text>\n"
                        + "  </g>\n" + "  <g>\n"
                        + "    <circle cx=\"-0.283555\" cy=\"0.080587\" fill=\"#0000FF\" r=\"0.075\"/>\n"
                        + "    <text fill=\"black\" font-size=\"0.075\" text-anchor=\"start\" x=\"-0.171055\" y=\"0.105587\">Blue (2)</text>\n"
                        + "  </g>\n"
                        + "  <text fill=\"black\" font-size=\"0.012556\" text-anchor=\"end\" transform=\"rotate(270 0.408555,-1.05)\" x=\"0.402277\" y=\"-1.056278\">Chart produced using SHecC</text>\n"
                        + "</svg>";
        assertEquals(expected, actual);
    }
}
