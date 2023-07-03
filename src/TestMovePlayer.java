import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestMovePlayer {

	//Test if player has moved positions by
	@Test
	void test() {
		
		//Populate Objects
		Board tstBoard = new Board();
		tstBoard.addPlayer("Player 1");
		int diff = 12;
		
		tstBoard.setCurrentPlayer(tstBoard.getAllPlayers().get(0));
		tstBoard.getCurrentPlayer().setPosition(1);
		int prevPos = tstBoard.getCurrentPlayer().getPosition();
		//Operation
		tstBoard.startGame();
		tstBoard.movePlayer(diff);
			
		assertEquals((prevPos + diff), tstBoard.getCurrentPlayer().getPosition());
		
	}

}
