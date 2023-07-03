import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestBuyCamps {

	@Test
	void test() {
		
		// Populate objects
		Square tstSqr = new Square("Test Square", 15, AreaType.AMAZON);
		tstSqr.setValues(1, 1,1,1,1,1,1,1, true, false, false);
		Player plyr = new Player("John");
		tstSqr.setOwner(plyr);
		
		//Buy transaction
		tstSqr.buyCamp(1);
		
		//Test if camp has been updated (starts at 0)
		if (tstSqr.getCampCount() == 1)
			assertTrue(true);
		else
			assertTrue(false);
	}

}
