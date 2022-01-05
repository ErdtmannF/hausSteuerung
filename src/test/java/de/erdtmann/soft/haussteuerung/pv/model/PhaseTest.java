package de.erdtmann.soft.haussteuerung.pv.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PhaseTest {

	private Phase p1;
	
	@Before
	public void setUp() throws Exception {
		p1 = Phase.builder().withStrom(3f).withSpannung(230f).withLeistung(690f).build();
	}

	@Test
	public void test() {
		assertEquals("Strom nicht korrekt", 3f, p1.getStrom(), 0);
		assertEquals("Spannung nicht korrekt", 230f, p1.getSpannung(), 0);
		assertEquals("Leistung nicht korrekt", 690f, p1.getLeistung(), 0);
	}

}
