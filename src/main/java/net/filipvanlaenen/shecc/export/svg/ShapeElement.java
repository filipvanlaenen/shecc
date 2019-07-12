package net.filipvanlaenen.shecc.export.svg;

/**
 * An interface to mark shape elements.
 */
public interface ShapeElement extends GraphicsElement {

    /**
     * Returns a string representation of the shape element.
     *
     * @return A string representation of the shape element.
     */
    @Override
    String asString();

}
