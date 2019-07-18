package net.filipvanlaenen.shecc.export.svg;

/**
 * Abstract class representing a transformation.
 */
public abstract class Transform implements Attribute {

    /**
     * Class representing a rotation transformation.
     */
    private static class Rotation extends Transform {
        /**
         * The angle on which to rotate.
         */
        private final Number angle;
        /**
         * The x coordinate of the rotation center.
         */
        private final Number cx;
        /**
         * The y coordinate of the rotation center.
         */
        private final Number cy;

        /**
         * Constructs a rotation.
         *
         * @param angle
         *            The angle on which to rotate.
         * @param cx
         *            The x coordinate of the rotation center.
         * @param cy
         *            The y coordinate of the rotation center.
         */
        Rotation(final Number angle, final Number cx, final Number cy) {
            this.angle = angle;
            this.cx = cx;
            this.cy = cy;
        }

        @Override
        public String asString() {
            return "rotate(" + DECIMAL_FORMAT.format(angle) + " " + DECIMAL_FORMAT.format(cx) + ","
                    + DECIMAL_FORMAT.format(cy) + ")";
        }

    }

    /**
     * Constructs a rotation.
     *
     * @param angle
     *            The angle on which to rotate.
     * @param cx
     *            The x coordinate of the rotation center.
     * @param cy
     *            The y coordinate of the rotation center.
     * @return A rotation.
     */
    public static Transform rotate(final Number angle, final Number cx, final Number cy) {
        return new Rotation(angle, cx, cy);
    }

}
