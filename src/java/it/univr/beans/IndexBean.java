package it.univr.beans;


import it.univr.messages.MessagesHandler;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Bean di tutti i tipi di Utente.
 */

@ManagedBean
@SessionScoped
public class IndexBean implements Serializable
{

    /** Serial Version UID. */

	private static final long serialVersionUID = 90526546886875511L;
	
	@ManagedProperty(value = "#{utenteBean}")
	private UtenteBean utenteBean;
	
	@ManagedProperty(value = "#{correttoreBean}")
	private CorrettoreBean correttoreBean;
	
	@ManagedProperty(value = "#{amministratoreBean}")
	private AmministratoreBean amministratoreBean;
	
	@ManagedProperty(value = "#{registrazioneBean}")
	private RegistrazioneBean registrazioneBean;
	
	private String username;
	
	private String password;
	
	private boolean loggedIn;
	
	
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * Inizializza il bean.  
	 */
	
	@PostConstruct
	public void initialize() {
		loggedIn = false;
	}
		
	public String login(){
		
		
		if(utenteBean.verifyUser(username, password) != null) {
			
			loggedIn = true;
			return "utente";
		
		} else if (correttoreBean.verifyUser(username, password) != null) {
			
			loggedIn = true;
			return "correttore";
		
		}else if(amministratoreBean.verifyUser(username, password) != null) {
			
			loggedIn = true;
			return "amministratore";
		
		}else {
			
			MessagesHandler.getInstance().buildMessage("loginFailed", 
					FacesMessage.SEVERITY_ERROR);
		}
		
		return "index";
	}
	
	public String registrazione() {
		
		if(registrazioneBean.registrazioneAux())
			return "verificaMail";
		else
			MessagesHandler.getInstance().buildMessage("registrationFailed", 
					FacesMessage.SEVERITY_ERROR);
		return "";
	}
		
	public void logout() {
		loggedIn = false;

		if (utenteBean.isLoggedIn())
			utenteBean.logout();
		
		else if (correttoreBean.isLoggedIn())
			correttoreBean.logout();
		
		else
			amministratoreBean.logout();
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UtenteBean getUtenteBean() {
		return utenteBean;
	}

	public void setUtenteBean(UtenteBean utenteBean) {
		this.utenteBean = utenteBean;
	}

	public CorrettoreBean getCorrettoreBean() {
		return correttoreBean;
	}

	public void setCorrettoreBean(CorrettoreBean correttoreBean) {
		this.correttoreBean = correttoreBean;
	}

	public AmministratoreBean getAmministratoreBean() {
		return amministratoreBean;
	}

	public void setAmministratoreBean(AmministratoreBean amministratoreBean) {
		this.amministratoreBean = amministratoreBean;
	}

	public RegistrazioneBean getRegistrazioneBean() {
		return registrazioneBean;
	}

	public void setRegistrazioneBean(RegistrazioneBean registrazioneBean) {
		this.registrazioneBean = registrazioneBean;
	}
	
}
