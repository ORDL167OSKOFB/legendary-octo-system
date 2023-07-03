import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testForPayingHigherRent {

	@Test
	void test() {
		
		Board tstBoard = new Board();
		
		//Populate objects
		AreaType area = AreaType.ARCTIC;
		Square tstSqr = new Square("TestSqr", 15, area);
		Square tstSqr2 = new Square("TstSqr 2", 15, area);
		Square tstSqr3 = new Square("TstSqr 3", 15, area);
		
		
		tstBoard.addPlayer("Test man");
		tstBoard.addPlayer("Rent man");
	
		Player ownPlyr = tstBoard.getAllPlayers().get(0);
		Player rentPayer = tstBoard.getAllPlayers().get(1);
		
		ownPlyr.addSquare(tstSqr);
		tstSqr.setOwner(ownPlyr);
		ownPlyr.addSquare(tstSqr2);
		tstSqr2.setOwner(ownPlyr);
		ownPlyr.addSquare(tstSqr3);
		tstSqr3.setOwner(ownPlyr);
		tstSqr.setValues(10, 50, 0, 50, 110, 170, 240, 290, true ,false, false);
		
		ownPlyr.addManpower(500);
		rentPayer.addManpower(200);
		ownPlyr.getOwnedSquares().get(0).buyCamp(3);
		
		int rentVal = ownPlyr.getOwnedSquares().get(0).getRent();
		int beforeRentPay = rentPayer.getManpower();
		
		rentPayer.removeManpower(rentVal);
		
		System.out.println((beforeRentPay - rentPayer.getManpower()) + "," + ownPlyr.getOwnedSquares().get(0).getRent());
		if ((beforeRentPay - rentPayer.getManpower()) == ownPlyr.getOwnedSquares().get(0).getRent() )
		assertEquals((beforeRentPay - rentPayer.getManpower()), ownPlyr.getOwnedSquares().get(0).getRent() );
		else
			fail("Not implemented");
	
		
	}

}
