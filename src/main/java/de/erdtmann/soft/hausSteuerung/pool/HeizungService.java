package de.erdtmann.soft.hausSteuerung.pool;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.erdtmann.soft.hausSteuerung.pool.exceptions.HeizungException;
import de.erdtmann.soft.hausSteuerung.pool.utils.Heizung;


@ApplicationScoped
public class HeizungService {

//	@Inject
//	HeizungRepository heizungRepo;

	@Inject
	HeizungRestClient restClient;

	public boolean holeHeizungSatus() throws HeizungException {
		return (restClient.statusHeizung() == 1);
	}

	public int schalteHeizung(Heizung heizung) throws HeizungException {
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

	}

	void beendeHeizungLauf() {

	}

}
