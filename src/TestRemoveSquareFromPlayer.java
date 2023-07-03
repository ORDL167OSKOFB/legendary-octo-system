import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestRemoveSquareFromPlayer {

	@Test
	void test() {
		// Add Square
		
				//Populate objects
				AreaType area = AreaType.ARCTIC;
				Square tstSqr = new Square("TestSqr", 15, area);
				Player plyr = new Player("Test man");
				
				// 1 Square added to player
				plyr.addSquare(tstSqr);
				
				//removed
				plyr.removeSquare(tstSqr);
				
				// if indexOutOfBoundsException is thrown, square has been removed. 
				  try {
				        plyr.getOwnedSquares().get(0);
				        fail("Not implemented");
				    }
				    catch (IndexOutOfBoundsException e) {
				        
				        assertTrue(true);
				    }
				    
				
		
	}

}
