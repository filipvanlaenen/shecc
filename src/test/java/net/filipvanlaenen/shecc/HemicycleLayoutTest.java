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
     * The magic number four.
     */
    private static final int FOUR = 4;
    /**
     * The magic number five.
     */
    private static final int FIVE = 5;
    /**
     * The magic number thirteen.
     */
    private static final int THIRTEEN = 13;
    /**
     * The magic number fourteen.
     */
    private static final int FOURTEEN = 14;
    /**
     * The magic number thirty-one.
     */
    private static final int THIRTY_ONE = 31;
    /**
     * The magic number fourty-nine.
     */
    private static final int FOURTY_NINE = 49;
    /**
     * The magic number fifty.
     */
    private static final int FIFTY = 50;
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
     * The magic number three.
     */
    private static final double THREE_DOUBLE = 3D;
    /**
     * The magic number four.
     */
    private static final double FOUR_DOUBLE = 4D;
    /**
     * The magic number five.
     */
    private static final double FIVE_DOUBLE = 5D;
    /**
     * The magic number six.
     */
    private static final double SIX_DOUBLE = 6D;
    /**
     * The magic number seven.
     */
    private static final double SEVEN_DOUBLE = 7D;
    /**
     * The magic number eight.
     */
    private static final double EIGHT_DOUBLE = 8D;
    /**
     * The magic number nine.
     */
    private static final double NINE_DOUBLE = 9D;
    /**
     * The magic number eleven.
     */
    private static final double ELEVEN_DOUBLE = 11D;
    /**
     * The magic number twelve.
     */
    private static final double TWELVE_DOUBLE = 12D;
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
     * Verifies that for 50 seats, the default angle is π.
     */
    @Test
    void getAngleShouldReturnPiForFiftySeats() {
        HemicycleLayout layout = new HemicycleLayout(FIFTY);
        assertEquals(Math.PI, layout.getAngle());
    }

    /**
     * Verifies that for 49 seats, the default angle is π * 39 / 40.
     */
    @Test
    void getAngleShouldReturnScaledPiForLessThanFiftySeats() {
        HemicycleLayout layout = new HemicycleLayout(FOURTY_NINE);
        assertEquals(Math.PI * FOURTY_NINE / FIFTY, layout.getAngle());
    }

    /**
     * Verifies that for 5 seats, the default angle is π * 5 / 50.
     */
    @Test
    void getAngleShouldReturnScaledPiDownToFiveSeats() {
        HemicycleLayout layout = new HemicycleLayout(FIVE);
        assertEquals(Math.PI * FIVE / FIFTY, layout.getAngle());
    }

    /**
     * Verifies that for 4 seats, the default angle is still π * 5 / 50.
     */
    @Test
    void getAngleShouldReturnMinimalAngleForLessThanFiveSeats() {
        HemicycleLayout layout = new HemicycleLayout(FOUR);
        assertEquals(Math.PI * FIVE / FIFTY, layout.getAngle());
    }

    /**
     * Test verifying that the angle is wired correctly from the constructor to the getter.
     */
    @Test
    void getAngleIsWiredCorrectlyFromConstructorToGetter() {
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
    void getRadiusRatioShouldReturnAThirdByDefault() {
        HemicycleLayout layout = new HemicycleLayout(1);
        assertEquals(A_THIRD, layout.getRadiusRatio());
    }

    /**
     * Test verifying that the ratio between the inner and the outer radius is wired correctly from the constructor to
     * the getter.
     */
    @Test
    void getRadiusRatioIsWiredCorrectlyFromConstructorToGetter() {
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
     * Verifies that the number of rows is one for one seat and angle π.
     */
    @Test
    void getNumberOfRowsShouldReturnOneForOneSeatAndAnglePi() {
        HemicycleLayout layout = new HemicycleLayout(1, Math.PI);
        assertEquals(1, layout.getNumberOfRows());
    }

    /**
     * Verifies that the number of rows is one for four seats and angle π.
     */
    @Test
    void getNumberOfRowsShouldReturnOneForFourSeatsAndAnglePi() {
        HemicycleLayout layout = new HemicycleLayout(FOUR, Math.PI);
        assertEquals(1, layout.getNumberOfRows());
    }

    /**
     * Verifies that the number of rows is two for five seats and angle π.
     */
    @Test
    void getNumberOfRowsShouldReturnTwoForFiveSeatsAndAnglePi() {
        HemicycleLayout layout = new HemicycleLayout(FIVE, Math.PI);
        assertEquals(2, layout.getNumberOfRows());
    }

    /**
     * Verifies that the number of rows is two for four seats and angle 3.
     */
    @Test
    void getNumberOfRowsShouldReturnTwoForFourSeatsAndAngleThree() {
        HemicycleLayout layout = new HemicycleLayout(FOUR, THREE_DOUBLE);
        assertEquals(2, layout.getNumberOfRows());
    }

    /**
     * Verifies that the number of rows is two for thirteen seats and angle π.
     */
    @Test
    void getNumberOfRowsShouldReturnTwoForThirteenSeatsAndAnglePi() {
        HemicycleLayout layout = new HemicycleLayout(THIRTEEN, Math.PI);
        assertEquals(2, layout.getNumberOfRows());
    }

    /**
     * Verifies that the number of rows is three for fourteen seats and angle π.
     */
    @Test
    void getNumberOfRowsShouldReturnThreeForFourteenSeatsAndAnglePi() {
        HemicycleLayout layout = new HemicycleLayout(FOURTEEN, Math.PI);
        assertEquals(THREE, layout.getNumberOfRows());
    }

    /**
     * Verifies that the row width is the full width of the hemicycle when there's only one row.
     */
    @Test
    void getRowWidthShouldReturnFullWidthIfThereIsOneRow() {
        HemicycleLayout layout = new HemicycleLayout(1, Math.PI);
        assertEquals(TWO_THIRDS, layout.getRowWidth(), DOUBLE_DELTA);
    }

    /**
     * Verifies that the row width is half the width of the hemicycle when there are two rows.
     */
    @Test
    void getRowWidthShouldReturnHalfWidthIfThereAreTwoRows() {
        HemicycleLayout layout = new HemicycleLayout(FIVE, Math.PI);
        assertEquals(A_THIRD, layout.getRowWidth(), DOUBLE_DELTA);
    }

    /**
     * Verifies that if there is only one seat and the angle is π, the position of the seat is at angle π/2 and radius
     * 2/3.
     */
    @Test
    void positionOfOneSeatWithAnglePiIsInTheMiddle() {
        HemicycleLayout layout = new HemicycleLayout(1, Math.PI);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(TWO_THIRDS, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(HALF_PI, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Verifies that if there are two seats and the angle is π, the position of the first seat is at angle π and radius
     * 2/3.
     */
    @Test
    void positionOfFirstOfTwoSeatsWithAnglePiIsInTheMiddleOfTheLeftSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2, Math.PI);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(TWO_THIRDS, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(Math.PI, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Verifies that if there are two seats and the angle is π, the position of the second seat is at angle 0 and radius
     * 2/3.
     */
    @Test
    void positionOfFirstOfTwoSeatsWithAnglePiIsInTheMiddleOfTheRightSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2, Math.PI);
        SeatPosition seatPosition = layout.getSeatPosition(1);
        assertEquals(TWO_THIRDS, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(0D, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Verifies that if there are two seats and the angle is 2, the position of the first seat is at angle π/2 + 1 and
     * radius 2/3.
     */
    @Test
    void positionOfFirstOfTwoSeatsWithAngleTwoIsInTheMiddleOfTheLeftSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2, 2D);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(TWO_THIRDS, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(HALF_PI + 1D, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Verifies that if there are two seats with angle π and radius ratio one half, but a radius ratio of 0.5, the
     * position of the first seat is at angle π and radius 3/4.
     */
    @Test
    void positionOfFirstOfTwoSeatsWithAnglePiAndRadiusRatioOneHalfTwoIsInTheMiddleOfTheLeftSideOfTheHemicycle() {
        HemicycleLayout layout = new HemicycleLayout(2, Math.PI, A_HALF);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(THREE_QUARTERS, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(Math.PI, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Verifies the position of the first of fourteen seats with angle π.
     */
    @Test
    void positionOfFirstOfFourteenSeatsWithAnglePi() {
        HemicycleLayout layout = new HemicycleLayout(FOURTEEN, Math.PI);
        SeatPosition seatPosition = layout.getSeatPosition(0);
        assertEquals(FOUR_DOUBLE / NINE_DOUBLE, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(Math.PI, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Verifies the position of the fourth of fourteen seats with angle π.
     */
    @Test
    void positionOfFourthOfFourteenSeatsWithAnglePi() {
        HemicycleLayout layout = new HemicycleLayout(FOURTEEN, Math.PI);
        SeatPosition seatPosition = layout.getSeatPosition(THREE);
        assertEquals(EIGHT_DOUBLE / NINE_DOUBLE, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(FOUR_DOUBLE * Math.PI / FIVE_DOUBLE, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Verifies the position of the fifth of thirty-one seats with angle π.
     */
    @Test
    void positionOfFifthOfThirtyOneSeatsWithAnglePi() {
        HemicycleLayout layout = new HemicycleLayout(THIRTY_ONE, Math.PI);
        SeatPosition seatPosition = layout.getSeatPosition(FOUR);
        assertEquals(ELEVEN_DOUBLE / TWELVE_DOUBLE, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(EIGHT_DOUBLE * Math.PI / NINE_DOUBLE, seatPosition.angle(), DOUBLE_DELTA);
    }

    /**
     * Verifies the position of the sixth of thirty-one seats with angle π.
     */
    @Test
    void positionOfSixthOfThirtyOneSeatsWithAnglePi() {
        HemicycleLayout layout = new HemicycleLayout(THIRTY_ONE, Math.PI);
        SeatPosition seatPosition = layout.getSeatPosition(FIVE);
        assertEquals(SIX_DOUBLE / EIGHT_DOUBLE, seatPosition.radius(), DOUBLE_DELTA);
        assertEquals(SEVEN_DOUBLE * Math.PI / EIGHT_DOUBLE, seatPosition.angle(), DOUBLE_DELTA);
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
        HemicycleLayout layout = new HemicycleLayout(1, FOUR_DOUBLE);
        double extensionHeight = TWO_THIRDS * Math.sin(FOUR_DOUBLE / 2D) / 2D;
        double expected = 1D - Math.cos(FOUR_DOUBLE / 2D) + extensionHeight;
        assertEquals(expected, layout.getHeight(), DOUBLE_DELTA);
    }
}
