package net.filipvanlaenen.shecc.export;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
     * Magic number for the color blue.
     */
    private static final int BLUE = 0x0000FF;
    /**
     * Magic number for the color magenta.
     */
    private static final int MAGENTA = 0xFF00FF;

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
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group
     * and one for the blue group using the default hemicycle layout to SVG, using
     * the letter R for the red group and B for the blue group.
     */
    @Test
    void svgExportForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayout() {
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        groups.add(new ParliamentaryGroup(2, RED, null, "R"));
        groups.add(new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\" y=\"-0.566667\">R</text>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group
     * and one for the blue group using the default hemicycle layout to SVG, using
     * the letter R for the red group and B for the blue group, and rotating the
     * letters to the center.
     */
    @Test
    void svgExportForTwoRedAndOneBlueSeatsWithRotatedLettersInADefaultHemicycleLayout() {
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        groups.add(new ParliamentaryGroup(2, RED, null, "R"));
        groups.add(new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setRotateLetters(true);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\""
                + " transform=\"rotate(-60 -0.57735,-0.333333)\" x=\"-0.57735\" y=\"-0.233333\">R</text>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\""
                + " transform=\"rotate(0 0,-0.666667)\" x=\"0\" y=\"-0.566667\">R</text>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\""
                + " transform=\"rotate(60 0.57735,-0.333333)\" x=\"0.57735\" y=\"-0.233333\">B</text>\n"
                + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legen with two seats for the
     * red group and one for the blue group using the default hemicycle layout to
     * SVG.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        groups.add(new ParliamentaryGroup(2, RED, "Red"));
        groups.add(new ParliamentaryGroup(1, BLUE, "Blue"));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"2000\" viewBox=\"-1.05 -1.05 2.1 2\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <circle cx=\"-0.7\" cy=\"0.6\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"0.7\">Red"
                + " (2)</text>\n" + "  <circle cx=\"0.3\" cy=\"0.6\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.3\" text-anchor=\"start\" x=\"0.75\" y=\"0.7\">Blue"
                + " (1)</text>\n" + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for
     * the red group and one for the blue group using the default hemicycle layout
     * to SVG, using the letter R for the red group and B for the blue group.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayout() {
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        groups.add(new ParliamentaryGroup(2, RED, "Red", "R"));
        groups.add(new ParliamentaryGroup(1, BLUE, "Blue", "B"));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"2000\" viewBox=\"-1.05 -1.05 2.1 2\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\" y=\"-0.566667\">R</text>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  <circle cx=\"-0.7\" cy=\"0.6\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.7\" y=\"0.7\">R</text>\n"
                + "  <text fill=\"#000000\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"0.7\">Red"
                + " (2)</text>\n" + "  <circle cx=\"0.3\" cy=\"0.6\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.3\" y=\"0.7\">B</text>\n"
                + "  <text fill=\"#000000\" font-size=\"0.3\" text-anchor=\"start\" x=\"0.75\" y=\"0.7\">Blue"
                + " (1)</text>\n" + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for
     * the red group and one for the blue group using the default hemicycle layout
     * to SVG, using the letter R for the red group and B for the blue group, and
     * with a text font set.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayoutWithASpecificFont() {
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        groups.add(new ParliamentaryGroup(2, RED, "Red", "R"));
        groups.add(new ParliamentaryGroup(1, BLUE, "Blue", "B"));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setFontFamily("Lato");
        String actual = exporter.export(plan);
        String expected = "<svg height=\"2000\" viewBox=\"-1.05 -1.05 2.1 2\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\""
                + " x=\"-0.57735\" y=\"-0.233333\">R</text>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  <circle cx=\"-0.7\" cy=\"0.6\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.7\""
                + " y=\"0.7\">R</text>\n"
                + "  <text fill=\"#000000\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\""
                + " y=\"0.7\">Red (2)</text>\n" + "  <circle cx=\"0.3\" cy=\"0.6\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.3\""
                + " y=\"0.7\">B</text>\n"
                + "  <text fill=\"#000000\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"start\" x=\"0.75\""
                + " y=\"0.7\">Blue (1)</text>\n"
                + "  <text fill=\"#000000\" font-family=\"Lato\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with legend with two seats for
     * the red group and one for the blue group using the default hemicycle layout
     * to SVG, using the letter R for the red group and B for the blue group, and
     * with a custom text color.
     */
    @Test
    void svgExportWithLegendForTwoRedAndOneBlueSeatsWithLettersInADefaultHemicycleLayoutWithATextColor() {
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        groups.add(new ParliamentaryGroup(2, RED, "Red", "R"));
        groups.add(new ParliamentaryGroup(1, BLUE, "Blue", "B"));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setFontColor(MAGENTA);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"2000\" viewBox=\"-1.05 -1.05 2.1 2\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\" y=\"-0.566667\">R</text>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  <circle cx=\"-0.7\" cy=\"0.6\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.7\" y=\"0.7\">R</text>\n"
                + "  <text fill=\"#FF00FF\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"0.7\">Red"
                + " (2)</text>\n" + "  <circle cx=\"0.3\" cy=\"0.6\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.3\" y=\"0.7\">B</text>\n"
                + "  <text fill=\"#FF00FF\" font-size=\"0.3\" text-anchor=\"start\" x=\"0.75\" y=\"0.7\">Blue"
                + " (1)</text>\n" + "  <text fill=\"#FF00FF\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group
     * and one for the blue group and a custom copyright notice using the default
     * hemicycle layout to SVG.
     */
    @Test
    void svgExportsWithCustomCopyrightNoticeForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        groups.add(new ParliamentaryGroup(2, RED));
        groups.add(new ParliamentaryGroup(1, BLUE));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setCustomCopyrightNotice("John Doe");
        String actual = exporter.export(plan);
        String year = new SimpleDateFormat("yyyy", Locale.US).format(new Date());
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">© " + year
                + " John Doe, chart produced using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group
     * and one for the blue group and a background color using the default hemicycle
     * layout to SVG.
     */
    @Test
    void svgExportsWithBackgroundColorForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        groups.add(new ParliamentaryGroup(2, RED));
        groups.add(new ParliamentaryGroup(1, BLUE));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.1\" width=\"2.1\" x=\"-1.05\" y=\"-1.05\"/>\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group
     * and one for the blue group, a title and a background color using the default
     * hemicycle layout to SVG.
     */
    @Test
    void svgExportsWithTitleAndBackgroundColorForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        groups.add(new ParliamentaryGroup(2, RED));
        groups.add(new ParliamentaryGroup(1, BLUE));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        exporter.setTitle("Lorem Ipsum");
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1200\" viewBox=\"-1.05 -1.15 2.1 1.2\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.2\" width=\"2.1\" x=\"-1.05\" y=\"-1.15\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.05\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.05\">Lorem Ipsum</text>\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.15)\" x=\"1.0395\" y=\"-1.1605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a seating plan with two seats for the red group
     * and one for the blue group, a title, a subtitle and a background color using
     * the default hemicycle layout to SVG.
     */
    @Test
    void svgExportsWithTitleAndSubtitleAndBackgroundColorForTwoRedAndOneBlueSeatsInADefaultHemicycleLayout() {
        List<ParliamentaryGroup> groups = new ArrayList<ParliamentaryGroup>();
        groups.add(new ParliamentaryGroup(2, RED));
        groups.add(new ParliamentaryGroup(1, BLUE));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        exporter.setTitle("Lorem Ipsum");
        exporter.setSubtitle("Dolor Sit Amet");
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1285\" viewBox=\"-1.05 -1.235 2.1 1.285\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.285\" width=\"2.1\" x=\"-1.05\" y=\"-1.235\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.05\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.135\">Lorem Ipsum</text>\n"
                + "  <text fill=\"#000000\" font-size=\"0.035\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.05\">Dolor Sit Amet</text>\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.235)\" x=\"1.0395\" y=\"-1.2455\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }
}
