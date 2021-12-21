package de.erdtmann.soft.hausSteuerung.licht.beans;

import javax.faces.view.ViewScoped;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import de.erdtmann.soft.hausSteuerung.licht.LichtService;

import java.io.Serializable;

@Named
@ViewScoped
public class LichtBean implements Serializable {

	private static final long serialVersionUID = 1583675924502529084L;
	Logger log = Logger.getLogger(LichtBean.class);
	
	@Inject
	LichtService lichtService;
	
	private String mainColor;
	private int helligkeit;
	private int geschwindigkeit;
	private String modus = "alleAus";
	
	@PostConstruct
	public void init() {
		
	}

	
	public void alleAn() {
		modus = "alleAn";
		lichtService.alleLedAn();
	}
	
	public void alleAus() {
		modus = "alleAus";
		lichtService.alleLedAus();
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

	public int getGeschwindigkeit() {
		return geschwindigkeit;
	}
	public void setGeschwindigkeit(int geschwindigkeit) {
		this.geschwindigkeit = geschwindigkeit;
	}

	public String getModus() {
		return modus;
	}
	public void setModus(String modus) {
		this.modus = modus;
	}
}
