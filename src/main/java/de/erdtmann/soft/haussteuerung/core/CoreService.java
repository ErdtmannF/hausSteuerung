package de.erdtmann.soft.haussteuerung.core;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.erdtmann.soft.haussteuerung.core.entities.KonfigurationE;
import de.erdtmann.soft.haussteuerung.core.utils.KonfigNames;
import de.erdtmann.soft.haussteuerung.pool.HeizungService;
import de.erdtmann.soft.haussteuerung.pool.PumpenService;
import de.erdtmann.soft.haussteuerung.pool.exceptions.HeizungException;
import de.erdtmann.soft.haussteuerung.pool.exceptions.PumpenException;
import de.erdtmann.soft.haussteuerung.pool.utils.Heizung;
import de.erdtmann.soft.haussteuerung.pool.utils.Pumpe;
import de.erdtmann.soft.haussteuerung.pv.PvService;
import de.erdtmann.soft.haussteuerung.pv.entities.BattLadungE;
import de.erdtmann.soft.haussteuerung.pv.entities.LeistungE;
import de.erdtmann.soft.haussteuerung.pv.exceptions.PvException;
import de.erdtmann.soft.haussteuerung.pv.model.BatterieDaten;
import de.erdtmann.soft.haussteuerung.pv.model.NetzDaten;
import de.erdtmann.soft.haussteuerung.pv.model.PvDaten;
import de.erdtmann.soft.haussteuerung.pv.model.Verbrauch;


@RequestScoped
public class CoreService {

	Logger log = Logger.getLogger(CoreService.class);
	
	@Inject
	CoreRepository coreRepo;

	@Inject
	PumpenService pumpenService;
	
	@Inject
	HeizungService heizungService;
	
	@Inject
	PvService pvService;
	
	private Map<KonfigNames, KonfigurationE> konfiguration;
	
	private static final String FLAG_TRUE = "1";
	private static final String FLAG_FALSE ="0";

	@PostConstruct
	public void ladeKonfiguration() {
		List<KonfigurationE> konfigDBWerte = coreRepo.ladeKonfiguration();

		if (konfiguration == null) {
			konfiguration = new EnumMap<>(KonfigNames.class);
		}
		
		konfiguration.clear();

		for (KonfigurationE konfigE : konfigDBWerte) {
			for (KonfigNames konfEnum : KonfigNames.values()) {
				if (konfigE.getRubrik().equals(konfEnum.getRubrik())	&& konfigE.getKategorie().equals(konfEnum.getKategorie())) {
					konfiguration.put(konfEnum, konfigE);
				}
			}
		}
	}
	
	public void poolSteuerung() {
		if (isPoolAutomatikEin()) {
			try {
				boolean isPumpeAn = pumpenService.holePumpenSatus();
				boolean isHeizungAn = heizungService.holeHeizungSatus();
				
				boolean isPvMin = isPvUeberMin();
				boolean isPvMax = isPvUeberMax();
				
				boolean isBattMin = isBattUeberMin();
				boolean isBattMax = isBattUeberMax();
				
				boolean schaltePumpeAn = false;
				boolean schalteHeizungAn = false;
				
				if (isPvMin && isBattMax) {
					schaltePumpeAn = true;
					if (isPvMax) {
						schalteHeizungAn = true;
					}
				} else {
					
				}
				
				
				
			} catch (Exception e) {
				log.error("Fehler in der PoolSteuerung");
				log.error(e.getMessage());
			}
			
		}
	}
	
	public boolean isPoolAutomatikEin() {
		return (konfiguration.get(KonfigNames.POOL_AUTOMATIK).getWert().equals(FLAG_TRUE));
	}
	
	public boolean isPoolWinterEin() {
		return (konfiguration.get(KonfigNames.POOL_WINTER).getWert().equals(FLAG_TRUE));
	}
	
	public void setPoolAutomatik(boolean automatik) {
		if (automatik) {
			saveKonfigWert(FLAG_TRUE, KonfigNames.POOL_AUTOMATIK);	
		} else {
			saveKonfigWert(FLAG_FALSE, KonfigNames.POOL_AUTOMATIK);
		}
	}
	
	public void setPoolWinter(boolean winter) {
		if (winter) {
			saveKonfigWert(FLAG_TRUE, KonfigNames.POOL_WINTER);
		} else {
			saveKonfigWert(FLAG_FALSE, KonfigNames.POOL_WINTER);
		}
	}

	public int holeMinPv() {
		return Integer.parseInt(konfiguration.get(KonfigNames.POOL_PV_MIN).getWert());
	}

	public int holeMaxPv() {
		return Integer.parseInt(konfiguration.get(KonfigNames.POOL_PV_MAX).getWert());
	}

	public int holeMinBatt() {
		return Integer.parseInt(konfiguration.get(KonfigNames.POOL_BATT_MIN).getWert());
	}

	public int holeMaxBatt() {
		return Integer.parseInt(konfiguration.get(KonfigNames.POOL_BATT_MAX).getWert());
	}

	
	private void saveKonfigWert(String wert, KonfigNames konfig) {
				
		KonfigurationE konfigE = konfiguration.get(konfig);
		
		konfigE.setWert(wert);
		
		coreRepo.saveKonfigurationsItem(konfigE);
	}

	public boolean holePumpenSatus() throws PumpenException {
		return pumpenService.holePumpenSatus();
	}

	public boolean holeHeizungSatus() throws HeizungException {
		return heizungService.holeHeizungSatus();
	}

	public int schaltePumpe(Pumpe zustand) throws PumpenException {
		return pumpenService.schaltePumpe(zustand);
	}

	public int schalteHeizung(Heizung status) throws HeizungException {	
		return heizungService.schalteHeizung(status);
	}

	public boolean isPvUeberMin() throws PvException {
		PvDaten pv = pvService.ladePv();
		
		int pvGesamt = (int) pv.getGesamtLeistung();
		
		return pvGesamt > Integer.parseInt(konfiguration.get(KonfigNames.POOL_PV_MIN).getWert());
	}

	public boolean isPvUeberMax() throws PvException {
		PvDaten pv = pvService.ladePv();
		
		int pvGesamt = (int)pv.getGesamtLeistung(); 
		
		return pvGesamt > Integer.parseInt(konfiguration.get(KonfigNames.POOL_PV_MAX).getWert());

	}

	public boolean isBattUeberMin() throws PvException {
		BatterieDaten batt = pvService.ladeBatterie();
		
		int battLadeStand = (int)batt.getLadeStand();
		
		return battLadeStand >  Integer.parseInt(konfiguration.get(KonfigNames.POOL_BATT_MIN).getWert());
	}

	public boolean isBattUeberMax() throws PvException {
		BatterieDaten batt = pvService.ladeBatterie();
		
		int battLadeStand = (int)batt.getLadeStand();
		
		return battLadeStand >  Integer.parseInt(konfiguration.get(KonfigNames.POOL_BATT_MAX).getWert());
	}

	public BatterieDaten ladeBatterie() throws PvException {
		return pvService.ladeBatterie();
	}

	public PvDaten ladePv() throws PvException {
		return pvService.ladePv();
	}

	public NetzDaten ladeNetz() throws PvException {
		return pvService.ladeNetz();
	}

	public Verbrauch ladeVerbrauch() throws PvException {
		return pvService.ladeVerbrauch();
	}

	public List<BattLadungE> ladeBattLadungTag(LocalDate datum) {
		return pvService.ladeBattLadungTag(datum);
	}

	public List<LeistungE> ladeVerbrauchTagTyp(LocalDate datum, int i) {
		return pvService.ladeVerbrauchTagTyp(datum, i);
	}

	public void speichereDaten() {
		// TODO Auto-generated method stub
		
	}

}
