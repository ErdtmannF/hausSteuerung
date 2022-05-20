package de.erdtmann.soft.haussteuerung.licht.beans;

import javax.faces.view.ViewScoped;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import de.erdtmann.soft.haussteuerung.licht.LichtService;

import java.io.Serializable;

@Named
@ViewScoped
public class LichtBean implements Serializable {

	private static final long serialVersionUID = 1583675924502529084L;
	Logger log = Logger.getLogger(LichtBean.class);
	
	@Inject
	LichtService lichtService;
	
	private String mainColor;
	private int helligkeit = 255;
	private int v1 = 50;
	private int v2 = 50;
	private String modus = "alleAus";
	
	@PostConstruct
	public void init() {
		// Aktuell nichts zu tun beim Starten
	}

	
	public void alleAn() {
		modus = "alleAn";
		lichtService.alleLedAn();
	}
	
	public void alleAus() {
		modus = "alleAus";
		lichtService.alleLedAus();
	}
	
	public void farbe(String farbe) {
		lichtService.farbe(farbe);
	}
	
	public void aendereHelligkeit() {
		lichtService.helligkeit(helligkeit);
	}
	
	public void aendereV1() {
		lichtService.v1(v1);
	}
	
	public void aendereV2() {
		lichtService.v2(v2);
	}
	
	public String getMainColor() {
		return mainColor;
	}
	public void setMainColor(String mainColor) {
		this.mainColor = mainColor;
	}

	public int getHelligkeit() {
		return helligkeit;
	}
	public void setHelligkeit(int helligkeit) {
		this.helligkeit = helligkeit;
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

	public String getModus() {
		return modus;
	}
	public void setModus(String modus) {
		this.modus = modus;
	}
}
