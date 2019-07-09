package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>ChartLayout</code> class.
 */
class HemicycleLayoutTest {

    /**
     * Test verifying that if no angle is set, π is used as the default value.
     */
    @Test
    void piShouldBeDefaultAngle() {
        HemicycleLayout layout = new HemicycleLayout();
        assertEquals(Math.PI, layout.getAngle());
    }

}
