package it.univr.beans;

import it.univr.db.DataSourceUtente;
import it.univr.exceptions.DatabaseException;
import it.univr.messages.MessagesHandler;
import it.univr.models.UtenteModel;
import it.univr.utils.EmailSender;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean per la registrazione dell'Utente.
 */

@ManagedBean
@SessionScoped
public class RegistrazioneBean implements Serializable
{
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 3677997034601234180L;

	private DataSourceUtente dsUtente;
	
	/** L'utente da inserire */
	private UtenteModel utente;
	
	private EmailSender emailSender;
	
	/** Path del file di properties */
	private static final String configEmail = 
			"/it/univr/properties/email.properties";
	
	/** Properties che mappano gli stati con i pezzi jsf da includere */
	private Properties email_properties;
	
	/** Inizializza il bean. */
	@PostConstruct
	public void initialize() {
		this.dsUtente = new DataSourceUtente();
		this.utente = new UtenteModel();
		this.emailSender = new EmailSender();
		email_properties = new Properties();

		try {
			email_properties.load(Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(configEmail));
		} catch (IOException e) {
			throw new DatabaseException();
		}
	}
	
	/** Registro il nuovo utente inserito */
	public String registraNuovoUtente() {
		
		//Mi assicuro che l'username inserito non sia gia in uso
		List<Object> list = new ArrayList<Object>();
		list.add(utente.getUsername());
		if(!dsUtente.isNewUsername(list)){
			MessagesHandler.getInstance().buildMessage("usernameGiaUsato", FacesMessage.SEVERITY_INFO);
			return "index";
		}
		
		//Aggiungo l'utente al Database
		list.clear();
		list.add(utente.getNome());
		list.add(utente.getCognome());
		list.add(utente.getUsername());
		list.add(utente.getPassword());
		list.add(utente.getEmail());
		list.add(utente.getIndirizzo());
		list.add(utente.getComune());
		list.add(utente.getProvincia());
		list.add(utente.getRegione());
		list.add(utente.getFoto());
		list.add(utente.getN_cell());
		
		try {
			//Inserisco il nuovo utente nel database
			dsUtente.inserimentoUtente(list);
			
			//Invio una mail con i dati del nuovo utente alla sua email
			String[] emailblocks = email_properties.getProperty("message.registration").replace("|", "\n").split("-");
			
			String[] contents = {
					utente.getNome(),
					utente.getUsername(),
					utente.getPassword()};
			
			String emailMessage="";
			
			for (int i = 0; i < contents.length; i++)
				emailMessage += emailblocks[i] + contents[i];
			// Invio la email con la avvenuta registrazione
			if (!emailSender.sendEmail(emailMessage, utente.getEmail())) {
				MessagesHandler.getInstance()
						.buildMessage("erroreRegistrazioneUtente",
								FacesMessage.SEVERITY_INFO);
				return "index";
			}
			

			MessagesHandler.getInstance().buildMessage("registrazioneEffettuata", FacesMessage.SEVERITY_INFO);
		} catch (SQLException e) {
			MessagesHandler.getInstance().buildMessage("erroreRegistrazioneUtente", FacesMessage.SEVERITY_INFO);
		}
		
		//Aggiorno l'utente
		utente = new UtenteModel();
		return "index";
	}
	
	public UtenteModel getUtente() { return utente; }

	public void setUtente(UtenteModel utente) { this.utente = utente; }

	/** Pulisco il Bean dagli oggetti non necessari dopo un logout. */
	public void cleanUp()
	{
		this.utente = null;
	}
	
}
