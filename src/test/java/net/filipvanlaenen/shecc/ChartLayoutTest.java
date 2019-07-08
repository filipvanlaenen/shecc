package net.filipvanlaenen.shecc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>ChartLayout</code> class.
 */
class ChartLayoutTest {

	@Test
	void defaultAngleIsPi() {
		ChartLayout layout = new ChartLayout();
		assertEquals(Math.PI, layout.getAngle());
	}

}
