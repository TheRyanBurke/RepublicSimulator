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
		// Logger.getLogger(ProposedCardTest.class).info("card id: " + c.id);
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
	public void testGetLowest() {
		ProposedCard c = new ProposedCard(1, 2, 3, 4);
		assertTrue(c.getLowest() == 1);
		c.rotate();
		assertTrue(c.getLowest() == 1);

		c = new ProposedCard(3, 2, 2, 2);
		assertTrue(c.getLowest() == 2);
		c.rotate();
		assertTrue(c.getLowest() == 2);
	}

	@Test
	public void testGetSeat() {
		ProposedCard c = new ProposedCard(1, 2, 3, 4);
		assertTrue(c.getValueOfSeat(0) == 1);
		assertTrue(c.getValueOfSeat(1) == 2);
		assertTrue(c.getValueOfSeat(2) == 3);
		assertTrue(c.getValueOfSeat(3) == 4);
	}

	@Test
	public void testGetRotatedSeat() {
		ProposedCard c = new ProposedCard(1, 2, 3, 4);
		assertTrue(c.getRotatedValueOfSeat(0) == 2);
		assertTrue(c.getRotatedValueOfSeat(1) == 3);
		assertTrue(c.getRotatedValueOfSeat(2) == 4);
		assertTrue(c.getRotatedValueOfSeat(3) == 1);
	}

	@Test
	public void testEquality() {
		ProposedCard a = new ProposedCard(1, 2, 3, 4);
		ProposedCard b = new ProposedCard(1, 2, 3, 4);
		ProposedCard c = new ProposedCard(1, 2, 3, 5);
		ProposedCard d = new ProposedCard(2, 3, 4, 1);

		assertTrue(a.equals(b));
		assertTrue(b.equals(a));
		assertTrue(!a.equals(c));
		assertTrue(a.equals(d));
		assertTrue(!c.equals(d));

	}
}
