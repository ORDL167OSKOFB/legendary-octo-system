import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testDrawCard {

	@Test
	void test() {
		Board tstBoard = new Board();
		
		Card tstCard1 = tstBoard.drawChanceCard();
		
		Card tstCard2 = tstBoard.drawChanceCard();
		
		System.out.println(tstCard1.getDesc());
		System.out.println(tstCard2.getDesc());
		
		assertNotEquals(tstCard1, tstCard2);
	
	}

}
