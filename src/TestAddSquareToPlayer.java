import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestAddSquareToPlayer {

	@Test
	public void test() {
		
		//Populate objects
		AreaType area = AreaType.ARCTIC;
		Square tstSqr = new Square("TestSqr", 15, area);
		Player plyr = new Player("Test man");
		
		// 1 Square added to player
		plyr.addSquare(tstSqr);
		
		
		if (plyr.getOwnedSquares().get(0) == tstSqr)
		{
			assertTrue(true);
		}
		else
			fail("Not implemented");
		
	}

}
