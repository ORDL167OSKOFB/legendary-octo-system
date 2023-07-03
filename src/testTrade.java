import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class testTrade {

	@Test
	void test() {
		
		Board tstBoard = new Board();
		
		ArrayList<Square> tstSquareArr = new ArrayList<Square>();
		Player oldPlyr = new Player("John");
		Player newPlyr = new Player("John 2.0");
		Square tstSquare = new Square("Trade square", 1, AreaType.AMAZON);
		
		oldPlyr.addSquare(tstSquare);
		tstSquareArr.add(tstSquare);
		
		tstBoard.trade(tstSquareArr, oldPlyr, newPlyr, 1);
		
		if (newPlyr.getOwnedSquares().get(0) == tstSquare)
			assertTrue(true);
		else
		fail("Not yet implemented");
	}

}
