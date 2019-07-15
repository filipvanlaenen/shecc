package net.filipvanlaenen.shecc.export.svg;

public class EnumeratedArrayAttribute implements Attribute {

    private final String name;
    private final Enum value;
    
    EnumeratedArrayAttribute(String name, Enum value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Converts the attribute value to a string.
     *
     * @return A string representing the value.
     */
    @Override
    public String asString() {
        return value.toString();
    }

}
