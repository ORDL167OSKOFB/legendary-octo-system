import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testRollDice {

	@Test
	void test() {
		
		Board tstBoard = new Board();
		
		int[] tstArray = tstBoard.rollDice();
		
		// Test that each roll returns a minimum value of 1 and maximum value of 6.
		if ((tstArray[0] > 0 && tstArray[0] <= 6) &&  (tstArray[0] > 0 &&tstArray[1] <= 6))
			assertTrue(true);
		else
		fail("Not yet implemented");
	}

}
