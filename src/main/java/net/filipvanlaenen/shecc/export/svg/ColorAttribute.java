package net.filipvanlaenen.shecc.export.svg;

public class ColorAttribute implements Attribute {
    private final String name;
    private final Integer color;

    ColorAttribute(final String name, final Integer color) {
        this.name = name;
        this.color = color;
    }
    
    /**
     * Converts the attribute value to a string.
     *
     * @return A string representing the value.
     */
    @Override
    public String asString() {
        return String.format("#%06X", color);
    }
}
