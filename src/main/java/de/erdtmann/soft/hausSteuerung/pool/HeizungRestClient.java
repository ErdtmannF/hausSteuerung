package de.erdtmann.soft.hausSteuerung.pool;

import javax.ws.rs.client.Client;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import de.erdtmann.soft.hausSteuerung.pool.utils.Heizung;
import de.erdtmann.soft.hausSteuerung.pool.utils.PoolConstants;


@ApplicationScoped
public class HeizungRestClient {

	Logger log = Logger.getLogger(HeizungRestClient.class);

	public int schalteHeizung(Heizung heizung) {
		
		String restCall = PoolConstants.REST_POOL_URL + PoolConstants.REST_PFAD_HEIZUNG + heizung.getRestPfad();
		
		Client restClient = ClientBuilder.newClient();
		Response response = restClient.target(restCall).request().post(Entity.text(""));
		
		if (response.getStatus() != 200) {
			log.error("Fehler beim Schalten der Heizung!");
			log.error("Response Staus: " + response.getStatus());
		}
		
		return response.getStatus();
	}
	
	public int statusHeizung() {
		String restCall = PoolConstants.REST_POOL_URL + PoolConstants.REST_PFAD_HEIZUNG + PoolConstants.REST_PFAD_HEIZUNG_STATUS;
		try {
			Client restClient = ClientBuilder.newClient();
			return restClient.target(restCall).request().get(Integer.class);
		} catch (Exception e) {
			log.error("Fehler beim holen des Heizungstatus!");
			log.error(e.getMessage());
		}
		return 0;

	}

}
