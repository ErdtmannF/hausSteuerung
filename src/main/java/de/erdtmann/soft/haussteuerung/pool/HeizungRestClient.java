package de.erdtmann.soft.haussteuerung.pool;

import javax.ws.rs.client.Client;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import de.erdtmann.soft.haussteuerung.pool.exceptions.HeizungException;
import de.erdtmann.soft.haussteuerung.pool.utils.Heizung;
import de.erdtmann.soft.haussteuerung.pool.utils.PoolConstants;


@ApplicationScoped
public class HeizungRestClient {

	Logger log = Logger.getLogger(HeizungRestClient.class);

	public int schalteHeizung(Heizung heizung) throws HeizungException {
		
		String restCall = PoolConstants.REST_POOL_URL + PoolConstants.REST_PFAD_HEIZUNG + heizung.getRestPfad();
		
		log.info(restCall);
		
		Client restClient = ClientBuilder.newClient();
		Response response = restClient.target(restCall).request().post(Entity.text(""));
		
		if (response.getStatus() != 200) {
			throw new HeizungException("Fehler beim Schalten der Heizung: Response Staus: " + response.getStatus());
		}
		
		log.info("Heizung Rest Call Response: " + response.getStatus());
		
		return response.getStatus();
	}
	
	public int statusHeizung() throws HeizungException {
		String restCall = PoolConstants.REST_POOL_URL + PoolConstants.REST_PFAD_HEIZUNG + PoolConstants.REST_PFAD_HEIZUNG_STATUS;
		
		log.info(restCall);
		
		try {
			Client restClient = ClientBuilder.newClient();
			return restClient.target(restCall).request().get(Integer.class);
		} catch (Exception e) {
			throw new HeizungException("Fehler beim holen des Heizungstatus: " + e.getMessage());
		}
	}

}
