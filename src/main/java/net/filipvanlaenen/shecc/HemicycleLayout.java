package net.filipvanlaenen.shecc;

/**
 *
 * Class defining the layout of a hemicycle. The layout is defined by the angle
 * of the hemicycle, the ratio between the inner and the outer radius, and the
 * number of mandates. By default, the angle is half a circle (π rad or 180°),
 * and the ratio is two thirds.
 *
 */
class HemicycleLayout {

    /**
     * Returns the angle of the hemicycle.
     *
     * @return The angle of the hemicycle.
     */
    Double getAngle() {
        return Math.PI;
    }
}
