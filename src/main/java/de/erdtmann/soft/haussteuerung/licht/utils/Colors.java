package de.erdtmann.soft.haussteuerung.licht.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public enum Colors {

	AUS (Color.BLACK, false),
	WEISS (Color.WHITE, false),
	GELB (Color.YELLOW, true),
	ORANGE (Color.ORANGE, true),
	ROT (Color.RED, true),
	PINK (Color.PINK, true),
	MAGENTA (Color.MAGENTA, true),
	CYAN (Color.CYAN, true),
	BLAU (Color.BLUE, true),
	GRUEN (Color.GREEN, true);

	private Color farbe;
	private boolean multi; 
	
	Colors(Color c, boolean multi) {
		this.farbe = c;
		this.multi = multi;
	}

	public Color getFarbe() {
		return farbe;
	}

	public boolean isMulti() {
		return multi;
	}
	
	public static List<Colors> alleMultiFarben() {
		
		List<Colors> liste = new ArrayList<>();
		
		for (Colors colors : values()) {
			if (colors.multi) {
				liste.add(colors);
			}
		}
		return liste;
	}
	
	public static Colors getFarbeByName(String farbe) {
		return valueOf(farbe);
	}
}
