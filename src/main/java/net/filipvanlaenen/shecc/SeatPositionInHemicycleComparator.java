package net.filipvanlaenen.shecc;

import java.util.Comparator;

/**
 * Class to compare two seat positions in a hemicycle. A seat position comes before another one if its angle is less
 * than the other's angle measures from 3π/2 (the south), and if both have equal angles, if its radius is less than the
 * other's radius.
 */
class SeatPositionInHemicycleComparator implements Comparator<SeatPosition> {
    /**
     * The delta to compare angles.
     */
    private static final double ANGLE_DELTA = 0.000001D;
    /**
     * Three and a half π.
     */
    private static final double ONE_AND_A_HALF_PI = 1.5D * Math.PI;

    /**
     * Compares two seat positions.
     */
    @Override
    public int compare(final SeatPosition seatPosition1, final SeatPosition seatPosition2) {
        return anglesArePraticallyEqual(seatPosition1, seatPosition2) ? compareRadiuses(seatPosition1, seatPosition2)
                : compareAngles(seatPosition1, seatPosition2);
    }

    /**
     * Calculates the distance of an angle from 3π/2 (the south), clockwise. For angles α less than 3π/2, this is 3π/2 -
     * α. For angles α greater than 3π/2, this is 7π/2 - α.
     *
     * @param angle The angle.
     * @return The distance of the angle from 3π/2 (the south), clockwise.
     */
    private double angleFromOneAndAHalfPi(final double angle) {
        double distance = ONE_AND_A_HALF_PI - angle;
        // EQMU: Changing the conditional boundary below produces a mutant that is practically equivalent.
        if (distance < 0D) {
            distance += 2D * Math.PI;
        }
        return distance;
    }

    /**
     * Compares the angles of two seat positions, both measured from 3π/2 (the south).
     *
     * @param seatPosition1 The first seat position.
     * @param seatPosition2 The second seat position.
     * @return The result of comparing the angle of the first seat position to the radius of the second seat position.
     */
    private int compareAngles(final SeatPosition seatPosition1, final SeatPosition seatPosition2) {
        return Double.compare(angleFromOneAndAHalfPi(seatPosition1.angle()),
                angleFromOneAndAHalfPi(seatPosition2.angle()));
    }

    /**
     * Compares the radiuses of two seat positions.
     *
     * @param seatPosition1 The first seat position.
     * @param seatPosition2 The second seat position.
     * @return The result of comparing the radius of the first seat position to the radius of the second seat position.
     */
    private int compareRadiuses(final SeatPosition seatPosition1, final SeatPosition seatPosition2) {
        return Double.compare(seatPosition1.radius(), seatPosition2.radius());
    }

    /**
     * Verifies whether two seat positions have angles that are practically equal. Practically equal means that their
     * difference is less than the angle delta.
     *
     * @param seatPosition1 The first seat position.
     * @param seatPosition2 The second seat position.
     * @return True if the angles of the two seat positions are practically equal.
     */
    private boolean anglesArePraticallyEqual(final SeatPosition seatPosition1, final SeatPosition seatPosition2) {
        // EQMU: Changing the conditional boundary below produces a mutant that is practically equivalent.
        return Math.abs(seatPosition1.angle() - seatPosition2.angle()) < ANGLE_DELTA;
    }
}
