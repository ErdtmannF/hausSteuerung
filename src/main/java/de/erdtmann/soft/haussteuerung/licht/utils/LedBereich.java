package de.erdtmann.soft.haussteuerung.licht.utils;


public class LedBereich {

	private int anfang;
	private int ende;
	
	protected LedBereich() { }
	
	private LedBereich(Builder builder) {
		setAnfang(builder.anfang);
		setEnde(builder.ende);
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public int getAnfang() {
		return anfang;
	}
	public void setAnfang(int anfang) {
		this.anfang = anfang;
	}
	public int getEnde() {
		return ende;
	}
	public void setEnde(int ende) {
		this.ende = ende;
	}

	public static final class Builder {
		private int anfang;
		private int ende;

		private Builder() {
		}

		public Builder withAnfang(int wert) {
			this.anfang = wert;
			return this;
		}
		
		public Builder withEnde(int wert) {
			this.ende = wert;
			return this;
		}

		
		public LedBereich build() {
			return new LedBereich(this);
		}
	}

	
	
}
