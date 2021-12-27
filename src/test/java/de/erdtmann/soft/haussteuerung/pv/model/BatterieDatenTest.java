package de.erdtmann.soft.haussteuerung.pv.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BatterieDatenTest {

	@Test
	public void test() {
		
		BatterieDaten batt = BatterieDaten.builder().withLadeStand(100).withLadeStrom(5).withLadeZyklen(50).withSpannung(230).withTemperatur(20).build();
		
		assertEquals("Ladestand nicht korrekt", 100, batt.getLadeStand(), 0);
		
	}

}
