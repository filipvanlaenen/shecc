package net.filipvanlaenen.shecc.export;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>Exporter</code> class.
 */
public class ExporterTest {
    /**
     * The magic number three.
     */
    private static final int THREE = 3;
    /**
     * The magic number four.
     */
    private static final int FOUR = 4;

    /**
     * A local subclass of the Exporter class for unit testing.
     */
    private static final class MyExporter extends Exporter {
    }

    /**
     * Test verifying the default export of a copyright notice.
     */
    @Test
    public void createCopyrightNoticeShouldProduceADefaultNotice() {
        MyExporter exporter = new MyExporter();
        String actual = exporter.createCopyrightNotice(null, 1, 2, THREE, FOUR).asString();
        String expected =
                "<text fill=\"black\" font-size=\"0.04\" text-anchor=\"end\" transform=\"rotate(270 1,2)\" x=\"0.98\""
                        + " y=\"1.98\">Chart produced using SHecC</text>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the export of a custom copyright notice.
     */
    @Test
    public void createCopyrightNoticeShouldProduceACustomNotice() {
        MyExporter exporter = new MyExporter();
        String actual = exporter.createCopyrightNotice("Foo", 1, 2, THREE, FOUR).asString();
        String expected =
                "<text fill=\"black\" font-size=\"0.04\" text-anchor=\"end\" transform=\"rotate(270 1,2)\" x=\"0.98\""
                        + " y=\"1.98\">© 2024 Foo, chart produced using SHecC</text>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the default export of a copyright notice with an explicit font color and family.
     */
    @Test
    public void createCopyrightNoticeShouldProduceADefaultNoticeWithExplicitFontColorAndFamily() {
        MyExporter exporter = new MyExporter();
        exporter.setFontColor(1);
        exporter.setFontFamily("Bar");
        String actual = exporter.createCopyrightNotice(null, 1, 2, THREE, FOUR).asString();
        String expected = "<text fill=\"#000001\" font-family=\"Bar\" font-size=\"0.04\" text-anchor=\"end\""
                + " transform=\"rotate(270 1,2)\" x=\"0.98\" y=\"1.98\">Chart produced using SHecC</text>";
        assertEquals(expected, actual);
    }

    /**
     * Test verifying the default font color.
     */
    @Test
    public void getFontColorOrZeroShouldReturnZeroAsDefault() {
        MyExporter exporter = new MyExporter();
        assertEquals(0, exporter.getFontColorOrZero());
    }

    /**
     * Test verifying an explicit font color.
     */
    @Test
    public void getFontColorOrZeroShouldReturnExplicitFontColor() {
        MyExporter exporter = new MyExporter();
        exporter.setFontColor(1);
        assertEquals(1, exporter.getFontColorOrZero());
    }
}
