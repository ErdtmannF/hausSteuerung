package de.erdtmann.soft.hausSteuerung.login;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import de.erdtmann.soft.hausSteuerung.login.entities.UserE;

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
		
		// Es wurde ein Eintrag gefunden
		if (list.size() > 0) {
			return true;
		// Es wurde kein Eintrag gefunden
		} else {
			return false;
		}
	}

	
}
