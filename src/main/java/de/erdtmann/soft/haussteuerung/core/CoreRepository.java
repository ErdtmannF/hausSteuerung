package de.erdtmann.soft.haussteuerung.core;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import de.erdtmann.soft.haussteuerung.core.entities.KonfigurationE;


@Stateless
public class CoreRepository {

	Logger log = Logger.getLogger(CoreRepository.class);
	
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<KonfigurationE> ladeKonfiguration() {
		Query query = em.createNamedQuery("Konfiguration.ladeKonfiguration", KonfigurationE.class);
		
		return query.getResultList();
	}
	
	public void saveKonfigurationsItem(KonfigurationE konfig) {
		em.merge(konfig);
	}

}
