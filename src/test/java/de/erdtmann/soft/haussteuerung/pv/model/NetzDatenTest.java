package de.erdtmann.soft.haussteuerung.pv.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NetzDatenTest {

	private NetzDaten netz;
	
	@Before
	public void setUp() throws Exception {
		netz = NetzDaten.builder().withLeistung(1230.98f).build();
	}

	@Test
	public void testpositiveNetzDaten() {
		assertEquals("Leistung nicht korrekt", 1230.98f, netz.getLeistung(), 0);
		assertEquals("Leistung nicht richtig gerundet", "1231", netz.getLeistungStr());
		assertEquals("Richtung nicht korrekt", "rot", netz.getFarbe());
		assertEquals("Richtung nicht Korrekt", "left", netz.getRichtung());
	}
	
	@Test
	public void testnegativeNetzDaten() {
		netz = NetzDaten.builder().withLeistung(-345.79f).build();
		assertEquals("Leistung nicht korrekt", -345.79f, netz.getLeistung(), 0);
		assertEquals("Leistung nicht richtig gerundet", " 346", netz.getLeistungStr());
		assertEquals("Richtung nicht korrekt", "gruen", netz.getFarbe());
		assertEquals("Richtung nicht Korrekt", "right", netz.getRichtung());
	}

}
