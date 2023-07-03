import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testRemovePlayer {

	@Test
	void test() {
		
		Board tstBoard = new Board();
		tstBoard.addPlayer("Jane"); // Add player 1
		Player tstPlayer = tstBoard.getCurrentPlayer(); // Copy currentplayer to another variable
		tstBoard.addPlayer("john"); // Add 2nd player
		tstBoard.removePlayer(tstPlayer); // Remove tstPlayer from board.
		
		// test that tstPlayer does not exist on the board anymore. 
		for (int i = 0; i < tstBoard.getAllPlayers().size(); i++)
		{
			assertNotEquals(tstBoard.getAllPlayers().get(i), tstPlayer);
		}
		
		
	}

}
