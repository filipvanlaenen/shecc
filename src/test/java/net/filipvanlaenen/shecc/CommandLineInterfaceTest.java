package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.shecc.export.SeatingPlanExporter;

/**
 * Unit tests on the <code>CommandLineInterface</code> class.
 */
public class CommandLineInterfaceTest {
    /**
     * Magic number for the color blue.
     */
    private static final int BLUE = 0x0000FF;
    /**
     * Magic number for the color green.
     */
    private static final int GREEN = 0x00FF00;
    private static final int GREY = 0x777777;
    /**
     * Magic number for the color magenta.
     */
    private static final int MAGENTA = 0x00FFFF;
    /**
     * Magic number for the color pink.
     */
    private static final int PINK = 0xFF00FF;
    /**
     * Magic number for the color red.
     */
    private static final int RED = 0xFF0000;
    /**
     * Magic number for the color white.
     */
    private static final int WHITE = 0xFFFFFF;
    private static final int YELLOW = 0xFFFF00;
    private static final ParliamentaryGroup ONE_BLUE = new ParliamentaryGroup(1, BLUE, "Blue", "B");

    /**
     * Test verifying that the command-line interface produces a simple seating plan without a legend if the names and
     * the letters are missing.
     */
    @Test
    void cliProducesSimpleSeatingPlanIfNamesAndLettersAreMissing() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..,1.0000FF..");
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED), new ParliamentaryGroup(1, BLUE));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan without a legend if the names are missing.
     */
    @Test
    void cliProducesSeatingPlanWithoutLegendIfNamesAreMissing() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B");
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan with a legend if the names are present.
     */
    @Test
    void cliProducesSeatingPlanWithLegendIfNamesArePresent() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000.Red.,1.0000FF.Blue.");
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, "Red"), new ParliamentaryGroup(1, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan with a legend if the names and characters
     * are present.
     */
    @Test
    void cliProducesSeatingPlanWithCharactersAndLegendIfNamesArePresent() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000.Red.R,1.0000FF.Blue.B");
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED, "Red", "R"), ONE_BLUE);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan with a legend with a different legend
     * label width if the names are present and the width changed.
     */
    @Test
    void cliProducesSeatingPlanWithLegendWithDifferentLegendLabelWidthRatio() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000.Red.,1.0000FF.Blue.", "--legend-label-width-ratio=3");
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, "Red"), new ParliamentaryGroup(1, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setLegendLabelWidthRatio(3);
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan and legend according to the differentiated
     * sizes.
     */
    @Test
    void cliProducesSeatingPlanWithDifferentiatedGroupSizes() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("1:2:3.FF0000.Red.R,1:2.00FF00.Green.G,1.0000FF.Blue.B");
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(new DifferentiatedGroupSize(1, 2, 3), RED, "Red", "R"),
                        new ParliamentaryGroup(new DifferentiatedGroupSize(1, 2), GREEN, "Green", "G"), ONE_BLUE);
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan and legend for groups with multiple
     * colors.
     */
    @Test
    void cliProducesSeatingPlanForGroupsWithMultipleColors() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("1.FF0000:00FF00.Red/Green.R,2.0000FF.Blue.B");
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(1, new int[] {RED, GREEN}, "Red/Green", "R"),
                new ParliamentaryGroup(2, BLUE, "Blue", "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan and legend for groups with multiple colors
     * and differentiated group sizes.
     */
    @Test
    void cliProducesSeatingPlanForGroupsWithMultipleColorsAndDifferentiatedGroupSizes() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("1:2:3.FF0000:00FF00:777777.Red/Green/Grey.R,1:2.0000FF:FFFF00.Blue/Yellow.B,"
                + "1.FF00FF:00FFFF.Pink/Magenta.P");
        SeatingPlan plan = new SeatingPlan(
                new ParliamentaryGroup(new DifferentiatedGroupSize(1, 2, 3), new int[] {RED, GREEN, GREY},
                        "Red/Green/Grey", "R"),
                new ParliamentaryGroup(new DifferentiatedGroupSize(1, 2), new int[] {BLUE, YELLOW}, "Blue/Yellow", "B"),
                new ParliamentaryGroup(1, new int[] {PINK, MAGENTA}, "Pink/Magenta", "P"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan with a legend if the names are present,
     * with all text in the right font family and color.
     */
    @Test
    void cliProducesSeatingPlanWithLegendInCorrectFontFamilyAndColor() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000.Red.R,1.0000FF.Blue.B", "--font-family=Lato", "--font-color=FF00FF");
        SeatingPlan plan = new SeatingPlan(new ParliamentaryGroup(2, RED, "Red", "R"),
                new ParliamentaryGroup(1, BLUE, "Blue", "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setFontColor(PINK);
        exporter.setFontFamily("Lato");
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan without a custom copyright notice when
     * specified.
     */
    @Test
    void cliProducesSeatingPlanWithCustomCopyrightNotice() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B", "--copyright-notice=John Doe");
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setCustomCopyrightNotice("John Doe");
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan without a background color when specified.
     */
    @Test
    void cliProducesSeatingPlanWithBackgroundColor() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B", "--background-color=FFFFFF");
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan without a title when specified, and the
     * background rectangle scaled accordingly.
     */
    @Test
    void cliProducesSeatingPlanWithTitle() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B", "--background-color=FFFFFF", "--title=Lorem Ipsum");
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        exporter.setTitle("Lorem Ipsum");
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan without a title and a subtitle when
     * specified, and the background rectangle scaled accordingly.
     */
    @Test
    void cliProducesSeatingPlanWithTitleAndSubtitle() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B", "--background-color=FFFFFF", "--title=Lorem Ipsum",
                "--subtitle=Dolor Sit Amet");
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setBackgroundColor(WHITE);
        exporter.setTitle("Lorem Ipsum");
        exporter.setSubtitle("Dolor Sit Amet");
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan with an angle when specified.
     */
    @Test
    void cliProducesSeatingPlanWithAngle() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B", "--angle=150");
        SeatingPlan plan =
                new SeatingPlan(new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setAngle(150);
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }
}
