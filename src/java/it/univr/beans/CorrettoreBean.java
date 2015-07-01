package it.univr.beans;

import it.univr.db.DataSourceCorrettoreBozza;
import it.univr.db.DataSourceInserzione;
import it.univr.models.AmministrazioneModel;
import it.univr.models.InserzioneModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
	
	private InserzioneModel inserzione;
		
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
	}
	
	public AmministrazioneModel getCorrettore() {
		return correttore;
	}
	
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
		if(correttore!=null){
			// Aggiorno il timestamp del last logout
			List<Object> list = new ArrayList<Object>();
			list.add(correttore.getId());
			dsCorr.updateLastLogout(list);			
		}
		
		return super.logout();
	}

	public boolean isLoggedIn() {
		return this.getLoggedIn(correttore);
	}
	
	@Override
	public void cleanUp() {
		cleanFields();
		
		this.correttore = null;
	}
	
	public List<InserzioneModel> getInserzioniDaValidare(){
		return dsIns.getInserzioniDaValidare();
	}	
	
	public void rifiutaInserzione(String motivazione){
		
		//TODO inviare una email con la motivazione
		
		List<Object> list = new ArrayList<Object>();
		
		list.add(inserzione.getCodice_libro());
		
		dsIns.deleteInserzione(list);
	}

	public void validaInserzione(){
		List<Object> list = new ArrayList<Object>();
		
		list.add(correttore.getId());
		list.add(inserzione.getId_utente());
		list.add(inserzione.getCodice_libro());
		
		dsIns.validateInserzione(list);
	}

}
