import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestAddSquareToArea {

	@Test
	void test() {
		
		Square tstSqr = new Square("abc", 15, AreaType.AMAZON);
		Area tstArea = new Area("Testing", AreaType.AMAZON);
		tstArea.addSquare(tstSqr);
		
		// Square has been added to Area.
		if (tstArea.getSquareById(tstSqr.getSquareId()) == tstSqr)
			assertTrue(true);
		else
		fail("Not yet implemented");
	}

}
