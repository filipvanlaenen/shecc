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
     * Just above a third. Can e.g. used to test a boundary condition depending on the number a third.
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
     * Just below four-fifths. Can e.g. used to test a boundary condition depending on the number of four-fifths.
     */
    private static final double JUST_BELOW_FOUR_FIFTHS = 0.79D;
    /**
     * Just above four-fifths. Can e.g. used to test a boundary condition depending on the number of four-fifths.
     */
    private static final double JUST_ABOVE_FOUR_FIFTHS = 0.81D;
    /**
     * Just below two. Can e.g. used to test a boundary condition depending on the number two.
     */
    private static final double JUST_BELOW_TWO = 1.99D;
    /**
     * Just above two. Can e.g. used to test a boundary condition depending on the number two.
     */
    private static final double JUST_ABOVE_TWO = 2.01D;
    /**
     * Just below three. Can e.g. used to test a boundary condition depending on the number three.
     */
    private static final double JUST_BELOW_THREE = 2.99D;
    /**
     * Just above three. Can e.g. used to test a boundary condition depending on the number three.
     */
    private static final double JUST_ABOVE_THREE = 3.01D;
    /**
     * The magic number four. Can e.g. used to test a boundary condition depending on the number four.
     */
    private static final double FOUR = 4D;
    /**
     * Half of π. Can e.g. be used as a test angle.
     */
    private static final double HALF_PI = Math.PI / 2D;
    /**
     * 2π. Can e.g. used to test a boundary condition depending on the number 2π.
     */
    private static final double TWO_PI = Math.PI * 2D;
    /**
     * Just above 2π. Can e.g. used to test a boundary condition depending on the number 2π.
     */
    private static final double JUST_ABOVE_TWO_PI = Math.PI * 2D + 0.1D;

    /**
     * Test verifying that the number of seats is wired correctly from the constructor to the getter.
     */
    @Test
    void noOfSeatsIsWiredCorrectlyFromConstructorToGetter() {
        HemicycleLayout layout = new HemicycleLayout(1);
        assertEquals(1, layout.getNoOfSeats());
    }

    /**
     * Test verifying that the constructor throws a <code>IllegalArgumentException</code> if the number of seats is zero
     * or negative.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfNoOfSeatsIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(0);
        });
    }

    /**
     * Verifies that for 40 seats, the default angle is π.
     */
    @Test
    void getAngleShouldReturnPiForFortySeats() {
        HemicycleLayout layout = new HemicycleLayout(40);
        assertEquals(Math.PI, layout.getAngle());
    }

    /**
     * Verifies that for 39 seats, the default angle is π * 39 / 40.
     */
    @Test
    void getAngleShouldReturnScaledPiForLessThanFortySeats() {
        HemicycleLayout layout = new HemicycleLayout(39);
        assertEquals(Math.PI * 39 / 40, layout.getAngle());
    }

    /**
     * Verifies that for 5 seats, the default angle is π * 5 / 40.
     */
    @Test
    void getAngleShouldReturnScaledPiDownToFiveSeats() {
        HemicycleLayout layout = new HemicycleLayout(5);
        assertEquals(Math.PI * 5 / 40, layout.getAngle());
    }

    /**
     * Verifies that for 4 seats, the default angle is still π * 5 / 40.
     */
    @Test
    void getAngleShouldReturnMinimalAngleForLessThanFiveSeats() {
        HemicycleLayout layout = new HemicycleLayout(4);
        assertEquals(Math.PI * 5 / 40, layout.getAngle());
    }

    /**
     * Test verifying that the angle is wired correctly from the constructor to the getter.
     */
    @Test
    void angleIsWiredCorrectlyFromConstructorToGetter() {
        HemicycleLayout layout = new HemicycleLayout(1, TWO_PI);
        assertEquals(TWO_PI, layout.getAngle());
    }

    /**
     * Test verifying that the constructor throws a <code>IllegalArgumentException</code> if the angle is zero or
     * negative.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfAngleIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(1, 0.0);
        });
    }

    /**
     * Test verifying that the constructor throws a <code>IllegalArgumentException</code> if the angle is greather than
     * 2π.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfAngleIsJustAboveTwoPi() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(1, JUST_ABOVE_TWO_PI);
        });
    }

    /**
     * Test verifying that if the ratio between the inner and the outer radius is not set, 1/3 is used as the default
     * value.
     */
    @Test
    void aThirdShouldBeDefaultRadiusRatio() {
        HemicycleLayout layout = new HemicycleLayout(1);
        assertEquals(A_THIRD, layout.getRadiusRatio());
    }

    /**
     * Test verifying that the ratio between the inner and the outer radius is wired correctly from the constructor to
     * the getter.
     */
    @Test
    void radiusRatioIsWiredCorrectlyFromConstructorToGetter() {
        HemicycleLayout layout = new HemicycleLayout(1, HALF_PI, JUST_ABOVE_A_THIRD);
        assertEquals(JUST_ABOVE_A_THIRD, layout.getRadiusRatio());
    }

    /**
     * Test verifying that the constructor throws a <code>IllegalArgumentException</code> if the ratio between the inner
     * and the outer radius is zero or negative.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfRadiusRatioIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(1, HALF_PI, 0.0D);
        });
    }

    /**
     * Test verifying that the constructor throws a <code>IllegalArgumentException</code> if the ratio between the inner
     * and the outer radius is one or greater.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfRadiusRatioIsOne() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HemicycleLayout(1, HALF_PI, 1.0D);
        });
    }

    /**
     * Test verifying that the number of rows is one for one seat and angle π.
     */
    @Test
    void noOfRowsIsOneForOneSeatUsingTheDefaultLayout() {
        HemicycleLayout layout = new HemicycleLayout(1, Math.PI);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is one for two seats and angle π.
     */
    @Test
    void noOfRowsIsOneForTwoSeatsUsingTheDefaultLayout() {
        HemicycleLayout layout = new HemicycleLayout(2, Math.PI);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is one for two seats when the angle is reduced to 2.01.
     */
    @Test
    void noOfRowsIsOneForTwoSeatsWhenTheAngleIsJustAboveTwo() {
        HemicycleLayout layout = new HemicycleLayout(2, JUST_ABOVE_TWO);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is two for two seats when the angle is reduced to 1.99.
     */
    @Test
    void noOfRowsIsTwoForTwoSeatsWhenTheAngleIsJustBelowTwo() {
        HemicycleLayout layout = new HemicycleLayout(2, JUST_BELOW_TWO);
        assertEquals(2, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is one for three seats and angle π.
     */
    @Test
    void noOfRowsIsOneForThreeSeatsUsingTheDefaultLayout() {
        HemicycleLayout layout = new HemicycleLayout(THREE, Math.PI);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is one for three seats when the angle is reduced to 3.01.
     */
    @Test
    void noOfRowsIsOneForThreeSeatsWhenTheAngleIsJustAboveThree() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_ABOVE_THREE);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is two for three seats when the angle is reduced to 2.99.
     */
    @Test
    void noOfRowsIsTwoForThreeSeatsWhenTheAngleIsJustBelowThree() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_BELOW_THREE);
        assertEquals(2, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is one for three seats when the angle is reduced to 2.99 but the ratio
     * increased to 0.34.
     */
    @Test
    void noOfRowsIsOneForThreeSeatsWhenTheAngleIsJustBelowThreeAndTheRadiusRatioAboveAThird() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_BELOW_THREE, JUST_ABOVE_A_THIRD);
        assertEquals(1, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is two for three seats when the angle is reduced to 0.81.
     */
    @Test
    void noOfRowsIsTwoForThreeSeatsWhenTheAngleIsJustAboveFourFifths() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_ABOVE_FOUR_FIFTHS);
        assertEquals(2, layout.getNoOfRows());
    }

    /**
     * Test verifying that the number of rows is three for three seats when the angle is reduced to 0.79.
     */
    @Test
    void noOfRowsIsThreeForThreeSeatsWhenTheAngleIsJustBelowFourFifths() {
        HemicycleLayout layout = new HemicycleLayout(THREE, JUST_BELOW_FOUR_FIFTHS);
        assertEquals(THREE, layout.getNoOfRows());
    }

    /**
     * Test verifying that the row width is the full width of the hemicycle when there's only one row and the angle is
     * π.
     */
    @Test
    void rowWidthIsTwoThirdsUsingTheDefaultLayoutForOneSeat() {
        HemicycleLayout layout = new HemicycleLayout(1, Math.PI);
        assertEquals(TWO_THIRDS, layout.getRowWidth(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that the row width is the full width of the hemicycle when there's only one row.
     */
    @Test
    void rowWidthIsAThirdUsingALayoutWithOneRowAndRadiusRatioTwoThirds() {
        HemicycleLayout layout = new HemicycleLayout(1, TWO_THIRDS);
        assertEquals(A_THIRD, layout.getRowWidth(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that the row width is half the width of the hemicycle when there are two rows.
     */
    @Test
    void rowWidthIsOneThirdUsingTheDefaultLayoutForOneSeat() {
        HemicycleLayout layout = new HemicycleLayout(2, JUST_BELOW_TWO);
        assertEquals(A_THIRD, layout.getRowWidth(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that if there is only one seat and the angle is π, the position of the seat is at angle π/2 and
     * radius 2/3.
     */
    @Test
    void singleSeatInDefaultLayoutIsPositionedInTheMiddle() {
        HemicycleLayout layout = new HemicycleLayout(1, Math.PI);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(TWO_THIRDS, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(HALF_PI, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that if there are two seats and the angle is π, the position of the first seat is at angle π and
     * radius 2/3.
     */
    @Test
    void firstOfTwoSeatsInDefaultLayoutIsPositionedInTheMiddleOfTheLeftSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2, Math.PI);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(TWO_THIRDS, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(Math.PI, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that if there are two seats and the angle is π, the position of the second seat is at angle 0 and
     * radius 2/3.
     */
    @Test
    void secondOfTwoSeatsInDefaultLayoutIsPositionedInTheMiddleOfTheRightSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2, Math.PI);
        SeatPosition seatPosition = layout.getSeatPosition(1);
        assertEquals(TWO_THIRDS, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(0D, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that if there are two seats in a layout with the default radius ratio, but an angle of 2, the
     * position of the first seat is at angle π/2 + 1 and radius 2/3.
     */
    @Test
    void firstOfTwoSeatsInLayoutWithAngleTwoIsPositionedInTheMiddleOfTheLeftSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2, 2D);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(TWO_THIRDS, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(HALF_PI + 1D, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that if there are two seats in a layout with the default angle, but a radius ratio of 0.5, the
     * position of the first seat is at angle π and radius 3/4.
     */
    @Test
    void firstOfTwoSeatsInLayoutWithRadiusRatioAHalfIsPositionedInTheMiddleOfTheLeftSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2, Math.PI, A_HALF);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(THREE_QUARTERS, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(Math.PI, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Test verifying that when a hemicycle layout is created with two seats, two seat positions are returned in the
     * list of seat positions.
     */
    @Test
    void getSeatPositionsReturnsTwoSeatPositionsWhenTheLayoutContainsTwoSeats() {
        HemicycleLayout layout = new HemicycleLayout(2);
        assertEquals(2, layout.getSeatPositions().size());
    }

    /**
     * Verifies that when the angle is less than π, the width is calculated based on the inner edge.
     */
    @Test
    void getWidthShouldCalculateWidthBasedOnOuterEdgeIfAngleIsLessThanPi() {
        HemicycleLayout layout = new HemicycleLayout(1, HALF_PI);
        assertEquals(2D * Math.sin(HALF_PI / 2D) + TWO_THIRDS * Math.cos(HALF_PI / 2D), layout.getWidth());
    }

    /**
     * Verifies that when the angle is greater than π, the width is 2.
     */
    @Test
    void getWidthShouldReturnTwoIfAngleIsGreaterThanPi() {
        HemicycleLayout layout = new HemicycleLayout(1, Math.PI);
        assertEquals(2D, layout.getWidth());
    }

    /**
     * Verifies that when the angle is less than π, the height is calculated based on the inner edge.
     */
    @Test
    void getHeightShouldCalculateHeightBasedOnInnerEdgeIfAngleIsLessThanPi() {
        HemicycleLayout layout = new HemicycleLayout(1, HALF_PI);
        double extensionHeight = TWO_THIRDS * Math.sin(HALF_PI / 2D) / 2D;
        double expected = 1D - A_THIRD * Math.cos(HALF_PI / 2D) + extensionHeight;
        assertEquals(expected, layout.getHeight(), DOUBLE_DELTA);
    }

    /**
     * Verifies that when the angle is greater than π, the height is calculated based on the outer edge.
     */
    @Test
    void getHeightShouldCalculateHeightBasedOnOuterEdgeIfAngleIsGreaterThanPi() {
        HemicycleLayout layout = new HemicycleLayout(1, FOUR);
        double extensionHeight = TWO_THIRDS * Math.sin(FOUR / 2D) / 2D;
        double expected = 1D - Math.cos(FOUR / 2D) + extensionHeight;
        assertEquals(expected, layout.getHeight(), DOUBLE_DELTA);
    }
}
