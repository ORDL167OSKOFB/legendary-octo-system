import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class drawCommunityCard {

	@Test
	void test() {
		
		Board tstBoard = new Board();
		if(tstBoard.drawChanceCard() != null)
			assertTrue(true);
		else
		fail("Not yet implemented");
	}

}
