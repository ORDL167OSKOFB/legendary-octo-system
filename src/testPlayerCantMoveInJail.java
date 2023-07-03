import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testPlayerCantMoveInJail {

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
		tstBoard.getCurrentPlayer().setOutOfJail(false);
		
		tstBoard.movePlayer(diff);
		assertNotEquals((prevPos + diff), tstBoard.getCurrentPlayer().getPosition());
		
	}

}
