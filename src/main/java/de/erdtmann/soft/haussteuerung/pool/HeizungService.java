package de.erdtmann.soft.haussteuerung.pool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.erdtmann.soft.haussteuerung.pool.utils.Heizung;


@ApplicationScoped
public class HeizungService {

//	@Inject
//	HeizungRepository heizungRepo;

	@Inject
	HeizungRestClient restClient;

	public boolean holeHeizungSatus() {
		return (restClient.statusHeizung() == 1);
	}

	public int schalteHeizung(Heizung heizung) {
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
