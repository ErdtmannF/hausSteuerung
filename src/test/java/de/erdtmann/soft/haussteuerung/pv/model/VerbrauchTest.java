package de.erdtmann.soft.haussteuerung.pv.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VerbrauchTest {

	private Verbrauch v;
	
	@Before
	public void setUp() throws Exception {
		v = Verbrauch.builder().withVonBatt(230.457f).withVonNetz(5.248f).withVonPv(120.687f).build();
	}

	@Test
	public void test() {
		assertEquals("VonBatt nicht korrekt", 230.457f, v.getVonBatt(), 0);
		assertEquals("VonNetz nicht korrekt", 5.248f, v.getVonNetz(), 0);
		assertEquals("VonPv nicht korrekt", 120.687f, v.getVonPv(), 0);
		assertEquals("gesamtVerbrauch nicht korrekt", 356.392f, v.getGesamtVerbrauch(), 0.005);
		assertEquals("gesamtVerbrauch String nicht korrekt", "356", v.getGesamtVerbrauchStr());
	}

}
