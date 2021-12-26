package de.erdtmann.soft.haussteuerung.pool.beans;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.erdtmann.soft.haussteuerung.core.CoreService;
import de.erdtmann.soft.haussteuerung.pool.HeizungService;
import de.erdtmann.soft.haussteuerung.pool.PumpenService;
import de.erdtmann.soft.haussteuerung.pool.exceptions.PumpenException;
import de.erdtmann.soft.haussteuerung.pool.utils.Heizung;
import de.erdtmann.soft.haussteuerung.pool.utils.Pumpe;

import java.io.Serializable;

@Named
@ViewScoped
public class PoolBean implements Serializable {

	private static final long serialVersionUID = -6089267772903098666L;

	@Inject
	PumpenService pumpenService;

	@Inject
	HeizungService heizungService;

	@Inject
	CoreService coreService;
	
	private boolean automatik = false;
	private boolean winter = true;

	private boolean pumpeAn = false;
	private boolean heizungAn = false;
	private String pumpenIcon;
	private String heizungIcon;

	@PostConstruct
	public void init() {
		aktualisiere();
	}

	public void aktualisiere() {
		try {
			winter = coreService.isPoolWinterEin();
			automatik = coreService.isPoolAutomatikEin();
			
			if (!winter) {
				pumpeAn = pumpenService.holePumpenSatus();
				heizungAn = heizungService.holeHeizungSatus();
			}
			
			setPumpenIcon();
			setHeizungIcon();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setPoolWinter() {
		coreService.setPoolWinter(winter);
	}
	
	public void setPoolAutomatik() {
		coreService.setPoolAutomatik(automatik);
	}

	private void setPumpenIcon() {
		if (pumpeAn) {
			pumpenIcon = "refresh fa-spin gruen";
		} else
			pumpenIcon = "close rot";
	}

	private void setHeizungIcon() {
		if (heizungAn) {
			heizungIcon = "check gruen";
		} else {
			heizungIcon = "close rot";
		}
	}

	public void pumpeEin() {
		int status = 0;
		try {
			status = pumpenService.schaltePumpe(Pumpe.AN);
		} catch (PumpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (status == 200) {
			addMessage("Pumpe eingeschaltet!");
			aktualisiere();
		} else {
			addErrMessage("Fehler beim Einschalten der Pumpe!");
		}
	}

	public void pumpeAus() {
		int status = 0;
		try {
			status = pumpenService.schaltePumpe(Pumpe.AUS);
		} catch (PumpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (status == 200) {
			addMessage("Pumpe ausgeschaltet!");
			aktualisiere();
		} else {
			addErrMessage("Fehler beim Ausschalten der Pumpe!");
		}
	}

	public void heizungEin() {
		int status = 0;
		
		status = heizungService.schalteHeizung(Heizung.AN);
		

		if (status == 200) {
			addMessage("Heizung eingeschaltet!");
			aktualisiere();
		} else {
			addErrMessage("Fehler beim Einschalten der Heizung!");
		}
	}

	public void heizungAus() {
		int status = 0;
		
		status = heizungService.schalteHeizung(Heizung.AUS);
		
		
		if (status == 200) {
			addMessage("Heizung ausgeschaltet!");
			aktualisiere();
		} else {
			addErrMessage("Fehler beim Ausschalten der Heizung!");
		}
	}

	
	public boolean isWinter() {
		return winter;
	}
	public void setWinter(boolean winter) {
		this.winter = winter;
	}
	
	public boolean isAutomatik() {
		return automatik;
	}
	public void setAutomatik(boolean automatik) {
		this.automatik = automatik;
	}

	public boolean isPumpeAn() {
		return pumpeAn;
	}

	public boolean isPumpeAnButtonDisabled() {
		return pumpeAn || automatik || winter;
	}

	public boolean isPumpeAusButtonDisabled() {
		return !pumpeAn || automatik || winter;
	}
	
	public boolean isHeizungAn() {
		return heizungAn;
	}

	public boolean isheizungAnBottonDisabled() {
		return heizungAn || automatik || winter;
	}

	public boolean isheizungAusBottonDisabled() {
		return !heizungAn || automatik || winter;
	}

	public String getPumpenIcon() {
		return pumpenIcon;
	}

	public String getHeizungIcon() {
		return heizungIcon;
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addErrMessage(String summary) {
		FacesMessage errMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, errMessage);
	}
}
