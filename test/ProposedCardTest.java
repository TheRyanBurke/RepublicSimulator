import static org.junit.Assert.assertTrue;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ansible.republic.ProposedCard;


public class ProposedCardTest {
	
	@BeforeClass
	public static void setup() {
		PropertyConfigurator.configure("log4j.properties");
	}
	
	@Test
	public void testRotate() {
		ProposedCard c = new ProposedCard(1, 2, 3, 4);
		c.rotate();
		assertTrue(c.up == 2);
		assertTrue(c.left == 3);
		assertTrue(c.down == 4);
		assertTrue(c.right == 1);
	}
	
	@Test
	public void testGetHighest() {
		ProposedCard c = new ProposedCard(0, 0, 0, 1);
		assertTrue(c.getHighest() == 1);
		c.rotate();
		assertTrue(c.getHighest() == 1);
		
		c = new ProposedCard(0, 2, 2, 1);
		assertTrue(c.getHighest() == 2);
		c.rotate();
		assertTrue(c.getHighest() == 2);
	}
	
	@Test
	public void testGetSeat() {
		ProposedCard c = new ProposedCard(1, 2, 3, 4);
		assertTrue(c.getValueOfSeat(0) == 1);
		assertTrue(c.getValueOfSeat(1) == 2);
		assertTrue(c.getValueOfSeat(2) == 3);
		assertTrue(c.getValueOfSeat(3) == 4);
	}
}
