package de.erdtmann.soft.haussteuerung.licht.utils;

public enum WsLedModus {

	ALLE_AN("alleAn", 0),
	ALLE_AUS("alleAus", 0),
	AN_AUS("anAus", 0),
	HIN_HER("hinHer", 0),
	FADE("fade", 0),
	COLOR_FADE("colorFade", 0),
	HELLIGKEIT("helligkeit", 1),
	V1("v1", 1),
	V2("v2", 1),
	FARBE("farbe", 1);
	
	private String parameter;
	private int werte;
	
	WsLedModus(String parameter, int werte) {
		this.parameter = parameter;
		this.werte = werte;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public int getWerte() {
		return werte;
	}

	public void setWerte(int werte) {
		this.werte = werte;
	}
	
	
}
