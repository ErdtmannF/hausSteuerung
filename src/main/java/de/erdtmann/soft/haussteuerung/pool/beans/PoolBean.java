package de.erdtmann.soft.haussteuerung.pool.beans;


import javax.faces.application.FacesMessage;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import de.erdtmann.soft.haussteuerung.core.CoreService;
import de.erdtmann.soft.haussteuerung.pool.exceptions.HeizungException;
import de.erdtmann.soft.haussteuerung.pool.exceptions.PumpenException;
import de.erdtmann.soft.haussteuerung.pool.utils.Heizung;
import de.erdtmann.soft.haussteuerung.pool.utils.Pumpe;
import de.erdtmann.soft.haussteuerung.pv.exceptions.PvException;

import java.io.Serializable;

@Named
@ViewScoped
public class PoolBean implements Serializable {

	private static final long serialVersionUID = -6089267772903098666L;
	Logger log = Logger.getLogger(PoolBean.class);
	
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
		
		log.info("PoolBean wird aktualisiert");
		
		try {
			winter = coreService.isPoolWinterEin();
			automatik = coreService.isPoolAutomatikEin();
			
			if (!winter) {
				pumpeAn = coreService.holePumpenSatus();
				heizungAn = coreService.holeHeizungSatus();
			}
			
			setPumpenIcon();
			setHeizungIcon();
			
		} catch (Exception e) {
			log.error(e.getMessage());
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
		log.info("Pumpe ein Button gedrueckt");
		int status = 0;
		try {
			status = coreService.schaltePumpe(Pumpe.AN);
		} catch (PumpenException e) {
			log.error("Fehler beim einschalten der Pumpe");
			log.error(e.getMessage());
		}

		if (status == 200) {
			addMessage("Pumpe eingeschaltet!");
			aktualisiere();
		} else {
			addErrMessage("Fehler beim Einschalten der Pumpe!");
		}
	}

	public void pumpeAus() {
		log.info("Pumpe aus Button gedrueckt");
		int status = 0;
		try {
			status = coreService.schaltePumpe(Pumpe.AUS);
		} catch (PumpenException e) {
			log.error("Fehler beim ausschalten der Pumpe");
			log.error(e.getMessage());		}
		
		if (status == 200) {
			addMessage("Pumpe ausgeschaltet!");
			aktualisiere();
		} else {
			addErrMessage("Fehler beim Ausschalten der Pumpe!");
		}
	}

	public void heizungEin() {
		log.info("Heizung ein Button gedrueckt");
		int status = 0;
		
		try {
			status = coreService.schalteHeizung(Heizung.AN);
		} catch (HeizungException e) {
			log.error("Fehler beim einschalten der Heizung");		
			log.error(e.getMessage());
		}		

		if (status == 200) {
			addMessage("Heizung eingeschaltet!");
			aktualisiere();
		} else {
			addErrMessage("Fehler beim Einschalten der Heizung!");
		}
	}

	public void heizungAus() {
		log.info("Heizung aus Button gedrueckt");
		int status = 0;
		
		try {
			status = coreService.schalteHeizung(Heizung.AUS);
		} catch (HeizungException e) {
			log.error("Fehler beim ausschalten der Heizung");
			log.error(e.getMessage());
		}
		
		
		if (status == 200) {
			addMessage("Heizung ausgeschaltet!");
			aktualisiere();
		} else {
			addErrMessage("Fehler beim Ausschalten der Heizung!");
		}
	}

	public boolean isPvMin() {
		try {
			return coreService.isPvUeberMin();
		} catch (PvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean isPvMax() {
		try {
			return coreService.isPvUeberMax();
		} catch (PvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean isBattMin() {
		try {
			return coreService.isBattUeberMin();
		} catch (PvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean isBattMax() {
		try {
			return coreService.isBattUeberMax();
		} catch (PvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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
		return pumpeAn || heizungAn || automatik || winter;
	}

	public boolean isheizungAusBottonDisabled() {
		return pumpeAn || !heizungAn || automatik || winter;
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
