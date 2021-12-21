package de.erdtmann.soft.hausSteuerung.pool.utils;

public enum Temperatur {
	WASSER("Wassertemperatur", 1, 0, "/wasser"), SOLAR("Solartemperatur", 2, 1, "/solar");

	// Technische ID für die Datenbank
	private int id;
	// ID für den AD Wandler
	private int value;
	// Rest Pfad für eine Temperatur
	private String restPfad;
	// Bezeichnung der Temperatur
	private String bezeichnung;

	Temperatur(String bezeichnung, int id, int value, String restPfad) {
		this.bezeichnung = bezeichnung;
		this.id = id;
		this.value = value;
		this.restPfad = restPfad;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getRestPfad() {
		return restPfad;
	}

	public void setRestPfad(String restPfad) {
		this.restPfad = restPfad;
	}

}
