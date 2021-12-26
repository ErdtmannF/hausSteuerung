package de.erdtmann.soft.haussteuerung.licht.utils;

import java.util.ArrayList;
import java.util.List;

public class LichtKonfig {

	private int anzahlLeds;
	private int v1;
	private int v2;
	private int anzahlBereiche;
	private int hell;
	private boolean aktiv;
	private Colors farbe;
	private WsLedModus aktMode;
	private List<LedBereich> bereiche;
	
	protected LichtKonfig() { }
	
	private LichtKonfig(Builder builder) {
		setAnzahlLeds(builder.anzahlLeds);
		setV1(builder.v1);
		setV2(builder.v2);
		setAnzahlBereiche(builder.anzahlBereiche);
		setHell(builder.hell);
		setAktiv(builder.aktiv);
		setFarbe(builder.farbe);
		setAktMode(builder.aktMode);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	
	public int getAnzahlLeds() {
		return anzahlLeds;
	}
	public void setAnzahlLeds(int anzahlLeds) {
		this.anzahlLeds = anzahlLeds;
	}
	public int getV1() {
		return v1;
	}
	public void setV1(int v1) {
		this.v1 = v1;
	}
	public int getV2() {
		return v2;
	}
	public void setV2(int v2) {
		this.v2 = v2;
	}
	public int getAnzahlBereiche() {
		return anzahlBereiche;
	}
	public void setAnzahlBereiche(int anzahlBereiche) {
		this.anzahlBereiche = anzahlBereiche;
	}
	public int getHell() {
		return hell;
	}
	public void setHell(int hell) {
		this.hell = hell;
	}
	public boolean isAktiv() {
		return aktiv;
	}
	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}
	public Colors getFarbe() {
		return farbe;
	}
	public void setFarbe(Colors farbe) {
		this.farbe = farbe;
	}
	public WsLedModus getAktMode() {
		return aktMode;
	}
	public void setAktMode(WsLedModus mode) {
		this.aktMode = mode;
	}
	public List<LedBereich> getBereiche() {
		return bereiche;
	}
	public void setBereiche() {
		if (bereiche == null) {
			bereiche = new ArrayList<>();
		}
		
		int g = this.anzahlLeds / this.anzahlBereiche;
		
		for (int i = 0; i < this.anzahlBereiche ; i++) {

			int anfang = i * g;
			int ende = g * (i+1);

			LedBereich bereich = LedBereich.builder().withAnfang(anfang).withEnde(ende).build();	
		}
	}
	
	public static final class Builder {
		private int anzahlLeds;
		private int v1;
		private int v2;
		private int anzahlBereiche;
		private int hell;
		private boolean aktiv;
		private Colors farbe;
		private WsLedModus aktMode;
		
		private Builder() {
		}

		public Builder withAnzahlLeds(int wert) {
			this.anzahlLeds = wert;
			return this;
		}
		
		public Builder withV1(int wert) {
			this.v1 = wert;
			return this;
		}
		
		public Builder withV2(int wert) {
			this.v2 = wert;
			return this;
		}

		public Builder withAnzahlBereiche(int wert) {
			this.anzahlBereiche = wert;
			return this;
		}
		
		public Builder withHell(int wert) {
			this.hell = wert;
			return this;
		}
		
		public Builder withAktiv(boolean wert) {
			this.aktiv = wert;
			return this;
		}
		
		public Builder withFarbe(Colors wert) {
			this.farbe = wert;
			return this;
		}

		public Builder withAktMode(WsLedModus wert) {
			this.aktMode = wert;
			return this;
		}
		
		public LichtKonfig build() {
			return new LichtKonfig(this);
		}
	}

}
