package de.erdtmann.soft.hausSteuerung.licht;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LichtService {

	@Inject
	LichtRestClient restClient;
	
	public void alleLedAn() {
		restClient.alleLEDsAn();
	}
	
	public void alleLedAus() {
		restClient.alleLEDsAus();
	}
}
