import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSubtractMP {

	@Test
	void test() {
	Player test = new Player("Test man ");
		
		int initialMP = test.getManpower();
		int increment = 20;
		
		test.removeManpower(increment);
		assertEquals((initialMP - increment), test.getManpower());
	}

}
