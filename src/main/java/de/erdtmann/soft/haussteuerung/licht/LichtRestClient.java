package de.erdtmann.soft.haussteuerung.licht;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import de.erdtmann.soft.utils.licht.Colors;
import de.erdtmann.soft.utils.licht.LichtConstants;
import de.erdtmann.soft.utils.licht.WsLedModus;

@ApplicationScoped
public class LichtRestClient {

	Logger log = Logger.getLogger(LichtRestClient.class);
	
	public int restPost(WsLedModus modus, String wert) {
		
		Client restClient = ClientBuilder.newClient();
		Response response = null;
		
		switch (modus.getWerte()) {
		case 0:{
			log.info(LichtConstants.REST_LICHT_URL + LichtConstants.REST_WS_PATH + "/" + modus.name());
			
			response = restClient.target(LichtConstants.REST_LICHT_URL)
									.path(LichtConstants.REST_WS_PATH)
									.path(modus.name())
									.request()
									.post(Entity.text(""));
			break;
		}
		case 1:{
			log.info(LichtConstants.REST_LICHT_URL + LichtConstants.REST_WS_PATH + "/" + modus.name() + "/" + wert);
			
			response = restClient.target(LichtConstants.REST_LICHT_URL)
									.path(LichtConstants.REST_WS_PATH)
									.path(modus.name())
									.path("/" + wert)
									.request()
									.post(Entity.text(""));
			break;
		}

		default:
			break;
		}
		
		if (response != null && response.getStatus() != 200) {
			logError("Fehler beim aendern der Helligkeit", response);
		} else if (response == null) {
			log.error("Kein Response vom Server");
		}
		
		return response.getStatus();
		
	}
	
	private void logError(String text, Response response) {
		log.error(text);
		log.error("Response Staus: " + response.getStatus());
	}

	
}
