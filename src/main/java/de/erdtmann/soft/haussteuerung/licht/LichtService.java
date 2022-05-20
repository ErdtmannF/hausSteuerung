package de.erdtmann.soft.haussteuerung.licht;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.erdtmann.soft.utils.licht.WsLedModus;

@ApplicationScoped
public class LichtService {

	@Inject
	LichtRestClient restClient;
	
	public void alleLedAn() {
		restClient.restPost(WsLedModus.ALLE_AN, null);
	}
	
	public void alleLedAus() {
		restClient.restPost(WsLedModus.ALLE_AUS, null);
	}
	
	public void farbe(String farbe) {
		restClient.restPost(WsLedModus.FARBE, farbe);
	}
	
	public void helligkeit(int wert) {
		restClient.restPost(WsLedModus.HELLIGKEIT, wert+"");
	}
	
	public void v1(int wert) {
		restClient.restPost(WsLedModus.V1, wert+"");
	}
	
	public void v2(int wert) {
		restClient.restPost(WsLedModus.V2, wert+"");
	}

}
