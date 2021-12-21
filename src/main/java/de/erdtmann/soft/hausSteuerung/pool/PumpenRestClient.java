package de.erdtmann.soft.hausSteuerung.pool;

import javax.ws.rs.client.Client;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import de.erdtmann.soft.hausSteuerung.pool.exceptions.PumpenException;
import de.erdtmann.soft.hausSteuerung.pool.utils.PoolConstants;
import de.erdtmann.soft.hausSteuerung.pool.utils.Pumpe;


@ApplicationScoped
public class PumpenRestClient {

	Logger log = Logger.getLogger(PumpenRestClient.class);

	public int schaltePumpe(Pumpe pumpe) throws PumpenException {
		
		String restCall = PoolConstants.REST_POOL_URL + PoolConstants.REST_PFAD_PUMPE + pumpe.getRestPfad();
		
		Client restClient = ClientBuilder.newClient();
		Response response = restClient.target(restCall).request().post(Entity.text(""));
		
		if (response.getStatus() != 200) {
			log.error("Fehler beim Schalten der Pumpe");
			log.error("Response Staus: " + response.getStatus());
		}
		
		return response.getStatus();
				
	}
	
	public int statusPumpe() throws PumpenException {
		String restCall = PoolConstants.REST_POOL_URL + PoolConstants.REST_PFAD_PUMPE + PoolConstants.REST_PFAD_PUMPE_STATUS;
		try {

			Client restClient = ClientBuilder.newClient();
			return restClient.target(restCall).request().get(Integer.class);

		} catch (Exception e) {
			log.error("Fehler beim holen des Pumpenstatus!");
			log.error(e.getMessage());
		}
		return 0;

	}
}
