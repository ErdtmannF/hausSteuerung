package de.erdtmann.soft.hausSteuerung.login.bean;

import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import de.erdtmann.soft.hausSteuerung.login.LoginRepository;

import java.io.Serializable;

@Named
@SessionScoped
public class Login implements Serializable {

	private static final long serialVersionUID = 7563756915746053977L;
	
	Logger log = Logger.getLogger(Login.class);
	
	@Inject
	LoginRepository loginRepo;
	
	private String pwd;
	private String msg;
	private String user;

	//validate login
	public String loginSite() {
		
		boolean valid = loginRepo.login(user, pwd);

		if (valid) {
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("username", user);
			return "home";
		} else {
			log.error("Benutzeranmeldung fehlgeschlagen");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falscher Benutzername oder Passwort", "Test"));
			return "login";
		}
	}

	//logout event, invalidate session
	public String logout() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		return "login";
	}
	
	public String getPwd() {
		return this.pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMsg() {
		return this.msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUser() {
		return this.user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
