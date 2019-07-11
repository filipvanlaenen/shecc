package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>ChartLayout</code> class.
 */
class HemicycleLayoutTest {

    /**
     * The magic number three.
     */
    private static final int THREE = 3;
    /**
     * The delta for double comparisons.
     */
    private static final double DOUBLE_DELTA = 0.000001D;
    /**
     * The magic number a third.
     */
    private static final double A_THIRD = 1.0D / 3.0D;
    /**
     * Just above a third. Can e.g. used to test a boundary condition depending on
     * the number a third.
     */
    private static final double JUST_ABOVE_A_THIRD = 0.34D;
    /**
     * The magic number a half.
     */
    private static final double A_HALF = 0.5D;
    /**
     * The magic number two thirds.
     */
    private static final double TWO_THIRDS = 2.0D / 3.0D;
    /**
     * The magic number three quarters.
     */
    private static final double THREE_QUARTERS = 0.75D;
    /**
     * Just below four-fifths. Can e.g. used to test a boundary condition depending
     * on the number of four-fifths.
     */
    private static final double JUST_BELOW_FOUR_FIFTHS = 0.79D;
    /**
     * Just above four-fifths. Can e.g. used to test a boundary condition depending
     * on the number of four-fifths.
     */
    private static final double JUST_ABOVE_FOUR_FIFTHS = 0.81D;
    /**
     * Just below two. Can e.g. used to test a boundary condition depending on the
     * number two.
     */
    private static final double JUST_BELOW_TWO = 1.99D;
    /**
     * Just above two. Can e.g. used to test a boundary condition depending on the
     * number two.
     */
    private static final double JUST_ABOVE_TWO = 2.01D;
    /**
     * Just below three. Can e.g. used to test a boundary condition depending on the
     * number three.
     */
    private static final double JUST_BELOW_THREE = 2.99D;
    /**
     * Just above three. Can e.g. used to test a boundary condition depending on the
     * number three.
     */
    private static final double JUST_ABOVE_THREE = 3.01D;
    /**
     * Quarter of π. Can e.g. be used as a test angle.
     */
    private static final double QUARTER_PI = Math.PI / 4D;
    /**
     * Half of π. Can e.g. be used as a test angle.
     */
    private static final double HALF_PI = Math.PI / 2D;
    /**
     * Three quarters of π. Can e.g. be used as a test angle.
     */
    private static final double THREE_QUARTERS_OF_PI = 0.75D * Math.PI;
    /**
     * 2π. Can e.g. used to test a boundary condition depending on the number 2π.
     */
    private static final double TWO_PI = Math.PI * 2D;
    /**
     * Just above 2π. Can e.g. used to test a boundary condition depending on the
     * number 2π.
     */
    private static final double JUST_ABOVE_TWO_PI = Math.PI * 2D + 0.1D;

    /**
     * Test verifying that the number of seats is wired correctly from the
     * constructor to the getter.
     */
    @Test
    void noOfSeatsIsWiredCorrectlyFromConstructorToGetter() {
        HemicycleLayout layout = new HemicycleLayout(1);
        assertEquals(1, layout.getNoOfSeats());
    }

    /**
     * Test verifying that the constructor throws a
     * <code>IllegalArgumentException</code> if the number of seats is zero or
     * negative.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfNoOfSeatsIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(0);
        });
    }

    /**
     * Test verifying that if no angle is set, π is used as the default value.
     */
    @Test
    void piShouldBeDefaultAngle() {
        HemicycleLayout layout = new HemicycleLayout(1);
        assertEquals(Math.PI, layout.getAngle());
    }

    /**
     * Test verifying that the angle is wired correctly from the constructor to the
     * getter.
     */
    @Test
    void angleIsWiredCorrectlyFromConstructorToGetter() {
        HemicycleLayout layout = new HemicycleLayout(1, TWO_PI);
        assertEquals(TWO_PI, layout.getAngle());
    }

    /**
     * Test verifying that the constructor throws a
     * <code>IllegalArgumentException</code> if the angle is zero or negative.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfAngleIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(1, 0.0);
        });
    }

    /**
     * Test verifying that the constructor throws a
     * <code>IllegalArgumentException</code> if the angle is greather than 2π.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfAngleIsJustAboveTwoPi() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(1, JUST_ABOVE_TWO_PI);
        });
    }

    /**
     * Test verifying that if the ratio between the inner and the outer radius is
     * not set, 1/3 is used as the default value.
     */
    @Test
    void aThirdShouldBeDefaultRadiusRatio() {
        HemicycleLayout layout = new HemicycleLayout(1);
        assertEquals(A_THIRD, layout.getRadiusRatio());
    }

    /**
     * Test verifying that the ratio between the inner and the outer radius is wired
     * correctly from the constructor to the getter.
     */
    @Test
    void radiusRatioIsWiredCorrectlyFromConstructorToGetter() {
        HemicycleLayout layout = new HemicycleLayout(1, HALF_PI, JUST_ABOVE_A_THIRD);
        assertEquals(JUST_ABOVE_A_THIRD, layout.getRadiusRatio());
    }

    /**
     * Test verifying that the constructor throws a
     * <code>IllegalArgumentException</code> if the ratio between the inner and the
     * outer radius is zero or negative.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfRadiusRatioIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(1, HALF_PI, 0.0D);
        });
    }

    /**
     * Test verifying that the constructor throws a
     * <code>IllegalArgumentException</code> if the ratio between the inner and the
     * outer radius is one or greater.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfRadiusRatioIsOne() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(1, HALF_PI, 1.0D);
        });
    }

    /**
     * Test verifying that the number of rows is one for one seat using the default
     * layout.
     */
    @Test
    void noOfRowsIsOneForOneSeatUsingTheDefaultLayout() {
        HemicycleLayout layout = new HemicycleLayout(1);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is one for two seats using the default
     * layout.
     */
    @Test
    void noOfRowsIsOneForTwoSeatsUsingTheDefaultLayout() {
        HemicycleLayout layout = new HemicycleLayout(2);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is one for two seats when the angle is
     * reduced to 2.01.
     */
    @Test
    void noOfRowsIsOneForTwoSeatsWhenTheAngleIsJustAboveTwo() {
        HemicycleLayout layout = new HemicycleLayout(2, JUST_ABOVE_TWO);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is two for two seats when the angle is
     * reduced to 1.99.
     */
    @Test
    void noOfRowsIsTwoForTwoSeatsWhenTheAngleIsJustBelowTwo() {
        HemicycleLayout layout = new HemicycleLayout(2, JUST_BELOW_TWO);
        assertEquals(2, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is one for three seats using the
     * default layout.
     */
    @Test
    void noOfRowsIsOneForThreeSeatsUsingTheDefaultLayout() {
        HemicycleLayout layout = new HemicycleLayout(THREE);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is one for three seats when the angle
     * is reduced to 3.01.
     */
    @Test
    void noOfRowsIsOneForThreeSeatsWhenTheAngleIsJustAboveThree() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_ABOVE_THREE);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is two for three seats when the angle
     * is reduced to 2.99.
     */
    @Test
    void noOfRowsIsTwoForThreeSeatsWhenTheAngleIsJustBelowThree() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_BELOW_THREE);
        assertEquals(2, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is one for three seats when the angle
     * is reduced to 2.99 but the ratio increased to 0.34.
     */
    @Test
    void noOfRowsIsOneForThreeSeatsWhenTheAngleIsJustBelowThreeAndTheRadiusRatioAboveAThird() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_BELOW_THREE, JUST_ABOVE_A_THIRD);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is two for three seats when the angle
     * is reduced to 0.81.
     */
    @Test
    void noOfRowsIsTwoForThreeSeatsWhenTheAngleIsJustAboveFourFifths() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_ABOVE_FOUR_FIFTHS);
        assertEquals(2, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is three for three seats when the
     * angle is reduced to 0.79.
     */
    @Test
    void noOfRowsIsThreeForThreeSeatsWhenTheAngleIsJustBelowFourFifths() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_BELOW_FOUR_FIFTHS);
        assertEquals(THREE, layout.getNoOfRows());
    }

    /**
     * Test verifying that if there is only one seat in the default layout, the
     * position of the seat is at angle π/2 and radius 2/3.
     */
    @Test
    void singleSeatInDefaultLayoutIsPositionedInTheMiddle() {
        HemicycleLayout layout = new HemicycleLayout(1);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(TWO_THIRDS, seatPosition.getRadius(), DOUBLE_DELTA);
        assertEquals(HALF_PI, seatPosition.getAngle(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that if there are two seats in the default layout, the
     * position of the first seat is at angle 3*π/4 and radius 2/3.
     */
    @Test
    void firstOfTwoSeatsInDefaultLayoutIsPositionedInTheMiddleOfTheLeftSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(TWO_THIRDS, seatPosition.getRadius(), DOUBLE_DELTA);
        assertEquals(THREE_QUARTERS_OF_PI, seatPosition.getAngle(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that if there are two seats in the default layout, the
     * position of the second seat is at angle π/4 and radius 2/3.
     */
    @Test
    void secondOfTwoSeatsInDefaultLayoutIsPositionedInTheMiddleOfTheRightSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2);
        SeatPosition seatPosition = layout.getSeatPosition(1);
        assertEquals(TWO_THIRDS, seatPosition.getRadius(), DOUBLE_DELTA);
        assertEquals(QUARTER_PI, seatPosition.getAngle(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that if there are two seats in a layout with the default
     * radius ratio, but an angle of 2, the position of the first seat is at angle
     * π/2 + 1/2 and radius 2/3.
     */
    @Test
    void firstOfTwoSeatsInLayoutWithAngleTwoIsPositionedInTheMiddleOfTheLeftSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2, 2D);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(TWO_THIRDS, seatPosition.getRadius(), DOUBLE_DELTA);
        assertEquals(HALF_PI + A_HALF, seatPosition.getAngle(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that if there are two seats in a layout with the default
     * angle, but a radius ratio of 0.5, the position of the first seat is at angle
     * 3*π/4 and radius 3/4.
     */
    @Test
    void firstOfTwoSeatsInLayoutWithRadiusRatioAHalfIsPositionedInTheMiddleOfTheLeftSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2, Math.PI, A_HALF);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(THREE_QUARTERS, seatPosition.getRadius(), DOUBLE_DELTA);
        assertEquals(THREE_QUARTERS_OF_PI, seatPosition.getAngle(), DOUBLE_DELTA);
    }
}
