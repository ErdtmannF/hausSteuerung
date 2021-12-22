package de.erdtmann.soft.hausSteuerung.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import de.erdtmann.soft.hausSteuerung.core.entities.KonfigurationE;
import de.erdtmann.soft.hausSteuerung.core.utils.KonfigNames;


@RequestScoped
public class CoreService {

	@Inject
	CoreRepository coreRepo;

	private Map<KonfigNames, KonfigurationE> konfiguration;
	
	private String FLAG_TRUE = "1";
	private String FLAG_FALSE ="0";

	@PostConstruct
	public void ladeKonfiguration() {
		List<KonfigurationE> konfigDBWerte = coreRepo.ladeKonfiguration();

		if (konfiguration == null) {
			konfiguration = new HashMap<KonfigNames, KonfigurationE>();
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

	private void saveKonfigWert(String wert, KonfigNames konfig) {
		
		KonfigurationE konfigE = konfiguration.get(konfig);
		
		konfigE.setWert(wert);
		
		coreRepo.saveKonfigurationsItem(konfigE);
	}

}
