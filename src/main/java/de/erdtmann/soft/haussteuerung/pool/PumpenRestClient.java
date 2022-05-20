package de.erdtmann.soft.haussteuerung.pool;

import javax.ws.rs.client.Client;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import de.erdtmann.soft.haussteuerung.pool.exceptions.PumpenException;
import de.erdtmann.soft.haussteuerung.pool.utils.PoolConstants;
import de.erdtmann.soft.haussteuerung.pool.utils.Pumpe;


@ApplicationScoped
public class PumpenRestClient {

	Logger log = Logger.getLogger(PumpenRestClient.class);

	public int schaltePumpe(Pumpe pumpe) throws PumpenException {
		
		String restCall = PoolConstants.REST_POOL_URL + PoolConstants.REST_PFAD_PUMPE + pumpe.getRestPfad();
		
		log.info(restCall);
		
		Client restClient = ClientBuilder.newClient();
		Response response = restClient.target(restCall).request().post(Entity.text(""));
		
		if (response.getStatus() != 200) {
			throw new PumpenException("Fehler beim Schalten der Pumpe, Response Staus: " + response.getStatus());
		}
		
		log.info("Pumpe Rest Call Response: " + response.getStatus());
		
		return response.getStatus();
				
	}
	
	public int statusPumpe() throws PumpenException {
		String restCall = PoolConstants.REST_POOL_URL + PoolConstants.REST_PFAD_PUMPE + PoolConstants.REST_PFAD_PUMPE_STATUS;
		
		log.info(restCall);
		
		try {

			Client restClient = ClientBuilder.newClient();
			return restClient.target(restCall).request().get(Integer.class);

		} catch (Exception e) {
			throw new PumpenException("Fehler beim holen des Pumpenstatus: " + e.getMessage());
		}
	}
}
