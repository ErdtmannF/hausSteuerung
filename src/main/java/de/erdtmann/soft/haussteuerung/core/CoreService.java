package de.erdtmann.soft.haussteuerung.core;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import de.erdtmann.soft.haussteuerung.core.entities.KonfigurationE;
import de.erdtmann.soft.haussteuerung.core.utils.KonfigNames;


@RequestScoped
public class CoreService {

	@Inject
	CoreRepository coreRepo;

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

}
