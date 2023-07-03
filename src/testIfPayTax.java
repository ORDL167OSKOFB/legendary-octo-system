import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testIfPayTax {

	@Test
	void test() {
		
		Board tstBoard = new Board();
		tstBoard.addPlayer("John");
		tstBoard.addPlayer("john 2");
		
		Player plyr = tstBoard.getAllPlayers().get(0);
		tstBoard.setCurrentPlayer(plyr);
		tstBoard.setCurrentSquare(tstBoard.getSquareById(5)); //Climate Square
		
		System.out.printf("Current Square %s", tstBoard.getCurrentSquare().getName());
		
		int beforeTax = tstBoard.getCurrentPlayer().getManpower();
		tstBoard.getCurrentPlayer().removeManpower(tstBoard.getCurrentSquare().getCost());
		int afterTax = tstBoard.getCurrentPlayer().getManpower();
		
		
		System.out.printf("Before tax: %d, After tax: %d", beforeTax, afterTax);
		assertNotEquals(beforeTax, afterTax);
        
	}

}
