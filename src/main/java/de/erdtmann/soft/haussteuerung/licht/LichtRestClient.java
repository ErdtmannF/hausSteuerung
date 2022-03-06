package de.erdtmann.soft.haussteuerung.licht;

import javax.ws.rs.client.Client;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import de.erdtmann.soft.utilsPackage.licht.LichtConstants;
import de.erdtmann.soft.utilsPackage.licht.WsLedModus;

@ApplicationScoped
public class LichtRestClient {

	Logger log = Logger.getLogger(LichtRestClient.class);
	
	public int alleLEDsAus() {
		
		Client restClient = ClientBuilder.newClient();

		Response response = restClient.target(LichtConstants.REST_LICHT_URL)
										.path(LichtConstants.REST_WS_PATH)
										.path(WsLedModus.ALLE_AUS.getParameter())
										.request()
										.post(Entity.text(""));
		
		if (response.getStatus() != 200) {
			logError("Fehler beim Ausschalten der LEDs", response);
		}
		
		return response.getStatus();
	}


	public int alleLEDsAn() {
		
		Client restClient = ClientBuilder.newClient();
		
		Response response = restClient.target(LichtConstants.REST_LICHT_URL)
										.path(LichtConstants.REST_WS_PATH)
										.path(WsLedModus.ALLE_AN.getParameter())
										.request()
										.post(Entity.text(""));
		
		if (response.getStatus() != 200) {
			logError("Fehler beim Einschalten der LEDs", response);
		}
		
		return response.getStatus();
				
	}

	public int helligkeit(int wert) {
		
		Client restClient = ClientBuilder.newClient();
		
		Response response = restClient.target(LichtConstants.REST_LICHT_URL)
										.path(LichtConstants.REST_WS_PATH)
										.path(WsLedModus.HELLIGKEIT.getParameter())
										.queryParam("wert", wert)
										.request()
										.post(Entity.text(""));
		
		if (response.getStatus() != 200) {
			logError("Fehler beim aendern der Helligkeit", response);
		}
		return response.getStatus();
	}

	private void logError(String text, Response response) {
		log.error(text);
		log.error("Response Staus: " + response.getStatus());
	}

	
}
