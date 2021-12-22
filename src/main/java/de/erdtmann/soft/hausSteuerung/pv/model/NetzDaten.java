package de.erdtmann.soft.hausSteuerung.pv.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NetzDaten implements Serializable {

	private static final long serialVersionUID = -701813234802852044L;

	private float leistung;
	private String richtung;
	private String farbe;
	
	DecimalFormat df;
	DecimalFormatSymbols symbols;

	public static Builder builder() {
		return new Builder();
	}

	public NetzDaten(Builder builder) {
		symbols = new DecimalFormatSymbols();
		symbols.setMinusSign(' ');
		df = new DecimalFormat("#", symbols);
		
		setLeistung(builder.leistung);
		setRichtung();
	}
	
	public float getLeistung() {
		return leistung;
	}
	public String getLeistungStr() {
		return df.format(leistung);
	}
	public String getRichtung() {
		return richtung;
	}
	public String getFarbe() {
		return farbe;
	}
	private void setLeistung(float leistung) {
		this.leistung = leistung;
	}
	private void setRichtung() {
		if (this.leistung > 0) {
			this.richtung = "left";
			this.farbe = "rot";
		} else {
			this.richtung = "right";
			this.farbe = "gruen";
		}
	}

	public static final class Builder {
		private float leistung;

		private Builder() {	}

		public Builder withLeistung(float leistung) {
			this.leistung = leistung;
			return this;
		}
		
		public NetzDaten build() {
			return new NetzDaten(this);
		}
	}
}
