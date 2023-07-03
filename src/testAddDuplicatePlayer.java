import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testAddDuplicatePlayer {

	
	// test if duplicate player name is added
	@Test
	void test() {
		
		Board tstBoard = new Board();
		
		String prevPlyr = "jane";
		Boolean prev = tstBoard.addPlayer("jane");
		Boolean dupl = tstBoard.addPlayer("jane");
		
		assertNotEquals(prev, dupl);
		
	}

}
