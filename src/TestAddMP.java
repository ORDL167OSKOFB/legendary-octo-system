import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
class TestAddMP {

	@Test
	//MP stands for ManPower
	void test() {
		Player test = new Player("Test man ");
		
		int initialMP = test.getManpower();
		int increment = 20;
		
		test.addManpower(increment);
		assertEquals((initialMP + increment), test.getManpower());
		
	}

}