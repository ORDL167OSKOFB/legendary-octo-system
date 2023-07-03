import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestbuySanctWithoutCamps {

	@Test
	void test() {
		
		// Populate objects
		Square tstSqr = new Square("Test Square", 15, AreaType.AMAZON);
		tstSqr.setValues(1, 1,1,1,1,1,1,1, true, false, false);
		Player plyr = new Player("John");
		tstSqr.setOwner(plyr);
		
		//Sanctuary pre-requisites not implemented
		if (tstSqr.buySanctuary() == false)
			assertTrue(false);
		else
			assertTrue(true);
	}

}
