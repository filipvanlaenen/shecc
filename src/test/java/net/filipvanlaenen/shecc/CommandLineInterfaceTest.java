package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>CommandLineInterface</code> class.
 */
public class CommandLineInterfaceTest {

    /**
     * Test verifying that SVG content is produced when the command-line interface
     * is called.
     */
    @Test
    void cliCallShouldProduceSvgContent() {
        CommandLineInterface cli = new CommandLineInterface();
        String actual = cli.perform("2.FF0000.R,1.0000FF.B");
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

}
