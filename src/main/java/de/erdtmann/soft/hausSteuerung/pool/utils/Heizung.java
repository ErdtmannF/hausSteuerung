package de.erdtmann.soft.hausSteuerung.pool.utils;

public enum Heizung {
	AN(true, "/ein"), AUS(false, "/aus");
	
	private boolean an;
	private String restPfad;
	
	Heizung(boolean an, String restPfad) {
		this.an = an;
		this.restPfad = restPfad;
	}

	public boolean isAn() {
		return an;
	}

	public void setAn(boolean an) {
		this.an = an;
	}

	public String getRestPfad() {
		return restPfad;
	}

	public void setRestPfad(String restPfad) {
		this.restPfad = restPfad;
	}
	
	
}
