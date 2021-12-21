package de.erdtmann.soft.hausSteuerung.pool.utils;

public enum Pumpe {
	AN(true, "/ein"), AUS(false, "/aus");
	
	private boolean an;
	private String restPfad;
	
	Pumpe(boolean an, String restPfad) {
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
