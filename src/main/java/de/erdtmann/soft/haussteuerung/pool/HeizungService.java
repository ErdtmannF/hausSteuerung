package de.erdtmann.soft.haussteuerung.pool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.erdtmann.soft.haussteuerung.pool.exceptions.HeizungException;
import de.erdtmann.soft.haussteuerung.pool.utils.Heizung;


@ApplicationScoped
public class HeizungService {

	Logger log = Logger.getLogger(HeizungService.class);
	
//	@Inject
//	HeizungRepository heizungRepo;

	@Inject
	HeizungRestClient restClient;

	public boolean holeHeizungSatus() throws HeizungException {
		return (restClient.statusHeizung() == 1);
	}

	public int schalteHeizung(Heizung heizung) throws HeizungException {
		log.info("heizung: " + heizung.name());
		
		int status = restClient.schalteHeizung(heizung);
		
		if (status == 200) {
			if (heizung.equals(Heizung.AN)) {
				starteHeizungLauf();
			} else if (heizung.equals(Heizung.AUS)) {
				beendeHeizungLauf();
			}
		}
		return status;
	}

	void starteHeizungLauf() {
		// Start Heizungslauf implementieren
	}

	void beendeHeizungLauf() {
		// Ende Heizungslauf implementieren
	}

}
