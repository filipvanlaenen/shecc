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
        String expected = "<svg height=\"1000\" viewBox=\"-1 -1 2 1\" width=\"2000\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "</svg>";
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
        groups.add(new ParliamentaryGroup(2, RED, "R"));
        groups.add(new ParliamentaryGroup(1, BLUE, "B"));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1000\" viewBox=\"-1 -1 2 1\" width=\"2000\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"MIDDLE\" x=\"-0.57735\" y=\"-0.233333\">R</text>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"MIDDLE\" x=\"0\" y=\"-0.566667\">R</text>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"MIDDLE\" x=\"0.57735\" y=\"-0.233333\">B</text>\n"
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
        groups.add(new ParliamentaryGroup(2, RED, "R"));
        groups.add(new ParliamentaryGroup(1, BLUE, "B"));
        SeatingPlan plan = new SeatingPlan(groups);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setRotateLetters(true);
        String actual = exporter.export(plan);
        String expected = "<svg height=\"1000\" viewBox=\"-1 -1 2 1\" width=\"2000\" xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"MIDDLE\" transform=\"rotate(-60 -0.57735,-0.333333)\" x=\"-0.57735\" y=\"-0.233333\">R</text>\n"
                + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"MIDDLE\" transform=\"rotate(0 0,-0.666667)\" x=\"0\" y=\"-0.566667\">R</text>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"MIDDLE\" transform=\"rotate(60 0.57735,-0.333333)\" x=\"0.57735\" y=\"-0.233333\">B</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }
}
