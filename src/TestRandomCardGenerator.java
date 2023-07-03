import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestRandomCardGenerator {

	@Test
	void test() {
		
		Board tstBoard = new Board();
		Card tstCard = new Card("The <randomAnimal> requires your help at the <randomArea>! If you pass Base Camp, collect 20 new protectors.", 0, 0, 20, true);
		
		tstCard = tstBoard.calculateCardDesc(tstCard);
		
		if (!tstCard.getDesc().contains("<randomAnimal>") || !tstCard.getDesc().contains("<randomArea>"))
		{
			System.out.println(tstCard.getDesc());
			assertTrue(true);
		}
		else
		fail("Not yet implemented");
	}

}
