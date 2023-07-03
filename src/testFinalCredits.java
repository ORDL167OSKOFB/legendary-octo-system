import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class testFinalCredits {

	@Test
	void test() throws FileNotFoundException {
		Credits tstCredits = new Credits("John");
		tstCredits.StoreCSV();
		
		tstCredits.creditRetrieve();
		
	}

}
