package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>CommandLineInterface</code> class.
 */
public class CommandLineInterfaceTest {

    /**
     * Test verifying that the command-line interface produces a simple seating plan
     * without a legend if the names and the letters are missing.
     */
    @Test
    void cliProducesSimpleSeatingPlanIfNamesAndLettersAreMissing() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..,1.0000FF..");
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
     * Test verifying that the command-line interface produces a seating plan
     * without a legend if the names are missing.
     */
    @Test
    void cliProducesSeatingPlanWithoutLegendIfNamesAreMissing() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B");
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
     * Test verifying that the command-line interface produces a seating plan with a
     * legend if the names are present.
     */
    @Test
    void cliProducesSeatingPlanWithLegendIfNamesArePresent() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000.Red.,1.0000FF.Blue.");
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
     * Test verifying that the command-line interface produces a seating plan with a
     * legend if the names and characters are present.
     */
    @Test
    void cliProducesSeatingPlanWithCharactersAndLegendIfNamesArePresent() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000.Red.R,1.0000FF.Blue.B");
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
     * Test verifying that the command-line interface produces a seating plan with a
     * legend if the names are present, with all text in the right font family and
     * color.
     */
    @Test
    void cliProducesSeatingPlanWithLegendInCorrectFontFamilyAndColor() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000.Red.R,1.0000FF.Blue.B", "--font-family=Lato", "--font-color=FF00FF");
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
                + "  <text fill=\"#FF00FF\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\""
                + " y=\"0.7\">Red (2)</text>\n" + "  <circle cx=\"0.3\" cy=\"0.6\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.3\""
                + " y=\"0.7\">B</text>\n"
                + "  <text fill=\"#FF00FF\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"start\" x=\"0.75\""
                + " y=\"0.7\">Blue (1)</text>\n"
                + "  <text fill=\"#FF00FF\" font-family=\"Lato\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan
     * without a custom copyright notice when specified.
     */
    @Test
    void cliProducesSeatingPlanWithCustomCopyrightNotice() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B", "--copyright-notice=John Doe");
        String year = new SimpleDateFormat("yyyy", Locale.US).format(new Date());
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\" y=\"-0.566667\">R</text>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">© " + year
                + " John Doe, chart produced using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan
     * without a background color when specified.
     */
    @Test
    void cliProducesSeatingPlanWithBackgroundColor() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B", "--background-color=FFFFFF");
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.1\" width=\"2.1\" x=\"-1.05\" y=\"-1.05\"/>\n"
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
     * Test verifying that the command-line interface produces a seating plan
     * without a title when specified, and the background rectangle scaled
     * accordingly.
     */
    @Test
    void cliProducesSeatingPlanWithTitle() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B", "--background-color=FFFFFF", "--title=Lorem Ipsum");
        String expected = "<svg height=\"1200\" viewBox=\"-1.05 -1.15 2.1 1.2\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.2\" width=\"2.1\" x=\"-1.05\" y=\"-1.15\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.05\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.05\">Lorem Ipsum</text>\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\" y=\"-0.566667\">R</text>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.15)\" x=\"1.0395\" y=\"-1.1605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan
     * without a title and a subtitle when specified, and the background rectangle
     * scaled accordingly.
     */
    @Test
    void cliProducesSeatingPlanWithTitleAndSubtitle() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B", "--background-color=FFFFFF", "--title=Lorem Ipsum",
                "--subtitle=Dolor Sit Samet");
        String expected = "<svg height=\"1285\" viewBox=\"-1.05 -1.235 2.1 1.285\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.285\" width=\"2.1\" x=\"-1.05\" y=\"-1.235\"/>\n"
                + "  <text fill=\"#000000\" font-size=\"0.05\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.135\">Lorem Ipsum</text>\n"
                + "  <text fill=\"#000000\" font-size=\"0.035\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.05\">Dolor Sit Samet</text>\n"
                + "  <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "  <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\" y=\"-0.566667\">R</text>\n"
                + "  <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "  <text fill=\"#FFFFFF\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  <text fill=\"#000000\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.235)\" x=\"1.0395\" y=\"-1.2455\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }
}
