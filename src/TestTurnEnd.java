
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestTurnEnd {

	@Test
	void test() {
		
		Board tstBoard = new Board();
		tstBoard.addPlayer("John");
		tstBoard.addPlayer("Jenny");
		
		
		Player prev = tstBoard.getCurrentPlayer();
		tstBoard.endTurn();
		Player next = tstBoard.getCurrentPlayer();
		
		if (prev != next)
			assertTrue(true);
		else
			fail("Not Implemented- user didn't change.");
		
	
	}

}
