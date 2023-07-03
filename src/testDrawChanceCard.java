import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testDrawChanceCard {

	@Test
	void test() {
		
		Board tstBoard = new Board();
		if(tstBoard.drawChanceCard() != null)
		{
			System.out.println(tstBoard.drawChanceCard().getDesc());
			System.out.println(tstBoard.drawChanceCard().getDesc());
			assertTrue(true);
		}
		else
		fail("Not yet implemented");
	}

}
