package it.univr.beans;

import it.univr.db.DataSourceCorrettoreBozza;
import it.univr.db.DataSourceInserzione;
import it.univr.db.DataSourceLibro;
import it.univr.db.DataSourceUtente;
import it.univr.messages.MessagesHandler;
import it.univr.models.AmministrazioneModel;
import it.univr.models.InserzioneModel;
import it.univr.models.PairInserzioneLibroModel;
import it.univr.utils.EmailSender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean per la gestione del correttore di bozze.
 */

@ManagedBean
@SessionScoped
public class CorrettoreBean extends AbstractUtente implements Serializable{
	
	private AmministrazioneModel correttore;

	private DataSourceCorrettoreBozza dsCorr;
	
	private DataSourceInserzione dsIns;
	
	private DataSourceLibro dsLib;
	
	private DataSourceUtente dsUt;
	
	private List<PairInserzioneLibroModel> listaInsLibDV;
	
	private EmailSender emailSender;

	private String emailMessage;
	
	

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -5662233765654870404L;
	
	/**
	 * Inizializza il bean.  
	 */
	
	@PostConstruct
	public void initialize() {
		dsCorr = new DataSourceCorrettoreBozza();
		dsIns = new DataSourceInserzione();
		dsLib = new DataSourceLibro();
		dsUt = new DataSourceUtente();
		emailSender = new EmailSender();
	}
	
	public AmministrazioneModel getCorrettore() { return correttore; }
	
	@Override
	public Object verifyUser(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
		
		List<Object> list = new ArrayList<Object>();
		
		list.add(getUsername());
		list.add(getPassword());
			
		correttore = dsCorr.getCorrettoreBozza(list);
		
		if (correttore == null)
           return null;
		else{
			// Aggiorno il timestamp del last login
			list = new ArrayList<Object>();
			list.add(correttore.getId());
			dsCorr.updateLastLogin(list);			
			
			//Ritorno l'oggetto amministratore per indicare
			//l'avvenuto login
			return correttore;
		}
	}
	
	public String logout() {
		
		if(correttore != null) {
			// Aggiorno il timestamp del last logout
			List<Object> list = new ArrayList<Object>();
			list.add(correttore.getId());
			dsCorr.updateLastLogout(list);			
		}
		return super.logout();
	}

	public boolean isLoggedIn() { return this.getLoggedIn(correttore); }
	
	@Override
	public void cleanUp() {
		cleanFields();
		
		this.correttore = null;
	}
		
	/**
	 * Ritorno la lista di coppie inserzione libro da validare
	 * 
	 * @return lista di coppie libro inserzione
	 */
	public List<PairInserzioneLibroModel> getInserzioniDaValidare() {

		listaInsLibDV = new ArrayList<PairInserzioneLibroModel>();

		// Recupero la lista di inserzioni
		List<InserzioneModel> ins = new ArrayList<InserzioneModel>();

		List<Object> list = new ArrayList<Object>();

		ins = dsIns.getInserzioniDaValidare();
		
		for (InserzioneModel inserzione : ins) {
			list.clear();
			list.add(inserzione.getCodice_libro());

			listaInsLibDV.add(new PairInserzioneLibroModel(inserzione, dsLib
					.getLibro(list)));
		}
		
		return listaInsLibDV;
	}
	
	public String rifiutaInserzione(int id_utente, int codice_libro){
		
		List<Object> list = new ArrayList<Object>();
		
		list.add(id_utente);
		list.add(codice_libro);
		
		dsIns.deleteInserzione(list);
		
		list.clear();
		list.add(id_utente);
		
		// Invia una mail con la motivazione del rifiuto all'utente possessore del libro
		emailSender.sendEmail(emailMessage, dsUt.getUtente(list).getEmail());
		
		MessagesHandler.getInstance().buildMessage("emailRifiutoInviata", 
				FacesMessage.SEVERITY_INFO);
		
		return this.setState("lista_inserzioni","correttore");
	}

	public String validaInserzione(int id_utente, int codice_libro, String titolo) {
		List<Object> list = new ArrayList<Object>();

		list.add(correttore.getId());
		list.add(id_utente);
		list.add(codice_libro);

		dsIns.validateInserzione(list);

		list.clear();
		list.add(id_utente);
		// Invia una mail con la motivazione del rifiuto all'utente possessore
		// del libro
		emailSender.sendEmail("Buongiorno,\n Il libro '\""
				+ titolo
				+ "\" da lei inserito è stato validato.\n Cordiali saluti,\n\nBookExchange",
				dsUt.getUtente(list).getEmail());
		
		MessagesHandler.getInstance().buildMessage("emailAccettazioneInviata", 
				FacesMessage.SEVERITY_INFO);
		
		return this.setState("lista_inserzioni","correttore");
	}
	
	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}
}
