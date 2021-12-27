package de.erdtmann.soft.haussteuerung.pv.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BatterieDatenTest {

	@Test
	public void testNormaleDaten() {
		
		BatterieDaten batt = BatterieDaten.builder().withLadeStand(100).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		
		assertEquals("Ladestand nicht korrekt", 100, batt.getLadeStand(), 0);
		assertEquals("Ladestrom nicht korrekt", 5, batt.getLadeStrom(), 0);
		assertEquals("Ladezyklen nicht korrekt", 50, batt.getLadeZyklen(), 0);
		assertEquals("Spannung nicht korrekt", 230, batt.getSpannung(), 0);
		assertEquals("Temperatur nicht korrekt", 20, batt.getTemperatur(), 0);
		assertEquals("Leistung nicht korrekt", 1150, batt.getLeistung(), 0);
		
		assertEquals("Ladestand String nicht korrekt", "100", batt.getLadeStandStr());
		assertEquals("Ladestrom String nicht korrekt", "5", batt.getLadeStromStr());
		assertEquals("Ladezyklen String nicht korrekt", "50", batt.getLadeZyklenStr());
		assertEquals("Spannung String nicht korrekt", "230", batt.getSpannungStr());
		assertEquals("Leistung String nicht korrekt", "1150", batt.getLeistungStr());
		
	}

	@Test
	public void testNachkommaDaten() {
		BatterieDaten batt = BatterieDaten.builder().withLadeStrom(5.7462f).withSpannung(230.5698f).build();
		
		assertEquals("Ladestrom nicht richtig gerundet", "5,75", batt.getLadeStromNachkomma());
		
	}
	
}
