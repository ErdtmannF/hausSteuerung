package de.erdtmann.soft.hausSteuerung.pv;


import java.util.List;
import java.time.LocalDate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import de.erdtmann.soft.hausSteuerung.pv.entities.BattLadungE;
import de.erdtmann.soft.hausSteuerung.pv.entities.LeistungE;



@Stateless
public class PvRepository {

	Logger log = Logger.getLogger(PvRepository.class);
	
	@PersistenceContext
	EntityManager em;

//	public List<PvModbusE> holeAlleModbus() {
//		TypedQuery<PvModbusE> query = em.createNamedQuery("Pv.holeAlleModbus", PvModbusE.class);
//
//		List<PvModbusE> modbusList = query.getResultList();
//	
//		return modbusList;
//	}

	public void speichereLeistung(LeistungE leistung) {
		em.persist(leistung);	
	}

	@SuppressWarnings("unchecked")
	public List<LeistungE> holeLeistungTagTyp(LocalDate tag, int typ) {
		Query query = em.createNamedQuery("Leistung.holeLeistungByTagAndTyp", LeistungE.class);
		query.setParameter("tag", tag);
		query.setParameter("typ", typ);
		
		return query.getResultList();
	}

	public void speichereBattLadung(BattLadungE battLadung) {
		em.persist(battLadung);
	}

	@SuppressWarnings("unchecked")
	public List<BattLadungE> holeBattLadungTag(LocalDate tag) {
		Query query = em.createNamedQuery("Batterie.holeLadungByTag", BattLadungE.class);
		query.setParameter("tag", tag);
		
		return query.getResultList();
	}
	
	
}