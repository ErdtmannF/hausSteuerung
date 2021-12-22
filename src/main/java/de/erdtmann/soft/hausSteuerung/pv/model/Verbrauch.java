package de.erdtmann.soft.hausSteuerung.pv.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class Verbrauch implements Serializable {

	private static final long serialVersionUID = 5421698026411882762L;

	private float vonBatt;
	private float vonPv;
	private float vonNetz;
	private float gesamtVerbrauch;
	
	DecimalFormat df;
	DecimalFormatSymbols symbols;

	public static Builder builder() {
		return new Builder();
	}

	public Verbrauch(Builder builder) {
		symbols = new DecimalFormatSymbols();
		symbols.setMinusSign(' ');
		df = new DecimalFormat("#", symbols);

		setVonBatt(builder.vonBatt);
		setVonPv(builder.vonPv);
		setVonNetz(builder.vonNetz);
		setGesamtVerbrauch();
	}

	public float getVonBatt() {
		return vonBatt;
	}
	public float getVonPv() {
		return vonPv;
	}
	public float getVonNetz() {
		return vonNetz;
	}
	public float getGesamtVerbrauch() {
		return gesamtVerbrauch;
	}
	public String getGesamtVerbrauchStr() {
		return df.format(gesamtVerbrauch);
	}

	private void setVonBatt(float vonBatt) {
		this.vonBatt = vonBatt;
	}
	private void setVonPv(float vonPv) {
		this.vonPv = vonPv;
	}
	private void setVonNetz(float vonNetz) {
		this.vonNetz = vonNetz;
	}
	private void setGesamtVerbrauch() {
		this.gesamtVerbrauch = vonBatt + vonPv + vonNetz;
	}

	public static final class Builder {
		private float vonBatt;
		private float vonPv;
		private float vonNetz;

		private Builder() {	}

		public Builder withVonBatt(float vonBatt) {
			this.vonBatt = vonBatt;
			return this;
		}
		public Builder withVonPv(float vonPv) {
			this.vonPv = vonPv;
			return this;
		}
		public Builder withVonNetz(float vonNetz) {
			this.vonNetz = vonNetz;
			return this;
		}
		public Verbrauch build() {
			return new Verbrauch(this);
		}
	}
}
