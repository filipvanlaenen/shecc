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
     * Test verifying that the command-line interface produces a simple seating plan without a legend if the names and
     * the letters are missing.
     */
    @Test
    void cliProducesSimpleSeatingPlanIfNamesAndLettersAreMissing() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..,1.0000FF..");
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan without a legend if the names are missing.
     */
    @Test
    void cliProducesSeatingPlanWithoutLegendIfNamesAreMissing() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B");
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n"
                + "    <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan with a legend if the names are present.
     */
    @Test
    void cliProducesSeatingPlanWithLegendIfNamesArePresent() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000.Red.,1.0000FF.Blue.");
        String expected = "<svg height=\"2900\" viewBox=\"-1.05 -1.05 2.1 2.9\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "  </g>\n" + "  <g>\n"
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
     * Test verifying that the command-line interface produces a seating plan with a legend if the names and characters
     * are present.
     */
    @Test
    void cliProducesSeatingPlanWithCharactersAndLegendIfNamesArePresent() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000.Red.R,1.0000FF.Blue.B");
        String expected = "<svg height=\"2900\" viewBox=\"-1.05 -1.05 2.1 2.9\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n"
                + "    <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
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
     * Test verifying that the command-line interface produces a seating plan with a legend with a different legend
     * label width if the names are present and the width changed.
     */
    @Test
    void cliProducesSeatingPlanWithLegendWithDifferentLegendLabelWidthRatio() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000.Red.,1.0000FF.Blue.", "--legend-label-width-ratio=3");
        String expected = "<svg height=\"2000\" viewBox=\"-1.05 -1.05 2.1 2\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"-0.7\" cy=\"0.6\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"0.7\">Red"
                + " (2)</text>\n" + "  </g>\n" + "  <g>\n"
                + "    <circle cx=\"0.3\" cy=\"0.6\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"black\" font-size=\"0.3\" text-anchor=\"start\" x=\"0.75\" y=\"0.7\">Blue"
                + " (1)</text>\n" + "  </g>\n" + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
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
        String expected = "<svg height=\"2450\" viewBox=\"-1.05 -1.05 2.1 2.45\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.7699\" cy=\"-0.318903\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.7699\""
                + " y=\"-0.268903\">R</text>\n"
                + "    <circle cx=\"-0.353553\" cy=\"-0.353553\" fill=\"#FF0000\" fill-opacity=\"0.3\" r=\"0.135\""
                + " stroke=\"#FF0000\" stroke-width=\"0.03\"/>\n"
                + "    <text fill=\"#FF0000\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.353553\""
                + " y=\"-0.303553\">R</text>\n"
                + "    <circle cx=\"-0.318903\" cy=\"-0.7699\" fill=\"none\" r=\"0.135\" stroke=\"#FF0000\""
                + " stroke-width=\"0.03\"/>\n"
                + "    <text fill=\"#FF0000\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.318903\""
                + " y=\"-0.7199\">R</text>\n"
                + "    <circle cx=\"0.318903\" cy=\"-0.7699\" fill=\"#00FF00\" fill-opacity=\"0.3\" r=\"0.135\""
                + " stroke=\"#00FF00\" stroke-width=\"0.03\"/>\n"
                + "    <text fill=\"#00FF00\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0.318903\""
                + " y=\"-0.7199\">G</text>\n"
                + "    <circle cx=\"0.353553\" cy=\"-0.353553\" fill=\"#00FF00\" r=\"0.15\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0.353553\""
                + " y=\"-0.303553\">G</text>\n"
                + "    <circle cx=\"0.7699\" cy=\"-0.318903\" fill=\"#0000FF\" r=\"0.15\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0.7699\""
                + " y=\"-0.268903\">B</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.85\" cy=\"0.3\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.85\""
                + " y=\"0.35\">R</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"-0.625\" y=\"0.35\">Red"
                + " (3)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"0.15\" cy=\"0.3\" fill=\"#00FF00\" r=\"0.15\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0.15\""
                + " y=\"0.35\">G</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"0.375\" y=\"0.35\">Green"
                + " (2)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.35\" cy=\"0.75\" fill=\"#0000FF\" r=\"0.15\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.35\""
                + " y=\"0.8\">B</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"-0.125\" y=\"0.8\">Blue"
                + " (1)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.85\" cy=\"1.2\" fill=\"#000000\" r=\"0.15\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.85\""
                + " y=\"1.25\">X</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"-0.625\" y=\"1.25\">Certain"
                + " (P ≥ 97.5%)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.183333\" cy=\"1.2\" fill=\"#000000\" fill-opacity=\"0.3\" r=\"0.135\""
                + " stroke=\"#000000\" stroke-width=\"0.03\"/>\n"
                + "      <text fill=\"black\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.183333\""
                + " y=\"1.25\">X</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"0.041667\""
                + " y=\"1.25\">Likely (P ≥ 50%)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"0.483333\" cy=\"1.2\" fill=\"none\" r=\"0.135\" stroke=\"#000000\""
                + " stroke-width=\"0.03\"/>\n"
                + "      <text fill=\"black\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0.483333\""
                + " y=\"1.25\">X</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"0.708333\""
                + " y=\"1.25\">Unlikely (P &lt; 50%)</text>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.0245\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.03775\" y=\"-1.06225\">Chart produced using"
                + " SHecC</text>\n" + "" + "</svg>";
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
        String expected = "<svg height=\"2900\" viewBox=\"-1.05 -1.05 2.1 2.9\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                + "      <path d=\"M -0.57735 -0.333333 L -0.57735 -0.633333 A 0.3 0.3 0 0 1 -0.57735 -0.033333 Z\""
                + " fill=\"#FF0000\"/>\n"
                + "      <path d=\"M -0.57735 -0.333333 L -0.57735 -0.033333 A 0.3 0.3 0 0 1 -0.57735 -0.633333 Z\""
                + " fill=\"#00FF00\"/>\n" + "    </g>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">B</text>\n"
                + "    <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                + "        <path d=\"M -0.7 0.6 L -0.7 0.3 A 0.3 0.3 0 0 1 -0.7 0.9 Z\" fill=\"#FF0000\"/>\n"
                + "        <path d=\"M -0.7 0.6 L -0.7 0.9 A 0.3 0.3 0 0 1 -0.7 0.3 Z\" fill=\"#00FF00\"/>\n"
                + "      </g>\n" + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.7\""
                + " y=\"0.7\">R</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"0.7\">Red/Green"
                + " (1)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.7\" cy=\"1.5\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.7\""
                + " y=\"1.6\">B</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\" y=\"1.6\">Blue"
                + " (2)</text>\n" + "  </g>\n" + "  <text fill=\"black\" font-size=\"0.029\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0355\" y=\"-1.0645\">Chart produced using SHecC</text>\n"
                + "</svg>";
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
        String expected = "<svg height=\"2450\" viewBox=\"-1.05 -1.05 2.1 2.45\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n" + "    <g>\n"
                + "      <path d=\"M -0.7699 -0.318903 L -0.7699 -0.468903 A 0.15 0.15 0 0 1 -0.639996 -0.243903 Z\""
                + " fill=\"#FF0000\"/>\n"
                + "      <path d=\"M -0.7699 -0.318903 L -0.639996 -0.243903 A 0.15 0.15 0 0 1 -0.899803 -0.243903 Z\""
                + " fill=\"#00FF00\"/>\n"
                + "      <path d=\"M -0.7699 -0.318903 L -0.899803 -0.243903 A 0.15 0.15 0 0 1 -0.7699 -0.468903 Z\""
                + " fill=\"#777777\"/>\n" + "    </g>\n"
                + "    <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.7699\""
                + " y=\"-0.268903\">R</text>\n" + "    <g>\n"
                + "      <path d=\"M -0.353553 -0.353553 L -0.353553 -0.503553 A 0.15 0.15 0 0 1 -0.22365 -0.278553"
                + " Z\" fill=\"#FF0000\" opacity=\"0.3\"/>\n"
                + "      <path d=\"M -0.353553 -0.488553 A 0.135 0.135 0 0 1 -0.23664 -0.286053\" fill=\"none\""
                + " stroke=\"#FF0000\" stroke-width=\"0.03\"/>\n"
                + "      <path d=\"M -0.353553 -0.353553 L -0.22365 -0.278553 A 0.15 0.15 0 0 1 -0.483457 -0.278553"
                + " Z\" fill=\"#00FF00\" opacity=\"0.3\"/>\n"
                + "      <path d=\"M -0.23664 -0.286053 A 0.135 0.135 0 0 1 -0.470467 -0.286053\" fill=\"none\""
                + " stroke=\"#00FF00\" stroke-width=\"0.03\"/>\n"
                + "      <path d=\"M -0.353553 -0.353553 L -0.483457 -0.278553 A 0.15 0.15 0 0 1 -0.353553 -0.503553"
                + " Z\" fill=\"#777777\" opacity=\"0.3\"/>\n"
                + "      <path d=\"M -0.470467 -0.286053 A 0.135 0.135 0 0 1 -0.353553 -0.488553\" fill=\"none\""
                + " stroke=\"#777777\" stroke-width=\"0.03\"/>\n" + "    </g>\n"
                + "    <text fill=\"#FF0000\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.353553\""
                + " y=\"-0.303553\">R</text>\n" + "    <g>\n"
                + "      <path d=\"M -0.318903 -0.9049 A 0.135 0.135 0 0 1 -0.201989 -0.7024\" fill=\"none\""
                + " stroke=\"#FF0000\" stroke-width=\"0.03\"/>\n"
                + "      <path d=\"M -0.201989 -0.7024 A 0.135 0.135 0 0 1 -0.435816 -0.7024\" fill=\"none\""
                + " stroke=\"#00FF00\" stroke-width=\"0.03\"/>\n"
                + "      <path d=\"M -0.435816 -0.7024 A 0.135 0.135 0 0 1 -0.318903 -0.9049\" fill=\"none\""
                + " stroke=\"#777777\" stroke-width=\"0.03\"/>\n" + "    </g>\n"
                + "    <text fill=\"#FF0000\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.318903\""
                + " y=\"-0.7199\">R</text>\n" + "    <g>\n"
                + "      <path d=\"M 0.318903 -0.7699 L 0.318903 -0.9199 A 0.15 0.15 0 0 1 0.318903 -0.6199 Z\""
                + " fill=\"#0000FF\" opacity=\"0.3\"/>\n"
                + "      <path d=\"M 0.318903 -0.9049 A 0.135 0.135 0 0 1 0.318903 -0.6349\" fill=\"none\""
                + " stroke=\"#0000FF\" stroke-width=\"0.03\"/>\n"
                + "      <path d=\"M 0.318903 -0.7699 L 0.318903 -0.6199 A 0.15 0.15 0 0 1 0.318903 -0.9199 Z\""
                + " fill=\"#FFFF00\" opacity=\"0.3\"/>\n"
                + "      <path d=\"M 0.318903 -0.6349 A 0.135 0.135 0 0 1 0.318903 -0.9049\" fill=\"none\""
                + " stroke=\"#FFFF00\" stroke-width=\"0.03\"/>\n" + "    </g>\n"
                + "    <text fill=\"#0000FF\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0.318903\""
                + " y=\"-0.7199\">B</text>\n" + "    <g>\n"
                + "      <path d=\"M 0.353553 -0.353553 L 0.353553 -0.503553 A 0.15 0.15 0 0 1 0.353553 -0.203553 Z\""
                + " fill=\"#0000FF\"/>\n"
                + "      <path d=\"M 0.353553 -0.353553 L 0.353553 -0.203553 A 0.15 0.15 0 0 1 0.353553 -0.503553 Z\""
                + " fill=\"#FFFF00\"/>\n" + "    </g>\n"
                + "    <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0.353553\""
                + " y=\"-0.303553\">B</text>\n" + "    <g>\n"
                + "      <path d=\"M 0.7699 -0.318903 L 0.7699 -0.468903 A 0.15 0.15 0 0 1 0.7699 -0.168903 Z\""
                + " fill=\"#FF00FF\"/>\n"
                + "      <path d=\"M 0.7699 -0.318903 L 0.7699 -0.168903 A 0.15 0.15 0 0 1 0.7699 -0.468903 Z\""
                + " fill=\"#00FFFF\"/>\n" + "    </g>\n"
                + "    <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0.7699\""
                + " y=\"-0.268903\">P</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                + "        <path d=\"M -0.85 0.3 L -0.85 0.15 A 0.15 0.15 0 0 1 -0.720096 0.375 Z\""
                + " fill=\"#FF0000\"/>\n"
                + "        <path d=\"M -0.85 0.3 L -0.720096 0.375 A 0.15 0.15 0 0 1 -0.979904 0.375 Z\""
                + " fill=\"#00FF00\"/>\n"
                + "        <path d=\"M -0.85 0.3 L -0.979904 0.375 A 0.15 0.15 0 0 1 -0.85 0.15 Z\""
                + " fill=\"#777777\"/>\n" + "      </g>\n"
                + "      <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.85\""
                + " y=\"0.35\">R</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"-0.625\""
                + " y=\"0.35\">Red/Green/Grey (3)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                + "        <path d=\"M 0.15 0.3 L 0.15 0.15 A 0.15 0.15 0 0 1 0.15 0.45 Z\" fill=\"#0000FF\"/>\n"
                + "        <path d=\"M 0.15 0.3 L 0.15 0.45 A 0.15 0.15 0 0 1 0.15 0.15 Z\" fill=\"#FFFF00\"/>\n"
                + "      </g>\n" + "      <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0.15\""
                + " y=\"0.35\">B</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"0.375\""
                + " y=\"0.35\">Blue/Yellow (2)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n" + "      <g>\n"
                + "        <path d=\"M -0.35 0.75 L -0.35 0.6 A 0.15 0.15 0 0 1 -0.35 0.9 Z\" fill=\"#FF00FF\"/>\n"
                + "        <path d=\"M -0.35 0.75 L -0.35 0.9 A 0.15 0.15 0 0 1 -0.35 0.6 Z\" fill=\"#00FFFF\"/>\n"
                + "      </g>\n" + "      <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.35\""
                + " y=\"0.8\">P</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"-0.125\""
                + " y=\"0.8\">Pink/Magenta (1)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.85\" cy=\"1.2\" fill=\"#000000\" r=\"0.15\"/>\n"
                + "      <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.85\""
                + " y=\"1.25\">X</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"-0.625\" y=\"1.25\">Certain"
                + " (P ≥ 97.5%)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.183333\" cy=\"1.2\" fill=\"#000000\" fill-opacity=\"0.3\" r=\"0.135\""
                + " stroke=\"#000000\" stroke-width=\"0.03\"/>\n"
                + "      <text fill=\"black\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.183333\""
                + " y=\"1.25\">X</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"0.041667\""
                + " y=\"1.25\">Likely (P ≥ 50%)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"0.483333\" cy=\"1.2\" fill=\"none\" r=\"0.135\" stroke=\"#000000\""
                + " stroke-width=\"0.03\"/>\n"
                + "      <text fill=\"black\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0.483333\""
                + " y=\"1.25\">X</text>\n" + "    </g>\n"
                + "    <text fill=\"black\" font-size=\"0.15\" text-anchor=\"start\" x=\"0.708333\""
                + " y=\"1.25\">Unlikely (P &lt; 50%)</text>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.0245\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.03775\" y=\"-1.06225\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
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
        String expected = "<svg height=\"2900\" viewBox=\"-1.05 -1.05 2.1 2.9\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\""
                + " x=\"-0.57735\" y=\"-0.233333\">R</text>\n"
                + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n"
                + "    <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\""
                + " x=\"0.57735\" y=\"-0.233333\">B</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.7\" cy=\"0.6\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\""
                + " x=\"-0.7\" y=\"0.7\">R</text>\n" + "    </g>\n"
                + "    <text fill=\"#FF00FF\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\""
                + " y=\"0.7\">Red (2)</text>\n" + "  </g>\n" + "  <g>\n" + "    <g>\n"
                + "      <circle cx=\"-0.7\" cy=\"1.5\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "      <text fill=\"white\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"middle\""
                + " x=\"-0.7\" y=\"1.6\">B</text>\n" + "    </g>\n"
                + "    <text fill=\"#FF00FF\" font-family=\"Lato\" font-size=\"0.3\" text-anchor=\"start\" x=\"-0.25\""
                + " y=\"1.6\">Blue (1)</text>\n" + "  </g>\n"
                + "  <text fill=\"#FF00FF\" font-family=\"Lato\" font-size=\"0.029\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0355\" y=\"-1.0645\">Chart produced using SHecC</text>\n"
                + "</svg>";
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
        String year = new SimpleDateFormat("yyyy", Locale.US).format(new Date());
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n"
                + "    <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">© " + year
                + " John Doe, chart produced using SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan without a background color when specified.
     */
    @Test
    void cliProducesSeatingPlanWithBackgroundColor() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B", "--background-color=FFFFFF");
        String expected = "<svg height=\"1100\" viewBox=\"-1.05 -1.05 2.1 1.1\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.1\" width=\"2.1\" x=\"-1.05\" y=\"-1.05\"/>\n" + "  <g>\n"
                + "    <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n"
                + "    <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.05)\" x=\"1.0395\" y=\"-1.0605\">Chart produced using SHecC</text>\n"
                + "</svg>";
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
        String expected = "<svg height=\"1200\" viewBox=\"-1.05 -1.15 2.1 1.2\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.2\" width=\"2.1\" x=\"-1.05\" y=\"-1.15\"/>\n"
                + "  <text fill=\"black\" font-size=\"0.05\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.05\">Lorem Ipsum</text>\n" + "  <g>\n"
                + "    <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n"
                + "    <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.15)\" x=\"1.0395\" y=\"-1.1605\">Chart produced using SHecC</text>\n"
                + "</svg>";
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
                "--subtitle=Dolor Sit Samet");
        String expected = "<svg height=\"1285\" viewBox=\"-1.05 -1.235 2.1 1.285\" width=\"2100\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n"
                + "  <rect fill=\"#FFFFFF\" height=\"1.285\" width=\"2.1\" x=\"-1.05\" y=\"-1.235\"/>\n"
                + "  <text fill=\"black\" font-size=\"0.05\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.135\">Lorem Ipsum</text>\n"
                + "  <text fill=\"black\" font-size=\"0.035\" font-weight=\"bold\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-1.05\">Dolor Sit Samet</text>\n" + "  <g>\n"
                + "    <circle cx=\"-0.57735\" cy=\"-0.333333\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"-0.57735\""
                + " y=\"-0.233333\">R</text>\n" + "    <circle cx=\"0\" cy=\"-0.666667\" fill=\"#FF0000\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.566667\">R</text>\n"
                + "    <circle cx=\"0.57735\" cy=\"-0.333333\" fill=\"#0000FF\" r=\"0.3\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.3\" text-anchor=\"middle\" x=\"0.57735\""
                + " y=\"-0.233333\">B</text>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.021\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.05,-1.235)\" x=\"1.0395\" y=\"-1.2455\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the command-line interface produces a seating plan with an angle when specified.
     */
    @Test
    void cliProducesSeatingPlanWithAngle() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000..R,1.0000FF..B", "--angle=150");
        String expected = "<svg height=\"1100\" viewBox=\"-1.015926 -1.05 2.031852 1.1\" width=\"2031.851653\""
                + " xmlns=\"http://www.w3.org/2000/svg\">\n" + "  <g>\n"
                + "    <circle cx=\"-0.507301\" cy=\"-0.661128\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"-0.507301\""
                + " y=\"-0.611128\">R</text>\n" + "    <circle cx=\"0\" cy=\"-0.5\" fill=\"#FF0000\" r=\"0.15\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0\""
                + " y=\"-0.45\">R</text>\n"
                + "    <circle cx=\"0.507301\" cy=\"-0.661128\" fill=\"#0000FF\" r=\"0.15\"/>\n"
                + "    <text fill=\"white\" font-size=\"0.15\" text-anchor=\"middle\" x=\"0.507301\""
                + " y=\"-0.611128\">B</text>\n" + "  </g>\n"
                + "  <text fill=\"black\" font-size=\"0.020319\" text-anchor=\"end\""
                + " transform=\"rotate(270 1.015926,-1.05)\" x=\"1.005767\" y=\"-1.060159\">Chart produced using"
                + " SHecC</text>\n" + "</svg>";
        assertEquals(expected, actual);
    }
}
