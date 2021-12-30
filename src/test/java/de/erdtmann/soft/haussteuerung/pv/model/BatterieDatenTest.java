package de.erdtmann.soft.haussteuerung.pv.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BatterieDatenTest {

	private BatterieDaten batt;
	private BatterieDaten battNachkomma;
	
	@Before
	public void setUp() {
		 batt = BatterieDaten.builder().withLadeStand(100).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		 battNachkomma = BatterieDaten.builder().withLadeStrom(5.7462f).withSpannung(230.5698f).build();
	}
	
	@Test
	public void testNormaleDaten() {
		assertEquals("Ladestand nicht korrekt", 100, batt.getLadeStand(), 0);
		assertEquals("Ladestrom nicht korrekt", 5, batt.getLadeStrom(), 0);
		assertEquals("Ladezyklen nicht korrekt", 50, batt.getLadeZyklen(), 0);
		assertEquals("Spannung nicht korrekt", 230, batt.getSpannung(), 0);
		assertEquals("Temperatur nicht korrekt", 20, batt.getTemperatur(), 0);
		assertEquals("Leistung nicht korrekt", 1150, batt.getLeistung(), 0);
	}

	@Test
	public void testStringDaten() {
		assertEquals("Ladestand String nicht korrekt", "100", batt.getLadeStandStr());
		assertEquals("Ladestrom String nicht korrekt", "5", batt.getLadeStromStr());
		assertEquals("Ladezyklen String nicht korrekt", "50", batt.getLadeZyklenStr());
		assertEquals("Spannung String nicht korrekt", "230", batt.getSpannungStr());
		assertEquals("Leistung String nicht korrekt", "1150", batt.getLeistungStr());

	}
	
	@Test
	public void testNachkommaDaten() {
		assertEquals("Ladestrom nicht richtig gerundet", "5,75", battNachkomma.getLadeStromNachkomma());
		assertEquals("Spannung nicht richtig gerundet", "230,57", battNachkomma.getSpannungNachkomma());
		assertEquals("Leistung nicht richtig gerundet", "1324,9", battNachkomma.getLeistungNachkomma());
	}
	
	@Test
	public void testBattIcon() {
		batt = BatterieDaten.builder().withLadeStand(5).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-0", batt.getIcon());
		batt = BatterieDaten.builder().withLadeStand(3).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-0", batt.getIcon());
		batt = BatterieDaten.builder().withLadeStand(0).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-0", batt.getIcon());

		batt = BatterieDaten.builder().withLadeStand(6).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-1", batt.getIcon());
		batt = BatterieDaten.builder().withLadeStand(18).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-1", batt.getIcon());
		batt = BatterieDaten.builder().withLadeStand(25).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-1", batt.getIcon());

		batt = BatterieDaten.builder().withLadeStand(26).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-2", batt.getIcon());
		batt = BatterieDaten.builder().withLadeStand(43).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-2", batt.getIcon());
		batt = BatterieDaten.builder().withLadeStand(50).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-2", batt.getIcon());

		batt = BatterieDaten.builder().withLadeStand(51).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-3", batt.getIcon());
		batt = BatterieDaten.builder().withLadeStand(68).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-3", batt.getIcon());
		batt = BatterieDaten.builder().withLadeStand(75).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-3", batt.getIcon());

		batt = BatterieDaten.builder().withLadeStand(76).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-4", batt.getIcon());
		batt = BatterieDaten.builder().withLadeStand(93).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-4", batt.getIcon());
		batt = BatterieDaten.builder().withLadeStand(100).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery-4", batt.getIcon());
		batt = BatterieDaten.builder().withLadeStand(101).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		assertEquals("Icon nicht korrekt","battery", batt.getIcon());

	}

	@Test
	public void testFarbeRichtungLadung() {

		assertEquals("Richtung nicht korrekt", "right", batt.getRichtung());
		assertEquals("Farbe nicht korrekt", "rot", batt.getFarbe());
		assertEquals("Ladung nicht korrekt", "Wird entladen", batt.getLadung());
		
		batt = BatterieDaten.builder().withLadeStand(76).withLadeStrom(-5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();

		assertEquals("Richtung nicht korrekt", "left", batt.getRichtung());
		assertEquals("Farbe nicht korrekt", "gruen", batt.getFarbe());
		assertEquals("Ladung nicht korrekt", "Wird geladen", batt.getLadung());
		
	}

}
