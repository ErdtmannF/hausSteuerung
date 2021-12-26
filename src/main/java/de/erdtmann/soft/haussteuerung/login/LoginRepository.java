package de.erdtmann.soft.haussteuerung.login;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import de.erdtmann.soft.haussteuerung.login.entities.UserE;

@Stateless
public class LoginRepository {

	Logger log = Logger.getLogger(LoginRepository.class);
	
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public boolean login(String user, String pw) {
		Query query = em.createNamedQuery("User.login", UserE.class);
		query.setParameter("user", user);
		query.setParameter("pw", pw);
		
		List<UserE> list = query.getResultList();
		
		return !list.isEmpty();
	}

	
}
