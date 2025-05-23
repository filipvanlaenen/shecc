package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.SortedCollection;
import net.filipvanlaenen.shecc.export.SeatingPlanExporter;

/**
 * Unit tests on the <code>CommandLineInterface</code> class.
 */
public class CommandLineInterfaceTest {
    /**
     * The magic number three.
     */
    private static final int THREE = 3;
    /**
     * The magic number six.
     */
    private static final int SIX = 6;
    /**
     * The magic number one hundred fifty.
     */
    private static final int ONE_HUNDRED_FIFTY = 150;
    /**
     * Magic number for the color blue.
     */
    private static final int BLUE = 0x0000FF;
    /**
     * Magic number for the color green.
     */
    private static final int GREEN = 0x00FF00;
    /**
     * Magic number for the color grey.
     */
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
    /**
     * Magic number for the color yellow.
     */
    private static final int YELLOW = 0xFFFF00;
    /**
     * A parliamentary group with one seat for the blue party.
     */
    private static final ParliamentaryGroup ONE_BLUE = new ParliamentaryGroup(1, BLUE, "Blue", "B");
    /**
     * The seat positions for a hemicycle layout with three seats.
     */
    private static final SortedCollection<SeatPosition> THREE_SEAT_POSITIONS =
            new HemicycleLayout(THREE).getSeatPositions();
    /**
     * The seat positions for a hemicycle layout with six seats.
     */
    private static final SortedCollection<SeatPosition> SIX_SEAT_POSITIONS =
            new HemicycleLayout(SIX).getSeatPositions();

    /**
     * Test verifying that the command-line interface produces a simple seating plan without a legend if the names and
     * the letters are missing.
     */
    @Test
    void cliProducesSimpleSeatingPlanIfNamesAndLettersAreMissing() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..,1.0000FF..");
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, new ParliamentaryGroup(2, RED),
                new ParliamentaryGroup(1, BLUE));
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, "Red"), new ParliamentaryGroup(1, BLUE, "Blue"));
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
        RowConnectedSeatingPlan plan =
                new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS, new ParliamentaryGroup(2, RED, "Red", "R"), ONE_BLUE);
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, "Red"), new ParliamentaryGroup(1, BLUE, "Blue"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setDisplayLegend(true);
        exporter.setLegendLabelWidthRatio(THREE);
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(SIX_SEAT_POSITIONS,
                new ParliamentaryGroup(new DifferentiatedGroupSize(1, 2, THREE), RED, "Red", "R"),
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(1, new int[] {RED, GREEN}, "Red/Green", "R"),
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(SIX_SEAT_POSITIONS,
                new ParliamentaryGroup(new DifferentiatedGroupSize(1, 2, THREE), new int[] {RED, GREEN, GREY},
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, "Red", "R"), new ParliamentaryGroup(1, BLUE, "Blue", "B"));
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
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
        RowConnectedSeatingPlan plan = new RowConnectedSeatingPlan(THREE_SEAT_POSITIONS,
                new ParliamentaryGroup(2, RED, null, "R"), new ParliamentaryGroup(1, BLUE, null, "B"));
        SeatingPlanExporter exporter = new SeatingPlanExporter();
        exporter.setAngle(ONE_HUNDRED_FIFTY);
        String expected = exporter.export(plan);
        assertEquals(expected, actual);
    }
}
